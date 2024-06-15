package com.bbg.gamethird;

import org.graalvm.polyglot.Context;
import org.junit.jupiter.api.Test;



class GameThirdApplicationTests {

	@Test
	void contextLoads() {
		try(Context context = Context.create()) {
			context.eval("js", "console.log('Hello, World!')");
		}
	}

}
