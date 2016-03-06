/*
 * JMagUI.java
 *
 * Created on 16 ������� 2006 �., 16:12
 */

package captain.northern;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.File;
import java.text.AttributedString;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ProgressMonitor;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

/**
 *
 * @author  leo
 */
public class JMagUI extends javax.swing.JFrame
{
    
    /** Creates new form JMagUI */
    public JMagUI()
    {
        initComponents();
        
        opt.setDbgOut(logwin.getLogOut());
        src_files_tbl.setModel(store.getTableModel());
        tuneGrid(src_files_tbl);
        updateSrcInfo();
        
        
        alb_files_tbl.setModel(album.getTableModel());
        tuneGrid(alb_files_tbl);
        updateAlbInfo();
        
        BoxLayout box = new BoxLayout(srcChartPanel, BoxLayout.X_AXIS);
        srcChartPanel.setLayout(box);
        srcChartPanel.add(createChartPanel());
        srcChartPanel.add(createAlbChartPanel());

//        srcChart2Panel.setLayout(new BorderLayout());
//        srcChart2Panel.add(createChartPanel());
        
        
        destFolderText.setText(opt.getDestFolder().getAbsolutePath());
        destFullLabel.setText(opt.getDestFolder().getAbsolutePath());
        randSubListsCheck.setSelected(opt.isSubListShuffle());
        divideCategoryCheck.setSelected(opt.isDivideSubList());
        randResultCheck.setSelected(opt.isResultShuffle());
        saveToID3Check.setSelected(opt.isSaveToID3());
        readID3Check.setSelected(opt.isReadID3());
        convertID3Check.setSelected(opt.isConvertID3());
        convertNamesCheck.setSelected(opt.isConvertNames());
        
        AlbPrefixText.setText(opt.getAlbimPrefix());
        albumNameLbl.setText(opt.getAlbumName());
        filterMP3Check.setSelected(opt.isFilterMP3());
        
        updateRecentList();
    }
    
    private void loadSrcList(final File from)
    {
        ProgressMonitor progressMonitor = new ProgressMonitor(this.getParent(),
                "Loading source database files...",
                "", 0, 100);
        progressMonitor.setProgress(0);
        progressMonitor.setMillisToDecideToPopup(50);
        final JIProgressMeter meter = new JMSwingProgressMeter(progressMonitor, 100);
        final Container parent = this.getParent();
        final SwingWorker worker = new SwingWorker()
        {
            public Object construct()
            {
                return new Boolean(store.load(from, meter));
            }
            public void finished()
            {
                Toolkit.getDefaultToolkit().beep();
                meter.stop();
                if(!(Boolean)this.getValue())
                {
                    JOptionPane.showMessageDialog(parent,
                            "Error: Wrong list file format or other problems with your file\n" +
                            "Please, choose the JMAG source list file (*.mag extension).",
                            "Load source list",
                            JOptionPane.ERROR_MESSAGE);
                    
                } else
                    opt.setLastSrcListFile(from);
                updateRecentList();
                updateSrcInfo();
            }
        };
        worker.start();
    }
        
