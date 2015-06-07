package sg.nus.totoryfinnal;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class calendaFragment extends Fragment{

	    ImageView detash;
	    DetachPhotoFragment myCallBack=null;
        public calendaFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.smile_fragment, container, false);
            linkGUItoVars(rootView);
            setLisenEven();
            return rootView;
        }
        private void linkGUItoVars(View rootView){
        	detash=(ImageView)rootView.findViewById(R.id.imageView1);
        }
        private void setLisenEven(){
        	detash.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(myCallBack!=null)
					myCallBack.detachFragment();
					
				}
        	
        	});
        }
         public interface DetachPhotoFragment{
        	public void detachFragment();
        }
        public void setDetachPhotoFragment(DetachPhotoFragment listener){
        		myCallBack=listener;
        	}
        
        
        
}
