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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_STRING", "RULE_INT", "RULE_ID", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'game'", "'{'", "'}'", "'import'", "'difficulty'", "'level'", "'instantDeath'", "'true'", "'speedMultiplier'", "'damageMultiplier'", "'maxThreat'", "'limit'", "'max'", "'opponent'", "'type'", "'displayName'", "'health'", "'speed'", "'threatLevel'", "'enabled'", "'behavior'", "'patrol'", "'loot'", "'zombie-stats'", "'attackDamage'", "'infectionLevel'", "'resurrectionTime'", "'ghost-stats'", "'visibilityLevel'", "'nonTangibilityEnergy'", "'ranged-stats'", "'attackRange'", "'attackCooldown'", "'projectileSpeed'", "'projectileType'", "'splashRadius'", "'visionRange'", "'path'", "'['", "','", "']'", "'zone'", "'topLeft'", "'('", "')'", "'width'", "'height'", "':'", "'ms'", "'loot-table'", "'capacity'", "'item'", "'value'", "'weight'", "'basedOn'", "'threatCapacity'", "'wallMaterials'", "'inheritWeapons'", "'cascadeDepth'", "'ring'", "'share'", "'materials'", "'sergeant'", "'sightRange'", "'rounds'", "'roundDamage'", "'roundInterval'", "'burstCooldown'", "'sprite'", "'fireSound'", "'drops'", "'postDistance'", "'sergeants'", "'mines'", "'count'", "'ringRadius'", "'secondRing'", "'triggerRadius'", "'pulse'", "'blastRadius'", "'blastDamage'", "'playerDamage'", "'holeRadius'", "'portalGhost'", "'portalGhosts'", "'guardTower'", "'wallsOver'", "'rpgInterval'", "'rpgSpeed'", "'rpgBlastRadius'", "'rpgPlayerDamage'", "'rpgWallDamage'", "'gatlingRounds'", "'gatlingInterval'", "'gatlingDamage'", "'deathBlast'", "'rpgLaunchSound'", "'rpgExplosionSound'", "'gatlingSound'", "'hitSound'", "'destroyedSound'", "'towers'", "'wallBuilder'", "'buildSeconds'", "'startRadius'", "'blockRadius'", "'spawnRadius'", "'material'", "'buildSound'", "'builders'", "'lava'", "'streams'", "'bridges'", "'bridgeLength'", "'meander'", "'wavelength'", "'flowSpeed'", "'glow'", "'crust'", "'deathSound'", "'lastLifeWeapon'", "'shots'", "'teleport'", "'cooldown'", "'banner'", "'bannerSeconds'", "'zapSound'", "'voiceSound'", "'-'", "'.'", "'false'", "'gatling'", "'easy'", "'normal'", "'hard'", "'zombie'", "'ghost'", "'pumpkinbomber'", "'passive'", "'wander'", "'aggressive'", "'straight'", "'lob'", "'beam'", "'food'", "'bomb'", "'trap'", "'weapon'"
    };
    public static final int T__144=144;
    public static final int T__143=143;
    public static final int T__146=146;
    public static final int T__50=50;
    public static final int T__145=145;
    public static final int T__140=140;
    public static final int T__142=142;
    public static final int T__141=141;
    public static final int T__59=59;
    public static final int T__55=55;
    public static final int T__56=56;
    public static final int T__57=57;
    public static final int T__58=58;
    public static final int T__51=51;
    public static final int T__137=137;
    public static final int T__52=52;
    public static final int T__136=136;
    public static final int T__53=53;
    public static final int T__139=139;
    public static final int T__54=54;
    public static final int T__138=138;
    public static final int T__133=133;
    public static final int T__132=132;
    public static final int T__60=60;
    public static final int T__135=135;
    public static final int T__61=61;
    public static final int T__134=134;
    public static final int RULE_ID=6;
    public static final int T__131=131;
    public static final int T__130=130;
    public static final int RULE_INT=5;
    public static final int T__66=66;
    public static final int RULE_ML_COMMENT=7;
    public static final int T__67=67;
    public static final int T__129=129;
    public static final int T__68=68;
    public static final int T__69=69;
    public static final int T__62=62;
    public static final int T__126=126;
    public static final int T__63=63;
    public static final int T__125=125;
    public static final int T__64=64;
    public static final int T__128=128;
    public static final int T__65=65;
    public static final int T__127=127;
    public static final int T__37=37;
    public static final int T__38=38;
    public static final int T__39=39;
    public static final int T__33=33;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int T__30=30;
    public static final int T__158=158;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int T__155=155;
    public static final int T__154=154;
    public static final int T__157=157;
    public static final int T__156=156;
    public static final int T__151=151;
    public static final int T__150=150;
    public static final int T__153=153;
    public static final int T__152=152;
    public static final int T__48=48;
    public static final int T__49=49;
    public static final int T__44=44;
    public static final int T__45=45;
    public static final int T__46=46;
    public static final int T__47=47;
    public static final int T__40=40;
    public static final int T__148=148;
    public static final int T__41=41;
    public static final int T__147=147;
    public static final int T__42=42;
    public static final int T__43=43;
    public static final int T__149=149;
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
    public static final int T__122=122;
    public static final int T__70=70;
    public static final int T__121=121;
    public static final int T__71=71;
    public static final int T__124=124;
    public static final int T__72=72;
    public static final int T__123=123;
    public static final int T__120=120;
    public static final int RULE_STRING=4;
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
    // InternalMazeDsl.g:72:1: ruleGameConfiguration returns [EObject current=null] : (otherlv_0= 'game' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' ( (lv_imports_3_0= ruleImport ) )* ( (lv_difficulty_4_0= ruleDifficultyConfig ) )? ( (lv_opponents_5_0= ruleOpponentConfig ) )* ( (lv_patrols_6_0= rulePatrolConfig ) )* ( (lv_lootTables_7_0= ruleLootTableConfig ) )* ( (lv_levels_8_0= ruleLevelConfig ) )* otherlv_9= '}' ) ;
    public final EObject ruleGameConfiguration() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_9=null;
        AntlrDatatypeRuleToken lv_name_1_0 = null;

        EObject lv_imports_3_0 = null;

        EObject lv_difficulty_4_0 = null;

        EObject lv_opponents_5_0 = null;

        EObject lv_patrols_6_0 = null;

        EObject lv_lootTables_7_0 = null;

        EObject lv_levels_8_0 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:78:2: ( (otherlv_0= 'game' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' ( (lv_imports_3_0= ruleImport ) )* ( (lv_difficulty_4_0= ruleDifficultyConfig ) )? ( (lv_opponents_5_0= ruleOpponentConfig ) )* ( (lv_patrols_6_0= rulePatrolConfig ) )* ( (lv_lootTables_7_0= ruleLootTableConfig ) )* ( (lv_levels_8_0= ruleLevelConfig ) )* otherlv_9= '}' ) )
            // InternalMazeDsl.g:79:2: (otherlv_0= 'game' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' ( (lv_imports_3_0= ruleImport ) )* ( (lv_difficulty_4_0= ruleDifficultyConfig ) )? ( (lv_opponents_5_0= ruleOpponentConfig ) )* ( (lv_patrols_6_0= rulePatrolConfig ) )* ( (lv_lootTables_7_0= ruleLootTableConfig ) )* ( (lv_levels_8_0= ruleLevelConfig ) )* otherlv_9= '}' )
            {
            // InternalMazeDsl.g:79:2: (otherlv_0= 'game' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' ( (lv_imports_3_0= ruleImport ) )* ( (lv_difficulty_4_0= ruleDifficultyConfig ) )? ( (lv_opponents_5_0= ruleOpponentConfig ) )* ( (lv_patrols_6_0= rulePatrolConfig ) )* ( (lv_lootTables_7_0= ruleLootTableConfig ) )* ( (lv_levels_8_0= ruleLevelConfig ) )* otherlv_9= '}' )
            // InternalMazeDsl.g:80:3: otherlv_0= 'game' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' ( (lv_imports_3_0= ruleImport ) )* ( (lv_difficulty_4_0= ruleDifficultyConfig ) )? ( (lv_opponents_5_0= ruleOpponentConfig ) )* ( (lv_patrols_6_0= rulePatrolConfig ) )* ( (lv_lootTables_7_0= ruleLootTableConfig ) )* ( (lv_levels_8_0= ruleLevelConfig ) )* otherlv_9= '}'
            {
            otherlv_0=(Token)match(input,11,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getGameConfigurationAccess().getGameKeyword_0());
            		
            // InternalMazeDsl.g:84:3: ( (lv_name_1_0= ruleValidID ) )
            // InternalMazeDsl.g:85:4: (lv_name_1_0= ruleValidID )
            {
            // InternalMazeDsl.g:85:4: (lv_name_1_0= ruleValidID )
            // InternalMazeDsl.g:86:5: lv_name_1_0= ruleValidID
            {

            					newCompositeNode(grammarAccess.getGameConfigurationAccess().getNameValidIDParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_4);
            lv_name_1_0=ruleValidID();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getGameConfigurationRule());
            					}
            					set(
            						current,
            						"name",
            						lv_name_1_0,
            						"main.game.maze.dsl.MazeDsl.ValidID");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_2=(Token)match(input,12,FOLLOW_5); 

            			newLeafNode(otherlv_2, grammarAccess.getGameConfigurationAccess().getLeftCurlyBracketKeyword_2());
            		
            // InternalMazeDsl.g:107:3: ( (lv_imports_3_0= ruleImport ) )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==14) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalMazeDsl.g:108:4: (lv_imports_3_0= ruleImport )
            	    {
            	    // InternalMazeDsl.g:108:4: (lv_imports_3_0= ruleImport )
            	    // InternalMazeDsl.g:109:5: lv_imports_3_0= ruleImport
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

            // InternalMazeDsl.g:126:3: ( (lv_difficulty_4_0= ruleDifficultyConfig ) )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==15) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // InternalMazeDsl.g:127:4: (lv_difficulty_4_0= ruleDifficultyConfig )
                    {
                    // InternalMazeDsl.g:127:4: (lv_difficulty_4_0= ruleDifficultyConfig )
                    // InternalMazeDsl.g:128:5: lv_difficulty_4_0= ruleDifficultyConfig
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

            // InternalMazeDsl.g:145:3: ( (lv_opponents_5_0= ruleOpponentConfig ) )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==24) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // InternalMazeDsl.g:146:4: (lv_opponents_5_0= ruleOpponentConfig )
            	    {
            	    // InternalMazeDsl.g:146:4: (lv_opponents_5_0= ruleOpponentConfig )
            	    // InternalMazeDsl.g:147:5: lv_opponents_5_0= ruleOpponentConfig
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

            // InternalMazeDsl.g:164:3: ( (lv_patrols_6_0= rulePatrolConfig ) )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==32) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // InternalMazeDsl.g:165:4: (lv_patrols_6_0= rulePatrolConfig )
            	    {
            	    // InternalMazeDsl.g:165:4: (lv_patrols_6_0= rulePatrolConfig )
            	    // InternalMazeDsl.g:166:5: lv_patrols_6_0= rulePatrolConfig
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

            // InternalMazeDsl.g:183:3: ( (lv_lootTables_7_0= ruleLootTableConfig ) )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==60) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // InternalMazeDsl.g:184:4: (lv_lootTables_7_0= ruleLootTableConfig )
            	    {
            	    // InternalMazeDsl.g:184:4: (lv_lootTables_7_0= ruleLootTableConfig )
            	    // InternalMazeDsl.g:185:5: lv_lootTables_7_0= ruleLootTableConfig
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

            // InternalMazeDsl.g:202:3: ( (lv_levels_8_0= ruleLevelConfig ) )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==16) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // InternalMazeDsl.g:203:4: (lv_levels_8_0= ruleLevelConfig )
            	    {
            	    // InternalMazeDsl.g:203:4: (lv_levels_8_0= ruleLevelConfig )
            	    // InternalMazeDsl.g:204:5: lv_levels_8_0= ruleLevelConfig
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
    // InternalMazeDsl.g:229:1: entryRuleImport returns [EObject current=null] : iv_ruleImport= ruleImport EOF ;
    public final EObject entryRuleImport() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleImport = null;


        try {
            // InternalMazeDsl.g:229:47: (iv_ruleImport= ruleImport EOF )
            // InternalMazeDsl.g:230:2: iv_ruleImport= ruleImport EOF
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
    // InternalMazeDsl.g:236:1: ruleImport returns [EObject current=null] : (otherlv_0= 'import' ( (lv_importURI_1_0= RULE_STRING ) ) ) ;
    public final EObject ruleImport() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_importURI_1_0=null;


        	enterRule();

        try {
            // InternalMazeDsl.g:242:2: ( (otherlv_0= 'import' ( (lv_importURI_1_0= RULE_STRING ) ) ) )
            // InternalMazeDsl.g:243:2: (otherlv_0= 'import' ( (lv_importURI_1_0= RULE_STRING ) ) )
            {
            // InternalMazeDsl.g:243:2: (otherlv_0= 'import' ( (lv_importURI_1_0= RULE_STRING ) ) )
            // InternalMazeDsl.g:244:3: otherlv_0= 'import' ( (lv_importURI_1_0= RULE_STRING ) )
            {
            otherlv_0=(Token)match(input,14,FOLLOW_10); 

            			newLeafNode(otherlv_0, grammarAccess.getImportAccess().getImportKeyword_0());
            		
            // InternalMazeDsl.g:248:3: ( (lv_importURI_1_0= RULE_STRING ) )
            // InternalMazeDsl.g:249:4: (lv_importURI_1_0= RULE_STRING )
            {
            // InternalMazeDsl.g:249:4: (lv_importURI_1_0= RULE_STRING )
            // InternalMazeDsl.g:250:5: lv_importURI_1_0= RULE_STRING
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
    // InternalMazeDsl.g:270:1: entryRuleDifficultyConfig returns [EObject current=null] : iv_ruleDifficultyConfig= ruleDifficultyConfig EOF ;
    public final EObject entryRuleDifficultyConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDifficultyConfig = null;


        try {
            // InternalMazeDsl.g:270:57: (iv_ruleDifficultyConfig= ruleDifficultyConfig EOF )
            // InternalMazeDsl.g:271:2: iv_ruleDifficultyConfig= ruleDifficultyConfig EOF
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
    // InternalMazeDsl.g:277:1: ruleDifficultyConfig returns [EObject current=null] : (otherlv_0= 'difficulty' otherlv_1= '{' otherlv_2= 'level' ( (lv_level_3_0= ruleDifficultyLevel ) ) (otherlv_4= 'instantDeath' ( (lv_instantDeath_5_0= 'true' ) ) )? (otherlv_6= 'speedMultiplier' ( (lv_speedMultiplier_7_0= ruleDOUBLE ) ) )? (otherlv_8= 'damageMultiplier' ( (lv_damageMultiplier_9_0= ruleDOUBLE ) ) )? (otherlv_10= 'maxThreat' ( (lv_maxThreat_11_0= ruleSIGNED_INT ) ) )? ( (lv_enemyLimits_12_0= ruleEnemyLimit ) )* otherlv_13= '}' ) ;
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
            // InternalMazeDsl.g:283:2: ( (otherlv_0= 'difficulty' otherlv_1= '{' otherlv_2= 'level' ( (lv_level_3_0= ruleDifficultyLevel ) ) (otherlv_4= 'instantDeath' ( (lv_instantDeath_5_0= 'true' ) ) )? (otherlv_6= 'speedMultiplier' ( (lv_speedMultiplier_7_0= ruleDOUBLE ) ) )? (otherlv_8= 'damageMultiplier' ( (lv_damageMultiplier_9_0= ruleDOUBLE ) ) )? (otherlv_10= 'maxThreat' ( (lv_maxThreat_11_0= ruleSIGNED_INT ) ) )? ( (lv_enemyLimits_12_0= ruleEnemyLimit ) )* otherlv_13= '}' ) )
            // InternalMazeDsl.g:284:2: (otherlv_0= 'difficulty' otherlv_1= '{' otherlv_2= 'level' ( (lv_level_3_0= ruleDifficultyLevel ) ) (otherlv_4= 'instantDeath' ( (lv_instantDeath_5_0= 'true' ) ) )? (otherlv_6= 'speedMultiplier' ( (lv_speedMultiplier_7_0= ruleDOUBLE ) ) )? (otherlv_8= 'damageMultiplier' ( (lv_damageMultiplier_9_0= ruleDOUBLE ) ) )? (otherlv_10= 'maxThreat' ( (lv_maxThreat_11_0= ruleSIGNED_INT ) ) )? ( (lv_enemyLimits_12_0= ruleEnemyLimit ) )* otherlv_13= '}' )
            {
            // InternalMazeDsl.g:284:2: (otherlv_0= 'difficulty' otherlv_1= '{' otherlv_2= 'level' ( (lv_level_3_0= ruleDifficultyLevel ) ) (otherlv_4= 'instantDeath' ( (lv_instantDeath_5_0= 'true' ) ) )? (otherlv_6= 'speedMultiplier' ( (lv_speedMultiplier_7_0= ruleDOUBLE ) ) )? (otherlv_8= 'damageMultiplier' ( (lv_damageMultiplier_9_0= ruleDOUBLE ) ) )? (otherlv_10= 'maxThreat' ( (lv_maxThreat_11_0= ruleSIGNED_INT ) ) )? ( (lv_enemyLimits_12_0= ruleEnemyLimit ) )* otherlv_13= '}' )
            // InternalMazeDsl.g:285:3: otherlv_0= 'difficulty' otherlv_1= '{' otherlv_2= 'level' ( (lv_level_3_0= ruleDifficultyLevel ) ) (otherlv_4= 'instantDeath' ( (lv_instantDeath_5_0= 'true' ) ) )? (otherlv_6= 'speedMultiplier' ( (lv_speedMultiplier_7_0= ruleDOUBLE ) ) )? (otherlv_8= 'damageMultiplier' ( (lv_damageMultiplier_9_0= ruleDOUBLE ) ) )? (otherlv_10= 'maxThreat' ( (lv_maxThreat_11_0= ruleSIGNED_INT ) ) )? ( (lv_enemyLimits_12_0= ruleEnemyLimit ) )* otherlv_13= '}'
            {
            otherlv_0=(Token)match(input,15,FOLLOW_4); 

            			newLeafNode(otherlv_0, grammarAccess.getDifficultyConfigAccess().getDifficultyKeyword_0());
            		
            otherlv_1=(Token)match(input,12,FOLLOW_11); 

            			newLeafNode(otherlv_1, grammarAccess.getDifficultyConfigAccess().getLeftCurlyBracketKeyword_1());
            		
            otherlv_2=(Token)match(input,16,FOLLOW_12); 

            			newLeafNode(otherlv_2, grammarAccess.getDifficultyConfigAccess().getLevelKeyword_2());
            		
            // InternalMazeDsl.g:297:3: ( (lv_level_3_0= ruleDifficultyLevel ) )
            // InternalMazeDsl.g:298:4: (lv_level_3_0= ruleDifficultyLevel )
            {
            // InternalMazeDsl.g:298:4: (lv_level_3_0= ruleDifficultyLevel )
            // InternalMazeDsl.g:299:5: lv_level_3_0= ruleDifficultyLevel
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

            // InternalMazeDsl.g:316:3: (otherlv_4= 'instantDeath' ( (lv_instantDeath_5_0= 'true' ) ) )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==17) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // InternalMazeDsl.g:317:4: otherlv_4= 'instantDeath' ( (lv_instantDeath_5_0= 'true' ) )
                    {
                    otherlv_4=(Token)match(input,17,FOLLOW_14); 

                    				newLeafNode(otherlv_4, grammarAccess.getDifficultyConfigAccess().getInstantDeathKeyword_4_0());
                    			
                    // InternalMazeDsl.g:321:4: ( (lv_instantDeath_5_0= 'true' ) )
                    // InternalMazeDsl.g:322:5: (lv_instantDeath_5_0= 'true' )
                    {
                    // InternalMazeDsl.g:322:5: (lv_instantDeath_5_0= 'true' )
                    // InternalMazeDsl.g:323:6: lv_instantDeath_5_0= 'true'
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

            // InternalMazeDsl.g:336:3: (otherlv_6= 'speedMultiplier' ( (lv_speedMultiplier_7_0= ruleDOUBLE ) ) )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==19) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // InternalMazeDsl.g:337:4: otherlv_6= 'speedMultiplier' ( (lv_speedMultiplier_7_0= ruleDOUBLE ) )
                    {
                    otherlv_6=(Token)match(input,19,FOLLOW_16); 

                    				newLeafNode(otherlv_6, grammarAccess.getDifficultyConfigAccess().getSpeedMultiplierKeyword_5_0());
                    			
                    // InternalMazeDsl.g:341:4: ( (lv_speedMultiplier_7_0= ruleDOUBLE ) )
                    // InternalMazeDsl.g:342:5: (lv_speedMultiplier_7_0= ruleDOUBLE )
                    {
                    // InternalMazeDsl.g:342:5: (lv_speedMultiplier_7_0= ruleDOUBLE )
                    // InternalMazeDsl.g:343:6: lv_speedMultiplier_7_0= ruleDOUBLE
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

            // InternalMazeDsl.g:361:3: (otherlv_8= 'damageMultiplier' ( (lv_damageMultiplier_9_0= ruleDOUBLE ) ) )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==20) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // InternalMazeDsl.g:362:4: otherlv_8= 'damageMultiplier' ( (lv_damageMultiplier_9_0= ruleDOUBLE ) )
                    {
                    otherlv_8=(Token)match(input,20,FOLLOW_16); 

                    				newLeafNode(otherlv_8, grammarAccess.getDifficultyConfigAccess().getDamageMultiplierKeyword_6_0());
                    			
                    // InternalMazeDsl.g:366:4: ( (lv_damageMultiplier_9_0= ruleDOUBLE ) )
                    // InternalMazeDsl.g:367:5: (lv_damageMultiplier_9_0= ruleDOUBLE )
                    {
                    // InternalMazeDsl.g:367:5: (lv_damageMultiplier_9_0= ruleDOUBLE )
                    // InternalMazeDsl.g:368:6: lv_damageMultiplier_9_0= ruleDOUBLE
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

            // InternalMazeDsl.g:386:3: (otherlv_10= 'maxThreat' ( (lv_maxThreat_11_0= ruleSIGNED_INT ) ) )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==21) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // InternalMazeDsl.g:387:4: otherlv_10= 'maxThreat' ( (lv_maxThreat_11_0= ruleSIGNED_INT ) )
                    {
                    otherlv_10=(Token)match(input,21,FOLLOW_16); 

                    				newLeafNode(otherlv_10, grammarAccess.getDifficultyConfigAccess().getMaxThreatKeyword_7_0());
                    			
                    // InternalMazeDsl.g:391:4: ( (lv_maxThreat_11_0= ruleSIGNED_INT ) )
                    // InternalMazeDsl.g:392:5: (lv_maxThreat_11_0= ruleSIGNED_INT )
                    {
                    // InternalMazeDsl.g:392:5: (lv_maxThreat_11_0= ruleSIGNED_INT )
                    // InternalMazeDsl.g:393:6: lv_maxThreat_11_0= ruleSIGNED_INT
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

            // InternalMazeDsl.g:411:3: ( (lv_enemyLimits_12_0= ruleEnemyLimit ) )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==22) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // InternalMazeDsl.g:412:4: (lv_enemyLimits_12_0= ruleEnemyLimit )
            	    {
            	    // InternalMazeDsl.g:412:4: (lv_enemyLimits_12_0= ruleEnemyLimit )
            	    // InternalMazeDsl.g:413:5: lv_enemyLimits_12_0= ruleEnemyLimit
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
    // InternalMazeDsl.g:438:1: entryRuleEnemyLimit returns [EObject current=null] : iv_ruleEnemyLimit= ruleEnemyLimit EOF ;
    public final EObject entryRuleEnemyLimit() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEnemyLimit = null;


        try {
            // InternalMazeDsl.g:438:51: (iv_ruleEnemyLimit= ruleEnemyLimit EOF )
            // InternalMazeDsl.g:439:2: iv_ruleEnemyLimit= ruleEnemyLimit EOF
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
    // InternalMazeDsl.g:445:1: ruleEnemyLimit returns [EObject current=null] : (otherlv_0= 'limit' ( (lv_type_1_0= ruleEnemyType ) ) otherlv_2= 'max' ( (lv_maxCount_3_0= ruleSIGNED_INT ) ) ) ;
    public final EObject ruleEnemyLimit() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Enumerator lv_type_1_0 = null;

        AntlrDatatypeRuleToken lv_maxCount_3_0 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:451:2: ( (otherlv_0= 'limit' ( (lv_type_1_0= ruleEnemyType ) ) otherlv_2= 'max' ( (lv_maxCount_3_0= ruleSIGNED_INT ) ) ) )
            // InternalMazeDsl.g:452:2: (otherlv_0= 'limit' ( (lv_type_1_0= ruleEnemyType ) ) otherlv_2= 'max' ( (lv_maxCount_3_0= ruleSIGNED_INT ) ) )
            {
            // InternalMazeDsl.g:452:2: (otherlv_0= 'limit' ( (lv_type_1_0= ruleEnemyType ) ) otherlv_2= 'max' ( (lv_maxCount_3_0= ruleSIGNED_INT ) ) )
            // InternalMazeDsl.g:453:3: otherlv_0= 'limit' ( (lv_type_1_0= ruleEnemyType ) ) otherlv_2= 'max' ( (lv_maxCount_3_0= ruleSIGNED_INT ) )
            {
            otherlv_0=(Token)match(input,22,FOLLOW_20); 

            			newLeafNode(otherlv_0, grammarAccess.getEnemyLimitAccess().getLimitKeyword_0());
            		
            // InternalMazeDsl.g:457:3: ( (lv_type_1_0= ruleEnemyType ) )
            // InternalMazeDsl.g:458:4: (lv_type_1_0= ruleEnemyType )
            {
            // InternalMazeDsl.g:458:4: (lv_type_1_0= ruleEnemyType )
            // InternalMazeDsl.g:459:5: lv_type_1_0= ruleEnemyType
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
            		
            // InternalMazeDsl.g:480:3: ( (lv_maxCount_3_0= ruleSIGNED_INT ) )
            // InternalMazeDsl.g:481:4: (lv_maxCount_3_0= ruleSIGNED_INT )
            {
            // InternalMazeDsl.g:481:4: (lv_maxCount_3_0= ruleSIGNED_INT )
            // InternalMazeDsl.g:482:5: lv_maxCount_3_0= ruleSIGNED_INT
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
    // InternalMazeDsl.g:503:1: entryRuleOpponentConfig returns [EObject current=null] : iv_ruleOpponentConfig= ruleOpponentConfig EOF ;
    public final EObject entryRuleOpponentConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOpponentConfig = null;


        try {
            // InternalMazeDsl.g:503:55: (iv_ruleOpponentConfig= ruleOpponentConfig EOF )
            // InternalMazeDsl.g:504:2: iv_ruleOpponentConfig= ruleOpponentConfig EOF
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
    // InternalMazeDsl.g:510:1: ruleOpponentConfig returns [EObject current=null] : (otherlv_0= 'opponent' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' otherlv_3= 'type' ( (lv_type_4_0= ruleCharacterTypeEnum ) ) (otherlv_5= 'displayName' ( (lv_displayName_6_0= RULE_STRING ) ) )? (otherlv_7= 'health' ( (lv_health_8_0= ruleSIGNED_INT ) ) )? (otherlv_9= 'speed' ( (lv_speed_10_0= ruleDOUBLE ) ) )? (otherlv_11= 'threatLevel' ( (lv_threatLevel_12_0= ruleDOUBLE ) ) )? (otherlv_13= 'enabled' ( (lv_enabled_14_0= ruleBOOLEAN ) ) )? (otherlv_15= 'behavior' ( (lv_behavior_16_0= ruleBehaviorTypeEnum ) ) )? ( (lv_characterSpecifics_17_0= ruleCharacterSpecifics ) )? (otherlv_18= 'patrol' ( ( ruleValidID ) ) )? (otherlv_20= 'loot' ( ( ruleValidID ) ) )? otherlv_22= '}' ) ;
    public final EObject ruleOpponentConfig() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
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
        Token otherlv_20=null;
        Token otherlv_22=null;
        AntlrDatatypeRuleToken lv_name_1_0 = null;

        Enumerator lv_type_4_0 = null;

        AntlrDatatypeRuleToken lv_health_8_0 = null;

        AntlrDatatypeRuleToken lv_speed_10_0 = null;

        AntlrDatatypeRuleToken lv_threatLevel_12_0 = null;

        AntlrDatatypeRuleToken lv_enabled_14_0 = null;

        Enumerator lv_behavior_16_0 = null;

        EObject lv_characterSpecifics_17_0 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:516:2: ( (otherlv_0= 'opponent' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' otherlv_3= 'type' ( (lv_type_4_0= ruleCharacterTypeEnum ) ) (otherlv_5= 'displayName' ( (lv_displayName_6_0= RULE_STRING ) ) )? (otherlv_7= 'health' ( (lv_health_8_0= ruleSIGNED_INT ) ) )? (otherlv_9= 'speed' ( (lv_speed_10_0= ruleDOUBLE ) ) )? (otherlv_11= 'threatLevel' ( (lv_threatLevel_12_0= ruleDOUBLE ) ) )? (otherlv_13= 'enabled' ( (lv_enabled_14_0= ruleBOOLEAN ) ) )? (otherlv_15= 'behavior' ( (lv_behavior_16_0= ruleBehaviorTypeEnum ) ) )? ( (lv_characterSpecifics_17_0= ruleCharacterSpecifics ) )? (otherlv_18= 'patrol' ( ( ruleValidID ) ) )? (otherlv_20= 'loot' ( ( ruleValidID ) ) )? otherlv_22= '}' ) )
            // InternalMazeDsl.g:517:2: (otherlv_0= 'opponent' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' otherlv_3= 'type' ( (lv_type_4_0= ruleCharacterTypeEnum ) ) (otherlv_5= 'displayName' ( (lv_displayName_6_0= RULE_STRING ) ) )? (otherlv_7= 'health' ( (lv_health_8_0= ruleSIGNED_INT ) ) )? (otherlv_9= 'speed' ( (lv_speed_10_0= ruleDOUBLE ) ) )? (otherlv_11= 'threatLevel' ( (lv_threatLevel_12_0= ruleDOUBLE ) ) )? (otherlv_13= 'enabled' ( (lv_enabled_14_0= ruleBOOLEAN ) ) )? (otherlv_15= 'behavior' ( (lv_behavior_16_0= ruleBehaviorTypeEnum ) ) )? ( (lv_characterSpecifics_17_0= ruleCharacterSpecifics ) )? (otherlv_18= 'patrol' ( ( ruleValidID ) ) )? (otherlv_20= 'loot' ( ( ruleValidID ) ) )? otherlv_22= '}' )
            {
            // InternalMazeDsl.g:517:2: (otherlv_0= 'opponent' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' otherlv_3= 'type' ( (lv_type_4_0= ruleCharacterTypeEnum ) ) (otherlv_5= 'displayName' ( (lv_displayName_6_0= RULE_STRING ) ) )? (otherlv_7= 'health' ( (lv_health_8_0= ruleSIGNED_INT ) ) )? (otherlv_9= 'speed' ( (lv_speed_10_0= ruleDOUBLE ) ) )? (otherlv_11= 'threatLevel' ( (lv_threatLevel_12_0= ruleDOUBLE ) ) )? (otherlv_13= 'enabled' ( (lv_enabled_14_0= ruleBOOLEAN ) ) )? (otherlv_15= 'behavior' ( (lv_behavior_16_0= ruleBehaviorTypeEnum ) ) )? ( (lv_characterSpecifics_17_0= ruleCharacterSpecifics ) )? (otherlv_18= 'patrol' ( ( ruleValidID ) ) )? (otherlv_20= 'loot' ( ( ruleValidID ) ) )? otherlv_22= '}' )
            // InternalMazeDsl.g:518:3: otherlv_0= 'opponent' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' otherlv_3= 'type' ( (lv_type_4_0= ruleCharacterTypeEnum ) ) (otherlv_5= 'displayName' ( (lv_displayName_6_0= RULE_STRING ) ) )? (otherlv_7= 'health' ( (lv_health_8_0= ruleSIGNED_INT ) ) )? (otherlv_9= 'speed' ( (lv_speed_10_0= ruleDOUBLE ) ) )? (otherlv_11= 'threatLevel' ( (lv_threatLevel_12_0= ruleDOUBLE ) ) )? (otherlv_13= 'enabled' ( (lv_enabled_14_0= ruleBOOLEAN ) ) )? (otherlv_15= 'behavior' ( (lv_behavior_16_0= ruleBehaviorTypeEnum ) ) )? ( (lv_characterSpecifics_17_0= ruleCharacterSpecifics ) )? (otherlv_18= 'patrol' ( ( ruleValidID ) ) )? (otherlv_20= 'loot' ( ( ruleValidID ) ) )? otherlv_22= '}'
            {
            otherlv_0=(Token)match(input,24,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getOpponentConfigAccess().getOpponentKeyword_0());
            		
            // InternalMazeDsl.g:522:3: ( (lv_name_1_0= ruleValidID ) )
            // InternalMazeDsl.g:523:4: (lv_name_1_0= ruleValidID )
            {
            // InternalMazeDsl.g:523:4: (lv_name_1_0= ruleValidID )
            // InternalMazeDsl.g:524:5: lv_name_1_0= ruleValidID
            {

            					newCompositeNode(grammarAccess.getOpponentConfigAccess().getNameValidIDParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_4);
            lv_name_1_0=ruleValidID();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getOpponentConfigRule());
            					}
            					set(
            						current,
            						"name",
            						lv_name_1_0,
            						"main.game.maze.dsl.MazeDsl.ValidID");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_2=(Token)match(input,12,FOLLOW_22); 

            			newLeafNode(otherlv_2, grammarAccess.getOpponentConfigAccess().getLeftCurlyBracketKeyword_2());
            		
            otherlv_3=(Token)match(input,25,FOLLOW_20); 

            			newLeafNode(otherlv_3, grammarAccess.getOpponentConfigAccess().getTypeKeyword_3());
            		
            // InternalMazeDsl.g:549:3: ( (lv_type_4_0= ruleCharacterTypeEnum ) )
            // InternalMazeDsl.g:550:4: (lv_type_4_0= ruleCharacterTypeEnum )
            {
            // InternalMazeDsl.g:550:4: (lv_type_4_0= ruleCharacterTypeEnum )
            // InternalMazeDsl.g:551:5: lv_type_4_0= ruleCharacterTypeEnum
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

            // InternalMazeDsl.g:568:3: (otherlv_5= 'displayName' ( (lv_displayName_6_0= RULE_STRING ) ) )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==26) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // InternalMazeDsl.g:569:4: otherlv_5= 'displayName' ( (lv_displayName_6_0= RULE_STRING ) )
                    {
                    otherlv_5=(Token)match(input,26,FOLLOW_10); 

                    				newLeafNode(otherlv_5, grammarAccess.getOpponentConfigAccess().getDisplayNameKeyword_5_0());
                    			
                    // InternalMazeDsl.g:573:4: ( (lv_displayName_6_0= RULE_STRING ) )
                    // InternalMazeDsl.g:574:5: (lv_displayName_6_0= RULE_STRING )
                    {
                    // InternalMazeDsl.g:574:5: (lv_displayName_6_0= RULE_STRING )
                    // InternalMazeDsl.g:575:6: lv_displayName_6_0= RULE_STRING
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

            // InternalMazeDsl.g:592:3: (otherlv_7= 'health' ( (lv_health_8_0= ruleSIGNED_INT ) ) )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==27) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // InternalMazeDsl.g:593:4: otherlv_7= 'health' ( (lv_health_8_0= ruleSIGNED_INT ) )
                    {
                    otherlv_7=(Token)match(input,27,FOLLOW_16); 

                    				newLeafNode(otherlv_7, grammarAccess.getOpponentConfigAccess().getHealthKeyword_6_0());
                    			
                    // InternalMazeDsl.g:597:4: ( (lv_health_8_0= ruleSIGNED_INT ) )
                    // InternalMazeDsl.g:598:5: (lv_health_8_0= ruleSIGNED_INT )
                    {
                    // InternalMazeDsl.g:598:5: (lv_health_8_0= ruleSIGNED_INT )
                    // InternalMazeDsl.g:599:6: lv_health_8_0= ruleSIGNED_INT
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

            // InternalMazeDsl.g:617:3: (otherlv_9= 'speed' ( (lv_speed_10_0= ruleDOUBLE ) ) )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==28) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // InternalMazeDsl.g:618:4: otherlv_9= 'speed' ( (lv_speed_10_0= ruleDOUBLE ) )
                    {
                    otherlv_9=(Token)match(input,28,FOLLOW_16); 

                    				newLeafNode(otherlv_9, grammarAccess.getOpponentConfigAccess().getSpeedKeyword_7_0());
                    			
                    // InternalMazeDsl.g:622:4: ( (lv_speed_10_0= ruleDOUBLE ) )
                    // InternalMazeDsl.g:623:5: (lv_speed_10_0= ruleDOUBLE )
                    {
                    // InternalMazeDsl.g:623:5: (lv_speed_10_0= ruleDOUBLE )
                    // InternalMazeDsl.g:624:6: lv_speed_10_0= ruleDOUBLE
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

            // InternalMazeDsl.g:642:3: (otherlv_11= 'threatLevel' ( (lv_threatLevel_12_0= ruleDOUBLE ) ) )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==29) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // InternalMazeDsl.g:643:4: otherlv_11= 'threatLevel' ( (lv_threatLevel_12_0= ruleDOUBLE ) )
                    {
                    otherlv_11=(Token)match(input,29,FOLLOW_16); 

                    				newLeafNode(otherlv_11, grammarAccess.getOpponentConfigAccess().getThreatLevelKeyword_8_0());
                    			
                    // InternalMazeDsl.g:647:4: ( (lv_threatLevel_12_0= ruleDOUBLE ) )
                    // InternalMazeDsl.g:648:5: (lv_threatLevel_12_0= ruleDOUBLE )
                    {
                    // InternalMazeDsl.g:648:5: (lv_threatLevel_12_0= ruleDOUBLE )
                    // InternalMazeDsl.g:649:6: lv_threatLevel_12_0= ruleDOUBLE
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

            // InternalMazeDsl.g:667:3: (otherlv_13= 'enabled' ( (lv_enabled_14_0= ruleBOOLEAN ) ) )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==30) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // InternalMazeDsl.g:668:4: otherlv_13= 'enabled' ( (lv_enabled_14_0= ruleBOOLEAN ) )
                    {
                    otherlv_13=(Token)match(input,30,FOLLOW_28); 

                    				newLeafNode(otherlv_13, grammarAccess.getOpponentConfigAccess().getEnabledKeyword_9_0());
                    			
                    // InternalMazeDsl.g:672:4: ( (lv_enabled_14_0= ruleBOOLEAN ) )
                    // InternalMazeDsl.g:673:5: (lv_enabled_14_0= ruleBOOLEAN )
                    {
                    // InternalMazeDsl.g:673:5: (lv_enabled_14_0= ruleBOOLEAN )
                    // InternalMazeDsl.g:674:6: lv_enabled_14_0= ruleBOOLEAN
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

            // InternalMazeDsl.g:692:3: (otherlv_15= 'behavior' ( (lv_behavior_16_0= ruleBehaviorTypeEnum ) ) )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==31) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // InternalMazeDsl.g:693:4: otherlv_15= 'behavior' ( (lv_behavior_16_0= ruleBehaviorTypeEnum ) )
                    {
                    otherlv_15=(Token)match(input,31,FOLLOW_30); 

                    				newLeafNode(otherlv_15, grammarAccess.getOpponentConfigAccess().getBehaviorKeyword_10_0());
                    			
                    // InternalMazeDsl.g:697:4: ( (lv_behavior_16_0= ruleBehaviorTypeEnum ) )
                    // InternalMazeDsl.g:698:5: (lv_behavior_16_0= ruleBehaviorTypeEnum )
                    {
                    // InternalMazeDsl.g:698:5: (lv_behavior_16_0= ruleBehaviorTypeEnum )
                    // InternalMazeDsl.g:699:6: lv_behavior_16_0= ruleBehaviorTypeEnum
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

            // InternalMazeDsl.g:717:3: ( (lv_characterSpecifics_17_0= ruleCharacterSpecifics ) )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==34||LA18_0==38||LA18_0==41) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // InternalMazeDsl.g:718:4: (lv_characterSpecifics_17_0= ruleCharacterSpecifics )
                    {
                    // InternalMazeDsl.g:718:4: (lv_characterSpecifics_17_0= ruleCharacterSpecifics )
                    // InternalMazeDsl.g:719:5: lv_characterSpecifics_17_0= ruleCharacterSpecifics
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

            // InternalMazeDsl.g:736:3: (otherlv_18= 'patrol' ( ( ruleValidID ) ) )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==32) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // InternalMazeDsl.g:737:4: otherlv_18= 'patrol' ( ( ruleValidID ) )
                    {
                    otherlv_18=(Token)match(input,32,FOLLOW_3); 

                    				newLeafNode(otherlv_18, grammarAccess.getOpponentConfigAccess().getPatrolKeyword_12_0());
                    			
                    // InternalMazeDsl.g:741:4: ( ( ruleValidID ) )
                    // InternalMazeDsl.g:742:5: ( ruleValidID )
                    {
                    // InternalMazeDsl.g:742:5: ( ruleValidID )
                    // InternalMazeDsl.g:743:6: ruleValidID
                    {

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getOpponentConfigRule());
                    						}
                    					

                    						newCompositeNode(grammarAccess.getOpponentConfigAccess().getPatrolRefPatrolConfigCrossReference_12_1_0());
                    					
                    pushFollow(FOLLOW_33);
                    ruleValidID();

                    state._fsp--;


                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeDsl.g:758:3: (otherlv_20= 'loot' ( ( ruleValidID ) ) )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==33) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // InternalMazeDsl.g:759:4: otherlv_20= 'loot' ( ( ruleValidID ) )
                    {
                    otherlv_20=(Token)match(input,33,FOLLOW_3); 

                    				newLeafNode(otherlv_20, grammarAccess.getOpponentConfigAccess().getLootKeyword_13_0());
                    			
                    // InternalMazeDsl.g:763:4: ( ( ruleValidID ) )
                    // InternalMazeDsl.g:764:5: ( ruleValidID )
                    {
                    // InternalMazeDsl.g:764:5: ( ruleValidID )
                    // InternalMazeDsl.g:765:6: ruleValidID
                    {

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getOpponentConfigRule());
                    						}
                    					

                    						newCompositeNode(grammarAccess.getOpponentConfigAccess().getLootRefLootTableConfigCrossReference_13_1_0());
                    					
                    pushFollow(FOLLOW_34);
                    ruleValidID();

                    state._fsp--;


                    						afterParserOrEnumRuleCall();
                    					

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
    // InternalMazeDsl.g:788:1: entryRuleCharacterSpecifics returns [EObject current=null] : iv_ruleCharacterSpecifics= ruleCharacterSpecifics EOF ;
    public final EObject entryRuleCharacterSpecifics() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCharacterSpecifics = null;


        try {
            // InternalMazeDsl.g:788:59: (iv_ruleCharacterSpecifics= ruleCharacterSpecifics EOF )
            // InternalMazeDsl.g:789:2: iv_ruleCharacterSpecifics= ruleCharacterSpecifics EOF
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
    // InternalMazeDsl.g:795:1: ruleCharacterSpecifics returns [EObject current=null] : (this_ZombieSpecifics_0= ruleZombieSpecifics | this_GhostSpecifics_1= ruleGhostSpecifics | this_RangedSpecifics_2= ruleRangedSpecifics ) ;
    public final EObject ruleCharacterSpecifics() throws RecognitionException {
        EObject current = null;

        EObject this_ZombieSpecifics_0 = null;

        EObject this_GhostSpecifics_1 = null;

        EObject this_RangedSpecifics_2 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:801:2: ( (this_ZombieSpecifics_0= ruleZombieSpecifics | this_GhostSpecifics_1= ruleGhostSpecifics | this_RangedSpecifics_2= ruleRangedSpecifics ) )
            // InternalMazeDsl.g:802:2: (this_ZombieSpecifics_0= ruleZombieSpecifics | this_GhostSpecifics_1= ruleGhostSpecifics | this_RangedSpecifics_2= ruleRangedSpecifics )
            {
            // InternalMazeDsl.g:802:2: (this_ZombieSpecifics_0= ruleZombieSpecifics | this_GhostSpecifics_1= ruleGhostSpecifics | this_RangedSpecifics_2= ruleRangedSpecifics )
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
                    // InternalMazeDsl.g:803:3: this_ZombieSpecifics_0= ruleZombieSpecifics
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
                    // InternalMazeDsl.g:812:3: this_GhostSpecifics_1= ruleGhostSpecifics
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
                    // InternalMazeDsl.g:821:3: this_RangedSpecifics_2= ruleRangedSpecifics
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
    // InternalMazeDsl.g:833:1: entryRuleZombieSpecifics returns [EObject current=null] : iv_ruleZombieSpecifics= ruleZombieSpecifics EOF ;
    public final EObject entryRuleZombieSpecifics() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleZombieSpecifics = null;


        try {
            // InternalMazeDsl.g:833:56: (iv_ruleZombieSpecifics= ruleZombieSpecifics EOF )
            // InternalMazeDsl.g:834:2: iv_ruleZombieSpecifics= ruleZombieSpecifics EOF
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
    // InternalMazeDsl.g:840:1: ruleZombieSpecifics returns [EObject current=null] : ( () otherlv_1= 'zombie-stats' otherlv_2= '{' (otherlv_3= 'attackDamage' ( (lv_attackDamage_4_0= RULE_INT ) ) )? (otherlv_5= 'infectionLevel' ( (lv_infectionLevel_6_0= RULE_INT ) ) )? (otherlv_7= 'resurrectionTime' ( (lv_resurrectionTime_8_0= RULE_INT ) ) )? otherlv_9= '}' ) ;
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
            // InternalMazeDsl.g:846:2: ( ( () otherlv_1= 'zombie-stats' otherlv_2= '{' (otherlv_3= 'attackDamage' ( (lv_attackDamage_4_0= RULE_INT ) ) )? (otherlv_5= 'infectionLevel' ( (lv_infectionLevel_6_0= RULE_INT ) ) )? (otherlv_7= 'resurrectionTime' ( (lv_resurrectionTime_8_0= RULE_INT ) ) )? otherlv_9= '}' ) )
            // InternalMazeDsl.g:847:2: ( () otherlv_1= 'zombie-stats' otherlv_2= '{' (otherlv_3= 'attackDamage' ( (lv_attackDamage_4_0= RULE_INT ) ) )? (otherlv_5= 'infectionLevel' ( (lv_infectionLevel_6_0= RULE_INT ) ) )? (otherlv_7= 'resurrectionTime' ( (lv_resurrectionTime_8_0= RULE_INT ) ) )? otherlv_9= '}' )
            {
            // InternalMazeDsl.g:847:2: ( () otherlv_1= 'zombie-stats' otherlv_2= '{' (otherlv_3= 'attackDamage' ( (lv_attackDamage_4_0= RULE_INT ) ) )? (otherlv_5= 'infectionLevel' ( (lv_infectionLevel_6_0= RULE_INT ) ) )? (otherlv_7= 'resurrectionTime' ( (lv_resurrectionTime_8_0= RULE_INT ) ) )? otherlv_9= '}' )
            // InternalMazeDsl.g:848:3: () otherlv_1= 'zombie-stats' otherlv_2= '{' (otherlv_3= 'attackDamage' ( (lv_attackDamage_4_0= RULE_INT ) ) )? (otherlv_5= 'infectionLevel' ( (lv_infectionLevel_6_0= RULE_INT ) ) )? (otherlv_7= 'resurrectionTime' ( (lv_resurrectionTime_8_0= RULE_INT ) ) )? otherlv_9= '}'
            {
            // InternalMazeDsl.g:848:3: ()
            // InternalMazeDsl.g:849:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getZombieSpecificsAccess().getZombieSpecificsAction_0(),
            					current);
            			

            }

            otherlv_1=(Token)match(input,34,FOLLOW_4); 

            			newLeafNode(otherlv_1, grammarAccess.getZombieSpecificsAccess().getZombieStatsKeyword_1());
            		
            otherlv_2=(Token)match(input,12,FOLLOW_35); 

            			newLeafNode(otherlv_2, grammarAccess.getZombieSpecificsAccess().getLeftCurlyBracketKeyword_2());
            		
            // InternalMazeDsl.g:863:3: (otherlv_3= 'attackDamage' ( (lv_attackDamage_4_0= RULE_INT ) ) )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==35) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // InternalMazeDsl.g:864:4: otherlv_3= 'attackDamage' ( (lv_attackDamage_4_0= RULE_INT ) )
                    {
                    otherlv_3=(Token)match(input,35,FOLLOW_36); 

                    				newLeafNode(otherlv_3, grammarAccess.getZombieSpecificsAccess().getAttackDamageKeyword_3_0());
                    			
                    // InternalMazeDsl.g:868:4: ( (lv_attackDamage_4_0= RULE_INT ) )
                    // InternalMazeDsl.g:869:5: (lv_attackDamage_4_0= RULE_INT )
                    {
                    // InternalMazeDsl.g:869:5: (lv_attackDamage_4_0= RULE_INT )
                    // InternalMazeDsl.g:870:6: lv_attackDamage_4_0= RULE_INT
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

            // InternalMazeDsl.g:887:3: (otherlv_5= 'infectionLevel' ( (lv_infectionLevel_6_0= RULE_INT ) ) )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==36) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // InternalMazeDsl.g:888:4: otherlv_5= 'infectionLevel' ( (lv_infectionLevel_6_0= RULE_INT ) )
                    {
                    otherlv_5=(Token)match(input,36,FOLLOW_36); 

                    				newLeafNode(otherlv_5, grammarAccess.getZombieSpecificsAccess().getInfectionLevelKeyword_4_0());
                    			
                    // InternalMazeDsl.g:892:4: ( (lv_infectionLevel_6_0= RULE_INT ) )
                    // InternalMazeDsl.g:893:5: (lv_infectionLevel_6_0= RULE_INT )
                    {
                    // InternalMazeDsl.g:893:5: (lv_infectionLevel_6_0= RULE_INT )
                    // InternalMazeDsl.g:894:6: lv_infectionLevel_6_0= RULE_INT
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

            // InternalMazeDsl.g:911:3: (otherlv_7= 'resurrectionTime' ( (lv_resurrectionTime_8_0= RULE_INT ) ) )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==37) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // InternalMazeDsl.g:912:4: otherlv_7= 'resurrectionTime' ( (lv_resurrectionTime_8_0= RULE_INT ) )
                    {
                    otherlv_7=(Token)match(input,37,FOLLOW_36); 

                    				newLeafNode(otherlv_7, grammarAccess.getZombieSpecificsAccess().getResurrectionTimeKeyword_5_0());
                    			
                    // InternalMazeDsl.g:916:4: ( (lv_resurrectionTime_8_0= RULE_INT ) )
                    // InternalMazeDsl.g:917:5: (lv_resurrectionTime_8_0= RULE_INT )
                    {
                    // InternalMazeDsl.g:917:5: (lv_resurrectionTime_8_0= RULE_INT )
                    // InternalMazeDsl.g:918:6: lv_resurrectionTime_8_0= RULE_INT
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
    // InternalMazeDsl.g:943:1: entryRuleGhostSpecifics returns [EObject current=null] : iv_ruleGhostSpecifics= ruleGhostSpecifics EOF ;
    public final EObject entryRuleGhostSpecifics() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGhostSpecifics = null;


        try {
            // InternalMazeDsl.g:943:55: (iv_ruleGhostSpecifics= ruleGhostSpecifics EOF )
            // InternalMazeDsl.g:944:2: iv_ruleGhostSpecifics= ruleGhostSpecifics EOF
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
    // InternalMazeDsl.g:950:1: ruleGhostSpecifics returns [EObject current=null] : ( () otherlv_1= 'ghost-stats' otherlv_2= '{' (otherlv_3= 'attackDamage' ( (lv_attackDamage_4_0= RULE_INT ) ) )? (otherlv_5= 'visibilityLevel' ( (lv_visibilityLevel_6_0= RULE_INT ) ) )? (otherlv_7= 'nonTangibilityEnergy' ( (lv_nonTangibilityEnergy_8_0= ruleDOUBLE ) ) )? otherlv_9= '}' ) ;
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
            // InternalMazeDsl.g:956:2: ( ( () otherlv_1= 'ghost-stats' otherlv_2= '{' (otherlv_3= 'attackDamage' ( (lv_attackDamage_4_0= RULE_INT ) ) )? (otherlv_5= 'visibilityLevel' ( (lv_visibilityLevel_6_0= RULE_INT ) ) )? (otherlv_7= 'nonTangibilityEnergy' ( (lv_nonTangibilityEnergy_8_0= ruleDOUBLE ) ) )? otherlv_9= '}' ) )
            // InternalMazeDsl.g:957:2: ( () otherlv_1= 'ghost-stats' otherlv_2= '{' (otherlv_3= 'attackDamage' ( (lv_attackDamage_4_0= RULE_INT ) ) )? (otherlv_5= 'visibilityLevel' ( (lv_visibilityLevel_6_0= RULE_INT ) ) )? (otherlv_7= 'nonTangibilityEnergy' ( (lv_nonTangibilityEnergy_8_0= ruleDOUBLE ) ) )? otherlv_9= '}' )
            {
            // InternalMazeDsl.g:957:2: ( () otherlv_1= 'ghost-stats' otherlv_2= '{' (otherlv_3= 'attackDamage' ( (lv_attackDamage_4_0= RULE_INT ) ) )? (otherlv_5= 'visibilityLevel' ( (lv_visibilityLevel_6_0= RULE_INT ) ) )? (otherlv_7= 'nonTangibilityEnergy' ( (lv_nonTangibilityEnergy_8_0= ruleDOUBLE ) ) )? otherlv_9= '}' )
            // InternalMazeDsl.g:958:3: () otherlv_1= 'ghost-stats' otherlv_2= '{' (otherlv_3= 'attackDamage' ( (lv_attackDamage_4_0= RULE_INT ) ) )? (otherlv_5= 'visibilityLevel' ( (lv_visibilityLevel_6_0= RULE_INT ) ) )? (otherlv_7= 'nonTangibilityEnergy' ( (lv_nonTangibilityEnergy_8_0= ruleDOUBLE ) ) )? otherlv_9= '}'
            {
            // InternalMazeDsl.g:958:3: ()
            // InternalMazeDsl.g:959:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getGhostSpecificsAccess().getGhostSpecificsAction_0(),
            					current);
            			

            }

            otherlv_1=(Token)match(input,38,FOLLOW_4); 

            			newLeafNode(otherlv_1, grammarAccess.getGhostSpecificsAccess().getGhostStatsKeyword_1());
            		
            otherlv_2=(Token)match(input,12,FOLLOW_39); 

            			newLeafNode(otherlv_2, grammarAccess.getGhostSpecificsAccess().getLeftCurlyBracketKeyword_2());
            		
            // InternalMazeDsl.g:973:3: (otherlv_3= 'attackDamage' ( (lv_attackDamage_4_0= RULE_INT ) ) )?
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==35) ) {
                alt25=1;
            }
            switch (alt25) {
                case 1 :
                    // InternalMazeDsl.g:974:4: otherlv_3= 'attackDamage' ( (lv_attackDamage_4_0= RULE_INT ) )
                    {
                    otherlv_3=(Token)match(input,35,FOLLOW_36); 

                    				newLeafNode(otherlv_3, grammarAccess.getGhostSpecificsAccess().getAttackDamageKeyword_3_0());
                    			
                    // InternalMazeDsl.g:978:4: ( (lv_attackDamage_4_0= RULE_INT ) )
                    // InternalMazeDsl.g:979:5: (lv_attackDamage_4_0= RULE_INT )
                    {
                    // InternalMazeDsl.g:979:5: (lv_attackDamage_4_0= RULE_INT )
                    // InternalMazeDsl.g:980:6: lv_attackDamage_4_0= RULE_INT
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

            // InternalMazeDsl.g:997:3: (otherlv_5= 'visibilityLevel' ( (lv_visibilityLevel_6_0= RULE_INT ) ) )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==39) ) {
                alt26=1;
            }
            switch (alt26) {
                case 1 :
                    // InternalMazeDsl.g:998:4: otherlv_5= 'visibilityLevel' ( (lv_visibilityLevel_6_0= RULE_INT ) )
                    {
                    otherlv_5=(Token)match(input,39,FOLLOW_36); 

                    				newLeafNode(otherlv_5, grammarAccess.getGhostSpecificsAccess().getVisibilityLevelKeyword_4_0());
                    			
                    // InternalMazeDsl.g:1002:4: ( (lv_visibilityLevel_6_0= RULE_INT ) )
                    // InternalMazeDsl.g:1003:5: (lv_visibilityLevel_6_0= RULE_INT )
                    {
                    // InternalMazeDsl.g:1003:5: (lv_visibilityLevel_6_0= RULE_INT )
                    // InternalMazeDsl.g:1004:6: lv_visibilityLevel_6_0= RULE_INT
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

            // InternalMazeDsl.g:1021:3: (otherlv_7= 'nonTangibilityEnergy' ( (lv_nonTangibilityEnergy_8_0= ruleDOUBLE ) ) )?
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==40) ) {
                alt27=1;
            }
            switch (alt27) {
                case 1 :
                    // InternalMazeDsl.g:1022:4: otherlv_7= 'nonTangibilityEnergy' ( (lv_nonTangibilityEnergy_8_0= ruleDOUBLE ) )
                    {
                    otherlv_7=(Token)match(input,40,FOLLOW_16); 

                    				newLeafNode(otherlv_7, grammarAccess.getGhostSpecificsAccess().getNonTangibilityEnergyKeyword_5_0());
                    			
                    // InternalMazeDsl.g:1026:4: ( (lv_nonTangibilityEnergy_8_0= ruleDOUBLE ) )
                    // InternalMazeDsl.g:1027:5: (lv_nonTangibilityEnergy_8_0= ruleDOUBLE )
                    {
                    // InternalMazeDsl.g:1027:5: (lv_nonTangibilityEnergy_8_0= ruleDOUBLE )
                    // InternalMazeDsl.g:1028:6: lv_nonTangibilityEnergy_8_0= ruleDOUBLE
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
    // InternalMazeDsl.g:1054:1: entryRuleRangedSpecifics returns [EObject current=null] : iv_ruleRangedSpecifics= ruleRangedSpecifics EOF ;
    public final EObject entryRuleRangedSpecifics() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRangedSpecifics = null;


        try {
            // InternalMazeDsl.g:1054:56: (iv_ruleRangedSpecifics= ruleRangedSpecifics EOF )
            // InternalMazeDsl.g:1055:2: iv_ruleRangedSpecifics= ruleRangedSpecifics EOF
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
    // InternalMazeDsl.g:1061:1: ruleRangedSpecifics returns [EObject current=null] : ( () otherlv_1= 'ranged-stats' otherlv_2= '{' (otherlv_3= 'attackRange' ( (lv_attackRange_4_0= ruleDOUBLE ) ) )? (otherlv_5= 'attackCooldown' ( (lv_attackCooldown_6_0= RULE_INT ) ) )? (otherlv_7= 'attackDamage' ( (lv_attackDamage_8_0= RULE_INT ) ) )? (otherlv_9= 'projectileSpeed' ( (lv_projectileSpeed_10_0= ruleDOUBLE ) ) )? (otherlv_11= 'projectileType' ( (lv_projectileType_12_0= ruleProjectileTypeEnum ) ) )? (otherlv_13= 'splashRadius' ( (lv_splashRadius_14_0= ruleDOUBLE ) ) )? otherlv_15= '}' ) ;
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
            // InternalMazeDsl.g:1067:2: ( ( () otherlv_1= 'ranged-stats' otherlv_2= '{' (otherlv_3= 'attackRange' ( (lv_attackRange_4_0= ruleDOUBLE ) ) )? (otherlv_5= 'attackCooldown' ( (lv_attackCooldown_6_0= RULE_INT ) ) )? (otherlv_7= 'attackDamage' ( (lv_attackDamage_8_0= RULE_INT ) ) )? (otherlv_9= 'projectileSpeed' ( (lv_projectileSpeed_10_0= ruleDOUBLE ) ) )? (otherlv_11= 'projectileType' ( (lv_projectileType_12_0= ruleProjectileTypeEnum ) ) )? (otherlv_13= 'splashRadius' ( (lv_splashRadius_14_0= ruleDOUBLE ) ) )? otherlv_15= '}' ) )
            // InternalMazeDsl.g:1068:2: ( () otherlv_1= 'ranged-stats' otherlv_2= '{' (otherlv_3= 'attackRange' ( (lv_attackRange_4_0= ruleDOUBLE ) ) )? (otherlv_5= 'attackCooldown' ( (lv_attackCooldown_6_0= RULE_INT ) ) )? (otherlv_7= 'attackDamage' ( (lv_attackDamage_8_0= RULE_INT ) ) )? (otherlv_9= 'projectileSpeed' ( (lv_projectileSpeed_10_0= ruleDOUBLE ) ) )? (otherlv_11= 'projectileType' ( (lv_projectileType_12_0= ruleProjectileTypeEnum ) ) )? (otherlv_13= 'splashRadius' ( (lv_splashRadius_14_0= ruleDOUBLE ) ) )? otherlv_15= '}' )
            {
            // InternalMazeDsl.g:1068:2: ( () otherlv_1= 'ranged-stats' otherlv_2= '{' (otherlv_3= 'attackRange' ( (lv_attackRange_4_0= ruleDOUBLE ) ) )? (otherlv_5= 'attackCooldown' ( (lv_attackCooldown_6_0= RULE_INT ) ) )? (otherlv_7= 'attackDamage' ( (lv_attackDamage_8_0= RULE_INT ) ) )? (otherlv_9= 'projectileSpeed' ( (lv_projectileSpeed_10_0= ruleDOUBLE ) ) )? (otherlv_11= 'projectileType' ( (lv_projectileType_12_0= ruleProjectileTypeEnum ) ) )? (otherlv_13= 'splashRadius' ( (lv_splashRadius_14_0= ruleDOUBLE ) ) )? otherlv_15= '}' )
            // InternalMazeDsl.g:1069:3: () otherlv_1= 'ranged-stats' otherlv_2= '{' (otherlv_3= 'attackRange' ( (lv_attackRange_4_0= ruleDOUBLE ) ) )? (otherlv_5= 'attackCooldown' ( (lv_attackCooldown_6_0= RULE_INT ) ) )? (otherlv_7= 'attackDamage' ( (lv_attackDamage_8_0= RULE_INT ) ) )? (otherlv_9= 'projectileSpeed' ( (lv_projectileSpeed_10_0= ruleDOUBLE ) ) )? (otherlv_11= 'projectileType' ( (lv_projectileType_12_0= ruleProjectileTypeEnum ) ) )? (otherlv_13= 'splashRadius' ( (lv_splashRadius_14_0= ruleDOUBLE ) ) )? otherlv_15= '}'
            {
            // InternalMazeDsl.g:1069:3: ()
            // InternalMazeDsl.g:1070:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getRangedSpecificsAccess().getRangedSpecificsAction_0(),
            					current);
            			

            }

            otherlv_1=(Token)match(input,41,FOLLOW_4); 

            			newLeafNode(otherlv_1, grammarAccess.getRangedSpecificsAccess().getRangedStatsKeyword_1());
            		
            otherlv_2=(Token)match(input,12,FOLLOW_42); 

            			newLeafNode(otherlv_2, grammarAccess.getRangedSpecificsAccess().getLeftCurlyBracketKeyword_2());
            		
            // InternalMazeDsl.g:1084:3: (otherlv_3= 'attackRange' ( (lv_attackRange_4_0= ruleDOUBLE ) ) )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==42) ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // InternalMazeDsl.g:1085:4: otherlv_3= 'attackRange' ( (lv_attackRange_4_0= ruleDOUBLE ) )
                    {
                    otherlv_3=(Token)match(input,42,FOLLOW_16); 

                    				newLeafNode(otherlv_3, grammarAccess.getRangedSpecificsAccess().getAttackRangeKeyword_3_0());
                    			
                    // InternalMazeDsl.g:1089:4: ( (lv_attackRange_4_0= ruleDOUBLE ) )
                    // InternalMazeDsl.g:1090:5: (lv_attackRange_4_0= ruleDOUBLE )
                    {
                    // InternalMazeDsl.g:1090:5: (lv_attackRange_4_0= ruleDOUBLE )
                    // InternalMazeDsl.g:1091:6: lv_attackRange_4_0= ruleDOUBLE
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

            // InternalMazeDsl.g:1109:3: (otherlv_5= 'attackCooldown' ( (lv_attackCooldown_6_0= RULE_INT ) ) )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==43) ) {
                alt29=1;
            }
            switch (alt29) {
                case 1 :
                    // InternalMazeDsl.g:1110:4: otherlv_5= 'attackCooldown' ( (lv_attackCooldown_6_0= RULE_INT ) )
                    {
                    otherlv_5=(Token)match(input,43,FOLLOW_36); 

                    				newLeafNode(otherlv_5, grammarAccess.getRangedSpecificsAccess().getAttackCooldownKeyword_4_0());
                    			
                    // InternalMazeDsl.g:1114:4: ( (lv_attackCooldown_6_0= RULE_INT ) )
                    // InternalMazeDsl.g:1115:5: (lv_attackCooldown_6_0= RULE_INT )
                    {
                    // InternalMazeDsl.g:1115:5: (lv_attackCooldown_6_0= RULE_INT )
                    // InternalMazeDsl.g:1116:6: lv_attackCooldown_6_0= RULE_INT
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

            // InternalMazeDsl.g:1133:3: (otherlv_7= 'attackDamage' ( (lv_attackDamage_8_0= RULE_INT ) ) )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==35) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // InternalMazeDsl.g:1134:4: otherlv_7= 'attackDamage' ( (lv_attackDamage_8_0= RULE_INT ) )
                    {
                    otherlv_7=(Token)match(input,35,FOLLOW_36); 

                    				newLeafNode(otherlv_7, grammarAccess.getRangedSpecificsAccess().getAttackDamageKeyword_5_0());
                    			
                    // InternalMazeDsl.g:1138:4: ( (lv_attackDamage_8_0= RULE_INT ) )
                    // InternalMazeDsl.g:1139:5: (lv_attackDamage_8_0= RULE_INT )
                    {
                    // InternalMazeDsl.g:1139:5: (lv_attackDamage_8_0= RULE_INT )
                    // InternalMazeDsl.g:1140:6: lv_attackDamage_8_0= RULE_INT
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

            // InternalMazeDsl.g:1157:3: (otherlv_9= 'projectileSpeed' ( (lv_projectileSpeed_10_0= ruleDOUBLE ) ) )?
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==44) ) {
                alt31=1;
            }
            switch (alt31) {
                case 1 :
                    // InternalMazeDsl.g:1158:4: otherlv_9= 'projectileSpeed' ( (lv_projectileSpeed_10_0= ruleDOUBLE ) )
                    {
                    otherlv_9=(Token)match(input,44,FOLLOW_16); 

                    				newLeafNode(otherlv_9, grammarAccess.getRangedSpecificsAccess().getProjectileSpeedKeyword_6_0());
                    			
                    // InternalMazeDsl.g:1162:4: ( (lv_projectileSpeed_10_0= ruleDOUBLE ) )
                    // InternalMazeDsl.g:1163:5: (lv_projectileSpeed_10_0= ruleDOUBLE )
                    {
                    // InternalMazeDsl.g:1163:5: (lv_projectileSpeed_10_0= ruleDOUBLE )
                    // InternalMazeDsl.g:1164:6: lv_projectileSpeed_10_0= ruleDOUBLE
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

            // InternalMazeDsl.g:1182:3: (otherlv_11= 'projectileType' ( (lv_projectileType_12_0= ruleProjectileTypeEnum ) ) )?
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==45) ) {
                alt32=1;
            }
            switch (alt32) {
                case 1 :
                    // InternalMazeDsl.g:1183:4: otherlv_11= 'projectileType' ( (lv_projectileType_12_0= ruleProjectileTypeEnum ) )
                    {
                    otherlv_11=(Token)match(input,45,FOLLOW_47); 

                    				newLeafNode(otherlv_11, grammarAccess.getRangedSpecificsAccess().getProjectileTypeKeyword_7_0());
                    			
                    // InternalMazeDsl.g:1187:4: ( (lv_projectileType_12_0= ruleProjectileTypeEnum ) )
                    // InternalMazeDsl.g:1188:5: (lv_projectileType_12_0= ruleProjectileTypeEnum )
                    {
                    // InternalMazeDsl.g:1188:5: (lv_projectileType_12_0= ruleProjectileTypeEnum )
                    // InternalMazeDsl.g:1189:6: lv_projectileType_12_0= ruleProjectileTypeEnum
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

            // InternalMazeDsl.g:1207:3: (otherlv_13= 'splashRadius' ( (lv_splashRadius_14_0= ruleDOUBLE ) ) )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==46) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // InternalMazeDsl.g:1208:4: otherlv_13= 'splashRadius' ( (lv_splashRadius_14_0= ruleDOUBLE ) )
                    {
                    otherlv_13=(Token)match(input,46,FOLLOW_16); 

                    				newLeafNode(otherlv_13, grammarAccess.getRangedSpecificsAccess().getSplashRadiusKeyword_8_0());
                    			
                    // InternalMazeDsl.g:1212:4: ( (lv_splashRadius_14_0= ruleDOUBLE ) )
                    // InternalMazeDsl.g:1213:5: (lv_splashRadius_14_0= ruleDOUBLE )
                    {
                    // InternalMazeDsl.g:1213:5: (lv_splashRadius_14_0= ruleDOUBLE )
                    // InternalMazeDsl.g:1214:6: lv_splashRadius_14_0= ruleDOUBLE
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
    // InternalMazeDsl.g:1240:1: entryRulePatrolConfig returns [EObject current=null] : iv_rulePatrolConfig= rulePatrolConfig EOF ;
    public final EObject entryRulePatrolConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePatrolConfig = null;


        try {
            // InternalMazeDsl.g:1240:53: (iv_rulePatrolConfig= rulePatrolConfig EOF )
            // InternalMazeDsl.g:1241:2: iv_rulePatrolConfig= rulePatrolConfig EOF
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
    // InternalMazeDsl.g:1247:1: rulePatrolConfig returns [EObject current=null] : (otherlv_0= 'patrol' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' (otherlv_3= 'visionRange' ( (lv_visionRange_4_0= ruleDOUBLE ) ) )? ( (lv_zone_5_0= rulePatrolZoneConfig ) )? otherlv_6= 'path' otherlv_7= '[' ( (lv_waypoints_8_0= ruleWaypoint ) ) (otherlv_9= ',' ( (lv_waypoints_10_0= ruleWaypoint ) ) )* otherlv_11= ']' otherlv_12= '}' ) ;
    public final EObject rulePatrolConfig() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        Token otherlv_11=null;
        Token otherlv_12=null;
        AntlrDatatypeRuleToken lv_name_1_0 = null;

        AntlrDatatypeRuleToken lv_visionRange_4_0 = null;

        EObject lv_zone_5_0 = null;

        EObject lv_waypoints_8_0 = null;

        EObject lv_waypoints_10_0 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:1253:2: ( (otherlv_0= 'patrol' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' (otherlv_3= 'visionRange' ( (lv_visionRange_4_0= ruleDOUBLE ) ) )? ( (lv_zone_5_0= rulePatrolZoneConfig ) )? otherlv_6= 'path' otherlv_7= '[' ( (lv_waypoints_8_0= ruleWaypoint ) ) (otherlv_9= ',' ( (lv_waypoints_10_0= ruleWaypoint ) ) )* otherlv_11= ']' otherlv_12= '}' ) )
            // InternalMazeDsl.g:1254:2: (otherlv_0= 'patrol' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' (otherlv_3= 'visionRange' ( (lv_visionRange_4_0= ruleDOUBLE ) ) )? ( (lv_zone_5_0= rulePatrolZoneConfig ) )? otherlv_6= 'path' otherlv_7= '[' ( (lv_waypoints_8_0= ruleWaypoint ) ) (otherlv_9= ',' ( (lv_waypoints_10_0= ruleWaypoint ) ) )* otherlv_11= ']' otherlv_12= '}' )
            {
            // InternalMazeDsl.g:1254:2: (otherlv_0= 'patrol' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' (otherlv_3= 'visionRange' ( (lv_visionRange_4_0= ruleDOUBLE ) ) )? ( (lv_zone_5_0= rulePatrolZoneConfig ) )? otherlv_6= 'path' otherlv_7= '[' ( (lv_waypoints_8_0= ruleWaypoint ) ) (otherlv_9= ',' ( (lv_waypoints_10_0= ruleWaypoint ) ) )* otherlv_11= ']' otherlv_12= '}' )
            // InternalMazeDsl.g:1255:3: otherlv_0= 'patrol' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' (otherlv_3= 'visionRange' ( (lv_visionRange_4_0= ruleDOUBLE ) ) )? ( (lv_zone_5_0= rulePatrolZoneConfig ) )? otherlv_6= 'path' otherlv_7= '[' ( (lv_waypoints_8_0= ruleWaypoint ) ) (otherlv_9= ',' ( (lv_waypoints_10_0= ruleWaypoint ) ) )* otherlv_11= ']' otherlv_12= '}'
            {
            otherlv_0=(Token)match(input,32,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getPatrolConfigAccess().getPatrolKeyword_0());
            		
            // InternalMazeDsl.g:1259:3: ( (lv_name_1_0= ruleValidID ) )
            // InternalMazeDsl.g:1260:4: (lv_name_1_0= ruleValidID )
            {
            // InternalMazeDsl.g:1260:4: (lv_name_1_0= ruleValidID )
            // InternalMazeDsl.g:1261:5: lv_name_1_0= ruleValidID
            {

            					newCompositeNode(grammarAccess.getPatrolConfigAccess().getNameValidIDParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_4);
            lv_name_1_0=ruleValidID();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getPatrolConfigRule());
            					}
            					set(
            						current,
            						"name",
            						lv_name_1_0,
            						"main.game.maze.dsl.MazeDsl.ValidID");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_2=(Token)match(input,12,FOLLOW_49); 

            			newLeafNode(otherlv_2, grammarAccess.getPatrolConfigAccess().getLeftCurlyBracketKeyword_2());
            		
            // InternalMazeDsl.g:1282:3: (otherlv_3= 'visionRange' ( (lv_visionRange_4_0= ruleDOUBLE ) ) )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==47) ) {
                alt34=1;
            }
            switch (alt34) {
                case 1 :
                    // InternalMazeDsl.g:1283:4: otherlv_3= 'visionRange' ( (lv_visionRange_4_0= ruleDOUBLE ) )
                    {
                    otherlv_3=(Token)match(input,47,FOLLOW_16); 

                    				newLeafNode(otherlv_3, grammarAccess.getPatrolConfigAccess().getVisionRangeKeyword_3_0());
                    			
                    // InternalMazeDsl.g:1287:4: ( (lv_visionRange_4_0= ruleDOUBLE ) )
                    // InternalMazeDsl.g:1288:5: (lv_visionRange_4_0= ruleDOUBLE )
                    {
                    // InternalMazeDsl.g:1288:5: (lv_visionRange_4_0= ruleDOUBLE )
                    // InternalMazeDsl.g:1289:6: lv_visionRange_4_0= ruleDOUBLE
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

            // InternalMazeDsl.g:1307:3: ( (lv_zone_5_0= rulePatrolZoneConfig ) )?
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==52) ) {
                alt35=1;
            }
            switch (alt35) {
                case 1 :
                    // InternalMazeDsl.g:1308:4: (lv_zone_5_0= rulePatrolZoneConfig )
                    {
                    // InternalMazeDsl.g:1308:4: (lv_zone_5_0= rulePatrolZoneConfig )
                    // InternalMazeDsl.g:1309:5: lv_zone_5_0= rulePatrolZoneConfig
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
            		
            // InternalMazeDsl.g:1334:3: ( (lv_waypoints_8_0= ruleWaypoint ) )
            // InternalMazeDsl.g:1335:4: (lv_waypoints_8_0= ruleWaypoint )
            {
            // InternalMazeDsl.g:1335:4: (lv_waypoints_8_0= ruleWaypoint )
            // InternalMazeDsl.g:1336:5: lv_waypoints_8_0= ruleWaypoint
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

            // InternalMazeDsl.g:1353:3: (otherlv_9= ',' ( (lv_waypoints_10_0= ruleWaypoint ) ) )*
            loop36:
            do {
                int alt36=2;
                int LA36_0 = input.LA(1);

                if ( (LA36_0==50) ) {
                    alt36=1;
                }


                switch (alt36) {
            	case 1 :
            	    // InternalMazeDsl.g:1354:4: otherlv_9= ',' ( (lv_waypoints_10_0= ruleWaypoint ) )
            	    {
            	    otherlv_9=(Token)match(input,50,FOLLOW_53); 

            	    				newLeafNode(otherlv_9, grammarAccess.getPatrolConfigAccess().getCommaKeyword_8_0());
            	    			
            	    // InternalMazeDsl.g:1358:4: ( (lv_waypoints_10_0= ruleWaypoint ) )
            	    // InternalMazeDsl.g:1359:5: (lv_waypoints_10_0= ruleWaypoint )
            	    {
            	    // InternalMazeDsl.g:1359:5: (lv_waypoints_10_0= ruleWaypoint )
            	    // InternalMazeDsl.g:1360:6: lv_waypoints_10_0= ruleWaypoint
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
    // InternalMazeDsl.g:1390:1: entryRulePatrolZoneConfig returns [EObject current=null] : iv_rulePatrolZoneConfig= rulePatrolZoneConfig EOF ;
    public final EObject entryRulePatrolZoneConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePatrolZoneConfig = null;


        try {
            // InternalMazeDsl.g:1390:57: (iv_rulePatrolZoneConfig= rulePatrolZoneConfig EOF )
            // InternalMazeDsl.g:1391:2: iv_rulePatrolZoneConfig= rulePatrolZoneConfig EOF
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
    // InternalMazeDsl.g:1397:1: rulePatrolZoneConfig returns [EObject current=null] : (otherlv_0= 'zone' otherlv_1= '{' otherlv_2= 'topLeft' otherlv_3= '(' ( (lv_topLeftX_4_0= ruleDOUBLE ) ) otherlv_5= ',' ( (lv_topLeftY_6_0= ruleDOUBLE ) ) otherlv_7= ')' otherlv_8= 'width' ( (lv_width_9_0= ruleDOUBLE ) ) otherlv_10= 'height' ( (lv_height_11_0= ruleDOUBLE ) ) otherlv_12= '}' ) ;
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
            // InternalMazeDsl.g:1403:2: ( (otherlv_0= 'zone' otherlv_1= '{' otherlv_2= 'topLeft' otherlv_3= '(' ( (lv_topLeftX_4_0= ruleDOUBLE ) ) otherlv_5= ',' ( (lv_topLeftY_6_0= ruleDOUBLE ) ) otherlv_7= ')' otherlv_8= 'width' ( (lv_width_9_0= ruleDOUBLE ) ) otherlv_10= 'height' ( (lv_height_11_0= ruleDOUBLE ) ) otherlv_12= '}' ) )
            // InternalMazeDsl.g:1404:2: (otherlv_0= 'zone' otherlv_1= '{' otherlv_2= 'topLeft' otherlv_3= '(' ( (lv_topLeftX_4_0= ruleDOUBLE ) ) otherlv_5= ',' ( (lv_topLeftY_6_0= ruleDOUBLE ) ) otherlv_7= ')' otherlv_8= 'width' ( (lv_width_9_0= ruleDOUBLE ) ) otherlv_10= 'height' ( (lv_height_11_0= ruleDOUBLE ) ) otherlv_12= '}' )
            {
            // InternalMazeDsl.g:1404:2: (otherlv_0= 'zone' otherlv_1= '{' otherlv_2= 'topLeft' otherlv_3= '(' ( (lv_topLeftX_4_0= ruleDOUBLE ) ) otherlv_5= ',' ( (lv_topLeftY_6_0= ruleDOUBLE ) ) otherlv_7= ')' otherlv_8= 'width' ( (lv_width_9_0= ruleDOUBLE ) ) otherlv_10= 'height' ( (lv_height_11_0= ruleDOUBLE ) ) otherlv_12= '}' )
            // InternalMazeDsl.g:1405:3: otherlv_0= 'zone' otherlv_1= '{' otherlv_2= 'topLeft' otherlv_3= '(' ( (lv_topLeftX_4_0= ruleDOUBLE ) ) otherlv_5= ',' ( (lv_topLeftY_6_0= ruleDOUBLE ) ) otherlv_7= ')' otherlv_8= 'width' ( (lv_width_9_0= ruleDOUBLE ) ) otherlv_10= 'height' ( (lv_height_11_0= ruleDOUBLE ) ) otherlv_12= '}'
            {
            otherlv_0=(Token)match(input,52,FOLLOW_4); 

            			newLeafNode(otherlv_0, grammarAccess.getPatrolZoneConfigAccess().getZoneKeyword_0());
            		
            otherlv_1=(Token)match(input,12,FOLLOW_55); 

            			newLeafNode(otherlv_1, grammarAccess.getPatrolZoneConfigAccess().getLeftCurlyBracketKeyword_1());
            		
            otherlv_2=(Token)match(input,53,FOLLOW_53); 

            			newLeafNode(otherlv_2, grammarAccess.getPatrolZoneConfigAccess().getTopLeftKeyword_2());
            		
            otherlv_3=(Token)match(input,54,FOLLOW_16); 

            			newLeafNode(otherlv_3, grammarAccess.getPatrolZoneConfigAccess().getLeftParenthesisKeyword_3());
            		
            // InternalMazeDsl.g:1421:3: ( (lv_topLeftX_4_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:1422:4: (lv_topLeftX_4_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:1422:4: (lv_topLeftX_4_0= ruleDOUBLE )
            // InternalMazeDsl.g:1423:5: lv_topLeftX_4_0= ruleDOUBLE
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
            		
            // InternalMazeDsl.g:1444:3: ( (lv_topLeftY_6_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:1445:4: (lv_topLeftY_6_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:1445:4: (lv_topLeftY_6_0= ruleDOUBLE )
            // InternalMazeDsl.g:1446:5: lv_topLeftY_6_0= ruleDOUBLE
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
            		
            // InternalMazeDsl.g:1471:3: ( (lv_width_9_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:1472:4: (lv_width_9_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:1472:4: (lv_width_9_0= ruleDOUBLE )
            // InternalMazeDsl.g:1473:5: lv_width_9_0= ruleDOUBLE
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
            		
            // InternalMazeDsl.g:1494:3: ( (lv_height_11_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:1495:4: (lv_height_11_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:1495:4: (lv_height_11_0= ruleDOUBLE )
            // InternalMazeDsl.g:1496:5: lv_height_11_0= ruleDOUBLE
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
    // InternalMazeDsl.g:1521:1: entryRuleWaypoint returns [EObject current=null] : iv_ruleWaypoint= ruleWaypoint EOF ;
    public final EObject entryRuleWaypoint() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleWaypoint = null;


        try {
            // InternalMazeDsl.g:1521:49: (iv_ruleWaypoint= ruleWaypoint EOF )
            // InternalMazeDsl.g:1522:2: iv_ruleWaypoint= ruleWaypoint EOF
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
    // InternalMazeDsl.g:1528:1: ruleWaypoint returns [EObject current=null] : (otherlv_0= '(' ( (lv_x_1_0= ruleDOUBLE ) ) otherlv_2= ',' ( (lv_y_3_0= ruleDOUBLE ) ) otherlv_4= ')' (otherlv_5= ':' ( (lv_waitTime_6_0= RULE_INT ) ) otherlv_7= 'ms' )? ) ;
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
            // InternalMazeDsl.g:1534:2: ( (otherlv_0= '(' ( (lv_x_1_0= ruleDOUBLE ) ) otherlv_2= ',' ( (lv_y_3_0= ruleDOUBLE ) ) otherlv_4= ')' (otherlv_5= ':' ( (lv_waitTime_6_0= RULE_INT ) ) otherlv_7= 'ms' )? ) )
            // InternalMazeDsl.g:1535:2: (otherlv_0= '(' ( (lv_x_1_0= ruleDOUBLE ) ) otherlv_2= ',' ( (lv_y_3_0= ruleDOUBLE ) ) otherlv_4= ')' (otherlv_5= ':' ( (lv_waitTime_6_0= RULE_INT ) ) otherlv_7= 'ms' )? )
            {
            // InternalMazeDsl.g:1535:2: (otherlv_0= '(' ( (lv_x_1_0= ruleDOUBLE ) ) otherlv_2= ',' ( (lv_y_3_0= ruleDOUBLE ) ) otherlv_4= ')' (otherlv_5= ':' ( (lv_waitTime_6_0= RULE_INT ) ) otherlv_7= 'ms' )? )
            // InternalMazeDsl.g:1536:3: otherlv_0= '(' ( (lv_x_1_0= ruleDOUBLE ) ) otherlv_2= ',' ( (lv_y_3_0= ruleDOUBLE ) ) otherlv_4= ')' (otherlv_5= ':' ( (lv_waitTime_6_0= RULE_INT ) ) otherlv_7= 'ms' )?
            {
            otherlv_0=(Token)match(input,54,FOLLOW_16); 

            			newLeafNode(otherlv_0, grammarAccess.getWaypointAccess().getLeftParenthesisKeyword_0());
            		
            // InternalMazeDsl.g:1540:3: ( (lv_x_1_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:1541:4: (lv_x_1_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:1541:4: (lv_x_1_0= ruleDOUBLE )
            // InternalMazeDsl.g:1542:5: lv_x_1_0= ruleDOUBLE
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
            		
            // InternalMazeDsl.g:1563:3: ( (lv_y_3_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:1564:4: (lv_y_3_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:1564:4: (lv_y_3_0= ruleDOUBLE )
            // InternalMazeDsl.g:1565:5: lv_y_3_0= ruleDOUBLE
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
            		
            // InternalMazeDsl.g:1586:3: (otherlv_5= ':' ( (lv_waitTime_6_0= RULE_INT ) ) otherlv_7= 'ms' )?
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( (LA37_0==58) ) {
                alt37=1;
            }
            switch (alt37) {
                case 1 :
                    // InternalMazeDsl.g:1587:4: otherlv_5= ':' ( (lv_waitTime_6_0= RULE_INT ) ) otherlv_7= 'ms'
                    {
                    otherlv_5=(Token)match(input,58,FOLLOW_36); 

                    				newLeafNode(otherlv_5, grammarAccess.getWaypointAccess().getColonKeyword_5_0());
                    			
                    // InternalMazeDsl.g:1591:4: ( (lv_waitTime_6_0= RULE_INT ) )
                    // InternalMazeDsl.g:1592:5: (lv_waitTime_6_0= RULE_INT )
                    {
                    // InternalMazeDsl.g:1592:5: (lv_waitTime_6_0= RULE_INT )
                    // InternalMazeDsl.g:1593:6: lv_waitTime_6_0= RULE_INT
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
    // InternalMazeDsl.g:1618:1: entryRuleLootTableConfig returns [EObject current=null] : iv_ruleLootTableConfig= ruleLootTableConfig EOF ;
    public final EObject entryRuleLootTableConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLootTableConfig = null;


        try {
            // InternalMazeDsl.g:1618:56: (iv_ruleLootTableConfig= ruleLootTableConfig EOF )
            // InternalMazeDsl.g:1619:2: iv_ruleLootTableConfig= ruleLootTableConfig EOF
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
    // InternalMazeDsl.g:1625:1: ruleLootTableConfig returns [EObject current=null] : (otherlv_0= 'loot-table' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' (otherlv_3= 'capacity' ( (lv_capacity_4_0= RULE_INT ) ) )? ( (lv_items_5_0= ruleLootItemConfig ) )+ otherlv_6= '}' ) ;
    public final EObject ruleLootTableConfig() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token lv_capacity_4_0=null;
        Token otherlv_6=null;
        AntlrDatatypeRuleToken lv_name_1_0 = null;

        EObject lv_items_5_0 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:1631:2: ( (otherlv_0= 'loot-table' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' (otherlv_3= 'capacity' ( (lv_capacity_4_0= RULE_INT ) ) )? ( (lv_items_5_0= ruleLootItemConfig ) )+ otherlv_6= '}' ) )
            // InternalMazeDsl.g:1632:2: (otherlv_0= 'loot-table' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' (otherlv_3= 'capacity' ( (lv_capacity_4_0= RULE_INT ) ) )? ( (lv_items_5_0= ruleLootItemConfig ) )+ otherlv_6= '}' )
            {
            // InternalMazeDsl.g:1632:2: (otherlv_0= 'loot-table' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' (otherlv_3= 'capacity' ( (lv_capacity_4_0= RULE_INT ) ) )? ( (lv_items_5_0= ruleLootItemConfig ) )+ otherlv_6= '}' )
            // InternalMazeDsl.g:1633:3: otherlv_0= 'loot-table' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' (otherlv_3= 'capacity' ( (lv_capacity_4_0= RULE_INT ) ) )? ( (lv_items_5_0= ruleLootItemConfig ) )+ otherlv_6= '}'
            {
            otherlv_0=(Token)match(input,60,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getLootTableConfigAccess().getLootTableKeyword_0());
            		
            // InternalMazeDsl.g:1637:3: ( (lv_name_1_0= ruleValidID ) )
            // InternalMazeDsl.g:1638:4: (lv_name_1_0= ruleValidID )
            {
            // InternalMazeDsl.g:1638:4: (lv_name_1_0= ruleValidID )
            // InternalMazeDsl.g:1639:5: lv_name_1_0= ruleValidID
            {

            					newCompositeNode(grammarAccess.getLootTableConfigAccess().getNameValidIDParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_4);
            lv_name_1_0=ruleValidID();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getLootTableConfigRule());
            					}
            					set(
            						current,
            						"name",
            						lv_name_1_0,
            						"main.game.maze.dsl.MazeDsl.ValidID");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_2=(Token)match(input,12,FOLLOW_62); 

            			newLeafNode(otherlv_2, grammarAccess.getLootTableConfigAccess().getLeftCurlyBracketKeyword_2());
            		
            // InternalMazeDsl.g:1660:3: (otherlv_3= 'capacity' ( (lv_capacity_4_0= RULE_INT ) ) )?
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==61) ) {
                alt38=1;
            }
            switch (alt38) {
                case 1 :
                    // InternalMazeDsl.g:1661:4: otherlv_3= 'capacity' ( (lv_capacity_4_0= RULE_INT ) )
                    {
                    otherlv_3=(Token)match(input,61,FOLLOW_36); 

                    				newLeafNode(otherlv_3, grammarAccess.getLootTableConfigAccess().getCapacityKeyword_3_0());
                    			
                    // InternalMazeDsl.g:1665:4: ( (lv_capacity_4_0= RULE_INT ) )
                    // InternalMazeDsl.g:1666:5: (lv_capacity_4_0= RULE_INT )
                    {
                    // InternalMazeDsl.g:1666:5: (lv_capacity_4_0= RULE_INT )
                    // InternalMazeDsl.g:1667:6: lv_capacity_4_0= RULE_INT
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

            // InternalMazeDsl.g:1684:3: ( (lv_items_5_0= ruleLootItemConfig ) )+
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
            	    // InternalMazeDsl.g:1685:4: (lv_items_5_0= ruleLootItemConfig )
            	    {
            	    // InternalMazeDsl.g:1685:4: (lv_items_5_0= ruleLootItemConfig )
            	    // InternalMazeDsl.g:1686:5: lv_items_5_0= ruleLootItemConfig
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
    // InternalMazeDsl.g:1711:1: entryRuleLootItemConfig returns [EObject current=null] : iv_ruleLootItemConfig= ruleLootItemConfig EOF ;
    public final EObject entryRuleLootItemConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLootItemConfig = null;


        try {
            // InternalMazeDsl.g:1711:55: (iv_ruleLootItemConfig= ruleLootItemConfig EOF )
            // InternalMazeDsl.g:1712:2: iv_ruleLootItemConfig= ruleLootItemConfig EOF
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
    // InternalMazeDsl.g:1718:1: ruleLootItemConfig returns [EObject current=null] : (otherlv_0= 'item' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' otherlv_3= 'type' ( (lv_type_4_0= ruleLootItemTypeEnum ) ) otherlv_5= 'value' ( (lv_value_6_0= RULE_INT ) ) (otherlv_7= 'weight' ( (lv_weight_8_0= RULE_INT ) ) )? otherlv_9= '}' ) ;
    public final EObject ruleLootItemConfig() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token lv_value_6_0=null;
        Token otherlv_7=null;
        Token lv_weight_8_0=null;
        Token otherlv_9=null;
        AntlrDatatypeRuleToken lv_name_1_0 = null;

        Enumerator lv_type_4_0 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:1724:2: ( (otherlv_0= 'item' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' otherlv_3= 'type' ( (lv_type_4_0= ruleLootItemTypeEnum ) ) otherlv_5= 'value' ( (lv_value_6_0= RULE_INT ) ) (otherlv_7= 'weight' ( (lv_weight_8_0= RULE_INT ) ) )? otherlv_9= '}' ) )
            // InternalMazeDsl.g:1725:2: (otherlv_0= 'item' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' otherlv_3= 'type' ( (lv_type_4_0= ruleLootItemTypeEnum ) ) otherlv_5= 'value' ( (lv_value_6_0= RULE_INT ) ) (otherlv_7= 'weight' ( (lv_weight_8_0= RULE_INT ) ) )? otherlv_9= '}' )
            {
            // InternalMazeDsl.g:1725:2: (otherlv_0= 'item' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' otherlv_3= 'type' ( (lv_type_4_0= ruleLootItemTypeEnum ) ) otherlv_5= 'value' ( (lv_value_6_0= RULE_INT ) ) (otherlv_7= 'weight' ( (lv_weight_8_0= RULE_INT ) ) )? otherlv_9= '}' )
            // InternalMazeDsl.g:1726:3: otherlv_0= 'item' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' otherlv_3= 'type' ( (lv_type_4_0= ruleLootItemTypeEnum ) ) otherlv_5= 'value' ( (lv_value_6_0= RULE_INT ) ) (otherlv_7= 'weight' ( (lv_weight_8_0= RULE_INT ) ) )? otherlv_9= '}'
            {
            otherlv_0=(Token)match(input,62,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getLootItemConfigAccess().getItemKeyword_0());
            		
            // InternalMazeDsl.g:1730:3: ( (lv_name_1_0= ruleValidID ) )
            // InternalMazeDsl.g:1731:4: (lv_name_1_0= ruleValidID )
            {
            // InternalMazeDsl.g:1731:4: (lv_name_1_0= ruleValidID )
            // InternalMazeDsl.g:1732:5: lv_name_1_0= ruleValidID
            {

            					newCompositeNode(grammarAccess.getLootItemConfigAccess().getNameValidIDParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_4);
            lv_name_1_0=ruleValidID();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getLootItemConfigRule());
            					}
            					set(
            						current,
            						"name",
            						lv_name_1_0,
            						"main.game.maze.dsl.MazeDsl.ValidID");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_2=(Token)match(input,12,FOLLOW_22); 

            			newLeafNode(otherlv_2, grammarAccess.getLootItemConfigAccess().getLeftCurlyBracketKeyword_2());
            		
            otherlv_3=(Token)match(input,25,FOLLOW_64); 

            			newLeafNode(otherlv_3, grammarAccess.getLootItemConfigAccess().getTypeKeyword_3());
            		
            // InternalMazeDsl.g:1757:3: ( (lv_type_4_0= ruleLootItemTypeEnum ) )
            // InternalMazeDsl.g:1758:4: (lv_type_4_0= ruleLootItemTypeEnum )
            {
            // InternalMazeDsl.g:1758:4: (lv_type_4_0= ruleLootItemTypeEnum )
            // InternalMazeDsl.g:1759:5: lv_type_4_0= ruleLootItemTypeEnum
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
            		
            // InternalMazeDsl.g:1780:3: ( (lv_value_6_0= RULE_INT ) )
            // InternalMazeDsl.g:1781:4: (lv_value_6_0= RULE_INT )
            {
            // InternalMazeDsl.g:1781:4: (lv_value_6_0= RULE_INT )
            // InternalMazeDsl.g:1782:5: lv_value_6_0= RULE_INT
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

            // InternalMazeDsl.g:1798:3: (otherlv_7= 'weight' ( (lv_weight_8_0= RULE_INT ) ) )?
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( (LA40_0==64) ) {
                alt40=1;
            }
            switch (alt40) {
                case 1 :
                    // InternalMazeDsl.g:1799:4: otherlv_7= 'weight' ( (lv_weight_8_0= RULE_INT ) )
                    {
                    otherlv_7=(Token)match(input,64,FOLLOW_36); 

                    				newLeafNode(otherlv_7, grammarAccess.getLootItemConfigAccess().getWeightKeyword_7_0());
                    			
                    // InternalMazeDsl.g:1803:4: ( (lv_weight_8_0= RULE_INT ) )
                    // InternalMazeDsl.g:1804:5: (lv_weight_8_0= RULE_INT )
                    {
                    // InternalMazeDsl.g:1804:5: (lv_weight_8_0= RULE_INT )
                    // InternalMazeDsl.g:1805:6: lv_weight_8_0= RULE_INT
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
    // InternalMazeDsl.g:1830:1: entryRuleLevelConfig returns [EObject current=null] : iv_ruleLevelConfig= ruleLevelConfig EOF ;
    public final EObject entryRuleLevelConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLevelConfig = null;


        try {
            // InternalMazeDsl.g:1830:52: (iv_ruleLevelConfig= ruleLevelConfig EOF )
            // InternalMazeDsl.g:1831:2: iv_ruleLevelConfig= ruleLevelConfig EOF
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
    // InternalMazeDsl.g:1837:1: ruleLevelConfig returns [EObject current=null] : (otherlv_0= 'level' ( (lv_number_1_0= RULE_INT ) ) ( (lv_name_2_0= RULE_STRING ) ) otherlv_3= '{' (otherlv_4= 'basedOn' ( (lv_baseLevel_5_0= RULE_INT ) ) )? (otherlv_6= 'threatCapacity' ( (lv_threatCapacityFactor_7_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_8= 'wallMaterials' ( (lv_showWallMaterials_9_0= ruleBOOLEAN_OBJECT ) ) )? (otherlv_10= 'inheritWeapons' ( (lv_inheritWeapons_11_0= ruleBOOLEAN_OBJECT ) ) )? (otherlv_12= 'cascadeDepth' ( (lv_cascadeDepth_13_0= ruleINT_OBJECT ) ) )? ( (lv_zones_14_0= ruleLevelZone ) )* ( (lv_sergeant_15_0= ruleSergeantConfig ) )? ( (lv_sergeantPosts_16_0= ruleTierCountConfig ) )* ( (lv_mines_17_0= ruleMineRingConfig ) )? ( (lv_portalGhost_18_0= rulePortalGhostConfig ) )? ( (lv_ghostCounts_19_0= ruleGhostCountConfig ) )* ( (lv_teleport_20_0= ruleTeleportRulesConfig ) )? ( (lv_guardTower_21_0= ruleGuardTowerConfig ) )? ( (lv_towerCounts_22_0= ruleTowerCountConfig ) )* ( (lv_wallBuilder_23_0= ruleWallBuilderConfig ) )? ( (lv_builderCounts_24_0= ruleBuilderCountConfig ) )* ( (lv_lava_25_0= ruleLavaConfig ) )? ( (lv_lastLifeGift_26_0= ruleLastLifeGiftConfig ) )? otherlv_27= '}' ) ;
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
        Token otherlv_27=null;
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

        EObject lv_guardTower_21_0 = null;

        EObject lv_towerCounts_22_0 = null;

        EObject lv_wallBuilder_23_0 = null;

        EObject lv_builderCounts_24_0 = null;

        EObject lv_lava_25_0 = null;

        EObject lv_lastLifeGift_26_0 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:1843:2: ( (otherlv_0= 'level' ( (lv_number_1_0= RULE_INT ) ) ( (lv_name_2_0= RULE_STRING ) ) otherlv_3= '{' (otherlv_4= 'basedOn' ( (lv_baseLevel_5_0= RULE_INT ) ) )? (otherlv_6= 'threatCapacity' ( (lv_threatCapacityFactor_7_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_8= 'wallMaterials' ( (lv_showWallMaterials_9_0= ruleBOOLEAN_OBJECT ) ) )? (otherlv_10= 'inheritWeapons' ( (lv_inheritWeapons_11_0= ruleBOOLEAN_OBJECT ) ) )? (otherlv_12= 'cascadeDepth' ( (lv_cascadeDepth_13_0= ruleINT_OBJECT ) ) )? ( (lv_zones_14_0= ruleLevelZone ) )* ( (lv_sergeant_15_0= ruleSergeantConfig ) )? ( (lv_sergeantPosts_16_0= ruleTierCountConfig ) )* ( (lv_mines_17_0= ruleMineRingConfig ) )? ( (lv_portalGhost_18_0= rulePortalGhostConfig ) )? ( (lv_ghostCounts_19_0= ruleGhostCountConfig ) )* ( (lv_teleport_20_0= ruleTeleportRulesConfig ) )? ( (lv_guardTower_21_0= ruleGuardTowerConfig ) )? ( (lv_towerCounts_22_0= ruleTowerCountConfig ) )* ( (lv_wallBuilder_23_0= ruleWallBuilderConfig ) )? ( (lv_builderCounts_24_0= ruleBuilderCountConfig ) )* ( (lv_lava_25_0= ruleLavaConfig ) )? ( (lv_lastLifeGift_26_0= ruleLastLifeGiftConfig ) )? otherlv_27= '}' ) )
            // InternalMazeDsl.g:1844:2: (otherlv_0= 'level' ( (lv_number_1_0= RULE_INT ) ) ( (lv_name_2_0= RULE_STRING ) ) otherlv_3= '{' (otherlv_4= 'basedOn' ( (lv_baseLevel_5_0= RULE_INT ) ) )? (otherlv_6= 'threatCapacity' ( (lv_threatCapacityFactor_7_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_8= 'wallMaterials' ( (lv_showWallMaterials_9_0= ruleBOOLEAN_OBJECT ) ) )? (otherlv_10= 'inheritWeapons' ( (lv_inheritWeapons_11_0= ruleBOOLEAN_OBJECT ) ) )? (otherlv_12= 'cascadeDepth' ( (lv_cascadeDepth_13_0= ruleINT_OBJECT ) ) )? ( (lv_zones_14_0= ruleLevelZone ) )* ( (lv_sergeant_15_0= ruleSergeantConfig ) )? ( (lv_sergeantPosts_16_0= ruleTierCountConfig ) )* ( (lv_mines_17_0= ruleMineRingConfig ) )? ( (lv_portalGhost_18_0= rulePortalGhostConfig ) )? ( (lv_ghostCounts_19_0= ruleGhostCountConfig ) )* ( (lv_teleport_20_0= ruleTeleportRulesConfig ) )? ( (lv_guardTower_21_0= ruleGuardTowerConfig ) )? ( (lv_towerCounts_22_0= ruleTowerCountConfig ) )* ( (lv_wallBuilder_23_0= ruleWallBuilderConfig ) )? ( (lv_builderCounts_24_0= ruleBuilderCountConfig ) )* ( (lv_lava_25_0= ruleLavaConfig ) )? ( (lv_lastLifeGift_26_0= ruleLastLifeGiftConfig ) )? otherlv_27= '}' )
            {
            // InternalMazeDsl.g:1844:2: (otherlv_0= 'level' ( (lv_number_1_0= RULE_INT ) ) ( (lv_name_2_0= RULE_STRING ) ) otherlv_3= '{' (otherlv_4= 'basedOn' ( (lv_baseLevel_5_0= RULE_INT ) ) )? (otherlv_6= 'threatCapacity' ( (lv_threatCapacityFactor_7_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_8= 'wallMaterials' ( (lv_showWallMaterials_9_0= ruleBOOLEAN_OBJECT ) ) )? (otherlv_10= 'inheritWeapons' ( (lv_inheritWeapons_11_0= ruleBOOLEAN_OBJECT ) ) )? (otherlv_12= 'cascadeDepth' ( (lv_cascadeDepth_13_0= ruleINT_OBJECT ) ) )? ( (lv_zones_14_0= ruleLevelZone ) )* ( (lv_sergeant_15_0= ruleSergeantConfig ) )? ( (lv_sergeantPosts_16_0= ruleTierCountConfig ) )* ( (lv_mines_17_0= ruleMineRingConfig ) )? ( (lv_portalGhost_18_0= rulePortalGhostConfig ) )? ( (lv_ghostCounts_19_0= ruleGhostCountConfig ) )* ( (lv_teleport_20_0= ruleTeleportRulesConfig ) )? ( (lv_guardTower_21_0= ruleGuardTowerConfig ) )? ( (lv_towerCounts_22_0= ruleTowerCountConfig ) )* ( (lv_wallBuilder_23_0= ruleWallBuilderConfig ) )? ( (lv_builderCounts_24_0= ruleBuilderCountConfig ) )* ( (lv_lava_25_0= ruleLavaConfig ) )? ( (lv_lastLifeGift_26_0= ruleLastLifeGiftConfig ) )? otherlv_27= '}' )
            // InternalMazeDsl.g:1845:3: otherlv_0= 'level' ( (lv_number_1_0= RULE_INT ) ) ( (lv_name_2_0= RULE_STRING ) ) otherlv_3= '{' (otherlv_4= 'basedOn' ( (lv_baseLevel_5_0= RULE_INT ) ) )? (otherlv_6= 'threatCapacity' ( (lv_threatCapacityFactor_7_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_8= 'wallMaterials' ( (lv_showWallMaterials_9_0= ruleBOOLEAN_OBJECT ) ) )? (otherlv_10= 'inheritWeapons' ( (lv_inheritWeapons_11_0= ruleBOOLEAN_OBJECT ) ) )? (otherlv_12= 'cascadeDepth' ( (lv_cascadeDepth_13_0= ruleINT_OBJECT ) ) )? ( (lv_zones_14_0= ruleLevelZone ) )* ( (lv_sergeant_15_0= ruleSergeantConfig ) )? ( (lv_sergeantPosts_16_0= ruleTierCountConfig ) )* ( (lv_mines_17_0= ruleMineRingConfig ) )? ( (lv_portalGhost_18_0= rulePortalGhostConfig ) )? ( (lv_ghostCounts_19_0= ruleGhostCountConfig ) )* ( (lv_teleport_20_0= ruleTeleportRulesConfig ) )? ( (lv_guardTower_21_0= ruleGuardTowerConfig ) )? ( (lv_towerCounts_22_0= ruleTowerCountConfig ) )* ( (lv_wallBuilder_23_0= ruleWallBuilderConfig ) )? ( (lv_builderCounts_24_0= ruleBuilderCountConfig ) )* ( (lv_lava_25_0= ruleLavaConfig ) )? ( (lv_lastLifeGift_26_0= ruleLastLifeGiftConfig ) )? otherlv_27= '}'
            {
            otherlv_0=(Token)match(input,16,FOLLOW_36); 

            			newLeafNode(otherlv_0, grammarAccess.getLevelConfigAccess().getLevelKeyword_0());
            		
            // InternalMazeDsl.g:1849:3: ( (lv_number_1_0= RULE_INT ) )
            // InternalMazeDsl.g:1850:4: (lv_number_1_0= RULE_INT )
            {
            // InternalMazeDsl.g:1850:4: (lv_number_1_0= RULE_INT )
            // InternalMazeDsl.g:1851:5: lv_number_1_0= RULE_INT
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

            // InternalMazeDsl.g:1867:3: ( (lv_name_2_0= RULE_STRING ) )
            // InternalMazeDsl.g:1868:4: (lv_name_2_0= RULE_STRING )
            {
            // InternalMazeDsl.g:1868:4: (lv_name_2_0= RULE_STRING )
            // InternalMazeDsl.g:1869:5: lv_name_2_0= RULE_STRING
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
            		
            // InternalMazeDsl.g:1889:3: (otherlv_4= 'basedOn' ( (lv_baseLevel_5_0= RULE_INT ) ) )?
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( (LA41_0==65) ) {
                alt41=1;
            }
            switch (alt41) {
                case 1 :
                    // InternalMazeDsl.g:1890:4: otherlv_4= 'basedOn' ( (lv_baseLevel_5_0= RULE_INT ) )
                    {
                    otherlv_4=(Token)match(input,65,FOLLOW_36); 

                    				newLeafNode(otherlv_4, grammarAccess.getLevelConfigAccess().getBasedOnKeyword_4_0());
                    			
                    // InternalMazeDsl.g:1894:4: ( (lv_baseLevel_5_0= RULE_INT ) )
                    // InternalMazeDsl.g:1895:5: (lv_baseLevel_5_0= RULE_INT )
                    {
                    // InternalMazeDsl.g:1895:5: (lv_baseLevel_5_0= RULE_INT )
                    // InternalMazeDsl.g:1896:6: lv_baseLevel_5_0= RULE_INT
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

            // InternalMazeDsl.g:1913:3: (otherlv_6= 'threatCapacity' ( (lv_threatCapacityFactor_7_0= ruleDOUBLE_OBJECT ) ) )?
            int alt42=2;
            int LA42_0 = input.LA(1);

            if ( (LA42_0==66) ) {
                alt42=1;
            }
            switch (alt42) {
                case 1 :
                    // InternalMazeDsl.g:1914:4: otherlv_6= 'threatCapacity' ( (lv_threatCapacityFactor_7_0= ruleDOUBLE_OBJECT ) )
                    {
                    otherlv_6=(Token)match(input,66,FOLLOW_16); 

                    				newLeafNode(otherlv_6, grammarAccess.getLevelConfigAccess().getThreatCapacityKeyword_5_0());
                    			
                    // InternalMazeDsl.g:1918:4: ( (lv_threatCapacityFactor_7_0= ruleDOUBLE_OBJECT ) )
                    // InternalMazeDsl.g:1919:5: (lv_threatCapacityFactor_7_0= ruleDOUBLE_OBJECT )
                    {
                    // InternalMazeDsl.g:1919:5: (lv_threatCapacityFactor_7_0= ruleDOUBLE_OBJECT )
                    // InternalMazeDsl.g:1920:6: lv_threatCapacityFactor_7_0= ruleDOUBLE_OBJECT
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

            // InternalMazeDsl.g:1938:3: (otherlv_8= 'wallMaterials' ( (lv_showWallMaterials_9_0= ruleBOOLEAN_OBJECT ) ) )?
            int alt43=2;
            int LA43_0 = input.LA(1);

            if ( (LA43_0==67) ) {
                alt43=1;
            }
            switch (alt43) {
                case 1 :
                    // InternalMazeDsl.g:1939:4: otherlv_8= 'wallMaterials' ( (lv_showWallMaterials_9_0= ruleBOOLEAN_OBJECT ) )
                    {
                    otherlv_8=(Token)match(input,67,FOLLOW_28); 

                    				newLeafNode(otherlv_8, grammarAccess.getLevelConfigAccess().getWallMaterialsKeyword_6_0());
                    			
                    // InternalMazeDsl.g:1943:4: ( (lv_showWallMaterials_9_0= ruleBOOLEAN_OBJECT ) )
                    // InternalMazeDsl.g:1944:5: (lv_showWallMaterials_9_0= ruleBOOLEAN_OBJECT )
                    {
                    // InternalMazeDsl.g:1944:5: (lv_showWallMaterials_9_0= ruleBOOLEAN_OBJECT )
                    // InternalMazeDsl.g:1945:6: lv_showWallMaterials_9_0= ruleBOOLEAN_OBJECT
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

            // InternalMazeDsl.g:1963:3: (otherlv_10= 'inheritWeapons' ( (lv_inheritWeapons_11_0= ruleBOOLEAN_OBJECT ) ) )?
            int alt44=2;
            int LA44_0 = input.LA(1);

            if ( (LA44_0==68) ) {
                alt44=1;
            }
            switch (alt44) {
                case 1 :
                    // InternalMazeDsl.g:1964:4: otherlv_10= 'inheritWeapons' ( (lv_inheritWeapons_11_0= ruleBOOLEAN_OBJECT ) )
                    {
                    otherlv_10=(Token)match(input,68,FOLLOW_28); 

                    				newLeafNode(otherlv_10, grammarAccess.getLevelConfigAccess().getInheritWeaponsKeyword_7_0());
                    			
                    // InternalMazeDsl.g:1968:4: ( (lv_inheritWeapons_11_0= ruleBOOLEAN_OBJECT ) )
                    // InternalMazeDsl.g:1969:5: (lv_inheritWeapons_11_0= ruleBOOLEAN_OBJECT )
                    {
                    // InternalMazeDsl.g:1969:5: (lv_inheritWeapons_11_0= ruleBOOLEAN_OBJECT )
                    // InternalMazeDsl.g:1970:6: lv_inheritWeapons_11_0= ruleBOOLEAN_OBJECT
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

            // InternalMazeDsl.g:1988:3: (otherlv_12= 'cascadeDepth' ( (lv_cascadeDepth_13_0= ruleINT_OBJECT ) ) )?
            int alt45=2;
            int LA45_0 = input.LA(1);

            if ( (LA45_0==69) ) {
                alt45=1;
            }
            switch (alt45) {
                case 1 :
                    // InternalMazeDsl.g:1989:4: otherlv_12= 'cascadeDepth' ( (lv_cascadeDepth_13_0= ruleINT_OBJECT ) )
                    {
                    otherlv_12=(Token)match(input,69,FOLLOW_16); 

                    				newLeafNode(otherlv_12, grammarAccess.getLevelConfigAccess().getCascadeDepthKeyword_8_0());
                    			
                    // InternalMazeDsl.g:1993:4: ( (lv_cascadeDepth_13_0= ruleINT_OBJECT ) )
                    // InternalMazeDsl.g:1994:5: (lv_cascadeDepth_13_0= ruleINT_OBJECT )
                    {
                    // InternalMazeDsl.g:1994:5: (lv_cascadeDepth_13_0= ruleINT_OBJECT )
                    // InternalMazeDsl.g:1995:6: lv_cascadeDepth_13_0= ruleINT_OBJECT
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

            // InternalMazeDsl.g:2013:3: ( (lv_zones_14_0= ruleLevelZone ) )*
            loop46:
            do {
                int alt46=2;
                int LA46_0 = input.LA(1);

                if ( (LA46_0==70) ) {
                    alt46=1;
                }


                switch (alt46) {
            	case 1 :
            	    // InternalMazeDsl.g:2014:4: (lv_zones_14_0= ruleLevelZone )
            	    {
            	    // InternalMazeDsl.g:2014:4: (lv_zones_14_0= ruleLevelZone )
            	    // InternalMazeDsl.g:2015:5: lv_zones_14_0= ruleLevelZone
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

            // InternalMazeDsl.g:2032:3: ( (lv_sergeant_15_0= ruleSergeantConfig ) )?
            int alt47=2;
            int LA47_0 = input.LA(1);

            if ( (LA47_0==73) ) {
                alt47=1;
            }
            switch (alt47) {
                case 1 :
                    // InternalMazeDsl.g:2033:4: (lv_sergeant_15_0= ruleSergeantConfig )
                    {
                    // InternalMazeDsl.g:2033:4: (lv_sergeant_15_0= ruleSergeantConfig )
                    // InternalMazeDsl.g:2034:5: lv_sergeant_15_0= ruleSergeantConfig
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

            // InternalMazeDsl.g:2051:3: ( (lv_sergeantPosts_16_0= ruleTierCountConfig ) )*
            loop48:
            do {
                int alt48=2;
                int LA48_0 = input.LA(1);

                if ( (LA48_0==83) ) {
                    alt48=1;
                }


                switch (alt48) {
            	case 1 :
            	    // InternalMazeDsl.g:2052:4: (lv_sergeantPosts_16_0= ruleTierCountConfig )
            	    {
            	    // InternalMazeDsl.g:2052:4: (lv_sergeantPosts_16_0= ruleTierCountConfig )
            	    // InternalMazeDsl.g:2053:5: lv_sergeantPosts_16_0= ruleTierCountConfig
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

            // InternalMazeDsl.g:2070:3: ( (lv_mines_17_0= ruleMineRingConfig ) )?
            int alt49=2;
            int LA49_0 = input.LA(1);

            if ( (LA49_0==84) ) {
                alt49=1;
            }
            switch (alt49) {
                case 1 :
                    // InternalMazeDsl.g:2071:4: (lv_mines_17_0= ruleMineRingConfig )
                    {
                    // InternalMazeDsl.g:2071:4: (lv_mines_17_0= ruleMineRingConfig )
                    // InternalMazeDsl.g:2072:5: lv_mines_17_0= ruleMineRingConfig
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

            // InternalMazeDsl.g:2089:3: ( (lv_portalGhost_18_0= rulePortalGhostConfig ) )?
            int alt50=2;
            int LA50_0 = input.LA(1);

            if ( (LA50_0==94) ) {
                alt50=1;
            }
            switch (alt50) {
                case 1 :
                    // InternalMazeDsl.g:2090:4: (lv_portalGhost_18_0= rulePortalGhostConfig )
                    {
                    // InternalMazeDsl.g:2090:4: (lv_portalGhost_18_0= rulePortalGhostConfig )
                    // InternalMazeDsl.g:2091:5: lv_portalGhost_18_0= rulePortalGhostConfig
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

            // InternalMazeDsl.g:2108:3: ( (lv_ghostCounts_19_0= ruleGhostCountConfig ) )*
            loop51:
            do {
                int alt51=2;
                int LA51_0 = input.LA(1);

                if ( (LA51_0==95) ) {
                    alt51=1;
                }


                switch (alt51) {
            	case 1 :
            	    // InternalMazeDsl.g:2109:4: (lv_ghostCounts_19_0= ruleGhostCountConfig )
            	    {
            	    // InternalMazeDsl.g:2109:4: (lv_ghostCounts_19_0= ruleGhostCountConfig )
            	    // InternalMazeDsl.g:2110:5: lv_ghostCounts_19_0= ruleGhostCountConfig
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

            // InternalMazeDsl.g:2127:3: ( (lv_teleport_20_0= ruleTeleportRulesConfig ) )?
            int alt52=2;
            int LA52_0 = input.LA(1);

            if ( (LA52_0==133) ) {
                alt52=1;
            }
            switch (alt52) {
                case 1 :
                    // InternalMazeDsl.g:2128:4: (lv_teleport_20_0= ruleTeleportRulesConfig )
                    {
                    // InternalMazeDsl.g:2128:4: (lv_teleport_20_0= ruleTeleportRulesConfig )
                    // InternalMazeDsl.g:2129:5: lv_teleport_20_0= ruleTeleportRulesConfig
                    {

                    					newCompositeNode(grammarAccess.getLevelConfigAccess().getTeleportTeleportRulesConfigParserRuleCall_15_0());
                    				
                    pushFollow(FOLLOW_76);
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

            // InternalMazeDsl.g:2146:3: ( (lv_guardTower_21_0= ruleGuardTowerConfig ) )?
            int alt53=2;
            int LA53_0 = input.LA(1);

            if ( (LA53_0==96) ) {
                alt53=1;
            }
            switch (alt53) {
                case 1 :
                    // InternalMazeDsl.g:2147:4: (lv_guardTower_21_0= ruleGuardTowerConfig )
                    {
                    // InternalMazeDsl.g:2147:4: (lv_guardTower_21_0= ruleGuardTowerConfig )
                    // InternalMazeDsl.g:2148:5: lv_guardTower_21_0= ruleGuardTowerConfig
                    {

                    					newCompositeNode(grammarAccess.getLevelConfigAccess().getGuardTowerGuardTowerConfigParserRuleCall_16_0());
                    				
                    pushFollow(FOLLOW_77);
                    lv_guardTower_21_0=ruleGuardTowerConfig();

                    state._fsp--;


                    					if (current==null) {
                    						current = createModelElementForParent(grammarAccess.getLevelConfigRule());
                    					}
                    					set(
                    						current,
                    						"guardTower",
                    						lv_guardTower_21_0,
                    						"main.game.maze.dsl.MazeDsl.GuardTowerConfig");
                    					afterParserOrEnumRuleCall();
                    				

                    }


                    }
                    break;

            }

            // InternalMazeDsl.g:2165:3: ( (lv_towerCounts_22_0= ruleTowerCountConfig ) )*
            loop54:
            do {
                int alt54=2;
                int LA54_0 = input.LA(1);

                if ( (LA54_0==112) ) {
                    alt54=1;
                }


                switch (alt54) {
            	case 1 :
            	    // InternalMazeDsl.g:2166:4: (lv_towerCounts_22_0= ruleTowerCountConfig )
            	    {
            	    // InternalMazeDsl.g:2166:4: (lv_towerCounts_22_0= ruleTowerCountConfig )
            	    // InternalMazeDsl.g:2167:5: lv_towerCounts_22_0= ruleTowerCountConfig
            	    {

            	    					newCompositeNode(grammarAccess.getLevelConfigAccess().getTowerCountsTowerCountConfigParserRuleCall_17_0());
            	    				
            	    pushFollow(FOLLOW_77);
            	    lv_towerCounts_22_0=ruleTowerCountConfig();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getLevelConfigRule());
            	    					}
            	    					add(
            	    						current,
            	    						"towerCounts",
            	    						lv_towerCounts_22_0,
            	    						"main.game.maze.dsl.MazeDsl.TowerCountConfig");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop54;
                }
            } while (true);

            // InternalMazeDsl.g:2184:3: ( (lv_wallBuilder_23_0= ruleWallBuilderConfig ) )?
            int alt55=2;
            int LA55_0 = input.LA(1);

            if ( (LA55_0==113) ) {
                alt55=1;
            }
            switch (alt55) {
                case 1 :
                    // InternalMazeDsl.g:2185:4: (lv_wallBuilder_23_0= ruleWallBuilderConfig )
                    {
                    // InternalMazeDsl.g:2185:4: (lv_wallBuilder_23_0= ruleWallBuilderConfig )
                    // InternalMazeDsl.g:2186:5: lv_wallBuilder_23_0= ruleWallBuilderConfig
                    {

                    					newCompositeNode(grammarAccess.getLevelConfigAccess().getWallBuilderWallBuilderConfigParserRuleCall_18_0());
                    				
                    pushFollow(FOLLOW_78);
                    lv_wallBuilder_23_0=ruleWallBuilderConfig();

                    state._fsp--;


                    					if (current==null) {
                    						current = createModelElementForParent(grammarAccess.getLevelConfigRule());
                    					}
                    					set(
                    						current,
                    						"wallBuilder",
                    						lv_wallBuilder_23_0,
                    						"main.game.maze.dsl.MazeDsl.WallBuilderConfig");
                    					afterParserOrEnumRuleCall();
                    				

                    }


                    }
                    break;

            }

            // InternalMazeDsl.g:2203:3: ( (lv_builderCounts_24_0= ruleBuilderCountConfig ) )*
            loop56:
            do {
                int alt56=2;
                int LA56_0 = input.LA(1);

                if ( (LA56_0==120) ) {
                    alt56=1;
                }


                switch (alt56) {
            	case 1 :
            	    // InternalMazeDsl.g:2204:4: (lv_builderCounts_24_0= ruleBuilderCountConfig )
            	    {
            	    // InternalMazeDsl.g:2204:4: (lv_builderCounts_24_0= ruleBuilderCountConfig )
            	    // InternalMazeDsl.g:2205:5: lv_builderCounts_24_0= ruleBuilderCountConfig
            	    {

            	    					newCompositeNode(grammarAccess.getLevelConfigAccess().getBuilderCountsBuilderCountConfigParserRuleCall_19_0());
            	    				
            	    pushFollow(FOLLOW_78);
            	    lv_builderCounts_24_0=ruleBuilderCountConfig();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getLevelConfigRule());
            	    					}
            	    					add(
            	    						current,
            	    						"builderCounts",
            	    						lv_builderCounts_24_0,
            	    						"main.game.maze.dsl.MazeDsl.BuilderCountConfig");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop56;
                }
            } while (true);

            // InternalMazeDsl.g:2222:3: ( (lv_lava_25_0= ruleLavaConfig ) )?
            int alt57=2;
            int LA57_0 = input.LA(1);

            if ( (LA57_0==121) ) {
                alt57=1;
            }
            switch (alt57) {
                case 1 :
                    // InternalMazeDsl.g:2223:4: (lv_lava_25_0= ruleLavaConfig )
                    {
                    // InternalMazeDsl.g:2223:4: (lv_lava_25_0= ruleLavaConfig )
                    // InternalMazeDsl.g:2224:5: lv_lava_25_0= ruleLavaConfig
                    {

                    					newCompositeNode(grammarAccess.getLevelConfigAccess().getLavaLavaConfigParserRuleCall_20_0());
                    				
                    pushFollow(FOLLOW_79);
                    lv_lava_25_0=ruleLavaConfig();

                    state._fsp--;


                    					if (current==null) {
                    						current = createModelElementForParent(grammarAccess.getLevelConfigRule());
                    					}
                    					set(
                    						current,
                    						"lava",
                    						lv_lava_25_0,
                    						"main.game.maze.dsl.MazeDsl.LavaConfig");
                    					afterParserOrEnumRuleCall();
                    				

                    }


                    }
                    break;

            }

            // InternalMazeDsl.g:2241:3: ( (lv_lastLifeGift_26_0= ruleLastLifeGiftConfig ) )?
            int alt58=2;
            int LA58_0 = input.LA(1);

            if ( (LA58_0==131) ) {
                alt58=1;
            }
            switch (alt58) {
                case 1 :
                    // InternalMazeDsl.g:2242:4: (lv_lastLifeGift_26_0= ruleLastLifeGiftConfig )
                    {
                    // InternalMazeDsl.g:2242:4: (lv_lastLifeGift_26_0= ruleLastLifeGiftConfig )
                    // InternalMazeDsl.g:2243:5: lv_lastLifeGift_26_0= ruleLastLifeGiftConfig
                    {

                    					newCompositeNode(grammarAccess.getLevelConfigAccess().getLastLifeGiftLastLifeGiftConfigParserRuleCall_21_0());
                    				
                    pushFollow(FOLLOW_34);
                    lv_lastLifeGift_26_0=ruleLastLifeGiftConfig();

                    state._fsp--;


                    					if (current==null) {
                    						current = createModelElementForParent(grammarAccess.getLevelConfigRule());
                    					}
                    					set(
                    						current,
                    						"lastLifeGift",
                    						lv_lastLifeGift_26_0,
                    						"main.game.maze.dsl.MazeDsl.LastLifeGiftConfig");
                    					afterParserOrEnumRuleCall();
                    				

                    }


                    }
                    break;

            }

            otherlv_27=(Token)match(input,13,FOLLOW_2); 

            			newLeafNode(otherlv_27, grammarAccess.getLevelConfigAccess().getRightCurlyBracketKeyword_22());
            		

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
    // InternalMazeDsl.g:2268:1: entryRuleLevelZone returns [EObject current=null] : iv_ruleLevelZone= ruleLevelZone EOF ;
    public final EObject entryRuleLevelZone() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLevelZone = null;


        try {
            // InternalMazeDsl.g:2268:50: (iv_ruleLevelZone= ruleLevelZone EOF )
            // InternalMazeDsl.g:2269:2: iv_ruleLevelZone= ruleLevelZone EOF
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
    // InternalMazeDsl.g:2275:1: ruleLevelZone returns [EObject current=null] : (otherlv_0= 'ring' ( (lv_displayName_1_0= RULE_STRING ) ) (otherlv_2= 'share' ( (lv_breakableShare_3_0= ruleDOUBLE_OBJECT ) ) )? otherlv_4= 'materials' ( (lv_materialIds_5_0= ruleValidID ) ) (otherlv_6= ',' ( (lv_materialIds_7_0= ruleValidID ) ) )* ) ;
    public final EObject ruleLevelZone() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_displayName_1_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        AntlrDatatypeRuleToken lv_breakableShare_3_0 = null;

        AntlrDatatypeRuleToken lv_materialIds_5_0 = null;

        AntlrDatatypeRuleToken lv_materialIds_7_0 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:2281:2: ( (otherlv_0= 'ring' ( (lv_displayName_1_0= RULE_STRING ) ) (otherlv_2= 'share' ( (lv_breakableShare_3_0= ruleDOUBLE_OBJECT ) ) )? otherlv_4= 'materials' ( (lv_materialIds_5_0= ruleValidID ) ) (otherlv_6= ',' ( (lv_materialIds_7_0= ruleValidID ) ) )* ) )
            // InternalMazeDsl.g:2282:2: (otherlv_0= 'ring' ( (lv_displayName_1_0= RULE_STRING ) ) (otherlv_2= 'share' ( (lv_breakableShare_3_0= ruleDOUBLE_OBJECT ) ) )? otherlv_4= 'materials' ( (lv_materialIds_5_0= ruleValidID ) ) (otherlv_6= ',' ( (lv_materialIds_7_0= ruleValidID ) ) )* )
            {
            // InternalMazeDsl.g:2282:2: (otherlv_0= 'ring' ( (lv_displayName_1_0= RULE_STRING ) ) (otherlv_2= 'share' ( (lv_breakableShare_3_0= ruleDOUBLE_OBJECT ) ) )? otherlv_4= 'materials' ( (lv_materialIds_5_0= ruleValidID ) ) (otherlv_6= ',' ( (lv_materialIds_7_0= ruleValidID ) ) )* )
            // InternalMazeDsl.g:2283:3: otherlv_0= 'ring' ( (lv_displayName_1_0= RULE_STRING ) ) (otherlv_2= 'share' ( (lv_breakableShare_3_0= ruleDOUBLE_OBJECT ) ) )? otherlv_4= 'materials' ( (lv_materialIds_5_0= ruleValidID ) ) (otherlv_6= ',' ( (lv_materialIds_7_0= ruleValidID ) ) )*
            {
            otherlv_0=(Token)match(input,70,FOLLOW_10); 

            			newLeafNode(otherlv_0, grammarAccess.getLevelZoneAccess().getRingKeyword_0());
            		
            // InternalMazeDsl.g:2287:3: ( (lv_displayName_1_0= RULE_STRING ) )
            // InternalMazeDsl.g:2288:4: (lv_displayName_1_0= RULE_STRING )
            {
            // InternalMazeDsl.g:2288:4: (lv_displayName_1_0= RULE_STRING )
            // InternalMazeDsl.g:2289:5: lv_displayName_1_0= RULE_STRING
            {
            lv_displayName_1_0=(Token)match(input,RULE_STRING,FOLLOW_80); 

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

            // InternalMazeDsl.g:2305:3: (otherlv_2= 'share' ( (lv_breakableShare_3_0= ruleDOUBLE_OBJECT ) ) )?
            int alt59=2;
            int LA59_0 = input.LA(1);

            if ( (LA59_0==71) ) {
                alt59=1;
            }
            switch (alt59) {
                case 1 :
                    // InternalMazeDsl.g:2306:4: otherlv_2= 'share' ( (lv_breakableShare_3_0= ruleDOUBLE_OBJECT ) )
                    {
                    otherlv_2=(Token)match(input,71,FOLLOW_16); 

                    				newLeafNode(otherlv_2, grammarAccess.getLevelZoneAccess().getShareKeyword_2_0());
                    			
                    // InternalMazeDsl.g:2310:4: ( (lv_breakableShare_3_0= ruleDOUBLE_OBJECT ) )
                    // InternalMazeDsl.g:2311:5: (lv_breakableShare_3_0= ruleDOUBLE_OBJECT )
                    {
                    // InternalMazeDsl.g:2311:5: (lv_breakableShare_3_0= ruleDOUBLE_OBJECT )
                    // InternalMazeDsl.g:2312:6: lv_breakableShare_3_0= ruleDOUBLE_OBJECT
                    {

                    						newCompositeNode(grammarAccess.getLevelZoneAccess().getBreakableShareDOUBLE_OBJECTParserRuleCall_2_1_0());
                    					
                    pushFollow(FOLLOW_81);
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
            		
            // InternalMazeDsl.g:2334:3: ( (lv_materialIds_5_0= ruleValidID ) )
            // InternalMazeDsl.g:2335:4: (lv_materialIds_5_0= ruleValidID )
            {
            // InternalMazeDsl.g:2335:4: (lv_materialIds_5_0= ruleValidID )
            // InternalMazeDsl.g:2336:5: lv_materialIds_5_0= ruleValidID
            {

            					newCompositeNode(grammarAccess.getLevelZoneAccess().getMaterialIdsValidIDParserRuleCall_4_0());
            				
            pushFollow(FOLLOW_82);
            lv_materialIds_5_0=ruleValidID();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getLevelZoneRule());
            					}
            					add(
            						current,
            						"materialIds",
            						lv_materialIds_5_0,
            						"main.game.maze.dsl.MazeDsl.ValidID");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalMazeDsl.g:2353:3: (otherlv_6= ',' ( (lv_materialIds_7_0= ruleValidID ) ) )*
            loop60:
            do {
                int alt60=2;
                int LA60_0 = input.LA(1);

                if ( (LA60_0==50) ) {
                    alt60=1;
                }


                switch (alt60) {
            	case 1 :
            	    // InternalMazeDsl.g:2354:4: otherlv_6= ',' ( (lv_materialIds_7_0= ruleValidID ) )
            	    {
            	    otherlv_6=(Token)match(input,50,FOLLOW_3); 

            	    				newLeafNode(otherlv_6, grammarAccess.getLevelZoneAccess().getCommaKeyword_5_0());
            	    			
            	    // InternalMazeDsl.g:2358:4: ( (lv_materialIds_7_0= ruleValidID ) )
            	    // InternalMazeDsl.g:2359:5: (lv_materialIds_7_0= ruleValidID )
            	    {
            	    // InternalMazeDsl.g:2359:5: (lv_materialIds_7_0= ruleValidID )
            	    // InternalMazeDsl.g:2360:6: lv_materialIds_7_0= ruleValidID
            	    {

            	    						newCompositeNode(grammarAccess.getLevelZoneAccess().getMaterialIdsValidIDParserRuleCall_5_1_0());
            	    					
            	    pushFollow(FOLLOW_82);
            	    lv_materialIds_7_0=ruleValidID();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getLevelZoneRule());
            	    						}
            	    						add(
            	    							current,
            	    							"materialIds",
            	    							lv_materialIds_7_0,
            	    							"main.game.maze.dsl.MazeDsl.ValidID");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop60;
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
    // InternalMazeDsl.g:2382:1: entryRuleSergeantConfig returns [EObject current=null] : iv_ruleSergeantConfig= ruleSergeantConfig EOF ;
    public final EObject entryRuleSergeantConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSergeantConfig = null;


        try {
            // InternalMazeDsl.g:2382:55: (iv_ruleSergeantConfig= ruleSergeantConfig EOF )
            // InternalMazeDsl.g:2383:2: iv_ruleSergeantConfig= ruleSergeantConfig EOF
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
    // InternalMazeDsl.g:2389:1: ruleSergeantConfig returns [EObject current=null] : (otherlv_0= 'sergeant' ( (lv_displayName_1_0= RULE_STRING ) ) otherlv_2= '{' otherlv_3= 'health' ( (lv_health_4_0= RULE_INT ) ) otherlv_5= 'sightRange' ( (lv_sightRange_6_0= ruleDOUBLE ) ) otherlv_7= 'rounds' ( (lv_roundsPerBurst_8_0= RULE_INT ) ) otherlv_9= 'roundDamage' ( (lv_roundDamage_10_0= RULE_INT ) ) otherlv_11= 'roundInterval' ( (lv_roundIntervalSeconds_12_0= ruleDOUBLE ) ) otherlv_13= 'burstCooldown' ( (lv_burstCooldownSeconds_14_0= ruleDOUBLE ) ) otherlv_15= 'sprite' ( (lv_sprite_16_0= RULE_STRING ) ) (otherlv_17= 'fireSound' ( (lv_fireSound_18_0= RULE_STRING ) ) )? otherlv_19= 'drops' ( (lv_dropWeapon_20_0= ruleDropWeaponEnum ) ) otherlv_21= 'postDistance' ( (lv_postDistanceFromHeart_22_0= ruleDOUBLE ) ) otherlv_23= '}' ) ;
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
            // InternalMazeDsl.g:2395:2: ( (otherlv_0= 'sergeant' ( (lv_displayName_1_0= RULE_STRING ) ) otherlv_2= '{' otherlv_3= 'health' ( (lv_health_4_0= RULE_INT ) ) otherlv_5= 'sightRange' ( (lv_sightRange_6_0= ruleDOUBLE ) ) otherlv_7= 'rounds' ( (lv_roundsPerBurst_8_0= RULE_INT ) ) otherlv_9= 'roundDamage' ( (lv_roundDamage_10_0= RULE_INT ) ) otherlv_11= 'roundInterval' ( (lv_roundIntervalSeconds_12_0= ruleDOUBLE ) ) otherlv_13= 'burstCooldown' ( (lv_burstCooldownSeconds_14_0= ruleDOUBLE ) ) otherlv_15= 'sprite' ( (lv_sprite_16_0= RULE_STRING ) ) (otherlv_17= 'fireSound' ( (lv_fireSound_18_0= RULE_STRING ) ) )? otherlv_19= 'drops' ( (lv_dropWeapon_20_0= ruleDropWeaponEnum ) ) otherlv_21= 'postDistance' ( (lv_postDistanceFromHeart_22_0= ruleDOUBLE ) ) otherlv_23= '}' ) )
            // InternalMazeDsl.g:2396:2: (otherlv_0= 'sergeant' ( (lv_displayName_1_0= RULE_STRING ) ) otherlv_2= '{' otherlv_3= 'health' ( (lv_health_4_0= RULE_INT ) ) otherlv_5= 'sightRange' ( (lv_sightRange_6_0= ruleDOUBLE ) ) otherlv_7= 'rounds' ( (lv_roundsPerBurst_8_0= RULE_INT ) ) otherlv_9= 'roundDamage' ( (lv_roundDamage_10_0= RULE_INT ) ) otherlv_11= 'roundInterval' ( (lv_roundIntervalSeconds_12_0= ruleDOUBLE ) ) otherlv_13= 'burstCooldown' ( (lv_burstCooldownSeconds_14_0= ruleDOUBLE ) ) otherlv_15= 'sprite' ( (lv_sprite_16_0= RULE_STRING ) ) (otherlv_17= 'fireSound' ( (lv_fireSound_18_0= RULE_STRING ) ) )? otherlv_19= 'drops' ( (lv_dropWeapon_20_0= ruleDropWeaponEnum ) ) otherlv_21= 'postDistance' ( (lv_postDistanceFromHeart_22_0= ruleDOUBLE ) ) otherlv_23= '}' )
            {
            // InternalMazeDsl.g:2396:2: (otherlv_0= 'sergeant' ( (lv_displayName_1_0= RULE_STRING ) ) otherlv_2= '{' otherlv_3= 'health' ( (lv_health_4_0= RULE_INT ) ) otherlv_5= 'sightRange' ( (lv_sightRange_6_0= ruleDOUBLE ) ) otherlv_7= 'rounds' ( (lv_roundsPerBurst_8_0= RULE_INT ) ) otherlv_9= 'roundDamage' ( (lv_roundDamage_10_0= RULE_INT ) ) otherlv_11= 'roundInterval' ( (lv_roundIntervalSeconds_12_0= ruleDOUBLE ) ) otherlv_13= 'burstCooldown' ( (lv_burstCooldownSeconds_14_0= ruleDOUBLE ) ) otherlv_15= 'sprite' ( (lv_sprite_16_0= RULE_STRING ) ) (otherlv_17= 'fireSound' ( (lv_fireSound_18_0= RULE_STRING ) ) )? otherlv_19= 'drops' ( (lv_dropWeapon_20_0= ruleDropWeaponEnum ) ) otherlv_21= 'postDistance' ( (lv_postDistanceFromHeart_22_0= ruleDOUBLE ) ) otherlv_23= '}' )
            // InternalMazeDsl.g:2397:3: otherlv_0= 'sergeant' ( (lv_displayName_1_0= RULE_STRING ) ) otherlv_2= '{' otherlv_3= 'health' ( (lv_health_4_0= RULE_INT ) ) otherlv_5= 'sightRange' ( (lv_sightRange_6_0= ruleDOUBLE ) ) otherlv_7= 'rounds' ( (lv_roundsPerBurst_8_0= RULE_INT ) ) otherlv_9= 'roundDamage' ( (lv_roundDamage_10_0= RULE_INT ) ) otherlv_11= 'roundInterval' ( (lv_roundIntervalSeconds_12_0= ruleDOUBLE ) ) otherlv_13= 'burstCooldown' ( (lv_burstCooldownSeconds_14_0= ruleDOUBLE ) ) otherlv_15= 'sprite' ( (lv_sprite_16_0= RULE_STRING ) ) (otherlv_17= 'fireSound' ( (lv_fireSound_18_0= RULE_STRING ) ) )? otherlv_19= 'drops' ( (lv_dropWeapon_20_0= ruleDropWeaponEnum ) ) otherlv_21= 'postDistance' ( (lv_postDistanceFromHeart_22_0= ruleDOUBLE ) ) otherlv_23= '}'
            {
            otherlv_0=(Token)match(input,73,FOLLOW_10); 

            			newLeafNode(otherlv_0, grammarAccess.getSergeantConfigAccess().getSergeantKeyword_0());
            		
            // InternalMazeDsl.g:2401:3: ( (lv_displayName_1_0= RULE_STRING ) )
            // InternalMazeDsl.g:2402:4: (lv_displayName_1_0= RULE_STRING )
            {
            // InternalMazeDsl.g:2402:4: (lv_displayName_1_0= RULE_STRING )
            // InternalMazeDsl.g:2403:5: lv_displayName_1_0= RULE_STRING
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

            otherlv_2=(Token)match(input,12,FOLLOW_83); 

            			newLeafNode(otherlv_2, grammarAccess.getSergeantConfigAccess().getLeftCurlyBracketKeyword_2());
            		
            otherlv_3=(Token)match(input,27,FOLLOW_36); 

            			newLeafNode(otherlv_3, grammarAccess.getSergeantConfigAccess().getHealthKeyword_3());
            		
            // InternalMazeDsl.g:2427:3: ( (lv_health_4_0= RULE_INT ) )
            // InternalMazeDsl.g:2428:4: (lv_health_4_0= RULE_INT )
            {
            // InternalMazeDsl.g:2428:4: (lv_health_4_0= RULE_INT )
            // InternalMazeDsl.g:2429:5: lv_health_4_0= RULE_INT
            {
            lv_health_4_0=(Token)match(input,RULE_INT,FOLLOW_84); 

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
            		
            // InternalMazeDsl.g:2449:3: ( (lv_sightRange_6_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:2450:4: (lv_sightRange_6_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:2450:4: (lv_sightRange_6_0= ruleDOUBLE )
            // InternalMazeDsl.g:2451:5: lv_sightRange_6_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getSergeantConfigAccess().getSightRangeDOUBLEParserRuleCall_6_0());
            				
            pushFollow(FOLLOW_85);
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
            		
            // InternalMazeDsl.g:2472:3: ( (lv_roundsPerBurst_8_0= RULE_INT ) )
            // InternalMazeDsl.g:2473:4: (lv_roundsPerBurst_8_0= RULE_INT )
            {
            // InternalMazeDsl.g:2473:4: (lv_roundsPerBurst_8_0= RULE_INT )
            // InternalMazeDsl.g:2474:5: lv_roundsPerBurst_8_0= RULE_INT
            {
            lv_roundsPerBurst_8_0=(Token)match(input,RULE_INT,FOLLOW_86); 

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
            		
            // InternalMazeDsl.g:2494:3: ( (lv_roundDamage_10_0= RULE_INT ) )
            // InternalMazeDsl.g:2495:4: (lv_roundDamage_10_0= RULE_INT )
            {
            // InternalMazeDsl.g:2495:4: (lv_roundDamage_10_0= RULE_INT )
            // InternalMazeDsl.g:2496:5: lv_roundDamage_10_0= RULE_INT
            {
            lv_roundDamage_10_0=(Token)match(input,RULE_INT,FOLLOW_87); 

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
            		
            // InternalMazeDsl.g:2516:3: ( (lv_roundIntervalSeconds_12_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:2517:4: (lv_roundIntervalSeconds_12_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:2517:4: (lv_roundIntervalSeconds_12_0= ruleDOUBLE )
            // InternalMazeDsl.g:2518:5: lv_roundIntervalSeconds_12_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getSergeantConfigAccess().getRoundIntervalSecondsDOUBLEParserRuleCall_12_0());
            				
            pushFollow(FOLLOW_88);
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
            		
            // InternalMazeDsl.g:2539:3: ( (lv_burstCooldownSeconds_14_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:2540:4: (lv_burstCooldownSeconds_14_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:2540:4: (lv_burstCooldownSeconds_14_0= ruleDOUBLE )
            // InternalMazeDsl.g:2541:5: lv_burstCooldownSeconds_14_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getSergeantConfigAccess().getBurstCooldownSecondsDOUBLEParserRuleCall_14_0());
            				
            pushFollow(FOLLOW_89);
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
            		
            // InternalMazeDsl.g:2562:3: ( (lv_sprite_16_0= RULE_STRING ) )
            // InternalMazeDsl.g:2563:4: (lv_sprite_16_0= RULE_STRING )
            {
            // InternalMazeDsl.g:2563:4: (lv_sprite_16_0= RULE_STRING )
            // InternalMazeDsl.g:2564:5: lv_sprite_16_0= RULE_STRING
            {
            lv_sprite_16_0=(Token)match(input,RULE_STRING,FOLLOW_90); 

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

            // InternalMazeDsl.g:2580:3: (otherlv_17= 'fireSound' ( (lv_fireSound_18_0= RULE_STRING ) ) )?
            int alt61=2;
            int LA61_0 = input.LA(1);

            if ( (LA61_0==80) ) {
                alt61=1;
            }
            switch (alt61) {
                case 1 :
                    // InternalMazeDsl.g:2581:4: otherlv_17= 'fireSound' ( (lv_fireSound_18_0= RULE_STRING ) )
                    {
                    otherlv_17=(Token)match(input,80,FOLLOW_10); 

                    				newLeafNode(otherlv_17, grammarAccess.getSergeantConfigAccess().getFireSoundKeyword_17_0());
                    			
                    // InternalMazeDsl.g:2585:4: ( (lv_fireSound_18_0= RULE_STRING ) )
                    // InternalMazeDsl.g:2586:5: (lv_fireSound_18_0= RULE_STRING )
                    {
                    // InternalMazeDsl.g:2586:5: (lv_fireSound_18_0= RULE_STRING )
                    // InternalMazeDsl.g:2587:6: lv_fireSound_18_0= RULE_STRING
                    {
                    lv_fireSound_18_0=(Token)match(input,RULE_STRING,FOLLOW_91); 

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

            otherlv_19=(Token)match(input,81,FOLLOW_92); 

            			newLeafNode(otherlv_19, grammarAccess.getSergeantConfigAccess().getDropsKeyword_18());
            		
            // InternalMazeDsl.g:2608:3: ( (lv_dropWeapon_20_0= ruleDropWeaponEnum ) )
            // InternalMazeDsl.g:2609:4: (lv_dropWeapon_20_0= ruleDropWeaponEnum )
            {
            // InternalMazeDsl.g:2609:4: (lv_dropWeapon_20_0= ruleDropWeaponEnum )
            // InternalMazeDsl.g:2610:5: lv_dropWeapon_20_0= ruleDropWeaponEnum
            {

            					newCompositeNode(grammarAccess.getSergeantConfigAccess().getDropWeaponDropWeaponEnumEnumRuleCall_19_0());
            				
            pushFollow(FOLLOW_93);
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
            		
            // InternalMazeDsl.g:2631:3: ( (lv_postDistanceFromHeart_22_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:2632:4: (lv_postDistanceFromHeart_22_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:2632:4: (lv_postDistanceFromHeart_22_0= ruleDOUBLE )
            // InternalMazeDsl.g:2633:5: lv_postDistanceFromHeart_22_0= ruleDOUBLE
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
    // InternalMazeDsl.g:2658:1: entryRuleTierCountConfig returns [EObject current=null] : iv_ruleTierCountConfig= ruleTierCountConfig EOF ;
    public final EObject entryRuleTierCountConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTierCountConfig = null;


        try {
            // InternalMazeDsl.g:2658:56: (iv_ruleTierCountConfig= ruleTierCountConfig EOF )
            // InternalMazeDsl.g:2659:2: iv_ruleTierCountConfig= ruleTierCountConfig EOF
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
    // InternalMazeDsl.g:2665:1: ruleTierCountConfig returns [EObject current=null] : (otherlv_0= 'sergeants' ( (lv_difficulty_1_0= ruleDifficultyLevel ) ) ( (lv_count_2_0= RULE_INT ) ) ) ;
    public final EObject ruleTierCountConfig() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_count_2_0=null;
        Enumerator lv_difficulty_1_0 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:2671:2: ( (otherlv_0= 'sergeants' ( (lv_difficulty_1_0= ruleDifficultyLevel ) ) ( (lv_count_2_0= RULE_INT ) ) ) )
            // InternalMazeDsl.g:2672:2: (otherlv_0= 'sergeants' ( (lv_difficulty_1_0= ruleDifficultyLevel ) ) ( (lv_count_2_0= RULE_INT ) ) )
            {
            // InternalMazeDsl.g:2672:2: (otherlv_0= 'sergeants' ( (lv_difficulty_1_0= ruleDifficultyLevel ) ) ( (lv_count_2_0= RULE_INT ) ) )
            // InternalMazeDsl.g:2673:3: otherlv_0= 'sergeants' ( (lv_difficulty_1_0= ruleDifficultyLevel ) ) ( (lv_count_2_0= RULE_INT ) )
            {
            otherlv_0=(Token)match(input,83,FOLLOW_12); 

            			newLeafNode(otherlv_0, grammarAccess.getTierCountConfigAccess().getSergeantsKeyword_0());
            		
            // InternalMazeDsl.g:2677:3: ( (lv_difficulty_1_0= ruleDifficultyLevel ) )
            // InternalMazeDsl.g:2678:4: (lv_difficulty_1_0= ruleDifficultyLevel )
            {
            // InternalMazeDsl.g:2678:4: (lv_difficulty_1_0= ruleDifficultyLevel )
            // InternalMazeDsl.g:2679:5: lv_difficulty_1_0= ruleDifficultyLevel
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

            // InternalMazeDsl.g:2696:3: ( (lv_count_2_0= RULE_INT ) )
            // InternalMazeDsl.g:2697:4: (lv_count_2_0= RULE_INT )
            {
            // InternalMazeDsl.g:2697:4: (lv_count_2_0= RULE_INT )
            // InternalMazeDsl.g:2698:5: lv_count_2_0= RULE_INT
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
    // InternalMazeDsl.g:2718:1: entryRuleMineRingConfig returns [EObject current=null] : iv_ruleMineRingConfig= ruleMineRingConfig EOF ;
    public final EObject entryRuleMineRingConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMineRingConfig = null;


        try {
            // InternalMazeDsl.g:2718:55: (iv_ruleMineRingConfig= ruleMineRingConfig EOF )
            // InternalMazeDsl.g:2719:2: iv_ruleMineRingConfig= ruleMineRingConfig EOF
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
    // InternalMazeDsl.g:2725:1: ruleMineRingConfig returns [EObject current=null] : (otherlv_0= 'mines' otherlv_1= '{' otherlv_2= 'count' ( (lv_count_3_0= RULE_INT ) ) otherlv_4= 'ringRadius' ( (lv_ringRadius_5_0= ruleDOUBLE ) ) (otherlv_6= 'secondRing' ( (lv_secondRingRadius_7_0= ruleDOUBLE ) ) )? otherlv_8= 'triggerRadius' ( (lv_triggerRadius_9_0= ruleDOUBLE ) ) otherlv_10= 'pulse' ( (lv_pulseSeconds_11_0= ruleDOUBLE ) ) otherlv_12= 'blastRadius' ( (lv_blastRadius_13_0= ruleDOUBLE ) ) otherlv_14= 'blastDamage' ( (lv_blastDamage_15_0= RULE_INT ) ) otherlv_16= 'playerDamage' ( (lv_playerDamage_17_0= RULE_INT ) ) otherlv_18= 'holeRadius' ( (lv_holeRadius_19_0= ruleDOUBLE ) ) otherlv_20= '}' ) ;
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
        Token otherlv_14=null;
        Token lv_blastDamage_15_0=null;
        Token otherlv_16=null;
        Token lv_playerDamage_17_0=null;
        Token otherlv_18=null;
        Token otherlv_20=null;
        AntlrDatatypeRuleToken lv_ringRadius_5_0 = null;

        AntlrDatatypeRuleToken lv_secondRingRadius_7_0 = null;

        AntlrDatatypeRuleToken lv_triggerRadius_9_0 = null;

        AntlrDatatypeRuleToken lv_pulseSeconds_11_0 = null;

        AntlrDatatypeRuleToken lv_blastRadius_13_0 = null;

        AntlrDatatypeRuleToken lv_holeRadius_19_0 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:2731:2: ( (otherlv_0= 'mines' otherlv_1= '{' otherlv_2= 'count' ( (lv_count_3_0= RULE_INT ) ) otherlv_4= 'ringRadius' ( (lv_ringRadius_5_0= ruleDOUBLE ) ) (otherlv_6= 'secondRing' ( (lv_secondRingRadius_7_0= ruleDOUBLE ) ) )? otherlv_8= 'triggerRadius' ( (lv_triggerRadius_9_0= ruleDOUBLE ) ) otherlv_10= 'pulse' ( (lv_pulseSeconds_11_0= ruleDOUBLE ) ) otherlv_12= 'blastRadius' ( (lv_blastRadius_13_0= ruleDOUBLE ) ) otherlv_14= 'blastDamage' ( (lv_blastDamage_15_0= RULE_INT ) ) otherlv_16= 'playerDamage' ( (lv_playerDamage_17_0= RULE_INT ) ) otherlv_18= 'holeRadius' ( (lv_holeRadius_19_0= ruleDOUBLE ) ) otherlv_20= '}' ) )
            // InternalMazeDsl.g:2732:2: (otherlv_0= 'mines' otherlv_1= '{' otherlv_2= 'count' ( (lv_count_3_0= RULE_INT ) ) otherlv_4= 'ringRadius' ( (lv_ringRadius_5_0= ruleDOUBLE ) ) (otherlv_6= 'secondRing' ( (lv_secondRingRadius_7_0= ruleDOUBLE ) ) )? otherlv_8= 'triggerRadius' ( (lv_triggerRadius_9_0= ruleDOUBLE ) ) otherlv_10= 'pulse' ( (lv_pulseSeconds_11_0= ruleDOUBLE ) ) otherlv_12= 'blastRadius' ( (lv_blastRadius_13_0= ruleDOUBLE ) ) otherlv_14= 'blastDamage' ( (lv_blastDamage_15_0= RULE_INT ) ) otherlv_16= 'playerDamage' ( (lv_playerDamage_17_0= RULE_INT ) ) otherlv_18= 'holeRadius' ( (lv_holeRadius_19_0= ruleDOUBLE ) ) otherlv_20= '}' )
            {
            // InternalMazeDsl.g:2732:2: (otherlv_0= 'mines' otherlv_1= '{' otherlv_2= 'count' ( (lv_count_3_0= RULE_INT ) ) otherlv_4= 'ringRadius' ( (lv_ringRadius_5_0= ruleDOUBLE ) ) (otherlv_6= 'secondRing' ( (lv_secondRingRadius_7_0= ruleDOUBLE ) ) )? otherlv_8= 'triggerRadius' ( (lv_triggerRadius_9_0= ruleDOUBLE ) ) otherlv_10= 'pulse' ( (lv_pulseSeconds_11_0= ruleDOUBLE ) ) otherlv_12= 'blastRadius' ( (lv_blastRadius_13_0= ruleDOUBLE ) ) otherlv_14= 'blastDamage' ( (lv_blastDamage_15_0= RULE_INT ) ) otherlv_16= 'playerDamage' ( (lv_playerDamage_17_0= RULE_INT ) ) otherlv_18= 'holeRadius' ( (lv_holeRadius_19_0= ruleDOUBLE ) ) otherlv_20= '}' )
            // InternalMazeDsl.g:2733:3: otherlv_0= 'mines' otherlv_1= '{' otherlv_2= 'count' ( (lv_count_3_0= RULE_INT ) ) otherlv_4= 'ringRadius' ( (lv_ringRadius_5_0= ruleDOUBLE ) ) (otherlv_6= 'secondRing' ( (lv_secondRingRadius_7_0= ruleDOUBLE ) ) )? otherlv_8= 'triggerRadius' ( (lv_triggerRadius_9_0= ruleDOUBLE ) ) otherlv_10= 'pulse' ( (lv_pulseSeconds_11_0= ruleDOUBLE ) ) otherlv_12= 'blastRadius' ( (lv_blastRadius_13_0= ruleDOUBLE ) ) otherlv_14= 'blastDamage' ( (lv_blastDamage_15_0= RULE_INT ) ) otherlv_16= 'playerDamage' ( (lv_playerDamage_17_0= RULE_INT ) ) otherlv_18= 'holeRadius' ( (lv_holeRadius_19_0= ruleDOUBLE ) ) otherlv_20= '}'
            {
            otherlv_0=(Token)match(input,84,FOLLOW_4); 

            			newLeafNode(otherlv_0, grammarAccess.getMineRingConfigAccess().getMinesKeyword_0());
            		
            otherlv_1=(Token)match(input,12,FOLLOW_94); 

            			newLeafNode(otherlv_1, grammarAccess.getMineRingConfigAccess().getLeftCurlyBracketKeyword_1());
            		
            otherlv_2=(Token)match(input,85,FOLLOW_36); 

            			newLeafNode(otherlv_2, grammarAccess.getMineRingConfigAccess().getCountKeyword_2());
            		
            // InternalMazeDsl.g:2745:3: ( (lv_count_3_0= RULE_INT ) )
            // InternalMazeDsl.g:2746:4: (lv_count_3_0= RULE_INT )
            {
            // InternalMazeDsl.g:2746:4: (lv_count_3_0= RULE_INT )
            // InternalMazeDsl.g:2747:5: lv_count_3_0= RULE_INT
            {
            lv_count_3_0=(Token)match(input,RULE_INT,FOLLOW_95); 

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
            		
            // InternalMazeDsl.g:2767:3: ( (lv_ringRadius_5_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:2768:4: (lv_ringRadius_5_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:2768:4: (lv_ringRadius_5_0= ruleDOUBLE )
            // InternalMazeDsl.g:2769:5: lv_ringRadius_5_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getMineRingConfigAccess().getRingRadiusDOUBLEParserRuleCall_5_0());
            				
            pushFollow(FOLLOW_96);
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

            // InternalMazeDsl.g:2786:3: (otherlv_6= 'secondRing' ( (lv_secondRingRadius_7_0= ruleDOUBLE ) ) )?
            int alt62=2;
            int LA62_0 = input.LA(1);

            if ( (LA62_0==87) ) {
                alt62=1;
            }
            switch (alt62) {
                case 1 :
                    // InternalMazeDsl.g:2787:4: otherlv_6= 'secondRing' ( (lv_secondRingRadius_7_0= ruleDOUBLE ) )
                    {
                    otherlv_6=(Token)match(input,87,FOLLOW_16); 

                    				newLeafNode(otherlv_6, grammarAccess.getMineRingConfigAccess().getSecondRingKeyword_6_0());
                    			
                    // InternalMazeDsl.g:2791:4: ( (lv_secondRingRadius_7_0= ruleDOUBLE ) )
                    // InternalMazeDsl.g:2792:5: (lv_secondRingRadius_7_0= ruleDOUBLE )
                    {
                    // InternalMazeDsl.g:2792:5: (lv_secondRingRadius_7_0= ruleDOUBLE )
                    // InternalMazeDsl.g:2793:6: lv_secondRingRadius_7_0= ruleDOUBLE
                    {

                    						newCompositeNode(grammarAccess.getMineRingConfigAccess().getSecondRingRadiusDOUBLEParserRuleCall_6_1_0());
                    					
                    pushFollow(FOLLOW_97);
                    lv_secondRingRadius_7_0=ruleDOUBLE();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getMineRingConfigRule());
                    						}
                    						set(
                    							current,
                    							"secondRingRadius",
                    							lv_secondRingRadius_7_0,
                    							"main.game.maze.dsl.MazeDsl.DOUBLE");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_8=(Token)match(input,88,FOLLOW_16); 

            			newLeafNode(otherlv_8, grammarAccess.getMineRingConfigAccess().getTriggerRadiusKeyword_7());
            		
            // InternalMazeDsl.g:2815:3: ( (lv_triggerRadius_9_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:2816:4: (lv_triggerRadius_9_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:2816:4: (lv_triggerRadius_9_0= ruleDOUBLE )
            // InternalMazeDsl.g:2817:5: lv_triggerRadius_9_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getMineRingConfigAccess().getTriggerRadiusDOUBLEParserRuleCall_8_0());
            				
            pushFollow(FOLLOW_98);
            lv_triggerRadius_9_0=ruleDOUBLE();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getMineRingConfigRule());
            					}
            					set(
            						current,
            						"triggerRadius",
            						lv_triggerRadius_9_0,
            						"main.game.maze.dsl.MazeDsl.DOUBLE");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_10=(Token)match(input,89,FOLLOW_16); 

            			newLeafNode(otherlv_10, grammarAccess.getMineRingConfigAccess().getPulseKeyword_9());
            		
            // InternalMazeDsl.g:2838:3: ( (lv_pulseSeconds_11_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:2839:4: (lv_pulseSeconds_11_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:2839:4: (lv_pulseSeconds_11_0= ruleDOUBLE )
            // InternalMazeDsl.g:2840:5: lv_pulseSeconds_11_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getMineRingConfigAccess().getPulseSecondsDOUBLEParserRuleCall_10_0());
            				
            pushFollow(FOLLOW_99);
            lv_pulseSeconds_11_0=ruleDOUBLE();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getMineRingConfigRule());
            					}
            					set(
            						current,
            						"pulseSeconds",
            						lv_pulseSeconds_11_0,
            						"main.game.maze.dsl.MazeDsl.DOUBLE");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_12=(Token)match(input,90,FOLLOW_16); 

            			newLeafNode(otherlv_12, grammarAccess.getMineRingConfigAccess().getBlastRadiusKeyword_11());
            		
            // InternalMazeDsl.g:2861:3: ( (lv_blastRadius_13_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:2862:4: (lv_blastRadius_13_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:2862:4: (lv_blastRadius_13_0= ruleDOUBLE )
            // InternalMazeDsl.g:2863:5: lv_blastRadius_13_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getMineRingConfigAccess().getBlastRadiusDOUBLEParserRuleCall_12_0());
            				
            pushFollow(FOLLOW_100);
            lv_blastRadius_13_0=ruleDOUBLE();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getMineRingConfigRule());
            					}
            					set(
            						current,
            						"blastRadius",
            						lv_blastRadius_13_0,
            						"main.game.maze.dsl.MazeDsl.DOUBLE");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_14=(Token)match(input,91,FOLLOW_36); 

            			newLeafNode(otherlv_14, grammarAccess.getMineRingConfigAccess().getBlastDamageKeyword_13());
            		
            // InternalMazeDsl.g:2884:3: ( (lv_blastDamage_15_0= RULE_INT ) )
            // InternalMazeDsl.g:2885:4: (lv_blastDamage_15_0= RULE_INT )
            {
            // InternalMazeDsl.g:2885:4: (lv_blastDamage_15_0= RULE_INT )
            // InternalMazeDsl.g:2886:5: lv_blastDamage_15_0= RULE_INT
            {
            lv_blastDamage_15_0=(Token)match(input,RULE_INT,FOLLOW_101); 

            					newLeafNode(lv_blastDamage_15_0, grammarAccess.getMineRingConfigAccess().getBlastDamageINTTerminalRuleCall_14_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getMineRingConfigRule());
            					}
            					setWithLastConsumed(
            						current,
            						"blastDamage",
            						lv_blastDamage_15_0,
            						"org.eclipse.xtext.common.Terminals.INT");
            				

            }


            }

            otherlv_16=(Token)match(input,92,FOLLOW_36); 

            			newLeafNode(otherlv_16, grammarAccess.getMineRingConfigAccess().getPlayerDamageKeyword_15());
            		
            // InternalMazeDsl.g:2906:3: ( (lv_playerDamage_17_0= RULE_INT ) )
            // InternalMazeDsl.g:2907:4: (lv_playerDamage_17_0= RULE_INT )
            {
            // InternalMazeDsl.g:2907:4: (lv_playerDamage_17_0= RULE_INT )
            // InternalMazeDsl.g:2908:5: lv_playerDamage_17_0= RULE_INT
            {
            lv_playerDamage_17_0=(Token)match(input,RULE_INT,FOLLOW_102); 

            					newLeafNode(lv_playerDamage_17_0, grammarAccess.getMineRingConfigAccess().getPlayerDamageINTTerminalRuleCall_16_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getMineRingConfigRule());
            					}
            					setWithLastConsumed(
            						current,
            						"playerDamage",
            						lv_playerDamage_17_0,
            						"org.eclipse.xtext.common.Terminals.INT");
            				

            }


            }

            otherlv_18=(Token)match(input,93,FOLLOW_16); 

            			newLeafNode(otherlv_18, grammarAccess.getMineRingConfigAccess().getHoleRadiusKeyword_17());
            		
            // InternalMazeDsl.g:2928:3: ( (lv_holeRadius_19_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:2929:4: (lv_holeRadius_19_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:2929:4: (lv_holeRadius_19_0= ruleDOUBLE )
            // InternalMazeDsl.g:2930:5: lv_holeRadius_19_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getMineRingConfigAccess().getHoleRadiusDOUBLEParserRuleCall_18_0());
            				
            pushFollow(FOLLOW_34);
            lv_holeRadius_19_0=ruleDOUBLE();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getMineRingConfigRule());
            					}
            					set(
            						current,
            						"holeRadius",
            						lv_holeRadius_19_0,
            						"main.game.maze.dsl.MazeDsl.DOUBLE");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_20=(Token)match(input,13,FOLLOW_2); 

            			newLeafNode(otherlv_20, grammarAccess.getMineRingConfigAccess().getRightCurlyBracketKeyword_19());
            		

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
    // InternalMazeDsl.g:2955:1: entryRulePortalGhostConfig returns [EObject current=null] : iv_rulePortalGhostConfig= rulePortalGhostConfig EOF ;
    public final EObject entryRulePortalGhostConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePortalGhostConfig = null;


        try {
            // InternalMazeDsl.g:2955:58: (iv_rulePortalGhostConfig= rulePortalGhostConfig EOF )
            // InternalMazeDsl.g:2956:2: iv_rulePortalGhostConfig= rulePortalGhostConfig EOF
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
    // InternalMazeDsl.g:2962:1: rulePortalGhostConfig returns [EObject current=null] : (otherlv_0= 'portalGhost' ( (lv_displayName_1_0= RULE_STRING ) ) otherlv_2= '{' otherlv_3= 'health' ( (lv_health_4_0= RULE_INT ) ) otherlv_5= 'speed' ( (lv_speed_6_0= ruleDOUBLE ) ) otherlv_7= 'sprite' ( (lv_sprite_8_0= RULE_STRING ) ) otherlv_9= '}' ) ;
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
            // InternalMazeDsl.g:2968:2: ( (otherlv_0= 'portalGhost' ( (lv_displayName_1_0= RULE_STRING ) ) otherlv_2= '{' otherlv_3= 'health' ( (lv_health_4_0= RULE_INT ) ) otherlv_5= 'speed' ( (lv_speed_6_0= ruleDOUBLE ) ) otherlv_7= 'sprite' ( (lv_sprite_8_0= RULE_STRING ) ) otherlv_9= '}' ) )
            // InternalMazeDsl.g:2969:2: (otherlv_0= 'portalGhost' ( (lv_displayName_1_0= RULE_STRING ) ) otherlv_2= '{' otherlv_3= 'health' ( (lv_health_4_0= RULE_INT ) ) otherlv_5= 'speed' ( (lv_speed_6_0= ruleDOUBLE ) ) otherlv_7= 'sprite' ( (lv_sprite_8_0= RULE_STRING ) ) otherlv_9= '}' )
            {
            // InternalMazeDsl.g:2969:2: (otherlv_0= 'portalGhost' ( (lv_displayName_1_0= RULE_STRING ) ) otherlv_2= '{' otherlv_3= 'health' ( (lv_health_4_0= RULE_INT ) ) otherlv_5= 'speed' ( (lv_speed_6_0= ruleDOUBLE ) ) otherlv_7= 'sprite' ( (lv_sprite_8_0= RULE_STRING ) ) otherlv_9= '}' )
            // InternalMazeDsl.g:2970:3: otherlv_0= 'portalGhost' ( (lv_displayName_1_0= RULE_STRING ) ) otherlv_2= '{' otherlv_3= 'health' ( (lv_health_4_0= RULE_INT ) ) otherlv_5= 'speed' ( (lv_speed_6_0= ruleDOUBLE ) ) otherlv_7= 'sprite' ( (lv_sprite_8_0= RULE_STRING ) ) otherlv_9= '}'
            {
            otherlv_0=(Token)match(input,94,FOLLOW_10); 

            			newLeafNode(otherlv_0, grammarAccess.getPortalGhostConfigAccess().getPortalGhostKeyword_0());
            		
            // InternalMazeDsl.g:2974:3: ( (lv_displayName_1_0= RULE_STRING ) )
            // InternalMazeDsl.g:2975:4: (lv_displayName_1_0= RULE_STRING )
            {
            // InternalMazeDsl.g:2975:4: (lv_displayName_1_0= RULE_STRING )
            // InternalMazeDsl.g:2976:5: lv_displayName_1_0= RULE_STRING
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

            otherlv_2=(Token)match(input,12,FOLLOW_83); 

            			newLeafNode(otherlv_2, grammarAccess.getPortalGhostConfigAccess().getLeftCurlyBracketKeyword_2());
            		
            otherlv_3=(Token)match(input,27,FOLLOW_36); 

            			newLeafNode(otherlv_3, grammarAccess.getPortalGhostConfigAccess().getHealthKeyword_3());
            		
            // InternalMazeDsl.g:3000:3: ( (lv_health_4_0= RULE_INT ) )
            // InternalMazeDsl.g:3001:4: (lv_health_4_0= RULE_INT )
            {
            // InternalMazeDsl.g:3001:4: (lv_health_4_0= RULE_INT )
            // InternalMazeDsl.g:3002:5: lv_health_4_0= RULE_INT
            {
            lv_health_4_0=(Token)match(input,RULE_INT,FOLLOW_103); 

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
            		
            // InternalMazeDsl.g:3022:3: ( (lv_speed_6_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:3023:4: (lv_speed_6_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:3023:4: (lv_speed_6_0= ruleDOUBLE )
            // InternalMazeDsl.g:3024:5: lv_speed_6_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getPortalGhostConfigAccess().getSpeedDOUBLEParserRuleCall_6_0());
            				
            pushFollow(FOLLOW_89);
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
            		
            // InternalMazeDsl.g:3045:3: ( (lv_sprite_8_0= RULE_STRING ) )
            // InternalMazeDsl.g:3046:4: (lv_sprite_8_0= RULE_STRING )
            {
            // InternalMazeDsl.g:3046:4: (lv_sprite_8_0= RULE_STRING )
            // InternalMazeDsl.g:3047:5: lv_sprite_8_0= RULE_STRING
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
    // InternalMazeDsl.g:3071:1: entryRuleGhostCountConfig returns [EObject current=null] : iv_ruleGhostCountConfig= ruleGhostCountConfig EOF ;
    public final EObject entryRuleGhostCountConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGhostCountConfig = null;


        try {
            // InternalMazeDsl.g:3071:57: (iv_ruleGhostCountConfig= ruleGhostCountConfig EOF )
            // InternalMazeDsl.g:3072:2: iv_ruleGhostCountConfig= ruleGhostCountConfig EOF
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
    // InternalMazeDsl.g:3078:1: ruleGhostCountConfig returns [EObject current=null] : (otherlv_0= 'portalGhosts' ( (lv_difficulty_1_0= ruleDifficultyLevel ) ) ( (lv_count_2_0= RULE_INT ) ) ) ;
    public final EObject ruleGhostCountConfig() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_count_2_0=null;
        Enumerator lv_difficulty_1_0 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:3084:2: ( (otherlv_0= 'portalGhosts' ( (lv_difficulty_1_0= ruleDifficultyLevel ) ) ( (lv_count_2_0= RULE_INT ) ) ) )
            // InternalMazeDsl.g:3085:2: (otherlv_0= 'portalGhosts' ( (lv_difficulty_1_0= ruleDifficultyLevel ) ) ( (lv_count_2_0= RULE_INT ) ) )
            {
            // InternalMazeDsl.g:3085:2: (otherlv_0= 'portalGhosts' ( (lv_difficulty_1_0= ruleDifficultyLevel ) ) ( (lv_count_2_0= RULE_INT ) ) )
            // InternalMazeDsl.g:3086:3: otherlv_0= 'portalGhosts' ( (lv_difficulty_1_0= ruleDifficultyLevel ) ) ( (lv_count_2_0= RULE_INT ) )
            {
            otherlv_0=(Token)match(input,95,FOLLOW_12); 

            			newLeafNode(otherlv_0, grammarAccess.getGhostCountConfigAccess().getPortalGhostsKeyword_0());
            		
            // InternalMazeDsl.g:3090:3: ( (lv_difficulty_1_0= ruleDifficultyLevel ) )
            // InternalMazeDsl.g:3091:4: (lv_difficulty_1_0= ruleDifficultyLevel )
            {
            // InternalMazeDsl.g:3091:4: (lv_difficulty_1_0= ruleDifficultyLevel )
            // InternalMazeDsl.g:3092:5: lv_difficulty_1_0= ruleDifficultyLevel
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

            // InternalMazeDsl.g:3109:3: ( (lv_count_2_0= RULE_INT ) )
            // InternalMazeDsl.g:3110:4: (lv_count_2_0= RULE_INT )
            {
            // InternalMazeDsl.g:3110:4: (lv_count_2_0= RULE_INT )
            // InternalMazeDsl.g:3111:5: lv_count_2_0= RULE_INT
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


    // $ANTLR start "entryRuleGuardTowerConfig"
    // InternalMazeDsl.g:3131:1: entryRuleGuardTowerConfig returns [EObject current=null] : iv_ruleGuardTowerConfig= ruleGuardTowerConfig EOF ;
    public final EObject entryRuleGuardTowerConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGuardTowerConfig = null;


        try {
            // InternalMazeDsl.g:3131:57: (iv_ruleGuardTowerConfig= ruleGuardTowerConfig EOF )
            // InternalMazeDsl.g:3132:2: iv_ruleGuardTowerConfig= ruleGuardTowerConfig EOF
            {
             newCompositeNode(grammarAccess.getGuardTowerConfigRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleGuardTowerConfig=ruleGuardTowerConfig();

            state._fsp--;

             current =iv_ruleGuardTowerConfig; 
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
    // $ANTLR end "entryRuleGuardTowerConfig"


    // $ANTLR start "ruleGuardTowerConfig"
    // InternalMazeDsl.g:3138:1: ruleGuardTowerConfig returns [EObject current=null] : (otherlv_0= 'guardTower' ( (lv_displayName_1_0= RULE_STRING ) ) otherlv_2= '{' otherlv_3= 'health' ( (lv_health_4_0= RULE_INT ) ) otherlv_5= 'sightRange' ( (lv_sightRange_6_0= ruleDOUBLE ) ) otherlv_7= 'wallsOver' ( (lv_maxWallsOver_8_0= RULE_INT ) ) otherlv_9= 'rpgInterval' ( (lv_rpgIntervalSeconds_10_0= ruleDOUBLE ) ) otherlv_11= 'rpgSpeed' ( (lv_rpgSpeed_12_0= ruleDOUBLE ) ) otherlv_13= 'rpgBlastRadius' ( (lv_rpgBlastRadius_14_0= ruleDOUBLE ) ) otherlv_15= 'rpgPlayerDamage' ( (lv_rpgPlayerDamage_16_0= RULE_INT ) ) otherlv_17= 'rpgWallDamage' ( (lv_rpgWallDamage_18_0= RULE_INT ) ) otherlv_19= 'gatlingRounds' ( (lv_gatlingRounds_20_0= RULE_INT ) ) otherlv_21= 'gatlingInterval' ( (lv_gatlingRoundIntervalSeconds_22_0= ruleDOUBLE ) ) otherlv_23= 'gatlingDamage' ( (lv_gatlingDamage_24_0= RULE_INT ) ) otherlv_25= 'deathBlast' ( (lv_deathBlastDamage_26_0= RULE_INT ) ) otherlv_27= 'sprite' ( (lv_sprite_28_0= RULE_STRING ) ) (otherlv_29= 'rpgLaunchSound' ( (lv_rpgLaunchSound_30_0= RULE_STRING ) ) )? (otherlv_31= 'rpgExplosionSound' ( (lv_rpgExplosionSound_32_0= RULE_STRING ) ) )? (otherlv_33= 'gatlingSound' ( (lv_gatlingSound_34_0= RULE_STRING ) ) )? (otherlv_35= 'hitSound' ( (lv_hitSound_36_0= RULE_STRING ) ) )? (otherlv_37= 'destroyedSound' ( (lv_destroyedSound_38_0= RULE_STRING ) ) )? otherlv_39= '}' ) ;
    public final EObject ruleGuardTowerConfig() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_displayName_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token lv_health_4_0=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Token lv_maxWallsOver_8_0=null;
        Token otherlv_9=null;
        Token otherlv_11=null;
        Token otherlv_13=null;
        Token otherlv_15=null;
        Token lv_rpgPlayerDamage_16_0=null;
        Token otherlv_17=null;
        Token lv_rpgWallDamage_18_0=null;
        Token otherlv_19=null;
        Token lv_gatlingRounds_20_0=null;
        Token otherlv_21=null;
        Token otherlv_23=null;
        Token lv_gatlingDamage_24_0=null;
        Token otherlv_25=null;
        Token lv_deathBlastDamage_26_0=null;
        Token otherlv_27=null;
        Token lv_sprite_28_0=null;
        Token otherlv_29=null;
        Token lv_rpgLaunchSound_30_0=null;
        Token otherlv_31=null;
        Token lv_rpgExplosionSound_32_0=null;
        Token otherlv_33=null;
        Token lv_gatlingSound_34_0=null;
        Token otherlv_35=null;
        Token lv_hitSound_36_0=null;
        Token otherlv_37=null;
        Token lv_destroyedSound_38_0=null;
        Token otherlv_39=null;
        AntlrDatatypeRuleToken lv_sightRange_6_0 = null;

        AntlrDatatypeRuleToken lv_rpgIntervalSeconds_10_0 = null;

        AntlrDatatypeRuleToken lv_rpgSpeed_12_0 = null;

        AntlrDatatypeRuleToken lv_rpgBlastRadius_14_0 = null;

        AntlrDatatypeRuleToken lv_gatlingRoundIntervalSeconds_22_0 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:3144:2: ( (otherlv_0= 'guardTower' ( (lv_displayName_1_0= RULE_STRING ) ) otherlv_2= '{' otherlv_3= 'health' ( (lv_health_4_0= RULE_INT ) ) otherlv_5= 'sightRange' ( (lv_sightRange_6_0= ruleDOUBLE ) ) otherlv_7= 'wallsOver' ( (lv_maxWallsOver_8_0= RULE_INT ) ) otherlv_9= 'rpgInterval' ( (lv_rpgIntervalSeconds_10_0= ruleDOUBLE ) ) otherlv_11= 'rpgSpeed' ( (lv_rpgSpeed_12_0= ruleDOUBLE ) ) otherlv_13= 'rpgBlastRadius' ( (lv_rpgBlastRadius_14_0= ruleDOUBLE ) ) otherlv_15= 'rpgPlayerDamage' ( (lv_rpgPlayerDamage_16_0= RULE_INT ) ) otherlv_17= 'rpgWallDamage' ( (lv_rpgWallDamage_18_0= RULE_INT ) ) otherlv_19= 'gatlingRounds' ( (lv_gatlingRounds_20_0= RULE_INT ) ) otherlv_21= 'gatlingInterval' ( (lv_gatlingRoundIntervalSeconds_22_0= ruleDOUBLE ) ) otherlv_23= 'gatlingDamage' ( (lv_gatlingDamage_24_0= RULE_INT ) ) otherlv_25= 'deathBlast' ( (lv_deathBlastDamage_26_0= RULE_INT ) ) otherlv_27= 'sprite' ( (lv_sprite_28_0= RULE_STRING ) ) (otherlv_29= 'rpgLaunchSound' ( (lv_rpgLaunchSound_30_0= RULE_STRING ) ) )? (otherlv_31= 'rpgExplosionSound' ( (lv_rpgExplosionSound_32_0= RULE_STRING ) ) )? (otherlv_33= 'gatlingSound' ( (lv_gatlingSound_34_0= RULE_STRING ) ) )? (otherlv_35= 'hitSound' ( (lv_hitSound_36_0= RULE_STRING ) ) )? (otherlv_37= 'destroyedSound' ( (lv_destroyedSound_38_0= RULE_STRING ) ) )? otherlv_39= '}' ) )
            // InternalMazeDsl.g:3145:2: (otherlv_0= 'guardTower' ( (lv_displayName_1_0= RULE_STRING ) ) otherlv_2= '{' otherlv_3= 'health' ( (lv_health_4_0= RULE_INT ) ) otherlv_5= 'sightRange' ( (lv_sightRange_6_0= ruleDOUBLE ) ) otherlv_7= 'wallsOver' ( (lv_maxWallsOver_8_0= RULE_INT ) ) otherlv_9= 'rpgInterval' ( (lv_rpgIntervalSeconds_10_0= ruleDOUBLE ) ) otherlv_11= 'rpgSpeed' ( (lv_rpgSpeed_12_0= ruleDOUBLE ) ) otherlv_13= 'rpgBlastRadius' ( (lv_rpgBlastRadius_14_0= ruleDOUBLE ) ) otherlv_15= 'rpgPlayerDamage' ( (lv_rpgPlayerDamage_16_0= RULE_INT ) ) otherlv_17= 'rpgWallDamage' ( (lv_rpgWallDamage_18_0= RULE_INT ) ) otherlv_19= 'gatlingRounds' ( (lv_gatlingRounds_20_0= RULE_INT ) ) otherlv_21= 'gatlingInterval' ( (lv_gatlingRoundIntervalSeconds_22_0= ruleDOUBLE ) ) otherlv_23= 'gatlingDamage' ( (lv_gatlingDamage_24_0= RULE_INT ) ) otherlv_25= 'deathBlast' ( (lv_deathBlastDamage_26_0= RULE_INT ) ) otherlv_27= 'sprite' ( (lv_sprite_28_0= RULE_STRING ) ) (otherlv_29= 'rpgLaunchSound' ( (lv_rpgLaunchSound_30_0= RULE_STRING ) ) )? (otherlv_31= 'rpgExplosionSound' ( (lv_rpgExplosionSound_32_0= RULE_STRING ) ) )? (otherlv_33= 'gatlingSound' ( (lv_gatlingSound_34_0= RULE_STRING ) ) )? (otherlv_35= 'hitSound' ( (lv_hitSound_36_0= RULE_STRING ) ) )? (otherlv_37= 'destroyedSound' ( (lv_destroyedSound_38_0= RULE_STRING ) ) )? otherlv_39= '}' )
            {
            // InternalMazeDsl.g:3145:2: (otherlv_0= 'guardTower' ( (lv_displayName_1_0= RULE_STRING ) ) otherlv_2= '{' otherlv_3= 'health' ( (lv_health_4_0= RULE_INT ) ) otherlv_5= 'sightRange' ( (lv_sightRange_6_0= ruleDOUBLE ) ) otherlv_7= 'wallsOver' ( (lv_maxWallsOver_8_0= RULE_INT ) ) otherlv_9= 'rpgInterval' ( (lv_rpgIntervalSeconds_10_0= ruleDOUBLE ) ) otherlv_11= 'rpgSpeed' ( (lv_rpgSpeed_12_0= ruleDOUBLE ) ) otherlv_13= 'rpgBlastRadius' ( (lv_rpgBlastRadius_14_0= ruleDOUBLE ) ) otherlv_15= 'rpgPlayerDamage' ( (lv_rpgPlayerDamage_16_0= RULE_INT ) ) otherlv_17= 'rpgWallDamage' ( (lv_rpgWallDamage_18_0= RULE_INT ) ) otherlv_19= 'gatlingRounds' ( (lv_gatlingRounds_20_0= RULE_INT ) ) otherlv_21= 'gatlingInterval' ( (lv_gatlingRoundIntervalSeconds_22_0= ruleDOUBLE ) ) otherlv_23= 'gatlingDamage' ( (lv_gatlingDamage_24_0= RULE_INT ) ) otherlv_25= 'deathBlast' ( (lv_deathBlastDamage_26_0= RULE_INT ) ) otherlv_27= 'sprite' ( (lv_sprite_28_0= RULE_STRING ) ) (otherlv_29= 'rpgLaunchSound' ( (lv_rpgLaunchSound_30_0= RULE_STRING ) ) )? (otherlv_31= 'rpgExplosionSound' ( (lv_rpgExplosionSound_32_0= RULE_STRING ) ) )? (otherlv_33= 'gatlingSound' ( (lv_gatlingSound_34_0= RULE_STRING ) ) )? (otherlv_35= 'hitSound' ( (lv_hitSound_36_0= RULE_STRING ) ) )? (otherlv_37= 'destroyedSound' ( (lv_destroyedSound_38_0= RULE_STRING ) ) )? otherlv_39= '}' )
            // InternalMazeDsl.g:3146:3: otherlv_0= 'guardTower' ( (lv_displayName_1_0= RULE_STRING ) ) otherlv_2= '{' otherlv_3= 'health' ( (lv_health_4_0= RULE_INT ) ) otherlv_5= 'sightRange' ( (lv_sightRange_6_0= ruleDOUBLE ) ) otherlv_7= 'wallsOver' ( (lv_maxWallsOver_8_0= RULE_INT ) ) otherlv_9= 'rpgInterval' ( (lv_rpgIntervalSeconds_10_0= ruleDOUBLE ) ) otherlv_11= 'rpgSpeed' ( (lv_rpgSpeed_12_0= ruleDOUBLE ) ) otherlv_13= 'rpgBlastRadius' ( (lv_rpgBlastRadius_14_0= ruleDOUBLE ) ) otherlv_15= 'rpgPlayerDamage' ( (lv_rpgPlayerDamage_16_0= RULE_INT ) ) otherlv_17= 'rpgWallDamage' ( (lv_rpgWallDamage_18_0= RULE_INT ) ) otherlv_19= 'gatlingRounds' ( (lv_gatlingRounds_20_0= RULE_INT ) ) otherlv_21= 'gatlingInterval' ( (lv_gatlingRoundIntervalSeconds_22_0= ruleDOUBLE ) ) otherlv_23= 'gatlingDamage' ( (lv_gatlingDamage_24_0= RULE_INT ) ) otherlv_25= 'deathBlast' ( (lv_deathBlastDamage_26_0= RULE_INT ) ) otherlv_27= 'sprite' ( (lv_sprite_28_0= RULE_STRING ) ) (otherlv_29= 'rpgLaunchSound' ( (lv_rpgLaunchSound_30_0= RULE_STRING ) ) )? (otherlv_31= 'rpgExplosionSound' ( (lv_rpgExplosionSound_32_0= RULE_STRING ) ) )? (otherlv_33= 'gatlingSound' ( (lv_gatlingSound_34_0= RULE_STRING ) ) )? (otherlv_35= 'hitSound' ( (lv_hitSound_36_0= RULE_STRING ) ) )? (otherlv_37= 'destroyedSound' ( (lv_destroyedSound_38_0= RULE_STRING ) ) )? otherlv_39= '}'
            {
            otherlv_0=(Token)match(input,96,FOLLOW_10); 

            			newLeafNode(otherlv_0, grammarAccess.getGuardTowerConfigAccess().getGuardTowerKeyword_0());
            		
            // InternalMazeDsl.g:3150:3: ( (lv_displayName_1_0= RULE_STRING ) )
            // InternalMazeDsl.g:3151:4: (lv_displayName_1_0= RULE_STRING )
            {
            // InternalMazeDsl.g:3151:4: (lv_displayName_1_0= RULE_STRING )
            // InternalMazeDsl.g:3152:5: lv_displayName_1_0= RULE_STRING
            {
            lv_displayName_1_0=(Token)match(input,RULE_STRING,FOLLOW_4); 

            					newLeafNode(lv_displayName_1_0, grammarAccess.getGuardTowerConfigAccess().getDisplayNameSTRINGTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getGuardTowerConfigRule());
            					}
            					setWithLastConsumed(
            						current,
            						"displayName",
            						lv_displayName_1_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            otherlv_2=(Token)match(input,12,FOLLOW_83); 

            			newLeafNode(otherlv_2, grammarAccess.getGuardTowerConfigAccess().getLeftCurlyBracketKeyword_2());
            		
            otherlv_3=(Token)match(input,27,FOLLOW_36); 

            			newLeafNode(otherlv_3, grammarAccess.getGuardTowerConfigAccess().getHealthKeyword_3());
            		
            // InternalMazeDsl.g:3176:3: ( (lv_health_4_0= RULE_INT ) )
            // InternalMazeDsl.g:3177:4: (lv_health_4_0= RULE_INT )
            {
            // InternalMazeDsl.g:3177:4: (lv_health_4_0= RULE_INT )
            // InternalMazeDsl.g:3178:5: lv_health_4_0= RULE_INT
            {
            lv_health_4_0=(Token)match(input,RULE_INT,FOLLOW_84); 

            					newLeafNode(lv_health_4_0, grammarAccess.getGuardTowerConfigAccess().getHealthINTTerminalRuleCall_4_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getGuardTowerConfigRule());
            					}
            					setWithLastConsumed(
            						current,
            						"health",
            						lv_health_4_0,
            						"org.eclipse.xtext.common.Terminals.INT");
            				

            }


            }

            otherlv_5=(Token)match(input,74,FOLLOW_16); 

            			newLeafNode(otherlv_5, grammarAccess.getGuardTowerConfigAccess().getSightRangeKeyword_5());
            		
            // InternalMazeDsl.g:3198:3: ( (lv_sightRange_6_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:3199:4: (lv_sightRange_6_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:3199:4: (lv_sightRange_6_0= ruleDOUBLE )
            // InternalMazeDsl.g:3200:5: lv_sightRange_6_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getGuardTowerConfigAccess().getSightRangeDOUBLEParserRuleCall_6_0());
            				
            pushFollow(FOLLOW_104);
            lv_sightRange_6_0=ruleDOUBLE();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getGuardTowerConfigRule());
            					}
            					set(
            						current,
            						"sightRange",
            						lv_sightRange_6_0,
            						"main.game.maze.dsl.MazeDsl.DOUBLE");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_7=(Token)match(input,97,FOLLOW_36); 

            			newLeafNode(otherlv_7, grammarAccess.getGuardTowerConfigAccess().getWallsOverKeyword_7());
            		
            // InternalMazeDsl.g:3221:3: ( (lv_maxWallsOver_8_0= RULE_INT ) )
            // InternalMazeDsl.g:3222:4: (lv_maxWallsOver_8_0= RULE_INT )
            {
            // InternalMazeDsl.g:3222:4: (lv_maxWallsOver_8_0= RULE_INT )
            // InternalMazeDsl.g:3223:5: lv_maxWallsOver_8_0= RULE_INT
            {
            lv_maxWallsOver_8_0=(Token)match(input,RULE_INT,FOLLOW_105); 

            					newLeafNode(lv_maxWallsOver_8_0, grammarAccess.getGuardTowerConfigAccess().getMaxWallsOverINTTerminalRuleCall_8_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getGuardTowerConfigRule());
            					}
            					setWithLastConsumed(
            						current,
            						"maxWallsOver",
            						lv_maxWallsOver_8_0,
            						"org.eclipse.xtext.common.Terminals.INT");
            				

            }


            }

            otherlv_9=(Token)match(input,98,FOLLOW_16); 

            			newLeafNode(otherlv_9, grammarAccess.getGuardTowerConfigAccess().getRpgIntervalKeyword_9());
            		
            // InternalMazeDsl.g:3243:3: ( (lv_rpgIntervalSeconds_10_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:3244:4: (lv_rpgIntervalSeconds_10_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:3244:4: (lv_rpgIntervalSeconds_10_0= ruleDOUBLE )
            // InternalMazeDsl.g:3245:5: lv_rpgIntervalSeconds_10_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getGuardTowerConfigAccess().getRpgIntervalSecondsDOUBLEParserRuleCall_10_0());
            				
            pushFollow(FOLLOW_106);
            lv_rpgIntervalSeconds_10_0=ruleDOUBLE();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getGuardTowerConfigRule());
            					}
            					set(
            						current,
            						"rpgIntervalSeconds",
            						lv_rpgIntervalSeconds_10_0,
            						"main.game.maze.dsl.MazeDsl.DOUBLE");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_11=(Token)match(input,99,FOLLOW_16); 

            			newLeafNode(otherlv_11, grammarAccess.getGuardTowerConfigAccess().getRpgSpeedKeyword_11());
            		
            // InternalMazeDsl.g:3266:3: ( (lv_rpgSpeed_12_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:3267:4: (lv_rpgSpeed_12_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:3267:4: (lv_rpgSpeed_12_0= ruleDOUBLE )
            // InternalMazeDsl.g:3268:5: lv_rpgSpeed_12_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getGuardTowerConfigAccess().getRpgSpeedDOUBLEParserRuleCall_12_0());
            				
            pushFollow(FOLLOW_107);
            lv_rpgSpeed_12_0=ruleDOUBLE();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getGuardTowerConfigRule());
            					}
            					set(
            						current,
            						"rpgSpeed",
            						lv_rpgSpeed_12_0,
            						"main.game.maze.dsl.MazeDsl.DOUBLE");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_13=(Token)match(input,100,FOLLOW_16); 

            			newLeafNode(otherlv_13, grammarAccess.getGuardTowerConfigAccess().getRpgBlastRadiusKeyword_13());
            		
            // InternalMazeDsl.g:3289:3: ( (lv_rpgBlastRadius_14_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:3290:4: (lv_rpgBlastRadius_14_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:3290:4: (lv_rpgBlastRadius_14_0= ruleDOUBLE )
            // InternalMazeDsl.g:3291:5: lv_rpgBlastRadius_14_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getGuardTowerConfigAccess().getRpgBlastRadiusDOUBLEParserRuleCall_14_0());
            				
            pushFollow(FOLLOW_108);
            lv_rpgBlastRadius_14_0=ruleDOUBLE();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getGuardTowerConfigRule());
            					}
            					set(
            						current,
            						"rpgBlastRadius",
            						lv_rpgBlastRadius_14_0,
            						"main.game.maze.dsl.MazeDsl.DOUBLE");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_15=(Token)match(input,101,FOLLOW_36); 

            			newLeafNode(otherlv_15, grammarAccess.getGuardTowerConfigAccess().getRpgPlayerDamageKeyword_15());
            		
            // InternalMazeDsl.g:3312:3: ( (lv_rpgPlayerDamage_16_0= RULE_INT ) )
            // InternalMazeDsl.g:3313:4: (lv_rpgPlayerDamage_16_0= RULE_INT )
            {
            // InternalMazeDsl.g:3313:4: (lv_rpgPlayerDamage_16_0= RULE_INT )
            // InternalMazeDsl.g:3314:5: lv_rpgPlayerDamage_16_0= RULE_INT
            {
            lv_rpgPlayerDamage_16_0=(Token)match(input,RULE_INT,FOLLOW_109); 

            					newLeafNode(lv_rpgPlayerDamage_16_0, grammarAccess.getGuardTowerConfigAccess().getRpgPlayerDamageINTTerminalRuleCall_16_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getGuardTowerConfigRule());
            					}
            					setWithLastConsumed(
            						current,
            						"rpgPlayerDamage",
            						lv_rpgPlayerDamage_16_0,
            						"org.eclipse.xtext.common.Terminals.INT");
            				

            }


            }

            otherlv_17=(Token)match(input,102,FOLLOW_36); 

            			newLeafNode(otherlv_17, grammarAccess.getGuardTowerConfigAccess().getRpgWallDamageKeyword_17());
            		
            // InternalMazeDsl.g:3334:3: ( (lv_rpgWallDamage_18_0= RULE_INT ) )
            // InternalMazeDsl.g:3335:4: (lv_rpgWallDamage_18_0= RULE_INT )
            {
            // InternalMazeDsl.g:3335:4: (lv_rpgWallDamage_18_0= RULE_INT )
            // InternalMazeDsl.g:3336:5: lv_rpgWallDamage_18_0= RULE_INT
            {
            lv_rpgWallDamage_18_0=(Token)match(input,RULE_INT,FOLLOW_110); 

            					newLeafNode(lv_rpgWallDamage_18_0, grammarAccess.getGuardTowerConfigAccess().getRpgWallDamageINTTerminalRuleCall_18_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getGuardTowerConfigRule());
            					}
            					setWithLastConsumed(
            						current,
            						"rpgWallDamage",
            						lv_rpgWallDamage_18_0,
            						"org.eclipse.xtext.common.Terminals.INT");
            				

            }


            }

            otherlv_19=(Token)match(input,103,FOLLOW_36); 

            			newLeafNode(otherlv_19, grammarAccess.getGuardTowerConfigAccess().getGatlingRoundsKeyword_19());
            		
            // InternalMazeDsl.g:3356:3: ( (lv_gatlingRounds_20_0= RULE_INT ) )
            // InternalMazeDsl.g:3357:4: (lv_gatlingRounds_20_0= RULE_INT )
            {
            // InternalMazeDsl.g:3357:4: (lv_gatlingRounds_20_0= RULE_INT )
            // InternalMazeDsl.g:3358:5: lv_gatlingRounds_20_0= RULE_INT
            {
            lv_gatlingRounds_20_0=(Token)match(input,RULE_INT,FOLLOW_111); 

            					newLeafNode(lv_gatlingRounds_20_0, grammarAccess.getGuardTowerConfigAccess().getGatlingRoundsINTTerminalRuleCall_20_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getGuardTowerConfigRule());
            					}
            					setWithLastConsumed(
            						current,
            						"gatlingRounds",
            						lv_gatlingRounds_20_0,
            						"org.eclipse.xtext.common.Terminals.INT");
            				

            }


            }

            otherlv_21=(Token)match(input,104,FOLLOW_16); 

            			newLeafNode(otherlv_21, grammarAccess.getGuardTowerConfigAccess().getGatlingIntervalKeyword_21());
            		
            // InternalMazeDsl.g:3378:3: ( (lv_gatlingRoundIntervalSeconds_22_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:3379:4: (lv_gatlingRoundIntervalSeconds_22_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:3379:4: (lv_gatlingRoundIntervalSeconds_22_0= ruleDOUBLE )
            // InternalMazeDsl.g:3380:5: lv_gatlingRoundIntervalSeconds_22_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getGuardTowerConfigAccess().getGatlingRoundIntervalSecondsDOUBLEParserRuleCall_22_0());
            				
            pushFollow(FOLLOW_112);
            lv_gatlingRoundIntervalSeconds_22_0=ruleDOUBLE();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getGuardTowerConfigRule());
            					}
            					set(
            						current,
            						"gatlingRoundIntervalSeconds",
            						lv_gatlingRoundIntervalSeconds_22_0,
            						"main.game.maze.dsl.MazeDsl.DOUBLE");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_23=(Token)match(input,105,FOLLOW_36); 

            			newLeafNode(otherlv_23, grammarAccess.getGuardTowerConfigAccess().getGatlingDamageKeyword_23());
            		
            // InternalMazeDsl.g:3401:3: ( (lv_gatlingDamage_24_0= RULE_INT ) )
            // InternalMazeDsl.g:3402:4: (lv_gatlingDamage_24_0= RULE_INT )
            {
            // InternalMazeDsl.g:3402:4: (lv_gatlingDamage_24_0= RULE_INT )
            // InternalMazeDsl.g:3403:5: lv_gatlingDamage_24_0= RULE_INT
            {
            lv_gatlingDamage_24_0=(Token)match(input,RULE_INT,FOLLOW_113); 

            					newLeafNode(lv_gatlingDamage_24_0, grammarAccess.getGuardTowerConfigAccess().getGatlingDamageINTTerminalRuleCall_24_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getGuardTowerConfigRule());
            					}
            					setWithLastConsumed(
            						current,
            						"gatlingDamage",
            						lv_gatlingDamage_24_0,
            						"org.eclipse.xtext.common.Terminals.INT");
            				

            }


            }

            otherlv_25=(Token)match(input,106,FOLLOW_36); 

            			newLeafNode(otherlv_25, grammarAccess.getGuardTowerConfigAccess().getDeathBlastKeyword_25());
            		
            // InternalMazeDsl.g:3423:3: ( (lv_deathBlastDamage_26_0= RULE_INT ) )
            // InternalMazeDsl.g:3424:4: (lv_deathBlastDamage_26_0= RULE_INT )
            {
            // InternalMazeDsl.g:3424:4: (lv_deathBlastDamage_26_0= RULE_INT )
            // InternalMazeDsl.g:3425:5: lv_deathBlastDamage_26_0= RULE_INT
            {
            lv_deathBlastDamage_26_0=(Token)match(input,RULE_INT,FOLLOW_89); 

            					newLeafNode(lv_deathBlastDamage_26_0, grammarAccess.getGuardTowerConfigAccess().getDeathBlastDamageINTTerminalRuleCall_26_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getGuardTowerConfigRule());
            					}
            					setWithLastConsumed(
            						current,
            						"deathBlastDamage",
            						lv_deathBlastDamage_26_0,
            						"org.eclipse.xtext.common.Terminals.INT");
            				

            }


            }

            otherlv_27=(Token)match(input,79,FOLLOW_10); 

            			newLeafNode(otherlv_27, grammarAccess.getGuardTowerConfigAccess().getSpriteKeyword_27());
            		
            // InternalMazeDsl.g:3445:3: ( (lv_sprite_28_0= RULE_STRING ) )
            // InternalMazeDsl.g:3446:4: (lv_sprite_28_0= RULE_STRING )
            {
            // InternalMazeDsl.g:3446:4: (lv_sprite_28_0= RULE_STRING )
            // InternalMazeDsl.g:3447:5: lv_sprite_28_0= RULE_STRING
            {
            lv_sprite_28_0=(Token)match(input,RULE_STRING,FOLLOW_114); 

            					newLeafNode(lv_sprite_28_0, grammarAccess.getGuardTowerConfigAccess().getSpriteSTRINGTerminalRuleCall_28_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getGuardTowerConfigRule());
            					}
            					setWithLastConsumed(
            						current,
            						"sprite",
            						lv_sprite_28_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            // InternalMazeDsl.g:3463:3: (otherlv_29= 'rpgLaunchSound' ( (lv_rpgLaunchSound_30_0= RULE_STRING ) ) )?
            int alt63=2;
            int LA63_0 = input.LA(1);

            if ( (LA63_0==107) ) {
                alt63=1;
            }
            switch (alt63) {
                case 1 :
                    // InternalMazeDsl.g:3464:4: otherlv_29= 'rpgLaunchSound' ( (lv_rpgLaunchSound_30_0= RULE_STRING ) )
                    {
                    otherlv_29=(Token)match(input,107,FOLLOW_10); 

                    				newLeafNode(otherlv_29, grammarAccess.getGuardTowerConfigAccess().getRpgLaunchSoundKeyword_29_0());
                    			
                    // InternalMazeDsl.g:3468:4: ( (lv_rpgLaunchSound_30_0= RULE_STRING ) )
                    // InternalMazeDsl.g:3469:5: (lv_rpgLaunchSound_30_0= RULE_STRING )
                    {
                    // InternalMazeDsl.g:3469:5: (lv_rpgLaunchSound_30_0= RULE_STRING )
                    // InternalMazeDsl.g:3470:6: lv_rpgLaunchSound_30_0= RULE_STRING
                    {
                    lv_rpgLaunchSound_30_0=(Token)match(input,RULE_STRING,FOLLOW_115); 

                    						newLeafNode(lv_rpgLaunchSound_30_0, grammarAccess.getGuardTowerConfigAccess().getRpgLaunchSoundSTRINGTerminalRuleCall_29_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getGuardTowerConfigRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"rpgLaunchSound",
                    							lv_rpgLaunchSound_30_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeDsl.g:3487:3: (otherlv_31= 'rpgExplosionSound' ( (lv_rpgExplosionSound_32_0= RULE_STRING ) ) )?
            int alt64=2;
            int LA64_0 = input.LA(1);

            if ( (LA64_0==108) ) {
                alt64=1;
            }
            switch (alt64) {
                case 1 :
                    // InternalMazeDsl.g:3488:4: otherlv_31= 'rpgExplosionSound' ( (lv_rpgExplosionSound_32_0= RULE_STRING ) )
                    {
                    otherlv_31=(Token)match(input,108,FOLLOW_10); 

                    				newLeafNode(otherlv_31, grammarAccess.getGuardTowerConfigAccess().getRpgExplosionSoundKeyword_30_0());
                    			
                    // InternalMazeDsl.g:3492:4: ( (lv_rpgExplosionSound_32_0= RULE_STRING ) )
                    // InternalMazeDsl.g:3493:5: (lv_rpgExplosionSound_32_0= RULE_STRING )
                    {
                    // InternalMazeDsl.g:3493:5: (lv_rpgExplosionSound_32_0= RULE_STRING )
                    // InternalMazeDsl.g:3494:6: lv_rpgExplosionSound_32_0= RULE_STRING
                    {
                    lv_rpgExplosionSound_32_0=(Token)match(input,RULE_STRING,FOLLOW_116); 

                    						newLeafNode(lv_rpgExplosionSound_32_0, grammarAccess.getGuardTowerConfigAccess().getRpgExplosionSoundSTRINGTerminalRuleCall_30_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getGuardTowerConfigRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"rpgExplosionSound",
                    							lv_rpgExplosionSound_32_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeDsl.g:3511:3: (otherlv_33= 'gatlingSound' ( (lv_gatlingSound_34_0= RULE_STRING ) ) )?
            int alt65=2;
            int LA65_0 = input.LA(1);

            if ( (LA65_0==109) ) {
                alt65=1;
            }
            switch (alt65) {
                case 1 :
                    // InternalMazeDsl.g:3512:4: otherlv_33= 'gatlingSound' ( (lv_gatlingSound_34_0= RULE_STRING ) )
                    {
                    otherlv_33=(Token)match(input,109,FOLLOW_10); 

                    				newLeafNode(otherlv_33, grammarAccess.getGuardTowerConfigAccess().getGatlingSoundKeyword_31_0());
                    			
                    // InternalMazeDsl.g:3516:4: ( (lv_gatlingSound_34_0= RULE_STRING ) )
                    // InternalMazeDsl.g:3517:5: (lv_gatlingSound_34_0= RULE_STRING )
                    {
                    // InternalMazeDsl.g:3517:5: (lv_gatlingSound_34_0= RULE_STRING )
                    // InternalMazeDsl.g:3518:6: lv_gatlingSound_34_0= RULE_STRING
                    {
                    lv_gatlingSound_34_0=(Token)match(input,RULE_STRING,FOLLOW_117); 

                    						newLeafNode(lv_gatlingSound_34_0, grammarAccess.getGuardTowerConfigAccess().getGatlingSoundSTRINGTerminalRuleCall_31_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getGuardTowerConfigRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"gatlingSound",
                    							lv_gatlingSound_34_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeDsl.g:3535:3: (otherlv_35= 'hitSound' ( (lv_hitSound_36_0= RULE_STRING ) ) )?
            int alt66=2;
            int LA66_0 = input.LA(1);

            if ( (LA66_0==110) ) {
                alt66=1;
            }
            switch (alt66) {
                case 1 :
                    // InternalMazeDsl.g:3536:4: otherlv_35= 'hitSound' ( (lv_hitSound_36_0= RULE_STRING ) )
                    {
                    otherlv_35=(Token)match(input,110,FOLLOW_10); 

                    				newLeafNode(otherlv_35, grammarAccess.getGuardTowerConfigAccess().getHitSoundKeyword_32_0());
                    			
                    // InternalMazeDsl.g:3540:4: ( (lv_hitSound_36_0= RULE_STRING ) )
                    // InternalMazeDsl.g:3541:5: (lv_hitSound_36_0= RULE_STRING )
                    {
                    // InternalMazeDsl.g:3541:5: (lv_hitSound_36_0= RULE_STRING )
                    // InternalMazeDsl.g:3542:6: lv_hitSound_36_0= RULE_STRING
                    {
                    lv_hitSound_36_0=(Token)match(input,RULE_STRING,FOLLOW_118); 

                    						newLeafNode(lv_hitSound_36_0, grammarAccess.getGuardTowerConfigAccess().getHitSoundSTRINGTerminalRuleCall_32_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getGuardTowerConfigRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"hitSound",
                    							lv_hitSound_36_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeDsl.g:3559:3: (otherlv_37= 'destroyedSound' ( (lv_destroyedSound_38_0= RULE_STRING ) ) )?
            int alt67=2;
            int LA67_0 = input.LA(1);

            if ( (LA67_0==111) ) {
                alt67=1;
            }
            switch (alt67) {
                case 1 :
                    // InternalMazeDsl.g:3560:4: otherlv_37= 'destroyedSound' ( (lv_destroyedSound_38_0= RULE_STRING ) )
                    {
                    otherlv_37=(Token)match(input,111,FOLLOW_10); 

                    				newLeafNode(otherlv_37, grammarAccess.getGuardTowerConfigAccess().getDestroyedSoundKeyword_33_0());
                    			
                    // InternalMazeDsl.g:3564:4: ( (lv_destroyedSound_38_0= RULE_STRING ) )
                    // InternalMazeDsl.g:3565:5: (lv_destroyedSound_38_0= RULE_STRING )
                    {
                    // InternalMazeDsl.g:3565:5: (lv_destroyedSound_38_0= RULE_STRING )
                    // InternalMazeDsl.g:3566:6: lv_destroyedSound_38_0= RULE_STRING
                    {
                    lv_destroyedSound_38_0=(Token)match(input,RULE_STRING,FOLLOW_34); 

                    						newLeafNode(lv_destroyedSound_38_0, grammarAccess.getGuardTowerConfigAccess().getDestroyedSoundSTRINGTerminalRuleCall_33_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getGuardTowerConfigRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"destroyedSound",
                    							lv_destroyedSound_38_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_39=(Token)match(input,13,FOLLOW_2); 

            			newLeafNode(otherlv_39, grammarAccess.getGuardTowerConfigAccess().getRightCurlyBracketKeyword_34());
            		

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
    // $ANTLR end "ruleGuardTowerConfig"


    // $ANTLR start "entryRuleTowerCountConfig"
    // InternalMazeDsl.g:3591:1: entryRuleTowerCountConfig returns [EObject current=null] : iv_ruleTowerCountConfig= ruleTowerCountConfig EOF ;
    public final EObject entryRuleTowerCountConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTowerCountConfig = null;


        try {
            // InternalMazeDsl.g:3591:57: (iv_ruleTowerCountConfig= ruleTowerCountConfig EOF )
            // InternalMazeDsl.g:3592:2: iv_ruleTowerCountConfig= ruleTowerCountConfig EOF
            {
             newCompositeNode(grammarAccess.getTowerCountConfigRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleTowerCountConfig=ruleTowerCountConfig();

            state._fsp--;

             current =iv_ruleTowerCountConfig; 
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
    // $ANTLR end "entryRuleTowerCountConfig"


    // $ANTLR start "ruleTowerCountConfig"
    // InternalMazeDsl.g:3598:1: ruleTowerCountConfig returns [EObject current=null] : (otherlv_0= 'towers' ( (lv_difficulty_1_0= ruleDifficultyLevel ) ) ( (lv_count_2_0= RULE_INT ) ) ) ;
    public final EObject ruleTowerCountConfig() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_count_2_0=null;
        Enumerator lv_difficulty_1_0 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:3604:2: ( (otherlv_0= 'towers' ( (lv_difficulty_1_0= ruleDifficultyLevel ) ) ( (lv_count_2_0= RULE_INT ) ) ) )
            // InternalMazeDsl.g:3605:2: (otherlv_0= 'towers' ( (lv_difficulty_1_0= ruleDifficultyLevel ) ) ( (lv_count_2_0= RULE_INT ) ) )
            {
            // InternalMazeDsl.g:3605:2: (otherlv_0= 'towers' ( (lv_difficulty_1_0= ruleDifficultyLevel ) ) ( (lv_count_2_0= RULE_INT ) ) )
            // InternalMazeDsl.g:3606:3: otherlv_0= 'towers' ( (lv_difficulty_1_0= ruleDifficultyLevel ) ) ( (lv_count_2_0= RULE_INT ) )
            {
            otherlv_0=(Token)match(input,112,FOLLOW_12); 

            			newLeafNode(otherlv_0, grammarAccess.getTowerCountConfigAccess().getTowersKeyword_0());
            		
            // InternalMazeDsl.g:3610:3: ( (lv_difficulty_1_0= ruleDifficultyLevel ) )
            // InternalMazeDsl.g:3611:4: (lv_difficulty_1_0= ruleDifficultyLevel )
            {
            // InternalMazeDsl.g:3611:4: (lv_difficulty_1_0= ruleDifficultyLevel )
            // InternalMazeDsl.g:3612:5: lv_difficulty_1_0= ruleDifficultyLevel
            {

            					newCompositeNode(grammarAccess.getTowerCountConfigAccess().getDifficultyDifficultyLevelEnumRuleCall_1_0());
            				
            pushFollow(FOLLOW_36);
            lv_difficulty_1_0=ruleDifficultyLevel();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getTowerCountConfigRule());
            					}
            					set(
            						current,
            						"difficulty",
            						lv_difficulty_1_0,
            						"main.game.maze.dsl.MazeDsl.DifficultyLevel");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalMazeDsl.g:3629:3: ( (lv_count_2_0= RULE_INT ) )
            // InternalMazeDsl.g:3630:4: (lv_count_2_0= RULE_INT )
            {
            // InternalMazeDsl.g:3630:4: (lv_count_2_0= RULE_INT )
            // InternalMazeDsl.g:3631:5: lv_count_2_0= RULE_INT
            {
            lv_count_2_0=(Token)match(input,RULE_INT,FOLLOW_2); 

            					newLeafNode(lv_count_2_0, grammarAccess.getTowerCountConfigAccess().getCountINTTerminalRuleCall_2_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getTowerCountConfigRule());
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
    // $ANTLR end "ruleTowerCountConfig"


    // $ANTLR start "entryRuleWallBuilderConfig"
    // InternalMazeDsl.g:3651:1: entryRuleWallBuilderConfig returns [EObject current=null] : iv_ruleWallBuilderConfig= ruleWallBuilderConfig EOF ;
    public final EObject entryRuleWallBuilderConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleWallBuilderConfig = null;


        try {
            // InternalMazeDsl.g:3651:58: (iv_ruleWallBuilderConfig= ruleWallBuilderConfig EOF )
            // InternalMazeDsl.g:3652:2: iv_ruleWallBuilderConfig= ruleWallBuilderConfig EOF
            {
             newCompositeNode(grammarAccess.getWallBuilderConfigRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleWallBuilderConfig=ruleWallBuilderConfig();

            state._fsp--;

             current =iv_ruleWallBuilderConfig; 
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
    // $ANTLR end "entryRuleWallBuilderConfig"


    // $ANTLR start "ruleWallBuilderConfig"
    // InternalMazeDsl.g:3658:1: ruleWallBuilderConfig returns [EObject current=null] : (otherlv_0= 'wallBuilder' ( (lv_displayName_1_0= RULE_STRING ) ) otherlv_2= '{' otherlv_3= 'health' ( (lv_health_4_0= RULE_INT ) ) otherlv_5= 'speed' ( (lv_speed_6_0= ruleDOUBLE ) ) otherlv_7= 'sprite' ( (lv_sprite_8_0= RULE_STRING ) ) otherlv_9= 'buildSeconds' ( (lv_buildSecondsPerCorridor_10_0= ruleDOUBLE ) ) otherlv_11= 'startRadius' ( (lv_startRadiusCells_12_0= RULE_INT ) ) otherlv_13= 'blockRadius' ( (lv_blockRadiusCells_14_0= RULE_INT ) ) otherlv_15= 'spawnRadius' ( (lv_spawnRadius_16_0= ruleDOUBLE ) ) otherlv_17= 'material' ( (lv_wallMaterial_18_0= ruleValidID ) ) (otherlv_19= 'buildSound' ( (lv_buildSound_20_0= RULE_STRING ) ) )? otherlv_21= '}' ) ;
    public final EObject ruleWallBuilderConfig() throws RecognitionException {
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
        Token otherlv_11=null;
        Token lv_startRadiusCells_12_0=null;
        Token otherlv_13=null;
        Token lv_blockRadiusCells_14_0=null;
        Token otherlv_15=null;
        Token otherlv_17=null;
        Token otherlv_19=null;
        Token lv_buildSound_20_0=null;
        Token otherlv_21=null;
        AntlrDatatypeRuleToken lv_speed_6_0 = null;

        AntlrDatatypeRuleToken lv_buildSecondsPerCorridor_10_0 = null;

        AntlrDatatypeRuleToken lv_spawnRadius_16_0 = null;

        AntlrDatatypeRuleToken lv_wallMaterial_18_0 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:3664:2: ( (otherlv_0= 'wallBuilder' ( (lv_displayName_1_0= RULE_STRING ) ) otherlv_2= '{' otherlv_3= 'health' ( (lv_health_4_0= RULE_INT ) ) otherlv_5= 'speed' ( (lv_speed_6_0= ruleDOUBLE ) ) otherlv_7= 'sprite' ( (lv_sprite_8_0= RULE_STRING ) ) otherlv_9= 'buildSeconds' ( (lv_buildSecondsPerCorridor_10_0= ruleDOUBLE ) ) otherlv_11= 'startRadius' ( (lv_startRadiusCells_12_0= RULE_INT ) ) otherlv_13= 'blockRadius' ( (lv_blockRadiusCells_14_0= RULE_INT ) ) otherlv_15= 'spawnRadius' ( (lv_spawnRadius_16_0= ruleDOUBLE ) ) otherlv_17= 'material' ( (lv_wallMaterial_18_0= ruleValidID ) ) (otherlv_19= 'buildSound' ( (lv_buildSound_20_0= RULE_STRING ) ) )? otherlv_21= '}' ) )
            // InternalMazeDsl.g:3665:2: (otherlv_0= 'wallBuilder' ( (lv_displayName_1_0= RULE_STRING ) ) otherlv_2= '{' otherlv_3= 'health' ( (lv_health_4_0= RULE_INT ) ) otherlv_5= 'speed' ( (lv_speed_6_0= ruleDOUBLE ) ) otherlv_7= 'sprite' ( (lv_sprite_8_0= RULE_STRING ) ) otherlv_9= 'buildSeconds' ( (lv_buildSecondsPerCorridor_10_0= ruleDOUBLE ) ) otherlv_11= 'startRadius' ( (lv_startRadiusCells_12_0= RULE_INT ) ) otherlv_13= 'blockRadius' ( (lv_blockRadiusCells_14_0= RULE_INT ) ) otherlv_15= 'spawnRadius' ( (lv_spawnRadius_16_0= ruleDOUBLE ) ) otherlv_17= 'material' ( (lv_wallMaterial_18_0= ruleValidID ) ) (otherlv_19= 'buildSound' ( (lv_buildSound_20_0= RULE_STRING ) ) )? otherlv_21= '}' )
            {
            // InternalMazeDsl.g:3665:2: (otherlv_0= 'wallBuilder' ( (lv_displayName_1_0= RULE_STRING ) ) otherlv_2= '{' otherlv_3= 'health' ( (lv_health_4_0= RULE_INT ) ) otherlv_5= 'speed' ( (lv_speed_6_0= ruleDOUBLE ) ) otherlv_7= 'sprite' ( (lv_sprite_8_0= RULE_STRING ) ) otherlv_9= 'buildSeconds' ( (lv_buildSecondsPerCorridor_10_0= ruleDOUBLE ) ) otherlv_11= 'startRadius' ( (lv_startRadiusCells_12_0= RULE_INT ) ) otherlv_13= 'blockRadius' ( (lv_blockRadiusCells_14_0= RULE_INT ) ) otherlv_15= 'spawnRadius' ( (lv_spawnRadius_16_0= ruleDOUBLE ) ) otherlv_17= 'material' ( (lv_wallMaterial_18_0= ruleValidID ) ) (otherlv_19= 'buildSound' ( (lv_buildSound_20_0= RULE_STRING ) ) )? otherlv_21= '}' )
            // InternalMazeDsl.g:3666:3: otherlv_0= 'wallBuilder' ( (lv_displayName_1_0= RULE_STRING ) ) otherlv_2= '{' otherlv_3= 'health' ( (lv_health_4_0= RULE_INT ) ) otherlv_5= 'speed' ( (lv_speed_6_0= ruleDOUBLE ) ) otherlv_7= 'sprite' ( (lv_sprite_8_0= RULE_STRING ) ) otherlv_9= 'buildSeconds' ( (lv_buildSecondsPerCorridor_10_0= ruleDOUBLE ) ) otherlv_11= 'startRadius' ( (lv_startRadiusCells_12_0= RULE_INT ) ) otherlv_13= 'blockRadius' ( (lv_blockRadiusCells_14_0= RULE_INT ) ) otherlv_15= 'spawnRadius' ( (lv_spawnRadius_16_0= ruleDOUBLE ) ) otherlv_17= 'material' ( (lv_wallMaterial_18_0= ruleValidID ) ) (otherlv_19= 'buildSound' ( (lv_buildSound_20_0= RULE_STRING ) ) )? otherlv_21= '}'
            {
            otherlv_0=(Token)match(input,113,FOLLOW_10); 

            			newLeafNode(otherlv_0, grammarAccess.getWallBuilderConfigAccess().getWallBuilderKeyword_0());
            		
            // InternalMazeDsl.g:3670:3: ( (lv_displayName_1_0= RULE_STRING ) )
            // InternalMazeDsl.g:3671:4: (lv_displayName_1_0= RULE_STRING )
            {
            // InternalMazeDsl.g:3671:4: (lv_displayName_1_0= RULE_STRING )
            // InternalMazeDsl.g:3672:5: lv_displayName_1_0= RULE_STRING
            {
            lv_displayName_1_0=(Token)match(input,RULE_STRING,FOLLOW_4); 

            					newLeafNode(lv_displayName_1_0, grammarAccess.getWallBuilderConfigAccess().getDisplayNameSTRINGTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getWallBuilderConfigRule());
            					}
            					setWithLastConsumed(
            						current,
            						"displayName",
            						lv_displayName_1_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            otherlv_2=(Token)match(input,12,FOLLOW_83); 

            			newLeafNode(otherlv_2, grammarAccess.getWallBuilderConfigAccess().getLeftCurlyBracketKeyword_2());
            		
            otherlv_3=(Token)match(input,27,FOLLOW_36); 

            			newLeafNode(otherlv_3, grammarAccess.getWallBuilderConfigAccess().getHealthKeyword_3());
            		
            // InternalMazeDsl.g:3696:3: ( (lv_health_4_0= RULE_INT ) )
            // InternalMazeDsl.g:3697:4: (lv_health_4_0= RULE_INT )
            {
            // InternalMazeDsl.g:3697:4: (lv_health_4_0= RULE_INT )
            // InternalMazeDsl.g:3698:5: lv_health_4_0= RULE_INT
            {
            lv_health_4_0=(Token)match(input,RULE_INT,FOLLOW_103); 

            					newLeafNode(lv_health_4_0, grammarAccess.getWallBuilderConfigAccess().getHealthINTTerminalRuleCall_4_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getWallBuilderConfigRule());
            					}
            					setWithLastConsumed(
            						current,
            						"health",
            						lv_health_4_0,
            						"org.eclipse.xtext.common.Terminals.INT");
            				

            }


            }

            otherlv_5=(Token)match(input,28,FOLLOW_16); 

            			newLeafNode(otherlv_5, grammarAccess.getWallBuilderConfigAccess().getSpeedKeyword_5());
            		
            // InternalMazeDsl.g:3718:3: ( (lv_speed_6_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:3719:4: (lv_speed_6_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:3719:4: (lv_speed_6_0= ruleDOUBLE )
            // InternalMazeDsl.g:3720:5: lv_speed_6_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getWallBuilderConfigAccess().getSpeedDOUBLEParserRuleCall_6_0());
            				
            pushFollow(FOLLOW_89);
            lv_speed_6_0=ruleDOUBLE();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getWallBuilderConfigRule());
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

            			newLeafNode(otherlv_7, grammarAccess.getWallBuilderConfigAccess().getSpriteKeyword_7());
            		
            // InternalMazeDsl.g:3741:3: ( (lv_sprite_8_0= RULE_STRING ) )
            // InternalMazeDsl.g:3742:4: (lv_sprite_8_0= RULE_STRING )
            {
            // InternalMazeDsl.g:3742:4: (lv_sprite_8_0= RULE_STRING )
            // InternalMazeDsl.g:3743:5: lv_sprite_8_0= RULE_STRING
            {
            lv_sprite_8_0=(Token)match(input,RULE_STRING,FOLLOW_119); 

            					newLeafNode(lv_sprite_8_0, grammarAccess.getWallBuilderConfigAccess().getSpriteSTRINGTerminalRuleCall_8_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getWallBuilderConfigRule());
            					}
            					setWithLastConsumed(
            						current,
            						"sprite",
            						lv_sprite_8_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            otherlv_9=(Token)match(input,114,FOLLOW_16); 

            			newLeafNode(otherlv_9, grammarAccess.getWallBuilderConfigAccess().getBuildSecondsKeyword_9());
            		
            // InternalMazeDsl.g:3763:3: ( (lv_buildSecondsPerCorridor_10_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:3764:4: (lv_buildSecondsPerCorridor_10_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:3764:4: (lv_buildSecondsPerCorridor_10_0= ruleDOUBLE )
            // InternalMazeDsl.g:3765:5: lv_buildSecondsPerCorridor_10_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getWallBuilderConfigAccess().getBuildSecondsPerCorridorDOUBLEParserRuleCall_10_0());
            				
            pushFollow(FOLLOW_120);
            lv_buildSecondsPerCorridor_10_0=ruleDOUBLE();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getWallBuilderConfigRule());
            					}
            					set(
            						current,
            						"buildSecondsPerCorridor",
            						lv_buildSecondsPerCorridor_10_0,
            						"main.game.maze.dsl.MazeDsl.DOUBLE");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_11=(Token)match(input,115,FOLLOW_36); 

            			newLeafNode(otherlv_11, grammarAccess.getWallBuilderConfigAccess().getStartRadiusKeyword_11());
            		
            // InternalMazeDsl.g:3786:3: ( (lv_startRadiusCells_12_0= RULE_INT ) )
            // InternalMazeDsl.g:3787:4: (lv_startRadiusCells_12_0= RULE_INT )
            {
            // InternalMazeDsl.g:3787:4: (lv_startRadiusCells_12_0= RULE_INT )
            // InternalMazeDsl.g:3788:5: lv_startRadiusCells_12_0= RULE_INT
            {
            lv_startRadiusCells_12_0=(Token)match(input,RULE_INT,FOLLOW_121); 

            					newLeafNode(lv_startRadiusCells_12_0, grammarAccess.getWallBuilderConfigAccess().getStartRadiusCellsINTTerminalRuleCall_12_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getWallBuilderConfigRule());
            					}
            					setWithLastConsumed(
            						current,
            						"startRadiusCells",
            						lv_startRadiusCells_12_0,
            						"org.eclipse.xtext.common.Terminals.INT");
            				

            }


            }

            otherlv_13=(Token)match(input,116,FOLLOW_36); 

            			newLeafNode(otherlv_13, grammarAccess.getWallBuilderConfigAccess().getBlockRadiusKeyword_13());
            		
            // InternalMazeDsl.g:3808:3: ( (lv_blockRadiusCells_14_0= RULE_INT ) )
            // InternalMazeDsl.g:3809:4: (lv_blockRadiusCells_14_0= RULE_INT )
            {
            // InternalMazeDsl.g:3809:4: (lv_blockRadiusCells_14_0= RULE_INT )
            // InternalMazeDsl.g:3810:5: lv_blockRadiusCells_14_0= RULE_INT
            {
            lv_blockRadiusCells_14_0=(Token)match(input,RULE_INT,FOLLOW_122); 

            					newLeafNode(lv_blockRadiusCells_14_0, grammarAccess.getWallBuilderConfigAccess().getBlockRadiusCellsINTTerminalRuleCall_14_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getWallBuilderConfigRule());
            					}
            					setWithLastConsumed(
            						current,
            						"blockRadiusCells",
            						lv_blockRadiusCells_14_0,
            						"org.eclipse.xtext.common.Terminals.INT");
            				

            }


            }

            otherlv_15=(Token)match(input,117,FOLLOW_16); 

            			newLeafNode(otherlv_15, grammarAccess.getWallBuilderConfigAccess().getSpawnRadiusKeyword_15());
            		
            // InternalMazeDsl.g:3830:3: ( (lv_spawnRadius_16_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:3831:4: (lv_spawnRadius_16_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:3831:4: (lv_spawnRadius_16_0= ruleDOUBLE )
            // InternalMazeDsl.g:3832:5: lv_spawnRadius_16_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getWallBuilderConfigAccess().getSpawnRadiusDOUBLEParserRuleCall_16_0());
            				
            pushFollow(FOLLOW_123);
            lv_spawnRadius_16_0=ruleDOUBLE();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getWallBuilderConfigRule());
            					}
            					set(
            						current,
            						"spawnRadius",
            						lv_spawnRadius_16_0,
            						"main.game.maze.dsl.MazeDsl.DOUBLE");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_17=(Token)match(input,118,FOLLOW_3); 

            			newLeafNode(otherlv_17, grammarAccess.getWallBuilderConfigAccess().getMaterialKeyword_17());
            		
            // InternalMazeDsl.g:3853:3: ( (lv_wallMaterial_18_0= ruleValidID ) )
            // InternalMazeDsl.g:3854:4: (lv_wallMaterial_18_0= ruleValidID )
            {
            // InternalMazeDsl.g:3854:4: (lv_wallMaterial_18_0= ruleValidID )
            // InternalMazeDsl.g:3855:5: lv_wallMaterial_18_0= ruleValidID
            {

            					newCompositeNode(grammarAccess.getWallBuilderConfigAccess().getWallMaterialValidIDParserRuleCall_18_0());
            				
            pushFollow(FOLLOW_124);
            lv_wallMaterial_18_0=ruleValidID();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getWallBuilderConfigRule());
            					}
            					set(
            						current,
            						"wallMaterial",
            						lv_wallMaterial_18_0,
            						"main.game.maze.dsl.MazeDsl.ValidID");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalMazeDsl.g:3872:3: (otherlv_19= 'buildSound' ( (lv_buildSound_20_0= RULE_STRING ) ) )?
            int alt68=2;
            int LA68_0 = input.LA(1);

            if ( (LA68_0==119) ) {
                alt68=1;
            }
            switch (alt68) {
                case 1 :
                    // InternalMazeDsl.g:3873:4: otherlv_19= 'buildSound' ( (lv_buildSound_20_0= RULE_STRING ) )
                    {
                    otherlv_19=(Token)match(input,119,FOLLOW_10); 

                    				newLeafNode(otherlv_19, grammarAccess.getWallBuilderConfigAccess().getBuildSoundKeyword_19_0());
                    			
                    // InternalMazeDsl.g:3877:4: ( (lv_buildSound_20_0= RULE_STRING ) )
                    // InternalMazeDsl.g:3878:5: (lv_buildSound_20_0= RULE_STRING )
                    {
                    // InternalMazeDsl.g:3878:5: (lv_buildSound_20_0= RULE_STRING )
                    // InternalMazeDsl.g:3879:6: lv_buildSound_20_0= RULE_STRING
                    {
                    lv_buildSound_20_0=(Token)match(input,RULE_STRING,FOLLOW_34); 

                    						newLeafNode(lv_buildSound_20_0, grammarAccess.getWallBuilderConfigAccess().getBuildSoundSTRINGTerminalRuleCall_19_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getWallBuilderConfigRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"buildSound",
                    							lv_buildSound_20_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_21=(Token)match(input,13,FOLLOW_2); 

            			newLeafNode(otherlv_21, grammarAccess.getWallBuilderConfigAccess().getRightCurlyBracketKeyword_20());
            		

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
    // $ANTLR end "ruleWallBuilderConfig"


    // $ANTLR start "entryRuleBuilderCountConfig"
    // InternalMazeDsl.g:3904:1: entryRuleBuilderCountConfig returns [EObject current=null] : iv_ruleBuilderCountConfig= ruleBuilderCountConfig EOF ;
    public final EObject entryRuleBuilderCountConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleBuilderCountConfig = null;


        try {
            // InternalMazeDsl.g:3904:59: (iv_ruleBuilderCountConfig= ruleBuilderCountConfig EOF )
            // InternalMazeDsl.g:3905:2: iv_ruleBuilderCountConfig= ruleBuilderCountConfig EOF
            {
             newCompositeNode(grammarAccess.getBuilderCountConfigRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleBuilderCountConfig=ruleBuilderCountConfig();

            state._fsp--;

             current =iv_ruleBuilderCountConfig; 
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
    // $ANTLR end "entryRuleBuilderCountConfig"


    // $ANTLR start "ruleBuilderCountConfig"
    // InternalMazeDsl.g:3911:1: ruleBuilderCountConfig returns [EObject current=null] : (otherlv_0= 'builders' ( (lv_difficulty_1_0= ruleDifficultyLevel ) ) ( (lv_count_2_0= RULE_INT ) ) ) ;
    public final EObject ruleBuilderCountConfig() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_count_2_0=null;
        Enumerator lv_difficulty_1_0 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:3917:2: ( (otherlv_0= 'builders' ( (lv_difficulty_1_0= ruleDifficultyLevel ) ) ( (lv_count_2_0= RULE_INT ) ) ) )
            // InternalMazeDsl.g:3918:2: (otherlv_0= 'builders' ( (lv_difficulty_1_0= ruleDifficultyLevel ) ) ( (lv_count_2_0= RULE_INT ) ) )
            {
            // InternalMazeDsl.g:3918:2: (otherlv_0= 'builders' ( (lv_difficulty_1_0= ruleDifficultyLevel ) ) ( (lv_count_2_0= RULE_INT ) ) )
            // InternalMazeDsl.g:3919:3: otherlv_0= 'builders' ( (lv_difficulty_1_0= ruleDifficultyLevel ) ) ( (lv_count_2_0= RULE_INT ) )
            {
            otherlv_0=(Token)match(input,120,FOLLOW_12); 

            			newLeafNode(otherlv_0, grammarAccess.getBuilderCountConfigAccess().getBuildersKeyword_0());
            		
            // InternalMazeDsl.g:3923:3: ( (lv_difficulty_1_0= ruleDifficultyLevel ) )
            // InternalMazeDsl.g:3924:4: (lv_difficulty_1_0= ruleDifficultyLevel )
            {
            // InternalMazeDsl.g:3924:4: (lv_difficulty_1_0= ruleDifficultyLevel )
            // InternalMazeDsl.g:3925:5: lv_difficulty_1_0= ruleDifficultyLevel
            {

            					newCompositeNode(grammarAccess.getBuilderCountConfigAccess().getDifficultyDifficultyLevelEnumRuleCall_1_0());
            				
            pushFollow(FOLLOW_36);
            lv_difficulty_1_0=ruleDifficultyLevel();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getBuilderCountConfigRule());
            					}
            					set(
            						current,
            						"difficulty",
            						lv_difficulty_1_0,
            						"main.game.maze.dsl.MazeDsl.DifficultyLevel");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalMazeDsl.g:3942:3: ( (lv_count_2_0= RULE_INT ) )
            // InternalMazeDsl.g:3943:4: (lv_count_2_0= RULE_INT )
            {
            // InternalMazeDsl.g:3943:4: (lv_count_2_0= RULE_INT )
            // InternalMazeDsl.g:3944:5: lv_count_2_0= RULE_INT
            {
            lv_count_2_0=(Token)match(input,RULE_INT,FOLLOW_2); 

            					newLeafNode(lv_count_2_0, grammarAccess.getBuilderCountConfigAccess().getCountINTTerminalRuleCall_2_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getBuilderCountConfigRule());
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
    // $ANTLR end "ruleBuilderCountConfig"


    // $ANTLR start "entryRuleLavaConfig"
    // InternalMazeDsl.g:3964:1: entryRuleLavaConfig returns [EObject current=null] : iv_ruleLavaConfig= ruleLavaConfig EOF ;
    public final EObject entryRuleLavaConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLavaConfig = null;


        try {
            // InternalMazeDsl.g:3964:51: (iv_ruleLavaConfig= ruleLavaConfig EOF )
            // InternalMazeDsl.g:3965:2: iv_ruleLavaConfig= ruleLavaConfig EOF
            {
             newCompositeNode(grammarAccess.getLavaConfigRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleLavaConfig=ruleLavaConfig();

            state._fsp--;

             current =iv_ruleLavaConfig; 
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
    // $ANTLR end "entryRuleLavaConfig"


    // $ANTLR start "ruleLavaConfig"
    // InternalMazeDsl.g:3971:1: ruleLavaConfig returns [EObject current=null] : (otherlv_0= 'lava' otherlv_1= '{' otherlv_2= 'streams' ( (lv_streamCount_3_0= RULE_INT ) ) otherlv_4= 'width' ( (lv_width_5_0= ruleDOUBLE ) ) otherlv_6= 'bridges' ( (lv_bridgesPerStream_7_0= RULE_INT ) ) otherlv_8= 'bridgeLength' ( (lv_bridgeLength_9_0= ruleDOUBLE ) ) (otherlv_10= 'meander' ( (lv_meander_11_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_12= 'wavelength' ( (lv_wavelength_13_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_14= 'flowSpeed' ( (lv_flowSpeed_15_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_16= 'glow' ( (lv_glow_17_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_18= 'crust' ( (lv_crust_19_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_20= 'deathSound' ( (lv_deathSound_21_0= RULE_STRING ) ) )? otherlv_22= '}' ) ;
    public final EObject ruleLavaConfig() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token lv_streamCount_3_0=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token lv_bridgesPerStream_7_0=null;
        Token otherlv_8=null;
        Token otherlv_10=null;
        Token otherlv_12=null;
        Token otherlv_14=null;
        Token otherlv_16=null;
        Token otherlv_18=null;
        Token otherlv_20=null;
        Token lv_deathSound_21_0=null;
        Token otherlv_22=null;
        AntlrDatatypeRuleToken lv_width_5_0 = null;

        AntlrDatatypeRuleToken lv_bridgeLength_9_0 = null;

        AntlrDatatypeRuleToken lv_meander_11_0 = null;

        AntlrDatatypeRuleToken lv_wavelength_13_0 = null;

        AntlrDatatypeRuleToken lv_flowSpeed_15_0 = null;

        AntlrDatatypeRuleToken lv_glow_17_0 = null;

        AntlrDatatypeRuleToken lv_crust_19_0 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:3977:2: ( (otherlv_0= 'lava' otherlv_1= '{' otherlv_2= 'streams' ( (lv_streamCount_3_0= RULE_INT ) ) otherlv_4= 'width' ( (lv_width_5_0= ruleDOUBLE ) ) otherlv_6= 'bridges' ( (lv_bridgesPerStream_7_0= RULE_INT ) ) otherlv_8= 'bridgeLength' ( (lv_bridgeLength_9_0= ruleDOUBLE ) ) (otherlv_10= 'meander' ( (lv_meander_11_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_12= 'wavelength' ( (lv_wavelength_13_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_14= 'flowSpeed' ( (lv_flowSpeed_15_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_16= 'glow' ( (lv_glow_17_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_18= 'crust' ( (lv_crust_19_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_20= 'deathSound' ( (lv_deathSound_21_0= RULE_STRING ) ) )? otherlv_22= '}' ) )
            // InternalMazeDsl.g:3978:2: (otherlv_0= 'lava' otherlv_1= '{' otherlv_2= 'streams' ( (lv_streamCount_3_0= RULE_INT ) ) otherlv_4= 'width' ( (lv_width_5_0= ruleDOUBLE ) ) otherlv_6= 'bridges' ( (lv_bridgesPerStream_7_0= RULE_INT ) ) otherlv_8= 'bridgeLength' ( (lv_bridgeLength_9_0= ruleDOUBLE ) ) (otherlv_10= 'meander' ( (lv_meander_11_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_12= 'wavelength' ( (lv_wavelength_13_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_14= 'flowSpeed' ( (lv_flowSpeed_15_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_16= 'glow' ( (lv_glow_17_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_18= 'crust' ( (lv_crust_19_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_20= 'deathSound' ( (lv_deathSound_21_0= RULE_STRING ) ) )? otherlv_22= '}' )
            {
            // InternalMazeDsl.g:3978:2: (otherlv_0= 'lava' otherlv_1= '{' otherlv_2= 'streams' ( (lv_streamCount_3_0= RULE_INT ) ) otherlv_4= 'width' ( (lv_width_5_0= ruleDOUBLE ) ) otherlv_6= 'bridges' ( (lv_bridgesPerStream_7_0= RULE_INT ) ) otherlv_8= 'bridgeLength' ( (lv_bridgeLength_9_0= ruleDOUBLE ) ) (otherlv_10= 'meander' ( (lv_meander_11_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_12= 'wavelength' ( (lv_wavelength_13_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_14= 'flowSpeed' ( (lv_flowSpeed_15_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_16= 'glow' ( (lv_glow_17_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_18= 'crust' ( (lv_crust_19_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_20= 'deathSound' ( (lv_deathSound_21_0= RULE_STRING ) ) )? otherlv_22= '}' )
            // InternalMazeDsl.g:3979:3: otherlv_0= 'lava' otherlv_1= '{' otherlv_2= 'streams' ( (lv_streamCount_3_0= RULE_INT ) ) otherlv_4= 'width' ( (lv_width_5_0= ruleDOUBLE ) ) otherlv_6= 'bridges' ( (lv_bridgesPerStream_7_0= RULE_INT ) ) otherlv_8= 'bridgeLength' ( (lv_bridgeLength_9_0= ruleDOUBLE ) ) (otherlv_10= 'meander' ( (lv_meander_11_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_12= 'wavelength' ( (lv_wavelength_13_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_14= 'flowSpeed' ( (lv_flowSpeed_15_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_16= 'glow' ( (lv_glow_17_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_18= 'crust' ( (lv_crust_19_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_20= 'deathSound' ( (lv_deathSound_21_0= RULE_STRING ) ) )? otherlv_22= '}'
            {
            otherlv_0=(Token)match(input,121,FOLLOW_4); 

            			newLeafNode(otherlv_0, grammarAccess.getLavaConfigAccess().getLavaKeyword_0());
            		
            otherlv_1=(Token)match(input,12,FOLLOW_125); 

            			newLeafNode(otherlv_1, grammarAccess.getLavaConfigAccess().getLeftCurlyBracketKeyword_1());
            		
            otherlv_2=(Token)match(input,122,FOLLOW_36); 

            			newLeafNode(otherlv_2, grammarAccess.getLavaConfigAccess().getStreamsKeyword_2());
            		
            // InternalMazeDsl.g:3991:3: ( (lv_streamCount_3_0= RULE_INT ) )
            // InternalMazeDsl.g:3992:4: (lv_streamCount_3_0= RULE_INT )
            {
            // InternalMazeDsl.g:3992:4: (lv_streamCount_3_0= RULE_INT )
            // InternalMazeDsl.g:3993:5: lv_streamCount_3_0= RULE_INT
            {
            lv_streamCount_3_0=(Token)match(input,RULE_INT,FOLLOW_58); 

            					newLeafNode(lv_streamCount_3_0, grammarAccess.getLavaConfigAccess().getStreamCountINTTerminalRuleCall_3_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getLavaConfigRule());
            					}
            					setWithLastConsumed(
            						current,
            						"streamCount",
            						lv_streamCount_3_0,
            						"org.eclipse.xtext.common.Terminals.INT");
            				

            }


            }

            otherlv_4=(Token)match(input,56,FOLLOW_16); 

            			newLeafNode(otherlv_4, grammarAccess.getLavaConfigAccess().getWidthKeyword_4());
            		
            // InternalMazeDsl.g:4013:3: ( (lv_width_5_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:4014:4: (lv_width_5_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:4014:4: (lv_width_5_0= ruleDOUBLE )
            // InternalMazeDsl.g:4015:5: lv_width_5_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getLavaConfigAccess().getWidthDOUBLEParserRuleCall_5_0());
            				
            pushFollow(FOLLOW_126);
            lv_width_5_0=ruleDOUBLE();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getLavaConfigRule());
            					}
            					set(
            						current,
            						"width",
            						lv_width_5_0,
            						"main.game.maze.dsl.MazeDsl.DOUBLE");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_6=(Token)match(input,123,FOLLOW_36); 

            			newLeafNode(otherlv_6, grammarAccess.getLavaConfigAccess().getBridgesKeyword_6());
            		
            // InternalMazeDsl.g:4036:3: ( (lv_bridgesPerStream_7_0= RULE_INT ) )
            // InternalMazeDsl.g:4037:4: (lv_bridgesPerStream_7_0= RULE_INT )
            {
            // InternalMazeDsl.g:4037:4: (lv_bridgesPerStream_7_0= RULE_INT )
            // InternalMazeDsl.g:4038:5: lv_bridgesPerStream_7_0= RULE_INT
            {
            lv_bridgesPerStream_7_0=(Token)match(input,RULE_INT,FOLLOW_127); 

            					newLeafNode(lv_bridgesPerStream_7_0, grammarAccess.getLavaConfigAccess().getBridgesPerStreamINTTerminalRuleCall_7_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getLavaConfigRule());
            					}
            					setWithLastConsumed(
            						current,
            						"bridgesPerStream",
            						lv_bridgesPerStream_7_0,
            						"org.eclipse.xtext.common.Terminals.INT");
            				

            }


            }

            otherlv_8=(Token)match(input,124,FOLLOW_16); 

            			newLeafNode(otherlv_8, grammarAccess.getLavaConfigAccess().getBridgeLengthKeyword_8());
            		
            // InternalMazeDsl.g:4058:3: ( (lv_bridgeLength_9_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:4059:4: (lv_bridgeLength_9_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:4059:4: (lv_bridgeLength_9_0= ruleDOUBLE )
            // InternalMazeDsl.g:4060:5: lv_bridgeLength_9_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getLavaConfigAccess().getBridgeLengthDOUBLEParserRuleCall_9_0());
            				
            pushFollow(FOLLOW_128);
            lv_bridgeLength_9_0=ruleDOUBLE();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getLavaConfigRule());
            					}
            					set(
            						current,
            						"bridgeLength",
            						lv_bridgeLength_9_0,
            						"main.game.maze.dsl.MazeDsl.DOUBLE");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalMazeDsl.g:4077:3: (otherlv_10= 'meander' ( (lv_meander_11_0= ruleDOUBLE_OBJECT ) ) )?
            int alt69=2;
            int LA69_0 = input.LA(1);

            if ( (LA69_0==125) ) {
                alt69=1;
            }
            switch (alt69) {
                case 1 :
                    // InternalMazeDsl.g:4078:4: otherlv_10= 'meander' ( (lv_meander_11_0= ruleDOUBLE_OBJECT ) )
                    {
                    otherlv_10=(Token)match(input,125,FOLLOW_16); 

                    				newLeafNode(otherlv_10, grammarAccess.getLavaConfigAccess().getMeanderKeyword_10_0());
                    			
                    // InternalMazeDsl.g:4082:4: ( (lv_meander_11_0= ruleDOUBLE_OBJECT ) )
                    // InternalMazeDsl.g:4083:5: (lv_meander_11_0= ruleDOUBLE_OBJECT )
                    {
                    // InternalMazeDsl.g:4083:5: (lv_meander_11_0= ruleDOUBLE_OBJECT )
                    // InternalMazeDsl.g:4084:6: lv_meander_11_0= ruleDOUBLE_OBJECT
                    {

                    						newCompositeNode(grammarAccess.getLavaConfigAccess().getMeanderDOUBLE_OBJECTParserRuleCall_10_1_0());
                    					
                    pushFollow(FOLLOW_129);
                    lv_meander_11_0=ruleDOUBLE_OBJECT();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getLavaConfigRule());
                    						}
                    						set(
                    							current,
                    							"meander",
                    							lv_meander_11_0,
                    							"main.game.maze.dsl.MazeDsl.DOUBLE_OBJECT");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeDsl.g:4102:3: (otherlv_12= 'wavelength' ( (lv_wavelength_13_0= ruleDOUBLE_OBJECT ) ) )?
            int alt70=2;
            int LA70_0 = input.LA(1);

            if ( (LA70_0==126) ) {
                alt70=1;
            }
            switch (alt70) {
                case 1 :
                    // InternalMazeDsl.g:4103:4: otherlv_12= 'wavelength' ( (lv_wavelength_13_0= ruleDOUBLE_OBJECT ) )
                    {
                    otherlv_12=(Token)match(input,126,FOLLOW_16); 

                    				newLeafNode(otherlv_12, grammarAccess.getLavaConfigAccess().getWavelengthKeyword_11_0());
                    			
                    // InternalMazeDsl.g:4107:4: ( (lv_wavelength_13_0= ruleDOUBLE_OBJECT ) )
                    // InternalMazeDsl.g:4108:5: (lv_wavelength_13_0= ruleDOUBLE_OBJECT )
                    {
                    // InternalMazeDsl.g:4108:5: (lv_wavelength_13_0= ruleDOUBLE_OBJECT )
                    // InternalMazeDsl.g:4109:6: lv_wavelength_13_0= ruleDOUBLE_OBJECT
                    {

                    						newCompositeNode(grammarAccess.getLavaConfigAccess().getWavelengthDOUBLE_OBJECTParserRuleCall_11_1_0());
                    					
                    pushFollow(FOLLOW_130);
                    lv_wavelength_13_0=ruleDOUBLE_OBJECT();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getLavaConfigRule());
                    						}
                    						set(
                    							current,
                    							"wavelength",
                    							lv_wavelength_13_0,
                    							"main.game.maze.dsl.MazeDsl.DOUBLE_OBJECT");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeDsl.g:4127:3: (otherlv_14= 'flowSpeed' ( (lv_flowSpeed_15_0= ruleDOUBLE_OBJECT ) ) )?
            int alt71=2;
            int LA71_0 = input.LA(1);

            if ( (LA71_0==127) ) {
                alt71=1;
            }
            switch (alt71) {
                case 1 :
                    // InternalMazeDsl.g:4128:4: otherlv_14= 'flowSpeed' ( (lv_flowSpeed_15_0= ruleDOUBLE_OBJECT ) )
                    {
                    otherlv_14=(Token)match(input,127,FOLLOW_16); 

                    				newLeafNode(otherlv_14, grammarAccess.getLavaConfigAccess().getFlowSpeedKeyword_12_0());
                    			
                    // InternalMazeDsl.g:4132:4: ( (lv_flowSpeed_15_0= ruleDOUBLE_OBJECT ) )
                    // InternalMazeDsl.g:4133:5: (lv_flowSpeed_15_0= ruleDOUBLE_OBJECT )
                    {
                    // InternalMazeDsl.g:4133:5: (lv_flowSpeed_15_0= ruleDOUBLE_OBJECT )
                    // InternalMazeDsl.g:4134:6: lv_flowSpeed_15_0= ruleDOUBLE_OBJECT
                    {

                    						newCompositeNode(grammarAccess.getLavaConfigAccess().getFlowSpeedDOUBLE_OBJECTParserRuleCall_12_1_0());
                    					
                    pushFollow(FOLLOW_131);
                    lv_flowSpeed_15_0=ruleDOUBLE_OBJECT();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getLavaConfigRule());
                    						}
                    						set(
                    							current,
                    							"flowSpeed",
                    							lv_flowSpeed_15_0,
                    							"main.game.maze.dsl.MazeDsl.DOUBLE_OBJECT");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeDsl.g:4152:3: (otherlv_16= 'glow' ( (lv_glow_17_0= ruleDOUBLE_OBJECT ) ) )?
            int alt72=2;
            int LA72_0 = input.LA(1);

            if ( (LA72_0==128) ) {
                alt72=1;
            }
            switch (alt72) {
                case 1 :
                    // InternalMazeDsl.g:4153:4: otherlv_16= 'glow' ( (lv_glow_17_0= ruleDOUBLE_OBJECT ) )
                    {
                    otherlv_16=(Token)match(input,128,FOLLOW_16); 

                    				newLeafNode(otherlv_16, grammarAccess.getLavaConfigAccess().getGlowKeyword_13_0());
                    			
                    // InternalMazeDsl.g:4157:4: ( (lv_glow_17_0= ruleDOUBLE_OBJECT ) )
                    // InternalMazeDsl.g:4158:5: (lv_glow_17_0= ruleDOUBLE_OBJECT )
                    {
                    // InternalMazeDsl.g:4158:5: (lv_glow_17_0= ruleDOUBLE_OBJECT )
                    // InternalMazeDsl.g:4159:6: lv_glow_17_0= ruleDOUBLE_OBJECT
                    {

                    						newCompositeNode(grammarAccess.getLavaConfigAccess().getGlowDOUBLE_OBJECTParserRuleCall_13_1_0());
                    					
                    pushFollow(FOLLOW_132);
                    lv_glow_17_0=ruleDOUBLE_OBJECT();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getLavaConfigRule());
                    						}
                    						set(
                    							current,
                    							"glow",
                    							lv_glow_17_0,
                    							"main.game.maze.dsl.MazeDsl.DOUBLE_OBJECT");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeDsl.g:4177:3: (otherlv_18= 'crust' ( (lv_crust_19_0= ruleDOUBLE_OBJECT ) ) )?
            int alt73=2;
            int LA73_0 = input.LA(1);

            if ( (LA73_0==129) ) {
                alt73=1;
            }
            switch (alt73) {
                case 1 :
                    // InternalMazeDsl.g:4178:4: otherlv_18= 'crust' ( (lv_crust_19_0= ruleDOUBLE_OBJECT ) )
                    {
                    otherlv_18=(Token)match(input,129,FOLLOW_16); 

                    				newLeafNode(otherlv_18, grammarAccess.getLavaConfigAccess().getCrustKeyword_14_0());
                    			
                    // InternalMazeDsl.g:4182:4: ( (lv_crust_19_0= ruleDOUBLE_OBJECT ) )
                    // InternalMazeDsl.g:4183:5: (lv_crust_19_0= ruleDOUBLE_OBJECT )
                    {
                    // InternalMazeDsl.g:4183:5: (lv_crust_19_0= ruleDOUBLE_OBJECT )
                    // InternalMazeDsl.g:4184:6: lv_crust_19_0= ruleDOUBLE_OBJECT
                    {

                    						newCompositeNode(grammarAccess.getLavaConfigAccess().getCrustDOUBLE_OBJECTParserRuleCall_14_1_0());
                    					
                    pushFollow(FOLLOW_133);
                    lv_crust_19_0=ruleDOUBLE_OBJECT();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getLavaConfigRule());
                    						}
                    						set(
                    							current,
                    							"crust",
                    							lv_crust_19_0,
                    							"main.game.maze.dsl.MazeDsl.DOUBLE_OBJECT");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeDsl.g:4202:3: (otherlv_20= 'deathSound' ( (lv_deathSound_21_0= RULE_STRING ) ) )?
            int alt74=2;
            int LA74_0 = input.LA(1);

            if ( (LA74_0==130) ) {
                alt74=1;
            }
            switch (alt74) {
                case 1 :
                    // InternalMazeDsl.g:4203:4: otherlv_20= 'deathSound' ( (lv_deathSound_21_0= RULE_STRING ) )
                    {
                    otherlv_20=(Token)match(input,130,FOLLOW_10); 

                    				newLeafNode(otherlv_20, grammarAccess.getLavaConfigAccess().getDeathSoundKeyword_15_0());
                    			
                    // InternalMazeDsl.g:4207:4: ( (lv_deathSound_21_0= RULE_STRING ) )
                    // InternalMazeDsl.g:4208:5: (lv_deathSound_21_0= RULE_STRING )
                    {
                    // InternalMazeDsl.g:4208:5: (lv_deathSound_21_0= RULE_STRING )
                    // InternalMazeDsl.g:4209:6: lv_deathSound_21_0= RULE_STRING
                    {
                    lv_deathSound_21_0=(Token)match(input,RULE_STRING,FOLLOW_34); 

                    						newLeafNode(lv_deathSound_21_0, grammarAccess.getLavaConfigAccess().getDeathSoundSTRINGTerminalRuleCall_15_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getLavaConfigRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"deathSound",
                    							lv_deathSound_21_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_22=(Token)match(input,13,FOLLOW_2); 

            			newLeafNode(otherlv_22, grammarAccess.getLavaConfigAccess().getRightCurlyBracketKeyword_16());
            		

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
    // $ANTLR end "ruleLavaConfig"


    // $ANTLR start "entryRuleLastLifeGiftConfig"
    // InternalMazeDsl.g:4234:1: entryRuleLastLifeGiftConfig returns [EObject current=null] : iv_ruleLastLifeGiftConfig= ruleLastLifeGiftConfig EOF ;
    public final EObject entryRuleLastLifeGiftConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLastLifeGiftConfig = null;


        try {
            // InternalMazeDsl.g:4234:59: (iv_ruleLastLifeGiftConfig= ruleLastLifeGiftConfig EOF )
            // InternalMazeDsl.g:4235:2: iv_ruleLastLifeGiftConfig= ruleLastLifeGiftConfig EOF
            {
             newCompositeNode(grammarAccess.getLastLifeGiftConfigRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleLastLifeGiftConfig=ruleLastLifeGiftConfig();

            state._fsp--;

             current =iv_ruleLastLifeGiftConfig; 
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
    // $ANTLR end "entryRuleLastLifeGiftConfig"


    // $ANTLR start "ruleLastLifeGiftConfig"
    // InternalMazeDsl.g:4241:1: ruleLastLifeGiftConfig returns [EObject current=null] : (otherlv_0= 'lastLifeWeapon' ( (lv_weapon_1_0= ruleValidID ) ) otherlv_2= 'shots' ( (lv_shots_3_0= RULE_INT ) ) ) ;
    public final EObject ruleLastLifeGiftConfig() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token lv_shots_3_0=null;
        AntlrDatatypeRuleToken lv_weapon_1_0 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:4247:2: ( (otherlv_0= 'lastLifeWeapon' ( (lv_weapon_1_0= ruleValidID ) ) otherlv_2= 'shots' ( (lv_shots_3_0= RULE_INT ) ) ) )
            // InternalMazeDsl.g:4248:2: (otherlv_0= 'lastLifeWeapon' ( (lv_weapon_1_0= ruleValidID ) ) otherlv_2= 'shots' ( (lv_shots_3_0= RULE_INT ) ) )
            {
            // InternalMazeDsl.g:4248:2: (otherlv_0= 'lastLifeWeapon' ( (lv_weapon_1_0= ruleValidID ) ) otherlv_2= 'shots' ( (lv_shots_3_0= RULE_INT ) ) )
            // InternalMazeDsl.g:4249:3: otherlv_0= 'lastLifeWeapon' ( (lv_weapon_1_0= ruleValidID ) ) otherlv_2= 'shots' ( (lv_shots_3_0= RULE_INT ) )
            {
            otherlv_0=(Token)match(input,131,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getLastLifeGiftConfigAccess().getLastLifeWeaponKeyword_0());
            		
            // InternalMazeDsl.g:4253:3: ( (lv_weapon_1_0= ruleValidID ) )
            // InternalMazeDsl.g:4254:4: (lv_weapon_1_0= ruleValidID )
            {
            // InternalMazeDsl.g:4254:4: (lv_weapon_1_0= ruleValidID )
            // InternalMazeDsl.g:4255:5: lv_weapon_1_0= ruleValidID
            {

            					newCompositeNode(grammarAccess.getLastLifeGiftConfigAccess().getWeaponValidIDParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_134);
            lv_weapon_1_0=ruleValidID();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getLastLifeGiftConfigRule());
            					}
            					set(
            						current,
            						"weapon",
            						lv_weapon_1_0,
            						"main.game.maze.dsl.MazeDsl.ValidID");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_2=(Token)match(input,132,FOLLOW_36); 

            			newLeafNode(otherlv_2, grammarAccess.getLastLifeGiftConfigAccess().getShotsKeyword_2());
            		
            // InternalMazeDsl.g:4276:3: ( (lv_shots_3_0= RULE_INT ) )
            // InternalMazeDsl.g:4277:4: (lv_shots_3_0= RULE_INT )
            {
            // InternalMazeDsl.g:4277:4: (lv_shots_3_0= RULE_INT )
            // InternalMazeDsl.g:4278:5: lv_shots_3_0= RULE_INT
            {
            lv_shots_3_0=(Token)match(input,RULE_INT,FOLLOW_2); 

            					newLeafNode(lv_shots_3_0, grammarAccess.getLastLifeGiftConfigAccess().getShotsINTTerminalRuleCall_3_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getLastLifeGiftConfigRule());
            					}
            					setWithLastConsumed(
            						current,
            						"shots",
            						lv_shots_3_0,
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
    // $ANTLR end "ruleLastLifeGiftConfig"


    // $ANTLR start "entryRuleTeleportRulesConfig"
    // InternalMazeDsl.g:4298:1: entryRuleTeleportRulesConfig returns [EObject current=null] : iv_ruleTeleportRulesConfig= ruleTeleportRulesConfig EOF ;
    public final EObject entryRuleTeleportRulesConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTeleportRulesConfig = null;


        try {
            // InternalMazeDsl.g:4298:60: (iv_ruleTeleportRulesConfig= ruleTeleportRulesConfig EOF )
            // InternalMazeDsl.g:4299:2: iv_ruleTeleportRulesConfig= ruleTeleportRulesConfig EOF
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
    // InternalMazeDsl.g:4305:1: ruleTeleportRulesConfig returns [EObject current=null] : (otherlv_0= 'teleport' otherlv_1= '{' otherlv_2= 'cooldown' ( (lv_cooldownSeconds_3_0= ruleDOUBLE ) ) otherlv_4= 'banner' ( (lv_bannerText_5_0= RULE_STRING ) ) otherlv_6= 'bannerSeconds' ( (lv_bannerSeconds_7_0= ruleDOUBLE ) ) (otherlv_8= 'zapSound' ( (lv_zapSound_9_0= RULE_STRING ) ) )? (otherlv_10= 'voiceSound' ( (lv_voiceSound_11_0= RULE_STRING ) ) )? otherlv_12= '}' ) ;
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
            // InternalMazeDsl.g:4311:2: ( (otherlv_0= 'teleport' otherlv_1= '{' otherlv_2= 'cooldown' ( (lv_cooldownSeconds_3_0= ruleDOUBLE ) ) otherlv_4= 'banner' ( (lv_bannerText_5_0= RULE_STRING ) ) otherlv_6= 'bannerSeconds' ( (lv_bannerSeconds_7_0= ruleDOUBLE ) ) (otherlv_8= 'zapSound' ( (lv_zapSound_9_0= RULE_STRING ) ) )? (otherlv_10= 'voiceSound' ( (lv_voiceSound_11_0= RULE_STRING ) ) )? otherlv_12= '}' ) )
            // InternalMazeDsl.g:4312:2: (otherlv_0= 'teleport' otherlv_1= '{' otherlv_2= 'cooldown' ( (lv_cooldownSeconds_3_0= ruleDOUBLE ) ) otherlv_4= 'banner' ( (lv_bannerText_5_0= RULE_STRING ) ) otherlv_6= 'bannerSeconds' ( (lv_bannerSeconds_7_0= ruleDOUBLE ) ) (otherlv_8= 'zapSound' ( (lv_zapSound_9_0= RULE_STRING ) ) )? (otherlv_10= 'voiceSound' ( (lv_voiceSound_11_0= RULE_STRING ) ) )? otherlv_12= '}' )
            {
            // InternalMazeDsl.g:4312:2: (otherlv_0= 'teleport' otherlv_1= '{' otherlv_2= 'cooldown' ( (lv_cooldownSeconds_3_0= ruleDOUBLE ) ) otherlv_4= 'banner' ( (lv_bannerText_5_0= RULE_STRING ) ) otherlv_6= 'bannerSeconds' ( (lv_bannerSeconds_7_0= ruleDOUBLE ) ) (otherlv_8= 'zapSound' ( (lv_zapSound_9_0= RULE_STRING ) ) )? (otherlv_10= 'voiceSound' ( (lv_voiceSound_11_0= RULE_STRING ) ) )? otherlv_12= '}' )
            // InternalMazeDsl.g:4313:3: otherlv_0= 'teleport' otherlv_1= '{' otherlv_2= 'cooldown' ( (lv_cooldownSeconds_3_0= ruleDOUBLE ) ) otherlv_4= 'banner' ( (lv_bannerText_5_0= RULE_STRING ) ) otherlv_6= 'bannerSeconds' ( (lv_bannerSeconds_7_0= ruleDOUBLE ) ) (otherlv_8= 'zapSound' ( (lv_zapSound_9_0= RULE_STRING ) ) )? (otherlv_10= 'voiceSound' ( (lv_voiceSound_11_0= RULE_STRING ) ) )? otherlv_12= '}'
            {
            otherlv_0=(Token)match(input,133,FOLLOW_4); 

            			newLeafNode(otherlv_0, grammarAccess.getTeleportRulesConfigAccess().getTeleportKeyword_0());
            		
            otherlv_1=(Token)match(input,12,FOLLOW_135); 

            			newLeafNode(otherlv_1, grammarAccess.getTeleportRulesConfigAccess().getLeftCurlyBracketKeyword_1());
            		
            otherlv_2=(Token)match(input,134,FOLLOW_16); 

            			newLeafNode(otherlv_2, grammarAccess.getTeleportRulesConfigAccess().getCooldownKeyword_2());
            		
            // InternalMazeDsl.g:4325:3: ( (lv_cooldownSeconds_3_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:4326:4: (lv_cooldownSeconds_3_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:4326:4: (lv_cooldownSeconds_3_0= ruleDOUBLE )
            // InternalMazeDsl.g:4327:5: lv_cooldownSeconds_3_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getTeleportRulesConfigAccess().getCooldownSecondsDOUBLEParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_136);
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

            otherlv_4=(Token)match(input,135,FOLLOW_10); 

            			newLeafNode(otherlv_4, grammarAccess.getTeleportRulesConfigAccess().getBannerKeyword_4());
            		
            // InternalMazeDsl.g:4348:3: ( (lv_bannerText_5_0= RULE_STRING ) )
            // InternalMazeDsl.g:4349:4: (lv_bannerText_5_0= RULE_STRING )
            {
            // InternalMazeDsl.g:4349:4: (lv_bannerText_5_0= RULE_STRING )
            // InternalMazeDsl.g:4350:5: lv_bannerText_5_0= RULE_STRING
            {
            lv_bannerText_5_0=(Token)match(input,RULE_STRING,FOLLOW_137); 

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

            otherlv_6=(Token)match(input,136,FOLLOW_16); 

            			newLeafNode(otherlv_6, grammarAccess.getTeleportRulesConfigAccess().getBannerSecondsKeyword_6());
            		
            // InternalMazeDsl.g:4370:3: ( (lv_bannerSeconds_7_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:4371:4: (lv_bannerSeconds_7_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:4371:4: (lv_bannerSeconds_7_0= ruleDOUBLE )
            // InternalMazeDsl.g:4372:5: lv_bannerSeconds_7_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getTeleportRulesConfigAccess().getBannerSecondsDOUBLEParserRuleCall_7_0());
            				
            pushFollow(FOLLOW_138);
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

            // InternalMazeDsl.g:4389:3: (otherlv_8= 'zapSound' ( (lv_zapSound_9_0= RULE_STRING ) ) )?
            int alt75=2;
            int LA75_0 = input.LA(1);

            if ( (LA75_0==137) ) {
                alt75=1;
            }
            switch (alt75) {
                case 1 :
                    // InternalMazeDsl.g:4390:4: otherlv_8= 'zapSound' ( (lv_zapSound_9_0= RULE_STRING ) )
                    {
                    otherlv_8=(Token)match(input,137,FOLLOW_10); 

                    				newLeafNode(otherlv_8, grammarAccess.getTeleportRulesConfigAccess().getZapSoundKeyword_8_0());
                    			
                    // InternalMazeDsl.g:4394:4: ( (lv_zapSound_9_0= RULE_STRING ) )
                    // InternalMazeDsl.g:4395:5: (lv_zapSound_9_0= RULE_STRING )
                    {
                    // InternalMazeDsl.g:4395:5: (lv_zapSound_9_0= RULE_STRING )
                    // InternalMazeDsl.g:4396:6: lv_zapSound_9_0= RULE_STRING
                    {
                    lv_zapSound_9_0=(Token)match(input,RULE_STRING,FOLLOW_139); 

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

            // InternalMazeDsl.g:4413:3: (otherlv_10= 'voiceSound' ( (lv_voiceSound_11_0= RULE_STRING ) ) )?
            int alt76=2;
            int LA76_0 = input.LA(1);

            if ( (LA76_0==138) ) {
                alt76=1;
            }
            switch (alt76) {
                case 1 :
                    // InternalMazeDsl.g:4414:4: otherlv_10= 'voiceSound' ( (lv_voiceSound_11_0= RULE_STRING ) )
                    {
                    otherlv_10=(Token)match(input,138,FOLLOW_10); 

                    				newLeafNode(otherlv_10, grammarAccess.getTeleportRulesConfigAccess().getVoiceSoundKeyword_9_0());
                    			
                    // InternalMazeDsl.g:4418:4: ( (lv_voiceSound_11_0= RULE_STRING ) )
                    // InternalMazeDsl.g:4419:5: (lv_voiceSound_11_0= RULE_STRING )
                    {
                    // InternalMazeDsl.g:4419:5: (lv_voiceSound_11_0= RULE_STRING )
                    // InternalMazeDsl.g:4420:6: lv_voiceSound_11_0= RULE_STRING
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
    // InternalMazeDsl.g:4445:1: entryRuleDOUBLE returns [String current=null] : iv_ruleDOUBLE= ruleDOUBLE EOF ;
    public final String entryRuleDOUBLE() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDOUBLE = null;


        try {
            // InternalMazeDsl.g:4445:46: (iv_ruleDOUBLE= ruleDOUBLE EOF )
            // InternalMazeDsl.g:4446:2: iv_ruleDOUBLE= ruleDOUBLE EOF
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
    // InternalMazeDsl.g:4452:1: ruleDOUBLE returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= '-' )? this_INT_1= RULE_INT (kw= '.' this_INT_3= RULE_INT )? ) ;
    public final AntlrDatatypeRuleToken ruleDOUBLE() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_INT_1=null;
        Token this_INT_3=null;


        	enterRule();

        try {
            // InternalMazeDsl.g:4458:2: ( ( (kw= '-' )? this_INT_1= RULE_INT (kw= '.' this_INT_3= RULE_INT )? ) )
            // InternalMazeDsl.g:4459:2: ( (kw= '-' )? this_INT_1= RULE_INT (kw= '.' this_INT_3= RULE_INT )? )
            {
            // InternalMazeDsl.g:4459:2: ( (kw= '-' )? this_INT_1= RULE_INT (kw= '.' this_INT_3= RULE_INT )? )
            // InternalMazeDsl.g:4460:3: (kw= '-' )? this_INT_1= RULE_INT (kw= '.' this_INT_3= RULE_INT )?
            {
            // InternalMazeDsl.g:4460:3: (kw= '-' )?
            int alt77=2;
            int LA77_0 = input.LA(1);

            if ( (LA77_0==139) ) {
                alt77=1;
            }
            switch (alt77) {
                case 1 :
                    // InternalMazeDsl.g:4461:4: kw= '-'
                    {
                    kw=(Token)match(input,139,FOLLOW_36); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getDOUBLEAccess().getHyphenMinusKeyword_0());
                    			

                    }
                    break;

            }

            this_INT_1=(Token)match(input,RULE_INT,FOLLOW_140); 

            			current.merge(this_INT_1);
            		

            			newLeafNode(this_INT_1, grammarAccess.getDOUBLEAccess().getINTTerminalRuleCall_1());
            		
            // InternalMazeDsl.g:4474:3: (kw= '.' this_INT_3= RULE_INT )?
            int alt78=2;
            int LA78_0 = input.LA(1);

            if ( (LA78_0==140) ) {
                alt78=1;
            }
            switch (alt78) {
                case 1 :
                    // InternalMazeDsl.g:4475:4: kw= '.' this_INT_3= RULE_INT
                    {
                    kw=(Token)match(input,140,FOLLOW_36); 

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
    // InternalMazeDsl.g:4492:1: entryRuleSIGNED_INT returns [String current=null] : iv_ruleSIGNED_INT= ruleSIGNED_INT EOF ;
    public final String entryRuleSIGNED_INT() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSIGNED_INT = null;


        try {
            // InternalMazeDsl.g:4492:50: (iv_ruleSIGNED_INT= ruleSIGNED_INT EOF )
            // InternalMazeDsl.g:4493:2: iv_ruleSIGNED_INT= ruleSIGNED_INT EOF
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
    // InternalMazeDsl.g:4499:1: ruleSIGNED_INT returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= '-' )? this_INT_1= RULE_INT ) ;
    public final AntlrDatatypeRuleToken ruleSIGNED_INT() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_INT_1=null;


        	enterRule();

        try {
            // InternalMazeDsl.g:4505:2: ( ( (kw= '-' )? this_INT_1= RULE_INT ) )
            // InternalMazeDsl.g:4506:2: ( (kw= '-' )? this_INT_1= RULE_INT )
            {
            // InternalMazeDsl.g:4506:2: ( (kw= '-' )? this_INT_1= RULE_INT )
            // InternalMazeDsl.g:4507:3: (kw= '-' )? this_INT_1= RULE_INT
            {
            // InternalMazeDsl.g:4507:3: (kw= '-' )?
            int alt79=2;
            int LA79_0 = input.LA(1);

            if ( (LA79_0==139) ) {
                alt79=1;
            }
            switch (alt79) {
                case 1 :
                    // InternalMazeDsl.g:4508:4: kw= '-'
                    {
                    kw=(Token)match(input,139,FOLLOW_36); 

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
    // InternalMazeDsl.g:4525:1: entryRuleBOOLEAN returns [String current=null] : iv_ruleBOOLEAN= ruleBOOLEAN EOF ;
    public final String entryRuleBOOLEAN() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleBOOLEAN = null;


        try {
            // InternalMazeDsl.g:4525:47: (iv_ruleBOOLEAN= ruleBOOLEAN EOF )
            // InternalMazeDsl.g:4526:2: iv_ruleBOOLEAN= ruleBOOLEAN EOF
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
    // InternalMazeDsl.g:4532:1: ruleBOOLEAN returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'true' | kw= 'false' ) ;
    public final AntlrDatatypeRuleToken ruleBOOLEAN() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalMazeDsl.g:4538:2: ( (kw= 'true' | kw= 'false' ) )
            // InternalMazeDsl.g:4539:2: (kw= 'true' | kw= 'false' )
            {
            // InternalMazeDsl.g:4539:2: (kw= 'true' | kw= 'false' )
            int alt80=2;
            int LA80_0 = input.LA(1);

            if ( (LA80_0==18) ) {
                alt80=1;
            }
            else if ( (LA80_0==141) ) {
                alt80=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 80, 0, input);

                throw nvae;
            }
            switch (alt80) {
                case 1 :
                    // InternalMazeDsl.g:4540:3: kw= 'true'
                    {
                    kw=(Token)match(input,18,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getBOOLEANAccess().getTrueKeyword_0());
                    		

                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:4546:3: kw= 'false'
                    {
                    kw=(Token)match(input,141,FOLLOW_2); 

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
    // InternalMazeDsl.g:4555:1: entryRuleDOUBLE_OBJECT returns [String current=null] : iv_ruleDOUBLE_OBJECT= ruleDOUBLE_OBJECT EOF ;
    public final String entryRuleDOUBLE_OBJECT() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDOUBLE_OBJECT = null;


        try {
            // InternalMazeDsl.g:4555:53: (iv_ruleDOUBLE_OBJECT= ruleDOUBLE_OBJECT EOF )
            // InternalMazeDsl.g:4556:2: iv_ruleDOUBLE_OBJECT= ruleDOUBLE_OBJECT EOF
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
    // InternalMazeDsl.g:4562:1: ruleDOUBLE_OBJECT returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= '-' )? this_INT_1= RULE_INT (kw= '.' this_INT_3= RULE_INT )? ) ;
    public final AntlrDatatypeRuleToken ruleDOUBLE_OBJECT() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_INT_1=null;
        Token this_INT_3=null;


        	enterRule();

        try {
            // InternalMazeDsl.g:4568:2: ( ( (kw= '-' )? this_INT_1= RULE_INT (kw= '.' this_INT_3= RULE_INT )? ) )
            // InternalMazeDsl.g:4569:2: ( (kw= '-' )? this_INT_1= RULE_INT (kw= '.' this_INT_3= RULE_INT )? )
            {
            // InternalMazeDsl.g:4569:2: ( (kw= '-' )? this_INT_1= RULE_INT (kw= '.' this_INT_3= RULE_INT )? )
            // InternalMazeDsl.g:4570:3: (kw= '-' )? this_INT_1= RULE_INT (kw= '.' this_INT_3= RULE_INT )?
            {
            // InternalMazeDsl.g:4570:3: (kw= '-' )?
            int alt81=2;
            int LA81_0 = input.LA(1);

            if ( (LA81_0==139) ) {
                alt81=1;
            }
            switch (alt81) {
                case 1 :
                    // InternalMazeDsl.g:4571:4: kw= '-'
                    {
                    kw=(Token)match(input,139,FOLLOW_36); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getDOUBLE_OBJECTAccess().getHyphenMinusKeyword_0());
                    			

                    }
                    break;

            }

            this_INT_1=(Token)match(input,RULE_INT,FOLLOW_140); 

            			current.merge(this_INT_1);
            		

            			newLeafNode(this_INT_1, grammarAccess.getDOUBLE_OBJECTAccess().getINTTerminalRuleCall_1());
            		
            // InternalMazeDsl.g:4584:3: (kw= '.' this_INT_3= RULE_INT )?
            int alt82=2;
            int LA82_0 = input.LA(1);

            if ( (LA82_0==140) ) {
                alt82=1;
            }
            switch (alt82) {
                case 1 :
                    // InternalMazeDsl.g:4585:4: kw= '.' this_INT_3= RULE_INT
                    {
                    kw=(Token)match(input,140,FOLLOW_36); 

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
    // InternalMazeDsl.g:4602:1: entryRuleINT_OBJECT returns [String current=null] : iv_ruleINT_OBJECT= ruleINT_OBJECT EOF ;
    public final String entryRuleINT_OBJECT() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleINT_OBJECT = null;


        try {
            // InternalMazeDsl.g:4602:50: (iv_ruleINT_OBJECT= ruleINT_OBJECT EOF )
            // InternalMazeDsl.g:4603:2: iv_ruleINT_OBJECT= ruleINT_OBJECT EOF
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
    // InternalMazeDsl.g:4609:1: ruleINT_OBJECT returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= '-' )? this_INT_1= RULE_INT ) ;
    public final AntlrDatatypeRuleToken ruleINT_OBJECT() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_INT_1=null;


        	enterRule();

        try {
            // InternalMazeDsl.g:4615:2: ( ( (kw= '-' )? this_INT_1= RULE_INT ) )
            // InternalMazeDsl.g:4616:2: ( (kw= '-' )? this_INT_1= RULE_INT )
            {
            // InternalMazeDsl.g:4616:2: ( (kw= '-' )? this_INT_1= RULE_INT )
            // InternalMazeDsl.g:4617:3: (kw= '-' )? this_INT_1= RULE_INT
            {
            // InternalMazeDsl.g:4617:3: (kw= '-' )?
            int alt83=2;
            int LA83_0 = input.LA(1);

            if ( (LA83_0==139) ) {
                alt83=1;
            }
            switch (alt83) {
                case 1 :
                    // InternalMazeDsl.g:4618:4: kw= '-'
                    {
                    kw=(Token)match(input,139,FOLLOW_36); 

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
    // InternalMazeDsl.g:4635:1: entryRuleBOOLEAN_OBJECT returns [String current=null] : iv_ruleBOOLEAN_OBJECT= ruleBOOLEAN_OBJECT EOF ;
    public final String entryRuleBOOLEAN_OBJECT() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleBOOLEAN_OBJECT = null;


        try {
            // InternalMazeDsl.g:4635:54: (iv_ruleBOOLEAN_OBJECT= ruleBOOLEAN_OBJECT EOF )
            // InternalMazeDsl.g:4636:2: iv_ruleBOOLEAN_OBJECT= ruleBOOLEAN_OBJECT EOF
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
    // InternalMazeDsl.g:4642:1: ruleBOOLEAN_OBJECT returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'true' | kw= 'false' ) ;
    public final AntlrDatatypeRuleToken ruleBOOLEAN_OBJECT() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalMazeDsl.g:4648:2: ( (kw= 'true' | kw= 'false' ) )
            // InternalMazeDsl.g:4649:2: (kw= 'true' | kw= 'false' )
            {
            // InternalMazeDsl.g:4649:2: (kw= 'true' | kw= 'false' )
            int alt84=2;
            int LA84_0 = input.LA(1);

            if ( (LA84_0==18) ) {
                alt84=1;
            }
            else if ( (LA84_0==141) ) {
                alt84=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 84, 0, input);

                throw nvae;
            }
            switch (alt84) {
                case 1 :
                    // InternalMazeDsl.g:4650:3: kw= 'true'
                    {
                    kw=(Token)match(input,18,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getBOOLEAN_OBJECTAccess().getTrueKeyword_0());
                    		

                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:4656:3: kw= 'false'
                    {
                    kw=(Token)match(input,141,FOLLOW_2); 

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


    // $ANTLR start "entryRuleValidID"
    // InternalMazeDsl.g:4665:1: entryRuleValidID returns [String current=null] : iv_ruleValidID= ruleValidID EOF ;
    public final String entryRuleValidID() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleValidID = null;


        try {
            // InternalMazeDsl.g:4665:47: (iv_ruleValidID= ruleValidID EOF )
            // InternalMazeDsl.g:4666:2: iv_ruleValidID= ruleValidID EOF
            {
             newCompositeNode(grammarAccess.getValidIDRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleValidID=ruleValidID();

            state._fsp--;

             current =iv_ruleValidID.getText(); 
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
    // $ANTLR end "entryRuleValidID"


    // $ANTLR start "ruleValidID"
    // InternalMazeDsl.g:4672:1: ruleValidID returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID | kw= 'basedOn' | kw= 'threatCapacity' | kw= 'wallMaterials' | kw= 'inheritWeapons' | kw= 'cascadeDepth' | kw= 'ring' | kw= 'share' | kw= 'materials' | kw= 'sergeant' | kw= 'sergeants' | kw= 'sightRange' | kw= 'rounds' | kw= 'roundDamage' | kw= 'roundInterval' | kw= 'burstCooldown' | kw= 'sprite' | kw= 'fireSound' | kw= 'drops' | kw= 'gatling' | kw= 'postDistance' | kw= 'mines' | kw= 'count' | kw= 'ringRadius' | kw= 'triggerRadius' | kw= 'pulse' | kw= 'blastRadius' | kw= 'blastDamage' | kw= 'playerDamage' | kw= 'holeRadius' | kw= 'portalGhost' | kw= 'portalGhosts' | kw= 'teleport' | kw= 'cooldown' | kw= 'banner' | kw= 'bannerSeconds' | kw= 'zapSound' | kw= 'voiceSound' | kw= 'secondRing' | kw= 'guardTower' | kw= 'towers' | kw= 'wallsOver' | kw= 'rpgInterval' | kw= 'rpgSpeed' | kw= 'rpgBlastRadius' | kw= 'rpgPlayerDamage' | kw= 'rpgWallDamage' | kw= 'gatlingRounds' | kw= 'gatlingInterval' | kw= 'gatlingDamage' | kw= 'deathBlast' | kw= 'rpgLaunchSound' | kw= 'rpgExplosionSound' | kw= 'gatlingSound' | kw= 'hitSound' | kw= 'destroyedSound' | kw= 'wallBuilder' | kw= 'builders' | kw= 'buildSeconds' | kw= 'blockRadius' | kw= 'spawnRadius' | kw= 'material' | kw= 'startRadius' | kw= 'lastLifeWeapon' | kw= 'shots' | kw= 'lava' | kw= 'streams' | kw= 'width' | kw= 'bridges' | kw= 'bridgeLength' | kw= 'meander' | kw= 'wavelength' | kw= 'flowSpeed' | kw= 'glow' | kw= 'crust' | kw= 'deathSound' | kw= 'buildSound' ) ;
    public final AntlrDatatypeRuleToken ruleValidID() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token kw=null;


        	enterRule();

        try {
            // InternalMazeDsl.g:4678:2: ( (this_ID_0= RULE_ID | kw= 'basedOn' | kw= 'threatCapacity' | kw= 'wallMaterials' | kw= 'inheritWeapons' | kw= 'cascadeDepth' | kw= 'ring' | kw= 'share' | kw= 'materials' | kw= 'sergeant' | kw= 'sergeants' | kw= 'sightRange' | kw= 'rounds' | kw= 'roundDamage' | kw= 'roundInterval' | kw= 'burstCooldown' | kw= 'sprite' | kw= 'fireSound' | kw= 'drops' | kw= 'gatling' | kw= 'postDistance' | kw= 'mines' | kw= 'count' | kw= 'ringRadius' | kw= 'triggerRadius' | kw= 'pulse' | kw= 'blastRadius' | kw= 'blastDamage' | kw= 'playerDamage' | kw= 'holeRadius' | kw= 'portalGhost' | kw= 'portalGhosts' | kw= 'teleport' | kw= 'cooldown' | kw= 'banner' | kw= 'bannerSeconds' | kw= 'zapSound' | kw= 'voiceSound' | kw= 'secondRing' | kw= 'guardTower' | kw= 'towers' | kw= 'wallsOver' | kw= 'rpgInterval' | kw= 'rpgSpeed' | kw= 'rpgBlastRadius' | kw= 'rpgPlayerDamage' | kw= 'rpgWallDamage' | kw= 'gatlingRounds' | kw= 'gatlingInterval' | kw= 'gatlingDamage' | kw= 'deathBlast' | kw= 'rpgLaunchSound' | kw= 'rpgExplosionSound' | kw= 'gatlingSound' | kw= 'hitSound' | kw= 'destroyedSound' | kw= 'wallBuilder' | kw= 'builders' | kw= 'buildSeconds' | kw= 'blockRadius' | kw= 'spawnRadius' | kw= 'material' | kw= 'startRadius' | kw= 'lastLifeWeapon' | kw= 'shots' | kw= 'lava' | kw= 'streams' | kw= 'width' | kw= 'bridges' | kw= 'bridgeLength' | kw= 'meander' | kw= 'wavelength' | kw= 'flowSpeed' | kw= 'glow' | kw= 'crust' | kw= 'deathSound' | kw= 'buildSound' ) )
            // InternalMazeDsl.g:4679:2: (this_ID_0= RULE_ID | kw= 'basedOn' | kw= 'threatCapacity' | kw= 'wallMaterials' | kw= 'inheritWeapons' | kw= 'cascadeDepth' | kw= 'ring' | kw= 'share' | kw= 'materials' | kw= 'sergeant' | kw= 'sergeants' | kw= 'sightRange' | kw= 'rounds' | kw= 'roundDamage' | kw= 'roundInterval' | kw= 'burstCooldown' | kw= 'sprite' | kw= 'fireSound' | kw= 'drops' | kw= 'gatling' | kw= 'postDistance' | kw= 'mines' | kw= 'count' | kw= 'ringRadius' | kw= 'triggerRadius' | kw= 'pulse' | kw= 'blastRadius' | kw= 'blastDamage' | kw= 'playerDamage' | kw= 'holeRadius' | kw= 'portalGhost' | kw= 'portalGhosts' | kw= 'teleport' | kw= 'cooldown' | kw= 'banner' | kw= 'bannerSeconds' | kw= 'zapSound' | kw= 'voiceSound' | kw= 'secondRing' | kw= 'guardTower' | kw= 'towers' | kw= 'wallsOver' | kw= 'rpgInterval' | kw= 'rpgSpeed' | kw= 'rpgBlastRadius' | kw= 'rpgPlayerDamage' | kw= 'rpgWallDamage' | kw= 'gatlingRounds' | kw= 'gatlingInterval' | kw= 'gatlingDamage' | kw= 'deathBlast' | kw= 'rpgLaunchSound' | kw= 'rpgExplosionSound' | kw= 'gatlingSound' | kw= 'hitSound' | kw= 'destroyedSound' | kw= 'wallBuilder' | kw= 'builders' | kw= 'buildSeconds' | kw= 'blockRadius' | kw= 'spawnRadius' | kw= 'material' | kw= 'startRadius' | kw= 'lastLifeWeapon' | kw= 'shots' | kw= 'lava' | kw= 'streams' | kw= 'width' | kw= 'bridges' | kw= 'bridgeLength' | kw= 'meander' | kw= 'wavelength' | kw= 'flowSpeed' | kw= 'glow' | kw= 'crust' | kw= 'deathSound' | kw= 'buildSound' )
            {
            // InternalMazeDsl.g:4679:2: (this_ID_0= RULE_ID | kw= 'basedOn' | kw= 'threatCapacity' | kw= 'wallMaterials' | kw= 'inheritWeapons' | kw= 'cascadeDepth' | kw= 'ring' | kw= 'share' | kw= 'materials' | kw= 'sergeant' | kw= 'sergeants' | kw= 'sightRange' | kw= 'rounds' | kw= 'roundDamage' | kw= 'roundInterval' | kw= 'burstCooldown' | kw= 'sprite' | kw= 'fireSound' | kw= 'drops' | kw= 'gatling' | kw= 'postDistance' | kw= 'mines' | kw= 'count' | kw= 'ringRadius' | kw= 'triggerRadius' | kw= 'pulse' | kw= 'blastRadius' | kw= 'blastDamage' | kw= 'playerDamage' | kw= 'holeRadius' | kw= 'portalGhost' | kw= 'portalGhosts' | kw= 'teleport' | kw= 'cooldown' | kw= 'banner' | kw= 'bannerSeconds' | kw= 'zapSound' | kw= 'voiceSound' | kw= 'secondRing' | kw= 'guardTower' | kw= 'towers' | kw= 'wallsOver' | kw= 'rpgInterval' | kw= 'rpgSpeed' | kw= 'rpgBlastRadius' | kw= 'rpgPlayerDamage' | kw= 'rpgWallDamage' | kw= 'gatlingRounds' | kw= 'gatlingInterval' | kw= 'gatlingDamage' | kw= 'deathBlast' | kw= 'rpgLaunchSound' | kw= 'rpgExplosionSound' | kw= 'gatlingSound' | kw= 'hitSound' | kw= 'destroyedSound' | kw= 'wallBuilder' | kw= 'builders' | kw= 'buildSeconds' | kw= 'blockRadius' | kw= 'spawnRadius' | kw= 'material' | kw= 'startRadius' | kw= 'lastLifeWeapon' | kw= 'shots' | kw= 'lava' | kw= 'streams' | kw= 'width' | kw= 'bridges' | kw= 'bridgeLength' | kw= 'meander' | kw= 'wavelength' | kw= 'flowSpeed' | kw= 'glow' | kw= 'crust' | kw= 'deathSound' | kw= 'buildSound' )
            int alt85=77;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                alt85=1;
                }
                break;
            case 65:
                {
                alt85=2;
                }
                break;
            case 66:
                {
                alt85=3;
                }
                break;
            case 67:
                {
                alt85=4;
                }
                break;
            case 68:
                {
                alt85=5;
                }
                break;
            case 69:
                {
                alt85=6;
                }
                break;
            case 70:
                {
                alt85=7;
                }
                break;
            case 71:
                {
                alt85=8;
                }
                break;
            case 72:
                {
                alt85=9;
                }
                break;
            case 73:
                {
                alt85=10;
                }
                break;
            case 83:
                {
                alt85=11;
                }
                break;
            case 74:
                {
                alt85=12;
                }
                break;
            case 75:
                {
                alt85=13;
                }
                break;
            case 76:
                {
                alt85=14;
                }
                break;
            case 77:
                {
                alt85=15;
                }
                break;
            case 78:
                {
                alt85=16;
                }
                break;
            case 79:
                {
                alt85=17;
                }
                break;
            case 80:
                {
                alt85=18;
                }
                break;
            case 81:
                {
                alt85=19;
                }
                break;
            case 142:
                {
                alt85=20;
                }
                break;
            case 82:
                {
                alt85=21;
                }
                break;
            case 84:
                {
                alt85=22;
                }
                break;
            case 85:
                {
                alt85=23;
                }
                break;
            case 86:
                {
                alt85=24;
                }
                break;
            case 88:
                {
                alt85=25;
                }
                break;
            case 89:
                {
                alt85=26;
                }
                break;
            case 90:
                {
                alt85=27;
                }
                break;
            case 91:
                {
                alt85=28;
                }
                break;
            case 92:
                {
                alt85=29;
                }
                break;
            case 93:
                {
                alt85=30;
                }
                break;
            case 94:
                {
                alt85=31;
                }
                break;
            case 95:
                {
                alt85=32;
                }
                break;
            case 133:
                {
                alt85=33;
                }
                break;
            case 134:
                {
                alt85=34;
                }
                break;
            case 135:
                {
                alt85=35;
                }
                break;
            case 136:
                {
                alt85=36;
                }
                break;
            case 137:
                {
                alt85=37;
                }
                break;
            case 138:
                {
                alt85=38;
                }
                break;
            case 87:
                {
                alt85=39;
                }
                break;
            case 96:
                {
                alt85=40;
                }
                break;
            case 112:
                {
                alt85=41;
                }
                break;
            case 97:
                {
                alt85=42;
                }
                break;
            case 98:
                {
                alt85=43;
                }
                break;
            case 99:
                {
                alt85=44;
                }
                break;
            case 100:
                {
                alt85=45;
                }
                break;
            case 101:
                {
                alt85=46;
                }
                break;
            case 102:
                {
                alt85=47;
                }
                break;
            case 103:
                {
                alt85=48;
                }
                break;
            case 104:
                {
                alt85=49;
                }
                break;
            case 105:
                {
                alt85=50;
                }
                break;
            case 106:
                {
                alt85=51;
                }
                break;
            case 107:
                {
                alt85=52;
                }
                break;
            case 108:
                {
                alt85=53;
                }
                break;
            case 109:
                {
                alt85=54;
                }
                break;
            case 110:
                {
                alt85=55;
                }
                break;
            case 111:
                {
                alt85=56;
                }
                break;
            case 113:
                {
                alt85=57;
                }
                break;
            case 120:
                {
                alt85=58;
                }
                break;
            case 114:
                {
                alt85=59;
                }
                break;
            case 116:
                {
                alt85=60;
                }
                break;
            case 117:
                {
                alt85=61;
                }
                break;
            case 118:
                {
                alt85=62;
                }
                break;
            case 115:
                {
                alt85=63;
                }
                break;
            case 131:
                {
                alt85=64;
                }
                break;
            case 132:
                {
                alt85=65;
                }
                break;
            case 121:
                {
                alt85=66;
                }
                break;
            case 122:
                {
                alt85=67;
                }
                break;
            case 56:
                {
                alt85=68;
                }
                break;
            case 123:
                {
                alt85=69;
                }
                break;
            case 124:
                {
                alt85=70;
                }
                break;
            case 125:
                {
                alt85=71;
                }
                break;
            case 126:
                {
                alt85=72;
                }
                break;
            case 127:
                {
                alt85=73;
                }
                break;
            case 128:
                {
                alt85=74;
                }
                break;
            case 129:
                {
                alt85=75;
                }
                break;
            case 130:
                {
                alt85=76;
                }
                break;
            case 119:
                {
                alt85=77;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 85, 0, input);

                throw nvae;
            }

            switch (alt85) {
                case 1 :
                    // InternalMazeDsl.g:4680:3: this_ID_0= RULE_ID
                    {
                    this_ID_0=(Token)match(input,RULE_ID,FOLLOW_2); 

                    			current.merge(this_ID_0);
                    		

                    			newLeafNode(this_ID_0, grammarAccess.getValidIDAccess().getIDTerminalRuleCall_0());
                    		

                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:4688:3: kw= 'basedOn'
                    {
                    kw=(Token)match(input,65,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getBasedOnKeyword_1());
                    		

                    }
                    break;
                case 3 :
                    // InternalMazeDsl.g:4694:3: kw= 'threatCapacity'
                    {
                    kw=(Token)match(input,66,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getThreatCapacityKeyword_2());
                    		

                    }
                    break;
                case 4 :
                    // InternalMazeDsl.g:4700:3: kw= 'wallMaterials'
                    {
                    kw=(Token)match(input,67,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getWallMaterialsKeyword_3());
                    		

                    }
                    break;
                case 5 :
                    // InternalMazeDsl.g:4706:3: kw= 'inheritWeapons'
                    {
                    kw=(Token)match(input,68,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getInheritWeaponsKeyword_4());
                    		

                    }
                    break;
                case 6 :
                    // InternalMazeDsl.g:4712:3: kw= 'cascadeDepth'
                    {
                    kw=(Token)match(input,69,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getCascadeDepthKeyword_5());
                    		

                    }
                    break;
                case 7 :
                    // InternalMazeDsl.g:4718:3: kw= 'ring'
                    {
                    kw=(Token)match(input,70,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getRingKeyword_6());
                    		

                    }
                    break;
                case 8 :
                    // InternalMazeDsl.g:4724:3: kw= 'share'
                    {
                    kw=(Token)match(input,71,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getShareKeyword_7());
                    		

                    }
                    break;
                case 9 :
                    // InternalMazeDsl.g:4730:3: kw= 'materials'
                    {
                    kw=(Token)match(input,72,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getMaterialsKeyword_8());
                    		

                    }
                    break;
                case 10 :
                    // InternalMazeDsl.g:4736:3: kw= 'sergeant'
                    {
                    kw=(Token)match(input,73,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getSergeantKeyword_9());
                    		

                    }
                    break;
                case 11 :
                    // InternalMazeDsl.g:4742:3: kw= 'sergeants'
                    {
                    kw=(Token)match(input,83,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getSergeantsKeyword_10());
                    		

                    }
                    break;
                case 12 :
                    // InternalMazeDsl.g:4748:3: kw= 'sightRange'
                    {
                    kw=(Token)match(input,74,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getSightRangeKeyword_11());
                    		

                    }
                    break;
                case 13 :
                    // InternalMazeDsl.g:4754:3: kw= 'rounds'
                    {
                    kw=(Token)match(input,75,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getRoundsKeyword_12());
                    		

                    }
                    break;
                case 14 :
                    // InternalMazeDsl.g:4760:3: kw= 'roundDamage'
                    {
                    kw=(Token)match(input,76,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getRoundDamageKeyword_13());
                    		

                    }
                    break;
                case 15 :
                    // InternalMazeDsl.g:4766:3: kw= 'roundInterval'
                    {
                    kw=(Token)match(input,77,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getRoundIntervalKeyword_14());
                    		

                    }
                    break;
                case 16 :
                    // InternalMazeDsl.g:4772:3: kw= 'burstCooldown'
                    {
                    kw=(Token)match(input,78,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getBurstCooldownKeyword_15());
                    		

                    }
                    break;
                case 17 :
                    // InternalMazeDsl.g:4778:3: kw= 'sprite'
                    {
                    kw=(Token)match(input,79,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getSpriteKeyword_16());
                    		

                    }
                    break;
                case 18 :
                    // InternalMazeDsl.g:4784:3: kw= 'fireSound'
                    {
                    kw=(Token)match(input,80,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getFireSoundKeyword_17());
                    		

                    }
                    break;
                case 19 :
                    // InternalMazeDsl.g:4790:3: kw= 'drops'
                    {
                    kw=(Token)match(input,81,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getDropsKeyword_18());
                    		

                    }
                    break;
                case 20 :
                    // InternalMazeDsl.g:4796:3: kw= 'gatling'
                    {
                    kw=(Token)match(input,142,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getGatlingKeyword_19());
                    		

                    }
                    break;
                case 21 :
                    // InternalMazeDsl.g:4802:3: kw= 'postDistance'
                    {
                    kw=(Token)match(input,82,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getPostDistanceKeyword_20());
                    		

                    }
                    break;
                case 22 :
                    // InternalMazeDsl.g:4808:3: kw= 'mines'
                    {
                    kw=(Token)match(input,84,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getMinesKeyword_21());
                    		

                    }
                    break;
                case 23 :
                    // InternalMazeDsl.g:4814:3: kw= 'count'
                    {
                    kw=(Token)match(input,85,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getCountKeyword_22());
                    		

                    }
                    break;
                case 24 :
                    // InternalMazeDsl.g:4820:3: kw= 'ringRadius'
                    {
                    kw=(Token)match(input,86,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getRingRadiusKeyword_23());
                    		

                    }
                    break;
                case 25 :
                    // InternalMazeDsl.g:4826:3: kw= 'triggerRadius'
                    {
                    kw=(Token)match(input,88,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getTriggerRadiusKeyword_24());
                    		

                    }
                    break;
                case 26 :
                    // InternalMazeDsl.g:4832:3: kw= 'pulse'
                    {
                    kw=(Token)match(input,89,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getPulseKeyword_25());
                    		

                    }
                    break;
                case 27 :
                    // InternalMazeDsl.g:4838:3: kw= 'blastRadius'
                    {
                    kw=(Token)match(input,90,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getBlastRadiusKeyword_26());
                    		

                    }
                    break;
                case 28 :
                    // InternalMazeDsl.g:4844:3: kw= 'blastDamage'
                    {
                    kw=(Token)match(input,91,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getBlastDamageKeyword_27());
                    		

                    }
                    break;
                case 29 :
                    // InternalMazeDsl.g:4850:3: kw= 'playerDamage'
                    {
                    kw=(Token)match(input,92,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getPlayerDamageKeyword_28());
                    		

                    }
                    break;
                case 30 :
                    // InternalMazeDsl.g:4856:3: kw= 'holeRadius'
                    {
                    kw=(Token)match(input,93,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getHoleRadiusKeyword_29());
                    		

                    }
                    break;
                case 31 :
                    // InternalMazeDsl.g:4862:3: kw= 'portalGhost'
                    {
                    kw=(Token)match(input,94,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getPortalGhostKeyword_30());
                    		

                    }
                    break;
                case 32 :
                    // InternalMazeDsl.g:4868:3: kw= 'portalGhosts'
                    {
                    kw=(Token)match(input,95,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getPortalGhostsKeyword_31());
                    		

                    }
                    break;
                case 33 :
                    // InternalMazeDsl.g:4874:3: kw= 'teleport'
                    {
                    kw=(Token)match(input,133,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getTeleportKeyword_32());
                    		

                    }
                    break;
                case 34 :
                    // InternalMazeDsl.g:4880:3: kw= 'cooldown'
                    {
                    kw=(Token)match(input,134,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getCooldownKeyword_33());
                    		

                    }
                    break;
                case 35 :
                    // InternalMazeDsl.g:4886:3: kw= 'banner'
                    {
                    kw=(Token)match(input,135,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getBannerKeyword_34());
                    		

                    }
                    break;
                case 36 :
                    // InternalMazeDsl.g:4892:3: kw= 'bannerSeconds'
                    {
                    kw=(Token)match(input,136,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getBannerSecondsKeyword_35());
                    		

                    }
                    break;
                case 37 :
                    // InternalMazeDsl.g:4898:3: kw= 'zapSound'
                    {
                    kw=(Token)match(input,137,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getZapSoundKeyword_36());
                    		

                    }
                    break;
                case 38 :
                    // InternalMazeDsl.g:4904:3: kw= 'voiceSound'
                    {
                    kw=(Token)match(input,138,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getVoiceSoundKeyword_37());
                    		

                    }
                    break;
                case 39 :
                    // InternalMazeDsl.g:4910:3: kw= 'secondRing'
                    {
                    kw=(Token)match(input,87,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getSecondRingKeyword_38());
                    		

                    }
                    break;
                case 40 :
                    // InternalMazeDsl.g:4916:3: kw= 'guardTower'
                    {
                    kw=(Token)match(input,96,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getGuardTowerKeyword_39());
                    		

                    }
                    break;
                case 41 :
                    // InternalMazeDsl.g:4922:3: kw= 'towers'
                    {
                    kw=(Token)match(input,112,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getTowersKeyword_40());
                    		

                    }
                    break;
                case 42 :
                    // InternalMazeDsl.g:4928:3: kw= 'wallsOver'
                    {
                    kw=(Token)match(input,97,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getWallsOverKeyword_41());
                    		

                    }
                    break;
                case 43 :
                    // InternalMazeDsl.g:4934:3: kw= 'rpgInterval'
                    {
                    kw=(Token)match(input,98,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getRpgIntervalKeyword_42());
                    		

                    }
                    break;
                case 44 :
                    // InternalMazeDsl.g:4940:3: kw= 'rpgSpeed'
                    {
                    kw=(Token)match(input,99,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getRpgSpeedKeyword_43());
                    		

                    }
                    break;
                case 45 :
                    // InternalMazeDsl.g:4946:3: kw= 'rpgBlastRadius'
                    {
                    kw=(Token)match(input,100,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getRpgBlastRadiusKeyword_44());
                    		

                    }
                    break;
                case 46 :
                    // InternalMazeDsl.g:4952:3: kw= 'rpgPlayerDamage'
                    {
                    kw=(Token)match(input,101,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getRpgPlayerDamageKeyword_45());
                    		

                    }
                    break;
                case 47 :
                    // InternalMazeDsl.g:4958:3: kw= 'rpgWallDamage'
                    {
                    kw=(Token)match(input,102,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getRpgWallDamageKeyword_46());
                    		

                    }
                    break;
                case 48 :
                    // InternalMazeDsl.g:4964:3: kw= 'gatlingRounds'
                    {
                    kw=(Token)match(input,103,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getGatlingRoundsKeyword_47());
                    		

                    }
                    break;
                case 49 :
                    // InternalMazeDsl.g:4970:3: kw= 'gatlingInterval'
                    {
                    kw=(Token)match(input,104,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getGatlingIntervalKeyword_48());
                    		

                    }
                    break;
                case 50 :
                    // InternalMazeDsl.g:4976:3: kw= 'gatlingDamage'
                    {
                    kw=(Token)match(input,105,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getGatlingDamageKeyword_49());
                    		

                    }
                    break;
                case 51 :
                    // InternalMazeDsl.g:4982:3: kw= 'deathBlast'
                    {
                    kw=(Token)match(input,106,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getDeathBlastKeyword_50());
                    		

                    }
                    break;
                case 52 :
                    // InternalMazeDsl.g:4988:3: kw= 'rpgLaunchSound'
                    {
                    kw=(Token)match(input,107,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getRpgLaunchSoundKeyword_51());
                    		

                    }
                    break;
                case 53 :
                    // InternalMazeDsl.g:4994:3: kw= 'rpgExplosionSound'
                    {
                    kw=(Token)match(input,108,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getRpgExplosionSoundKeyword_52());
                    		

                    }
                    break;
                case 54 :
                    // InternalMazeDsl.g:5000:3: kw= 'gatlingSound'
                    {
                    kw=(Token)match(input,109,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getGatlingSoundKeyword_53());
                    		

                    }
                    break;
                case 55 :
                    // InternalMazeDsl.g:5006:3: kw= 'hitSound'
                    {
                    kw=(Token)match(input,110,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getHitSoundKeyword_54());
                    		

                    }
                    break;
                case 56 :
                    // InternalMazeDsl.g:5012:3: kw= 'destroyedSound'
                    {
                    kw=(Token)match(input,111,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getDestroyedSoundKeyword_55());
                    		

                    }
                    break;
                case 57 :
                    // InternalMazeDsl.g:5018:3: kw= 'wallBuilder'
                    {
                    kw=(Token)match(input,113,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getWallBuilderKeyword_56());
                    		

                    }
                    break;
                case 58 :
                    // InternalMazeDsl.g:5024:3: kw= 'builders'
                    {
                    kw=(Token)match(input,120,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getBuildersKeyword_57());
                    		

                    }
                    break;
                case 59 :
                    // InternalMazeDsl.g:5030:3: kw= 'buildSeconds'
                    {
                    kw=(Token)match(input,114,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getBuildSecondsKeyword_58());
                    		

                    }
                    break;
                case 60 :
                    // InternalMazeDsl.g:5036:3: kw= 'blockRadius'
                    {
                    kw=(Token)match(input,116,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getBlockRadiusKeyword_59());
                    		

                    }
                    break;
                case 61 :
                    // InternalMazeDsl.g:5042:3: kw= 'spawnRadius'
                    {
                    kw=(Token)match(input,117,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getSpawnRadiusKeyword_60());
                    		

                    }
                    break;
                case 62 :
                    // InternalMazeDsl.g:5048:3: kw= 'material'
                    {
                    kw=(Token)match(input,118,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getMaterialKeyword_61());
                    		

                    }
                    break;
                case 63 :
                    // InternalMazeDsl.g:5054:3: kw= 'startRadius'
                    {
                    kw=(Token)match(input,115,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getStartRadiusKeyword_62());
                    		

                    }
                    break;
                case 64 :
                    // InternalMazeDsl.g:5060:3: kw= 'lastLifeWeapon'
                    {
                    kw=(Token)match(input,131,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getLastLifeWeaponKeyword_63());
                    		

                    }
                    break;
                case 65 :
                    // InternalMazeDsl.g:5066:3: kw= 'shots'
                    {
                    kw=(Token)match(input,132,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getShotsKeyword_64());
                    		

                    }
                    break;
                case 66 :
                    // InternalMazeDsl.g:5072:3: kw= 'lava'
                    {
                    kw=(Token)match(input,121,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getLavaKeyword_65());
                    		

                    }
                    break;
                case 67 :
                    // InternalMazeDsl.g:5078:3: kw= 'streams'
                    {
                    kw=(Token)match(input,122,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getStreamsKeyword_66());
                    		

                    }
                    break;
                case 68 :
                    // InternalMazeDsl.g:5084:3: kw= 'width'
                    {
                    kw=(Token)match(input,56,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getWidthKeyword_67());
                    		

                    }
                    break;
                case 69 :
                    // InternalMazeDsl.g:5090:3: kw= 'bridges'
                    {
                    kw=(Token)match(input,123,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getBridgesKeyword_68());
                    		

                    }
                    break;
                case 70 :
                    // InternalMazeDsl.g:5096:3: kw= 'bridgeLength'
                    {
                    kw=(Token)match(input,124,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getBridgeLengthKeyword_69());
                    		

                    }
                    break;
                case 71 :
                    // InternalMazeDsl.g:5102:3: kw= 'meander'
                    {
                    kw=(Token)match(input,125,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getMeanderKeyword_70());
                    		

                    }
                    break;
                case 72 :
                    // InternalMazeDsl.g:5108:3: kw= 'wavelength'
                    {
                    kw=(Token)match(input,126,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getWavelengthKeyword_71());
                    		

                    }
                    break;
                case 73 :
                    // InternalMazeDsl.g:5114:3: kw= 'flowSpeed'
                    {
                    kw=(Token)match(input,127,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getFlowSpeedKeyword_72());
                    		

                    }
                    break;
                case 74 :
                    // InternalMazeDsl.g:5120:3: kw= 'glow'
                    {
                    kw=(Token)match(input,128,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getGlowKeyword_73());
                    		

                    }
                    break;
                case 75 :
                    // InternalMazeDsl.g:5126:3: kw= 'crust'
                    {
                    kw=(Token)match(input,129,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getCrustKeyword_74());
                    		

                    }
                    break;
                case 76 :
                    // InternalMazeDsl.g:5132:3: kw= 'deathSound'
                    {
                    kw=(Token)match(input,130,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getDeathSoundKeyword_75());
                    		

                    }
                    break;
                case 77 :
                    // InternalMazeDsl.g:5138:3: kw= 'buildSound'
                    {
                    kw=(Token)match(input,119,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getBuildSoundKeyword_76());
                    		

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
    // $ANTLR end "ruleValidID"


    // $ANTLR start "ruleDifficultyLevel"
    // InternalMazeDsl.g:5147:1: ruleDifficultyLevel returns [Enumerator current=null] : ( (enumLiteral_0= 'easy' ) | (enumLiteral_1= 'normal' ) | (enumLiteral_2= 'hard' ) ) ;
    public final Enumerator ruleDifficultyLevel() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalMazeDsl.g:5153:2: ( ( (enumLiteral_0= 'easy' ) | (enumLiteral_1= 'normal' ) | (enumLiteral_2= 'hard' ) ) )
            // InternalMazeDsl.g:5154:2: ( (enumLiteral_0= 'easy' ) | (enumLiteral_1= 'normal' ) | (enumLiteral_2= 'hard' ) )
            {
            // InternalMazeDsl.g:5154:2: ( (enumLiteral_0= 'easy' ) | (enumLiteral_1= 'normal' ) | (enumLiteral_2= 'hard' ) )
            int alt86=3;
            switch ( input.LA(1) ) {
            case 143:
                {
                alt86=1;
                }
                break;
            case 144:
                {
                alt86=2;
                }
                break;
            case 145:
                {
                alt86=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 86, 0, input);

                throw nvae;
            }

            switch (alt86) {
                case 1 :
                    // InternalMazeDsl.g:5155:3: (enumLiteral_0= 'easy' )
                    {
                    // InternalMazeDsl.g:5155:3: (enumLiteral_0= 'easy' )
                    // InternalMazeDsl.g:5156:4: enumLiteral_0= 'easy'
                    {
                    enumLiteral_0=(Token)match(input,143,FOLLOW_2); 

                    				current = grammarAccess.getDifficultyLevelAccess().getEASYEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getDifficultyLevelAccess().getEASYEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:5163:3: (enumLiteral_1= 'normal' )
                    {
                    // InternalMazeDsl.g:5163:3: (enumLiteral_1= 'normal' )
                    // InternalMazeDsl.g:5164:4: enumLiteral_1= 'normal'
                    {
                    enumLiteral_1=(Token)match(input,144,FOLLOW_2); 

                    				current = grammarAccess.getDifficultyLevelAccess().getNORMALEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getDifficultyLevelAccess().getNORMALEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalMazeDsl.g:5171:3: (enumLiteral_2= 'hard' )
                    {
                    // InternalMazeDsl.g:5171:3: (enumLiteral_2= 'hard' )
                    // InternalMazeDsl.g:5172:4: enumLiteral_2= 'hard'
                    {
                    enumLiteral_2=(Token)match(input,145,FOLLOW_2); 

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
    // InternalMazeDsl.g:5182:1: ruleEnemyType returns [Enumerator current=null] : ( (enumLiteral_0= 'zombie' ) | (enumLiteral_1= 'ghost' ) | (enumLiteral_2= 'pumpkinbomber' ) ) ;
    public final Enumerator ruleEnemyType() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalMazeDsl.g:5188:2: ( ( (enumLiteral_0= 'zombie' ) | (enumLiteral_1= 'ghost' ) | (enumLiteral_2= 'pumpkinbomber' ) ) )
            // InternalMazeDsl.g:5189:2: ( (enumLiteral_0= 'zombie' ) | (enumLiteral_1= 'ghost' ) | (enumLiteral_2= 'pumpkinbomber' ) )
            {
            // InternalMazeDsl.g:5189:2: ( (enumLiteral_0= 'zombie' ) | (enumLiteral_1= 'ghost' ) | (enumLiteral_2= 'pumpkinbomber' ) )
            int alt87=3;
            switch ( input.LA(1) ) {
            case 146:
                {
                alt87=1;
                }
                break;
            case 147:
                {
                alt87=2;
                }
                break;
            case 148:
                {
                alt87=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 87, 0, input);

                throw nvae;
            }

            switch (alt87) {
                case 1 :
                    // InternalMazeDsl.g:5190:3: (enumLiteral_0= 'zombie' )
                    {
                    // InternalMazeDsl.g:5190:3: (enumLiteral_0= 'zombie' )
                    // InternalMazeDsl.g:5191:4: enumLiteral_0= 'zombie'
                    {
                    enumLiteral_0=(Token)match(input,146,FOLLOW_2); 

                    				current = grammarAccess.getEnemyTypeAccess().getZOMBIEEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getEnemyTypeAccess().getZOMBIEEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:5198:3: (enumLiteral_1= 'ghost' )
                    {
                    // InternalMazeDsl.g:5198:3: (enumLiteral_1= 'ghost' )
                    // InternalMazeDsl.g:5199:4: enumLiteral_1= 'ghost'
                    {
                    enumLiteral_1=(Token)match(input,147,FOLLOW_2); 

                    				current = grammarAccess.getEnemyTypeAccess().getGHOSTEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getEnemyTypeAccess().getGHOSTEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalMazeDsl.g:5206:3: (enumLiteral_2= 'pumpkinbomber' )
                    {
                    // InternalMazeDsl.g:5206:3: (enumLiteral_2= 'pumpkinbomber' )
                    // InternalMazeDsl.g:5207:4: enumLiteral_2= 'pumpkinbomber'
                    {
                    enumLiteral_2=(Token)match(input,148,FOLLOW_2); 

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
    // InternalMazeDsl.g:5217:1: ruleCharacterTypeEnum returns [Enumerator current=null] : ( (enumLiteral_0= 'zombie' ) | (enumLiteral_1= 'ghost' ) | (enumLiteral_2= 'pumpkinbomber' ) ) ;
    public final Enumerator ruleCharacterTypeEnum() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalMazeDsl.g:5223:2: ( ( (enumLiteral_0= 'zombie' ) | (enumLiteral_1= 'ghost' ) | (enumLiteral_2= 'pumpkinbomber' ) ) )
            // InternalMazeDsl.g:5224:2: ( (enumLiteral_0= 'zombie' ) | (enumLiteral_1= 'ghost' ) | (enumLiteral_2= 'pumpkinbomber' ) )
            {
            // InternalMazeDsl.g:5224:2: ( (enumLiteral_0= 'zombie' ) | (enumLiteral_1= 'ghost' ) | (enumLiteral_2= 'pumpkinbomber' ) )
            int alt88=3;
            switch ( input.LA(1) ) {
            case 146:
                {
                alt88=1;
                }
                break;
            case 147:
                {
                alt88=2;
                }
                break;
            case 148:
                {
                alt88=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 88, 0, input);

                throw nvae;
            }

            switch (alt88) {
                case 1 :
                    // InternalMazeDsl.g:5225:3: (enumLiteral_0= 'zombie' )
                    {
                    // InternalMazeDsl.g:5225:3: (enumLiteral_0= 'zombie' )
                    // InternalMazeDsl.g:5226:4: enumLiteral_0= 'zombie'
                    {
                    enumLiteral_0=(Token)match(input,146,FOLLOW_2); 

                    				current = grammarAccess.getCharacterTypeEnumAccess().getZOMBIEEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getCharacterTypeEnumAccess().getZOMBIEEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:5233:3: (enumLiteral_1= 'ghost' )
                    {
                    // InternalMazeDsl.g:5233:3: (enumLiteral_1= 'ghost' )
                    // InternalMazeDsl.g:5234:4: enumLiteral_1= 'ghost'
                    {
                    enumLiteral_1=(Token)match(input,147,FOLLOW_2); 

                    				current = grammarAccess.getCharacterTypeEnumAccess().getGHOSTEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getCharacterTypeEnumAccess().getGHOSTEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalMazeDsl.g:5241:3: (enumLiteral_2= 'pumpkinbomber' )
                    {
                    // InternalMazeDsl.g:5241:3: (enumLiteral_2= 'pumpkinbomber' )
                    // InternalMazeDsl.g:5242:4: enumLiteral_2= 'pumpkinbomber'
                    {
                    enumLiteral_2=(Token)match(input,148,FOLLOW_2); 

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
    // InternalMazeDsl.g:5252:1: ruleBehaviorTypeEnum returns [Enumerator current=null] : ( (enumLiteral_0= 'passive' ) | (enumLiteral_1= 'wander' ) | (enumLiteral_2= 'aggressive' ) | (enumLiteral_3= 'patrol' ) ) ;
    public final Enumerator ruleBehaviorTypeEnum() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;


        	enterRule();

        try {
            // InternalMazeDsl.g:5258:2: ( ( (enumLiteral_0= 'passive' ) | (enumLiteral_1= 'wander' ) | (enumLiteral_2= 'aggressive' ) | (enumLiteral_3= 'patrol' ) ) )
            // InternalMazeDsl.g:5259:2: ( (enumLiteral_0= 'passive' ) | (enumLiteral_1= 'wander' ) | (enumLiteral_2= 'aggressive' ) | (enumLiteral_3= 'patrol' ) )
            {
            // InternalMazeDsl.g:5259:2: ( (enumLiteral_0= 'passive' ) | (enumLiteral_1= 'wander' ) | (enumLiteral_2= 'aggressive' ) | (enumLiteral_3= 'patrol' ) )
            int alt89=4;
            switch ( input.LA(1) ) {
            case 149:
                {
                alt89=1;
                }
                break;
            case 150:
                {
                alt89=2;
                }
                break;
            case 151:
                {
                alt89=3;
                }
                break;
            case 32:
                {
                alt89=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 89, 0, input);

                throw nvae;
            }

            switch (alt89) {
                case 1 :
                    // InternalMazeDsl.g:5260:3: (enumLiteral_0= 'passive' )
                    {
                    // InternalMazeDsl.g:5260:3: (enumLiteral_0= 'passive' )
                    // InternalMazeDsl.g:5261:4: enumLiteral_0= 'passive'
                    {
                    enumLiteral_0=(Token)match(input,149,FOLLOW_2); 

                    				current = grammarAccess.getBehaviorTypeEnumAccess().getPASSIVEEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getBehaviorTypeEnumAccess().getPASSIVEEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:5268:3: (enumLiteral_1= 'wander' )
                    {
                    // InternalMazeDsl.g:5268:3: (enumLiteral_1= 'wander' )
                    // InternalMazeDsl.g:5269:4: enumLiteral_1= 'wander'
                    {
                    enumLiteral_1=(Token)match(input,150,FOLLOW_2); 

                    				current = grammarAccess.getBehaviorTypeEnumAccess().getWANDEREnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getBehaviorTypeEnumAccess().getWANDEREnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalMazeDsl.g:5276:3: (enumLiteral_2= 'aggressive' )
                    {
                    // InternalMazeDsl.g:5276:3: (enumLiteral_2= 'aggressive' )
                    // InternalMazeDsl.g:5277:4: enumLiteral_2= 'aggressive'
                    {
                    enumLiteral_2=(Token)match(input,151,FOLLOW_2); 

                    				current = grammarAccess.getBehaviorTypeEnumAccess().getAGGRESSIVEEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getBehaviorTypeEnumAccess().getAGGRESSIVEEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalMazeDsl.g:5284:3: (enumLiteral_3= 'patrol' )
                    {
                    // InternalMazeDsl.g:5284:3: (enumLiteral_3= 'patrol' )
                    // InternalMazeDsl.g:5285:4: enumLiteral_3= 'patrol'
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
    // InternalMazeDsl.g:5295:1: ruleProjectileTypeEnum returns [Enumerator current=null] : ( (enumLiteral_0= 'straight' ) | (enumLiteral_1= 'lob' ) | (enumLiteral_2= 'beam' ) ) ;
    public final Enumerator ruleProjectileTypeEnum() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalMazeDsl.g:5301:2: ( ( (enumLiteral_0= 'straight' ) | (enumLiteral_1= 'lob' ) | (enumLiteral_2= 'beam' ) ) )
            // InternalMazeDsl.g:5302:2: ( (enumLiteral_0= 'straight' ) | (enumLiteral_1= 'lob' ) | (enumLiteral_2= 'beam' ) )
            {
            // InternalMazeDsl.g:5302:2: ( (enumLiteral_0= 'straight' ) | (enumLiteral_1= 'lob' ) | (enumLiteral_2= 'beam' ) )
            int alt90=3;
            switch ( input.LA(1) ) {
            case 152:
                {
                alt90=1;
                }
                break;
            case 153:
                {
                alt90=2;
                }
                break;
            case 154:
                {
                alt90=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 90, 0, input);

                throw nvae;
            }

            switch (alt90) {
                case 1 :
                    // InternalMazeDsl.g:5303:3: (enumLiteral_0= 'straight' )
                    {
                    // InternalMazeDsl.g:5303:3: (enumLiteral_0= 'straight' )
                    // InternalMazeDsl.g:5304:4: enumLiteral_0= 'straight'
                    {
                    enumLiteral_0=(Token)match(input,152,FOLLOW_2); 

                    				current = grammarAccess.getProjectileTypeEnumAccess().getSTRAIGHTEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getProjectileTypeEnumAccess().getSTRAIGHTEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:5311:3: (enumLiteral_1= 'lob' )
                    {
                    // InternalMazeDsl.g:5311:3: (enumLiteral_1= 'lob' )
                    // InternalMazeDsl.g:5312:4: enumLiteral_1= 'lob'
                    {
                    enumLiteral_1=(Token)match(input,153,FOLLOW_2); 

                    				current = grammarAccess.getProjectileTypeEnumAccess().getLOBEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getProjectileTypeEnumAccess().getLOBEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalMazeDsl.g:5319:3: (enumLiteral_2= 'beam' )
                    {
                    // InternalMazeDsl.g:5319:3: (enumLiteral_2= 'beam' )
                    // InternalMazeDsl.g:5320:4: enumLiteral_2= 'beam'
                    {
                    enumLiteral_2=(Token)match(input,154,FOLLOW_2); 

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
    // InternalMazeDsl.g:5330:1: ruleLootItemTypeEnum returns [Enumerator current=null] : ( (enumLiteral_0= 'food' ) | (enumLiteral_1= 'bomb' ) | (enumLiteral_2= 'trap' ) | (enumLiteral_3= 'weapon' ) ) ;
    public final Enumerator ruleLootItemTypeEnum() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;


        	enterRule();

        try {
            // InternalMazeDsl.g:5336:2: ( ( (enumLiteral_0= 'food' ) | (enumLiteral_1= 'bomb' ) | (enumLiteral_2= 'trap' ) | (enumLiteral_3= 'weapon' ) ) )
            // InternalMazeDsl.g:5337:2: ( (enumLiteral_0= 'food' ) | (enumLiteral_1= 'bomb' ) | (enumLiteral_2= 'trap' ) | (enumLiteral_3= 'weapon' ) )
            {
            // InternalMazeDsl.g:5337:2: ( (enumLiteral_0= 'food' ) | (enumLiteral_1= 'bomb' ) | (enumLiteral_2= 'trap' ) | (enumLiteral_3= 'weapon' ) )
            int alt91=4;
            switch ( input.LA(1) ) {
            case 155:
                {
                alt91=1;
                }
                break;
            case 156:
                {
                alt91=2;
                }
                break;
            case 157:
                {
                alt91=3;
                }
                break;
            case 158:
                {
                alt91=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 91, 0, input);

                throw nvae;
            }

            switch (alt91) {
                case 1 :
                    // InternalMazeDsl.g:5338:3: (enumLiteral_0= 'food' )
                    {
                    // InternalMazeDsl.g:5338:3: (enumLiteral_0= 'food' )
                    // InternalMazeDsl.g:5339:4: enumLiteral_0= 'food'
                    {
                    enumLiteral_0=(Token)match(input,155,FOLLOW_2); 

                    				current = grammarAccess.getLootItemTypeEnumAccess().getFOODEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getLootItemTypeEnumAccess().getFOODEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:5346:3: (enumLiteral_1= 'bomb' )
                    {
                    // InternalMazeDsl.g:5346:3: (enumLiteral_1= 'bomb' )
                    // InternalMazeDsl.g:5347:4: enumLiteral_1= 'bomb'
                    {
                    enumLiteral_1=(Token)match(input,156,FOLLOW_2); 

                    				current = grammarAccess.getLootItemTypeEnumAccess().getBOMBEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getLootItemTypeEnumAccess().getBOMBEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalMazeDsl.g:5354:3: (enumLiteral_2= 'trap' )
                    {
                    // InternalMazeDsl.g:5354:3: (enumLiteral_2= 'trap' )
                    // InternalMazeDsl.g:5355:4: enumLiteral_2= 'trap'
                    {
                    enumLiteral_2=(Token)match(input,157,FOLLOW_2); 

                    				current = grammarAccess.getLootItemTypeEnumAccess().getTRAPEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getLootItemTypeEnumAccess().getTRAPEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalMazeDsl.g:5362:3: (enumLiteral_3= 'weapon' )
                    {
                    // InternalMazeDsl.g:5362:3: (enumLiteral_3= 'weapon' )
                    // InternalMazeDsl.g:5363:4: enumLiteral_3= 'weapon'
                    {
                    enumLiteral_3=(Token)match(input,158,FOLLOW_2); 

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
    // InternalMazeDsl.g:5373:1: ruleDropWeaponEnum returns [Enumerator current=null] : (enumLiteral_0= 'gatling' ) ;
    public final Enumerator ruleDropWeaponEnum() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalMazeDsl.g:5379:2: ( (enumLiteral_0= 'gatling' ) )
            // InternalMazeDsl.g:5380:2: (enumLiteral_0= 'gatling' )
            {
            // InternalMazeDsl.g:5380:2: (enumLiteral_0= 'gatling' )
            // InternalMazeDsl.g:5381:3: enumLiteral_0= 'gatling'
            {
            enumLiteral_0=(Token)match(input,142,FOLLOW_2); 

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
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0100000000000040L,0xFFFFFFFFFFFFFFFEL,0x00000000000047FFL});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x100000010101E000L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x1000000101012000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x1000000100012000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x1000000000012000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000012000L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000038000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x00000000007A2000L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000000782000L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000000000020L,0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000000702000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000000602000L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000000402000L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x00000000001C0000L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x00000247FC002000L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x00000247F8002000L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x00000247F0002000L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x00000247E0002000L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x00000247C0002000L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000000000040000L,0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000024780002000L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0000000100000000L,0x0000000000000000L,0x0000000000E00000L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0000024700002000L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0000000300002000L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0000000200002000L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000003800002000L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0000000000000020L});
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
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000007000000L});
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
    public static final BitSet FOLLOW_64 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000078000000L});
    public static final BitSet FOLLOW_65 = new BitSet(new long[]{0x8000000000000000L});
    public static final BitSet FOLLOW_66 = new BitSet(new long[]{0x0000000000002000L,0x0000000000000001L});
    public static final BitSet FOLLOW_67 = new BitSet(new long[]{0x0000000000002000L,0x03030001C018027EL,0x0000000000000028L});
    public static final BitSet FOLLOW_68 = new BitSet(new long[]{0x0000000000002000L,0x03030001C018027CL,0x0000000000000028L});
    public static final BitSet FOLLOW_69 = new BitSet(new long[]{0x0000000000002000L,0x03030001C0180278L,0x0000000000000028L});
    public static final BitSet FOLLOW_70 = new BitSet(new long[]{0x0000000000002000L,0x03030001C0180270L,0x0000000000000028L});
    public static final BitSet FOLLOW_71 = new BitSet(new long[]{0x0000000000002000L,0x03030001C0180260L,0x0000000000000028L});
    public static final BitSet FOLLOW_72 = new BitSet(new long[]{0x0000000000002000L,0x03030001C0180240L,0x0000000000000028L});
    public static final BitSet FOLLOW_73 = new BitSet(new long[]{0x0000000000002000L,0x03030001C0180000L,0x0000000000000028L});
    public static final BitSet FOLLOW_74 = new BitSet(new long[]{0x0000000000002000L,0x03030001C0000000L,0x0000000000000028L});
    public static final BitSet FOLLOW_75 = new BitSet(new long[]{0x0000000000002000L,0x0303000180000000L,0x0000000000000028L});
    public static final BitSet FOLLOW_76 = new BitSet(new long[]{0x0000000000002000L,0x0303000100000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_77 = new BitSet(new long[]{0x0000000000002000L,0x0303000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_78 = new BitSet(new long[]{0x0000000000002000L,0x0300000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_79 = new BitSet(new long[]{0x0000000000002000L,0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_80 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000180L});
    public static final BitSet FOLLOW_81 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_82 = new BitSet(new long[]{0x0004000000000002L});
    public static final BitSet FOLLOW_83 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_84 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_85 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_86 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_87 = new BitSet(new long[]{0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_88 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_89 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_90 = new BitSet(new long[]{0x0000000000000000L,0x0000000000030000L});
    public static final BitSet FOLLOW_91 = new BitSet(new long[]{0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_92 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_93 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_94 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_95 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_96 = new BitSet(new long[]{0x0000000000000000L,0x0000000001800000L});
    public static final BitSet FOLLOW_97 = new BitSet(new long[]{0x0000000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_98 = new BitSet(new long[]{0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_99 = new BitSet(new long[]{0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_100 = new BitSet(new long[]{0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_101 = new BitSet(new long[]{0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_102 = new BitSet(new long[]{0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_103 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_104 = new BitSet(new long[]{0x0000000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_105 = new BitSet(new long[]{0x0000000000000000L,0x0000000400000000L});
    public static final BitSet FOLLOW_106 = new BitSet(new long[]{0x0000000000000000L,0x0000000800000000L});
    public static final BitSet FOLLOW_107 = new BitSet(new long[]{0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_108 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L});
    public static final BitSet FOLLOW_109 = new BitSet(new long[]{0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_110 = new BitSet(new long[]{0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_111 = new BitSet(new long[]{0x0000000000000000L,0x0000010000000000L});
    public static final BitSet FOLLOW_112 = new BitSet(new long[]{0x0000000000000000L,0x0000020000000000L});
    public static final BitSet FOLLOW_113 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L});
    public static final BitSet FOLLOW_114 = new BitSet(new long[]{0x0000000000002000L,0x0000F80000000000L});
    public static final BitSet FOLLOW_115 = new BitSet(new long[]{0x0000000000002000L,0x0000F00000000000L});
    public static final BitSet FOLLOW_116 = new BitSet(new long[]{0x0000000000002000L,0x0000E00000000000L});
    public static final BitSet FOLLOW_117 = new BitSet(new long[]{0x0000000000002000L,0x0000C00000000000L});
    public static final BitSet FOLLOW_118 = new BitSet(new long[]{0x0000000000002000L,0x0000800000000000L});
    public static final BitSet FOLLOW_119 = new BitSet(new long[]{0x0000000000000000L,0x0004000000000000L});
    public static final BitSet FOLLOW_120 = new BitSet(new long[]{0x0000000000000000L,0x0008000000000000L});
    public static final BitSet FOLLOW_121 = new BitSet(new long[]{0x0000000000000000L,0x0010000000000000L});
    public static final BitSet FOLLOW_122 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_123 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L});
    public static final BitSet FOLLOW_124 = new BitSet(new long[]{0x0000000000002000L,0x0080000000000000L});
    public static final BitSet FOLLOW_125 = new BitSet(new long[]{0x0000000000000000L,0x0400000000000000L});
    public static final BitSet FOLLOW_126 = new BitSet(new long[]{0x0000000000000000L,0x0800000000000000L});
    public static final BitSet FOLLOW_127 = new BitSet(new long[]{0x0000000000000000L,0x1000000000000000L});
    public static final BitSet FOLLOW_128 = new BitSet(new long[]{0x0000000000002000L,0xE000000000000000L,0x0000000000000007L});
    public static final BitSet FOLLOW_129 = new BitSet(new long[]{0x0000000000002000L,0xC000000000000000L,0x0000000000000007L});
    public static final BitSet FOLLOW_130 = new BitSet(new long[]{0x0000000000002000L,0x8000000000000000L,0x0000000000000007L});
    public static final BitSet FOLLOW_131 = new BitSet(new long[]{0x0000000000002000L,0x0000000000000000L,0x0000000000000007L});
    public static final BitSet FOLLOW_132 = new BitSet(new long[]{0x0000000000002000L,0x0000000000000000L,0x0000000000000006L});
    public static final BitSet FOLLOW_133 = new BitSet(new long[]{0x0000000000002000L,0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_134 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_135 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_136 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_137 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_138 = new BitSet(new long[]{0x0000000000002000L,0x0000000000000000L,0x0000000000000600L});
    public static final BitSet FOLLOW_139 = new BitSet(new long[]{0x0000000000002000L,0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_140 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000001000L});

}