    void tuneGrid(JTable tbl)
    {
        {
            TableColumn numbersColumn = tbl.getColumn("Used");
            DefaultTableCellRenderer numberColumnRenderer = new DefaultTableCellRenderer()
            {
                public void setValue(Object value)
                {
                    int cellValue = (value instanceof Number) ? ((Number)value).intValue() : 0;
                    setBackground(opt.getCategoryColor(opt.getCategory(cellValue)));
                    setText((value == null) ? "" : value.toString());
                }
            };
            numberColumnRenderer.setHorizontalAlignment(JLabel.RIGHT);
            numbersColumn.setCellRenderer(numberColumnRenderer);
            numbersColumn.setPreferredWidth(40);
        }
        {
            TableColumn column = tbl.getColumn("Filename");
            column.setPreferredWidth(300);
        }
        {
            TableColumn column = tbl.getColumn("Time");
            column.setPreferredWidth(90);
        }
        {
            TableColumn column = tbl.getColumn("Size");
            column.setPreferredWidth(60);
        }
        {
            TableColumn column = tbl.getColumn("Directory");
            column.setPreferredWidth(150);
        }
        {
            TableColumn column = tbl.getColumn("Description");
            column.setPreferredWidth(350);
        }
        {
            TableColumn numbersColumn = tbl.getColumn("Exists");
            DefaultTableCellRenderer numberColumnRenderer = new DefaultTableCellRenderer()
            {
                public void setValue(Object value)
                {
                    Boolean cellValue = (value instanceof Boolean) ? (Boolean)value : false;
                    if(cellValue == false)
                        setBackground(opt.getCategoryColor(0));
                    else
                        setBackground(Color.WHITE);
                    setText(cellValue ? "yes" : "NO");
                }
            };
            numberColumnRenderer.setHorizontalAlignment(JLabel.RIGHT);
            numbersColumn.setCellRenderer(numberColumnRenderer);
            numbersColumn.setPreferredWidth(45);
        }
        
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        alb_src_files_lbl = new javax.swing.JLabel();
        alb_src_size_lbl = new javax.swing.JLabel();
        alb_src_time_lbl = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        srcPathLbl = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        dest_type_combo = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        cleanDirButton = new javax.swing.JButton();
        destFullLabel = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        alb_dest_size_lbl = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        alb_dest_files_lbl = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        AlbPrefixText = new javax.swing.JTextField();
        albumNameLbl = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        convertNamesCheck = new javax.swing.JCheckBox();
        convertID3Check = new javax.swing.JCheckBox();
        saveToID3Check = new javax.swing.JCheckBox();
        divideCategoryCheck = new javax.swing.JCheckBox();
        randSubListsCheck = new javax.swing.JCheckBox();
        randResultCheck = new javax.swing.JCheckBox();
        filterMP3Check = new javax.swing.JCheckBox();
        readID3Check = new javax.swing.JCheckBox();
        jLabel8 = new javax.swing.JLabel();
        ID3FCombo = new javax.swing.JComboBox();
        jPanel14 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        srcChartPanel = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        recentBut0 = new javax.swing.JButton();
        recentBut1 = new javax.swing.JButton();
        recentBut2 = new javax.swing.JButton();
        recentBut3 = new javax.swing.JButton();
        recentBut4 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        src_files_tbl = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        src_files_lbl = new javax.swing.JLabel();
        src_files_size_lbl = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        mustBeFilesLbl = new javax.swing.JLabel();
        mustBeSizeLbl = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        alwaysUseButton = new javax.swing.JButton();
        rndUsageButton = new javax.swing.JButton();
        usedMinusButton = new javax.swing.JButton();
        usedPlusButton = new javax.swing.JButton();
        LoadID3But = new javax.swing.JButton();
        updateID3But = new javax.swing.JButton();
        file2ID3But = new javax.swing.JButton();
        id3toFileBut = new javax.swing.JButton();
        jPanel16 = new javax.swing.JPanel();
        addFldBut = new javax.swing.JButton();
        addFilesButton = new javax.swing.JButton();
        removeSrcButton = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        alb_files_tbl = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        shuffleButton = new javax.swing.JButton();
        genButton = new javax.swing.JButton();
        jPanel18 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        alb_files_lbl = new javax.swing.JLabel();
        alb_files_size_lbl = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        destFolderText = new javax.swing.JTextField();
        destFolderButton = new javax.swing.JButton();
        jLabel62 = new javax.swing.JLabel();
        copyButton = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuNewCol = new javax.swing.JMenuItem();
        jMenuOpen = new javax.swing.JMenuItem();
        jMenuSave = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        showLogWin = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        jMenuExit = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("JMAG - Music Album Generator");

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Source collection"));

        jLabel3.setText("Total files in source database:");

        jLabel5.setText("Total files size:");

        jLabel6.setText("Total playing time:");
        jLabel6.setEnabled(false);

        alb_src_files_lbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        alb_src_files_lbl.setText("0");

        alb_src_size_lbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        alb_src_size_lbl.setText("0Kb");

        alb_src_time_lbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        alb_src_time_lbl.setText("00:00:00");
        alb_src_time_lbl.setEnabled(false);

        jLabel2.setText("Source path:");

        srcPathLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        srcPathLbl.setText("none");

        org.jdesktop.layout.GroupLayout jPanel4Layout = new org.jdesktop.layout.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel4Layout.createSequentialGroup()
                        .add(jLabel2)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(srcPathLbl, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel4Layout.createSequentialGroup()
                        .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel3)
                            .add(jLabel5)
                            .add(jLabel6))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(alb_src_time_lbl, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(alb_src_size_lbl, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(alb_src_files_lbl, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 67, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel4Layout.createSequentialGroup()
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(srcPathLbl))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel3)
                    .add(alb_src_files_lbl))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel5)
                    .add(alb_src_size_lbl))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel6)
                    .add(alb_src_time_lbl))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Generated Album"));

        jLabel4.setText("Destination type:");

        dest_type_combo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "USB 512Mb", "USB 1Gb", "USB 1.5Gb", "USB 2Gb", "USB 3Gb", "USB 4Gb", "USB 8Gb", "USB 16Gb", "CD 600Mb", "CD 700Mb", "DVD 4.5G" }));
        dest_type_combo.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                dest_type_comboActionPerformed(evt);
            }
        });

        jLabel7.setText("Destination path:");

        cleanDirButton.setText("Clear");
        cleanDirButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                cleanDirButtonActionPerformed(evt);
            }
        });

        destFullLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        destFullLabel.setText("L:\\");

            jLabel9.setText("Total files size:");

            alb_dest_size_lbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
            alb_dest_size_lbl.setText("0Kb");

            jLabel10.setText("Total files in generated album:");

            alb_dest_files_lbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
            alb_dest_files_lbl.setText("0");

            jLabel11.setText("Total playing time:");
            jLabel11.setEnabled(false);

            jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
            jLabel12.setText("00:00:00");
            jLabel12.setEnabled(false);

            jLabel1.setText("Album name, prefix:");

            AlbPrefixText.setText("JMAG_");
            AlbPrefixText.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    AlbPrefixTextActionPerformed(evt);
                }
            });

            albumNameLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
            albumNameLbl.setText("0");

            jButton1.setText("Generate");
            jButton1.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    genButtonActionPerformed(evt);
                }
            });

            org.jdesktop.layout.GroupLayout jPanel5Layout = new org.jdesktop.layout.GroupLayout(jPanel5);
            jPanel5.setLayout(jPanel5Layout);
            jPanel5Layout.setHorizontalGroup(
                jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel5Layout.createSequentialGroup()
                    .addContainerGap()
                    .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(jPanel5Layout.createSequentialGroup()
                            .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(jPanel5Layout.createSequentialGroup()
                                    .add(jLabel4)
                                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .add(dest_type_combo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                .add(jPanel5Layout.createSequentialGroup()
                                    .add(jLabel1)
                                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                    .add(albumNameLbl, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .add(jPanel5Layout.createSequentialGroup()
                                    .add(jLabel7)
                                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                    .add(destFullLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .add(13, 13, 13))
                        .add(jPanel5Layout.createSequentialGroup()
                            .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(jLabel10)
                                .add(jLabel9)
                                .add(jLabel11))
                            .add(263, 263, 263)))
                    .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, jLabel12, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 77, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, alb_dest_size_lbl, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 77, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, alb_dest_files_lbl, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 77, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(AlbPrefixText, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 77, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(cleanDirButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 77, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jButton1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 77, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap())
            );
            jPanel5Layout.setVerticalGroup(
                jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel5Layout.createSequentialGroup()
                    .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabel4)
                        .add(dest_type_combo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jButton1))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabel7)
                        .add(destFullLabel)
                        .add(cleanDirButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabel1)
                        .add(AlbPrefixText, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(albumNameLbl))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabel10)
                        .add(alb_dest_files_lbl))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabel9)
                        .add(alb_dest_size_lbl))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabel11)
                        .add(jLabel12))
                    .addContainerGap(26, Short.MAX_VALUE))
            );

            jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder("Options"));

            convertNamesCheck.setSelected(true);
            convertNamesCheck.setText("Convert russian file names to latin ones");
            convertNamesCheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
            convertNamesCheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
            convertNamesCheck.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    convertNamesCheckActionPerformed(evt);
                }
            });

            convertID3Check.setSelected(true);
            convertID3Check.setText("Convert ID3 russian tags to latin ones");
            convertID3Check.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
            convertID3Check.setMargin(new java.awt.Insets(0, 0, 0, 0));
            convertID3Check.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    convertID3CheckActionPerformed(evt);
                }
            });

            saveToID3Check.setSelected(true);
            saveToID3Check.setText("Write JMAG file statistics to ID3 comment field");
            saveToID3Check.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
            saveToID3Check.setMargin(new java.awt.Insets(0, 0, 0, 0));
            saveToID3Check.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    saveToID3CheckActionPerformed(evt);
                }
            });

            divideCategoryCheck.setSelected(true);
            divideCategoryCheck.setText("Divide source list to sub-lists by usage statistics");
            divideCategoryCheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
            divideCategoryCheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
            divideCategoryCheck.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    divideCategoryCheckActionPerformed(evt);
                }
            });

            randSubListsCheck.setSelected(true);
            randSubListsCheck.setText("Randomize sub-lists before usage");
            randSubListsCheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
            randSubListsCheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
            randSubListsCheck.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    randSubListsCheckActionPerformed(evt);
                }
            });

            randResultCheck.setSelected(true);
            randResultCheck.setText("Randomize (shuffle) result list after generation");
            randResultCheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
            randResultCheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
            randResultCheck.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    randResultCheckActionPerformed(evt);
                }
            });

            filterMP3Check.setSelected(true);
            filterMP3Check.setText("Use only music MP3 files");
            filterMP3Check.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
            filterMP3Check.setMargin(new java.awt.Insets(0, 0, 0, 0));
            filterMP3Check.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    filterMP3CheckActionPerformed(evt);
                }
            });

            readID3Check.setText("Read ID3 tags on source list loading");
            readID3Check.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
            readID3Check.setMargin(new java.awt.Insets(0, 0, 0, 0));
            readID3Check.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    readID3CheckActionPerformed(evt);
                }
            });

            jLabel8.setText("ID3 -> File convertion format:");

            ID3FCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "N-Artist-Album-Song", "N-Artist-Song", "N-Album-Song", "N-Song", "Song", "Artist-Song" }));

            org.jdesktop.layout.GroupLayout jPanel12Layout = new org.jdesktop.layout.GroupLayout(jPanel12);
            jPanel12.setLayout(jPanel12Layout);
            jPanel12Layout.setHorizontalGroup(
                jPanel12Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel12Layout.createSequentialGroup()
                    .addContainerGap()
                    .add(jPanel12Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(convertNamesCheck)
                        .add(convertID3Check)
                        .add(saveToID3Check)
                        .add(divideCategoryCheck)
                        .add(randSubListsCheck)
                        .add(randResultCheck)
                        .add(filterMP3Check)
                        .add(readID3Check)
                        .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel12Layout.createSequentialGroup()
                            .add(jLabel8)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 53, Short.MAX_VALUE)
                            .add(ID3FCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 138, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap())
            );
            jPanel12Layout.setVerticalGroup(
                jPanel12Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel12Layout.createSequentialGroup()
                    .addContainerGap()
                    .add(convertNamesCheck)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(convertID3Check)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(saveToID3Check)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(divideCategoryCheck)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(randSubListsCheck)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(randResultCheck)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(filterMP3Check)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(readID3Check)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel12Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(ID3FCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jLabel8)))
            );

            jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder("About"));

            jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/captain/northern/duke.gif"))); // NOI18N
            jLabel25.setText("<html>JMAG v 1.0.5 23-Aug-2006 <br>Music album generator is written in Java using <b>Swing</b> toolkit.<br>Copyright (c) 2006 Leonid Khramov aka <b>Leo</b>.<br>E-mail: KingLeonid@yandex.ru or leo@xnc.dubna.su</html>");

            org.jdesktop.layout.GroupLayout jPanel14Layout = new org.jdesktop.layout.GroupLayout(jPanel14);
            jPanel14.setLayout(jPanel14Layout);
            jPanel14Layout.setHorizontalGroup(
                jPanel14Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel14Layout.createSequentialGroup()
                    .addContainerGap()
                    .add(jLabel25, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE)
                    .addContainerGap())
            );
            jPanel14Layout.setVerticalGroup(
                jPanel14Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel14Layout.createSequentialGroup()
                    .add(jLabel25)
                    .addContainerGap())
            );

            srcChartPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Usage statistics by category (source, album)"));

            org.jdesktop.layout.GroupLayout srcChartPanelLayout = new org.jdesktop.layout.GroupLayout(srcChartPanel);
            srcChartPanel.setLayout(srcChartPanelLayout);
            srcChartPanelLayout.setHorizontalGroup(
                srcChartPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(0, 0, Short.MAX_VALUE)
            );
            srcChartPanelLayout.setVerticalGroup(
                srcChartPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(0, 0, Short.MAX_VALUE)
            );

            jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder("Recent lists"));

            recentBut0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/captain/northern/images/16/gtk-cdrom.png"))); // NOI18N
            recentBut0.setText("none");
            recentBut0.setContentAreaFilled(false);
            recentBut0.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            recentBut0.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    recentBut0ActionPerformed(evt);
                }
            });

            recentBut1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/captain/northern/images/16/gtk-cdrom.png"))); // NOI18N
            recentBut1.setText("none");
            recentBut1.setContentAreaFilled(false);
            recentBut1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            recentBut1.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    recentBut1ActionPerformed(evt);
                }
            });

            recentBut2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/captain/northern/images/16/gtk-cdrom.png"))); // NOI18N
            recentBut2.setText("none");
            recentBut2.setContentAreaFilled(false);
            recentBut2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            recentBut2.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    recentBut2ActionPerformed(evt);
                }
            });

            recentBut3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/captain/northern/images/16/gtk-cdrom.png"))); // NOI18N
            recentBut3.setText("none");
            recentBut3.setContentAreaFilled(false);
            recentBut3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            recentBut3.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    recentBut3ActionPerformed(evt);
                }
            });

            recentBut4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/captain/northern/images/16/gtk-cdrom.png"))); // NOI18N
            recentBut4.setText("none");
            recentBut4.setContentAreaFilled(false);
            recentBut4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            recentBut4.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    recentBut4ActionPerformed(evt);
                }
            });

            org.jdesktop.layout.GroupLayout jPanel13Layout = new org.jdesktop.layout.GroupLayout(jPanel13);
            jPanel13.setLayout(jPanel13Layout);
            jPanel13Layout.setHorizontalGroup(
                jPanel13Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(recentBut0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
                .add(recentBut1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
                .add(recentBut2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
                .add(recentBut3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
                .add(recentBut4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
            );
            jPanel13Layout.setVerticalGroup(
                jPanel13Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel13Layout.createSequentialGroup()
                    .add(recentBut0)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(recentBut1)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(recentBut2)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(recentBut3)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(recentBut4)
                    .addContainerGap(22, Short.MAX_VALUE))
            );

            org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
            jPanel1.setLayout(jPanel1Layout);
            jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(jPanel12, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(jPanel13, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(jPanel14, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel5, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(srcChartPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap())
            );
            jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(jPanel14, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jPanel4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                        .add(jPanel13, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(jPanel5, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(jPanel12, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(srcChartPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap())
            );

            jTabbedPane1.addTab("Common", jPanel1);

            jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Source files"));

            jScrollPane1.setAutoscrolls(true);

            src_files_tbl.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][]
                {
                    {null, null, null, null, null, null},
                    {null, null, null, null, null, null},
                    {null, null, null, null, null, null},
                    {null, null, null, null, null, null}
                },
                new String []
                {
                    "Mark", "Filename", "Description", "Size", "Time", "Directory"
                }
            ));
            src_files_tbl.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            jScrollPane1.setViewportView(src_files_tbl);

            org.jdesktop.layout.GroupLayout jPanel6Layout = new org.jdesktop.layout.GroupLayout(jPanel6);
            jPanel6.setLayout(jPanel6Layout);
            jPanel6Layout.setHorizontalGroup(
                jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 663, Short.MAX_VALUE)
            );
            jPanel6Layout.setVerticalGroup(
                jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE)
            );

            jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Info"));

            jLabel13.setText("Files:");

            jLabel14.setText("Size:");

            jLabel15.setText("Time:");
            jLabel15.setEnabled(false);

            src_files_lbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
            src_files_lbl.setText("0");

            src_files_size_lbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
            src_files_size_lbl.setText("0");

            jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
            jLabel21.setText("00:00:00");
            jLabel21.setEnabled(false);

            org.jdesktop.layout.GroupLayout jPanel7Layout = new org.jdesktop.layout.GroupLayout(jPanel7);
            jPanel7.setLayout(jPanel7Layout);
            jPanel7Layout.setHorizontalGroup(
                jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel7Layout.createSequentialGroup()
                    .addContainerGap()
                    .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(jPanel7Layout.createSequentialGroup()
                            .add(jLabel13)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                            .add(src_files_lbl, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE))
                        .add(jPanel7Layout.createSequentialGroup()
                            .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(jLabel14)
                                .add(jLabel15))
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                            .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(jLabel21, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                                .add(src_files_size_lbl, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE))))
                    .addContainerGap())
            );
            jPanel7Layout.setVerticalGroup(
                jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel7Layout.createSequentialGroup()
                    .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabel13)
                        .add(src_files_lbl))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabel14)
                        .add(src_files_size_lbl))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabel15)
                        .add(jLabel21)))
            );

            jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("'Must be' files"));

            jLabel16.setText("Files:");

            jLabel17.setText("Size:");

            jLabel18.setText("Time:");
            jLabel18.setEnabled(false);

            mustBeFilesLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
            mustBeFilesLbl.setText("0");

            mustBeSizeLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
            mustBeSizeLbl.setText("0");

            jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
            jLabel24.setText("00:00:00");
            jLabel24.setEnabled(false);

            org.jdesktop.layout.GroupLayout jPanel8Layout = new org.jdesktop.layout.GroupLayout(jPanel8);
            jPanel8.setLayout(jPanel8Layout);
            jPanel8Layout.setHorizontalGroup(
                jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel8Layout.createSequentialGroup()
                    .addContainerGap()
                    .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(jLabel16)
                        .add(jLabel17)
                        .add(jLabel18))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(jLabel24, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                        .add(mustBeSizeLbl, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                        .add(mustBeFilesLbl, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE))
                    .addContainerGap())
            );
            jPanel8Layout.setVerticalGroup(
                jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel8Layout.createSequentialGroup()
                    .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabel16)
                        .add(mustBeFilesLbl))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabel17)
                        .add(mustBeSizeLbl))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabel18)
                        .add(jLabel24)))
            );

            jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder("Usage control"));

            alwaysUseButton.setText("Always use");
            alwaysUseButton.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    alwaysUseButtonActionPerformed(evt);
                }
            });

            rndUsageButton.setText("Rnd usage");
            rndUsageButton.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    rndUsageButtonActionPerformed(evt);
                }
            });

            usedMinusButton.setText("Used  -1");
            usedMinusButton.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    usedMinusButtonActionPerformed(evt);
                }
            });

            usedPlusButton.setText("Used  +1");
            usedPlusButton.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    usedPlusButtonActionPerformed(evt);
                }
            });

            LoadID3But.setText("Load ID3");
            LoadID3But.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    LoadID3ButActionPerformed(evt);
                }
            });

            updateID3But.setText("Update ID3");
            updateID3But.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    updateID3ButActionPerformed(evt);
                }
            });

            file2ID3But.setText("File -> ID3");
            file2ID3But.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    file2ID3ButActionPerformed(evt);
                }
            });

            id3toFileBut.setText("ID3 -> File");
            id3toFileBut.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    id3toFileButActionPerformed(evt);
                }
            });

            org.jdesktop.layout.GroupLayout jPanel15Layout = new org.jdesktop.layout.GroupLayout(jPanel15);
            jPanel15.setLayout(jPanel15Layout);
            jPanel15Layout.setHorizontalGroup(
                jPanel15Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel15Layout.createSequentialGroup()
                    .add(jPanel15Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                        .add(id3toFileBut, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(LoadID3But, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(usedMinusButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(alwaysUseButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 113, Short.MAX_VALUE))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel15Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                        .add(org.jdesktop.layout.GroupLayout.TRAILING, updateID3But, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(org.jdesktop.layout.GroupLayout.TRAILING, file2ID3But, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(org.jdesktop.layout.GroupLayout.TRAILING, usedPlusButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(org.jdesktop.layout.GroupLayout.TRAILING, rndUsageButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            );
            jPanel15Layout.setVerticalGroup(
                jPanel15Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel15Layout.createSequentialGroup()
                    .add(jPanel15Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(rndUsageButton)
                        .add(alwaysUseButton))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(jPanel15Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(usedMinusButton)
                        .add(usedPlusButton))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(jPanel15Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(file2ID3But)
                        .add(id3toFileBut))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel15Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(updateID3But)
                        .add(LoadID3But)))
            );

            jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder("File control"));

            addFldBut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/captain/northern/images/24/edit_add.png"))); // NOI18N
            addFldBut.setText("Add Folder");
            addFldBut.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    addFldButActionPerformed(evt);
                }
            });

            addFilesButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/captain/northern/images/24/edit_add.png"))); // NOI18N
            addFilesButton.setText("Add Files");
            addFilesButton.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    addFilesButtonActionPerformed(evt);
                }
            });

            removeSrcButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/captain/northern/images/24/edit_remove.png"))); // NOI18N
            removeSrcButton.setText("Remove");
            removeSrcButton.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    removeSrcButtonActionPerformed(evt);
                }
            });

            org.jdesktop.layout.GroupLayout jPanel16Layout = new org.jdesktop.layout.GroupLayout(jPanel16);
            jPanel16.setLayout(jPanel16Layout);
            jPanel16Layout.setHorizontalGroup(
                jPanel16Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(removeSrcButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(addFilesButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(addFldBut, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );
            jPanel16Layout.setVerticalGroup(
                jPanel16Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel16Layout.createSequentialGroup()
                    .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(addFldBut)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(addFilesButton)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(removeSrcButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 33, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            );

            org.jdesktop.layout.GroupLayout jPanel9Layout = new org.jdesktop.layout.GroupLayout(jPanel9);
            jPanel9.setLayout(jPanel9Layout);
            jPanel9Layout.setHorizontalGroup(
                jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel9Layout.createSequentialGroup()
                    .addContainerGap()
                    .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(jPanel9Layout.createSequentialGroup()
                            .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(jPanel7, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(jPanel8, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .addContainerGap())
                        .add(jPanel9Layout.createSequentialGroup()
                            .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(jPanel15, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(jPanel16, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .add(14, 14, 14))))
            );
            jPanel9Layout.setVerticalGroup(
                jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel9Layout.createSequentialGroup()
                    .add(jPanel7, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(jPanel8, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 41, Short.MAX_VALUE)
                    .add(jPanel15, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(jPanel16, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            );

            org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
            jPanel2.setLayout(jPanel2Layout);
            jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .add(jPanel6, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(jPanel9, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
            );
            jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel9, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel6, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap())
            );

            jTabbedPane1.addTab("Sources", jPanel2);

            jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("Album files"));

            alb_files_tbl.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][]
                {
                    {null, null, null, null},
                    {null, null, null, null},
                    {null, null, null, null},
                    {null, null, null, null}
                },
                new String []
                {
                    "Title 1", "Title 2", "Title 3", "Title 4"
                }
            ));
            alb_files_tbl.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            jScrollPane2.setViewportView(alb_files_tbl);

            org.jdesktop.layout.GroupLayout jPanel10Layout = new org.jdesktop.layout.GroupLayout(jPanel10);
            jPanel10.setLayout(jPanel10Layout);
            jPanel10Layout.setHorizontalGroup(
                jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 729, Short.MAX_VALUE)
            );
            jPanel10Layout.setVerticalGroup(
                jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE)
            );

            shuffleButton.setText("Re-shuffle");
            shuffleButton.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    shuffleButtonActionPerformed(evt);
                }
            });

            genButton.setText("Generate");
            genButton.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    genButtonActionPerformed(evt);
                }
            });

            jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder("Info"));

            jLabel19.setText("Files:");

            jLabel20.setText("Size:");

            jLabel61.setText("Time:");
            jLabel61.setEnabled(false);

            alb_files_lbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
            alb_files_lbl.setText("0");

            alb_files_size_lbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
            alb_files_size_lbl.setText("0");

            jLabel64.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
            jLabel64.setText("00:00:00");
            jLabel64.setEnabled(false);

            org.jdesktop.layout.GroupLayout jPanel18Layout = new org.jdesktop.layout.GroupLayout(jPanel18);
            jPanel18.setLayout(jPanel18Layout);
            jPanel18Layout.setHorizontalGroup(
                jPanel18Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel18Layout.createSequentialGroup()
                    .addContainerGap()
                    .add(jPanel18Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(jLabel19)
                        .add(jLabel20)
                        .add(jLabel61))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(jPanel18Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(jLabel64, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                        .add(alb_files_size_lbl, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                        .add(alb_files_lbl, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE))
                    .addContainerGap())
            );
            jPanel18Layout.setVerticalGroup(
                jPanel18Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel18Layout.createSequentialGroup()
                    .add(jPanel18Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabel19)
                        .add(alb_files_lbl))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(jPanel18Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabel20)
                        .add(alb_files_size_lbl))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(jPanel18Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabel61)
                        .add(jLabel64)))
            );

            org.jdesktop.layout.GroupLayout jPanel11Layout = new org.jdesktop.layout.GroupLayout(jPanel11);
            jPanel11.setLayout(jPanel11Layout);
            jPanel11Layout.setHorizontalGroup(
                jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(genButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
                .add(shuffleButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
                .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel18, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );
            jPanel11Layout.setVerticalGroup(
                jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel11Layout.createSequentialGroup()
                    .add(jPanel18, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 390, Short.MAX_VALUE)
                    .add(genButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(shuffleButton)
                    .addContainerGap())
            );

            org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
            jPanel3.setLayout(jPanel3Layout);
            jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .add(jPanel10, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(jPanel11, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
            );
            jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel10, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel11, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap())
            );

            jTabbedPane1.addTab("Album", jPanel3);

            destFolderText.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    destFolderTextActionPerformed(evt);
                }
            });

            destFolderButton.setText("...");
            destFolderButton.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    destFolderButtonActionPerformed(evt);
                }
            });

            jLabel62.setText("Destination folder:");

            copyButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/captain/northern/images/24/icon-packages.png"))); // NOI18N
            copyButton.setText("Copy");
            copyButton.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    copyButtonActionPerformed(evt);
                }
            });

            jMenu1.setText("File");

            jMenuNewCol.setIcon(new javax.swing.ImageIcon(getClass().getResource("/captain/northern/images/16/gtk-new.png"))); // NOI18N
            jMenuNewCol.setText("New Collection");
            jMenuNewCol.setToolTipText("Creates a new collection");
            jMenuNewCol.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    jMenuNewColActionPerformed(evt);
                }
            });
            jMenu1.add(jMenuNewCol);

            jMenuOpen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/captain/northern/images/16/gtk-open.png"))); // NOI18N
            jMenuOpen.setText("Open");
            jMenuOpen.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    jMenuOpenActionPerformed(evt);
                }
            });
            jMenu1.add(jMenuOpen);

            jMenuSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/captain/northern/images/16/gtk-save.png"))); // NOI18N
            jMenuSave.setText("Save");
            jMenuSave.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    jMenuSaveActionPerformed(evt);
                }
            });
            jMenu1.add(jMenuSave);
            jMenu1.add(jSeparator1);

            showLogWin.setText("Show Log window");
            showLogWin.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    showLogWinActionPerformed(evt);
                }
            });
            jMenu1.add(showLogWin);
            jMenu1.add(jSeparator2);

            jMenuExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/captain/northern/images/16/gnome-logout.png"))); // NOI18N
            jMenuExit.setText("Exit");
            jMenuExit.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    jMenuExitActionPerformed(evt);
                }
            });
            jMenu1.add(jMenuExit);

            jMenuBar1.add(jMenu1);

            setJMenuBar(jMenuBar1);

            org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                    .addContainerGap()
                    .add(jLabel62)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(destFolderText, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 724, Short.MAX_VALUE)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(destFolderButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 28, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(copyButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 97, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
                .add(jTabbedPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 997, Short.MAX_VALUE)
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                    .add(jTabbedPane1)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(destFolderButton)
                        .add(jLabel62)
                        .add(destFolderText, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(copyButton))
                    .addContainerGap())
            );

            pack();
        }// </editor-fold>//GEN-END:initComponents

    private void recentBut4ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_recentBut4ActionPerformed
    {//GEN-HEADEREND:event_recentBut4ActionPerformed
        loadSrcList(new File(opt.getRecentFileName(4)));
    }//GEN-LAST:event_recentBut4ActionPerformed

    private void recentBut3ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_recentBut3ActionPerformed
    {//GEN-HEADEREND:event_recentBut3ActionPerformed
        loadSrcList(new File(opt.getRecentFileName(3)));
    }//GEN-LAST:event_recentBut3ActionPerformed

    private void recentBut2ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_recentBut2ActionPerformed
    {//GEN-HEADEREND:event_recentBut2ActionPerformed
        loadSrcList(new File(opt.getRecentFileName(2)));
    }//GEN-LAST:event_recentBut2ActionPerformed

    private void recentBut1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_recentBut1ActionPerformed
    {//GEN-HEADEREND:event_recentBut1ActionPerformed
        loadSrcList(new File(opt.getRecentFileName(1)));
    }//GEN-LAST:event_recentBut1ActionPerformed

    private void recentBut0ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_recentBut0ActionPerformed
    {//GEN-HEADEREND:event_recentBut0ActionPerformed
        loadSrcList(new File(opt.getRecentFileName(0)));
    }//GEN-LAST:event_recentBut0ActionPerformed

    private void updateRecentList()
    {
        JButton rBut[] = {recentBut0, recentBut1, recentBut2, recentBut3, recentBut4 };
        
        for(int i=0;i<5;i++)
        {
            String s = opt.getRecentFileName(i);
            if(s.equals(""))
            {
                rBut[i].setText("none");
                rBut[i].setEnabled(false);
            } else
            {
                rBut[i].setText(s);
                rBut[i].setEnabled(true);
            }
        }
    }
    
    private void jMenuSaveActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuSaveActionPerformed
    {//GEN-HEADEREND:event_jMenuSaveActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setFileHidingEnabled(true);
        chooser.setMultiSelectionEnabled(false);
        chooser.setSelectedFile(opt.getLastSrcListFile());
        int returnVal = chooser.showSaveDialog(this.getParent());
        if(returnVal == JFileChooser.APPROVE_OPTION)
        {
            opt.dbg("You chose to save list to this file: " +
                    chooser.getSelectedFile().getAbsolutePath());
            opt.setLastSrcListFile(chooser.getSelectedFile());
            store.setSaveFile(opt.getLastSrcListFile());
            store.save();
            updateSrcInfo();
        }
    }//GEN-LAST:event_jMenuSaveActionPerformed

    private void jMenuNewColActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuNewColActionPerformed
    {//GEN-HEADEREND:event_jMenuNewColActionPerformed
        File oldf = opt.getLastSrcListFile();
        String newName = oldf.getParentFile().getAbsolutePath() + oldf.separator + "jmagList-";
        newName += Integer.toString(opt.getAlbumCount()) + ".mag";
        store.save();
        opt.setLastSrcListFile(new File(newName));
        store.cleanAll();
        store.setSaveFile(opt.getLastSrcListFile());
        opt.dbg("Created new empty source list: " + newName);
        updateSrcInfo();
        album.cleanAll();
        updateAlbInfo();
    }//GEN-LAST:event_jMenuNewColActionPerformed

    private void jMenuOpenActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuOpenActionPerformed
    {//GEN-HEADEREND:event_jMenuOpenActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setFileHidingEnabled(true);
        chooser.setMultiSelectionEnabled(false);
        chooser.setCurrentDirectory(opt.getLastSrcListFile().getParentFile().getAbsoluteFile());
        int returnVal = chooser.showOpenDialog(this.getParent());
        if(returnVal == JFileChooser.APPROVE_OPTION)
        {
            opt.dbg("You chose to open this list file: " +
                    chooser.getSelectedFile().getAbsolutePath());
            loadSrcList(chooser.getSelectedFile());
        }
    }//GEN-LAST:event_jMenuOpenActionPerformed

    static class CustomLabelGenerator
        implements PieSectionLabelGenerator
    {

        public String generateSectionLabel(PieDataset piedataset, Comparable comparable)
        {
            String s = null;
            s = comparable.toString();
            int val;
            val = piedataset.getValue(comparable).intValue();
            if(val <= 2)
                return null;
//            return s + " = " + Integer.toString(val) + "%";
            return s;
        }

        public AttributedString generateAttributedSectionLabel(PieDataset piedataset, Comparable comparable)
        {
            return null;
        }

        CustomLabelGenerator()
        {
        }
    }
 
    private JFreeChart createChart(PieDataset piedataset)
    {
        JFreeChart jfreechart = ChartFactory.createPieChart3D("", piedataset, true, true, false);
        TextTitle texttitle = jfreechart.getTitle();
        texttitle.setToolTipText("A title tooltip!");
        PiePlot3D pieplot = (PiePlot3D)jfreechart.getPlot();
        pieplot.setLabelFont(new Font("SansSerif", 0, 12));
        pieplot.setNoDataMessage("No data available");
        pieplot.setCircular(false);
        pieplot.setLabelGap(0.02D);
        pieplot.setStartAngle(290D);
        pieplot.setForegroundAlpha(0.8F);
        pieplot.setSectionPaint(0, opt.getCategoryColor(0));
        pieplot.setSectionPaint(1, opt.getCategoryColor(1));
        pieplot.setSectionPaint(2, opt.getCategoryColor(2));
        pieplot.setSectionPaint(3, opt.getCategoryColor(3));
        pieplot.setSectionPaint(4, opt.getCategoryColor(4));
        pieplot.setExplodePercent(0, 0.29999999999999999D);
        pieplot.setLabelGenerator(new CustomLabelGenerator()); 
        return jfreechart;
    }

    private void updateSrcPie()
    {
        long sz = store.getTotalSize();
        if(sz <= 0)
        {
            srcPie.setValue("Always", null);
            srcPie.setValue("One", null);
            srcPie.setValue("Two", null);
            srcPie.setValue("Three", null);
            srcPie.setValue("Four", null);
            srcPie.remove("Always");
            srcPie.remove("One");
            srcPie.remove("Two");
            srcPie.remove("Three");
            srcPie.remove("Four");
            return;
        }
        srcPie.setValue("Always", new Double(store.getCategoryInfo(0).size*100L/sz));
        srcPie.setValue("One", new Double(store.getCategoryInfo(1).size*100L/sz));
        srcPie.setValue("Two", new Double(store.getCategoryInfo(2).size*100L/sz));
        srcPie.setValue("Three", new Double(store.getCategoryInfo(3).size*100L/sz));
        srcPie.setValue("Four", new Double(store.getCategoryInfo(4).size*100L/sz));
    }
 
    private void updateAlbPie()
    {
        long sz = album.getTotalSize();
        if(sz <= 0)
        {
            albPie.setValue("Always", null);
            albPie.setValue("One", null);
            albPie.setValue("Two", null);
            albPie.setValue("Three", null);
            albPie.setValue("Four", null);
            albPie.remove("Always");
            albPie.remove("One");
            albPie.remove("Two");
            albPie.remove("Three");
            albPie.remove("Four");
            return;
        }
        albPie.setValue("Always", new Double(album.getCategoryInfo(0).size*100L/sz));
        albPie.setValue("One", new Double(album.getCategoryInfo(1).size*100L/sz));
        albPie.setValue("Two", new Double(album.getCategoryInfo(2).size*100L/sz));
        albPie.setValue("Three", new Double(album.getCategoryInfo(3).size*100L/sz));
        albPie.setValue("Four", new Double(album.getCategoryInfo(4).size*100L/sz));
    }
 
    public JPanel createChartPanel()
    {
        JFreeChart jfreechart = createChart(srcPie);
        return new ChartPanel(jfreechart);
    }

    public JPanel createAlbChartPanel()
    {
        JFreeChart jfreechart = createChart(albPie);
        return new ChartPanel(jfreechart);
    }
 
    private void showLogWinActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_showLogWinActionPerformed
    {//GEN-HEADEREND:event_showLogWinActionPerformed
        logwin.setVisible(true);
    }//GEN-LAST:event_showLogWinActionPerformed
    
    private void id3toFileButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_id3toFileButActionPerformed
        final int rows[] = src_files_tbl.getSelectedRows();
        
        ProgressMonitor progressMonitor = new ProgressMonitor(this.getParent(),
                "Generating file names from ID3 tags...",
                "", 0, 100);
        progressMonitor.setProgress(0);
        progressMonitor.setMillisToDecideToPopup(50);
        final JIProgressMeter meter = new JMSwingProgressMeter(progressMonitor, rows.length);
        final SwingWorker worker = new SwingWorker()
        {
            public Object construct()
            {
                store.ID3toFiles((String)ID3FCombo.getSelectedItem(), rows, meter);
                return null;
            }
            public void finished()
            {
                store.save();
                Toolkit.getDefaultToolkit().beep();
                meter.stop();
                updateSrcInfo();
            }
        };
        worker.start();
    }//GEN-LAST:event_id3toFileButActionPerformed
    
    private void file2ID3ButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_file2ID3ButActionPerformed
        final int rows[] = src_files_tbl.getSelectedRows();
        
        ProgressMonitor progressMonitor = new ProgressMonitor(this.getParent(),
                "Generating ID3 tags from file names...",
                "", 0, 100);
        progressMonitor.setProgress(0);
        progressMonitor.setMillisToDecideToPopup(50);
        final JIProgressMeter meter = new JMSwingProgressMeter(progressMonitor, rows.length);
        final SwingWorker worker = new SwingWorker()
        {
            public Object construct()
            {
                store.file2ID3Files(rows, meter);
                return null;
            }
            public void finished()
            {
                Toolkit.getDefaultToolkit().beep();
                meter.stop();
                updateSrcInfo();
            }
        };
        worker.start();
    }//GEN-LAST:event_file2ID3ButActionPerformed
    
    private void updateID3ButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateID3ButActionPerformed
        final int rows[] = src_files_tbl.getSelectedRows();
        
        ProgressMonitor progressMonitor = new ProgressMonitor(this.getParent(),
                "Updating ID3 tags...",
                "", 0, 100);
        progressMonitor.setProgress(0);
        progressMonitor.setMillisToDecideToPopup(50);
        final JIProgressMeter meter = new JMSwingProgressMeter(progressMonitor, rows.length);
        final SwingWorker worker = new SwingWorker()
        {
            public Object construct()
            {
                store.updateID3Files(rows, meter);
                return null;
            }
            public void finished()
            {
                Toolkit.getDefaultToolkit().beep();
                meter.stop();
                updateSrcInfo();
            }
        };
        worker.start();
        
    }//GEN-LAST:event_updateID3ButActionPerformed
    
    private void LoadID3ButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoadID3ButActionPerformed
        final int rows[] = src_files_tbl.getSelectedRows();
        
        ProgressMonitor progressMonitor = new ProgressMonitor(this.getParent(),
                "Loading ID3 tags...",
                "", 0, 100);
        progressMonitor.setProgress(0);
        progressMonitor.setMillisToDecideToPopup(50);
        final JIProgressMeter meter = new JMSwingProgressMeter(progressMonitor, rows.length);
        final SwingWorker worker = new SwingWorker()
        {
            public Object construct()
            {
                store.readID3Files(rows, meter);
                return null;
            }
            public void finished()
            {
                Toolkit.getDefaultToolkit().beep();
                meter.stop();
                updateSrcInfo();
            }
        };
        worker.start();
        
    }//GEN-LAST:event_LoadID3ButActionPerformed
    
    private void readID3CheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_readID3CheckActionPerformed
        opt.setReadID3(readID3Check.isSelected());
    }//GEN-LAST:event_readID3CheckActionPerformed
    
    private void copyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyButtonActionPerformed
        if(album.getNumFiles()<1)
        {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Error: Empty album list, nothing to copy\n" +
                    "Please, generate your album from source files first.\nChoose 'Album' tab and press 'Generate' button.",
                    "Empty album",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        ProgressMonitor progressMonitor = new ProgressMonitor(this.getParent(),
                "Creating album by copying files...",
                "", 0, 100);
        progressMonitor.setProgress(0);
        progressMonitor.setMillisToDecideToPopup(50);
        JIProgressMeter meter = new JMSwingProgressMeter(progressMonitor, album.getTotalSize());
        proc.copyAlbumFiles(this, album, store, opt.getDestFolder(), meter);
    }//GEN-LAST:event_copyButtonActionPerformed
    
    private void rndUsageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rndUsageButtonActionPerformed
        store.randomizeCountFiles(src_files_tbl.getSelectedRows());
        store.save();
        updateSrcInfo();
    }//GEN-LAST:event_rndUsageButtonActionPerformed
    
    private void usedPlusButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usedPlusButtonActionPerformed
        store.addCountFiles(src_files_tbl.getSelectedRows(), 1);
        store.save();
        updateSrcInfo();
    }//GEN-LAST:event_usedPlusButtonActionPerformed
    
    private void usedMinusButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usedMinusButtonActionPerformed
        store.addCountFiles(src_files_tbl.getSelectedRows(), -1);
        store.save();
        updateSrcInfo();
        
    }//GEN-LAST:event_usedMinusButtonActionPerformed
    
    private void alwaysUseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alwaysUseButtonActionPerformed
        store.setCountFiles(src_files_tbl.getSelectedRows(), -1);
        store.save();
        updateSrcInfo();
    }//GEN-LAST:event_alwaysUseButtonActionPerformed
    
    private void removeSrcButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeSrcButtonActionPerformed
        int rows[] = src_files_tbl.getSelectedRows();
        store.removeFiles(rows);
        store.save();
        updateSrcInfo();
        if(src_files_tbl.getRowCount()>0)
            src_files_tbl.removeRowSelectionInterval(0,src_files_tbl.getColumnCount()-1);
    }//GEN-LAST:event_removeSrcButtonActionPerformed
    
    private void filterMP3CheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filterMP3CheckActionPerformed
        opt.setFilterMP3(filterMP3Check.isSelected());
    }//GEN-LAST:event_filterMP3CheckActionPerformed
    
    private void AlbPrefixTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AlbPrefixTextActionPerformed
        opt.setAlbumPrefix(AlbPrefixText.getText());
        albumNameLbl.setText(opt.getAlbumName());
    }//GEN-LAST:event_AlbPrefixTextActionPerformed
    
    private void cleanDirButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cleanDirButtonActionPerformed
        File dir = opt.getDestFolder();
        //Custom button text
        Object[] options = {"Yes, please",
        "No, thanks"};
        
        int count = 0;
        int n = JOptionPane.showOptionDialog(this.getParent(),
                "Do you really want to\nDelete all files from destination folder?",
                "Delete files?",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]);
        if(n==0)
        {
            File[] files = dir.listFiles();
            for(int i=0;i<files.length;i++)
            {
                if(files[i].isFile())
                    files[i].delete();
                count++;
            }
            JOptionPane.showMessageDialog(this.getParent(),
                    "Total " + Integer.toString(count) +
                    " files were deleted.",
                    "Operation result",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_cleanDirButtonActionPerformed
    
    private void saveToID3CheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveToID3CheckActionPerformed
        opt.setSaveToID3(saveToID3Check.isSelected());
    }//GEN-LAST:event_saveToID3CheckActionPerformed
    
    private void convertID3CheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_convertID3CheckActionPerformed
        opt.setConvertID3(convertID3Check.isSelected());
    }//GEN-LAST:event_convertID3CheckActionPerformed
    
    private void convertNamesCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_convertNamesCheckActionPerformed
        opt.setConvertNames(convertNamesCheck.isSelected());
    }//GEN-LAST:event_convertNamesCheckActionPerformed
    
    private void addFldButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addFldButActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setFileHidingEnabled(true);
        chooser.setMultiSelectionEnabled(true);
        if(store.getNumFiles()>0)
        {
            chooser.setCurrentDirectory(store.getFiles().lastElement().getFile().getParentFile().getAbsoluteFile());
        }
        int returnVal = chooser.showOpenDialog(this.getParent());
        if(returnVal == JFileChooser.APPROVE_OPTION)
        {
            File flist[] = chooser.getSelectedFiles();
            for(int i=0; i<flist.length; i++)
                opt.dbg("You chose to open this file: " +
                        flist[i].getPath());
            store.addFiles(flist);
            store.save();
            updateSrcInfo();
        }
    }//GEN-LAST:event_addFldButActionPerformed
    
    private void randSubListsCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_randSubListsCheckActionPerformed
        opt.setSubListShuffle(randSubListsCheck.isSelected());
    }//GEN-LAST:event_randSubListsCheckActionPerformed
    
    private void divideCategoryCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_divideCategoryCheckActionPerformed
        opt.setDivideSubList(divideCategoryCheck.isSelected());
    }//GEN-LAST:event_divideCategoryCheckActionPerformed
    
    private void randResultCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_randResultCheckActionPerformed
        opt.setResultShuffle(randResultCheck.isSelected());
    }//GEN-LAST:event_randResultCheckActionPerformed
    
    private void dest_type_comboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dest_type_comboActionPerformed
        int idx = dest_type_combo.getSelectedIndex();
        opt.setDestSizeByIdx(idx);
    }//GEN-LAST:event_dest_type_comboActionPerformed
    
    private void destFolderTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_destFolderTextActionPerformed
        String path = destFolderText.getText();
        if(path == null || path.equals(""))
        {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Error: Empty destination folder!\nPlease, provide appropiate path.",
                    "Error setting destination folder",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        File file = new File(path);
        if(!file.exists())
        {
            //Custom button text
            Object[] options = {"Yes, please",
            "No, thanks"};
            
            int n = JOptionPane.showOptionDialog(this.getParent(),
                    "Destination folder does not exist\n"
                    + "Create it for you?",
                    "Create folder?",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);
            if(n==0)
            {
                if(!file.mkdirs())
                    JOptionPane.showMessageDialog(this.getParent(),
                            "Error: Creating destination folder\nPlease, check the path you have entered",
                            "Error creating destination folder",
                            JOptionPane.ERROR_MESSAGE);
            }
            
        }
        if(!file.exists())
        {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Error: Wrong destination folder!\nPlease, provide appropiate path, \n"
                    + "othewise old destination folder will be used",
                    "Error setting destination folder",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        opt.setDestFolder(file);
        destFolderText.setText(file.getAbsolutePath());
        destFullLabel.setText(opt.getDestFolder().getAbsolutePath());
    }//GEN-LAST:event_destFolderTextActionPerformed
    
    private void destFolderButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_destFolderButtonActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setFileHidingEnabled(true);
        chooser.setMultiSelectionEnabled(false);
        chooser.setDialogTitle("Choose destination folder");
        chooser.setCurrentDirectory(opt.getDestFolder().getAbsoluteFile());
        int returnVal = chooser.showOpenDialog(this.getParent());
        if(returnVal == JFileChooser.APPROVE_OPTION)
        {
            opt.setDestFolder(chooser.getSelectedFile());
            destFolderText.setText(opt.getDestFolder().getAbsolutePath());
            destFullLabel.setText(opt.getDestFolder().getAbsolutePath());
        }
    }//GEN-LAST:event_destFolderButtonActionPerformed
    
    
    void updateSrcInfo()
    {
//        src_files_tbl.setModel(store.getTableModel());
        src_files_tbl.repaint();
        src_files_tbl.revalidate();
        src_files_size_lbl.setText(opt.getSizeString(store.getTotalSize()));
        src_files_lbl.setText(Integer.toString(store.getNumFiles()));
        alb_src_files_lbl.setText(Integer.toString(store.getNumFiles()));
        alb_src_size_lbl.setText(opt.getSizeString(store.getTotalSize()));
        JMStore.JMFInfo inf = store.getCategoryInfo(0);
        
        mustBeFilesLbl.setText(Integer.toString(inf.nfiles));
        mustBeSizeLbl.setText(opt.getSizeString(inf.size));
        srcPathLbl.setText(opt.getLastSrcListFile().getAbsolutePath());
        updateSrcPie();
    }
    
    void updateAlbInfo()
    {
        alb_files_tbl.setModel(album.getTableModel());
        tuneGrid(alb_files_tbl);
        alb_files_tbl.repaint();
        alb_files_tbl.revalidate();
        alb_files_size_lbl.setText(opt.getSizeString(album.getTotalSize()));
        alb_files_lbl.setText(Integer.toString(album.getNumFiles()));
        alb_dest_size_lbl.setText(opt.getSizeString(album.getTotalSize()));
        alb_dest_files_lbl.setText(Integer.toString(album.getNumFiles()));
        updateAlbPie();
    }
    
    
    private void shuffleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shuffleButtonActionPerformed
        proc.shuffleAlbum(album);
        alb_files_tbl.repaint();
    }//GEN-LAST:event_shuffleButtonActionPerformed
    
    private void genButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_genButtonActionPerformed
        album = proc.generateAlbum(store);
        updateAlbInfo();
    }//GEN-LAST:event_genButtonActionPerformed
    
    private void addFilesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addFilesButtonActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setFileHidingEnabled(true);
        chooser.setMultiSelectionEnabled(true);
        if(store.getNumFiles()>0)
        {
            chooser.setCurrentDirectory(store.getFiles().lastElement().getFile().getParentFile().getAbsoluteFile());
        }
        int returnVal = chooser.showOpenDialog(this.getParent());
        if(returnVal == JFileChooser.APPROVE_OPTION)
        {
            File flist[] = chooser.getSelectedFiles();
            for(int i=0; i<flist.length; i++)
                opt.dbg("You chose to open this file: " +
                        flist[i].getPath() + " = " + flist[i].getParent());
            store.addFiles(flist);
            store.save();
            updateSrcInfo();
        }
        
    }//GEN-LAST:event_addFilesButtonActionPerformed
    
    private void jMenuExitActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuExitActionPerformed
    {//GEN-HEADEREND:event_jMenuExitActionPerformed
        java.lang.System.exit(0);
    }//GEN-LAST:event_jMenuExitActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        try
        {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception ex)
        {
            try
            {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            }
            catch (Exception ex2)
            {
            }
        }
        
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                JMagUI ui = new JMagUI();
                ui.setVisible(true);
                ui.loadSrcList(JMOptions.getOpt().getLastSrcListFile());
            }
        });
    }
    
    JMOptions               opt   = JMOptions.getOpt();
    JMStore                 store = new JMStore(opt);
    JMAlbum                 album = new JMAlbum(opt);
    JIAlbumProcessor        proc  = opt.getAlbumProcessor();
    JMLogWin                logwin = new JMLogWin();
    DefaultPieDataset       srcPie = new DefaultPieDataset();
    DefaultPieDataset       albPie = new DefaultPieDataset();
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JTextField AlbPrefixText;
    protected javax.swing.JComboBox ID3FCombo;
    protected javax.swing.JButton LoadID3But;
    protected javax.swing.JButton addFilesButton;
    protected javax.swing.JButton addFldBut;
    protected javax.swing.JLabel alb_dest_files_lbl;
    protected javax.swing.JLabel alb_dest_size_lbl;
    protected javax.swing.JLabel alb_files_lbl;
    protected javax.swing.JLabel alb_files_size_lbl;
    protected javax.swing.JTable alb_files_tbl;
    protected javax.swing.JLabel alb_src_files_lbl;
    protected javax.swing.JLabel alb_src_size_lbl;
    protected javax.swing.JLabel alb_src_time_lbl;
    protected javax.swing.JLabel albumNameLbl;
    protected javax.swing.JButton alwaysUseButton;
    protected javax.swing.JButton cleanDirButton;
    protected javax.swing.JCheckBox convertID3Check;
    protected javax.swing.JCheckBox convertNamesCheck;
    protected javax.swing.JButton copyButton;
    protected javax.swing.JButton destFolderButton;
    protected javax.swing.JTextField destFolderText;
    protected javax.swing.JLabel destFullLabel;
    protected javax.swing.JComboBox dest_type_combo;
    protected javax.swing.JCheckBox divideCategoryCheck;
    protected javax.swing.JButton file2ID3But;
    protected javax.swing.JCheckBox filterMP3Check;
    protected javax.swing.JButton genButton;
    protected javax.swing.JButton id3toFileBut;
    protected javax.swing.JButton jButton1;
    protected javax.swing.JLabel jLabel1;
    protected javax.swing.JLabel jLabel10;
    protected javax.swing.JLabel jLabel11;
    protected javax.swing.JLabel jLabel12;
    protected javax.swing.JLabel jLabel13;
    protected javax.swing.JLabel jLabel14;
    protected javax.swing.JLabel jLabel15;
    protected javax.swing.JLabel jLabel16;
    protected javax.swing.JLabel jLabel17;
    protected javax.swing.JLabel jLabel18;
    protected javax.swing.JLabel jLabel19;
    protected javax.swing.JLabel jLabel2;
    protected javax.swing.JLabel jLabel20;
    protected javax.swing.JLabel jLabel21;
    protected javax.swing.JLabel jLabel24;
    protected javax.swing.JLabel jLabel25;
    protected javax.swing.JLabel jLabel3;
    protected javax.swing.JLabel jLabel4;
    protected javax.swing.JLabel jLabel5;
    protected javax.swing.JLabel jLabel6;
    protected javax.swing.JLabel jLabel61;
    protected javax.swing.JLabel jLabel62;
    protected javax.swing.JLabel jLabel64;
    protected javax.swing.JLabel jLabel7;
    protected javax.swing.JLabel jLabel8;
    protected javax.swing.JLabel jLabel9;
    protected javax.swing.JMenu jMenu1;
    protected javax.swing.JMenuBar jMenuBar1;
    protected javax.swing.JMenuItem jMenuExit;
    protected javax.swing.JMenuItem jMenuNewCol;
    protected javax.swing.JMenuItem jMenuOpen;
    protected javax.swing.JMenuItem jMenuSave;
    protected javax.swing.JPanel jPanel1;
    protected javax.swing.JPanel jPanel10;
    protected javax.swing.JPanel jPanel11;
    protected javax.swing.JPanel jPanel12;
    protected javax.swing.JPanel jPanel13;
    protected javax.swing.JPanel jPanel14;
    protected javax.swing.JPanel jPanel15;
    protected javax.swing.JPanel jPanel16;
    protected javax.swing.JPanel jPanel18;
    protected javax.swing.JPanel jPanel2;
    protected javax.swing.JPanel jPanel3;
    protected javax.swing.JPanel jPanel4;
    protected javax.swing.JPanel jPanel5;
    protected javax.swing.JPanel jPanel6;
    protected javax.swing.JPanel jPanel7;
    protected javax.swing.JPanel jPanel8;
    protected javax.swing.JPanel jPanel9;
    protected javax.swing.JScrollPane jScrollPane1;
    protected javax.swing.JScrollPane jScrollPane2;
    protected javax.swing.JSeparator jSeparator1;
    protected javax.swing.JSeparator jSeparator2;
    protected javax.swing.JTabbedPane jTabbedPane1;
    protected javax.swing.JLabel mustBeFilesLbl;
    protected javax.swing.JLabel mustBeSizeLbl;
    protected javax.swing.JCheckBox randResultCheck;
    protected javax.swing.JCheckBox randSubListsCheck;
    protected javax.swing.JCheckBox readID3Check;
    protected javax.swing.JButton recentBut0;
    protected javax.swing.JButton recentBut1;
    protected javax.swing.JButton recentBut2;
    protected javax.swing.JButton recentBut3;
    protected javax.swing.JButton recentBut4;
    protected javax.swing.JButton removeSrcButton;
    protected javax.swing.JButton rndUsageButton;
    protected javax.swing.JCheckBox saveToID3Check;
    protected javax.swing.JMenuItem showLogWin;
    protected javax.swing.JButton shuffleButton;
    protected javax.swing.JPanel srcChartPanel;
    protected javax.swing.JLabel srcPathLbl;
    protected javax.swing.JLabel src_files_lbl;
    protected javax.swing.JLabel src_files_size_lbl;
    protected javax.swing.JTable src_files_tbl;
    protected javax.swing.JButton updateID3But;
    protected javax.swing.JButton usedMinusButton;
    protected javax.swing.JButton usedPlusButton;
    // End of variables declaration//GEN-END:variables
    
}
