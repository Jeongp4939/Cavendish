from sqlalchemy import Column, Integer, String, Float, SmallInteger, BigInteger
from sqlalchemy.orm import declarative_base

Base = declarative_base()

class GPU(Base):
    __tablename__ = 'gpu'
    id = Column(Integer, primary_key=True)
    name = Column(String(200), nullable=False)
    price = Column(Integer)
    link = Column(String(500), nullable=False)
    company = Column(String(30), nullable=False)
    product_seq = Column(Integer, nullable=False)
    image = Column(String(500), nullable=True, comment='이미지 링크')
    chipset_company = Column(String(30))
    chipset = Column(String(30), nullable=False)
    nm = Column(Integer, comment='단위: nm')
    base_clock = Column(Integer, comment='단위: MHz')
    boost_clock = Column(Integer, comment='단위: MHz')
    cuda_processor = Column(Integer, comment='단위: 개')
    stream_processor = Column(Integer, comment='단위: 개')
    interface = Column(String(30), comment='호환성')
    memory_type = Column(String(20))
    memory_capacity = Column(Float, comment='단위: GB')
    memory_clock = Column(Integer, comment='단위: MHz')
    memory_bus = Column(Integer, comment='단위: bit')
    port = Column(Integer, comment='비트마스킹')
    monitor_support = Column(Integer, comment='단위: 최대 n개')
    additional_function = Column(Integer, comment='비트마스킹')
    usage_power = Column(Integer, comment='단위: 최대 nW')
    recommend_power = Column(Integer, comment='호환성, 단위: W')
    cooling_type = Column(Integer, comment='비트마스킹')
    pan_number = Column(SmallInteger, comment='단위: 개')
    length = Column(Float, comment='호환성, 단위: mm')
    thickness = Column(Float, comment='단위: mm')
    pin = Column(String(6), comment='6/8/12/16핀 갯수')
    feature = Column(Integer, comment='비트마스킹')
    as_years = Column(SmallInteger, comment='단위: 년')
    bench_mark = Column(Integer)
    reg_date = Column(Integer, comment='yyyymm')
    bookmark = Column(Integer, default=0)