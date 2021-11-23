package com.tickerapp.ui.ticker

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import com.tickerapp.R
import com.tickerapp.database.RegisterDatabase
import com.tickerapp.database.TickerRepository
import com.tickerapp.databinding.FragmentTickerBinding

class TickerFragment : Fragment(), OnChartValueSelectedListener {
    private lateinit var chart: LineChart
    private lateinit var tickerViewModel: TickerViewModel
    private  var adapter: TickerRecyclerViewAdapter? = null

    private lateinit var binding: FragmentTickerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_ticker, container, false
        )
        val application = requireNotNull(this.activity).application
        val dao = RegisterDatabase.getInstance(application).tickerDatabaseDao

        val repository = TickerRepository(dao)
        val factory = TickerViewModelFactory(repository, application)

        tickerViewModel = ViewModelProvider(this, factory).get(TickerViewModel::class.java)

        binding.myTickerViewModel = tickerViewModel

        binding.lifecycleOwner = this
        initRecyclerView()
        tickerViewModel.getData()
        /*    chart = binding.chart
            chart.setOnChartValueSelectedListener(this)
            chart.setDrawGridBackground(false)
            chart.description.isEnabled = false
            chart.setNoDataText("No chart data available. Use the menu to add entries and data sets!");

            chart.invalidate()

            binding.tickerView.setCharacterLists(TickerUtils.provideNumberList())
            chart.setOnChartValueSelectedListener(this)

            chart.description.isEnabled = true

            chart.setTouchEnabled(true)

            chart.isDragEnabled = true
            chart.setScaleEnabled(true)
            chart.setDrawGridBackground(false)

            chart.setPinchZoom(true)

            chart.setBackgroundColor(Color.LTGRAY)
            chart.description.text = "Ticker data"
            val data = LineData()
            data.setValueTextColor(Color.WHITE)
            chart.data = data


            val l = chart.legend
            l.form = Legend.LegendForm.LINE
            l.textColor = Color.WHITE

            val xl = chart.xAxis
            xl.textColor = Color.WHITE
            xl.setDrawGridLines(false)
            xl.setAvoidFirstLastClipping(true)
            xl.isEnabled = true

            val leftAxis = chart.axisLeft
            leftAxis.textColor = Color.WHITE
            leftAxis.axisMaximum = 800f
            leftAxis.axisMinimum = 0f
            leftAxis.setDrawGridLines(true)

            val rightAxis = chart.axisRight
            rightAxis.isEnabled = false

    */

        tickerViewModel.tickerData.observe(viewLifecycleOwner, {
            /*         if (previousValue < it) {
                         binding.ivArrow.setImageResource(R.drawable.arrow_up)
                         binding.tickerView.textColor =
                             ContextCompat.getColor(requireActivity(), R.color.green)
                     } else {
                         binding.tickerView.textColor =
                             ContextCompat.getColor(requireActivity(), R.color.red)
                         binding.ivArrow.setImageResource(R.drawable.arrow_down)
                     }
                     previousValue = it*/


            // addEntry(it)
        })


        return binding.root
    }

    private fun initRecyclerView() {
        binding.rvCoins.layoutManager = LinearLayoutManager(this.context)
        adapter = TickerRecyclerViewAdapter()
        binding.rvCoins.adapter = adapter
        displayList()
    }


    private fun displayList() {
        tickerViewModel.tickerDataList.observe(this, Observer {
            adapter?.setData(it)
        })

    }

    private fun addEntry(newValue: Int) {
        val data = chart.data
        if (data != null) {
            var set = data.getDataSetByIndex(0)
            // set.addEntry(...); // can be called as well
            if (set == null) {
                set = createSet()
                data.addDataSet(set)
            }
            data.addEntry(Entry(set.entryCount.toFloat(), newValue.toFloat()), 0)
            data.notifyDataChanged()

            // let the chart know it's data has changed
            chart.notifyDataSetChanged()

            // limit the number of visible entries
            chart.setVisibleXRangeMaximum(10f)
            // chart.setVisibleYRange(30, AxisDependency.LEFT);

            // move to the latest entry
            chart.moveViewToX(data.entryCount.toFloat())

            // this automatically refreshes the chart (calls invalidate())
            // chart.moveViewTo(data.getXValCount()-7, 55f,
            // AxisDependency.LEFT);
        }
    }

    private fun createSet(): LineDataSet {
        val set = LineDataSet(null, "Dynamic Data")
        set.axisDependency = YAxis.AxisDependency.LEFT
        set.color = ColorTemplate.getHoloBlue()
        set.setCircleColor(Color.WHITE)
        set.lineWidth = 2f
        set.circleRadius = 4f
        set.fillAlpha = 65
        set.fillColor = ColorTemplate.getHoloBlue()
        set.highLightColor = Color.rgb(244, 117, 117)
        set.valueTextColor = Color.WHITE
        set.valueTextSize = 9f
        set.setDrawValues(false)
        return set
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {

    }

    override fun onNothingSelected() {

    }

}