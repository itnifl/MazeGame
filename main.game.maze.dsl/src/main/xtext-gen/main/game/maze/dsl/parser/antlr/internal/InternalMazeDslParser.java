package main.game.maze.dsl.parser.antlr.internal;

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import main.game.maze.dsl.services.MazeDslGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalMazeDslParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_STRING", "RULE_INT", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'game'", "'{'", "'}'", "'import'", "'difficulty'", "'level'", "'instantDeath'", "'true'", "'speedMultiplier'", "'damageMultiplier'", "'maxThreat'", "'limit'", "'max'", "'opponent'", "'type'", "'displayName'", "'health'", "'speed'", "'threatLevel'", "'enabled'", "'behavior'", "'patrol'", "'loot'", "'zombie-stats'", "'attackDamage'", "'infectionLevel'", "'resurrectionTime'", "'ghost-stats'", "'visibilityLevel'", "'nonTangibilityEnergy'", "'ranged-stats'", "'attackRange'", "'attackCooldown'", "'projectileSpeed'", "'projectileType'", "'splashRadius'", "'visionRange'", "'path'", "'['", "','", "']'", "'zone'", "'topLeft'", "'('", "')'", "'width'", "'height'", "':'", "'ms'", "'loot-table'", "'capacity'", "'item'", "'value'", "'weight'", "'basedOn'", "'threatCapacity'", "'wallMaterials'", "'inheritWeapons'", "'cascadeDepth'", "'ring'", "'share'", "'materials'", "'sergeant'", "'sightRange'", "'rounds'", "'roundDamage'", "'roundInterval'", "'burstCooldown'", "'sprite'", "'fireSound'", "'drops'", "'postDistance'", "'sergeants'", "'mines'", "'count'", "'ringRadius'", "'triggerRadius'", "'pulse'", "'blastRadius'", "'blastDamage'", "'playerDamage'", "'holeRadius'", "'portalGhost'", "'portalGhosts'", "'teleport'", "'cooldown'", "'banner'", "'bannerSeconds'", "'zapSound'", "'voiceSound'", "'-'", "'.'", "'false'", "'easy'", "'normal'", "'hard'", "'zombie'", "'ghost'", "'pumpkinbomber'", "'passive'", "'wander'", "'aggressive'", "'straight'", "'lob'", "'beam'", "'food'", "'bomb'", "'trap'", "'weapon'", "'gatling'"
    };
    public static final int T__50=50;
    public static final int T__59=59;
    public static final int T__55=55;
    public static final int T__56=56;
    public static final int T__57=57;
    public static final int T__58=58;
    public static final int T__51=51;
    public static final int T__52=52;
    public static final int T__53=53;
    public static final int T__54=54;
    public static final int T__60=60;
    public static final int T__61=61;
    public static final int RULE_ID=4;
    public static final int RULE_INT=6;
    public static final int T__66=66;
    public static final int RULE_ML_COMMENT=7;
    public static final int T__67=67;
    public static final int T__68=68;
    public static final int T__69=69;
    public static final int T__62=62;
    public static final int T__63=63;
    public static final int T__64=64;
    public static final int T__65=65;
    public static final int T__37=37;
    public static final int T__38=38;
    public static final int T__39=39;
    public static final int T__33=33;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int T__48=48;
    public static final int T__49=49;
    public static final int T__44=44;
    public static final int T__45=45;
    public static final int T__46=46;
    public static final int T__47=47;
    public static final int T__40=40;
    public static final int T__41=41;
    public static final int T__42=42;
    public static final int T__43=43;
    public static final int T__91=91;
    public static final int T__100=100;
    public static final int T__92=92;
    public static final int T__93=93;
    public static final int T__102=102;
    public static final int T__94=94;
    public static final int T__101=101;
    public static final int T__90=90;
    public static final int T__19=19;
    public static final int T__15=15;
    public static final int T__16=16;
    public static final int T__17=17;
    public static final int T__18=18;
    public static final int T__11=11;
    public static final int T__99=99;
    public static final int T__12=12;
    public static final int T__13=13;
    public static final int T__14=14;
    public static final int T__95=95;
    public static final int T__96=96;
    public static final int T__97=97;
    public static final int T__98=98;
    public static final int T__26=26;
    public static final int T__27=27;
    public static final int T__28=28;
    public static final int T__29=29;
    public static final int T__22=22;
    public static final int T__23=23;
    public static final int T__24=24;
    public static final int T__25=25;
    public static final int T__20=20;
    public static final int T__21=21;
    public static final int T__70=70;
    public static final int T__71=71;
    public static final int T__72=72;
    public static final int T__120=120;
    public static final int RULE_STRING=5;
    public static final int RULE_SL_COMMENT=8;
    public static final int T__77=77;
    public static final int T__119=119;
    public static final int T__78=78;
    public static final int T__118=118;
    public static final int T__79=79;
    public static final int T__73=73;
    public static final int T__115=115;
    public static final int EOF=-1;
    public static final int T__74=74;
    public static final int T__114=114;
    public static final int T__75=75;
    public static final int T__117=117;
    public static final int T__76=76;
    public static final int T__116=116;
    public static final int T__80=80;
    public static final int T__111=111;
    public static final int T__81=81;
    public static final int T__110=110;
    public static final int T__82=82;
    public static final int T__113=113;
    public static final int T__83=83;
    public static final int T__112=112;
    public static final int RULE_WS=9;
    public static final int RULE_ANY_OTHER=10;
    public static final int T__88=88;
    public static final int T__108=108;
    public static final int T__89=89;
    public static final int T__107=107;
    public static final int T__109=109;
    public static final int T__84=84;
    public static final int T__104=104;
    public static final int T__85=85;
    public static final int T__103=103;
    public static final int T__86=86;
    public static final int T__106=106;
    public static final int T__87=87;
    public static final int T__105=105;

    // delegates
    // delegators


        public InternalMazeDslParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalMazeDslParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalMazeDslParser.tokenNames; }
    public String getGrammarFileName() { return "InternalMazeDsl.g"; }



     	private MazeDslGrammarAccess grammarAccess;

        public InternalMazeDslParser(TokenStream input, MazeDslGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }

        @Override
        protected String getFirstRuleName() {
        	return "GameConfiguration";
       	}

       	@Override
       	protected MazeDslGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}




    // $ANTLR start "entryRuleGameConfiguration"
    // InternalMazeDsl.g:65:1: entryRuleGameConfiguration returns [EObject current=null] : iv_ruleGameConfiguration= ruleGameConfiguration EOF ;
    public final EObject entryRuleGameConfiguration() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGameConfiguration = null;


        try {
            // InternalMazeDsl.g:65:58: (iv_ruleGameConfiguration= ruleGameConfiguration EOF )
            // InternalMazeDsl.g:66:2: iv_ruleGameConfiguration= ruleGameConfiguration EOF
            {
             newCompositeNode(grammarAccess.getGameConfigurationRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleGameConfiguration=ruleGameConfiguration();

            state._fsp--;

             current =iv_ruleGameConfiguration; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleGameConfiguration"


    // $ANTLR start "ruleGameConfiguration"
    // InternalMazeDsl.g:72:1: ruleGameConfiguration returns [EObject current=null] : (otherlv_0= 'game' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_imports_3_0= ruleImport ) )* ( (lv_difficulty_4_0= ruleDifficultyConfig ) )? ( (lv_opponents_5_0= ruleOpponentConfig ) )* ( (lv_patrols_6_0= rulePatrolConfig ) )* ( (lv_lootTables_7_0= ruleLootTableConfig ) )* ( (lv_levels_8_0= ruleLevelConfig ) )* otherlv_9= '}' ) ;
    public final EObject ruleGameConfiguration() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_9=null;
        EObject lv_imports_3_0 = null;

        EObject lv_difficulty_4_0 = null;

        EObject lv_opponents_5_0 = null;

        EObject lv_patrols_6_0 = null;

        EObject lv_lootTables_7_0 = null;

        EObject lv_levels_8_0 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:78:2: ( (otherlv_0= 'game' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_imports_3_0= ruleImport ) )* ( (lv_difficulty_4_0= ruleDifficultyConfig ) )? ( (lv_opponents_5_0= ruleOpponentConfig ) )* ( (lv_patrols_6_0= rulePatrolConfig ) )* ( (lv_lootTables_7_0= ruleLootTableConfig ) )* ( (lv_levels_8_0= ruleLevelConfig ) )* otherlv_9= '}' ) )
            // InternalMazeDsl.g:79:2: (otherlv_0= 'game' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_imports_3_0= ruleImport ) )* ( (lv_difficulty_4_0= ruleDifficultyConfig ) )? ( (lv_opponents_5_0= ruleOpponentConfig ) )* ( (lv_patrols_6_0= rulePatrolConfig ) )* ( (lv_lootTables_7_0= ruleLootTableConfig ) )* ( (lv_levels_8_0= ruleLevelConfig ) )* otherlv_9= '}' )
            {
            // InternalMazeDsl.g:79:2: (otherlv_0= 'game' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_imports_3_0= ruleImport ) )* ( (lv_difficulty_4_0= ruleDifficultyConfig ) )? ( (lv_opponents_5_0= ruleOpponentConfig ) )* ( (lv_patrols_6_0= rulePatrolConfig ) )* ( (lv_lootTables_7_0= ruleLootTableConfig ) )* ( (lv_levels_8_0= ruleLevelConfig ) )* otherlv_9= '}' )
            // InternalMazeDsl.g:80:3: otherlv_0= 'game' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_imports_3_0= ruleImport ) )* ( (lv_difficulty_4_0= ruleDifficultyConfig ) )? ( (lv_opponents_5_0= ruleOpponentConfig ) )* ( (lv_patrols_6_0= rulePatrolConfig ) )* ( (lv_lootTables_7_0= ruleLootTableConfig ) )* ( (lv_levels_8_0= ruleLevelConfig ) )* otherlv_9= '}'
            {
            otherlv_0=(Token)match(input,11,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getGameConfigurationAccess().getGameKeyword_0());
            		
            // InternalMazeDsl.g:84:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalMazeDsl.g:85:4: (lv_name_1_0= RULE_ID )
            {
            // InternalMazeDsl.g:85:4: (lv_name_1_0= RULE_ID )
            // InternalMazeDsl.g:86:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_4); 

            					newLeafNode(lv_name_1_0, grammarAccess.getGameConfigurationAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getGameConfigurationRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            otherlv_2=(Token)match(input,12,FOLLOW_5); 

            			newLeafNode(otherlv_2, grammarAccess.getGameConfigurationAccess().getLeftCurlyBracketKeyword_2());
            		
            // InternalMazeDsl.g:106:3: ( (lv_imports_3_0= ruleImport ) )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==14) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalMazeDsl.g:107:4: (lv_imports_3_0= ruleImport )
            	    {
            	    // InternalMazeDsl.g:107:4: (lv_imports_3_0= ruleImport )
            	    // InternalMazeDsl.g:108:5: lv_imports_3_0= ruleImport
            	    {

            	    					newCompositeNode(grammarAccess.getGameConfigurationAccess().getImportsImportParserRuleCall_3_0());
            	    				
            	    pushFollow(FOLLOW_5);
            	    lv_imports_3_0=ruleImport();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getGameConfigurationRule());
            	    					}
            	    					add(
            	    						current,
            	    						"imports",
            	    						lv_imports_3_0,
            	    						"main.game.maze.dsl.MazeDsl.Import");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            // InternalMazeDsl.g:125:3: ( (lv_difficulty_4_0= ruleDifficultyConfig ) )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==15) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // InternalMazeDsl.g:126:4: (lv_difficulty_4_0= ruleDifficultyConfig )
                    {
                    // InternalMazeDsl.g:126:4: (lv_difficulty_4_0= ruleDifficultyConfig )
                    // InternalMazeDsl.g:127:5: lv_difficulty_4_0= ruleDifficultyConfig
                    {

                    					newCompositeNode(grammarAccess.getGameConfigurationAccess().getDifficultyDifficultyConfigParserRuleCall_4_0());
                    				
                    pushFollow(FOLLOW_6);
                    lv_difficulty_4_0=ruleDifficultyConfig();

                    state._fsp--;


                    					if (current==null) {
                    						current = createModelElementForParent(grammarAccess.getGameConfigurationRule());
                    					}
                    					set(
                    						current,
                    						"difficulty",
                    						lv_difficulty_4_0,
                    						"main.game.maze.dsl.MazeDsl.DifficultyConfig");
                    					afterParserOrEnumRuleCall();
                    				

                    }


                    }
                    break;

            }

            // InternalMazeDsl.g:144:3: ( (lv_opponents_5_0= ruleOpponentConfig ) )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==24) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // InternalMazeDsl.g:145:4: (lv_opponents_5_0= ruleOpponentConfig )
            	    {
            	    // InternalMazeDsl.g:145:4: (lv_opponents_5_0= ruleOpponentConfig )
            	    // InternalMazeDsl.g:146:5: lv_opponents_5_0= ruleOpponentConfig
            	    {

            	    					newCompositeNode(grammarAccess.getGameConfigurationAccess().getOpponentsOpponentConfigParserRuleCall_5_0());
            	    				
            	    pushFollow(FOLLOW_6);
            	    lv_opponents_5_0=ruleOpponentConfig();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getGameConfigurationRule());
            	    					}
            	    					add(
            	    						current,
            	    						"opponents",
            	    						lv_opponents_5_0,
            	    						"main.game.maze.dsl.MazeDsl.OpponentConfig");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);

            // InternalMazeDsl.g:163:3: ( (lv_patrols_6_0= rulePatrolConfig ) )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==32) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // InternalMazeDsl.g:164:4: (lv_patrols_6_0= rulePatrolConfig )
            	    {
            	    // InternalMazeDsl.g:164:4: (lv_patrols_6_0= rulePatrolConfig )
            	    // InternalMazeDsl.g:165:5: lv_patrols_6_0= rulePatrolConfig
            	    {

            	    					newCompositeNode(grammarAccess.getGameConfigurationAccess().getPatrolsPatrolConfigParserRuleCall_6_0());
            	    				
            	    pushFollow(FOLLOW_7);
            	    lv_patrols_6_0=rulePatrolConfig();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getGameConfigurationRule());
            	    					}
            	    					add(
            	    						current,
            	    						"patrols",
            	    						lv_patrols_6_0,
            	    						"main.game.maze.dsl.MazeDsl.PatrolConfig");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);

            // InternalMazeDsl.g:182:3: ( (lv_lootTables_7_0= ruleLootTableConfig ) )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==60) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // InternalMazeDsl.g:183:4: (lv_lootTables_7_0= ruleLootTableConfig )
            	    {
            	    // InternalMazeDsl.g:183:4: (lv_lootTables_7_0= ruleLootTableConfig )
            	    // InternalMazeDsl.g:184:5: lv_lootTables_7_0= ruleLootTableConfig
            	    {

            	    					newCompositeNode(grammarAccess.getGameConfigurationAccess().getLootTablesLootTableConfigParserRuleCall_7_0());
            	    				
            	    pushFollow(FOLLOW_8);
            	    lv_lootTables_7_0=ruleLootTableConfig();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getGameConfigurationRule());
            	    					}
            	    					add(
            	    						current,
            	    						"lootTables",
            	    						lv_lootTables_7_0,
            	    						"main.game.maze.dsl.MazeDsl.LootTableConfig");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

            // InternalMazeDsl.g:201:3: ( (lv_levels_8_0= ruleLevelConfig ) )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==16) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // InternalMazeDsl.g:202:4: (lv_levels_8_0= ruleLevelConfig )
            	    {
            	    // InternalMazeDsl.g:202:4: (lv_levels_8_0= ruleLevelConfig )
            	    // InternalMazeDsl.g:203:5: lv_levels_8_0= ruleLevelConfig
            	    {

            	    					newCompositeNode(grammarAccess.getGameConfigurationAccess().getLevelsLevelConfigParserRuleCall_8_0());
            	    				
            	    pushFollow(FOLLOW_9);
            	    lv_levels_8_0=ruleLevelConfig();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getGameConfigurationRule());
            	    					}
            	    					add(
            	    						current,
            	    						"levels",
            	    						lv_levels_8_0,
            	    						"main.game.maze.dsl.MazeDsl.LevelConfig");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);

            otherlv_9=(Token)match(input,13,FOLLOW_2); 

            			newLeafNode(otherlv_9, grammarAccess.getGameConfigurationAccess().getRightCurlyBracketKeyword_9());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleGameConfiguration"


    // $ANTLR start "entryRuleImport"
    // InternalMazeDsl.g:228:1: entryRuleImport returns [EObject current=null] : iv_ruleImport= ruleImport EOF ;
    public final EObject entryRuleImport() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleImport = null;


        try {
            // InternalMazeDsl.g:228:47: (iv_ruleImport= ruleImport EOF )
            // InternalMazeDsl.g:229:2: iv_ruleImport= ruleImport EOF
            {
             newCompositeNode(grammarAccess.getImportRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleImport=ruleImport();

            state._fsp--;

             current =iv_ruleImport; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleImport"


    // $ANTLR start "ruleImport"
    // InternalMazeDsl.g:235:1: ruleImport returns [EObject current=null] : (otherlv_0= 'import' ( (lv_importURI_1_0= RULE_STRING ) ) ) ;
    public final EObject ruleImport() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_importURI_1_0=null;


        	enterRule();

        try {
            // InternalMazeDsl.g:241:2: ( (otherlv_0= 'import' ( (lv_importURI_1_0= RULE_STRING ) ) ) )
            // InternalMazeDsl.g:242:2: (otherlv_0= 'import' ( (lv_importURI_1_0= RULE_STRING ) ) )
            {
            // InternalMazeDsl.g:242:2: (otherlv_0= 'import' ( (lv_importURI_1_0= RULE_STRING ) ) )
            // InternalMazeDsl.g:243:3: otherlv_0= 'import' ( (lv_importURI_1_0= RULE_STRING ) )
            {
            otherlv_0=(Token)match(input,14,FOLLOW_10); 

            			newLeafNode(otherlv_0, grammarAccess.getImportAccess().getImportKeyword_0());
            		
            // InternalMazeDsl.g:247:3: ( (lv_importURI_1_0= RULE_STRING ) )
            // InternalMazeDsl.g:248:4: (lv_importURI_1_0= RULE_STRING )
            {
            // InternalMazeDsl.g:248:4: (lv_importURI_1_0= RULE_STRING )
            // InternalMazeDsl.g:249:5: lv_importURI_1_0= RULE_STRING
            {
            lv_importURI_1_0=(Token)match(input,RULE_STRING,FOLLOW_2); 

            					newLeafNode(lv_importURI_1_0, grammarAccess.getImportAccess().getImportURISTRINGTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getImportRule());
            					}
            					setWithLastConsumed(
            						current,
            						"importURI",
            						lv_importURI_1_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleImport"


    // $ANTLR start "entryRuleDifficultyConfig"
    // InternalMazeDsl.g:269:1: entryRuleDifficultyConfig returns [EObject current=null] : iv_ruleDifficultyConfig= ruleDifficultyConfig EOF ;
    public final EObject entryRuleDifficultyConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDifficultyConfig = null;


        try {
            // InternalMazeDsl.g:269:57: (iv_ruleDifficultyConfig= ruleDifficultyConfig EOF )
            // InternalMazeDsl.g:270:2: iv_ruleDifficultyConfig= ruleDifficultyConfig EOF
            {
             newCompositeNode(grammarAccess.getDifficultyConfigRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleDifficultyConfig=ruleDifficultyConfig();

            state._fsp--;

             current =iv_ruleDifficultyConfig; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleDifficultyConfig"


    // $ANTLR start "ruleDifficultyConfig"
    // InternalMazeDsl.g:276:1: ruleDifficultyConfig returns [EObject current=null] : (otherlv_0= 'difficulty' otherlv_1= '{' otherlv_2= 'level' ( (lv_level_3_0= ruleDifficultyLevel ) ) (otherlv_4= 'instantDeath' ( (lv_instantDeath_5_0= 'true' ) ) )? (otherlv_6= 'speedMultiplier' ( (lv_speedMultiplier_7_0= ruleDOUBLE ) ) )? (otherlv_8= 'damageMultiplier' ( (lv_damageMultiplier_9_0= ruleDOUBLE ) ) )? (otherlv_10= 'maxThreat' ( (lv_maxThreat_11_0= ruleSIGNED_INT ) ) )? ( (lv_enemyLimits_12_0= ruleEnemyLimit ) )* otherlv_13= '}' ) ;
    public final EObject ruleDifficultyConfig() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token lv_instantDeath_5_0=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        Token otherlv_10=null;
        Token otherlv_13=null;
        Enumerator lv_level_3_0 = null;

        AntlrDatatypeRuleToken lv_speedMultiplier_7_0 = null;

        AntlrDatatypeRuleToken lv_damageMultiplier_9_0 = null;

        AntlrDatatypeRuleToken lv_maxThreat_11_0 = null;

        EObject lv_enemyLimits_12_0 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:282:2: ( (otherlv_0= 'difficulty' otherlv_1= '{' otherlv_2= 'level' ( (lv_level_3_0= ruleDifficultyLevel ) ) (otherlv_4= 'instantDeath' ( (lv_instantDeath_5_0= 'true' ) ) )? (otherlv_6= 'speedMultiplier' ( (lv_speedMultiplier_7_0= ruleDOUBLE ) ) )? (otherlv_8= 'damageMultiplier' ( (lv_damageMultiplier_9_0= ruleDOUBLE ) ) )? (otherlv_10= 'maxThreat' ( (lv_maxThreat_11_0= ruleSIGNED_INT ) ) )? ( (lv_enemyLimits_12_0= ruleEnemyLimit ) )* otherlv_13= '}' ) )
            // InternalMazeDsl.g:283:2: (otherlv_0= 'difficulty' otherlv_1= '{' otherlv_2= 'level' ( (lv_level_3_0= ruleDifficultyLevel ) ) (otherlv_4= 'instantDeath' ( (lv_instantDeath_5_0= 'true' ) ) )? (otherlv_6= 'speedMultiplier' ( (lv_speedMultiplier_7_0= ruleDOUBLE ) ) )? (otherlv_8= 'damageMultiplier' ( (lv_damageMultiplier_9_0= ruleDOUBLE ) ) )? (otherlv_10= 'maxThreat' ( (lv_maxThreat_11_0= ruleSIGNED_INT ) ) )? ( (lv_enemyLimits_12_0= ruleEnemyLimit ) )* otherlv_13= '}' )
            {
            // InternalMazeDsl.g:283:2: (otherlv_0= 'difficulty' otherlv_1= '{' otherlv_2= 'level' ( (lv_level_3_0= ruleDifficultyLevel ) ) (otherlv_4= 'instantDeath' ( (lv_instantDeath_5_0= 'true' ) ) )? (otherlv_6= 'speedMultiplier' ( (lv_speedMultiplier_7_0= ruleDOUBLE ) ) )? (otherlv_8= 'damageMultiplier' ( (lv_damageMultiplier_9_0= ruleDOUBLE ) ) )? (otherlv_10= 'maxThreat' ( (lv_maxThreat_11_0= ruleSIGNED_INT ) ) )? ( (lv_enemyLimits_12_0= ruleEnemyLimit ) )* otherlv_13= '}' )
            // InternalMazeDsl.g:284:3: otherlv_0= 'difficulty' otherlv_1= '{' otherlv_2= 'level' ( (lv_level_3_0= ruleDifficultyLevel ) ) (otherlv_4= 'instantDeath' ( (lv_instantDeath_5_0= 'true' ) ) )? (otherlv_6= 'speedMultiplier' ( (lv_speedMultiplier_7_0= ruleDOUBLE ) ) )? (otherlv_8= 'damageMultiplier' ( (lv_damageMultiplier_9_0= ruleDOUBLE ) ) )? (otherlv_10= 'maxThreat' ( (lv_maxThreat_11_0= ruleSIGNED_INT ) ) )? ( (lv_enemyLimits_12_0= ruleEnemyLimit ) )* otherlv_13= '}'
            {
            otherlv_0=(Token)match(input,15,FOLLOW_4); 

            			newLeafNode(otherlv_0, grammarAccess.getDifficultyConfigAccess().getDifficultyKeyword_0());
            		
            otherlv_1=(Token)match(input,12,FOLLOW_11); 

            			newLeafNode(otherlv_1, grammarAccess.getDifficultyConfigAccess().getLeftCurlyBracketKeyword_1());
            		
            otherlv_2=(Token)match(input,16,FOLLOW_12); 

            			newLeafNode(otherlv_2, grammarAccess.getDifficultyConfigAccess().getLevelKeyword_2());
            		
            // InternalMazeDsl.g:296:3: ( (lv_level_3_0= ruleDifficultyLevel ) )
            // InternalMazeDsl.g:297:4: (lv_level_3_0= ruleDifficultyLevel )
            {
            // InternalMazeDsl.g:297:4: (lv_level_3_0= ruleDifficultyLevel )
            // InternalMazeDsl.g:298:5: lv_level_3_0= ruleDifficultyLevel
            {

            					newCompositeNode(grammarAccess.getDifficultyConfigAccess().getLevelDifficultyLevelEnumRuleCall_3_0());
            				
            pushFollow(FOLLOW_13);
            lv_level_3_0=ruleDifficultyLevel();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getDifficultyConfigRule());
            					}
            					set(
            						current,
            						"level",
            						lv_level_3_0,
            						"main.game.maze.dsl.MazeDsl.DifficultyLevel");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalMazeDsl.g:315:3: (otherlv_4= 'instantDeath' ( (lv_instantDeath_5_0= 'true' ) ) )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==17) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // InternalMazeDsl.g:316:4: otherlv_4= 'instantDeath' ( (lv_instantDeath_5_0= 'true' ) )
                    {
                    otherlv_4=(Token)match(input,17,FOLLOW_14); 

                    				newLeafNode(otherlv_4, grammarAccess.getDifficultyConfigAccess().getInstantDeathKeyword_4_0());
                    			
                    // InternalMazeDsl.g:320:4: ( (lv_instantDeath_5_0= 'true' ) )
                    // InternalMazeDsl.g:321:5: (lv_instantDeath_5_0= 'true' )
                    {
                    // InternalMazeDsl.g:321:5: (lv_instantDeath_5_0= 'true' )
                    // InternalMazeDsl.g:322:6: lv_instantDeath_5_0= 'true'
                    {
                    lv_instantDeath_5_0=(Token)match(input,18,FOLLOW_15); 

                    						newLeafNode(lv_instantDeath_5_0, grammarAccess.getDifficultyConfigAccess().getInstantDeathTrueKeyword_4_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getDifficultyConfigRule());
                    						}
                    						setWithLastConsumed(current, "instantDeath", lv_instantDeath_5_0 != null, "true");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeDsl.g:335:3: (otherlv_6= 'speedMultiplier' ( (lv_speedMultiplier_7_0= ruleDOUBLE ) ) )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==19) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // InternalMazeDsl.g:336:4: otherlv_6= 'speedMultiplier' ( (lv_speedMultiplier_7_0= ruleDOUBLE ) )
                    {
                    otherlv_6=(Token)match(input,19,FOLLOW_16); 

                    				newLeafNode(otherlv_6, grammarAccess.getDifficultyConfigAccess().getSpeedMultiplierKeyword_5_0());
                    			
                    // InternalMazeDsl.g:340:4: ( (lv_speedMultiplier_7_0= ruleDOUBLE ) )
                    // InternalMazeDsl.g:341:5: (lv_speedMultiplier_7_0= ruleDOUBLE )
                    {
                    // InternalMazeDsl.g:341:5: (lv_speedMultiplier_7_0= ruleDOUBLE )
                    // InternalMazeDsl.g:342:6: lv_speedMultiplier_7_0= ruleDOUBLE
                    {

                    						newCompositeNode(grammarAccess.getDifficultyConfigAccess().getSpeedMultiplierDOUBLEParserRuleCall_5_1_0());
                    					
                    pushFollow(FOLLOW_17);
                    lv_speedMultiplier_7_0=ruleDOUBLE();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getDifficultyConfigRule());
                    						}
                    						set(
                    							current,
                    							"speedMultiplier",
                    							lv_speedMultiplier_7_0,
                    							"main.game.maze.dsl.MazeDsl.DOUBLE");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeDsl.g:360:3: (otherlv_8= 'damageMultiplier' ( (lv_damageMultiplier_9_0= ruleDOUBLE ) ) )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==20) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // InternalMazeDsl.g:361:4: otherlv_8= 'damageMultiplier' ( (lv_damageMultiplier_9_0= ruleDOUBLE ) )
                    {
                    otherlv_8=(Token)match(input,20,FOLLOW_16); 

                    				newLeafNode(otherlv_8, grammarAccess.getDifficultyConfigAccess().getDamageMultiplierKeyword_6_0());
                    			
                    // InternalMazeDsl.g:365:4: ( (lv_damageMultiplier_9_0= ruleDOUBLE ) )
                    // InternalMazeDsl.g:366:5: (lv_damageMultiplier_9_0= ruleDOUBLE )
                    {
                    // InternalMazeDsl.g:366:5: (lv_damageMultiplier_9_0= ruleDOUBLE )
                    // InternalMazeDsl.g:367:6: lv_damageMultiplier_9_0= ruleDOUBLE
                    {

                    						newCompositeNode(grammarAccess.getDifficultyConfigAccess().getDamageMultiplierDOUBLEParserRuleCall_6_1_0());
                    					
                    pushFollow(FOLLOW_18);
                    lv_damageMultiplier_9_0=ruleDOUBLE();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getDifficultyConfigRule());
                    						}
                    						set(
                    							current,
                    							"damageMultiplier",
                    							lv_damageMultiplier_9_0,
                    							"main.game.maze.dsl.MazeDsl.DOUBLE");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeDsl.g:385:3: (otherlv_10= 'maxThreat' ( (lv_maxThreat_11_0= ruleSIGNED_INT ) ) )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==21) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // InternalMazeDsl.g:386:4: otherlv_10= 'maxThreat' ( (lv_maxThreat_11_0= ruleSIGNED_INT ) )
                    {
                    otherlv_10=(Token)match(input,21,FOLLOW_16); 

                    				newLeafNode(otherlv_10, grammarAccess.getDifficultyConfigAccess().getMaxThreatKeyword_7_0());
                    			
                    // InternalMazeDsl.g:390:4: ( (lv_maxThreat_11_0= ruleSIGNED_INT ) )
                    // InternalMazeDsl.g:391:5: (lv_maxThreat_11_0= ruleSIGNED_INT )
                    {
                    // InternalMazeDsl.g:391:5: (lv_maxThreat_11_0= ruleSIGNED_INT )
                    // InternalMazeDsl.g:392:6: lv_maxThreat_11_0= ruleSIGNED_INT
                    {

                    						newCompositeNode(grammarAccess.getDifficultyConfigAccess().getMaxThreatSIGNED_INTParserRuleCall_7_1_0());
                    					
                    pushFollow(FOLLOW_19);
                    lv_maxThreat_11_0=ruleSIGNED_INT();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getDifficultyConfigRule());
                    						}
                    						set(
                    							current,
                    							"maxThreat",
                    							lv_maxThreat_11_0,
                    							"main.game.maze.dsl.MazeDsl.SIGNED_INT");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeDsl.g:410:3: ( (lv_enemyLimits_12_0= ruleEnemyLimit ) )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==22) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // InternalMazeDsl.g:411:4: (lv_enemyLimits_12_0= ruleEnemyLimit )
            	    {
            	    // InternalMazeDsl.g:411:4: (lv_enemyLimits_12_0= ruleEnemyLimit )
            	    // InternalMazeDsl.g:412:5: lv_enemyLimits_12_0= ruleEnemyLimit
            	    {

            	    					newCompositeNode(grammarAccess.getDifficultyConfigAccess().getEnemyLimitsEnemyLimitParserRuleCall_8_0());
            	    				
            	    pushFollow(FOLLOW_19);
            	    lv_enemyLimits_12_0=ruleEnemyLimit();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getDifficultyConfigRule());
            	    					}
            	    					add(
            	    						current,
            	    						"enemyLimits",
            	    						lv_enemyLimits_12_0,
            	    						"main.game.maze.dsl.MazeDsl.EnemyLimit");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);

            otherlv_13=(Token)match(input,13,FOLLOW_2); 

            			newLeafNode(otherlv_13, grammarAccess.getDifficultyConfigAccess().getRightCurlyBracketKeyword_9());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDifficultyConfig"


    // $ANTLR start "entryRuleEnemyLimit"
    // InternalMazeDsl.g:437:1: entryRuleEnemyLimit returns [EObject current=null] : iv_ruleEnemyLimit= ruleEnemyLimit EOF ;
    public final EObject entryRuleEnemyLimit() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEnemyLimit = null;


        try {
            // InternalMazeDsl.g:437:51: (iv_ruleEnemyLimit= ruleEnemyLimit EOF )
            // InternalMazeDsl.g:438:2: iv_ruleEnemyLimit= ruleEnemyLimit EOF
            {
             newCompositeNode(grammarAccess.getEnemyLimitRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleEnemyLimit=ruleEnemyLimit();

            state._fsp--;

             current =iv_ruleEnemyLimit; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleEnemyLimit"


    // $ANTLR start "ruleEnemyLimit"
    // InternalMazeDsl.g:444:1: ruleEnemyLimit returns [EObject current=null] : (otherlv_0= 'limit' ( (lv_type_1_0= ruleEnemyType ) ) otherlv_2= 'max' ( (lv_maxCount_3_0= ruleSIGNED_INT ) ) ) ;
    public final EObject ruleEnemyLimit() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Enumerator lv_type_1_0 = null;

        AntlrDatatypeRuleToken lv_maxCount_3_0 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:450:2: ( (otherlv_0= 'limit' ( (lv_type_1_0= ruleEnemyType ) ) otherlv_2= 'max' ( (lv_maxCount_3_0= ruleSIGNED_INT ) ) ) )
            // InternalMazeDsl.g:451:2: (otherlv_0= 'limit' ( (lv_type_1_0= ruleEnemyType ) ) otherlv_2= 'max' ( (lv_maxCount_3_0= ruleSIGNED_INT ) ) )
            {
            // InternalMazeDsl.g:451:2: (otherlv_0= 'limit' ( (lv_type_1_0= ruleEnemyType ) ) otherlv_2= 'max' ( (lv_maxCount_3_0= ruleSIGNED_INT ) ) )
            // InternalMazeDsl.g:452:3: otherlv_0= 'limit' ( (lv_type_1_0= ruleEnemyType ) ) otherlv_2= 'max' ( (lv_maxCount_3_0= ruleSIGNED_INT ) )
            {
            otherlv_0=(Token)match(input,22,FOLLOW_20); 

            			newLeafNode(otherlv_0, grammarAccess.getEnemyLimitAccess().getLimitKeyword_0());
            		
            // InternalMazeDsl.g:456:3: ( (lv_type_1_0= ruleEnemyType ) )
            // InternalMazeDsl.g:457:4: (lv_type_1_0= ruleEnemyType )
            {
            // InternalMazeDsl.g:457:4: (lv_type_1_0= ruleEnemyType )
            // InternalMazeDsl.g:458:5: lv_type_1_0= ruleEnemyType
            {

            					newCompositeNode(grammarAccess.getEnemyLimitAccess().getTypeEnemyTypeEnumRuleCall_1_0());
            				
            pushFollow(FOLLOW_21);
            lv_type_1_0=ruleEnemyType();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getEnemyLimitRule());
            					}
            					set(
            						current,
            						"type",
            						lv_type_1_0,
            						"main.game.maze.dsl.MazeDsl.EnemyType");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_2=(Token)match(input,23,FOLLOW_16); 

            			newLeafNode(otherlv_2, grammarAccess.getEnemyLimitAccess().getMaxKeyword_2());
            		
            // InternalMazeDsl.g:479:3: ( (lv_maxCount_3_0= ruleSIGNED_INT ) )
            // InternalMazeDsl.g:480:4: (lv_maxCount_3_0= ruleSIGNED_INT )
            {
            // InternalMazeDsl.g:480:4: (lv_maxCount_3_0= ruleSIGNED_INT )
            // InternalMazeDsl.g:481:5: lv_maxCount_3_0= ruleSIGNED_INT
            {

            					newCompositeNode(grammarAccess.getEnemyLimitAccess().getMaxCountSIGNED_INTParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_2);
            lv_maxCount_3_0=ruleSIGNED_INT();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getEnemyLimitRule());
            					}
            					set(
            						current,
            						"maxCount",
            						lv_maxCount_3_0,
            						"main.game.maze.dsl.MazeDsl.SIGNED_INT");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleEnemyLimit"


    // $ANTLR start "entryRuleOpponentConfig"
    // InternalMazeDsl.g:502:1: entryRuleOpponentConfig returns [EObject current=null] : iv_ruleOpponentConfig= ruleOpponentConfig EOF ;
    public final EObject entryRuleOpponentConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOpponentConfig = null;


        try {
            // InternalMazeDsl.g:502:55: (iv_ruleOpponentConfig= ruleOpponentConfig EOF )
            // InternalMazeDsl.g:503:2: iv_ruleOpponentConfig= ruleOpponentConfig EOF
            {
             newCompositeNode(grammarAccess.getOpponentConfigRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleOpponentConfig=ruleOpponentConfig();

            state._fsp--;

             current =iv_ruleOpponentConfig; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleOpponentConfig"


    // $ANTLR start "ruleOpponentConfig"
    // InternalMazeDsl.g:509:1: ruleOpponentConfig returns [EObject current=null] : (otherlv_0= 'opponent' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'type' ( (lv_type_4_0= ruleCharacterTypeEnum ) ) (otherlv_5= 'displayName' ( (lv_displayName_6_0= RULE_STRING ) ) )? (otherlv_7= 'health' ( (lv_health_8_0= ruleSIGNED_INT ) ) )? (otherlv_9= 'speed' ( (lv_speed_10_0= ruleDOUBLE ) ) )? (otherlv_11= 'threatLevel' ( (lv_threatLevel_12_0= ruleDOUBLE ) ) )? (otherlv_13= 'enabled' ( (lv_enabled_14_0= ruleBOOLEAN ) ) )? (otherlv_15= 'behavior' ( (lv_behavior_16_0= ruleBehaviorTypeEnum ) ) )? ( (lv_characterSpecifics_17_0= ruleCharacterSpecifics ) )? (otherlv_18= 'patrol' ( (otherlv_19= RULE_ID ) ) )? (otherlv_20= 'loot' ( (otherlv_21= RULE_ID ) ) )? otherlv_22= '}' ) ;
    public final EObject ruleOpponentConfig() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token lv_displayName_6_0=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        Token otherlv_11=null;
        Token otherlv_13=null;
        Token otherlv_15=null;
        Token otherlv_18=null;
        Token otherlv_19=null;
        Token otherlv_20=null;
        Token otherlv_21=null;
        Token otherlv_22=null;
        Enumerator lv_type_4_0 = null;

        AntlrDatatypeRuleToken lv_health_8_0 = null;

        AntlrDatatypeRuleToken lv_speed_10_0 = null;

        AntlrDatatypeRuleToken lv_threatLevel_12_0 = null;

        AntlrDatatypeRuleToken lv_enabled_14_0 = null;

        Enumerator lv_behavior_16_0 = null;

        EObject lv_characterSpecifics_17_0 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:515:2: ( (otherlv_0= 'opponent' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'type' ( (lv_type_4_0= ruleCharacterTypeEnum ) ) (otherlv_5= 'displayName' ( (lv_displayName_6_0= RULE_STRING ) ) )? (otherlv_7= 'health' ( (lv_health_8_0= ruleSIGNED_INT ) ) )? (otherlv_9= 'speed' ( (lv_speed_10_0= ruleDOUBLE ) ) )? (otherlv_11= 'threatLevel' ( (lv_threatLevel_12_0= ruleDOUBLE ) ) )? (otherlv_13= 'enabled' ( (lv_enabled_14_0= ruleBOOLEAN ) ) )? (otherlv_15= 'behavior' ( (lv_behavior_16_0= ruleBehaviorTypeEnum ) ) )? ( (lv_characterSpecifics_17_0= ruleCharacterSpecifics ) )? (otherlv_18= 'patrol' ( (otherlv_19= RULE_ID ) ) )? (otherlv_20= 'loot' ( (otherlv_21= RULE_ID ) ) )? otherlv_22= '}' ) )
            // InternalMazeDsl.g:516:2: (otherlv_0= 'opponent' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'type' ( (lv_type_4_0= ruleCharacterTypeEnum ) ) (otherlv_5= 'displayName' ( (lv_displayName_6_0= RULE_STRING ) ) )? (otherlv_7= 'health' ( (lv_health_8_0= ruleSIGNED_INT ) ) )? (otherlv_9= 'speed' ( (lv_speed_10_0= ruleDOUBLE ) ) )? (otherlv_11= 'threatLevel' ( (lv_threatLevel_12_0= ruleDOUBLE ) ) )? (otherlv_13= 'enabled' ( (lv_enabled_14_0= ruleBOOLEAN ) ) )? (otherlv_15= 'behavior' ( (lv_behavior_16_0= ruleBehaviorTypeEnum ) ) )? ( (lv_characterSpecifics_17_0= ruleCharacterSpecifics ) )? (otherlv_18= 'patrol' ( (otherlv_19= RULE_ID ) ) )? (otherlv_20= 'loot' ( (otherlv_21= RULE_ID ) ) )? otherlv_22= '}' )
            {
            // InternalMazeDsl.g:516:2: (otherlv_0= 'opponent' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'type' ( (lv_type_4_0= ruleCharacterTypeEnum ) ) (otherlv_5= 'displayName' ( (lv_displayName_6_0= RULE_STRING ) ) )? (otherlv_7= 'health' ( (lv_health_8_0= ruleSIGNED_INT ) ) )? (otherlv_9= 'speed' ( (lv_speed_10_0= ruleDOUBLE ) ) )? (otherlv_11= 'threatLevel' ( (lv_threatLevel_12_0= ruleDOUBLE ) ) )? (otherlv_13= 'enabled' ( (lv_enabled_14_0= ruleBOOLEAN ) ) )? (otherlv_15= 'behavior' ( (lv_behavior_16_0= ruleBehaviorTypeEnum ) ) )? ( (lv_characterSpecifics_17_0= ruleCharacterSpecifics ) )? (otherlv_18= 'patrol' ( (otherlv_19= RULE_ID ) ) )? (otherlv_20= 'loot' ( (otherlv_21= RULE_ID ) ) )? otherlv_22= '}' )
            // InternalMazeDsl.g:517:3: otherlv_0= 'opponent' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'type' ( (lv_type_4_0= ruleCharacterTypeEnum ) ) (otherlv_5= 'displayName' ( (lv_displayName_6_0= RULE_STRING ) ) )? (otherlv_7= 'health' ( (lv_health_8_0= ruleSIGNED_INT ) ) )? (otherlv_9= 'speed' ( (lv_speed_10_0= ruleDOUBLE ) ) )? (otherlv_11= 'threatLevel' ( (lv_threatLevel_12_0= ruleDOUBLE ) ) )? (otherlv_13= 'enabled' ( (lv_enabled_14_0= ruleBOOLEAN ) ) )? (otherlv_15= 'behavior' ( (lv_behavior_16_0= ruleBehaviorTypeEnum ) ) )? ( (lv_characterSpecifics_17_0= ruleCharacterSpecifics ) )? (otherlv_18= 'patrol' ( (otherlv_19= RULE_ID ) ) )? (otherlv_20= 'loot' ( (otherlv_21= RULE_ID ) ) )? otherlv_22= '}'
            {
            otherlv_0=(Token)match(input,24,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getOpponentConfigAccess().getOpponentKeyword_0());
            		
            // InternalMazeDsl.g:521:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalMazeDsl.g:522:4: (lv_name_1_0= RULE_ID )
            {
            // InternalMazeDsl.g:522:4: (lv_name_1_0= RULE_ID )
            // InternalMazeDsl.g:523:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_4); 

            					newLeafNode(lv_name_1_0, grammarAccess.getOpponentConfigAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getOpponentConfigRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            otherlv_2=(Token)match(input,12,FOLLOW_22); 

            			newLeafNode(otherlv_2, grammarAccess.getOpponentConfigAccess().getLeftCurlyBracketKeyword_2());
            		
            otherlv_3=(Token)match(input,25,FOLLOW_20); 

            			newLeafNode(otherlv_3, grammarAccess.getOpponentConfigAccess().getTypeKeyword_3());
            		
            // InternalMazeDsl.g:547:3: ( (lv_type_4_0= ruleCharacterTypeEnum ) )
            // InternalMazeDsl.g:548:4: (lv_type_4_0= ruleCharacterTypeEnum )
            {
            // InternalMazeDsl.g:548:4: (lv_type_4_0= ruleCharacterTypeEnum )
            // InternalMazeDsl.g:549:5: lv_type_4_0= ruleCharacterTypeEnum
            {

            					newCompositeNode(grammarAccess.getOpponentConfigAccess().getTypeCharacterTypeEnumEnumRuleCall_4_0());
            				
            pushFollow(FOLLOW_23);
            lv_type_4_0=ruleCharacterTypeEnum();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getOpponentConfigRule());
            					}
            					set(
            						current,
            						"type",
            						lv_type_4_0,
            						"main.game.maze.dsl.MazeDsl.CharacterTypeEnum");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalMazeDsl.g:566:3: (otherlv_5= 'displayName' ( (lv_displayName_6_0= RULE_STRING ) ) )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==26) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // InternalMazeDsl.g:567:4: otherlv_5= 'displayName' ( (lv_displayName_6_0= RULE_STRING ) )
                    {
                    otherlv_5=(Token)match(input,26,FOLLOW_10); 

                    				newLeafNode(otherlv_5, grammarAccess.getOpponentConfigAccess().getDisplayNameKeyword_5_0());
                    			
                    // InternalMazeDsl.g:571:4: ( (lv_displayName_6_0= RULE_STRING ) )
                    // InternalMazeDsl.g:572:5: (lv_displayName_6_0= RULE_STRING )
                    {
                    // InternalMazeDsl.g:572:5: (lv_displayName_6_0= RULE_STRING )
                    // InternalMazeDsl.g:573:6: lv_displayName_6_0= RULE_STRING
                    {
                    lv_displayName_6_0=(Token)match(input,RULE_STRING,FOLLOW_24); 

                    						newLeafNode(lv_displayName_6_0, grammarAccess.getOpponentConfigAccess().getDisplayNameSTRINGTerminalRuleCall_5_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getOpponentConfigRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"displayName",
                    							lv_displayName_6_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeDsl.g:590:3: (otherlv_7= 'health' ( (lv_health_8_0= ruleSIGNED_INT ) ) )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==27) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // InternalMazeDsl.g:591:4: otherlv_7= 'health' ( (lv_health_8_0= ruleSIGNED_INT ) )
                    {
                    otherlv_7=(Token)match(input,27,FOLLOW_16); 

                    				newLeafNode(otherlv_7, grammarAccess.getOpponentConfigAccess().getHealthKeyword_6_0());
                    			
                    // InternalMazeDsl.g:595:4: ( (lv_health_8_0= ruleSIGNED_INT ) )
                    // InternalMazeDsl.g:596:5: (lv_health_8_0= ruleSIGNED_INT )
                    {
                    // InternalMazeDsl.g:596:5: (lv_health_8_0= ruleSIGNED_INT )
                    // InternalMazeDsl.g:597:6: lv_health_8_0= ruleSIGNED_INT
                    {

                    						newCompositeNode(grammarAccess.getOpponentConfigAccess().getHealthSIGNED_INTParserRuleCall_6_1_0());
                    					
                    pushFollow(FOLLOW_25);
                    lv_health_8_0=ruleSIGNED_INT();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getOpponentConfigRule());
                    						}
                    						set(
                    							current,
                    							"health",
                    							lv_health_8_0,
                    							"main.game.maze.dsl.MazeDsl.SIGNED_INT");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeDsl.g:615:3: (otherlv_9= 'speed' ( (lv_speed_10_0= ruleDOUBLE ) ) )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==28) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // InternalMazeDsl.g:616:4: otherlv_9= 'speed' ( (lv_speed_10_0= ruleDOUBLE ) )
                    {
                    otherlv_9=(Token)match(input,28,FOLLOW_16); 

                    				newLeafNode(otherlv_9, grammarAccess.getOpponentConfigAccess().getSpeedKeyword_7_0());
                    			
                    // InternalMazeDsl.g:620:4: ( (lv_speed_10_0= ruleDOUBLE ) )
                    // InternalMazeDsl.g:621:5: (lv_speed_10_0= ruleDOUBLE )
                    {
                    // InternalMazeDsl.g:621:5: (lv_speed_10_0= ruleDOUBLE )
                    // InternalMazeDsl.g:622:6: lv_speed_10_0= ruleDOUBLE
                    {

                    						newCompositeNode(grammarAccess.getOpponentConfigAccess().getSpeedDOUBLEParserRuleCall_7_1_0());
                    					
                    pushFollow(FOLLOW_26);
                    lv_speed_10_0=ruleDOUBLE();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getOpponentConfigRule());
                    						}
                    						set(
                    							current,
                    							"speed",
                    							lv_speed_10_0,
                    							"main.game.maze.dsl.MazeDsl.DOUBLE");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeDsl.g:640:3: (otherlv_11= 'threatLevel' ( (lv_threatLevel_12_0= ruleDOUBLE ) ) )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==29) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // InternalMazeDsl.g:641:4: otherlv_11= 'threatLevel' ( (lv_threatLevel_12_0= ruleDOUBLE ) )
                    {
                    otherlv_11=(Token)match(input,29,FOLLOW_16); 

                    				newLeafNode(otherlv_11, grammarAccess.getOpponentConfigAccess().getThreatLevelKeyword_8_0());
                    			
                    // InternalMazeDsl.g:645:4: ( (lv_threatLevel_12_0= ruleDOUBLE ) )
                    // InternalMazeDsl.g:646:5: (lv_threatLevel_12_0= ruleDOUBLE )
                    {
                    // InternalMazeDsl.g:646:5: (lv_threatLevel_12_0= ruleDOUBLE )
                    // InternalMazeDsl.g:647:6: lv_threatLevel_12_0= ruleDOUBLE
                    {

                    						newCompositeNode(grammarAccess.getOpponentConfigAccess().getThreatLevelDOUBLEParserRuleCall_8_1_0());
                    					
                    pushFollow(FOLLOW_27);
                    lv_threatLevel_12_0=ruleDOUBLE();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getOpponentConfigRule());
                    						}
                    						set(
                    							current,
                    							"threatLevel",
                    							lv_threatLevel_12_0,
                    							"main.game.maze.dsl.MazeDsl.DOUBLE");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeDsl.g:665:3: (otherlv_13= 'enabled' ( (lv_enabled_14_0= ruleBOOLEAN ) ) )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==30) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // InternalMazeDsl.g:666:4: otherlv_13= 'enabled' ( (lv_enabled_14_0= ruleBOOLEAN ) )
                    {
                    otherlv_13=(Token)match(input,30,FOLLOW_28); 

                    				newLeafNode(otherlv_13, grammarAccess.getOpponentConfigAccess().getEnabledKeyword_9_0());
                    			
                    // InternalMazeDsl.g:670:4: ( (lv_enabled_14_0= ruleBOOLEAN ) )
                    // InternalMazeDsl.g:671:5: (lv_enabled_14_0= ruleBOOLEAN )
                    {
                    // InternalMazeDsl.g:671:5: (lv_enabled_14_0= ruleBOOLEAN )
                    // InternalMazeDsl.g:672:6: lv_enabled_14_0= ruleBOOLEAN
                    {

                    						newCompositeNode(grammarAccess.getOpponentConfigAccess().getEnabledBOOLEANParserRuleCall_9_1_0());
                    					
                    pushFollow(FOLLOW_29);
                    lv_enabled_14_0=ruleBOOLEAN();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getOpponentConfigRule());
                    						}
                    						set(
                    							current,
                    							"enabled",
                    							lv_enabled_14_0,
                    							"main.game.maze.dsl.MazeDsl.BOOLEAN");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeDsl.g:690:3: (otherlv_15= 'behavior' ( (lv_behavior_16_0= ruleBehaviorTypeEnum ) ) )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==31) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // InternalMazeDsl.g:691:4: otherlv_15= 'behavior' ( (lv_behavior_16_0= ruleBehaviorTypeEnum ) )
                    {
                    otherlv_15=(Token)match(input,31,FOLLOW_30); 

                    				newLeafNode(otherlv_15, grammarAccess.getOpponentConfigAccess().getBehaviorKeyword_10_0());
                    			
                    // InternalMazeDsl.g:695:4: ( (lv_behavior_16_0= ruleBehaviorTypeEnum ) )
                    // InternalMazeDsl.g:696:5: (lv_behavior_16_0= ruleBehaviorTypeEnum )
                    {
                    // InternalMazeDsl.g:696:5: (lv_behavior_16_0= ruleBehaviorTypeEnum )
                    // InternalMazeDsl.g:697:6: lv_behavior_16_0= ruleBehaviorTypeEnum
                    {

                    						newCompositeNode(grammarAccess.getOpponentConfigAccess().getBehaviorBehaviorTypeEnumEnumRuleCall_10_1_0());
                    					
                    pushFollow(FOLLOW_31);
                    lv_behavior_16_0=ruleBehaviorTypeEnum();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getOpponentConfigRule());
                    						}
                    						set(
                    							current,
                    							"behavior",
                    							lv_behavior_16_0,
                    							"main.game.maze.dsl.MazeDsl.BehaviorTypeEnum");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeDsl.g:715:3: ( (lv_characterSpecifics_17_0= ruleCharacterSpecifics ) )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==34||LA18_0==38||LA18_0==41) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // InternalMazeDsl.g:716:4: (lv_characterSpecifics_17_0= ruleCharacterSpecifics )
                    {
                    // InternalMazeDsl.g:716:4: (lv_characterSpecifics_17_0= ruleCharacterSpecifics )
                    // InternalMazeDsl.g:717:5: lv_characterSpecifics_17_0= ruleCharacterSpecifics
                    {

                    					newCompositeNode(grammarAccess.getOpponentConfigAccess().getCharacterSpecificsCharacterSpecificsParserRuleCall_11_0());
                    				
                    pushFollow(FOLLOW_32);
                    lv_characterSpecifics_17_0=ruleCharacterSpecifics();

                    state._fsp--;


                    					if (current==null) {
                    						current = createModelElementForParent(grammarAccess.getOpponentConfigRule());
                    					}
                    					set(
                    						current,
                    						"characterSpecifics",
                    						lv_characterSpecifics_17_0,
                    						"main.game.maze.dsl.MazeDsl.CharacterSpecifics");
                    					afterParserOrEnumRuleCall();
                    				

                    }


                    }
                    break;

            }

            // InternalMazeDsl.g:734:3: (otherlv_18= 'patrol' ( (otherlv_19= RULE_ID ) ) )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==32) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // InternalMazeDsl.g:735:4: otherlv_18= 'patrol' ( (otherlv_19= RULE_ID ) )
                    {
                    otherlv_18=(Token)match(input,32,FOLLOW_3); 

                    				newLeafNode(otherlv_18, grammarAccess.getOpponentConfigAccess().getPatrolKeyword_12_0());
                    			
                    // InternalMazeDsl.g:739:4: ( (otherlv_19= RULE_ID ) )
                    // InternalMazeDsl.g:740:5: (otherlv_19= RULE_ID )
                    {
                    // InternalMazeDsl.g:740:5: (otherlv_19= RULE_ID )
                    // InternalMazeDsl.g:741:6: otherlv_19= RULE_ID
                    {

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getOpponentConfigRule());
                    						}
                    					
                    otherlv_19=(Token)match(input,RULE_ID,FOLLOW_33); 

                    						newLeafNode(otherlv_19, grammarAccess.getOpponentConfigAccess().getPatrolRefPatrolConfigCrossReference_12_1_0());
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeDsl.g:753:3: (otherlv_20= 'loot' ( (otherlv_21= RULE_ID ) ) )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==33) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // InternalMazeDsl.g:754:4: otherlv_20= 'loot' ( (otherlv_21= RULE_ID ) )
                    {
                    otherlv_20=(Token)match(input,33,FOLLOW_3); 

                    				newLeafNode(otherlv_20, grammarAccess.getOpponentConfigAccess().getLootKeyword_13_0());
                    			
                    // InternalMazeDsl.g:758:4: ( (otherlv_21= RULE_ID ) )
                    // InternalMazeDsl.g:759:5: (otherlv_21= RULE_ID )
                    {
                    // InternalMazeDsl.g:759:5: (otherlv_21= RULE_ID )
                    // InternalMazeDsl.g:760:6: otherlv_21= RULE_ID
                    {

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getOpponentConfigRule());
                    						}
                    					
                    otherlv_21=(Token)match(input,RULE_ID,FOLLOW_34); 

                    						newLeafNode(otherlv_21, grammarAccess.getOpponentConfigAccess().getLootRefLootTableConfigCrossReference_13_1_0());
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_22=(Token)match(input,13,FOLLOW_2); 

            			newLeafNode(otherlv_22, grammarAccess.getOpponentConfigAccess().getRightCurlyBracketKeyword_14());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOpponentConfig"


    // $ANTLR start "entryRuleCharacterSpecifics"
    // InternalMazeDsl.g:780:1: entryRuleCharacterSpecifics returns [EObject current=null] : iv_ruleCharacterSpecifics= ruleCharacterSpecifics EOF ;
    public final EObject entryRuleCharacterSpecifics() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCharacterSpecifics = null;


        try {
            // InternalMazeDsl.g:780:59: (iv_ruleCharacterSpecifics= ruleCharacterSpecifics EOF )
            // InternalMazeDsl.g:781:2: iv_ruleCharacterSpecifics= ruleCharacterSpecifics EOF
            {
             newCompositeNode(grammarAccess.getCharacterSpecificsRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleCharacterSpecifics=ruleCharacterSpecifics();

            state._fsp--;

             current =iv_ruleCharacterSpecifics; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleCharacterSpecifics"


    // $ANTLR start "ruleCharacterSpecifics"
    // InternalMazeDsl.g:787:1: ruleCharacterSpecifics returns [EObject current=null] : (this_ZombieSpecifics_0= ruleZombieSpecifics | this_GhostSpecifics_1= ruleGhostSpecifics | this_RangedSpecifics_2= ruleRangedSpecifics ) ;
    public final EObject ruleCharacterSpecifics() throws RecognitionException {
        EObject current = null;

        EObject this_ZombieSpecifics_0 = null;

        EObject this_GhostSpecifics_1 = null;

        EObject this_RangedSpecifics_2 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:793:2: ( (this_ZombieSpecifics_0= ruleZombieSpecifics | this_GhostSpecifics_1= ruleGhostSpecifics | this_RangedSpecifics_2= ruleRangedSpecifics ) )
            // InternalMazeDsl.g:794:2: (this_ZombieSpecifics_0= ruleZombieSpecifics | this_GhostSpecifics_1= ruleGhostSpecifics | this_RangedSpecifics_2= ruleRangedSpecifics )
            {
            // InternalMazeDsl.g:794:2: (this_ZombieSpecifics_0= ruleZombieSpecifics | this_GhostSpecifics_1= ruleGhostSpecifics | this_RangedSpecifics_2= ruleRangedSpecifics )
            int alt21=3;
            switch ( input.LA(1) ) {
            case 34:
                {
                alt21=1;
                }
                break;
            case 38:
                {
                alt21=2;
                }
                break;
            case 41:
                {
                alt21=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 21, 0, input);

                throw nvae;
            }

            switch (alt21) {
                case 1 :
                    // InternalMazeDsl.g:795:3: this_ZombieSpecifics_0= ruleZombieSpecifics
                    {

                    			newCompositeNode(grammarAccess.getCharacterSpecificsAccess().getZombieSpecificsParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_ZombieSpecifics_0=ruleZombieSpecifics();

                    state._fsp--;


                    			current = this_ZombieSpecifics_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:804:3: this_GhostSpecifics_1= ruleGhostSpecifics
                    {

                    			newCompositeNode(grammarAccess.getCharacterSpecificsAccess().getGhostSpecificsParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_GhostSpecifics_1=ruleGhostSpecifics();

                    state._fsp--;


                    			current = this_GhostSpecifics_1;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 3 :
                    // InternalMazeDsl.g:813:3: this_RangedSpecifics_2= ruleRangedSpecifics
                    {

                    			newCompositeNode(grammarAccess.getCharacterSpecificsAccess().getRangedSpecificsParserRuleCall_2());
                    		
                    pushFollow(FOLLOW_2);
                    this_RangedSpecifics_2=ruleRangedSpecifics();

                    state._fsp--;


                    			current = this_RangedSpecifics_2;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleCharacterSpecifics"


    // $ANTLR start "entryRuleZombieSpecifics"
    // InternalMazeDsl.g:825:1: entryRuleZombieSpecifics returns [EObject current=null] : iv_ruleZombieSpecifics= ruleZombieSpecifics EOF ;
    public final EObject entryRuleZombieSpecifics() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleZombieSpecifics = null;


        try {
            // InternalMazeDsl.g:825:56: (iv_ruleZombieSpecifics= ruleZombieSpecifics EOF )
            // InternalMazeDsl.g:826:2: iv_ruleZombieSpecifics= ruleZombieSpecifics EOF
            {
             newCompositeNode(grammarAccess.getZombieSpecificsRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleZombieSpecifics=ruleZombieSpecifics();

            state._fsp--;

             current =iv_ruleZombieSpecifics; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleZombieSpecifics"


    // $ANTLR start "ruleZombieSpecifics"
    // InternalMazeDsl.g:832:1: ruleZombieSpecifics returns [EObject current=null] : ( () otherlv_1= 'zombie-stats' otherlv_2= '{' (otherlv_3= 'attackDamage' ( (lv_attackDamage_4_0= RULE_INT ) ) )? (otherlv_5= 'infectionLevel' ( (lv_infectionLevel_6_0= RULE_INT ) ) )? (otherlv_7= 'resurrectionTime' ( (lv_resurrectionTime_8_0= RULE_INT ) ) )? otherlv_9= '}' ) ;
    public final EObject ruleZombieSpecifics() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token lv_attackDamage_4_0=null;
        Token otherlv_5=null;
        Token lv_infectionLevel_6_0=null;
        Token otherlv_7=null;
        Token lv_resurrectionTime_8_0=null;
        Token otherlv_9=null;


        	enterRule();

        try {
            // InternalMazeDsl.g:838:2: ( ( () otherlv_1= 'zombie-stats' otherlv_2= '{' (otherlv_3= 'attackDamage' ( (lv_attackDamage_4_0= RULE_INT ) ) )? (otherlv_5= 'infectionLevel' ( (lv_infectionLevel_6_0= RULE_INT ) ) )? (otherlv_7= 'resurrectionTime' ( (lv_resurrectionTime_8_0= RULE_INT ) ) )? otherlv_9= '}' ) )
            // InternalMazeDsl.g:839:2: ( () otherlv_1= 'zombie-stats' otherlv_2= '{' (otherlv_3= 'attackDamage' ( (lv_attackDamage_4_0= RULE_INT ) ) )? (otherlv_5= 'infectionLevel' ( (lv_infectionLevel_6_0= RULE_INT ) ) )? (otherlv_7= 'resurrectionTime' ( (lv_resurrectionTime_8_0= RULE_INT ) ) )? otherlv_9= '}' )
            {
            // InternalMazeDsl.g:839:2: ( () otherlv_1= 'zombie-stats' otherlv_2= '{' (otherlv_3= 'attackDamage' ( (lv_attackDamage_4_0= RULE_INT ) ) )? (otherlv_5= 'infectionLevel' ( (lv_infectionLevel_6_0= RULE_INT ) ) )? (otherlv_7= 'resurrectionTime' ( (lv_resurrectionTime_8_0= RULE_INT ) ) )? otherlv_9= '}' )
            // InternalMazeDsl.g:840:3: () otherlv_1= 'zombie-stats' otherlv_2= '{' (otherlv_3= 'attackDamage' ( (lv_attackDamage_4_0= RULE_INT ) ) )? (otherlv_5= 'infectionLevel' ( (lv_infectionLevel_6_0= RULE_INT ) ) )? (otherlv_7= 'resurrectionTime' ( (lv_resurrectionTime_8_0= RULE_INT ) ) )? otherlv_9= '}'
            {
            // InternalMazeDsl.g:840:3: ()
            // InternalMazeDsl.g:841:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getZombieSpecificsAccess().getZombieSpecificsAction_0(),
            					current);
            			

            }

            otherlv_1=(Token)match(input,34,FOLLOW_4); 

            			newLeafNode(otherlv_1, grammarAccess.getZombieSpecificsAccess().getZombieStatsKeyword_1());
            		
            otherlv_2=(Token)match(input,12,FOLLOW_35); 

            			newLeafNode(otherlv_2, grammarAccess.getZombieSpecificsAccess().getLeftCurlyBracketKeyword_2());
            		
            // InternalMazeDsl.g:855:3: (otherlv_3= 'attackDamage' ( (lv_attackDamage_4_0= RULE_INT ) ) )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==35) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // InternalMazeDsl.g:856:4: otherlv_3= 'attackDamage' ( (lv_attackDamage_4_0= RULE_INT ) )
                    {
                    otherlv_3=(Token)match(input,35,FOLLOW_36); 

                    				newLeafNode(otherlv_3, grammarAccess.getZombieSpecificsAccess().getAttackDamageKeyword_3_0());
                    			
                    // InternalMazeDsl.g:860:4: ( (lv_attackDamage_4_0= RULE_INT ) )
                    // InternalMazeDsl.g:861:5: (lv_attackDamage_4_0= RULE_INT )
                    {
                    // InternalMazeDsl.g:861:5: (lv_attackDamage_4_0= RULE_INT )
                    // InternalMazeDsl.g:862:6: lv_attackDamage_4_0= RULE_INT
                    {
                    lv_attackDamage_4_0=(Token)match(input,RULE_INT,FOLLOW_37); 

                    						newLeafNode(lv_attackDamage_4_0, grammarAccess.getZombieSpecificsAccess().getAttackDamageINTTerminalRuleCall_3_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getZombieSpecificsRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"attackDamage",
                    							lv_attackDamage_4_0,
                    							"org.eclipse.xtext.common.Terminals.INT");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeDsl.g:879:3: (otherlv_5= 'infectionLevel' ( (lv_infectionLevel_6_0= RULE_INT ) ) )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==36) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // InternalMazeDsl.g:880:4: otherlv_5= 'infectionLevel' ( (lv_infectionLevel_6_0= RULE_INT ) )
                    {
                    otherlv_5=(Token)match(input,36,FOLLOW_36); 

                    				newLeafNode(otherlv_5, grammarAccess.getZombieSpecificsAccess().getInfectionLevelKeyword_4_0());
                    			
                    // InternalMazeDsl.g:884:4: ( (lv_infectionLevel_6_0= RULE_INT ) )
                    // InternalMazeDsl.g:885:5: (lv_infectionLevel_6_0= RULE_INT )
                    {
                    // InternalMazeDsl.g:885:5: (lv_infectionLevel_6_0= RULE_INT )
                    // InternalMazeDsl.g:886:6: lv_infectionLevel_6_0= RULE_INT
                    {
                    lv_infectionLevel_6_0=(Token)match(input,RULE_INT,FOLLOW_38); 

                    						newLeafNode(lv_infectionLevel_6_0, grammarAccess.getZombieSpecificsAccess().getInfectionLevelINTTerminalRuleCall_4_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getZombieSpecificsRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"infectionLevel",
                    							lv_infectionLevel_6_0,
                    							"org.eclipse.xtext.common.Terminals.INT");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeDsl.g:903:3: (otherlv_7= 'resurrectionTime' ( (lv_resurrectionTime_8_0= RULE_INT ) ) )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==37) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // InternalMazeDsl.g:904:4: otherlv_7= 'resurrectionTime' ( (lv_resurrectionTime_8_0= RULE_INT ) )
                    {
                    otherlv_7=(Token)match(input,37,FOLLOW_36); 

                    				newLeafNode(otherlv_7, grammarAccess.getZombieSpecificsAccess().getResurrectionTimeKeyword_5_0());
                    			
                    // InternalMazeDsl.g:908:4: ( (lv_resurrectionTime_8_0= RULE_INT ) )
                    // InternalMazeDsl.g:909:5: (lv_resurrectionTime_8_0= RULE_INT )
                    {
                    // InternalMazeDsl.g:909:5: (lv_resurrectionTime_8_0= RULE_INT )
                    // InternalMazeDsl.g:910:6: lv_resurrectionTime_8_0= RULE_INT
                    {
                    lv_resurrectionTime_8_0=(Token)match(input,RULE_INT,FOLLOW_34); 

                    						newLeafNode(lv_resurrectionTime_8_0, grammarAccess.getZombieSpecificsAccess().getResurrectionTimeINTTerminalRuleCall_5_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getZombieSpecificsRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"resurrectionTime",
                    							lv_resurrectionTime_8_0,
                    							"org.eclipse.xtext.common.Terminals.INT");
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_9=(Token)match(input,13,FOLLOW_2); 

            			newLeafNode(otherlv_9, grammarAccess.getZombieSpecificsAccess().getRightCurlyBracketKeyword_6());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleZombieSpecifics"


    // $ANTLR start "entryRuleGhostSpecifics"
    // InternalMazeDsl.g:935:1: entryRuleGhostSpecifics returns [EObject current=null] : iv_ruleGhostSpecifics= ruleGhostSpecifics EOF ;
    public final EObject entryRuleGhostSpecifics() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGhostSpecifics = null;


        try {
            // InternalMazeDsl.g:935:55: (iv_ruleGhostSpecifics= ruleGhostSpecifics EOF )
            // InternalMazeDsl.g:936:2: iv_ruleGhostSpecifics= ruleGhostSpecifics EOF
            {
             newCompositeNode(grammarAccess.getGhostSpecificsRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleGhostSpecifics=ruleGhostSpecifics();

            state._fsp--;

             current =iv_ruleGhostSpecifics; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleGhostSpecifics"


    // $ANTLR start "ruleGhostSpecifics"
    // InternalMazeDsl.g:942:1: ruleGhostSpecifics returns [EObject current=null] : ( () otherlv_1= 'ghost-stats' otherlv_2= '{' (otherlv_3= 'attackDamage' ( (lv_attackDamage_4_0= RULE_INT ) ) )? (otherlv_5= 'visibilityLevel' ( (lv_visibilityLevel_6_0= RULE_INT ) ) )? (otherlv_7= 'nonTangibilityEnergy' ( (lv_nonTangibilityEnergy_8_0= ruleDOUBLE ) ) )? otherlv_9= '}' ) ;
    public final EObject ruleGhostSpecifics() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token lv_attackDamage_4_0=null;
        Token otherlv_5=null;
        Token lv_visibilityLevel_6_0=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        AntlrDatatypeRuleToken lv_nonTangibilityEnergy_8_0 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:948:2: ( ( () otherlv_1= 'ghost-stats' otherlv_2= '{' (otherlv_3= 'attackDamage' ( (lv_attackDamage_4_0= RULE_INT ) ) )? (otherlv_5= 'visibilityLevel' ( (lv_visibilityLevel_6_0= RULE_INT ) ) )? (otherlv_7= 'nonTangibilityEnergy' ( (lv_nonTangibilityEnergy_8_0= ruleDOUBLE ) ) )? otherlv_9= '}' ) )
            // InternalMazeDsl.g:949:2: ( () otherlv_1= 'ghost-stats' otherlv_2= '{' (otherlv_3= 'attackDamage' ( (lv_attackDamage_4_0= RULE_INT ) ) )? (otherlv_5= 'visibilityLevel' ( (lv_visibilityLevel_6_0= RULE_INT ) ) )? (otherlv_7= 'nonTangibilityEnergy' ( (lv_nonTangibilityEnergy_8_0= ruleDOUBLE ) ) )? otherlv_9= '}' )
            {
            // InternalMazeDsl.g:949:2: ( () otherlv_1= 'ghost-stats' otherlv_2= '{' (otherlv_3= 'attackDamage' ( (lv_attackDamage_4_0= RULE_INT ) ) )? (otherlv_5= 'visibilityLevel' ( (lv_visibilityLevel_6_0= RULE_INT ) ) )? (otherlv_7= 'nonTangibilityEnergy' ( (lv_nonTangibilityEnergy_8_0= ruleDOUBLE ) ) )? otherlv_9= '}' )
            // InternalMazeDsl.g:950:3: () otherlv_1= 'ghost-stats' otherlv_2= '{' (otherlv_3= 'attackDamage' ( (lv_attackDamage_4_0= RULE_INT ) ) )? (otherlv_5= 'visibilityLevel' ( (lv_visibilityLevel_6_0= RULE_INT ) ) )? (otherlv_7= 'nonTangibilityEnergy' ( (lv_nonTangibilityEnergy_8_0= ruleDOUBLE ) ) )? otherlv_9= '}'
            {
            // InternalMazeDsl.g:950:3: ()
            // InternalMazeDsl.g:951:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getGhostSpecificsAccess().getGhostSpecificsAction_0(),
            					current);
            			

            }

            otherlv_1=(Token)match(input,38,FOLLOW_4); 

            			newLeafNode(otherlv_1, grammarAccess.getGhostSpecificsAccess().getGhostStatsKeyword_1());
            		
            otherlv_2=(Token)match(input,12,FOLLOW_39); 

            			newLeafNode(otherlv_2, grammarAccess.getGhostSpecificsAccess().getLeftCurlyBracketKeyword_2());
            		
            // InternalMazeDsl.g:965:3: (otherlv_3= 'attackDamage' ( (lv_attackDamage_4_0= RULE_INT ) ) )?
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==35) ) {
                alt25=1;
            }
            switch (alt25) {
                case 1 :
                    // InternalMazeDsl.g:966:4: otherlv_3= 'attackDamage' ( (lv_attackDamage_4_0= RULE_INT ) )
                    {
                    otherlv_3=(Token)match(input,35,FOLLOW_36); 

                    				newLeafNode(otherlv_3, grammarAccess.getGhostSpecificsAccess().getAttackDamageKeyword_3_0());
                    			
                    // InternalMazeDsl.g:970:4: ( (lv_attackDamage_4_0= RULE_INT ) )
                    // InternalMazeDsl.g:971:5: (lv_attackDamage_4_0= RULE_INT )
                    {
                    // InternalMazeDsl.g:971:5: (lv_attackDamage_4_0= RULE_INT )
                    // InternalMazeDsl.g:972:6: lv_attackDamage_4_0= RULE_INT
                    {
                    lv_attackDamage_4_0=(Token)match(input,RULE_INT,FOLLOW_40); 

                    						newLeafNode(lv_attackDamage_4_0, grammarAccess.getGhostSpecificsAccess().getAttackDamageINTTerminalRuleCall_3_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getGhostSpecificsRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"attackDamage",
                    							lv_attackDamage_4_0,
                    							"org.eclipse.xtext.common.Terminals.INT");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeDsl.g:989:3: (otherlv_5= 'visibilityLevel' ( (lv_visibilityLevel_6_0= RULE_INT ) ) )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==39) ) {
                alt26=1;
            }
            switch (alt26) {
                case 1 :
                    // InternalMazeDsl.g:990:4: otherlv_5= 'visibilityLevel' ( (lv_visibilityLevel_6_0= RULE_INT ) )
                    {
                    otherlv_5=(Token)match(input,39,FOLLOW_36); 

                    				newLeafNode(otherlv_5, grammarAccess.getGhostSpecificsAccess().getVisibilityLevelKeyword_4_0());
                    			
                    // InternalMazeDsl.g:994:4: ( (lv_visibilityLevel_6_0= RULE_INT ) )
                    // InternalMazeDsl.g:995:5: (lv_visibilityLevel_6_0= RULE_INT )
                    {
                    // InternalMazeDsl.g:995:5: (lv_visibilityLevel_6_0= RULE_INT )
                    // InternalMazeDsl.g:996:6: lv_visibilityLevel_6_0= RULE_INT
                    {
                    lv_visibilityLevel_6_0=(Token)match(input,RULE_INT,FOLLOW_41); 

                    						newLeafNode(lv_visibilityLevel_6_0, grammarAccess.getGhostSpecificsAccess().getVisibilityLevelINTTerminalRuleCall_4_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getGhostSpecificsRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"visibilityLevel",
                    							lv_visibilityLevel_6_0,
                    							"org.eclipse.xtext.common.Terminals.INT");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeDsl.g:1013:3: (otherlv_7= 'nonTangibilityEnergy' ( (lv_nonTangibilityEnergy_8_0= ruleDOUBLE ) ) )?
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==40) ) {
                alt27=1;
            }
            switch (alt27) {
                case 1 :
                    // InternalMazeDsl.g:1014:4: otherlv_7= 'nonTangibilityEnergy' ( (lv_nonTangibilityEnergy_8_0= ruleDOUBLE ) )
                    {
                    otherlv_7=(Token)match(input,40,FOLLOW_16); 

                    				newLeafNode(otherlv_7, grammarAccess.getGhostSpecificsAccess().getNonTangibilityEnergyKeyword_5_0());
                    			
                    // InternalMazeDsl.g:1018:4: ( (lv_nonTangibilityEnergy_8_0= ruleDOUBLE ) )
                    // InternalMazeDsl.g:1019:5: (lv_nonTangibilityEnergy_8_0= ruleDOUBLE )
                    {
                    // InternalMazeDsl.g:1019:5: (lv_nonTangibilityEnergy_8_0= ruleDOUBLE )
                    // InternalMazeDsl.g:1020:6: lv_nonTangibilityEnergy_8_0= ruleDOUBLE
                    {

                    						newCompositeNode(grammarAccess.getGhostSpecificsAccess().getNonTangibilityEnergyDOUBLEParserRuleCall_5_1_0());
                    					
                    pushFollow(FOLLOW_34);
                    lv_nonTangibilityEnergy_8_0=ruleDOUBLE();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getGhostSpecificsRule());
                    						}
                    						set(
                    							current,
                    							"nonTangibilityEnergy",
                    							lv_nonTangibilityEnergy_8_0,
                    							"main.game.maze.dsl.MazeDsl.DOUBLE");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_9=(Token)match(input,13,FOLLOW_2); 

            			newLeafNode(otherlv_9, grammarAccess.getGhostSpecificsAccess().getRightCurlyBracketKeyword_6());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleGhostSpecifics"


    // $ANTLR start "entryRuleRangedSpecifics"
    // InternalMazeDsl.g:1046:1: entryRuleRangedSpecifics returns [EObject current=null] : iv_ruleRangedSpecifics= ruleRangedSpecifics EOF ;
    public final EObject entryRuleRangedSpecifics() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRangedSpecifics = null;


        try {
            // InternalMazeDsl.g:1046:56: (iv_ruleRangedSpecifics= ruleRangedSpecifics EOF )
            // InternalMazeDsl.g:1047:2: iv_ruleRangedSpecifics= ruleRangedSpecifics EOF
            {
             newCompositeNode(grammarAccess.getRangedSpecificsRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleRangedSpecifics=ruleRangedSpecifics();

            state._fsp--;

             current =iv_ruleRangedSpecifics; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleRangedSpecifics"


    // $ANTLR start "ruleRangedSpecifics"
    // InternalMazeDsl.g:1053:1: ruleRangedSpecifics returns [EObject current=null] : ( () otherlv_1= 'ranged-stats' otherlv_2= '{' (otherlv_3= 'attackRange' ( (lv_attackRange_4_0= ruleDOUBLE ) ) )? (otherlv_5= 'attackCooldown' ( (lv_attackCooldown_6_0= RULE_INT ) ) )? (otherlv_7= 'attackDamage' ( (lv_attackDamage_8_0= RULE_INT ) ) )? (otherlv_9= 'projectileSpeed' ( (lv_projectileSpeed_10_0= ruleDOUBLE ) ) )? (otherlv_11= 'projectileType' ( (lv_projectileType_12_0= ruleProjectileTypeEnum ) ) )? (otherlv_13= 'splashRadius' ( (lv_splashRadius_14_0= ruleDOUBLE ) ) )? otherlv_15= '}' ) ;
    public final EObject ruleRangedSpecifics() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token lv_attackCooldown_6_0=null;
        Token otherlv_7=null;
        Token lv_attackDamage_8_0=null;
        Token otherlv_9=null;
        Token otherlv_11=null;
        Token otherlv_13=null;
        Token otherlv_15=null;
        AntlrDatatypeRuleToken lv_attackRange_4_0 = null;

        AntlrDatatypeRuleToken lv_projectileSpeed_10_0 = null;

        Enumerator lv_projectileType_12_0 = null;

        AntlrDatatypeRuleToken lv_splashRadius_14_0 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:1059:2: ( ( () otherlv_1= 'ranged-stats' otherlv_2= '{' (otherlv_3= 'attackRange' ( (lv_attackRange_4_0= ruleDOUBLE ) ) )? (otherlv_5= 'attackCooldown' ( (lv_attackCooldown_6_0= RULE_INT ) ) )? (otherlv_7= 'attackDamage' ( (lv_attackDamage_8_0= RULE_INT ) ) )? (otherlv_9= 'projectileSpeed' ( (lv_projectileSpeed_10_0= ruleDOUBLE ) ) )? (otherlv_11= 'projectileType' ( (lv_projectileType_12_0= ruleProjectileTypeEnum ) ) )? (otherlv_13= 'splashRadius' ( (lv_splashRadius_14_0= ruleDOUBLE ) ) )? otherlv_15= '}' ) )
            // InternalMazeDsl.g:1060:2: ( () otherlv_1= 'ranged-stats' otherlv_2= '{' (otherlv_3= 'attackRange' ( (lv_attackRange_4_0= ruleDOUBLE ) ) )? (otherlv_5= 'attackCooldown' ( (lv_attackCooldown_6_0= RULE_INT ) ) )? (otherlv_7= 'attackDamage' ( (lv_attackDamage_8_0= RULE_INT ) ) )? (otherlv_9= 'projectileSpeed' ( (lv_projectileSpeed_10_0= ruleDOUBLE ) ) )? (otherlv_11= 'projectileType' ( (lv_projectileType_12_0= ruleProjectileTypeEnum ) ) )? (otherlv_13= 'splashRadius' ( (lv_splashRadius_14_0= ruleDOUBLE ) ) )? otherlv_15= '}' )
            {
            // InternalMazeDsl.g:1060:2: ( () otherlv_1= 'ranged-stats' otherlv_2= '{' (otherlv_3= 'attackRange' ( (lv_attackRange_4_0= ruleDOUBLE ) ) )? (otherlv_5= 'attackCooldown' ( (lv_attackCooldown_6_0= RULE_INT ) ) )? (otherlv_7= 'attackDamage' ( (lv_attackDamage_8_0= RULE_INT ) ) )? (otherlv_9= 'projectileSpeed' ( (lv_projectileSpeed_10_0= ruleDOUBLE ) ) )? (otherlv_11= 'projectileType' ( (lv_projectileType_12_0= ruleProjectileTypeEnum ) ) )? (otherlv_13= 'splashRadius' ( (lv_splashRadius_14_0= ruleDOUBLE ) ) )? otherlv_15= '}' )
            // InternalMazeDsl.g:1061:3: () otherlv_1= 'ranged-stats' otherlv_2= '{' (otherlv_3= 'attackRange' ( (lv_attackRange_4_0= ruleDOUBLE ) ) )? (otherlv_5= 'attackCooldown' ( (lv_attackCooldown_6_0= RULE_INT ) ) )? (otherlv_7= 'attackDamage' ( (lv_attackDamage_8_0= RULE_INT ) ) )? (otherlv_9= 'projectileSpeed' ( (lv_projectileSpeed_10_0= ruleDOUBLE ) ) )? (otherlv_11= 'projectileType' ( (lv_projectileType_12_0= ruleProjectileTypeEnum ) ) )? (otherlv_13= 'splashRadius' ( (lv_splashRadius_14_0= ruleDOUBLE ) ) )? otherlv_15= '}'
            {
            // InternalMazeDsl.g:1061:3: ()
            // InternalMazeDsl.g:1062:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getRangedSpecificsAccess().getRangedSpecificsAction_0(),
            					current);
            			

            }

            otherlv_1=(Token)match(input,41,FOLLOW_4); 

            			newLeafNode(otherlv_1, grammarAccess.getRangedSpecificsAccess().getRangedStatsKeyword_1());
            		
            otherlv_2=(Token)match(input,12,FOLLOW_42); 

            			newLeafNode(otherlv_2, grammarAccess.getRangedSpecificsAccess().getLeftCurlyBracketKeyword_2());
            		
            // InternalMazeDsl.g:1076:3: (otherlv_3= 'attackRange' ( (lv_attackRange_4_0= ruleDOUBLE ) ) )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==42) ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // InternalMazeDsl.g:1077:4: otherlv_3= 'attackRange' ( (lv_attackRange_4_0= ruleDOUBLE ) )
                    {
                    otherlv_3=(Token)match(input,42,FOLLOW_16); 

                    				newLeafNode(otherlv_3, grammarAccess.getRangedSpecificsAccess().getAttackRangeKeyword_3_0());
                    			
                    // InternalMazeDsl.g:1081:4: ( (lv_attackRange_4_0= ruleDOUBLE ) )
                    // InternalMazeDsl.g:1082:5: (lv_attackRange_4_0= ruleDOUBLE )
                    {
                    // InternalMazeDsl.g:1082:5: (lv_attackRange_4_0= ruleDOUBLE )
                    // InternalMazeDsl.g:1083:6: lv_attackRange_4_0= ruleDOUBLE
                    {

                    						newCompositeNode(grammarAccess.getRangedSpecificsAccess().getAttackRangeDOUBLEParserRuleCall_3_1_0());
                    					
                    pushFollow(FOLLOW_43);
                    lv_attackRange_4_0=ruleDOUBLE();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getRangedSpecificsRule());
                    						}
                    						set(
                    							current,
                    							"attackRange",
                    							lv_attackRange_4_0,
                    							"main.game.maze.dsl.MazeDsl.DOUBLE");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeDsl.g:1101:3: (otherlv_5= 'attackCooldown' ( (lv_attackCooldown_6_0= RULE_INT ) ) )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==43) ) {
                alt29=1;
            }
            switch (alt29) {
                case 1 :
                    // InternalMazeDsl.g:1102:4: otherlv_5= 'attackCooldown' ( (lv_attackCooldown_6_0= RULE_INT ) )
                    {
                    otherlv_5=(Token)match(input,43,FOLLOW_36); 

                    				newLeafNode(otherlv_5, grammarAccess.getRangedSpecificsAccess().getAttackCooldownKeyword_4_0());
                    			
                    // InternalMazeDsl.g:1106:4: ( (lv_attackCooldown_6_0= RULE_INT ) )
                    // InternalMazeDsl.g:1107:5: (lv_attackCooldown_6_0= RULE_INT )
                    {
                    // InternalMazeDsl.g:1107:5: (lv_attackCooldown_6_0= RULE_INT )
                    // InternalMazeDsl.g:1108:6: lv_attackCooldown_6_0= RULE_INT
                    {
                    lv_attackCooldown_6_0=(Token)match(input,RULE_INT,FOLLOW_44); 

                    						newLeafNode(lv_attackCooldown_6_0, grammarAccess.getRangedSpecificsAccess().getAttackCooldownINTTerminalRuleCall_4_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getRangedSpecificsRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"attackCooldown",
                    							lv_attackCooldown_6_0,
                    							"org.eclipse.xtext.common.Terminals.INT");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeDsl.g:1125:3: (otherlv_7= 'attackDamage' ( (lv_attackDamage_8_0= RULE_INT ) ) )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==35) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // InternalMazeDsl.g:1126:4: otherlv_7= 'attackDamage' ( (lv_attackDamage_8_0= RULE_INT ) )
                    {
                    otherlv_7=(Token)match(input,35,FOLLOW_36); 

                    				newLeafNode(otherlv_7, grammarAccess.getRangedSpecificsAccess().getAttackDamageKeyword_5_0());
                    			
                    // InternalMazeDsl.g:1130:4: ( (lv_attackDamage_8_0= RULE_INT ) )
                    // InternalMazeDsl.g:1131:5: (lv_attackDamage_8_0= RULE_INT )
                    {
                    // InternalMazeDsl.g:1131:5: (lv_attackDamage_8_0= RULE_INT )
                    // InternalMazeDsl.g:1132:6: lv_attackDamage_8_0= RULE_INT
                    {
                    lv_attackDamage_8_0=(Token)match(input,RULE_INT,FOLLOW_45); 

                    						newLeafNode(lv_attackDamage_8_0, grammarAccess.getRangedSpecificsAccess().getAttackDamageINTTerminalRuleCall_5_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getRangedSpecificsRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"attackDamage",
                    							lv_attackDamage_8_0,
                    							"org.eclipse.xtext.common.Terminals.INT");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeDsl.g:1149:3: (otherlv_9= 'projectileSpeed' ( (lv_projectileSpeed_10_0= ruleDOUBLE ) ) )?
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==44) ) {
                alt31=1;
            }
            switch (alt31) {
                case 1 :
                    // InternalMazeDsl.g:1150:4: otherlv_9= 'projectileSpeed' ( (lv_projectileSpeed_10_0= ruleDOUBLE ) )
                    {
                    otherlv_9=(Token)match(input,44,FOLLOW_16); 

                    				newLeafNode(otherlv_9, grammarAccess.getRangedSpecificsAccess().getProjectileSpeedKeyword_6_0());
                    			
                    // InternalMazeDsl.g:1154:4: ( (lv_projectileSpeed_10_0= ruleDOUBLE ) )
                    // InternalMazeDsl.g:1155:5: (lv_projectileSpeed_10_0= ruleDOUBLE )
                    {
                    // InternalMazeDsl.g:1155:5: (lv_projectileSpeed_10_0= ruleDOUBLE )
                    // InternalMazeDsl.g:1156:6: lv_projectileSpeed_10_0= ruleDOUBLE
                    {

                    						newCompositeNode(grammarAccess.getRangedSpecificsAccess().getProjectileSpeedDOUBLEParserRuleCall_6_1_0());
                    					
                    pushFollow(FOLLOW_46);
                    lv_projectileSpeed_10_0=ruleDOUBLE();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getRangedSpecificsRule());
                    						}
                    						set(
                    							current,
                    							"projectileSpeed",
                    							lv_projectileSpeed_10_0,
                    							"main.game.maze.dsl.MazeDsl.DOUBLE");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeDsl.g:1174:3: (otherlv_11= 'projectileType' ( (lv_projectileType_12_0= ruleProjectileTypeEnum ) ) )?
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==45) ) {
                alt32=1;
            }
            switch (alt32) {
                case 1 :
                    // InternalMazeDsl.g:1175:4: otherlv_11= 'projectileType' ( (lv_projectileType_12_0= ruleProjectileTypeEnum ) )
                    {
                    otherlv_11=(Token)match(input,45,FOLLOW_47); 

                    				newLeafNode(otherlv_11, grammarAccess.getRangedSpecificsAccess().getProjectileTypeKeyword_7_0());
                    			
                    // InternalMazeDsl.g:1179:4: ( (lv_projectileType_12_0= ruleProjectileTypeEnum ) )
                    // InternalMazeDsl.g:1180:5: (lv_projectileType_12_0= ruleProjectileTypeEnum )
                    {
                    // InternalMazeDsl.g:1180:5: (lv_projectileType_12_0= ruleProjectileTypeEnum )
                    // InternalMazeDsl.g:1181:6: lv_projectileType_12_0= ruleProjectileTypeEnum
                    {

                    						newCompositeNode(grammarAccess.getRangedSpecificsAccess().getProjectileTypeProjectileTypeEnumEnumRuleCall_7_1_0());
                    					
                    pushFollow(FOLLOW_48);
                    lv_projectileType_12_0=ruleProjectileTypeEnum();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getRangedSpecificsRule());
                    						}
                    						set(
                    							current,
                    							"projectileType",
                    							lv_projectileType_12_0,
                    							"main.game.maze.dsl.MazeDsl.ProjectileTypeEnum");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeDsl.g:1199:3: (otherlv_13= 'splashRadius' ( (lv_splashRadius_14_0= ruleDOUBLE ) ) )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==46) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // InternalMazeDsl.g:1200:4: otherlv_13= 'splashRadius' ( (lv_splashRadius_14_0= ruleDOUBLE ) )
                    {
                    otherlv_13=(Token)match(input,46,FOLLOW_16); 

                    				newLeafNode(otherlv_13, grammarAccess.getRangedSpecificsAccess().getSplashRadiusKeyword_8_0());
                    			
                    // InternalMazeDsl.g:1204:4: ( (lv_splashRadius_14_0= ruleDOUBLE ) )
                    // InternalMazeDsl.g:1205:5: (lv_splashRadius_14_0= ruleDOUBLE )
                    {
                    // InternalMazeDsl.g:1205:5: (lv_splashRadius_14_0= ruleDOUBLE )
                    // InternalMazeDsl.g:1206:6: lv_splashRadius_14_0= ruleDOUBLE
                    {

                    						newCompositeNode(grammarAccess.getRangedSpecificsAccess().getSplashRadiusDOUBLEParserRuleCall_8_1_0());
                    					
                    pushFollow(FOLLOW_34);
                    lv_splashRadius_14_0=ruleDOUBLE();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getRangedSpecificsRule());
                    						}
                    						set(
                    							current,
                    							"splashRadius",
                    							lv_splashRadius_14_0,
                    							"main.game.maze.dsl.MazeDsl.DOUBLE");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_15=(Token)match(input,13,FOLLOW_2); 

            			newLeafNode(otherlv_15, grammarAccess.getRangedSpecificsAccess().getRightCurlyBracketKeyword_9());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleRangedSpecifics"


    // $ANTLR start "entryRulePatrolConfig"
    // InternalMazeDsl.g:1232:1: entryRulePatrolConfig returns [EObject current=null] : iv_rulePatrolConfig= rulePatrolConfig EOF ;
    public final EObject entryRulePatrolConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePatrolConfig = null;


        try {
            // InternalMazeDsl.g:1232:53: (iv_rulePatrolConfig= rulePatrolConfig EOF )
            // InternalMazeDsl.g:1233:2: iv_rulePatrolConfig= rulePatrolConfig EOF
            {
             newCompositeNode(grammarAccess.getPatrolConfigRule()); 
            pushFollow(FOLLOW_1);
            iv_rulePatrolConfig=rulePatrolConfig();

            state._fsp--;

             current =iv_rulePatrolConfig; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePatrolConfig"


    // $ANTLR start "rulePatrolConfig"
    // InternalMazeDsl.g:1239:1: rulePatrolConfig returns [EObject current=null] : (otherlv_0= 'patrol' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' (otherlv_3= 'visionRange' ( (lv_visionRange_4_0= ruleDOUBLE ) ) )? ( (lv_zone_5_0= rulePatrolZoneConfig ) )? otherlv_6= 'path' otherlv_7= '[' ( (lv_waypoints_8_0= ruleWaypoint ) ) (otherlv_9= ',' ( (lv_waypoints_10_0= ruleWaypoint ) ) )* otherlv_11= ']' otherlv_12= '}' ) ;
    public final EObject rulePatrolConfig() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        Token otherlv_11=null;
        Token otherlv_12=null;
        AntlrDatatypeRuleToken lv_visionRange_4_0 = null;

        EObject lv_zone_5_0 = null;

        EObject lv_waypoints_8_0 = null;

        EObject lv_waypoints_10_0 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:1245:2: ( (otherlv_0= 'patrol' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' (otherlv_3= 'visionRange' ( (lv_visionRange_4_0= ruleDOUBLE ) ) )? ( (lv_zone_5_0= rulePatrolZoneConfig ) )? otherlv_6= 'path' otherlv_7= '[' ( (lv_waypoints_8_0= ruleWaypoint ) ) (otherlv_9= ',' ( (lv_waypoints_10_0= ruleWaypoint ) ) )* otherlv_11= ']' otherlv_12= '}' ) )
            // InternalMazeDsl.g:1246:2: (otherlv_0= 'patrol' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' (otherlv_3= 'visionRange' ( (lv_visionRange_4_0= ruleDOUBLE ) ) )? ( (lv_zone_5_0= rulePatrolZoneConfig ) )? otherlv_6= 'path' otherlv_7= '[' ( (lv_waypoints_8_0= ruleWaypoint ) ) (otherlv_9= ',' ( (lv_waypoints_10_0= ruleWaypoint ) ) )* otherlv_11= ']' otherlv_12= '}' )
            {
            // InternalMazeDsl.g:1246:2: (otherlv_0= 'patrol' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' (otherlv_3= 'visionRange' ( (lv_visionRange_4_0= ruleDOUBLE ) ) )? ( (lv_zone_5_0= rulePatrolZoneConfig ) )? otherlv_6= 'path' otherlv_7= '[' ( (lv_waypoints_8_0= ruleWaypoint ) ) (otherlv_9= ',' ( (lv_waypoints_10_0= ruleWaypoint ) ) )* otherlv_11= ']' otherlv_12= '}' )
            // InternalMazeDsl.g:1247:3: otherlv_0= 'patrol' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' (otherlv_3= 'visionRange' ( (lv_visionRange_4_0= ruleDOUBLE ) ) )? ( (lv_zone_5_0= rulePatrolZoneConfig ) )? otherlv_6= 'path' otherlv_7= '[' ( (lv_waypoints_8_0= ruleWaypoint ) ) (otherlv_9= ',' ( (lv_waypoints_10_0= ruleWaypoint ) ) )* otherlv_11= ']' otherlv_12= '}'
            {
            otherlv_0=(Token)match(input,32,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getPatrolConfigAccess().getPatrolKeyword_0());
            		
            // InternalMazeDsl.g:1251:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalMazeDsl.g:1252:4: (lv_name_1_0= RULE_ID )
            {
            // InternalMazeDsl.g:1252:4: (lv_name_1_0= RULE_ID )
            // InternalMazeDsl.g:1253:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_4); 

            					newLeafNode(lv_name_1_0, grammarAccess.getPatrolConfigAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getPatrolConfigRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            otherlv_2=(Token)match(input,12,FOLLOW_49); 

            			newLeafNode(otherlv_2, grammarAccess.getPatrolConfigAccess().getLeftCurlyBracketKeyword_2());
            		
            // InternalMazeDsl.g:1273:3: (otherlv_3= 'visionRange' ( (lv_visionRange_4_0= ruleDOUBLE ) ) )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==47) ) {
                alt34=1;
            }
            switch (alt34) {
                case 1 :
                    // InternalMazeDsl.g:1274:4: otherlv_3= 'visionRange' ( (lv_visionRange_4_0= ruleDOUBLE ) )
                    {
                    otherlv_3=(Token)match(input,47,FOLLOW_16); 

                    				newLeafNode(otherlv_3, grammarAccess.getPatrolConfigAccess().getVisionRangeKeyword_3_0());
                    			
                    // InternalMazeDsl.g:1278:4: ( (lv_visionRange_4_0= ruleDOUBLE ) )
                    // InternalMazeDsl.g:1279:5: (lv_visionRange_4_0= ruleDOUBLE )
                    {
                    // InternalMazeDsl.g:1279:5: (lv_visionRange_4_0= ruleDOUBLE )
                    // InternalMazeDsl.g:1280:6: lv_visionRange_4_0= ruleDOUBLE
                    {

                    						newCompositeNode(grammarAccess.getPatrolConfigAccess().getVisionRangeDOUBLEParserRuleCall_3_1_0());
                    					
                    pushFollow(FOLLOW_50);
                    lv_visionRange_4_0=ruleDOUBLE();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getPatrolConfigRule());
                    						}
                    						set(
                    							current,
                    							"visionRange",
                    							lv_visionRange_4_0,
                    							"main.game.maze.dsl.MazeDsl.DOUBLE");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeDsl.g:1298:3: ( (lv_zone_5_0= rulePatrolZoneConfig ) )?
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==52) ) {
                alt35=1;
            }
            switch (alt35) {
                case 1 :
                    // InternalMazeDsl.g:1299:4: (lv_zone_5_0= rulePatrolZoneConfig )
                    {
                    // InternalMazeDsl.g:1299:4: (lv_zone_5_0= rulePatrolZoneConfig )
                    // InternalMazeDsl.g:1300:5: lv_zone_5_0= rulePatrolZoneConfig
                    {

                    					newCompositeNode(grammarAccess.getPatrolConfigAccess().getZonePatrolZoneConfigParserRuleCall_4_0());
                    				
                    pushFollow(FOLLOW_51);
                    lv_zone_5_0=rulePatrolZoneConfig();

                    state._fsp--;


                    					if (current==null) {
                    						current = createModelElementForParent(grammarAccess.getPatrolConfigRule());
                    					}
                    					set(
                    						current,
                    						"zone",
                    						lv_zone_5_0,
                    						"main.game.maze.dsl.MazeDsl.PatrolZoneConfig");
                    					afterParserOrEnumRuleCall();
                    				

                    }


                    }
                    break;

            }

            otherlv_6=(Token)match(input,48,FOLLOW_52); 

            			newLeafNode(otherlv_6, grammarAccess.getPatrolConfigAccess().getPathKeyword_5());
            		
            otherlv_7=(Token)match(input,49,FOLLOW_53); 

            			newLeafNode(otherlv_7, grammarAccess.getPatrolConfigAccess().getLeftSquareBracketKeyword_6());
            		
            // InternalMazeDsl.g:1325:3: ( (lv_waypoints_8_0= ruleWaypoint ) )
            // InternalMazeDsl.g:1326:4: (lv_waypoints_8_0= ruleWaypoint )
            {
            // InternalMazeDsl.g:1326:4: (lv_waypoints_8_0= ruleWaypoint )
            // InternalMazeDsl.g:1327:5: lv_waypoints_8_0= ruleWaypoint
            {

            					newCompositeNode(grammarAccess.getPatrolConfigAccess().getWaypointsWaypointParserRuleCall_7_0());
            				
            pushFollow(FOLLOW_54);
            lv_waypoints_8_0=ruleWaypoint();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getPatrolConfigRule());
            					}
            					add(
            						current,
            						"waypoints",
            						lv_waypoints_8_0,
            						"main.game.maze.dsl.MazeDsl.Waypoint");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalMazeDsl.g:1344:3: (otherlv_9= ',' ( (lv_waypoints_10_0= ruleWaypoint ) ) )*
            loop36:
            do {
                int alt36=2;
                int LA36_0 = input.LA(1);

                if ( (LA36_0==50) ) {
                    alt36=1;
                }


                switch (alt36) {
            	case 1 :
            	    // InternalMazeDsl.g:1345:4: otherlv_9= ',' ( (lv_waypoints_10_0= ruleWaypoint ) )
            	    {
            	    otherlv_9=(Token)match(input,50,FOLLOW_53); 

            	    				newLeafNode(otherlv_9, grammarAccess.getPatrolConfigAccess().getCommaKeyword_8_0());
            	    			
            	    // InternalMazeDsl.g:1349:4: ( (lv_waypoints_10_0= ruleWaypoint ) )
            	    // InternalMazeDsl.g:1350:5: (lv_waypoints_10_0= ruleWaypoint )
            	    {
            	    // InternalMazeDsl.g:1350:5: (lv_waypoints_10_0= ruleWaypoint )
            	    // InternalMazeDsl.g:1351:6: lv_waypoints_10_0= ruleWaypoint
            	    {

            	    						newCompositeNode(grammarAccess.getPatrolConfigAccess().getWaypointsWaypointParserRuleCall_8_1_0());
            	    					
            	    pushFollow(FOLLOW_54);
            	    lv_waypoints_10_0=ruleWaypoint();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getPatrolConfigRule());
            	    						}
            	    						add(
            	    							current,
            	    							"waypoints",
            	    							lv_waypoints_10_0,
            	    							"main.game.maze.dsl.MazeDsl.Waypoint");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop36;
                }
            } while (true);

            otherlv_11=(Token)match(input,51,FOLLOW_34); 

            			newLeafNode(otherlv_11, grammarAccess.getPatrolConfigAccess().getRightSquareBracketKeyword_9());
            		
            otherlv_12=(Token)match(input,13,FOLLOW_2); 

            			newLeafNode(otherlv_12, grammarAccess.getPatrolConfigAccess().getRightCurlyBracketKeyword_10());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePatrolConfig"


    // $ANTLR start "entryRulePatrolZoneConfig"
    // InternalMazeDsl.g:1381:1: entryRulePatrolZoneConfig returns [EObject current=null] : iv_rulePatrolZoneConfig= rulePatrolZoneConfig EOF ;
    public final EObject entryRulePatrolZoneConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePatrolZoneConfig = null;


        try {
            // InternalMazeDsl.g:1381:57: (iv_rulePatrolZoneConfig= rulePatrolZoneConfig EOF )
            // InternalMazeDsl.g:1382:2: iv_rulePatrolZoneConfig= rulePatrolZoneConfig EOF
            {
             newCompositeNode(grammarAccess.getPatrolZoneConfigRule()); 
            pushFollow(FOLLOW_1);
            iv_rulePatrolZoneConfig=rulePatrolZoneConfig();

            state._fsp--;

             current =iv_rulePatrolZoneConfig; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePatrolZoneConfig"


    // $ANTLR start "rulePatrolZoneConfig"
    // InternalMazeDsl.g:1388:1: rulePatrolZoneConfig returns [EObject current=null] : (otherlv_0= 'zone' otherlv_1= '{' otherlv_2= 'topLeft' otherlv_3= '(' ( (lv_topLeftX_4_0= ruleDOUBLE ) ) otherlv_5= ',' ( (lv_topLeftY_6_0= ruleDOUBLE ) ) otherlv_7= ')' otherlv_8= 'width' ( (lv_width_9_0= ruleDOUBLE ) ) otherlv_10= 'height' ( (lv_height_11_0= ruleDOUBLE ) ) otherlv_12= '}' ) ;
    public final EObject rulePatrolZoneConfig() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Token otherlv_8=null;
        Token otherlv_10=null;
        Token otherlv_12=null;
        AntlrDatatypeRuleToken lv_topLeftX_4_0 = null;

        AntlrDatatypeRuleToken lv_topLeftY_6_0 = null;

        AntlrDatatypeRuleToken lv_width_9_0 = null;

        AntlrDatatypeRuleToken lv_height_11_0 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:1394:2: ( (otherlv_0= 'zone' otherlv_1= '{' otherlv_2= 'topLeft' otherlv_3= '(' ( (lv_topLeftX_4_0= ruleDOUBLE ) ) otherlv_5= ',' ( (lv_topLeftY_6_0= ruleDOUBLE ) ) otherlv_7= ')' otherlv_8= 'width' ( (lv_width_9_0= ruleDOUBLE ) ) otherlv_10= 'height' ( (lv_height_11_0= ruleDOUBLE ) ) otherlv_12= '}' ) )
            // InternalMazeDsl.g:1395:2: (otherlv_0= 'zone' otherlv_1= '{' otherlv_2= 'topLeft' otherlv_3= '(' ( (lv_topLeftX_4_0= ruleDOUBLE ) ) otherlv_5= ',' ( (lv_topLeftY_6_0= ruleDOUBLE ) ) otherlv_7= ')' otherlv_8= 'width' ( (lv_width_9_0= ruleDOUBLE ) ) otherlv_10= 'height' ( (lv_height_11_0= ruleDOUBLE ) ) otherlv_12= '}' )
            {
            // InternalMazeDsl.g:1395:2: (otherlv_0= 'zone' otherlv_1= '{' otherlv_2= 'topLeft' otherlv_3= '(' ( (lv_topLeftX_4_0= ruleDOUBLE ) ) otherlv_5= ',' ( (lv_topLeftY_6_0= ruleDOUBLE ) ) otherlv_7= ')' otherlv_8= 'width' ( (lv_width_9_0= ruleDOUBLE ) ) otherlv_10= 'height' ( (lv_height_11_0= ruleDOUBLE ) ) otherlv_12= '}' )
            // InternalMazeDsl.g:1396:3: otherlv_0= 'zone' otherlv_1= '{' otherlv_2= 'topLeft' otherlv_3= '(' ( (lv_topLeftX_4_0= ruleDOUBLE ) ) otherlv_5= ',' ( (lv_topLeftY_6_0= ruleDOUBLE ) ) otherlv_7= ')' otherlv_8= 'width' ( (lv_width_9_0= ruleDOUBLE ) ) otherlv_10= 'height' ( (lv_height_11_0= ruleDOUBLE ) ) otherlv_12= '}'
            {
            otherlv_0=(Token)match(input,52,FOLLOW_4); 

            			newLeafNode(otherlv_0, grammarAccess.getPatrolZoneConfigAccess().getZoneKeyword_0());
            		
            otherlv_1=(Token)match(input,12,FOLLOW_55); 

            			newLeafNode(otherlv_1, grammarAccess.getPatrolZoneConfigAccess().getLeftCurlyBracketKeyword_1());
            		
            otherlv_2=(Token)match(input,53,FOLLOW_53); 

            			newLeafNode(otherlv_2, grammarAccess.getPatrolZoneConfigAccess().getTopLeftKeyword_2());
            		
            otherlv_3=(Token)match(input,54,FOLLOW_16); 

            			newLeafNode(otherlv_3, grammarAccess.getPatrolZoneConfigAccess().getLeftParenthesisKeyword_3());
            		
            // InternalMazeDsl.g:1412:3: ( (lv_topLeftX_4_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:1413:4: (lv_topLeftX_4_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:1413:4: (lv_topLeftX_4_0= ruleDOUBLE )
            // InternalMazeDsl.g:1414:5: lv_topLeftX_4_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getPatrolZoneConfigAccess().getTopLeftXDOUBLEParserRuleCall_4_0());
            				
            pushFollow(FOLLOW_56);
            lv_topLeftX_4_0=ruleDOUBLE();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getPatrolZoneConfigRule());
            					}
            					set(
            						current,
            						"topLeftX",
            						lv_topLeftX_4_0,
            						"main.game.maze.dsl.MazeDsl.DOUBLE");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_5=(Token)match(input,50,FOLLOW_16); 

            			newLeafNode(otherlv_5, grammarAccess.getPatrolZoneConfigAccess().getCommaKeyword_5());
            		
            // InternalMazeDsl.g:1435:3: ( (lv_topLeftY_6_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:1436:4: (lv_topLeftY_6_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:1436:4: (lv_topLeftY_6_0= ruleDOUBLE )
            // InternalMazeDsl.g:1437:5: lv_topLeftY_6_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getPatrolZoneConfigAccess().getTopLeftYDOUBLEParserRuleCall_6_0());
            				
            pushFollow(FOLLOW_57);
            lv_topLeftY_6_0=ruleDOUBLE();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getPatrolZoneConfigRule());
            					}
            					set(
            						current,
            						"topLeftY",
            						lv_topLeftY_6_0,
            						"main.game.maze.dsl.MazeDsl.DOUBLE");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_7=(Token)match(input,55,FOLLOW_58); 

            			newLeafNode(otherlv_7, grammarAccess.getPatrolZoneConfigAccess().getRightParenthesisKeyword_7());
            		
            otherlv_8=(Token)match(input,56,FOLLOW_16); 

            			newLeafNode(otherlv_8, grammarAccess.getPatrolZoneConfigAccess().getWidthKeyword_8());
            		
            // InternalMazeDsl.g:1462:3: ( (lv_width_9_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:1463:4: (lv_width_9_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:1463:4: (lv_width_9_0= ruleDOUBLE )
            // InternalMazeDsl.g:1464:5: lv_width_9_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getPatrolZoneConfigAccess().getWidthDOUBLEParserRuleCall_9_0());
            				
            pushFollow(FOLLOW_59);
            lv_width_9_0=ruleDOUBLE();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getPatrolZoneConfigRule());
            					}
            					set(
            						current,
            						"width",
            						lv_width_9_0,
            						"main.game.maze.dsl.MazeDsl.DOUBLE");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_10=(Token)match(input,57,FOLLOW_16); 

            			newLeafNode(otherlv_10, grammarAccess.getPatrolZoneConfigAccess().getHeightKeyword_10());
            		
            // InternalMazeDsl.g:1485:3: ( (lv_height_11_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:1486:4: (lv_height_11_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:1486:4: (lv_height_11_0= ruleDOUBLE )
            // InternalMazeDsl.g:1487:5: lv_height_11_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getPatrolZoneConfigAccess().getHeightDOUBLEParserRuleCall_11_0());
            				
            pushFollow(FOLLOW_34);
            lv_height_11_0=ruleDOUBLE();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getPatrolZoneConfigRule());
            					}
            					set(
            						current,
            						"height",
            						lv_height_11_0,
            						"main.game.maze.dsl.MazeDsl.DOUBLE");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_12=(Token)match(input,13,FOLLOW_2); 

            			newLeafNode(otherlv_12, grammarAccess.getPatrolZoneConfigAccess().getRightCurlyBracketKeyword_12());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePatrolZoneConfig"


    // $ANTLR start "entryRuleWaypoint"
    // InternalMazeDsl.g:1512:1: entryRuleWaypoint returns [EObject current=null] : iv_ruleWaypoint= ruleWaypoint EOF ;
    public final EObject entryRuleWaypoint() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleWaypoint = null;


        try {
            // InternalMazeDsl.g:1512:49: (iv_ruleWaypoint= ruleWaypoint EOF )
            // InternalMazeDsl.g:1513:2: iv_ruleWaypoint= ruleWaypoint EOF
            {
             newCompositeNode(grammarAccess.getWaypointRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleWaypoint=ruleWaypoint();

            state._fsp--;

             current =iv_ruleWaypoint; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleWaypoint"


    // $ANTLR start "ruleWaypoint"
    // InternalMazeDsl.g:1519:1: ruleWaypoint returns [EObject current=null] : (otherlv_0= '(' ( (lv_x_1_0= ruleDOUBLE ) ) otherlv_2= ',' ( (lv_y_3_0= ruleDOUBLE ) ) otherlv_4= ')' (otherlv_5= ':' ( (lv_waitTime_6_0= RULE_INT ) ) otherlv_7= 'ms' )? ) ;
    public final EObject ruleWaypoint() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token lv_waitTime_6_0=null;
        Token otherlv_7=null;
        AntlrDatatypeRuleToken lv_x_1_0 = null;

        AntlrDatatypeRuleToken lv_y_3_0 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:1525:2: ( (otherlv_0= '(' ( (lv_x_1_0= ruleDOUBLE ) ) otherlv_2= ',' ( (lv_y_3_0= ruleDOUBLE ) ) otherlv_4= ')' (otherlv_5= ':' ( (lv_waitTime_6_0= RULE_INT ) ) otherlv_7= 'ms' )? ) )
            // InternalMazeDsl.g:1526:2: (otherlv_0= '(' ( (lv_x_1_0= ruleDOUBLE ) ) otherlv_2= ',' ( (lv_y_3_0= ruleDOUBLE ) ) otherlv_4= ')' (otherlv_5= ':' ( (lv_waitTime_6_0= RULE_INT ) ) otherlv_7= 'ms' )? )
            {
            // InternalMazeDsl.g:1526:2: (otherlv_0= '(' ( (lv_x_1_0= ruleDOUBLE ) ) otherlv_2= ',' ( (lv_y_3_0= ruleDOUBLE ) ) otherlv_4= ')' (otherlv_5= ':' ( (lv_waitTime_6_0= RULE_INT ) ) otherlv_7= 'ms' )? )
            // InternalMazeDsl.g:1527:3: otherlv_0= '(' ( (lv_x_1_0= ruleDOUBLE ) ) otherlv_2= ',' ( (lv_y_3_0= ruleDOUBLE ) ) otherlv_4= ')' (otherlv_5= ':' ( (lv_waitTime_6_0= RULE_INT ) ) otherlv_7= 'ms' )?
            {
            otherlv_0=(Token)match(input,54,FOLLOW_16); 

            			newLeafNode(otherlv_0, grammarAccess.getWaypointAccess().getLeftParenthesisKeyword_0());
            		
            // InternalMazeDsl.g:1531:3: ( (lv_x_1_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:1532:4: (lv_x_1_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:1532:4: (lv_x_1_0= ruleDOUBLE )
            // InternalMazeDsl.g:1533:5: lv_x_1_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getWaypointAccess().getXDOUBLEParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_56);
            lv_x_1_0=ruleDOUBLE();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getWaypointRule());
            					}
            					set(
            						current,
            						"x",
            						lv_x_1_0,
            						"main.game.maze.dsl.MazeDsl.DOUBLE");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_2=(Token)match(input,50,FOLLOW_16); 

            			newLeafNode(otherlv_2, grammarAccess.getWaypointAccess().getCommaKeyword_2());
            		
            // InternalMazeDsl.g:1554:3: ( (lv_y_3_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:1555:4: (lv_y_3_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:1555:4: (lv_y_3_0= ruleDOUBLE )
            // InternalMazeDsl.g:1556:5: lv_y_3_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getWaypointAccess().getYDOUBLEParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_57);
            lv_y_3_0=ruleDOUBLE();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getWaypointRule());
            					}
            					set(
            						current,
            						"y",
            						lv_y_3_0,
            						"main.game.maze.dsl.MazeDsl.DOUBLE");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_4=(Token)match(input,55,FOLLOW_60); 

            			newLeafNode(otherlv_4, grammarAccess.getWaypointAccess().getRightParenthesisKeyword_4());
            		
            // InternalMazeDsl.g:1577:3: (otherlv_5= ':' ( (lv_waitTime_6_0= RULE_INT ) ) otherlv_7= 'ms' )?
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( (LA37_0==58) ) {
                alt37=1;
            }
            switch (alt37) {
                case 1 :
                    // InternalMazeDsl.g:1578:4: otherlv_5= ':' ( (lv_waitTime_6_0= RULE_INT ) ) otherlv_7= 'ms'
                    {
                    otherlv_5=(Token)match(input,58,FOLLOW_36); 

                    				newLeafNode(otherlv_5, grammarAccess.getWaypointAccess().getColonKeyword_5_0());
                    			
                    // InternalMazeDsl.g:1582:4: ( (lv_waitTime_6_0= RULE_INT ) )
                    // InternalMazeDsl.g:1583:5: (lv_waitTime_6_0= RULE_INT )
                    {
                    // InternalMazeDsl.g:1583:5: (lv_waitTime_6_0= RULE_INT )
                    // InternalMazeDsl.g:1584:6: lv_waitTime_6_0= RULE_INT
                    {
                    lv_waitTime_6_0=(Token)match(input,RULE_INT,FOLLOW_61); 

                    						newLeafNode(lv_waitTime_6_0, grammarAccess.getWaypointAccess().getWaitTimeINTTerminalRuleCall_5_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getWaypointRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"waitTime",
                    							lv_waitTime_6_0,
                    							"org.eclipse.xtext.common.Terminals.INT");
                    					

                    }


                    }

                    otherlv_7=(Token)match(input,59,FOLLOW_2); 

                    				newLeafNode(otherlv_7, grammarAccess.getWaypointAccess().getMsKeyword_5_2());
                    			

                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleWaypoint"


    // $ANTLR start "entryRuleLootTableConfig"
    // InternalMazeDsl.g:1609:1: entryRuleLootTableConfig returns [EObject current=null] : iv_ruleLootTableConfig= ruleLootTableConfig EOF ;
    public final EObject entryRuleLootTableConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLootTableConfig = null;


        try {
            // InternalMazeDsl.g:1609:56: (iv_ruleLootTableConfig= ruleLootTableConfig EOF )
            // InternalMazeDsl.g:1610:2: iv_ruleLootTableConfig= ruleLootTableConfig EOF
            {
             newCompositeNode(grammarAccess.getLootTableConfigRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleLootTableConfig=ruleLootTableConfig();

            state._fsp--;

             current =iv_ruleLootTableConfig; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleLootTableConfig"


    // $ANTLR start "ruleLootTableConfig"
    // InternalMazeDsl.g:1616:1: ruleLootTableConfig returns [EObject current=null] : (otherlv_0= 'loot-table' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' (otherlv_3= 'capacity' ( (lv_capacity_4_0= RULE_INT ) ) )? ( (lv_items_5_0= ruleLootItemConfig ) )+ otherlv_6= '}' ) ;
    public final EObject ruleLootTableConfig() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token lv_capacity_4_0=null;
        Token otherlv_6=null;
        EObject lv_items_5_0 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:1622:2: ( (otherlv_0= 'loot-table' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' (otherlv_3= 'capacity' ( (lv_capacity_4_0= RULE_INT ) ) )? ( (lv_items_5_0= ruleLootItemConfig ) )+ otherlv_6= '}' ) )
            // InternalMazeDsl.g:1623:2: (otherlv_0= 'loot-table' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' (otherlv_3= 'capacity' ( (lv_capacity_4_0= RULE_INT ) ) )? ( (lv_items_5_0= ruleLootItemConfig ) )+ otherlv_6= '}' )
            {
            // InternalMazeDsl.g:1623:2: (otherlv_0= 'loot-table' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' (otherlv_3= 'capacity' ( (lv_capacity_4_0= RULE_INT ) ) )? ( (lv_items_5_0= ruleLootItemConfig ) )+ otherlv_6= '}' )
            // InternalMazeDsl.g:1624:3: otherlv_0= 'loot-table' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' (otherlv_3= 'capacity' ( (lv_capacity_4_0= RULE_INT ) ) )? ( (lv_items_5_0= ruleLootItemConfig ) )+ otherlv_6= '}'
            {
            otherlv_0=(Token)match(input,60,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getLootTableConfigAccess().getLootTableKeyword_0());
            		
            // InternalMazeDsl.g:1628:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalMazeDsl.g:1629:4: (lv_name_1_0= RULE_ID )
            {
            // InternalMazeDsl.g:1629:4: (lv_name_1_0= RULE_ID )
            // InternalMazeDsl.g:1630:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_4); 

            					newLeafNode(lv_name_1_0, grammarAccess.getLootTableConfigAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getLootTableConfigRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            otherlv_2=(Token)match(input,12,FOLLOW_62); 

            			newLeafNode(otherlv_2, grammarAccess.getLootTableConfigAccess().getLeftCurlyBracketKeyword_2());
            		
            // InternalMazeDsl.g:1650:3: (otherlv_3= 'capacity' ( (lv_capacity_4_0= RULE_INT ) ) )?
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==61) ) {
                alt38=1;
            }
            switch (alt38) {
                case 1 :
                    // InternalMazeDsl.g:1651:4: otherlv_3= 'capacity' ( (lv_capacity_4_0= RULE_INT ) )
                    {
                    otherlv_3=(Token)match(input,61,FOLLOW_36); 

                    				newLeafNode(otherlv_3, grammarAccess.getLootTableConfigAccess().getCapacityKeyword_3_0());
                    			
                    // InternalMazeDsl.g:1655:4: ( (lv_capacity_4_0= RULE_INT ) )
                    // InternalMazeDsl.g:1656:5: (lv_capacity_4_0= RULE_INT )
                    {
                    // InternalMazeDsl.g:1656:5: (lv_capacity_4_0= RULE_INT )
                    // InternalMazeDsl.g:1657:6: lv_capacity_4_0= RULE_INT
                    {
                    lv_capacity_4_0=(Token)match(input,RULE_INT,FOLLOW_62); 

                    						newLeafNode(lv_capacity_4_0, grammarAccess.getLootTableConfigAccess().getCapacityINTTerminalRuleCall_3_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getLootTableConfigRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"capacity",
                    							lv_capacity_4_0,
                    							"org.eclipse.xtext.common.Terminals.INT");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeDsl.g:1674:3: ( (lv_items_5_0= ruleLootItemConfig ) )+
            int cnt39=0;
            loop39:
            do {
                int alt39=2;
                int LA39_0 = input.LA(1);

                if ( (LA39_0==62) ) {
                    alt39=1;
                }


                switch (alt39) {
            	case 1 :
            	    // InternalMazeDsl.g:1675:4: (lv_items_5_0= ruleLootItemConfig )
            	    {
            	    // InternalMazeDsl.g:1675:4: (lv_items_5_0= ruleLootItemConfig )
            	    // InternalMazeDsl.g:1676:5: lv_items_5_0= ruleLootItemConfig
            	    {

            	    					newCompositeNode(grammarAccess.getLootTableConfigAccess().getItemsLootItemConfigParserRuleCall_4_0());
            	    				
            	    pushFollow(FOLLOW_63);
            	    lv_items_5_0=ruleLootItemConfig();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getLootTableConfigRule());
            	    					}
            	    					add(
            	    						current,
            	    						"items",
            	    						lv_items_5_0,
            	    						"main.game.maze.dsl.MazeDsl.LootItemConfig");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt39 >= 1 ) break loop39;
                        EarlyExitException eee =
                            new EarlyExitException(39, input);
                        throw eee;
                }
                cnt39++;
            } while (true);

            otherlv_6=(Token)match(input,13,FOLLOW_2); 

            			newLeafNode(otherlv_6, grammarAccess.getLootTableConfigAccess().getRightCurlyBracketKeyword_5());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleLootTableConfig"


    // $ANTLR start "entryRuleLootItemConfig"
    // InternalMazeDsl.g:1701:1: entryRuleLootItemConfig returns [EObject current=null] : iv_ruleLootItemConfig= ruleLootItemConfig EOF ;
    public final EObject entryRuleLootItemConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLootItemConfig = null;


        try {
            // InternalMazeDsl.g:1701:55: (iv_ruleLootItemConfig= ruleLootItemConfig EOF )
            // InternalMazeDsl.g:1702:2: iv_ruleLootItemConfig= ruleLootItemConfig EOF
            {
             newCompositeNode(grammarAccess.getLootItemConfigRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleLootItemConfig=ruleLootItemConfig();

            state._fsp--;

             current =iv_ruleLootItemConfig; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleLootItemConfig"


    // $ANTLR start "ruleLootItemConfig"
    // InternalMazeDsl.g:1708:1: ruleLootItemConfig returns [EObject current=null] : (otherlv_0= 'item' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'type' ( (lv_type_4_0= ruleLootItemTypeEnum ) ) otherlv_5= 'value' ( (lv_value_6_0= RULE_INT ) ) (otherlv_7= 'weight' ( (lv_weight_8_0= RULE_INT ) ) )? otherlv_9= '}' ) ;
    public final EObject ruleLootItemConfig() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token lv_value_6_0=null;
        Token otherlv_7=null;
        Token lv_weight_8_0=null;
        Token otherlv_9=null;
        Enumerator lv_type_4_0 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:1714:2: ( (otherlv_0= 'item' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'type' ( (lv_type_4_0= ruleLootItemTypeEnum ) ) otherlv_5= 'value' ( (lv_value_6_0= RULE_INT ) ) (otherlv_7= 'weight' ( (lv_weight_8_0= RULE_INT ) ) )? otherlv_9= '}' ) )
            // InternalMazeDsl.g:1715:2: (otherlv_0= 'item' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'type' ( (lv_type_4_0= ruleLootItemTypeEnum ) ) otherlv_5= 'value' ( (lv_value_6_0= RULE_INT ) ) (otherlv_7= 'weight' ( (lv_weight_8_0= RULE_INT ) ) )? otherlv_9= '}' )
            {
            // InternalMazeDsl.g:1715:2: (otherlv_0= 'item' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'type' ( (lv_type_4_0= ruleLootItemTypeEnum ) ) otherlv_5= 'value' ( (lv_value_6_0= RULE_INT ) ) (otherlv_7= 'weight' ( (lv_weight_8_0= RULE_INT ) ) )? otherlv_9= '}' )
            // InternalMazeDsl.g:1716:3: otherlv_0= 'item' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'type' ( (lv_type_4_0= ruleLootItemTypeEnum ) ) otherlv_5= 'value' ( (lv_value_6_0= RULE_INT ) ) (otherlv_7= 'weight' ( (lv_weight_8_0= RULE_INT ) ) )? otherlv_9= '}'
            {
            otherlv_0=(Token)match(input,62,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getLootItemConfigAccess().getItemKeyword_0());
            		
            // InternalMazeDsl.g:1720:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalMazeDsl.g:1721:4: (lv_name_1_0= RULE_ID )
            {
            // InternalMazeDsl.g:1721:4: (lv_name_1_0= RULE_ID )
            // InternalMazeDsl.g:1722:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_4); 

            					newLeafNode(lv_name_1_0, grammarAccess.getLootItemConfigAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getLootItemConfigRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            otherlv_2=(Token)match(input,12,FOLLOW_22); 

            			newLeafNode(otherlv_2, grammarAccess.getLootItemConfigAccess().getLeftCurlyBracketKeyword_2());
            		
            otherlv_3=(Token)match(input,25,FOLLOW_64); 

            			newLeafNode(otherlv_3, grammarAccess.getLootItemConfigAccess().getTypeKeyword_3());
            		
            // InternalMazeDsl.g:1746:3: ( (lv_type_4_0= ruleLootItemTypeEnum ) )
            // InternalMazeDsl.g:1747:4: (lv_type_4_0= ruleLootItemTypeEnum )
            {
            // InternalMazeDsl.g:1747:4: (lv_type_4_0= ruleLootItemTypeEnum )
            // InternalMazeDsl.g:1748:5: lv_type_4_0= ruleLootItemTypeEnum
            {

            					newCompositeNode(grammarAccess.getLootItemConfigAccess().getTypeLootItemTypeEnumEnumRuleCall_4_0());
            				
            pushFollow(FOLLOW_65);
            lv_type_4_0=ruleLootItemTypeEnum();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getLootItemConfigRule());
            					}
            					set(
            						current,
            						"type",
            						lv_type_4_0,
            						"main.game.maze.dsl.MazeDsl.LootItemTypeEnum");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_5=(Token)match(input,63,FOLLOW_36); 

            			newLeafNode(otherlv_5, grammarAccess.getLootItemConfigAccess().getValueKeyword_5());
            		
            // InternalMazeDsl.g:1769:3: ( (lv_value_6_0= RULE_INT ) )
            // InternalMazeDsl.g:1770:4: (lv_value_6_0= RULE_INT )
            {
            // InternalMazeDsl.g:1770:4: (lv_value_6_0= RULE_INT )
            // InternalMazeDsl.g:1771:5: lv_value_6_0= RULE_INT
            {
            lv_value_6_0=(Token)match(input,RULE_INT,FOLLOW_66); 

            					newLeafNode(lv_value_6_0, grammarAccess.getLootItemConfigAccess().getValueINTTerminalRuleCall_6_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getLootItemConfigRule());
            					}
            					setWithLastConsumed(
            						current,
            						"value",
            						lv_value_6_0,
            						"org.eclipse.xtext.common.Terminals.INT");
            				

            }


            }

            // InternalMazeDsl.g:1787:3: (otherlv_7= 'weight' ( (lv_weight_8_0= RULE_INT ) ) )?
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( (LA40_0==64) ) {
                alt40=1;
            }
            switch (alt40) {
                case 1 :
                    // InternalMazeDsl.g:1788:4: otherlv_7= 'weight' ( (lv_weight_8_0= RULE_INT ) )
                    {
                    otherlv_7=(Token)match(input,64,FOLLOW_36); 

                    				newLeafNode(otherlv_7, grammarAccess.getLootItemConfigAccess().getWeightKeyword_7_0());
                    			
                    // InternalMazeDsl.g:1792:4: ( (lv_weight_8_0= RULE_INT ) )
                    // InternalMazeDsl.g:1793:5: (lv_weight_8_0= RULE_INT )
                    {
                    // InternalMazeDsl.g:1793:5: (lv_weight_8_0= RULE_INT )
                    // InternalMazeDsl.g:1794:6: lv_weight_8_0= RULE_INT
                    {
                    lv_weight_8_0=(Token)match(input,RULE_INT,FOLLOW_34); 

                    						newLeafNode(lv_weight_8_0, grammarAccess.getLootItemConfigAccess().getWeightINTTerminalRuleCall_7_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getLootItemConfigRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"weight",
                    							lv_weight_8_0,
                    							"org.eclipse.xtext.common.Terminals.INT");
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_9=(Token)match(input,13,FOLLOW_2); 

            			newLeafNode(otherlv_9, grammarAccess.getLootItemConfigAccess().getRightCurlyBracketKeyword_8());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleLootItemConfig"


    // $ANTLR start "entryRuleLevelConfig"
    // InternalMazeDsl.g:1819:1: entryRuleLevelConfig returns [EObject current=null] : iv_ruleLevelConfig= ruleLevelConfig EOF ;
    public final EObject entryRuleLevelConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLevelConfig = null;


        try {
            // InternalMazeDsl.g:1819:52: (iv_ruleLevelConfig= ruleLevelConfig EOF )
            // InternalMazeDsl.g:1820:2: iv_ruleLevelConfig= ruleLevelConfig EOF
            {
             newCompositeNode(grammarAccess.getLevelConfigRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleLevelConfig=ruleLevelConfig();

            state._fsp--;

             current =iv_ruleLevelConfig; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleLevelConfig"


    // $ANTLR start "ruleLevelConfig"
    // InternalMazeDsl.g:1826:1: ruleLevelConfig returns [EObject current=null] : (otherlv_0= 'level' ( (lv_number_1_0= RULE_INT ) ) ( (lv_name_2_0= RULE_STRING ) ) otherlv_3= '{' (otherlv_4= 'basedOn' ( (lv_baseLevel_5_0= RULE_INT ) ) )? (otherlv_6= 'threatCapacity' ( (lv_threatCapacityFactor_7_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_8= 'wallMaterials' ( (lv_showWallMaterials_9_0= ruleBOOLEAN_OBJECT ) ) )? (otherlv_10= 'inheritWeapons' ( (lv_inheritWeapons_11_0= ruleBOOLEAN_OBJECT ) ) )? (otherlv_12= 'cascadeDepth' ( (lv_cascadeDepth_13_0= ruleINT_OBJECT ) ) )? ( (lv_zones_14_0= ruleLevelZone ) )* ( (lv_sergeant_15_0= ruleSergeantConfig ) )? ( (lv_sergeantPosts_16_0= ruleTierCountConfig ) )* ( (lv_mines_17_0= ruleMineRingConfig ) )? ( (lv_portalGhost_18_0= rulePortalGhostConfig ) )? ( (lv_ghostCounts_19_0= ruleGhostCountConfig ) )* ( (lv_teleport_20_0= ruleTeleportRulesConfig ) )? otherlv_21= '}' ) ;
    public final EObject ruleLevelConfig() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_number_1_0=null;
        Token lv_name_2_0=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token lv_baseLevel_5_0=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        Token otherlv_10=null;
        Token otherlv_12=null;
        Token otherlv_21=null;
        AntlrDatatypeRuleToken lv_threatCapacityFactor_7_0 = null;

        AntlrDatatypeRuleToken lv_showWallMaterials_9_0 = null;

        AntlrDatatypeRuleToken lv_inheritWeapons_11_0 = null;

        AntlrDatatypeRuleToken lv_cascadeDepth_13_0 = null;

        EObject lv_zones_14_0 = null;

        EObject lv_sergeant_15_0 = null;

        EObject lv_sergeantPosts_16_0 = null;

        EObject lv_mines_17_0 = null;

        EObject lv_portalGhost_18_0 = null;

        EObject lv_ghostCounts_19_0 = null;

        EObject lv_teleport_20_0 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:1832:2: ( (otherlv_0= 'level' ( (lv_number_1_0= RULE_INT ) ) ( (lv_name_2_0= RULE_STRING ) ) otherlv_3= '{' (otherlv_4= 'basedOn' ( (lv_baseLevel_5_0= RULE_INT ) ) )? (otherlv_6= 'threatCapacity' ( (lv_threatCapacityFactor_7_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_8= 'wallMaterials' ( (lv_showWallMaterials_9_0= ruleBOOLEAN_OBJECT ) ) )? (otherlv_10= 'inheritWeapons' ( (lv_inheritWeapons_11_0= ruleBOOLEAN_OBJECT ) ) )? (otherlv_12= 'cascadeDepth' ( (lv_cascadeDepth_13_0= ruleINT_OBJECT ) ) )? ( (lv_zones_14_0= ruleLevelZone ) )* ( (lv_sergeant_15_0= ruleSergeantConfig ) )? ( (lv_sergeantPosts_16_0= ruleTierCountConfig ) )* ( (lv_mines_17_0= ruleMineRingConfig ) )? ( (lv_portalGhost_18_0= rulePortalGhostConfig ) )? ( (lv_ghostCounts_19_0= ruleGhostCountConfig ) )* ( (lv_teleport_20_0= ruleTeleportRulesConfig ) )? otherlv_21= '}' ) )
            // InternalMazeDsl.g:1833:2: (otherlv_0= 'level' ( (lv_number_1_0= RULE_INT ) ) ( (lv_name_2_0= RULE_STRING ) ) otherlv_3= '{' (otherlv_4= 'basedOn' ( (lv_baseLevel_5_0= RULE_INT ) ) )? (otherlv_6= 'threatCapacity' ( (lv_threatCapacityFactor_7_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_8= 'wallMaterials' ( (lv_showWallMaterials_9_0= ruleBOOLEAN_OBJECT ) ) )? (otherlv_10= 'inheritWeapons' ( (lv_inheritWeapons_11_0= ruleBOOLEAN_OBJECT ) ) )? (otherlv_12= 'cascadeDepth' ( (lv_cascadeDepth_13_0= ruleINT_OBJECT ) ) )? ( (lv_zones_14_0= ruleLevelZone ) )* ( (lv_sergeant_15_0= ruleSergeantConfig ) )? ( (lv_sergeantPosts_16_0= ruleTierCountConfig ) )* ( (lv_mines_17_0= ruleMineRingConfig ) )? ( (lv_portalGhost_18_0= rulePortalGhostConfig ) )? ( (lv_ghostCounts_19_0= ruleGhostCountConfig ) )* ( (lv_teleport_20_0= ruleTeleportRulesConfig ) )? otherlv_21= '}' )
            {
            // InternalMazeDsl.g:1833:2: (otherlv_0= 'level' ( (lv_number_1_0= RULE_INT ) ) ( (lv_name_2_0= RULE_STRING ) ) otherlv_3= '{' (otherlv_4= 'basedOn' ( (lv_baseLevel_5_0= RULE_INT ) ) )? (otherlv_6= 'threatCapacity' ( (lv_threatCapacityFactor_7_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_8= 'wallMaterials' ( (lv_showWallMaterials_9_0= ruleBOOLEAN_OBJECT ) ) )? (otherlv_10= 'inheritWeapons' ( (lv_inheritWeapons_11_0= ruleBOOLEAN_OBJECT ) ) )? (otherlv_12= 'cascadeDepth' ( (lv_cascadeDepth_13_0= ruleINT_OBJECT ) ) )? ( (lv_zones_14_0= ruleLevelZone ) )* ( (lv_sergeant_15_0= ruleSergeantConfig ) )? ( (lv_sergeantPosts_16_0= ruleTierCountConfig ) )* ( (lv_mines_17_0= ruleMineRingConfig ) )? ( (lv_portalGhost_18_0= rulePortalGhostConfig ) )? ( (lv_ghostCounts_19_0= ruleGhostCountConfig ) )* ( (lv_teleport_20_0= ruleTeleportRulesConfig ) )? otherlv_21= '}' )
            // InternalMazeDsl.g:1834:3: otherlv_0= 'level' ( (lv_number_1_0= RULE_INT ) ) ( (lv_name_2_0= RULE_STRING ) ) otherlv_3= '{' (otherlv_4= 'basedOn' ( (lv_baseLevel_5_0= RULE_INT ) ) )? (otherlv_6= 'threatCapacity' ( (lv_threatCapacityFactor_7_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_8= 'wallMaterials' ( (lv_showWallMaterials_9_0= ruleBOOLEAN_OBJECT ) ) )? (otherlv_10= 'inheritWeapons' ( (lv_inheritWeapons_11_0= ruleBOOLEAN_OBJECT ) ) )? (otherlv_12= 'cascadeDepth' ( (lv_cascadeDepth_13_0= ruleINT_OBJECT ) ) )? ( (lv_zones_14_0= ruleLevelZone ) )* ( (lv_sergeant_15_0= ruleSergeantConfig ) )? ( (lv_sergeantPosts_16_0= ruleTierCountConfig ) )* ( (lv_mines_17_0= ruleMineRingConfig ) )? ( (lv_portalGhost_18_0= rulePortalGhostConfig ) )? ( (lv_ghostCounts_19_0= ruleGhostCountConfig ) )* ( (lv_teleport_20_0= ruleTeleportRulesConfig ) )? otherlv_21= '}'
            {
            otherlv_0=(Token)match(input,16,FOLLOW_36); 

            			newLeafNode(otherlv_0, grammarAccess.getLevelConfigAccess().getLevelKeyword_0());
            		
            // InternalMazeDsl.g:1838:3: ( (lv_number_1_0= RULE_INT ) )
            // InternalMazeDsl.g:1839:4: (lv_number_1_0= RULE_INT )
            {
            // InternalMazeDsl.g:1839:4: (lv_number_1_0= RULE_INT )
            // InternalMazeDsl.g:1840:5: lv_number_1_0= RULE_INT
            {
            lv_number_1_0=(Token)match(input,RULE_INT,FOLLOW_10); 

            					newLeafNode(lv_number_1_0, grammarAccess.getLevelConfigAccess().getNumberINTTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getLevelConfigRule());
            					}
            					setWithLastConsumed(
            						current,
            						"number",
            						lv_number_1_0,
            						"org.eclipse.xtext.common.Terminals.INT");
            				

            }


            }

            // InternalMazeDsl.g:1856:3: ( (lv_name_2_0= RULE_STRING ) )
            // InternalMazeDsl.g:1857:4: (lv_name_2_0= RULE_STRING )
            {
            // InternalMazeDsl.g:1857:4: (lv_name_2_0= RULE_STRING )
            // InternalMazeDsl.g:1858:5: lv_name_2_0= RULE_STRING
            {
            lv_name_2_0=(Token)match(input,RULE_STRING,FOLLOW_4); 

            					newLeafNode(lv_name_2_0, grammarAccess.getLevelConfigAccess().getNameSTRINGTerminalRuleCall_2_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getLevelConfigRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_2_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            otherlv_3=(Token)match(input,12,FOLLOW_67); 

            			newLeafNode(otherlv_3, grammarAccess.getLevelConfigAccess().getLeftCurlyBracketKeyword_3());
            		
            // InternalMazeDsl.g:1878:3: (otherlv_4= 'basedOn' ( (lv_baseLevel_5_0= RULE_INT ) ) )?
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( (LA41_0==65) ) {
                alt41=1;
            }
            switch (alt41) {
                case 1 :
                    // InternalMazeDsl.g:1879:4: otherlv_4= 'basedOn' ( (lv_baseLevel_5_0= RULE_INT ) )
                    {
                    otherlv_4=(Token)match(input,65,FOLLOW_36); 

                    				newLeafNode(otherlv_4, grammarAccess.getLevelConfigAccess().getBasedOnKeyword_4_0());
                    			
                    // InternalMazeDsl.g:1883:4: ( (lv_baseLevel_5_0= RULE_INT ) )
                    // InternalMazeDsl.g:1884:5: (lv_baseLevel_5_0= RULE_INT )
                    {
                    // InternalMazeDsl.g:1884:5: (lv_baseLevel_5_0= RULE_INT )
                    // InternalMazeDsl.g:1885:6: lv_baseLevel_5_0= RULE_INT
                    {
                    lv_baseLevel_5_0=(Token)match(input,RULE_INT,FOLLOW_68); 

                    						newLeafNode(lv_baseLevel_5_0, grammarAccess.getLevelConfigAccess().getBaseLevelINTTerminalRuleCall_4_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getLevelConfigRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"baseLevel",
                    							lv_baseLevel_5_0,
                    							"org.eclipse.xtext.common.Terminals.INT");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeDsl.g:1902:3: (otherlv_6= 'threatCapacity' ( (lv_threatCapacityFactor_7_0= ruleDOUBLE_OBJECT ) ) )?
            int alt42=2;
            int LA42_0 = input.LA(1);

            if ( (LA42_0==66) ) {
                alt42=1;
            }
            switch (alt42) {
                case 1 :
                    // InternalMazeDsl.g:1903:4: otherlv_6= 'threatCapacity' ( (lv_threatCapacityFactor_7_0= ruleDOUBLE_OBJECT ) )
                    {
                    otherlv_6=(Token)match(input,66,FOLLOW_16); 

                    				newLeafNode(otherlv_6, grammarAccess.getLevelConfigAccess().getThreatCapacityKeyword_5_0());
                    			
                    // InternalMazeDsl.g:1907:4: ( (lv_threatCapacityFactor_7_0= ruleDOUBLE_OBJECT ) )
                    // InternalMazeDsl.g:1908:5: (lv_threatCapacityFactor_7_0= ruleDOUBLE_OBJECT )
                    {
                    // InternalMazeDsl.g:1908:5: (lv_threatCapacityFactor_7_0= ruleDOUBLE_OBJECT )
                    // InternalMazeDsl.g:1909:6: lv_threatCapacityFactor_7_0= ruleDOUBLE_OBJECT
                    {

                    						newCompositeNode(grammarAccess.getLevelConfigAccess().getThreatCapacityFactorDOUBLE_OBJECTParserRuleCall_5_1_0());
                    					
                    pushFollow(FOLLOW_69);
                    lv_threatCapacityFactor_7_0=ruleDOUBLE_OBJECT();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getLevelConfigRule());
                    						}
                    						set(
                    							current,
                    							"threatCapacityFactor",
                    							lv_threatCapacityFactor_7_0,
                    							"main.game.maze.dsl.MazeDsl.DOUBLE_OBJECT");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeDsl.g:1927:3: (otherlv_8= 'wallMaterials' ( (lv_showWallMaterials_9_0= ruleBOOLEAN_OBJECT ) ) )?
            int alt43=2;
            int LA43_0 = input.LA(1);

            if ( (LA43_0==67) ) {
                alt43=1;
            }
            switch (alt43) {
                case 1 :
                    // InternalMazeDsl.g:1928:4: otherlv_8= 'wallMaterials' ( (lv_showWallMaterials_9_0= ruleBOOLEAN_OBJECT ) )
                    {
                    otherlv_8=(Token)match(input,67,FOLLOW_28); 

                    				newLeafNode(otherlv_8, grammarAccess.getLevelConfigAccess().getWallMaterialsKeyword_6_0());
                    			
                    // InternalMazeDsl.g:1932:4: ( (lv_showWallMaterials_9_0= ruleBOOLEAN_OBJECT ) )
                    // InternalMazeDsl.g:1933:5: (lv_showWallMaterials_9_0= ruleBOOLEAN_OBJECT )
                    {
                    // InternalMazeDsl.g:1933:5: (lv_showWallMaterials_9_0= ruleBOOLEAN_OBJECT )
                    // InternalMazeDsl.g:1934:6: lv_showWallMaterials_9_0= ruleBOOLEAN_OBJECT
                    {

                    						newCompositeNode(grammarAccess.getLevelConfigAccess().getShowWallMaterialsBOOLEAN_OBJECTParserRuleCall_6_1_0());
                    					
                    pushFollow(FOLLOW_70);
                    lv_showWallMaterials_9_0=ruleBOOLEAN_OBJECT();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getLevelConfigRule());
                    						}
                    						set(
                    							current,
                    							"showWallMaterials",
                    							lv_showWallMaterials_9_0,
                    							"main.game.maze.dsl.MazeDsl.BOOLEAN_OBJECT");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeDsl.g:1952:3: (otherlv_10= 'inheritWeapons' ( (lv_inheritWeapons_11_0= ruleBOOLEAN_OBJECT ) ) )?
            int alt44=2;
            int LA44_0 = input.LA(1);

            if ( (LA44_0==68) ) {
                alt44=1;
            }
            switch (alt44) {
                case 1 :
                    // InternalMazeDsl.g:1953:4: otherlv_10= 'inheritWeapons' ( (lv_inheritWeapons_11_0= ruleBOOLEAN_OBJECT ) )
                    {
                    otherlv_10=(Token)match(input,68,FOLLOW_28); 

                    				newLeafNode(otherlv_10, grammarAccess.getLevelConfigAccess().getInheritWeaponsKeyword_7_0());
                    			
                    // InternalMazeDsl.g:1957:4: ( (lv_inheritWeapons_11_0= ruleBOOLEAN_OBJECT ) )
                    // InternalMazeDsl.g:1958:5: (lv_inheritWeapons_11_0= ruleBOOLEAN_OBJECT )
                    {
                    // InternalMazeDsl.g:1958:5: (lv_inheritWeapons_11_0= ruleBOOLEAN_OBJECT )
                    // InternalMazeDsl.g:1959:6: lv_inheritWeapons_11_0= ruleBOOLEAN_OBJECT
                    {

                    						newCompositeNode(grammarAccess.getLevelConfigAccess().getInheritWeaponsBOOLEAN_OBJECTParserRuleCall_7_1_0());
                    					
                    pushFollow(FOLLOW_71);
                    lv_inheritWeapons_11_0=ruleBOOLEAN_OBJECT();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getLevelConfigRule());
                    						}
                    						set(
                    							current,
                    							"inheritWeapons",
                    							lv_inheritWeapons_11_0,
                    							"main.game.maze.dsl.MazeDsl.BOOLEAN_OBJECT");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeDsl.g:1977:3: (otherlv_12= 'cascadeDepth' ( (lv_cascadeDepth_13_0= ruleINT_OBJECT ) ) )?
            int alt45=2;
            int LA45_0 = input.LA(1);

            if ( (LA45_0==69) ) {
                alt45=1;
            }
            switch (alt45) {
                case 1 :
                    // InternalMazeDsl.g:1978:4: otherlv_12= 'cascadeDepth' ( (lv_cascadeDepth_13_0= ruleINT_OBJECT ) )
                    {
                    otherlv_12=(Token)match(input,69,FOLLOW_16); 

                    				newLeafNode(otherlv_12, grammarAccess.getLevelConfigAccess().getCascadeDepthKeyword_8_0());
                    			
                    // InternalMazeDsl.g:1982:4: ( (lv_cascadeDepth_13_0= ruleINT_OBJECT ) )
                    // InternalMazeDsl.g:1983:5: (lv_cascadeDepth_13_0= ruleINT_OBJECT )
                    {
                    // InternalMazeDsl.g:1983:5: (lv_cascadeDepth_13_0= ruleINT_OBJECT )
                    // InternalMazeDsl.g:1984:6: lv_cascadeDepth_13_0= ruleINT_OBJECT
                    {

                    						newCompositeNode(grammarAccess.getLevelConfigAccess().getCascadeDepthINT_OBJECTParserRuleCall_8_1_0());
                    					
                    pushFollow(FOLLOW_72);
                    lv_cascadeDepth_13_0=ruleINT_OBJECT();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getLevelConfigRule());
                    						}
                    						set(
                    							current,
                    							"cascadeDepth",
                    							lv_cascadeDepth_13_0,
                    							"main.game.maze.dsl.MazeDsl.INT_OBJECT");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeDsl.g:2002:3: ( (lv_zones_14_0= ruleLevelZone ) )*
            loop46:
            do {
                int alt46=2;
                int LA46_0 = input.LA(1);

                if ( (LA46_0==70) ) {
                    alt46=1;
                }


                switch (alt46) {
            	case 1 :
            	    // InternalMazeDsl.g:2003:4: (lv_zones_14_0= ruleLevelZone )
            	    {
            	    // InternalMazeDsl.g:2003:4: (lv_zones_14_0= ruleLevelZone )
            	    // InternalMazeDsl.g:2004:5: lv_zones_14_0= ruleLevelZone
            	    {

            	    					newCompositeNode(grammarAccess.getLevelConfigAccess().getZonesLevelZoneParserRuleCall_9_0());
            	    				
            	    pushFollow(FOLLOW_72);
            	    lv_zones_14_0=ruleLevelZone();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getLevelConfigRule());
            	    					}
            	    					add(
            	    						current,
            	    						"zones",
            	    						lv_zones_14_0,
            	    						"main.game.maze.dsl.MazeDsl.LevelZone");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop46;
                }
            } while (true);

            // InternalMazeDsl.g:2021:3: ( (lv_sergeant_15_0= ruleSergeantConfig ) )?
            int alt47=2;
            int LA47_0 = input.LA(1);

            if ( (LA47_0==73) ) {
                alt47=1;
            }
            switch (alt47) {
                case 1 :
                    // InternalMazeDsl.g:2022:4: (lv_sergeant_15_0= ruleSergeantConfig )
                    {
                    // InternalMazeDsl.g:2022:4: (lv_sergeant_15_0= ruleSergeantConfig )
                    // InternalMazeDsl.g:2023:5: lv_sergeant_15_0= ruleSergeantConfig
                    {

                    					newCompositeNode(grammarAccess.getLevelConfigAccess().getSergeantSergeantConfigParserRuleCall_10_0());
                    				
                    pushFollow(FOLLOW_73);
                    lv_sergeant_15_0=ruleSergeantConfig();

                    state._fsp--;


                    					if (current==null) {
                    						current = createModelElementForParent(grammarAccess.getLevelConfigRule());
                    					}
                    					set(
                    						current,
                    						"sergeant",
                    						lv_sergeant_15_0,
                    						"main.game.maze.dsl.MazeDsl.SergeantConfig");
                    					afterParserOrEnumRuleCall();
                    				

                    }


                    }
                    break;

            }

            // InternalMazeDsl.g:2040:3: ( (lv_sergeantPosts_16_0= ruleTierCountConfig ) )*
            loop48:
            do {
                int alt48=2;
                int LA48_0 = input.LA(1);

                if ( (LA48_0==83) ) {
                    alt48=1;
                }


                switch (alt48) {
            	case 1 :
            	    // InternalMazeDsl.g:2041:4: (lv_sergeantPosts_16_0= ruleTierCountConfig )
            	    {
            	    // InternalMazeDsl.g:2041:4: (lv_sergeantPosts_16_0= ruleTierCountConfig )
            	    // InternalMazeDsl.g:2042:5: lv_sergeantPosts_16_0= ruleTierCountConfig
            	    {

            	    					newCompositeNode(grammarAccess.getLevelConfigAccess().getSergeantPostsTierCountConfigParserRuleCall_11_0());
            	    				
            	    pushFollow(FOLLOW_73);
            	    lv_sergeantPosts_16_0=ruleTierCountConfig();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getLevelConfigRule());
            	    					}
            	    					add(
            	    						current,
            	    						"sergeantPosts",
            	    						lv_sergeantPosts_16_0,
            	    						"main.game.maze.dsl.MazeDsl.TierCountConfig");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop48;
                }
            } while (true);

            // InternalMazeDsl.g:2059:3: ( (lv_mines_17_0= ruleMineRingConfig ) )?
            int alt49=2;
            int LA49_0 = input.LA(1);

            if ( (LA49_0==84) ) {
                alt49=1;
            }
            switch (alt49) {
                case 1 :
                    // InternalMazeDsl.g:2060:4: (lv_mines_17_0= ruleMineRingConfig )
                    {
                    // InternalMazeDsl.g:2060:4: (lv_mines_17_0= ruleMineRingConfig )
                    // InternalMazeDsl.g:2061:5: lv_mines_17_0= ruleMineRingConfig
                    {

                    					newCompositeNode(grammarAccess.getLevelConfigAccess().getMinesMineRingConfigParserRuleCall_12_0());
                    				
                    pushFollow(FOLLOW_74);
                    lv_mines_17_0=ruleMineRingConfig();

                    state._fsp--;


                    					if (current==null) {
                    						current = createModelElementForParent(grammarAccess.getLevelConfigRule());
                    					}
                    					set(
                    						current,
                    						"mines",
                    						lv_mines_17_0,
                    						"main.game.maze.dsl.MazeDsl.MineRingConfig");
                    					afterParserOrEnumRuleCall();
                    				

                    }


                    }
                    break;

            }

            // InternalMazeDsl.g:2078:3: ( (lv_portalGhost_18_0= rulePortalGhostConfig ) )?
            int alt50=2;
            int LA50_0 = input.LA(1);

            if ( (LA50_0==93) ) {
                alt50=1;
            }
            switch (alt50) {
                case 1 :
                    // InternalMazeDsl.g:2079:4: (lv_portalGhost_18_0= rulePortalGhostConfig )
                    {
                    // InternalMazeDsl.g:2079:4: (lv_portalGhost_18_0= rulePortalGhostConfig )
                    // InternalMazeDsl.g:2080:5: lv_portalGhost_18_0= rulePortalGhostConfig
                    {

                    					newCompositeNode(grammarAccess.getLevelConfigAccess().getPortalGhostPortalGhostConfigParserRuleCall_13_0());
                    				
                    pushFollow(FOLLOW_75);
                    lv_portalGhost_18_0=rulePortalGhostConfig();

                    state._fsp--;


                    					if (current==null) {
                    						current = createModelElementForParent(grammarAccess.getLevelConfigRule());
                    					}
                    					set(
                    						current,
                    						"portalGhost",
                    						lv_portalGhost_18_0,
                    						"main.game.maze.dsl.MazeDsl.PortalGhostConfig");
                    					afterParserOrEnumRuleCall();
                    				

                    }


                    }
                    break;

            }

            // InternalMazeDsl.g:2097:3: ( (lv_ghostCounts_19_0= ruleGhostCountConfig ) )*
            loop51:
            do {
                int alt51=2;
                int LA51_0 = input.LA(1);

                if ( (LA51_0==94) ) {
                    alt51=1;
                }


                switch (alt51) {
            	case 1 :
            	    // InternalMazeDsl.g:2098:4: (lv_ghostCounts_19_0= ruleGhostCountConfig )
            	    {
            	    // InternalMazeDsl.g:2098:4: (lv_ghostCounts_19_0= ruleGhostCountConfig )
            	    // InternalMazeDsl.g:2099:5: lv_ghostCounts_19_0= ruleGhostCountConfig
            	    {

            	    					newCompositeNode(grammarAccess.getLevelConfigAccess().getGhostCountsGhostCountConfigParserRuleCall_14_0());
            	    				
            	    pushFollow(FOLLOW_75);
            	    lv_ghostCounts_19_0=ruleGhostCountConfig();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getLevelConfigRule());
            	    					}
            	    					add(
            	    						current,
            	    						"ghostCounts",
            	    						lv_ghostCounts_19_0,
            	    						"main.game.maze.dsl.MazeDsl.GhostCountConfig");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop51;
                }
            } while (true);

            // InternalMazeDsl.g:2116:3: ( (lv_teleport_20_0= ruleTeleportRulesConfig ) )?
            int alt52=2;
            int LA52_0 = input.LA(1);

            if ( (LA52_0==95) ) {
                alt52=1;
            }
            switch (alt52) {
                case 1 :
                    // InternalMazeDsl.g:2117:4: (lv_teleport_20_0= ruleTeleportRulesConfig )
                    {
                    // InternalMazeDsl.g:2117:4: (lv_teleport_20_0= ruleTeleportRulesConfig )
                    // InternalMazeDsl.g:2118:5: lv_teleport_20_0= ruleTeleportRulesConfig
                    {

                    					newCompositeNode(grammarAccess.getLevelConfigAccess().getTeleportTeleportRulesConfigParserRuleCall_15_0());
                    				
                    pushFollow(FOLLOW_34);
                    lv_teleport_20_0=ruleTeleportRulesConfig();

                    state._fsp--;


                    					if (current==null) {
                    						current = createModelElementForParent(grammarAccess.getLevelConfigRule());
                    					}
                    					set(
                    						current,
                    						"teleport",
                    						lv_teleport_20_0,
                    						"main.game.maze.dsl.MazeDsl.TeleportRulesConfig");
                    					afterParserOrEnumRuleCall();
                    				

                    }


                    }
                    break;

            }

            otherlv_21=(Token)match(input,13,FOLLOW_2); 

            			newLeafNode(otherlv_21, grammarAccess.getLevelConfigAccess().getRightCurlyBracketKeyword_16());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleLevelConfig"


    // $ANTLR start "entryRuleLevelZone"
    // InternalMazeDsl.g:2143:1: entryRuleLevelZone returns [EObject current=null] : iv_ruleLevelZone= ruleLevelZone EOF ;
    public final EObject entryRuleLevelZone() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLevelZone = null;


        try {
            // InternalMazeDsl.g:2143:50: (iv_ruleLevelZone= ruleLevelZone EOF )
            // InternalMazeDsl.g:2144:2: iv_ruleLevelZone= ruleLevelZone EOF
            {
             newCompositeNode(grammarAccess.getLevelZoneRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleLevelZone=ruleLevelZone();

            state._fsp--;

             current =iv_ruleLevelZone; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleLevelZone"


    // $ANTLR start "ruleLevelZone"
    // InternalMazeDsl.g:2150:1: ruleLevelZone returns [EObject current=null] : (otherlv_0= 'ring' ( (lv_displayName_1_0= RULE_STRING ) ) (otherlv_2= 'share' ( (lv_breakableShare_3_0= ruleDOUBLE_OBJECT ) ) )? otherlv_4= 'materials' ( (lv_materialIds_5_0= RULE_ID ) ) (otherlv_6= ',' ( (lv_materialIds_7_0= RULE_ID ) ) )* ) ;
    public final EObject ruleLevelZone() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_displayName_1_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token lv_materialIds_5_0=null;
        Token otherlv_6=null;
        Token lv_materialIds_7_0=null;
        AntlrDatatypeRuleToken lv_breakableShare_3_0 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:2156:2: ( (otherlv_0= 'ring' ( (lv_displayName_1_0= RULE_STRING ) ) (otherlv_2= 'share' ( (lv_breakableShare_3_0= ruleDOUBLE_OBJECT ) ) )? otherlv_4= 'materials' ( (lv_materialIds_5_0= RULE_ID ) ) (otherlv_6= ',' ( (lv_materialIds_7_0= RULE_ID ) ) )* ) )
            // InternalMazeDsl.g:2157:2: (otherlv_0= 'ring' ( (lv_displayName_1_0= RULE_STRING ) ) (otherlv_2= 'share' ( (lv_breakableShare_3_0= ruleDOUBLE_OBJECT ) ) )? otherlv_4= 'materials' ( (lv_materialIds_5_0= RULE_ID ) ) (otherlv_6= ',' ( (lv_materialIds_7_0= RULE_ID ) ) )* )
            {
            // InternalMazeDsl.g:2157:2: (otherlv_0= 'ring' ( (lv_displayName_1_0= RULE_STRING ) ) (otherlv_2= 'share' ( (lv_breakableShare_3_0= ruleDOUBLE_OBJECT ) ) )? otherlv_4= 'materials' ( (lv_materialIds_5_0= RULE_ID ) ) (otherlv_6= ',' ( (lv_materialIds_7_0= RULE_ID ) ) )* )
            // InternalMazeDsl.g:2158:3: otherlv_0= 'ring' ( (lv_displayName_1_0= RULE_STRING ) ) (otherlv_2= 'share' ( (lv_breakableShare_3_0= ruleDOUBLE_OBJECT ) ) )? otherlv_4= 'materials' ( (lv_materialIds_5_0= RULE_ID ) ) (otherlv_6= ',' ( (lv_materialIds_7_0= RULE_ID ) ) )*
            {
            otherlv_0=(Token)match(input,70,FOLLOW_10); 

            			newLeafNode(otherlv_0, grammarAccess.getLevelZoneAccess().getRingKeyword_0());
            		
            // InternalMazeDsl.g:2162:3: ( (lv_displayName_1_0= RULE_STRING ) )
            // InternalMazeDsl.g:2163:4: (lv_displayName_1_0= RULE_STRING )
            {
            // InternalMazeDsl.g:2163:4: (lv_displayName_1_0= RULE_STRING )
            // InternalMazeDsl.g:2164:5: lv_displayName_1_0= RULE_STRING
            {
            lv_displayName_1_0=(Token)match(input,RULE_STRING,FOLLOW_76); 

            					newLeafNode(lv_displayName_1_0, grammarAccess.getLevelZoneAccess().getDisplayNameSTRINGTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getLevelZoneRule());
            					}
            					setWithLastConsumed(
            						current,
            						"displayName",
            						lv_displayName_1_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            // InternalMazeDsl.g:2180:3: (otherlv_2= 'share' ( (lv_breakableShare_3_0= ruleDOUBLE_OBJECT ) ) )?
            int alt53=2;
            int LA53_0 = input.LA(1);

            if ( (LA53_0==71) ) {
                alt53=1;
            }
            switch (alt53) {
                case 1 :
                    // InternalMazeDsl.g:2181:4: otherlv_2= 'share' ( (lv_breakableShare_3_0= ruleDOUBLE_OBJECT ) )
                    {
                    otherlv_2=(Token)match(input,71,FOLLOW_16); 

                    				newLeafNode(otherlv_2, grammarAccess.getLevelZoneAccess().getShareKeyword_2_0());
                    			
                    // InternalMazeDsl.g:2185:4: ( (lv_breakableShare_3_0= ruleDOUBLE_OBJECT ) )
                    // InternalMazeDsl.g:2186:5: (lv_breakableShare_3_0= ruleDOUBLE_OBJECT )
                    {
                    // InternalMazeDsl.g:2186:5: (lv_breakableShare_3_0= ruleDOUBLE_OBJECT )
                    // InternalMazeDsl.g:2187:6: lv_breakableShare_3_0= ruleDOUBLE_OBJECT
                    {

                    						newCompositeNode(grammarAccess.getLevelZoneAccess().getBreakableShareDOUBLE_OBJECTParserRuleCall_2_1_0());
                    					
                    pushFollow(FOLLOW_77);
                    lv_breakableShare_3_0=ruleDOUBLE_OBJECT();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getLevelZoneRule());
                    						}
                    						set(
                    							current,
                    							"breakableShare",
                    							lv_breakableShare_3_0,
                    							"main.game.maze.dsl.MazeDsl.DOUBLE_OBJECT");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_4=(Token)match(input,72,FOLLOW_3); 

            			newLeafNode(otherlv_4, grammarAccess.getLevelZoneAccess().getMaterialsKeyword_3());
            		
            // InternalMazeDsl.g:2209:3: ( (lv_materialIds_5_0= RULE_ID ) )
            // InternalMazeDsl.g:2210:4: (lv_materialIds_5_0= RULE_ID )
            {
            // InternalMazeDsl.g:2210:4: (lv_materialIds_5_0= RULE_ID )
            // InternalMazeDsl.g:2211:5: lv_materialIds_5_0= RULE_ID
            {
            lv_materialIds_5_0=(Token)match(input,RULE_ID,FOLLOW_78); 

            					newLeafNode(lv_materialIds_5_0, grammarAccess.getLevelZoneAccess().getMaterialIdsIDTerminalRuleCall_4_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getLevelZoneRule());
            					}
            					addWithLastConsumed(
            						current,
            						"materialIds",
            						lv_materialIds_5_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            // InternalMazeDsl.g:2227:3: (otherlv_6= ',' ( (lv_materialIds_7_0= RULE_ID ) ) )*
            loop54:
            do {
                int alt54=2;
                int LA54_0 = input.LA(1);

                if ( (LA54_0==50) ) {
                    alt54=1;
                }


                switch (alt54) {
            	case 1 :
            	    // InternalMazeDsl.g:2228:4: otherlv_6= ',' ( (lv_materialIds_7_0= RULE_ID ) )
            	    {
            	    otherlv_6=(Token)match(input,50,FOLLOW_3); 

            	    				newLeafNode(otherlv_6, grammarAccess.getLevelZoneAccess().getCommaKeyword_5_0());
            	    			
            	    // InternalMazeDsl.g:2232:4: ( (lv_materialIds_7_0= RULE_ID ) )
            	    // InternalMazeDsl.g:2233:5: (lv_materialIds_7_0= RULE_ID )
            	    {
            	    // InternalMazeDsl.g:2233:5: (lv_materialIds_7_0= RULE_ID )
            	    // InternalMazeDsl.g:2234:6: lv_materialIds_7_0= RULE_ID
            	    {
            	    lv_materialIds_7_0=(Token)match(input,RULE_ID,FOLLOW_78); 

            	    						newLeafNode(lv_materialIds_7_0, grammarAccess.getLevelZoneAccess().getMaterialIdsIDTerminalRuleCall_5_1_0());
            	    					

            	    						if (current==null) {
            	    							current = createModelElement(grammarAccess.getLevelZoneRule());
            	    						}
            	    						addWithLastConsumed(
            	    							current,
            	    							"materialIds",
            	    							lv_materialIds_7_0,
            	    							"org.eclipse.xtext.common.Terminals.ID");
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop54;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleLevelZone"


    // $ANTLR start "entryRuleSergeantConfig"
    // InternalMazeDsl.g:2255:1: entryRuleSergeantConfig returns [EObject current=null] : iv_ruleSergeantConfig= ruleSergeantConfig EOF ;
    public final EObject entryRuleSergeantConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSergeantConfig = null;


        try {
            // InternalMazeDsl.g:2255:55: (iv_ruleSergeantConfig= ruleSergeantConfig EOF )
            // InternalMazeDsl.g:2256:2: iv_ruleSergeantConfig= ruleSergeantConfig EOF
            {
             newCompositeNode(grammarAccess.getSergeantConfigRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleSergeantConfig=ruleSergeantConfig();

            state._fsp--;

             current =iv_ruleSergeantConfig; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSergeantConfig"


    // $ANTLR start "ruleSergeantConfig"
    // InternalMazeDsl.g:2262:1: ruleSergeantConfig returns [EObject current=null] : (otherlv_0= 'sergeant' ( (lv_displayName_1_0= RULE_STRING ) ) otherlv_2= '{' otherlv_3= 'health' ( (lv_health_4_0= RULE_INT ) ) otherlv_5= 'sightRange' ( (lv_sightRange_6_0= ruleDOUBLE ) ) otherlv_7= 'rounds' ( (lv_roundsPerBurst_8_0= RULE_INT ) ) otherlv_9= 'roundDamage' ( (lv_roundDamage_10_0= RULE_INT ) ) otherlv_11= 'roundInterval' ( (lv_roundIntervalSeconds_12_0= ruleDOUBLE ) ) otherlv_13= 'burstCooldown' ( (lv_burstCooldownSeconds_14_0= ruleDOUBLE ) ) otherlv_15= 'sprite' ( (lv_sprite_16_0= RULE_STRING ) ) (otherlv_17= 'fireSound' ( (lv_fireSound_18_0= RULE_STRING ) ) )? otherlv_19= 'drops' ( (lv_dropWeapon_20_0= ruleDropWeaponEnum ) ) otherlv_21= 'postDistance' ( (lv_postDistanceFromHeart_22_0= ruleDOUBLE ) ) otherlv_23= '}' ) ;
    public final EObject ruleSergeantConfig() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_displayName_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token lv_health_4_0=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Token lv_roundsPerBurst_8_0=null;
        Token otherlv_9=null;
        Token lv_roundDamage_10_0=null;
        Token otherlv_11=null;
        Token otherlv_13=null;
        Token otherlv_15=null;
        Token lv_sprite_16_0=null;
        Token otherlv_17=null;
        Token lv_fireSound_18_0=null;
        Token otherlv_19=null;
        Token otherlv_21=null;
        Token otherlv_23=null;
        AntlrDatatypeRuleToken lv_sightRange_6_0 = null;

        AntlrDatatypeRuleToken lv_roundIntervalSeconds_12_0 = null;

        AntlrDatatypeRuleToken lv_burstCooldownSeconds_14_0 = null;

        Enumerator lv_dropWeapon_20_0 = null;

        AntlrDatatypeRuleToken lv_postDistanceFromHeart_22_0 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:2268:2: ( (otherlv_0= 'sergeant' ( (lv_displayName_1_0= RULE_STRING ) ) otherlv_2= '{' otherlv_3= 'health' ( (lv_health_4_0= RULE_INT ) ) otherlv_5= 'sightRange' ( (lv_sightRange_6_0= ruleDOUBLE ) ) otherlv_7= 'rounds' ( (lv_roundsPerBurst_8_0= RULE_INT ) ) otherlv_9= 'roundDamage' ( (lv_roundDamage_10_0= RULE_INT ) ) otherlv_11= 'roundInterval' ( (lv_roundIntervalSeconds_12_0= ruleDOUBLE ) ) otherlv_13= 'burstCooldown' ( (lv_burstCooldownSeconds_14_0= ruleDOUBLE ) ) otherlv_15= 'sprite' ( (lv_sprite_16_0= RULE_STRING ) ) (otherlv_17= 'fireSound' ( (lv_fireSound_18_0= RULE_STRING ) ) )? otherlv_19= 'drops' ( (lv_dropWeapon_20_0= ruleDropWeaponEnum ) ) otherlv_21= 'postDistance' ( (lv_postDistanceFromHeart_22_0= ruleDOUBLE ) ) otherlv_23= '}' ) )
            // InternalMazeDsl.g:2269:2: (otherlv_0= 'sergeant' ( (lv_displayName_1_0= RULE_STRING ) ) otherlv_2= '{' otherlv_3= 'health' ( (lv_health_4_0= RULE_INT ) ) otherlv_5= 'sightRange' ( (lv_sightRange_6_0= ruleDOUBLE ) ) otherlv_7= 'rounds' ( (lv_roundsPerBurst_8_0= RULE_INT ) ) otherlv_9= 'roundDamage' ( (lv_roundDamage_10_0= RULE_INT ) ) otherlv_11= 'roundInterval' ( (lv_roundIntervalSeconds_12_0= ruleDOUBLE ) ) otherlv_13= 'burstCooldown' ( (lv_burstCooldownSeconds_14_0= ruleDOUBLE ) ) otherlv_15= 'sprite' ( (lv_sprite_16_0= RULE_STRING ) ) (otherlv_17= 'fireSound' ( (lv_fireSound_18_0= RULE_STRING ) ) )? otherlv_19= 'drops' ( (lv_dropWeapon_20_0= ruleDropWeaponEnum ) ) otherlv_21= 'postDistance' ( (lv_postDistanceFromHeart_22_0= ruleDOUBLE ) ) otherlv_23= '}' )
            {
            // InternalMazeDsl.g:2269:2: (otherlv_0= 'sergeant' ( (lv_displayName_1_0= RULE_STRING ) ) otherlv_2= '{' otherlv_3= 'health' ( (lv_health_4_0= RULE_INT ) ) otherlv_5= 'sightRange' ( (lv_sightRange_6_0= ruleDOUBLE ) ) otherlv_7= 'rounds' ( (lv_roundsPerBurst_8_0= RULE_INT ) ) otherlv_9= 'roundDamage' ( (lv_roundDamage_10_0= RULE_INT ) ) otherlv_11= 'roundInterval' ( (lv_roundIntervalSeconds_12_0= ruleDOUBLE ) ) otherlv_13= 'burstCooldown' ( (lv_burstCooldownSeconds_14_0= ruleDOUBLE ) ) otherlv_15= 'sprite' ( (lv_sprite_16_0= RULE_STRING ) ) (otherlv_17= 'fireSound' ( (lv_fireSound_18_0= RULE_STRING ) ) )? otherlv_19= 'drops' ( (lv_dropWeapon_20_0= ruleDropWeaponEnum ) ) otherlv_21= 'postDistance' ( (lv_postDistanceFromHeart_22_0= ruleDOUBLE ) ) otherlv_23= '}' )
            // InternalMazeDsl.g:2270:3: otherlv_0= 'sergeant' ( (lv_displayName_1_0= RULE_STRING ) ) otherlv_2= '{' otherlv_3= 'health' ( (lv_health_4_0= RULE_INT ) ) otherlv_5= 'sightRange' ( (lv_sightRange_6_0= ruleDOUBLE ) ) otherlv_7= 'rounds' ( (lv_roundsPerBurst_8_0= RULE_INT ) ) otherlv_9= 'roundDamage' ( (lv_roundDamage_10_0= RULE_INT ) ) otherlv_11= 'roundInterval' ( (lv_roundIntervalSeconds_12_0= ruleDOUBLE ) ) otherlv_13= 'burstCooldown' ( (lv_burstCooldownSeconds_14_0= ruleDOUBLE ) ) otherlv_15= 'sprite' ( (lv_sprite_16_0= RULE_STRING ) ) (otherlv_17= 'fireSound' ( (lv_fireSound_18_0= RULE_STRING ) ) )? otherlv_19= 'drops' ( (lv_dropWeapon_20_0= ruleDropWeaponEnum ) ) otherlv_21= 'postDistance' ( (lv_postDistanceFromHeart_22_0= ruleDOUBLE ) ) otherlv_23= '}'
            {
            otherlv_0=(Token)match(input,73,FOLLOW_10); 

            			newLeafNode(otherlv_0, grammarAccess.getSergeantConfigAccess().getSergeantKeyword_0());
            		
            // InternalMazeDsl.g:2274:3: ( (lv_displayName_1_0= RULE_STRING ) )
            // InternalMazeDsl.g:2275:4: (lv_displayName_1_0= RULE_STRING )
            {
            // InternalMazeDsl.g:2275:4: (lv_displayName_1_0= RULE_STRING )
            // InternalMazeDsl.g:2276:5: lv_displayName_1_0= RULE_STRING
            {
            lv_displayName_1_0=(Token)match(input,RULE_STRING,FOLLOW_4); 

            					newLeafNode(lv_displayName_1_0, grammarAccess.getSergeantConfigAccess().getDisplayNameSTRINGTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getSergeantConfigRule());
            					}
            					setWithLastConsumed(
            						current,
            						"displayName",
            						lv_displayName_1_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            otherlv_2=(Token)match(input,12,FOLLOW_79); 

            			newLeafNode(otherlv_2, grammarAccess.getSergeantConfigAccess().getLeftCurlyBracketKeyword_2());
            		
            otherlv_3=(Token)match(input,27,FOLLOW_36); 

            			newLeafNode(otherlv_3, grammarAccess.getSergeantConfigAccess().getHealthKeyword_3());
            		
            // InternalMazeDsl.g:2300:3: ( (lv_health_4_0= RULE_INT ) )
            // InternalMazeDsl.g:2301:4: (lv_health_4_0= RULE_INT )
            {
            // InternalMazeDsl.g:2301:4: (lv_health_4_0= RULE_INT )
            // InternalMazeDsl.g:2302:5: lv_health_4_0= RULE_INT
            {
            lv_health_4_0=(Token)match(input,RULE_INT,FOLLOW_80); 

            					newLeafNode(lv_health_4_0, grammarAccess.getSergeantConfigAccess().getHealthINTTerminalRuleCall_4_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getSergeantConfigRule());
            					}
            					setWithLastConsumed(
            						current,
            						"health",
            						lv_health_4_0,
            						"org.eclipse.xtext.common.Terminals.INT");
            				

            }


            }

            otherlv_5=(Token)match(input,74,FOLLOW_16); 

            			newLeafNode(otherlv_5, grammarAccess.getSergeantConfigAccess().getSightRangeKeyword_5());
            		
            // InternalMazeDsl.g:2322:3: ( (lv_sightRange_6_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:2323:4: (lv_sightRange_6_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:2323:4: (lv_sightRange_6_0= ruleDOUBLE )
            // InternalMazeDsl.g:2324:5: lv_sightRange_6_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getSergeantConfigAccess().getSightRangeDOUBLEParserRuleCall_6_0());
            				
            pushFollow(FOLLOW_81);
            lv_sightRange_6_0=ruleDOUBLE();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getSergeantConfigRule());
            					}
            					set(
            						current,
            						"sightRange",
            						lv_sightRange_6_0,
            						"main.game.maze.dsl.MazeDsl.DOUBLE");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_7=(Token)match(input,75,FOLLOW_36); 

            			newLeafNode(otherlv_7, grammarAccess.getSergeantConfigAccess().getRoundsKeyword_7());
            		
            // InternalMazeDsl.g:2345:3: ( (lv_roundsPerBurst_8_0= RULE_INT ) )
            // InternalMazeDsl.g:2346:4: (lv_roundsPerBurst_8_0= RULE_INT )
            {
            // InternalMazeDsl.g:2346:4: (lv_roundsPerBurst_8_0= RULE_INT )
            // InternalMazeDsl.g:2347:5: lv_roundsPerBurst_8_0= RULE_INT
            {
            lv_roundsPerBurst_8_0=(Token)match(input,RULE_INT,FOLLOW_82); 

            					newLeafNode(lv_roundsPerBurst_8_0, grammarAccess.getSergeantConfigAccess().getRoundsPerBurstINTTerminalRuleCall_8_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getSergeantConfigRule());
            					}
            					setWithLastConsumed(
            						current,
            						"roundsPerBurst",
            						lv_roundsPerBurst_8_0,
            						"org.eclipse.xtext.common.Terminals.INT");
            				

            }


            }

            otherlv_9=(Token)match(input,76,FOLLOW_36); 

            			newLeafNode(otherlv_9, grammarAccess.getSergeantConfigAccess().getRoundDamageKeyword_9());
            		
            // InternalMazeDsl.g:2367:3: ( (lv_roundDamage_10_0= RULE_INT ) )
            // InternalMazeDsl.g:2368:4: (lv_roundDamage_10_0= RULE_INT )
            {
            // InternalMazeDsl.g:2368:4: (lv_roundDamage_10_0= RULE_INT )
            // InternalMazeDsl.g:2369:5: lv_roundDamage_10_0= RULE_INT
            {
            lv_roundDamage_10_0=(Token)match(input,RULE_INT,FOLLOW_83); 

            					newLeafNode(lv_roundDamage_10_0, grammarAccess.getSergeantConfigAccess().getRoundDamageINTTerminalRuleCall_10_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getSergeantConfigRule());
            					}
            					setWithLastConsumed(
            						current,
            						"roundDamage",
            						lv_roundDamage_10_0,
            						"org.eclipse.xtext.common.Terminals.INT");
            				

            }


            }

            otherlv_11=(Token)match(input,77,FOLLOW_16); 

            			newLeafNode(otherlv_11, grammarAccess.getSergeantConfigAccess().getRoundIntervalKeyword_11());
            		
            // InternalMazeDsl.g:2389:3: ( (lv_roundIntervalSeconds_12_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:2390:4: (lv_roundIntervalSeconds_12_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:2390:4: (lv_roundIntervalSeconds_12_0= ruleDOUBLE )
            // InternalMazeDsl.g:2391:5: lv_roundIntervalSeconds_12_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getSergeantConfigAccess().getRoundIntervalSecondsDOUBLEParserRuleCall_12_0());
            				
            pushFollow(FOLLOW_84);
            lv_roundIntervalSeconds_12_0=ruleDOUBLE();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getSergeantConfigRule());
            					}
            					set(
            						current,
            						"roundIntervalSeconds",
            						lv_roundIntervalSeconds_12_0,
            						"main.game.maze.dsl.MazeDsl.DOUBLE");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_13=(Token)match(input,78,FOLLOW_16); 

            			newLeafNode(otherlv_13, grammarAccess.getSergeantConfigAccess().getBurstCooldownKeyword_13());
            		
            // InternalMazeDsl.g:2412:3: ( (lv_burstCooldownSeconds_14_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:2413:4: (lv_burstCooldownSeconds_14_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:2413:4: (lv_burstCooldownSeconds_14_0= ruleDOUBLE )
            // InternalMazeDsl.g:2414:5: lv_burstCooldownSeconds_14_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getSergeantConfigAccess().getBurstCooldownSecondsDOUBLEParserRuleCall_14_0());
            				
            pushFollow(FOLLOW_85);
            lv_burstCooldownSeconds_14_0=ruleDOUBLE();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getSergeantConfigRule());
            					}
            					set(
            						current,
            						"burstCooldownSeconds",
            						lv_burstCooldownSeconds_14_0,
            						"main.game.maze.dsl.MazeDsl.DOUBLE");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_15=(Token)match(input,79,FOLLOW_10); 

            			newLeafNode(otherlv_15, grammarAccess.getSergeantConfigAccess().getSpriteKeyword_15());
            		
            // InternalMazeDsl.g:2435:3: ( (lv_sprite_16_0= RULE_STRING ) )
            // InternalMazeDsl.g:2436:4: (lv_sprite_16_0= RULE_STRING )
            {
            // InternalMazeDsl.g:2436:4: (lv_sprite_16_0= RULE_STRING )
            // InternalMazeDsl.g:2437:5: lv_sprite_16_0= RULE_STRING
            {
            lv_sprite_16_0=(Token)match(input,RULE_STRING,FOLLOW_86); 

            					newLeafNode(lv_sprite_16_0, grammarAccess.getSergeantConfigAccess().getSpriteSTRINGTerminalRuleCall_16_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getSergeantConfigRule());
            					}
            					setWithLastConsumed(
            						current,
            						"sprite",
            						lv_sprite_16_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            // InternalMazeDsl.g:2453:3: (otherlv_17= 'fireSound' ( (lv_fireSound_18_0= RULE_STRING ) ) )?
            int alt55=2;
            int LA55_0 = input.LA(1);

            if ( (LA55_0==80) ) {
                alt55=1;
            }
            switch (alt55) {
                case 1 :
                    // InternalMazeDsl.g:2454:4: otherlv_17= 'fireSound' ( (lv_fireSound_18_0= RULE_STRING ) )
                    {
                    otherlv_17=(Token)match(input,80,FOLLOW_10); 

                    				newLeafNode(otherlv_17, grammarAccess.getSergeantConfigAccess().getFireSoundKeyword_17_0());
                    			
                    // InternalMazeDsl.g:2458:4: ( (lv_fireSound_18_0= RULE_STRING ) )
                    // InternalMazeDsl.g:2459:5: (lv_fireSound_18_0= RULE_STRING )
                    {
                    // InternalMazeDsl.g:2459:5: (lv_fireSound_18_0= RULE_STRING )
                    // InternalMazeDsl.g:2460:6: lv_fireSound_18_0= RULE_STRING
                    {
                    lv_fireSound_18_0=(Token)match(input,RULE_STRING,FOLLOW_87); 

                    						newLeafNode(lv_fireSound_18_0, grammarAccess.getSergeantConfigAccess().getFireSoundSTRINGTerminalRuleCall_17_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getSergeantConfigRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"fireSound",
                    							lv_fireSound_18_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_19=(Token)match(input,81,FOLLOW_88); 

            			newLeafNode(otherlv_19, grammarAccess.getSergeantConfigAccess().getDropsKeyword_18());
            		
            // InternalMazeDsl.g:2481:3: ( (lv_dropWeapon_20_0= ruleDropWeaponEnum ) )
            // InternalMazeDsl.g:2482:4: (lv_dropWeapon_20_0= ruleDropWeaponEnum )
            {
            // InternalMazeDsl.g:2482:4: (lv_dropWeapon_20_0= ruleDropWeaponEnum )
            // InternalMazeDsl.g:2483:5: lv_dropWeapon_20_0= ruleDropWeaponEnum
            {

            					newCompositeNode(grammarAccess.getSergeantConfigAccess().getDropWeaponDropWeaponEnumEnumRuleCall_19_0());
            				
            pushFollow(FOLLOW_89);
            lv_dropWeapon_20_0=ruleDropWeaponEnum();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getSergeantConfigRule());
            					}
            					set(
            						current,
            						"dropWeapon",
            						lv_dropWeapon_20_0,
            						"main.game.maze.dsl.MazeDsl.DropWeaponEnum");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_21=(Token)match(input,82,FOLLOW_16); 

            			newLeafNode(otherlv_21, grammarAccess.getSergeantConfigAccess().getPostDistanceKeyword_20());
            		
            // InternalMazeDsl.g:2504:3: ( (lv_postDistanceFromHeart_22_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:2505:4: (lv_postDistanceFromHeart_22_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:2505:4: (lv_postDistanceFromHeart_22_0= ruleDOUBLE )
            // InternalMazeDsl.g:2506:5: lv_postDistanceFromHeart_22_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getSergeantConfigAccess().getPostDistanceFromHeartDOUBLEParserRuleCall_21_0());
            				
            pushFollow(FOLLOW_34);
            lv_postDistanceFromHeart_22_0=ruleDOUBLE();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getSergeantConfigRule());
            					}
            					set(
            						current,
            						"postDistanceFromHeart",
            						lv_postDistanceFromHeart_22_0,
            						"main.game.maze.dsl.MazeDsl.DOUBLE");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_23=(Token)match(input,13,FOLLOW_2); 

            			newLeafNode(otherlv_23, grammarAccess.getSergeantConfigAccess().getRightCurlyBracketKeyword_22());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSergeantConfig"


    // $ANTLR start "entryRuleTierCountConfig"
    // InternalMazeDsl.g:2531:1: entryRuleTierCountConfig returns [EObject current=null] : iv_ruleTierCountConfig= ruleTierCountConfig EOF ;
    public final EObject entryRuleTierCountConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTierCountConfig = null;


        try {
            // InternalMazeDsl.g:2531:56: (iv_ruleTierCountConfig= ruleTierCountConfig EOF )
            // InternalMazeDsl.g:2532:2: iv_ruleTierCountConfig= ruleTierCountConfig EOF
            {
             newCompositeNode(grammarAccess.getTierCountConfigRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleTierCountConfig=ruleTierCountConfig();

            state._fsp--;

             current =iv_ruleTierCountConfig; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTierCountConfig"


    // $ANTLR start "ruleTierCountConfig"
    // InternalMazeDsl.g:2538:1: ruleTierCountConfig returns [EObject current=null] : (otherlv_0= 'sergeants' ( (lv_difficulty_1_0= ruleDifficultyLevel ) ) ( (lv_count_2_0= RULE_INT ) ) ) ;
    public final EObject ruleTierCountConfig() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_count_2_0=null;
        Enumerator lv_difficulty_1_0 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:2544:2: ( (otherlv_0= 'sergeants' ( (lv_difficulty_1_0= ruleDifficultyLevel ) ) ( (lv_count_2_0= RULE_INT ) ) ) )
            // InternalMazeDsl.g:2545:2: (otherlv_0= 'sergeants' ( (lv_difficulty_1_0= ruleDifficultyLevel ) ) ( (lv_count_2_0= RULE_INT ) ) )
            {
            // InternalMazeDsl.g:2545:2: (otherlv_0= 'sergeants' ( (lv_difficulty_1_0= ruleDifficultyLevel ) ) ( (lv_count_2_0= RULE_INT ) ) )
            // InternalMazeDsl.g:2546:3: otherlv_0= 'sergeants' ( (lv_difficulty_1_0= ruleDifficultyLevel ) ) ( (lv_count_2_0= RULE_INT ) )
            {
            otherlv_0=(Token)match(input,83,FOLLOW_12); 

            			newLeafNode(otherlv_0, grammarAccess.getTierCountConfigAccess().getSergeantsKeyword_0());
            		
            // InternalMazeDsl.g:2550:3: ( (lv_difficulty_1_0= ruleDifficultyLevel ) )
            // InternalMazeDsl.g:2551:4: (lv_difficulty_1_0= ruleDifficultyLevel )
            {
            // InternalMazeDsl.g:2551:4: (lv_difficulty_1_0= ruleDifficultyLevel )
            // InternalMazeDsl.g:2552:5: lv_difficulty_1_0= ruleDifficultyLevel
            {

            					newCompositeNode(grammarAccess.getTierCountConfigAccess().getDifficultyDifficultyLevelEnumRuleCall_1_0());
            				
            pushFollow(FOLLOW_36);
            lv_difficulty_1_0=ruleDifficultyLevel();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getTierCountConfigRule());
            					}
            					set(
            						current,
            						"difficulty",
            						lv_difficulty_1_0,
            						"main.game.maze.dsl.MazeDsl.DifficultyLevel");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalMazeDsl.g:2569:3: ( (lv_count_2_0= RULE_INT ) )
            // InternalMazeDsl.g:2570:4: (lv_count_2_0= RULE_INT )
            {
            // InternalMazeDsl.g:2570:4: (lv_count_2_0= RULE_INT )
            // InternalMazeDsl.g:2571:5: lv_count_2_0= RULE_INT
            {
            lv_count_2_0=(Token)match(input,RULE_INT,FOLLOW_2); 

            					newLeafNode(lv_count_2_0, grammarAccess.getTierCountConfigAccess().getCountINTTerminalRuleCall_2_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getTierCountConfigRule());
            					}
            					setWithLastConsumed(
            						current,
            						"count",
            						lv_count_2_0,
            						"org.eclipse.xtext.common.Terminals.INT");
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTierCountConfig"


    // $ANTLR start "entryRuleMineRingConfig"
    // InternalMazeDsl.g:2591:1: entryRuleMineRingConfig returns [EObject current=null] : iv_ruleMineRingConfig= ruleMineRingConfig EOF ;
    public final EObject entryRuleMineRingConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMineRingConfig = null;


        try {
            // InternalMazeDsl.g:2591:55: (iv_ruleMineRingConfig= ruleMineRingConfig EOF )
            // InternalMazeDsl.g:2592:2: iv_ruleMineRingConfig= ruleMineRingConfig EOF
            {
             newCompositeNode(grammarAccess.getMineRingConfigRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleMineRingConfig=ruleMineRingConfig();

            state._fsp--;

             current =iv_ruleMineRingConfig; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleMineRingConfig"


    // $ANTLR start "ruleMineRingConfig"
    // InternalMazeDsl.g:2598:1: ruleMineRingConfig returns [EObject current=null] : (otherlv_0= 'mines' otherlv_1= '{' otherlv_2= 'count' ( (lv_count_3_0= RULE_INT ) ) otherlv_4= 'ringRadius' ( (lv_ringRadius_5_0= ruleDOUBLE ) ) otherlv_6= 'triggerRadius' ( (lv_triggerRadius_7_0= ruleDOUBLE ) ) otherlv_8= 'pulse' ( (lv_pulseSeconds_9_0= ruleDOUBLE ) ) otherlv_10= 'blastRadius' ( (lv_blastRadius_11_0= ruleDOUBLE ) ) otherlv_12= 'blastDamage' ( (lv_blastDamage_13_0= RULE_INT ) ) otherlv_14= 'playerDamage' ( (lv_playerDamage_15_0= RULE_INT ) ) otherlv_16= 'holeRadius' ( (lv_holeRadius_17_0= ruleDOUBLE ) ) otherlv_18= '}' ) ;
    public final EObject ruleMineRingConfig() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token lv_count_3_0=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        Token otherlv_10=null;
        Token otherlv_12=null;
        Token lv_blastDamage_13_0=null;
        Token otherlv_14=null;
        Token lv_playerDamage_15_0=null;
        Token otherlv_16=null;
        Token otherlv_18=null;
        AntlrDatatypeRuleToken lv_ringRadius_5_0 = null;

        AntlrDatatypeRuleToken lv_triggerRadius_7_0 = null;

        AntlrDatatypeRuleToken lv_pulseSeconds_9_0 = null;

        AntlrDatatypeRuleToken lv_blastRadius_11_0 = null;

        AntlrDatatypeRuleToken lv_holeRadius_17_0 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:2604:2: ( (otherlv_0= 'mines' otherlv_1= '{' otherlv_2= 'count' ( (lv_count_3_0= RULE_INT ) ) otherlv_4= 'ringRadius' ( (lv_ringRadius_5_0= ruleDOUBLE ) ) otherlv_6= 'triggerRadius' ( (lv_triggerRadius_7_0= ruleDOUBLE ) ) otherlv_8= 'pulse' ( (lv_pulseSeconds_9_0= ruleDOUBLE ) ) otherlv_10= 'blastRadius' ( (lv_blastRadius_11_0= ruleDOUBLE ) ) otherlv_12= 'blastDamage' ( (lv_blastDamage_13_0= RULE_INT ) ) otherlv_14= 'playerDamage' ( (lv_playerDamage_15_0= RULE_INT ) ) otherlv_16= 'holeRadius' ( (lv_holeRadius_17_0= ruleDOUBLE ) ) otherlv_18= '}' ) )
            // InternalMazeDsl.g:2605:2: (otherlv_0= 'mines' otherlv_1= '{' otherlv_2= 'count' ( (lv_count_3_0= RULE_INT ) ) otherlv_4= 'ringRadius' ( (lv_ringRadius_5_0= ruleDOUBLE ) ) otherlv_6= 'triggerRadius' ( (lv_triggerRadius_7_0= ruleDOUBLE ) ) otherlv_8= 'pulse' ( (lv_pulseSeconds_9_0= ruleDOUBLE ) ) otherlv_10= 'blastRadius' ( (lv_blastRadius_11_0= ruleDOUBLE ) ) otherlv_12= 'blastDamage' ( (lv_blastDamage_13_0= RULE_INT ) ) otherlv_14= 'playerDamage' ( (lv_playerDamage_15_0= RULE_INT ) ) otherlv_16= 'holeRadius' ( (lv_holeRadius_17_0= ruleDOUBLE ) ) otherlv_18= '}' )
            {
            // InternalMazeDsl.g:2605:2: (otherlv_0= 'mines' otherlv_1= '{' otherlv_2= 'count' ( (lv_count_3_0= RULE_INT ) ) otherlv_4= 'ringRadius' ( (lv_ringRadius_5_0= ruleDOUBLE ) ) otherlv_6= 'triggerRadius' ( (lv_triggerRadius_7_0= ruleDOUBLE ) ) otherlv_8= 'pulse' ( (lv_pulseSeconds_9_0= ruleDOUBLE ) ) otherlv_10= 'blastRadius' ( (lv_blastRadius_11_0= ruleDOUBLE ) ) otherlv_12= 'blastDamage' ( (lv_blastDamage_13_0= RULE_INT ) ) otherlv_14= 'playerDamage' ( (lv_playerDamage_15_0= RULE_INT ) ) otherlv_16= 'holeRadius' ( (lv_holeRadius_17_0= ruleDOUBLE ) ) otherlv_18= '}' )
            // InternalMazeDsl.g:2606:3: otherlv_0= 'mines' otherlv_1= '{' otherlv_2= 'count' ( (lv_count_3_0= RULE_INT ) ) otherlv_4= 'ringRadius' ( (lv_ringRadius_5_0= ruleDOUBLE ) ) otherlv_6= 'triggerRadius' ( (lv_triggerRadius_7_0= ruleDOUBLE ) ) otherlv_8= 'pulse' ( (lv_pulseSeconds_9_0= ruleDOUBLE ) ) otherlv_10= 'blastRadius' ( (lv_blastRadius_11_0= ruleDOUBLE ) ) otherlv_12= 'blastDamage' ( (lv_blastDamage_13_0= RULE_INT ) ) otherlv_14= 'playerDamage' ( (lv_playerDamage_15_0= RULE_INT ) ) otherlv_16= 'holeRadius' ( (lv_holeRadius_17_0= ruleDOUBLE ) ) otherlv_18= '}'
            {
            otherlv_0=(Token)match(input,84,FOLLOW_4); 

            			newLeafNode(otherlv_0, grammarAccess.getMineRingConfigAccess().getMinesKeyword_0());
            		
            otherlv_1=(Token)match(input,12,FOLLOW_90); 

            			newLeafNode(otherlv_1, grammarAccess.getMineRingConfigAccess().getLeftCurlyBracketKeyword_1());
            		
            otherlv_2=(Token)match(input,85,FOLLOW_36); 

            			newLeafNode(otherlv_2, grammarAccess.getMineRingConfigAccess().getCountKeyword_2());
            		
            // InternalMazeDsl.g:2618:3: ( (lv_count_3_0= RULE_INT ) )
            // InternalMazeDsl.g:2619:4: (lv_count_3_0= RULE_INT )
            {
            // InternalMazeDsl.g:2619:4: (lv_count_3_0= RULE_INT )
            // InternalMazeDsl.g:2620:5: lv_count_3_0= RULE_INT
            {
            lv_count_3_0=(Token)match(input,RULE_INT,FOLLOW_91); 

            					newLeafNode(lv_count_3_0, grammarAccess.getMineRingConfigAccess().getCountINTTerminalRuleCall_3_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getMineRingConfigRule());
            					}
            					setWithLastConsumed(
            						current,
            						"count",
            						lv_count_3_0,
            						"org.eclipse.xtext.common.Terminals.INT");
            				

            }


            }

            otherlv_4=(Token)match(input,86,FOLLOW_16); 

            			newLeafNode(otherlv_4, grammarAccess.getMineRingConfigAccess().getRingRadiusKeyword_4());
            		
            // InternalMazeDsl.g:2640:3: ( (lv_ringRadius_5_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:2641:4: (lv_ringRadius_5_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:2641:4: (lv_ringRadius_5_0= ruleDOUBLE )
            // InternalMazeDsl.g:2642:5: lv_ringRadius_5_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getMineRingConfigAccess().getRingRadiusDOUBLEParserRuleCall_5_0());
            				
            pushFollow(FOLLOW_92);
            lv_ringRadius_5_0=ruleDOUBLE();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getMineRingConfigRule());
            					}
            					set(
            						current,
            						"ringRadius",
            						lv_ringRadius_5_0,
            						"main.game.maze.dsl.MazeDsl.DOUBLE");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_6=(Token)match(input,87,FOLLOW_16); 

            			newLeafNode(otherlv_6, grammarAccess.getMineRingConfigAccess().getTriggerRadiusKeyword_6());
            		
            // InternalMazeDsl.g:2663:3: ( (lv_triggerRadius_7_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:2664:4: (lv_triggerRadius_7_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:2664:4: (lv_triggerRadius_7_0= ruleDOUBLE )
            // InternalMazeDsl.g:2665:5: lv_triggerRadius_7_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getMineRingConfigAccess().getTriggerRadiusDOUBLEParserRuleCall_7_0());
            				
            pushFollow(FOLLOW_93);
            lv_triggerRadius_7_0=ruleDOUBLE();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getMineRingConfigRule());
            					}
            					set(
            						current,
            						"triggerRadius",
            						lv_triggerRadius_7_0,
            						"main.game.maze.dsl.MazeDsl.DOUBLE");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_8=(Token)match(input,88,FOLLOW_16); 

            			newLeafNode(otherlv_8, grammarAccess.getMineRingConfigAccess().getPulseKeyword_8());
            		
            // InternalMazeDsl.g:2686:3: ( (lv_pulseSeconds_9_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:2687:4: (lv_pulseSeconds_9_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:2687:4: (lv_pulseSeconds_9_0= ruleDOUBLE )
            // InternalMazeDsl.g:2688:5: lv_pulseSeconds_9_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getMineRingConfigAccess().getPulseSecondsDOUBLEParserRuleCall_9_0());
            				
            pushFollow(FOLLOW_94);
            lv_pulseSeconds_9_0=ruleDOUBLE();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getMineRingConfigRule());
            					}
            					set(
            						current,
            						"pulseSeconds",
            						lv_pulseSeconds_9_0,
            						"main.game.maze.dsl.MazeDsl.DOUBLE");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_10=(Token)match(input,89,FOLLOW_16); 

            			newLeafNode(otherlv_10, grammarAccess.getMineRingConfigAccess().getBlastRadiusKeyword_10());
            		
            // InternalMazeDsl.g:2709:3: ( (lv_blastRadius_11_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:2710:4: (lv_blastRadius_11_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:2710:4: (lv_blastRadius_11_0= ruleDOUBLE )
            // InternalMazeDsl.g:2711:5: lv_blastRadius_11_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getMineRingConfigAccess().getBlastRadiusDOUBLEParserRuleCall_11_0());
            				
            pushFollow(FOLLOW_95);
            lv_blastRadius_11_0=ruleDOUBLE();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getMineRingConfigRule());
            					}
            					set(
            						current,
            						"blastRadius",
            						lv_blastRadius_11_0,
            						"main.game.maze.dsl.MazeDsl.DOUBLE");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_12=(Token)match(input,90,FOLLOW_36); 

            			newLeafNode(otherlv_12, grammarAccess.getMineRingConfigAccess().getBlastDamageKeyword_12());
            		
            // InternalMazeDsl.g:2732:3: ( (lv_blastDamage_13_0= RULE_INT ) )
            // InternalMazeDsl.g:2733:4: (lv_blastDamage_13_0= RULE_INT )
            {
            // InternalMazeDsl.g:2733:4: (lv_blastDamage_13_0= RULE_INT )
            // InternalMazeDsl.g:2734:5: lv_blastDamage_13_0= RULE_INT
            {
            lv_blastDamage_13_0=(Token)match(input,RULE_INT,FOLLOW_96); 

            					newLeafNode(lv_blastDamage_13_0, grammarAccess.getMineRingConfigAccess().getBlastDamageINTTerminalRuleCall_13_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getMineRingConfigRule());
            					}
            					setWithLastConsumed(
            						current,
            						"blastDamage",
            						lv_blastDamage_13_0,
            						"org.eclipse.xtext.common.Terminals.INT");
            				

            }


            }

            otherlv_14=(Token)match(input,91,FOLLOW_36); 

            			newLeafNode(otherlv_14, grammarAccess.getMineRingConfigAccess().getPlayerDamageKeyword_14());
            		
            // InternalMazeDsl.g:2754:3: ( (lv_playerDamage_15_0= RULE_INT ) )
            // InternalMazeDsl.g:2755:4: (lv_playerDamage_15_0= RULE_INT )
            {
            // InternalMazeDsl.g:2755:4: (lv_playerDamage_15_0= RULE_INT )
            // InternalMazeDsl.g:2756:5: lv_playerDamage_15_0= RULE_INT
            {
            lv_playerDamage_15_0=(Token)match(input,RULE_INT,FOLLOW_97); 

            					newLeafNode(lv_playerDamage_15_0, grammarAccess.getMineRingConfigAccess().getPlayerDamageINTTerminalRuleCall_15_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getMineRingConfigRule());
            					}
            					setWithLastConsumed(
            						current,
            						"playerDamage",
            						lv_playerDamage_15_0,
            						"org.eclipse.xtext.common.Terminals.INT");
            				

            }


            }

            otherlv_16=(Token)match(input,92,FOLLOW_16); 

            			newLeafNode(otherlv_16, grammarAccess.getMineRingConfigAccess().getHoleRadiusKeyword_16());
            		
            // InternalMazeDsl.g:2776:3: ( (lv_holeRadius_17_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:2777:4: (lv_holeRadius_17_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:2777:4: (lv_holeRadius_17_0= ruleDOUBLE )
            // InternalMazeDsl.g:2778:5: lv_holeRadius_17_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getMineRingConfigAccess().getHoleRadiusDOUBLEParserRuleCall_17_0());
            				
            pushFollow(FOLLOW_34);
            lv_holeRadius_17_0=ruleDOUBLE();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getMineRingConfigRule());
            					}
            					set(
            						current,
            						"holeRadius",
            						lv_holeRadius_17_0,
            						"main.game.maze.dsl.MazeDsl.DOUBLE");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_18=(Token)match(input,13,FOLLOW_2); 

            			newLeafNode(otherlv_18, grammarAccess.getMineRingConfigAccess().getRightCurlyBracketKeyword_18());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleMineRingConfig"


    // $ANTLR start "entryRulePortalGhostConfig"
    // InternalMazeDsl.g:2803:1: entryRulePortalGhostConfig returns [EObject current=null] : iv_rulePortalGhostConfig= rulePortalGhostConfig EOF ;
    public final EObject entryRulePortalGhostConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePortalGhostConfig = null;


        try {
            // InternalMazeDsl.g:2803:58: (iv_rulePortalGhostConfig= rulePortalGhostConfig EOF )
            // InternalMazeDsl.g:2804:2: iv_rulePortalGhostConfig= rulePortalGhostConfig EOF
            {
             newCompositeNode(grammarAccess.getPortalGhostConfigRule()); 
            pushFollow(FOLLOW_1);
            iv_rulePortalGhostConfig=rulePortalGhostConfig();

            state._fsp--;

             current =iv_rulePortalGhostConfig; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePortalGhostConfig"


    // $ANTLR start "rulePortalGhostConfig"
    // InternalMazeDsl.g:2810:1: rulePortalGhostConfig returns [EObject current=null] : (otherlv_0= 'portalGhost' ( (lv_displayName_1_0= RULE_STRING ) ) otherlv_2= '{' otherlv_3= 'health' ( (lv_health_4_0= RULE_INT ) ) otherlv_5= 'speed' ( (lv_speed_6_0= ruleDOUBLE ) ) otherlv_7= 'sprite' ( (lv_sprite_8_0= RULE_STRING ) ) otherlv_9= '}' ) ;
    public final EObject rulePortalGhostConfig() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_displayName_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token lv_health_4_0=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Token lv_sprite_8_0=null;
        Token otherlv_9=null;
        AntlrDatatypeRuleToken lv_speed_6_0 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:2816:2: ( (otherlv_0= 'portalGhost' ( (lv_displayName_1_0= RULE_STRING ) ) otherlv_2= '{' otherlv_3= 'health' ( (lv_health_4_0= RULE_INT ) ) otherlv_5= 'speed' ( (lv_speed_6_0= ruleDOUBLE ) ) otherlv_7= 'sprite' ( (lv_sprite_8_0= RULE_STRING ) ) otherlv_9= '}' ) )
            // InternalMazeDsl.g:2817:2: (otherlv_0= 'portalGhost' ( (lv_displayName_1_0= RULE_STRING ) ) otherlv_2= '{' otherlv_3= 'health' ( (lv_health_4_0= RULE_INT ) ) otherlv_5= 'speed' ( (lv_speed_6_0= ruleDOUBLE ) ) otherlv_7= 'sprite' ( (lv_sprite_8_0= RULE_STRING ) ) otherlv_9= '}' )
            {
            // InternalMazeDsl.g:2817:2: (otherlv_0= 'portalGhost' ( (lv_displayName_1_0= RULE_STRING ) ) otherlv_2= '{' otherlv_3= 'health' ( (lv_health_4_0= RULE_INT ) ) otherlv_5= 'speed' ( (lv_speed_6_0= ruleDOUBLE ) ) otherlv_7= 'sprite' ( (lv_sprite_8_0= RULE_STRING ) ) otherlv_9= '}' )
            // InternalMazeDsl.g:2818:3: otherlv_0= 'portalGhost' ( (lv_displayName_1_0= RULE_STRING ) ) otherlv_2= '{' otherlv_3= 'health' ( (lv_health_4_0= RULE_INT ) ) otherlv_5= 'speed' ( (lv_speed_6_0= ruleDOUBLE ) ) otherlv_7= 'sprite' ( (lv_sprite_8_0= RULE_STRING ) ) otherlv_9= '}'
            {
            otherlv_0=(Token)match(input,93,FOLLOW_10); 

            			newLeafNode(otherlv_0, grammarAccess.getPortalGhostConfigAccess().getPortalGhostKeyword_0());
            		
            // InternalMazeDsl.g:2822:3: ( (lv_displayName_1_0= RULE_STRING ) )
            // InternalMazeDsl.g:2823:4: (lv_displayName_1_0= RULE_STRING )
            {
            // InternalMazeDsl.g:2823:4: (lv_displayName_1_0= RULE_STRING )
            // InternalMazeDsl.g:2824:5: lv_displayName_1_0= RULE_STRING
            {
            lv_displayName_1_0=(Token)match(input,RULE_STRING,FOLLOW_4); 

            					newLeafNode(lv_displayName_1_0, grammarAccess.getPortalGhostConfigAccess().getDisplayNameSTRINGTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getPortalGhostConfigRule());
            					}
            					setWithLastConsumed(
            						current,
            						"displayName",
            						lv_displayName_1_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            otherlv_2=(Token)match(input,12,FOLLOW_79); 

            			newLeafNode(otherlv_2, grammarAccess.getPortalGhostConfigAccess().getLeftCurlyBracketKeyword_2());
            		
            otherlv_3=(Token)match(input,27,FOLLOW_36); 

            			newLeafNode(otherlv_3, grammarAccess.getPortalGhostConfigAccess().getHealthKeyword_3());
            		
            // InternalMazeDsl.g:2848:3: ( (lv_health_4_0= RULE_INT ) )
            // InternalMazeDsl.g:2849:4: (lv_health_4_0= RULE_INT )
            {
            // InternalMazeDsl.g:2849:4: (lv_health_4_0= RULE_INT )
            // InternalMazeDsl.g:2850:5: lv_health_4_0= RULE_INT
            {
            lv_health_4_0=(Token)match(input,RULE_INT,FOLLOW_98); 

            					newLeafNode(lv_health_4_0, grammarAccess.getPortalGhostConfigAccess().getHealthINTTerminalRuleCall_4_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getPortalGhostConfigRule());
            					}
            					setWithLastConsumed(
            						current,
            						"health",
            						lv_health_4_0,
            						"org.eclipse.xtext.common.Terminals.INT");
            				

            }


            }

            otherlv_5=(Token)match(input,28,FOLLOW_16); 

            			newLeafNode(otherlv_5, grammarAccess.getPortalGhostConfigAccess().getSpeedKeyword_5());
            		
            // InternalMazeDsl.g:2870:3: ( (lv_speed_6_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:2871:4: (lv_speed_6_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:2871:4: (lv_speed_6_0= ruleDOUBLE )
            // InternalMazeDsl.g:2872:5: lv_speed_6_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getPortalGhostConfigAccess().getSpeedDOUBLEParserRuleCall_6_0());
            				
            pushFollow(FOLLOW_85);
            lv_speed_6_0=ruleDOUBLE();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getPortalGhostConfigRule());
            					}
            					set(
            						current,
            						"speed",
            						lv_speed_6_0,
            						"main.game.maze.dsl.MazeDsl.DOUBLE");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_7=(Token)match(input,79,FOLLOW_10); 

            			newLeafNode(otherlv_7, grammarAccess.getPortalGhostConfigAccess().getSpriteKeyword_7());
            		
            // InternalMazeDsl.g:2893:3: ( (lv_sprite_8_0= RULE_STRING ) )
            // InternalMazeDsl.g:2894:4: (lv_sprite_8_0= RULE_STRING )
            {
            // InternalMazeDsl.g:2894:4: (lv_sprite_8_0= RULE_STRING )
            // InternalMazeDsl.g:2895:5: lv_sprite_8_0= RULE_STRING
            {
            lv_sprite_8_0=(Token)match(input,RULE_STRING,FOLLOW_34); 

            					newLeafNode(lv_sprite_8_0, grammarAccess.getPortalGhostConfigAccess().getSpriteSTRINGTerminalRuleCall_8_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getPortalGhostConfigRule());
            					}
            					setWithLastConsumed(
            						current,
            						"sprite",
            						lv_sprite_8_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            otherlv_9=(Token)match(input,13,FOLLOW_2); 

            			newLeafNode(otherlv_9, grammarAccess.getPortalGhostConfigAccess().getRightCurlyBracketKeyword_9());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePortalGhostConfig"


    // $ANTLR start "entryRuleGhostCountConfig"
    // InternalMazeDsl.g:2919:1: entryRuleGhostCountConfig returns [EObject current=null] : iv_ruleGhostCountConfig= ruleGhostCountConfig EOF ;
    public final EObject entryRuleGhostCountConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGhostCountConfig = null;


        try {
            // InternalMazeDsl.g:2919:57: (iv_ruleGhostCountConfig= ruleGhostCountConfig EOF )
            // InternalMazeDsl.g:2920:2: iv_ruleGhostCountConfig= ruleGhostCountConfig EOF
            {
             newCompositeNode(grammarAccess.getGhostCountConfigRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleGhostCountConfig=ruleGhostCountConfig();

            state._fsp--;

             current =iv_ruleGhostCountConfig; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleGhostCountConfig"


    // $ANTLR start "ruleGhostCountConfig"
    // InternalMazeDsl.g:2926:1: ruleGhostCountConfig returns [EObject current=null] : (otherlv_0= 'portalGhosts' ( (lv_difficulty_1_0= ruleDifficultyLevel ) ) ( (lv_count_2_0= RULE_INT ) ) ) ;
    public final EObject ruleGhostCountConfig() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_count_2_0=null;
        Enumerator lv_difficulty_1_0 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:2932:2: ( (otherlv_0= 'portalGhosts' ( (lv_difficulty_1_0= ruleDifficultyLevel ) ) ( (lv_count_2_0= RULE_INT ) ) ) )
            // InternalMazeDsl.g:2933:2: (otherlv_0= 'portalGhosts' ( (lv_difficulty_1_0= ruleDifficultyLevel ) ) ( (lv_count_2_0= RULE_INT ) ) )
            {
            // InternalMazeDsl.g:2933:2: (otherlv_0= 'portalGhosts' ( (lv_difficulty_1_0= ruleDifficultyLevel ) ) ( (lv_count_2_0= RULE_INT ) ) )
            // InternalMazeDsl.g:2934:3: otherlv_0= 'portalGhosts' ( (lv_difficulty_1_0= ruleDifficultyLevel ) ) ( (lv_count_2_0= RULE_INT ) )
            {
            otherlv_0=(Token)match(input,94,FOLLOW_12); 

            			newLeafNode(otherlv_0, grammarAccess.getGhostCountConfigAccess().getPortalGhostsKeyword_0());
            		
            // InternalMazeDsl.g:2938:3: ( (lv_difficulty_1_0= ruleDifficultyLevel ) )
            // InternalMazeDsl.g:2939:4: (lv_difficulty_1_0= ruleDifficultyLevel )
            {
            // InternalMazeDsl.g:2939:4: (lv_difficulty_1_0= ruleDifficultyLevel )
            // InternalMazeDsl.g:2940:5: lv_difficulty_1_0= ruleDifficultyLevel
            {

            					newCompositeNode(grammarAccess.getGhostCountConfigAccess().getDifficultyDifficultyLevelEnumRuleCall_1_0());
            				
            pushFollow(FOLLOW_36);
            lv_difficulty_1_0=ruleDifficultyLevel();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getGhostCountConfigRule());
            					}
            					set(
            						current,
            						"difficulty",
            						lv_difficulty_1_0,
            						"main.game.maze.dsl.MazeDsl.DifficultyLevel");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalMazeDsl.g:2957:3: ( (lv_count_2_0= RULE_INT ) )
            // InternalMazeDsl.g:2958:4: (lv_count_2_0= RULE_INT )
            {
            // InternalMazeDsl.g:2958:4: (lv_count_2_0= RULE_INT )
            // InternalMazeDsl.g:2959:5: lv_count_2_0= RULE_INT
            {
            lv_count_2_0=(Token)match(input,RULE_INT,FOLLOW_2); 

            					newLeafNode(lv_count_2_0, grammarAccess.getGhostCountConfigAccess().getCountINTTerminalRuleCall_2_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getGhostCountConfigRule());
            					}
            					setWithLastConsumed(
            						current,
            						"count",
            						lv_count_2_0,
            						"org.eclipse.xtext.common.Terminals.INT");
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleGhostCountConfig"


    // $ANTLR start "entryRuleTeleportRulesConfig"
    // InternalMazeDsl.g:2979:1: entryRuleTeleportRulesConfig returns [EObject current=null] : iv_ruleTeleportRulesConfig= ruleTeleportRulesConfig EOF ;
    public final EObject entryRuleTeleportRulesConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTeleportRulesConfig = null;


        try {
            // InternalMazeDsl.g:2979:60: (iv_ruleTeleportRulesConfig= ruleTeleportRulesConfig EOF )
            // InternalMazeDsl.g:2980:2: iv_ruleTeleportRulesConfig= ruleTeleportRulesConfig EOF
            {
             newCompositeNode(grammarAccess.getTeleportRulesConfigRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleTeleportRulesConfig=ruleTeleportRulesConfig();

            state._fsp--;

             current =iv_ruleTeleportRulesConfig; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTeleportRulesConfig"


    // $ANTLR start "ruleTeleportRulesConfig"
    // InternalMazeDsl.g:2986:1: ruleTeleportRulesConfig returns [EObject current=null] : (otherlv_0= 'teleport' otherlv_1= '{' otherlv_2= 'cooldown' ( (lv_cooldownSeconds_3_0= ruleDOUBLE ) ) otherlv_4= 'banner' ( (lv_bannerText_5_0= RULE_STRING ) ) otherlv_6= 'bannerSeconds' ( (lv_bannerSeconds_7_0= ruleDOUBLE ) ) (otherlv_8= 'zapSound' ( (lv_zapSound_9_0= RULE_STRING ) ) )? (otherlv_10= 'voiceSound' ( (lv_voiceSound_11_0= RULE_STRING ) ) )? otherlv_12= '}' ) ;
    public final EObject ruleTeleportRulesConfig() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token lv_bannerText_5_0=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        Token lv_zapSound_9_0=null;
        Token otherlv_10=null;
        Token lv_voiceSound_11_0=null;
        Token otherlv_12=null;
        AntlrDatatypeRuleToken lv_cooldownSeconds_3_0 = null;

        AntlrDatatypeRuleToken lv_bannerSeconds_7_0 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:2992:2: ( (otherlv_0= 'teleport' otherlv_1= '{' otherlv_2= 'cooldown' ( (lv_cooldownSeconds_3_0= ruleDOUBLE ) ) otherlv_4= 'banner' ( (lv_bannerText_5_0= RULE_STRING ) ) otherlv_6= 'bannerSeconds' ( (lv_bannerSeconds_7_0= ruleDOUBLE ) ) (otherlv_8= 'zapSound' ( (lv_zapSound_9_0= RULE_STRING ) ) )? (otherlv_10= 'voiceSound' ( (lv_voiceSound_11_0= RULE_STRING ) ) )? otherlv_12= '}' ) )
            // InternalMazeDsl.g:2993:2: (otherlv_0= 'teleport' otherlv_1= '{' otherlv_2= 'cooldown' ( (lv_cooldownSeconds_3_0= ruleDOUBLE ) ) otherlv_4= 'banner' ( (lv_bannerText_5_0= RULE_STRING ) ) otherlv_6= 'bannerSeconds' ( (lv_bannerSeconds_7_0= ruleDOUBLE ) ) (otherlv_8= 'zapSound' ( (lv_zapSound_9_0= RULE_STRING ) ) )? (otherlv_10= 'voiceSound' ( (lv_voiceSound_11_0= RULE_STRING ) ) )? otherlv_12= '}' )
            {
            // InternalMazeDsl.g:2993:2: (otherlv_0= 'teleport' otherlv_1= '{' otherlv_2= 'cooldown' ( (lv_cooldownSeconds_3_0= ruleDOUBLE ) ) otherlv_4= 'banner' ( (lv_bannerText_5_0= RULE_STRING ) ) otherlv_6= 'bannerSeconds' ( (lv_bannerSeconds_7_0= ruleDOUBLE ) ) (otherlv_8= 'zapSound' ( (lv_zapSound_9_0= RULE_STRING ) ) )? (otherlv_10= 'voiceSound' ( (lv_voiceSound_11_0= RULE_STRING ) ) )? otherlv_12= '}' )
            // InternalMazeDsl.g:2994:3: otherlv_0= 'teleport' otherlv_1= '{' otherlv_2= 'cooldown' ( (lv_cooldownSeconds_3_0= ruleDOUBLE ) ) otherlv_4= 'banner' ( (lv_bannerText_5_0= RULE_STRING ) ) otherlv_6= 'bannerSeconds' ( (lv_bannerSeconds_7_0= ruleDOUBLE ) ) (otherlv_8= 'zapSound' ( (lv_zapSound_9_0= RULE_STRING ) ) )? (otherlv_10= 'voiceSound' ( (lv_voiceSound_11_0= RULE_STRING ) ) )? otherlv_12= '}'
            {
            otherlv_0=(Token)match(input,95,FOLLOW_4); 

            			newLeafNode(otherlv_0, grammarAccess.getTeleportRulesConfigAccess().getTeleportKeyword_0());
            		
            otherlv_1=(Token)match(input,12,FOLLOW_99); 

            			newLeafNode(otherlv_1, grammarAccess.getTeleportRulesConfigAccess().getLeftCurlyBracketKeyword_1());
            		
            otherlv_2=(Token)match(input,96,FOLLOW_16); 

            			newLeafNode(otherlv_2, grammarAccess.getTeleportRulesConfigAccess().getCooldownKeyword_2());
            		
            // InternalMazeDsl.g:3006:3: ( (lv_cooldownSeconds_3_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:3007:4: (lv_cooldownSeconds_3_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:3007:4: (lv_cooldownSeconds_3_0= ruleDOUBLE )
            // InternalMazeDsl.g:3008:5: lv_cooldownSeconds_3_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getTeleportRulesConfigAccess().getCooldownSecondsDOUBLEParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_100);
            lv_cooldownSeconds_3_0=ruleDOUBLE();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getTeleportRulesConfigRule());
            					}
            					set(
            						current,
            						"cooldownSeconds",
            						lv_cooldownSeconds_3_0,
            						"main.game.maze.dsl.MazeDsl.DOUBLE");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_4=(Token)match(input,97,FOLLOW_10); 

            			newLeafNode(otherlv_4, grammarAccess.getTeleportRulesConfigAccess().getBannerKeyword_4());
            		
            // InternalMazeDsl.g:3029:3: ( (lv_bannerText_5_0= RULE_STRING ) )
            // InternalMazeDsl.g:3030:4: (lv_bannerText_5_0= RULE_STRING )
            {
            // InternalMazeDsl.g:3030:4: (lv_bannerText_5_0= RULE_STRING )
            // InternalMazeDsl.g:3031:5: lv_bannerText_5_0= RULE_STRING
            {
            lv_bannerText_5_0=(Token)match(input,RULE_STRING,FOLLOW_101); 

            					newLeafNode(lv_bannerText_5_0, grammarAccess.getTeleportRulesConfigAccess().getBannerTextSTRINGTerminalRuleCall_5_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getTeleportRulesConfigRule());
            					}
            					setWithLastConsumed(
            						current,
            						"bannerText",
            						lv_bannerText_5_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            otherlv_6=(Token)match(input,98,FOLLOW_16); 

            			newLeafNode(otherlv_6, grammarAccess.getTeleportRulesConfigAccess().getBannerSecondsKeyword_6());
            		
            // InternalMazeDsl.g:3051:3: ( (lv_bannerSeconds_7_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:3052:4: (lv_bannerSeconds_7_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:3052:4: (lv_bannerSeconds_7_0= ruleDOUBLE )
            // InternalMazeDsl.g:3053:5: lv_bannerSeconds_7_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getTeleportRulesConfigAccess().getBannerSecondsDOUBLEParserRuleCall_7_0());
            				
            pushFollow(FOLLOW_102);
            lv_bannerSeconds_7_0=ruleDOUBLE();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getTeleportRulesConfigRule());
            					}
            					set(
            						current,
            						"bannerSeconds",
            						lv_bannerSeconds_7_0,
            						"main.game.maze.dsl.MazeDsl.DOUBLE");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalMazeDsl.g:3070:3: (otherlv_8= 'zapSound' ( (lv_zapSound_9_0= RULE_STRING ) ) )?
            int alt56=2;
            int LA56_0 = input.LA(1);

            if ( (LA56_0==99) ) {
                alt56=1;
            }
            switch (alt56) {
                case 1 :
                    // InternalMazeDsl.g:3071:4: otherlv_8= 'zapSound' ( (lv_zapSound_9_0= RULE_STRING ) )
                    {
                    otherlv_8=(Token)match(input,99,FOLLOW_10); 

                    				newLeafNode(otherlv_8, grammarAccess.getTeleportRulesConfigAccess().getZapSoundKeyword_8_0());
                    			
                    // InternalMazeDsl.g:3075:4: ( (lv_zapSound_9_0= RULE_STRING ) )
                    // InternalMazeDsl.g:3076:5: (lv_zapSound_9_0= RULE_STRING )
                    {
                    // InternalMazeDsl.g:3076:5: (lv_zapSound_9_0= RULE_STRING )
                    // InternalMazeDsl.g:3077:6: lv_zapSound_9_0= RULE_STRING
                    {
                    lv_zapSound_9_0=(Token)match(input,RULE_STRING,FOLLOW_103); 

                    						newLeafNode(lv_zapSound_9_0, grammarAccess.getTeleportRulesConfigAccess().getZapSoundSTRINGTerminalRuleCall_8_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getTeleportRulesConfigRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"zapSound",
                    							lv_zapSound_9_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeDsl.g:3094:3: (otherlv_10= 'voiceSound' ( (lv_voiceSound_11_0= RULE_STRING ) ) )?
            int alt57=2;
            int LA57_0 = input.LA(1);

            if ( (LA57_0==100) ) {
                alt57=1;
            }
            switch (alt57) {
                case 1 :
                    // InternalMazeDsl.g:3095:4: otherlv_10= 'voiceSound' ( (lv_voiceSound_11_0= RULE_STRING ) )
                    {
                    otherlv_10=(Token)match(input,100,FOLLOW_10); 

                    				newLeafNode(otherlv_10, grammarAccess.getTeleportRulesConfigAccess().getVoiceSoundKeyword_9_0());
                    			
                    // InternalMazeDsl.g:3099:4: ( (lv_voiceSound_11_0= RULE_STRING ) )
                    // InternalMazeDsl.g:3100:5: (lv_voiceSound_11_0= RULE_STRING )
                    {
                    // InternalMazeDsl.g:3100:5: (lv_voiceSound_11_0= RULE_STRING )
                    // InternalMazeDsl.g:3101:6: lv_voiceSound_11_0= RULE_STRING
                    {
                    lv_voiceSound_11_0=(Token)match(input,RULE_STRING,FOLLOW_34); 

                    						newLeafNode(lv_voiceSound_11_0, grammarAccess.getTeleportRulesConfigAccess().getVoiceSoundSTRINGTerminalRuleCall_9_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getTeleportRulesConfigRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"voiceSound",
                    							lv_voiceSound_11_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_12=(Token)match(input,13,FOLLOW_2); 

            			newLeafNode(otherlv_12, grammarAccess.getTeleportRulesConfigAccess().getRightCurlyBracketKeyword_10());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTeleportRulesConfig"


    // $ANTLR start "entryRuleDOUBLE"
    // InternalMazeDsl.g:3126:1: entryRuleDOUBLE returns [String current=null] : iv_ruleDOUBLE= ruleDOUBLE EOF ;
    public final String entryRuleDOUBLE() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDOUBLE = null;


        try {
            // InternalMazeDsl.g:3126:46: (iv_ruleDOUBLE= ruleDOUBLE EOF )
            // InternalMazeDsl.g:3127:2: iv_ruleDOUBLE= ruleDOUBLE EOF
            {
             newCompositeNode(grammarAccess.getDOUBLERule()); 
            pushFollow(FOLLOW_1);
            iv_ruleDOUBLE=ruleDOUBLE();

            state._fsp--;

             current =iv_ruleDOUBLE.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleDOUBLE"


    // $ANTLR start "ruleDOUBLE"
    // InternalMazeDsl.g:3133:1: ruleDOUBLE returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= '-' )? this_INT_1= RULE_INT (kw= '.' this_INT_3= RULE_INT )? ) ;
    public final AntlrDatatypeRuleToken ruleDOUBLE() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_INT_1=null;
        Token this_INT_3=null;


        	enterRule();

        try {
            // InternalMazeDsl.g:3139:2: ( ( (kw= '-' )? this_INT_1= RULE_INT (kw= '.' this_INT_3= RULE_INT )? ) )
            // InternalMazeDsl.g:3140:2: ( (kw= '-' )? this_INT_1= RULE_INT (kw= '.' this_INT_3= RULE_INT )? )
            {
            // InternalMazeDsl.g:3140:2: ( (kw= '-' )? this_INT_1= RULE_INT (kw= '.' this_INT_3= RULE_INT )? )
            // InternalMazeDsl.g:3141:3: (kw= '-' )? this_INT_1= RULE_INT (kw= '.' this_INT_3= RULE_INT )?
            {
            // InternalMazeDsl.g:3141:3: (kw= '-' )?
            int alt58=2;
            int LA58_0 = input.LA(1);

            if ( (LA58_0==101) ) {
                alt58=1;
            }
            switch (alt58) {
                case 1 :
                    // InternalMazeDsl.g:3142:4: kw= '-'
                    {
                    kw=(Token)match(input,101,FOLLOW_36); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getDOUBLEAccess().getHyphenMinusKeyword_0());
                    			

                    }
                    break;

            }

            this_INT_1=(Token)match(input,RULE_INT,FOLLOW_104); 

            			current.merge(this_INT_1);
            		

            			newLeafNode(this_INT_1, grammarAccess.getDOUBLEAccess().getINTTerminalRuleCall_1());
            		
            // InternalMazeDsl.g:3155:3: (kw= '.' this_INT_3= RULE_INT )?
            int alt59=2;
            int LA59_0 = input.LA(1);

            if ( (LA59_0==102) ) {
                alt59=1;
            }
            switch (alt59) {
                case 1 :
                    // InternalMazeDsl.g:3156:4: kw= '.' this_INT_3= RULE_INT
                    {
                    kw=(Token)match(input,102,FOLLOW_36); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getDOUBLEAccess().getFullStopKeyword_2_0());
                    			
                    this_INT_3=(Token)match(input,RULE_INT,FOLLOW_2); 

                    				current.merge(this_INT_3);
                    			

                    				newLeafNode(this_INT_3, grammarAccess.getDOUBLEAccess().getINTTerminalRuleCall_2_1());
                    			

                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDOUBLE"


    // $ANTLR start "entryRuleSIGNED_INT"
    // InternalMazeDsl.g:3173:1: entryRuleSIGNED_INT returns [String current=null] : iv_ruleSIGNED_INT= ruleSIGNED_INT EOF ;
    public final String entryRuleSIGNED_INT() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSIGNED_INT = null;


        try {
            // InternalMazeDsl.g:3173:50: (iv_ruleSIGNED_INT= ruleSIGNED_INT EOF )
            // InternalMazeDsl.g:3174:2: iv_ruleSIGNED_INT= ruleSIGNED_INT EOF
            {
             newCompositeNode(grammarAccess.getSIGNED_INTRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleSIGNED_INT=ruleSIGNED_INT();

            state._fsp--;

             current =iv_ruleSIGNED_INT.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSIGNED_INT"


    // $ANTLR start "ruleSIGNED_INT"
    // InternalMazeDsl.g:3180:1: ruleSIGNED_INT returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= '-' )? this_INT_1= RULE_INT ) ;
    public final AntlrDatatypeRuleToken ruleSIGNED_INT() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_INT_1=null;


        	enterRule();

        try {
            // InternalMazeDsl.g:3186:2: ( ( (kw= '-' )? this_INT_1= RULE_INT ) )
            // InternalMazeDsl.g:3187:2: ( (kw= '-' )? this_INT_1= RULE_INT )
            {
            // InternalMazeDsl.g:3187:2: ( (kw= '-' )? this_INT_1= RULE_INT )
            // InternalMazeDsl.g:3188:3: (kw= '-' )? this_INT_1= RULE_INT
            {
            // InternalMazeDsl.g:3188:3: (kw= '-' )?
            int alt60=2;
            int LA60_0 = input.LA(1);

            if ( (LA60_0==101) ) {
                alt60=1;
            }
            switch (alt60) {
                case 1 :
                    // InternalMazeDsl.g:3189:4: kw= '-'
                    {
                    kw=(Token)match(input,101,FOLLOW_36); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getSIGNED_INTAccess().getHyphenMinusKeyword_0());
                    			

                    }
                    break;

            }

            this_INT_1=(Token)match(input,RULE_INT,FOLLOW_2); 

            			current.merge(this_INT_1);
            		

            			newLeafNode(this_INT_1, grammarAccess.getSIGNED_INTAccess().getINTTerminalRuleCall_1());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSIGNED_INT"


    // $ANTLR start "entryRuleBOOLEAN"
    // InternalMazeDsl.g:3206:1: entryRuleBOOLEAN returns [String current=null] : iv_ruleBOOLEAN= ruleBOOLEAN EOF ;
    public final String entryRuleBOOLEAN() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleBOOLEAN = null;


        try {
            // InternalMazeDsl.g:3206:47: (iv_ruleBOOLEAN= ruleBOOLEAN EOF )
            // InternalMazeDsl.g:3207:2: iv_ruleBOOLEAN= ruleBOOLEAN EOF
            {
             newCompositeNode(grammarAccess.getBOOLEANRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleBOOLEAN=ruleBOOLEAN();

            state._fsp--;

             current =iv_ruleBOOLEAN.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleBOOLEAN"


    // $ANTLR start "ruleBOOLEAN"
    // InternalMazeDsl.g:3213:1: ruleBOOLEAN returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'true' | kw= 'false' ) ;
    public final AntlrDatatypeRuleToken ruleBOOLEAN() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalMazeDsl.g:3219:2: ( (kw= 'true' | kw= 'false' ) )
            // InternalMazeDsl.g:3220:2: (kw= 'true' | kw= 'false' )
            {
            // InternalMazeDsl.g:3220:2: (kw= 'true' | kw= 'false' )
            int alt61=2;
            int LA61_0 = input.LA(1);

            if ( (LA61_0==18) ) {
                alt61=1;
            }
            else if ( (LA61_0==103) ) {
                alt61=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 61, 0, input);

                throw nvae;
            }
            switch (alt61) {
                case 1 :
                    // InternalMazeDsl.g:3221:3: kw= 'true'
                    {
                    kw=(Token)match(input,18,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getBOOLEANAccess().getTrueKeyword_0());
                    		

                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:3227:3: kw= 'false'
                    {
                    kw=(Token)match(input,103,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getBOOLEANAccess().getFalseKeyword_1());
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleBOOLEAN"


    // $ANTLR start "entryRuleDOUBLE_OBJECT"
    // InternalMazeDsl.g:3236:1: entryRuleDOUBLE_OBJECT returns [String current=null] : iv_ruleDOUBLE_OBJECT= ruleDOUBLE_OBJECT EOF ;
    public final String entryRuleDOUBLE_OBJECT() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDOUBLE_OBJECT = null;


        try {
            // InternalMazeDsl.g:3236:53: (iv_ruleDOUBLE_OBJECT= ruleDOUBLE_OBJECT EOF )
            // InternalMazeDsl.g:3237:2: iv_ruleDOUBLE_OBJECT= ruleDOUBLE_OBJECT EOF
            {
             newCompositeNode(grammarAccess.getDOUBLE_OBJECTRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleDOUBLE_OBJECT=ruleDOUBLE_OBJECT();

            state._fsp--;

             current =iv_ruleDOUBLE_OBJECT.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleDOUBLE_OBJECT"


    // $ANTLR start "ruleDOUBLE_OBJECT"
    // InternalMazeDsl.g:3243:1: ruleDOUBLE_OBJECT returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= '-' )? this_INT_1= RULE_INT (kw= '.' this_INT_3= RULE_INT )? ) ;
    public final AntlrDatatypeRuleToken ruleDOUBLE_OBJECT() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_INT_1=null;
        Token this_INT_3=null;


        	enterRule();

        try {
            // InternalMazeDsl.g:3249:2: ( ( (kw= '-' )? this_INT_1= RULE_INT (kw= '.' this_INT_3= RULE_INT )? ) )
            // InternalMazeDsl.g:3250:2: ( (kw= '-' )? this_INT_1= RULE_INT (kw= '.' this_INT_3= RULE_INT )? )
            {
            // InternalMazeDsl.g:3250:2: ( (kw= '-' )? this_INT_1= RULE_INT (kw= '.' this_INT_3= RULE_INT )? )
            // InternalMazeDsl.g:3251:3: (kw= '-' )? this_INT_1= RULE_INT (kw= '.' this_INT_3= RULE_INT )?
            {
            // InternalMazeDsl.g:3251:3: (kw= '-' )?
            int alt62=2;
            int LA62_0 = input.LA(1);

            if ( (LA62_0==101) ) {
                alt62=1;
            }
            switch (alt62) {
                case 1 :
                    // InternalMazeDsl.g:3252:4: kw= '-'
                    {
                    kw=(Token)match(input,101,FOLLOW_36); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getDOUBLE_OBJECTAccess().getHyphenMinusKeyword_0());
                    			

                    }
                    break;

            }

            this_INT_1=(Token)match(input,RULE_INT,FOLLOW_104); 

            			current.merge(this_INT_1);
            		

            			newLeafNode(this_INT_1, grammarAccess.getDOUBLE_OBJECTAccess().getINTTerminalRuleCall_1());
            		
            // InternalMazeDsl.g:3265:3: (kw= '.' this_INT_3= RULE_INT )?
            int alt63=2;
            int LA63_0 = input.LA(1);

            if ( (LA63_0==102) ) {
                alt63=1;
            }
            switch (alt63) {
                case 1 :
                    // InternalMazeDsl.g:3266:4: kw= '.' this_INT_3= RULE_INT
                    {
                    kw=(Token)match(input,102,FOLLOW_36); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getDOUBLE_OBJECTAccess().getFullStopKeyword_2_0());
                    			
                    this_INT_3=(Token)match(input,RULE_INT,FOLLOW_2); 

                    				current.merge(this_INT_3);
                    			

                    				newLeafNode(this_INT_3, grammarAccess.getDOUBLE_OBJECTAccess().getINTTerminalRuleCall_2_1());
                    			

                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDOUBLE_OBJECT"


    // $ANTLR start "entryRuleINT_OBJECT"
    // InternalMazeDsl.g:3283:1: entryRuleINT_OBJECT returns [String current=null] : iv_ruleINT_OBJECT= ruleINT_OBJECT EOF ;
    public final String entryRuleINT_OBJECT() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleINT_OBJECT = null;


        try {
            // InternalMazeDsl.g:3283:50: (iv_ruleINT_OBJECT= ruleINT_OBJECT EOF )
            // InternalMazeDsl.g:3284:2: iv_ruleINT_OBJECT= ruleINT_OBJECT EOF
            {
             newCompositeNode(grammarAccess.getINT_OBJECTRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleINT_OBJECT=ruleINT_OBJECT();

            state._fsp--;

             current =iv_ruleINT_OBJECT.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleINT_OBJECT"


    // $ANTLR start "ruleINT_OBJECT"
    // InternalMazeDsl.g:3290:1: ruleINT_OBJECT returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= '-' )? this_INT_1= RULE_INT ) ;
    public final AntlrDatatypeRuleToken ruleINT_OBJECT() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_INT_1=null;


        	enterRule();

        try {
            // InternalMazeDsl.g:3296:2: ( ( (kw= '-' )? this_INT_1= RULE_INT ) )
            // InternalMazeDsl.g:3297:2: ( (kw= '-' )? this_INT_1= RULE_INT )
            {
            // InternalMazeDsl.g:3297:2: ( (kw= '-' )? this_INT_1= RULE_INT )
            // InternalMazeDsl.g:3298:3: (kw= '-' )? this_INT_1= RULE_INT
            {
            // InternalMazeDsl.g:3298:3: (kw= '-' )?
            int alt64=2;
            int LA64_0 = input.LA(1);

            if ( (LA64_0==101) ) {
                alt64=1;
            }
            switch (alt64) {
                case 1 :
                    // InternalMazeDsl.g:3299:4: kw= '-'
                    {
                    kw=(Token)match(input,101,FOLLOW_36); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getINT_OBJECTAccess().getHyphenMinusKeyword_0());
                    			

                    }
                    break;

            }

            this_INT_1=(Token)match(input,RULE_INT,FOLLOW_2); 

            			current.merge(this_INT_1);
            		

            			newLeafNode(this_INT_1, grammarAccess.getINT_OBJECTAccess().getINTTerminalRuleCall_1());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleINT_OBJECT"


    // $ANTLR start "entryRuleBOOLEAN_OBJECT"
    // InternalMazeDsl.g:3316:1: entryRuleBOOLEAN_OBJECT returns [String current=null] : iv_ruleBOOLEAN_OBJECT= ruleBOOLEAN_OBJECT EOF ;
    public final String entryRuleBOOLEAN_OBJECT() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleBOOLEAN_OBJECT = null;


        try {
            // InternalMazeDsl.g:3316:54: (iv_ruleBOOLEAN_OBJECT= ruleBOOLEAN_OBJECT EOF )
            // InternalMazeDsl.g:3317:2: iv_ruleBOOLEAN_OBJECT= ruleBOOLEAN_OBJECT EOF
            {
             newCompositeNode(grammarAccess.getBOOLEAN_OBJECTRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleBOOLEAN_OBJECT=ruleBOOLEAN_OBJECT();

            state._fsp--;

             current =iv_ruleBOOLEAN_OBJECT.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleBOOLEAN_OBJECT"


    // $ANTLR start "ruleBOOLEAN_OBJECT"
    // InternalMazeDsl.g:3323:1: ruleBOOLEAN_OBJECT returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'true' | kw= 'false' ) ;
    public final AntlrDatatypeRuleToken ruleBOOLEAN_OBJECT() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalMazeDsl.g:3329:2: ( (kw= 'true' | kw= 'false' ) )
            // InternalMazeDsl.g:3330:2: (kw= 'true' | kw= 'false' )
            {
            // InternalMazeDsl.g:3330:2: (kw= 'true' | kw= 'false' )
            int alt65=2;
            int LA65_0 = input.LA(1);

            if ( (LA65_0==18) ) {
                alt65=1;
            }
            else if ( (LA65_0==103) ) {
                alt65=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 65, 0, input);

                throw nvae;
            }
            switch (alt65) {
                case 1 :
                    // InternalMazeDsl.g:3331:3: kw= 'true'
                    {
                    kw=(Token)match(input,18,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getBOOLEAN_OBJECTAccess().getTrueKeyword_0());
                    		

                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:3337:3: kw= 'false'
                    {
                    kw=(Token)match(input,103,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getBOOLEAN_OBJECTAccess().getFalseKeyword_1());
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleBOOLEAN_OBJECT"


    // $ANTLR start "ruleDifficultyLevel"
    // InternalMazeDsl.g:3346:1: ruleDifficultyLevel returns [Enumerator current=null] : ( (enumLiteral_0= 'easy' ) | (enumLiteral_1= 'normal' ) | (enumLiteral_2= 'hard' ) ) ;
    public final Enumerator ruleDifficultyLevel() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalMazeDsl.g:3352:2: ( ( (enumLiteral_0= 'easy' ) | (enumLiteral_1= 'normal' ) | (enumLiteral_2= 'hard' ) ) )
            // InternalMazeDsl.g:3353:2: ( (enumLiteral_0= 'easy' ) | (enumLiteral_1= 'normal' ) | (enumLiteral_2= 'hard' ) )
            {
            // InternalMazeDsl.g:3353:2: ( (enumLiteral_0= 'easy' ) | (enumLiteral_1= 'normal' ) | (enumLiteral_2= 'hard' ) )
            int alt66=3;
            switch ( input.LA(1) ) {
            case 104:
                {
                alt66=1;
                }
                break;
            case 105:
                {
                alt66=2;
                }
                break;
            case 106:
                {
                alt66=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 66, 0, input);

                throw nvae;
            }

            switch (alt66) {
                case 1 :
                    // InternalMazeDsl.g:3354:3: (enumLiteral_0= 'easy' )
                    {
                    // InternalMazeDsl.g:3354:3: (enumLiteral_0= 'easy' )
                    // InternalMazeDsl.g:3355:4: enumLiteral_0= 'easy'
                    {
                    enumLiteral_0=(Token)match(input,104,FOLLOW_2); 

                    				current = grammarAccess.getDifficultyLevelAccess().getEASYEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getDifficultyLevelAccess().getEASYEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:3362:3: (enumLiteral_1= 'normal' )
                    {
                    // InternalMazeDsl.g:3362:3: (enumLiteral_1= 'normal' )
                    // InternalMazeDsl.g:3363:4: enumLiteral_1= 'normal'
                    {
                    enumLiteral_1=(Token)match(input,105,FOLLOW_2); 

                    				current = grammarAccess.getDifficultyLevelAccess().getNORMALEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getDifficultyLevelAccess().getNORMALEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalMazeDsl.g:3370:3: (enumLiteral_2= 'hard' )
                    {
                    // InternalMazeDsl.g:3370:3: (enumLiteral_2= 'hard' )
                    // InternalMazeDsl.g:3371:4: enumLiteral_2= 'hard'
                    {
                    enumLiteral_2=(Token)match(input,106,FOLLOW_2); 

                    				current = grammarAccess.getDifficultyLevelAccess().getHARDEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getDifficultyLevelAccess().getHARDEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDifficultyLevel"


    // $ANTLR start "ruleEnemyType"
    // InternalMazeDsl.g:3381:1: ruleEnemyType returns [Enumerator current=null] : ( (enumLiteral_0= 'zombie' ) | (enumLiteral_1= 'ghost' ) | (enumLiteral_2= 'pumpkinbomber' ) ) ;
    public final Enumerator ruleEnemyType() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalMazeDsl.g:3387:2: ( ( (enumLiteral_0= 'zombie' ) | (enumLiteral_1= 'ghost' ) | (enumLiteral_2= 'pumpkinbomber' ) ) )
            // InternalMazeDsl.g:3388:2: ( (enumLiteral_0= 'zombie' ) | (enumLiteral_1= 'ghost' ) | (enumLiteral_2= 'pumpkinbomber' ) )
            {
            // InternalMazeDsl.g:3388:2: ( (enumLiteral_0= 'zombie' ) | (enumLiteral_1= 'ghost' ) | (enumLiteral_2= 'pumpkinbomber' ) )
            int alt67=3;
            switch ( input.LA(1) ) {
            case 107:
                {
                alt67=1;
                }
                break;
            case 108:
                {
                alt67=2;
                }
                break;
            case 109:
                {
                alt67=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 67, 0, input);

                throw nvae;
            }

            switch (alt67) {
                case 1 :
                    // InternalMazeDsl.g:3389:3: (enumLiteral_0= 'zombie' )
                    {
                    // InternalMazeDsl.g:3389:3: (enumLiteral_0= 'zombie' )
                    // InternalMazeDsl.g:3390:4: enumLiteral_0= 'zombie'
                    {
                    enumLiteral_0=(Token)match(input,107,FOLLOW_2); 

                    				current = grammarAccess.getEnemyTypeAccess().getZOMBIEEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getEnemyTypeAccess().getZOMBIEEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:3397:3: (enumLiteral_1= 'ghost' )
                    {
                    // InternalMazeDsl.g:3397:3: (enumLiteral_1= 'ghost' )
                    // InternalMazeDsl.g:3398:4: enumLiteral_1= 'ghost'
                    {
                    enumLiteral_1=(Token)match(input,108,FOLLOW_2); 

                    				current = grammarAccess.getEnemyTypeAccess().getGHOSTEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getEnemyTypeAccess().getGHOSTEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalMazeDsl.g:3405:3: (enumLiteral_2= 'pumpkinbomber' )
                    {
                    // InternalMazeDsl.g:3405:3: (enumLiteral_2= 'pumpkinbomber' )
                    // InternalMazeDsl.g:3406:4: enumLiteral_2= 'pumpkinbomber'
                    {
                    enumLiteral_2=(Token)match(input,109,FOLLOW_2); 

                    				current = grammarAccess.getEnemyTypeAccess().getPUMPKINBOMBEREnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getEnemyTypeAccess().getPUMPKINBOMBEREnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleEnemyType"


    // $ANTLR start "ruleCharacterTypeEnum"
    // InternalMazeDsl.g:3416:1: ruleCharacterTypeEnum returns [Enumerator current=null] : ( (enumLiteral_0= 'zombie' ) | (enumLiteral_1= 'ghost' ) | (enumLiteral_2= 'pumpkinbomber' ) ) ;
    public final Enumerator ruleCharacterTypeEnum() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalMazeDsl.g:3422:2: ( ( (enumLiteral_0= 'zombie' ) | (enumLiteral_1= 'ghost' ) | (enumLiteral_2= 'pumpkinbomber' ) ) )
            // InternalMazeDsl.g:3423:2: ( (enumLiteral_0= 'zombie' ) | (enumLiteral_1= 'ghost' ) | (enumLiteral_2= 'pumpkinbomber' ) )
            {
            // InternalMazeDsl.g:3423:2: ( (enumLiteral_0= 'zombie' ) | (enumLiteral_1= 'ghost' ) | (enumLiteral_2= 'pumpkinbomber' ) )
            int alt68=3;
            switch ( input.LA(1) ) {
            case 107:
                {
                alt68=1;
                }
                break;
            case 108:
                {
                alt68=2;
                }
                break;
            case 109:
                {
                alt68=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 68, 0, input);

                throw nvae;
            }

            switch (alt68) {
                case 1 :
                    // InternalMazeDsl.g:3424:3: (enumLiteral_0= 'zombie' )
                    {
                    // InternalMazeDsl.g:3424:3: (enumLiteral_0= 'zombie' )
                    // InternalMazeDsl.g:3425:4: enumLiteral_0= 'zombie'
                    {
                    enumLiteral_0=(Token)match(input,107,FOLLOW_2); 

                    				current = grammarAccess.getCharacterTypeEnumAccess().getZOMBIEEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getCharacterTypeEnumAccess().getZOMBIEEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:3432:3: (enumLiteral_1= 'ghost' )
                    {
                    // InternalMazeDsl.g:3432:3: (enumLiteral_1= 'ghost' )
                    // InternalMazeDsl.g:3433:4: enumLiteral_1= 'ghost'
                    {
                    enumLiteral_1=(Token)match(input,108,FOLLOW_2); 

                    				current = grammarAccess.getCharacterTypeEnumAccess().getGHOSTEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getCharacterTypeEnumAccess().getGHOSTEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalMazeDsl.g:3440:3: (enumLiteral_2= 'pumpkinbomber' )
                    {
                    // InternalMazeDsl.g:3440:3: (enumLiteral_2= 'pumpkinbomber' )
                    // InternalMazeDsl.g:3441:4: enumLiteral_2= 'pumpkinbomber'
                    {
                    enumLiteral_2=(Token)match(input,109,FOLLOW_2); 

                    				current = grammarAccess.getCharacterTypeEnumAccess().getPUMPKINBOMBEREnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getCharacterTypeEnumAccess().getPUMPKINBOMBEREnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleCharacterTypeEnum"


    // $ANTLR start "ruleBehaviorTypeEnum"
    // InternalMazeDsl.g:3451:1: ruleBehaviorTypeEnum returns [Enumerator current=null] : ( (enumLiteral_0= 'passive' ) | (enumLiteral_1= 'wander' ) | (enumLiteral_2= 'aggressive' ) | (enumLiteral_3= 'patrol' ) ) ;
    public final Enumerator ruleBehaviorTypeEnum() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;


        	enterRule();

        try {
            // InternalMazeDsl.g:3457:2: ( ( (enumLiteral_0= 'passive' ) | (enumLiteral_1= 'wander' ) | (enumLiteral_2= 'aggressive' ) | (enumLiteral_3= 'patrol' ) ) )
            // InternalMazeDsl.g:3458:2: ( (enumLiteral_0= 'passive' ) | (enumLiteral_1= 'wander' ) | (enumLiteral_2= 'aggressive' ) | (enumLiteral_3= 'patrol' ) )
            {
            // InternalMazeDsl.g:3458:2: ( (enumLiteral_0= 'passive' ) | (enumLiteral_1= 'wander' ) | (enumLiteral_2= 'aggressive' ) | (enumLiteral_3= 'patrol' ) )
            int alt69=4;
            switch ( input.LA(1) ) {
            case 110:
                {
                alt69=1;
                }
                break;
            case 111:
                {
                alt69=2;
                }
                break;
            case 112:
                {
                alt69=3;
                }
                break;
            case 32:
                {
                alt69=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 69, 0, input);

                throw nvae;
            }

            switch (alt69) {
                case 1 :
                    // InternalMazeDsl.g:3459:3: (enumLiteral_0= 'passive' )
                    {
                    // InternalMazeDsl.g:3459:3: (enumLiteral_0= 'passive' )
                    // InternalMazeDsl.g:3460:4: enumLiteral_0= 'passive'
                    {
                    enumLiteral_0=(Token)match(input,110,FOLLOW_2); 

                    				current = grammarAccess.getBehaviorTypeEnumAccess().getPASSIVEEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getBehaviorTypeEnumAccess().getPASSIVEEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:3467:3: (enumLiteral_1= 'wander' )
                    {
                    // InternalMazeDsl.g:3467:3: (enumLiteral_1= 'wander' )
                    // InternalMazeDsl.g:3468:4: enumLiteral_1= 'wander'
                    {
                    enumLiteral_1=(Token)match(input,111,FOLLOW_2); 

                    				current = grammarAccess.getBehaviorTypeEnumAccess().getWANDEREnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getBehaviorTypeEnumAccess().getWANDEREnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalMazeDsl.g:3475:3: (enumLiteral_2= 'aggressive' )
                    {
                    // InternalMazeDsl.g:3475:3: (enumLiteral_2= 'aggressive' )
                    // InternalMazeDsl.g:3476:4: enumLiteral_2= 'aggressive'
                    {
                    enumLiteral_2=(Token)match(input,112,FOLLOW_2); 

                    				current = grammarAccess.getBehaviorTypeEnumAccess().getAGGRESSIVEEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getBehaviorTypeEnumAccess().getAGGRESSIVEEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalMazeDsl.g:3483:3: (enumLiteral_3= 'patrol' )
                    {
                    // InternalMazeDsl.g:3483:3: (enumLiteral_3= 'patrol' )
                    // InternalMazeDsl.g:3484:4: enumLiteral_3= 'patrol'
                    {
                    enumLiteral_3=(Token)match(input,32,FOLLOW_2); 

                    				current = grammarAccess.getBehaviorTypeEnumAccess().getPATROLEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_3, grammarAccess.getBehaviorTypeEnumAccess().getPATROLEnumLiteralDeclaration_3());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleBehaviorTypeEnum"


    // $ANTLR start "ruleProjectileTypeEnum"
    // InternalMazeDsl.g:3494:1: ruleProjectileTypeEnum returns [Enumerator current=null] : ( (enumLiteral_0= 'straight' ) | (enumLiteral_1= 'lob' ) | (enumLiteral_2= 'beam' ) ) ;
    public final Enumerator ruleProjectileTypeEnum() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalMazeDsl.g:3500:2: ( ( (enumLiteral_0= 'straight' ) | (enumLiteral_1= 'lob' ) | (enumLiteral_2= 'beam' ) ) )
            // InternalMazeDsl.g:3501:2: ( (enumLiteral_0= 'straight' ) | (enumLiteral_1= 'lob' ) | (enumLiteral_2= 'beam' ) )
            {
            // InternalMazeDsl.g:3501:2: ( (enumLiteral_0= 'straight' ) | (enumLiteral_1= 'lob' ) | (enumLiteral_2= 'beam' ) )
            int alt70=3;
            switch ( input.LA(1) ) {
            case 113:
                {
                alt70=1;
                }
                break;
            case 114:
                {
                alt70=2;
                }
                break;
            case 115:
                {
                alt70=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 70, 0, input);

                throw nvae;
            }

            switch (alt70) {
                case 1 :
                    // InternalMazeDsl.g:3502:3: (enumLiteral_0= 'straight' )
                    {
                    // InternalMazeDsl.g:3502:3: (enumLiteral_0= 'straight' )
                    // InternalMazeDsl.g:3503:4: enumLiteral_0= 'straight'
                    {
                    enumLiteral_0=(Token)match(input,113,FOLLOW_2); 

                    				current = grammarAccess.getProjectileTypeEnumAccess().getSTRAIGHTEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getProjectileTypeEnumAccess().getSTRAIGHTEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:3510:3: (enumLiteral_1= 'lob' )
                    {
                    // InternalMazeDsl.g:3510:3: (enumLiteral_1= 'lob' )
                    // InternalMazeDsl.g:3511:4: enumLiteral_1= 'lob'
                    {
                    enumLiteral_1=(Token)match(input,114,FOLLOW_2); 

                    				current = grammarAccess.getProjectileTypeEnumAccess().getLOBEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getProjectileTypeEnumAccess().getLOBEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalMazeDsl.g:3518:3: (enumLiteral_2= 'beam' )
                    {
                    // InternalMazeDsl.g:3518:3: (enumLiteral_2= 'beam' )
                    // InternalMazeDsl.g:3519:4: enumLiteral_2= 'beam'
                    {
                    enumLiteral_2=(Token)match(input,115,FOLLOW_2); 

                    				current = grammarAccess.getProjectileTypeEnumAccess().getBEAMEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getProjectileTypeEnumAccess().getBEAMEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleProjectileTypeEnum"


    // $ANTLR start "ruleLootItemTypeEnum"
    // InternalMazeDsl.g:3529:1: ruleLootItemTypeEnum returns [Enumerator current=null] : ( (enumLiteral_0= 'food' ) | (enumLiteral_1= 'bomb' ) | (enumLiteral_2= 'trap' ) | (enumLiteral_3= 'weapon' ) ) ;
    public final Enumerator ruleLootItemTypeEnum() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;


        	enterRule();

        try {
            // InternalMazeDsl.g:3535:2: ( ( (enumLiteral_0= 'food' ) | (enumLiteral_1= 'bomb' ) | (enumLiteral_2= 'trap' ) | (enumLiteral_3= 'weapon' ) ) )
            // InternalMazeDsl.g:3536:2: ( (enumLiteral_0= 'food' ) | (enumLiteral_1= 'bomb' ) | (enumLiteral_2= 'trap' ) | (enumLiteral_3= 'weapon' ) )
            {
            // InternalMazeDsl.g:3536:2: ( (enumLiteral_0= 'food' ) | (enumLiteral_1= 'bomb' ) | (enumLiteral_2= 'trap' ) | (enumLiteral_3= 'weapon' ) )
            int alt71=4;
            switch ( input.LA(1) ) {
            case 116:
                {
                alt71=1;
                }
                break;
            case 117:
                {
                alt71=2;
                }
                break;
            case 118:
                {
                alt71=3;
                }
                break;
            case 119:
                {
                alt71=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 71, 0, input);

                throw nvae;
            }

            switch (alt71) {
                case 1 :
                    // InternalMazeDsl.g:3537:3: (enumLiteral_0= 'food' )
                    {
                    // InternalMazeDsl.g:3537:3: (enumLiteral_0= 'food' )
                    // InternalMazeDsl.g:3538:4: enumLiteral_0= 'food'
                    {
                    enumLiteral_0=(Token)match(input,116,FOLLOW_2); 

                    				current = grammarAccess.getLootItemTypeEnumAccess().getFOODEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getLootItemTypeEnumAccess().getFOODEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:3545:3: (enumLiteral_1= 'bomb' )
                    {
                    // InternalMazeDsl.g:3545:3: (enumLiteral_1= 'bomb' )
                    // InternalMazeDsl.g:3546:4: enumLiteral_1= 'bomb'
                    {
                    enumLiteral_1=(Token)match(input,117,FOLLOW_2); 

                    				current = grammarAccess.getLootItemTypeEnumAccess().getBOMBEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getLootItemTypeEnumAccess().getBOMBEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalMazeDsl.g:3553:3: (enumLiteral_2= 'trap' )
                    {
                    // InternalMazeDsl.g:3553:3: (enumLiteral_2= 'trap' )
                    // InternalMazeDsl.g:3554:4: enumLiteral_2= 'trap'
                    {
                    enumLiteral_2=(Token)match(input,118,FOLLOW_2); 

                    				current = grammarAccess.getLootItemTypeEnumAccess().getTRAPEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getLootItemTypeEnumAccess().getTRAPEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalMazeDsl.g:3561:3: (enumLiteral_3= 'weapon' )
                    {
                    // InternalMazeDsl.g:3561:3: (enumLiteral_3= 'weapon' )
                    // InternalMazeDsl.g:3562:4: enumLiteral_3= 'weapon'
                    {
                    enumLiteral_3=(Token)match(input,119,FOLLOW_2); 

                    				current = grammarAccess.getLootItemTypeEnumAccess().getWEAPONEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_3, grammarAccess.getLootItemTypeEnumAccess().getWEAPONEnumLiteralDeclaration_3());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleLootItemTypeEnum"


    // $ANTLR start "ruleDropWeaponEnum"
    // InternalMazeDsl.g:3572:1: ruleDropWeaponEnum returns [Enumerator current=null] : (enumLiteral_0= 'gatling' ) ;
    public final Enumerator ruleDropWeaponEnum() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalMazeDsl.g:3578:2: ( (enumLiteral_0= 'gatling' ) )
            // InternalMazeDsl.g:3579:2: (enumLiteral_0= 'gatling' )
            {
            // InternalMazeDsl.g:3579:2: (enumLiteral_0= 'gatling' )
            // InternalMazeDsl.g:3580:3: enumLiteral_0= 'gatling'
            {
            enumLiteral_0=(Token)match(input,120,FOLLOW_2); 

            			current = grammarAccess.getDropWeaponEnumAccess().getGATLING_GUNEnumLiteralDeclaration().getEnumLiteral().getInstance();
            			newLeafNode(enumLiteral_0, grammarAccess.getDropWeaponEnumAccess().getGATLING_GUNEnumLiteralDeclaration());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDropWeaponEnum"

    // Delegated rules


 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x100000010101E000L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x1000000101012000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x1000000100012000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x1000000000012000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000012000L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000000000L,0x0000070000000000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x00000000007A2000L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000000782000L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000000000040L,0x0000002000000000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000000702000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000000602000L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000000402000L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000000000000L,0x0000380000000000L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x00000247FC002000L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x00000247F8002000L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x00000247F0002000L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x00000247E0002000L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x00000247C0002000L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000000000040000L,0x0000008000000000L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000024780002000L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0000000100000000L,0x0001C00000000000L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0000024700002000L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0000000300002000L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0000000200002000L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000003800002000L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x0000003000002000L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x0000002000002000L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x0000018800002000L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x0000018000002000L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x0000010000002000L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x00007C0800002000L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x0000780800002000L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x0000700800002000L});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0x0000700000002000L});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x0000600000002000L});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x0000000000000000L,0x000E000000000000L});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0x0000400000002000L});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x0011800000000000L});
    public static final BitSet FOLLOW_50 = new BitSet(new long[]{0x0011000000000000L});
    public static final BitSet FOLLOW_51 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_52 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_53 = new BitSet(new long[]{0x0040000000000000L});
    public static final BitSet FOLLOW_54 = new BitSet(new long[]{0x000C000000000000L});
    public static final BitSet FOLLOW_55 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_56 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_57 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_58 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_59 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_60 = new BitSet(new long[]{0x0400000000000002L});
    public static final BitSet FOLLOW_61 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_62 = new BitSet(new long[]{0x6000000000000000L});
    public static final BitSet FOLLOW_63 = new BitSet(new long[]{0x6000000000002000L});
    public static final BitSet FOLLOW_64 = new BitSet(new long[]{0x0000000000000000L,0x00F0000000000000L});
    public static final BitSet FOLLOW_65 = new BitSet(new long[]{0x8000000000000000L});
    public static final BitSet FOLLOW_66 = new BitSet(new long[]{0x0000000000002000L,0x0000000000000001L});
    public static final BitSet FOLLOW_67 = new BitSet(new long[]{0x0000000000002000L,0x00000000E018027EL});
    public static final BitSet FOLLOW_68 = new BitSet(new long[]{0x0000000000002000L,0x00000000E018027CL});
    public static final BitSet FOLLOW_69 = new BitSet(new long[]{0x0000000000002000L,0x00000000E0180278L});
    public static final BitSet FOLLOW_70 = new BitSet(new long[]{0x0000000000002000L,0x00000000E0180270L});
    public static final BitSet FOLLOW_71 = new BitSet(new long[]{0x0000000000002000L,0x00000000E0180260L});
    public static final BitSet FOLLOW_72 = new BitSet(new long[]{0x0000000000002000L,0x00000000E0180240L});
    public static final BitSet FOLLOW_73 = new BitSet(new long[]{0x0000000000002000L,0x00000000E0180000L});
    public static final BitSet FOLLOW_74 = new BitSet(new long[]{0x0000000000002000L,0x00000000E0000000L});
    public static final BitSet FOLLOW_75 = new BitSet(new long[]{0x0000000000002000L,0x00000000C0000000L});
    public static final BitSet FOLLOW_76 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000180L});
    public static final BitSet FOLLOW_77 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_78 = new BitSet(new long[]{0x0004000000000002L});
    public static final BitSet FOLLOW_79 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_80 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_81 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_82 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_83 = new BitSet(new long[]{0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_84 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_85 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_86 = new BitSet(new long[]{0x0000000000000000L,0x0000000000030000L});
    public static final BitSet FOLLOW_87 = new BitSet(new long[]{0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_88 = new BitSet(new long[]{0x0000000000000000L,0x0100000000000000L});
    public static final BitSet FOLLOW_89 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_90 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_91 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_92 = new BitSet(new long[]{0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_93 = new BitSet(new long[]{0x0000000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_94 = new BitSet(new long[]{0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_95 = new BitSet(new long[]{0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_96 = new BitSet(new long[]{0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_97 = new BitSet(new long[]{0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_98 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_99 = new BitSet(new long[]{0x0000000000000000L,0x0000000100000000L});
    public static final BitSet FOLLOW_100 = new BitSet(new long[]{0x0000000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_101 = new BitSet(new long[]{0x0000000000000000L,0x0000000400000000L});
    public static final BitSet FOLLOW_102 = new BitSet(new long[]{0x0000000000002000L,0x0000001800000000L});
    public static final BitSet FOLLOW_103 = new BitSet(new long[]{0x0000000000002000L,0x0000001000000000L});
    public static final BitSet FOLLOW_104 = new BitSet(new long[]{0x0000000000000002L,0x0000004000000000L});

}