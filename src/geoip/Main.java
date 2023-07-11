package geoip;

// Main standard swing deps
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

// Main file reader and writer deps
import java.io.*;

// GoIPConnector deps
import java.net.*;

public class Main extends JPanel {
	private static GoIPConnector goIPConnector = new GoIPConnector();
	private static JFrame jFrame;

	private Main() {
		JButton searchButton = new JButton("Search");

		final int W = 400;
		final int H = 800;
		final int PAD = 5;

		// JPanel properties
		setPreferredSize(new Dimension(W, H));
		setLayout(null);

		// Set up menu bar
		JMenuBar jMenuBar = new JMenuBar();
		jMenuBar.setBounds(0, 0, W, 25);
		add(jMenuBar);

		// Set up settings menu
		JMenu settings = new JMenu("Settings");
		jMenuBar.add(settings);

		// Set up Connection menu item
		JMenuItem connectionItem = new JMenuItem("Connection");
		connectionItem.addActionListener(e -> openConnectionDialog());
		settings.add(connectionItem);

		// Set up Save Settings menu item
		JMenuItem saveSettingsItem = new JMenuItem("Save Settings");
		saveSettingsItem.addActionListener(e -> {
			try {
				FileWriter writer = new FileWriter("geoip.conf");
				writer.write("host="+goIPConnector.getHost()+"\r\nport="+Integer.toString(goIPConnector.getPort()));
				writer.close();
			} catch (Exception err) {new ErrorDialog(err.getMessage());}
		});
		settings.add(saveSettingsItem);

		// IP label
		JLabel ipLabel = new JLabel("IP");
		ipLabel.setBounds(PAD, PAD*2+25, (W/3)-PAD, 25);
		add(ipLabel);

		// IP text field
		JTextField ipTextField = new JTextField();
		ipTextField.setBounds((W/3)-PAD, PAD*2+25, (W/3)*2-PAD, 25);
		ipTextField.setEditable(false);
		ipTextField.setBackground(Color.WHITE);
		add(ipTextField);

		// WAN section header label
		JLabel wanSectionLabel = new JLabel("----- WAN -----");
		wanSectionLabel.setBounds(PAD, PAD*3+25*2, W-PAD*2, 25);
		wanSectionLabel.setHorizontalAlignment(JTextField.CENTER);
		add(wanSectionLabel);

		// Wan label
		JLabel wanLabel = new JLabel("WAN");
		wanLabel.setBounds(PAD, PAD*4+25*3, (W/2)-PAD, 25);
		add(wanLabel);

		// WAN text field
		JTextField wanTextField = new JTextField();
		wanTextField.setBounds((W/3)-PAD, PAD*4+25*3, (W/3)*2-PAD, 25);
		wanTextField.setEditable(false);
		wanTextField.setBackground(Color.WHITE);
		add(wanTextField);

		// Continent label
		JLabel contLabel = new JLabel("Continent");
		contLabel.setBounds(PAD, PAD*5+25*4, (W/3)-PAD, 25);
		add(contLabel);

		// Continent text field
		JTextField contTextField = new JTextField();
		contTextField.setBounds((W/3)-PAD, PAD*5+25*4, (W/3)*2-PAD, 25);
		contTextField.setEditable(false);
		contTextField.setBackground(Color.WHITE);
		add(contTextField);

		// Country label
		JLabel countLabel = new JLabel("Country");
		countLabel.setBounds(PAD, PAD*6+25*5, (W/3)-PAD, 25);
		add(countLabel);

		// Country text field
		JTextField countTextField = new JTextField();
		countTextField.setBounds((W/3)-PAD, PAD*6+25*5, (W/3)*2-PAD, 25);
		countTextField.setEditable(false);
		countTextField.setBackground(Color.WHITE);
		add(countTextField);

		// Major Subdivision label
		JLabel sub1Label = new JLabel("Major Subdivision");
		sub1Label.setBounds(PAD, PAD*7+25*6, (W/3)-PAD, 25);
		add(sub1Label);

		// Major Subdivision text field
		JTextField sub1TextField = new JTextField();
		sub1TextField.setBounds((W/3)-PAD, PAD*7+25*6, (W/3)*2-PAD, 25);
		sub1TextField.setEditable(false);
		sub1TextField.setBackground(Color.WHITE);
		add(sub1TextField);

		// Minor Subdivision label
		JLabel sub2Label = new JLabel("Minor Subdivision");
		sub2Label.setBounds(PAD, PAD*8+25*7, (W/3)-PAD, 25);
		add(sub2Label);

		// Minor Subdivision text field
		JTextField sub2TextField = new JTextField();
		sub2TextField.setBounds((W/3)-PAD, PAD*8+25*7, (W/3)*2-PAD, 25);
		sub2TextField.setEditable(false);
		sub2TextField.setBackground(Color.WHITE);
		add(sub2TextField);

		// City label
		JLabel cityLabel = new JLabel("City");
		cityLabel.setBounds(PAD, PAD*9+25*8, (W/3)-PAD, 25);
		add(cityLabel);

		// City text field
		JTextField cityTextField = new JTextField();
		cityTextField.setBounds((W/3)-PAD, PAD*9+25*8, (W/3)*2-PAD, 25);
		cityTextField.setEditable(false);
		cityTextField.setBackground(Color.WHITE);
		add(cityTextField);

		// Time zone label
		JLabel tzLabel = new JLabel("Time Zone");
		tzLabel.setBounds(PAD, PAD*10+25*9, (W/3)-PAD, 25);
		add(tzLabel);

		// Time zone text field
		JTextField tzTextField = new JTextField();
		tzTextField.setBounds((W/3)-PAD, PAD*10+25*9, (W/3)*2-PAD, 25);
		tzTextField.setEditable(false);
		tzTextField.setBackground(Color.WHITE);
		add(tzTextField);

		// EU label
		JLabel euLabel = new JLabel("EU");
		euLabel.setBounds(PAD, PAD*11+25*10, (W/3)-PAD, 25);
		add(euLabel);

		// EU text field
		JTextField euTextField = new JTextField();
		euTextField.setBounds((W/3)-PAD, PAD*11+25*10, (W/3)*2-PAD, 25);
		euTextField.setEditable(false);
		euTextField.setBackground(Color.WHITE);
		add(euTextField);

		// Known proxy label
		JLabel anonLabel = new JLabel("Known Proxy");
		anonLabel.setBounds(PAD, PAD*12+25*11, (W/3)-PAD, 25);
		add(anonLabel);

		// Known proxy text field
		JTextField anonTextField = new JTextField();
		anonTextField.setBounds((W/3)-PAD, PAD*12+25*11, (W/3)*2-PAD, 25);
		anonTextField.setEditable(false);
		anonTextField.setBackground(Color.WHITE);
		add(anonTextField);

		// Latitude label
		JLabel latLabel = new JLabel("Latitude");
		latLabel.setBounds(PAD, PAD*13+25*12, (W/3)-PAD, 25);
		add(latLabel);

		// Latitude text field
		JTextField latTextField = new JTextField();
		latTextField.setBounds((W/3)-PAD, PAD*13+25*12, (W/3)*2-PAD, 25);
		latTextField.setEditable(false);
		latTextField.setBackground(Color.WHITE);
		add(latTextField);

		// Longitude label
		JLabel lonLabel = new JLabel("Longitude");
		lonLabel.setBounds(PAD, PAD*14+25*13, (W/3)-PAD, 25);
		add(lonLabel);

		// Longitude text field
		JTextField lonTextField = new JTextField();
		lonTextField.setBounds((W/3)-PAD, PAD*14+25*13, (W/3)*2-PAD, 25);
		lonTextField.setEditable(false);
		lonTextField.setBackground(Color.WHITE);
		add(lonTextField);

		// Accuracy label
		JLabel accLabel = new JLabel("Accuracy");
		accLabel.setBounds(PAD, PAD*15+25*14, (W/3)-PAD, 25);
		add(accLabel);

		// Accuracy text field
		JTextField accTextField = new JTextField();
		accTextField.setBounds((W/3)-PAD, PAD*15+25*14, (W/3)*2-PAD, 25);
		accTextField.setEditable(false);
		accTextField.setBackground(Color.WHITE);
		add(accTextField);

		// ASN section header label
		JLabel asnSectionLabel = new JLabel("----- ASN -----");
		asnSectionLabel.setBounds(PAD, PAD*16+25*15, W-PAD*2, 25);
		asnSectionLabel.setHorizontalAlignment(JTextField.CENTER);
		add(asnSectionLabel);

		// ASN network label
		JLabel asnNetLabel = new JLabel("ASN Network");
		asnNetLabel.setBounds(PAD, PAD*17+25*16, (W/3)-PAD, 25);
		add(asnNetLabel);

		// ASN network text field
		JTextField asnNetTextField = new JTextField();
		asnNetTextField.setBounds((W/3)-PAD, PAD*17+25*16, (W/3)*2-PAD, 25);
		asnNetTextField.setEditable(false);
		asnNetTextField.setBackground(Color.WHITE);
		add(asnNetTextField);

		// ASN label
		JLabel asnLabel = new JLabel("ASN");
		asnLabel.setBounds(PAD, PAD*18+25*17, (W/3)-PAD, 25);
		add(asnLabel);

		// ASN text field
		JTextField asnTextField = new JTextField();
		asnTextField.setBounds((W/3)-PAD, PAD*18+25*17, (W/3)*2-PAD, 25);
		asnTextField.setEditable(false);
		asnTextField.setBackground(Color.WHITE);
		add(asnTextField);

		// ISP label
		JLabel ispLabel = new JLabel("ISP");
		ispLabel.setBounds(PAD, PAD*19+25*18, (W/3)-PAD, 25);
		add(ispLabel);

		// ISP text field
		JTextField ispTextField = new JTextField();
		ispTextField.setBounds((W/3)-PAD, PAD*19+25*18, (W/3)*2-PAD, 25);
		ispTextField.setEditable(false);
		ispTextField.setBackground(Color.WHITE);
		add(ispTextField);

		// Other section header label
		JLabel otherSectionLabel = new JLabel("----- Other -----");
		otherSectionLabel.setBounds(PAD, PAD*20+25*19, W-PAD*2, 25);
		otherSectionLabel.setHorizontalAlignment(JTextField.CENTER);
		add(otherSectionLabel);

		// Tor label
		JLabel torLabel = new JLabel("Known Tor Exit");
		torLabel.setBounds(PAD, PAD*21+25*20, (W/3)-PAD, 25);
		add(torLabel);

		// Tor text field
		JTextField torTextField = new JTextField();
		torTextField.setBounds((W/3)-PAD, PAD*21+25*20, (W/3)*2-PAD, 25);
		torTextField.setEditable(false);
		torTextField.setBackground(Color.WHITE);
		add(torTextField);

		// Snort label
		JLabel snortLabel = new JLabel("Blocked by Snort");
		snortLabel.setBounds(PAD, PAD*22+25*21, (W/3)-PAD, 25);
		add(snortLabel);

		// Snort text field
		JTextField snortTextField = new JTextField();
		snortTextField.setBounds((W/3)-PAD, PAD*22+25*21, (W/3)*2-PAD, 25);
		snortTextField.setEditable(false);
		snortTextField.setBackground(Color.WHITE);
		add(snortTextField);

		// AV label
		JLabel avLabel = new JLabel("AlienVault Warning");
		avLabel.setBounds(PAD, PAD*23+25*22, (W/3)-PAD, 25);
		add(avLabel);

		// AV text field
		JTextField avTextField = new JTextField();
		avTextField.setBounds((W/3)-PAD,  PAD*23+25*22, (W/3)*2-PAD, 25);
		avTextField.setEditable(false);
		avTextField.setBackground(Color.WHITE);
		add(avTextField);

		// Address text field
		JTextField addressTextField = new JTextField();
		addressTextField.setBounds(PAD, H-25-PAD, (W/3)*2-PAD, 25);
		addressTextField.addActionListener(e -> searchButton.doClick());
		add(addressTextField);

		// Search button
		searchButton.setBounds((W/3)*2+PAD, H-25-PAD, W/3-PAD*2, 25);
		searchButton.addActionListener(e -> {
			GoIPConnector.IPRecord ipRecord = goIPConnector.query(addressTextField.getText());
			ipTextField.setText(ipRecord.ip);
			wanTextField.setText(ipRecord.wan);
			contTextField.setText(ipRecord.contName);
			countTextField.setText(ipRecord.countName);
			sub1TextField.setText(ipRecord.sub1Name);
			sub2TextField.setText(ipRecord.sub2Name);
			cityTextField.setText(ipRecord.city);
			tzTextField.setText(ipRecord.tz);
			euTextField.setText(ipRecord.isEU.replace("0", "False").replace("1", "True"));
			anonTextField.setText(ipRecord.isAnon.replace("0", "False").replace("1", "True"));
			latTextField.setText(ipRecord.lat);
			lonTextField.setText(ipRecord.lon);
			accTextField.setText(ipRecord.acc+" km");
			asnNetTextField.setText(ipRecord.asnNet);
			asnTextField.setText(ipRecord.asn);
			ispTextField.setText(ipRecord.org);
			torTextField.setText(ipRecord.isTor.replace("0", "False").replace("1", "True"));
			snortTextField.setText(ipRecord.isBlocked.replace("0", "False").replace("1", "True"));
			avTextField.setText(ipRecord.isMal.replace("0", "False").replace("1", "True"));
		});
		add(searchButton);
	}

