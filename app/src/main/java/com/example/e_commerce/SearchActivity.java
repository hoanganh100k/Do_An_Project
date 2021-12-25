package com.example.e_commerce;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lib.Model.WishlistModel;

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


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String s) {
                list.clear();
                ids.clear();
                OkHttpClient client = new OkHttpClient();
                System.out.println(searchView.getQuery());
                AsyncTask<String, Void, String> task1 = new AsyncTask<String, Void, String>() {
                    ArrayList<WishlistModel> temp = new ArrayList<WishlistModel>();

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
                                    String tenloai = b.getString("TENLOAI");
                                    String gia = b.getString("GIA");
                                    String img = Config.IP_IMG_ADDRESS + b.getString("IMGAGESPATH");
                                    WishlistModel model = new WishlistModel(
                                            id
                                            , img
                                            , name
                                            , (long) 1000
                                            , tenloai
                                            , (long) 1000
                                            , gia
                                            , "10000"
                                            , (boolean) true
                                            , true
                                    );

                                    if (!ids.contains(model.getProductId())) {
                                        list.add(model);
                                        ids.add(model.getProductId());
                                    }
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
                        adapter = new Adapter(list, false);
                        adapter.setFromSearch(true);
                        recyclerView.setAdapter(adapter);

                        if (list.size() == 0) {
                            textView.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                            adapter.notifyDataSetChanged();
                        } else {
                            textView.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            adapter.notifyDataSetChanged();
                        }
                    }

                    ;
                };
                task1.execute("Load_San_Pham_Hot");

//                final String[] tags = s.toLowerCase().split(" ");
//                WishlistModel model = new WishlistModel(
//                        "1"
//                        , "1"
//                        , "123"
//                        , (long) 1000
//                        , "10"
//                        , (long) 1000
//                        , "10000"
//                        , "10000"
//                        , (boolean) true
//                        , true
//                );
//                if (!ids.contains(model.getProductId())) {
//                    list.add(model);
//                    ids.add(model.getProductId());
//                }
//                ArrayList<String> a = new ArrayList<String>();
//                a.add("a");
//                model.setTags((ArrayList<String>) a);
//                for (final String tag : tags) {
//                    tag.trim();
//                    FirebaseFirestore.getInstance().collection("PRODUCTS").whereArrayContains("tags", tag)
//                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                            if (task.isSuccessful()) {
//                                for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
//                                    WishlistModel model = new WishlistModel(
//                                            documentSnapshot.getId()
//                                            , documentSnapshot.get("product_image_1").toString()
//                                            , documentSnapshot.get("product_title").toString()
//                                            , (long) documentSnapshot.get("free_coupens")
//                                            , documentSnapshot.get("average_rating").toString()
//                                            , (long) documentSnapshot.get("total_ratings")
//                                            , documentSnapshot.get("product_price").toString()
//                                            , documentSnapshot.get("cutted_price").toString()
//                                            , (boolean) documentSnapshot.get("COD")
//                                            , true
//                                    );
//                                    model.setTags((ArrayList<String>) documentSnapshot.get("tags"));
//
//                                    if (!ids.contains(model.getProductId())) {
//                                        list.add(model);
//                                        ids.add(model.getProductId());
//                                    }
//                                }
//                                if (tag.equals(tags[tags.length - 1])) {
//
//                                    if (list.size() == 0) {
//                                        textView.setVisibility(View.VISIBLE);
//                                        recyclerView.setVisibility(View.GONE);
//                                    } else {
//                                        textView.setVisibility(View.GONE);
//                                        recyclerView.setVisibility(View.VISIBLE);
//                                        adapter.getFilter().filter(s);
//                                    }
//                                }
//                            } else {
//                                String error = task.getException().getMessage();
//                                Toast.makeText(SearchActivity.this, error, Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

    }

    class Adapter extends WishlistAdapter {
        private List<WishlistModel> originalList;

        public Adapter(List<WishlistModel> wishlistModelList, Boolean wishlist) {
            super(wishlistModelList, wishlist);
            originalList = wishlistModelList;
        }

    }

}