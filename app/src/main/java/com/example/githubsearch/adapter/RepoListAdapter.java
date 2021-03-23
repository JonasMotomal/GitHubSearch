package com.example.githubsearch.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.githubsearch.databinding.ReposCellBinding;
import com.example.githubsearch.model.Item;
import com.example.githubsearch.utils.IOnItemClickListener;

import java.util.List;

public class RepoListAdapter extends RecyclerView.Adapter<RepoListAdapter.ViewHolder> {
    private LayoutInflater layoutInflater;
    private List<Item> _repoList;
    private IOnItemClickListener _onRoleClickListener;

    public RepoListAdapter(IOnItemClickListener onRoleClickListener) {
        _onRoleClickListener = onRoleClickListener;
    }

    public void setRepoList(List<Item> repoList) {
        _repoList = repoList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }

        ReposCellBinding binding = ReposCellBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = _repoList.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        if (_repoList != null) {
            return _repoList.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ReposCellBinding _binding;

        public ViewHolder(@NonNull ReposCellBinding binding) {
            super(binding.getRoot());
            _binding = binding;
            _binding.setViewholder(RepoListAdapter.this);
        }

        public void bind(Item repoItem) {
            _binding.setItem(repoItem);
            _binding.tvRepoName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    _onRoleClickListener.itemClick(repoItem.getHtmlUrl());
                }
            });
        }
    }
}
