# Seckill by using akka

https://akka.io/
High Performance
Up to 50 million msg/sec on a single machine. Small memory footprint; ~2.5 million actors per GB of heap
akka宣称每GB可以创建250万个actor,
1GB=1024MB
1024MB=1024*1024kb=1048576kb
1个actor大概占用空间:1048576kb/2500000=0.4kb=4byte
## 秒杀系统如果用actor来表示可以分为
 - sku actor 每一个商品对应一个actor()
 - 
