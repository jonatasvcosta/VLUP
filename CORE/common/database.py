from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import scoped_session, sessionmaker
from sqlalchemy import create_engine

DBSession = scoped_session(sessionmaker(autocommit=True))
Base = declarative_base()
conn = "postgresql+psycopg2://vlup:g9txVUyyB5Q3Es57CYneWh@database.vlup.com.br:5432/vlupdb"


def initialize_sql():
    engine = create_engine(conn)
    DBSession.configure(bind=engine)
    Base.metadata.bind = engine
    Base.metadata.create_all(engine)
