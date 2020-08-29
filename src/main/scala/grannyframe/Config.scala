package grannyframe

case class Config (bot: Bot, database: Database, frontend: Frontend)

case class Bot(token: String)

case class Database(connectionString: String, database: String)

case class Frontend(imageCount: Int, imageTime: Int, newImageTime: Int)
