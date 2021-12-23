package com.example.e_commerce;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lib.Model.WishlistModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchActivity extends AppCompatActivity {
    private TextView textView;
    private androidx.appcompat.widget.SearchView searchView;
    private RecyclerView recyclerView;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchView = findViewById(R.id.search_view);
        textView = findViewById(R.id.search_textView);
        recyclerView = findViewById(R.id.search_recyclerview);

        recyclerView.setVisibility(View.VISIBLE);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        final List<WishlistModel> list = new ArrayList<>();
        final List<String> ids = new ArrayList<>();

        adapter = new Adapter(list, false);
        adapter.setFromSearch(true);
        recyclerView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String s) {
                list.clear();
                ids.clear();
                OkHttpClient client = new OkHttpClient();
                System.out.println(searchView.getQuery());
                AsyncTask<String, Void, String> task1 = new AsyncTask<String, Void, String>() {
                    @Override
                    protected String doInBackground(String... params) {
                        String url = Config.IP_ADDRESS + "/api/product/search/" + searchView.getQuery();
                        Request request = new Request.Builder()
                                .url(url)
                                .header("Accept-Encoding", "identity")
                                .build();

                        try (Response response = client.newCall(request).execute()) {
                            if (!response.isSuccessful()) {
                                throw new IOException("Unexpected code: " + response);
                            } else {
                                String jsonData = response.body().string();
                                JSONArray json = new JSONArray(jsonData);
                                for (int i = 0; i < json.length(); i++) {
                                    JSONObject b = new JSONObject(json.get(i).toString());
                                    String name = b.getString("TENHANGHOA");
                                    String id = b.getString("MAHANGHOA");
                                    String gia = b.getString("GIA");
                                    String img = Config.IP_IMG_ADDRESS + b.getString("IMGAGESPATH");
                                    WishlistModel model = new WishlistModel(
                                            id
                                            , img
                                            , name
                                            , (long) 1000
                                            , "10"
                                            , (long) 1000
                                            , gia
                                            , "10000"
                                            , (boolean) true
                                            , true
                                    );
                                    ArrayList<String> a = new ArrayList<String>();
                                    a.add("a");
                                    model.setTags((ArrayList<String>) a);
                                }

                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return "Load_San_Pham_Hot";
                    }

                    protected void onPostExecute(String result) {

                    }

                    ;
                };
                task1.execute("Load_San_Pham_Hot");
                final String[] tags = s.toLowerCase().split(" ");
                WishlistModel model = new WishlistModel(
                        "1"
                        , "1"
                        , "123"
                        , (long) 1000
                        , "10"
                        , (long) 1000
                        , "10000"
                        , "10000"
                        , (boolean) true
                        , true
                );
                if (!ids.contains(model.getProductId())) {
                    list.add(model);
                    ids.add(model.getProductId());
                }
                ArrayList<String> a = new ArrayList<String>();
                a.add("a");
                model.setTags((ArrayList<String>) a);
                for (final String tag : tags) {
                    tag.trim();
                    FirebaseFirestore.getInstance().collection("PRODUCTS").whereArrayContains("tags", tag)
                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                    WishlistModel model = new WishlistModel(
                                            documentSnapshot.getId()
                                            , documentSnapshot.get("product_image_1").toString()
                                            , documentSnapshot.get("product_title").toString()
                                            , (long) documentSnapshot.get("free_coupens")
                                            , documentSnapshot.get("average_rating").toString()
                                            , (long) documentSnapshot.get("total_ratings")
                                            , documentSnapshot.get("product_price").toString()
                                            , documentSnapshot.get("cutted_price").toString()
                                            , (boolean) documentSnapshot.get("COD")
                                            , true
                                    );
                                    model.setTags((ArrayList<String>) documentSnapshot.get("tags"));

                                    if (!ids.contains(model.getProductId())) {
                                        list.add(model);
                                        ids.add(model.getProductId());
                                    }
                                }
                                if (tag.equals(tags[tags.length - 1])) {

                                    if (list.size() == 0) {
                                        textView.setVisibility(View.VISIBLE);
                                        recyclerView.setVisibility(View.GONE);
                                    } else {
                                        textView.setVisibility(View.GONE);
                                        recyclerView.setVisibility(View.VISIBLE);
                                        adapter.getFilter().filter(s);
                                    }
                                }
                            } else {
                                String error = task.getException().getMessage();
                                Toast.makeText(SearchActivity.this, error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    class Adapter extends WishlistAdapter implements Filterable {
        private List<WishlistModel> originalList;

        public Adapter(List<WishlistModel> wishlistModelList, Boolean wishlist) {
            super(wishlistModelList, wishlist);
            originalList = wishlistModelList;
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    FilterResults filterResults = new FilterResults();
                    List<WishlistModel> filteredList = new ArrayList<>();
                    final String[] tags = charSequence.toString().toLowerCase().split(" ");
                    for (WishlistModel model : originalList) {
                        ArrayList<String> presentTags = new ArrayList<>();
                        for (String tag : tags) {
                            if (model.getTags().contains(tag)) {
                                presentTags.add(tag);
                            }
                        }
                        model.setTags(presentTags);
                    }
                    for (int i = tags.length; i > 0; i--) {
                        for (WishlistModel model : originalList) {
                            if (model.getTags().size() == i) {
                                filteredList.add(model);
                            }
                        }
                    }
                    filterResults.values = filteredList;
                    filterResults.count = filteredList.size();
                    return filterResults;
                }
                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    if (filterResults.count > 0) {
                        setWishlistModelList((List<WishlistModel>) filterResults.values);
                    }
                    notifyDataSetChanged();
                }
            };
        }
    }

}