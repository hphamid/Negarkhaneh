package ir.abrstudio.negarkhaneh.list;


public interface GetDataCallback<T>{
	public void failed(int code, String message);
	public void success(T data);
}
