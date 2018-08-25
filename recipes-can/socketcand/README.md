### Short socketcand user manual: How to activate socketcand on Beaglebone Black?

```
root@beaglebone:~# modprobe can
[  481.509387] can: controller area network core (rev 20170425 abi 9)
[  481.516123] NET: Registered protocol family 29
root@beaglebone:~#   modprobe can_raw
[  481.567775] can: raw protocol (rev 20170425)
root@beaglebone:~#   modprobe can-bcm
[  481.618128] can: broadcast manager protocol (rev 20170425 t)
root@beaglebone:~#   modprobe can-dev
[  481.668321] CAN device driver interface
root@beaglebone:~#   modprobe can-gw
[  481.717922] can: netlink gateway (rev 20170425) max_hops=1
root@beaglebone:~#   modprobe vcan
[  481.767334] vcan: Virtual CAN interface driver
root@beaglebone:~#   lsmod | grep can
vcan                   16384  0
can_gw                 20480  0
can_dev                24576  0
can_bcm                24576  0
can_raw                20480  0
can                    28672  3 can_raw,can_bcm,can_gw
root@beaglebone:~# ip link add dev vcan0 type vcan
root@beaglebone:~#   ip link set vcan0 mtu 72
root@beaglebone:~#   ip link set dev vcan0 up
root@beaglebone:~#   ifconfig
eth0      Link encap:Ethernet  HWaddr F0:45:DA:83:09:B4  
          inet addr:192.168.15.101  Bcast:192.168.15.255  Mask:255.255.255.0
          UP BROADCAST RUNNING MULTICAST  MTU:1500  Metric:1
          RX packets:8 errors:0 dropped:0 overruns:0 frame:0
          TX packets:24 errors:0 dropped:0 overruns:0 carrier:0
          collisions:0 txqueuelen:1000 
          RX bytes:3101 (3.0 KiB)  TX bytes:6156 (6.0 KiB)
          Interrupt:44 

lo        Link encap:Local Loopback  
          inet addr:127.0.0.1  Mask:255.0.0.0
          UP LOOPBACK RUNNING  MTU:65536  Metric:1
          RX packets:2 errors:0 dropped:0 overruns:0 frame:0
          TX packets:2 errors:0 dropped:0 overruns:0 carrier:0
          collisions:0 txqueuelen:1000 
          RX bytes:140 (140.0 B)  TX bytes:140 (140.0 B)

vcan0     Link encap:UNSPEC  HWaddr 00-00-00-00-00-00-00-00-00-00-00-00-00-00-00-00  
          UP RUNNING NOARP  MTU:72  Metric:1
          RX packets:0 errors:0 dropped:0 overruns:0 frame:0
          TX packets:0 errors:0 dropped:0 overruns:0 carrier:0
          collisions:0 txqueuelen:1000 
          RX bytes:0 (0.0 B)  TX bytes:0 (0.0 B)

root@beaglebone:~# ifconfig
eth0      Link encap:Ethernet  HWaddr F0:45:DA:83:09:B4  
          inet addr:192.168.15.101  Bcast:192.168.15.255  Mask:255.255.255.0
          UP BROADCAST RUNNING MULTICAST  MTU:1500  Metric:1
          RX packets:12 errors:0 dropped:0 overruns:0 frame:0
          TX packets:24 errors:0 dropped:0 overruns:0 carrier:0
          collisions:0 txqueuelen:1000 
          RX bytes:3360 (3.2 KiB)  TX bytes:6156 (6.0 KiB)
          Interrupt:44 

lo        Link encap:Local Loopback  
          inet addr:127.0.0.1  Mask:255.0.0.0
          UP LOOPBACK RUNNING  MTU:65536  Metric:1
          RX packets:2 errors:0 dropped:0 overruns:0 frame:0
          TX packets:2 errors:0 dropped:0 overruns:0 carrier:0
          collisions:0 txqueuelen:1000 
          RX bytes:140 (140.0 B)  TX bytes:140 (140.0 B)

vcan0     Link encap:UNSPEC  HWaddr 00-00-00-00-00-00-00-00-00-00-00-00-00-00-00-00  
          UP RUNNING NOARP  MTU:72  Metric:1
          RX packets:0 errors:0 dropped:0 overruns:0 frame:0
          TX packets:0 errors:0 dropped:0 overruns:0 carrier:0
          collisions:0 txqueuelen:1000 
          RX bytes:0 (0.0 B)  TX bytes:0 (0.0 B)

root@beaglebone:~# cat /etc/socketcand.conf
# The network interface the socketcand will bind to
# listen = "eth0";

# The port the socketcand is listening on
# port = 28600;

# List of busses the daemon shall provide access to
# Multiple busses must be separated with ',' and whitespace
# is not allowed. eg "vcan0,vcan1"
busses = "vcan0";

# Description of the service. This will show up in the discovery beacon
# description = "socketcand";

root@beaglebone:~# vi /etc/socketcand.conf
root@beaglebone:~# cat /etc/socketcand.conf
# The network interface the socketcand will bind to
listen = "127.0.0.1";

# The port the socketcand is listening on
port = 28601;

# List of busses the daemon shall provide access to
# Multiple busses must be separated with ',' and whitespace
# is not allowed. eg "vcan0,vcan1"
busses = "vcan0";

# Description of the service. This will show up in the discovery beacon
description = "socketcand";

root@beaglebone:~# ps -aux | grep socketcand
root      1259  0.0  0.2   2776  1228 ttyO0    S+   05:39   0:00 grep socketcand
root@beaglebone:~# /etc/init.d/socketcand start
Starting SocketCAN daemon socketcand
root@beaglebone:~# ps -aux | grep socketcand
root      1263  0.0  0.2  10196  1324 ?        Sl   05:39   0:00 /usr/bin/socketcand --daemon
root      1270  0.0  0.2   2776  1268 ttyO0    S+   05:40   0:00 grep socketcand
root@beaglebone:~# nc 127.0.0.1 28601
< hi >< open vcan0 >
< ok >< rawmode >
< ok >
root@beaglebone:~#
```
=======

