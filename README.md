## socketCAN-Fd framework setup

Here summarized what I, after some lengthily investigation, found using
Beaglebone Black and Vbox Virtual machine, which passes through host
(my host is pass-through VT-d capable).

VBox VM and BBB HW are connected via private IP network, NOT
influencing host PC at all.

I created two vcan0 interfaces on both ends, and using user space app
Cannelloni: https://github.com/mguentner/cannelloni as binding
application, I use CAN tunneling via ETH phys.

I, actually use socketCAN-Fd framework from the Linux 4.17.2 LTS
kernel, playing with user space can-utils app.

Here is the condensed Beaglebone Black local.conf for such kind of setup:

```
  CONF_VERSION = "1"
  PATCHRESOLVE = "noop"
  SSTATE_DIR ?= "${TOPDIR}/sstate-cache"
  DL_DIR ?= "${TOPDIR}/downloads"
  TMPDIR ?= "${TOPDIR}/tmp"
  PACKAGE_CLASSES ?= "package_rpm package_deb"
  BBMASK = "meta-bbb/recipes-mender"
  EXTRA_IMAGE_FEATURES = "debug-tweaks"
  CORE_IMAGE_EXTRA_INSTALL_append = "openssh cmake canutils libsocketcan
  nfs-utils rt-tests strace procps packagegroup-core-buildessential "
  DISTRO_FEATURES_append = " ram"
  IMAGE_FSTYPES_append = " cpio.xz"
  MACHINE ??= "beaglebone"
  DISTRO ??= "poky"
  BBMULTICONFIG ?= ""
```
And, the setup (on both sides) is shown here:
https://stackoverflow.com/questions/36568167/can-fd-support-for-virtual-can-vcan-on-socketcan/51376306#51376306
(signed as _nobody_, aka me).

It works as a charm. Please, do note that commands are generic.

Beaglebone kernel 4.17.2 CAN framework .config setup for CAN-Fd setup to work is given here:
```
  CONFIG_CAN=m
  CONFIG_CAN_RAW=m
  CONFIG_CAN_BCM=m
  CONFIG_CAN_GW=m
  # CAN Device Drivers
  CONFIG_CAN_VCAN=m
  CONFIG_CAN_VXCAN=m
  CONFIG_CAN_SLCAN=m
  CONFIG_CAN_DEV=m
  CONFIG_CAN_CALC_BITTIMING=y
  # CONFIG_CAN_LEDS is not set
  # CONFIG_CAN_FLEXCAN is not set
  # CONFIG_CAN_GRCAN is not set
  CONFIG_CAN_TI_HECC=m
  CONFIG_CAN_C_CAN=m
  CONFIG_CAN_C_CAN_PLATFORM=m
  # CONFIG_CAN_CC770 is not set
  # CONFIG_CAN_IFI_CANFD is not set
  # CONFIG_CAN_M_CAN is not set
  # CONFIG_CAN_RCAR is not set
  # CONFIG_CAN_RCAR_CANFD is not set
  # CONFIG_CAN_SJA1000 is not set
  CONFIG_CAN_SOFTING=m
  # CAN SPI interfaces
  # CONFIG_CAN_HI311X is not set
  CONFIG_CAN_MCP251X=m
  # CAN USB interfaces
  CONFIG_CAN_EMS_USB=m
  CONFIG_CAN_ESD_USB2=m
  CONFIG_CAN_GS_USB=m
  CONFIG_CAN_KVASER_USB=m
  CONFIG_CAN_PEAK_USB=m
  CONFIG_CAN_8DEV_USB=m
  # CONFIG_CAN_MCBA_USB is not set
  CONFIG_CAN_DEBUG_DEVICES=y
  # CONFIG_SCSI_SCAN_ASYNC is not set
```
The complete defconfig (the config of the Beaglebone 4.17.2 kernel) file is given here:
https://github.com/ZoranStojsavljevic/cip-rt-misc/blob/master/configs/bbb/YOCTO/SocketCAN/kernel-config/defconfig

To set socketCAN-Fd framework beneath Linux kernel (example given: 4.17.2), please, do as root:
```
  lsmod | grep can
  modprobe can
  modprobe can_raw
  modprobe can-bcm
  modprobe can-dev
  modprobe can-gw
  modprobe vcan
  lsmod | grep can
```
To set the socketCAN-Fd framework, the following should be done (also as root):
```
  ip link add dev vcan0 type vcan
  ip link set vcan0 mtu 72
  ip link set dev vcan0 up
  ifconfig
```
The can-utils package is required to test the socketCAN-Fd framework.
Also, the following is required:
https://github.com/mguentner/cannelloni

And, everything works like a Swatch!

On the xmit side: cangen -f vcan0 -v vcan0
```
2C3##0.25.5A.FF.1E.DC.BD.CB.42.25.5A.FF.1E.DC.BD.CB.42.25.5A.FF.1E.DC.
BD.CB.42.25.5A.FF.1E.DC.BD.CB.42.25.5A.FF.1E.DC.BD.CB.42.25.5A.FF.1E.
DC.BD.CB.42.25.5A.FF.1E.DC.BD.CB.42.25.5A.FF.1E.DC.BD.CB.42
```
On the receiving side: candump vcan0
```
vcan0 2C3 [64] 25 5A FF 1E DC BD CB 42 25 5A FF 1E DC BD CB 42 25 5A
FF 1E DC BD CB 42 25 5A FF 1E DC BD CB 42 25 5A FF 1E DC BD CB 42 25
5A FF 1E DC BD CB 42 25 5A FF 1E DC BD CB 42 25 5A FF 1E DC BD CB 42
```
True socketCAN-Fd framework.
