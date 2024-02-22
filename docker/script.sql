create table clients
(
    id       bigserial not null,
    limite   bigint,
    nome     varchar(50),
    saldo    bigint,
    -- version integer default 1,
    primary key (id)
);

create table transactions
(
    id               bigserial    not null,
    create_at        timestamp(6) with time zone,
    description      varchar(50)  not null,
    type_transaction varchar(1) not null check (type_transaction in ('C', 'D')),
    value            bigint       not null,
    client_id        bigint       not null,
    primary key (id)
);

alter table if exists transactions
    add constraint FKjp6w7dmqrj0h9vykk2pbtik2 foreign key (client_id) references clients;

create index if not exists transactions_order_date on transactions using btree (create_at desc);

delete
from transactions;
delete
from clients;

DO
$$
    BEGIN
        insert into clients(id, limite, nome, saldo)
        values (1, 100000, 'Joao', 0),
               (2, 80000, 'Maria', 0),
               (3, 1000000, 'Fabio', 0),
               (4, 10000000, 'Louco', 0),
               (5, 500000, 'Lucas', 0);
    END;
$$;


CREATE OR REPLACE FUNCTION create_transaction(IN _id_client bigint,
                                              IN _valor bigint,
                                              IN _descricao varchar(20),
                                              IN _tipo varchar)
    RETURNS TABLE
            (
                ret_return_status int,
                ret_limite        bigint,
                ret_saldo         bigint
            )
AS
$$
DECLARE
    response    clients%rowtype;
    saldo_final bigint;
BEGIN
    SELECT * INTO response FROM clients WHERE id = _id_client;

    IF not found THEN
        return query select 404, 0::bigint, 00::bigint;
    END IF;

    IF _tipo = 'C' OR _tipo = 'c' THEN
        saldo_final := response.saldo + _valor;
    ELSE
        IF response.limite + response.saldo < _valor THEN
            return query select 422, response.limite, response.saldo ;
        ELSE
            saldo_final := response.saldo - _valor;
        END IF;
    END IF;

    -- Add a record to Transaction
    INSERT INTO transactions(create_at, description, type_transaction, value, client_id)
    VALUES (current_timestamp, _descricao, _tipo, _valor, _id_client);

    -- Save the changes to the Client
    UPDATE clients
    SET saldo = saldo_final
    WHERE id = _id_client
    RETURNING saldo, limite into response;

    return query select 200, response.limite, response.saldo;
END
$$
    LANGUAGE plpgsql;

-- drop function create_transaction(id_client bigint, valor bigint, descricao varchar(20), tipo varchar)

ALTER FUNCTION create_transaction(bigint, bigint, varchar, varchar) OWNER TO rinha_user;

-- select * from create_transaction(1, 1, 'function 1', 'D') limit 1;
