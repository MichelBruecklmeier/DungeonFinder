package Utils;

public class Logger {
    //Depending on when logging the type will point to this with 1 being nothing and 2 being a error
    public static final String[] LOG_TYPE = {
            "\033[48;2;0;0;0mMSG",
            "\033[48;2;100;0;0mERROR",
            "\033[48;2;100;100;0mWARNING",
            "\033[48;2;0;0;100mINFO",
            "\033[48;2;50;50;50mDEBUG",
            "\033[48;2;100;0;50mTRACE",
            "\033[48;2;0;100;0mSUCCESS"
    };
    public static String[] processes = new String[0];
    private static int ID = 0;
    private final int id  = ID++;
    private static int currentLoggingId=-1;
    private String lastMessage = "";
    private int repeatMessage = 0;
    private final String RESET = "\033[0m";
    private String buffer = "";
    String process;
    public Logger(String process) {
        this.process = process;
        addProcess(process);
    }
    private void addProcess(String process) {
        String[] oldPrcs = processes;
        processes = new String[oldPrcs.length+1];
        for(int i=0; i<oldPrcs.length; i++)
            processes[i] = oldPrcs[i];
        processes[oldPrcs.length] = process;
    }
    private void printProcess(){
        if(currentLoggingId!=id) {
            if(currentLoggingId!=-1)
                System.out.println(RESET+"__END OF "+processes[currentLoggingId]+"__");
            System.out.println();
            System.out.println(process);
            currentLoggingId=id;
        }
    }
    public void addBuffer(String buf){
        buffer=buffer+buf;
    }
    public void logBuffer(int type){
        log(buffer,type);
    }
    //Implement the actual logging method
    public void log(String message,int type) {
        if(type > LOG_TYPE.length-1){
            System.out.println(LOG_TYPE[2]+": INVALID LOG TYPE"+RESET);
            type = 0;
        }
        printProcess();
        if(lastMessage.equals(message)){
            repeatMessage++;
        }else if(repeatMessage!=0){
            System.out.println("\t^Repeated "+repeatMessage+" times.^");
            repeatMessage=0;
        }
        if(!lastMessage.equals(message)){
            System.out.println("\t"+LOG_TYPE[type]+": "+message+RESET);
        }

        lastMessage=message;
    }
}