	private void openConnectionDialog() {
		final int W = 300;
		final int H = 65;
		final int PAD = 5;

		// Set up the dialog window
		JDialog connectionDialog = new JDialog(jFrame, "Connection", true);
		connectionDialog.getContentPane().setPreferredSize(new Dimension(W, H));
		connectionDialog.getContentPane().setLayout(null);

		// Host label
		JLabel hostLabel = new JLabel("Host");
		hostLabel.setBounds(PAD, PAD, W/3, 25);
		connectionDialog.add(hostLabel);

		// Host text field
		JTextField hostTextField = new JTextField(goIPConnector.getHost());
		hostTextField.setBounds(W/3, PAD, (W/3)*2-PAD, 25);
		connectionDialog.add(hostTextField);

		// Port label
		JLabel portLabel = new JLabel("Port");
		portLabel.setBounds(PAD, PAD*2+25, W/3, 25);
		connectionDialog.add(portLabel);

		// Port text field
		JTextField portTextField = new JTextField(Integer.toString(goIPConnector.getPort()));
		portTextField.setBounds(W/3, PAD*2+25, W/3, 25);
		connectionDialog.add(portTextField);

		// Apply button
		JButton apply = new JButton("Apply");
		apply.setBounds((W/3)*2+PAD, PAD*2+25, W/3-PAD*2, 25);
		apply.addActionListener(e -> {
			goIPConnector.setHost(hostTextField.getText());
			goIPConnector.setPort(Integer.parseInt(portTextField.getText()));
			connectionDialog.setVisible(false);
			connectionDialog.dispose();
		});
		connectionDialog.add(apply);

		// Finish setting up dialog window and show
		connectionDialog.pack();
		connectionDialog.setResizable(false);
		connectionDialog.setLocationRelativeTo(null);
		connectionDialog.setVisible(true);
	}

