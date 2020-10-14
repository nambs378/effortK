package com.example.effortkotlin.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.example.effortkotlin.R
import com.example.effortkotlin.factory.setBitmapImage
import com.github.barteksc.pdfviewer.PDFView

class RenderPdfActivity : AppCompatActivity() {
    lateinit var pdfView: PDFView
    lateinit var view: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_render_pdf)

        view = findViewById(R.id.view_pdfrender)
        setBitmapImage.setBackground(applicationContext, view, R.drawable.background_all)

        pdfView = findViewById(R.id.pdfView)
        val bundle = intent.extras
        val title = bundle?.getString("Grammar")


        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setTitle(title)
        val actionBar = supportActionBar
        actionBar?.setHomeButtonEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)


        Log.e("tiitel"," titlene $title")

        if (title == "Hiện tại đơn") {
            loadpdffile("hientaidon.pdf")
        }
        else if (title == "Hiện tại tiếp diễn") {
            loadpdffile("hientaitiepdien.pdf")
        }
        else if (title == "Hiện tại hoàn thành") {
            loadpdffile("hientaihoanthanh.pdf")
        }
        else if (title == "Hiện tại hoàn thành tiếp diễn") {
            loadpdffile("hientaihoanthanhtiepdien.pdf")
        }
        else if (title == "Quá khứ đơn") {
            loadpdffile("quakhudon.pdf")
        }
        else if (title == "Quá khứ tiếp diễn") {
            loadpdffile("quakhutiepdien.pdf")
        }
        else if (title == "Quá khứ hoàn thành") {
            loadpdffile("quakhuhoanthanh.pdf")
        }
        else if (title == "Quá khứ hoàn thành tiếp diễn") {
            loadpdffile("quakhuhoanthanhtiepdien.pdf")
        }
        else if (title == "Tương lai đơn") {
            loadpdffile("tuonglaidon.pdf")
        }
        else if (title == "Tương lai tiếp diễn") {
            loadpdffile("tuonglaitiepdien.pdf")
        }
        else if (title == "Tương lai hoàn thành") {
            loadpdffile("tuonglaihoanthanh.pdf")
        }
        else if (title == "Tương lai hoàn thành tiếp diễn") {
            loadpdffile("tuonglaihoanthanhtiepdien.pdf")
        }
        else if (title == "Cấu trúc ngữ pháp") {
            loadpdffile("caubidong.pdf")
        }
        else if (title == "Các trường hợp đặc biệt") {
            loadpdffile("cactruonghopdacbiet.pdf")
        }
        else if (title == "Câu ước loại 1 (Tương lai)") {
            loadpdffile("cauuocloai1.pdf")
        }
        else if (title == "Câu ước loại 2 (Hiện tại)") {
            loadpdffile("cauuocloai2.pdf")
        }
        else if (title == "Câu ước loại 3 (Quá khứ)") {
            loadpdffile("cauuocloai3.pdf")
        }
        else if (title == "Câu điều kiện loại 1") {
            loadpdffile("caudieukienloai1.pdf")
        }
        else if (title == "Câu điều kiện loại 2") {
            loadpdffile("caudieukienloai2.pdf")
        }
        else if (title == "Câu điều kiện loại 3") {
            loadpdffile("caudieukienloai1.pdf")
        }
        else if (title == "Câu điều kiện dạng đảo ngữ") {
            loadpdffile("caudieukiendaongu.pdf")
        }
        else if (title == "Câu điều kiện dạng đặc biệt") {
            loadpdffile("caudieukienhonhop.pdf")
        }
        else if (title == "So sanh bằng, so sánh bội số") {
            loadpdffile("")
        }
        else if (title == "So sánh hơn") {
            loadpdffile("sosanhhon.pdf")
        }
        else if (title == "So sánh hơn nhất") {
            loadpdffile("sosanhnhat.pdf")
        }
        else if (title == "So sánh kép") {
            loadpdffile("")
        }
        else if (title == "So sánh hơn gấp nhiều lần") {
            loadpdffile("")
        }
        else if (title == "Bảng so sánh các tính từ, trạng từ bất quy tắc") {
            loadpdffile("")
        }
        else if (title == "Đại từ quan hệ, trạng từ quan hệ") {
            loadpdffile("menhdequanhe.pdf")
        }
        else if (title == "Rút gọn mệnh đề, lược bỏ đại từ quan hệ") {
            loadpdffile("menhdequanherutgon.pdf")
        }
        else if (title == "Công thức") {
            loadpdffile("cauhoiduoicongthuc.pdf")
        }
        else if (title == "Các dạng đặc biệt") {
            loadpdffile("cauhoiduoidacbiet.pdf")
        }
