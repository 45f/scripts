package omniapi.finders;

public interface FinderCondition<T> {

	public boolean meetsCondition(T target);

}
