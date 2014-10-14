package org.wso2.carbon.gmail.mediator.util;


public class MailSessionInfoStore {

    private static int bundleCount;
    private static long previouseBundleFirstMailTS;
    private static long previouseMessageTS;
    private static long nextBundleFirstMailTs;
    private static boolean bundleRecived;
   private static boolean readMailRecived;

    public static boolean isReadMailRecived() {
        return readMailRecived;
    }

    public static void setReadMailRecived(boolean readMailRecived) {
        MailSessionInfoStore.readMailRecived = readMailRecived;
    }

    public static int getBundleCount() {
        return bundleCount;
    }

    public static void setBundleCount(int bundleCount) {
        MailSessionInfoStore.bundleCount = bundleCount;
    }

    public static void incrementBundleCount() {
        bundleCount++;
    }

    public static long getPreviouseBundleFirstMailTS() {
        return previouseBundleFirstMailTS;
    }

    public static void setPreviouseBundleFirstMailTS(long previouseBundleFirstMailTS) {
        MailSessionInfoStore.previouseBundleFirstMailTS = previouseBundleFirstMailTS;
    }

    public static long getPreviouseMessageTS() {
        return previouseMessageTS;
    }

    public static void setPreviouseMessageTS(long previouseMessageTS) {
        MailSessionInfoStore.previouseMessageTS = previouseMessageTS;
    }

    public static long getNextBundleFirstMailTs() {
        return nextBundleFirstMailTs;
    }

    public static void setNextBundleFirstMailTs(long nextBundleFirstMailTs) {
        MailSessionInfoStore.nextBundleFirstMailTs = nextBundleFirstMailTs;
    }



    public static boolean isBundleRecived() {
        return bundleRecived;
    }

    public static void setBundleRecived(boolean bundleRecived) {
        MailSessionInfoStore.bundleRecived = bundleRecived;
    }
}
