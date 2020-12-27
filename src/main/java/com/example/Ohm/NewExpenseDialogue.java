package com.example.Ohm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class NewExpenseDialogue extends AppCompatDialogFragment {
    private TextView editTVName, editTVCategory, editTVDate, editTVAmount, editTVNote;
    private String dialogueName = "New Expense";
    private String buttonName = "Add";
    private Boolean toEdit = false;

    private Long _id;
    private Float Amount;
    private String Name, Category, Date, Note;
    private NewExpenseDialogueListener listener;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder =  new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialogue, null);

        //link the edittexts to variables
        editTVName = view.findViewById(R.id.edit_name);
        editTVCategory = view.findViewById(R.id.edit_category);
        editTVDate = view.findViewById(R.id.edit_date);
        editTVAmount = view.findViewById(R.id.edit_amount);
        editTVNote = view.findViewById(R.id.edit_note);

        // display the previous card information if editing a card
        if(toEdit) {
            editTVName.setText(Name);
            editTVCategory.setText(Category);
            editTVDate.setText(Date);
            editTVAmount.setText(Amount.toString());
            editTVNote.setText(Note);
        }

        builder.setView(view)
                .setTitle(dialogueName)
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton(buttonName, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Amount = null;
                        // create a new data entry
                        if (!checkFields() && !toEdit) {
                            Name = editTVName.getText().toString();
                            Category = editTVCategory.getText().toString();
                            Date = editTVDate.getText().toString();
                            Amount = Float.parseFloat(editTVAmount.getText().toString());
                            Note = editTVNote.getText().toString();
                            listener.applyTexts(Name, Category, Date, Amount, Note);
                        }
                        //edit an old data entry
                        else if (!checkFields() && toEdit) {
                            Name = editTVName.getText().toString();
                            Log.d("TAG", "Changing name to: " + Name);
                            Category = editTVCategory.getText().toString();
                            Date = editTVDate.getText().toString();
                            Amount = Float.parseFloat(editTVAmount.getText().toString());
                            Note = editTVNote.getText().toString();


                            //((MainActivity)getActivity()).refreshViewHolder();
                            /*
                            if (success) {
                                Log.d("TAG", "Succeeded editing expense.");
                            }
                            */
                            toEdit = false;
                        } else {
                            Log.d("TAG", "Failed to add.");
                        }

                    }
                })
                .setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
                        //Boolean success = Delete alarm?
                        /*
                        ((MainActivity)getActivity()).refreshViewHolder();
                        if (success) {
                            Log.d("TAG", "Deleted expense from data.");
                        }
                        */
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (NewExpenseDialogueListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "Must implement NewExpenseDialogueListener");
        }
    }

    public interface NewExpenseDialogueListener {
        void applyTexts(String Name, String Category, String Date, Float amount, String Note);
    }

    //check if any of the required fields were left empty, return false if so
    public Boolean checkFields() {
        return (editTVAmount.length() == 0 ||
                editTVName.length() == 0 ||
                editTVCategory.length() == 0 ||
                editTVDate.length() == 0 ||
                editTVAmount.getText().toString().equals("."));
    }


    // if a this dialogue is called to edit data, pass the old data to the dialogue for editing.
    public void setDetails(Long id, String name, String category, String date, Float amount, String note) {
        _id = id;
        Name = name;
        Category = category;
        Date = date;
        Amount = amount;
        Note = note;
        dialogueName = "Edit Expense";
        buttonName = "Save";
        toEdit = true;
    }
}
