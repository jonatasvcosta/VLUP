from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import scoped_session, sessionmaker
from sqlalchemy import Column, ForeignKey, Integer, Text, String, create_engine
from sqlalchemy.orm import relationship

DBSession = scoped_session(sessionmaker(autocommit=True))
Base = declarative_base()
conn = "postgresql+psycopg2://vlup:g9txVUyyB5Q3Es57CYneWh@database.vlup.com.br:5432/vlupdb"


def initialize_sql():
    engine = create_engine(conn)
    DBSession.configure(bind=engine)
    Base.metadata.bind = engine
    Base.metadata.create_all(engine)


class Website(Base):
    __tablename__ = 'news_website'
    id = Column(Integer, primary_key=True)
    name = Column(String(255))
    url = Column(String(255))


class Article(Base):
    __tablename__ = 'news_article'
    id = Column(Integer, primary_key=True)
    title = Column(String(300))
    text = Column(Text())
    url = Column(String(500))
    website_id = Column(Integer, ForeignKey('news_website.id'))
    website = relationship(Website)
