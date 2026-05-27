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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_STRING", "RULE_INT", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'game'", "'{'", "'}'", "'import'", "'difficulty'", "'level'", "'instantDeath'", "'true'", "'speedMultiplier'", "'damageMultiplier'", "'maxThreat'", "'limit'", "'max'", "'opponent'", "'type'", "'displayName'", "'health'", "'speed'", "'threatLevel'", "'enabled'", "'behavior'", "'patrol'", "'loot'", "'zombie-stats'", "'attackDamage'", "'infectionLevel'", "'resurrectionTime'", "'ghost-stats'", "'visibilityLevel'", "'nonTangibilityEnergy'", "'ranged-stats'", "'attackRange'", "'attackCooldown'", "'projectileSpeed'", "'projectileType'", "'splashRadius'", "'visionRange'", "'path'", "'['", "','", "']'", "'zone'", "'topLeft'", "'('", "')'", "'width'", "'height'", "':'", "'ms'", "'loot-table'", "'capacity'", "'item'", "'value'", "'weight'", "'-'", "'.'", "'false'", "'easy'", "'normal'", "'hard'", "'zombie'", "'ghost'", "'pumpkinbomber'", "'passive'", "'wander'", "'aggressive'", "'straight'", "'lob'", "'beam'", "'food'", "'bomb'", "'trap'", "'weapon'"
    };
    public static final int T__50=50;
    public static final int T__19=19;
    public static final int T__15=15;
    public static final int T__59=59;
    public static final int T__16=16;
    public static final int T__17=17;
    public static final int T__18=18;
    public static final int T__11=11;
    public static final int T__55=55;
    public static final int T__12=12;
    public static final int T__56=56;
    public static final int T__13=13;
    public static final int T__57=57;
    public static final int T__14=14;
    public static final int T__58=58;
    public static final int T__51=51;
    public static final int T__52=52;
    public static final int T__53=53;
    public static final int T__54=54;
    public static final int T__60=60;
    public static final int T__61=61;
    public static final int RULE_ID=4;
    public static final int T__26=26;
    public static final int T__27=27;
    public static final int T__28=28;
    public static final int RULE_INT=6;
    public static final int T__29=29;
    public static final int T__22=22;
    public static final int T__66=66;
    public static final int RULE_ML_COMMENT=7;
    public static final int T__23=23;
    public static final int T__67=67;
    public static final int T__24=24;
    public static final int T__68=68;
    public static final int T__25=25;
    public static final int T__69=69;
    public static final int T__62=62;
    public static final int T__63=63;
    public static final int T__20=20;
    public static final int T__64=64;
    public static final int T__21=21;
    public static final int T__65=65;
    public static final int T__70=70;
    public static final int T__71=71;
    public static final int T__72=72;
    public static final int RULE_STRING=5;
    public static final int RULE_SL_COMMENT=8;
    public static final int T__37=37;
    public static final int T__38=38;
    public static final int T__39=39;
    public static final int T__33=33;
    public static final int T__77=77;
    public static final int T__34=34;
    public static final int T__78=78;
    public static final int T__35=35;
    public static final int T__79=79;
    public static final int T__36=36;
    public static final int T__73=73;
    public static final int EOF=-1;
    public static final int T__30=30;
    public static final int T__74=74;
    public static final int T__31=31;
    public static final int T__75=75;
    public static final int T__32=32;
    public static final int T__76=76;
    public static final int T__80=80;
    public static final int T__81=81;
    public static final int T__82=82;
    public static final int T__83=83;
    public static final int RULE_WS=9;
    public static final int RULE_ANY_OTHER=10;
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
    // InternalMazeDsl.g:72:1: ruleGameConfiguration returns [EObject current=null] : (otherlv_0= 'game' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_imports_3_0= ruleImport ) )* ( (lv_difficulty_4_0= ruleDifficultyConfig ) )? ( (lv_opponents_5_0= ruleOpponentConfig ) )* ( (lv_patrols_6_0= rulePatrolConfig ) )* ( (lv_lootTables_7_0= ruleLootTableConfig ) )* otherlv_8= '}' ) ;
    public final EObject ruleGameConfiguration() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_8=null;
        EObject lv_imports_3_0 = null;

        EObject lv_difficulty_4_0 = null;

        EObject lv_opponents_5_0 = null;

        EObject lv_patrols_6_0 = null;

        EObject lv_lootTables_7_0 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:78:2: ( (otherlv_0= 'game' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_imports_3_0= ruleImport ) )* ( (lv_difficulty_4_0= ruleDifficultyConfig ) )? ( (lv_opponents_5_0= ruleOpponentConfig ) )* ( (lv_patrols_6_0= rulePatrolConfig ) )* ( (lv_lootTables_7_0= ruleLootTableConfig ) )* otherlv_8= '}' ) )
            // InternalMazeDsl.g:79:2: (otherlv_0= 'game' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_imports_3_0= ruleImport ) )* ( (lv_difficulty_4_0= ruleDifficultyConfig ) )? ( (lv_opponents_5_0= ruleOpponentConfig ) )* ( (lv_patrols_6_0= rulePatrolConfig ) )* ( (lv_lootTables_7_0= ruleLootTableConfig ) )* otherlv_8= '}' )
            {
            // InternalMazeDsl.g:79:2: (otherlv_0= 'game' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_imports_3_0= ruleImport ) )* ( (lv_difficulty_4_0= ruleDifficultyConfig ) )? ( (lv_opponents_5_0= ruleOpponentConfig ) )* ( (lv_patrols_6_0= rulePatrolConfig ) )* ( (lv_lootTables_7_0= ruleLootTableConfig ) )* otherlv_8= '}' )
            // InternalMazeDsl.g:80:3: otherlv_0= 'game' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_imports_3_0= ruleImport ) )* ( (lv_difficulty_4_0= ruleDifficultyConfig ) )? ( (lv_opponents_5_0= ruleOpponentConfig ) )* ( (lv_patrols_6_0= rulePatrolConfig ) )* ( (lv_lootTables_7_0= ruleLootTableConfig ) )* otherlv_8= '}'
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

            otherlv_8=(Token)match(input,13,FOLLOW_2); 

            			newLeafNode(otherlv_8, grammarAccess.getGameConfigurationAccess().getRightCurlyBracketKeyword_8());
            		

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
    // InternalMazeDsl.g:209:1: entryRuleImport returns [EObject current=null] : iv_ruleImport= ruleImport EOF ;
    public final EObject entryRuleImport() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleImport = null;


        try {
            // InternalMazeDsl.g:209:47: (iv_ruleImport= ruleImport EOF )
            // InternalMazeDsl.g:210:2: iv_ruleImport= ruleImport EOF
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
    // InternalMazeDsl.g:216:1: ruleImport returns [EObject current=null] : (otherlv_0= 'import' ( (lv_importURI_1_0= RULE_STRING ) ) ) ;
    public final EObject ruleImport() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_importURI_1_0=null;


        	enterRule();

        try {
            // InternalMazeDsl.g:222:2: ( (otherlv_0= 'import' ( (lv_importURI_1_0= RULE_STRING ) ) ) )
            // InternalMazeDsl.g:223:2: (otherlv_0= 'import' ( (lv_importURI_1_0= RULE_STRING ) ) )
            {
            // InternalMazeDsl.g:223:2: (otherlv_0= 'import' ( (lv_importURI_1_0= RULE_STRING ) ) )
            // InternalMazeDsl.g:224:3: otherlv_0= 'import' ( (lv_importURI_1_0= RULE_STRING ) )
            {
            otherlv_0=(Token)match(input,14,FOLLOW_9); 

            			newLeafNode(otherlv_0, grammarAccess.getImportAccess().getImportKeyword_0());
            		
            // InternalMazeDsl.g:228:3: ( (lv_importURI_1_0= RULE_STRING ) )
            // InternalMazeDsl.g:229:4: (lv_importURI_1_0= RULE_STRING )
            {
            // InternalMazeDsl.g:229:4: (lv_importURI_1_0= RULE_STRING )
            // InternalMazeDsl.g:230:5: lv_importURI_1_0= RULE_STRING
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
    // InternalMazeDsl.g:250:1: entryRuleDifficultyConfig returns [EObject current=null] : iv_ruleDifficultyConfig= ruleDifficultyConfig EOF ;
    public final EObject entryRuleDifficultyConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDifficultyConfig = null;


        try {
            // InternalMazeDsl.g:250:57: (iv_ruleDifficultyConfig= ruleDifficultyConfig EOF )
            // InternalMazeDsl.g:251:2: iv_ruleDifficultyConfig= ruleDifficultyConfig EOF
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
    // InternalMazeDsl.g:257:1: ruleDifficultyConfig returns [EObject current=null] : (otherlv_0= 'difficulty' otherlv_1= '{' otherlv_2= 'level' ( (lv_level_3_0= ruleDifficultyLevel ) ) (otherlv_4= 'instantDeath' ( (lv_instantDeath_5_0= 'true' ) ) )? (otherlv_6= 'speedMultiplier' ( (lv_speedMultiplier_7_0= ruleDOUBLE ) ) )? (otherlv_8= 'damageMultiplier' ( (lv_damageMultiplier_9_0= ruleDOUBLE ) ) )? (otherlv_10= 'maxThreat' ( (lv_maxThreat_11_0= ruleSIGNED_INT ) ) )? ( (lv_enemyLimits_12_0= ruleEnemyLimit ) )* otherlv_13= '}' ) ;
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
            // InternalMazeDsl.g:263:2: ( (otherlv_0= 'difficulty' otherlv_1= '{' otherlv_2= 'level' ( (lv_level_3_0= ruleDifficultyLevel ) ) (otherlv_4= 'instantDeath' ( (lv_instantDeath_5_0= 'true' ) ) )? (otherlv_6= 'speedMultiplier' ( (lv_speedMultiplier_7_0= ruleDOUBLE ) ) )? (otherlv_8= 'damageMultiplier' ( (lv_damageMultiplier_9_0= ruleDOUBLE ) ) )? (otherlv_10= 'maxThreat' ( (lv_maxThreat_11_0= ruleSIGNED_INT ) ) )? ( (lv_enemyLimits_12_0= ruleEnemyLimit ) )* otherlv_13= '}' ) )
            // InternalMazeDsl.g:264:2: (otherlv_0= 'difficulty' otherlv_1= '{' otherlv_2= 'level' ( (lv_level_3_0= ruleDifficultyLevel ) ) (otherlv_4= 'instantDeath' ( (lv_instantDeath_5_0= 'true' ) ) )? (otherlv_6= 'speedMultiplier' ( (lv_speedMultiplier_7_0= ruleDOUBLE ) ) )? (otherlv_8= 'damageMultiplier' ( (lv_damageMultiplier_9_0= ruleDOUBLE ) ) )? (otherlv_10= 'maxThreat' ( (lv_maxThreat_11_0= ruleSIGNED_INT ) ) )? ( (lv_enemyLimits_12_0= ruleEnemyLimit ) )* otherlv_13= '}' )
            {
            // InternalMazeDsl.g:264:2: (otherlv_0= 'difficulty' otherlv_1= '{' otherlv_2= 'level' ( (lv_level_3_0= ruleDifficultyLevel ) ) (otherlv_4= 'instantDeath' ( (lv_instantDeath_5_0= 'true' ) ) )? (otherlv_6= 'speedMultiplier' ( (lv_speedMultiplier_7_0= ruleDOUBLE ) ) )? (otherlv_8= 'damageMultiplier' ( (lv_damageMultiplier_9_0= ruleDOUBLE ) ) )? (otherlv_10= 'maxThreat' ( (lv_maxThreat_11_0= ruleSIGNED_INT ) ) )? ( (lv_enemyLimits_12_0= ruleEnemyLimit ) )* otherlv_13= '}' )
            // InternalMazeDsl.g:265:3: otherlv_0= 'difficulty' otherlv_1= '{' otherlv_2= 'level' ( (lv_level_3_0= ruleDifficultyLevel ) ) (otherlv_4= 'instantDeath' ( (lv_instantDeath_5_0= 'true' ) ) )? (otherlv_6= 'speedMultiplier' ( (lv_speedMultiplier_7_0= ruleDOUBLE ) ) )? (otherlv_8= 'damageMultiplier' ( (lv_damageMultiplier_9_0= ruleDOUBLE ) ) )? (otherlv_10= 'maxThreat' ( (lv_maxThreat_11_0= ruleSIGNED_INT ) ) )? ( (lv_enemyLimits_12_0= ruleEnemyLimit ) )* otherlv_13= '}'
            {
            otherlv_0=(Token)match(input,15,FOLLOW_4); 

            			newLeafNode(otherlv_0, grammarAccess.getDifficultyConfigAccess().getDifficultyKeyword_0());
            		
            otherlv_1=(Token)match(input,12,FOLLOW_10); 

            			newLeafNode(otherlv_1, grammarAccess.getDifficultyConfigAccess().getLeftCurlyBracketKeyword_1());
            		
            otherlv_2=(Token)match(input,16,FOLLOW_11); 

            			newLeafNode(otherlv_2, grammarAccess.getDifficultyConfigAccess().getLevelKeyword_2());
            		
            // InternalMazeDsl.g:277:3: ( (lv_level_3_0= ruleDifficultyLevel ) )
            // InternalMazeDsl.g:278:4: (lv_level_3_0= ruleDifficultyLevel )
            {
            // InternalMazeDsl.g:278:4: (lv_level_3_0= ruleDifficultyLevel )
            // InternalMazeDsl.g:279:5: lv_level_3_0= ruleDifficultyLevel
            {

            					newCompositeNode(grammarAccess.getDifficultyConfigAccess().getLevelDifficultyLevelEnumRuleCall_3_0());
            				
            pushFollow(FOLLOW_12);
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

            // InternalMazeDsl.g:296:3: (otherlv_4= 'instantDeath' ( (lv_instantDeath_5_0= 'true' ) ) )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==17) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // InternalMazeDsl.g:297:4: otherlv_4= 'instantDeath' ( (lv_instantDeath_5_0= 'true' ) )
                    {
                    otherlv_4=(Token)match(input,17,FOLLOW_13); 

                    				newLeafNode(otherlv_4, grammarAccess.getDifficultyConfigAccess().getInstantDeathKeyword_4_0());
                    			
                    // InternalMazeDsl.g:301:4: ( (lv_instantDeath_5_0= 'true' ) )
                    // InternalMazeDsl.g:302:5: (lv_instantDeath_5_0= 'true' )
                    {
                    // InternalMazeDsl.g:302:5: (lv_instantDeath_5_0= 'true' )
                    // InternalMazeDsl.g:303:6: lv_instantDeath_5_0= 'true'
                    {
                    lv_instantDeath_5_0=(Token)match(input,18,FOLLOW_14); 

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

            // InternalMazeDsl.g:316:3: (otherlv_6= 'speedMultiplier' ( (lv_speedMultiplier_7_0= ruleDOUBLE ) ) )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==19) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // InternalMazeDsl.g:317:4: otherlv_6= 'speedMultiplier' ( (lv_speedMultiplier_7_0= ruleDOUBLE ) )
                    {
                    otherlv_6=(Token)match(input,19,FOLLOW_15); 

                    				newLeafNode(otherlv_6, grammarAccess.getDifficultyConfigAccess().getSpeedMultiplierKeyword_5_0());
                    			
                    // InternalMazeDsl.g:321:4: ( (lv_speedMultiplier_7_0= ruleDOUBLE ) )
                    // InternalMazeDsl.g:322:5: (lv_speedMultiplier_7_0= ruleDOUBLE )
                    {
                    // InternalMazeDsl.g:322:5: (lv_speedMultiplier_7_0= ruleDOUBLE )
                    // InternalMazeDsl.g:323:6: lv_speedMultiplier_7_0= ruleDOUBLE
                    {

                    						newCompositeNode(grammarAccess.getDifficultyConfigAccess().getSpeedMultiplierDOUBLEParserRuleCall_5_1_0());
                    					
                    pushFollow(FOLLOW_16);
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

            // InternalMazeDsl.g:341:3: (otherlv_8= 'damageMultiplier' ( (lv_damageMultiplier_9_0= ruleDOUBLE ) ) )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==20) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // InternalMazeDsl.g:342:4: otherlv_8= 'damageMultiplier' ( (lv_damageMultiplier_9_0= ruleDOUBLE ) )
                    {
                    otherlv_8=(Token)match(input,20,FOLLOW_15); 

                    				newLeafNode(otherlv_8, grammarAccess.getDifficultyConfigAccess().getDamageMultiplierKeyword_6_0());
                    			
                    // InternalMazeDsl.g:346:4: ( (lv_damageMultiplier_9_0= ruleDOUBLE ) )
                    // InternalMazeDsl.g:347:5: (lv_damageMultiplier_9_0= ruleDOUBLE )
                    {
                    // InternalMazeDsl.g:347:5: (lv_damageMultiplier_9_0= ruleDOUBLE )
                    // InternalMazeDsl.g:348:6: lv_damageMultiplier_9_0= ruleDOUBLE
                    {

                    						newCompositeNode(grammarAccess.getDifficultyConfigAccess().getDamageMultiplierDOUBLEParserRuleCall_6_1_0());
                    					
                    pushFollow(FOLLOW_17);
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

            // InternalMazeDsl.g:366:3: (otherlv_10= 'maxThreat' ( (lv_maxThreat_11_0= ruleSIGNED_INT ) ) )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==21) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // InternalMazeDsl.g:367:4: otherlv_10= 'maxThreat' ( (lv_maxThreat_11_0= ruleSIGNED_INT ) )
                    {
                    otherlv_10=(Token)match(input,21,FOLLOW_15); 

                    				newLeafNode(otherlv_10, grammarAccess.getDifficultyConfigAccess().getMaxThreatKeyword_7_0());
                    			
                    // InternalMazeDsl.g:371:4: ( (lv_maxThreat_11_0= ruleSIGNED_INT ) )
                    // InternalMazeDsl.g:372:5: (lv_maxThreat_11_0= ruleSIGNED_INT )
                    {
                    // InternalMazeDsl.g:372:5: (lv_maxThreat_11_0= ruleSIGNED_INT )
                    // InternalMazeDsl.g:373:6: lv_maxThreat_11_0= ruleSIGNED_INT
                    {

                    						newCompositeNode(grammarAccess.getDifficultyConfigAccess().getMaxThreatSIGNED_INTParserRuleCall_7_1_0());
                    					
                    pushFollow(FOLLOW_18);
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

            // InternalMazeDsl.g:391:3: ( (lv_enemyLimits_12_0= ruleEnemyLimit ) )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==22) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // InternalMazeDsl.g:392:4: (lv_enemyLimits_12_0= ruleEnemyLimit )
            	    {
            	    // InternalMazeDsl.g:392:4: (lv_enemyLimits_12_0= ruleEnemyLimit )
            	    // InternalMazeDsl.g:393:5: lv_enemyLimits_12_0= ruleEnemyLimit
            	    {

            	    					newCompositeNode(grammarAccess.getDifficultyConfigAccess().getEnemyLimitsEnemyLimitParserRuleCall_8_0());
            	    				
            	    pushFollow(FOLLOW_18);
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
            	    break loop10;
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
    // InternalMazeDsl.g:418:1: entryRuleEnemyLimit returns [EObject current=null] : iv_ruleEnemyLimit= ruleEnemyLimit EOF ;
    public final EObject entryRuleEnemyLimit() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEnemyLimit = null;


        try {
            // InternalMazeDsl.g:418:51: (iv_ruleEnemyLimit= ruleEnemyLimit EOF )
            // InternalMazeDsl.g:419:2: iv_ruleEnemyLimit= ruleEnemyLimit EOF
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
    // InternalMazeDsl.g:425:1: ruleEnemyLimit returns [EObject current=null] : (otherlv_0= 'limit' ( (lv_type_1_0= ruleEnemyType ) ) otherlv_2= 'max' ( (lv_maxCount_3_0= ruleSIGNED_INT ) ) ) ;
    public final EObject ruleEnemyLimit() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Enumerator lv_type_1_0 = null;

        AntlrDatatypeRuleToken lv_maxCount_3_0 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:431:2: ( (otherlv_0= 'limit' ( (lv_type_1_0= ruleEnemyType ) ) otherlv_2= 'max' ( (lv_maxCount_3_0= ruleSIGNED_INT ) ) ) )
            // InternalMazeDsl.g:432:2: (otherlv_0= 'limit' ( (lv_type_1_0= ruleEnemyType ) ) otherlv_2= 'max' ( (lv_maxCount_3_0= ruleSIGNED_INT ) ) )
            {
            // InternalMazeDsl.g:432:2: (otherlv_0= 'limit' ( (lv_type_1_0= ruleEnemyType ) ) otherlv_2= 'max' ( (lv_maxCount_3_0= ruleSIGNED_INT ) ) )
            // InternalMazeDsl.g:433:3: otherlv_0= 'limit' ( (lv_type_1_0= ruleEnemyType ) ) otherlv_2= 'max' ( (lv_maxCount_3_0= ruleSIGNED_INT ) )
            {
            otherlv_0=(Token)match(input,22,FOLLOW_19); 

            			newLeafNode(otherlv_0, grammarAccess.getEnemyLimitAccess().getLimitKeyword_0());
            		
            // InternalMazeDsl.g:437:3: ( (lv_type_1_0= ruleEnemyType ) )
            // InternalMazeDsl.g:438:4: (lv_type_1_0= ruleEnemyType )
            {
            // InternalMazeDsl.g:438:4: (lv_type_1_0= ruleEnemyType )
            // InternalMazeDsl.g:439:5: lv_type_1_0= ruleEnemyType
            {

            					newCompositeNode(grammarAccess.getEnemyLimitAccess().getTypeEnemyTypeEnumRuleCall_1_0());
            				
            pushFollow(FOLLOW_20);
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

            otherlv_2=(Token)match(input,23,FOLLOW_15); 

            			newLeafNode(otherlv_2, grammarAccess.getEnemyLimitAccess().getMaxKeyword_2());
            		
            // InternalMazeDsl.g:460:3: ( (lv_maxCount_3_0= ruleSIGNED_INT ) )
            // InternalMazeDsl.g:461:4: (lv_maxCount_3_0= ruleSIGNED_INT )
            {
            // InternalMazeDsl.g:461:4: (lv_maxCount_3_0= ruleSIGNED_INT )
            // InternalMazeDsl.g:462:5: lv_maxCount_3_0= ruleSIGNED_INT
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
    // InternalMazeDsl.g:483:1: entryRuleOpponentConfig returns [EObject current=null] : iv_ruleOpponentConfig= ruleOpponentConfig EOF ;
    public final EObject entryRuleOpponentConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOpponentConfig = null;


        try {
            // InternalMazeDsl.g:483:55: (iv_ruleOpponentConfig= ruleOpponentConfig EOF )
            // InternalMazeDsl.g:484:2: iv_ruleOpponentConfig= ruleOpponentConfig EOF
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
    // InternalMazeDsl.g:490:1: ruleOpponentConfig returns [EObject current=null] : (otherlv_0= 'opponent' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'type' ( (lv_type_4_0= ruleCharacterTypeEnum ) ) (otherlv_5= 'displayName' ( (lv_displayName_6_0= RULE_STRING ) ) )? (otherlv_7= 'health' ( (lv_health_8_0= ruleSIGNED_INT ) ) )? (otherlv_9= 'speed' ( (lv_speed_10_0= ruleDOUBLE ) ) )? (otherlv_11= 'threatLevel' ( (lv_threatLevel_12_0= ruleDOUBLE ) ) )? (otherlv_13= 'enabled' ( (lv_enabled_14_0= ruleBOOLEAN ) ) )? (otherlv_15= 'behavior' ( (lv_behavior_16_0= ruleBehaviorTypeEnum ) ) )? ( (lv_characterSpecifics_17_0= ruleCharacterSpecifics ) )? (otherlv_18= 'patrol' ( (otherlv_19= RULE_ID ) ) )? (otherlv_20= 'loot' ( (otherlv_21= RULE_ID ) ) )? otherlv_22= '}' ) ;
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
            // InternalMazeDsl.g:496:2: ( (otherlv_0= 'opponent' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'type' ( (lv_type_4_0= ruleCharacterTypeEnum ) ) (otherlv_5= 'displayName' ( (lv_displayName_6_0= RULE_STRING ) ) )? (otherlv_7= 'health' ( (lv_health_8_0= ruleSIGNED_INT ) ) )? (otherlv_9= 'speed' ( (lv_speed_10_0= ruleDOUBLE ) ) )? (otherlv_11= 'threatLevel' ( (lv_threatLevel_12_0= ruleDOUBLE ) ) )? (otherlv_13= 'enabled' ( (lv_enabled_14_0= ruleBOOLEAN ) ) )? (otherlv_15= 'behavior' ( (lv_behavior_16_0= ruleBehaviorTypeEnum ) ) )? ( (lv_characterSpecifics_17_0= ruleCharacterSpecifics ) )? (otherlv_18= 'patrol' ( (otherlv_19= RULE_ID ) ) )? (otherlv_20= 'loot' ( (otherlv_21= RULE_ID ) ) )? otherlv_22= '}' ) )
            // InternalMazeDsl.g:497:2: (otherlv_0= 'opponent' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'type' ( (lv_type_4_0= ruleCharacterTypeEnum ) ) (otherlv_5= 'displayName' ( (lv_displayName_6_0= RULE_STRING ) ) )? (otherlv_7= 'health' ( (lv_health_8_0= ruleSIGNED_INT ) ) )? (otherlv_9= 'speed' ( (lv_speed_10_0= ruleDOUBLE ) ) )? (otherlv_11= 'threatLevel' ( (lv_threatLevel_12_0= ruleDOUBLE ) ) )? (otherlv_13= 'enabled' ( (lv_enabled_14_0= ruleBOOLEAN ) ) )? (otherlv_15= 'behavior' ( (lv_behavior_16_0= ruleBehaviorTypeEnum ) ) )? ( (lv_characterSpecifics_17_0= ruleCharacterSpecifics ) )? (otherlv_18= 'patrol' ( (otherlv_19= RULE_ID ) ) )? (otherlv_20= 'loot' ( (otherlv_21= RULE_ID ) ) )? otherlv_22= '}' )
            {
            // InternalMazeDsl.g:497:2: (otherlv_0= 'opponent' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'type' ( (lv_type_4_0= ruleCharacterTypeEnum ) ) (otherlv_5= 'displayName' ( (lv_displayName_6_0= RULE_STRING ) ) )? (otherlv_7= 'health' ( (lv_health_8_0= ruleSIGNED_INT ) ) )? (otherlv_9= 'speed' ( (lv_speed_10_0= ruleDOUBLE ) ) )? (otherlv_11= 'threatLevel' ( (lv_threatLevel_12_0= ruleDOUBLE ) ) )? (otherlv_13= 'enabled' ( (lv_enabled_14_0= ruleBOOLEAN ) ) )? (otherlv_15= 'behavior' ( (lv_behavior_16_0= ruleBehaviorTypeEnum ) ) )? ( (lv_characterSpecifics_17_0= ruleCharacterSpecifics ) )? (otherlv_18= 'patrol' ( (otherlv_19= RULE_ID ) ) )? (otherlv_20= 'loot' ( (otherlv_21= RULE_ID ) ) )? otherlv_22= '}' )
            // InternalMazeDsl.g:498:3: otherlv_0= 'opponent' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'type' ( (lv_type_4_0= ruleCharacterTypeEnum ) ) (otherlv_5= 'displayName' ( (lv_displayName_6_0= RULE_STRING ) ) )? (otherlv_7= 'health' ( (lv_health_8_0= ruleSIGNED_INT ) ) )? (otherlv_9= 'speed' ( (lv_speed_10_0= ruleDOUBLE ) ) )? (otherlv_11= 'threatLevel' ( (lv_threatLevel_12_0= ruleDOUBLE ) ) )? (otherlv_13= 'enabled' ( (lv_enabled_14_0= ruleBOOLEAN ) ) )? (otherlv_15= 'behavior' ( (lv_behavior_16_0= ruleBehaviorTypeEnum ) ) )? ( (lv_characterSpecifics_17_0= ruleCharacterSpecifics ) )? (otherlv_18= 'patrol' ( (otherlv_19= RULE_ID ) ) )? (otherlv_20= 'loot' ( (otherlv_21= RULE_ID ) ) )? otherlv_22= '}'
            {
            otherlv_0=(Token)match(input,24,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getOpponentConfigAccess().getOpponentKeyword_0());
            		
            // InternalMazeDsl.g:502:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalMazeDsl.g:503:4: (lv_name_1_0= RULE_ID )
            {
            // InternalMazeDsl.g:503:4: (lv_name_1_0= RULE_ID )
            // InternalMazeDsl.g:504:5: lv_name_1_0= RULE_ID
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

            otherlv_2=(Token)match(input,12,FOLLOW_21); 

            			newLeafNode(otherlv_2, grammarAccess.getOpponentConfigAccess().getLeftCurlyBracketKeyword_2());
            		
            otherlv_3=(Token)match(input,25,FOLLOW_19); 

            			newLeafNode(otherlv_3, grammarAccess.getOpponentConfigAccess().getTypeKeyword_3());
            		
            // InternalMazeDsl.g:528:3: ( (lv_type_4_0= ruleCharacterTypeEnum ) )
            // InternalMazeDsl.g:529:4: (lv_type_4_0= ruleCharacterTypeEnum )
            {
            // InternalMazeDsl.g:529:4: (lv_type_4_0= ruleCharacterTypeEnum )
            // InternalMazeDsl.g:530:5: lv_type_4_0= ruleCharacterTypeEnum
            {

            					newCompositeNode(grammarAccess.getOpponentConfigAccess().getTypeCharacterTypeEnumEnumRuleCall_4_0());
            				
            pushFollow(FOLLOW_22);
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

            // InternalMazeDsl.g:547:3: (otherlv_5= 'displayName' ( (lv_displayName_6_0= RULE_STRING ) ) )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==26) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // InternalMazeDsl.g:548:4: otherlv_5= 'displayName' ( (lv_displayName_6_0= RULE_STRING ) )
                    {
                    otherlv_5=(Token)match(input,26,FOLLOW_9); 

                    				newLeafNode(otherlv_5, grammarAccess.getOpponentConfigAccess().getDisplayNameKeyword_5_0());
                    			
                    // InternalMazeDsl.g:552:4: ( (lv_displayName_6_0= RULE_STRING ) )
                    // InternalMazeDsl.g:553:5: (lv_displayName_6_0= RULE_STRING )
                    {
                    // InternalMazeDsl.g:553:5: (lv_displayName_6_0= RULE_STRING )
                    // InternalMazeDsl.g:554:6: lv_displayName_6_0= RULE_STRING
                    {
                    lv_displayName_6_0=(Token)match(input,RULE_STRING,FOLLOW_23); 

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

            // InternalMazeDsl.g:571:3: (otherlv_7= 'health' ( (lv_health_8_0= ruleSIGNED_INT ) ) )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==27) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // InternalMazeDsl.g:572:4: otherlv_7= 'health' ( (lv_health_8_0= ruleSIGNED_INT ) )
                    {
                    otherlv_7=(Token)match(input,27,FOLLOW_15); 

                    				newLeafNode(otherlv_7, grammarAccess.getOpponentConfigAccess().getHealthKeyword_6_0());
                    			
                    // InternalMazeDsl.g:576:4: ( (lv_health_8_0= ruleSIGNED_INT ) )
                    // InternalMazeDsl.g:577:5: (lv_health_8_0= ruleSIGNED_INT )
                    {
                    // InternalMazeDsl.g:577:5: (lv_health_8_0= ruleSIGNED_INT )
                    // InternalMazeDsl.g:578:6: lv_health_8_0= ruleSIGNED_INT
                    {

                    						newCompositeNode(grammarAccess.getOpponentConfigAccess().getHealthSIGNED_INTParserRuleCall_6_1_0());
                    					
                    pushFollow(FOLLOW_24);
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

            // InternalMazeDsl.g:596:3: (otherlv_9= 'speed' ( (lv_speed_10_0= ruleDOUBLE ) ) )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==28) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // InternalMazeDsl.g:597:4: otherlv_9= 'speed' ( (lv_speed_10_0= ruleDOUBLE ) )
                    {
                    otherlv_9=(Token)match(input,28,FOLLOW_15); 

                    				newLeafNode(otherlv_9, grammarAccess.getOpponentConfigAccess().getSpeedKeyword_7_0());
                    			
                    // InternalMazeDsl.g:601:4: ( (lv_speed_10_0= ruleDOUBLE ) )
                    // InternalMazeDsl.g:602:5: (lv_speed_10_0= ruleDOUBLE )
                    {
                    // InternalMazeDsl.g:602:5: (lv_speed_10_0= ruleDOUBLE )
                    // InternalMazeDsl.g:603:6: lv_speed_10_0= ruleDOUBLE
                    {

                    						newCompositeNode(grammarAccess.getOpponentConfigAccess().getSpeedDOUBLEParserRuleCall_7_1_0());
                    					
                    pushFollow(FOLLOW_25);
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

            // InternalMazeDsl.g:621:3: (otherlv_11= 'threatLevel' ( (lv_threatLevel_12_0= ruleDOUBLE ) ) )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==29) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // InternalMazeDsl.g:622:4: otherlv_11= 'threatLevel' ( (lv_threatLevel_12_0= ruleDOUBLE ) )
                    {
                    otherlv_11=(Token)match(input,29,FOLLOW_15); 

                    				newLeafNode(otherlv_11, grammarAccess.getOpponentConfigAccess().getThreatLevelKeyword_8_0());
                    			
                    // InternalMazeDsl.g:626:4: ( (lv_threatLevel_12_0= ruleDOUBLE ) )
                    // InternalMazeDsl.g:627:5: (lv_threatLevel_12_0= ruleDOUBLE )
                    {
                    // InternalMazeDsl.g:627:5: (lv_threatLevel_12_0= ruleDOUBLE )
                    // InternalMazeDsl.g:628:6: lv_threatLevel_12_0= ruleDOUBLE
                    {

                    						newCompositeNode(grammarAccess.getOpponentConfigAccess().getThreatLevelDOUBLEParserRuleCall_8_1_0());
                    					
                    pushFollow(FOLLOW_26);
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

            // InternalMazeDsl.g:646:3: (otherlv_13= 'enabled' ( (lv_enabled_14_0= ruleBOOLEAN ) ) )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==30) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // InternalMazeDsl.g:647:4: otherlv_13= 'enabled' ( (lv_enabled_14_0= ruleBOOLEAN ) )
                    {
                    otherlv_13=(Token)match(input,30,FOLLOW_27); 

                    				newLeafNode(otherlv_13, grammarAccess.getOpponentConfigAccess().getEnabledKeyword_9_0());
                    			
                    // InternalMazeDsl.g:651:4: ( (lv_enabled_14_0= ruleBOOLEAN ) )
                    // InternalMazeDsl.g:652:5: (lv_enabled_14_0= ruleBOOLEAN )
                    {
                    // InternalMazeDsl.g:652:5: (lv_enabled_14_0= ruleBOOLEAN )
                    // InternalMazeDsl.g:653:6: lv_enabled_14_0= ruleBOOLEAN
                    {

                    						newCompositeNode(grammarAccess.getOpponentConfigAccess().getEnabledBOOLEANParserRuleCall_9_1_0());
                    					
                    pushFollow(FOLLOW_28);
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

            // InternalMazeDsl.g:671:3: (otherlv_15= 'behavior' ( (lv_behavior_16_0= ruleBehaviorTypeEnum ) ) )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==31) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // InternalMazeDsl.g:672:4: otherlv_15= 'behavior' ( (lv_behavior_16_0= ruleBehaviorTypeEnum ) )
                    {
                    otherlv_15=(Token)match(input,31,FOLLOW_29); 

                    				newLeafNode(otherlv_15, grammarAccess.getOpponentConfigAccess().getBehaviorKeyword_10_0());
                    			
                    // InternalMazeDsl.g:676:4: ( (lv_behavior_16_0= ruleBehaviorTypeEnum ) )
                    // InternalMazeDsl.g:677:5: (lv_behavior_16_0= ruleBehaviorTypeEnum )
                    {
                    // InternalMazeDsl.g:677:5: (lv_behavior_16_0= ruleBehaviorTypeEnum )
                    // InternalMazeDsl.g:678:6: lv_behavior_16_0= ruleBehaviorTypeEnum
                    {

                    						newCompositeNode(grammarAccess.getOpponentConfigAccess().getBehaviorBehaviorTypeEnumEnumRuleCall_10_1_0());
                    					
                    pushFollow(FOLLOW_30);
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

            // InternalMazeDsl.g:696:3: ( (lv_characterSpecifics_17_0= ruleCharacterSpecifics ) )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==34||LA17_0==38||LA17_0==41) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // InternalMazeDsl.g:697:4: (lv_characterSpecifics_17_0= ruleCharacterSpecifics )
                    {
                    // InternalMazeDsl.g:697:4: (lv_characterSpecifics_17_0= ruleCharacterSpecifics )
                    // InternalMazeDsl.g:698:5: lv_characterSpecifics_17_0= ruleCharacterSpecifics
                    {

                    					newCompositeNode(grammarAccess.getOpponentConfigAccess().getCharacterSpecificsCharacterSpecificsParserRuleCall_11_0());
                    				
                    pushFollow(FOLLOW_31);
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

            // InternalMazeDsl.g:715:3: (otherlv_18= 'patrol' ( (otherlv_19= RULE_ID ) ) )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==32) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // InternalMazeDsl.g:716:4: otherlv_18= 'patrol' ( (otherlv_19= RULE_ID ) )
                    {
                    otherlv_18=(Token)match(input,32,FOLLOW_3); 

                    				newLeafNode(otherlv_18, grammarAccess.getOpponentConfigAccess().getPatrolKeyword_12_0());
                    			
                    // InternalMazeDsl.g:720:4: ( (otherlv_19= RULE_ID ) )
                    // InternalMazeDsl.g:721:5: (otherlv_19= RULE_ID )
                    {
                    // InternalMazeDsl.g:721:5: (otherlv_19= RULE_ID )
                    // InternalMazeDsl.g:722:6: otherlv_19= RULE_ID
                    {

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getOpponentConfigRule());
                    						}
                    					
                    otherlv_19=(Token)match(input,RULE_ID,FOLLOW_32); 

                    						newLeafNode(otherlv_19, grammarAccess.getOpponentConfigAccess().getPatrolRefPatrolConfigCrossReference_12_1_0());
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeDsl.g:734:3: (otherlv_20= 'loot' ( (otherlv_21= RULE_ID ) ) )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==33) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // InternalMazeDsl.g:735:4: otherlv_20= 'loot' ( (otherlv_21= RULE_ID ) )
                    {
                    otherlv_20=(Token)match(input,33,FOLLOW_3); 

                    				newLeafNode(otherlv_20, grammarAccess.getOpponentConfigAccess().getLootKeyword_13_0());
                    			
                    // InternalMazeDsl.g:739:4: ( (otherlv_21= RULE_ID ) )
                    // InternalMazeDsl.g:740:5: (otherlv_21= RULE_ID )
                    {
                    // InternalMazeDsl.g:740:5: (otherlv_21= RULE_ID )
                    // InternalMazeDsl.g:741:6: otherlv_21= RULE_ID
                    {

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getOpponentConfigRule());
                    						}
                    					
                    otherlv_21=(Token)match(input,RULE_ID,FOLLOW_33); 

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
    // InternalMazeDsl.g:761:1: entryRuleCharacterSpecifics returns [EObject current=null] : iv_ruleCharacterSpecifics= ruleCharacterSpecifics EOF ;
    public final EObject entryRuleCharacterSpecifics() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCharacterSpecifics = null;


        try {
            // InternalMazeDsl.g:761:59: (iv_ruleCharacterSpecifics= ruleCharacterSpecifics EOF )
            // InternalMazeDsl.g:762:2: iv_ruleCharacterSpecifics= ruleCharacterSpecifics EOF
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
    // InternalMazeDsl.g:768:1: ruleCharacterSpecifics returns [EObject current=null] : (this_ZombieSpecifics_0= ruleZombieSpecifics | this_GhostSpecifics_1= ruleGhostSpecifics | this_RangedSpecifics_2= ruleRangedSpecifics ) ;
    public final EObject ruleCharacterSpecifics() throws RecognitionException {
        EObject current = null;

        EObject this_ZombieSpecifics_0 = null;

        EObject this_GhostSpecifics_1 = null;

        EObject this_RangedSpecifics_2 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:774:2: ( (this_ZombieSpecifics_0= ruleZombieSpecifics | this_GhostSpecifics_1= ruleGhostSpecifics | this_RangedSpecifics_2= ruleRangedSpecifics ) )
            // InternalMazeDsl.g:775:2: (this_ZombieSpecifics_0= ruleZombieSpecifics | this_GhostSpecifics_1= ruleGhostSpecifics | this_RangedSpecifics_2= ruleRangedSpecifics )
            {
            // InternalMazeDsl.g:775:2: (this_ZombieSpecifics_0= ruleZombieSpecifics | this_GhostSpecifics_1= ruleGhostSpecifics | this_RangedSpecifics_2= ruleRangedSpecifics )
            int alt20=3;
            switch ( input.LA(1) ) {
            case 34:
                {
                alt20=1;
                }
                break;
            case 38:
                {
                alt20=2;
                }
                break;
            case 41:
                {
                alt20=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 20, 0, input);

                throw nvae;
            }

            switch (alt20) {
                case 1 :
                    // InternalMazeDsl.g:776:3: this_ZombieSpecifics_0= ruleZombieSpecifics
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
                    // InternalMazeDsl.g:785:3: this_GhostSpecifics_1= ruleGhostSpecifics
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
                    // InternalMazeDsl.g:794:3: this_RangedSpecifics_2= ruleRangedSpecifics
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
    // InternalMazeDsl.g:806:1: entryRuleZombieSpecifics returns [EObject current=null] : iv_ruleZombieSpecifics= ruleZombieSpecifics EOF ;
    public final EObject entryRuleZombieSpecifics() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleZombieSpecifics = null;


        try {
            // InternalMazeDsl.g:806:56: (iv_ruleZombieSpecifics= ruleZombieSpecifics EOF )
            // InternalMazeDsl.g:807:2: iv_ruleZombieSpecifics= ruleZombieSpecifics EOF
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
    // InternalMazeDsl.g:813:1: ruleZombieSpecifics returns [EObject current=null] : ( () otherlv_1= 'zombie-stats' otherlv_2= '{' (otherlv_3= 'attackDamage' ( (lv_attackDamage_4_0= RULE_INT ) ) )? (otherlv_5= 'infectionLevel' ( (lv_infectionLevel_6_0= RULE_INT ) ) )? (otherlv_7= 'resurrectionTime' ( (lv_resurrectionTime_8_0= RULE_INT ) ) )? otherlv_9= '}' ) ;
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
            // InternalMazeDsl.g:819:2: ( ( () otherlv_1= 'zombie-stats' otherlv_2= '{' (otherlv_3= 'attackDamage' ( (lv_attackDamage_4_0= RULE_INT ) ) )? (otherlv_5= 'infectionLevel' ( (lv_infectionLevel_6_0= RULE_INT ) ) )? (otherlv_7= 'resurrectionTime' ( (lv_resurrectionTime_8_0= RULE_INT ) ) )? otherlv_9= '}' ) )
            // InternalMazeDsl.g:820:2: ( () otherlv_1= 'zombie-stats' otherlv_2= '{' (otherlv_3= 'attackDamage' ( (lv_attackDamage_4_0= RULE_INT ) ) )? (otherlv_5= 'infectionLevel' ( (lv_infectionLevel_6_0= RULE_INT ) ) )? (otherlv_7= 'resurrectionTime' ( (lv_resurrectionTime_8_0= RULE_INT ) ) )? otherlv_9= '}' )
            {
            // InternalMazeDsl.g:820:2: ( () otherlv_1= 'zombie-stats' otherlv_2= '{' (otherlv_3= 'attackDamage' ( (lv_attackDamage_4_0= RULE_INT ) ) )? (otherlv_5= 'infectionLevel' ( (lv_infectionLevel_6_0= RULE_INT ) ) )? (otherlv_7= 'resurrectionTime' ( (lv_resurrectionTime_8_0= RULE_INT ) ) )? otherlv_9= '}' )
            // InternalMazeDsl.g:821:3: () otherlv_1= 'zombie-stats' otherlv_2= '{' (otherlv_3= 'attackDamage' ( (lv_attackDamage_4_0= RULE_INT ) ) )? (otherlv_5= 'infectionLevel' ( (lv_infectionLevel_6_0= RULE_INT ) ) )? (otherlv_7= 'resurrectionTime' ( (lv_resurrectionTime_8_0= RULE_INT ) ) )? otherlv_9= '}'
            {
            // InternalMazeDsl.g:821:3: ()
            // InternalMazeDsl.g:822:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getZombieSpecificsAccess().getZombieSpecificsAction_0(),
            					current);
            			

            }

            otherlv_1=(Token)match(input,34,FOLLOW_4); 

            			newLeafNode(otherlv_1, grammarAccess.getZombieSpecificsAccess().getZombieStatsKeyword_1());
            		
            otherlv_2=(Token)match(input,12,FOLLOW_34); 

            			newLeafNode(otherlv_2, grammarAccess.getZombieSpecificsAccess().getLeftCurlyBracketKeyword_2());
            		
            // InternalMazeDsl.g:836:3: (otherlv_3= 'attackDamage' ( (lv_attackDamage_4_0= RULE_INT ) ) )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==35) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // InternalMazeDsl.g:837:4: otherlv_3= 'attackDamage' ( (lv_attackDamage_4_0= RULE_INT ) )
                    {
                    otherlv_3=(Token)match(input,35,FOLLOW_35); 

                    				newLeafNode(otherlv_3, grammarAccess.getZombieSpecificsAccess().getAttackDamageKeyword_3_0());
                    			
                    // InternalMazeDsl.g:841:4: ( (lv_attackDamage_4_0= RULE_INT ) )
                    // InternalMazeDsl.g:842:5: (lv_attackDamage_4_0= RULE_INT )
                    {
                    // InternalMazeDsl.g:842:5: (lv_attackDamage_4_0= RULE_INT )
                    // InternalMazeDsl.g:843:6: lv_attackDamage_4_0= RULE_INT
                    {
                    lv_attackDamage_4_0=(Token)match(input,RULE_INT,FOLLOW_36); 

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

            // InternalMazeDsl.g:860:3: (otherlv_5= 'infectionLevel' ( (lv_infectionLevel_6_0= RULE_INT ) ) )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==36) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // InternalMazeDsl.g:861:4: otherlv_5= 'infectionLevel' ( (lv_infectionLevel_6_0= RULE_INT ) )
                    {
                    otherlv_5=(Token)match(input,36,FOLLOW_35); 

                    				newLeafNode(otherlv_5, grammarAccess.getZombieSpecificsAccess().getInfectionLevelKeyword_4_0());
                    			
                    // InternalMazeDsl.g:865:4: ( (lv_infectionLevel_6_0= RULE_INT ) )
                    // InternalMazeDsl.g:866:5: (lv_infectionLevel_6_0= RULE_INT )
                    {
                    // InternalMazeDsl.g:866:5: (lv_infectionLevel_6_0= RULE_INT )
                    // InternalMazeDsl.g:867:6: lv_infectionLevel_6_0= RULE_INT
                    {
                    lv_infectionLevel_6_0=(Token)match(input,RULE_INT,FOLLOW_37); 

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

            // InternalMazeDsl.g:884:3: (otherlv_7= 'resurrectionTime' ( (lv_resurrectionTime_8_0= RULE_INT ) ) )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==37) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // InternalMazeDsl.g:885:4: otherlv_7= 'resurrectionTime' ( (lv_resurrectionTime_8_0= RULE_INT ) )
                    {
                    otherlv_7=(Token)match(input,37,FOLLOW_35); 

                    				newLeafNode(otherlv_7, grammarAccess.getZombieSpecificsAccess().getResurrectionTimeKeyword_5_0());
                    			
                    // InternalMazeDsl.g:889:4: ( (lv_resurrectionTime_8_0= RULE_INT ) )
                    // InternalMazeDsl.g:890:5: (lv_resurrectionTime_8_0= RULE_INT )
                    {
                    // InternalMazeDsl.g:890:5: (lv_resurrectionTime_8_0= RULE_INT )
                    // InternalMazeDsl.g:891:6: lv_resurrectionTime_8_0= RULE_INT
                    {
                    lv_resurrectionTime_8_0=(Token)match(input,RULE_INT,FOLLOW_33); 

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
    // InternalMazeDsl.g:916:1: entryRuleGhostSpecifics returns [EObject current=null] : iv_ruleGhostSpecifics= ruleGhostSpecifics EOF ;
    public final EObject entryRuleGhostSpecifics() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGhostSpecifics = null;


        try {
            // InternalMazeDsl.g:916:55: (iv_ruleGhostSpecifics= ruleGhostSpecifics EOF )
            // InternalMazeDsl.g:917:2: iv_ruleGhostSpecifics= ruleGhostSpecifics EOF
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
    // InternalMazeDsl.g:923:1: ruleGhostSpecifics returns [EObject current=null] : ( () otherlv_1= 'ghost-stats' otherlv_2= '{' (otherlv_3= 'attackDamage' ( (lv_attackDamage_4_0= RULE_INT ) ) )? (otherlv_5= 'visibilityLevel' ( (lv_visibilityLevel_6_0= RULE_INT ) ) )? (otherlv_7= 'nonTangibilityEnergy' ( (lv_nonTangibilityEnergy_8_0= ruleDOUBLE ) ) )? otherlv_9= '}' ) ;
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
            // InternalMazeDsl.g:929:2: ( ( () otherlv_1= 'ghost-stats' otherlv_2= '{' (otherlv_3= 'attackDamage' ( (lv_attackDamage_4_0= RULE_INT ) ) )? (otherlv_5= 'visibilityLevel' ( (lv_visibilityLevel_6_0= RULE_INT ) ) )? (otherlv_7= 'nonTangibilityEnergy' ( (lv_nonTangibilityEnergy_8_0= ruleDOUBLE ) ) )? otherlv_9= '}' ) )
            // InternalMazeDsl.g:930:2: ( () otherlv_1= 'ghost-stats' otherlv_2= '{' (otherlv_3= 'attackDamage' ( (lv_attackDamage_4_0= RULE_INT ) ) )? (otherlv_5= 'visibilityLevel' ( (lv_visibilityLevel_6_0= RULE_INT ) ) )? (otherlv_7= 'nonTangibilityEnergy' ( (lv_nonTangibilityEnergy_8_0= ruleDOUBLE ) ) )? otherlv_9= '}' )
            {
            // InternalMazeDsl.g:930:2: ( () otherlv_1= 'ghost-stats' otherlv_2= '{' (otherlv_3= 'attackDamage' ( (lv_attackDamage_4_0= RULE_INT ) ) )? (otherlv_5= 'visibilityLevel' ( (lv_visibilityLevel_6_0= RULE_INT ) ) )? (otherlv_7= 'nonTangibilityEnergy' ( (lv_nonTangibilityEnergy_8_0= ruleDOUBLE ) ) )? otherlv_9= '}' )
            // InternalMazeDsl.g:931:3: () otherlv_1= 'ghost-stats' otherlv_2= '{' (otherlv_3= 'attackDamage' ( (lv_attackDamage_4_0= RULE_INT ) ) )? (otherlv_5= 'visibilityLevel' ( (lv_visibilityLevel_6_0= RULE_INT ) ) )? (otherlv_7= 'nonTangibilityEnergy' ( (lv_nonTangibilityEnergy_8_0= ruleDOUBLE ) ) )? otherlv_9= '}'
            {
            // InternalMazeDsl.g:931:3: ()
            // InternalMazeDsl.g:932:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getGhostSpecificsAccess().getGhostSpecificsAction_0(),
            					current);
            			

            }

            otherlv_1=(Token)match(input,38,FOLLOW_4); 

            			newLeafNode(otherlv_1, grammarAccess.getGhostSpecificsAccess().getGhostStatsKeyword_1());
            		
            otherlv_2=(Token)match(input,12,FOLLOW_38); 

            			newLeafNode(otherlv_2, grammarAccess.getGhostSpecificsAccess().getLeftCurlyBracketKeyword_2());
            		
            // InternalMazeDsl.g:946:3: (otherlv_3= 'attackDamage' ( (lv_attackDamage_4_0= RULE_INT ) ) )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==35) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // InternalMazeDsl.g:947:4: otherlv_3= 'attackDamage' ( (lv_attackDamage_4_0= RULE_INT ) )
                    {
                    otherlv_3=(Token)match(input,35,FOLLOW_35); 

                    				newLeafNode(otherlv_3, grammarAccess.getGhostSpecificsAccess().getAttackDamageKeyword_3_0());
                    			
                    // InternalMazeDsl.g:951:4: ( (lv_attackDamage_4_0= RULE_INT ) )
                    // InternalMazeDsl.g:952:5: (lv_attackDamage_4_0= RULE_INT )
                    {
                    // InternalMazeDsl.g:952:5: (lv_attackDamage_4_0= RULE_INT )
                    // InternalMazeDsl.g:953:6: lv_attackDamage_4_0= RULE_INT
                    {
                    lv_attackDamage_4_0=(Token)match(input,RULE_INT,FOLLOW_39); 

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

            // InternalMazeDsl.g:970:3: (otherlv_5= 'visibilityLevel' ( (lv_visibilityLevel_6_0= RULE_INT ) ) )?
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==39) ) {
                alt25=1;
            }
            switch (alt25) {
                case 1 :
                    // InternalMazeDsl.g:971:4: otherlv_5= 'visibilityLevel' ( (lv_visibilityLevel_6_0= RULE_INT ) )
                    {
                    otherlv_5=(Token)match(input,39,FOLLOW_35); 

                    				newLeafNode(otherlv_5, grammarAccess.getGhostSpecificsAccess().getVisibilityLevelKeyword_4_0());
                    			
                    // InternalMazeDsl.g:975:4: ( (lv_visibilityLevel_6_0= RULE_INT ) )
                    // InternalMazeDsl.g:976:5: (lv_visibilityLevel_6_0= RULE_INT )
                    {
                    // InternalMazeDsl.g:976:5: (lv_visibilityLevel_6_0= RULE_INT )
                    // InternalMazeDsl.g:977:6: lv_visibilityLevel_6_0= RULE_INT
                    {
                    lv_visibilityLevel_6_0=(Token)match(input,RULE_INT,FOLLOW_40); 

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

            // InternalMazeDsl.g:994:3: (otherlv_7= 'nonTangibilityEnergy' ( (lv_nonTangibilityEnergy_8_0= ruleDOUBLE ) ) )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==40) ) {
                alt26=1;
            }
            switch (alt26) {
                case 1 :
                    // InternalMazeDsl.g:995:4: otherlv_7= 'nonTangibilityEnergy' ( (lv_nonTangibilityEnergy_8_0= ruleDOUBLE ) )
                    {
                    otherlv_7=(Token)match(input,40,FOLLOW_15); 

                    				newLeafNode(otherlv_7, grammarAccess.getGhostSpecificsAccess().getNonTangibilityEnergyKeyword_5_0());
                    			
                    // InternalMazeDsl.g:999:4: ( (lv_nonTangibilityEnergy_8_0= ruleDOUBLE ) )
                    // InternalMazeDsl.g:1000:5: (lv_nonTangibilityEnergy_8_0= ruleDOUBLE )
                    {
                    // InternalMazeDsl.g:1000:5: (lv_nonTangibilityEnergy_8_0= ruleDOUBLE )
                    // InternalMazeDsl.g:1001:6: lv_nonTangibilityEnergy_8_0= ruleDOUBLE
                    {

                    						newCompositeNode(grammarAccess.getGhostSpecificsAccess().getNonTangibilityEnergyDOUBLEParserRuleCall_5_1_0());
                    					
                    pushFollow(FOLLOW_33);
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
    // InternalMazeDsl.g:1027:1: entryRuleRangedSpecifics returns [EObject current=null] : iv_ruleRangedSpecifics= ruleRangedSpecifics EOF ;
    public final EObject entryRuleRangedSpecifics() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRangedSpecifics = null;


        try {
            // InternalMazeDsl.g:1027:56: (iv_ruleRangedSpecifics= ruleRangedSpecifics EOF )
            // InternalMazeDsl.g:1028:2: iv_ruleRangedSpecifics= ruleRangedSpecifics EOF
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
    // InternalMazeDsl.g:1034:1: ruleRangedSpecifics returns [EObject current=null] : ( () otherlv_1= 'ranged-stats' otherlv_2= '{' (otherlv_3= 'attackRange' ( (lv_attackRange_4_0= ruleDOUBLE ) ) )? (otherlv_5= 'attackCooldown' ( (lv_attackCooldown_6_0= RULE_INT ) ) )? (otherlv_7= 'attackDamage' ( (lv_attackDamage_8_0= RULE_INT ) ) )? (otherlv_9= 'projectileSpeed' ( (lv_projectileSpeed_10_0= ruleDOUBLE ) ) )? (otherlv_11= 'projectileType' ( (lv_projectileType_12_0= ruleProjectileTypeEnum ) ) )? (otherlv_13= 'splashRadius' ( (lv_splashRadius_14_0= ruleDOUBLE ) ) )? otherlv_15= '}' ) ;
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
            // InternalMazeDsl.g:1040:2: ( ( () otherlv_1= 'ranged-stats' otherlv_2= '{' (otherlv_3= 'attackRange' ( (lv_attackRange_4_0= ruleDOUBLE ) ) )? (otherlv_5= 'attackCooldown' ( (lv_attackCooldown_6_0= RULE_INT ) ) )? (otherlv_7= 'attackDamage' ( (lv_attackDamage_8_0= RULE_INT ) ) )? (otherlv_9= 'projectileSpeed' ( (lv_projectileSpeed_10_0= ruleDOUBLE ) ) )? (otherlv_11= 'projectileType' ( (lv_projectileType_12_0= ruleProjectileTypeEnum ) ) )? (otherlv_13= 'splashRadius' ( (lv_splashRadius_14_0= ruleDOUBLE ) ) )? otherlv_15= '}' ) )
            // InternalMazeDsl.g:1041:2: ( () otherlv_1= 'ranged-stats' otherlv_2= '{' (otherlv_3= 'attackRange' ( (lv_attackRange_4_0= ruleDOUBLE ) ) )? (otherlv_5= 'attackCooldown' ( (lv_attackCooldown_6_0= RULE_INT ) ) )? (otherlv_7= 'attackDamage' ( (lv_attackDamage_8_0= RULE_INT ) ) )? (otherlv_9= 'projectileSpeed' ( (lv_projectileSpeed_10_0= ruleDOUBLE ) ) )? (otherlv_11= 'projectileType' ( (lv_projectileType_12_0= ruleProjectileTypeEnum ) ) )? (otherlv_13= 'splashRadius' ( (lv_splashRadius_14_0= ruleDOUBLE ) ) )? otherlv_15= '}' )
            {
            // InternalMazeDsl.g:1041:2: ( () otherlv_1= 'ranged-stats' otherlv_2= '{' (otherlv_3= 'attackRange' ( (lv_attackRange_4_0= ruleDOUBLE ) ) )? (otherlv_5= 'attackCooldown' ( (lv_attackCooldown_6_0= RULE_INT ) ) )? (otherlv_7= 'attackDamage' ( (lv_attackDamage_8_0= RULE_INT ) ) )? (otherlv_9= 'projectileSpeed' ( (lv_projectileSpeed_10_0= ruleDOUBLE ) ) )? (otherlv_11= 'projectileType' ( (lv_projectileType_12_0= ruleProjectileTypeEnum ) ) )? (otherlv_13= 'splashRadius' ( (lv_splashRadius_14_0= ruleDOUBLE ) ) )? otherlv_15= '}' )
            // InternalMazeDsl.g:1042:3: () otherlv_1= 'ranged-stats' otherlv_2= '{' (otherlv_3= 'attackRange' ( (lv_attackRange_4_0= ruleDOUBLE ) ) )? (otherlv_5= 'attackCooldown' ( (lv_attackCooldown_6_0= RULE_INT ) ) )? (otherlv_7= 'attackDamage' ( (lv_attackDamage_8_0= RULE_INT ) ) )? (otherlv_9= 'projectileSpeed' ( (lv_projectileSpeed_10_0= ruleDOUBLE ) ) )? (otherlv_11= 'projectileType' ( (lv_projectileType_12_0= ruleProjectileTypeEnum ) ) )? (otherlv_13= 'splashRadius' ( (lv_splashRadius_14_0= ruleDOUBLE ) ) )? otherlv_15= '}'
            {
            // InternalMazeDsl.g:1042:3: ()
            // InternalMazeDsl.g:1043:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getRangedSpecificsAccess().getRangedSpecificsAction_0(),
            					current);
            			

            }

            otherlv_1=(Token)match(input,41,FOLLOW_4); 

            			newLeafNode(otherlv_1, grammarAccess.getRangedSpecificsAccess().getRangedStatsKeyword_1());
            		
            otherlv_2=(Token)match(input,12,FOLLOW_41); 

            			newLeafNode(otherlv_2, grammarAccess.getRangedSpecificsAccess().getLeftCurlyBracketKeyword_2());
            		
            // InternalMazeDsl.g:1057:3: (otherlv_3= 'attackRange' ( (lv_attackRange_4_0= ruleDOUBLE ) ) )?
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==42) ) {
                alt27=1;
            }
            switch (alt27) {
                case 1 :
                    // InternalMazeDsl.g:1058:4: otherlv_3= 'attackRange' ( (lv_attackRange_4_0= ruleDOUBLE ) )
                    {
                    otherlv_3=(Token)match(input,42,FOLLOW_15); 

                    				newLeafNode(otherlv_3, grammarAccess.getRangedSpecificsAccess().getAttackRangeKeyword_3_0());
                    			
                    // InternalMazeDsl.g:1062:4: ( (lv_attackRange_4_0= ruleDOUBLE ) )
                    // InternalMazeDsl.g:1063:5: (lv_attackRange_4_0= ruleDOUBLE )
                    {
                    // InternalMazeDsl.g:1063:5: (lv_attackRange_4_0= ruleDOUBLE )
                    // InternalMazeDsl.g:1064:6: lv_attackRange_4_0= ruleDOUBLE
                    {

                    						newCompositeNode(grammarAccess.getRangedSpecificsAccess().getAttackRangeDOUBLEParserRuleCall_3_1_0());
                    					
                    pushFollow(FOLLOW_42);
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

            // InternalMazeDsl.g:1082:3: (otherlv_5= 'attackCooldown' ( (lv_attackCooldown_6_0= RULE_INT ) ) )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==43) ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // InternalMazeDsl.g:1083:4: otherlv_5= 'attackCooldown' ( (lv_attackCooldown_6_0= RULE_INT ) )
                    {
                    otherlv_5=(Token)match(input,43,FOLLOW_35); 

                    				newLeafNode(otherlv_5, grammarAccess.getRangedSpecificsAccess().getAttackCooldownKeyword_4_0());
                    			
                    // InternalMazeDsl.g:1087:4: ( (lv_attackCooldown_6_0= RULE_INT ) )
                    // InternalMazeDsl.g:1088:5: (lv_attackCooldown_6_0= RULE_INT )
                    {
                    // InternalMazeDsl.g:1088:5: (lv_attackCooldown_6_0= RULE_INT )
                    // InternalMazeDsl.g:1089:6: lv_attackCooldown_6_0= RULE_INT
                    {
                    lv_attackCooldown_6_0=(Token)match(input,RULE_INT,FOLLOW_43); 

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

            // InternalMazeDsl.g:1106:3: (otherlv_7= 'attackDamage' ( (lv_attackDamage_8_0= RULE_INT ) ) )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==35) ) {
                alt29=1;
            }
            switch (alt29) {
                case 1 :
                    // InternalMazeDsl.g:1107:4: otherlv_7= 'attackDamage' ( (lv_attackDamage_8_0= RULE_INT ) )
                    {
                    otherlv_7=(Token)match(input,35,FOLLOW_35); 

                    				newLeafNode(otherlv_7, grammarAccess.getRangedSpecificsAccess().getAttackDamageKeyword_5_0());
                    			
                    // InternalMazeDsl.g:1111:4: ( (lv_attackDamage_8_0= RULE_INT ) )
                    // InternalMazeDsl.g:1112:5: (lv_attackDamage_8_0= RULE_INT )
                    {
                    // InternalMazeDsl.g:1112:5: (lv_attackDamage_8_0= RULE_INT )
                    // InternalMazeDsl.g:1113:6: lv_attackDamage_8_0= RULE_INT
                    {
                    lv_attackDamage_8_0=(Token)match(input,RULE_INT,FOLLOW_44); 

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

            // InternalMazeDsl.g:1130:3: (otherlv_9= 'projectileSpeed' ( (lv_projectileSpeed_10_0= ruleDOUBLE ) ) )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==44) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // InternalMazeDsl.g:1131:4: otherlv_9= 'projectileSpeed' ( (lv_projectileSpeed_10_0= ruleDOUBLE ) )
                    {
                    otherlv_9=(Token)match(input,44,FOLLOW_15); 

                    				newLeafNode(otherlv_9, grammarAccess.getRangedSpecificsAccess().getProjectileSpeedKeyword_6_0());
                    			
                    // InternalMazeDsl.g:1135:4: ( (lv_projectileSpeed_10_0= ruleDOUBLE ) )
                    // InternalMazeDsl.g:1136:5: (lv_projectileSpeed_10_0= ruleDOUBLE )
                    {
                    // InternalMazeDsl.g:1136:5: (lv_projectileSpeed_10_0= ruleDOUBLE )
                    // InternalMazeDsl.g:1137:6: lv_projectileSpeed_10_0= ruleDOUBLE
                    {

                    						newCompositeNode(grammarAccess.getRangedSpecificsAccess().getProjectileSpeedDOUBLEParserRuleCall_6_1_0());
                    					
                    pushFollow(FOLLOW_45);
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

            // InternalMazeDsl.g:1155:3: (otherlv_11= 'projectileType' ( (lv_projectileType_12_0= ruleProjectileTypeEnum ) ) )?
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==45) ) {
                alt31=1;
            }
            switch (alt31) {
                case 1 :
                    // InternalMazeDsl.g:1156:4: otherlv_11= 'projectileType' ( (lv_projectileType_12_0= ruleProjectileTypeEnum ) )
                    {
                    otherlv_11=(Token)match(input,45,FOLLOW_46); 

                    				newLeafNode(otherlv_11, grammarAccess.getRangedSpecificsAccess().getProjectileTypeKeyword_7_0());
                    			
                    // InternalMazeDsl.g:1160:4: ( (lv_projectileType_12_0= ruleProjectileTypeEnum ) )
                    // InternalMazeDsl.g:1161:5: (lv_projectileType_12_0= ruleProjectileTypeEnum )
                    {
                    // InternalMazeDsl.g:1161:5: (lv_projectileType_12_0= ruleProjectileTypeEnum )
                    // InternalMazeDsl.g:1162:6: lv_projectileType_12_0= ruleProjectileTypeEnum
                    {

                    						newCompositeNode(grammarAccess.getRangedSpecificsAccess().getProjectileTypeProjectileTypeEnumEnumRuleCall_7_1_0());
                    					
                    pushFollow(FOLLOW_47);
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

            // InternalMazeDsl.g:1180:3: (otherlv_13= 'splashRadius' ( (lv_splashRadius_14_0= ruleDOUBLE ) ) )?
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==46) ) {
                alt32=1;
            }
            switch (alt32) {
                case 1 :
                    // InternalMazeDsl.g:1181:4: otherlv_13= 'splashRadius' ( (lv_splashRadius_14_0= ruleDOUBLE ) )
                    {
                    otherlv_13=(Token)match(input,46,FOLLOW_15); 

                    				newLeafNode(otherlv_13, grammarAccess.getRangedSpecificsAccess().getSplashRadiusKeyword_8_0());
                    			
                    // InternalMazeDsl.g:1185:4: ( (lv_splashRadius_14_0= ruleDOUBLE ) )
                    // InternalMazeDsl.g:1186:5: (lv_splashRadius_14_0= ruleDOUBLE )
                    {
                    // InternalMazeDsl.g:1186:5: (lv_splashRadius_14_0= ruleDOUBLE )
                    // InternalMazeDsl.g:1187:6: lv_splashRadius_14_0= ruleDOUBLE
                    {

                    						newCompositeNode(grammarAccess.getRangedSpecificsAccess().getSplashRadiusDOUBLEParserRuleCall_8_1_0());
                    					
                    pushFollow(FOLLOW_33);
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
    // InternalMazeDsl.g:1213:1: entryRulePatrolConfig returns [EObject current=null] : iv_rulePatrolConfig= rulePatrolConfig EOF ;
    public final EObject entryRulePatrolConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePatrolConfig = null;


        try {
            // InternalMazeDsl.g:1213:53: (iv_rulePatrolConfig= rulePatrolConfig EOF )
            // InternalMazeDsl.g:1214:2: iv_rulePatrolConfig= rulePatrolConfig EOF
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
    // InternalMazeDsl.g:1220:1: rulePatrolConfig returns [EObject current=null] : (otherlv_0= 'patrol' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' (otherlv_3= 'visionRange' ( (lv_visionRange_4_0= ruleDOUBLE ) ) )? ( (lv_zone_5_0= rulePatrolZoneConfig ) )? otherlv_6= 'path' otherlv_7= '[' ( (lv_waypoints_8_0= ruleWaypoint ) ) (otherlv_9= ',' ( (lv_waypoints_10_0= ruleWaypoint ) ) )* otherlv_11= ']' otherlv_12= '}' ) ;
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
            // InternalMazeDsl.g:1226:2: ( (otherlv_0= 'patrol' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' (otherlv_3= 'visionRange' ( (lv_visionRange_4_0= ruleDOUBLE ) ) )? ( (lv_zone_5_0= rulePatrolZoneConfig ) )? otherlv_6= 'path' otherlv_7= '[' ( (lv_waypoints_8_0= ruleWaypoint ) ) (otherlv_9= ',' ( (lv_waypoints_10_0= ruleWaypoint ) ) )* otherlv_11= ']' otherlv_12= '}' ) )
            // InternalMazeDsl.g:1227:2: (otherlv_0= 'patrol' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' (otherlv_3= 'visionRange' ( (lv_visionRange_4_0= ruleDOUBLE ) ) )? ( (lv_zone_5_0= rulePatrolZoneConfig ) )? otherlv_6= 'path' otherlv_7= '[' ( (lv_waypoints_8_0= ruleWaypoint ) ) (otherlv_9= ',' ( (lv_waypoints_10_0= ruleWaypoint ) ) )* otherlv_11= ']' otherlv_12= '}' )
            {
            // InternalMazeDsl.g:1227:2: (otherlv_0= 'patrol' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' (otherlv_3= 'visionRange' ( (lv_visionRange_4_0= ruleDOUBLE ) ) )? ( (lv_zone_5_0= rulePatrolZoneConfig ) )? otherlv_6= 'path' otherlv_7= '[' ( (lv_waypoints_8_0= ruleWaypoint ) ) (otherlv_9= ',' ( (lv_waypoints_10_0= ruleWaypoint ) ) )* otherlv_11= ']' otherlv_12= '}' )
            // InternalMazeDsl.g:1228:3: otherlv_0= 'patrol' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' (otherlv_3= 'visionRange' ( (lv_visionRange_4_0= ruleDOUBLE ) ) )? ( (lv_zone_5_0= rulePatrolZoneConfig ) )? otherlv_6= 'path' otherlv_7= '[' ( (lv_waypoints_8_0= ruleWaypoint ) ) (otherlv_9= ',' ( (lv_waypoints_10_0= ruleWaypoint ) ) )* otherlv_11= ']' otherlv_12= '}'
            {
            otherlv_0=(Token)match(input,32,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getPatrolConfigAccess().getPatrolKeyword_0());
            		
            // InternalMazeDsl.g:1232:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalMazeDsl.g:1233:4: (lv_name_1_0= RULE_ID )
            {
            // InternalMazeDsl.g:1233:4: (lv_name_1_0= RULE_ID )
            // InternalMazeDsl.g:1234:5: lv_name_1_0= RULE_ID
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

            otherlv_2=(Token)match(input,12,FOLLOW_48); 

            			newLeafNode(otherlv_2, grammarAccess.getPatrolConfigAccess().getLeftCurlyBracketKeyword_2());
            		
            // InternalMazeDsl.g:1254:3: (otherlv_3= 'visionRange' ( (lv_visionRange_4_0= ruleDOUBLE ) ) )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==47) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // InternalMazeDsl.g:1255:4: otherlv_3= 'visionRange' ( (lv_visionRange_4_0= ruleDOUBLE ) )
                    {
                    otherlv_3=(Token)match(input,47,FOLLOW_15); 

                    				newLeafNode(otherlv_3, grammarAccess.getPatrolConfigAccess().getVisionRangeKeyword_3_0());
                    			
                    // InternalMazeDsl.g:1259:4: ( (lv_visionRange_4_0= ruleDOUBLE ) )
                    // InternalMazeDsl.g:1260:5: (lv_visionRange_4_0= ruleDOUBLE )
                    {
                    // InternalMazeDsl.g:1260:5: (lv_visionRange_4_0= ruleDOUBLE )
                    // InternalMazeDsl.g:1261:6: lv_visionRange_4_0= ruleDOUBLE
                    {

                    						newCompositeNode(grammarAccess.getPatrolConfigAccess().getVisionRangeDOUBLEParserRuleCall_3_1_0());
                    					
                    pushFollow(FOLLOW_49);
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

            // InternalMazeDsl.g:1279:3: ( (lv_zone_5_0= rulePatrolZoneConfig ) )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==52) ) {
                alt34=1;
            }
            switch (alt34) {
                case 1 :
                    // InternalMazeDsl.g:1280:4: (lv_zone_5_0= rulePatrolZoneConfig )
                    {
                    // InternalMazeDsl.g:1280:4: (lv_zone_5_0= rulePatrolZoneConfig )
                    // InternalMazeDsl.g:1281:5: lv_zone_5_0= rulePatrolZoneConfig
                    {

                    					newCompositeNode(grammarAccess.getPatrolConfigAccess().getZonePatrolZoneConfigParserRuleCall_4_0());
                    				
                    pushFollow(FOLLOW_50);
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

            otherlv_6=(Token)match(input,48,FOLLOW_51); 

            			newLeafNode(otherlv_6, grammarAccess.getPatrolConfigAccess().getPathKeyword_5());
            		
            otherlv_7=(Token)match(input,49,FOLLOW_52); 

            			newLeafNode(otherlv_7, grammarAccess.getPatrolConfigAccess().getLeftSquareBracketKeyword_6());
            		
            // InternalMazeDsl.g:1306:3: ( (lv_waypoints_8_0= ruleWaypoint ) )
            // InternalMazeDsl.g:1307:4: (lv_waypoints_8_0= ruleWaypoint )
            {
            // InternalMazeDsl.g:1307:4: (lv_waypoints_8_0= ruleWaypoint )
            // InternalMazeDsl.g:1308:5: lv_waypoints_8_0= ruleWaypoint
            {

            					newCompositeNode(grammarAccess.getPatrolConfigAccess().getWaypointsWaypointParserRuleCall_7_0());
            				
            pushFollow(FOLLOW_53);
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

            // InternalMazeDsl.g:1325:3: (otherlv_9= ',' ( (lv_waypoints_10_0= ruleWaypoint ) ) )*
            loop35:
            do {
                int alt35=2;
                int LA35_0 = input.LA(1);

                if ( (LA35_0==50) ) {
                    alt35=1;
                }


                switch (alt35) {
            	case 1 :
            	    // InternalMazeDsl.g:1326:4: otherlv_9= ',' ( (lv_waypoints_10_0= ruleWaypoint ) )
            	    {
            	    otherlv_9=(Token)match(input,50,FOLLOW_52); 

            	    				newLeafNode(otherlv_9, grammarAccess.getPatrolConfigAccess().getCommaKeyword_8_0());
            	    			
            	    // InternalMazeDsl.g:1330:4: ( (lv_waypoints_10_0= ruleWaypoint ) )
            	    // InternalMazeDsl.g:1331:5: (lv_waypoints_10_0= ruleWaypoint )
            	    {
            	    // InternalMazeDsl.g:1331:5: (lv_waypoints_10_0= ruleWaypoint )
            	    // InternalMazeDsl.g:1332:6: lv_waypoints_10_0= ruleWaypoint
            	    {

            	    						newCompositeNode(grammarAccess.getPatrolConfigAccess().getWaypointsWaypointParserRuleCall_8_1_0());
            	    					
            	    pushFollow(FOLLOW_53);
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
            	    break loop35;
                }
            } while (true);

            otherlv_11=(Token)match(input,51,FOLLOW_33); 

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
    // InternalMazeDsl.g:1362:1: entryRulePatrolZoneConfig returns [EObject current=null] : iv_rulePatrolZoneConfig= rulePatrolZoneConfig EOF ;
    public final EObject entryRulePatrolZoneConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePatrolZoneConfig = null;


        try {
            // InternalMazeDsl.g:1362:57: (iv_rulePatrolZoneConfig= rulePatrolZoneConfig EOF )
            // InternalMazeDsl.g:1363:2: iv_rulePatrolZoneConfig= rulePatrolZoneConfig EOF
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
    // InternalMazeDsl.g:1369:1: rulePatrolZoneConfig returns [EObject current=null] : (otherlv_0= 'zone' otherlv_1= '{' otherlv_2= 'topLeft' otherlv_3= '(' ( (lv_topLeftX_4_0= ruleDOUBLE ) ) otherlv_5= ',' ( (lv_topLeftY_6_0= ruleDOUBLE ) ) otherlv_7= ')' otherlv_8= 'width' ( (lv_width_9_0= ruleDOUBLE ) ) otherlv_10= 'height' ( (lv_height_11_0= ruleDOUBLE ) ) otherlv_12= '}' ) ;
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
            // InternalMazeDsl.g:1375:2: ( (otherlv_0= 'zone' otherlv_1= '{' otherlv_2= 'topLeft' otherlv_3= '(' ( (lv_topLeftX_4_0= ruleDOUBLE ) ) otherlv_5= ',' ( (lv_topLeftY_6_0= ruleDOUBLE ) ) otherlv_7= ')' otherlv_8= 'width' ( (lv_width_9_0= ruleDOUBLE ) ) otherlv_10= 'height' ( (lv_height_11_0= ruleDOUBLE ) ) otherlv_12= '}' ) )
            // InternalMazeDsl.g:1376:2: (otherlv_0= 'zone' otherlv_1= '{' otherlv_2= 'topLeft' otherlv_3= '(' ( (lv_topLeftX_4_0= ruleDOUBLE ) ) otherlv_5= ',' ( (lv_topLeftY_6_0= ruleDOUBLE ) ) otherlv_7= ')' otherlv_8= 'width' ( (lv_width_9_0= ruleDOUBLE ) ) otherlv_10= 'height' ( (lv_height_11_0= ruleDOUBLE ) ) otherlv_12= '}' )
            {
            // InternalMazeDsl.g:1376:2: (otherlv_0= 'zone' otherlv_1= '{' otherlv_2= 'topLeft' otherlv_3= '(' ( (lv_topLeftX_4_0= ruleDOUBLE ) ) otherlv_5= ',' ( (lv_topLeftY_6_0= ruleDOUBLE ) ) otherlv_7= ')' otherlv_8= 'width' ( (lv_width_9_0= ruleDOUBLE ) ) otherlv_10= 'height' ( (lv_height_11_0= ruleDOUBLE ) ) otherlv_12= '}' )
            // InternalMazeDsl.g:1377:3: otherlv_0= 'zone' otherlv_1= '{' otherlv_2= 'topLeft' otherlv_3= '(' ( (lv_topLeftX_4_0= ruleDOUBLE ) ) otherlv_5= ',' ( (lv_topLeftY_6_0= ruleDOUBLE ) ) otherlv_7= ')' otherlv_8= 'width' ( (lv_width_9_0= ruleDOUBLE ) ) otherlv_10= 'height' ( (lv_height_11_0= ruleDOUBLE ) ) otherlv_12= '}'
            {
            otherlv_0=(Token)match(input,52,FOLLOW_4); 

            			newLeafNode(otherlv_0, grammarAccess.getPatrolZoneConfigAccess().getZoneKeyword_0());
            		
            otherlv_1=(Token)match(input,12,FOLLOW_54); 

            			newLeafNode(otherlv_1, grammarAccess.getPatrolZoneConfigAccess().getLeftCurlyBracketKeyword_1());
            		
            otherlv_2=(Token)match(input,53,FOLLOW_52); 

            			newLeafNode(otherlv_2, grammarAccess.getPatrolZoneConfigAccess().getTopLeftKeyword_2());
            		
            otherlv_3=(Token)match(input,54,FOLLOW_15); 

            			newLeafNode(otherlv_3, grammarAccess.getPatrolZoneConfigAccess().getLeftParenthesisKeyword_3());
            		
            // InternalMazeDsl.g:1393:3: ( (lv_topLeftX_4_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:1394:4: (lv_topLeftX_4_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:1394:4: (lv_topLeftX_4_0= ruleDOUBLE )
            // InternalMazeDsl.g:1395:5: lv_topLeftX_4_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getPatrolZoneConfigAccess().getTopLeftXDOUBLEParserRuleCall_4_0());
            				
            pushFollow(FOLLOW_55);
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

            otherlv_5=(Token)match(input,50,FOLLOW_15); 

            			newLeafNode(otherlv_5, grammarAccess.getPatrolZoneConfigAccess().getCommaKeyword_5());
            		
            // InternalMazeDsl.g:1416:3: ( (lv_topLeftY_6_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:1417:4: (lv_topLeftY_6_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:1417:4: (lv_topLeftY_6_0= ruleDOUBLE )
            // InternalMazeDsl.g:1418:5: lv_topLeftY_6_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getPatrolZoneConfigAccess().getTopLeftYDOUBLEParserRuleCall_6_0());
            				
            pushFollow(FOLLOW_56);
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

            otherlv_7=(Token)match(input,55,FOLLOW_57); 

            			newLeafNode(otherlv_7, grammarAccess.getPatrolZoneConfigAccess().getRightParenthesisKeyword_7());
            		
            otherlv_8=(Token)match(input,56,FOLLOW_15); 

            			newLeafNode(otherlv_8, grammarAccess.getPatrolZoneConfigAccess().getWidthKeyword_8());
            		
            // InternalMazeDsl.g:1443:3: ( (lv_width_9_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:1444:4: (lv_width_9_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:1444:4: (lv_width_9_0= ruleDOUBLE )
            // InternalMazeDsl.g:1445:5: lv_width_9_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getPatrolZoneConfigAccess().getWidthDOUBLEParserRuleCall_9_0());
            				
            pushFollow(FOLLOW_58);
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

            otherlv_10=(Token)match(input,57,FOLLOW_15); 

            			newLeafNode(otherlv_10, grammarAccess.getPatrolZoneConfigAccess().getHeightKeyword_10());
            		
            // InternalMazeDsl.g:1466:3: ( (lv_height_11_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:1467:4: (lv_height_11_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:1467:4: (lv_height_11_0= ruleDOUBLE )
            // InternalMazeDsl.g:1468:5: lv_height_11_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getPatrolZoneConfigAccess().getHeightDOUBLEParserRuleCall_11_0());
            				
            pushFollow(FOLLOW_33);
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
    // InternalMazeDsl.g:1493:1: entryRuleWaypoint returns [EObject current=null] : iv_ruleWaypoint= ruleWaypoint EOF ;
    public final EObject entryRuleWaypoint() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleWaypoint = null;


        try {
            // InternalMazeDsl.g:1493:49: (iv_ruleWaypoint= ruleWaypoint EOF )
            // InternalMazeDsl.g:1494:2: iv_ruleWaypoint= ruleWaypoint EOF
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
    // InternalMazeDsl.g:1500:1: ruleWaypoint returns [EObject current=null] : (otherlv_0= '(' ( (lv_x_1_0= ruleDOUBLE ) ) otherlv_2= ',' ( (lv_y_3_0= ruleDOUBLE ) ) otherlv_4= ')' (otherlv_5= ':' ( (lv_waitTime_6_0= RULE_INT ) ) otherlv_7= 'ms' )? ) ;
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
            // InternalMazeDsl.g:1506:2: ( (otherlv_0= '(' ( (lv_x_1_0= ruleDOUBLE ) ) otherlv_2= ',' ( (lv_y_3_0= ruleDOUBLE ) ) otherlv_4= ')' (otherlv_5= ':' ( (lv_waitTime_6_0= RULE_INT ) ) otherlv_7= 'ms' )? ) )
            // InternalMazeDsl.g:1507:2: (otherlv_0= '(' ( (lv_x_1_0= ruleDOUBLE ) ) otherlv_2= ',' ( (lv_y_3_0= ruleDOUBLE ) ) otherlv_4= ')' (otherlv_5= ':' ( (lv_waitTime_6_0= RULE_INT ) ) otherlv_7= 'ms' )? )
            {
            // InternalMazeDsl.g:1507:2: (otherlv_0= '(' ( (lv_x_1_0= ruleDOUBLE ) ) otherlv_2= ',' ( (lv_y_3_0= ruleDOUBLE ) ) otherlv_4= ')' (otherlv_5= ':' ( (lv_waitTime_6_0= RULE_INT ) ) otherlv_7= 'ms' )? )
            // InternalMazeDsl.g:1508:3: otherlv_0= '(' ( (lv_x_1_0= ruleDOUBLE ) ) otherlv_2= ',' ( (lv_y_3_0= ruleDOUBLE ) ) otherlv_4= ')' (otherlv_5= ':' ( (lv_waitTime_6_0= RULE_INT ) ) otherlv_7= 'ms' )?
            {
            otherlv_0=(Token)match(input,54,FOLLOW_15); 

            			newLeafNode(otherlv_0, grammarAccess.getWaypointAccess().getLeftParenthesisKeyword_0());
            		
            // InternalMazeDsl.g:1512:3: ( (lv_x_1_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:1513:4: (lv_x_1_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:1513:4: (lv_x_1_0= ruleDOUBLE )
            // InternalMazeDsl.g:1514:5: lv_x_1_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getWaypointAccess().getXDOUBLEParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_55);
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

            otherlv_2=(Token)match(input,50,FOLLOW_15); 

            			newLeafNode(otherlv_2, grammarAccess.getWaypointAccess().getCommaKeyword_2());
            		
            // InternalMazeDsl.g:1535:3: ( (lv_y_3_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:1536:4: (lv_y_3_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:1536:4: (lv_y_3_0= ruleDOUBLE )
            // InternalMazeDsl.g:1537:5: lv_y_3_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getWaypointAccess().getYDOUBLEParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_56);
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

            otherlv_4=(Token)match(input,55,FOLLOW_59); 

            			newLeafNode(otherlv_4, grammarAccess.getWaypointAccess().getRightParenthesisKeyword_4());
            		
            // InternalMazeDsl.g:1558:3: (otherlv_5= ':' ( (lv_waitTime_6_0= RULE_INT ) ) otherlv_7= 'ms' )?
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==58) ) {
                alt36=1;
            }
            switch (alt36) {
                case 1 :
                    // InternalMazeDsl.g:1559:4: otherlv_5= ':' ( (lv_waitTime_6_0= RULE_INT ) ) otherlv_7= 'ms'
                    {
                    otherlv_5=(Token)match(input,58,FOLLOW_35); 

                    				newLeafNode(otherlv_5, grammarAccess.getWaypointAccess().getColonKeyword_5_0());
                    			
                    // InternalMazeDsl.g:1563:4: ( (lv_waitTime_6_0= RULE_INT ) )
                    // InternalMazeDsl.g:1564:5: (lv_waitTime_6_0= RULE_INT )
                    {
                    // InternalMazeDsl.g:1564:5: (lv_waitTime_6_0= RULE_INT )
                    // InternalMazeDsl.g:1565:6: lv_waitTime_6_0= RULE_INT
                    {
                    lv_waitTime_6_0=(Token)match(input,RULE_INT,FOLLOW_60); 

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
    // InternalMazeDsl.g:1590:1: entryRuleLootTableConfig returns [EObject current=null] : iv_ruleLootTableConfig= ruleLootTableConfig EOF ;
    public final EObject entryRuleLootTableConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLootTableConfig = null;


        try {
            // InternalMazeDsl.g:1590:56: (iv_ruleLootTableConfig= ruleLootTableConfig EOF )
            // InternalMazeDsl.g:1591:2: iv_ruleLootTableConfig= ruleLootTableConfig EOF
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
    // InternalMazeDsl.g:1597:1: ruleLootTableConfig returns [EObject current=null] : (otherlv_0= 'loot-table' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' (otherlv_3= 'capacity' ( (lv_capacity_4_0= RULE_INT ) ) )? ( (lv_items_5_0= ruleLootItemConfig ) )+ otherlv_6= '}' ) ;
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
            // InternalMazeDsl.g:1603:2: ( (otherlv_0= 'loot-table' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' (otherlv_3= 'capacity' ( (lv_capacity_4_0= RULE_INT ) ) )? ( (lv_items_5_0= ruleLootItemConfig ) )+ otherlv_6= '}' ) )
            // InternalMazeDsl.g:1604:2: (otherlv_0= 'loot-table' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' (otherlv_3= 'capacity' ( (lv_capacity_4_0= RULE_INT ) ) )? ( (lv_items_5_0= ruleLootItemConfig ) )+ otherlv_6= '}' )
            {
            // InternalMazeDsl.g:1604:2: (otherlv_0= 'loot-table' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' (otherlv_3= 'capacity' ( (lv_capacity_4_0= RULE_INT ) ) )? ( (lv_items_5_0= ruleLootItemConfig ) )+ otherlv_6= '}' )
            // InternalMazeDsl.g:1605:3: otherlv_0= 'loot-table' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' (otherlv_3= 'capacity' ( (lv_capacity_4_0= RULE_INT ) ) )? ( (lv_items_5_0= ruleLootItemConfig ) )+ otherlv_6= '}'
            {
            otherlv_0=(Token)match(input,60,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getLootTableConfigAccess().getLootTableKeyword_0());
            		
            // InternalMazeDsl.g:1609:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalMazeDsl.g:1610:4: (lv_name_1_0= RULE_ID )
            {
            // InternalMazeDsl.g:1610:4: (lv_name_1_0= RULE_ID )
            // InternalMazeDsl.g:1611:5: lv_name_1_0= RULE_ID
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

            otherlv_2=(Token)match(input,12,FOLLOW_61); 

            			newLeafNode(otherlv_2, grammarAccess.getLootTableConfigAccess().getLeftCurlyBracketKeyword_2());
            		
            // InternalMazeDsl.g:1631:3: (otherlv_3= 'capacity' ( (lv_capacity_4_0= RULE_INT ) ) )?
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( (LA37_0==61) ) {
                alt37=1;
            }
            switch (alt37) {
                case 1 :
                    // InternalMazeDsl.g:1632:4: otherlv_3= 'capacity' ( (lv_capacity_4_0= RULE_INT ) )
                    {
                    otherlv_3=(Token)match(input,61,FOLLOW_35); 

                    				newLeafNode(otherlv_3, grammarAccess.getLootTableConfigAccess().getCapacityKeyword_3_0());
                    			
                    // InternalMazeDsl.g:1636:4: ( (lv_capacity_4_0= RULE_INT ) )
                    // InternalMazeDsl.g:1637:5: (lv_capacity_4_0= RULE_INT )
                    {
                    // InternalMazeDsl.g:1637:5: (lv_capacity_4_0= RULE_INT )
                    // InternalMazeDsl.g:1638:6: lv_capacity_4_0= RULE_INT
                    {
                    lv_capacity_4_0=(Token)match(input,RULE_INT,FOLLOW_61); 

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

            // InternalMazeDsl.g:1655:3: ( (lv_items_5_0= ruleLootItemConfig ) )+
            int cnt38=0;
            loop38:
            do {
                int alt38=2;
                int LA38_0 = input.LA(1);

                if ( (LA38_0==62) ) {
                    alt38=1;
                }


                switch (alt38) {
            	case 1 :
            	    // InternalMazeDsl.g:1656:4: (lv_items_5_0= ruleLootItemConfig )
            	    {
            	    // InternalMazeDsl.g:1656:4: (lv_items_5_0= ruleLootItemConfig )
            	    // InternalMazeDsl.g:1657:5: lv_items_5_0= ruleLootItemConfig
            	    {

            	    					newCompositeNode(grammarAccess.getLootTableConfigAccess().getItemsLootItemConfigParserRuleCall_4_0());
            	    				
            	    pushFollow(FOLLOW_62);
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
            	    if ( cnt38 >= 1 ) break loop38;
                        EarlyExitException eee =
                            new EarlyExitException(38, input);
                        throw eee;
                }
                cnt38++;
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
    // InternalMazeDsl.g:1682:1: entryRuleLootItemConfig returns [EObject current=null] : iv_ruleLootItemConfig= ruleLootItemConfig EOF ;
    public final EObject entryRuleLootItemConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLootItemConfig = null;


        try {
            // InternalMazeDsl.g:1682:55: (iv_ruleLootItemConfig= ruleLootItemConfig EOF )
            // InternalMazeDsl.g:1683:2: iv_ruleLootItemConfig= ruleLootItemConfig EOF
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
    // InternalMazeDsl.g:1689:1: ruleLootItemConfig returns [EObject current=null] : (otherlv_0= 'item' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'type' ( (lv_type_4_0= ruleLootItemTypeEnum ) ) otherlv_5= 'value' ( (lv_value_6_0= RULE_INT ) ) (otherlv_7= 'weight' ( (lv_weight_8_0= RULE_INT ) ) )? otherlv_9= '}' ) ;
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
            // InternalMazeDsl.g:1695:2: ( (otherlv_0= 'item' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'type' ( (lv_type_4_0= ruleLootItemTypeEnum ) ) otherlv_5= 'value' ( (lv_value_6_0= RULE_INT ) ) (otherlv_7= 'weight' ( (lv_weight_8_0= RULE_INT ) ) )? otherlv_9= '}' ) )
            // InternalMazeDsl.g:1696:2: (otherlv_0= 'item' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'type' ( (lv_type_4_0= ruleLootItemTypeEnum ) ) otherlv_5= 'value' ( (lv_value_6_0= RULE_INT ) ) (otherlv_7= 'weight' ( (lv_weight_8_0= RULE_INT ) ) )? otherlv_9= '}' )
            {
            // InternalMazeDsl.g:1696:2: (otherlv_0= 'item' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'type' ( (lv_type_4_0= ruleLootItemTypeEnum ) ) otherlv_5= 'value' ( (lv_value_6_0= RULE_INT ) ) (otherlv_7= 'weight' ( (lv_weight_8_0= RULE_INT ) ) )? otherlv_9= '}' )
            // InternalMazeDsl.g:1697:3: otherlv_0= 'item' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'type' ( (lv_type_4_0= ruleLootItemTypeEnum ) ) otherlv_5= 'value' ( (lv_value_6_0= RULE_INT ) ) (otherlv_7= 'weight' ( (lv_weight_8_0= RULE_INT ) ) )? otherlv_9= '}'
            {
            otherlv_0=(Token)match(input,62,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getLootItemConfigAccess().getItemKeyword_0());
            		
            // InternalMazeDsl.g:1701:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalMazeDsl.g:1702:4: (lv_name_1_0= RULE_ID )
            {
            // InternalMazeDsl.g:1702:4: (lv_name_1_0= RULE_ID )
            // InternalMazeDsl.g:1703:5: lv_name_1_0= RULE_ID
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

            otherlv_2=(Token)match(input,12,FOLLOW_21); 

            			newLeafNode(otherlv_2, grammarAccess.getLootItemConfigAccess().getLeftCurlyBracketKeyword_2());
            		
            otherlv_3=(Token)match(input,25,FOLLOW_63); 

            			newLeafNode(otherlv_3, grammarAccess.getLootItemConfigAccess().getTypeKeyword_3());
            		
            // InternalMazeDsl.g:1727:3: ( (lv_type_4_0= ruleLootItemTypeEnum ) )
            // InternalMazeDsl.g:1728:4: (lv_type_4_0= ruleLootItemTypeEnum )
            {
            // InternalMazeDsl.g:1728:4: (lv_type_4_0= ruleLootItemTypeEnum )
            // InternalMazeDsl.g:1729:5: lv_type_4_0= ruleLootItemTypeEnum
            {

            					newCompositeNode(grammarAccess.getLootItemConfigAccess().getTypeLootItemTypeEnumEnumRuleCall_4_0());
            				
            pushFollow(FOLLOW_64);
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

            otherlv_5=(Token)match(input,63,FOLLOW_35); 

            			newLeafNode(otherlv_5, grammarAccess.getLootItemConfigAccess().getValueKeyword_5());
            		
            // InternalMazeDsl.g:1750:3: ( (lv_value_6_0= RULE_INT ) )
            // InternalMazeDsl.g:1751:4: (lv_value_6_0= RULE_INT )
            {
            // InternalMazeDsl.g:1751:4: (lv_value_6_0= RULE_INT )
            // InternalMazeDsl.g:1752:5: lv_value_6_0= RULE_INT
            {
            lv_value_6_0=(Token)match(input,RULE_INT,FOLLOW_65); 

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

            // InternalMazeDsl.g:1768:3: (otherlv_7= 'weight' ( (lv_weight_8_0= RULE_INT ) ) )?
            int alt39=2;
            int LA39_0 = input.LA(1);

            if ( (LA39_0==64) ) {
                alt39=1;
            }
            switch (alt39) {
                case 1 :
                    // InternalMazeDsl.g:1769:4: otherlv_7= 'weight' ( (lv_weight_8_0= RULE_INT ) )
                    {
                    otherlv_7=(Token)match(input,64,FOLLOW_35); 

                    				newLeafNode(otherlv_7, grammarAccess.getLootItemConfigAccess().getWeightKeyword_7_0());
                    			
                    // InternalMazeDsl.g:1773:4: ( (lv_weight_8_0= RULE_INT ) )
                    // InternalMazeDsl.g:1774:5: (lv_weight_8_0= RULE_INT )
                    {
                    // InternalMazeDsl.g:1774:5: (lv_weight_8_0= RULE_INT )
                    // InternalMazeDsl.g:1775:6: lv_weight_8_0= RULE_INT
                    {
                    lv_weight_8_0=(Token)match(input,RULE_INT,FOLLOW_33); 

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


    // $ANTLR start "entryRuleDOUBLE"
    // InternalMazeDsl.g:1800:1: entryRuleDOUBLE returns [String current=null] : iv_ruleDOUBLE= ruleDOUBLE EOF ;
    public final String entryRuleDOUBLE() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDOUBLE = null;


        try {
            // InternalMazeDsl.g:1800:46: (iv_ruleDOUBLE= ruleDOUBLE EOF )
            // InternalMazeDsl.g:1801:2: iv_ruleDOUBLE= ruleDOUBLE EOF
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
    // InternalMazeDsl.g:1807:1: ruleDOUBLE returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= '-' )? this_INT_1= RULE_INT (kw= '.' this_INT_3= RULE_INT )? ) ;
    public final AntlrDatatypeRuleToken ruleDOUBLE() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_INT_1=null;
        Token this_INT_3=null;


        	enterRule();

        try {
            // InternalMazeDsl.g:1813:2: ( ( (kw= '-' )? this_INT_1= RULE_INT (kw= '.' this_INT_3= RULE_INT )? ) )
            // InternalMazeDsl.g:1814:2: ( (kw= '-' )? this_INT_1= RULE_INT (kw= '.' this_INT_3= RULE_INT )? )
            {
            // InternalMazeDsl.g:1814:2: ( (kw= '-' )? this_INT_1= RULE_INT (kw= '.' this_INT_3= RULE_INT )? )
            // InternalMazeDsl.g:1815:3: (kw= '-' )? this_INT_1= RULE_INT (kw= '.' this_INT_3= RULE_INT )?
            {
            // InternalMazeDsl.g:1815:3: (kw= '-' )?
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( (LA40_0==65) ) {
                alt40=1;
            }
            switch (alt40) {
                case 1 :
                    // InternalMazeDsl.g:1816:4: kw= '-'
                    {
                    kw=(Token)match(input,65,FOLLOW_35); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getDOUBLEAccess().getHyphenMinusKeyword_0());
                    			

                    }
                    break;

            }

            this_INT_1=(Token)match(input,RULE_INT,FOLLOW_66); 

            			current.merge(this_INT_1);
            		

            			newLeafNode(this_INT_1, grammarAccess.getDOUBLEAccess().getINTTerminalRuleCall_1());
            		
            // InternalMazeDsl.g:1829:3: (kw= '.' this_INT_3= RULE_INT )?
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( (LA41_0==66) ) {
                alt41=1;
            }
            switch (alt41) {
                case 1 :
                    // InternalMazeDsl.g:1830:4: kw= '.' this_INT_3= RULE_INT
                    {
                    kw=(Token)match(input,66,FOLLOW_35); 

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
    // InternalMazeDsl.g:1847:1: entryRuleSIGNED_INT returns [String current=null] : iv_ruleSIGNED_INT= ruleSIGNED_INT EOF ;
    public final String entryRuleSIGNED_INT() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSIGNED_INT = null;


        try {
            // InternalMazeDsl.g:1847:50: (iv_ruleSIGNED_INT= ruleSIGNED_INT EOF )
            // InternalMazeDsl.g:1848:2: iv_ruleSIGNED_INT= ruleSIGNED_INT EOF
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
    // InternalMazeDsl.g:1854:1: ruleSIGNED_INT returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= '-' )? this_INT_1= RULE_INT ) ;
    public final AntlrDatatypeRuleToken ruleSIGNED_INT() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_INT_1=null;


        	enterRule();

        try {
            // InternalMazeDsl.g:1860:2: ( ( (kw= '-' )? this_INT_1= RULE_INT ) )
            // InternalMazeDsl.g:1861:2: ( (kw= '-' )? this_INT_1= RULE_INT )
            {
            // InternalMazeDsl.g:1861:2: ( (kw= '-' )? this_INT_1= RULE_INT )
            // InternalMazeDsl.g:1862:3: (kw= '-' )? this_INT_1= RULE_INT
            {
            // InternalMazeDsl.g:1862:3: (kw= '-' )?
            int alt42=2;
            int LA42_0 = input.LA(1);

            if ( (LA42_0==65) ) {
                alt42=1;
            }
            switch (alt42) {
                case 1 :
                    // InternalMazeDsl.g:1863:4: kw= '-'
                    {
                    kw=(Token)match(input,65,FOLLOW_35); 

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
    // InternalMazeDsl.g:1880:1: entryRuleBOOLEAN returns [String current=null] : iv_ruleBOOLEAN= ruleBOOLEAN EOF ;
    public final String entryRuleBOOLEAN() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleBOOLEAN = null;


        try {
            // InternalMazeDsl.g:1880:47: (iv_ruleBOOLEAN= ruleBOOLEAN EOF )
            // InternalMazeDsl.g:1881:2: iv_ruleBOOLEAN= ruleBOOLEAN EOF
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
    // InternalMazeDsl.g:1887:1: ruleBOOLEAN returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'true' | kw= 'false' ) ;
    public final AntlrDatatypeRuleToken ruleBOOLEAN() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalMazeDsl.g:1893:2: ( (kw= 'true' | kw= 'false' ) )
            // InternalMazeDsl.g:1894:2: (kw= 'true' | kw= 'false' )
            {
            // InternalMazeDsl.g:1894:2: (kw= 'true' | kw= 'false' )
            int alt43=2;
            int LA43_0 = input.LA(1);

            if ( (LA43_0==18) ) {
                alt43=1;
            }
            else if ( (LA43_0==67) ) {
                alt43=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 43, 0, input);

                throw nvae;
            }
            switch (alt43) {
                case 1 :
                    // InternalMazeDsl.g:1895:3: kw= 'true'
                    {
                    kw=(Token)match(input,18,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getBOOLEANAccess().getTrueKeyword_0());
                    		

                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:1901:3: kw= 'false'
                    {
                    kw=(Token)match(input,67,FOLLOW_2); 

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


    // $ANTLR start "ruleDifficultyLevel"
    // InternalMazeDsl.g:1910:1: ruleDifficultyLevel returns [Enumerator current=null] : ( (enumLiteral_0= 'easy' ) | (enumLiteral_1= 'normal' ) | (enumLiteral_2= 'hard' ) ) ;
    public final Enumerator ruleDifficultyLevel() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalMazeDsl.g:1916:2: ( ( (enumLiteral_0= 'easy' ) | (enumLiteral_1= 'normal' ) | (enumLiteral_2= 'hard' ) ) )
            // InternalMazeDsl.g:1917:2: ( (enumLiteral_0= 'easy' ) | (enumLiteral_1= 'normal' ) | (enumLiteral_2= 'hard' ) )
            {
            // InternalMazeDsl.g:1917:2: ( (enumLiteral_0= 'easy' ) | (enumLiteral_1= 'normal' ) | (enumLiteral_2= 'hard' ) )
            int alt44=3;
            switch ( input.LA(1) ) {
            case 68:
                {
                alt44=1;
                }
                break;
            case 69:
                {
                alt44=2;
                }
                break;
            case 70:
                {
                alt44=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 44, 0, input);

                throw nvae;
            }

            switch (alt44) {
                case 1 :
                    // InternalMazeDsl.g:1918:3: (enumLiteral_0= 'easy' )
                    {
                    // InternalMazeDsl.g:1918:3: (enumLiteral_0= 'easy' )
                    // InternalMazeDsl.g:1919:4: enumLiteral_0= 'easy'
                    {
                    enumLiteral_0=(Token)match(input,68,FOLLOW_2); 

                    				current = grammarAccess.getDifficultyLevelAccess().getEASYEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getDifficultyLevelAccess().getEASYEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:1926:3: (enumLiteral_1= 'normal' )
                    {
                    // InternalMazeDsl.g:1926:3: (enumLiteral_1= 'normal' )
                    // InternalMazeDsl.g:1927:4: enumLiteral_1= 'normal'
                    {
                    enumLiteral_1=(Token)match(input,69,FOLLOW_2); 

                    				current = grammarAccess.getDifficultyLevelAccess().getNORMALEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getDifficultyLevelAccess().getNORMALEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalMazeDsl.g:1934:3: (enumLiteral_2= 'hard' )
                    {
                    // InternalMazeDsl.g:1934:3: (enumLiteral_2= 'hard' )
                    // InternalMazeDsl.g:1935:4: enumLiteral_2= 'hard'
                    {
                    enumLiteral_2=(Token)match(input,70,FOLLOW_2); 

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
    // InternalMazeDsl.g:1945:1: ruleEnemyType returns [Enumerator current=null] : ( (enumLiteral_0= 'zombie' ) | (enumLiteral_1= 'ghost' ) | (enumLiteral_2= 'pumpkinbomber' ) ) ;
    public final Enumerator ruleEnemyType() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalMazeDsl.g:1951:2: ( ( (enumLiteral_0= 'zombie' ) | (enumLiteral_1= 'ghost' ) | (enumLiteral_2= 'pumpkinbomber' ) ) )
            // InternalMazeDsl.g:1952:2: ( (enumLiteral_0= 'zombie' ) | (enumLiteral_1= 'ghost' ) | (enumLiteral_2= 'pumpkinbomber' ) )
            {
            // InternalMazeDsl.g:1952:2: ( (enumLiteral_0= 'zombie' ) | (enumLiteral_1= 'ghost' ) | (enumLiteral_2= 'pumpkinbomber' ) )
            int alt45=3;
            switch ( input.LA(1) ) {
            case 71:
                {
                alt45=1;
                }
                break;
            case 72:
                {
                alt45=2;
                }
                break;
            case 73:
                {
                alt45=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 45, 0, input);

                throw nvae;
            }

            switch (alt45) {
                case 1 :
                    // InternalMazeDsl.g:1953:3: (enumLiteral_0= 'zombie' )
                    {
                    // InternalMazeDsl.g:1953:3: (enumLiteral_0= 'zombie' )
                    // InternalMazeDsl.g:1954:4: enumLiteral_0= 'zombie'
                    {
                    enumLiteral_0=(Token)match(input,71,FOLLOW_2); 

                    				current = grammarAccess.getEnemyTypeAccess().getZOMBIEEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getEnemyTypeAccess().getZOMBIEEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:1961:3: (enumLiteral_1= 'ghost' )
                    {
                    // InternalMazeDsl.g:1961:3: (enumLiteral_1= 'ghost' )
                    // InternalMazeDsl.g:1962:4: enumLiteral_1= 'ghost'
                    {
                    enumLiteral_1=(Token)match(input,72,FOLLOW_2); 

                    				current = grammarAccess.getEnemyTypeAccess().getGHOSTEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getEnemyTypeAccess().getGHOSTEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalMazeDsl.g:1969:3: (enumLiteral_2= 'pumpkinbomber' )
                    {
                    // InternalMazeDsl.g:1969:3: (enumLiteral_2= 'pumpkinbomber' )
                    // InternalMazeDsl.g:1970:4: enumLiteral_2= 'pumpkinbomber'
                    {
                    enumLiteral_2=(Token)match(input,73,FOLLOW_2); 

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
    // InternalMazeDsl.g:1980:1: ruleCharacterTypeEnum returns [Enumerator current=null] : ( (enumLiteral_0= 'zombie' ) | (enumLiteral_1= 'ghost' ) | (enumLiteral_2= 'pumpkinbomber' ) ) ;
    public final Enumerator ruleCharacterTypeEnum() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalMazeDsl.g:1986:2: ( ( (enumLiteral_0= 'zombie' ) | (enumLiteral_1= 'ghost' ) | (enumLiteral_2= 'pumpkinbomber' ) ) )
            // InternalMazeDsl.g:1987:2: ( (enumLiteral_0= 'zombie' ) | (enumLiteral_1= 'ghost' ) | (enumLiteral_2= 'pumpkinbomber' ) )
            {
            // InternalMazeDsl.g:1987:2: ( (enumLiteral_0= 'zombie' ) | (enumLiteral_1= 'ghost' ) | (enumLiteral_2= 'pumpkinbomber' ) )
            int alt46=3;
            switch ( input.LA(1) ) {
            case 71:
                {
                alt46=1;
                }
                break;
            case 72:
                {
                alt46=2;
                }
                break;
            case 73:
                {
                alt46=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 46, 0, input);

                throw nvae;
            }

            switch (alt46) {
                case 1 :
                    // InternalMazeDsl.g:1988:3: (enumLiteral_0= 'zombie' )
                    {
                    // InternalMazeDsl.g:1988:3: (enumLiteral_0= 'zombie' )
                    // InternalMazeDsl.g:1989:4: enumLiteral_0= 'zombie'
                    {
                    enumLiteral_0=(Token)match(input,71,FOLLOW_2); 

                    				current = grammarAccess.getCharacterTypeEnumAccess().getZOMBIEEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getCharacterTypeEnumAccess().getZOMBIEEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:1996:3: (enumLiteral_1= 'ghost' )
                    {
                    // InternalMazeDsl.g:1996:3: (enumLiteral_1= 'ghost' )
                    // InternalMazeDsl.g:1997:4: enumLiteral_1= 'ghost'
                    {
                    enumLiteral_1=(Token)match(input,72,FOLLOW_2); 

                    				current = grammarAccess.getCharacterTypeEnumAccess().getGHOSTEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getCharacterTypeEnumAccess().getGHOSTEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalMazeDsl.g:2004:3: (enumLiteral_2= 'pumpkinbomber' )
                    {
                    // InternalMazeDsl.g:2004:3: (enumLiteral_2= 'pumpkinbomber' )
                    // InternalMazeDsl.g:2005:4: enumLiteral_2= 'pumpkinbomber'
                    {
                    enumLiteral_2=(Token)match(input,73,FOLLOW_2); 

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
    // InternalMazeDsl.g:2015:1: ruleBehaviorTypeEnum returns [Enumerator current=null] : ( (enumLiteral_0= 'passive' ) | (enumLiteral_1= 'wander' ) | (enumLiteral_2= 'aggressive' ) | (enumLiteral_3= 'patrol' ) ) ;
    public final Enumerator ruleBehaviorTypeEnum() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;


        	enterRule();

        try {
            // InternalMazeDsl.g:2021:2: ( ( (enumLiteral_0= 'passive' ) | (enumLiteral_1= 'wander' ) | (enumLiteral_2= 'aggressive' ) | (enumLiteral_3= 'patrol' ) ) )
            // InternalMazeDsl.g:2022:2: ( (enumLiteral_0= 'passive' ) | (enumLiteral_1= 'wander' ) | (enumLiteral_2= 'aggressive' ) | (enumLiteral_3= 'patrol' ) )
            {
            // InternalMazeDsl.g:2022:2: ( (enumLiteral_0= 'passive' ) | (enumLiteral_1= 'wander' ) | (enumLiteral_2= 'aggressive' ) | (enumLiteral_3= 'patrol' ) )
            int alt47=4;
            switch ( input.LA(1) ) {
            case 74:
                {
                alt47=1;
                }
                break;
            case 75:
                {
                alt47=2;
                }
                break;
            case 76:
                {
                alt47=3;
                }
                break;
            case 32:
                {
                alt47=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 47, 0, input);

                throw nvae;
            }

            switch (alt47) {
                case 1 :
                    // InternalMazeDsl.g:2023:3: (enumLiteral_0= 'passive' )
                    {
                    // InternalMazeDsl.g:2023:3: (enumLiteral_0= 'passive' )
                    // InternalMazeDsl.g:2024:4: enumLiteral_0= 'passive'
                    {
                    enumLiteral_0=(Token)match(input,74,FOLLOW_2); 

                    				current = grammarAccess.getBehaviorTypeEnumAccess().getPASSIVEEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getBehaviorTypeEnumAccess().getPASSIVEEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:2031:3: (enumLiteral_1= 'wander' )
                    {
                    // InternalMazeDsl.g:2031:3: (enumLiteral_1= 'wander' )
                    // InternalMazeDsl.g:2032:4: enumLiteral_1= 'wander'
                    {
                    enumLiteral_1=(Token)match(input,75,FOLLOW_2); 

                    				current = grammarAccess.getBehaviorTypeEnumAccess().getWANDEREnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getBehaviorTypeEnumAccess().getWANDEREnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalMazeDsl.g:2039:3: (enumLiteral_2= 'aggressive' )
                    {
                    // InternalMazeDsl.g:2039:3: (enumLiteral_2= 'aggressive' )
                    // InternalMazeDsl.g:2040:4: enumLiteral_2= 'aggressive'
                    {
                    enumLiteral_2=(Token)match(input,76,FOLLOW_2); 

                    				current = grammarAccess.getBehaviorTypeEnumAccess().getAGGRESSIVEEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getBehaviorTypeEnumAccess().getAGGRESSIVEEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalMazeDsl.g:2047:3: (enumLiteral_3= 'patrol' )
                    {
                    // InternalMazeDsl.g:2047:3: (enumLiteral_3= 'patrol' )
                    // InternalMazeDsl.g:2048:4: enumLiteral_3= 'patrol'
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
    // InternalMazeDsl.g:2058:1: ruleProjectileTypeEnum returns [Enumerator current=null] : ( (enumLiteral_0= 'straight' ) | (enumLiteral_1= 'lob' ) | (enumLiteral_2= 'beam' ) ) ;
    public final Enumerator ruleProjectileTypeEnum() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalMazeDsl.g:2064:2: ( ( (enumLiteral_0= 'straight' ) | (enumLiteral_1= 'lob' ) | (enumLiteral_2= 'beam' ) ) )
            // InternalMazeDsl.g:2065:2: ( (enumLiteral_0= 'straight' ) | (enumLiteral_1= 'lob' ) | (enumLiteral_2= 'beam' ) )
            {
            // InternalMazeDsl.g:2065:2: ( (enumLiteral_0= 'straight' ) | (enumLiteral_1= 'lob' ) | (enumLiteral_2= 'beam' ) )
            int alt48=3;
            switch ( input.LA(1) ) {
            case 77:
                {
                alt48=1;
                }
                break;
            case 78:
                {
                alt48=2;
                }
                break;
            case 79:
                {
                alt48=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 48, 0, input);

                throw nvae;
            }

            switch (alt48) {
                case 1 :
                    // InternalMazeDsl.g:2066:3: (enumLiteral_0= 'straight' )
                    {
                    // InternalMazeDsl.g:2066:3: (enumLiteral_0= 'straight' )
                    // InternalMazeDsl.g:2067:4: enumLiteral_0= 'straight'
                    {
                    enumLiteral_0=(Token)match(input,77,FOLLOW_2); 

                    				current = grammarAccess.getProjectileTypeEnumAccess().getSTRAIGHTEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getProjectileTypeEnumAccess().getSTRAIGHTEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:2074:3: (enumLiteral_1= 'lob' )
                    {
                    // InternalMazeDsl.g:2074:3: (enumLiteral_1= 'lob' )
                    // InternalMazeDsl.g:2075:4: enumLiteral_1= 'lob'
                    {
                    enumLiteral_1=(Token)match(input,78,FOLLOW_2); 

                    				current = grammarAccess.getProjectileTypeEnumAccess().getLOBEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getProjectileTypeEnumAccess().getLOBEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalMazeDsl.g:2082:3: (enumLiteral_2= 'beam' )
                    {
                    // InternalMazeDsl.g:2082:3: (enumLiteral_2= 'beam' )
                    // InternalMazeDsl.g:2083:4: enumLiteral_2= 'beam'
                    {
                    enumLiteral_2=(Token)match(input,79,FOLLOW_2); 

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
    // InternalMazeDsl.g:2093:1: ruleLootItemTypeEnum returns [Enumerator current=null] : ( (enumLiteral_0= 'food' ) | (enumLiteral_1= 'bomb' ) | (enumLiteral_2= 'trap' ) | (enumLiteral_3= 'weapon' ) ) ;
    public final Enumerator ruleLootItemTypeEnum() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;


        	enterRule();

        try {
            // InternalMazeDsl.g:2099:2: ( ( (enumLiteral_0= 'food' ) | (enumLiteral_1= 'bomb' ) | (enumLiteral_2= 'trap' ) | (enumLiteral_3= 'weapon' ) ) )
            // InternalMazeDsl.g:2100:2: ( (enumLiteral_0= 'food' ) | (enumLiteral_1= 'bomb' ) | (enumLiteral_2= 'trap' ) | (enumLiteral_3= 'weapon' ) )
            {
            // InternalMazeDsl.g:2100:2: ( (enumLiteral_0= 'food' ) | (enumLiteral_1= 'bomb' ) | (enumLiteral_2= 'trap' ) | (enumLiteral_3= 'weapon' ) )
            int alt49=4;
            switch ( input.LA(1) ) {
            case 80:
                {
                alt49=1;
                }
                break;
            case 81:
                {
                alt49=2;
                }
                break;
            case 82:
                {
                alt49=3;
                }
                break;
            case 83:
                {
                alt49=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 49, 0, input);

                throw nvae;
            }

            switch (alt49) {
                case 1 :
                    // InternalMazeDsl.g:2101:3: (enumLiteral_0= 'food' )
                    {
                    // InternalMazeDsl.g:2101:3: (enumLiteral_0= 'food' )
                    // InternalMazeDsl.g:2102:4: enumLiteral_0= 'food'
                    {
                    enumLiteral_0=(Token)match(input,80,FOLLOW_2); 

                    				current = grammarAccess.getLootItemTypeEnumAccess().getFOODEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getLootItemTypeEnumAccess().getFOODEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:2109:3: (enumLiteral_1= 'bomb' )
                    {
                    // InternalMazeDsl.g:2109:3: (enumLiteral_1= 'bomb' )
                    // InternalMazeDsl.g:2110:4: enumLiteral_1= 'bomb'
                    {
                    enumLiteral_1=(Token)match(input,81,FOLLOW_2); 

                    				current = grammarAccess.getLootItemTypeEnumAccess().getBOMBEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getLootItemTypeEnumAccess().getBOMBEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalMazeDsl.g:2117:3: (enumLiteral_2= 'trap' )
                    {
                    // InternalMazeDsl.g:2117:3: (enumLiteral_2= 'trap' )
                    // InternalMazeDsl.g:2118:4: enumLiteral_2= 'trap'
                    {
                    enumLiteral_2=(Token)match(input,82,FOLLOW_2); 

                    				current = grammarAccess.getLootItemTypeEnumAccess().getTRAPEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getLootItemTypeEnumAccess().getTRAPEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalMazeDsl.g:2125:3: (enumLiteral_3= 'weapon' )
                    {
                    // InternalMazeDsl.g:2125:3: (enumLiteral_3= 'weapon' )
                    // InternalMazeDsl.g:2126:4: enumLiteral_3= 'weapon'
                    {
                    enumLiteral_3=(Token)match(input,83,FOLLOW_2); 

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

    // Delegated rules


 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x100000010100E000L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x1000000101002000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x1000000100002000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x1000000000002000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000070L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x00000000007A2000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000000782000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000000000040L,0x0000000000000002L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000000702000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000000602000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000000402000L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000380L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x00000247FC002000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x00000247F8002000L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x00000247F0002000L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x00000247E0002000L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x00000247C0002000L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000000000040000L,0x0000000000000008L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000024780002000L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000000100000000L,0x0000000000001C00L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0000024700002000L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0000000300002000L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0000000200002000L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0000003800002000L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0000003000002000L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x0000002000002000L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x0000018800002000L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x0000018000002000L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x0000010000002000L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x00007C0800002000L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x0000780800002000L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x0000700800002000L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x0000700000002000L});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0x0000600000002000L});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x0000000000000000L,0x000000000000E000L});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x0000400000002000L});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0x0011800000000000L});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x0011000000000000L});
    public static final BitSet FOLLOW_50 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_51 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_52 = new BitSet(new long[]{0x0040000000000000L});
    public static final BitSet FOLLOW_53 = new BitSet(new long[]{0x000C000000000000L});
    public static final BitSet FOLLOW_54 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_55 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_56 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_57 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_58 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_59 = new BitSet(new long[]{0x0400000000000002L});
    public static final BitSet FOLLOW_60 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_61 = new BitSet(new long[]{0x6000000000000000L});
    public static final BitSet FOLLOW_62 = new BitSet(new long[]{0x6000000000002000L});
    public static final BitSet FOLLOW_63 = new BitSet(new long[]{0x0000000000000000L,0x00000000000F0000L});
    public static final BitSet FOLLOW_64 = new BitSet(new long[]{0x8000000000000000L});
    public static final BitSet FOLLOW_65 = new BitSet(new long[]{0x0000000000002000L,0x0000000000000001L});
    public static final BitSet FOLLOW_66 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000004L});

}