	public static void main(String[] args) {

		// Construct and set up jFrame
		JFrame jFrame = new JFrame("GeoIP");
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.getContentPane().add(new Main());
		jFrame.pack();
		jFrame.setResizable(false);
		jFrame.setLocationRelativeTo(null);

		// Read configuration from geoip.conf file if it exists
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File("geoip.conf")));

			String configLine;

			while ((configLine = reader.readLine()) != null) {
				String[] tokens = configLine.split("=", 2);
				switch (tokens[0].toLowerCase()) {
					case "host":
						goIPConnector.setHost(tokens[1]);
						break;
					case "port":
						goIPConnector.setPort(Integer.parseInt(tokens[1]));
						break;
				}
			}
			reader.close();
		} catch (Exception err) {}

		// Show jFrame
		jFrame.setVisible(true);
	}
}

// Generic error dialog
class ErrorDialog {
	public ErrorDialog(String msg) {
		JOptionPane optionPane = new JOptionPane(msg, JOptionPane.ERROR_MESSAGE);
		JDialog dialog = optionPane.createDialog("Error");
		dialog.setVisible(true);
	}
}

class GoIPConnector {
	private static String host = "";
	private static int port = 0;

	// Getters
	public String getHost() {return host;}
	public int getPort() {return port;}

	// Setters
	public void setHost(String host) {this.host = host;}
	public void setPort(int port) {this.port = port;}

