/*
 This is a test ls script
 */

@java("out")
flow test {

    def out
    
    {
        out.println("bob 1");
        out.println("bob 2");
        out.println("bob " + 3);
        int bob = 1 + 3
        String fred = "testing"
        out.println("fred " + bob );
        if (bob == 4) {
            out.println("test 1");
        } else {
            out.println("test 2");
        }
        if (fred.equals("testing")) {
            out.println("Called equals");
        } else {
            out.println("Failed to call equals");
        }
        while (bob < 8) {
            bob = bob + 1
            out.println("loop \"\\\n" + bob);
        }
        helloWorld()
        for (int test = 0; test < 2; test++) {
            out.println("for " + test);
        }
    }
    
    def helloWorld() {
        out.println("hello")
    }
}