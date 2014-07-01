use mdm
db.createCollection("log")

db.deviceLocationMongo.find({'imei':'589899886964646'},{imei:1,op_time:1}).count()

db.log.find({'log_category':2},{op_time:1,log_category:1,software_name:1}).sort({op_time:-1})


db.log.find({'log_category':2},{op_time:1,log_category:1,software_name:1}).sort({op_time:-1})
db.log.find({'log_category':2}).sort({op_time:-1})
db.log.count({'log_category':2})


db.log.group( {key: { imei:1 },cond: { log_category: 0},reduce: function ( curr, result ) { },initial: {total : 0 }} )


-- group
db.deviceLocationMongo.group( {key: { imei:1 },cond: {},reduce: function ( curr, result ) {result.total += 1; },initial: {total : 0 }} )


db.log.group( {key: { imei:1,op_type:1 },cond: { log_category: 0,op_type:0},reduce: function ( curr, result ) {result.total += 1; },initial: {total : 0 }} )


db.log.aggregate({$match : {log_category : 0}},{ $group : { log_category : "$log_category", total : { $sum : 1 } }});

db.log.aggregate({$group : {_id : "$hosting", total : { $sum : 1 }}} );
				
-- 	aggregate	
db.log.aggregate( 
{$match : { op_type :2, log_category:1,username:{$in: ["min.zhao","aaa"]},"$and":[
	{op_time:{$gt:ISODate("2014-03-18T07:50:26.614Z")}}, 
	{op_time:{$lt:ISODate("2014-03-28T23:50:26.614Z")}} 
 ] }}, 
{$group : { _id : "$imei", count : { $sum : 1 }}  },  
{$sort : {count : -1}}, 
{$limit:10} )


---\
db.log.aggregate( 
{$match : { op_type :0, log_category:2,"$and":[
 {op_time:{$gt:ISODate("2014-03-18T07:50:26.614Z")}}, 
 {op_time:{$lt:ISODate("2014-04-05T23:50:26.614Z")}} 
 ] }}, 
{$group : { _id : "$pkg_name", count : { $sum : 1 }}  },  
{$sort : {count : -1}}, 
{$limit:10} )




-- 	aggregate	
, username:"$username",op_time:"$op_time"
username : { $username : 1 },
username:{$addToSet: "$username"},



db.log.aggregate(
{$match : { op_type :0, log_category:2,"$and":[
 {op_time:{$gt:ISODate("2014-03-18T07:50:26.614Z")}}, 
 {op_time:{$lt:ISODate("2014-04-05T23:50:26.614Z")}} 
 ] }}, 
{$group : {
_id :{pkg_name:"$pkg_name",software_name:"$software_name"},
count : { $sum : 1 } 
} },  
{$sort : {count : -1}}, 
{$limit:10} )

------------------------
db.log.aggregate( 
{$match : { op_type :0, log_category:0, "$and" : [ 
{ op_time : { $gt : ISODate('2014-03-10T10:01:38Z')}} , 
{ op_time : { $lt : ISODate('2014-03-28T10:01:38Z')}}] }}, 
{$group : { _id : "$imei", total : { $sum : 1 }}  },  
{$sort : {total : -1}}, 
{$limit:10} )



db.log.aggregate( 
{$match : { op_type :0, log_category:0 }}, 
{$group : { _id : "$imei", total : { $sum : 1 }, average:{$avg:"$amount"}}  },  
{$sort : {total : -1}}, 
{$limit:10} )


-- and 
db.log.find(
{
$and:[{op_time:{$gt:ISODate("2014-03-27T07:50:26.614Z")}}, {op_time:{$lt:ISODate("2014-03-28T23:50:26.614Z")}}]
}
).count()

db.log.aggregate(
	{ $project : {
	_id : 0,
	op_time:1
	}}
);


		



		

///////////////////////////////////////////////////////////////////////////////////
-- 插入
db.log.insert({
'log_category':0,
'device_uuid':'0000000000000000000000000000',
'imei':'12345678',
'username':'min.zhao',
'curr_login':'min.zhao',
'op_type':0,
'op_time':new Date(),
'remark':'nothing'
})

	private int  op_type;	//操作类型
	
	private String imsi_before; //更换前的imsi号
	private String phone_num_before; // 更换前的手机号
	private int special_card_before; //更换前的sim卡是否是内部卡
	private String imsi_after;
	private String phone_num_after;
	private int special_card_after;
	db.log.insert({
'log_category':3,
'device_uuid':'0000000000000000000000000000',
'imei':'12345678',
'username':'min.zhao',
'curr_login':'min.zhao',
'op_type':0,
'op_time':new Date(),
'imsi_before':'0000000000000000000000000001',
'phone_num_before':'13999124512',
'special_card_before':1,
'imsi_after':'0000000000000000000000000002',
'phone_num_after':'13999124518',
'special_card_after':1,
'remark':'nothing'
})

	
	private final int log_category = Constants.LOG_TYPE_SOFT;	//日志分类
	private int  op_type;	//操作类型
	String software_name;	//软件名称
	String pkg_name; 	//软件包名
	String version;	//软件版本号
	
db.log.insert({
'log_category':2,
'device_uuid':'0000000000000000000000000000',
'imei':'12345678',
'username':'min.zhao',
'curr_login':'min.zhao',
'op_type':0,
'op_time':new Date(),
'software_name':'任我游导航',
'pkg_name':'navi.unistrong.com',
'version':'1.0.15'
})
	

-- 插入
db.log.insert({
'log_category':4,
'imei':'0000000000000000000000000000',
'op_time':new Date(),
'remark':'nothing'
})


"op_time" : "2014-03-14 17:15:00",
db.inventory.remove( { op_time : "2014-03-14 17:15:00" })

db.inventory.remove( {"log_category":2 })

-- 查询
db.log.find({log_category:3})