	public IPRecord query(String ip) {
		if (ip.equals("")) return null;
		IPRecord ipRecord = new IPRecord();
		try {
			URL url = new URL("http://"+host+":"+Integer.toString(port)+"/api?ip="+ip);
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			String responseLine;
			String cat = "ip";
			while ((responseLine = reader.readLine()) != null) {
				// Reformat input
				responseLine = responseLine
					.replaceAll("^\\s+", "")
					.replaceAll(",$", "")
					.replaceAll("^\"", "")
					.replaceAll("\": \"", " ")
					.replaceAll("\": ", " ")
					.replaceAll("\"$", "");
				if (responseLine.equals("{") || responseLine.equals("}")) {continue;}

				String[] tokens = responseLine.split(" ", 2);
				switch (tokens[0]) {
					case "ip":
						ipRecord.ip = tokens[1];
						break;
					case "wan":
						cat = "wan";
						break;
					case "network":
						if (cat.equals("wan")) {
							ipRecord.wan = tokens[1];
						} else if (cat.equals("asn")) {
							ipRecord.asnNet = tokens[1];
						}
						break;
					case "geoname_id":
						if (cat.equals("wan")) {
							ipRecord.geo = tokens[1];
						} else if (cat.equals("location")) {
							ipRecord.asnGeo = tokens[1];
						}
						break;
					case "registered_country_geoname_id":
						ipRecord.reg = tokens[1];
						break;
					case "represented_country_geoname_id":
						ipRecord.rep = tokens[1];
						break;
					case "is_anonymous_proxy":
						ipRecord.isAnon = tokens[1];
						break;
					case "is_satellite_provider":
						ipRecord.isSat = tokens[1];
						break;
					case "postal_code":
						ipRecord.post = tokens[1];
						break;
					case "latitude":
						ipRecord.lat = tokens[1];
						break;
					case "longitude":
						ipRecord.lon = tokens[1];
						break;
					case "accuracy_radius":
						ipRecord.acc = tokens[1];
						break;
					case "asn":
						cat = "asn";
						break;
					case "autonomous_system_number":
						ipRecord.asn = tokens[1];
						break;
					case "autonomous_system_organization":
						ipRecord.org = tokens[1];
						break;
					case "location":
						cat = "location";
						break;
					case "locale_code":
						ipRecord.loc = tokens[1];
						break;
					case "continent_code":
						ipRecord.contISO = tokens[1];
						break;
					case "continent_name":
						ipRecord.contName = tokens[1];
						break;
					case "country_iso_code":
						ipRecord.countISO = tokens[1];
						break;
					case "country_name":
						ipRecord.countName = tokens[1];
						break;
					case "subdivision_1_iso_code":
						ipRecord.sub1ISO = tokens[1];
						break;
					case "subdivision_1_name":
						ipRecord.sub1Name = tokens[1];
						break;
					case "subdivision_2_iso_code":
						ipRecord.sub2ISO = tokens[1];
						break;
					case "subdivision_2_name":
						ipRecord.sub2Name = tokens[1];
						break;
					case "city_name":
						ipRecord.city = tokens[1];
						break;
					case "metro_code":
						ipRecord.metro = tokens[1];
						break;
					case "time_zone":
						ipRecord.tz = tokens[1];
						break;
					case "is_in_european_union":
						ipRecord.isEU = tokens[1];
						break;
					case "other":
						cat = "other";
						break;
					case "is_tor_node":
						ipRecord.isTor = tokens[1];
						break;
					case "is_blocked":
						ipRecord.isBlocked = tokens[1];
						break;
					case "is_malicious":
						ipRecord.isMal = tokens[1];
						break;
				}
			}
		} catch (Exception err) {new ErrorDialog(err.getMessage());}
		return ipRecord;
	}

	class IPRecord {
		String ip = "";
		String wan = "";
		String geo = "";
		String reg = "";
		String rep = "";
		String isAnon = "";
		String isSat = "";
		String post = "";
		String lat = "";
		String lon = "";
		String acc = "";
		String asnNet = "";
		String asn = "";
		String org = "";
		String asnGeo = "";
		String loc = "";
		String contISO = "";
		String contName = "";
		String countISO = "";
		String countName = "";
		String sub1ISO = "";
		String sub1Name = "";
		String sub2ISO = "";
		String sub2Name = "";
		String city = "";
		String metro = "";
		String tz = "";
		String isEU = "";
		String isTor = "";
		String isBlocked = "";
		String isMal = "";
	}
}