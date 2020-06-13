    Hello, Evaldas
    Thank you for your patience!
   ______________________________________
    
    The aggregator accepts a data.csv file, which you can downloaded from
 https://covid19.gov.ua/csv/data.csv
  
    sbt run 
    and aggregator starts to filter the streets of Odessa 
    from a file /data.csv and scraps only the latest publications 
    data the real estate from site 
    
    After extracting, cleaning and
    sorting by priceSqm listings saves top 10 locally with the best price per square meter.
    Listings are updated every 60 min.

    Wait until loger say "Update data in Store" in the logger, 
    after that you can get the data, on:

http://localhost:8080/api/v1/flatfy/list