//        else if (title == "Phần 1") {
//            loadpdffile("")
//        }
//        else if (title == "Phần 2") {
//            loadpdffile("")
//        }
//        else if (title == "Phần 3") {
//            loadpdffile("")
//        }
//        else if (title == "Phần 4") {
//            loadpdffile("")
//        }
//        else if (title == "Các loại danh từ") {
//            loadpdffile("")
//        }
//        else if (title == "Danh từ đếm được và không đếm được") {
//            loadpdffile("")
//        }
//        else if (title == "Danh từ số it và số nhiều") {
//            loadpdffile("")
//        }
//        else if (title == "Tổng hợp danh từ bất quy tắc") {
//            loadpdffile("")
//        }
        else if (title == "Danh từ") {
            loadpdffile("cacloaidanhtu.pdf")
        }
//        else if (title == "Động từ khuyết thiếu") {
//            loadpdffile("")
//        }
//        else if (title == "Nội động từ và ngoại động từ") {
//            loadpdffile("")
//        }
        else if (title == "Động từ") {
            loadpdffile("dongtukhiemkhuyet.pdf")
        }
//        else if (title == "Vị trí tính từ") {
//            loadpdffile("")
//        }
        else if (title == "Tính từ") {
            loadpdffile("cautructrattutinhtu.pdf")
        }
        else if (title == "Trạng từ") {
            loadpdffile("cacloaitrangtu.pdf")
        }



//
//        else if (title == "Tính từ tận cùng bằng 'ing' và 'ed'") {
//            loadpdffile("")
//        }
//        else if (title == "Tính từ kép/ghép") {
//            loadpdffile("")
//        }
//        else if (title == "Cấu trúc trật tự tính từ") {
//            loadpdffile("")
//        }
//        else if (title == "Các tính từ thường gặp") {
//            loadpdffile("")
//        }
//        else if (title == "Vị trí trạng từ") {
//            loadpdffile("")
//        }
//        else if (title == "các loại trạng từ") {
//            loadpdffile("")
//        }
//        else if (title == "Phân loại trạng từ") {
//            loadpdffile("")
//        }
//        else if (title == "Các trạng từ thường gặp") {
//            loadpdffile("")
//        }
//        else if (title == "Định nghĩa, các dùng") {
//            loadpdffile("")
//        }
//        else if (title == "Các loại giới từ") {
//            loadpdffile("")
//        }
        else if (title == "Câu gián tiếp") {
            loadpdffile("")
        }
        else if (title == "Câu cảm thán") {
            loadpdffile("caucamthan.pdf")
        }
        else if (title == "Câu đảo ngữ") {
            loadpdffile("caudaongu.pdf")
        }
        else if (title == "Câu mệnh lệnh") {
            loadpdffile("caumenhlenh.pdf")
        }
        else if (title == "Câu nhấn mạnh (câu chẻ)") {
            loadpdffile("caunhanmanh.pdf")
        }
        else if (title == "Thành ngữ tục ngữ") {
            loadpdffile("thanhngu.pdf")
        }
        else if (title == "Câu đồng tình") {
            loadpdffile("caudongtinh.pdf")
        }
        else if (title == "Bảng động từ bất quy tắc") {
            loadpdffile("dongtubatquytac.pdf")
        }
        else if (title == "Quy tác trọng âm") {
            loadpdffile("quytactrongam.pdf")
        }
        else if (title == "Cách phát âm 's/es'") {
            loadpdffile("phatames.pdf")
        }
        else if (title == "Cách phát ân 'ed'") {
            loadpdffile("phatamed.pdf")
        }
        else if (title == "Vị trí cảu Tính từ-Danh từ-Động từ-Trạng từ") {
            loadpdffile("vitricuatu.pdf")
        }

        
        


    }

    private fun loadpdffile(url: String) {

        pdfView.fromAsset(url)
            .pages(0, 1, 2) // all pages are displayed by default
            .enableSwipe(true) // allows to block changing pages using swipe
            .swipeHorizontal(false)
            .enableDoubletap(false)
            .defaultPage(30)
            // allows to draw something on the current page, usually visible in the middle of the screen
            .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
            .enableAntialiasing(true)
            .password(null)
            .scrollHandle(null)
            //                .nightMode(true) // toggle night mode
            // spacing between pages in dp. To define spacing color, set view background
            .spacing(0)
            .load()
    }


}
