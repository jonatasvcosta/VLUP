from sqlalchemy import Column, ForeignKey, Integer, Text, String, DateTime, create_engine
from sqlalchemy.orm import relationship
from common.database import Base

class Website(Base):
    __tablename__ = 'news_website'
    id = Column(Integer, primary_key=True)
    name = Column(String(255))
    language = Column(String(255))
    url = Column(String(255))


class Article(Base):
    __tablename__ = 'news_article'
    id = Column(Integer, primary_key=True)
    title = Column(String(300))
    text = Column(Text())
    url = Column(String(500))

    description = Column(String(500))
    image_url = Column(String(500))
    publish_date = Column(DateTime())

    website_id = Column(Integer, ForeignKey('news_website.id'))
    website = relationship(Website)
