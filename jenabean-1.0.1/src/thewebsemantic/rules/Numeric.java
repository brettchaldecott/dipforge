package thewebsemantic.rules;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.impl.LiteralLabel;
import com.hp.hpl.jena.reasoner.rulesys.BindingEnvironment;
import com.hp.hpl.jena.reasoner.rulesys.RuleContext;
import com.hp.hpl.jena.reasoner.rulesys.Util;
import com.hp.hpl.jena.reasoner.rulesys.builtins.BaseBuiltin;

/**
 * 
 */
public class Numeric extends BaseBuiltin {

	public String getName() {
		return "numeric";
	}

	public int getArgLength() {
		return 2;
	}

	public boolean bodyCall(Node[] args, int length, RuleContext context) {
		checkArgs(length, context);
		BindingEnvironment env = context.getEnv();
		Node n0 = getArg(0, args, context);
		if (Util.isNumeric(n0)) {
			return env.bind(args[1], n0);
		} else if (n0.isLiteral()) {
			Object v1 = n0.getLiteralValue();
			if (v1 instanceof String) {
				try {
					NumberFormat f = NumberFormat.getInstance(Locale.ROOT);
					Number num = f.parse(v1.toString());
					Node n = Node.createLiteral(new LiteralLabel(num));
					return env.bind(args[1], n);
				} catch (ParseException e) {
					// it's not a number, we do nothing
				}
			} 
		}
		return false;
	}

}
