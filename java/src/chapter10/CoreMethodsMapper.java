package chapter10;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;

public class CoreMethodsMapper {

	public static class Map extends MapReduceBase implements
			Mapper<LongWritable, Text, Text, Text> {
		private Text userid = new Text();
		private Text userinfo = new Text();

		public void map(LongWritable key, Text value,
				OutputCollector<Text, Text> output, Reporter reporter)
				throws IOException {
			String[] split = value.toString().split(",");
			double[] datavalues = new double[12];

			for (int i = 1; i <= 12; i++) {
				datavalues[i - 1] = Double.parseDouble(split[i]);
			}

			double mean = getMean(datavalues);

			StringBuilder sb = new StringBuilder()
					.append(mean + "\t")
					.append(calcSalesDrop(Double.parseDouble(split[13]), mean)
							+ "\t").append(monthsBelow(datavalues, mean));

			userid.set(split[0]); // define our output key, the user id
			userinfo.set(sb.toString()); // define the output data

			output.collect(userid, userinfo);
		}

		private int calcSalesDrop(double lastMonth, double mean) {
			return (int) (lastMonth - mean) < 0 ? 0 : (int) (lastMonth - mean);
		}

		private int monthsBelow(double[] data, double mean) {
			int count = 0;
			for (double a : data) {
				if ((a < (mean * 0.40)))
					count++;
			}
			return count;
		}

		private double getMean(double[] data) {
			double sum = 0.0;
			for (double a : data) {
				sum += a;
			}
			return sum / data.length;
		}

	}
	
	public static void main(String args[]) throws IOException {
		JobConf conf = new JobConf(CoreMethodsMapper.class);
		conf.setJobName("CoreMethods Sales Data");
		conf.setNumReduceTasks(0); // no reducers!
		
		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(Text.class);
		
		conf.setMapperClass(Map.class); // the map class within this code
		
		conf.setInputFormat(TextInputFormat.class);
		conf.setOutputFormat(TextOutputFormat.class);
		
		FileInputFormat.setInputPaths(conf, new Path(args[0]));
		FileOutputFormat.setOutputPath(conf, new Path(args[1]));
		
		JobClient.runJob(conf);
		
	}
}