### Another use case with cannelloni, which does not work (different data formats)!

I discovered that socketcand on the embedded side does NOT recognize cannelloni on my VM, which has pass-through mechanisms between VM on PC and embedded HW (Beaglebone Black).

What I do, I set in my VM my private network, which sees all the components (energenie switch, bbb board and VM eth2 interface with built-in DHCP and TFTPD-hpa which work perfectly).

On the VM, I have the following set:
```
lsmod | grep can
vcan 16384 0
can_gw 20480 0
can_dev 24576 0
can_bcm 24576 0
can_raw 20480 2
can 49152 3 can_raw,can_bcm,can_gw
ifconfig vcan0
vcan0: flags=193<UP,RUNNING,NOARP> mtu 72
unspec 00-00-00-00-00-00-00-00-00-00-00-00-00-00-00-00 txqueuelen 1 (UNSPEC)
RX packets 54555 bytes 861125 (840.9 KiB)
RX errors 0 dropped 0 overruns 0 frame 0
TX packets 54555 bytes 861125 (840.9 KiB)
TX errors 0 dropped 0 overruns 0 carrier 0 collisions 0
```
True CAN-Fd vcan0 interface.

The VM private IP eth2 looks like:
```
eth2: flags=4163<UP,BROADCAST,RUNNING,MULTICAST> mtu 1500
inet 192.168.15.2 netmask 255.255.255.0 broadcast 192.168.15.255
inet6 fe80::210:60ff:fe31:5757 prefixlen 64 scopeid 0x20
ether 00:10:60:31:57:57 txqueuelen 1000 (Ethernet)
RX packets 51066 bytes 3185024 (3.0 MiB)
RX errors 0 dropped 0 overruns 0 frame 0
TX packets 103432 bytes 65516832 (62.4 MiB)
TX errors 0 dropped 0 overruns 0 carrier 0 collisions 0
```
NMAP command gives me the following:
```
nmap -T5 -sP 192.168.15.0/24

Starting Nmap 7.40 ( https://nmap.org ) at 2018-08-14 12:59 GMT
Nmap scan report for 192.168.15.21
Host is up (0.0037s latency).
MAC Address: 88:B6:27:01:66:EA (Gembird Europe BV)
Nmap scan report for 192.168.15.53
Host is up (0.010s latency).
MAC Address: 88:F0:77:5A:14:1C (Cisco Systems)
Nmap scan report for 192.168.15.101
Host is up (0.0023s latency).
MAC Address: F0:45:DA:83:09:B4 (Unknown)
Nmap scan report for 192.168.15.2
Host is up.
Nmap done: 256 IP addresses (4 hosts up) scanned in 1.70 seconds
```
So, my BBB IP is 192.168.15.101 !

The VM Cannelonni is configured as:
cannelloni -I vcan0 -R 192.168.15.101 -r 28601 -l 28601&

On the BBB side, I have very similar VCAN interfaces, and similar VCAN0 CAN-Fd capable interface.

But I am not able to connect with the command:
socketcand -v -i vcan0 -p 28601 -l eth0&

My best guess is that beacon is working, but I do NOT see any receiving CAN-Fd packets which Cannelloni sends to 192.168.15.101 , port 28601.

And here is the CLI transcript what I did on the BBB side:
```
root@beaglebone:~ ps -aux | grep socketcand
root 1607 0.0 0.2 10196 1324 pts/0 Sl 12:07 0:00 socketcand -v -i vcan0 -p 28601 -l eth0
root 1656 0.0 0.2 2776 1208 pts/0 S+ 13:05 0:00 grep socketcand
root@beaglebone:~ nc 192.168.15.101 28601
client connected
< hi > < open vcan0 >
connecting BCM socket...
< ok > < rawmode >
state changed to 2
< ok >
```
I do NOT see any received packets. neither any beacon discovery.

There are two imminent questions about this problem:
```
[1] Does socketcand recognize cannelloni CAN packets? If not, why, and how to rework socketcand, so it could be achievable?
[2] Does any another socketcancl (socketcan client) exist which is compatible with socketcand?
```
