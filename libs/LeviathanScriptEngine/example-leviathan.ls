define "http://type1.com" as type1
define "http://type2.com" as type2


@info(["bob"])
@info2(["bob"])
flow test {
    def var8
	String var1 = "bob"
	def var4 = 1
	{
        def var2 = 2
		def var3 = 1 + var2 +( 5 + 6)
		def var4 = "test"
        var4 = "test2"
		if (a == b) {
			a = "test"
			if (fred == test) {
				bob = "five"
			}
 		} else if (b == b) {
			a = "test2"
        } else {
			a = "test3"
        }
		
		while(50==50) {
			test=10;
		}
		for (def count = 0; count < 10; count++) {
			test=11;
		}
		helloWorld(test)
		test.size()
		test = test.bob + 5
 	}
	
	
	def helloWorld(bob) {
		test = 5
	}
}