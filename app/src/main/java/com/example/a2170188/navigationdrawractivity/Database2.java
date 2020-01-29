//作成者:盛

package com.example.a2170188.navigationdrawractivity;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//Database2をMenu1Activityなど使うクラスで生成
//Database2の各関数内でDatabase2Valueを生成 繰り返し処理内等で生成する(複数の別の場所参照のインスタンスを生成)
//更に別関数を呼び出す(別画面へ移行)するときはDatabaseValue2オブジェクトから必要な値を取得して使いまわすのが理想

//データベースはコレクション>ドキュメント>フィールドが基本


//マイリスト、ユーザー詳細ページ、お店登録画面, 投稿画面, お店に対するコメント一覧部分、　お店登録画面と投稿画面を選ぶ画面 プロフィール編集画面
//評価点数がない
//フォロー/フォロワー機能がない (マイリストのフォロー/フォロワーリストも機能していない)
//いいねtop10でのいいね数の表示がない
//いいねlistとgood数が合っていない

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

    Database2() {}

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

    //トップ画面のいいね数top10取り出すよう
    //いいね数top10を押下時
    //コメント詳細画面へ遷移する
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

                        //次の画面に遷移するために必要なidを保持する変数
                        final Database2Value database2Value = new Database2Value();

                        //投稿者ID
                        database2Value.setPoster(document.get("poster").toString());
                        //コメントID
                        database2Value.setCommentID(document.getId());

                        //テキスト
                        Log.d(TAG, "テキスト:" + document.get("text").toString());
                        textlist.get(num).setText(document.get("text").toString());
                        database2Value.setText(document.get("text").toString());

                        //写真
                        List<String> photolist = (List<String>) document.get("photolist");
                        database2Value.setPhotolist((List<String>) document.get("photolist"));
                        for(int i = 0; i < 2; i++) {
                            try {
                                photocount++;
                                Log.d(TAG, "画像:" + photolist.get(i));
                                GlideApp.with(context).load(photolist.get(i)).override(100, 100).into(imglist.get(photocount));

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
                                        database2Value.setFirstname(d.get("firstname").toString());
                                        database2Value.setLastname(d.get("lastname").toString());
                                        database2Value.setIcon(d.get("icon").toString());

                                        //店名
                                        db.collection("stores").document(document.get("store").toString()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    DocumentSnapshot d= task.getResult();
                                                    if (d.exists()) {
                                                        Log.d(TAG, "店名:" + d.get("name").toString());
                                                        storenamelist.get(num).setText(d.get("name").toString());
                                                        database2Value.setStoreName(d.get("name").toString());

                                                        //店舗Id
                                                        database2Value.setStoreID(d.getId());
                                                        //LinearLayoutにClickListner実装
                                                        ViewGroup vg = (ViewGroup)storenamelist.get(num).getParent();
                                                        vg.setClickable(true);
                                                        vg.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                //コメント詳細画面に遷移
                                                                //必要なのはこめんとid てんぽid アイコン 名前 投稿日 店名 テキスト 写真
                                                                //yeaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
                                                                commentDetails(database2Value);
                                                            }
                                                        });
                                                    } else {
                                                        Log.d(TAG, "店名取得エラー");
                                                    }
                                                } else {
                                                    Log.d(TAG, "失敗する ", task.getException());
                                                }
                                            }
                                        });
                                    } else {
                                        Log.d(TAG, "名前とアイコンエラー");
                                    }
                                } else {
                                    Log.d(TAG, "失敗する ", task.getException());
                                }
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

    //トップ画面の新着10件取り出すよう
    //新着10件を押下時
    //コメント詳細画面に遷移する
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
                        final Database2Value database2Value = new Database2Value();
                        //投稿者
                        database2Value.setPoster(document.get("poster").toString());

                        //日時
                        Log.d(TAG, document.get("good") + " => " + new SimpleDateFormat("yyyy/MM/dd").format(document.getTimestamp("time").toDate()));
                        timelist.get(num).setText(new SimpleDateFormat("yyyy/MM/dd").format(document.getTimestamp("time").toDate()));
                        database2Value.setDate(new SimpleDateFormat("yyyy/MM/dd").format(document.getTimestamp("time").toDate()));

                        //テキスト
                        Log.d(TAG, "テキスト:" + document.get("text").toString());
                        textlist.get(num).setText(document.get("text").toString());
                        database2Value.setText(document.get("text").toString());

                        //写真
                        List<String> photolist = (List<String>) document.get("photolist");
                        database2Value.setPhotolist((List<String>) document.get("photolist"));
                        for(int i = 0; i < 2; i++) {
                            try {
                                photocount++;
                                Log.d(TAG, "画像:" + photolist.get(i));
                                GlideApp.with(context).load(photolist.get(i)).downsample(DownsampleStrategy.CENTER_INSIDE)
                                        .dontTransform().override(150, 150).into(imglist.get(photocount));
                            } catch (NullPointerException | IndexOutOfBoundsException e) {
                                imglist.get(photocount).setVisibility(View.GONE);
                            }
                        }
