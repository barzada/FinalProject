package com.project.stylebuyer.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Validator {

    public abstract static class Watcher {

        private String error;
        public Watcher(String error) {
            this.error = error;
        }
        public abstract boolean privateCheck(String input);
    }

    public static class Watcher_Email extends Watcher {

        public Watcher_Email(String error) {
            super(error);
        }

        @Override
        public boolean privateCheck(String input) {
            String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
            Pattern pat = Pattern.compile(ePattern);
            if (!pat.matcher(input).matches()) {
                return false;
            }
            return true;
        }


    }

    public static class Watcher_NotEmpty extends Watcher{

        public Watcher_NotEmpty(String error) {
            super(error);
        }

        @Override
        public boolean privateCheck(String input) {
            return input.trim().length() != 0;
        }
    }

    public static class Watcher_FullName extends Watcher{

        public Watcher_FullName(String error) {
            super(error);
        }

        @Override
        public boolean privateCheck(String input) {
            String[] fullName = input.split(" ");
            if(fullName.length < 2)
                return false;

            if(fullName[0].trim().length() == 0 || fullName[1].trim().length() == 0)
                return false;

            return true;
        }
    }

    public static class Watcher_Phone extends Watcher {

        public Watcher_Phone(String error) {
            super(error);
        }

        @Override
        public boolean privateCheck(String input) {
            if(input.length() != 10)
                return false;

            if(input.length() >= 1 && input.charAt(0) != '0')
                return false;

            return true;
        }
    }

    public static class Watcher_password extends Watcher {

        public Watcher_password(String error) {
            super(error);
        }

        @Override
        public boolean privateCheck(String input) {
            if(input.length() < 6)
                return false;
            if(input.toLowerCase().equals(input))
                return false;
            return true;
        }
    }

    public static class Watcher_Equal extends Watcher {
        private EditText value;

        public Watcher_Equal(String error, EditText value) {
            super(error);
            this.value = value;
        }

        @Override
        public boolean privateCheck(String input) {
            return this.value.getText().toString().equals(input);
        }
    }

    public static class Builder {
        private boolean status;
        private TextInputLayout textInputLayout;
        private ArrayList<Watcher> watchers = new ArrayList<>();
        private boolean isAlreadyBuild = false;
        private  CallBack_Builder callBack_builder = new CallBack_Builder() {
            @Override
            public void updateStatus(boolean status) {
                setStatus(status);
            }
        };


        public interface CallBack_Builder {
            void updateStatus(boolean status);
        }


        public static Builder make(@NonNull TextInputLayout textInputLayout) {
            return new Builder(textInputLayout);
        }

        private Builder(@NonNull TextInputLayout textInputLayout) {
            this.status = false;
            this.textInputLayout = textInputLayout;
        }

        public Builder addWatcher(Watcher watcher) {
            if (!isAlreadyBuild) {
                this.watchers.add(watcher);
            }
            return this;
        }

        public void setStatus(boolean status){
            this.status = status;
        }

        public boolean getStatus(){
            return this.status;
        }

        public Builder build() {
            if (!isAlreadyBuild) {
                isAlreadyBuild = true;
                addValidator(textInputLayout, watchers, callBack_builder);
            }
            return this;
        }
    }

    private static void addValidator(TextInputLayout textInputLayout, ArrayList<Watcher> watchers, Builder.CallBack_Builder callBack_builder) {
        textInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input = charSequence.toString();

                boolean result = true;
                callBack_builder.updateStatus(true);
                for (Watcher watcher : watchers) {
                    result = watcher.privateCheck(input);
                    if (!result) {
                        callBack_builder.updateStatus(false);
                        textInputLayout.setError(watcher.error);
                        break;
                    }
                }

                if (result) {
                    textInputLayout.setError("");

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

}
