--
-- PostgreSQL database dump
--

-- Dumped from database version 17.5
-- Dumped by pg_dump version 17.5

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: problems; Type: TABLE; Schema: public; Owner: isiki
--

CREATE TABLE public.problems (
    id integer NOT NULL,
    title text NOT NULL,
    description text,
    time_limit integer,
    memory_limit integer,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    input text,
    output text
);


ALTER TABLE public.problems OWNER TO isiki;

--
-- Name: problems_id_seq; Type: SEQUENCE; Schema: public; Owner: isiki
--

CREATE SEQUENCE public.problems_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.problems_id_seq OWNER TO isiki;

--
-- Name: problems_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: isiki
--

ALTER SEQUENCE public.problems_id_seq OWNED BY public.problems.id;


--
-- Name: submissions; Type: TABLE; Schema: public; Owner: isiki
--

CREATE TABLE public.submissions (
    id integer NOT NULL,
    username character varying(50) NOT NULL,
    problem_id integer NOT NULL,
    language character varying(20) NOT NULL,
    code text NOT NULL,
    status character varying(20) DEFAULT 'Pending'::character varying,
    submit_time timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.submissions OWNER TO isiki;

--
-- Name: submissions_id_seq; Type: SEQUENCE; Schema: public; Owner: isiki
--

CREATE SEQUENCE public.submissions_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.submissions_id_seq OWNER TO isiki;

--
-- Name: submissions_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: isiki
--

ALTER SEQUENCE public.submissions_id_seq OWNED BY public.submissions.id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: isiki
--

CREATE TABLE public.users (
    id integer NOT NULL,
    username character varying(50) NOT NULL,
    password character varying(100) NOT NULL,
    role character varying(20) DEFAULT 'USER'::character varying,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.users OWNER TO isiki;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: isiki
--

CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.users_id_seq OWNER TO isiki;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: isiki
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- Name: problems id; Type: DEFAULT; Schema: public; Owner: isiki
--

ALTER TABLE ONLY public.problems ALTER COLUMN id SET DEFAULT nextval('public.problems_id_seq'::regclass);


--
-- Name: submissions id; Type: DEFAULT; Schema: public; Owner: isiki
--

ALTER TABLE ONLY public.submissions ALTER COLUMN id SET DEFAULT nextval('public.submissions_id_seq'::regclass);


--
-- Name: users id; Type: DEFAULT; Schema: public; Owner: isiki
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- Data for Name: problems; Type: TABLE DATA; Schema: public; Owner: isiki
--

COPY public.problems (id, title, description, time_limit, memory_limit, created_at, input, output) FROM stdin;
1	两数之和	给定一个整数数组和目标值，找出两个数之和等于目标值。	1000	65536	2025-06-24 17:20:15.073949	\N	\N
\.


--
-- Data for Name: submissions; Type: TABLE DATA; Schema: public; Owner: isiki
--

COPY public.submissions (id, username, problem_id, language, code, status, submit_time) FROM stdin;
1	alice	1	java	public class Main { public static void main(String[] args) { System.out.println("Hello"); } }	Pending	2025-06-24 18:38:19.431706
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: isiki
--

COPY public.users (id, username, password, role, created_at) FROM stdin;
1	alice	123456	USER	2025-06-24 17:03:39.118724
\.


--
-- Name: problems_id_seq; Type: SEQUENCE SET; Schema: public; Owner: isiki
--

SELECT pg_catalog.setval('public.problems_id_seq', 1, true);


--
-- Name: submissions_id_seq; Type: SEQUENCE SET; Schema: public; Owner: isiki
--

SELECT pg_catalog.setval('public.submissions_id_seq', 1, true);


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: isiki
--

SELECT pg_catalog.setval('public.users_id_seq', 1, true);


--
-- Name: problems problems_pkey; Type: CONSTRAINT; Schema: public; Owner: isiki
--

ALTER TABLE ONLY public.problems
    ADD CONSTRAINT problems_pkey PRIMARY KEY (id);


--
-- Name: submissions submissions_pkey; Type: CONSTRAINT; Schema: public; Owner: isiki
--

ALTER TABLE ONLY public.submissions
    ADD CONSTRAINT submissions_pkey PRIMARY KEY (id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: isiki
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: users users_username_key; Type: CONSTRAINT; Schema: public; Owner: isiki
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_username_key UNIQUE (username);


--
-- PostgreSQL database dump complete
--

