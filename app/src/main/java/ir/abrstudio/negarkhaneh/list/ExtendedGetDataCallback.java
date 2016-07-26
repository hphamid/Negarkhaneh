package ir.abrstudio.negarkhaneh.list;

public class ExtendedGetDataCallback<T> implements GetDataCallback<T>{

	private GetDataCallback<T> callback;

	public ExtendedGetDataCallback(GetDataCallback<T> callback) {
		this.callback = callback;
	}

	@Override
	public void failed(int code, String message) {
		if(this.callback != null){
			this.callback.failed(code, message);
		}
	}

	@Override
	public void success(T data) {		
		if(this.callback != null){
			this.callback.success(data);
		}
	}
}
