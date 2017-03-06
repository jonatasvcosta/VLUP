from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import scoped_session, sessionmaker
from tenacity import retry, wait_exponential, stop_after_attempt

DBSession = scoped_session(sessionmaker())
Base = declarative_base()


@retry(wait=wait_exponential(multiplier=1, max=10),
       stop=stop_after_attempt(5))
def initialize_sql(engine):
    DBSession.configure(bind=engine)
    Base.metadata.bind = engine
    Base.metadata.create_all(engine)
