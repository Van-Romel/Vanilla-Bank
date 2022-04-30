CREATE SCHEMA IF NOT EXISTS "vanilla_bank"
    AUTHORIZATION postgres;

COMMENT ON SCHEMA "vanilla_bank"
    IS 'standard vanilla_bank application schema';

GRANT ALL ON SCHEMA "vanilla_bank" TO PUBLIC;

GRANT ALL ON SCHEMA "vanilla_bank" TO postgres;