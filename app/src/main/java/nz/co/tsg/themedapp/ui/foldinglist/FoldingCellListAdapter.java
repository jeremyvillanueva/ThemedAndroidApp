package nz.co.tsg.themedapp.ui.foldinglist;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ramotion.foldingcell.FoldingCell;

import nz.co.tsg.themedapp.ThemedApplication;
import nz.co.tsg.themedapp.databinding.CellBinding;
import nz.co.tsg.themedapp.ui.Branding;

import java.util.HashSet;
import java.util.List;

/**
 * Simple example of ListAdapter for using with Folding Cell
 * Adapter holds indexes of unfolded elements for correct work with default reusable views behavior
 */
public class FoldingCellListAdapter extends ArrayAdapter<FoldingItem> {

    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private View.OnClickListener defaultRequestBtnClickListener;

    Branding mBranding;

    public FoldingCellListAdapter(Context context, List<FoldingItem> objects) {
        super(context, 0, objects);

        ThemedApplication app = (ThemedApplication) ((Activity) context).getApplication();
        mBranding = app.getmBranding();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // get item for selected view
        FoldingItem item = getItem(position);
        // if cell is exists - reuse it, if not - create the new one from resource
        FoldingCell cell = (FoldingCell) convertView;
        ViewHolder viewHolder;
        CellBinding binding=null;
        if (cell == null) {

            viewHolder = new ViewHolder();
            LayoutInflater vi = LayoutInflater.from(getContext());
            binding = CellBinding.inflate(vi, parent, false);

            binding.cellTitle.leftTitleArea.setBackgroundColor(mBranding.getColorMainColor());
            cell = binding.foldingCellView;

            viewHolder.price = (TextView) binding.cellTitle.titlePrice;
            viewHolder.time = (TextView) binding.cellTitle.titleTimeLabel;
            viewHolder.date = (TextView) binding.cellTitle.titleDateLabel;
            viewHolder.fromAddress = (TextView) binding.cellTitle.titleFromAddress;
            viewHolder.toAddress = (TextView) binding.cellTitle.titleToAddress;
            viewHolder.requestsCount = (TextView) binding.cellTitle.titleRequestsCount;
            viewHolder.pledgePrice = (TextView) binding.cellTitle.titlePledge;
            viewHolder.contentRequestBtn = (TextView) binding.cellContent.contentRequestBtn;
            cell.setTag(viewHolder);

        } else {
            // for existing cell set valid valid state(without animation)
            if (unfoldedIndexes.contains(position)) {
                cell.unfold(true);
            } else {
                cell.fold(true);
            }
            viewHolder = (ViewHolder) cell.getTag();
        }

        // bind data from selected element to view through view holder
        viewHolder.price.setText(item.getPrice());
        viewHolder.time.setText(item.getTime());
        viewHolder.date.setText(item.getDate());
        viewHolder.fromAddress.setText(item.getFromAddress());
        viewHolder.toAddress.setText(item.getToAddress());
        viewHolder.requestsCount.setText(String.valueOf(item.getRequestsCount()));
        viewHolder.pledgePrice.setText(item.getPledgePrice());

        if (binding != null) {
            if (mBranding.isDarkTheme()) {
                binding.cellTitle.leftTitleArea.setBackgroundColor(mBranding.getColorLightAccent());
                binding.cellTitle.titlePrice.setTextColor(mBranding.getTextColorOverLightAccent());
                binding.cellTitle.titleTimeLabel.setTextColor(mBranding.getTextColorOverLightAccent());
                binding.cellTitle.titleDateLabel.setTextColor(mBranding.getTextColorOverLightAccent());
                binding.cellContent.contentRequestBtn.setBackgroundColor(mBranding.getColorMainColor());
                binding.cellContent.contentRequestBtn.setTextColor(mBranding.getTextColorOverMainColor());
                binding.cellContent.cellContentTopContent.setBackgroundColor(mBranding.getColorDarkAccent());
                binding.cellContent.cellContentPhone.setTextColor(mBranding.getTextColorOverDarkAccent());
                binding.cellContent.cellContentPrice.setTextColor(mBranding.getTextColorOverDarkAccent());
                binding.cellContent.cellContentMenuIcon.setColorFilter(mBranding.getTextColorOverDarkAccent());
            } else {
                binding.cellTitle.leftTitleArea.setBackgroundColor(mBranding.getColorDarkAccent());
                binding.cellTitle.titlePrice.setTextColor(mBranding.getTextColorOverDarkAccent());
                binding.cellTitle.titleTimeLabel.setTextColor(mBranding.getTextColorOverDarkAccent());
                binding.cellTitle.titleDateLabel.setTextColor(mBranding.getTextColorOverDarkAccent());
                binding.cellContent.contentRequestBtn.setBackgroundColor(mBranding.getColorMainColor());
                binding.cellContent.contentRequestBtn.setTextColor(mBranding.getTextColorOverMainColor());
                binding.cellContent.cellContentTopContent.setBackgroundColor(mBranding.getColorLightAccent());
                binding.cellContent.cellContentPhone.setTextColor(mBranding.getTextColorOverLightAccent());
                binding.cellContent.cellContentPrice.setTextColor(mBranding.getTextColorOverLightAccent());
                binding.cellContent.cellContentMenuIcon.setColorFilter(mBranding.getTextColorOverLightAccent());
            }
        }

        // set custom btn handler for list item from that item
        if (item.getRequestBtnClickListener() != null) {
            viewHolder.contentRequestBtn.setOnClickListener(item.getRequestBtnClickListener());
        } else {
            // (optionally) add "default" handler if no handler found in item
            viewHolder.contentRequestBtn.setOnClickListener(defaultRequestBtnClickListener);
        }


        return cell;
    }

    // simple methods for register cell state changes
    public void registerToggle(int position) {
        if (unfoldedIndexes.contains(position))
            registerFold(position);
        else
            registerUnfold(position);
    }

    public void registerFold(int position) {
        unfoldedIndexes.remove(position);
    }

    public void registerUnfold(int position) {
        unfoldedIndexes.add(position);
    }

    public View.OnClickListener getDefaultRequestBtnClickListener() {
        return defaultRequestBtnClickListener;
    }

    public void setDefaultRequestBtnClickListener(View.OnClickListener defaultRequestBtnClickListener) {
        this.defaultRequestBtnClickListener = defaultRequestBtnClickListener;
    }

    // View lookup cache
    private static class ViewHolder {
        TextView price;
        TextView contentRequestBtn;
        TextView pledgePrice;
        TextView fromAddress;
        TextView toAddress;
        TextView requestsCount;
        TextView date;
        TextView time;
    }
}
