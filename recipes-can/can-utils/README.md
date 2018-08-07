## can-utils package

#### candump -help
```
Usage: candump [options] <CAN interface>+
  (use CTRL-C to terminate candump)

Options: -t <type>   (timestamp: (a)bsolute/(d)elta/(z)ero/(A)bsolute w date)
         -H          (read hardware timestamps instead of system timestamps)
         -c          (increment color mode level)
         -i          (binary output - may exceed 80 chars/line)
         -a          (enable additional ASCII output)
         -S          (swap byte order in printed CAN data[] - marked with '`' )
         -s <level>  (silent mode - 0: off (default) 1: animation 2: silent)
         -b <can>    (bridge mode - send received frames to <can>)
         -B <can>    (bridge mode - like '-b' with disabled loopback)
         -u <usecs>  (delay bridge forwarding by <usecs> microseconds)
         -l          (log CAN-frames into file. Sets '-s 2' by default)
         -L          (use log file format on stdout)
         -n <count>  (terminate after receiption of <count> CAN frames)
         -r <size>   (set socket receive buffer to <size>)
         -D          (Don't exit if a "detected" can device goes down.
         -d          (monitor dropped CAN frames)
         -e          (dump CAN error frames in human-readable format)
         -x          (print extra message infos, rx/tx brs esi)
         -T <msecs>  (terminate after <msecs> without any reception)

Up to 16 CAN interfaces with optional filter sets can be specified
on the commandline in the form: <ifname>[,filter]*

Comma separated filters can be specified for each given CAN interface:
 <can_id>:<can_mask> (matches when <received_can_id> & mask == can_id & mask)
 <can_id>~<can_mask> (matches when <received_can_id> & mask != can_id & mask)
 #<error_mask>       (set error frame filter, see include/linux/can/error.h)
 [j|J]               (join the given CAN filters - logical AND semantic)

CAN IDs, masks and data content are given and expected in hexadecimal values.
When can_id and can_mask are both 8 digits, they are assumed to be 29 bit EFF.
Without any given filter all data frames are received ('0:0' default filter).

Use interface name 'any' to receive from all CAN interfaces.

Examples:
candump -c -c -ta can0,123:7FF,400:700,#000000FF can2,400~7F0 can3 can8
candump -l any,0~0,#FFFFFFFF    (log only error frames but no(!) data frames)
candump -l any,0:0,#FFFFFFFF    (log error frames and also all data frames)
candump vcan2,92345678:DFFFFFFF (match only for extended CAN ID 12345678)
candump vcan2,123:7FF (matches CAN ID 123 - including EFF and RTR frames)
candump vcan2,123:C00007FF (matches CAN ID 123 - only SFF and non-RTR frames)
```
#### cangen -help  
```
cangen: generate CAN frames

Usage: cangen [options] <CAN interface>
Options: -g <ms>       (gap in milli seconds - default: 200 ms)
         -e            (generate extended frame mode (EFF) CAN frames)
         -f            (generate CAN FD CAN frames)
         -b            (generate CAN FD CAN frames with bitrate switch (BRS))
         -R            (send RTR frame)
         -m            (mix -e -f -b -R frames)
         -I <mode>     (CAN ID generation mode - see below)
         -L <mode>     (CAN data length code (dlc) generation mode - see below)
         -D <mode>     (CAN data (payload) generation mode - see below)
         -p <timeout>  (poll on -ENOBUFS to write frames with <timeout> ms)
         -n <count>    (terminate after <count> CAN frames - default infinite)
         -i            (ignore -ENOBUFS return values on write() syscalls)
         -x            (disable local loopback of generated CAN frames)
         -v            (increment verbose level for printing sent CAN frames)

Generation modes:
'r'        => random values (default)
'i'        => increment values
<hexvalue> => fix value using <hexvalue>

When incrementing the CAN data the data length code minimum is set to 1.
CAN IDs and data content are given and expected in hexadecimal values.

Examples:
cangen vcan0 -g 4 -I 42A -L 1 -D i -v -v   (fixed CAN ID and length, inc. data)
cangen vcan0 -e -L i -v -v -v              (generate EFF frames, incr. length)
cangen vcan0 -D 11223344DEADBEEF -L 8      (fixed CAN data payload and length)
cangen vcan0 -g 0 -i -x                    (full load test ignoring -ENOBUFS)
cangen vcan0 -g 0 -p 10 -x                 (full load test with polling, 10ms timeout)
cangen vcan0                               (my favourite default :)
```
