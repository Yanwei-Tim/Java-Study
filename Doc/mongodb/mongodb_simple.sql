db.deviceLocationMongo.find({"log_category":2}).sort({create_time:-1}).limit(10)

db.log.find({"log_category":2}).sort({create_time:-1})