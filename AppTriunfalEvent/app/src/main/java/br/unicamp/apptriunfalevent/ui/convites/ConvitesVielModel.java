package br.unicamp.apptriunfalevent.ui.convites;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ConvitesVielModel extends ViewModel {
    private MutableLiveData<String> mText;

    public ConvitesVielModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
