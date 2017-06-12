from ..base import Base
from sqlalchemy import Column, Integer, Float, String


class User(Base):
    __tablename__ = 'user'
    user_id = Column(Integer, primary_key=True)
    name = Column(String(255))
    email = Column(String(255))
    password = Column(String(255))

    native_language = Column(String(20))
    learning_language = Column(String(20))

    latitude = Column(Float())
    longitude = Column(Float())
