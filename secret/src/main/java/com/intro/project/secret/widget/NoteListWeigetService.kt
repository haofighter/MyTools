package com.intro.project.secret.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.intro.hao.mytools.Utils.DataUtils
import com.intro.project.secret.R
import com.intro.project.secret.model.NoteInfo
import com.intro.project.secret.moudle.note.EditNoteActivity
import com.vicpin.krealmextensions.queryAll


/**
 * Created by hao on 2018/3/8.
 */
class NoteListWeigetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        Log.i("小部件", "NoteListWeigetService开始调用");
        return NoteListRemoteView(this, intent)
    }


    class NoteListRemoteView : RemoteViewsFactory {
        var mContext: Context? = null
        var mWidgetId: Int = -1

        constructor(context: Context, intent: Intent?) {
            this.mContext = context

            mWidgetId = intent!!.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID)
        }


        override fun onCreate() {

        }

        override fun getLoadingView(): RemoteViews {
            return RemoteViews(mContext!!.getPackageName(), R.layout.text_layout);
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun onDataSetChanged() {
        }

        override fun hasStableIds(): Boolean {
            return true
        }

        override fun getViewAt(position: Int): RemoteViews {
            val rv = RemoteViews(mContext!!.getPackageName(), R.layout.text_layout)
            rv.setTextViewText(R.id.note_widget_item_title, NoteInfo().queryAll()!!.get(position).constent)
            rv.setTextViewText(R.id.note_widget_item_time, DataUtils().FormatDate(NoteInfo().queryAll()!!.get(position).time, "MM/dd"))

//            // 设置 第position位的“视图”对应的响应事件
            val fillInIntent = Intent()
            fillInIntent.putExtra(NoteWidgetProvider().noteListExtra, NoteInfo().queryAll()!!.get(position).id)
            rv.setOnClickFillInIntent(R.id.ll_note_item, fillInIntent)

//            val skipIntent = Intent(mContext, EditNoteActivity::class.java)
//            skipIntent.putExtra(NoteWidgetProvider().noteListExtra, NoteInfo().queryAll()!!.get(position).time)
//            val pi = PendingIntent.getActivity(mContext, 200, skipIntent, PendingIntent.FLAG_CANCEL_CURRENT)
//            rv.setOnClickPendingIntent(R.id.ll_note_item, pi)


            return rv
        }

        override fun getCount(): Int {
            Log.i("note小部件展示的条目数", "" + NoteInfo().queryAll()!!.size)
            return NoteInfo().queryAll()!!.size
        }

        override fun getViewTypeCount(): Int {
            return 1;
        }

        override fun onDestroy() {

        }

    }

}