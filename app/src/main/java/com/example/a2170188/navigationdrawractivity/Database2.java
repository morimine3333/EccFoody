package com.example.a2170188.navigationdrawractivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.io.InputStream;
import java.net.URL;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

//データベースはコレクション>ドキュメント>フィールドが基本

public class Database2 {

    //Cloud Firestoreデータベースを表し、すべてのCloud Firestore操作のエントリポイント
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    //CollectionReferenceとQueryはほとんど同じもので、<"複数のドキュメントへの参照">
    private CollectionReference colRef;
    //DocumentReferenceは一つのドキュメントへの参照
    private DocumentReference docRef;
    ////コレクションに対してクエリを作成します。
    //例:CollectionReference citiesRef = db.collection("cities");
    //Query query = citiesRef.whereEqualTo("state", "CA");
    Query query;
    //使っているクラス名
    private static final String TAG = "Database2";

    //rank用のカウント変数
    private int rankcount;
    //rankの写真用のカウント変数
    private int rankphotocount = -1;

    //コレクションを指定
    Database2(String colPath) {
        colRef = db.collection(colPath);
    }
    //現在時刻のセット
    //"timestamp", FieldValue.serverTimestamp()

    //ドキュメント名取得
    //document.geiId();

    //全て取得
    //Log.d(TAG, document.getId() + " => " + document.getData());

    //自動生成された ID を持つドキュメントを作成する方のメソッド
    //単一のフィールドを作成または上書きするメソッド
    //SetOptions.merge()によって新しいデータを既存のドキュメントに結合する
    //リストは完全に上書きになるので注意
    public void set(String fieldId, Object fieldVal) {
        //フィールドのidと値を結合
        Map<String, Object> hashMap = new HashMap();
        hashMap.put(fieldId, fieldVal);

        //値をデータベースにセット
        colRef.document().set(hashMap, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "DocumentSnapshotが正常に書き込まれました！");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "ドキュメントの書き込みエラー", e);
            }
        });
    }

    //ドキュメント名を指定して作成する方のメソッド
    //単一のフィールドを作成または上書きするメソッド
    //SetOptions.merge()によって新しいデータを既存のドキュメントに結合する
    //リストは完全に上書きになるので注意
    public void set(String docPath, String fieldId, Object fieldVal) {
        //ドキュメント名を指定 存在しない場合は新規で生成される
        DocumentReference docRef = colRef.document(docPath);

        //フィールドのidと値を結合
        Map<String, Object> hashMap = new HashMap();
        hashMap.put(fieldId, fieldVal);

        //値をデータベースにセット
        docRef.set(hashMap, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "DocumentSnapshotが正常に書き込まれました！");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "ドキュメントの書き込みエラー", e);
            }
        });
    }

    //set()と同じでフィールドが存在しなかったら生成する
    //必要あるかは不明
