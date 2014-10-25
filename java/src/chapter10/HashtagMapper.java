package chapter10;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

public class HashtagMapper extends MapReduceBase implements
		Mapper<LongWritable, Text, Text, IntWritable> {

	private final static IntWritable one = new IntWritable(1);
	private Text word = new Text();

	@Override
	public void map(LongWritable key, Text value,
			OutputCollector<Text, IntWritable> output, Reporter reporter)
			throws IOException {

		String inputLine = value.toString().toLowerCase();
		System.out.println("Reading: " + inputLine);
		String[] splitLine = inputLine.split("\\|");
		System.out.println("Line has " + splitLine.length + " elements.");
		Pattern pattern = Pattern.compile("#[\\w]+");
		if (splitLine.length > 1) {
			Matcher matcher = pattern.matcher(splitLine[1]);
			while (matcher.find()) {
				word.set(matcher.group());
				output.collect(word, one);
			}
		}
	}
}
