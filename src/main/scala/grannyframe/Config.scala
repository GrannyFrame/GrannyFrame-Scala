package grannyframe

case class Config (bot: Bot, frontend: Frontend)

case class Bot(token: String)

case class Frontend(imageCount: Int, imageTime: Int)
