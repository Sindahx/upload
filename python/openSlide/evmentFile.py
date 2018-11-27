#coding=utf-8
import os
import datetime
import pyinotify
import logging
import thread
import time


class MyEventHandler(pyinotify.ProcessEvent):
    logging.basicConfig(level=logging.INFO, filename='/home/sinda/shell/monitor.log')
    # 自定义写入那个文件，可以自己修改
    logging.info("Starting monitor...")

    # def process_IN_ACCESS(self, event):
    #     print "ACCESS event:", event.pathname
    #     logging.info("ACCESS event : %s  %s" % (os.path.join(event.path, event.name), datetime.datetime.now()))

    def process_IN_ATTRIB(self, event):
        print "ATTRIB event:", event.pathnane
        logging.info("IN_ATTRIB event : %s  %s" % (os.path.join(event.path, event.name), datetime.datetime.now()))


    def process_IN_CLOSE_NOWRITE(self, event):
        print "CLOSE_NOWRITE event:", event.pathname
        logging.info("CLOSE_NOWRITE event : %s  %s" % (os.path.join(event.path, event.name), datetime.datetime.now()))


    def process_IN_CLOSE_WRITE(self, event):
        # print "CLOSE_WRITE event:", event.pathname
        # print("vips dzsave {} {}{}".format(event.pathname, targetPath, event.name))
        # os.system("vips dzsave {} {}{}".format(event.pathname, targetPath, event.name))
        dict = {}
        dict['name'] = event.name
        dict['pathname'] = event.pathname
        imageList.append(dict)
        # try:
        #     thread.start_new_thread(print_time, (event.pathname, targetPath, event.name,))
        # except:
        #     print "Error: unable to start thread"
        # print_time(event.pathname, targetPath, event.name)
        # logging.info("CLOSE_WRITE event : %s  %s" % (os.path.join(event.path, event.name), datetime.datetime.now()))

    def process_IN_CREATE(self, event):
        print "CREATE event:", event.pathname
        logging.info("CREATE event : %s  %s" % (os.path.join(event.path, event.name), datetime.datetime.now()))


    def process_IN_DELETE(self, event):
        print "CREATE event:", event.pathname
        logging.info("CREATE event : %s  %s" % (os.path.join(event.path, event.name), datetime.datetime.now()))


    def process_IN_DELETE(self, event):
        print "DELETE event:", event.pathname
        logging.info("DELETE event : %s  %s" % (os.path.join(event.path, event.name), datetime.datetime.now()))


    def process_IN_MODIFY(self, event):
        print "MODIFY event:", event.pathname
        logging.info("MODIFY event : %s  %s" % (os.path.join(event.path, event.name), datetime.datetime.now()))


    def process_IN_OPEN(self, event):
        print "OPEN event:", event.pathname
        logging.info("OPEN event : %s  %s" % (os.path.join(event.path, event.name), datetime.datetime.now()))


imageList = []


# 为线程定义一个函数
def print_time(pathname):
    print("线程启动了。。。。")
    targetPath = "/home/java/apache-tomcat-7.0.85/webapps/openSlide/image/"
    while 1 == 1:
        time.sleep(4)
        if (len(imageList) > 0):
            dict = imageList.pop()
            pathname_ = dict['pathname']
            name_ = dict['name']
            print(time.asctime(time.localtime(time.time())))
            print("vips dzsave {} {}{}".format(pathname_, targetPath, name_))
            time_start = time.time()
            os.system("vips dzsave {} {}{}".format(pathname_, targetPath, name_))
            time_end = time.time()
            print("文件转换完成。。。 用时：{} -----------{}".format(time_end - time_start, name_))
    print("线程循环结束了。。。")


def start_th():
    try:
        thread.start_new_thread(print_time, ("",))
    except:
        print "Error: unable to start thread"


def main():
    # 启用一个线程
    start_th()
    # watch manager
    wm = pyinotify.WatchManager()
    wm.add_watch('/home/openslide/file', pyinotify.ALL_EVENTS, rec=True)
    # /tmp是可以自己修改的监控的目录
    # event handler
    eh = MyEventHandler()

    # notifier
    notifier = pyinotify.Notifier(wm, eh)
    notifier.loop()


if __name__ == '__main__':
    main()

