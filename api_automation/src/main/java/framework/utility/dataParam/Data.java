package framework.utility.dataParam;

import java.util.List;

/**
 * @author Prateek Sethi
 *
 *
 */

public class Data {
	public static <T extends BaseBean> DataBuilder<List<T>> listOf(Class<T> klass) {
		return new DataListBuilder<T>(klass);
	}
}