//    public void update(String docPath, String fieldId, Object fieldVal) {
//        //ドキュメント名を指定 存在しない場合は新規で生成される
//        DocumentReference docRef = colRef.document(docPath);
//
//        docRef.update(fieldId, fieldVal).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                Log.d(TAG, "DocumentSnapshotが正常に更新されました！");
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.w(TAG, "ドキュメントの更新エラー", e);
//            }
//        });
//    }

    //フィールドの配列に要素を追加するメソッド
    //追加されるのはまだ存在しない要素のみ
    public void arrayUnion(String docPath, String fieldId, String fieldVal) {
        DocumentReference docRef = colRef.document(docPath);

        // 配列フィールドに新しい領域を原子的に追加します。
        docRef.update(fieldId, FieldValue.arrayUnion(fieldVal));
    }

    //フィールドの配列の指定した要素を削除するメソッド
    //指定した各要素のすべてのインスタンスを削除します
    //同じ値が複数ある場合、全部消える
    public void arrayRemove(String docPath, String fieldId, String fieldVal) {
        DocumentReference docRef = colRef.document(docPath);

        // 配列フィールドから領域を原子的に削除します。
        //原子性:一連の処理は、全体として実行されるか、実行されないか、どちらかであることが保証されることを指す。
        docRef.update(fieldId, FieldValue.arrayRemove(fieldVal));
    }

    //いいね数/フォロー数/フォロワー数用のメソッド
    //負の値入れるとその分マイナスされると思われる
    //毎回配列取り出して数えて値を入れたほうがいいのか？
    public void increment(String docPath, String fieldId, int val) {
        docRef = colRef.document(docPath);

        docRef.update(fieldId, FieldValue.increment(val));
    }

    //コレクション内のドキュメントを全て取得するメソッド
    //引数なしでも呼び出せてしまう
    public void getAll(final TextView... textView) {
        colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int i = 0;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        try {
                            textView[i++].setText(document.getId());
                        } catch (ArrayIndexOutOfBoundsException aioobe) {
                            break;
                        }
                        Log.d(TAG, document.getId() + " => " + document.getData());
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }

    //いいね数/フォロー数/フォロワー数が多い順にn件取り出すメソッド
    //ASC・・・昇順
    //DESC・・・降順
    //number型じゃないとString型の数字が優先的に表示されるため注意
    public void ranking(String fieldId, int limit, final TextView... textView) {
        colRef.orderBy(fieldId, Query.Direction.DESCENDING).limit(limit).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int i = 0;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        try {
                            textView[i++].setText(document.getId());
                        } catch (ArrayIndexOutOfBoundsException aioobe) {
                            break;
                        }
                        Log.d(TAG, document.getId() + " => " + document.getData());
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }

    //コレクション内から条件を満たす全ての<"ドキュメント">を取得するメソッド
    //whereLessThan:～より小さい
    //whereGreaterThan:～より大きい
    //whereArrayContains:配列値に基づいてフィルタ
    //fieldValの値が違うと反応しない
    public void getWhere(String fieldId, Object fieldVal, final TextView... textView) {
        colRef.whereEqualTo(fieldId, fieldVal)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int i = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                try {
                                    textView[i++].setText(document.getId());
                                } catch (ArrayIndexOutOfBoundsException aioobe) {
                                    break;
                                }
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    //単体のフィールドを取り出すメソッド
    //単一のドキュメントの内容を取得すしてから単一のフィールドを取り出している
    public void get(final String docPath, final String fieldPath, final TextView textView) {
        //DocumentReferenceは一つのドキュメントへの参照
        DocumentReference docRef = colRef.document(docPath);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        textView.setText(document.get(fieldPath).toString());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    //単体のフィールドを取り出すメソッド
    //Listからひとつを取り出すよう
    public void get(final String docPath, final String fieldPath, final int num, final TextView textView) {
        //DocumentReferenceは一つのドキュメントへの参照
        DocumentReference docRef = colRef.document(docPath);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        Object object = document.get(fieldPath);
                        List<Object> list = (ArrayList<Object>)object;
                        textView.setText(list.get(num).toString());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    //単体のフィールドを取り出すメソッド
    //Mapからひとつを取り出すよう
    //おそらく今回は使わない
    public void get(final String docPath, final String fieldPath, final String key, final TextView textView) {
        //DocumentReferenceは一つのドキュメントへの参照
        DocumentReference docRef = colRef.document(docPath);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        Object object = document.get(fieldPath);
                        Map<String, Object> map = (HashMap<String, Object>)object;
                        textView.setText(map.get(key).toString());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    //値を返り値で返すメソッド
    //ドキュメントとフィールドを指定
//    public Object get(String docPath, String fieldPath) {
//        //メインスレッド
//        //返り値
//        Object obj = null;
//
//        docRef = colRef.document(docPath);
//
//        //別タスクで非同期処理
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                //別スレッド
//                task_temp = docRef.get();
//            }
//        }).start();
//
//        //メインスレッド
//        //get()が終わるまで待つ
//        synchronized (this) {
//            try {
//                while(task_temp == null) {
//                    wait(1000);
//                }
//                //? なぜtask==nullだけではダメなのか
//                while(!task_temp.isSuccessful()) {
//                    wait(500);
//                }
//            } catch (InterruptedException ie) {
//                //あるスレッドが待ち状態、休止状態、または占有されているとき、アクティビティーの前かその間のいずれかにそのスレッドで割り込みが発生した場合にスローされます
//                Log.d(TAG, "待ち状態のスレッドに割り込み発生");
//            }
//
//            if (task_temp.isSuccessful()) {
//                DocumentSnapshot document = task_temp.getResult();
//                if (document.exists()) {
//                    Log.d(TAG, "ドキュメントデータ " + document.getData());
//                    obj = document.get(fieldPath);
//                } else {
//                    Log.d(TAG, "そのような文書はありません");
//                }
//            } else {
//                Log.d(TAG, "taskの処理に失敗 ", task_temp.getException());
//            }
//            //2回目以降のためnullにしておく
//            task_temp = null;
//        }
//        return obj;
//    }


//    public Object get(String docPath, String fieldPath) {
//        //メインスレッド
//        //返り値
//        Object obj = null;
//
//        docRef = colRef.document(docPath);
//
//        new Thread(new Runnable() {
//            Task<DocumentSnapshot> tttt;
//
//            @Override
//            public void run() {
//
//                new Thread()
//            }
//        })
//
//            //2回目以降のためnullにしておく
//            task_temp = null;
//        return obj;
//    }

    //ドキュメントを削除するメソッド
    public void delete(String docPath) {
        docRef = colRef.document(docPath);

        docRef.delete();
    }

    //フィールドを削除するメソッド
    public void delete(String docPath, String fieldId) {
        docRef = colRef.document(docPath);

        // Remove the 'capital' field from the document
        Map<String,Object> updates = new HashMap<>();
        updates.put("capital", FieldValue.delete());

        docRef.update(updates);
    }


//    final Context context, final List<TextView> rankstorenamelist, final List<TextView> ranktextlist,
//                          final List<ImageView> rankimglist, final List<TextView> rankfirstnamelist, final List<TextView> ranklastnamelist, final List<ImageView> iconlist
//    public void top10() {
//        //いいねが多い順で10件問い合わせる
//        colRef.orderBy("good", Query.Direction.DESCENDING).limit(10).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    for (final QueryDocumentSnapshot document : task.getResult()) {
////                        Log.d(TAG, document.getId() + " => " + document.getData());
//
//                        Log.d(TAG, "店名:" + document.get("storename").toString());
////                        rankstorenamelist.get(rankcount).setText(document.get("storename").toString());
//
//                        //テキスト
////                        Log.d(TAG, "テキスト:" + text);
////                        ranktextlist.get(rankcount).setText(document.get("text").toString());
//
//                        //写真
//                        List<String> photolist = (List<String>) document.get("photolist");
//                        for(int i = 0; i < 2; i++) {
//                            try {
//                                rankphotocount++;
////                                GlideApp.with(context).load(photolist.get(i)).into(rankimglist.get(rankphotocount));
//                                Log.d(TAG, "画像:" + photolist.get(i));
//                            } catch (IndexOutOfBoundsException aioob) { }
//                        }
//
//                        //??? マルチスレッド　並行処理　非同期処理
//                        //??? synchronizedで出来ないか
//                        //別スレッドに何番目の処理か分かるようにする変数
//                        final int num = rankcount;
//
//                        new Thread(new Runnable() {
//                            Task<DocumentSnapshot> top10_task;
//                            @Override
//                            public void run() {
//
////                                Log.d(TAG, String.valueOf(num));
//                                new Thread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        //順番バラバラになる
//                                        top10_task = db.collection("users").document(document.get("poster").toString()).get();
//                                    }
//                                }).start();
//
//                                try {
//                                    while (top10_task == null) {
//                                        Thread.sleep(100);
//                                    }
//                                    //? なぜtask==nullだけではダメなのか
//                                    while (!top10_task.isSuccessful()) {
//                                        Thread.sleep(100);
//                                    }
//                                } catch (InterruptedException ie) {
//                                    //あるスレッドが待ち状態、休止状態、または占有されているとき、アクティビティーの前かその間のいずれかにそのスレッドで割り込みが発生した場合にスローされます
//                                    Log.d(TAG, "待ち状態のスレッドに割り込み発生");
//                                }
//
//                                final DocumentSnapshot d = top10_task.getResult();
//                                if (d.exists()) {
//                                    // Handlerを使用してメイン(UI)スレッドに処理を依頼する
//                                    handler.post(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            Log.d(TAG, String.valueOf(num) + d.get("lastname").toString());
//                                            rankfirstnamelist.get(num).setText(d.get("firstname").toString());
//                                            ranklastnamelist.get(num).setText(d.get("lastname").toString());
//                                            GlideApp.with(context).load(d.get("icon")).circleCrop().into(iconlist.get(num));
//                                        }
//                                    });
//                                }
//                            }
//                        }).start();
//
//                        rankcount++;
//                    }
//                } else {
//                    Log.d(TAG, "ドキュメントの取得エラー： ", task.getException());
//                }
//            }
//        });
//    }

    //順番
    //1:写真          テキスト
    //2:アイコン      性 名
    //3:店名
    public void top10(final Context context, final List<TextView> storenamelist, final List<TextView> textlist,
                      final List<ImageView> imglist, final List<TextView> firstnamelist, final List<TextView> lastnamelist, final List<ImageView> iconlist) {

        //いいねが多い順で10件問い合わせる
        colRef.orderBy("good", Query.Direction.DESCENDING).limit(10).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            int count;
            int photocount = -1;

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (final QueryDocumentSnapshot document : task.getResult()) {
                        //何番目の処理か分かるようにする変数
                        final int num = count;

                        //テキスト
                        Log.d(TAG, "テキスト:" + document.get("text").toString());
                        textlist.get(count).setText(document.get("text").toString());

                        //写真
                        List<String> photolist = (List<String>) document.get("photolist");
                        for(int i = 0; i < 2; i++) {
                            try {
                                photocount++;
                                Log.d(TAG, "画像:" + photolist.get(i));
                                GlideApp.with(context).load(photolist.get(i)).into(imglist.get(photocount));
                            } catch (NullPointerException | IndexOutOfBoundsException e) {
                                //https://codeday.me/jp/qa/20181230/111052.html
                                //1枚だけ表示とかの時にimgviewに変更をかける
                                imglist.get(photocount).setVisibility(View.GONE);
                            }
                        }

                        //名前とアイコンの取得
                        db.collection("users").document(document.get("poster").toString()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot d= task.getResult();
                                    if (d.exists()) {
                                        Log.d(TAG, "名前:" + d.get("firstname").toString() + d.get("lastname").toString());
                                        Log.d(TAG, "アイコン:" + d.get("icon"));
                                        firstnamelist.get(num).setText(d.get("firstname").toString());
                                        lastnamelist.get(num).setText(d.get("lastname").toString());
                                        GlideApp.with(context).load(d.get("icon")).circleCrop().into(iconlist.get(num));
                                    } else {
                                        Log.d(TAG, "名前とアイコンエラー");
                                    }
                                } else {
                                    Log.d(TAG, "失敗する ", task.getException());
                                }
                            }
                        });

                        //店名
                        db.collection("stores").document(document.get("store").toString()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot d= task.getResult();
                                    if (d.exists()) {
                                        Log.d(TAG, "店名:" + d.get("name").toString());
                                        storenamelist.get(num).setText(d.get("name").toString());
                                    } else {
                                        Log.d(TAG, "店名エラー");
                                    }
                                } else {
                                    Log.d(TAG, "失敗する ", task.getException());
                                }
                            }
                        });

                        //LinearLayoutにClickListner実装
                        ViewGroup vg = (ViewGroup)storenamelist.get(count).getParent();
                        vg.setClickable(true);
                        vg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //コメント詳細画面に遷移
                                storenamelist.get(num).setText("aaaaaaaaaaaaaaaaaaaaaa");
                                String commentID = document.getId();

                            }
                        });

                        count++;
                    }
                } else {
                    Log.d(TAG, "ドキュメントの取得エラー： ", task.getException());
                }
            }
        });
    }

    //順番
    //1:写真          店名、テキスト、日時
    //2:アイコン      性 名
    //3: 店名
    public void new10(final Context context, final List<TextView> timelist, final List<TextView> storenamelist, final List<TextView> textlist,
                      final List<ImageView> imglist, final List<TextView> firstnamelist, final List<TextView> lastnamelist, final List<ImageView> iconlist) {

        //投稿日時が新しい順で10件問い合わせる
        colRef.orderBy("time", Query.Direction.DESCENDING).limit(10).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            int count;
            int photocount = -1;

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (final QueryDocumentSnapshot document : task.getResult()) {
                        //何番目の処理か分かるようにする変数
                        final int num = count;

                        //日時
                        Log.d(TAG, document.get("good") + " => " + new SimpleDateFormat("yyyy/MM/dd").format(document.getTimestamp("time").toDate()));
                        timelist.get(count).setText(new SimpleDateFormat("yyyy/MM/dd").format(document.getTimestamp("time").toDate()));

                        //テキスト
                        Log.d(TAG, "テキスト:" + document.get("text").toString());
                        textlist.get(count).setText(document.get("text").toString());

                        //写真
                        List<String> photolist = (List<String>) document.get("photolist");
                        for(int i = 0; i < 2; i++) {
                            try {
                                photocount++;
                                Log.d(TAG, "画像:" + photolist.get(i));
                                GlideApp.with(context).load(photolist.get(i)).into(imglist.get(photocount));
                            } catch (NullPointerException | IndexOutOfBoundsException e) {
                                imglist.get(photocount).setVisibility(View.GONE);
                            }
                        }
//
                        //アイコンと名前
                        db.collection("users").document(document.get("poster").toString()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot d= task.getResult();
                                    if (d.exists()) {
                                        Log.d(TAG, "名前:" + d.get("firstname").toString() + d.get("lastname").toString());
                                        Log.d(TAG, "アイコン:" + d.get("icon"));
                                        firstnamelist.get(num).setText(d.get("firstname").toString());
                                        lastnamelist.get(num).setText(d.get("lastname").toString());
                                        GlideApp.with(context).load(d.get("icon")).circleCrop().into(iconlist.get(num));
                                    } else {
                                        Log.d(TAG, "そのような文書はありません");
                                    }
                                } else {
                                    Log.d(TAG, "失敗する ", task.getException());
                                }
                            }
                        });

                        //店名
                        db.collection("stores").document(document.get("store").toString()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot d= task.getResult();
                                    if (d.exists()) {
                                        Log.d(TAG, "店名:" + d.get("name").toString());
                                        storenamelist.get(num).setText(d.get("name").toString());
                                    } else {
                                        Log.d(TAG, "そのような文書はありません");
                                    }
                                } else {
                                    Log.d(TAG, "失敗する ", task.getException());
                                }
                            }
                        });

                        //LinearLayoutにClickListner実装
                        ViewGroup vg = (ViewGroup)textlist.get(count).getParent();
                        vg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //コメント詳細画面に遷移
                            }
                        });

                        count++;
                    }
                } else {
                    Log.d(TAG, "ドキュメントの取得エラー： ", task.getException());
                }
            }
        });
    }


    //1.店名, 昼予算、　夜予算
    //2.写真
    public void search(final Context context, final String searchText, final String searchGenre, final List<TextView> storeNameList, final List<ImageView> imgList) {

        db.collection("stores").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            int count;

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(!searchText.equals("")) {
                    if (task.isSuccessful()) {
                        for (final QueryDocumentSnapshot document : task.getResult()) {
                            if (document.get("name").toString().contains(searchText) && document.get("genre").toString().contains(searchGenre)) {
                                //店名
                                storeNameList.get(count).setText(document.get("name").toString());
                                String storeId = document.getId();

                                final int photocount = count * 2;
                                //写真
                                db.collection("comments").whereEqualTo("store", document.getId()).get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {


                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                                        List<String> photolist = (List<String>) document.get("photolist");
                                                        for (int i = 0; i < 2; i++) {
                                                            try {
                                                                GlideApp.with(context).load(photolist.get(i)).into(imgList.get(photocount + i));
                                                            } catch (NullPointerException | IndexOutOfBoundsException e) {
                                                                imgList.get(photocount + i).setVisibility(View.GONE);
                                                            }

                                                        }
                                                    }
                                                } else {
                                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                                }
                                            }
                                        });
                                count++;

                            }
                        }
                    }
                }

                while(count < 10) {
                    ViewGroup vg = (ViewGroup)storeNameList.get(count).getParent();
                    vg.removeAllViews();
                    count++;
                }
            }
        });

    }
}