//
                        //アイコンと名前
                        //users表へ問い合わせ
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
                                        database2Value.setFirstname(d.get("firstname").toString());
                                        database2Value.setLastname(d.get("lastname").toString());
                                        database2Value.setIcon(d.get("icon").toString());

                                        //ユーザーid
                                        database2Value.setUserID(d.getId());

                                        //店名
                                        //stores表へ問い合わせ
                                        db.collection("stores").document(document.get("store").toString()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    DocumentSnapshot d= task.getResult();
                                                    if (d.exists()) {
                                                        Log.d(TAG, "店名:" + d.get("name").toString());
                                                        storenamelist.get(num).setText(d.get("name").toString());
                                                        database2Value.setStoreName(d.get("name").toString());

                                                        //お店ID
                                                        database2Value.setStoreID(d.getId());

                                                        //コメントId
                                                        database2Value.setCommentID(document.getId());
                                                        //LinearLayoutにClickListner実装
                                                        ViewGroup vg = (ViewGroup)textlist.get(num).getParent();
                                                        vg.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                //コメント詳細画面に遷移
                                                                //必要なのはコメントID

                                                                //呼び出し
                                                                commentDetails(database2Value);
                                                            }
                                                        });
                                                    } else {
                                                        Log.d(TAG, "店名エラー");
                                                    }
                                                } else {
                                                    Log.d(TAG, "失敗する ", task.getException());
                                                }
                                            }
                                        });
                                    } else {
                                        Log.d(TAG, "名前とアイコンエラー");
                                    }
                                } else {
                                    Log.d(TAG, "失敗する ", task.getException());
                                }
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


    //検索結果表示画面用
    //現状10件までしか表示できないのが問題
    //既にあるlayoutに埋め込む形ではなく
    //viewを生成する方法にしたほうが良い
    //1.店名, (昼予算/夜予算 未実装)
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
                                //何番目の処理か判断するための変数
                                final int num = count;

                                //店名
                                storeNameList.get(num).setText(document.get("name").toString());

                                final int photocount = num * 2;
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


                                //店舗Id
                                final String storeID = document.getId();

                                ViewGroup vg = (ViewGroup)storeNameList.get(num).getParent();
                                vg.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //店舗詳細画面に遷移
                                        //必要なのは店舗ID
                                        storeDetails(storeID);
                                    }
                                });

                                count++;

                            }
                        }
                    }
                }

                //検索結果が10件未満だった場合のlayout削除
                while(count < 10) {
                    ViewGroup vg = (ViewGroup)storeNameList.get(count).getParent();
                    vg.removeAllViews();
                    count++;
                }
            }
        });

    }

    //コメント詳細画面用
    public void commentDetails(final Database2Value database2Value) {
        final View view =MyApplication.getInflater().inflate(R.layout.activity_postscreen, MyApplication.getFrameLayout(), false);
        //いいねボタンの押下状態の読み込み
        database2Value.setGoodFlg(false);

        //名前タップの処理に必要
        LinearLayout nameView = view.findViewById(R.id.nameView);

        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        final String userID = user.getUid();

        //コメントを見ている人が投稿者自身かどうかの判定
        if(!database2Value.getPoster().equals(userID)) {
            DocumentReference docRef = db.collection("comments").document(database2Value.getCommentID());
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            List<String> goodList = (ArrayList<String>) document.get("goodlist");

                            if(goodList.contains(userID)) {
                                GlideApp.with(MyApplication.getAppContext()).load(MyApplication.getR().getDrawable(R.drawable.good1)).into((ImageView)view.findViewById(R.id.goodImage));
                                database2Value.setGoodFlg(true);
                            }
                        } else {
                            Log.d(TAG, "No such document");
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });

            //マイリストボタンの押下状態の読み込み(お店に対してのマイリスト)
            database2Value.setHeartFlg(false);
            DocumentReference docRef1 = db.collection("users").document(userID);

            docRef1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            List<String> favoriteList = (ArrayList<String>) document.get("favoritelist");

                            if(favoriteList.contains(database2Value.getStoreID())) {
                                GlideApp.with(MyApplication.getAppContext()).load(MyApplication.getR().getDrawable(R.drawable.heart1)).into((ImageView)view.findViewById(R.id.heartImage));
                                database2Value.setHeartFlg(true);
                            }
                        } else {
                            Log.d(TAG, "No such document");
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });

            //いいねボタン押下時
            ((ImageView)view.findViewById(R.id.goodImage)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(database2Value.getGoodFlg()) {
                        GlideApp.with(MyApplication.getAppContext()).load(MyApplication.getR().getDrawable(R.drawable.good0)).into((ImageView)view.findViewById(R.id.goodImage));
                        database2Value.setGoodFlg(false);

                        //goodlistからuserID削除
                        DocumentReference docRef = db.collection("comments").document(database2Value.getCommentID());
                        docRef.update("goodlist", FieldValue.arrayRemove(userID));

                        //いいね数-1
                        docRef.update("good", FieldValue.increment(-1));
                    } else {
                        GlideApp.with(MyApplication.getAppContext()).load(MyApplication.getR().getDrawable(R.drawable.good1)).into((ImageView)view.findViewById(R.id.goodImage));
                        database2Value.setGoodFlg(true);

                        //goodlistにuserID追加
                        DocumentReference docRef = db.collection("comments").document(database2Value.getCommentID());
                        docRef.update("goodlist", FieldValue.arrayUnion(userID));

                        //いいね数-1
                        docRef.update("good", FieldValue.increment(1));
                    }
                }
            });

            //マイリストボタン押下時
            ((ImageView)view.findViewById(R.id.heartImage)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(database2Value.getHeartFlg()) {
                        GlideApp.with(MyApplication.getAppContext()).load(MyApplication.getR().getDrawable(R.drawable.heart0)).into((ImageView)view.findViewById(R.id.heartImage));
                        database2Value.setHeartFlg(false);

                        //お気に入りリストからstoreID削除
                        DocumentReference docRef = db.collection("users").document(userID);
                        docRef.update("favoritelist", FieldValue.arrayRemove(database2Value.getStoreID()));
                    } else {
                        GlideApp.with(MyApplication.getAppContext()).load(MyApplication.getR().getDrawable(R.drawable.heart1)).into((ImageView)view.findViewById(R.id.heartImage));
                        database2Value.setHeartFlg(true);

                        //お気に入りリストにstoreID追加
                        DocumentReference docRef = db.collection("users").document(userID);
                        docRef.update("favoritelist", FieldValue.arrayUnion(database2Value.getStoreID()));
                    }
                }
            });

            //名前押下時,ユーザー詳細画面に遷移
            nameView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userDetails(database2Value);
                }
            });
        } else {
            ((ImageView)view.findViewById(R.id.goodImage)).setVisibility(View.GONE);
            ((ImageView)view.findViewById(R.id.heartImage)).setVisibility(View.GONE);
        }

        //アイコン
        ImageView resulticon = view.findViewById(R.id.resulticon);
        GlideApp.with(MyApplication.getAppContext()).load(database2Value.getIcon()).into(resulticon);

        //性
        TextView resultfirstname = view.findViewById(R.id.resultfirstname);
        resultfirstname.setText(database2Value.getFirstname());

        //名
        TextView resultlastname = view.findViewById(R.id.resultlastname);
        resultlastname.setText(database2Value.getLastname());



        //投稿日
        TextView resultTime = view.findViewById(R.id.resultTime);
        resultTime.setText(database2Value.getDate());

        //店名
        Button resultstorename = view.findViewById(R.id.resultstorename);
        resultstorename.setText(database2Value.getStoreName());

        resultstorename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeDetails(database2Value.getStoreID());
            }
        });

        //投稿の説明
        TextView resultText = view.findViewById(R.id.resultText);
        resultText.setText(database2Value.getText());

        //写真
        ImageView resultImage01 = view.findViewById(R.id.resultImage01);
        try {
            GlideApp.with(MyApplication.getAppContext()).load(database2Value.getPhotolist().get(0)).into(resultImage01);
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            resultImage01.setVisibility(View.GONE);
        }
        //2枚目
        ImageView resultImage02 = view.findViewById(R.id.resultImage02);
        try {
            GlideApp.with(MyApplication.getAppContext()).load(database2Value.getPhotolist().get(1)).into(resultImage02);
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            resultImage02.setVisibility(View.GONE);
        }

        MyApplication.getFrameLayout().removeAllViews();
        MyApplication.getFrameLayout().addView(view);
    }

    //店舗詳細画面
    //写真(全部), 店名, 料金, ジャンル, 住所, 営業時間, 定休日, 電話番号, その店に対するコメント
    //住所と定休日が見切れる
    public void storeDetails(String storeID) {
        //viewを取得
        final View view =MyApplication.getInflater().inflate(R.layout.storedetails_layout, MyApplication.getFrameLayout(), false);

        //写真(全部)
        //その店に対するコメント
        //コメント表への問い合わせ
        db.collection("comments").whereEqualTo("store", storeID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            //画面上部のscrollviewに表示する写真
            List<String> photoAll = new ArrayList<>();

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int count = 0;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        //写真
                        List<String> photolist = (List<String>) document.get("photolist");
                        for(int i = 0; i < 2; i++) {
                            try {
                                Log.d(TAG, "画像:" + photolist.get(i));
                                photoAll.add(photolist.get(i));
//                                GlideApp.with(context).load(photolist.get(i)).into(imglist.get(photocount));
                            } catch (NullPointerException | IndexOutOfBoundsException e) {
//                                imglist.get(photocount).setVisibility(View.GONE);
                            }
                        }
                    }

                    ImageView imageView = (ImageView)view.findViewById(R.id.imageView);
                    ImageView imageView2 = (ImageView)view.findViewById(R.id.imageView2);
                    ImageView imageView3 = (ImageView)view.findViewById(R.id.imageView3);
                    ImageView imageView4 = (ImageView)view.findViewById(R.id.imageView4);
                    ImageView imageView5 = (ImageView)view.findViewById(R.id.imageView5);
                    ImageView imageView6 = (ImageView)view.findViewById(R.id.imageView6);
                    ImageView imageView7 = (ImageView)view.findViewById(R.id.imageView7);
                    ImageView imageView8 = (ImageView)view.findViewById(R.id.imageView8);

                    List<ImageView> imageViews = Arrays.asList(imageView, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, imageView8);

                    for(int i = 0; i < 8; i++) {
                        try {
                            Log.d(TAG, "画像aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa:" + photoAll.get(i));
                            GlideApp.with(MyApplication.getAppContext()).load(photoAll.get(i)).into(imageViews.get(i));
                        } catch (NullPointerException | IndexOutOfBoundsException e) {
                            Log.d(TAG, "走ってない");
                            imageViews.get(i).setVisibility(View.GONE);
                        }
                    }
                } else {
                }
            }
        });

        Log.d(TAG, "daaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa:" + storeID);

        //店名, 料金, ジャンル, 住所, 営業時間, 定休日, 電話番号
        DocumentReference storeRef =  db.collection("stores").document(storeID);

        storeRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //店名
                        Log.d(TAG, "とれてるううううううううううううう:" + document.get("name").toString());
                        ((TextView)view.findViewById(R.id.textView1)).setText(document.get("name").toString());

                        //料金(昼, 夜)
                        ((TextView)view.findViewById(R.id.textView2)).setText("～" + document.get("dinner budget").toString() + "円");
                        ((TextView)view.findViewById(R.id.textView3)).setText("～" + document.get("lunch budget").toString() + "円");
                        ((TextView)view.findViewById(R.id.textView10)).setText("～" + document.get("dinner budget").toString() + "円");
                        ((TextView)view.findViewById(R.id.textView11)).setText("～" + document.get("lunch budget").toString() + "円");

                        //ジャンル
                        ((TextView)view.findViewById(R.id.textView4)).setText(document.get("genre").toString());

                        //住所
                        GeoPoint geoPoint = document.getGeoPoint("Street address");
                        try {
                            String streetaddress = getAddress(MyApplication.getAppContext(), geoPoint.getLatitude(), geoPoint.getLongitude());
//                            String streetaddress = getAddress(MyApplication.getAppContext(),35.689643 ,139.691706);
                            ((TextView)view.findViewById(R.id.textView5)).setText(streetaddress);
                        } catch (IOException ioe) {
                            Log.d(TAG, "住所取得失敗");
                        }

                        //営業時間
                        ((TextView)view.findViewById(R.id.textView9)).setText(document.get("business hours").toString());

                        //定休日
                        Map<String, Boolean> map = (Map<String, Boolean>)document.get("Regular holiday");
                        String string = "";
                        //https://www.sejuku.net/blog/16055
                        for(Map.Entry<String, Boolean> entry : map.entrySet()) {
                            if(entry.getValue()) {
                                string += entry.getKey() + "、";
                            }
                        }
                        string = string.substring(0, string.length() - 1);
                        ((TextView)view.findViewById(R.id.textView15)).setText(string);

                        //電話番号
                        ((TextView)view.findViewById(R.id.textView17)).setText(document.get("tel").toString());
                    }

                    //viewを配置
                    MyApplication.getFrameLayout().removeAllViews();
                    MyApplication.getFrameLayout().addView(view);
                } else {
                    Log.d(TAG, "とれてなあああああああああああああああああああああああい");
                }
            }
        });

    }

    //https://blog.suzukishouten.co.jp/archives/761
    //https://oldfish.hatenadiary.org/entry/20081214/1229257066
    //https://seesaawiki.jp/w/moonlight_aska/d/%B0%CC%C3%D6%BE%F0%CA%F3%A4%AB%A4%E9%BD%BB%BD%EA%A4%F2%BC%E8%C6%C0%A4%B9%A4%EB

    /**
     * 緯度・経度から住所を取得する。
     * @param context
     * @param latitude
     * @param longitude
     * @return 住所
     */
    public static String getAddress(
            Context context, double latitude, double longitude) throws IOException {
        String result = new String();

        //geocoedrの実体化
        Log.d(TAG, "Start point2adress");
        Geocoder geocoder = new Geocoder(context, Locale.JAPAN);
        List<Address> list_address = geocoder.getFromLocation(latitude, longitude, 1);	//引数末尾は返す検索結果数

        Log.d("location", list_address.get(0).toString());

        //ジオコーディングに成功したらStringへ
        if (!list_address.isEmpty()){

            Address address = list_address.get(0);
            StringBuffer strbuf = new StringBuffer();

            //adressをStringへ
            String buf;
            for (int i = 0; (buf = address.getAddressLine(i)) != null; i++){
                Log.d(TAG, "loop no."+i);
//                strbuf.append("address.getAddressLine("+i+"):"+buf+"\n");
                strbuf.append(buf+"\n");
            }

            result = strbuf.toString();
            result = result.substring(result.indexOf("、") + 1, result.length() - 1);
//            result = strbuf.toString().replace("日本、", "").trim();
            result = result.replaceAll("〒[0-9]{3}-[0-9]{4} ", "").trim();

        } else {
            //失敗（Listが空だったら）
            Log.d(TAG, "Fail Geocoding");
        }

        Log.d(TAG, result);

        return result;
    }



    //ユーザー詳細画面
    public void userDetails(Database2Value database2Value) {
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        final String userID = user.getUid();
        //ユーザーをタップした人が投稿者自身かどうかの判定
        if(!database2Value.getPoster().equals(userID)) {

            //viewを取得
            final View view = MyApplication.getInflater().inflate(R.layout.userdetails_layout, MyApplication.getFrameLayout(), false);

            //アイコン
            GlideApp.with(MyApplication.getAppContext()).load(database2Value.getIcon()).into((ImageView) view.findViewById(R.id.icon));

            //名前
            ((TextView) view.findViewById(R.id.newfirstname01)).setText(database2Value.getFirstname());
            ((TextView) view.findViewById(R.id.newlastname01)).setText(database2Value.getLastname());

            //viewを配置
            MyApplication.getFrameLayout().removeAllViews();
            MyApplication.getFrameLayout().addView(view);
        }
    }














    //行きたいリスト
    public void favorite(String currentUserDocumentPath, final RecyclerView rv) {

        //ユーザー表へ問い合わせ
        db.collection("users").document(currentUserDocumentPath).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            int count;

            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                final DocumentSnapshot documentSnapshot = task.getResult();
                final List<String> favorites = (ArrayList<String>)documentSnapshot.get("favoritelist");

                final List<String> storeNames = new ArrayList<>();
                String[] h = new String[10];

                final List<String> images = Arrays.asList(h);
                final List<String> dinnerBudgets = new ArrayList<>();
                final List<String> lunchBudgets = new ArrayList<>();
                final List<String> genres = new ArrayList<>();

                final Database2Value database2Value = new Database2Value();
                database2Value.setNaibu(0);

                //お気に入りリストの数だけお店表へ問い合わせ
                //???????????????????????????????????????   お気に入り0のときエラー吐きそう
                //??????????????????????????? 店の写真をコメントから持ってくるのめんどくさい
                try {
                    for (final String storeDocumentPath : favorites) {

                        db.collection("stores").document(storeDocumentPath).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                DocumentSnapshot documentSnapshot1 = task.getResult();

                                storeNames.add(documentSnapshot1.get("name").toString());
                                lunchBudgets.add("～" + documentSnapshot1.get("lunch budget").toString() + "円");
                                dinnerBudgets.add("～" + documentSnapshot1.get("dinner budget").toString() + "円");
                                genres.add(documentSnapshot1.get("genre").toString());


                                final int count2 = count;

//                            コメント表への問い合わせ
                                //db.collection("comments").whereEqualTo("store", storeDocumentPath).limit(1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                db.collection("comments").whereEqualTo("store", storeDocumentPath).limit(1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                        for (DocumentSnapshot documentSnapshot2 : task.getResult()) {
                                            //???????????????????????????????          写真ないときどうするねん
                                            List<String> photolist = (List<String>) documentSnapshot2.get("photolist");
                                            try {
                                                images.set(count2, photolist.get(0));
                                                Log.d("yeaaaaaaaaaaaaaaaaa", images.get(count2));
//                                           Log.d("aaaaaaaaaaaaaa", String.valueOf(count2));
                                                database2Value.addNaibu();

                                                if (favorites.size() == database2Value.getNaibu()) {
                                                    MyAdapter1 adapter = new MyAdapter1(storeNames, images, lunchBudgets, dinnerBudgets, genres);
                                                    rv.setAdapter(adapter);
                                                    count = 0;
                                                }
                                            } catch (NullPointerException | IndexOutOfBoundsException e) {
                                                Log.d("yeaaaaaaaaaaaaaaaaa", "とれてない");
                                                break;
                                            }
                                            break;
                                        }
                                    }
                                });

                                count++;

                                if (favorites.size() == count) {
                                    MyAdapter1 adapter = new MyAdapter1(storeNames, images, lunchBudgets, dinnerBudgets, genres);
                                    rv.setAdapter(adapter);
                                    count = 0;
                                }
                            }
                        });
                    }
                } catch(RuntimeException re) {
                    re.getMessage();
                }
            }
        });
    }

    //行ったリスト
    void went() {

    }















    //新規店舗登録
    void newStore(String genre, String storeName, String tel, GeoPoint geoPoint, String lunchBudget,
                  String dinnerBudget, String businessHours, Map<String, Boolean> weekMap,
                  final Context context, final Activity activity) {


        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("genre", genre);
        hashMap.put("name", storeName);
        hashMap.put("tel", tel);
        hashMap.put("Street address", geoPoint);
        hashMap.put("lunch budget", Integer.parseInt(lunchBudget));
        hashMap.put("dinner budget", Integer.parseInt(dinnerBudget));
        hashMap.put("business hours", businessHours);
        hashMap.put("Regular holiday", weekMap);

        //値をデータベースにセット
        colRef.document().set(hashMap, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "DocumentSnapshotが正常に書き込まれました！");
                Toast.makeText(context, "登録しました!", Toast.LENGTH_SHORT).show();
                activity.finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "ドキュメントの書き込みエラー", e);
                Toast.makeText(context, "登録に失敗しました", Toast.LENGTH_SHORT).show();
            }
        });
    }


    //コメント投稿画面での店舗検索
    public void search2(final Context context, final String searchText, final String searchGenre,
                        final List<TextView> storeNameList, final List<ImageView> imgList,
                        final Activity activity) {

        db.collection("stores").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            int count;

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(!searchText.equals("")) {
                    if (task.isSuccessful()) {
                        for (final QueryDocumentSnapshot document : task.getResult()) {
                            if (document.get("name").toString().contains(searchText) && document.get("genre").toString().contains(searchGenre)) {
                                //何番目の処理か判断するための変数
                                final int num = count;

                                //店名
                                storeNameList.get(num).setText(document.get("name").toString());

                                final int photocount = num * 2;
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


                                //店舗Id
                                final String storeID = document.getId();

                                ViewGroup vg = (ViewGroup)storeNameList.get(num).getParent();
                                vg.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        GetImageActivity.storeNameText.setText(document.get("name").toString());
                                        GetImageActivity.storeID = storeID;
                                        activity.finish();
                                    }
                                });

                                count++;

                            }
                        }
                    }
                }

                //検索結果が10件未満だった場合のlayout削除
                while(count < 10) {
                    ViewGroup vg = (ViewGroup)storeNameList.get(count).getParent();
                    vg.removeAllViews();
                    count++;
                }
            }
        });
    }

    //コメント投稿処理

    //先にDBでドキュメント作ってIDとって
    //StorageにそのIDで階層を作り画像を保存
    //そのあと,urlをDBの方に追加する
    void commentPost(String storeID, String storeName,  String genre,  final File img, String text, final Activity activity) {
        //投稿者のID取得
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        final String userID = user.getUid();

        Map<String, Object> hashMap = new HashMap();
        hashMap.put("store", storeID);
        hashMap.put("storename", storeName);
        hashMap.put("genre", genre);
        hashMap.put("text", text);
        hashMap.put("time", FieldValue.serverTimestamp());
        hashMap.put("poster", userID);
        hashMap.put("good", 0);
        hashMap.put("goodlist", new ArrayList<String>());
        hashMap.put("photolist", new ArrayList<String>());

        Log.d("aaaaaaa", "走っている");
        if(img != null) {
            final DocumentReference documentReference = db.collection("comments").document();

            documentReference.set(hashMap, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(TAG, "DocumentSnapshotが正常に書き込まれました！" + documentReference.getId());

                    Storage2 storage2 = new Storage2();
                    storage2.set2(documentReference.getId(), img, documentReference, activity);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w(TAG, "ドキュメントの書き込みエラー", e);
                }
            });

        } else {
            Log.d("aaaaaaa", "img取れず");
        }
    }
}