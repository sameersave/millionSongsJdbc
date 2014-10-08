/**
 * Make a logger instance available to other classes
 */
package resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author neerajsharma
 *
 */
public class Log {
	public static Logger logger = LoggerFactory.getLogger("Default Logger");
	
	public Log() {
	}

}
