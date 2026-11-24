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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_STRING", "RULE_INT", "RULE_ID", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'game'", "'{'", "'}'", "'import'", "'difficulty'", "'level'", "'instantDeath'", "'true'", "'speedMultiplier'", "'damageMultiplier'", "'maxThreat'", "'limit'", "'max'", "'opponent'", "'type'", "'displayName'", "'health'", "'speed'", "'threatLevel'", "'enabled'", "'behavior'", "'patrol'", "'loot'", "'zombie-stats'", "'attackDamage'", "'infectionLevel'", "'resurrectionTime'", "'ghost-stats'", "'visibilityLevel'", "'nonTangibilityEnergy'", "'ranged-stats'", "'attackRange'", "'attackCooldown'", "'projectileSpeed'", "'projectileType'", "'splashRadius'", "'visionRange'", "'path'", "'['", "','", "']'", "'zone'", "'topLeft'", "'('", "')'", "'width'", "'height'", "':'", "'ms'", "'loot-table'", "'capacity'", "'item'", "'value'", "'weight'", "'basedOn'", "'threatCapacity'", "'wallMaterials'", "'inheritWeapons'", "'cascadeDepth'", "'ring'", "'share'", "'materials'", "'sergeant'", "'sightRange'", "'rounds'", "'roundDamage'", "'roundInterval'", "'burstCooldown'", "'sprite'", "'fireSound'", "'drops'", "'postDistance'", "'sergeants'", "'mines'", "'count'", "'ringRadius'", "'secondRing'", "'triggerRadius'", "'pulse'", "'blastRadius'", "'blastDamage'", "'playerDamage'", "'holeRadius'", "'portalGhost'", "'portalGhosts'", "'guardTower'", "'wallsOver'", "'rpgInterval'", "'rpgSpeed'", "'rpgBlastRadius'", "'rpgPlayerDamage'", "'rpgWallDamage'", "'gatlingRounds'", "'gatlingInterval'", "'gatlingDamage'", "'deathBlast'", "'rpgLaunchSound'", "'rpgExplosionSound'", "'gatlingSound'", "'hitSound'", "'destroyedSound'", "'towers'", "'wallBuilder'", "'buildSeconds'", "'startRadius'", "'blockRadius'", "'spawnRadius'", "'material'", "'buildSound'", "'builders'", "'lava'", "'streams'", "'bridges'", "'bridgeLength'", "'meander'", "'wavelength'", "'flowSpeed'", "'glow'", "'crust'", "'deathSound'", "'ufoRaid'", "'trigger'", "'ufos'", "'duration'", "'ufoSpeed'", "'beamEvery'", "'charge'", "'beamSeconds'", "'beamRadius'", "'aimMin'", "'aimMax'", "'damageFraction'", "'soldiers'", "'soldierHealth'", "'soldierDamage'", "'soldierSight'", "'soldierSeconds'", "'banner'", "'ufoSprite'", "'beamSprite'", "'arriveSound'", "'beamSound'", "'shop'", "'unlock'", "'fromLevel'", "'tier'", "'price'", "'amount'", "'factor'", "'description'", "'theme'", "'font'", "'colour'", "'image'", "'slice'", "'badge'", "'lastLifeWeapon'", "'shots'", "'teleport'", "'cooldown'", "'bannerSeconds'", "'zapSound'", "'voiceSound'", "'-'", "'.'", "'false'", "'gatling'", "'easy'", "'normal'", "'hard'", "'zombie'", "'ghost'", "'pumpkinbomber'", "'passive'", "'wander'", "'aggressive'", "'straight'", "'lob'", "'beam'", "'food'", "'bomb'", "'trap'", "'weapon'"
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
    public static final int T__166=166;
    public static final int T__165=165;
    public static final int T__168=168;
    public static final int T__167=167;
    public static final int T__162=162;
    public static final int T__161=161;
    public static final int T__164=164;
    public static final int T__163=163;
    public static final int T__160=160;
    public static final int T__37=37;
    public static final int T__38=38;
    public static final int T__39=39;
    public static final int T__33=33;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int T__159=159;
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
    public static final int T__100=100;
    public static final int T__102=102;
    public static final int T__101=101;
    public static final int T__19=19;
    public static final int T__15=15;
    public static final int T__16=16;
    public static final int T__17=17;
    public static final int T__18=18;
    public static final int T__11=11;
    public static final int T__12=12;
    public static final int T__13=13;
    public static final int T__14=14;
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
    public static final int T__121=121;
    public static final int T__124=124;
    public static final int T__123=123;
    public static final int T__120=120;
    public static final int RULE_SL_COMMENT=8;
    public static final int T__119=119;
    public static final int T__118=118;
    public static final int T__115=115;
    public static final int EOF=-1;
    public static final int T__114=114;
    public static final int T__117=117;
    public static final int T__116=116;
    public static final int T__111=111;
    public static final int T__110=110;
    public static final int T__113=113;
    public static final int T__112=112;
    public static final int T__108=108;
    public static final int T__107=107;
    public static final int T__109=109;
    public static final int T__104=104;
    public static final int T__103=103;
    public static final int T__106=106;
    public static final int T__105=105;
    public static final int RULE_ML_COMMENT=7;
    public static final int T__91=91;
    public static final int T__188=188;
    public static final int T__92=92;
    public static final int T__187=187;
    public static final int T__93=93;
    public static final int T__94=94;
    public static final int T__189=189;
    public static final int T__184=184;
    public static final int T__183=183;
    public static final int T__186=186;
    public static final int T__90=90;
    public static final int T__185=185;
    public static final int T__180=180;
    public static final int T__182=182;
    public static final int T__181=181;
    public static final int T__99=99;
    public static final int T__95=95;
    public static final int T__96=96;
    public static final int T__97=97;
    public static final int T__98=98;
    public static final int T__177=177;
    public static final int T__176=176;
    public static final int T__179=179;
    public static final int T__178=178;
    public static final int T__173=173;
    public static final int T__172=172;
    public static final int T__175=175;
    public static final int T__174=174;
    public static final int T__171=171;
    public static final int T__170=170;
    public static final int T__169=169;
    public static final int T__70=70;
    public static final int T__71=71;
    public static final int T__72=72;
    public static final int RULE_STRING=4;
    public static final int T__77=77;
    public static final int T__78=78;
    public static final int T__79=79;
    public static final int T__73=73;
    public static final int T__74=74;
    public static final int T__75=75;
    public static final int T__76=76;
    public static final int T__80=80;
    public static final int T__81=81;
    public static final int T__82=82;
    public static final int T__83=83;
    public static final int RULE_WS=9;
    public static final int T__191=191;
    public static final int T__190=190;
    public static final int T__193=193;
    public static final int T__192=192;
    public static final int RULE_ANY_OTHER=10;
    public static final int T__88=88;
    public static final int T__89=89;
    public static final int T__84=84;
    public static final int T__85=85;
    public static final int T__86=86;
    public static final int T__87=87;

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
    // InternalMazeDsl.g:72:1: ruleGameConfiguration returns [EObject current=null] : (otherlv_0= 'game' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' ( (lv_imports_3_0= ruleImport ) )* ( (lv_difficulty_4_0= ruleDifficultyConfig ) )? ( (lv_opponents_5_0= ruleOpponentConfig ) )* ( (lv_patrols_6_0= rulePatrolConfig ) )* ( (lv_lootTables_7_0= ruleLootTableConfig ) )* ( (lv_levels_8_0= ruleLevelConfig ) )* ( (lv_shop_9_0= ruleShopConfig ) )? ( (lv_theme_10_0= ruleMenuThemeConfig ) )? otherlv_11= '}' ) ;
    public final EObject ruleGameConfiguration() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_11=null;
        AntlrDatatypeRuleToken lv_name_1_0 = null;

        EObject lv_imports_3_0 = null;

        EObject lv_difficulty_4_0 = null;

        EObject lv_opponents_5_0 = null;

        EObject lv_patrols_6_0 = null;

        EObject lv_lootTables_7_0 = null;

        EObject lv_levels_8_0 = null;

        EObject lv_shop_9_0 = null;

        EObject lv_theme_10_0 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:78:2: ( (otherlv_0= 'game' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' ( (lv_imports_3_0= ruleImport ) )* ( (lv_difficulty_4_0= ruleDifficultyConfig ) )? ( (lv_opponents_5_0= ruleOpponentConfig ) )* ( (lv_patrols_6_0= rulePatrolConfig ) )* ( (lv_lootTables_7_0= ruleLootTableConfig ) )* ( (lv_levels_8_0= ruleLevelConfig ) )* ( (lv_shop_9_0= ruleShopConfig ) )? ( (lv_theme_10_0= ruleMenuThemeConfig ) )? otherlv_11= '}' ) )
            // InternalMazeDsl.g:79:2: (otherlv_0= 'game' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' ( (lv_imports_3_0= ruleImport ) )* ( (lv_difficulty_4_0= ruleDifficultyConfig ) )? ( (lv_opponents_5_0= ruleOpponentConfig ) )* ( (lv_patrols_6_0= rulePatrolConfig ) )* ( (lv_lootTables_7_0= ruleLootTableConfig ) )* ( (lv_levels_8_0= ruleLevelConfig ) )* ( (lv_shop_9_0= ruleShopConfig ) )? ( (lv_theme_10_0= ruleMenuThemeConfig ) )? otherlv_11= '}' )
            {
            // InternalMazeDsl.g:79:2: (otherlv_0= 'game' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' ( (lv_imports_3_0= ruleImport ) )* ( (lv_difficulty_4_0= ruleDifficultyConfig ) )? ( (lv_opponents_5_0= ruleOpponentConfig ) )* ( (lv_patrols_6_0= rulePatrolConfig ) )* ( (lv_lootTables_7_0= ruleLootTableConfig ) )* ( (lv_levels_8_0= ruleLevelConfig ) )* ( (lv_shop_9_0= ruleShopConfig ) )? ( (lv_theme_10_0= ruleMenuThemeConfig ) )? otherlv_11= '}' )
            // InternalMazeDsl.g:80:3: otherlv_0= 'game' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' ( (lv_imports_3_0= ruleImport ) )* ( (lv_difficulty_4_0= ruleDifficultyConfig ) )? ( (lv_opponents_5_0= ruleOpponentConfig ) )* ( (lv_patrols_6_0= rulePatrolConfig ) )* ( (lv_lootTables_7_0= ruleLootTableConfig ) )* ( (lv_levels_8_0= ruleLevelConfig ) )* ( (lv_shop_9_0= ruleShopConfig ) )? ( (lv_theme_10_0= ruleMenuThemeConfig ) )? otherlv_11= '}'
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

            // InternalMazeDsl.g:221:3: ( (lv_shop_9_0= ruleShopConfig ) )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==153) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // InternalMazeDsl.g:222:4: (lv_shop_9_0= ruleShopConfig )
                    {
                    // InternalMazeDsl.g:222:4: (lv_shop_9_0= ruleShopConfig )
                    // InternalMazeDsl.g:223:5: lv_shop_9_0= ruleShopConfig
                    {

                    					newCompositeNode(grammarAccess.getGameConfigurationAccess().getShopShopConfigParserRuleCall_9_0());
                    				
                    pushFollow(FOLLOW_10);
                    lv_shop_9_0=ruleShopConfig();

                    state._fsp--;


                    					if (current==null) {
                    						current = createModelElementForParent(grammarAccess.getGameConfigurationRule());
                    					}
                    					set(
                    						current,
                    						"shop",
                    						lv_shop_9_0,
                    						"main.game.maze.dsl.MazeDsl.ShopConfig");
                    					afterParserOrEnumRuleCall();
                    				

                    }


                    }
                    break;

            }

            // InternalMazeDsl.g:240:3: ( (lv_theme_10_0= ruleMenuThemeConfig ) )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==161) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // InternalMazeDsl.g:241:4: (lv_theme_10_0= ruleMenuThemeConfig )
                    {
                    // InternalMazeDsl.g:241:4: (lv_theme_10_0= ruleMenuThemeConfig )
                    // InternalMazeDsl.g:242:5: lv_theme_10_0= ruleMenuThemeConfig
                    {

                    					newCompositeNode(grammarAccess.getGameConfigurationAccess().getThemeMenuThemeConfigParserRuleCall_10_0());
                    				
                    pushFollow(FOLLOW_11);
                    lv_theme_10_0=ruleMenuThemeConfig();

                    state._fsp--;


                    					if (current==null) {
                    						current = createModelElementForParent(grammarAccess.getGameConfigurationRule());
                    					}
                    					set(
                    						current,
                    						"theme",
                    						lv_theme_10_0,
                    						"main.game.maze.dsl.MazeDsl.MenuThemeConfig");
                    					afterParserOrEnumRuleCall();
                    				

                    }


                    }
                    break;

            }

            otherlv_11=(Token)match(input,13,FOLLOW_2); 

            			newLeafNode(otherlv_11, grammarAccess.getGameConfigurationAccess().getRightCurlyBracketKeyword_11());
            		

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
    // InternalMazeDsl.g:267:1: entryRuleImport returns [EObject current=null] : iv_ruleImport= ruleImport EOF ;
    public final EObject entryRuleImport() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleImport = null;


        try {
            // InternalMazeDsl.g:267:47: (iv_ruleImport= ruleImport EOF )
            // InternalMazeDsl.g:268:2: iv_ruleImport= ruleImport EOF
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
    // InternalMazeDsl.g:274:1: ruleImport returns [EObject current=null] : (otherlv_0= 'import' ( (lv_importURI_1_0= RULE_STRING ) ) ) ;
    public final EObject ruleImport() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_importURI_1_0=null;


        	enterRule();

        try {
            // InternalMazeDsl.g:280:2: ( (otherlv_0= 'import' ( (lv_importURI_1_0= RULE_STRING ) ) ) )
            // InternalMazeDsl.g:281:2: (otherlv_0= 'import' ( (lv_importURI_1_0= RULE_STRING ) ) )
            {
            // InternalMazeDsl.g:281:2: (otherlv_0= 'import' ( (lv_importURI_1_0= RULE_STRING ) ) )
            // InternalMazeDsl.g:282:3: otherlv_0= 'import' ( (lv_importURI_1_0= RULE_STRING ) )
            {
            otherlv_0=(Token)match(input,14,FOLLOW_12); 

            			newLeafNode(otherlv_0, grammarAccess.getImportAccess().getImportKeyword_0());
            		
            // InternalMazeDsl.g:286:3: ( (lv_importURI_1_0= RULE_STRING ) )
            // InternalMazeDsl.g:287:4: (lv_importURI_1_0= RULE_STRING )
            {
            // InternalMazeDsl.g:287:4: (lv_importURI_1_0= RULE_STRING )
            // InternalMazeDsl.g:288:5: lv_importURI_1_0= RULE_STRING
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
    // InternalMazeDsl.g:308:1: entryRuleDifficultyConfig returns [EObject current=null] : iv_ruleDifficultyConfig= ruleDifficultyConfig EOF ;
    public final EObject entryRuleDifficultyConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDifficultyConfig = null;


        try {
            // InternalMazeDsl.g:308:57: (iv_ruleDifficultyConfig= ruleDifficultyConfig EOF )
            // InternalMazeDsl.g:309:2: iv_ruleDifficultyConfig= ruleDifficultyConfig EOF
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
    // InternalMazeDsl.g:315:1: ruleDifficultyConfig returns [EObject current=null] : (otherlv_0= 'difficulty' otherlv_1= '{' otherlv_2= 'level' ( (lv_level_3_0= ruleDifficultyLevel ) ) (otherlv_4= 'instantDeath' ( (lv_instantDeath_5_0= 'true' ) ) )? (otherlv_6= 'speedMultiplier' ( (lv_speedMultiplier_7_0= ruleDOUBLE ) ) )? (otherlv_8= 'damageMultiplier' ( (lv_damageMultiplier_9_0= ruleDOUBLE ) ) )? (otherlv_10= 'maxThreat' ( (lv_maxThreat_11_0= ruleSIGNED_INT ) ) )? ( (lv_enemyLimits_12_0= ruleEnemyLimit ) )* otherlv_13= '}' ) ;
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
            // InternalMazeDsl.g:321:2: ( (otherlv_0= 'difficulty' otherlv_1= '{' otherlv_2= 'level' ( (lv_level_3_0= ruleDifficultyLevel ) ) (otherlv_4= 'instantDeath' ( (lv_instantDeath_5_0= 'true' ) ) )? (otherlv_6= 'speedMultiplier' ( (lv_speedMultiplier_7_0= ruleDOUBLE ) ) )? (otherlv_8= 'damageMultiplier' ( (lv_damageMultiplier_9_0= ruleDOUBLE ) ) )? (otherlv_10= 'maxThreat' ( (lv_maxThreat_11_0= ruleSIGNED_INT ) ) )? ( (lv_enemyLimits_12_0= ruleEnemyLimit ) )* otherlv_13= '}' ) )
            // InternalMazeDsl.g:322:2: (otherlv_0= 'difficulty' otherlv_1= '{' otherlv_2= 'level' ( (lv_level_3_0= ruleDifficultyLevel ) ) (otherlv_4= 'instantDeath' ( (lv_instantDeath_5_0= 'true' ) ) )? (otherlv_6= 'speedMultiplier' ( (lv_speedMultiplier_7_0= ruleDOUBLE ) ) )? (otherlv_8= 'damageMultiplier' ( (lv_damageMultiplier_9_0= ruleDOUBLE ) ) )? (otherlv_10= 'maxThreat' ( (lv_maxThreat_11_0= ruleSIGNED_INT ) ) )? ( (lv_enemyLimits_12_0= ruleEnemyLimit ) )* otherlv_13= '}' )
            {
            // InternalMazeDsl.g:322:2: (otherlv_0= 'difficulty' otherlv_1= '{' otherlv_2= 'level' ( (lv_level_3_0= ruleDifficultyLevel ) ) (otherlv_4= 'instantDeath' ( (lv_instantDeath_5_0= 'true' ) ) )? (otherlv_6= 'speedMultiplier' ( (lv_speedMultiplier_7_0= ruleDOUBLE ) ) )? (otherlv_8= 'damageMultiplier' ( (lv_damageMultiplier_9_0= ruleDOUBLE ) ) )? (otherlv_10= 'maxThreat' ( (lv_maxThreat_11_0= ruleSIGNED_INT ) ) )? ( (lv_enemyLimits_12_0= ruleEnemyLimit ) )* otherlv_13= '}' )
            // InternalMazeDsl.g:323:3: otherlv_0= 'difficulty' otherlv_1= '{' otherlv_2= 'level' ( (lv_level_3_0= ruleDifficultyLevel ) ) (otherlv_4= 'instantDeath' ( (lv_instantDeath_5_0= 'true' ) ) )? (otherlv_6= 'speedMultiplier' ( (lv_speedMultiplier_7_0= ruleDOUBLE ) ) )? (otherlv_8= 'damageMultiplier' ( (lv_damageMultiplier_9_0= ruleDOUBLE ) ) )? (otherlv_10= 'maxThreat' ( (lv_maxThreat_11_0= ruleSIGNED_INT ) ) )? ( (lv_enemyLimits_12_0= ruleEnemyLimit ) )* otherlv_13= '}'
            {
            otherlv_0=(Token)match(input,15,FOLLOW_4); 

            			newLeafNode(otherlv_0, grammarAccess.getDifficultyConfigAccess().getDifficultyKeyword_0());
            		
            otherlv_1=(Token)match(input,12,FOLLOW_13); 

            			newLeafNode(otherlv_1, grammarAccess.getDifficultyConfigAccess().getLeftCurlyBracketKeyword_1());
            		
            otherlv_2=(Token)match(input,16,FOLLOW_14); 

            			newLeafNode(otherlv_2, grammarAccess.getDifficultyConfigAccess().getLevelKeyword_2());
            		
            // InternalMazeDsl.g:335:3: ( (lv_level_3_0= ruleDifficultyLevel ) )
            // InternalMazeDsl.g:336:4: (lv_level_3_0= ruleDifficultyLevel )
            {
            // InternalMazeDsl.g:336:4: (lv_level_3_0= ruleDifficultyLevel )
            // InternalMazeDsl.g:337:5: lv_level_3_0= ruleDifficultyLevel
            {

            					newCompositeNode(grammarAccess.getDifficultyConfigAccess().getLevelDifficultyLevelEnumRuleCall_3_0());
            				
            pushFollow(FOLLOW_15);
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

            // InternalMazeDsl.g:354:3: (otherlv_4= 'instantDeath' ( (lv_instantDeath_5_0= 'true' ) ) )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==17) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // InternalMazeDsl.g:355:4: otherlv_4= 'instantDeath' ( (lv_instantDeath_5_0= 'true' ) )
                    {
                    otherlv_4=(Token)match(input,17,FOLLOW_16); 

                    				newLeafNode(otherlv_4, grammarAccess.getDifficultyConfigAccess().getInstantDeathKeyword_4_0());
                    			
                    // InternalMazeDsl.g:359:4: ( (lv_instantDeath_5_0= 'true' ) )
                    // InternalMazeDsl.g:360:5: (lv_instantDeath_5_0= 'true' )
                    {
                    // InternalMazeDsl.g:360:5: (lv_instantDeath_5_0= 'true' )
                    // InternalMazeDsl.g:361:6: lv_instantDeath_5_0= 'true'
                    {
                    lv_instantDeath_5_0=(Token)match(input,18,FOLLOW_17); 

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

            // InternalMazeDsl.g:374:3: (otherlv_6= 'speedMultiplier' ( (lv_speedMultiplier_7_0= ruleDOUBLE ) ) )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==19) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // InternalMazeDsl.g:375:4: otherlv_6= 'speedMultiplier' ( (lv_speedMultiplier_7_0= ruleDOUBLE ) )
                    {
                    otherlv_6=(Token)match(input,19,FOLLOW_18); 

                    				newLeafNode(otherlv_6, grammarAccess.getDifficultyConfigAccess().getSpeedMultiplierKeyword_5_0());
                    			
                    // InternalMazeDsl.g:379:4: ( (lv_speedMultiplier_7_0= ruleDOUBLE ) )
                    // InternalMazeDsl.g:380:5: (lv_speedMultiplier_7_0= ruleDOUBLE )
                    {
                    // InternalMazeDsl.g:380:5: (lv_speedMultiplier_7_0= ruleDOUBLE )
                    // InternalMazeDsl.g:381:6: lv_speedMultiplier_7_0= ruleDOUBLE
                    {

                    						newCompositeNode(grammarAccess.getDifficultyConfigAccess().getSpeedMultiplierDOUBLEParserRuleCall_5_1_0());
                    					
                    pushFollow(FOLLOW_19);
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

            // InternalMazeDsl.g:399:3: (otherlv_8= 'damageMultiplier' ( (lv_damageMultiplier_9_0= ruleDOUBLE ) ) )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==20) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // InternalMazeDsl.g:400:4: otherlv_8= 'damageMultiplier' ( (lv_damageMultiplier_9_0= ruleDOUBLE ) )
                    {
                    otherlv_8=(Token)match(input,20,FOLLOW_18); 

                    				newLeafNode(otherlv_8, grammarAccess.getDifficultyConfigAccess().getDamageMultiplierKeyword_6_0());
                    			
                    // InternalMazeDsl.g:404:4: ( (lv_damageMultiplier_9_0= ruleDOUBLE ) )
                    // InternalMazeDsl.g:405:5: (lv_damageMultiplier_9_0= ruleDOUBLE )
                    {
                    // InternalMazeDsl.g:405:5: (lv_damageMultiplier_9_0= ruleDOUBLE )
                    // InternalMazeDsl.g:406:6: lv_damageMultiplier_9_0= ruleDOUBLE
                    {

                    						newCompositeNode(grammarAccess.getDifficultyConfigAccess().getDamageMultiplierDOUBLEParserRuleCall_6_1_0());
                    					
                    pushFollow(FOLLOW_20);
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

            // InternalMazeDsl.g:424:3: (otherlv_10= 'maxThreat' ( (lv_maxThreat_11_0= ruleSIGNED_INT ) ) )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==21) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // InternalMazeDsl.g:425:4: otherlv_10= 'maxThreat' ( (lv_maxThreat_11_0= ruleSIGNED_INT ) )
                    {
                    otherlv_10=(Token)match(input,21,FOLLOW_18); 

                    				newLeafNode(otherlv_10, grammarAccess.getDifficultyConfigAccess().getMaxThreatKeyword_7_0());
                    			
                    // InternalMazeDsl.g:429:4: ( (lv_maxThreat_11_0= ruleSIGNED_INT ) )
                    // InternalMazeDsl.g:430:5: (lv_maxThreat_11_0= ruleSIGNED_INT )
                    {
                    // InternalMazeDsl.g:430:5: (lv_maxThreat_11_0= ruleSIGNED_INT )
                    // InternalMazeDsl.g:431:6: lv_maxThreat_11_0= ruleSIGNED_INT
                    {

                    						newCompositeNode(grammarAccess.getDifficultyConfigAccess().getMaxThreatSIGNED_INTParserRuleCall_7_1_0());
                    					
                    pushFollow(FOLLOW_21);
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

            // InternalMazeDsl.g:449:3: ( (lv_enemyLimits_12_0= ruleEnemyLimit ) )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==22) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // InternalMazeDsl.g:450:4: (lv_enemyLimits_12_0= ruleEnemyLimit )
            	    {
            	    // InternalMazeDsl.g:450:4: (lv_enemyLimits_12_0= ruleEnemyLimit )
            	    // InternalMazeDsl.g:451:5: lv_enemyLimits_12_0= ruleEnemyLimit
            	    {

            	    					newCompositeNode(grammarAccess.getDifficultyConfigAccess().getEnemyLimitsEnemyLimitParserRuleCall_8_0());
            	    				
            	    pushFollow(FOLLOW_21);
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
            	    break loop13;
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
    // InternalMazeDsl.g:476:1: entryRuleEnemyLimit returns [EObject current=null] : iv_ruleEnemyLimit= ruleEnemyLimit EOF ;
    public final EObject entryRuleEnemyLimit() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEnemyLimit = null;


        try {
            // InternalMazeDsl.g:476:51: (iv_ruleEnemyLimit= ruleEnemyLimit EOF )
            // InternalMazeDsl.g:477:2: iv_ruleEnemyLimit= ruleEnemyLimit EOF
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
    // InternalMazeDsl.g:483:1: ruleEnemyLimit returns [EObject current=null] : (otherlv_0= 'limit' ( (lv_type_1_0= ruleEnemyType ) ) otherlv_2= 'max' ( (lv_maxCount_3_0= ruleSIGNED_INT ) ) ) ;
    public final EObject ruleEnemyLimit() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Enumerator lv_type_1_0 = null;

        AntlrDatatypeRuleToken lv_maxCount_3_0 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:489:2: ( (otherlv_0= 'limit' ( (lv_type_1_0= ruleEnemyType ) ) otherlv_2= 'max' ( (lv_maxCount_3_0= ruleSIGNED_INT ) ) ) )
            // InternalMazeDsl.g:490:2: (otherlv_0= 'limit' ( (lv_type_1_0= ruleEnemyType ) ) otherlv_2= 'max' ( (lv_maxCount_3_0= ruleSIGNED_INT ) ) )
            {
            // InternalMazeDsl.g:490:2: (otherlv_0= 'limit' ( (lv_type_1_0= ruleEnemyType ) ) otherlv_2= 'max' ( (lv_maxCount_3_0= ruleSIGNED_INT ) ) )
            // InternalMazeDsl.g:491:3: otherlv_0= 'limit' ( (lv_type_1_0= ruleEnemyType ) ) otherlv_2= 'max' ( (lv_maxCount_3_0= ruleSIGNED_INT ) )
            {
            otherlv_0=(Token)match(input,22,FOLLOW_22); 

            			newLeafNode(otherlv_0, grammarAccess.getEnemyLimitAccess().getLimitKeyword_0());
            		
            // InternalMazeDsl.g:495:3: ( (lv_type_1_0= ruleEnemyType ) )
            // InternalMazeDsl.g:496:4: (lv_type_1_0= ruleEnemyType )
            {
            // InternalMazeDsl.g:496:4: (lv_type_1_0= ruleEnemyType )
            // InternalMazeDsl.g:497:5: lv_type_1_0= ruleEnemyType
            {

            					newCompositeNode(grammarAccess.getEnemyLimitAccess().getTypeEnemyTypeEnumRuleCall_1_0());
            				
            pushFollow(FOLLOW_23);
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

            otherlv_2=(Token)match(input,23,FOLLOW_18); 

            			newLeafNode(otherlv_2, grammarAccess.getEnemyLimitAccess().getMaxKeyword_2());
            		
            // InternalMazeDsl.g:518:3: ( (lv_maxCount_3_0= ruleSIGNED_INT ) )
            // InternalMazeDsl.g:519:4: (lv_maxCount_3_0= ruleSIGNED_INT )
            {
            // InternalMazeDsl.g:519:4: (lv_maxCount_3_0= ruleSIGNED_INT )
            // InternalMazeDsl.g:520:5: lv_maxCount_3_0= ruleSIGNED_INT
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
    // InternalMazeDsl.g:541:1: entryRuleOpponentConfig returns [EObject current=null] : iv_ruleOpponentConfig= ruleOpponentConfig EOF ;
    public final EObject entryRuleOpponentConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOpponentConfig = null;


        try {
            // InternalMazeDsl.g:541:55: (iv_ruleOpponentConfig= ruleOpponentConfig EOF )
            // InternalMazeDsl.g:542:2: iv_ruleOpponentConfig= ruleOpponentConfig EOF
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
    // InternalMazeDsl.g:548:1: ruleOpponentConfig returns [EObject current=null] : (otherlv_0= 'opponent' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' otherlv_3= 'type' ( (lv_type_4_0= ruleCharacterTypeEnum ) ) (otherlv_5= 'displayName' ( (lv_displayName_6_0= RULE_STRING ) ) )? (otherlv_7= 'health' ( (lv_health_8_0= ruleSIGNED_INT ) ) )? (otherlv_9= 'speed' ( (lv_speed_10_0= ruleDOUBLE ) ) )? (otherlv_11= 'threatLevel' ( (lv_threatLevel_12_0= ruleDOUBLE ) ) )? (otherlv_13= 'enabled' ( (lv_enabled_14_0= ruleBOOLEAN ) ) )? (otherlv_15= 'behavior' ( (lv_behavior_16_0= ruleBehaviorTypeEnum ) ) )? ( (lv_characterSpecifics_17_0= ruleCharacterSpecifics ) )? (otherlv_18= 'patrol' ( ( ruleValidID ) ) )? (otherlv_20= 'loot' ( ( ruleValidID ) ) )? otherlv_22= '}' ) ;
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
            // InternalMazeDsl.g:554:2: ( (otherlv_0= 'opponent' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' otherlv_3= 'type' ( (lv_type_4_0= ruleCharacterTypeEnum ) ) (otherlv_5= 'displayName' ( (lv_displayName_6_0= RULE_STRING ) ) )? (otherlv_7= 'health' ( (lv_health_8_0= ruleSIGNED_INT ) ) )? (otherlv_9= 'speed' ( (lv_speed_10_0= ruleDOUBLE ) ) )? (otherlv_11= 'threatLevel' ( (lv_threatLevel_12_0= ruleDOUBLE ) ) )? (otherlv_13= 'enabled' ( (lv_enabled_14_0= ruleBOOLEAN ) ) )? (otherlv_15= 'behavior' ( (lv_behavior_16_0= ruleBehaviorTypeEnum ) ) )? ( (lv_characterSpecifics_17_0= ruleCharacterSpecifics ) )? (otherlv_18= 'patrol' ( ( ruleValidID ) ) )? (otherlv_20= 'loot' ( ( ruleValidID ) ) )? otherlv_22= '}' ) )
            // InternalMazeDsl.g:555:2: (otherlv_0= 'opponent' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' otherlv_3= 'type' ( (lv_type_4_0= ruleCharacterTypeEnum ) ) (otherlv_5= 'displayName' ( (lv_displayName_6_0= RULE_STRING ) ) )? (otherlv_7= 'health' ( (lv_health_8_0= ruleSIGNED_INT ) ) )? (otherlv_9= 'speed' ( (lv_speed_10_0= ruleDOUBLE ) ) )? (otherlv_11= 'threatLevel' ( (lv_threatLevel_12_0= ruleDOUBLE ) ) )? (otherlv_13= 'enabled' ( (lv_enabled_14_0= ruleBOOLEAN ) ) )? (otherlv_15= 'behavior' ( (lv_behavior_16_0= ruleBehaviorTypeEnum ) ) )? ( (lv_characterSpecifics_17_0= ruleCharacterSpecifics ) )? (otherlv_18= 'patrol' ( ( ruleValidID ) ) )? (otherlv_20= 'loot' ( ( ruleValidID ) ) )? otherlv_22= '}' )
            {
            // InternalMazeDsl.g:555:2: (otherlv_0= 'opponent' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' otherlv_3= 'type' ( (lv_type_4_0= ruleCharacterTypeEnum ) ) (otherlv_5= 'displayName' ( (lv_displayName_6_0= RULE_STRING ) ) )? (otherlv_7= 'health' ( (lv_health_8_0= ruleSIGNED_INT ) ) )? (otherlv_9= 'speed' ( (lv_speed_10_0= ruleDOUBLE ) ) )? (otherlv_11= 'threatLevel' ( (lv_threatLevel_12_0= ruleDOUBLE ) ) )? (otherlv_13= 'enabled' ( (lv_enabled_14_0= ruleBOOLEAN ) ) )? (otherlv_15= 'behavior' ( (lv_behavior_16_0= ruleBehaviorTypeEnum ) ) )? ( (lv_characterSpecifics_17_0= ruleCharacterSpecifics ) )? (otherlv_18= 'patrol' ( ( ruleValidID ) ) )? (otherlv_20= 'loot' ( ( ruleValidID ) ) )? otherlv_22= '}' )
            // InternalMazeDsl.g:556:3: otherlv_0= 'opponent' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' otherlv_3= 'type' ( (lv_type_4_0= ruleCharacterTypeEnum ) ) (otherlv_5= 'displayName' ( (lv_displayName_6_0= RULE_STRING ) ) )? (otherlv_7= 'health' ( (lv_health_8_0= ruleSIGNED_INT ) ) )? (otherlv_9= 'speed' ( (lv_speed_10_0= ruleDOUBLE ) ) )? (otherlv_11= 'threatLevel' ( (lv_threatLevel_12_0= ruleDOUBLE ) ) )? (otherlv_13= 'enabled' ( (lv_enabled_14_0= ruleBOOLEAN ) ) )? (otherlv_15= 'behavior' ( (lv_behavior_16_0= ruleBehaviorTypeEnum ) ) )? ( (lv_characterSpecifics_17_0= ruleCharacterSpecifics ) )? (otherlv_18= 'patrol' ( ( ruleValidID ) ) )? (otherlv_20= 'loot' ( ( ruleValidID ) ) )? otherlv_22= '}'
            {
            otherlv_0=(Token)match(input,24,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getOpponentConfigAccess().getOpponentKeyword_0());
            		
            // InternalMazeDsl.g:560:3: ( (lv_name_1_0= ruleValidID ) )
            // InternalMazeDsl.g:561:4: (lv_name_1_0= ruleValidID )
            {
            // InternalMazeDsl.g:561:4: (lv_name_1_0= ruleValidID )
            // InternalMazeDsl.g:562:5: lv_name_1_0= ruleValidID
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

            otherlv_2=(Token)match(input,12,FOLLOW_24); 

            			newLeafNode(otherlv_2, grammarAccess.getOpponentConfigAccess().getLeftCurlyBracketKeyword_2());
            		
            otherlv_3=(Token)match(input,25,FOLLOW_22); 

            			newLeafNode(otherlv_3, grammarAccess.getOpponentConfigAccess().getTypeKeyword_3());
            		
            // InternalMazeDsl.g:587:3: ( (lv_type_4_0= ruleCharacterTypeEnum ) )
            // InternalMazeDsl.g:588:4: (lv_type_4_0= ruleCharacterTypeEnum )
            {
            // InternalMazeDsl.g:588:4: (lv_type_4_0= ruleCharacterTypeEnum )
            // InternalMazeDsl.g:589:5: lv_type_4_0= ruleCharacterTypeEnum
            {

            					newCompositeNode(grammarAccess.getOpponentConfigAccess().getTypeCharacterTypeEnumEnumRuleCall_4_0());
            				
            pushFollow(FOLLOW_25);
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

            // InternalMazeDsl.g:606:3: (otherlv_5= 'displayName' ( (lv_displayName_6_0= RULE_STRING ) ) )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==26) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // InternalMazeDsl.g:607:4: otherlv_5= 'displayName' ( (lv_displayName_6_0= RULE_STRING ) )
                    {
                    otherlv_5=(Token)match(input,26,FOLLOW_12); 

                    				newLeafNode(otherlv_5, grammarAccess.getOpponentConfigAccess().getDisplayNameKeyword_5_0());
                    			
                    // InternalMazeDsl.g:611:4: ( (lv_displayName_6_0= RULE_STRING ) )
                    // InternalMazeDsl.g:612:5: (lv_displayName_6_0= RULE_STRING )
                    {
                    // InternalMazeDsl.g:612:5: (lv_displayName_6_0= RULE_STRING )
                    // InternalMazeDsl.g:613:6: lv_displayName_6_0= RULE_STRING
                    {
                    lv_displayName_6_0=(Token)match(input,RULE_STRING,FOLLOW_26); 

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

            // InternalMazeDsl.g:630:3: (otherlv_7= 'health' ( (lv_health_8_0= ruleSIGNED_INT ) ) )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==27) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // InternalMazeDsl.g:631:4: otherlv_7= 'health' ( (lv_health_8_0= ruleSIGNED_INT ) )
                    {
                    otherlv_7=(Token)match(input,27,FOLLOW_18); 

                    				newLeafNode(otherlv_7, grammarAccess.getOpponentConfigAccess().getHealthKeyword_6_0());
                    			
                    // InternalMazeDsl.g:635:4: ( (lv_health_8_0= ruleSIGNED_INT ) )
                    // InternalMazeDsl.g:636:5: (lv_health_8_0= ruleSIGNED_INT )
                    {
                    // InternalMazeDsl.g:636:5: (lv_health_8_0= ruleSIGNED_INT )
                    // InternalMazeDsl.g:637:6: lv_health_8_0= ruleSIGNED_INT
                    {

                    						newCompositeNode(grammarAccess.getOpponentConfigAccess().getHealthSIGNED_INTParserRuleCall_6_1_0());
                    					
                    pushFollow(FOLLOW_27);
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

            // InternalMazeDsl.g:655:3: (otherlv_9= 'speed' ( (lv_speed_10_0= ruleDOUBLE ) ) )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==28) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // InternalMazeDsl.g:656:4: otherlv_9= 'speed' ( (lv_speed_10_0= ruleDOUBLE ) )
                    {
                    otherlv_9=(Token)match(input,28,FOLLOW_18); 

                    				newLeafNode(otherlv_9, grammarAccess.getOpponentConfigAccess().getSpeedKeyword_7_0());
                    			
                    // InternalMazeDsl.g:660:4: ( (lv_speed_10_0= ruleDOUBLE ) )
                    // InternalMazeDsl.g:661:5: (lv_speed_10_0= ruleDOUBLE )
                    {
                    // InternalMazeDsl.g:661:5: (lv_speed_10_0= ruleDOUBLE )
                    // InternalMazeDsl.g:662:6: lv_speed_10_0= ruleDOUBLE
                    {

                    						newCompositeNode(grammarAccess.getOpponentConfigAccess().getSpeedDOUBLEParserRuleCall_7_1_0());
                    					
                    pushFollow(FOLLOW_28);
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

            // InternalMazeDsl.g:680:3: (otherlv_11= 'threatLevel' ( (lv_threatLevel_12_0= ruleDOUBLE ) ) )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==29) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // InternalMazeDsl.g:681:4: otherlv_11= 'threatLevel' ( (lv_threatLevel_12_0= ruleDOUBLE ) )
                    {
                    otherlv_11=(Token)match(input,29,FOLLOW_18); 

                    				newLeafNode(otherlv_11, grammarAccess.getOpponentConfigAccess().getThreatLevelKeyword_8_0());
                    			
                    // InternalMazeDsl.g:685:4: ( (lv_threatLevel_12_0= ruleDOUBLE ) )
                    // InternalMazeDsl.g:686:5: (lv_threatLevel_12_0= ruleDOUBLE )
                    {
                    // InternalMazeDsl.g:686:5: (lv_threatLevel_12_0= ruleDOUBLE )
                    // InternalMazeDsl.g:687:6: lv_threatLevel_12_0= ruleDOUBLE
                    {

                    						newCompositeNode(grammarAccess.getOpponentConfigAccess().getThreatLevelDOUBLEParserRuleCall_8_1_0());
                    					
                    pushFollow(FOLLOW_29);
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

            // InternalMazeDsl.g:705:3: (otherlv_13= 'enabled' ( (lv_enabled_14_0= ruleBOOLEAN ) ) )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==30) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // InternalMazeDsl.g:706:4: otherlv_13= 'enabled' ( (lv_enabled_14_0= ruleBOOLEAN ) )
                    {
                    otherlv_13=(Token)match(input,30,FOLLOW_30); 

                    				newLeafNode(otherlv_13, grammarAccess.getOpponentConfigAccess().getEnabledKeyword_9_0());
                    			
                    // InternalMazeDsl.g:710:4: ( (lv_enabled_14_0= ruleBOOLEAN ) )
                    // InternalMazeDsl.g:711:5: (lv_enabled_14_0= ruleBOOLEAN )
                    {
                    // InternalMazeDsl.g:711:5: (lv_enabled_14_0= ruleBOOLEAN )
                    // InternalMazeDsl.g:712:6: lv_enabled_14_0= ruleBOOLEAN
                    {

                    						newCompositeNode(grammarAccess.getOpponentConfigAccess().getEnabledBOOLEANParserRuleCall_9_1_0());
                    					
                    pushFollow(FOLLOW_31);
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

            // InternalMazeDsl.g:730:3: (otherlv_15= 'behavior' ( (lv_behavior_16_0= ruleBehaviorTypeEnum ) ) )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==31) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // InternalMazeDsl.g:731:4: otherlv_15= 'behavior' ( (lv_behavior_16_0= ruleBehaviorTypeEnum ) )
                    {
                    otherlv_15=(Token)match(input,31,FOLLOW_32); 

                    				newLeafNode(otherlv_15, grammarAccess.getOpponentConfigAccess().getBehaviorKeyword_10_0());
                    			
                    // InternalMazeDsl.g:735:4: ( (lv_behavior_16_0= ruleBehaviorTypeEnum ) )
                    // InternalMazeDsl.g:736:5: (lv_behavior_16_0= ruleBehaviorTypeEnum )
                    {
                    // InternalMazeDsl.g:736:5: (lv_behavior_16_0= ruleBehaviorTypeEnum )
                    // InternalMazeDsl.g:737:6: lv_behavior_16_0= ruleBehaviorTypeEnum
                    {

                    						newCompositeNode(grammarAccess.getOpponentConfigAccess().getBehaviorBehaviorTypeEnumEnumRuleCall_10_1_0());
                    					
                    pushFollow(FOLLOW_33);
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

            // InternalMazeDsl.g:755:3: ( (lv_characterSpecifics_17_0= ruleCharacterSpecifics ) )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==34||LA20_0==38||LA20_0==41) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // InternalMazeDsl.g:756:4: (lv_characterSpecifics_17_0= ruleCharacterSpecifics )
                    {
                    // InternalMazeDsl.g:756:4: (lv_characterSpecifics_17_0= ruleCharacterSpecifics )
                    // InternalMazeDsl.g:757:5: lv_characterSpecifics_17_0= ruleCharacterSpecifics
                    {

                    					newCompositeNode(grammarAccess.getOpponentConfigAccess().getCharacterSpecificsCharacterSpecificsParserRuleCall_11_0());
                    				
                    pushFollow(FOLLOW_34);
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

            // InternalMazeDsl.g:774:3: (otherlv_18= 'patrol' ( ( ruleValidID ) ) )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==32) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // InternalMazeDsl.g:775:4: otherlv_18= 'patrol' ( ( ruleValidID ) )
                    {
                    otherlv_18=(Token)match(input,32,FOLLOW_3); 

                    				newLeafNode(otherlv_18, grammarAccess.getOpponentConfigAccess().getPatrolKeyword_12_0());
                    			
                    // InternalMazeDsl.g:779:4: ( ( ruleValidID ) )
                    // InternalMazeDsl.g:780:5: ( ruleValidID )
                    {
                    // InternalMazeDsl.g:780:5: ( ruleValidID )
                    // InternalMazeDsl.g:781:6: ruleValidID
                    {

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getOpponentConfigRule());
                    						}
                    					

                    						newCompositeNode(grammarAccess.getOpponentConfigAccess().getPatrolRefPatrolConfigCrossReference_12_1_0());
                    					
                    pushFollow(FOLLOW_35);
                    ruleValidID();

                    state._fsp--;


                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeDsl.g:796:3: (otherlv_20= 'loot' ( ( ruleValidID ) ) )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==33) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // InternalMazeDsl.g:797:4: otherlv_20= 'loot' ( ( ruleValidID ) )
                    {
                    otherlv_20=(Token)match(input,33,FOLLOW_3); 

                    				newLeafNode(otherlv_20, grammarAccess.getOpponentConfigAccess().getLootKeyword_13_0());
                    			
                    // InternalMazeDsl.g:801:4: ( ( ruleValidID ) )
                    // InternalMazeDsl.g:802:5: ( ruleValidID )
                    {
                    // InternalMazeDsl.g:802:5: ( ruleValidID )
                    // InternalMazeDsl.g:803:6: ruleValidID
                    {

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getOpponentConfigRule());
                    						}
                    					

                    						newCompositeNode(grammarAccess.getOpponentConfigAccess().getLootRefLootTableConfigCrossReference_13_1_0());
                    					
                    pushFollow(FOLLOW_11);
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
    // InternalMazeDsl.g:826:1: entryRuleCharacterSpecifics returns [EObject current=null] : iv_ruleCharacterSpecifics= ruleCharacterSpecifics EOF ;
    public final EObject entryRuleCharacterSpecifics() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCharacterSpecifics = null;


        try {
            // InternalMazeDsl.g:826:59: (iv_ruleCharacterSpecifics= ruleCharacterSpecifics EOF )
            // InternalMazeDsl.g:827:2: iv_ruleCharacterSpecifics= ruleCharacterSpecifics EOF
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
    // InternalMazeDsl.g:833:1: ruleCharacterSpecifics returns [EObject current=null] : (this_ZombieSpecifics_0= ruleZombieSpecifics | this_GhostSpecifics_1= ruleGhostSpecifics | this_RangedSpecifics_2= ruleRangedSpecifics ) ;
    public final EObject ruleCharacterSpecifics() throws RecognitionException {
        EObject current = null;

        EObject this_ZombieSpecifics_0 = null;

        EObject this_GhostSpecifics_1 = null;

        EObject this_RangedSpecifics_2 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:839:2: ( (this_ZombieSpecifics_0= ruleZombieSpecifics | this_GhostSpecifics_1= ruleGhostSpecifics | this_RangedSpecifics_2= ruleRangedSpecifics ) )
            // InternalMazeDsl.g:840:2: (this_ZombieSpecifics_0= ruleZombieSpecifics | this_GhostSpecifics_1= ruleGhostSpecifics | this_RangedSpecifics_2= ruleRangedSpecifics )
            {
            // InternalMazeDsl.g:840:2: (this_ZombieSpecifics_0= ruleZombieSpecifics | this_GhostSpecifics_1= ruleGhostSpecifics | this_RangedSpecifics_2= ruleRangedSpecifics )
            int alt23=3;
            switch ( input.LA(1) ) {
            case 34:
                {
                alt23=1;
                }
                break;
            case 38:
                {
                alt23=2;
                }
                break;
            case 41:
                {
                alt23=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 23, 0, input);

                throw nvae;
            }

            switch (alt23) {
                case 1 :
                    // InternalMazeDsl.g:841:3: this_ZombieSpecifics_0= ruleZombieSpecifics
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
                    // InternalMazeDsl.g:850:3: this_GhostSpecifics_1= ruleGhostSpecifics
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
                    // InternalMazeDsl.g:859:3: this_RangedSpecifics_2= ruleRangedSpecifics
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
    // InternalMazeDsl.g:871:1: entryRuleZombieSpecifics returns [EObject current=null] : iv_ruleZombieSpecifics= ruleZombieSpecifics EOF ;
    public final EObject entryRuleZombieSpecifics() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleZombieSpecifics = null;


        try {
            // InternalMazeDsl.g:871:56: (iv_ruleZombieSpecifics= ruleZombieSpecifics EOF )
            // InternalMazeDsl.g:872:2: iv_ruleZombieSpecifics= ruleZombieSpecifics EOF
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
    // InternalMazeDsl.g:878:1: ruleZombieSpecifics returns [EObject current=null] : ( () otherlv_1= 'zombie-stats' otherlv_2= '{' (otherlv_3= 'attackDamage' ( (lv_attackDamage_4_0= RULE_INT ) ) )? (otherlv_5= 'infectionLevel' ( (lv_infectionLevel_6_0= RULE_INT ) ) )? (otherlv_7= 'resurrectionTime' ( (lv_resurrectionTime_8_0= RULE_INT ) ) )? otherlv_9= '}' ) ;
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
            // InternalMazeDsl.g:884:2: ( ( () otherlv_1= 'zombie-stats' otherlv_2= '{' (otherlv_3= 'attackDamage' ( (lv_attackDamage_4_0= RULE_INT ) ) )? (otherlv_5= 'infectionLevel' ( (lv_infectionLevel_6_0= RULE_INT ) ) )? (otherlv_7= 'resurrectionTime' ( (lv_resurrectionTime_8_0= RULE_INT ) ) )? otherlv_9= '}' ) )
            // InternalMazeDsl.g:885:2: ( () otherlv_1= 'zombie-stats' otherlv_2= '{' (otherlv_3= 'attackDamage' ( (lv_attackDamage_4_0= RULE_INT ) ) )? (otherlv_5= 'infectionLevel' ( (lv_infectionLevel_6_0= RULE_INT ) ) )? (otherlv_7= 'resurrectionTime' ( (lv_resurrectionTime_8_0= RULE_INT ) ) )? otherlv_9= '}' )
            {
            // InternalMazeDsl.g:885:2: ( () otherlv_1= 'zombie-stats' otherlv_2= '{' (otherlv_3= 'attackDamage' ( (lv_attackDamage_4_0= RULE_INT ) ) )? (otherlv_5= 'infectionLevel' ( (lv_infectionLevel_6_0= RULE_INT ) ) )? (otherlv_7= 'resurrectionTime' ( (lv_resurrectionTime_8_0= RULE_INT ) ) )? otherlv_9= '}' )
            // InternalMazeDsl.g:886:3: () otherlv_1= 'zombie-stats' otherlv_2= '{' (otherlv_3= 'attackDamage' ( (lv_attackDamage_4_0= RULE_INT ) ) )? (otherlv_5= 'infectionLevel' ( (lv_infectionLevel_6_0= RULE_INT ) ) )? (otherlv_7= 'resurrectionTime' ( (lv_resurrectionTime_8_0= RULE_INT ) ) )? otherlv_9= '}'
            {
            // InternalMazeDsl.g:886:3: ()
            // InternalMazeDsl.g:887:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getZombieSpecificsAccess().getZombieSpecificsAction_0(),
            					current);
            			

            }

            otherlv_1=(Token)match(input,34,FOLLOW_4); 

            			newLeafNode(otherlv_1, grammarAccess.getZombieSpecificsAccess().getZombieStatsKeyword_1());
            		
            otherlv_2=(Token)match(input,12,FOLLOW_36); 

            			newLeafNode(otherlv_2, grammarAccess.getZombieSpecificsAccess().getLeftCurlyBracketKeyword_2());
            		
            // InternalMazeDsl.g:901:3: (otherlv_3= 'attackDamage' ( (lv_attackDamage_4_0= RULE_INT ) ) )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==35) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // InternalMazeDsl.g:902:4: otherlv_3= 'attackDamage' ( (lv_attackDamage_4_0= RULE_INT ) )
                    {
                    otherlv_3=(Token)match(input,35,FOLLOW_37); 

                    				newLeafNode(otherlv_3, grammarAccess.getZombieSpecificsAccess().getAttackDamageKeyword_3_0());
                    			
                    // InternalMazeDsl.g:906:4: ( (lv_attackDamage_4_0= RULE_INT ) )
                    // InternalMazeDsl.g:907:5: (lv_attackDamage_4_0= RULE_INT )
                    {
                    // InternalMazeDsl.g:907:5: (lv_attackDamage_4_0= RULE_INT )
                    // InternalMazeDsl.g:908:6: lv_attackDamage_4_0= RULE_INT
                    {
                    lv_attackDamage_4_0=(Token)match(input,RULE_INT,FOLLOW_38); 

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

            // InternalMazeDsl.g:925:3: (otherlv_5= 'infectionLevel' ( (lv_infectionLevel_6_0= RULE_INT ) ) )?
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==36) ) {
                alt25=1;
            }
            switch (alt25) {
                case 1 :
                    // InternalMazeDsl.g:926:4: otherlv_5= 'infectionLevel' ( (lv_infectionLevel_6_0= RULE_INT ) )
                    {
                    otherlv_5=(Token)match(input,36,FOLLOW_37); 

                    				newLeafNode(otherlv_5, grammarAccess.getZombieSpecificsAccess().getInfectionLevelKeyword_4_0());
                    			
                    // InternalMazeDsl.g:930:4: ( (lv_infectionLevel_6_0= RULE_INT ) )
                    // InternalMazeDsl.g:931:5: (lv_infectionLevel_6_0= RULE_INT )
                    {
                    // InternalMazeDsl.g:931:5: (lv_infectionLevel_6_0= RULE_INT )
                    // InternalMazeDsl.g:932:6: lv_infectionLevel_6_0= RULE_INT
                    {
                    lv_infectionLevel_6_0=(Token)match(input,RULE_INT,FOLLOW_39); 

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

            // InternalMazeDsl.g:949:3: (otherlv_7= 'resurrectionTime' ( (lv_resurrectionTime_8_0= RULE_INT ) ) )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==37) ) {
                alt26=1;
            }
            switch (alt26) {
                case 1 :
                    // InternalMazeDsl.g:950:4: otherlv_7= 'resurrectionTime' ( (lv_resurrectionTime_8_0= RULE_INT ) )
                    {
                    otherlv_7=(Token)match(input,37,FOLLOW_37); 

                    				newLeafNode(otherlv_7, grammarAccess.getZombieSpecificsAccess().getResurrectionTimeKeyword_5_0());
                    			
                    // InternalMazeDsl.g:954:4: ( (lv_resurrectionTime_8_0= RULE_INT ) )
                    // InternalMazeDsl.g:955:5: (lv_resurrectionTime_8_0= RULE_INT )
                    {
                    // InternalMazeDsl.g:955:5: (lv_resurrectionTime_8_0= RULE_INT )
                    // InternalMazeDsl.g:956:6: lv_resurrectionTime_8_0= RULE_INT
                    {
                    lv_resurrectionTime_8_0=(Token)match(input,RULE_INT,FOLLOW_11); 

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
    // InternalMazeDsl.g:981:1: entryRuleGhostSpecifics returns [EObject current=null] : iv_ruleGhostSpecifics= ruleGhostSpecifics EOF ;
    public final EObject entryRuleGhostSpecifics() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGhostSpecifics = null;


        try {
            // InternalMazeDsl.g:981:55: (iv_ruleGhostSpecifics= ruleGhostSpecifics EOF )
            // InternalMazeDsl.g:982:2: iv_ruleGhostSpecifics= ruleGhostSpecifics EOF
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
    // InternalMazeDsl.g:988:1: ruleGhostSpecifics returns [EObject current=null] : ( () otherlv_1= 'ghost-stats' otherlv_2= '{' (otherlv_3= 'attackDamage' ( (lv_attackDamage_4_0= RULE_INT ) ) )? (otherlv_5= 'visibilityLevel' ( (lv_visibilityLevel_6_0= RULE_INT ) ) )? (otherlv_7= 'nonTangibilityEnergy' ( (lv_nonTangibilityEnergy_8_0= ruleDOUBLE ) ) )? otherlv_9= '}' ) ;
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
            // InternalMazeDsl.g:994:2: ( ( () otherlv_1= 'ghost-stats' otherlv_2= '{' (otherlv_3= 'attackDamage' ( (lv_attackDamage_4_0= RULE_INT ) ) )? (otherlv_5= 'visibilityLevel' ( (lv_visibilityLevel_6_0= RULE_INT ) ) )? (otherlv_7= 'nonTangibilityEnergy' ( (lv_nonTangibilityEnergy_8_0= ruleDOUBLE ) ) )? otherlv_9= '}' ) )
            // InternalMazeDsl.g:995:2: ( () otherlv_1= 'ghost-stats' otherlv_2= '{' (otherlv_3= 'attackDamage' ( (lv_attackDamage_4_0= RULE_INT ) ) )? (otherlv_5= 'visibilityLevel' ( (lv_visibilityLevel_6_0= RULE_INT ) ) )? (otherlv_7= 'nonTangibilityEnergy' ( (lv_nonTangibilityEnergy_8_0= ruleDOUBLE ) ) )? otherlv_9= '}' )
            {
            // InternalMazeDsl.g:995:2: ( () otherlv_1= 'ghost-stats' otherlv_2= '{' (otherlv_3= 'attackDamage' ( (lv_attackDamage_4_0= RULE_INT ) ) )? (otherlv_5= 'visibilityLevel' ( (lv_visibilityLevel_6_0= RULE_INT ) ) )? (otherlv_7= 'nonTangibilityEnergy' ( (lv_nonTangibilityEnergy_8_0= ruleDOUBLE ) ) )? otherlv_9= '}' )
            // InternalMazeDsl.g:996:3: () otherlv_1= 'ghost-stats' otherlv_2= '{' (otherlv_3= 'attackDamage' ( (lv_attackDamage_4_0= RULE_INT ) ) )? (otherlv_5= 'visibilityLevel' ( (lv_visibilityLevel_6_0= RULE_INT ) ) )? (otherlv_7= 'nonTangibilityEnergy' ( (lv_nonTangibilityEnergy_8_0= ruleDOUBLE ) ) )? otherlv_9= '}'
            {
            // InternalMazeDsl.g:996:3: ()
            // InternalMazeDsl.g:997:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getGhostSpecificsAccess().getGhostSpecificsAction_0(),
            					current);
            			

            }

            otherlv_1=(Token)match(input,38,FOLLOW_4); 

            			newLeafNode(otherlv_1, grammarAccess.getGhostSpecificsAccess().getGhostStatsKeyword_1());
            		
            otherlv_2=(Token)match(input,12,FOLLOW_40); 

            			newLeafNode(otherlv_2, grammarAccess.getGhostSpecificsAccess().getLeftCurlyBracketKeyword_2());
            		
            // InternalMazeDsl.g:1011:3: (otherlv_3= 'attackDamage' ( (lv_attackDamage_4_0= RULE_INT ) ) )?
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==35) ) {
                alt27=1;
            }
            switch (alt27) {
                case 1 :
                    // InternalMazeDsl.g:1012:4: otherlv_3= 'attackDamage' ( (lv_attackDamage_4_0= RULE_INT ) )
                    {
                    otherlv_3=(Token)match(input,35,FOLLOW_37); 

                    				newLeafNode(otherlv_3, grammarAccess.getGhostSpecificsAccess().getAttackDamageKeyword_3_0());
                    			
                    // InternalMazeDsl.g:1016:4: ( (lv_attackDamage_4_0= RULE_INT ) )
                    // InternalMazeDsl.g:1017:5: (lv_attackDamage_4_0= RULE_INT )
                    {
                    // InternalMazeDsl.g:1017:5: (lv_attackDamage_4_0= RULE_INT )
                    // InternalMazeDsl.g:1018:6: lv_attackDamage_4_0= RULE_INT
                    {
                    lv_attackDamage_4_0=(Token)match(input,RULE_INT,FOLLOW_41); 

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

            // InternalMazeDsl.g:1035:3: (otherlv_5= 'visibilityLevel' ( (lv_visibilityLevel_6_0= RULE_INT ) ) )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==39) ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // InternalMazeDsl.g:1036:4: otherlv_5= 'visibilityLevel' ( (lv_visibilityLevel_6_0= RULE_INT ) )
                    {
                    otherlv_5=(Token)match(input,39,FOLLOW_37); 

                    				newLeafNode(otherlv_5, grammarAccess.getGhostSpecificsAccess().getVisibilityLevelKeyword_4_0());
                    			
                    // InternalMazeDsl.g:1040:4: ( (lv_visibilityLevel_6_0= RULE_INT ) )
                    // InternalMazeDsl.g:1041:5: (lv_visibilityLevel_6_0= RULE_INT )
                    {
                    // InternalMazeDsl.g:1041:5: (lv_visibilityLevel_6_0= RULE_INT )
                    // InternalMazeDsl.g:1042:6: lv_visibilityLevel_6_0= RULE_INT
                    {
                    lv_visibilityLevel_6_0=(Token)match(input,RULE_INT,FOLLOW_42); 

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

            // InternalMazeDsl.g:1059:3: (otherlv_7= 'nonTangibilityEnergy' ( (lv_nonTangibilityEnergy_8_0= ruleDOUBLE ) ) )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==40) ) {
                alt29=1;
            }
            switch (alt29) {
                case 1 :
                    // InternalMazeDsl.g:1060:4: otherlv_7= 'nonTangibilityEnergy' ( (lv_nonTangibilityEnergy_8_0= ruleDOUBLE ) )
                    {
                    otherlv_7=(Token)match(input,40,FOLLOW_18); 

                    				newLeafNode(otherlv_7, grammarAccess.getGhostSpecificsAccess().getNonTangibilityEnergyKeyword_5_0());
                    			
                    // InternalMazeDsl.g:1064:4: ( (lv_nonTangibilityEnergy_8_0= ruleDOUBLE ) )
                    // InternalMazeDsl.g:1065:5: (lv_nonTangibilityEnergy_8_0= ruleDOUBLE )
                    {
                    // InternalMazeDsl.g:1065:5: (lv_nonTangibilityEnergy_8_0= ruleDOUBLE )
                    // InternalMazeDsl.g:1066:6: lv_nonTangibilityEnergy_8_0= ruleDOUBLE
                    {

                    						newCompositeNode(grammarAccess.getGhostSpecificsAccess().getNonTangibilityEnergyDOUBLEParserRuleCall_5_1_0());
                    					
                    pushFollow(FOLLOW_11);
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
    // InternalMazeDsl.g:1092:1: entryRuleRangedSpecifics returns [EObject current=null] : iv_ruleRangedSpecifics= ruleRangedSpecifics EOF ;
    public final EObject entryRuleRangedSpecifics() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRangedSpecifics = null;


        try {
            // InternalMazeDsl.g:1092:56: (iv_ruleRangedSpecifics= ruleRangedSpecifics EOF )
            // InternalMazeDsl.g:1093:2: iv_ruleRangedSpecifics= ruleRangedSpecifics EOF
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
    // InternalMazeDsl.g:1099:1: ruleRangedSpecifics returns [EObject current=null] : ( () otherlv_1= 'ranged-stats' otherlv_2= '{' (otherlv_3= 'attackRange' ( (lv_attackRange_4_0= ruleDOUBLE ) ) )? (otherlv_5= 'attackCooldown' ( (lv_attackCooldown_6_0= RULE_INT ) ) )? (otherlv_7= 'attackDamage' ( (lv_attackDamage_8_0= RULE_INT ) ) )? (otherlv_9= 'projectileSpeed' ( (lv_projectileSpeed_10_0= ruleDOUBLE ) ) )? (otherlv_11= 'projectileType' ( (lv_projectileType_12_0= ruleProjectileTypeEnum ) ) )? (otherlv_13= 'splashRadius' ( (lv_splashRadius_14_0= ruleDOUBLE ) ) )? otherlv_15= '}' ) ;
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
            // InternalMazeDsl.g:1105:2: ( ( () otherlv_1= 'ranged-stats' otherlv_2= '{' (otherlv_3= 'attackRange' ( (lv_attackRange_4_0= ruleDOUBLE ) ) )? (otherlv_5= 'attackCooldown' ( (lv_attackCooldown_6_0= RULE_INT ) ) )? (otherlv_7= 'attackDamage' ( (lv_attackDamage_8_0= RULE_INT ) ) )? (otherlv_9= 'projectileSpeed' ( (lv_projectileSpeed_10_0= ruleDOUBLE ) ) )? (otherlv_11= 'projectileType' ( (lv_projectileType_12_0= ruleProjectileTypeEnum ) ) )? (otherlv_13= 'splashRadius' ( (lv_splashRadius_14_0= ruleDOUBLE ) ) )? otherlv_15= '}' ) )
            // InternalMazeDsl.g:1106:2: ( () otherlv_1= 'ranged-stats' otherlv_2= '{' (otherlv_3= 'attackRange' ( (lv_attackRange_4_0= ruleDOUBLE ) ) )? (otherlv_5= 'attackCooldown' ( (lv_attackCooldown_6_0= RULE_INT ) ) )? (otherlv_7= 'attackDamage' ( (lv_attackDamage_8_0= RULE_INT ) ) )? (otherlv_9= 'projectileSpeed' ( (lv_projectileSpeed_10_0= ruleDOUBLE ) ) )? (otherlv_11= 'projectileType' ( (lv_projectileType_12_0= ruleProjectileTypeEnum ) ) )? (otherlv_13= 'splashRadius' ( (lv_splashRadius_14_0= ruleDOUBLE ) ) )? otherlv_15= '}' )
            {
            // InternalMazeDsl.g:1106:2: ( () otherlv_1= 'ranged-stats' otherlv_2= '{' (otherlv_3= 'attackRange' ( (lv_attackRange_4_0= ruleDOUBLE ) ) )? (otherlv_5= 'attackCooldown' ( (lv_attackCooldown_6_0= RULE_INT ) ) )? (otherlv_7= 'attackDamage' ( (lv_attackDamage_8_0= RULE_INT ) ) )? (otherlv_9= 'projectileSpeed' ( (lv_projectileSpeed_10_0= ruleDOUBLE ) ) )? (otherlv_11= 'projectileType' ( (lv_projectileType_12_0= ruleProjectileTypeEnum ) ) )? (otherlv_13= 'splashRadius' ( (lv_splashRadius_14_0= ruleDOUBLE ) ) )? otherlv_15= '}' )
            // InternalMazeDsl.g:1107:3: () otherlv_1= 'ranged-stats' otherlv_2= '{' (otherlv_3= 'attackRange' ( (lv_attackRange_4_0= ruleDOUBLE ) ) )? (otherlv_5= 'attackCooldown' ( (lv_attackCooldown_6_0= RULE_INT ) ) )? (otherlv_7= 'attackDamage' ( (lv_attackDamage_8_0= RULE_INT ) ) )? (otherlv_9= 'projectileSpeed' ( (lv_projectileSpeed_10_0= ruleDOUBLE ) ) )? (otherlv_11= 'projectileType' ( (lv_projectileType_12_0= ruleProjectileTypeEnum ) ) )? (otherlv_13= 'splashRadius' ( (lv_splashRadius_14_0= ruleDOUBLE ) ) )? otherlv_15= '}'
            {
            // InternalMazeDsl.g:1107:3: ()
            // InternalMazeDsl.g:1108:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getRangedSpecificsAccess().getRangedSpecificsAction_0(),
            					current);
            			

            }

            otherlv_1=(Token)match(input,41,FOLLOW_4); 

            			newLeafNode(otherlv_1, grammarAccess.getRangedSpecificsAccess().getRangedStatsKeyword_1());
            		
            otherlv_2=(Token)match(input,12,FOLLOW_43); 

            			newLeafNode(otherlv_2, grammarAccess.getRangedSpecificsAccess().getLeftCurlyBracketKeyword_2());
            		
            // InternalMazeDsl.g:1122:3: (otherlv_3= 'attackRange' ( (lv_attackRange_4_0= ruleDOUBLE ) ) )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==42) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // InternalMazeDsl.g:1123:4: otherlv_3= 'attackRange' ( (lv_attackRange_4_0= ruleDOUBLE ) )
                    {
                    otherlv_3=(Token)match(input,42,FOLLOW_18); 

                    				newLeafNode(otherlv_3, grammarAccess.getRangedSpecificsAccess().getAttackRangeKeyword_3_0());
                    			
                    // InternalMazeDsl.g:1127:4: ( (lv_attackRange_4_0= ruleDOUBLE ) )
                    // InternalMazeDsl.g:1128:5: (lv_attackRange_4_0= ruleDOUBLE )
                    {
                    // InternalMazeDsl.g:1128:5: (lv_attackRange_4_0= ruleDOUBLE )
                    // InternalMazeDsl.g:1129:6: lv_attackRange_4_0= ruleDOUBLE
                    {

                    						newCompositeNode(grammarAccess.getRangedSpecificsAccess().getAttackRangeDOUBLEParserRuleCall_3_1_0());
                    					
                    pushFollow(FOLLOW_44);
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

            // InternalMazeDsl.g:1147:3: (otherlv_5= 'attackCooldown' ( (lv_attackCooldown_6_0= RULE_INT ) ) )?
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==43) ) {
                alt31=1;
            }
            switch (alt31) {
                case 1 :
                    // InternalMazeDsl.g:1148:4: otherlv_5= 'attackCooldown' ( (lv_attackCooldown_6_0= RULE_INT ) )
                    {
                    otherlv_5=(Token)match(input,43,FOLLOW_37); 

                    				newLeafNode(otherlv_5, grammarAccess.getRangedSpecificsAccess().getAttackCooldownKeyword_4_0());
                    			
                    // InternalMazeDsl.g:1152:4: ( (lv_attackCooldown_6_0= RULE_INT ) )
                    // InternalMazeDsl.g:1153:5: (lv_attackCooldown_6_0= RULE_INT )
                    {
                    // InternalMazeDsl.g:1153:5: (lv_attackCooldown_6_0= RULE_INT )
                    // InternalMazeDsl.g:1154:6: lv_attackCooldown_6_0= RULE_INT
                    {
                    lv_attackCooldown_6_0=(Token)match(input,RULE_INT,FOLLOW_45); 

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

            // InternalMazeDsl.g:1171:3: (otherlv_7= 'attackDamage' ( (lv_attackDamage_8_0= RULE_INT ) ) )?
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==35) ) {
                alt32=1;
            }
            switch (alt32) {
                case 1 :
                    // InternalMazeDsl.g:1172:4: otherlv_7= 'attackDamage' ( (lv_attackDamage_8_0= RULE_INT ) )
                    {
                    otherlv_7=(Token)match(input,35,FOLLOW_37); 

                    				newLeafNode(otherlv_7, grammarAccess.getRangedSpecificsAccess().getAttackDamageKeyword_5_0());
                    			
                    // InternalMazeDsl.g:1176:4: ( (lv_attackDamage_8_0= RULE_INT ) )
                    // InternalMazeDsl.g:1177:5: (lv_attackDamage_8_0= RULE_INT )
                    {
                    // InternalMazeDsl.g:1177:5: (lv_attackDamage_8_0= RULE_INT )
                    // InternalMazeDsl.g:1178:6: lv_attackDamage_8_0= RULE_INT
                    {
                    lv_attackDamage_8_0=(Token)match(input,RULE_INT,FOLLOW_46); 

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

            // InternalMazeDsl.g:1195:3: (otherlv_9= 'projectileSpeed' ( (lv_projectileSpeed_10_0= ruleDOUBLE ) ) )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==44) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // InternalMazeDsl.g:1196:4: otherlv_9= 'projectileSpeed' ( (lv_projectileSpeed_10_0= ruleDOUBLE ) )
                    {
                    otherlv_9=(Token)match(input,44,FOLLOW_18); 

                    				newLeafNode(otherlv_9, grammarAccess.getRangedSpecificsAccess().getProjectileSpeedKeyword_6_0());
                    			
                    // InternalMazeDsl.g:1200:4: ( (lv_projectileSpeed_10_0= ruleDOUBLE ) )
                    // InternalMazeDsl.g:1201:5: (lv_projectileSpeed_10_0= ruleDOUBLE )
                    {
                    // InternalMazeDsl.g:1201:5: (lv_projectileSpeed_10_0= ruleDOUBLE )
                    // InternalMazeDsl.g:1202:6: lv_projectileSpeed_10_0= ruleDOUBLE
                    {

                    						newCompositeNode(grammarAccess.getRangedSpecificsAccess().getProjectileSpeedDOUBLEParserRuleCall_6_1_0());
                    					
                    pushFollow(FOLLOW_47);
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

            // InternalMazeDsl.g:1220:3: (otherlv_11= 'projectileType' ( (lv_projectileType_12_0= ruleProjectileTypeEnum ) ) )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==45) ) {
                alt34=1;
            }
            switch (alt34) {
                case 1 :
                    // InternalMazeDsl.g:1221:4: otherlv_11= 'projectileType' ( (lv_projectileType_12_0= ruleProjectileTypeEnum ) )
                    {
                    otherlv_11=(Token)match(input,45,FOLLOW_48); 

                    				newLeafNode(otherlv_11, grammarAccess.getRangedSpecificsAccess().getProjectileTypeKeyword_7_0());
                    			
                    // InternalMazeDsl.g:1225:4: ( (lv_projectileType_12_0= ruleProjectileTypeEnum ) )
                    // InternalMazeDsl.g:1226:5: (lv_projectileType_12_0= ruleProjectileTypeEnum )
                    {
                    // InternalMazeDsl.g:1226:5: (lv_projectileType_12_0= ruleProjectileTypeEnum )
                    // InternalMazeDsl.g:1227:6: lv_projectileType_12_0= ruleProjectileTypeEnum
                    {

                    						newCompositeNode(grammarAccess.getRangedSpecificsAccess().getProjectileTypeProjectileTypeEnumEnumRuleCall_7_1_0());
                    					
                    pushFollow(FOLLOW_49);
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

            // InternalMazeDsl.g:1245:3: (otherlv_13= 'splashRadius' ( (lv_splashRadius_14_0= ruleDOUBLE ) ) )?
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==46) ) {
                alt35=1;
            }
            switch (alt35) {
                case 1 :
                    // InternalMazeDsl.g:1246:4: otherlv_13= 'splashRadius' ( (lv_splashRadius_14_0= ruleDOUBLE ) )
                    {
                    otherlv_13=(Token)match(input,46,FOLLOW_18); 

                    				newLeafNode(otherlv_13, grammarAccess.getRangedSpecificsAccess().getSplashRadiusKeyword_8_0());
                    			
                    // InternalMazeDsl.g:1250:4: ( (lv_splashRadius_14_0= ruleDOUBLE ) )
                    // InternalMazeDsl.g:1251:5: (lv_splashRadius_14_0= ruleDOUBLE )
                    {
                    // InternalMazeDsl.g:1251:5: (lv_splashRadius_14_0= ruleDOUBLE )
                    // InternalMazeDsl.g:1252:6: lv_splashRadius_14_0= ruleDOUBLE
                    {

                    						newCompositeNode(grammarAccess.getRangedSpecificsAccess().getSplashRadiusDOUBLEParserRuleCall_8_1_0());
                    					
                    pushFollow(FOLLOW_11);
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
    // InternalMazeDsl.g:1278:1: entryRulePatrolConfig returns [EObject current=null] : iv_rulePatrolConfig= rulePatrolConfig EOF ;
    public final EObject entryRulePatrolConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePatrolConfig = null;


        try {
            // InternalMazeDsl.g:1278:53: (iv_rulePatrolConfig= rulePatrolConfig EOF )
            // InternalMazeDsl.g:1279:2: iv_rulePatrolConfig= rulePatrolConfig EOF
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
    // InternalMazeDsl.g:1285:1: rulePatrolConfig returns [EObject current=null] : (otherlv_0= 'patrol' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' (otherlv_3= 'visionRange' ( (lv_visionRange_4_0= ruleDOUBLE ) ) )? ( (lv_zone_5_0= rulePatrolZoneConfig ) )? otherlv_6= 'path' otherlv_7= '[' ( (lv_waypoints_8_0= ruleWaypoint ) ) (otherlv_9= ',' ( (lv_waypoints_10_0= ruleWaypoint ) ) )* otherlv_11= ']' otherlv_12= '}' ) ;
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
            // InternalMazeDsl.g:1291:2: ( (otherlv_0= 'patrol' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' (otherlv_3= 'visionRange' ( (lv_visionRange_4_0= ruleDOUBLE ) ) )? ( (lv_zone_5_0= rulePatrolZoneConfig ) )? otherlv_6= 'path' otherlv_7= '[' ( (lv_waypoints_8_0= ruleWaypoint ) ) (otherlv_9= ',' ( (lv_waypoints_10_0= ruleWaypoint ) ) )* otherlv_11= ']' otherlv_12= '}' ) )
            // InternalMazeDsl.g:1292:2: (otherlv_0= 'patrol' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' (otherlv_3= 'visionRange' ( (lv_visionRange_4_0= ruleDOUBLE ) ) )? ( (lv_zone_5_0= rulePatrolZoneConfig ) )? otherlv_6= 'path' otherlv_7= '[' ( (lv_waypoints_8_0= ruleWaypoint ) ) (otherlv_9= ',' ( (lv_waypoints_10_0= ruleWaypoint ) ) )* otherlv_11= ']' otherlv_12= '}' )
            {
            // InternalMazeDsl.g:1292:2: (otherlv_0= 'patrol' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' (otherlv_3= 'visionRange' ( (lv_visionRange_4_0= ruleDOUBLE ) ) )? ( (lv_zone_5_0= rulePatrolZoneConfig ) )? otherlv_6= 'path' otherlv_7= '[' ( (lv_waypoints_8_0= ruleWaypoint ) ) (otherlv_9= ',' ( (lv_waypoints_10_0= ruleWaypoint ) ) )* otherlv_11= ']' otherlv_12= '}' )
            // InternalMazeDsl.g:1293:3: otherlv_0= 'patrol' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' (otherlv_3= 'visionRange' ( (lv_visionRange_4_0= ruleDOUBLE ) ) )? ( (lv_zone_5_0= rulePatrolZoneConfig ) )? otherlv_6= 'path' otherlv_7= '[' ( (lv_waypoints_8_0= ruleWaypoint ) ) (otherlv_9= ',' ( (lv_waypoints_10_0= ruleWaypoint ) ) )* otherlv_11= ']' otherlv_12= '}'
            {
            otherlv_0=(Token)match(input,32,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getPatrolConfigAccess().getPatrolKeyword_0());
            		
            // InternalMazeDsl.g:1297:3: ( (lv_name_1_0= ruleValidID ) )
            // InternalMazeDsl.g:1298:4: (lv_name_1_0= ruleValidID )
            {
            // InternalMazeDsl.g:1298:4: (lv_name_1_0= ruleValidID )
            // InternalMazeDsl.g:1299:5: lv_name_1_0= ruleValidID
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

            otherlv_2=(Token)match(input,12,FOLLOW_50); 

            			newLeafNode(otherlv_2, grammarAccess.getPatrolConfigAccess().getLeftCurlyBracketKeyword_2());
            		
            // InternalMazeDsl.g:1320:3: (otherlv_3= 'visionRange' ( (lv_visionRange_4_0= ruleDOUBLE ) ) )?
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==47) ) {
                alt36=1;
            }
            switch (alt36) {
                case 1 :
                    // InternalMazeDsl.g:1321:4: otherlv_3= 'visionRange' ( (lv_visionRange_4_0= ruleDOUBLE ) )
                    {
                    otherlv_3=(Token)match(input,47,FOLLOW_18); 

                    				newLeafNode(otherlv_3, grammarAccess.getPatrolConfigAccess().getVisionRangeKeyword_3_0());
                    			
                    // InternalMazeDsl.g:1325:4: ( (lv_visionRange_4_0= ruleDOUBLE ) )
                    // InternalMazeDsl.g:1326:5: (lv_visionRange_4_0= ruleDOUBLE )
                    {
                    // InternalMazeDsl.g:1326:5: (lv_visionRange_4_0= ruleDOUBLE )
                    // InternalMazeDsl.g:1327:6: lv_visionRange_4_0= ruleDOUBLE
                    {

                    						newCompositeNode(grammarAccess.getPatrolConfigAccess().getVisionRangeDOUBLEParserRuleCall_3_1_0());
                    					
                    pushFollow(FOLLOW_51);
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

            // InternalMazeDsl.g:1345:3: ( (lv_zone_5_0= rulePatrolZoneConfig ) )?
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( (LA37_0==52) ) {
                alt37=1;
            }
            switch (alt37) {
                case 1 :
                    // InternalMazeDsl.g:1346:4: (lv_zone_5_0= rulePatrolZoneConfig )
                    {
                    // InternalMazeDsl.g:1346:4: (lv_zone_5_0= rulePatrolZoneConfig )
                    // InternalMazeDsl.g:1347:5: lv_zone_5_0= rulePatrolZoneConfig
                    {

                    					newCompositeNode(grammarAccess.getPatrolConfigAccess().getZonePatrolZoneConfigParserRuleCall_4_0());
                    				
                    pushFollow(FOLLOW_52);
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

            otherlv_6=(Token)match(input,48,FOLLOW_53); 

            			newLeafNode(otherlv_6, grammarAccess.getPatrolConfigAccess().getPathKeyword_5());
            		
            otherlv_7=(Token)match(input,49,FOLLOW_54); 

            			newLeafNode(otherlv_7, grammarAccess.getPatrolConfigAccess().getLeftSquareBracketKeyword_6());
            		
            // InternalMazeDsl.g:1372:3: ( (lv_waypoints_8_0= ruleWaypoint ) )
            // InternalMazeDsl.g:1373:4: (lv_waypoints_8_0= ruleWaypoint )
            {
            // InternalMazeDsl.g:1373:4: (lv_waypoints_8_0= ruleWaypoint )
            // InternalMazeDsl.g:1374:5: lv_waypoints_8_0= ruleWaypoint
            {

            					newCompositeNode(grammarAccess.getPatrolConfigAccess().getWaypointsWaypointParserRuleCall_7_0());
            				
            pushFollow(FOLLOW_55);
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

            // InternalMazeDsl.g:1391:3: (otherlv_9= ',' ( (lv_waypoints_10_0= ruleWaypoint ) ) )*
            loop38:
            do {
                int alt38=2;
                int LA38_0 = input.LA(1);

                if ( (LA38_0==50) ) {
                    alt38=1;
                }


                switch (alt38) {
            	case 1 :
            	    // InternalMazeDsl.g:1392:4: otherlv_9= ',' ( (lv_waypoints_10_0= ruleWaypoint ) )
            	    {
            	    otherlv_9=(Token)match(input,50,FOLLOW_54); 

            	    				newLeafNode(otherlv_9, grammarAccess.getPatrolConfigAccess().getCommaKeyword_8_0());
            	    			
            	    // InternalMazeDsl.g:1396:4: ( (lv_waypoints_10_0= ruleWaypoint ) )
            	    // InternalMazeDsl.g:1397:5: (lv_waypoints_10_0= ruleWaypoint )
            	    {
            	    // InternalMazeDsl.g:1397:5: (lv_waypoints_10_0= ruleWaypoint )
            	    // InternalMazeDsl.g:1398:6: lv_waypoints_10_0= ruleWaypoint
            	    {

            	    						newCompositeNode(grammarAccess.getPatrolConfigAccess().getWaypointsWaypointParserRuleCall_8_1_0());
            	    					
            	    pushFollow(FOLLOW_55);
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
            	    break loop38;
                }
            } while (true);

            otherlv_11=(Token)match(input,51,FOLLOW_11); 

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
    // InternalMazeDsl.g:1428:1: entryRulePatrolZoneConfig returns [EObject current=null] : iv_rulePatrolZoneConfig= rulePatrolZoneConfig EOF ;
    public final EObject entryRulePatrolZoneConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePatrolZoneConfig = null;


        try {
            // InternalMazeDsl.g:1428:57: (iv_rulePatrolZoneConfig= rulePatrolZoneConfig EOF )
            // InternalMazeDsl.g:1429:2: iv_rulePatrolZoneConfig= rulePatrolZoneConfig EOF
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
    // InternalMazeDsl.g:1435:1: rulePatrolZoneConfig returns [EObject current=null] : (otherlv_0= 'zone' otherlv_1= '{' otherlv_2= 'topLeft' otherlv_3= '(' ( (lv_topLeftX_4_0= ruleDOUBLE ) ) otherlv_5= ',' ( (lv_topLeftY_6_0= ruleDOUBLE ) ) otherlv_7= ')' otherlv_8= 'width' ( (lv_width_9_0= ruleDOUBLE ) ) otherlv_10= 'height' ( (lv_height_11_0= ruleDOUBLE ) ) otherlv_12= '}' ) ;
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
            // InternalMazeDsl.g:1441:2: ( (otherlv_0= 'zone' otherlv_1= '{' otherlv_2= 'topLeft' otherlv_3= '(' ( (lv_topLeftX_4_0= ruleDOUBLE ) ) otherlv_5= ',' ( (lv_topLeftY_6_0= ruleDOUBLE ) ) otherlv_7= ')' otherlv_8= 'width' ( (lv_width_9_0= ruleDOUBLE ) ) otherlv_10= 'height' ( (lv_height_11_0= ruleDOUBLE ) ) otherlv_12= '}' ) )
            // InternalMazeDsl.g:1442:2: (otherlv_0= 'zone' otherlv_1= '{' otherlv_2= 'topLeft' otherlv_3= '(' ( (lv_topLeftX_4_0= ruleDOUBLE ) ) otherlv_5= ',' ( (lv_topLeftY_6_0= ruleDOUBLE ) ) otherlv_7= ')' otherlv_8= 'width' ( (lv_width_9_0= ruleDOUBLE ) ) otherlv_10= 'height' ( (lv_height_11_0= ruleDOUBLE ) ) otherlv_12= '}' )
            {
            // InternalMazeDsl.g:1442:2: (otherlv_0= 'zone' otherlv_1= '{' otherlv_2= 'topLeft' otherlv_3= '(' ( (lv_topLeftX_4_0= ruleDOUBLE ) ) otherlv_5= ',' ( (lv_topLeftY_6_0= ruleDOUBLE ) ) otherlv_7= ')' otherlv_8= 'width' ( (lv_width_9_0= ruleDOUBLE ) ) otherlv_10= 'height' ( (lv_height_11_0= ruleDOUBLE ) ) otherlv_12= '}' )
            // InternalMazeDsl.g:1443:3: otherlv_0= 'zone' otherlv_1= '{' otherlv_2= 'topLeft' otherlv_3= '(' ( (lv_topLeftX_4_0= ruleDOUBLE ) ) otherlv_5= ',' ( (lv_topLeftY_6_0= ruleDOUBLE ) ) otherlv_7= ')' otherlv_8= 'width' ( (lv_width_9_0= ruleDOUBLE ) ) otherlv_10= 'height' ( (lv_height_11_0= ruleDOUBLE ) ) otherlv_12= '}'
            {
            otherlv_0=(Token)match(input,52,FOLLOW_4); 

            			newLeafNode(otherlv_0, grammarAccess.getPatrolZoneConfigAccess().getZoneKeyword_0());
            		
            otherlv_1=(Token)match(input,12,FOLLOW_56); 

            			newLeafNode(otherlv_1, grammarAccess.getPatrolZoneConfigAccess().getLeftCurlyBracketKeyword_1());
            		
            otherlv_2=(Token)match(input,53,FOLLOW_54); 

            			newLeafNode(otherlv_2, grammarAccess.getPatrolZoneConfigAccess().getTopLeftKeyword_2());
            		
            otherlv_3=(Token)match(input,54,FOLLOW_18); 

            			newLeafNode(otherlv_3, grammarAccess.getPatrolZoneConfigAccess().getLeftParenthesisKeyword_3());
            		
            // InternalMazeDsl.g:1459:3: ( (lv_topLeftX_4_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:1460:4: (lv_topLeftX_4_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:1460:4: (lv_topLeftX_4_0= ruleDOUBLE )
            // InternalMazeDsl.g:1461:5: lv_topLeftX_4_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getPatrolZoneConfigAccess().getTopLeftXDOUBLEParserRuleCall_4_0());
            				
            pushFollow(FOLLOW_57);
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

            otherlv_5=(Token)match(input,50,FOLLOW_18); 

            			newLeafNode(otherlv_5, grammarAccess.getPatrolZoneConfigAccess().getCommaKeyword_5());
            		
            // InternalMazeDsl.g:1482:3: ( (lv_topLeftY_6_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:1483:4: (lv_topLeftY_6_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:1483:4: (lv_topLeftY_6_0= ruleDOUBLE )
            // InternalMazeDsl.g:1484:5: lv_topLeftY_6_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getPatrolZoneConfigAccess().getTopLeftYDOUBLEParserRuleCall_6_0());
            				
            pushFollow(FOLLOW_58);
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

            otherlv_7=(Token)match(input,55,FOLLOW_59); 

            			newLeafNode(otherlv_7, grammarAccess.getPatrolZoneConfigAccess().getRightParenthesisKeyword_7());
            		
            otherlv_8=(Token)match(input,56,FOLLOW_18); 

            			newLeafNode(otherlv_8, grammarAccess.getPatrolZoneConfigAccess().getWidthKeyword_8());
            		
            // InternalMazeDsl.g:1509:3: ( (lv_width_9_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:1510:4: (lv_width_9_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:1510:4: (lv_width_9_0= ruleDOUBLE )
            // InternalMazeDsl.g:1511:5: lv_width_9_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getPatrolZoneConfigAccess().getWidthDOUBLEParserRuleCall_9_0());
            				
            pushFollow(FOLLOW_60);
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

            otherlv_10=(Token)match(input,57,FOLLOW_18); 

            			newLeafNode(otherlv_10, grammarAccess.getPatrolZoneConfigAccess().getHeightKeyword_10());
            		
            // InternalMazeDsl.g:1532:3: ( (lv_height_11_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:1533:4: (lv_height_11_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:1533:4: (lv_height_11_0= ruleDOUBLE )
            // InternalMazeDsl.g:1534:5: lv_height_11_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getPatrolZoneConfigAccess().getHeightDOUBLEParserRuleCall_11_0());
            				
            pushFollow(FOLLOW_11);
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
    // InternalMazeDsl.g:1559:1: entryRuleWaypoint returns [EObject current=null] : iv_ruleWaypoint= ruleWaypoint EOF ;
    public final EObject entryRuleWaypoint() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleWaypoint = null;


        try {
            // InternalMazeDsl.g:1559:49: (iv_ruleWaypoint= ruleWaypoint EOF )
            // InternalMazeDsl.g:1560:2: iv_ruleWaypoint= ruleWaypoint EOF
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
    // InternalMazeDsl.g:1566:1: ruleWaypoint returns [EObject current=null] : (otherlv_0= '(' ( (lv_x_1_0= ruleDOUBLE ) ) otherlv_2= ',' ( (lv_y_3_0= ruleDOUBLE ) ) otherlv_4= ')' (otherlv_5= ':' ( (lv_waitTime_6_0= RULE_INT ) ) otherlv_7= 'ms' )? ) ;
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
            // InternalMazeDsl.g:1572:2: ( (otherlv_0= '(' ( (lv_x_1_0= ruleDOUBLE ) ) otherlv_2= ',' ( (lv_y_3_0= ruleDOUBLE ) ) otherlv_4= ')' (otherlv_5= ':' ( (lv_waitTime_6_0= RULE_INT ) ) otherlv_7= 'ms' )? ) )
            // InternalMazeDsl.g:1573:2: (otherlv_0= '(' ( (lv_x_1_0= ruleDOUBLE ) ) otherlv_2= ',' ( (lv_y_3_0= ruleDOUBLE ) ) otherlv_4= ')' (otherlv_5= ':' ( (lv_waitTime_6_0= RULE_INT ) ) otherlv_7= 'ms' )? )
            {
            // InternalMazeDsl.g:1573:2: (otherlv_0= '(' ( (lv_x_1_0= ruleDOUBLE ) ) otherlv_2= ',' ( (lv_y_3_0= ruleDOUBLE ) ) otherlv_4= ')' (otherlv_5= ':' ( (lv_waitTime_6_0= RULE_INT ) ) otherlv_7= 'ms' )? )
            // InternalMazeDsl.g:1574:3: otherlv_0= '(' ( (lv_x_1_0= ruleDOUBLE ) ) otherlv_2= ',' ( (lv_y_3_0= ruleDOUBLE ) ) otherlv_4= ')' (otherlv_5= ':' ( (lv_waitTime_6_0= RULE_INT ) ) otherlv_7= 'ms' )?
            {
            otherlv_0=(Token)match(input,54,FOLLOW_18); 

            			newLeafNode(otherlv_0, grammarAccess.getWaypointAccess().getLeftParenthesisKeyword_0());
            		
            // InternalMazeDsl.g:1578:3: ( (lv_x_1_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:1579:4: (lv_x_1_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:1579:4: (lv_x_1_0= ruleDOUBLE )
            // InternalMazeDsl.g:1580:5: lv_x_1_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getWaypointAccess().getXDOUBLEParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_57);
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

            otherlv_2=(Token)match(input,50,FOLLOW_18); 

            			newLeafNode(otherlv_2, grammarAccess.getWaypointAccess().getCommaKeyword_2());
            		
            // InternalMazeDsl.g:1601:3: ( (lv_y_3_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:1602:4: (lv_y_3_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:1602:4: (lv_y_3_0= ruleDOUBLE )
            // InternalMazeDsl.g:1603:5: lv_y_3_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getWaypointAccess().getYDOUBLEParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_58);
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

            otherlv_4=(Token)match(input,55,FOLLOW_61); 

            			newLeafNode(otherlv_4, grammarAccess.getWaypointAccess().getRightParenthesisKeyword_4());
            		
            // InternalMazeDsl.g:1624:3: (otherlv_5= ':' ( (lv_waitTime_6_0= RULE_INT ) ) otherlv_7= 'ms' )?
            int alt39=2;
            int LA39_0 = input.LA(1);

            if ( (LA39_0==58) ) {
                alt39=1;
            }
            switch (alt39) {
                case 1 :
                    // InternalMazeDsl.g:1625:4: otherlv_5= ':' ( (lv_waitTime_6_0= RULE_INT ) ) otherlv_7= 'ms'
                    {
                    otherlv_5=(Token)match(input,58,FOLLOW_37); 

                    				newLeafNode(otherlv_5, grammarAccess.getWaypointAccess().getColonKeyword_5_0());
                    			
                    // InternalMazeDsl.g:1629:4: ( (lv_waitTime_6_0= RULE_INT ) )
                    // InternalMazeDsl.g:1630:5: (lv_waitTime_6_0= RULE_INT )
                    {
                    // InternalMazeDsl.g:1630:5: (lv_waitTime_6_0= RULE_INT )
                    // InternalMazeDsl.g:1631:6: lv_waitTime_6_0= RULE_INT
                    {
                    lv_waitTime_6_0=(Token)match(input,RULE_INT,FOLLOW_62); 

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
    // InternalMazeDsl.g:1656:1: entryRuleLootTableConfig returns [EObject current=null] : iv_ruleLootTableConfig= ruleLootTableConfig EOF ;
    public final EObject entryRuleLootTableConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLootTableConfig = null;


        try {
            // InternalMazeDsl.g:1656:56: (iv_ruleLootTableConfig= ruleLootTableConfig EOF )
            // InternalMazeDsl.g:1657:2: iv_ruleLootTableConfig= ruleLootTableConfig EOF
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
    // InternalMazeDsl.g:1663:1: ruleLootTableConfig returns [EObject current=null] : (otherlv_0= 'loot-table' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' (otherlv_3= 'capacity' ( (lv_capacity_4_0= RULE_INT ) ) )? ( (lv_items_5_0= ruleLootItemConfig ) )+ otherlv_6= '}' ) ;
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
            // InternalMazeDsl.g:1669:2: ( (otherlv_0= 'loot-table' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' (otherlv_3= 'capacity' ( (lv_capacity_4_0= RULE_INT ) ) )? ( (lv_items_5_0= ruleLootItemConfig ) )+ otherlv_6= '}' ) )
            // InternalMazeDsl.g:1670:2: (otherlv_0= 'loot-table' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' (otherlv_3= 'capacity' ( (lv_capacity_4_0= RULE_INT ) ) )? ( (lv_items_5_0= ruleLootItemConfig ) )+ otherlv_6= '}' )
            {
            // InternalMazeDsl.g:1670:2: (otherlv_0= 'loot-table' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' (otherlv_3= 'capacity' ( (lv_capacity_4_0= RULE_INT ) ) )? ( (lv_items_5_0= ruleLootItemConfig ) )+ otherlv_6= '}' )
            // InternalMazeDsl.g:1671:3: otherlv_0= 'loot-table' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' (otherlv_3= 'capacity' ( (lv_capacity_4_0= RULE_INT ) ) )? ( (lv_items_5_0= ruleLootItemConfig ) )+ otherlv_6= '}'
            {
            otherlv_0=(Token)match(input,60,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getLootTableConfigAccess().getLootTableKeyword_0());
            		
            // InternalMazeDsl.g:1675:3: ( (lv_name_1_0= ruleValidID ) )
            // InternalMazeDsl.g:1676:4: (lv_name_1_0= ruleValidID )
            {
            // InternalMazeDsl.g:1676:4: (lv_name_1_0= ruleValidID )
            // InternalMazeDsl.g:1677:5: lv_name_1_0= ruleValidID
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

            otherlv_2=(Token)match(input,12,FOLLOW_63); 

            			newLeafNode(otherlv_2, grammarAccess.getLootTableConfigAccess().getLeftCurlyBracketKeyword_2());
            		
            // InternalMazeDsl.g:1698:3: (otherlv_3= 'capacity' ( (lv_capacity_4_0= RULE_INT ) ) )?
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( (LA40_0==61) ) {
                alt40=1;
            }
            switch (alt40) {
                case 1 :
                    // InternalMazeDsl.g:1699:4: otherlv_3= 'capacity' ( (lv_capacity_4_0= RULE_INT ) )
                    {
                    otherlv_3=(Token)match(input,61,FOLLOW_37); 

                    				newLeafNode(otherlv_3, grammarAccess.getLootTableConfigAccess().getCapacityKeyword_3_0());
                    			
                    // InternalMazeDsl.g:1703:4: ( (lv_capacity_4_0= RULE_INT ) )
                    // InternalMazeDsl.g:1704:5: (lv_capacity_4_0= RULE_INT )
                    {
                    // InternalMazeDsl.g:1704:5: (lv_capacity_4_0= RULE_INT )
                    // InternalMazeDsl.g:1705:6: lv_capacity_4_0= RULE_INT
                    {
                    lv_capacity_4_0=(Token)match(input,RULE_INT,FOLLOW_63); 

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

            // InternalMazeDsl.g:1722:3: ( (lv_items_5_0= ruleLootItemConfig ) )+
            int cnt41=0;
            loop41:
            do {
                int alt41=2;
                int LA41_0 = input.LA(1);

                if ( (LA41_0==62) ) {
                    alt41=1;
                }


                switch (alt41) {
            	case 1 :
            	    // InternalMazeDsl.g:1723:4: (lv_items_5_0= ruleLootItemConfig )
            	    {
            	    // InternalMazeDsl.g:1723:4: (lv_items_5_0= ruleLootItemConfig )
            	    // InternalMazeDsl.g:1724:5: lv_items_5_0= ruleLootItemConfig
            	    {

            	    					newCompositeNode(grammarAccess.getLootTableConfigAccess().getItemsLootItemConfigParserRuleCall_4_0());
            	    				
            	    pushFollow(FOLLOW_64);
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
            	    if ( cnt41 >= 1 ) break loop41;
                        EarlyExitException eee =
                            new EarlyExitException(41, input);
                        throw eee;
                }
                cnt41++;
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
    // InternalMazeDsl.g:1749:1: entryRuleLootItemConfig returns [EObject current=null] : iv_ruleLootItemConfig= ruleLootItemConfig EOF ;
    public final EObject entryRuleLootItemConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLootItemConfig = null;


        try {
            // InternalMazeDsl.g:1749:55: (iv_ruleLootItemConfig= ruleLootItemConfig EOF )
            // InternalMazeDsl.g:1750:2: iv_ruleLootItemConfig= ruleLootItemConfig EOF
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
    // InternalMazeDsl.g:1756:1: ruleLootItemConfig returns [EObject current=null] : (otherlv_0= 'item' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' otherlv_3= 'type' ( (lv_type_4_0= ruleLootItemTypeEnum ) ) otherlv_5= 'value' ( (lv_value_6_0= RULE_INT ) ) (otherlv_7= 'weight' ( (lv_weight_8_0= RULE_INT ) ) )? otherlv_9= '}' ) ;
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
            // InternalMazeDsl.g:1762:2: ( (otherlv_0= 'item' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' otherlv_3= 'type' ( (lv_type_4_0= ruleLootItemTypeEnum ) ) otherlv_5= 'value' ( (lv_value_6_0= RULE_INT ) ) (otherlv_7= 'weight' ( (lv_weight_8_0= RULE_INT ) ) )? otherlv_9= '}' ) )
            // InternalMazeDsl.g:1763:2: (otherlv_0= 'item' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' otherlv_3= 'type' ( (lv_type_4_0= ruleLootItemTypeEnum ) ) otherlv_5= 'value' ( (lv_value_6_0= RULE_INT ) ) (otherlv_7= 'weight' ( (lv_weight_8_0= RULE_INT ) ) )? otherlv_9= '}' )
            {
            // InternalMazeDsl.g:1763:2: (otherlv_0= 'item' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' otherlv_3= 'type' ( (lv_type_4_0= ruleLootItemTypeEnum ) ) otherlv_5= 'value' ( (lv_value_6_0= RULE_INT ) ) (otherlv_7= 'weight' ( (lv_weight_8_0= RULE_INT ) ) )? otherlv_9= '}' )
            // InternalMazeDsl.g:1764:3: otherlv_0= 'item' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' otherlv_3= 'type' ( (lv_type_4_0= ruleLootItemTypeEnum ) ) otherlv_5= 'value' ( (lv_value_6_0= RULE_INT ) ) (otherlv_7= 'weight' ( (lv_weight_8_0= RULE_INT ) ) )? otherlv_9= '}'
            {
            otherlv_0=(Token)match(input,62,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getLootItemConfigAccess().getItemKeyword_0());
            		
            // InternalMazeDsl.g:1768:3: ( (lv_name_1_0= ruleValidID ) )
            // InternalMazeDsl.g:1769:4: (lv_name_1_0= ruleValidID )
            {
            // InternalMazeDsl.g:1769:4: (lv_name_1_0= ruleValidID )
            // InternalMazeDsl.g:1770:5: lv_name_1_0= ruleValidID
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

            otherlv_2=(Token)match(input,12,FOLLOW_24); 

            			newLeafNode(otherlv_2, grammarAccess.getLootItemConfigAccess().getLeftCurlyBracketKeyword_2());
            		
            otherlv_3=(Token)match(input,25,FOLLOW_65); 

            			newLeafNode(otherlv_3, grammarAccess.getLootItemConfigAccess().getTypeKeyword_3());
            		
            // InternalMazeDsl.g:1795:3: ( (lv_type_4_0= ruleLootItemTypeEnum ) )
            // InternalMazeDsl.g:1796:4: (lv_type_4_0= ruleLootItemTypeEnum )
            {
            // InternalMazeDsl.g:1796:4: (lv_type_4_0= ruleLootItemTypeEnum )
            // InternalMazeDsl.g:1797:5: lv_type_4_0= ruleLootItemTypeEnum
            {

            					newCompositeNode(grammarAccess.getLootItemConfigAccess().getTypeLootItemTypeEnumEnumRuleCall_4_0());
            				
            pushFollow(FOLLOW_66);
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

            otherlv_5=(Token)match(input,63,FOLLOW_37); 

            			newLeafNode(otherlv_5, grammarAccess.getLootItemConfigAccess().getValueKeyword_5());
            		
            // InternalMazeDsl.g:1818:3: ( (lv_value_6_0= RULE_INT ) )
            // InternalMazeDsl.g:1819:4: (lv_value_6_0= RULE_INT )
            {
            // InternalMazeDsl.g:1819:4: (lv_value_6_0= RULE_INT )
            // InternalMazeDsl.g:1820:5: lv_value_6_0= RULE_INT
            {
            lv_value_6_0=(Token)match(input,RULE_INT,FOLLOW_67); 

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

            // InternalMazeDsl.g:1836:3: (otherlv_7= 'weight' ( (lv_weight_8_0= RULE_INT ) ) )?
            int alt42=2;
            int LA42_0 = input.LA(1);

            if ( (LA42_0==64) ) {
                alt42=1;
            }
            switch (alt42) {
                case 1 :
                    // InternalMazeDsl.g:1837:4: otherlv_7= 'weight' ( (lv_weight_8_0= RULE_INT ) )
                    {
                    otherlv_7=(Token)match(input,64,FOLLOW_37); 

                    				newLeafNode(otherlv_7, grammarAccess.getLootItemConfigAccess().getWeightKeyword_7_0());
                    			
                    // InternalMazeDsl.g:1841:4: ( (lv_weight_8_0= RULE_INT ) )
                    // InternalMazeDsl.g:1842:5: (lv_weight_8_0= RULE_INT )
                    {
                    // InternalMazeDsl.g:1842:5: (lv_weight_8_0= RULE_INT )
                    // InternalMazeDsl.g:1843:6: lv_weight_8_0= RULE_INT
                    {
                    lv_weight_8_0=(Token)match(input,RULE_INT,FOLLOW_11); 

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
    // InternalMazeDsl.g:1868:1: entryRuleLevelConfig returns [EObject current=null] : iv_ruleLevelConfig= ruleLevelConfig EOF ;
    public final EObject entryRuleLevelConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLevelConfig = null;


        try {
            // InternalMazeDsl.g:1868:52: (iv_ruleLevelConfig= ruleLevelConfig EOF )
            // InternalMazeDsl.g:1869:2: iv_ruleLevelConfig= ruleLevelConfig EOF
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
    // InternalMazeDsl.g:1875:1: ruleLevelConfig returns [EObject current=null] : (otherlv_0= 'level' ( (lv_number_1_0= RULE_INT ) ) ( (lv_name_2_0= RULE_STRING ) ) otherlv_3= '{' (otherlv_4= 'basedOn' ( (lv_baseLevel_5_0= RULE_INT ) ) )? (otherlv_6= 'threatCapacity' ( (lv_threatCapacityFactor_7_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_8= 'wallMaterials' ( (lv_showWallMaterials_9_0= ruleBOOLEAN_OBJECT ) ) )? (otherlv_10= 'inheritWeapons' ( (lv_inheritWeapons_11_0= ruleBOOLEAN_OBJECT ) ) )? (otherlv_12= 'cascadeDepth' ( (lv_cascadeDepth_13_0= ruleINT_OBJECT ) ) )? ( (lv_zones_14_0= ruleLevelZone ) )* ( (lv_sergeant_15_0= ruleSergeantConfig ) )? ( (lv_sergeantPosts_16_0= ruleTierCountConfig ) )* ( (lv_mines_17_0= ruleMineRingConfig ) )? ( (lv_portalGhost_18_0= rulePortalGhostConfig ) )? ( (lv_ghostCounts_19_0= ruleGhostCountConfig ) )* ( (lv_teleport_20_0= ruleTeleportRulesConfig ) )? ( (lv_guardTower_21_0= ruleGuardTowerConfig ) )? ( (lv_towerCounts_22_0= ruleTowerCountConfig ) )* ( (lv_wallBuilder_23_0= ruleWallBuilderConfig ) )? ( (lv_builderCounts_24_0= ruleBuilderCountConfig ) )* ( (lv_lava_25_0= ruleLavaConfig ) )? ( (lv_ufoRaid_26_0= ruleUfoRaidConfig ) )? ( (lv_lastLifeGift_27_0= ruleLastLifeGiftConfig ) )? otherlv_28= '}' ) ;
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
        Token otherlv_28=null;
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

        EObject lv_ufoRaid_26_0 = null;

        EObject lv_lastLifeGift_27_0 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:1881:2: ( (otherlv_0= 'level' ( (lv_number_1_0= RULE_INT ) ) ( (lv_name_2_0= RULE_STRING ) ) otherlv_3= '{' (otherlv_4= 'basedOn' ( (lv_baseLevel_5_0= RULE_INT ) ) )? (otherlv_6= 'threatCapacity' ( (lv_threatCapacityFactor_7_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_8= 'wallMaterials' ( (lv_showWallMaterials_9_0= ruleBOOLEAN_OBJECT ) ) )? (otherlv_10= 'inheritWeapons' ( (lv_inheritWeapons_11_0= ruleBOOLEAN_OBJECT ) ) )? (otherlv_12= 'cascadeDepth' ( (lv_cascadeDepth_13_0= ruleINT_OBJECT ) ) )? ( (lv_zones_14_0= ruleLevelZone ) )* ( (lv_sergeant_15_0= ruleSergeantConfig ) )? ( (lv_sergeantPosts_16_0= ruleTierCountConfig ) )* ( (lv_mines_17_0= ruleMineRingConfig ) )? ( (lv_portalGhost_18_0= rulePortalGhostConfig ) )? ( (lv_ghostCounts_19_0= ruleGhostCountConfig ) )* ( (lv_teleport_20_0= ruleTeleportRulesConfig ) )? ( (lv_guardTower_21_0= ruleGuardTowerConfig ) )? ( (lv_towerCounts_22_0= ruleTowerCountConfig ) )* ( (lv_wallBuilder_23_0= ruleWallBuilderConfig ) )? ( (lv_builderCounts_24_0= ruleBuilderCountConfig ) )* ( (lv_lava_25_0= ruleLavaConfig ) )? ( (lv_ufoRaid_26_0= ruleUfoRaidConfig ) )? ( (lv_lastLifeGift_27_0= ruleLastLifeGiftConfig ) )? otherlv_28= '}' ) )
            // InternalMazeDsl.g:1882:2: (otherlv_0= 'level' ( (lv_number_1_0= RULE_INT ) ) ( (lv_name_2_0= RULE_STRING ) ) otherlv_3= '{' (otherlv_4= 'basedOn' ( (lv_baseLevel_5_0= RULE_INT ) ) )? (otherlv_6= 'threatCapacity' ( (lv_threatCapacityFactor_7_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_8= 'wallMaterials' ( (lv_showWallMaterials_9_0= ruleBOOLEAN_OBJECT ) ) )? (otherlv_10= 'inheritWeapons' ( (lv_inheritWeapons_11_0= ruleBOOLEAN_OBJECT ) ) )? (otherlv_12= 'cascadeDepth' ( (lv_cascadeDepth_13_0= ruleINT_OBJECT ) ) )? ( (lv_zones_14_0= ruleLevelZone ) )* ( (lv_sergeant_15_0= ruleSergeantConfig ) )? ( (lv_sergeantPosts_16_0= ruleTierCountConfig ) )* ( (lv_mines_17_0= ruleMineRingConfig ) )? ( (lv_portalGhost_18_0= rulePortalGhostConfig ) )? ( (lv_ghostCounts_19_0= ruleGhostCountConfig ) )* ( (lv_teleport_20_0= ruleTeleportRulesConfig ) )? ( (lv_guardTower_21_0= ruleGuardTowerConfig ) )? ( (lv_towerCounts_22_0= ruleTowerCountConfig ) )* ( (lv_wallBuilder_23_0= ruleWallBuilderConfig ) )? ( (lv_builderCounts_24_0= ruleBuilderCountConfig ) )* ( (lv_lava_25_0= ruleLavaConfig ) )? ( (lv_ufoRaid_26_0= ruleUfoRaidConfig ) )? ( (lv_lastLifeGift_27_0= ruleLastLifeGiftConfig ) )? otherlv_28= '}' )
            {
            // InternalMazeDsl.g:1882:2: (otherlv_0= 'level' ( (lv_number_1_0= RULE_INT ) ) ( (lv_name_2_0= RULE_STRING ) ) otherlv_3= '{' (otherlv_4= 'basedOn' ( (lv_baseLevel_5_0= RULE_INT ) ) )? (otherlv_6= 'threatCapacity' ( (lv_threatCapacityFactor_7_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_8= 'wallMaterials' ( (lv_showWallMaterials_9_0= ruleBOOLEAN_OBJECT ) ) )? (otherlv_10= 'inheritWeapons' ( (lv_inheritWeapons_11_0= ruleBOOLEAN_OBJECT ) ) )? (otherlv_12= 'cascadeDepth' ( (lv_cascadeDepth_13_0= ruleINT_OBJECT ) ) )? ( (lv_zones_14_0= ruleLevelZone ) )* ( (lv_sergeant_15_0= ruleSergeantConfig ) )? ( (lv_sergeantPosts_16_0= ruleTierCountConfig ) )* ( (lv_mines_17_0= ruleMineRingConfig ) )? ( (lv_portalGhost_18_0= rulePortalGhostConfig ) )? ( (lv_ghostCounts_19_0= ruleGhostCountConfig ) )* ( (lv_teleport_20_0= ruleTeleportRulesConfig ) )? ( (lv_guardTower_21_0= ruleGuardTowerConfig ) )? ( (lv_towerCounts_22_0= ruleTowerCountConfig ) )* ( (lv_wallBuilder_23_0= ruleWallBuilderConfig ) )? ( (lv_builderCounts_24_0= ruleBuilderCountConfig ) )* ( (lv_lava_25_0= ruleLavaConfig ) )? ( (lv_ufoRaid_26_0= ruleUfoRaidConfig ) )? ( (lv_lastLifeGift_27_0= ruleLastLifeGiftConfig ) )? otherlv_28= '}' )
            // InternalMazeDsl.g:1883:3: otherlv_0= 'level' ( (lv_number_1_0= RULE_INT ) ) ( (lv_name_2_0= RULE_STRING ) ) otherlv_3= '{' (otherlv_4= 'basedOn' ( (lv_baseLevel_5_0= RULE_INT ) ) )? (otherlv_6= 'threatCapacity' ( (lv_threatCapacityFactor_7_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_8= 'wallMaterials' ( (lv_showWallMaterials_9_0= ruleBOOLEAN_OBJECT ) ) )? (otherlv_10= 'inheritWeapons' ( (lv_inheritWeapons_11_0= ruleBOOLEAN_OBJECT ) ) )? (otherlv_12= 'cascadeDepth' ( (lv_cascadeDepth_13_0= ruleINT_OBJECT ) ) )? ( (lv_zones_14_0= ruleLevelZone ) )* ( (lv_sergeant_15_0= ruleSergeantConfig ) )? ( (lv_sergeantPosts_16_0= ruleTierCountConfig ) )* ( (lv_mines_17_0= ruleMineRingConfig ) )? ( (lv_portalGhost_18_0= rulePortalGhostConfig ) )? ( (lv_ghostCounts_19_0= ruleGhostCountConfig ) )* ( (lv_teleport_20_0= ruleTeleportRulesConfig ) )? ( (lv_guardTower_21_0= ruleGuardTowerConfig ) )? ( (lv_towerCounts_22_0= ruleTowerCountConfig ) )* ( (lv_wallBuilder_23_0= ruleWallBuilderConfig ) )? ( (lv_builderCounts_24_0= ruleBuilderCountConfig ) )* ( (lv_lava_25_0= ruleLavaConfig ) )? ( (lv_ufoRaid_26_0= ruleUfoRaidConfig ) )? ( (lv_lastLifeGift_27_0= ruleLastLifeGiftConfig ) )? otherlv_28= '}'
            {
            otherlv_0=(Token)match(input,16,FOLLOW_37); 

            			newLeafNode(otherlv_0, grammarAccess.getLevelConfigAccess().getLevelKeyword_0());
            		
            // InternalMazeDsl.g:1887:3: ( (lv_number_1_0= RULE_INT ) )
            // InternalMazeDsl.g:1888:4: (lv_number_1_0= RULE_INT )
            {
            // InternalMazeDsl.g:1888:4: (lv_number_1_0= RULE_INT )
            // InternalMazeDsl.g:1889:5: lv_number_1_0= RULE_INT
            {
            lv_number_1_0=(Token)match(input,RULE_INT,FOLLOW_12); 

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

            // InternalMazeDsl.g:1905:3: ( (lv_name_2_0= RULE_STRING ) )
            // InternalMazeDsl.g:1906:4: (lv_name_2_0= RULE_STRING )
            {
            // InternalMazeDsl.g:1906:4: (lv_name_2_0= RULE_STRING )
            // InternalMazeDsl.g:1907:5: lv_name_2_0= RULE_STRING
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

            otherlv_3=(Token)match(input,12,FOLLOW_68); 

            			newLeafNode(otherlv_3, grammarAccess.getLevelConfigAccess().getLeftCurlyBracketKeyword_3());
            		
            // InternalMazeDsl.g:1927:3: (otherlv_4= 'basedOn' ( (lv_baseLevel_5_0= RULE_INT ) ) )?
            int alt43=2;
            int LA43_0 = input.LA(1);

            if ( (LA43_0==65) ) {
                alt43=1;
            }
            switch (alt43) {
                case 1 :
                    // InternalMazeDsl.g:1928:4: otherlv_4= 'basedOn' ( (lv_baseLevel_5_0= RULE_INT ) )
                    {
                    otherlv_4=(Token)match(input,65,FOLLOW_37); 

                    				newLeafNode(otherlv_4, grammarAccess.getLevelConfigAccess().getBasedOnKeyword_4_0());
                    			
                    // InternalMazeDsl.g:1932:4: ( (lv_baseLevel_5_0= RULE_INT ) )
                    // InternalMazeDsl.g:1933:5: (lv_baseLevel_5_0= RULE_INT )
                    {
                    // InternalMazeDsl.g:1933:5: (lv_baseLevel_5_0= RULE_INT )
                    // InternalMazeDsl.g:1934:6: lv_baseLevel_5_0= RULE_INT
                    {
                    lv_baseLevel_5_0=(Token)match(input,RULE_INT,FOLLOW_69); 

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

            // InternalMazeDsl.g:1951:3: (otherlv_6= 'threatCapacity' ( (lv_threatCapacityFactor_7_0= ruleDOUBLE_OBJECT ) ) )?
            int alt44=2;
            int LA44_0 = input.LA(1);

            if ( (LA44_0==66) ) {
                alt44=1;
            }
            switch (alt44) {
                case 1 :
                    // InternalMazeDsl.g:1952:4: otherlv_6= 'threatCapacity' ( (lv_threatCapacityFactor_7_0= ruleDOUBLE_OBJECT ) )
                    {
                    otherlv_6=(Token)match(input,66,FOLLOW_18); 

                    				newLeafNode(otherlv_6, grammarAccess.getLevelConfigAccess().getThreatCapacityKeyword_5_0());
                    			
                    // InternalMazeDsl.g:1956:4: ( (lv_threatCapacityFactor_7_0= ruleDOUBLE_OBJECT ) )
                    // InternalMazeDsl.g:1957:5: (lv_threatCapacityFactor_7_0= ruleDOUBLE_OBJECT )
                    {
                    // InternalMazeDsl.g:1957:5: (lv_threatCapacityFactor_7_0= ruleDOUBLE_OBJECT )
                    // InternalMazeDsl.g:1958:6: lv_threatCapacityFactor_7_0= ruleDOUBLE_OBJECT
                    {

                    						newCompositeNode(grammarAccess.getLevelConfigAccess().getThreatCapacityFactorDOUBLE_OBJECTParserRuleCall_5_1_0());
                    					
                    pushFollow(FOLLOW_70);
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

            // InternalMazeDsl.g:1976:3: (otherlv_8= 'wallMaterials' ( (lv_showWallMaterials_9_0= ruleBOOLEAN_OBJECT ) ) )?
            int alt45=2;
            int LA45_0 = input.LA(1);

            if ( (LA45_0==67) ) {
                alt45=1;
            }
            switch (alt45) {
                case 1 :
                    // InternalMazeDsl.g:1977:4: otherlv_8= 'wallMaterials' ( (lv_showWallMaterials_9_0= ruleBOOLEAN_OBJECT ) )
                    {
                    otherlv_8=(Token)match(input,67,FOLLOW_30); 

                    				newLeafNode(otherlv_8, grammarAccess.getLevelConfigAccess().getWallMaterialsKeyword_6_0());
                    			
                    // InternalMazeDsl.g:1981:4: ( (lv_showWallMaterials_9_0= ruleBOOLEAN_OBJECT ) )
                    // InternalMazeDsl.g:1982:5: (lv_showWallMaterials_9_0= ruleBOOLEAN_OBJECT )
                    {
                    // InternalMazeDsl.g:1982:5: (lv_showWallMaterials_9_0= ruleBOOLEAN_OBJECT )
                    // InternalMazeDsl.g:1983:6: lv_showWallMaterials_9_0= ruleBOOLEAN_OBJECT
                    {

                    						newCompositeNode(grammarAccess.getLevelConfigAccess().getShowWallMaterialsBOOLEAN_OBJECTParserRuleCall_6_1_0());
                    					
                    pushFollow(FOLLOW_71);
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

            // InternalMazeDsl.g:2001:3: (otherlv_10= 'inheritWeapons' ( (lv_inheritWeapons_11_0= ruleBOOLEAN_OBJECT ) ) )?
            int alt46=2;
            int LA46_0 = input.LA(1);

            if ( (LA46_0==68) ) {
                alt46=1;
            }
            switch (alt46) {
                case 1 :
                    // InternalMazeDsl.g:2002:4: otherlv_10= 'inheritWeapons' ( (lv_inheritWeapons_11_0= ruleBOOLEAN_OBJECT ) )
                    {
                    otherlv_10=(Token)match(input,68,FOLLOW_30); 

                    				newLeafNode(otherlv_10, grammarAccess.getLevelConfigAccess().getInheritWeaponsKeyword_7_0());
                    			
                    // InternalMazeDsl.g:2006:4: ( (lv_inheritWeapons_11_0= ruleBOOLEAN_OBJECT ) )
                    // InternalMazeDsl.g:2007:5: (lv_inheritWeapons_11_0= ruleBOOLEAN_OBJECT )
                    {
                    // InternalMazeDsl.g:2007:5: (lv_inheritWeapons_11_0= ruleBOOLEAN_OBJECT )
                    // InternalMazeDsl.g:2008:6: lv_inheritWeapons_11_0= ruleBOOLEAN_OBJECT
                    {

                    						newCompositeNode(grammarAccess.getLevelConfigAccess().getInheritWeaponsBOOLEAN_OBJECTParserRuleCall_7_1_0());
                    					
                    pushFollow(FOLLOW_72);
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

            // InternalMazeDsl.g:2026:3: (otherlv_12= 'cascadeDepth' ( (lv_cascadeDepth_13_0= ruleINT_OBJECT ) ) )?
            int alt47=2;
            int LA47_0 = input.LA(1);

            if ( (LA47_0==69) ) {
                alt47=1;
            }
            switch (alt47) {
                case 1 :
                    // InternalMazeDsl.g:2027:4: otherlv_12= 'cascadeDepth' ( (lv_cascadeDepth_13_0= ruleINT_OBJECT ) )
                    {
                    otherlv_12=(Token)match(input,69,FOLLOW_18); 

                    				newLeafNode(otherlv_12, grammarAccess.getLevelConfigAccess().getCascadeDepthKeyword_8_0());
                    			
                    // InternalMazeDsl.g:2031:4: ( (lv_cascadeDepth_13_0= ruleINT_OBJECT ) )
                    // InternalMazeDsl.g:2032:5: (lv_cascadeDepth_13_0= ruleINT_OBJECT )
                    {
                    // InternalMazeDsl.g:2032:5: (lv_cascadeDepth_13_0= ruleINT_OBJECT )
                    // InternalMazeDsl.g:2033:6: lv_cascadeDepth_13_0= ruleINT_OBJECT
                    {

                    						newCompositeNode(grammarAccess.getLevelConfigAccess().getCascadeDepthINT_OBJECTParserRuleCall_8_1_0());
                    					
                    pushFollow(FOLLOW_73);
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

            // InternalMazeDsl.g:2051:3: ( (lv_zones_14_0= ruleLevelZone ) )*
            loop48:
            do {
                int alt48=2;
                int LA48_0 = input.LA(1);

                if ( (LA48_0==70) ) {
                    alt48=1;
                }


                switch (alt48) {
            	case 1 :
            	    // InternalMazeDsl.g:2052:4: (lv_zones_14_0= ruleLevelZone )
            	    {
            	    // InternalMazeDsl.g:2052:4: (lv_zones_14_0= ruleLevelZone )
            	    // InternalMazeDsl.g:2053:5: lv_zones_14_0= ruleLevelZone
            	    {

            	    					newCompositeNode(grammarAccess.getLevelConfigAccess().getZonesLevelZoneParserRuleCall_9_0());
            	    				
            	    pushFollow(FOLLOW_73);
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
            	    break loop48;
                }
            } while (true);

            // InternalMazeDsl.g:2070:3: ( (lv_sergeant_15_0= ruleSergeantConfig ) )?
            int alt49=2;
            int LA49_0 = input.LA(1);

            if ( (LA49_0==73) ) {
                alt49=1;
            }
            switch (alt49) {
                case 1 :
                    // InternalMazeDsl.g:2071:4: (lv_sergeant_15_0= ruleSergeantConfig )
                    {
                    // InternalMazeDsl.g:2071:4: (lv_sergeant_15_0= ruleSergeantConfig )
                    // InternalMazeDsl.g:2072:5: lv_sergeant_15_0= ruleSergeantConfig
                    {

                    					newCompositeNode(grammarAccess.getLevelConfigAccess().getSergeantSergeantConfigParserRuleCall_10_0());
                    				
                    pushFollow(FOLLOW_74);
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

            // InternalMazeDsl.g:2089:3: ( (lv_sergeantPosts_16_0= ruleTierCountConfig ) )*
            loop50:
            do {
                int alt50=2;
                int LA50_0 = input.LA(1);

                if ( (LA50_0==83) ) {
                    alt50=1;
                }


                switch (alt50) {
            	case 1 :
            	    // InternalMazeDsl.g:2090:4: (lv_sergeantPosts_16_0= ruleTierCountConfig )
            	    {
            	    // InternalMazeDsl.g:2090:4: (lv_sergeantPosts_16_0= ruleTierCountConfig )
            	    // InternalMazeDsl.g:2091:5: lv_sergeantPosts_16_0= ruleTierCountConfig
            	    {

            	    					newCompositeNode(grammarAccess.getLevelConfigAccess().getSergeantPostsTierCountConfigParserRuleCall_11_0());
            	    				
            	    pushFollow(FOLLOW_74);
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
            	    break loop50;
                }
            } while (true);

            // InternalMazeDsl.g:2108:3: ( (lv_mines_17_0= ruleMineRingConfig ) )?
            int alt51=2;
            int LA51_0 = input.LA(1);

            if ( (LA51_0==84) ) {
                alt51=1;
            }
            switch (alt51) {
                case 1 :
                    // InternalMazeDsl.g:2109:4: (lv_mines_17_0= ruleMineRingConfig )
                    {
                    // InternalMazeDsl.g:2109:4: (lv_mines_17_0= ruleMineRingConfig )
                    // InternalMazeDsl.g:2110:5: lv_mines_17_0= ruleMineRingConfig
                    {

                    					newCompositeNode(grammarAccess.getLevelConfigAccess().getMinesMineRingConfigParserRuleCall_12_0());
                    				
                    pushFollow(FOLLOW_75);
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

            // InternalMazeDsl.g:2127:3: ( (lv_portalGhost_18_0= rulePortalGhostConfig ) )?
            int alt52=2;
            int LA52_0 = input.LA(1);

            if ( (LA52_0==94) ) {
                alt52=1;
            }
            switch (alt52) {
                case 1 :
                    // InternalMazeDsl.g:2128:4: (lv_portalGhost_18_0= rulePortalGhostConfig )
                    {
                    // InternalMazeDsl.g:2128:4: (lv_portalGhost_18_0= rulePortalGhostConfig )
                    // InternalMazeDsl.g:2129:5: lv_portalGhost_18_0= rulePortalGhostConfig
                    {

                    					newCompositeNode(grammarAccess.getLevelConfigAccess().getPortalGhostPortalGhostConfigParserRuleCall_13_0());
                    				
                    pushFollow(FOLLOW_76);
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

            // InternalMazeDsl.g:2146:3: ( (lv_ghostCounts_19_0= ruleGhostCountConfig ) )*
            loop53:
            do {
                int alt53=2;
                int LA53_0 = input.LA(1);

                if ( (LA53_0==95) ) {
                    alt53=1;
                }


                switch (alt53) {
            	case 1 :
            	    // InternalMazeDsl.g:2147:4: (lv_ghostCounts_19_0= ruleGhostCountConfig )
            	    {
            	    // InternalMazeDsl.g:2147:4: (lv_ghostCounts_19_0= ruleGhostCountConfig )
            	    // InternalMazeDsl.g:2148:5: lv_ghostCounts_19_0= ruleGhostCountConfig
            	    {

            	    					newCompositeNode(grammarAccess.getLevelConfigAccess().getGhostCountsGhostCountConfigParserRuleCall_14_0());
            	    				
            	    pushFollow(FOLLOW_76);
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
            	    break loop53;
                }
            } while (true);

            // InternalMazeDsl.g:2165:3: ( (lv_teleport_20_0= ruleTeleportRulesConfig ) )?
            int alt54=2;
            int LA54_0 = input.LA(1);

            if ( (LA54_0==169) ) {
                alt54=1;
            }
            switch (alt54) {
                case 1 :
                    // InternalMazeDsl.g:2166:4: (lv_teleport_20_0= ruleTeleportRulesConfig )
                    {
                    // InternalMazeDsl.g:2166:4: (lv_teleport_20_0= ruleTeleportRulesConfig )
                    // InternalMazeDsl.g:2167:5: lv_teleport_20_0= ruleTeleportRulesConfig
                    {

                    					newCompositeNode(grammarAccess.getLevelConfigAccess().getTeleportTeleportRulesConfigParserRuleCall_15_0());
                    				
                    pushFollow(FOLLOW_77);
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

            // InternalMazeDsl.g:2184:3: ( (lv_guardTower_21_0= ruleGuardTowerConfig ) )?
            int alt55=2;
            int LA55_0 = input.LA(1);

            if ( (LA55_0==96) ) {
                alt55=1;
            }
            switch (alt55) {
                case 1 :
                    // InternalMazeDsl.g:2185:4: (lv_guardTower_21_0= ruleGuardTowerConfig )
                    {
                    // InternalMazeDsl.g:2185:4: (lv_guardTower_21_0= ruleGuardTowerConfig )
                    // InternalMazeDsl.g:2186:5: lv_guardTower_21_0= ruleGuardTowerConfig
                    {

                    					newCompositeNode(grammarAccess.getLevelConfigAccess().getGuardTowerGuardTowerConfigParserRuleCall_16_0());
                    				
                    pushFollow(FOLLOW_78);
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

            // InternalMazeDsl.g:2203:3: ( (lv_towerCounts_22_0= ruleTowerCountConfig ) )*
            loop56:
            do {
                int alt56=2;
                int LA56_0 = input.LA(1);

                if ( (LA56_0==112) ) {
                    alt56=1;
                }


                switch (alt56) {
            	case 1 :
            	    // InternalMazeDsl.g:2204:4: (lv_towerCounts_22_0= ruleTowerCountConfig )
            	    {
            	    // InternalMazeDsl.g:2204:4: (lv_towerCounts_22_0= ruleTowerCountConfig )
            	    // InternalMazeDsl.g:2205:5: lv_towerCounts_22_0= ruleTowerCountConfig
            	    {

            	    					newCompositeNode(grammarAccess.getLevelConfigAccess().getTowerCountsTowerCountConfigParserRuleCall_17_0());
            	    				
            	    pushFollow(FOLLOW_78);
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
            	    break loop56;
                }
            } while (true);

            // InternalMazeDsl.g:2222:3: ( (lv_wallBuilder_23_0= ruleWallBuilderConfig ) )?
            int alt57=2;
            int LA57_0 = input.LA(1);

            if ( (LA57_0==113) ) {
                alt57=1;
            }
            switch (alt57) {
                case 1 :
                    // InternalMazeDsl.g:2223:4: (lv_wallBuilder_23_0= ruleWallBuilderConfig )
                    {
                    // InternalMazeDsl.g:2223:4: (lv_wallBuilder_23_0= ruleWallBuilderConfig )
                    // InternalMazeDsl.g:2224:5: lv_wallBuilder_23_0= ruleWallBuilderConfig
                    {

                    					newCompositeNode(grammarAccess.getLevelConfigAccess().getWallBuilderWallBuilderConfigParserRuleCall_18_0());
                    				
                    pushFollow(FOLLOW_79);
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

            // InternalMazeDsl.g:2241:3: ( (lv_builderCounts_24_0= ruleBuilderCountConfig ) )*
            loop58:
            do {
                int alt58=2;
                int LA58_0 = input.LA(1);

                if ( (LA58_0==120) ) {
                    alt58=1;
                }


                switch (alt58) {
            	case 1 :
            	    // InternalMazeDsl.g:2242:4: (lv_builderCounts_24_0= ruleBuilderCountConfig )
            	    {
            	    // InternalMazeDsl.g:2242:4: (lv_builderCounts_24_0= ruleBuilderCountConfig )
            	    // InternalMazeDsl.g:2243:5: lv_builderCounts_24_0= ruleBuilderCountConfig
            	    {

            	    					newCompositeNode(grammarAccess.getLevelConfigAccess().getBuilderCountsBuilderCountConfigParserRuleCall_19_0());
            	    				
            	    pushFollow(FOLLOW_79);
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
            	    break loop58;
                }
            } while (true);

            // InternalMazeDsl.g:2260:3: ( (lv_lava_25_0= ruleLavaConfig ) )?
            int alt59=2;
            int LA59_0 = input.LA(1);

            if ( (LA59_0==121) ) {
                alt59=1;
            }
            switch (alt59) {
                case 1 :
                    // InternalMazeDsl.g:2261:4: (lv_lava_25_0= ruleLavaConfig )
                    {
                    // InternalMazeDsl.g:2261:4: (lv_lava_25_0= ruleLavaConfig )
                    // InternalMazeDsl.g:2262:5: lv_lava_25_0= ruleLavaConfig
                    {

                    					newCompositeNode(grammarAccess.getLevelConfigAccess().getLavaLavaConfigParserRuleCall_20_0());
                    				
                    pushFollow(FOLLOW_80);
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

            // InternalMazeDsl.g:2279:3: ( (lv_ufoRaid_26_0= ruleUfoRaidConfig ) )?
            int alt60=2;
            int LA60_0 = input.LA(1);

            if ( (LA60_0==131) ) {
                alt60=1;
            }
            switch (alt60) {
                case 1 :
                    // InternalMazeDsl.g:2280:4: (lv_ufoRaid_26_0= ruleUfoRaidConfig )
                    {
                    // InternalMazeDsl.g:2280:4: (lv_ufoRaid_26_0= ruleUfoRaidConfig )
                    // InternalMazeDsl.g:2281:5: lv_ufoRaid_26_0= ruleUfoRaidConfig
                    {

                    					newCompositeNode(grammarAccess.getLevelConfigAccess().getUfoRaidUfoRaidConfigParserRuleCall_21_0());
                    				
                    pushFollow(FOLLOW_81);
                    lv_ufoRaid_26_0=ruleUfoRaidConfig();

                    state._fsp--;


                    					if (current==null) {
                    						current = createModelElementForParent(grammarAccess.getLevelConfigRule());
                    					}
                    					set(
                    						current,
                    						"ufoRaid",
                    						lv_ufoRaid_26_0,
                    						"main.game.maze.dsl.MazeDsl.UfoRaidConfig");
                    					afterParserOrEnumRuleCall();
                    				

                    }


                    }
                    break;

            }

            // InternalMazeDsl.g:2298:3: ( (lv_lastLifeGift_27_0= ruleLastLifeGiftConfig ) )?
            int alt61=2;
            int LA61_0 = input.LA(1);

            if ( (LA61_0==167) ) {
                alt61=1;
            }
            switch (alt61) {
                case 1 :
                    // InternalMazeDsl.g:2299:4: (lv_lastLifeGift_27_0= ruleLastLifeGiftConfig )
                    {
                    // InternalMazeDsl.g:2299:4: (lv_lastLifeGift_27_0= ruleLastLifeGiftConfig )
                    // InternalMazeDsl.g:2300:5: lv_lastLifeGift_27_0= ruleLastLifeGiftConfig
                    {

                    					newCompositeNode(grammarAccess.getLevelConfigAccess().getLastLifeGiftLastLifeGiftConfigParserRuleCall_22_0());
                    				
                    pushFollow(FOLLOW_11);
                    lv_lastLifeGift_27_0=ruleLastLifeGiftConfig();

                    state._fsp--;


                    					if (current==null) {
                    						current = createModelElementForParent(grammarAccess.getLevelConfigRule());
                    					}
                    					set(
                    						current,
                    						"lastLifeGift",
                    						lv_lastLifeGift_27_0,
                    						"main.game.maze.dsl.MazeDsl.LastLifeGiftConfig");
                    					afterParserOrEnumRuleCall();
                    				

                    }


                    }
                    break;

            }

            otherlv_28=(Token)match(input,13,FOLLOW_2); 

            			newLeafNode(otherlv_28, grammarAccess.getLevelConfigAccess().getRightCurlyBracketKeyword_23());
            		

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
    // InternalMazeDsl.g:2325:1: entryRuleLevelZone returns [EObject current=null] : iv_ruleLevelZone= ruleLevelZone EOF ;
    public final EObject entryRuleLevelZone() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLevelZone = null;


        try {
            // InternalMazeDsl.g:2325:50: (iv_ruleLevelZone= ruleLevelZone EOF )
            // InternalMazeDsl.g:2326:2: iv_ruleLevelZone= ruleLevelZone EOF
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
    // InternalMazeDsl.g:2332:1: ruleLevelZone returns [EObject current=null] : (otherlv_0= 'ring' ( (lv_displayName_1_0= RULE_STRING ) ) (otherlv_2= 'share' ( (lv_breakableShare_3_0= ruleDOUBLE_OBJECT ) ) )? otherlv_4= 'materials' ( (lv_materialIds_5_0= ruleValidID ) ) (otherlv_6= ',' ( (lv_materialIds_7_0= ruleValidID ) ) )* ) ;
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
            // InternalMazeDsl.g:2338:2: ( (otherlv_0= 'ring' ( (lv_displayName_1_0= RULE_STRING ) ) (otherlv_2= 'share' ( (lv_breakableShare_3_0= ruleDOUBLE_OBJECT ) ) )? otherlv_4= 'materials' ( (lv_materialIds_5_0= ruleValidID ) ) (otherlv_6= ',' ( (lv_materialIds_7_0= ruleValidID ) ) )* ) )
            // InternalMazeDsl.g:2339:2: (otherlv_0= 'ring' ( (lv_displayName_1_0= RULE_STRING ) ) (otherlv_2= 'share' ( (lv_breakableShare_3_0= ruleDOUBLE_OBJECT ) ) )? otherlv_4= 'materials' ( (lv_materialIds_5_0= ruleValidID ) ) (otherlv_6= ',' ( (lv_materialIds_7_0= ruleValidID ) ) )* )
            {
            // InternalMazeDsl.g:2339:2: (otherlv_0= 'ring' ( (lv_displayName_1_0= RULE_STRING ) ) (otherlv_2= 'share' ( (lv_breakableShare_3_0= ruleDOUBLE_OBJECT ) ) )? otherlv_4= 'materials' ( (lv_materialIds_5_0= ruleValidID ) ) (otherlv_6= ',' ( (lv_materialIds_7_0= ruleValidID ) ) )* )
            // InternalMazeDsl.g:2340:3: otherlv_0= 'ring' ( (lv_displayName_1_0= RULE_STRING ) ) (otherlv_2= 'share' ( (lv_breakableShare_3_0= ruleDOUBLE_OBJECT ) ) )? otherlv_4= 'materials' ( (lv_materialIds_5_0= ruleValidID ) ) (otherlv_6= ',' ( (lv_materialIds_7_0= ruleValidID ) ) )*
            {
            otherlv_0=(Token)match(input,70,FOLLOW_12); 

            			newLeafNode(otherlv_0, grammarAccess.getLevelZoneAccess().getRingKeyword_0());
            		
            // InternalMazeDsl.g:2344:3: ( (lv_displayName_1_0= RULE_STRING ) )
            // InternalMazeDsl.g:2345:4: (lv_displayName_1_0= RULE_STRING )
            {
            // InternalMazeDsl.g:2345:4: (lv_displayName_1_0= RULE_STRING )
            // InternalMazeDsl.g:2346:5: lv_displayName_1_0= RULE_STRING
            {
            lv_displayName_1_0=(Token)match(input,RULE_STRING,FOLLOW_82); 

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

            // InternalMazeDsl.g:2362:3: (otherlv_2= 'share' ( (lv_breakableShare_3_0= ruleDOUBLE_OBJECT ) ) )?
            int alt62=2;
            int LA62_0 = input.LA(1);

            if ( (LA62_0==71) ) {
                alt62=1;
            }
            switch (alt62) {
                case 1 :
                    // InternalMazeDsl.g:2363:4: otherlv_2= 'share' ( (lv_breakableShare_3_0= ruleDOUBLE_OBJECT ) )
                    {
                    otherlv_2=(Token)match(input,71,FOLLOW_18); 

                    				newLeafNode(otherlv_2, grammarAccess.getLevelZoneAccess().getShareKeyword_2_0());
                    			
                    // InternalMazeDsl.g:2367:4: ( (lv_breakableShare_3_0= ruleDOUBLE_OBJECT ) )
                    // InternalMazeDsl.g:2368:5: (lv_breakableShare_3_0= ruleDOUBLE_OBJECT )
                    {
                    // InternalMazeDsl.g:2368:5: (lv_breakableShare_3_0= ruleDOUBLE_OBJECT )
                    // InternalMazeDsl.g:2369:6: lv_breakableShare_3_0= ruleDOUBLE_OBJECT
                    {

                    						newCompositeNode(grammarAccess.getLevelZoneAccess().getBreakableShareDOUBLE_OBJECTParserRuleCall_2_1_0());
                    					
                    pushFollow(FOLLOW_83);
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
            		
            // InternalMazeDsl.g:2391:3: ( (lv_materialIds_5_0= ruleValidID ) )
            // InternalMazeDsl.g:2392:4: (lv_materialIds_5_0= ruleValidID )
            {
            // InternalMazeDsl.g:2392:4: (lv_materialIds_5_0= ruleValidID )
            // InternalMazeDsl.g:2393:5: lv_materialIds_5_0= ruleValidID
            {

            					newCompositeNode(grammarAccess.getLevelZoneAccess().getMaterialIdsValidIDParserRuleCall_4_0());
            				
            pushFollow(FOLLOW_84);
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

            // InternalMazeDsl.g:2410:3: (otherlv_6= ',' ( (lv_materialIds_7_0= ruleValidID ) ) )*
            loop63:
            do {
                int alt63=2;
                int LA63_0 = input.LA(1);

                if ( (LA63_0==50) ) {
                    alt63=1;
                }


                switch (alt63) {
            	case 1 :
            	    // InternalMazeDsl.g:2411:4: otherlv_6= ',' ( (lv_materialIds_7_0= ruleValidID ) )
            	    {
            	    otherlv_6=(Token)match(input,50,FOLLOW_3); 

            	    				newLeafNode(otherlv_6, grammarAccess.getLevelZoneAccess().getCommaKeyword_5_0());
            	    			
            	    // InternalMazeDsl.g:2415:4: ( (lv_materialIds_7_0= ruleValidID ) )
            	    // InternalMazeDsl.g:2416:5: (lv_materialIds_7_0= ruleValidID )
            	    {
            	    // InternalMazeDsl.g:2416:5: (lv_materialIds_7_0= ruleValidID )
            	    // InternalMazeDsl.g:2417:6: lv_materialIds_7_0= ruleValidID
            	    {

            	    						newCompositeNode(grammarAccess.getLevelZoneAccess().getMaterialIdsValidIDParserRuleCall_5_1_0());
            	    					
            	    pushFollow(FOLLOW_84);
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
            	    break loop63;
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
    // InternalMazeDsl.g:2439:1: entryRuleSergeantConfig returns [EObject current=null] : iv_ruleSergeantConfig= ruleSergeantConfig EOF ;
    public final EObject entryRuleSergeantConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSergeantConfig = null;


        try {
            // InternalMazeDsl.g:2439:55: (iv_ruleSergeantConfig= ruleSergeantConfig EOF )
            // InternalMazeDsl.g:2440:2: iv_ruleSergeantConfig= ruleSergeantConfig EOF
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
    // InternalMazeDsl.g:2446:1: ruleSergeantConfig returns [EObject current=null] : (otherlv_0= 'sergeant' ( (lv_displayName_1_0= RULE_STRING ) ) otherlv_2= '{' otherlv_3= 'health' ( (lv_health_4_0= RULE_INT ) ) otherlv_5= 'sightRange' ( (lv_sightRange_6_0= ruleDOUBLE ) ) otherlv_7= 'rounds' ( (lv_roundsPerBurst_8_0= RULE_INT ) ) otherlv_9= 'roundDamage' ( (lv_roundDamage_10_0= RULE_INT ) ) otherlv_11= 'roundInterval' ( (lv_roundIntervalSeconds_12_0= ruleDOUBLE ) ) otherlv_13= 'burstCooldown' ( (lv_burstCooldownSeconds_14_0= ruleDOUBLE ) ) otherlv_15= 'sprite' ( (lv_sprite_16_0= RULE_STRING ) ) (otherlv_17= 'fireSound' ( (lv_fireSound_18_0= RULE_STRING ) ) )? otherlv_19= 'drops' ( (lv_dropWeapon_20_0= ruleDropWeaponEnum ) ) otherlv_21= 'postDistance' ( (lv_postDistanceFromHeart_22_0= ruleDOUBLE ) ) otherlv_23= '}' ) ;
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
            // InternalMazeDsl.g:2452:2: ( (otherlv_0= 'sergeant' ( (lv_displayName_1_0= RULE_STRING ) ) otherlv_2= '{' otherlv_3= 'health' ( (lv_health_4_0= RULE_INT ) ) otherlv_5= 'sightRange' ( (lv_sightRange_6_0= ruleDOUBLE ) ) otherlv_7= 'rounds' ( (lv_roundsPerBurst_8_0= RULE_INT ) ) otherlv_9= 'roundDamage' ( (lv_roundDamage_10_0= RULE_INT ) ) otherlv_11= 'roundInterval' ( (lv_roundIntervalSeconds_12_0= ruleDOUBLE ) ) otherlv_13= 'burstCooldown' ( (lv_burstCooldownSeconds_14_0= ruleDOUBLE ) ) otherlv_15= 'sprite' ( (lv_sprite_16_0= RULE_STRING ) ) (otherlv_17= 'fireSound' ( (lv_fireSound_18_0= RULE_STRING ) ) )? otherlv_19= 'drops' ( (lv_dropWeapon_20_0= ruleDropWeaponEnum ) ) otherlv_21= 'postDistance' ( (lv_postDistanceFromHeart_22_0= ruleDOUBLE ) ) otherlv_23= '}' ) )
            // InternalMazeDsl.g:2453:2: (otherlv_0= 'sergeant' ( (lv_displayName_1_0= RULE_STRING ) ) otherlv_2= '{' otherlv_3= 'health' ( (lv_health_4_0= RULE_INT ) ) otherlv_5= 'sightRange' ( (lv_sightRange_6_0= ruleDOUBLE ) ) otherlv_7= 'rounds' ( (lv_roundsPerBurst_8_0= RULE_INT ) ) otherlv_9= 'roundDamage' ( (lv_roundDamage_10_0= RULE_INT ) ) otherlv_11= 'roundInterval' ( (lv_roundIntervalSeconds_12_0= ruleDOUBLE ) ) otherlv_13= 'burstCooldown' ( (lv_burstCooldownSeconds_14_0= ruleDOUBLE ) ) otherlv_15= 'sprite' ( (lv_sprite_16_0= RULE_STRING ) ) (otherlv_17= 'fireSound' ( (lv_fireSound_18_0= RULE_STRING ) ) )? otherlv_19= 'drops' ( (lv_dropWeapon_20_0= ruleDropWeaponEnum ) ) otherlv_21= 'postDistance' ( (lv_postDistanceFromHeart_22_0= ruleDOUBLE ) ) otherlv_23= '}' )
            {
            // InternalMazeDsl.g:2453:2: (otherlv_0= 'sergeant' ( (lv_displayName_1_0= RULE_STRING ) ) otherlv_2= '{' otherlv_3= 'health' ( (lv_health_4_0= RULE_INT ) ) otherlv_5= 'sightRange' ( (lv_sightRange_6_0= ruleDOUBLE ) ) otherlv_7= 'rounds' ( (lv_roundsPerBurst_8_0= RULE_INT ) ) otherlv_9= 'roundDamage' ( (lv_roundDamage_10_0= RULE_INT ) ) otherlv_11= 'roundInterval' ( (lv_roundIntervalSeconds_12_0= ruleDOUBLE ) ) otherlv_13= 'burstCooldown' ( (lv_burstCooldownSeconds_14_0= ruleDOUBLE ) ) otherlv_15= 'sprite' ( (lv_sprite_16_0= RULE_STRING ) ) (otherlv_17= 'fireSound' ( (lv_fireSound_18_0= RULE_STRING ) ) )? otherlv_19= 'drops' ( (lv_dropWeapon_20_0= ruleDropWeaponEnum ) ) otherlv_21= 'postDistance' ( (lv_postDistanceFromHeart_22_0= ruleDOUBLE ) ) otherlv_23= '}' )
            // InternalMazeDsl.g:2454:3: otherlv_0= 'sergeant' ( (lv_displayName_1_0= RULE_STRING ) ) otherlv_2= '{' otherlv_3= 'health' ( (lv_health_4_0= RULE_INT ) ) otherlv_5= 'sightRange' ( (lv_sightRange_6_0= ruleDOUBLE ) ) otherlv_7= 'rounds' ( (lv_roundsPerBurst_8_0= RULE_INT ) ) otherlv_9= 'roundDamage' ( (lv_roundDamage_10_0= RULE_INT ) ) otherlv_11= 'roundInterval' ( (lv_roundIntervalSeconds_12_0= ruleDOUBLE ) ) otherlv_13= 'burstCooldown' ( (lv_burstCooldownSeconds_14_0= ruleDOUBLE ) ) otherlv_15= 'sprite' ( (lv_sprite_16_0= RULE_STRING ) ) (otherlv_17= 'fireSound' ( (lv_fireSound_18_0= RULE_STRING ) ) )? otherlv_19= 'drops' ( (lv_dropWeapon_20_0= ruleDropWeaponEnum ) ) otherlv_21= 'postDistance' ( (lv_postDistanceFromHeart_22_0= ruleDOUBLE ) ) otherlv_23= '}'
            {
            otherlv_0=(Token)match(input,73,FOLLOW_12); 

            			newLeafNode(otherlv_0, grammarAccess.getSergeantConfigAccess().getSergeantKeyword_0());
            		
            // InternalMazeDsl.g:2458:3: ( (lv_displayName_1_0= RULE_STRING ) )
            // InternalMazeDsl.g:2459:4: (lv_displayName_1_0= RULE_STRING )
            {
            // InternalMazeDsl.g:2459:4: (lv_displayName_1_0= RULE_STRING )
            // InternalMazeDsl.g:2460:5: lv_displayName_1_0= RULE_STRING
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

            otherlv_2=(Token)match(input,12,FOLLOW_85); 

            			newLeafNode(otherlv_2, grammarAccess.getSergeantConfigAccess().getLeftCurlyBracketKeyword_2());
            		
            otherlv_3=(Token)match(input,27,FOLLOW_37); 

            			newLeafNode(otherlv_3, grammarAccess.getSergeantConfigAccess().getHealthKeyword_3());
            		
            // InternalMazeDsl.g:2484:3: ( (lv_health_4_0= RULE_INT ) )
            // InternalMazeDsl.g:2485:4: (lv_health_4_0= RULE_INT )
            {
            // InternalMazeDsl.g:2485:4: (lv_health_4_0= RULE_INT )
            // InternalMazeDsl.g:2486:5: lv_health_4_0= RULE_INT
            {
            lv_health_4_0=(Token)match(input,RULE_INT,FOLLOW_86); 

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

            otherlv_5=(Token)match(input,74,FOLLOW_18); 

            			newLeafNode(otherlv_5, grammarAccess.getSergeantConfigAccess().getSightRangeKeyword_5());
            		
            // InternalMazeDsl.g:2506:3: ( (lv_sightRange_6_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:2507:4: (lv_sightRange_6_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:2507:4: (lv_sightRange_6_0= ruleDOUBLE )
            // InternalMazeDsl.g:2508:5: lv_sightRange_6_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getSergeantConfigAccess().getSightRangeDOUBLEParserRuleCall_6_0());
            				
            pushFollow(FOLLOW_87);
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

            otherlv_7=(Token)match(input,75,FOLLOW_37); 

            			newLeafNode(otherlv_7, grammarAccess.getSergeantConfigAccess().getRoundsKeyword_7());
            		
            // InternalMazeDsl.g:2529:3: ( (lv_roundsPerBurst_8_0= RULE_INT ) )
            // InternalMazeDsl.g:2530:4: (lv_roundsPerBurst_8_0= RULE_INT )
            {
            // InternalMazeDsl.g:2530:4: (lv_roundsPerBurst_8_0= RULE_INT )
            // InternalMazeDsl.g:2531:5: lv_roundsPerBurst_8_0= RULE_INT
            {
            lv_roundsPerBurst_8_0=(Token)match(input,RULE_INT,FOLLOW_88); 

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

            otherlv_9=(Token)match(input,76,FOLLOW_37); 

            			newLeafNode(otherlv_9, grammarAccess.getSergeantConfigAccess().getRoundDamageKeyword_9());
            		
            // InternalMazeDsl.g:2551:3: ( (lv_roundDamage_10_0= RULE_INT ) )
            // InternalMazeDsl.g:2552:4: (lv_roundDamage_10_0= RULE_INT )
            {
            // InternalMazeDsl.g:2552:4: (lv_roundDamage_10_0= RULE_INT )
            // InternalMazeDsl.g:2553:5: lv_roundDamage_10_0= RULE_INT
            {
            lv_roundDamage_10_0=(Token)match(input,RULE_INT,FOLLOW_89); 

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

            otherlv_11=(Token)match(input,77,FOLLOW_18); 

            			newLeafNode(otherlv_11, grammarAccess.getSergeantConfigAccess().getRoundIntervalKeyword_11());
            		
            // InternalMazeDsl.g:2573:3: ( (lv_roundIntervalSeconds_12_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:2574:4: (lv_roundIntervalSeconds_12_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:2574:4: (lv_roundIntervalSeconds_12_0= ruleDOUBLE )
            // InternalMazeDsl.g:2575:5: lv_roundIntervalSeconds_12_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getSergeantConfigAccess().getRoundIntervalSecondsDOUBLEParserRuleCall_12_0());
            				
            pushFollow(FOLLOW_90);
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

            otherlv_13=(Token)match(input,78,FOLLOW_18); 

            			newLeafNode(otherlv_13, grammarAccess.getSergeantConfigAccess().getBurstCooldownKeyword_13());
            		
            // InternalMazeDsl.g:2596:3: ( (lv_burstCooldownSeconds_14_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:2597:4: (lv_burstCooldownSeconds_14_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:2597:4: (lv_burstCooldownSeconds_14_0= ruleDOUBLE )
            // InternalMazeDsl.g:2598:5: lv_burstCooldownSeconds_14_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getSergeantConfigAccess().getBurstCooldownSecondsDOUBLEParserRuleCall_14_0());
            				
            pushFollow(FOLLOW_91);
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

            otherlv_15=(Token)match(input,79,FOLLOW_12); 

            			newLeafNode(otherlv_15, grammarAccess.getSergeantConfigAccess().getSpriteKeyword_15());
            		
            // InternalMazeDsl.g:2619:3: ( (lv_sprite_16_0= RULE_STRING ) )
            // InternalMazeDsl.g:2620:4: (lv_sprite_16_0= RULE_STRING )
            {
            // InternalMazeDsl.g:2620:4: (lv_sprite_16_0= RULE_STRING )
            // InternalMazeDsl.g:2621:5: lv_sprite_16_0= RULE_STRING
            {
            lv_sprite_16_0=(Token)match(input,RULE_STRING,FOLLOW_92); 

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

            // InternalMazeDsl.g:2637:3: (otherlv_17= 'fireSound' ( (lv_fireSound_18_0= RULE_STRING ) ) )?
            int alt64=2;
            int LA64_0 = input.LA(1);

            if ( (LA64_0==80) ) {
                alt64=1;
            }
            switch (alt64) {
                case 1 :
                    // InternalMazeDsl.g:2638:4: otherlv_17= 'fireSound' ( (lv_fireSound_18_0= RULE_STRING ) )
                    {
                    otherlv_17=(Token)match(input,80,FOLLOW_12); 

                    				newLeafNode(otherlv_17, grammarAccess.getSergeantConfigAccess().getFireSoundKeyword_17_0());
                    			
                    // InternalMazeDsl.g:2642:4: ( (lv_fireSound_18_0= RULE_STRING ) )
                    // InternalMazeDsl.g:2643:5: (lv_fireSound_18_0= RULE_STRING )
                    {
                    // InternalMazeDsl.g:2643:5: (lv_fireSound_18_0= RULE_STRING )
                    // InternalMazeDsl.g:2644:6: lv_fireSound_18_0= RULE_STRING
                    {
                    lv_fireSound_18_0=(Token)match(input,RULE_STRING,FOLLOW_93); 

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

            otherlv_19=(Token)match(input,81,FOLLOW_94); 

            			newLeafNode(otherlv_19, grammarAccess.getSergeantConfigAccess().getDropsKeyword_18());
            		
            // InternalMazeDsl.g:2665:3: ( (lv_dropWeapon_20_0= ruleDropWeaponEnum ) )
            // InternalMazeDsl.g:2666:4: (lv_dropWeapon_20_0= ruleDropWeaponEnum )
            {
            // InternalMazeDsl.g:2666:4: (lv_dropWeapon_20_0= ruleDropWeaponEnum )
            // InternalMazeDsl.g:2667:5: lv_dropWeapon_20_0= ruleDropWeaponEnum
            {

            					newCompositeNode(grammarAccess.getSergeantConfigAccess().getDropWeaponDropWeaponEnumEnumRuleCall_19_0());
            				
            pushFollow(FOLLOW_95);
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

            otherlv_21=(Token)match(input,82,FOLLOW_18); 

            			newLeafNode(otherlv_21, grammarAccess.getSergeantConfigAccess().getPostDistanceKeyword_20());
            		
            // InternalMazeDsl.g:2688:3: ( (lv_postDistanceFromHeart_22_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:2689:4: (lv_postDistanceFromHeart_22_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:2689:4: (lv_postDistanceFromHeart_22_0= ruleDOUBLE )
            // InternalMazeDsl.g:2690:5: lv_postDistanceFromHeart_22_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getSergeantConfigAccess().getPostDistanceFromHeartDOUBLEParserRuleCall_21_0());
            				
            pushFollow(FOLLOW_11);
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
    // InternalMazeDsl.g:2715:1: entryRuleTierCountConfig returns [EObject current=null] : iv_ruleTierCountConfig= ruleTierCountConfig EOF ;
    public final EObject entryRuleTierCountConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTierCountConfig = null;


        try {
            // InternalMazeDsl.g:2715:56: (iv_ruleTierCountConfig= ruleTierCountConfig EOF )
            // InternalMazeDsl.g:2716:2: iv_ruleTierCountConfig= ruleTierCountConfig EOF
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
    // InternalMazeDsl.g:2722:1: ruleTierCountConfig returns [EObject current=null] : (otherlv_0= 'sergeants' ( (lv_difficulty_1_0= ruleDifficultyLevel ) ) ( (lv_count_2_0= RULE_INT ) ) ) ;
    public final EObject ruleTierCountConfig() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_count_2_0=null;
        Enumerator lv_difficulty_1_0 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:2728:2: ( (otherlv_0= 'sergeants' ( (lv_difficulty_1_0= ruleDifficultyLevel ) ) ( (lv_count_2_0= RULE_INT ) ) ) )
            // InternalMazeDsl.g:2729:2: (otherlv_0= 'sergeants' ( (lv_difficulty_1_0= ruleDifficultyLevel ) ) ( (lv_count_2_0= RULE_INT ) ) )
            {
            // InternalMazeDsl.g:2729:2: (otherlv_0= 'sergeants' ( (lv_difficulty_1_0= ruleDifficultyLevel ) ) ( (lv_count_2_0= RULE_INT ) ) )
            // InternalMazeDsl.g:2730:3: otherlv_0= 'sergeants' ( (lv_difficulty_1_0= ruleDifficultyLevel ) ) ( (lv_count_2_0= RULE_INT ) )
            {
            otherlv_0=(Token)match(input,83,FOLLOW_14); 

            			newLeafNode(otherlv_0, grammarAccess.getTierCountConfigAccess().getSergeantsKeyword_0());
            		
            // InternalMazeDsl.g:2734:3: ( (lv_difficulty_1_0= ruleDifficultyLevel ) )
            // InternalMazeDsl.g:2735:4: (lv_difficulty_1_0= ruleDifficultyLevel )
            {
            // InternalMazeDsl.g:2735:4: (lv_difficulty_1_0= ruleDifficultyLevel )
            // InternalMazeDsl.g:2736:5: lv_difficulty_1_0= ruleDifficultyLevel
            {

            					newCompositeNode(grammarAccess.getTierCountConfigAccess().getDifficultyDifficultyLevelEnumRuleCall_1_0());
            				
            pushFollow(FOLLOW_37);
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

            // InternalMazeDsl.g:2753:3: ( (lv_count_2_0= RULE_INT ) )
            // InternalMazeDsl.g:2754:4: (lv_count_2_0= RULE_INT )
            {
            // InternalMazeDsl.g:2754:4: (lv_count_2_0= RULE_INT )
            // InternalMazeDsl.g:2755:5: lv_count_2_0= RULE_INT
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
    // InternalMazeDsl.g:2775:1: entryRuleMineRingConfig returns [EObject current=null] : iv_ruleMineRingConfig= ruleMineRingConfig EOF ;
    public final EObject entryRuleMineRingConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMineRingConfig = null;


        try {
            // InternalMazeDsl.g:2775:55: (iv_ruleMineRingConfig= ruleMineRingConfig EOF )
            // InternalMazeDsl.g:2776:2: iv_ruleMineRingConfig= ruleMineRingConfig EOF
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
    // InternalMazeDsl.g:2782:1: ruleMineRingConfig returns [EObject current=null] : (otherlv_0= 'mines' otherlv_1= '{' otherlv_2= 'count' ( (lv_count_3_0= RULE_INT ) ) otherlv_4= 'ringRadius' ( (lv_ringRadius_5_0= ruleDOUBLE ) ) (otherlv_6= 'secondRing' ( (lv_secondRingRadius_7_0= ruleDOUBLE ) ) )? otherlv_8= 'triggerRadius' ( (lv_triggerRadius_9_0= ruleDOUBLE ) ) otherlv_10= 'pulse' ( (lv_pulseSeconds_11_0= ruleDOUBLE ) ) otherlv_12= 'blastRadius' ( (lv_blastRadius_13_0= ruleDOUBLE ) ) otherlv_14= 'blastDamage' ( (lv_blastDamage_15_0= RULE_INT ) ) otherlv_16= 'playerDamage' ( (lv_playerDamage_17_0= RULE_INT ) ) otherlv_18= 'holeRadius' ( (lv_holeRadius_19_0= ruleDOUBLE ) ) otherlv_20= '}' ) ;
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
            // InternalMazeDsl.g:2788:2: ( (otherlv_0= 'mines' otherlv_1= '{' otherlv_2= 'count' ( (lv_count_3_0= RULE_INT ) ) otherlv_4= 'ringRadius' ( (lv_ringRadius_5_0= ruleDOUBLE ) ) (otherlv_6= 'secondRing' ( (lv_secondRingRadius_7_0= ruleDOUBLE ) ) )? otherlv_8= 'triggerRadius' ( (lv_triggerRadius_9_0= ruleDOUBLE ) ) otherlv_10= 'pulse' ( (lv_pulseSeconds_11_0= ruleDOUBLE ) ) otherlv_12= 'blastRadius' ( (lv_blastRadius_13_0= ruleDOUBLE ) ) otherlv_14= 'blastDamage' ( (lv_blastDamage_15_0= RULE_INT ) ) otherlv_16= 'playerDamage' ( (lv_playerDamage_17_0= RULE_INT ) ) otherlv_18= 'holeRadius' ( (lv_holeRadius_19_0= ruleDOUBLE ) ) otherlv_20= '}' ) )
            // InternalMazeDsl.g:2789:2: (otherlv_0= 'mines' otherlv_1= '{' otherlv_2= 'count' ( (lv_count_3_0= RULE_INT ) ) otherlv_4= 'ringRadius' ( (lv_ringRadius_5_0= ruleDOUBLE ) ) (otherlv_6= 'secondRing' ( (lv_secondRingRadius_7_0= ruleDOUBLE ) ) )? otherlv_8= 'triggerRadius' ( (lv_triggerRadius_9_0= ruleDOUBLE ) ) otherlv_10= 'pulse' ( (lv_pulseSeconds_11_0= ruleDOUBLE ) ) otherlv_12= 'blastRadius' ( (lv_blastRadius_13_0= ruleDOUBLE ) ) otherlv_14= 'blastDamage' ( (lv_blastDamage_15_0= RULE_INT ) ) otherlv_16= 'playerDamage' ( (lv_playerDamage_17_0= RULE_INT ) ) otherlv_18= 'holeRadius' ( (lv_holeRadius_19_0= ruleDOUBLE ) ) otherlv_20= '}' )
            {
            // InternalMazeDsl.g:2789:2: (otherlv_0= 'mines' otherlv_1= '{' otherlv_2= 'count' ( (lv_count_3_0= RULE_INT ) ) otherlv_4= 'ringRadius' ( (lv_ringRadius_5_0= ruleDOUBLE ) ) (otherlv_6= 'secondRing' ( (lv_secondRingRadius_7_0= ruleDOUBLE ) ) )? otherlv_8= 'triggerRadius' ( (lv_triggerRadius_9_0= ruleDOUBLE ) ) otherlv_10= 'pulse' ( (lv_pulseSeconds_11_0= ruleDOUBLE ) ) otherlv_12= 'blastRadius' ( (lv_blastRadius_13_0= ruleDOUBLE ) ) otherlv_14= 'blastDamage' ( (lv_blastDamage_15_0= RULE_INT ) ) otherlv_16= 'playerDamage' ( (lv_playerDamage_17_0= RULE_INT ) ) otherlv_18= 'holeRadius' ( (lv_holeRadius_19_0= ruleDOUBLE ) ) otherlv_20= '}' )
            // InternalMazeDsl.g:2790:3: otherlv_0= 'mines' otherlv_1= '{' otherlv_2= 'count' ( (lv_count_3_0= RULE_INT ) ) otherlv_4= 'ringRadius' ( (lv_ringRadius_5_0= ruleDOUBLE ) ) (otherlv_6= 'secondRing' ( (lv_secondRingRadius_7_0= ruleDOUBLE ) ) )? otherlv_8= 'triggerRadius' ( (lv_triggerRadius_9_0= ruleDOUBLE ) ) otherlv_10= 'pulse' ( (lv_pulseSeconds_11_0= ruleDOUBLE ) ) otherlv_12= 'blastRadius' ( (lv_blastRadius_13_0= ruleDOUBLE ) ) otherlv_14= 'blastDamage' ( (lv_blastDamage_15_0= RULE_INT ) ) otherlv_16= 'playerDamage' ( (lv_playerDamage_17_0= RULE_INT ) ) otherlv_18= 'holeRadius' ( (lv_holeRadius_19_0= ruleDOUBLE ) ) otherlv_20= '}'
            {
            otherlv_0=(Token)match(input,84,FOLLOW_4); 

            			newLeafNode(otherlv_0, grammarAccess.getMineRingConfigAccess().getMinesKeyword_0());
            		
            otherlv_1=(Token)match(input,12,FOLLOW_96); 

            			newLeafNode(otherlv_1, grammarAccess.getMineRingConfigAccess().getLeftCurlyBracketKeyword_1());
            		
            otherlv_2=(Token)match(input,85,FOLLOW_37); 

            			newLeafNode(otherlv_2, grammarAccess.getMineRingConfigAccess().getCountKeyword_2());
            		
            // InternalMazeDsl.g:2802:3: ( (lv_count_3_0= RULE_INT ) )
            // InternalMazeDsl.g:2803:4: (lv_count_3_0= RULE_INT )
            {
            // InternalMazeDsl.g:2803:4: (lv_count_3_0= RULE_INT )
            // InternalMazeDsl.g:2804:5: lv_count_3_0= RULE_INT
            {
            lv_count_3_0=(Token)match(input,RULE_INT,FOLLOW_97); 

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

            otherlv_4=(Token)match(input,86,FOLLOW_18); 

            			newLeafNode(otherlv_4, grammarAccess.getMineRingConfigAccess().getRingRadiusKeyword_4());
            		
            // InternalMazeDsl.g:2824:3: ( (lv_ringRadius_5_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:2825:4: (lv_ringRadius_5_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:2825:4: (lv_ringRadius_5_0= ruleDOUBLE )
            // InternalMazeDsl.g:2826:5: lv_ringRadius_5_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getMineRingConfigAccess().getRingRadiusDOUBLEParserRuleCall_5_0());
            				
            pushFollow(FOLLOW_98);
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

            // InternalMazeDsl.g:2843:3: (otherlv_6= 'secondRing' ( (lv_secondRingRadius_7_0= ruleDOUBLE ) ) )?
            int alt65=2;
            int LA65_0 = input.LA(1);

            if ( (LA65_0==87) ) {
                alt65=1;
            }
            switch (alt65) {
                case 1 :
                    // InternalMazeDsl.g:2844:4: otherlv_6= 'secondRing' ( (lv_secondRingRadius_7_0= ruleDOUBLE ) )
                    {
                    otherlv_6=(Token)match(input,87,FOLLOW_18); 

                    				newLeafNode(otherlv_6, grammarAccess.getMineRingConfigAccess().getSecondRingKeyword_6_0());
                    			
                    // InternalMazeDsl.g:2848:4: ( (lv_secondRingRadius_7_0= ruleDOUBLE ) )
                    // InternalMazeDsl.g:2849:5: (lv_secondRingRadius_7_0= ruleDOUBLE )
                    {
                    // InternalMazeDsl.g:2849:5: (lv_secondRingRadius_7_0= ruleDOUBLE )
                    // InternalMazeDsl.g:2850:6: lv_secondRingRadius_7_0= ruleDOUBLE
                    {

                    						newCompositeNode(grammarAccess.getMineRingConfigAccess().getSecondRingRadiusDOUBLEParserRuleCall_6_1_0());
                    					
                    pushFollow(FOLLOW_99);
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

            otherlv_8=(Token)match(input,88,FOLLOW_18); 

            			newLeafNode(otherlv_8, grammarAccess.getMineRingConfigAccess().getTriggerRadiusKeyword_7());
            		
            // InternalMazeDsl.g:2872:3: ( (lv_triggerRadius_9_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:2873:4: (lv_triggerRadius_9_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:2873:4: (lv_triggerRadius_9_0= ruleDOUBLE )
            // InternalMazeDsl.g:2874:5: lv_triggerRadius_9_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getMineRingConfigAccess().getTriggerRadiusDOUBLEParserRuleCall_8_0());
            				
            pushFollow(FOLLOW_100);
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

            otherlv_10=(Token)match(input,89,FOLLOW_18); 

            			newLeafNode(otherlv_10, grammarAccess.getMineRingConfigAccess().getPulseKeyword_9());
            		
            // InternalMazeDsl.g:2895:3: ( (lv_pulseSeconds_11_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:2896:4: (lv_pulseSeconds_11_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:2896:4: (lv_pulseSeconds_11_0= ruleDOUBLE )
            // InternalMazeDsl.g:2897:5: lv_pulseSeconds_11_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getMineRingConfigAccess().getPulseSecondsDOUBLEParserRuleCall_10_0());
            				
            pushFollow(FOLLOW_101);
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

            otherlv_12=(Token)match(input,90,FOLLOW_18); 

            			newLeafNode(otherlv_12, grammarAccess.getMineRingConfigAccess().getBlastRadiusKeyword_11());
            		
            // InternalMazeDsl.g:2918:3: ( (lv_blastRadius_13_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:2919:4: (lv_blastRadius_13_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:2919:4: (lv_blastRadius_13_0= ruleDOUBLE )
            // InternalMazeDsl.g:2920:5: lv_blastRadius_13_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getMineRingConfigAccess().getBlastRadiusDOUBLEParserRuleCall_12_0());
            				
            pushFollow(FOLLOW_102);
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

            otherlv_14=(Token)match(input,91,FOLLOW_37); 

            			newLeafNode(otherlv_14, grammarAccess.getMineRingConfigAccess().getBlastDamageKeyword_13());
            		
            // InternalMazeDsl.g:2941:3: ( (lv_blastDamage_15_0= RULE_INT ) )
            // InternalMazeDsl.g:2942:4: (lv_blastDamage_15_0= RULE_INT )
            {
            // InternalMazeDsl.g:2942:4: (lv_blastDamage_15_0= RULE_INT )
            // InternalMazeDsl.g:2943:5: lv_blastDamage_15_0= RULE_INT
            {
            lv_blastDamage_15_0=(Token)match(input,RULE_INT,FOLLOW_103); 

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

            otherlv_16=(Token)match(input,92,FOLLOW_37); 

            			newLeafNode(otherlv_16, grammarAccess.getMineRingConfigAccess().getPlayerDamageKeyword_15());
            		
            // InternalMazeDsl.g:2963:3: ( (lv_playerDamage_17_0= RULE_INT ) )
            // InternalMazeDsl.g:2964:4: (lv_playerDamage_17_0= RULE_INT )
            {
            // InternalMazeDsl.g:2964:4: (lv_playerDamage_17_0= RULE_INT )
            // InternalMazeDsl.g:2965:5: lv_playerDamage_17_0= RULE_INT
            {
            lv_playerDamage_17_0=(Token)match(input,RULE_INT,FOLLOW_104); 

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

            otherlv_18=(Token)match(input,93,FOLLOW_18); 

            			newLeafNode(otherlv_18, grammarAccess.getMineRingConfigAccess().getHoleRadiusKeyword_17());
            		
            // InternalMazeDsl.g:2985:3: ( (lv_holeRadius_19_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:2986:4: (lv_holeRadius_19_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:2986:4: (lv_holeRadius_19_0= ruleDOUBLE )
            // InternalMazeDsl.g:2987:5: lv_holeRadius_19_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getMineRingConfigAccess().getHoleRadiusDOUBLEParserRuleCall_18_0());
            				
            pushFollow(FOLLOW_11);
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
    // InternalMazeDsl.g:3012:1: entryRulePortalGhostConfig returns [EObject current=null] : iv_rulePortalGhostConfig= rulePortalGhostConfig EOF ;
    public final EObject entryRulePortalGhostConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePortalGhostConfig = null;


        try {
            // InternalMazeDsl.g:3012:58: (iv_rulePortalGhostConfig= rulePortalGhostConfig EOF )
            // InternalMazeDsl.g:3013:2: iv_rulePortalGhostConfig= rulePortalGhostConfig EOF
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
    // InternalMazeDsl.g:3019:1: rulePortalGhostConfig returns [EObject current=null] : (otherlv_0= 'portalGhost' ( (lv_displayName_1_0= RULE_STRING ) ) otherlv_2= '{' otherlv_3= 'health' ( (lv_health_4_0= RULE_INT ) ) otherlv_5= 'speed' ( (lv_speed_6_0= ruleDOUBLE ) ) otherlv_7= 'sprite' ( (lv_sprite_8_0= RULE_STRING ) ) otherlv_9= '}' ) ;
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
            // InternalMazeDsl.g:3025:2: ( (otherlv_0= 'portalGhost' ( (lv_displayName_1_0= RULE_STRING ) ) otherlv_2= '{' otherlv_3= 'health' ( (lv_health_4_0= RULE_INT ) ) otherlv_5= 'speed' ( (lv_speed_6_0= ruleDOUBLE ) ) otherlv_7= 'sprite' ( (lv_sprite_8_0= RULE_STRING ) ) otherlv_9= '}' ) )
            // InternalMazeDsl.g:3026:2: (otherlv_0= 'portalGhost' ( (lv_displayName_1_0= RULE_STRING ) ) otherlv_2= '{' otherlv_3= 'health' ( (lv_health_4_0= RULE_INT ) ) otherlv_5= 'speed' ( (lv_speed_6_0= ruleDOUBLE ) ) otherlv_7= 'sprite' ( (lv_sprite_8_0= RULE_STRING ) ) otherlv_9= '}' )
            {
            // InternalMazeDsl.g:3026:2: (otherlv_0= 'portalGhost' ( (lv_displayName_1_0= RULE_STRING ) ) otherlv_2= '{' otherlv_3= 'health' ( (lv_health_4_0= RULE_INT ) ) otherlv_5= 'speed' ( (lv_speed_6_0= ruleDOUBLE ) ) otherlv_7= 'sprite' ( (lv_sprite_8_0= RULE_STRING ) ) otherlv_9= '}' )
            // InternalMazeDsl.g:3027:3: otherlv_0= 'portalGhost' ( (lv_displayName_1_0= RULE_STRING ) ) otherlv_2= '{' otherlv_3= 'health' ( (lv_health_4_0= RULE_INT ) ) otherlv_5= 'speed' ( (lv_speed_6_0= ruleDOUBLE ) ) otherlv_7= 'sprite' ( (lv_sprite_8_0= RULE_STRING ) ) otherlv_9= '}'
            {
            otherlv_0=(Token)match(input,94,FOLLOW_12); 

            			newLeafNode(otherlv_0, grammarAccess.getPortalGhostConfigAccess().getPortalGhostKeyword_0());
            		
            // InternalMazeDsl.g:3031:3: ( (lv_displayName_1_0= RULE_STRING ) )
            // InternalMazeDsl.g:3032:4: (lv_displayName_1_0= RULE_STRING )
            {
            // InternalMazeDsl.g:3032:4: (lv_displayName_1_0= RULE_STRING )
            // InternalMazeDsl.g:3033:5: lv_displayName_1_0= RULE_STRING
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

            otherlv_2=(Token)match(input,12,FOLLOW_85); 

            			newLeafNode(otherlv_2, grammarAccess.getPortalGhostConfigAccess().getLeftCurlyBracketKeyword_2());
            		
            otherlv_3=(Token)match(input,27,FOLLOW_37); 

            			newLeafNode(otherlv_3, grammarAccess.getPortalGhostConfigAccess().getHealthKeyword_3());
            		
            // InternalMazeDsl.g:3057:3: ( (lv_health_4_0= RULE_INT ) )
            // InternalMazeDsl.g:3058:4: (lv_health_4_0= RULE_INT )
            {
            // InternalMazeDsl.g:3058:4: (lv_health_4_0= RULE_INT )
            // InternalMazeDsl.g:3059:5: lv_health_4_0= RULE_INT
            {
            lv_health_4_0=(Token)match(input,RULE_INT,FOLLOW_105); 

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

            otherlv_5=(Token)match(input,28,FOLLOW_18); 

            			newLeafNode(otherlv_5, grammarAccess.getPortalGhostConfigAccess().getSpeedKeyword_5());
            		
            // InternalMazeDsl.g:3079:3: ( (lv_speed_6_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:3080:4: (lv_speed_6_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:3080:4: (lv_speed_6_0= ruleDOUBLE )
            // InternalMazeDsl.g:3081:5: lv_speed_6_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getPortalGhostConfigAccess().getSpeedDOUBLEParserRuleCall_6_0());
            				
            pushFollow(FOLLOW_91);
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

            otherlv_7=(Token)match(input,79,FOLLOW_12); 

            			newLeafNode(otherlv_7, grammarAccess.getPortalGhostConfigAccess().getSpriteKeyword_7());
            		
            // InternalMazeDsl.g:3102:3: ( (lv_sprite_8_0= RULE_STRING ) )
            // InternalMazeDsl.g:3103:4: (lv_sprite_8_0= RULE_STRING )
            {
            // InternalMazeDsl.g:3103:4: (lv_sprite_8_0= RULE_STRING )
            // InternalMazeDsl.g:3104:5: lv_sprite_8_0= RULE_STRING
            {
            lv_sprite_8_0=(Token)match(input,RULE_STRING,FOLLOW_11); 

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
    // InternalMazeDsl.g:3128:1: entryRuleGhostCountConfig returns [EObject current=null] : iv_ruleGhostCountConfig= ruleGhostCountConfig EOF ;
    public final EObject entryRuleGhostCountConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGhostCountConfig = null;


        try {
            // InternalMazeDsl.g:3128:57: (iv_ruleGhostCountConfig= ruleGhostCountConfig EOF )
            // InternalMazeDsl.g:3129:2: iv_ruleGhostCountConfig= ruleGhostCountConfig EOF
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
    // InternalMazeDsl.g:3135:1: ruleGhostCountConfig returns [EObject current=null] : (otherlv_0= 'portalGhosts' ( (lv_difficulty_1_0= ruleDifficultyLevel ) ) ( (lv_count_2_0= RULE_INT ) ) ) ;
    public final EObject ruleGhostCountConfig() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_count_2_0=null;
        Enumerator lv_difficulty_1_0 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:3141:2: ( (otherlv_0= 'portalGhosts' ( (lv_difficulty_1_0= ruleDifficultyLevel ) ) ( (lv_count_2_0= RULE_INT ) ) ) )
            // InternalMazeDsl.g:3142:2: (otherlv_0= 'portalGhosts' ( (lv_difficulty_1_0= ruleDifficultyLevel ) ) ( (lv_count_2_0= RULE_INT ) ) )
            {
            // InternalMazeDsl.g:3142:2: (otherlv_0= 'portalGhosts' ( (lv_difficulty_1_0= ruleDifficultyLevel ) ) ( (lv_count_2_0= RULE_INT ) ) )
            // InternalMazeDsl.g:3143:3: otherlv_0= 'portalGhosts' ( (lv_difficulty_1_0= ruleDifficultyLevel ) ) ( (lv_count_2_0= RULE_INT ) )
            {
            otherlv_0=(Token)match(input,95,FOLLOW_14); 

            			newLeafNode(otherlv_0, grammarAccess.getGhostCountConfigAccess().getPortalGhostsKeyword_0());
            		
            // InternalMazeDsl.g:3147:3: ( (lv_difficulty_1_0= ruleDifficultyLevel ) )
            // InternalMazeDsl.g:3148:4: (lv_difficulty_1_0= ruleDifficultyLevel )
            {
            // InternalMazeDsl.g:3148:4: (lv_difficulty_1_0= ruleDifficultyLevel )
            // InternalMazeDsl.g:3149:5: lv_difficulty_1_0= ruleDifficultyLevel
            {

            					newCompositeNode(grammarAccess.getGhostCountConfigAccess().getDifficultyDifficultyLevelEnumRuleCall_1_0());
            				
            pushFollow(FOLLOW_37);
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

            // InternalMazeDsl.g:3166:3: ( (lv_count_2_0= RULE_INT ) )
            // InternalMazeDsl.g:3167:4: (lv_count_2_0= RULE_INT )
            {
            // InternalMazeDsl.g:3167:4: (lv_count_2_0= RULE_INT )
            // InternalMazeDsl.g:3168:5: lv_count_2_0= RULE_INT
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
    // InternalMazeDsl.g:3188:1: entryRuleGuardTowerConfig returns [EObject current=null] : iv_ruleGuardTowerConfig= ruleGuardTowerConfig EOF ;
    public final EObject entryRuleGuardTowerConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGuardTowerConfig = null;


        try {
            // InternalMazeDsl.g:3188:57: (iv_ruleGuardTowerConfig= ruleGuardTowerConfig EOF )
            // InternalMazeDsl.g:3189:2: iv_ruleGuardTowerConfig= ruleGuardTowerConfig EOF
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
    // InternalMazeDsl.g:3195:1: ruleGuardTowerConfig returns [EObject current=null] : (otherlv_0= 'guardTower' ( (lv_displayName_1_0= RULE_STRING ) ) otherlv_2= '{' otherlv_3= 'health' ( (lv_health_4_0= RULE_INT ) ) otherlv_5= 'sightRange' ( (lv_sightRange_6_0= ruleDOUBLE ) ) otherlv_7= 'wallsOver' ( (lv_maxWallsOver_8_0= RULE_INT ) ) otherlv_9= 'rpgInterval' ( (lv_rpgIntervalSeconds_10_0= ruleDOUBLE ) ) otherlv_11= 'rpgSpeed' ( (lv_rpgSpeed_12_0= ruleDOUBLE ) ) otherlv_13= 'rpgBlastRadius' ( (lv_rpgBlastRadius_14_0= ruleDOUBLE ) ) otherlv_15= 'rpgPlayerDamage' ( (lv_rpgPlayerDamage_16_0= RULE_INT ) ) otherlv_17= 'rpgWallDamage' ( (lv_rpgWallDamage_18_0= RULE_INT ) ) otherlv_19= 'gatlingRounds' ( (lv_gatlingRounds_20_0= RULE_INT ) ) otherlv_21= 'gatlingInterval' ( (lv_gatlingRoundIntervalSeconds_22_0= ruleDOUBLE ) ) otherlv_23= 'gatlingDamage' ( (lv_gatlingDamage_24_0= RULE_INT ) ) otherlv_25= 'deathBlast' ( (lv_deathBlastDamage_26_0= RULE_INT ) ) otherlv_27= 'sprite' ( (lv_sprite_28_0= RULE_STRING ) ) (otherlv_29= 'rpgLaunchSound' ( (lv_rpgLaunchSound_30_0= RULE_STRING ) ) )? (otherlv_31= 'rpgExplosionSound' ( (lv_rpgExplosionSound_32_0= RULE_STRING ) ) )? (otherlv_33= 'gatlingSound' ( (lv_gatlingSound_34_0= RULE_STRING ) ) )? (otherlv_35= 'hitSound' ( (lv_hitSound_36_0= RULE_STRING ) ) )? (otherlv_37= 'destroyedSound' ( (lv_destroyedSound_38_0= RULE_STRING ) ) )? otherlv_39= '}' ) ;
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
            // InternalMazeDsl.g:3201:2: ( (otherlv_0= 'guardTower' ( (lv_displayName_1_0= RULE_STRING ) ) otherlv_2= '{' otherlv_3= 'health' ( (lv_health_4_0= RULE_INT ) ) otherlv_5= 'sightRange' ( (lv_sightRange_6_0= ruleDOUBLE ) ) otherlv_7= 'wallsOver' ( (lv_maxWallsOver_8_0= RULE_INT ) ) otherlv_9= 'rpgInterval' ( (lv_rpgIntervalSeconds_10_0= ruleDOUBLE ) ) otherlv_11= 'rpgSpeed' ( (lv_rpgSpeed_12_0= ruleDOUBLE ) ) otherlv_13= 'rpgBlastRadius' ( (lv_rpgBlastRadius_14_0= ruleDOUBLE ) ) otherlv_15= 'rpgPlayerDamage' ( (lv_rpgPlayerDamage_16_0= RULE_INT ) ) otherlv_17= 'rpgWallDamage' ( (lv_rpgWallDamage_18_0= RULE_INT ) ) otherlv_19= 'gatlingRounds' ( (lv_gatlingRounds_20_0= RULE_INT ) ) otherlv_21= 'gatlingInterval' ( (lv_gatlingRoundIntervalSeconds_22_0= ruleDOUBLE ) ) otherlv_23= 'gatlingDamage' ( (lv_gatlingDamage_24_0= RULE_INT ) ) otherlv_25= 'deathBlast' ( (lv_deathBlastDamage_26_0= RULE_INT ) ) otherlv_27= 'sprite' ( (lv_sprite_28_0= RULE_STRING ) ) (otherlv_29= 'rpgLaunchSound' ( (lv_rpgLaunchSound_30_0= RULE_STRING ) ) )? (otherlv_31= 'rpgExplosionSound' ( (lv_rpgExplosionSound_32_0= RULE_STRING ) ) )? (otherlv_33= 'gatlingSound' ( (lv_gatlingSound_34_0= RULE_STRING ) ) )? (otherlv_35= 'hitSound' ( (lv_hitSound_36_0= RULE_STRING ) ) )? (otherlv_37= 'destroyedSound' ( (lv_destroyedSound_38_0= RULE_STRING ) ) )? otherlv_39= '}' ) )
            // InternalMazeDsl.g:3202:2: (otherlv_0= 'guardTower' ( (lv_displayName_1_0= RULE_STRING ) ) otherlv_2= '{' otherlv_3= 'health' ( (lv_health_4_0= RULE_INT ) ) otherlv_5= 'sightRange' ( (lv_sightRange_6_0= ruleDOUBLE ) ) otherlv_7= 'wallsOver' ( (lv_maxWallsOver_8_0= RULE_INT ) ) otherlv_9= 'rpgInterval' ( (lv_rpgIntervalSeconds_10_0= ruleDOUBLE ) ) otherlv_11= 'rpgSpeed' ( (lv_rpgSpeed_12_0= ruleDOUBLE ) ) otherlv_13= 'rpgBlastRadius' ( (lv_rpgBlastRadius_14_0= ruleDOUBLE ) ) otherlv_15= 'rpgPlayerDamage' ( (lv_rpgPlayerDamage_16_0= RULE_INT ) ) otherlv_17= 'rpgWallDamage' ( (lv_rpgWallDamage_18_0= RULE_INT ) ) otherlv_19= 'gatlingRounds' ( (lv_gatlingRounds_20_0= RULE_INT ) ) otherlv_21= 'gatlingInterval' ( (lv_gatlingRoundIntervalSeconds_22_0= ruleDOUBLE ) ) otherlv_23= 'gatlingDamage' ( (lv_gatlingDamage_24_0= RULE_INT ) ) otherlv_25= 'deathBlast' ( (lv_deathBlastDamage_26_0= RULE_INT ) ) otherlv_27= 'sprite' ( (lv_sprite_28_0= RULE_STRING ) ) (otherlv_29= 'rpgLaunchSound' ( (lv_rpgLaunchSound_30_0= RULE_STRING ) ) )? (otherlv_31= 'rpgExplosionSound' ( (lv_rpgExplosionSound_32_0= RULE_STRING ) ) )? (otherlv_33= 'gatlingSound' ( (lv_gatlingSound_34_0= RULE_STRING ) ) )? (otherlv_35= 'hitSound' ( (lv_hitSound_36_0= RULE_STRING ) ) )? (otherlv_37= 'destroyedSound' ( (lv_destroyedSound_38_0= RULE_STRING ) ) )? otherlv_39= '}' )
            {
            // InternalMazeDsl.g:3202:2: (otherlv_0= 'guardTower' ( (lv_displayName_1_0= RULE_STRING ) ) otherlv_2= '{' otherlv_3= 'health' ( (lv_health_4_0= RULE_INT ) ) otherlv_5= 'sightRange' ( (lv_sightRange_6_0= ruleDOUBLE ) ) otherlv_7= 'wallsOver' ( (lv_maxWallsOver_8_0= RULE_INT ) ) otherlv_9= 'rpgInterval' ( (lv_rpgIntervalSeconds_10_0= ruleDOUBLE ) ) otherlv_11= 'rpgSpeed' ( (lv_rpgSpeed_12_0= ruleDOUBLE ) ) otherlv_13= 'rpgBlastRadius' ( (lv_rpgBlastRadius_14_0= ruleDOUBLE ) ) otherlv_15= 'rpgPlayerDamage' ( (lv_rpgPlayerDamage_16_0= RULE_INT ) ) otherlv_17= 'rpgWallDamage' ( (lv_rpgWallDamage_18_0= RULE_INT ) ) otherlv_19= 'gatlingRounds' ( (lv_gatlingRounds_20_0= RULE_INT ) ) otherlv_21= 'gatlingInterval' ( (lv_gatlingRoundIntervalSeconds_22_0= ruleDOUBLE ) ) otherlv_23= 'gatlingDamage' ( (lv_gatlingDamage_24_0= RULE_INT ) ) otherlv_25= 'deathBlast' ( (lv_deathBlastDamage_26_0= RULE_INT ) ) otherlv_27= 'sprite' ( (lv_sprite_28_0= RULE_STRING ) ) (otherlv_29= 'rpgLaunchSound' ( (lv_rpgLaunchSound_30_0= RULE_STRING ) ) )? (otherlv_31= 'rpgExplosionSound' ( (lv_rpgExplosionSound_32_0= RULE_STRING ) ) )? (otherlv_33= 'gatlingSound' ( (lv_gatlingSound_34_0= RULE_STRING ) ) )? (otherlv_35= 'hitSound' ( (lv_hitSound_36_0= RULE_STRING ) ) )? (otherlv_37= 'destroyedSound' ( (lv_destroyedSound_38_0= RULE_STRING ) ) )? otherlv_39= '}' )
            // InternalMazeDsl.g:3203:3: otherlv_0= 'guardTower' ( (lv_displayName_1_0= RULE_STRING ) ) otherlv_2= '{' otherlv_3= 'health' ( (lv_health_4_0= RULE_INT ) ) otherlv_5= 'sightRange' ( (lv_sightRange_6_0= ruleDOUBLE ) ) otherlv_7= 'wallsOver' ( (lv_maxWallsOver_8_0= RULE_INT ) ) otherlv_9= 'rpgInterval' ( (lv_rpgIntervalSeconds_10_0= ruleDOUBLE ) ) otherlv_11= 'rpgSpeed' ( (lv_rpgSpeed_12_0= ruleDOUBLE ) ) otherlv_13= 'rpgBlastRadius' ( (lv_rpgBlastRadius_14_0= ruleDOUBLE ) ) otherlv_15= 'rpgPlayerDamage' ( (lv_rpgPlayerDamage_16_0= RULE_INT ) ) otherlv_17= 'rpgWallDamage' ( (lv_rpgWallDamage_18_0= RULE_INT ) ) otherlv_19= 'gatlingRounds' ( (lv_gatlingRounds_20_0= RULE_INT ) ) otherlv_21= 'gatlingInterval' ( (lv_gatlingRoundIntervalSeconds_22_0= ruleDOUBLE ) ) otherlv_23= 'gatlingDamage' ( (lv_gatlingDamage_24_0= RULE_INT ) ) otherlv_25= 'deathBlast' ( (lv_deathBlastDamage_26_0= RULE_INT ) ) otherlv_27= 'sprite' ( (lv_sprite_28_0= RULE_STRING ) ) (otherlv_29= 'rpgLaunchSound' ( (lv_rpgLaunchSound_30_0= RULE_STRING ) ) )? (otherlv_31= 'rpgExplosionSound' ( (lv_rpgExplosionSound_32_0= RULE_STRING ) ) )? (otherlv_33= 'gatlingSound' ( (lv_gatlingSound_34_0= RULE_STRING ) ) )? (otherlv_35= 'hitSound' ( (lv_hitSound_36_0= RULE_STRING ) ) )? (otherlv_37= 'destroyedSound' ( (lv_destroyedSound_38_0= RULE_STRING ) ) )? otherlv_39= '}'
            {
            otherlv_0=(Token)match(input,96,FOLLOW_12); 

            			newLeafNode(otherlv_0, grammarAccess.getGuardTowerConfigAccess().getGuardTowerKeyword_0());
            		
            // InternalMazeDsl.g:3207:3: ( (lv_displayName_1_0= RULE_STRING ) )
            // InternalMazeDsl.g:3208:4: (lv_displayName_1_0= RULE_STRING )
            {
            // InternalMazeDsl.g:3208:4: (lv_displayName_1_0= RULE_STRING )
            // InternalMazeDsl.g:3209:5: lv_displayName_1_0= RULE_STRING
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

            otherlv_2=(Token)match(input,12,FOLLOW_85); 

            			newLeafNode(otherlv_2, grammarAccess.getGuardTowerConfigAccess().getLeftCurlyBracketKeyword_2());
            		
            otherlv_3=(Token)match(input,27,FOLLOW_37); 

            			newLeafNode(otherlv_3, grammarAccess.getGuardTowerConfigAccess().getHealthKeyword_3());
            		
            // InternalMazeDsl.g:3233:3: ( (lv_health_4_0= RULE_INT ) )
            // InternalMazeDsl.g:3234:4: (lv_health_4_0= RULE_INT )
            {
            // InternalMazeDsl.g:3234:4: (lv_health_4_0= RULE_INT )
            // InternalMazeDsl.g:3235:5: lv_health_4_0= RULE_INT
            {
            lv_health_4_0=(Token)match(input,RULE_INT,FOLLOW_86); 

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

            otherlv_5=(Token)match(input,74,FOLLOW_18); 

            			newLeafNode(otherlv_5, grammarAccess.getGuardTowerConfigAccess().getSightRangeKeyword_5());
            		
            // InternalMazeDsl.g:3255:3: ( (lv_sightRange_6_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:3256:4: (lv_sightRange_6_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:3256:4: (lv_sightRange_6_0= ruleDOUBLE )
            // InternalMazeDsl.g:3257:5: lv_sightRange_6_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getGuardTowerConfigAccess().getSightRangeDOUBLEParserRuleCall_6_0());
            				
            pushFollow(FOLLOW_106);
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

            otherlv_7=(Token)match(input,97,FOLLOW_37); 

            			newLeafNode(otherlv_7, grammarAccess.getGuardTowerConfigAccess().getWallsOverKeyword_7());
            		
            // InternalMazeDsl.g:3278:3: ( (lv_maxWallsOver_8_0= RULE_INT ) )
            // InternalMazeDsl.g:3279:4: (lv_maxWallsOver_8_0= RULE_INT )
            {
            // InternalMazeDsl.g:3279:4: (lv_maxWallsOver_8_0= RULE_INT )
            // InternalMazeDsl.g:3280:5: lv_maxWallsOver_8_0= RULE_INT
            {
            lv_maxWallsOver_8_0=(Token)match(input,RULE_INT,FOLLOW_107); 

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

            otherlv_9=(Token)match(input,98,FOLLOW_18); 

            			newLeafNode(otherlv_9, grammarAccess.getGuardTowerConfigAccess().getRpgIntervalKeyword_9());
            		
            // InternalMazeDsl.g:3300:3: ( (lv_rpgIntervalSeconds_10_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:3301:4: (lv_rpgIntervalSeconds_10_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:3301:4: (lv_rpgIntervalSeconds_10_0= ruleDOUBLE )
            // InternalMazeDsl.g:3302:5: lv_rpgIntervalSeconds_10_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getGuardTowerConfigAccess().getRpgIntervalSecondsDOUBLEParserRuleCall_10_0());
            				
            pushFollow(FOLLOW_108);
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

            otherlv_11=(Token)match(input,99,FOLLOW_18); 

            			newLeafNode(otherlv_11, grammarAccess.getGuardTowerConfigAccess().getRpgSpeedKeyword_11());
            		
            // InternalMazeDsl.g:3323:3: ( (lv_rpgSpeed_12_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:3324:4: (lv_rpgSpeed_12_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:3324:4: (lv_rpgSpeed_12_0= ruleDOUBLE )
            // InternalMazeDsl.g:3325:5: lv_rpgSpeed_12_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getGuardTowerConfigAccess().getRpgSpeedDOUBLEParserRuleCall_12_0());
            				
            pushFollow(FOLLOW_109);
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

            otherlv_13=(Token)match(input,100,FOLLOW_18); 

            			newLeafNode(otherlv_13, grammarAccess.getGuardTowerConfigAccess().getRpgBlastRadiusKeyword_13());
            		
            // InternalMazeDsl.g:3346:3: ( (lv_rpgBlastRadius_14_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:3347:4: (lv_rpgBlastRadius_14_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:3347:4: (lv_rpgBlastRadius_14_0= ruleDOUBLE )
            // InternalMazeDsl.g:3348:5: lv_rpgBlastRadius_14_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getGuardTowerConfigAccess().getRpgBlastRadiusDOUBLEParserRuleCall_14_0());
            				
            pushFollow(FOLLOW_110);
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

            otherlv_15=(Token)match(input,101,FOLLOW_37); 

            			newLeafNode(otherlv_15, grammarAccess.getGuardTowerConfigAccess().getRpgPlayerDamageKeyword_15());
            		
            // InternalMazeDsl.g:3369:3: ( (lv_rpgPlayerDamage_16_0= RULE_INT ) )
            // InternalMazeDsl.g:3370:4: (lv_rpgPlayerDamage_16_0= RULE_INT )
            {
            // InternalMazeDsl.g:3370:4: (lv_rpgPlayerDamage_16_0= RULE_INT )
            // InternalMazeDsl.g:3371:5: lv_rpgPlayerDamage_16_0= RULE_INT
            {
            lv_rpgPlayerDamage_16_0=(Token)match(input,RULE_INT,FOLLOW_111); 

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

            otherlv_17=(Token)match(input,102,FOLLOW_37); 

            			newLeafNode(otherlv_17, grammarAccess.getGuardTowerConfigAccess().getRpgWallDamageKeyword_17());
            		
            // InternalMazeDsl.g:3391:3: ( (lv_rpgWallDamage_18_0= RULE_INT ) )
            // InternalMazeDsl.g:3392:4: (lv_rpgWallDamage_18_0= RULE_INT )
            {
            // InternalMazeDsl.g:3392:4: (lv_rpgWallDamage_18_0= RULE_INT )
            // InternalMazeDsl.g:3393:5: lv_rpgWallDamage_18_0= RULE_INT
            {
            lv_rpgWallDamage_18_0=(Token)match(input,RULE_INT,FOLLOW_112); 

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

            otherlv_19=(Token)match(input,103,FOLLOW_37); 

            			newLeafNode(otherlv_19, grammarAccess.getGuardTowerConfigAccess().getGatlingRoundsKeyword_19());
            		
            // InternalMazeDsl.g:3413:3: ( (lv_gatlingRounds_20_0= RULE_INT ) )
            // InternalMazeDsl.g:3414:4: (lv_gatlingRounds_20_0= RULE_INT )
            {
            // InternalMazeDsl.g:3414:4: (lv_gatlingRounds_20_0= RULE_INT )
            // InternalMazeDsl.g:3415:5: lv_gatlingRounds_20_0= RULE_INT
            {
            lv_gatlingRounds_20_0=(Token)match(input,RULE_INT,FOLLOW_113); 

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

            otherlv_21=(Token)match(input,104,FOLLOW_18); 

            			newLeafNode(otherlv_21, grammarAccess.getGuardTowerConfigAccess().getGatlingIntervalKeyword_21());
            		
            // InternalMazeDsl.g:3435:3: ( (lv_gatlingRoundIntervalSeconds_22_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:3436:4: (lv_gatlingRoundIntervalSeconds_22_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:3436:4: (lv_gatlingRoundIntervalSeconds_22_0= ruleDOUBLE )
            // InternalMazeDsl.g:3437:5: lv_gatlingRoundIntervalSeconds_22_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getGuardTowerConfigAccess().getGatlingRoundIntervalSecondsDOUBLEParserRuleCall_22_0());
            				
            pushFollow(FOLLOW_114);
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

            otherlv_23=(Token)match(input,105,FOLLOW_37); 

            			newLeafNode(otherlv_23, grammarAccess.getGuardTowerConfigAccess().getGatlingDamageKeyword_23());
            		
            // InternalMazeDsl.g:3458:3: ( (lv_gatlingDamage_24_0= RULE_INT ) )
            // InternalMazeDsl.g:3459:4: (lv_gatlingDamage_24_0= RULE_INT )
            {
            // InternalMazeDsl.g:3459:4: (lv_gatlingDamage_24_0= RULE_INT )
            // InternalMazeDsl.g:3460:5: lv_gatlingDamage_24_0= RULE_INT
            {
            lv_gatlingDamage_24_0=(Token)match(input,RULE_INT,FOLLOW_115); 

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

            otherlv_25=(Token)match(input,106,FOLLOW_37); 

            			newLeafNode(otherlv_25, grammarAccess.getGuardTowerConfigAccess().getDeathBlastKeyword_25());
            		
            // InternalMazeDsl.g:3480:3: ( (lv_deathBlastDamage_26_0= RULE_INT ) )
            // InternalMazeDsl.g:3481:4: (lv_deathBlastDamage_26_0= RULE_INT )
            {
            // InternalMazeDsl.g:3481:4: (lv_deathBlastDamage_26_0= RULE_INT )
            // InternalMazeDsl.g:3482:5: lv_deathBlastDamage_26_0= RULE_INT
            {
            lv_deathBlastDamage_26_0=(Token)match(input,RULE_INT,FOLLOW_91); 

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

            otherlv_27=(Token)match(input,79,FOLLOW_12); 

            			newLeafNode(otherlv_27, grammarAccess.getGuardTowerConfigAccess().getSpriteKeyword_27());
            		
            // InternalMazeDsl.g:3502:3: ( (lv_sprite_28_0= RULE_STRING ) )
            // InternalMazeDsl.g:3503:4: (lv_sprite_28_0= RULE_STRING )
            {
            // InternalMazeDsl.g:3503:4: (lv_sprite_28_0= RULE_STRING )
            // InternalMazeDsl.g:3504:5: lv_sprite_28_0= RULE_STRING
            {
            lv_sprite_28_0=(Token)match(input,RULE_STRING,FOLLOW_116); 

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

            // InternalMazeDsl.g:3520:3: (otherlv_29= 'rpgLaunchSound' ( (lv_rpgLaunchSound_30_0= RULE_STRING ) ) )?
            int alt66=2;
            int LA66_0 = input.LA(1);

            if ( (LA66_0==107) ) {
                alt66=1;
            }
            switch (alt66) {
                case 1 :
                    // InternalMazeDsl.g:3521:4: otherlv_29= 'rpgLaunchSound' ( (lv_rpgLaunchSound_30_0= RULE_STRING ) )
                    {
                    otherlv_29=(Token)match(input,107,FOLLOW_12); 

                    				newLeafNode(otherlv_29, grammarAccess.getGuardTowerConfigAccess().getRpgLaunchSoundKeyword_29_0());
                    			
                    // InternalMazeDsl.g:3525:4: ( (lv_rpgLaunchSound_30_0= RULE_STRING ) )
                    // InternalMazeDsl.g:3526:5: (lv_rpgLaunchSound_30_0= RULE_STRING )
                    {
                    // InternalMazeDsl.g:3526:5: (lv_rpgLaunchSound_30_0= RULE_STRING )
                    // InternalMazeDsl.g:3527:6: lv_rpgLaunchSound_30_0= RULE_STRING
                    {
                    lv_rpgLaunchSound_30_0=(Token)match(input,RULE_STRING,FOLLOW_117); 

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

            // InternalMazeDsl.g:3544:3: (otherlv_31= 'rpgExplosionSound' ( (lv_rpgExplosionSound_32_0= RULE_STRING ) ) )?
            int alt67=2;
            int LA67_0 = input.LA(1);

            if ( (LA67_0==108) ) {
                alt67=1;
            }
            switch (alt67) {
                case 1 :
                    // InternalMazeDsl.g:3545:4: otherlv_31= 'rpgExplosionSound' ( (lv_rpgExplosionSound_32_0= RULE_STRING ) )
                    {
                    otherlv_31=(Token)match(input,108,FOLLOW_12); 

                    				newLeafNode(otherlv_31, grammarAccess.getGuardTowerConfigAccess().getRpgExplosionSoundKeyword_30_0());
                    			
                    // InternalMazeDsl.g:3549:4: ( (lv_rpgExplosionSound_32_0= RULE_STRING ) )
                    // InternalMazeDsl.g:3550:5: (lv_rpgExplosionSound_32_0= RULE_STRING )
                    {
                    // InternalMazeDsl.g:3550:5: (lv_rpgExplosionSound_32_0= RULE_STRING )
                    // InternalMazeDsl.g:3551:6: lv_rpgExplosionSound_32_0= RULE_STRING
                    {
                    lv_rpgExplosionSound_32_0=(Token)match(input,RULE_STRING,FOLLOW_118); 

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

            // InternalMazeDsl.g:3568:3: (otherlv_33= 'gatlingSound' ( (lv_gatlingSound_34_0= RULE_STRING ) ) )?
            int alt68=2;
            int LA68_0 = input.LA(1);

            if ( (LA68_0==109) ) {
                alt68=1;
            }
            switch (alt68) {
                case 1 :
                    // InternalMazeDsl.g:3569:4: otherlv_33= 'gatlingSound' ( (lv_gatlingSound_34_0= RULE_STRING ) )
                    {
                    otherlv_33=(Token)match(input,109,FOLLOW_12); 

                    				newLeafNode(otherlv_33, grammarAccess.getGuardTowerConfigAccess().getGatlingSoundKeyword_31_0());
                    			
                    // InternalMazeDsl.g:3573:4: ( (lv_gatlingSound_34_0= RULE_STRING ) )
                    // InternalMazeDsl.g:3574:5: (lv_gatlingSound_34_0= RULE_STRING )
                    {
                    // InternalMazeDsl.g:3574:5: (lv_gatlingSound_34_0= RULE_STRING )
                    // InternalMazeDsl.g:3575:6: lv_gatlingSound_34_0= RULE_STRING
                    {
                    lv_gatlingSound_34_0=(Token)match(input,RULE_STRING,FOLLOW_119); 

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

            // InternalMazeDsl.g:3592:3: (otherlv_35= 'hitSound' ( (lv_hitSound_36_0= RULE_STRING ) ) )?
            int alt69=2;
            int LA69_0 = input.LA(1);

            if ( (LA69_0==110) ) {
                alt69=1;
            }
            switch (alt69) {
                case 1 :
                    // InternalMazeDsl.g:3593:4: otherlv_35= 'hitSound' ( (lv_hitSound_36_0= RULE_STRING ) )
                    {
                    otherlv_35=(Token)match(input,110,FOLLOW_12); 

                    				newLeafNode(otherlv_35, grammarAccess.getGuardTowerConfigAccess().getHitSoundKeyword_32_0());
                    			
                    // InternalMazeDsl.g:3597:4: ( (lv_hitSound_36_0= RULE_STRING ) )
                    // InternalMazeDsl.g:3598:5: (lv_hitSound_36_0= RULE_STRING )
                    {
                    // InternalMazeDsl.g:3598:5: (lv_hitSound_36_0= RULE_STRING )
                    // InternalMazeDsl.g:3599:6: lv_hitSound_36_0= RULE_STRING
                    {
                    lv_hitSound_36_0=(Token)match(input,RULE_STRING,FOLLOW_120); 

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

            // InternalMazeDsl.g:3616:3: (otherlv_37= 'destroyedSound' ( (lv_destroyedSound_38_0= RULE_STRING ) ) )?
            int alt70=2;
            int LA70_0 = input.LA(1);

            if ( (LA70_0==111) ) {
                alt70=1;
            }
            switch (alt70) {
                case 1 :
                    // InternalMazeDsl.g:3617:4: otherlv_37= 'destroyedSound' ( (lv_destroyedSound_38_0= RULE_STRING ) )
                    {
                    otherlv_37=(Token)match(input,111,FOLLOW_12); 

                    				newLeafNode(otherlv_37, grammarAccess.getGuardTowerConfigAccess().getDestroyedSoundKeyword_33_0());
                    			
                    // InternalMazeDsl.g:3621:4: ( (lv_destroyedSound_38_0= RULE_STRING ) )
                    // InternalMazeDsl.g:3622:5: (lv_destroyedSound_38_0= RULE_STRING )
                    {
                    // InternalMazeDsl.g:3622:5: (lv_destroyedSound_38_0= RULE_STRING )
                    // InternalMazeDsl.g:3623:6: lv_destroyedSound_38_0= RULE_STRING
                    {
                    lv_destroyedSound_38_0=(Token)match(input,RULE_STRING,FOLLOW_11); 

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
    // InternalMazeDsl.g:3648:1: entryRuleTowerCountConfig returns [EObject current=null] : iv_ruleTowerCountConfig= ruleTowerCountConfig EOF ;
    public final EObject entryRuleTowerCountConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTowerCountConfig = null;


        try {
            // InternalMazeDsl.g:3648:57: (iv_ruleTowerCountConfig= ruleTowerCountConfig EOF )
            // InternalMazeDsl.g:3649:2: iv_ruleTowerCountConfig= ruleTowerCountConfig EOF
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
    // InternalMazeDsl.g:3655:1: ruleTowerCountConfig returns [EObject current=null] : (otherlv_0= 'towers' ( (lv_difficulty_1_0= ruleDifficultyLevel ) ) ( (lv_count_2_0= RULE_INT ) ) ) ;
    public final EObject ruleTowerCountConfig() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_count_2_0=null;
        Enumerator lv_difficulty_1_0 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:3661:2: ( (otherlv_0= 'towers' ( (lv_difficulty_1_0= ruleDifficultyLevel ) ) ( (lv_count_2_0= RULE_INT ) ) ) )
            // InternalMazeDsl.g:3662:2: (otherlv_0= 'towers' ( (lv_difficulty_1_0= ruleDifficultyLevel ) ) ( (lv_count_2_0= RULE_INT ) ) )
            {
            // InternalMazeDsl.g:3662:2: (otherlv_0= 'towers' ( (lv_difficulty_1_0= ruleDifficultyLevel ) ) ( (lv_count_2_0= RULE_INT ) ) )
            // InternalMazeDsl.g:3663:3: otherlv_0= 'towers' ( (lv_difficulty_1_0= ruleDifficultyLevel ) ) ( (lv_count_2_0= RULE_INT ) )
            {
            otherlv_0=(Token)match(input,112,FOLLOW_14); 

            			newLeafNode(otherlv_0, grammarAccess.getTowerCountConfigAccess().getTowersKeyword_0());
            		
            // InternalMazeDsl.g:3667:3: ( (lv_difficulty_1_0= ruleDifficultyLevel ) )
            // InternalMazeDsl.g:3668:4: (lv_difficulty_1_0= ruleDifficultyLevel )
            {
            // InternalMazeDsl.g:3668:4: (lv_difficulty_1_0= ruleDifficultyLevel )
            // InternalMazeDsl.g:3669:5: lv_difficulty_1_0= ruleDifficultyLevel
            {

            					newCompositeNode(grammarAccess.getTowerCountConfigAccess().getDifficultyDifficultyLevelEnumRuleCall_1_0());
            				
            pushFollow(FOLLOW_37);
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

            // InternalMazeDsl.g:3686:3: ( (lv_count_2_0= RULE_INT ) )
            // InternalMazeDsl.g:3687:4: (lv_count_2_0= RULE_INT )
            {
            // InternalMazeDsl.g:3687:4: (lv_count_2_0= RULE_INT )
            // InternalMazeDsl.g:3688:5: lv_count_2_0= RULE_INT
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
    // InternalMazeDsl.g:3708:1: entryRuleWallBuilderConfig returns [EObject current=null] : iv_ruleWallBuilderConfig= ruleWallBuilderConfig EOF ;
    public final EObject entryRuleWallBuilderConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleWallBuilderConfig = null;


        try {
            // InternalMazeDsl.g:3708:58: (iv_ruleWallBuilderConfig= ruleWallBuilderConfig EOF )
            // InternalMazeDsl.g:3709:2: iv_ruleWallBuilderConfig= ruleWallBuilderConfig EOF
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
    // InternalMazeDsl.g:3715:1: ruleWallBuilderConfig returns [EObject current=null] : (otherlv_0= 'wallBuilder' ( (lv_displayName_1_0= RULE_STRING ) ) otherlv_2= '{' otherlv_3= 'health' ( (lv_health_4_0= RULE_INT ) ) otherlv_5= 'speed' ( (lv_speed_6_0= ruleDOUBLE ) ) otherlv_7= 'sprite' ( (lv_sprite_8_0= RULE_STRING ) ) otherlv_9= 'buildSeconds' ( (lv_buildSecondsPerCorridor_10_0= ruleDOUBLE ) ) otherlv_11= 'startRadius' ( (lv_startRadiusCells_12_0= RULE_INT ) ) otherlv_13= 'blockRadius' ( (lv_blockRadiusCells_14_0= RULE_INT ) ) otherlv_15= 'spawnRadius' ( (lv_spawnRadius_16_0= ruleDOUBLE ) ) otherlv_17= 'material' ( (lv_wallMaterial_18_0= ruleValidID ) ) (otherlv_19= 'buildSound' ( (lv_buildSound_20_0= RULE_STRING ) ) )? otherlv_21= '}' ) ;
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
            // InternalMazeDsl.g:3721:2: ( (otherlv_0= 'wallBuilder' ( (lv_displayName_1_0= RULE_STRING ) ) otherlv_2= '{' otherlv_3= 'health' ( (lv_health_4_0= RULE_INT ) ) otherlv_5= 'speed' ( (lv_speed_6_0= ruleDOUBLE ) ) otherlv_7= 'sprite' ( (lv_sprite_8_0= RULE_STRING ) ) otherlv_9= 'buildSeconds' ( (lv_buildSecondsPerCorridor_10_0= ruleDOUBLE ) ) otherlv_11= 'startRadius' ( (lv_startRadiusCells_12_0= RULE_INT ) ) otherlv_13= 'blockRadius' ( (lv_blockRadiusCells_14_0= RULE_INT ) ) otherlv_15= 'spawnRadius' ( (lv_spawnRadius_16_0= ruleDOUBLE ) ) otherlv_17= 'material' ( (lv_wallMaterial_18_0= ruleValidID ) ) (otherlv_19= 'buildSound' ( (lv_buildSound_20_0= RULE_STRING ) ) )? otherlv_21= '}' ) )
            // InternalMazeDsl.g:3722:2: (otherlv_0= 'wallBuilder' ( (lv_displayName_1_0= RULE_STRING ) ) otherlv_2= '{' otherlv_3= 'health' ( (lv_health_4_0= RULE_INT ) ) otherlv_5= 'speed' ( (lv_speed_6_0= ruleDOUBLE ) ) otherlv_7= 'sprite' ( (lv_sprite_8_0= RULE_STRING ) ) otherlv_9= 'buildSeconds' ( (lv_buildSecondsPerCorridor_10_0= ruleDOUBLE ) ) otherlv_11= 'startRadius' ( (lv_startRadiusCells_12_0= RULE_INT ) ) otherlv_13= 'blockRadius' ( (lv_blockRadiusCells_14_0= RULE_INT ) ) otherlv_15= 'spawnRadius' ( (lv_spawnRadius_16_0= ruleDOUBLE ) ) otherlv_17= 'material' ( (lv_wallMaterial_18_0= ruleValidID ) ) (otherlv_19= 'buildSound' ( (lv_buildSound_20_0= RULE_STRING ) ) )? otherlv_21= '}' )
            {
            // InternalMazeDsl.g:3722:2: (otherlv_0= 'wallBuilder' ( (lv_displayName_1_0= RULE_STRING ) ) otherlv_2= '{' otherlv_3= 'health' ( (lv_health_4_0= RULE_INT ) ) otherlv_5= 'speed' ( (lv_speed_6_0= ruleDOUBLE ) ) otherlv_7= 'sprite' ( (lv_sprite_8_0= RULE_STRING ) ) otherlv_9= 'buildSeconds' ( (lv_buildSecondsPerCorridor_10_0= ruleDOUBLE ) ) otherlv_11= 'startRadius' ( (lv_startRadiusCells_12_0= RULE_INT ) ) otherlv_13= 'blockRadius' ( (lv_blockRadiusCells_14_0= RULE_INT ) ) otherlv_15= 'spawnRadius' ( (lv_spawnRadius_16_0= ruleDOUBLE ) ) otherlv_17= 'material' ( (lv_wallMaterial_18_0= ruleValidID ) ) (otherlv_19= 'buildSound' ( (lv_buildSound_20_0= RULE_STRING ) ) )? otherlv_21= '}' )
            // InternalMazeDsl.g:3723:3: otherlv_0= 'wallBuilder' ( (lv_displayName_1_0= RULE_STRING ) ) otherlv_2= '{' otherlv_3= 'health' ( (lv_health_4_0= RULE_INT ) ) otherlv_5= 'speed' ( (lv_speed_6_0= ruleDOUBLE ) ) otherlv_7= 'sprite' ( (lv_sprite_8_0= RULE_STRING ) ) otherlv_9= 'buildSeconds' ( (lv_buildSecondsPerCorridor_10_0= ruleDOUBLE ) ) otherlv_11= 'startRadius' ( (lv_startRadiusCells_12_0= RULE_INT ) ) otherlv_13= 'blockRadius' ( (lv_blockRadiusCells_14_0= RULE_INT ) ) otherlv_15= 'spawnRadius' ( (lv_spawnRadius_16_0= ruleDOUBLE ) ) otherlv_17= 'material' ( (lv_wallMaterial_18_0= ruleValidID ) ) (otherlv_19= 'buildSound' ( (lv_buildSound_20_0= RULE_STRING ) ) )? otherlv_21= '}'
            {
            otherlv_0=(Token)match(input,113,FOLLOW_12); 

            			newLeafNode(otherlv_0, grammarAccess.getWallBuilderConfigAccess().getWallBuilderKeyword_0());
            		
            // InternalMazeDsl.g:3727:3: ( (lv_displayName_1_0= RULE_STRING ) )
            // InternalMazeDsl.g:3728:4: (lv_displayName_1_0= RULE_STRING )
            {
            // InternalMazeDsl.g:3728:4: (lv_displayName_1_0= RULE_STRING )
            // InternalMazeDsl.g:3729:5: lv_displayName_1_0= RULE_STRING
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

            otherlv_2=(Token)match(input,12,FOLLOW_85); 

            			newLeafNode(otherlv_2, grammarAccess.getWallBuilderConfigAccess().getLeftCurlyBracketKeyword_2());
            		
            otherlv_3=(Token)match(input,27,FOLLOW_37); 

            			newLeafNode(otherlv_3, grammarAccess.getWallBuilderConfigAccess().getHealthKeyword_3());
            		
            // InternalMazeDsl.g:3753:3: ( (lv_health_4_0= RULE_INT ) )
            // InternalMazeDsl.g:3754:4: (lv_health_4_0= RULE_INT )
            {
            // InternalMazeDsl.g:3754:4: (lv_health_4_0= RULE_INT )
            // InternalMazeDsl.g:3755:5: lv_health_4_0= RULE_INT
            {
            lv_health_4_0=(Token)match(input,RULE_INT,FOLLOW_105); 

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

            otherlv_5=(Token)match(input,28,FOLLOW_18); 

            			newLeafNode(otherlv_5, grammarAccess.getWallBuilderConfigAccess().getSpeedKeyword_5());
            		
            // InternalMazeDsl.g:3775:3: ( (lv_speed_6_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:3776:4: (lv_speed_6_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:3776:4: (lv_speed_6_0= ruleDOUBLE )
            // InternalMazeDsl.g:3777:5: lv_speed_6_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getWallBuilderConfigAccess().getSpeedDOUBLEParserRuleCall_6_0());
            				
            pushFollow(FOLLOW_91);
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

            otherlv_7=(Token)match(input,79,FOLLOW_12); 

            			newLeafNode(otherlv_7, grammarAccess.getWallBuilderConfigAccess().getSpriteKeyword_7());
            		
            // InternalMazeDsl.g:3798:3: ( (lv_sprite_8_0= RULE_STRING ) )
            // InternalMazeDsl.g:3799:4: (lv_sprite_8_0= RULE_STRING )
            {
            // InternalMazeDsl.g:3799:4: (lv_sprite_8_0= RULE_STRING )
            // InternalMazeDsl.g:3800:5: lv_sprite_8_0= RULE_STRING
            {
            lv_sprite_8_0=(Token)match(input,RULE_STRING,FOLLOW_121); 

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

            otherlv_9=(Token)match(input,114,FOLLOW_18); 

            			newLeafNode(otherlv_9, grammarAccess.getWallBuilderConfigAccess().getBuildSecondsKeyword_9());
            		
            // InternalMazeDsl.g:3820:3: ( (lv_buildSecondsPerCorridor_10_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:3821:4: (lv_buildSecondsPerCorridor_10_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:3821:4: (lv_buildSecondsPerCorridor_10_0= ruleDOUBLE )
            // InternalMazeDsl.g:3822:5: lv_buildSecondsPerCorridor_10_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getWallBuilderConfigAccess().getBuildSecondsPerCorridorDOUBLEParserRuleCall_10_0());
            				
            pushFollow(FOLLOW_122);
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

            otherlv_11=(Token)match(input,115,FOLLOW_37); 

            			newLeafNode(otherlv_11, grammarAccess.getWallBuilderConfigAccess().getStartRadiusKeyword_11());
            		
            // InternalMazeDsl.g:3843:3: ( (lv_startRadiusCells_12_0= RULE_INT ) )
            // InternalMazeDsl.g:3844:4: (lv_startRadiusCells_12_0= RULE_INT )
            {
            // InternalMazeDsl.g:3844:4: (lv_startRadiusCells_12_0= RULE_INT )
            // InternalMazeDsl.g:3845:5: lv_startRadiusCells_12_0= RULE_INT
            {
            lv_startRadiusCells_12_0=(Token)match(input,RULE_INT,FOLLOW_123); 

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

            otherlv_13=(Token)match(input,116,FOLLOW_37); 

            			newLeafNode(otherlv_13, grammarAccess.getWallBuilderConfigAccess().getBlockRadiusKeyword_13());
            		
            // InternalMazeDsl.g:3865:3: ( (lv_blockRadiusCells_14_0= RULE_INT ) )
            // InternalMazeDsl.g:3866:4: (lv_blockRadiusCells_14_0= RULE_INT )
            {
            // InternalMazeDsl.g:3866:4: (lv_blockRadiusCells_14_0= RULE_INT )
            // InternalMazeDsl.g:3867:5: lv_blockRadiusCells_14_0= RULE_INT
            {
            lv_blockRadiusCells_14_0=(Token)match(input,RULE_INT,FOLLOW_124); 

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

            otherlv_15=(Token)match(input,117,FOLLOW_18); 

            			newLeafNode(otherlv_15, grammarAccess.getWallBuilderConfigAccess().getSpawnRadiusKeyword_15());
            		
            // InternalMazeDsl.g:3887:3: ( (lv_spawnRadius_16_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:3888:4: (lv_spawnRadius_16_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:3888:4: (lv_spawnRadius_16_0= ruleDOUBLE )
            // InternalMazeDsl.g:3889:5: lv_spawnRadius_16_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getWallBuilderConfigAccess().getSpawnRadiusDOUBLEParserRuleCall_16_0());
            				
            pushFollow(FOLLOW_125);
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
            		
            // InternalMazeDsl.g:3910:3: ( (lv_wallMaterial_18_0= ruleValidID ) )
            // InternalMazeDsl.g:3911:4: (lv_wallMaterial_18_0= ruleValidID )
            {
            // InternalMazeDsl.g:3911:4: (lv_wallMaterial_18_0= ruleValidID )
            // InternalMazeDsl.g:3912:5: lv_wallMaterial_18_0= ruleValidID
            {

            					newCompositeNode(grammarAccess.getWallBuilderConfigAccess().getWallMaterialValidIDParserRuleCall_18_0());
            				
            pushFollow(FOLLOW_126);
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

            // InternalMazeDsl.g:3929:3: (otherlv_19= 'buildSound' ( (lv_buildSound_20_0= RULE_STRING ) ) )?
            int alt71=2;
            int LA71_0 = input.LA(1);

            if ( (LA71_0==119) ) {
                alt71=1;
            }
            switch (alt71) {
                case 1 :
                    // InternalMazeDsl.g:3930:4: otherlv_19= 'buildSound' ( (lv_buildSound_20_0= RULE_STRING ) )
                    {
                    otherlv_19=(Token)match(input,119,FOLLOW_12); 

                    				newLeafNode(otherlv_19, grammarAccess.getWallBuilderConfigAccess().getBuildSoundKeyword_19_0());
                    			
                    // InternalMazeDsl.g:3934:4: ( (lv_buildSound_20_0= RULE_STRING ) )
                    // InternalMazeDsl.g:3935:5: (lv_buildSound_20_0= RULE_STRING )
                    {
                    // InternalMazeDsl.g:3935:5: (lv_buildSound_20_0= RULE_STRING )
                    // InternalMazeDsl.g:3936:6: lv_buildSound_20_0= RULE_STRING
                    {
                    lv_buildSound_20_0=(Token)match(input,RULE_STRING,FOLLOW_11); 

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
    // InternalMazeDsl.g:3961:1: entryRuleBuilderCountConfig returns [EObject current=null] : iv_ruleBuilderCountConfig= ruleBuilderCountConfig EOF ;
    public final EObject entryRuleBuilderCountConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleBuilderCountConfig = null;


        try {
            // InternalMazeDsl.g:3961:59: (iv_ruleBuilderCountConfig= ruleBuilderCountConfig EOF )
            // InternalMazeDsl.g:3962:2: iv_ruleBuilderCountConfig= ruleBuilderCountConfig EOF
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
    // InternalMazeDsl.g:3968:1: ruleBuilderCountConfig returns [EObject current=null] : (otherlv_0= 'builders' ( (lv_difficulty_1_0= ruleDifficultyLevel ) ) ( (lv_count_2_0= RULE_INT ) ) ) ;
    public final EObject ruleBuilderCountConfig() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_count_2_0=null;
        Enumerator lv_difficulty_1_0 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:3974:2: ( (otherlv_0= 'builders' ( (lv_difficulty_1_0= ruleDifficultyLevel ) ) ( (lv_count_2_0= RULE_INT ) ) ) )
            // InternalMazeDsl.g:3975:2: (otherlv_0= 'builders' ( (lv_difficulty_1_0= ruleDifficultyLevel ) ) ( (lv_count_2_0= RULE_INT ) ) )
            {
            // InternalMazeDsl.g:3975:2: (otherlv_0= 'builders' ( (lv_difficulty_1_0= ruleDifficultyLevel ) ) ( (lv_count_2_0= RULE_INT ) ) )
            // InternalMazeDsl.g:3976:3: otherlv_0= 'builders' ( (lv_difficulty_1_0= ruleDifficultyLevel ) ) ( (lv_count_2_0= RULE_INT ) )
            {
            otherlv_0=(Token)match(input,120,FOLLOW_14); 

            			newLeafNode(otherlv_0, grammarAccess.getBuilderCountConfigAccess().getBuildersKeyword_0());
            		
            // InternalMazeDsl.g:3980:3: ( (lv_difficulty_1_0= ruleDifficultyLevel ) )
            // InternalMazeDsl.g:3981:4: (lv_difficulty_1_0= ruleDifficultyLevel )
            {
            // InternalMazeDsl.g:3981:4: (lv_difficulty_1_0= ruleDifficultyLevel )
            // InternalMazeDsl.g:3982:5: lv_difficulty_1_0= ruleDifficultyLevel
            {

            					newCompositeNode(grammarAccess.getBuilderCountConfigAccess().getDifficultyDifficultyLevelEnumRuleCall_1_0());
            				
            pushFollow(FOLLOW_37);
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

            // InternalMazeDsl.g:3999:3: ( (lv_count_2_0= RULE_INT ) )
            // InternalMazeDsl.g:4000:4: (lv_count_2_0= RULE_INT )
            {
            // InternalMazeDsl.g:4000:4: (lv_count_2_0= RULE_INT )
            // InternalMazeDsl.g:4001:5: lv_count_2_0= RULE_INT
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
    // InternalMazeDsl.g:4021:1: entryRuleLavaConfig returns [EObject current=null] : iv_ruleLavaConfig= ruleLavaConfig EOF ;
    public final EObject entryRuleLavaConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLavaConfig = null;


        try {
            // InternalMazeDsl.g:4021:51: (iv_ruleLavaConfig= ruleLavaConfig EOF )
            // InternalMazeDsl.g:4022:2: iv_ruleLavaConfig= ruleLavaConfig EOF
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
    // InternalMazeDsl.g:4028:1: ruleLavaConfig returns [EObject current=null] : (otherlv_0= 'lava' otherlv_1= '{' otherlv_2= 'streams' ( (lv_streamCount_3_0= RULE_INT ) ) otherlv_4= 'width' ( (lv_width_5_0= ruleDOUBLE ) ) otherlv_6= 'bridges' ( (lv_bridgesPerStream_7_0= RULE_INT ) ) otherlv_8= 'bridgeLength' ( (lv_bridgeLength_9_0= ruleDOUBLE ) ) (otherlv_10= 'meander' ( (lv_meander_11_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_12= 'wavelength' ( (lv_wavelength_13_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_14= 'flowSpeed' ( (lv_flowSpeed_15_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_16= 'glow' ( (lv_glow_17_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_18= 'crust' ( (lv_crust_19_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_20= 'deathSound' ( (lv_deathSound_21_0= RULE_STRING ) ) )? otherlv_22= '}' ) ;
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
            // InternalMazeDsl.g:4034:2: ( (otherlv_0= 'lava' otherlv_1= '{' otherlv_2= 'streams' ( (lv_streamCount_3_0= RULE_INT ) ) otherlv_4= 'width' ( (lv_width_5_0= ruleDOUBLE ) ) otherlv_6= 'bridges' ( (lv_bridgesPerStream_7_0= RULE_INT ) ) otherlv_8= 'bridgeLength' ( (lv_bridgeLength_9_0= ruleDOUBLE ) ) (otherlv_10= 'meander' ( (lv_meander_11_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_12= 'wavelength' ( (lv_wavelength_13_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_14= 'flowSpeed' ( (lv_flowSpeed_15_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_16= 'glow' ( (lv_glow_17_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_18= 'crust' ( (lv_crust_19_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_20= 'deathSound' ( (lv_deathSound_21_0= RULE_STRING ) ) )? otherlv_22= '}' ) )
            // InternalMazeDsl.g:4035:2: (otherlv_0= 'lava' otherlv_1= '{' otherlv_2= 'streams' ( (lv_streamCount_3_0= RULE_INT ) ) otherlv_4= 'width' ( (lv_width_5_0= ruleDOUBLE ) ) otherlv_6= 'bridges' ( (lv_bridgesPerStream_7_0= RULE_INT ) ) otherlv_8= 'bridgeLength' ( (lv_bridgeLength_9_0= ruleDOUBLE ) ) (otherlv_10= 'meander' ( (lv_meander_11_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_12= 'wavelength' ( (lv_wavelength_13_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_14= 'flowSpeed' ( (lv_flowSpeed_15_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_16= 'glow' ( (lv_glow_17_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_18= 'crust' ( (lv_crust_19_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_20= 'deathSound' ( (lv_deathSound_21_0= RULE_STRING ) ) )? otherlv_22= '}' )
            {
            // InternalMazeDsl.g:4035:2: (otherlv_0= 'lava' otherlv_1= '{' otherlv_2= 'streams' ( (lv_streamCount_3_0= RULE_INT ) ) otherlv_4= 'width' ( (lv_width_5_0= ruleDOUBLE ) ) otherlv_6= 'bridges' ( (lv_bridgesPerStream_7_0= RULE_INT ) ) otherlv_8= 'bridgeLength' ( (lv_bridgeLength_9_0= ruleDOUBLE ) ) (otherlv_10= 'meander' ( (lv_meander_11_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_12= 'wavelength' ( (lv_wavelength_13_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_14= 'flowSpeed' ( (lv_flowSpeed_15_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_16= 'glow' ( (lv_glow_17_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_18= 'crust' ( (lv_crust_19_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_20= 'deathSound' ( (lv_deathSound_21_0= RULE_STRING ) ) )? otherlv_22= '}' )
            // InternalMazeDsl.g:4036:3: otherlv_0= 'lava' otherlv_1= '{' otherlv_2= 'streams' ( (lv_streamCount_3_0= RULE_INT ) ) otherlv_4= 'width' ( (lv_width_5_0= ruleDOUBLE ) ) otherlv_6= 'bridges' ( (lv_bridgesPerStream_7_0= RULE_INT ) ) otherlv_8= 'bridgeLength' ( (lv_bridgeLength_9_0= ruleDOUBLE ) ) (otherlv_10= 'meander' ( (lv_meander_11_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_12= 'wavelength' ( (lv_wavelength_13_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_14= 'flowSpeed' ( (lv_flowSpeed_15_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_16= 'glow' ( (lv_glow_17_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_18= 'crust' ( (lv_crust_19_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_20= 'deathSound' ( (lv_deathSound_21_0= RULE_STRING ) ) )? otherlv_22= '}'
            {
            otherlv_0=(Token)match(input,121,FOLLOW_4); 

            			newLeafNode(otherlv_0, grammarAccess.getLavaConfigAccess().getLavaKeyword_0());
            		
            otherlv_1=(Token)match(input,12,FOLLOW_127); 

            			newLeafNode(otherlv_1, grammarAccess.getLavaConfigAccess().getLeftCurlyBracketKeyword_1());
            		
            otherlv_2=(Token)match(input,122,FOLLOW_37); 

            			newLeafNode(otherlv_2, grammarAccess.getLavaConfigAccess().getStreamsKeyword_2());
            		
            // InternalMazeDsl.g:4048:3: ( (lv_streamCount_3_0= RULE_INT ) )
            // InternalMazeDsl.g:4049:4: (lv_streamCount_3_0= RULE_INT )
            {
            // InternalMazeDsl.g:4049:4: (lv_streamCount_3_0= RULE_INT )
            // InternalMazeDsl.g:4050:5: lv_streamCount_3_0= RULE_INT
            {
            lv_streamCount_3_0=(Token)match(input,RULE_INT,FOLLOW_59); 

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

            otherlv_4=(Token)match(input,56,FOLLOW_18); 

            			newLeafNode(otherlv_4, grammarAccess.getLavaConfigAccess().getWidthKeyword_4());
            		
            // InternalMazeDsl.g:4070:3: ( (lv_width_5_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:4071:4: (lv_width_5_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:4071:4: (lv_width_5_0= ruleDOUBLE )
            // InternalMazeDsl.g:4072:5: lv_width_5_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getLavaConfigAccess().getWidthDOUBLEParserRuleCall_5_0());
            				
            pushFollow(FOLLOW_128);
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

            otherlv_6=(Token)match(input,123,FOLLOW_37); 

            			newLeafNode(otherlv_6, grammarAccess.getLavaConfigAccess().getBridgesKeyword_6());
            		
            // InternalMazeDsl.g:4093:3: ( (lv_bridgesPerStream_7_0= RULE_INT ) )
            // InternalMazeDsl.g:4094:4: (lv_bridgesPerStream_7_0= RULE_INT )
            {
            // InternalMazeDsl.g:4094:4: (lv_bridgesPerStream_7_0= RULE_INT )
            // InternalMazeDsl.g:4095:5: lv_bridgesPerStream_7_0= RULE_INT
            {
            lv_bridgesPerStream_7_0=(Token)match(input,RULE_INT,FOLLOW_129); 

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

            otherlv_8=(Token)match(input,124,FOLLOW_18); 

            			newLeafNode(otherlv_8, grammarAccess.getLavaConfigAccess().getBridgeLengthKeyword_8());
            		
            // InternalMazeDsl.g:4115:3: ( (lv_bridgeLength_9_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:4116:4: (lv_bridgeLength_9_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:4116:4: (lv_bridgeLength_9_0= ruleDOUBLE )
            // InternalMazeDsl.g:4117:5: lv_bridgeLength_9_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getLavaConfigAccess().getBridgeLengthDOUBLEParserRuleCall_9_0());
            				
            pushFollow(FOLLOW_130);
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

            // InternalMazeDsl.g:4134:3: (otherlv_10= 'meander' ( (lv_meander_11_0= ruleDOUBLE_OBJECT ) ) )?
            int alt72=2;
            int LA72_0 = input.LA(1);

            if ( (LA72_0==125) ) {
                alt72=1;
            }
            switch (alt72) {
                case 1 :
                    // InternalMazeDsl.g:4135:4: otherlv_10= 'meander' ( (lv_meander_11_0= ruleDOUBLE_OBJECT ) )
                    {
                    otherlv_10=(Token)match(input,125,FOLLOW_18); 

                    				newLeafNode(otherlv_10, grammarAccess.getLavaConfigAccess().getMeanderKeyword_10_0());
                    			
                    // InternalMazeDsl.g:4139:4: ( (lv_meander_11_0= ruleDOUBLE_OBJECT ) )
                    // InternalMazeDsl.g:4140:5: (lv_meander_11_0= ruleDOUBLE_OBJECT )
                    {
                    // InternalMazeDsl.g:4140:5: (lv_meander_11_0= ruleDOUBLE_OBJECT )
                    // InternalMazeDsl.g:4141:6: lv_meander_11_0= ruleDOUBLE_OBJECT
                    {

                    						newCompositeNode(grammarAccess.getLavaConfigAccess().getMeanderDOUBLE_OBJECTParserRuleCall_10_1_0());
                    					
                    pushFollow(FOLLOW_131);
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

            // InternalMazeDsl.g:4159:3: (otherlv_12= 'wavelength' ( (lv_wavelength_13_0= ruleDOUBLE_OBJECT ) ) )?
            int alt73=2;
            int LA73_0 = input.LA(1);

            if ( (LA73_0==126) ) {
                alt73=1;
            }
            switch (alt73) {
                case 1 :
                    // InternalMazeDsl.g:4160:4: otherlv_12= 'wavelength' ( (lv_wavelength_13_0= ruleDOUBLE_OBJECT ) )
                    {
                    otherlv_12=(Token)match(input,126,FOLLOW_18); 

                    				newLeafNode(otherlv_12, grammarAccess.getLavaConfigAccess().getWavelengthKeyword_11_0());
                    			
                    // InternalMazeDsl.g:4164:4: ( (lv_wavelength_13_0= ruleDOUBLE_OBJECT ) )
                    // InternalMazeDsl.g:4165:5: (lv_wavelength_13_0= ruleDOUBLE_OBJECT )
                    {
                    // InternalMazeDsl.g:4165:5: (lv_wavelength_13_0= ruleDOUBLE_OBJECT )
                    // InternalMazeDsl.g:4166:6: lv_wavelength_13_0= ruleDOUBLE_OBJECT
                    {

                    						newCompositeNode(grammarAccess.getLavaConfigAccess().getWavelengthDOUBLE_OBJECTParserRuleCall_11_1_0());
                    					
                    pushFollow(FOLLOW_132);
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

            // InternalMazeDsl.g:4184:3: (otherlv_14= 'flowSpeed' ( (lv_flowSpeed_15_0= ruleDOUBLE_OBJECT ) ) )?
            int alt74=2;
            int LA74_0 = input.LA(1);

            if ( (LA74_0==127) ) {
                alt74=1;
            }
            switch (alt74) {
                case 1 :
                    // InternalMazeDsl.g:4185:4: otherlv_14= 'flowSpeed' ( (lv_flowSpeed_15_0= ruleDOUBLE_OBJECT ) )
                    {
                    otherlv_14=(Token)match(input,127,FOLLOW_18); 

                    				newLeafNode(otherlv_14, grammarAccess.getLavaConfigAccess().getFlowSpeedKeyword_12_0());
                    			
                    // InternalMazeDsl.g:4189:4: ( (lv_flowSpeed_15_0= ruleDOUBLE_OBJECT ) )
                    // InternalMazeDsl.g:4190:5: (lv_flowSpeed_15_0= ruleDOUBLE_OBJECT )
                    {
                    // InternalMazeDsl.g:4190:5: (lv_flowSpeed_15_0= ruleDOUBLE_OBJECT )
                    // InternalMazeDsl.g:4191:6: lv_flowSpeed_15_0= ruleDOUBLE_OBJECT
                    {

                    						newCompositeNode(grammarAccess.getLavaConfigAccess().getFlowSpeedDOUBLE_OBJECTParserRuleCall_12_1_0());
                    					
                    pushFollow(FOLLOW_133);
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

            // InternalMazeDsl.g:4209:3: (otherlv_16= 'glow' ( (lv_glow_17_0= ruleDOUBLE_OBJECT ) ) )?
            int alt75=2;
            int LA75_0 = input.LA(1);

            if ( (LA75_0==128) ) {
                alt75=1;
            }
            switch (alt75) {
                case 1 :
                    // InternalMazeDsl.g:4210:4: otherlv_16= 'glow' ( (lv_glow_17_0= ruleDOUBLE_OBJECT ) )
                    {
                    otherlv_16=(Token)match(input,128,FOLLOW_18); 

                    				newLeafNode(otherlv_16, grammarAccess.getLavaConfigAccess().getGlowKeyword_13_0());
                    			
                    // InternalMazeDsl.g:4214:4: ( (lv_glow_17_0= ruleDOUBLE_OBJECT ) )
                    // InternalMazeDsl.g:4215:5: (lv_glow_17_0= ruleDOUBLE_OBJECT )
                    {
                    // InternalMazeDsl.g:4215:5: (lv_glow_17_0= ruleDOUBLE_OBJECT )
                    // InternalMazeDsl.g:4216:6: lv_glow_17_0= ruleDOUBLE_OBJECT
                    {

                    						newCompositeNode(grammarAccess.getLavaConfigAccess().getGlowDOUBLE_OBJECTParserRuleCall_13_1_0());
                    					
                    pushFollow(FOLLOW_134);
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

            // InternalMazeDsl.g:4234:3: (otherlv_18= 'crust' ( (lv_crust_19_0= ruleDOUBLE_OBJECT ) ) )?
            int alt76=2;
            int LA76_0 = input.LA(1);

            if ( (LA76_0==129) ) {
                alt76=1;
            }
            switch (alt76) {
                case 1 :
                    // InternalMazeDsl.g:4235:4: otherlv_18= 'crust' ( (lv_crust_19_0= ruleDOUBLE_OBJECT ) )
                    {
                    otherlv_18=(Token)match(input,129,FOLLOW_18); 

                    				newLeafNode(otherlv_18, grammarAccess.getLavaConfigAccess().getCrustKeyword_14_0());
                    			
                    // InternalMazeDsl.g:4239:4: ( (lv_crust_19_0= ruleDOUBLE_OBJECT ) )
                    // InternalMazeDsl.g:4240:5: (lv_crust_19_0= ruleDOUBLE_OBJECT )
                    {
                    // InternalMazeDsl.g:4240:5: (lv_crust_19_0= ruleDOUBLE_OBJECT )
                    // InternalMazeDsl.g:4241:6: lv_crust_19_0= ruleDOUBLE_OBJECT
                    {

                    						newCompositeNode(grammarAccess.getLavaConfigAccess().getCrustDOUBLE_OBJECTParserRuleCall_14_1_0());
                    					
                    pushFollow(FOLLOW_135);
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

            // InternalMazeDsl.g:4259:3: (otherlv_20= 'deathSound' ( (lv_deathSound_21_0= RULE_STRING ) ) )?
            int alt77=2;
            int LA77_0 = input.LA(1);

            if ( (LA77_0==130) ) {
                alt77=1;
            }
            switch (alt77) {
                case 1 :
                    // InternalMazeDsl.g:4260:4: otherlv_20= 'deathSound' ( (lv_deathSound_21_0= RULE_STRING ) )
                    {
                    otherlv_20=(Token)match(input,130,FOLLOW_12); 

                    				newLeafNode(otherlv_20, grammarAccess.getLavaConfigAccess().getDeathSoundKeyword_15_0());
                    			
                    // InternalMazeDsl.g:4264:4: ( (lv_deathSound_21_0= RULE_STRING ) )
                    // InternalMazeDsl.g:4265:5: (lv_deathSound_21_0= RULE_STRING )
                    {
                    // InternalMazeDsl.g:4265:5: (lv_deathSound_21_0= RULE_STRING )
                    // InternalMazeDsl.g:4266:6: lv_deathSound_21_0= RULE_STRING
                    {
                    lv_deathSound_21_0=(Token)match(input,RULE_STRING,FOLLOW_11); 

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


    // $ANTLR start "entryRuleUfoRaidConfig"
    // InternalMazeDsl.g:4291:1: entryRuleUfoRaidConfig returns [EObject current=null] : iv_ruleUfoRaidConfig= ruleUfoRaidConfig EOF ;
    public final EObject entryRuleUfoRaidConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUfoRaidConfig = null;


        try {
            // InternalMazeDsl.g:4291:54: (iv_ruleUfoRaidConfig= ruleUfoRaidConfig EOF )
            // InternalMazeDsl.g:4292:2: iv_ruleUfoRaidConfig= ruleUfoRaidConfig EOF
            {
             newCompositeNode(grammarAccess.getUfoRaidConfigRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleUfoRaidConfig=ruleUfoRaidConfig();

            state._fsp--;

             current =iv_ruleUfoRaidConfig; 
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
    // $ANTLR end "entryRuleUfoRaidConfig"


    // $ANTLR start "ruleUfoRaidConfig"
    // InternalMazeDsl.g:4298:1: ruleUfoRaidConfig returns [EObject current=null] : (otherlv_0= 'ufoRaid' otherlv_1= '{' (otherlv_2= 'trigger' ( (lv_triggerTier_3_0= ruleValidID ) ) )? otherlv_4= 'ufos' ( (lv_ufos_5_0= RULE_INT ) ) otherlv_6= 'duration' ( (lv_seconds_7_0= ruleDOUBLE ) ) otherlv_8= 'ufoSpeed' ( (lv_ufoSpeed_9_0= ruleDOUBLE ) ) otherlv_10= 'beamEvery' ( (lv_beamEverySeconds_11_0= ruleDOUBLE ) ) otherlv_12= 'charge' ( (lv_chargeSeconds_13_0= ruleDOUBLE ) ) otherlv_14= 'beamSeconds' ( (lv_beamSeconds_15_0= ruleDOUBLE ) ) otherlv_16= 'beamRadius' ( (lv_beamRadius_17_0= ruleDOUBLE ) ) otherlv_18= 'aimMin' ( (lv_aimMinDistance_19_0= ruleDOUBLE ) ) otherlv_20= 'aimMax' ( (lv_aimMaxDistance_21_0= ruleDOUBLE ) ) otherlv_22= 'damageFraction' ( (lv_damageFraction_23_0= ruleDOUBLE ) ) otherlv_24= 'soldiers' ( (lv_soldiers_25_0= RULE_INT ) ) otherlv_26= 'soldierHealth' ( (lv_soldierHealth_27_0= RULE_INT ) ) otherlv_28= 'soldierDamage' ( (lv_soldierDamage_29_0= RULE_INT ) ) otherlv_30= 'soldierSight' ( (lv_soldierSight_31_0= ruleDOUBLE ) ) otherlv_32= 'soldierSeconds' ( (lv_soldierSeconds_33_0= ruleDOUBLE ) ) (otherlv_34= 'banner' ( (lv_bannerText_35_0= RULE_STRING ) ) )? otherlv_36= 'ufoSprite' ( (lv_ufoSprite_37_0= RULE_STRING ) ) otherlv_38= 'beamSprite' ( (lv_beamSprite_39_0= RULE_STRING ) ) (otherlv_40= 'arriveSound' ( (lv_arriveSound_41_0= RULE_STRING ) ) )? (otherlv_42= 'beamSound' ( (lv_beamSound_43_0= RULE_STRING ) ) )? otherlv_44= '}' ) ;
    public final EObject ruleUfoRaidConfig() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token lv_ufos_5_0=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        Token otherlv_10=null;
        Token otherlv_12=null;
        Token otherlv_14=null;
        Token otherlv_16=null;
        Token otherlv_18=null;
        Token otherlv_20=null;
        Token otherlv_22=null;
        Token otherlv_24=null;
        Token lv_soldiers_25_0=null;
        Token otherlv_26=null;
        Token lv_soldierHealth_27_0=null;
        Token otherlv_28=null;
        Token lv_soldierDamage_29_0=null;
        Token otherlv_30=null;
        Token otherlv_32=null;
        Token otherlv_34=null;
        Token lv_bannerText_35_0=null;
        Token otherlv_36=null;
        Token lv_ufoSprite_37_0=null;
        Token otherlv_38=null;
        Token lv_beamSprite_39_0=null;
        Token otherlv_40=null;
        Token lv_arriveSound_41_0=null;
        Token otherlv_42=null;
        Token lv_beamSound_43_0=null;
        Token otherlv_44=null;
        AntlrDatatypeRuleToken lv_triggerTier_3_0 = null;

        AntlrDatatypeRuleToken lv_seconds_7_0 = null;

        AntlrDatatypeRuleToken lv_ufoSpeed_9_0 = null;

        AntlrDatatypeRuleToken lv_beamEverySeconds_11_0 = null;

        AntlrDatatypeRuleToken lv_chargeSeconds_13_0 = null;

        AntlrDatatypeRuleToken lv_beamSeconds_15_0 = null;

        AntlrDatatypeRuleToken lv_beamRadius_17_0 = null;

        AntlrDatatypeRuleToken lv_aimMinDistance_19_0 = null;

        AntlrDatatypeRuleToken lv_aimMaxDistance_21_0 = null;

        AntlrDatatypeRuleToken lv_damageFraction_23_0 = null;

        AntlrDatatypeRuleToken lv_soldierSight_31_0 = null;

        AntlrDatatypeRuleToken lv_soldierSeconds_33_0 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:4304:2: ( (otherlv_0= 'ufoRaid' otherlv_1= '{' (otherlv_2= 'trigger' ( (lv_triggerTier_3_0= ruleValidID ) ) )? otherlv_4= 'ufos' ( (lv_ufos_5_0= RULE_INT ) ) otherlv_6= 'duration' ( (lv_seconds_7_0= ruleDOUBLE ) ) otherlv_8= 'ufoSpeed' ( (lv_ufoSpeed_9_0= ruleDOUBLE ) ) otherlv_10= 'beamEvery' ( (lv_beamEverySeconds_11_0= ruleDOUBLE ) ) otherlv_12= 'charge' ( (lv_chargeSeconds_13_0= ruleDOUBLE ) ) otherlv_14= 'beamSeconds' ( (lv_beamSeconds_15_0= ruleDOUBLE ) ) otherlv_16= 'beamRadius' ( (lv_beamRadius_17_0= ruleDOUBLE ) ) otherlv_18= 'aimMin' ( (lv_aimMinDistance_19_0= ruleDOUBLE ) ) otherlv_20= 'aimMax' ( (lv_aimMaxDistance_21_0= ruleDOUBLE ) ) otherlv_22= 'damageFraction' ( (lv_damageFraction_23_0= ruleDOUBLE ) ) otherlv_24= 'soldiers' ( (lv_soldiers_25_0= RULE_INT ) ) otherlv_26= 'soldierHealth' ( (lv_soldierHealth_27_0= RULE_INT ) ) otherlv_28= 'soldierDamage' ( (lv_soldierDamage_29_0= RULE_INT ) ) otherlv_30= 'soldierSight' ( (lv_soldierSight_31_0= ruleDOUBLE ) ) otherlv_32= 'soldierSeconds' ( (lv_soldierSeconds_33_0= ruleDOUBLE ) ) (otherlv_34= 'banner' ( (lv_bannerText_35_0= RULE_STRING ) ) )? otherlv_36= 'ufoSprite' ( (lv_ufoSprite_37_0= RULE_STRING ) ) otherlv_38= 'beamSprite' ( (lv_beamSprite_39_0= RULE_STRING ) ) (otherlv_40= 'arriveSound' ( (lv_arriveSound_41_0= RULE_STRING ) ) )? (otherlv_42= 'beamSound' ( (lv_beamSound_43_0= RULE_STRING ) ) )? otherlv_44= '}' ) )
            // InternalMazeDsl.g:4305:2: (otherlv_0= 'ufoRaid' otherlv_1= '{' (otherlv_2= 'trigger' ( (lv_triggerTier_3_0= ruleValidID ) ) )? otherlv_4= 'ufos' ( (lv_ufos_5_0= RULE_INT ) ) otherlv_6= 'duration' ( (lv_seconds_7_0= ruleDOUBLE ) ) otherlv_8= 'ufoSpeed' ( (lv_ufoSpeed_9_0= ruleDOUBLE ) ) otherlv_10= 'beamEvery' ( (lv_beamEverySeconds_11_0= ruleDOUBLE ) ) otherlv_12= 'charge' ( (lv_chargeSeconds_13_0= ruleDOUBLE ) ) otherlv_14= 'beamSeconds' ( (lv_beamSeconds_15_0= ruleDOUBLE ) ) otherlv_16= 'beamRadius' ( (lv_beamRadius_17_0= ruleDOUBLE ) ) otherlv_18= 'aimMin' ( (lv_aimMinDistance_19_0= ruleDOUBLE ) ) otherlv_20= 'aimMax' ( (lv_aimMaxDistance_21_0= ruleDOUBLE ) ) otherlv_22= 'damageFraction' ( (lv_damageFraction_23_0= ruleDOUBLE ) ) otherlv_24= 'soldiers' ( (lv_soldiers_25_0= RULE_INT ) ) otherlv_26= 'soldierHealth' ( (lv_soldierHealth_27_0= RULE_INT ) ) otherlv_28= 'soldierDamage' ( (lv_soldierDamage_29_0= RULE_INT ) ) otherlv_30= 'soldierSight' ( (lv_soldierSight_31_0= ruleDOUBLE ) ) otherlv_32= 'soldierSeconds' ( (lv_soldierSeconds_33_0= ruleDOUBLE ) ) (otherlv_34= 'banner' ( (lv_bannerText_35_0= RULE_STRING ) ) )? otherlv_36= 'ufoSprite' ( (lv_ufoSprite_37_0= RULE_STRING ) ) otherlv_38= 'beamSprite' ( (lv_beamSprite_39_0= RULE_STRING ) ) (otherlv_40= 'arriveSound' ( (lv_arriveSound_41_0= RULE_STRING ) ) )? (otherlv_42= 'beamSound' ( (lv_beamSound_43_0= RULE_STRING ) ) )? otherlv_44= '}' )
            {
            // InternalMazeDsl.g:4305:2: (otherlv_0= 'ufoRaid' otherlv_1= '{' (otherlv_2= 'trigger' ( (lv_triggerTier_3_0= ruleValidID ) ) )? otherlv_4= 'ufos' ( (lv_ufos_5_0= RULE_INT ) ) otherlv_6= 'duration' ( (lv_seconds_7_0= ruleDOUBLE ) ) otherlv_8= 'ufoSpeed' ( (lv_ufoSpeed_9_0= ruleDOUBLE ) ) otherlv_10= 'beamEvery' ( (lv_beamEverySeconds_11_0= ruleDOUBLE ) ) otherlv_12= 'charge' ( (lv_chargeSeconds_13_0= ruleDOUBLE ) ) otherlv_14= 'beamSeconds' ( (lv_beamSeconds_15_0= ruleDOUBLE ) ) otherlv_16= 'beamRadius' ( (lv_beamRadius_17_0= ruleDOUBLE ) ) otherlv_18= 'aimMin' ( (lv_aimMinDistance_19_0= ruleDOUBLE ) ) otherlv_20= 'aimMax' ( (lv_aimMaxDistance_21_0= ruleDOUBLE ) ) otherlv_22= 'damageFraction' ( (lv_damageFraction_23_0= ruleDOUBLE ) ) otherlv_24= 'soldiers' ( (lv_soldiers_25_0= RULE_INT ) ) otherlv_26= 'soldierHealth' ( (lv_soldierHealth_27_0= RULE_INT ) ) otherlv_28= 'soldierDamage' ( (lv_soldierDamage_29_0= RULE_INT ) ) otherlv_30= 'soldierSight' ( (lv_soldierSight_31_0= ruleDOUBLE ) ) otherlv_32= 'soldierSeconds' ( (lv_soldierSeconds_33_0= ruleDOUBLE ) ) (otherlv_34= 'banner' ( (lv_bannerText_35_0= RULE_STRING ) ) )? otherlv_36= 'ufoSprite' ( (lv_ufoSprite_37_0= RULE_STRING ) ) otherlv_38= 'beamSprite' ( (lv_beamSprite_39_0= RULE_STRING ) ) (otherlv_40= 'arriveSound' ( (lv_arriveSound_41_0= RULE_STRING ) ) )? (otherlv_42= 'beamSound' ( (lv_beamSound_43_0= RULE_STRING ) ) )? otherlv_44= '}' )
            // InternalMazeDsl.g:4306:3: otherlv_0= 'ufoRaid' otherlv_1= '{' (otherlv_2= 'trigger' ( (lv_triggerTier_3_0= ruleValidID ) ) )? otherlv_4= 'ufos' ( (lv_ufos_5_0= RULE_INT ) ) otherlv_6= 'duration' ( (lv_seconds_7_0= ruleDOUBLE ) ) otherlv_8= 'ufoSpeed' ( (lv_ufoSpeed_9_0= ruleDOUBLE ) ) otherlv_10= 'beamEvery' ( (lv_beamEverySeconds_11_0= ruleDOUBLE ) ) otherlv_12= 'charge' ( (lv_chargeSeconds_13_0= ruleDOUBLE ) ) otherlv_14= 'beamSeconds' ( (lv_beamSeconds_15_0= ruleDOUBLE ) ) otherlv_16= 'beamRadius' ( (lv_beamRadius_17_0= ruleDOUBLE ) ) otherlv_18= 'aimMin' ( (lv_aimMinDistance_19_0= ruleDOUBLE ) ) otherlv_20= 'aimMax' ( (lv_aimMaxDistance_21_0= ruleDOUBLE ) ) otherlv_22= 'damageFraction' ( (lv_damageFraction_23_0= ruleDOUBLE ) ) otherlv_24= 'soldiers' ( (lv_soldiers_25_0= RULE_INT ) ) otherlv_26= 'soldierHealth' ( (lv_soldierHealth_27_0= RULE_INT ) ) otherlv_28= 'soldierDamage' ( (lv_soldierDamage_29_0= RULE_INT ) ) otherlv_30= 'soldierSight' ( (lv_soldierSight_31_0= ruleDOUBLE ) ) otherlv_32= 'soldierSeconds' ( (lv_soldierSeconds_33_0= ruleDOUBLE ) ) (otherlv_34= 'banner' ( (lv_bannerText_35_0= RULE_STRING ) ) )? otherlv_36= 'ufoSprite' ( (lv_ufoSprite_37_0= RULE_STRING ) ) otherlv_38= 'beamSprite' ( (lv_beamSprite_39_0= RULE_STRING ) ) (otherlv_40= 'arriveSound' ( (lv_arriveSound_41_0= RULE_STRING ) ) )? (otherlv_42= 'beamSound' ( (lv_beamSound_43_0= RULE_STRING ) ) )? otherlv_44= '}'
            {
            otherlv_0=(Token)match(input,131,FOLLOW_4); 

            			newLeafNode(otherlv_0, grammarAccess.getUfoRaidConfigAccess().getUfoRaidKeyword_0());
            		
            otherlv_1=(Token)match(input,12,FOLLOW_136); 

            			newLeafNode(otherlv_1, grammarAccess.getUfoRaidConfigAccess().getLeftCurlyBracketKeyword_1());
            		
            // InternalMazeDsl.g:4314:3: (otherlv_2= 'trigger' ( (lv_triggerTier_3_0= ruleValidID ) ) )?
            int alt78=2;
            int LA78_0 = input.LA(1);

            if ( (LA78_0==132) ) {
                alt78=1;
            }
            switch (alt78) {
                case 1 :
                    // InternalMazeDsl.g:4315:4: otherlv_2= 'trigger' ( (lv_triggerTier_3_0= ruleValidID ) )
                    {
                    otherlv_2=(Token)match(input,132,FOLLOW_3); 

                    				newLeafNode(otherlv_2, grammarAccess.getUfoRaidConfigAccess().getTriggerKeyword_2_0());
                    			
                    // InternalMazeDsl.g:4319:4: ( (lv_triggerTier_3_0= ruleValidID ) )
                    // InternalMazeDsl.g:4320:5: (lv_triggerTier_3_0= ruleValidID )
                    {
                    // InternalMazeDsl.g:4320:5: (lv_triggerTier_3_0= ruleValidID )
                    // InternalMazeDsl.g:4321:6: lv_triggerTier_3_0= ruleValidID
                    {

                    						newCompositeNode(grammarAccess.getUfoRaidConfigAccess().getTriggerTierValidIDParserRuleCall_2_1_0());
                    					
                    pushFollow(FOLLOW_137);
                    lv_triggerTier_3_0=ruleValidID();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getUfoRaidConfigRule());
                    						}
                    						set(
                    							current,
                    							"triggerTier",
                    							lv_triggerTier_3_0,
                    							"main.game.maze.dsl.MazeDsl.ValidID");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_4=(Token)match(input,133,FOLLOW_37); 

            			newLeafNode(otherlv_4, grammarAccess.getUfoRaidConfigAccess().getUfosKeyword_3());
            		
            // InternalMazeDsl.g:4343:3: ( (lv_ufos_5_0= RULE_INT ) )
            // InternalMazeDsl.g:4344:4: (lv_ufos_5_0= RULE_INT )
            {
            // InternalMazeDsl.g:4344:4: (lv_ufos_5_0= RULE_INT )
            // InternalMazeDsl.g:4345:5: lv_ufos_5_0= RULE_INT
            {
            lv_ufos_5_0=(Token)match(input,RULE_INT,FOLLOW_138); 

            					newLeafNode(lv_ufos_5_0, grammarAccess.getUfoRaidConfigAccess().getUfosINTTerminalRuleCall_4_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getUfoRaidConfigRule());
            					}
            					setWithLastConsumed(
            						current,
            						"ufos",
            						lv_ufos_5_0,
            						"org.eclipse.xtext.common.Terminals.INT");
            				

            }


            }

            otherlv_6=(Token)match(input,134,FOLLOW_18); 

            			newLeafNode(otherlv_6, grammarAccess.getUfoRaidConfigAccess().getDurationKeyword_5());
            		
            // InternalMazeDsl.g:4365:3: ( (lv_seconds_7_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:4366:4: (lv_seconds_7_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:4366:4: (lv_seconds_7_0= ruleDOUBLE )
            // InternalMazeDsl.g:4367:5: lv_seconds_7_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getUfoRaidConfigAccess().getSecondsDOUBLEParserRuleCall_6_0());
            				
            pushFollow(FOLLOW_139);
            lv_seconds_7_0=ruleDOUBLE();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getUfoRaidConfigRule());
            					}
            					set(
            						current,
            						"seconds",
            						lv_seconds_7_0,
            						"main.game.maze.dsl.MazeDsl.DOUBLE");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_8=(Token)match(input,135,FOLLOW_18); 

            			newLeafNode(otherlv_8, grammarAccess.getUfoRaidConfigAccess().getUfoSpeedKeyword_7());
            		
            // InternalMazeDsl.g:4388:3: ( (lv_ufoSpeed_9_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:4389:4: (lv_ufoSpeed_9_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:4389:4: (lv_ufoSpeed_9_0= ruleDOUBLE )
            // InternalMazeDsl.g:4390:5: lv_ufoSpeed_9_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getUfoRaidConfigAccess().getUfoSpeedDOUBLEParserRuleCall_8_0());
            				
            pushFollow(FOLLOW_140);
            lv_ufoSpeed_9_0=ruleDOUBLE();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getUfoRaidConfigRule());
            					}
            					set(
            						current,
            						"ufoSpeed",
            						lv_ufoSpeed_9_0,
            						"main.game.maze.dsl.MazeDsl.DOUBLE");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_10=(Token)match(input,136,FOLLOW_18); 

            			newLeafNode(otherlv_10, grammarAccess.getUfoRaidConfigAccess().getBeamEveryKeyword_9());
            		
            // InternalMazeDsl.g:4411:3: ( (lv_beamEverySeconds_11_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:4412:4: (lv_beamEverySeconds_11_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:4412:4: (lv_beamEverySeconds_11_0= ruleDOUBLE )
            // InternalMazeDsl.g:4413:5: lv_beamEverySeconds_11_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getUfoRaidConfigAccess().getBeamEverySecondsDOUBLEParserRuleCall_10_0());
            				
            pushFollow(FOLLOW_141);
            lv_beamEverySeconds_11_0=ruleDOUBLE();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getUfoRaidConfigRule());
            					}
            					set(
            						current,
            						"beamEverySeconds",
            						lv_beamEverySeconds_11_0,
            						"main.game.maze.dsl.MazeDsl.DOUBLE");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_12=(Token)match(input,137,FOLLOW_18); 

            			newLeafNode(otherlv_12, grammarAccess.getUfoRaidConfigAccess().getChargeKeyword_11());
            		
            // InternalMazeDsl.g:4434:3: ( (lv_chargeSeconds_13_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:4435:4: (lv_chargeSeconds_13_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:4435:4: (lv_chargeSeconds_13_0= ruleDOUBLE )
            // InternalMazeDsl.g:4436:5: lv_chargeSeconds_13_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getUfoRaidConfigAccess().getChargeSecondsDOUBLEParserRuleCall_12_0());
            				
            pushFollow(FOLLOW_142);
            lv_chargeSeconds_13_0=ruleDOUBLE();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getUfoRaidConfigRule());
            					}
            					set(
            						current,
            						"chargeSeconds",
            						lv_chargeSeconds_13_0,
            						"main.game.maze.dsl.MazeDsl.DOUBLE");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_14=(Token)match(input,138,FOLLOW_18); 

            			newLeafNode(otherlv_14, grammarAccess.getUfoRaidConfigAccess().getBeamSecondsKeyword_13());
            		
            // InternalMazeDsl.g:4457:3: ( (lv_beamSeconds_15_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:4458:4: (lv_beamSeconds_15_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:4458:4: (lv_beamSeconds_15_0= ruleDOUBLE )
            // InternalMazeDsl.g:4459:5: lv_beamSeconds_15_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getUfoRaidConfigAccess().getBeamSecondsDOUBLEParserRuleCall_14_0());
            				
            pushFollow(FOLLOW_143);
            lv_beamSeconds_15_0=ruleDOUBLE();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getUfoRaidConfigRule());
            					}
            					set(
            						current,
            						"beamSeconds",
            						lv_beamSeconds_15_0,
            						"main.game.maze.dsl.MazeDsl.DOUBLE");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_16=(Token)match(input,139,FOLLOW_18); 

            			newLeafNode(otherlv_16, grammarAccess.getUfoRaidConfigAccess().getBeamRadiusKeyword_15());
            		
            // InternalMazeDsl.g:4480:3: ( (lv_beamRadius_17_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:4481:4: (lv_beamRadius_17_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:4481:4: (lv_beamRadius_17_0= ruleDOUBLE )
            // InternalMazeDsl.g:4482:5: lv_beamRadius_17_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getUfoRaidConfigAccess().getBeamRadiusDOUBLEParserRuleCall_16_0());
            				
            pushFollow(FOLLOW_144);
            lv_beamRadius_17_0=ruleDOUBLE();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getUfoRaidConfigRule());
            					}
            					set(
            						current,
            						"beamRadius",
            						lv_beamRadius_17_0,
            						"main.game.maze.dsl.MazeDsl.DOUBLE");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_18=(Token)match(input,140,FOLLOW_18); 

            			newLeafNode(otherlv_18, grammarAccess.getUfoRaidConfigAccess().getAimMinKeyword_17());
            		
            // InternalMazeDsl.g:4503:3: ( (lv_aimMinDistance_19_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:4504:4: (lv_aimMinDistance_19_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:4504:4: (lv_aimMinDistance_19_0= ruleDOUBLE )
            // InternalMazeDsl.g:4505:5: lv_aimMinDistance_19_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getUfoRaidConfigAccess().getAimMinDistanceDOUBLEParserRuleCall_18_0());
            				
            pushFollow(FOLLOW_145);
            lv_aimMinDistance_19_0=ruleDOUBLE();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getUfoRaidConfigRule());
            					}
            					set(
            						current,
            						"aimMinDistance",
            						lv_aimMinDistance_19_0,
            						"main.game.maze.dsl.MazeDsl.DOUBLE");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_20=(Token)match(input,141,FOLLOW_18); 

            			newLeafNode(otherlv_20, grammarAccess.getUfoRaidConfigAccess().getAimMaxKeyword_19());
            		
            // InternalMazeDsl.g:4526:3: ( (lv_aimMaxDistance_21_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:4527:4: (lv_aimMaxDistance_21_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:4527:4: (lv_aimMaxDistance_21_0= ruleDOUBLE )
            // InternalMazeDsl.g:4528:5: lv_aimMaxDistance_21_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getUfoRaidConfigAccess().getAimMaxDistanceDOUBLEParserRuleCall_20_0());
            				
            pushFollow(FOLLOW_146);
            lv_aimMaxDistance_21_0=ruleDOUBLE();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getUfoRaidConfigRule());
            					}
            					set(
            						current,
            						"aimMaxDistance",
            						lv_aimMaxDistance_21_0,
            						"main.game.maze.dsl.MazeDsl.DOUBLE");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_22=(Token)match(input,142,FOLLOW_18); 

            			newLeafNode(otherlv_22, grammarAccess.getUfoRaidConfigAccess().getDamageFractionKeyword_21());
            		
            // InternalMazeDsl.g:4549:3: ( (lv_damageFraction_23_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:4550:4: (lv_damageFraction_23_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:4550:4: (lv_damageFraction_23_0= ruleDOUBLE )
            // InternalMazeDsl.g:4551:5: lv_damageFraction_23_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getUfoRaidConfigAccess().getDamageFractionDOUBLEParserRuleCall_22_0());
            				
            pushFollow(FOLLOW_147);
            lv_damageFraction_23_0=ruleDOUBLE();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getUfoRaidConfigRule());
            					}
            					set(
            						current,
            						"damageFraction",
            						lv_damageFraction_23_0,
            						"main.game.maze.dsl.MazeDsl.DOUBLE");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_24=(Token)match(input,143,FOLLOW_37); 

            			newLeafNode(otherlv_24, grammarAccess.getUfoRaidConfigAccess().getSoldiersKeyword_23());
            		
            // InternalMazeDsl.g:4572:3: ( (lv_soldiers_25_0= RULE_INT ) )
            // InternalMazeDsl.g:4573:4: (lv_soldiers_25_0= RULE_INT )
            {
            // InternalMazeDsl.g:4573:4: (lv_soldiers_25_0= RULE_INT )
            // InternalMazeDsl.g:4574:5: lv_soldiers_25_0= RULE_INT
            {
            lv_soldiers_25_0=(Token)match(input,RULE_INT,FOLLOW_148); 

            					newLeafNode(lv_soldiers_25_0, grammarAccess.getUfoRaidConfigAccess().getSoldiersINTTerminalRuleCall_24_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getUfoRaidConfigRule());
            					}
            					setWithLastConsumed(
            						current,
            						"soldiers",
            						lv_soldiers_25_0,
            						"org.eclipse.xtext.common.Terminals.INT");
            				

            }


            }

            otherlv_26=(Token)match(input,144,FOLLOW_37); 

            			newLeafNode(otherlv_26, grammarAccess.getUfoRaidConfigAccess().getSoldierHealthKeyword_25());
            		
            // InternalMazeDsl.g:4594:3: ( (lv_soldierHealth_27_0= RULE_INT ) )
            // InternalMazeDsl.g:4595:4: (lv_soldierHealth_27_0= RULE_INT )
            {
            // InternalMazeDsl.g:4595:4: (lv_soldierHealth_27_0= RULE_INT )
            // InternalMazeDsl.g:4596:5: lv_soldierHealth_27_0= RULE_INT
            {
            lv_soldierHealth_27_0=(Token)match(input,RULE_INT,FOLLOW_149); 

            					newLeafNode(lv_soldierHealth_27_0, grammarAccess.getUfoRaidConfigAccess().getSoldierHealthINTTerminalRuleCall_26_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getUfoRaidConfigRule());
            					}
            					setWithLastConsumed(
            						current,
            						"soldierHealth",
            						lv_soldierHealth_27_0,
            						"org.eclipse.xtext.common.Terminals.INT");
            				

            }


            }

            otherlv_28=(Token)match(input,145,FOLLOW_37); 

            			newLeafNode(otherlv_28, grammarAccess.getUfoRaidConfigAccess().getSoldierDamageKeyword_27());
            		
            // InternalMazeDsl.g:4616:3: ( (lv_soldierDamage_29_0= RULE_INT ) )
            // InternalMazeDsl.g:4617:4: (lv_soldierDamage_29_0= RULE_INT )
            {
            // InternalMazeDsl.g:4617:4: (lv_soldierDamage_29_0= RULE_INT )
            // InternalMazeDsl.g:4618:5: lv_soldierDamage_29_0= RULE_INT
            {
            lv_soldierDamage_29_0=(Token)match(input,RULE_INT,FOLLOW_150); 

            					newLeafNode(lv_soldierDamage_29_0, grammarAccess.getUfoRaidConfigAccess().getSoldierDamageINTTerminalRuleCall_28_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getUfoRaidConfigRule());
            					}
            					setWithLastConsumed(
            						current,
            						"soldierDamage",
            						lv_soldierDamage_29_0,
            						"org.eclipse.xtext.common.Terminals.INT");
            				

            }


            }

            otherlv_30=(Token)match(input,146,FOLLOW_18); 

            			newLeafNode(otherlv_30, grammarAccess.getUfoRaidConfigAccess().getSoldierSightKeyword_29());
            		
            // InternalMazeDsl.g:4638:3: ( (lv_soldierSight_31_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:4639:4: (lv_soldierSight_31_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:4639:4: (lv_soldierSight_31_0= ruleDOUBLE )
            // InternalMazeDsl.g:4640:5: lv_soldierSight_31_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getUfoRaidConfigAccess().getSoldierSightDOUBLEParserRuleCall_30_0());
            				
            pushFollow(FOLLOW_151);
            lv_soldierSight_31_0=ruleDOUBLE();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getUfoRaidConfigRule());
            					}
            					set(
            						current,
            						"soldierSight",
            						lv_soldierSight_31_0,
            						"main.game.maze.dsl.MazeDsl.DOUBLE");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_32=(Token)match(input,147,FOLLOW_18); 

            			newLeafNode(otherlv_32, grammarAccess.getUfoRaidConfigAccess().getSoldierSecondsKeyword_31());
            		
            // InternalMazeDsl.g:4661:3: ( (lv_soldierSeconds_33_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:4662:4: (lv_soldierSeconds_33_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:4662:4: (lv_soldierSeconds_33_0= ruleDOUBLE )
            // InternalMazeDsl.g:4663:5: lv_soldierSeconds_33_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getUfoRaidConfigAccess().getSoldierSecondsDOUBLEParserRuleCall_32_0());
            				
            pushFollow(FOLLOW_152);
            lv_soldierSeconds_33_0=ruleDOUBLE();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getUfoRaidConfigRule());
            					}
            					set(
            						current,
            						"soldierSeconds",
            						lv_soldierSeconds_33_0,
            						"main.game.maze.dsl.MazeDsl.DOUBLE");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalMazeDsl.g:4680:3: (otherlv_34= 'banner' ( (lv_bannerText_35_0= RULE_STRING ) ) )?
            int alt79=2;
            int LA79_0 = input.LA(1);

            if ( (LA79_0==148) ) {
                alt79=1;
            }
            switch (alt79) {
                case 1 :
                    // InternalMazeDsl.g:4681:4: otherlv_34= 'banner' ( (lv_bannerText_35_0= RULE_STRING ) )
                    {
                    otherlv_34=(Token)match(input,148,FOLLOW_12); 

                    				newLeafNode(otherlv_34, grammarAccess.getUfoRaidConfigAccess().getBannerKeyword_33_0());
                    			
                    // InternalMazeDsl.g:4685:4: ( (lv_bannerText_35_0= RULE_STRING ) )
                    // InternalMazeDsl.g:4686:5: (lv_bannerText_35_0= RULE_STRING )
                    {
                    // InternalMazeDsl.g:4686:5: (lv_bannerText_35_0= RULE_STRING )
                    // InternalMazeDsl.g:4687:6: lv_bannerText_35_0= RULE_STRING
                    {
                    lv_bannerText_35_0=(Token)match(input,RULE_STRING,FOLLOW_153); 

                    						newLeafNode(lv_bannerText_35_0, grammarAccess.getUfoRaidConfigAccess().getBannerTextSTRINGTerminalRuleCall_33_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getUfoRaidConfigRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"bannerText",
                    							lv_bannerText_35_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_36=(Token)match(input,149,FOLLOW_12); 

            			newLeafNode(otherlv_36, grammarAccess.getUfoRaidConfigAccess().getUfoSpriteKeyword_34());
            		
            // InternalMazeDsl.g:4708:3: ( (lv_ufoSprite_37_0= RULE_STRING ) )
            // InternalMazeDsl.g:4709:4: (lv_ufoSprite_37_0= RULE_STRING )
            {
            // InternalMazeDsl.g:4709:4: (lv_ufoSprite_37_0= RULE_STRING )
            // InternalMazeDsl.g:4710:5: lv_ufoSprite_37_0= RULE_STRING
            {
            lv_ufoSprite_37_0=(Token)match(input,RULE_STRING,FOLLOW_154); 

            					newLeafNode(lv_ufoSprite_37_0, grammarAccess.getUfoRaidConfigAccess().getUfoSpriteSTRINGTerminalRuleCall_35_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getUfoRaidConfigRule());
            					}
            					setWithLastConsumed(
            						current,
            						"ufoSprite",
            						lv_ufoSprite_37_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            otherlv_38=(Token)match(input,150,FOLLOW_12); 

            			newLeafNode(otherlv_38, grammarAccess.getUfoRaidConfigAccess().getBeamSpriteKeyword_36());
            		
            // InternalMazeDsl.g:4730:3: ( (lv_beamSprite_39_0= RULE_STRING ) )
            // InternalMazeDsl.g:4731:4: (lv_beamSprite_39_0= RULE_STRING )
            {
            // InternalMazeDsl.g:4731:4: (lv_beamSprite_39_0= RULE_STRING )
            // InternalMazeDsl.g:4732:5: lv_beamSprite_39_0= RULE_STRING
            {
            lv_beamSprite_39_0=(Token)match(input,RULE_STRING,FOLLOW_155); 

            					newLeafNode(lv_beamSprite_39_0, grammarAccess.getUfoRaidConfigAccess().getBeamSpriteSTRINGTerminalRuleCall_37_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getUfoRaidConfigRule());
            					}
            					setWithLastConsumed(
            						current,
            						"beamSprite",
            						lv_beamSprite_39_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            // InternalMazeDsl.g:4748:3: (otherlv_40= 'arriveSound' ( (lv_arriveSound_41_0= RULE_STRING ) ) )?
            int alt80=2;
            int LA80_0 = input.LA(1);

            if ( (LA80_0==151) ) {
                alt80=1;
            }
            switch (alt80) {
                case 1 :
                    // InternalMazeDsl.g:4749:4: otherlv_40= 'arriveSound' ( (lv_arriveSound_41_0= RULE_STRING ) )
                    {
                    otherlv_40=(Token)match(input,151,FOLLOW_12); 

                    				newLeafNode(otherlv_40, grammarAccess.getUfoRaidConfigAccess().getArriveSoundKeyword_38_0());
                    			
                    // InternalMazeDsl.g:4753:4: ( (lv_arriveSound_41_0= RULE_STRING ) )
                    // InternalMazeDsl.g:4754:5: (lv_arriveSound_41_0= RULE_STRING )
                    {
                    // InternalMazeDsl.g:4754:5: (lv_arriveSound_41_0= RULE_STRING )
                    // InternalMazeDsl.g:4755:6: lv_arriveSound_41_0= RULE_STRING
                    {
                    lv_arriveSound_41_0=(Token)match(input,RULE_STRING,FOLLOW_156); 

                    						newLeafNode(lv_arriveSound_41_0, grammarAccess.getUfoRaidConfigAccess().getArriveSoundSTRINGTerminalRuleCall_38_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getUfoRaidConfigRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"arriveSound",
                    							lv_arriveSound_41_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeDsl.g:4772:3: (otherlv_42= 'beamSound' ( (lv_beamSound_43_0= RULE_STRING ) ) )?
            int alt81=2;
            int LA81_0 = input.LA(1);

            if ( (LA81_0==152) ) {
                alt81=1;
            }
            switch (alt81) {
                case 1 :
                    // InternalMazeDsl.g:4773:4: otherlv_42= 'beamSound' ( (lv_beamSound_43_0= RULE_STRING ) )
                    {
                    otherlv_42=(Token)match(input,152,FOLLOW_12); 

                    				newLeafNode(otherlv_42, grammarAccess.getUfoRaidConfigAccess().getBeamSoundKeyword_39_0());
                    			
                    // InternalMazeDsl.g:4777:4: ( (lv_beamSound_43_0= RULE_STRING ) )
                    // InternalMazeDsl.g:4778:5: (lv_beamSound_43_0= RULE_STRING )
                    {
                    // InternalMazeDsl.g:4778:5: (lv_beamSound_43_0= RULE_STRING )
                    // InternalMazeDsl.g:4779:6: lv_beamSound_43_0= RULE_STRING
                    {
                    lv_beamSound_43_0=(Token)match(input,RULE_STRING,FOLLOW_11); 

                    						newLeafNode(lv_beamSound_43_0, grammarAccess.getUfoRaidConfigAccess().getBeamSoundSTRINGTerminalRuleCall_39_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getUfoRaidConfigRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"beamSound",
                    							lv_beamSound_43_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_44=(Token)match(input,13,FOLLOW_2); 

            			newLeafNode(otherlv_44, grammarAccess.getUfoRaidConfigAccess().getRightCurlyBracketKeyword_40());
            		

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
    // $ANTLR end "ruleUfoRaidConfig"


    // $ANTLR start "entryRuleShopConfig"
    // InternalMazeDsl.g:4804:1: entryRuleShopConfig returns [EObject current=null] : iv_ruleShopConfig= ruleShopConfig EOF ;
    public final EObject entryRuleShopConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleShopConfig = null;


        try {
            // InternalMazeDsl.g:4804:51: (iv_ruleShopConfig= ruleShopConfig EOF )
            // InternalMazeDsl.g:4805:2: iv_ruleShopConfig= ruleShopConfig EOF
            {
             newCompositeNode(grammarAccess.getShopConfigRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleShopConfig=ruleShopConfig();

            state._fsp--;

             current =iv_ruleShopConfig; 
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
    // $ANTLR end "entryRuleShopConfig"


    // $ANTLR start "ruleShopConfig"
    // InternalMazeDsl.g:4811:1: ruleShopConfig returns [EObject current=null] : (otherlv_0= 'shop' otherlv_1= '{' ( (lv_unlocks_2_0= ruleShopUnlockConfig ) )* ( (lv_items_3_0= ruleShopItemConfig ) )* otherlv_4= '}' ) ;
    public final EObject ruleShopConfig() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_4=null;
        EObject lv_unlocks_2_0 = null;

        EObject lv_items_3_0 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:4817:2: ( (otherlv_0= 'shop' otherlv_1= '{' ( (lv_unlocks_2_0= ruleShopUnlockConfig ) )* ( (lv_items_3_0= ruleShopItemConfig ) )* otherlv_4= '}' ) )
            // InternalMazeDsl.g:4818:2: (otherlv_0= 'shop' otherlv_1= '{' ( (lv_unlocks_2_0= ruleShopUnlockConfig ) )* ( (lv_items_3_0= ruleShopItemConfig ) )* otherlv_4= '}' )
            {
            // InternalMazeDsl.g:4818:2: (otherlv_0= 'shop' otherlv_1= '{' ( (lv_unlocks_2_0= ruleShopUnlockConfig ) )* ( (lv_items_3_0= ruleShopItemConfig ) )* otherlv_4= '}' )
            // InternalMazeDsl.g:4819:3: otherlv_0= 'shop' otherlv_1= '{' ( (lv_unlocks_2_0= ruleShopUnlockConfig ) )* ( (lv_items_3_0= ruleShopItemConfig ) )* otherlv_4= '}'
            {
            otherlv_0=(Token)match(input,153,FOLLOW_4); 

            			newLeafNode(otherlv_0, grammarAccess.getShopConfigAccess().getShopKeyword_0());
            		
            otherlv_1=(Token)match(input,12,FOLLOW_157); 

            			newLeafNode(otherlv_1, grammarAccess.getShopConfigAccess().getLeftCurlyBracketKeyword_1());
            		
            // InternalMazeDsl.g:4827:3: ( (lv_unlocks_2_0= ruleShopUnlockConfig ) )*
            loop82:
            do {
                int alt82=2;
                int LA82_0 = input.LA(1);

                if ( (LA82_0==154) ) {
                    alt82=1;
                }


                switch (alt82) {
            	case 1 :
            	    // InternalMazeDsl.g:4828:4: (lv_unlocks_2_0= ruleShopUnlockConfig )
            	    {
            	    // InternalMazeDsl.g:4828:4: (lv_unlocks_2_0= ruleShopUnlockConfig )
            	    // InternalMazeDsl.g:4829:5: lv_unlocks_2_0= ruleShopUnlockConfig
            	    {

            	    					newCompositeNode(grammarAccess.getShopConfigAccess().getUnlocksShopUnlockConfigParserRuleCall_2_0());
            	    				
            	    pushFollow(FOLLOW_157);
            	    lv_unlocks_2_0=ruleShopUnlockConfig();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getShopConfigRule());
            	    					}
            	    					add(
            	    						current,
            	    						"unlocks",
            	    						lv_unlocks_2_0,
            	    						"main.game.maze.dsl.MazeDsl.ShopUnlockConfig");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop82;
                }
            } while (true);

            // InternalMazeDsl.g:4846:3: ( (lv_items_3_0= ruleShopItemConfig ) )*
            loop83:
            do {
                int alt83=2;
                int LA83_0 = input.LA(1);

                if ( (LA83_0==62) ) {
                    alt83=1;
                }


                switch (alt83) {
            	case 1 :
            	    // InternalMazeDsl.g:4847:4: (lv_items_3_0= ruleShopItemConfig )
            	    {
            	    // InternalMazeDsl.g:4847:4: (lv_items_3_0= ruleShopItemConfig )
            	    // InternalMazeDsl.g:4848:5: lv_items_3_0= ruleShopItemConfig
            	    {

            	    					newCompositeNode(grammarAccess.getShopConfigAccess().getItemsShopItemConfigParserRuleCall_3_0());
            	    				
            	    pushFollow(FOLLOW_158);
            	    lv_items_3_0=ruleShopItemConfig();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getShopConfigRule());
            	    					}
            	    					add(
            	    						current,
            	    						"items",
            	    						lv_items_3_0,
            	    						"main.game.maze.dsl.MazeDsl.ShopItemConfig");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop83;
                }
            } while (true);

            otherlv_4=(Token)match(input,13,FOLLOW_2); 

            			newLeafNode(otherlv_4, grammarAccess.getShopConfigAccess().getRightCurlyBracketKeyword_4());
            		

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
    // $ANTLR end "ruleShopConfig"


    // $ANTLR start "entryRuleShopUnlockConfig"
    // InternalMazeDsl.g:4873:1: entryRuleShopUnlockConfig returns [EObject current=null] : iv_ruleShopUnlockConfig= ruleShopUnlockConfig EOF ;
    public final EObject entryRuleShopUnlockConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleShopUnlockConfig = null;


        try {
            // InternalMazeDsl.g:4873:57: (iv_ruleShopUnlockConfig= ruleShopUnlockConfig EOF )
            // InternalMazeDsl.g:4874:2: iv_ruleShopUnlockConfig= ruleShopUnlockConfig EOF
            {
             newCompositeNode(grammarAccess.getShopUnlockConfigRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleShopUnlockConfig=ruleShopUnlockConfig();

            state._fsp--;

             current =iv_ruleShopUnlockConfig; 
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
    // $ANTLR end "entryRuleShopUnlockConfig"


    // $ANTLR start "ruleShopUnlockConfig"
    // InternalMazeDsl.g:4880:1: ruleShopUnlockConfig returns [EObject current=null] : (otherlv_0= 'unlock' ( (lv_tier_1_0= ruleValidID ) ) otherlv_2= 'fromLevel' ( (lv_level_3_0= RULE_INT ) ) ) ;
    public final EObject ruleShopUnlockConfig() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token lv_level_3_0=null;
        AntlrDatatypeRuleToken lv_tier_1_0 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:4886:2: ( (otherlv_0= 'unlock' ( (lv_tier_1_0= ruleValidID ) ) otherlv_2= 'fromLevel' ( (lv_level_3_0= RULE_INT ) ) ) )
            // InternalMazeDsl.g:4887:2: (otherlv_0= 'unlock' ( (lv_tier_1_0= ruleValidID ) ) otherlv_2= 'fromLevel' ( (lv_level_3_0= RULE_INT ) ) )
            {
            // InternalMazeDsl.g:4887:2: (otherlv_0= 'unlock' ( (lv_tier_1_0= ruleValidID ) ) otherlv_2= 'fromLevel' ( (lv_level_3_0= RULE_INT ) ) )
            // InternalMazeDsl.g:4888:3: otherlv_0= 'unlock' ( (lv_tier_1_0= ruleValidID ) ) otherlv_2= 'fromLevel' ( (lv_level_3_0= RULE_INT ) )
            {
            otherlv_0=(Token)match(input,154,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getShopUnlockConfigAccess().getUnlockKeyword_0());
            		
            // InternalMazeDsl.g:4892:3: ( (lv_tier_1_0= ruleValidID ) )
            // InternalMazeDsl.g:4893:4: (lv_tier_1_0= ruleValidID )
            {
            // InternalMazeDsl.g:4893:4: (lv_tier_1_0= ruleValidID )
            // InternalMazeDsl.g:4894:5: lv_tier_1_0= ruleValidID
            {

            					newCompositeNode(grammarAccess.getShopUnlockConfigAccess().getTierValidIDParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_159);
            lv_tier_1_0=ruleValidID();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getShopUnlockConfigRule());
            					}
            					set(
            						current,
            						"tier",
            						lv_tier_1_0,
            						"main.game.maze.dsl.MazeDsl.ValidID");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_2=(Token)match(input,155,FOLLOW_37); 

            			newLeafNode(otherlv_2, grammarAccess.getShopUnlockConfigAccess().getFromLevelKeyword_2());
            		
            // InternalMazeDsl.g:4915:3: ( (lv_level_3_0= RULE_INT ) )
            // InternalMazeDsl.g:4916:4: (lv_level_3_0= RULE_INT )
            {
            // InternalMazeDsl.g:4916:4: (lv_level_3_0= RULE_INT )
            // InternalMazeDsl.g:4917:5: lv_level_3_0= RULE_INT
            {
            lv_level_3_0=(Token)match(input,RULE_INT,FOLLOW_2); 

            					newLeafNode(lv_level_3_0, grammarAccess.getShopUnlockConfigAccess().getLevelINTTerminalRuleCall_3_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getShopUnlockConfigRule());
            					}
            					setWithLastConsumed(
            						current,
            						"level",
            						lv_level_3_0,
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
    // $ANTLR end "ruleShopUnlockConfig"


    // $ANTLR start "entryRuleShopItemConfig"
    // InternalMazeDsl.g:4937:1: entryRuleShopItemConfig returns [EObject current=null] : iv_ruleShopItemConfig= ruleShopItemConfig EOF ;
    public final EObject entryRuleShopItemConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleShopItemConfig = null;


        try {
            // InternalMazeDsl.g:4937:55: (iv_ruleShopItemConfig= ruleShopItemConfig EOF )
            // InternalMazeDsl.g:4938:2: iv_ruleShopItemConfig= ruleShopItemConfig EOF
            {
             newCompositeNode(grammarAccess.getShopItemConfigRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleShopItemConfig=ruleShopItemConfig();

            state._fsp--;

             current =iv_ruleShopItemConfig; 
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
    // $ANTLR end "entryRuleShopItemConfig"


    // $ANTLR start "ruleShopItemConfig"
    // InternalMazeDsl.g:4944:1: ruleShopItemConfig returns [EObject current=null] : (otherlv_0= 'item' ( (lv_kind_1_0= ruleValidID ) ) ( (lv_displayName_2_0= RULE_STRING ) ) otherlv_3= 'tier' ( (lv_tier_4_0= ruleValidID ) ) otherlv_5= 'price' ( (lv_price_6_0= RULE_INT ) ) (otherlv_7= 'amount' ( (lv_amount_8_0= ruleINT_OBJECT ) ) )? (otherlv_9= 'factor' ( (lv_factor_10_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_11= 'description' ( (lv_description_12_0= RULE_STRING ) ) )? ) ;
    public final EObject ruleShopItemConfig() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_displayName_2_0=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token lv_price_6_0=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        Token otherlv_11=null;
        Token lv_description_12_0=null;
        AntlrDatatypeRuleToken lv_kind_1_0 = null;

        AntlrDatatypeRuleToken lv_tier_4_0 = null;

        AntlrDatatypeRuleToken lv_amount_8_0 = null;

        AntlrDatatypeRuleToken lv_factor_10_0 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:4950:2: ( (otherlv_0= 'item' ( (lv_kind_1_0= ruleValidID ) ) ( (lv_displayName_2_0= RULE_STRING ) ) otherlv_3= 'tier' ( (lv_tier_4_0= ruleValidID ) ) otherlv_5= 'price' ( (lv_price_6_0= RULE_INT ) ) (otherlv_7= 'amount' ( (lv_amount_8_0= ruleINT_OBJECT ) ) )? (otherlv_9= 'factor' ( (lv_factor_10_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_11= 'description' ( (lv_description_12_0= RULE_STRING ) ) )? ) )
            // InternalMazeDsl.g:4951:2: (otherlv_0= 'item' ( (lv_kind_1_0= ruleValidID ) ) ( (lv_displayName_2_0= RULE_STRING ) ) otherlv_3= 'tier' ( (lv_tier_4_0= ruleValidID ) ) otherlv_5= 'price' ( (lv_price_6_0= RULE_INT ) ) (otherlv_7= 'amount' ( (lv_amount_8_0= ruleINT_OBJECT ) ) )? (otherlv_9= 'factor' ( (lv_factor_10_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_11= 'description' ( (lv_description_12_0= RULE_STRING ) ) )? )
            {
            // InternalMazeDsl.g:4951:2: (otherlv_0= 'item' ( (lv_kind_1_0= ruleValidID ) ) ( (lv_displayName_2_0= RULE_STRING ) ) otherlv_3= 'tier' ( (lv_tier_4_0= ruleValidID ) ) otherlv_5= 'price' ( (lv_price_6_0= RULE_INT ) ) (otherlv_7= 'amount' ( (lv_amount_8_0= ruleINT_OBJECT ) ) )? (otherlv_9= 'factor' ( (lv_factor_10_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_11= 'description' ( (lv_description_12_0= RULE_STRING ) ) )? )
            // InternalMazeDsl.g:4952:3: otherlv_0= 'item' ( (lv_kind_1_0= ruleValidID ) ) ( (lv_displayName_2_0= RULE_STRING ) ) otherlv_3= 'tier' ( (lv_tier_4_0= ruleValidID ) ) otherlv_5= 'price' ( (lv_price_6_0= RULE_INT ) ) (otherlv_7= 'amount' ( (lv_amount_8_0= ruleINT_OBJECT ) ) )? (otherlv_9= 'factor' ( (lv_factor_10_0= ruleDOUBLE_OBJECT ) ) )? (otherlv_11= 'description' ( (lv_description_12_0= RULE_STRING ) ) )?
            {
            otherlv_0=(Token)match(input,62,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getShopItemConfigAccess().getItemKeyword_0());
            		
            // InternalMazeDsl.g:4956:3: ( (lv_kind_1_0= ruleValidID ) )
            // InternalMazeDsl.g:4957:4: (lv_kind_1_0= ruleValidID )
            {
            // InternalMazeDsl.g:4957:4: (lv_kind_1_0= ruleValidID )
            // InternalMazeDsl.g:4958:5: lv_kind_1_0= ruleValidID
            {

            					newCompositeNode(grammarAccess.getShopItemConfigAccess().getKindValidIDParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_12);
            lv_kind_1_0=ruleValidID();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getShopItemConfigRule());
            					}
            					set(
            						current,
            						"kind",
            						lv_kind_1_0,
            						"main.game.maze.dsl.MazeDsl.ValidID");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalMazeDsl.g:4975:3: ( (lv_displayName_2_0= RULE_STRING ) )
            // InternalMazeDsl.g:4976:4: (lv_displayName_2_0= RULE_STRING )
            {
            // InternalMazeDsl.g:4976:4: (lv_displayName_2_0= RULE_STRING )
            // InternalMazeDsl.g:4977:5: lv_displayName_2_0= RULE_STRING
            {
            lv_displayName_2_0=(Token)match(input,RULE_STRING,FOLLOW_160); 

            					newLeafNode(lv_displayName_2_0, grammarAccess.getShopItemConfigAccess().getDisplayNameSTRINGTerminalRuleCall_2_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getShopItemConfigRule());
            					}
            					setWithLastConsumed(
            						current,
            						"displayName",
            						lv_displayName_2_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            otherlv_3=(Token)match(input,156,FOLLOW_3); 

            			newLeafNode(otherlv_3, grammarAccess.getShopItemConfigAccess().getTierKeyword_3());
            		
            // InternalMazeDsl.g:4997:3: ( (lv_tier_4_0= ruleValidID ) )
            // InternalMazeDsl.g:4998:4: (lv_tier_4_0= ruleValidID )
            {
            // InternalMazeDsl.g:4998:4: (lv_tier_4_0= ruleValidID )
            // InternalMazeDsl.g:4999:5: lv_tier_4_0= ruleValidID
            {

            					newCompositeNode(grammarAccess.getShopItemConfigAccess().getTierValidIDParserRuleCall_4_0());
            				
            pushFollow(FOLLOW_161);
            lv_tier_4_0=ruleValidID();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getShopItemConfigRule());
            					}
            					set(
            						current,
            						"tier",
            						lv_tier_4_0,
            						"main.game.maze.dsl.MazeDsl.ValidID");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_5=(Token)match(input,157,FOLLOW_37); 

            			newLeafNode(otherlv_5, grammarAccess.getShopItemConfigAccess().getPriceKeyword_5());
            		
            // InternalMazeDsl.g:5020:3: ( (lv_price_6_0= RULE_INT ) )
            // InternalMazeDsl.g:5021:4: (lv_price_6_0= RULE_INT )
            {
            // InternalMazeDsl.g:5021:4: (lv_price_6_0= RULE_INT )
            // InternalMazeDsl.g:5022:5: lv_price_6_0= RULE_INT
            {
            lv_price_6_0=(Token)match(input,RULE_INT,FOLLOW_162); 

            					newLeafNode(lv_price_6_0, grammarAccess.getShopItemConfigAccess().getPriceINTTerminalRuleCall_6_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getShopItemConfigRule());
            					}
            					setWithLastConsumed(
            						current,
            						"price",
            						lv_price_6_0,
            						"org.eclipse.xtext.common.Terminals.INT");
            				

            }


            }

            // InternalMazeDsl.g:5038:3: (otherlv_7= 'amount' ( (lv_amount_8_0= ruleINT_OBJECT ) ) )?
            int alt84=2;
            int LA84_0 = input.LA(1);

            if ( (LA84_0==158) ) {
                alt84=1;
            }
            switch (alt84) {
                case 1 :
                    // InternalMazeDsl.g:5039:4: otherlv_7= 'amount' ( (lv_amount_8_0= ruleINT_OBJECT ) )
                    {
                    otherlv_7=(Token)match(input,158,FOLLOW_18); 

                    				newLeafNode(otherlv_7, grammarAccess.getShopItemConfigAccess().getAmountKeyword_7_0());
                    			
                    // InternalMazeDsl.g:5043:4: ( (lv_amount_8_0= ruleINT_OBJECT ) )
                    // InternalMazeDsl.g:5044:5: (lv_amount_8_0= ruleINT_OBJECT )
                    {
                    // InternalMazeDsl.g:5044:5: (lv_amount_8_0= ruleINT_OBJECT )
                    // InternalMazeDsl.g:5045:6: lv_amount_8_0= ruleINT_OBJECT
                    {

                    						newCompositeNode(grammarAccess.getShopItemConfigAccess().getAmountINT_OBJECTParserRuleCall_7_1_0());
                    					
                    pushFollow(FOLLOW_163);
                    lv_amount_8_0=ruleINT_OBJECT();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getShopItemConfigRule());
                    						}
                    						set(
                    							current,
                    							"amount",
                    							lv_amount_8_0,
                    							"main.game.maze.dsl.MazeDsl.INT_OBJECT");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeDsl.g:5063:3: (otherlv_9= 'factor' ( (lv_factor_10_0= ruleDOUBLE_OBJECT ) ) )?
            int alt85=2;
            int LA85_0 = input.LA(1);

            if ( (LA85_0==159) ) {
                alt85=1;
            }
            switch (alt85) {
                case 1 :
                    // InternalMazeDsl.g:5064:4: otherlv_9= 'factor' ( (lv_factor_10_0= ruleDOUBLE_OBJECT ) )
                    {
                    otherlv_9=(Token)match(input,159,FOLLOW_18); 

                    				newLeafNode(otherlv_9, grammarAccess.getShopItemConfigAccess().getFactorKeyword_8_0());
                    			
                    // InternalMazeDsl.g:5068:4: ( (lv_factor_10_0= ruleDOUBLE_OBJECT ) )
                    // InternalMazeDsl.g:5069:5: (lv_factor_10_0= ruleDOUBLE_OBJECT )
                    {
                    // InternalMazeDsl.g:5069:5: (lv_factor_10_0= ruleDOUBLE_OBJECT )
                    // InternalMazeDsl.g:5070:6: lv_factor_10_0= ruleDOUBLE_OBJECT
                    {

                    						newCompositeNode(grammarAccess.getShopItemConfigAccess().getFactorDOUBLE_OBJECTParserRuleCall_8_1_0());
                    					
                    pushFollow(FOLLOW_164);
                    lv_factor_10_0=ruleDOUBLE_OBJECT();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getShopItemConfigRule());
                    						}
                    						set(
                    							current,
                    							"factor",
                    							lv_factor_10_0,
                    							"main.game.maze.dsl.MazeDsl.DOUBLE_OBJECT");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeDsl.g:5088:3: (otherlv_11= 'description' ( (lv_description_12_0= RULE_STRING ) ) )?
            int alt86=2;
            int LA86_0 = input.LA(1);

            if ( (LA86_0==160) ) {
                alt86=1;
            }
            switch (alt86) {
                case 1 :
                    // InternalMazeDsl.g:5089:4: otherlv_11= 'description' ( (lv_description_12_0= RULE_STRING ) )
                    {
                    otherlv_11=(Token)match(input,160,FOLLOW_12); 

                    				newLeafNode(otherlv_11, grammarAccess.getShopItemConfigAccess().getDescriptionKeyword_9_0());
                    			
                    // InternalMazeDsl.g:5093:4: ( (lv_description_12_0= RULE_STRING ) )
                    // InternalMazeDsl.g:5094:5: (lv_description_12_0= RULE_STRING )
                    {
                    // InternalMazeDsl.g:5094:5: (lv_description_12_0= RULE_STRING )
                    // InternalMazeDsl.g:5095:6: lv_description_12_0= RULE_STRING
                    {
                    lv_description_12_0=(Token)match(input,RULE_STRING,FOLLOW_2); 

                    						newLeafNode(lv_description_12_0, grammarAccess.getShopItemConfigAccess().getDescriptionSTRINGTerminalRuleCall_9_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getShopItemConfigRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"description",
                    							lv_description_12_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


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
    // $ANTLR end "ruleShopItemConfig"


    // $ANTLR start "entryRuleMenuThemeConfig"
    // InternalMazeDsl.g:5116:1: entryRuleMenuThemeConfig returns [EObject current=null] : iv_ruleMenuThemeConfig= ruleMenuThemeConfig EOF ;
    public final EObject entryRuleMenuThemeConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMenuThemeConfig = null;


        try {
            // InternalMazeDsl.g:5116:56: (iv_ruleMenuThemeConfig= ruleMenuThemeConfig EOF )
            // InternalMazeDsl.g:5117:2: iv_ruleMenuThemeConfig= ruleMenuThemeConfig EOF
            {
             newCompositeNode(grammarAccess.getMenuThemeConfigRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleMenuThemeConfig=ruleMenuThemeConfig();

            state._fsp--;

             current =iv_ruleMenuThemeConfig; 
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
    // $ANTLR end "entryRuleMenuThemeConfig"


    // $ANTLR start "ruleMenuThemeConfig"
    // InternalMazeDsl.g:5123:1: ruleMenuThemeConfig returns [EObject current=null] : (otherlv_0= 'theme' otherlv_1= '{' otherlv_2= 'font' ( (lv_font_3_0= RULE_STRING ) ) ( (lv_swatches_4_0= ruleThemeSwatchConfig ) )* ( (lv_images_5_0= ruleThemeImageConfig ) )* ( (lv_badges_6_0= ruleThemeBadgeConfig ) )* otherlv_7= '}' ) ;
    public final EObject ruleMenuThemeConfig() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token lv_font_3_0=null;
        Token otherlv_7=null;
        EObject lv_swatches_4_0 = null;

        EObject lv_images_5_0 = null;

        EObject lv_badges_6_0 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:5129:2: ( (otherlv_0= 'theme' otherlv_1= '{' otherlv_2= 'font' ( (lv_font_3_0= RULE_STRING ) ) ( (lv_swatches_4_0= ruleThemeSwatchConfig ) )* ( (lv_images_5_0= ruleThemeImageConfig ) )* ( (lv_badges_6_0= ruleThemeBadgeConfig ) )* otherlv_7= '}' ) )
            // InternalMazeDsl.g:5130:2: (otherlv_0= 'theme' otherlv_1= '{' otherlv_2= 'font' ( (lv_font_3_0= RULE_STRING ) ) ( (lv_swatches_4_0= ruleThemeSwatchConfig ) )* ( (lv_images_5_0= ruleThemeImageConfig ) )* ( (lv_badges_6_0= ruleThemeBadgeConfig ) )* otherlv_7= '}' )
            {
            // InternalMazeDsl.g:5130:2: (otherlv_0= 'theme' otherlv_1= '{' otherlv_2= 'font' ( (lv_font_3_0= RULE_STRING ) ) ( (lv_swatches_4_0= ruleThemeSwatchConfig ) )* ( (lv_images_5_0= ruleThemeImageConfig ) )* ( (lv_badges_6_0= ruleThemeBadgeConfig ) )* otherlv_7= '}' )
            // InternalMazeDsl.g:5131:3: otherlv_0= 'theme' otherlv_1= '{' otherlv_2= 'font' ( (lv_font_3_0= RULE_STRING ) ) ( (lv_swatches_4_0= ruleThemeSwatchConfig ) )* ( (lv_images_5_0= ruleThemeImageConfig ) )* ( (lv_badges_6_0= ruleThemeBadgeConfig ) )* otherlv_7= '}'
            {
            otherlv_0=(Token)match(input,161,FOLLOW_4); 

            			newLeafNode(otherlv_0, grammarAccess.getMenuThemeConfigAccess().getThemeKeyword_0());
            		
            otherlv_1=(Token)match(input,12,FOLLOW_165); 

            			newLeafNode(otherlv_1, grammarAccess.getMenuThemeConfigAccess().getLeftCurlyBracketKeyword_1());
            		
            otherlv_2=(Token)match(input,162,FOLLOW_12); 

            			newLeafNode(otherlv_2, grammarAccess.getMenuThemeConfigAccess().getFontKeyword_2());
            		
            // InternalMazeDsl.g:5143:3: ( (lv_font_3_0= RULE_STRING ) )
            // InternalMazeDsl.g:5144:4: (lv_font_3_0= RULE_STRING )
            {
            // InternalMazeDsl.g:5144:4: (lv_font_3_0= RULE_STRING )
            // InternalMazeDsl.g:5145:5: lv_font_3_0= RULE_STRING
            {
            lv_font_3_0=(Token)match(input,RULE_STRING,FOLLOW_166); 

            					newLeafNode(lv_font_3_0, grammarAccess.getMenuThemeConfigAccess().getFontSTRINGTerminalRuleCall_3_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getMenuThemeConfigRule());
            					}
            					setWithLastConsumed(
            						current,
            						"font",
            						lv_font_3_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            // InternalMazeDsl.g:5161:3: ( (lv_swatches_4_0= ruleThemeSwatchConfig ) )*
            loop87:
            do {
                int alt87=2;
                int LA87_0 = input.LA(1);

                if ( (LA87_0==163) ) {
                    alt87=1;
                }


                switch (alt87) {
            	case 1 :
            	    // InternalMazeDsl.g:5162:4: (lv_swatches_4_0= ruleThemeSwatchConfig )
            	    {
            	    // InternalMazeDsl.g:5162:4: (lv_swatches_4_0= ruleThemeSwatchConfig )
            	    // InternalMazeDsl.g:5163:5: lv_swatches_4_0= ruleThemeSwatchConfig
            	    {

            	    					newCompositeNode(grammarAccess.getMenuThemeConfigAccess().getSwatchesThemeSwatchConfigParserRuleCall_4_0());
            	    				
            	    pushFollow(FOLLOW_166);
            	    lv_swatches_4_0=ruleThemeSwatchConfig();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getMenuThemeConfigRule());
            	    					}
            	    					add(
            	    						current,
            	    						"swatches",
            	    						lv_swatches_4_0,
            	    						"main.game.maze.dsl.MazeDsl.ThemeSwatchConfig");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop87;
                }
            } while (true);

            // InternalMazeDsl.g:5180:3: ( (lv_images_5_0= ruleThemeImageConfig ) )*
            loop88:
            do {
                int alt88=2;
                int LA88_0 = input.LA(1);

                if ( (LA88_0==164) ) {
                    alt88=1;
                }


                switch (alt88) {
            	case 1 :
            	    // InternalMazeDsl.g:5181:4: (lv_images_5_0= ruleThemeImageConfig )
            	    {
            	    // InternalMazeDsl.g:5181:4: (lv_images_5_0= ruleThemeImageConfig )
            	    // InternalMazeDsl.g:5182:5: lv_images_5_0= ruleThemeImageConfig
            	    {

            	    					newCompositeNode(grammarAccess.getMenuThemeConfigAccess().getImagesThemeImageConfigParserRuleCall_5_0());
            	    				
            	    pushFollow(FOLLOW_167);
            	    lv_images_5_0=ruleThemeImageConfig();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getMenuThemeConfigRule());
            	    					}
            	    					add(
            	    						current,
            	    						"images",
            	    						lv_images_5_0,
            	    						"main.game.maze.dsl.MazeDsl.ThemeImageConfig");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop88;
                }
            } while (true);

            // InternalMazeDsl.g:5199:3: ( (lv_badges_6_0= ruleThemeBadgeConfig ) )*
            loop89:
            do {
                int alt89=2;
                int LA89_0 = input.LA(1);

                if ( (LA89_0==166) ) {
                    alt89=1;
                }


                switch (alt89) {
            	case 1 :
            	    // InternalMazeDsl.g:5200:4: (lv_badges_6_0= ruleThemeBadgeConfig )
            	    {
            	    // InternalMazeDsl.g:5200:4: (lv_badges_6_0= ruleThemeBadgeConfig )
            	    // InternalMazeDsl.g:5201:5: lv_badges_6_0= ruleThemeBadgeConfig
            	    {

            	    					newCompositeNode(grammarAccess.getMenuThemeConfigAccess().getBadgesThemeBadgeConfigParserRuleCall_6_0());
            	    				
            	    pushFollow(FOLLOW_168);
            	    lv_badges_6_0=ruleThemeBadgeConfig();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getMenuThemeConfigRule());
            	    					}
            	    					add(
            	    						current,
            	    						"badges",
            	    						lv_badges_6_0,
            	    						"main.game.maze.dsl.MazeDsl.ThemeBadgeConfig");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop89;
                }
            } while (true);

            otherlv_7=(Token)match(input,13,FOLLOW_2); 

            			newLeafNode(otherlv_7, grammarAccess.getMenuThemeConfigAccess().getRightCurlyBracketKeyword_7());
            		

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
    // $ANTLR end "ruleMenuThemeConfig"


    // $ANTLR start "entryRuleThemeSwatchConfig"
    // InternalMazeDsl.g:5226:1: entryRuleThemeSwatchConfig returns [EObject current=null] : iv_ruleThemeSwatchConfig= ruleThemeSwatchConfig EOF ;
    public final EObject entryRuleThemeSwatchConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleThemeSwatchConfig = null;


        try {
            // InternalMazeDsl.g:5226:58: (iv_ruleThemeSwatchConfig= ruleThemeSwatchConfig EOF )
            // InternalMazeDsl.g:5227:2: iv_ruleThemeSwatchConfig= ruleThemeSwatchConfig EOF
            {
             newCompositeNode(grammarAccess.getThemeSwatchConfigRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleThemeSwatchConfig=ruleThemeSwatchConfig();

            state._fsp--;

             current =iv_ruleThemeSwatchConfig; 
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
    // $ANTLR end "entryRuleThemeSwatchConfig"


    // $ANTLR start "ruleThemeSwatchConfig"
    // InternalMazeDsl.g:5233:1: ruleThemeSwatchConfig returns [EObject current=null] : (otherlv_0= 'colour' ( (lv_role_1_0= ruleValidID ) ) ( (lv_colour_2_0= RULE_STRING ) ) ) ;
    public final EObject ruleThemeSwatchConfig() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_colour_2_0=null;
        AntlrDatatypeRuleToken lv_role_1_0 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:5239:2: ( (otherlv_0= 'colour' ( (lv_role_1_0= ruleValidID ) ) ( (lv_colour_2_0= RULE_STRING ) ) ) )
            // InternalMazeDsl.g:5240:2: (otherlv_0= 'colour' ( (lv_role_1_0= ruleValidID ) ) ( (lv_colour_2_0= RULE_STRING ) ) )
            {
            // InternalMazeDsl.g:5240:2: (otherlv_0= 'colour' ( (lv_role_1_0= ruleValidID ) ) ( (lv_colour_2_0= RULE_STRING ) ) )
            // InternalMazeDsl.g:5241:3: otherlv_0= 'colour' ( (lv_role_1_0= ruleValidID ) ) ( (lv_colour_2_0= RULE_STRING ) )
            {
            otherlv_0=(Token)match(input,163,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getThemeSwatchConfigAccess().getColourKeyword_0());
            		
            // InternalMazeDsl.g:5245:3: ( (lv_role_1_0= ruleValidID ) )
            // InternalMazeDsl.g:5246:4: (lv_role_1_0= ruleValidID )
            {
            // InternalMazeDsl.g:5246:4: (lv_role_1_0= ruleValidID )
            // InternalMazeDsl.g:5247:5: lv_role_1_0= ruleValidID
            {

            					newCompositeNode(grammarAccess.getThemeSwatchConfigAccess().getRoleValidIDParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_12);
            lv_role_1_0=ruleValidID();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getThemeSwatchConfigRule());
            					}
            					set(
            						current,
            						"role",
            						lv_role_1_0,
            						"main.game.maze.dsl.MazeDsl.ValidID");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalMazeDsl.g:5264:3: ( (lv_colour_2_0= RULE_STRING ) )
            // InternalMazeDsl.g:5265:4: (lv_colour_2_0= RULE_STRING )
            {
            // InternalMazeDsl.g:5265:4: (lv_colour_2_0= RULE_STRING )
            // InternalMazeDsl.g:5266:5: lv_colour_2_0= RULE_STRING
            {
            lv_colour_2_0=(Token)match(input,RULE_STRING,FOLLOW_2); 

            					newLeafNode(lv_colour_2_0, grammarAccess.getThemeSwatchConfigAccess().getColourSTRINGTerminalRuleCall_2_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getThemeSwatchConfigRule());
            					}
            					setWithLastConsumed(
            						current,
            						"colour",
            						lv_colour_2_0,
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
    // $ANTLR end "ruleThemeSwatchConfig"


    // $ANTLR start "entryRuleThemeImageConfig"
    // InternalMazeDsl.g:5286:1: entryRuleThemeImageConfig returns [EObject current=null] : iv_ruleThemeImageConfig= ruleThemeImageConfig EOF ;
    public final EObject entryRuleThemeImageConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleThemeImageConfig = null;


        try {
            // InternalMazeDsl.g:5286:57: (iv_ruleThemeImageConfig= ruleThemeImageConfig EOF )
            // InternalMazeDsl.g:5287:2: iv_ruleThemeImageConfig= ruleThemeImageConfig EOF
            {
             newCompositeNode(grammarAccess.getThemeImageConfigRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleThemeImageConfig=ruleThemeImageConfig();

            state._fsp--;

             current =iv_ruleThemeImageConfig; 
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
    // $ANTLR end "entryRuleThemeImageConfig"


    // $ANTLR start "ruleThemeImageConfig"
    // InternalMazeDsl.g:5293:1: ruleThemeImageConfig returns [EObject current=null] : (otherlv_0= 'image' ( (lv_role_1_0= ruleValidID ) ) ( (lv_path_2_0= RULE_STRING ) ) (otherlv_3= 'slice' ( (lv_slice_4_0= ruleINT_OBJECT ) ) )? ) ;
    public final EObject ruleThemeImageConfig() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_path_2_0=null;
        Token otherlv_3=null;
        AntlrDatatypeRuleToken lv_role_1_0 = null;

        AntlrDatatypeRuleToken lv_slice_4_0 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:5299:2: ( (otherlv_0= 'image' ( (lv_role_1_0= ruleValidID ) ) ( (lv_path_2_0= RULE_STRING ) ) (otherlv_3= 'slice' ( (lv_slice_4_0= ruleINT_OBJECT ) ) )? ) )
            // InternalMazeDsl.g:5300:2: (otherlv_0= 'image' ( (lv_role_1_0= ruleValidID ) ) ( (lv_path_2_0= RULE_STRING ) ) (otherlv_3= 'slice' ( (lv_slice_4_0= ruleINT_OBJECT ) ) )? )
            {
            // InternalMazeDsl.g:5300:2: (otherlv_0= 'image' ( (lv_role_1_0= ruleValidID ) ) ( (lv_path_2_0= RULE_STRING ) ) (otherlv_3= 'slice' ( (lv_slice_4_0= ruleINT_OBJECT ) ) )? )
            // InternalMazeDsl.g:5301:3: otherlv_0= 'image' ( (lv_role_1_0= ruleValidID ) ) ( (lv_path_2_0= RULE_STRING ) ) (otherlv_3= 'slice' ( (lv_slice_4_0= ruleINT_OBJECT ) ) )?
            {
            otherlv_0=(Token)match(input,164,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getThemeImageConfigAccess().getImageKeyword_0());
            		
            // InternalMazeDsl.g:5305:3: ( (lv_role_1_0= ruleValidID ) )
            // InternalMazeDsl.g:5306:4: (lv_role_1_0= ruleValidID )
            {
            // InternalMazeDsl.g:5306:4: (lv_role_1_0= ruleValidID )
            // InternalMazeDsl.g:5307:5: lv_role_1_0= ruleValidID
            {

            					newCompositeNode(grammarAccess.getThemeImageConfigAccess().getRoleValidIDParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_12);
            lv_role_1_0=ruleValidID();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getThemeImageConfigRule());
            					}
            					set(
            						current,
            						"role",
            						lv_role_1_0,
            						"main.game.maze.dsl.MazeDsl.ValidID");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalMazeDsl.g:5324:3: ( (lv_path_2_0= RULE_STRING ) )
            // InternalMazeDsl.g:5325:4: (lv_path_2_0= RULE_STRING )
            {
            // InternalMazeDsl.g:5325:4: (lv_path_2_0= RULE_STRING )
            // InternalMazeDsl.g:5326:5: lv_path_2_0= RULE_STRING
            {
            lv_path_2_0=(Token)match(input,RULE_STRING,FOLLOW_169); 

            					newLeafNode(lv_path_2_0, grammarAccess.getThemeImageConfigAccess().getPathSTRINGTerminalRuleCall_2_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getThemeImageConfigRule());
            					}
            					setWithLastConsumed(
            						current,
            						"path",
            						lv_path_2_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            // InternalMazeDsl.g:5342:3: (otherlv_3= 'slice' ( (lv_slice_4_0= ruleINT_OBJECT ) ) )?
            int alt90=2;
            int LA90_0 = input.LA(1);

            if ( (LA90_0==165) ) {
                alt90=1;
            }
            switch (alt90) {
                case 1 :
                    // InternalMazeDsl.g:5343:4: otherlv_3= 'slice' ( (lv_slice_4_0= ruleINT_OBJECT ) )
                    {
                    otherlv_3=(Token)match(input,165,FOLLOW_18); 

                    				newLeafNode(otherlv_3, grammarAccess.getThemeImageConfigAccess().getSliceKeyword_3_0());
                    			
                    // InternalMazeDsl.g:5347:4: ( (lv_slice_4_0= ruleINT_OBJECT ) )
                    // InternalMazeDsl.g:5348:5: (lv_slice_4_0= ruleINT_OBJECT )
                    {
                    // InternalMazeDsl.g:5348:5: (lv_slice_4_0= ruleINT_OBJECT )
                    // InternalMazeDsl.g:5349:6: lv_slice_4_0= ruleINT_OBJECT
                    {

                    						newCompositeNode(grammarAccess.getThemeImageConfigAccess().getSliceINT_OBJECTParserRuleCall_3_1_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_slice_4_0=ruleINT_OBJECT();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getThemeImageConfigRule());
                    						}
                    						set(
                    							current,
                    							"slice",
                    							lv_slice_4_0,
                    							"main.game.maze.dsl.MazeDsl.INT_OBJECT");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


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
    // $ANTLR end "ruleThemeImageConfig"


    // $ANTLR start "entryRuleThemeBadgeConfig"
    // InternalMazeDsl.g:5371:1: entryRuleThemeBadgeConfig returns [EObject current=null] : iv_ruleThemeBadgeConfig= ruleThemeBadgeConfig EOF ;
    public final EObject entryRuleThemeBadgeConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleThemeBadgeConfig = null;


        try {
            // InternalMazeDsl.g:5371:57: (iv_ruleThemeBadgeConfig= ruleThemeBadgeConfig EOF )
            // InternalMazeDsl.g:5372:2: iv_ruleThemeBadgeConfig= ruleThemeBadgeConfig EOF
            {
             newCompositeNode(grammarAccess.getThemeBadgeConfigRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleThemeBadgeConfig=ruleThemeBadgeConfig();

            state._fsp--;

             current =iv_ruleThemeBadgeConfig; 
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
    // $ANTLR end "entryRuleThemeBadgeConfig"


    // $ANTLR start "ruleThemeBadgeConfig"
    // InternalMazeDsl.g:5378:1: ruleThemeBadgeConfig returns [EObject current=null] : (otherlv_0= 'badge' ( (lv_tier_1_0= ruleValidID ) ) ( (lv_image_2_0= RULE_STRING ) ) otherlv_3= 'colour' ( (lv_colour_4_0= RULE_STRING ) ) ) ;
    public final EObject ruleThemeBadgeConfig() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_image_2_0=null;
        Token otherlv_3=null;
        Token lv_colour_4_0=null;
        AntlrDatatypeRuleToken lv_tier_1_0 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:5384:2: ( (otherlv_0= 'badge' ( (lv_tier_1_0= ruleValidID ) ) ( (lv_image_2_0= RULE_STRING ) ) otherlv_3= 'colour' ( (lv_colour_4_0= RULE_STRING ) ) ) )
            // InternalMazeDsl.g:5385:2: (otherlv_0= 'badge' ( (lv_tier_1_0= ruleValidID ) ) ( (lv_image_2_0= RULE_STRING ) ) otherlv_3= 'colour' ( (lv_colour_4_0= RULE_STRING ) ) )
            {
            // InternalMazeDsl.g:5385:2: (otherlv_0= 'badge' ( (lv_tier_1_0= ruleValidID ) ) ( (lv_image_2_0= RULE_STRING ) ) otherlv_3= 'colour' ( (lv_colour_4_0= RULE_STRING ) ) )
            // InternalMazeDsl.g:5386:3: otherlv_0= 'badge' ( (lv_tier_1_0= ruleValidID ) ) ( (lv_image_2_0= RULE_STRING ) ) otherlv_3= 'colour' ( (lv_colour_4_0= RULE_STRING ) )
            {
            otherlv_0=(Token)match(input,166,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getThemeBadgeConfigAccess().getBadgeKeyword_0());
            		
            // InternalMazeDsl.g:5390:3: ( (lv_tier_1_0= ruleValidID ) )
            // InternalMazeDsl.g:5391:4: (lv_tier_1_0= ruleValidID )
            {
            // InternalMazeDsl.g:5391:4: (lv_tier_1_0= ruleValidID )
            // InternalMazeDsl.g:5392:5: lv_tier_1_0= ruleValidID
            {

            					newCompositeNode(grammarAccess.getThemeBadgeConfigAccess().getTierValidIDParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_12);
            lv_tier_1_0=ruleValidID();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getThemeBadgeConfigRule());
            					}
            					set(
            						current,
            						"tier",
            						lv_tier_1_0,
            						"main.game.maze.dsl.MazeDsl.ValidID");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalMazeDsl.g:5409:3: ( (lv_image_2_0= RULE_STRING ) )
            // InternalMazeDsl.g:5410:4: (lv_image_2_0= RULE_STRING )
            {
            // InternalMazeDsl.g:5410:4: (lv_image_2_0= RULE_STRING )
            // InternalMazeDsl.g:5411:5: lv_image_2_0= RULE_STRING
            {
            lv_image_2_0=(Token)match(input,RULE_STRING,FOLLOW_170); 

            					newLeafNode(lv_image_2_0, grammarAccess.getThemeBadgeConfigAccess().getImageSTRINGTerminalRuleCall_2_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getThemeBadgeConfigRule());
            					}
            					setWithLastConsumed(
            						current,
            						"image",
            						lv_image_2_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            otherlv_3=(Token)match(input,163,FOLLOW_12); 

            			newLeafNode(otherlv_3, grammarAccess.getThemeBadgeConfigAccess().getColourKeyword_3());
            		
            // InternalMazeDsl.g:5431:3: ( (lv_colour_4_0= RULE_STRING ) )
            // InternalMazeDsl.g:5432:4: (lv_colour_4_0= RULE_STRING )
            {
            // InternalMazeDsl.g:5432:4: (lv_colour_4_0= RULE_STRING )
            // InternalMazeDsl.g:5433:5: lv_colour_4_0= RULE_STRING
            {
            lv_colour_4_0=(Token)match(input,RULE_STRING,FOLLOW_2); 

            					newLeafNode(lv_colour_4_0, grammarAccess.getThemeBadgeConfigAccess().getColourSTRINGTerminalRuleCall_4_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getThemeBadgeConfigRule());
            					}
            					setWithLastConsumed(
            						current,
            						"colour",
            						lv_colour_4_0,
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
    // $ANTLR end "ruleThemeBadgeConfig"


    // $ANTLR start "entryRuleLastLifeGiftConfig"
    // InternalMazeDsl.g:5453:1: entryRuleLastLifeGiftConfig returns [EObject current=null] : iv_ruleLastLifeGiftConfig= ruleLastLifeGiftConfig EOF ;
    public final EObject entryRuleLastLifeGiftConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLastLifeGiftConfig = null;


        try {
            // InternalMazeDsl.g:5453:59: (iv_ruleLastLifeGiftConfig= ruleLastLifeGiftConfig EOF )
            // InternalMazeDsl.g:5454:2: iv_ruleLastLifeGiftConfig= ruleLastLifeGiftConfig EOF
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
    // InternalMazeDsl.g:5460:1: ruleLastLifeGiftConfig returns [EObject current=null] : (otherlv_0= 'lastLifeWeapon' ( (lv_weapon_1_0= ruleValidID ) ) otherlv_2= 'shots' ( (lv_shots_3_0= RULE_INT ) ) ) ;
    public final EObject ruleLastLifeGiftConfig() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token lv_shots_3_0=null;
        AntlrDatatypeRuleToken lv_weapon_1_0 = null;



        	enterRule();

        try {
            // InternalMazeDsl.g:5466:2: ( (otherlv_0= 'lastLifeWeapon' ( (lv_weapon_1_0= ruleValidID ) ) otherlv_2= 'shots' ( (lv_shots_3_0= RULE_INT ) ) ) )
            // InternalMazeDsl.g:5467:2: (otherlv_0= 'lastLifeWeapon' ( (lv_weapon_1_0= ruleValidID ) ) otherlv_2= 'shots' ( (lv_shots_3_0= RULE_INT ) ) )
            {
            // InternalMazeDsl.g:5467:2: (otherlv_0= 'lastLifeWeapon' ( (lv_weapon_1_0= ruleValidID ) ) otherlv_2= 'shots' ( (lv_shots_3_0= RULE_INT ) ) )
            // InternalMazeDsl.g:5468:3: otherlv_0= 'lastLifeWeapon' ( (lv_weapon_1_0= ruleValidID ) ) otherlv_2= 'shots' ( (lv_shots_3_0= RULE_INT ) )
            {
            otherlv_0=(Token)match(input,167,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getLastLifeGiftConfigAccess().getLastLifeWeaponKeyword_0());
            		
            // InternalMazeDsl.g:5472:3: ( (lv_weapon_1_0= ruleValidID ) )
            // InternalMazeDsl.g:5473:4: (lv_weapon_1_0= ruleValidID )
            {
            // InternalMazeDsl.g:5473:4: (lv_weapon_1_0= ruleValidID )
            // InternalMazeDsl.g:5474:5: lv_weapon_1_0= ruleValidID
            {

            					newCompositeNode(grammarAccess.getLastLifeGiftConfigAccess().getWeaponValidIDParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_171);
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

            otherlv_2=(Token)match(input,168,FOLLOW_37); 

            			newLeafNode(otherlv_2, grammarAccess.getLastLifeGiftConfigAccess().getShotsKeyword_2());
            		
            // InternalMazeDsl.g:5495:3: ( (lv_shots_3_0= RULE_INT ) )
            // InternalMazeDsl.g:5496:4: (lv_shots_3_0= RULE_INT )
            {
            // InternalMazeDsl.g:5496:4: (lv_shots_3_0= RULE_INT )
            // InternalMazeDsl.g:5497:5: lv_shots_3_0= RULE_INT
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
    // InternalMazeDsl.g:5517:1: entryRuleTeleportRulesConfig returns [EObject current=null] : iv_ruleTeleportRulesConfig= ruleTeleportRulesConfig EOF ;
    public final EObject entryRuleTeleportRulesConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTeleportRulesConfig = null;


        try {
            // InternalMazeDsl.g:5517:60: (iv_ruleTeleportRulesConfig= ruleTeleportRulesConfig EOF )
            // InternalMazeDsl.g:5518:2: iv_ruleTeleportRulesConfig= ruleTeleportRulesConfig EOF
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
    // InternalMazeDsl.g:5524:1: ruleTeleportRulesConfig returns [EObject current=null] : (otherlv_0= 'teleport' otherlv_1= '{' otherlv_2= 'cooldown' ( (lv_cooldownSeconds_3_0= ruleDOUBLE ) ) otherlv_4= 'banner' ( (lv_bannerText_5_0= RULE_STRING ) ) otherlv_6= 'bannerSeconds' ( (lv_bannerSeconds_7_0= ruleDOUBLE ) ) (otherlv_8= 'zapSound' ( (lv_zapSound_9_0= RULE_STRING ) ) )? (otherlv_10= 'voiceSound' ( (lv_voiceSound_11_0= RULE_STRING ) ) )? otherlv_12= '}' ) ;
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
            // InternalMazeDsl.g:5530:2: ( (otherlv_0= 'teleport' otherlv_1= '{' otherlv_2= 'cooldown' ( (lv_cooldownSeconds_3_0= ruleDOUBLE ) ) otherlv_4= 'banner' ( (lv_bannerText_5_0= RULE_STRING ) ) otherlv_6= 'bannerSeconds' ( (lv_bannerSeconds_7_0= ruleDOUBLE ) ) (otherlv_8= 'zapSound' ( (lv_zapSound_9_0= RULE_STRING ) ) )? (otherlv_10= 'voiceSound' ( (lv_voiceSound_11_0= RULE_STRING ) ) )? otherlv_12= '}' ) )
            // InternalMazeDsl.g:5531:2: (otherlv_0= 'teleport' otherlv_1= '{' otherlv_2= 'cooldown' ( (lv_cooldownSeconds_3_0= ruleDOUBLE ) ) otherlv_4= 'banner' ( (lv_bannerText_5_0= RULE_STRING ) ) otherlv_6= 'bannerSeconds' ( (lv_bannerSeconds_7_0= ruleDOUBLE ) ) (otherlv_8= 'zapSound' ( (lv_zapSound_9_0= RULE_STRING ) ) )? (otherlv_10= 'voiceSound' ( (lv_voiceSound_11_0= RULE_STRING ) ) )? otherlv_12= '}' )
            {
            // InternalMazeDsl.g:5531:2: (otherlv_0= 'teleport' otherlv_1= '{' otherlv_2= 'cooldown' ( (lv_cooldownSeconds_3_0= ruleDOUBLE ) ) otherlv_4= 'banner' ( (lv_bannerText_5_0= RULE_STRING ) ) otherlv_6= 'bannerSeconds' ( (lv_bannerSeconds_7_0= ruleDOUBLE ) ) (otherlv_8= 'zapSound' ( (lv_zapSound_9_0= RULE_STRING ) ) )? (otherlv_10= 'voiceSound' ( (lv_voiceSound_11_0= RULE_STRING ) ) )? otherlv_12= '}' )
            // InternalMazeDsl.g:5532:3: otherlv_0= 'teleport' otherlv_1= '{' otherlv_2= 'cooldown' ( (lv_cooldownSeconds_3_0= ruleDOUBLE ) ) otherlv_4= 'banner' ( (lv_bannerText_5_0= RULE_STRING ) ) otherlv_6= 'bannerSeconds' ( (lv_bannerSeconds_7_0= ruleDOUBLE ) ) (otherlv_8= 'zapSound' ( (lv_zapSound_9_0= RULE_STRING ) ) )? (otherlv_10= 'voiceSound' ( (lv_voiceSound_11_0= RULE_STRING ) ) )? otherlv_12= '}'
            {
            otherlv_0=(Token)match(input,169,FOLLOW_4); 

            			newLeafNode(otherlv_0, grammarAccess.getTeleportRulesConfigAccess().getTeleportKeyword_0());
            		
            otherlv_1=(Token)match(input,12,FOLLOW_172); 

            			newLeafNode(otherlv_1, grammarAccess.getTeleportRulesConfigAccess().getLeftCurlyBracketKeyword_1());
            		
            otherlv_2=(Token)match(input,170,FOLLOW_18); 

            			newLeafNode(otherlv_2, grammarAccess.getTeleportRulesConfigAccess().getCooldownKeyword_2());
            		
            // InternalMazeDsl.g:5544:3: ( (lv_cooldownSeconds_3_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:5545:4: (lv_cooldownSeconds_3_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:5545:4: (lv_cooldownSeconds_3_0= ruleDOUBLE )
            // InternalMazeDsl.g:5546:5: lv_cooldownSeconds_3_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getTeleportRulesConfigAccess().getCooldownSecondsDOUBLEParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_173);
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

            otherlv_4=(Token)match(input,148,FOLLOW_12); 

            			newLeafNode(otherlv_4, grammarAccess.getTeleportRulesConfigAccess().getBannerKeyword_4());
            		
            // InternalMazeDsl.g:5567:3: ( (lv_bannerText_5_0= RULE_STRING ) )
            // InternalMazeDsl.g:5568:4: (lv_bannerText_5_0= RULE_STRING )
            {
            // InternalMazeDsl.g:5568:4: (lv_bannerText_5_0= RULE_STRING )
            // InternalMazeDsl.g:5569:5: lv_bannerText_5_0= RULE_STRING
            {
            lv_bannerText_5_0=(Token)match(input,RULE_STRING,FOLLOW_174); 

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

            otherlv_6=(Token)match(input,171,FOLLOW_18); 

            			newLeafNode(otherlv_6, grammarAccess.getTeleportRulesConfigAccess().getBannerSecondsKeyword_6());
            		
            // InternalMazeDsl.g:5589:3: ( (lv_bannerSeconds_7_0= ruleDOUBLE ) )
            // InternalMazeDsl.g:5590:4: (lv_bannerSeconds_7_0= ruleDOUBLE )
            {
            // InternalMazeDsl.g:5590:4: (lv_bannerSeconds_7_0= ruleDOUBLE )
            // InternalMazeDsl.g:5591:5: lv_bannerSeconds_7_0= ruleDOUBLE
            {

            					newCompositeNode(grammarAccess.getTeleportRulesConfigAccess().getBannerSecondsDOUBLEParserRuleCall_7_0());
            				
            pushFollow(FOLLOW_175);
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

            // InternalMazeDsl.g:5608:3: (otherlv_8= 'zapSound' ( (lv_zapSound_9_0= RULE_STRING ) ) )?
            int alt91=2;
            int LA91_0 = input.LA(1);

            if ( (LA91_0==172) ) {
                alt91=1;
            }
            switch (alt91) {
                case 1 :
                    // InternalMazeDsl.g:5609:4: otherlv_8= 'zapSound' ( (lv_zapSound_9_0= RULE_STRING ) )
                    {
                    otherlv_8=(Token)match(input,172,FOLLOW_12); 

                    				newLeafNode(otherlv_8, grammarAccess.getTeleportRulesConfigAccess().getZapSoundKeyword_8_0());
                    			
                    // InternalMazeDsl.g:5613:4: ( (lv_zapSound_9_0= RULE_STRING ) )
                    // InternalMazeDsl.g:5614:5: (lv_zapSound_9_0= RULE_STRING )
                    {
                    // InternalMazeDsl.g:5614:5: (lv_zapSound_9_0= RULE_STRING )
                    // InternalMazeDsl.g:5615:6: lv_zapSound_9_0= RULE_STRING
                    {
                    lv_zapSound_9_0=(Token)match(input,RULE_STRING,FOLLOW_176); 

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

            // InternalMazeDsl.g:5632:3: (otherlv_10= 'voiceSound' ( (lv_voiceSound_11_0= RULE_STRING ) ) )?
            int alt92=2;
            int LA92_0 = input.LA(1);

            if ( (LA92_0==173) ) {
                alt92=1;
            }
            switch (alt92) {
                case 1 :
                    // InternalMazeDsl.g:5633:4: otherlv_10= 'voiceSound' ( (lv_voiceSound_11_0= RULE_STRING ) )
                    {
                    otherlv_10=(Token)match(input,173,FOLLOW_12); 

                    				newLeafNode(otherlv_10, grammarAccess.getTeleportRulesConfigAccess().getVoiceSoundKeyword_9_0());
                    			
                    // InternalMazeDsl.g:5637:4: ( (lv_voiceSound_11_0= RULE_STRING ) )
                    // InternalMazeDsl.g:5638:5: (lv_voiceSound_11_0= RULE_STRING )
                    {
                    // InternalMazeDsl.g:5638:5: (lv_voiceSound_11_0= RULE_STRING )
                    // InternalMazeDsl.g:5639:6: lv_voiceSound_11_0= RULE_STRING
                    {
                    lv_voiceSound_11_0=(Token)match(input,RULE_STRING,FOLLOW_11); 

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
    // InternalMazeDsl.g:5664:1: entryRuleDOUBLE returns [String current=null] : iv_ruleDOUBLE= ruleDOUBLE EOF ;
    public final String entryRuleDOUBLE() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDOUBLE = null;


        try {
            // InternalMazeDsl.g:5664:46: (iv_ruleDOUBLE= ruleDOUBLE EOF )
            // InternalMazeDsl.g:5665:2: iv_ruleDOUBLE= ruleDOUBLE EOF
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
    // InternalMazeDsl.g:5671:1: ruleDOUBLE returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= '-' )? this_INT_1= RULE_INT (kw= '.' this_INT_3= RULE_INT )? ) ;
    public final AntlrDatatypeRuleToken ruleDOUBLE() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_INT_1=null;
        Token this_INT_3=null;


        	enterRule();

        try {
            // InternalMazeDsl.g:5677:2: ( ( (kw= '-' )? this_INT_1= RULE_INT (kw= '.' this_INT_3= RULE_INT )? ) )
            // InternalMazeDsl.g:5678:2: ( (kw= '-' )? this_INT_1= RULE_INT (kw= '.' this_INT_3= RULE_INT )? )
            {
            // InternalMazeDsl.g:5678:2: ( (kw= '-' )? this_INT_1= RULE_INT (kw= '.' this_INT_3= RULE_INT )? )
            // InternalMazeDsl.g:5679:3: (kw= '-' )? this_INT_1= RULE_INT (kw= '.' this_INT_3= RULE_INT )?
            {
            // InternalMazeDsl.g:5679:3: (kw= '-' )?
            int alt93=2;
            int LA93_0 = input.LA(1);

            if ( (LA93_0==174) ) {
                alt93=1;
            }
            switch (alt93) {
                case 1 :
                    // InternalMazeDsl.g:5680:4: kw= '-'
                    {
                    kw=(Token)match(input,174,FOLLOW_37); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getDOUBLEAccess().getHyphenMinusKeyword_0());
                    			

                    }
                    break;

            }

            this_INT_1=(Token)match(input,RULE_INT,FOLLOW_177); 

            			current.merge(this_INT_1);
            		

            			newLeafNode(this_INT_1, grammarAccess.getDOUBLEAccess().getINTTerminalRuleCall_1());
            		
            // InternalMazeDsl.g:5693:3: (kw= '.' this_INT_3= RULE_INT )?
            int alt94=2;
            int LA94_0 = input.LA(1);

            if ( (LA94_0==175) ) {
                alt94=1;
            }
            switch (alt94) {
                case 1 :
                    // InternalMazeDsl.g:5694:4: kw= '.' this_INT_3= RULE_INT
                    {
                    kw=(Token)match(input,175,FOLLOW_37); 

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
    // InternalMazeDsl.g:5711:1: entryRuleSIGNED_INT returns [String current=null] : iv_ruleSIGNED_INT= ruleSIGNED_INT EOF ;
    public final String entryRuleSIGNED_INT() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSIGNED_INT = null;


        try {
            // InternalMazeDsl.g:5711:50: (iv_ruleSIGNED_INT= ruleSIGNED_INT EOF )
            // InternalMazeDsl.g:5712:2: iv_ruleSIGNED_INT= ruleSIGNED_INT EOF
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
    // InternalMazeDsl.g:5718:1: ruleSIGNED_INT returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= '-' )? this_INT_1= RULE_INT ) ;
    public final AntlrDatatypeRuleToken ruleSIGNED_INT() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_INT_1=null;


        	enterRule();

        try {
            // InternalMazeDsl.g:5724:2: ( ( (kw= '-' )? this_INT_1= RULE_INT ) )
            // InternalMazeDsl.g:5725:2: ( (kw= '-' )? this_INT_1= RULE_INT )
            {
            // InternalMazeDsl.g:5725:2: ( (kw= '-' )? this_INT_1= RULE_INT )
            // InternalMazeDsl.g:5726:3: (kw= '-' )? this_INT_1= RULE_INT
            {
            // InternalMazeDsl.g:5726:3: (kw= '-' )?
            int alt95=2;
            int LA95_0 = input.LA(1);

            if ( (LA95_0==174) ) {
                alt95=1;
            }
            switch (alt95) {
                case 1 :
                    // InternalMazeDsl.g:5727:4: kw= '-'
                    {
                    kw=(Token)match(input,174,FOLLOW_37); 

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
    // InternalMazeDsl.g:5744:1: entryRuleBOOLEAN returns [String current=null] : iv_ruleBOOLEAN= ruleBOOLEAN EOF ;
    public final String entryRuleBOOLEAN() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleBOOLEAN = null;


        try {
            // InternalMazeDsl.g:5744:47: (iv_ruleBOOLEAN= ruleBOOLEAN EOF )
            // InternalMazeDsl.g:5745:2: iv_ruleBOOLEAN= ruleBOOLEAN EOF
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
    // InternalMazeDsl.g:5751:1: ruleBOOLEAN returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'true' | kw= 'false' ) ;
    public final AntlrDatatypeRuleToken ruleBOOLEAN() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalMazeDsl.g:5757:2: ( (kw= 'true' | kw= 'false' ) )
            // InternalMazeDsl.g:5758:2: (kw= 'true' | kw= 'false' )
            {
            // InternalMazeDsl.g:5758:2: (kw= 'true' | kw= 'false' )
            int alt96=2;
            int LA96_0 = input.LA(1);

            if ( (LA96_0==18) ) {
                alt96=1;
            }
            else if ( (LA96_0==176) ) {
                alt96=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 96, 0, input);

                throw nvae;
            }
            switch (alt96) {
                case 1 :
                    // InternalMazeDsl.g:5759:3: kw= 'true'
                    {
                    kw=(Token)match(input,18,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getBOOLEANAccess().getTrueKeyword_0());
                    		

                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:5765:3: kw= 'false'
                    {
                    kw=(Token)match(input,176,FOLLOW_2); 

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
    // InternalMazeDsl.g:5774:1: entryRuleDOUBLE_OBJECT returns [String current=null] : iv_ruleDOUBLE_OBJECT= ruleDOUBLE_OBJECT EOF ;
    public final String entryRuleDOUBLE_OBJECT() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDOUBLE_OBJECT = null;


        try {
            // InternalMazeDsl.g:5774:53: (iv_ruleDOUBLE_OBJECT= ruleDOUBLE_OBJECT EOF )
            // InternalMazeDsl.g:5775:2: iv_ruleDOUBLE_OBJECT= ruleDOUBLE_OBJECT EOF
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
    // InternalMazeDsl.g:5781:1: ruleDOUBLE_OBJECT returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= '-' )? this_INT_1= RULE_INT (kw= '.' this_INT_3= RULE_INT )? ) ;
    public final AntlrDatatypeRuleToken ruleDOUBLE_OBJECT() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_INT_1=null;
        Token this_INT_3=null;


        	enterRule();

        try {
            // InternalMazeDsl.g:5787:2: ( ( (kw= '-' )? this_INT_1= RULE_INT (kw= '.' this_INT_3= RULE_INT )? ) )
            // InternalMazeDsl.g:5788:2: ( (kw= '-' )? this_INT_1= RULE_INT (kw= '.' this_INT_3= RULE_INT )? )
            {
            // InternalMazeDsl.g:5788:2: ( (kw= '-' )? this_INT_1= RULE_INT (kw= '.' this_INT_3= RULE_INT )? )
            // InternalMazeDsl.g:5789:3: (kw= '-' )? this_INT_1= RULE_INT (kw= '.' this_INT_3= RULE_INT )?
            {
            // InternalMazeDsl.g:5789:3: (kw= '-' )?
            int alt97=2;
            int LA97_0 = input.LA(1);

            if ( (LA97_0==174) ) {
                alt97=1;
            }
            switch (alt97) {
                case 1 :
                    // InternalMazeDsl.g:5790:4: kw= '-'
                    {
                    kw=(Token)match(input,174,FOLLOW_37); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getDOUBLE_OBJECTAccess().getHyphenMinusKeyword_0());
                    			

                    }
                    break;

            }

            this_INT_1=(Token)match(input,RULE_INT,FOLLOW_177); 

            			current.merge(this_INT_1);
            		

            			newLeafNode(this_INT_1, grammarAccess.getDOUBLE_OBJECTAccess().getINTTerminalRuleCall_1());
            		
            // InternalMazeDsl.g:5803:3: (kw= '.' this_INT_3= RULE_INT )?
            int alt98=2;
            int LA98_0 = input.LA(1);

            if ( (LA98_0==175) ) {
                alt98=1;
            }
            switch (alt98) {
                case 1 :
                    // InternalMazeDsl.g:5804:4: kw= '.' this_INT_3= RULE_INT
                    {
                    kw=(Token)match(input,175,FOLLOW_37); 

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
    // InternalMazeDsl.g:5821:1: entryRuleINT_OBJECT returns [String current=null] : iv_ruleINT_OBJECT= ruleINT_OBJECT EOF ;
    public final String entryRuleINT_OBJECT() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleINT_OBJECT = null;


        try {
            // InternalMazeDsl.g:5821:50: (iv_ruleINT_OBJECT= ruleINT_OBJECT EOF )
            // InternalMazeDsl.g:5822:2: iv_ruleINT_OBJECT= ruleINT_OBJECT EOF
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
    // InternalMazeDsl.g:5828:1: ruleINT_OBJECT returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= '-' )? this_INT_1= RULE_INT ) ;
    public final AntlrDatatypeRuleToken ruleINT_OBJECT() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_INT_1=null;


        	enterRule();

        try {
            // InternalMazeDsl.g:5834:2: ( ( (kw= '-' )? this_INT_1= RULE_INT ) )
            // InternalMazeDsl.g:5835:2: ( (kw= '-' )? this_INT_1= RULE_INT )
            {
            // InternalMazeDsl.g:5835:2: ( (kw= '-' )? this_INT_1= RULE_INT )
            // InternalMazeDsl.g:5836:3: (kw= '-' )? this_INT_1= RULE_INT
            {
            // InternalMazeDsl.g:5836:3: (kw= '-' )?
            int alt99=2;
            int LA99_0 = input.LA(1);

            if ( (LA99_0==174) ) {
                alt99=1;
            }
            switch (alt99) {
                case 1 :
                    // InternalMazeDsl.g:5837:4: kw= '-'
                    {
                    kw=(Token)match(input,174,FOLLOW_37); 

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
    // InternalMazeDsl.g:5854:1: entryRuleBOOLEAN_OBJECT returns [String current=null] : iv_ruleBOOLEAN_OBJECT= ruleBOOLEAN_OBJECT EOF ;
    public final String entryRuleBOOLEAN_OBJECT() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleBOOLEAN_OBJECT = null;


        try {
            // InternalMazeDsl.g:5854:54: (iv_ruleBOOLEAN_OBJECT= ruleBOOLEAN_OBJECT EOF )
            // InternalMazeDsl.g:5855:2: iv_ruleBOOLEAN_OBJECT= ruleBOOLEAN_OBJECT EOF
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
    // InternalMazeDsl.g:5861:1: ruleBOOLEAN_OBJECT returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'true' | kw= 'false' ) ;
    public final AntlrDatatypeRuleToken ruleBOOLEAN_OBJECT() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalMazeDsl.g:5867:2: ( (kw= 'true' | kw= 'false' ) )
            // InternalMazeDsl.g:5868:2: (kw= 'true' | kw= 'false' )
            {
            // InternalMazeDsl.g:5868:2: (kw= 'true' | kw= 'false' )
            int alt100=2;
            int LA100_0 = input.LA(1);

            if ( (LA100_0==18) ) {
                alt100=1;
            }
            else if ( (LA100_0==176) ) {
                alt100=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 100, 0, input);

                throw nvae;
            }
            switch (alt100) {
                case 1 :
                    // InternalMazeDsl.g:5869:3: kw= 'true'
                    {
                    kw=(Token)match(input,18,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getBOOLEAN_OBJECTAccess().getTrueKeyword_0());
                    		

                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:5875:3: kw= 'false'
                    {
                    kw=(Token)match(input,176,FOLLOW_2); 

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
    // InternalMazeDsl.g:5884:1: entryRuleValidID returns [String current=null] : iv_ruleValidID= ruleValidID EOF ;
    public final String entryRuleValidID() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleValidID = null;


        try {
            // InternalMazeDsl.g:5884:47: (iv_ruleValidID= ruleValidID EOF )
            // InternalMazeDsl.g:5885:2: iv_ruleValidID= ruleValidID EOF
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
    // InternalMazeDsl.g:5891:1: ruleValidID returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID | kw= 'basedOn' | kw= 'threatCapacity' | kw= 'wallMaterials' | kw= 'inheritWeapons' | kw= 'cascadeDepth' | kw= 'ring' | kw= 'share' | kw= 'materials' | kw= 'sergeant' | kw= 'sergeants' | kw= 'sightRange' | kw= 'rounds' | kw= 'roundDamage' | kw= 'roundInterval' | kw= 'burstCooldown' | kw= 'sprite' | kw= 'fireSound' | kw= 'drops' | kw= 'gatling' | kw= 'postDistance' | kw= 'mines' | kw= 'count' | kw= 'ringRadius' | kw= 'triggerRadius' | kw= 'pulse' | kw= 'blastRadius' | kw= 'blastDamage' | kw= 'playerDamage' | kw= 'holeRadius' | kw= 'portalGhost' | kw= 'portalGhosts' | kw= 'teleport' | kw= 'cooldown' | kw= 'banner' | kw= 'bannerSeconds' | kw= 'zapSound' | kw= 'voiceSound' | kw= 'secondRing' | kw= 'guardTower' | kw= 'towers' | kw= 'wallsOver' | kw= 'rpgInterval' | kw= 'rpgSpeed' | kw= 'rpgBlastRadius' | kw= 'rpgPlayerDamage' | kw= 'rpgWallDamage' | kw= 'gatlingRounds' | kw= 'gatlingInterval' | kw= 'gatlingDamage' | kw= 'deathBlast' | kw= 'rpgLaunchSound' | kw= 'rpgExplosionSound' | kw= 'gatlingSound' | kw= 'hitSound' | kw= 'destroyedSound' | kw= 'wallBuilder' | kw= 'builders' | kw= 'buildSeconds' | kw= 'blockRadius' | kw= 'spawnRadius' | kw= 'material' | kw= 'startRadius' | kw= 'lastLifeWeapon' | kw= 'shots' | kw= 'lava' | kw= 'streams' | kw= 'width' | kw= 'bridges' | kw= 'bridgeLength' | kw= 'meander' | kw= 'wavelength' | kw= 'flowSpeed' | kw= 'glow' | kw= 'crust' | kw= 'deathSound' | kw= 'buildSound' | kw= 'ufoRaid' | kw= 'trigger' | kw= 'ufos' | kw= 'duration' | kw= 'ufoSpeed' | kw= 'beamEvery' | kw= 'charge' | kw= 'beamSeconds' | kw= 'beamRadius' | kw= 'aimMin' | kw= 'aimMax' | kw= 'damageFraction' | kw= 'soldiers' | kw= 'soldierHealth' | kw= 'soldierDamage' | kw= 'soldierSight' | kw= 'soldierSeconds' | kw= 'ufoSprite' | kw= 'beamSprite' | kw= 'arriveSound' | kw= 'beamSound' | kw= 'shop' | kw= 'tier' | kw= 'price' | kw= 'amount' | kw= 'factor' | kw= 'description' | kw= 'unlock' | kw= 'fromLevel' | kw= 'theme' | kw= 'font' | kw= 'colour' | kw= 'image' | kw= 'slice' | kw= 'badge' ) ;
    public final AntlrDatatypeRuleToken ruleValidID() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token kw=null;


        	enterRule();

        try {
            // InternalMazeDsl.g:5897:2: ( (this_ID_0= RULE_ID | kw= 'basedOn' | kw= 'threatCapacity' | kw= 'wallMaterials' | kw= 'inheritWeapons' | kw= 'cascadeDepth' | kw= 'ring' | kw= 'share' | kw= 'materials' | kw= 'sergeant' | kw= 'sergeants' | kw= 'sightRange' | kw= 'rounds' | kw= 'roundDamage' | kw= 'roundInterval' | kw= 'burstCooldown' | kw= 'sprite' | kw= 'fireSound' | kw= 'drops' | kw= 'gatling' | kw= 'postDistance' | kw= 'mines' | kw= 'count' | kw= 'ringRadius' | kw= 'triggerRadius' | kw= 'pulse' | kw= 'blastRadius' | kw= 'blastDamage' | kw= 'playerDamage' | kw= 'holeRadius' | kw= 'portalGhost' | kw= 'portalGhosts' | kw= 'teleport' | kw= 'cooldown' | kw= 'banner' | kw= 'bannerSeconds' | kw= 'zapSound' | kw= 'voiceSound' | kw= 'secondRing' | kw= 'guardTower' | kw= 'towers' | kw= 'wallsOver' | kw= 'rpgInterval' | kw= 'rpgSpeed' | kw= 'rpgBlastRadius' | kw= 'rpgPlayerDamage' | kw= 'rpgWallDamage' | kw= 'gatlingRounds' | kw= 'gatlingInterval' | kw= 'gatlingDamage' | kw= 'deathBlast' | kw= 'rpgLaunchSound' | kw= 'rpgExplosionSound' | kw= 'gatlingSound' | kw= 'hitSound' | kw= 'destroyedSound' | kw= 'wallBuilder' | kw= 'builders' | kw= 'buildSeconds' | kw= 'blockRadius' | kw= 'spawnRadius' | kw= 'material' | kw= 'startRadius' | kw= 'lastLifeWeapon' | kw= 'shots' | kw= 'lava' | kw= 'streams' | kw= 'width' | kw= 'bridges' | kw= 'bridgeLength' | kw= 'meander' | kw= 'wavelength' | kw= 'flowSpeed' | kw= 'glow' | kw= 'crust' | kw= 'deathSound' | kw= 'buildSound' | kw= 'ufoRaid' | kw= 'trigger' | kw= 'ufos' | kw= 'duration' | kw= 'ufoSpeed' | kw= 'beamEvery' | kw= 'charge' | kw= 'beamSeconds' | kw= 'beamRadius' | kw= 'aimMin' | kw= 'aimMax' | kw= 'damageFraction' | kw= 'soldiers' | kw= 'soldierHealth' | kw= 'soldierDamage' | kw= 'soldierSight' | kw= 'soldierSeconds' | kw= 'ufoSprite' | kw= 'beamSprite' | kw= 'arriveSound' | kw= 'beamSound' | kw= 'shop' | kw= 'tier' | kw= 'price' | kw= 'amount' | kw= 'factor' | kw= 'description' | kw= 'unlock' | kw= 'fromLevel' | kw= 'theme' | kw= 'font' | kw= 'colour' | kw= 'image' | kw= 'slice' | kw= 'badge' ) )
            // InternalMazeDsl.g:5898:2: (this_ID_0= RULE_ID | kw= 'basedOn' | kw= 'threatCapacity' | kw= 'wallMaterials' | kw= 'inheritWeapons' | kw= 'cascadeDepth' | kw= 'ring' | kw= 'share' | kw= 'materials' | kw= 'sergeant' | kw= 'sergeants' | kw= 'sightRange' | kw= 'rounds' | kw= 'roundDamage' | kw= 'roundInterval' | kw= 'burstCooldown' | kw= 'sprite' | kw= 'fireSound' | kw= 'drops' | kw= 'gatling' | kw= 'postDistance' | kw= 'mines' | kw= 'count' | kw= 'ringRadius' | kw= 'triggerRadius' | kw= 'pulse' | kw= 'blastRadius' | kw= 'blastDamage' | kw= 'playerDamage' | kw= 'holeRadius' | kw= 'portalGhost' | kw= 'portalGhosts' | kw= 'teleport' | kw= 'cooldown' | kw= 'banner' | kw= 'bannerSeconds' | kw= 'zapSound' | kw= 'voiceSound' | kw= 'secondRing' | kw= 'guardTower' | kw= 'towers' | kw= 'wallsOver' | kw= 'rpgInterval' | kw= 'rpgSpeed' | kw= 'rpgBlastRadius' | kw= 'rpgPlayerDamage' | kw= 'rpgWallDamage' | kw= 'gatlingRounds' | kw= 'gatlingInterval' | kw= 'gatlingDamage' | kw= 'deathBlast' | kw= 'rpgLaunchSound' | kw= 'rpgExplosionSound' | kw= 'gatlingSound' | kw= 'hitSound' | kw= 'destroyedSound' | kw= 'wallBuilder' | kw= 'builders' | kw= 'buildSeconds' | kw= 'blockRadius' | kw= 'spawnRadius' | kw= 'material' | kw= 'startRadius' | kw= 'lastLifeWeapon' | kw= 'shots' | kw= 'lava' | kw= 'streams' | kw= 'width' | kw= 'bridges' | kw= 'bridgeLength' | kw= 'meander' | kw= 'wavelength' | kw= 'flowSpeed' | kw= 'glow' | kw= 'crust' | kw= 'deathSound' | kw= 'buildSound' | kw= 'ufoRaid' | kw= 'trigger' | kw= 'ufos' | kw= 'duration' | kw= 'ufoSpeed' | kw= 'beamEvery' | kw= 'charge' | kw= 'beamSeconds' | kw= 'beamRadius' | kw= 'aimMin' | kw= 'aimMax' | kw= 'damageFraction' | kw= 'soldiers' | kw= 'soldierHealth' | kw= 'soldierDamage' | kw= 'soldierSight' | kw= 'soldierSeconds' | kw= 'ufoSprite' | kw= 'beamSprite' | kw= 'arriveSound' | kw= 'beamSound' | kw= 'shop' | kw= 'tier' | kw= 'price' | kw= 'amount' | kw= 'factor' | kw= 'description' | kw= 'unlock' | kw= 'fromLevel' | kw= 'theme' | kw= 'font' | kw= 'colour' | kw= 'image' | kw= 'slice' | kw= 'badge' )
            {
            // InternalMazeDsl.g:5898:2: (this_ID_0= RULE_ID | kw= 'basedOn' | kw= 'threatCapacity' | kw= 'wallMaterials' | kw= 'inheritWeapons' | kw= 'cascadeDepth' | kw= 'ring' | kw= 'share' | kw= 'materials' | kw= 'sergeant' | kw= 'sergeants' | kw= 'sightRange' | kw= 'rounds' | kw= 'roundDamage' | kw= 'roundInterval' | kw= 'burstCooldown' | kw= 'sprite' | kw= 'fireSound' | kw= 'drops' | kw= 'gatling' | kw= 'postDistance' | kw= 'mines' | kw= 'count' | kw= 'ringRadius' | kw= 'triggerRadius' | kw= 'pulse' | kw= 'blastRadius' | kw= 'blastDamage' | kw= 'playerDamage' | kw= 'holeRadius' | kw= 'portalGhost' | kw= 'portalGhosts' | kw= 'teleport' | kw= 'cooldown' | kw= 'banner' | kw= 'bannerSeconds' | kw= 'zapSound' | kw= 'voiceSound' | kw= 'secondRing' | kw= 'guardTower' | kw= 'towers' | kw= 'wallsOver' | kw= 'rpgInterval' | kw= 'rpgSpeed' | kw= 'rpgBlastRadius' | kw= 'rpgPlayerDamage' | kw= 'rpgWallDamage' | kw= 'gatlingRounds' | kw= 'gatlingInterval' | kw= 'gatlingDamage' | kw= 'deathBlast' | kw= 'rpgLaunchSound' | kw= 'rpgExplosionSound' | kw= 'gatlingSound' | kw= 'hitSound' | kw= 'destroyedSound' | kw= 'wallBuilder' | kw= 'builders' | kw= 'buildSeconds' | kw= 'blockRadius' | kw= 'spawnRadius' | kw= 'material' | kw= 'startRadius' | kw= 'lastLifeWeapon' | kw= 'shots' | kw= 'lava' | kw= 'streams' | kw= 'width' | kw= 'bridges' | kw= 'bridgeLength' | kw= 'meander' | kw= 'wavelength' | kw= 'flowSpeed' | kw= 'glow' | kw= 'crust' | kw= 'deathSound' | kw= 'buildSound' | kw= 'ufoRaid' | kw= 'trigger' | kw= 'ufos' | kw= 'duration' | kw= 'ufoSpeed' | kw= 'beamEvery' | kw= 'charge' | kw= 'beamSeconds' | kw= 'beamRadius' | kw= 'aimMin' | kw= 'aimMax' | kw= 'damageFraction' | kw= 'soldiers' | kw= 'soldierHealth' | kw= 'soldierDamage' | kw= 'soldierSight' | kw= 'soldierSeconds' | kw= 'ufoSprite' | kw= 'beamSprite' | kw= 'arriveSound' | kw= 'beamSound' | kw= 'shop' | kw= 'tier' | kw= 'price' | kw= 'amount' | kw= 'factor' | kw= 'description' | kw= 'unlock' | kw= 'fromLevel' | kw= 'theme' | kw= 'font' | kw= 'colour' | kw= 'image' | kw= 'slice' | kw= 'badge' )
            int alt101=112;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                alt101=1;
                }
                break;
            case 65:
                {
                alt101=2;
                }
                break;
            case 66:
                {
                alt101=3;
                }
                break;
            case 67:
                {
                alt101=4;
                }
                break;
            case 68:
                {
                alt101=5;
                }
                break;
            case 69:
                {
                alt101=6;
                }
                break;
            case 70:
                {
                alt101=7;
                }
                break;
            case 71:
                {
                alt101=8;
                }
                break;
            case 72:
                {
                alt101=9;
                }
                break;
            case 73:
                {
                alt101=10;
                }
                break;
            case 83:
                {
                alt101=11;
                }
                break;
            case 74:
                {
                alt101=12;
                }
                break;
            case 75:
                {
                alt101=13;
                }
                break;
            case 76:
                {
                alt101=14;
                }
                break;
            case 77:
                {
                alt101=15;
                }
                break;
            case 78:
                {
                alt101=16;
                }
                break;
            case 79:
                {
                alt101=17;
                }
                break;
            case 80:
                {
                alt101=18;
                }
                break;
            case 81:
                {
                alt101=19;
                }
                break;
            case 177:
                {
                alt101=20;
                }
                break;
            case 82:
                {
                alt101=21;
                }
                break;
            case 84:
                {
                alt101=22;
                }
                break;
            case 85:
                {
                alt101=23;
                }
                break;
            case 86:
                {
                alt101=24;
                }
                break;
            case 88:
                {
                alt101=25;
                }
                break;
            case 89:
                {
                alt101=26;
                }
                break;
            case 90:
                {
                alt101=27;
                }
                break;
            case 91:
                {
                alt101=28;
                }
                break;
            case 92:
                {
                alt101=29;
                }
                break;
            case 93:
                {
                alt101=30;
                }
                break;
            case 94:
                {
                alt101=31;
                }
                break;
            case 95:
                {
                alt101=32;
                }
                break;
            case 169:
                {
                alt101=33;
                }
                break;
            case 170:
                {
                alt101=34;
                }
                break;
            case 148:
                {
                alt101=35;
                }
                break;
            case 171:
                {
                alt101=36;
                }
                break;
            case 172:
                {
                alt101=37;
                }
                break;
            case 173:
                {
                alt101=38;
                }
                break;
            case 87:
                {
                alt101=39;
                }
                break;
            case 96:
                {
                alt101=40;
                }
                break;
            case 112:
                {
                alt101=41;
                }
                break;
            case 97:
                {
                alt101=42;
                }
                break;
            case 98:
                {
                alt101=43;
                }
                break;
            case 99:
                {
                alt101=44;
                }
                break;
            case 100:
                {
                alt101=45;
                }
                break;
            case 101:
                {
                alt101=46;
                }
                break;
            case 102:
                {
                alt101=47;
                }
                break;
            case 103:
                {
                alt101=48;
                }
                break;
            case 104:
                {
                alt101=49;
                }
                break;
            case 105:
                {
                alt101=50;
                }
                break;
            case 106:
                {
                alt101=51;
                }
                break;
            case 107:
                {
                alt101=52;
                }
                break;
            case 108:
                {
                alt101=53;
                }
                break;
            case 109:
                {
                alt101=54;
                }
                break;
            case 110:
                {
                alt101=55;
                }
                break;
            case 111:
                {
                alt101=56;
                }
                break;
            case 113:
                {
                alt101=57;
                }
                break;
            case 120:
                {
                alt101=58;
                }
                break;
            case 114:
                {
                alt101=59;
                }
                break;
            case 116:
                {
                alt101=60;
                }
                break;
            case 117:
                {
                alt101=61;
                }
                break;
            case 118:
                {
                alt101=62;
                }
                break;
            case 115:
                {
                alt101=63;
                }
                break;
            case 167:
                {
                alt101=64;
                }
                break;
            case 168:
                {
                alt101=65;
                }
                break;
            case 121:
                {
                alt101=66;
                }
                break;
            case 122:
                {
                alt101=67;
                }
                break;
            case 56:
                {
                alt101=68;
                }
                break;
            case 123:
                {
                alt101=69;
                }
                break;
            case 124:
                {
                alt101=70;
                }
                break;
            case 125:
                {
                alt101=71;
                }
                break;
            case 126:
                {
                alt101=72;
                }
                break;
            case 127:
                {
                alt101=73;
                }
                break;
            case 128:
                {
                alt101=74;
                }
                break;
            case 129:
                {
                alt101=75;
                }
                break;
            case 130:
                {
                alt101=76;
                }
                break;
            case 119:
                {
                alt101=77;
                }
                break;
            case 131:
                {
                alt101=78;
                }
                break;
            case 132:
                {
                alt101=79;
                }
                break;
            case 133:
                {
                alt101=80;
                }
                break;
            case 134:
                {
                alt101=81;
                }
                break;
            case 135:
                {
                alt101=82;
                }
                break;
            case 136:
                {
                alt101=83;
                }
                break;
            case 137:
                {
                alt101=84;
                }
                break;
            case 138:
                {
                alt101=85;
                }
                break;
            case 139:
                {
                alt101=86;
                }
                break;
            case 140:
                {
                alt101=87;
                }
                break;
            case 141:
                {
                alt101=88;
                }
                break;
            case 142:
                {
                alt101=89;
                }
                break;
            case 143:
                {
                alt101=90;
                }
                break;
            case 144:
                {
                alt101=91;
                }
                break;
            case 145:
                {
                alt101=92;
                }
                break;
            case 146:
                {
                alt101=93;
                }
                break;
            case 147:
                {
                alt101=94;
                }
                break;
            case 149:
                {
                alt101=95;
                }
                break;
            case 150:
                {
                alt101=96;
                }
                break;
            case 151:
                {
                alt101=97;
                }
                break;
            case 152:
                {
                alt101=98;
                }
                break;
            case 153:
                {
                alt101=99;
                }
                break;
            case 156:
                {
                alt101=100;
                }
                break;
            case 157:
                {
                alt101=101;
                }
                break;
            case 158:
                {
                alt101=102;
                }
                break;
            case 159:
                {
                alt101=103;
                }
                break;
            case 160:
                {
                alt101=104;
                }
                break;
            case 154:
                {
                alt101=105;
                }
                break;
            case 155:
                {
                alt101=106;
                }
                break;
            case 161:
                {
                alt101=107;
                }
                break;
            case 162:
                {
                alt101=108;
                }
                break;
            case 163:
                {
                alt101=109;
                }
                break;
            case 164:
                {
                alt101=110;
                }
                break;
            case 165:
                {
                alt101=111;
                }
                break;
            case 166:
                {
                alt101=112;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 101, 0, input);

                throw nvae;
            }

            switch (alt101) {
                case 1 :
                    // InternalMazeDsl.g:5899:3: this_ID_0= RULE_ID
                    {
                    this_ID_0=(Token)match(input,RULE_ID,FOLLOW_2); 

                    			current.merge(this_ID_0);
                    		

                    			newLeafNode(this_ID_0, grammarAccess.getValidIDAccess().getIDTerminalRuleCall_0());
                    		

                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:5907:3: kw= 'basedOn'
                    {
                    kw=(Token)match(input,65,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getBasedOnKeyword_1());
                    		

                    }
                    break;
                case 3 :
                    // InternalMazeDsl.g:5913:3: kw= 'threatCapacity'
                    {
                    kw=(Token)match(input,66,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getThreatCapacityKeyword_2());
                    		

                    }
                    break;
                case 4 :
                    // InternalMazeDsl.g:5919:3: kw= 'wallMaterials'
                    {
                    kw=(Token)match(input,67,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getWallMaterialsKeyword_3());
                    		

                    }
                    break;
                case 5 :
                    // InternalMazeDsl.g:5925:3: kw= 'inheritWeapons'
                    {
                    kw=(Token)match(input,68,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getInheritWeaponsKeyword_4());
                    		

                    }
                    break;
                case 6 :
                    // InternalMazeDsl.g:5931:3: kw= 'cascadeDepth'
                    {
                    kw=(Token)match(input,69,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getCascadeDepthKeyword_5());
                    		

                    }
                    break;
                case 7 :
                    // InternalMazeDsl.g:5937:3: kw= 'ring'
                    {
                    kw=(Token)match(input,70,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getRingKeyword_6());
                    		

                    }
                    break;
                case 8 :
                    // InternalMazeDsl.g:5943:3: kw= 'share'
                    {
                    kw=(Token)match(input,71,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getShareKeyword_7());
                    		

                    }
                    break;
                case 9 :
                    // InternalMazeDsl.g:5949:3: kw= 'materials'
                    {
                    kw=(Token)match(input,72,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getMaterialsKeyword_8());
                    		

                    }
                    break;
                case 10 :
                    // InternalMazeDsl.g:5955:3: kw= 'sergeant'
                    {
                    kw=(Token)match(input,73,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getSergeantKeyword_9());
                    		

                    }
                    break;
                case 11 :
                    // InternalMazeDsl.g:5961:3: kw= 'sergeants'
                    {
                    kw=(Token)match(input,83,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getSergeantsKeyword_10());
                    		

                    }
                    break;
                case 12 :
                    // InternalMazeDsl.g:5967:3: kw= 'sightRange'
                    {
                    kw=(Token)match(input,74,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getSightRangeKeyword_11());
                    		

                    }
                    break;
                case 13 :
                    // InternalMazeDsl.g:5973:3: kw= 'rounds'
                    {
                    kw=(Token)match(input,75,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getRoundsKeyword_12());
                    		

                    }
                    break;
                case 14 :
                    // InternalMazeDsl.g:5979:3: kw= 'roundDamage'
                    {
                    kw=(Token)match(input,76,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getRoundDamageKeyword_13());
                    		

                    }
                    break;
                case 15 :
                    // InternalMazeDsl.g:5985:3: kw= 'roundInterval'
                    {
                    kw=(Token)match(input,77,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getRoundIntervalKeyword_14());
                    		

                    }
                    break;
                case 16 :
                    // InternalMazeDsl.g:5991:3: kw= 'burstCooldown'
                    {
                    kw=(Token)match(input,78,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getBurstCooldownKeyword_15());
                    		

                    }
                    break;
                case 17 :
                    // InternalMazeDsl.g:5997:3: kw= 'sprite'
                    {
                    kw=(Token)match(input,79,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getSpriteKeyword_16());
                    		

                    }
                    break;
                case 18 :
                    // InternalMazeDsl.g:6003:3: kw= 'fireSound'
                    {
                    kw=(Token)match(input,80,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getFireSoundKeyword_17());
                    		

                    }
                    break;
                case 19 :
                    // InternalMazeDsl.g:6009:3: kw= 'drops'
                    {
                    kw=(Token)match(input,81,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getDropsKeyword_18());
                    		

                    }
                    break;
                case 20 :
                    // InternalMazeDsl.g:6015:3: kw= 'gatling'
                    {
                    kw=(Token)match(input,177,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getGatlingKeyword_19());
                    		

                    }
                    break;
                case 21 :
                    // InternalMazeDsl.g:6021:3: kw= 'postDistance'
                    {
                    kw=(Token)match(input,82,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getPostDistanceKeyword_20());
                    		

                    }
                    break;
                case 22 :
                    // InternalMazeDsl.g:6027:3: kw= 'mines'
                    {
                    kw=(Token)match(input,84,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getMinesKeyword_21());
                    		

                    }
                    break;
                case 23 :
                    // InternalMazeDsl.g:6033:3: kw= 'count'
                    {
                    kw=(Token)match(input,85,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getCountKeyword_22());
                    		

                    }
                    break;
                case 24 :
                    // InternalMazeDsl.g:6039:3: kw= 'ringRadius'
                    {
                    kw=(Token)match(input,86,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getRingRadiusKeyword_23());
                    		

                    }
                    break;
                case 25 :
                    // InternalMazeDsl.g:6045:3: kw= 'triggerRadius'
                    {
                    kw=(Token)match(input,88,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getTriggerRadiusKeyword_24());
                    		

                    }
                    break;
                case 26 :
                    // InternalMazeDsl.g:6051:3: kw= 'pulse'
                    {
                    kw=(Token)match(input,89,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getPulseKeyword_25());
                    		

                    }
                    break;
                case 27 :
                    // InternalMazeDsl.g:6057:3: kw= 'blastRadius'
                    {
                    kw=(Token)match(input,90,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getBlastRadiusKeyword_26());
                    		

                    }
                    break;
                case 28 :
                    // InternalMazeDsl.g:6063:3: kw= 'blastDamage'
                    {
                    kw=(Token)match(input,91,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getBlastDamageKeyword_27());
                    		

                    }
                    break;
                case 29 :
                    // InternalMazeDsl.g:6069:3: kw= 'playerDamage'
                    {
                    kw=(Token)match(input,92,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getPlayerDamageKeyword_28());
                    		

                    }
                    break;
                case 30 :
                    // InternalMazeDsl.g:6075:3: kw= 'holeRadius'
                    {
                    kw=(Token)match(input,93,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getHoleRadiusKeyword_29());
                    		

                    }
                    break;
                case 31 :
                    // InternalMazeDsl.g:6081:3: kw= 'portalGhost'
                    {
                    kw=(Token)match(input,94,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getPortalGhostKeyword_30());
                    		

                    }
                    break;
                case 32 :
                    // InternalMazeDsl.g:6087:3: kw= 'portalGhosts'
                    {
                    kw=(Token)match(input,95,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getPortalGhostsKeyword_31());
                    		

                    }
                    break;
                case 33 :
                    // InternalMazeDsl.g:6093:3: kw= 'teleport'
                    {
                    kw=(Token)match(input,169,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getTeleportKeyword_32());
                    		

                    }
                    break;
                case 34 :
                    // InternalMazeDsl.g:6099:3: kw= 'cooldown'
                    {
                    kw=(Token)match(input,170,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getCooldownKeyword_33());
                    		

                    }
                    break;
                case 35 :
                    // InternalMazeDsl.g:6105:3: kw= 'banner'
                    {
                    kw=(Token)match(input,148,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getBannerKeyword_34());
                    		

                    }
                    break;
                case 36 :
                    // InternalMazeDsl.g:6111:3: kw= 'bannerSeconds'
                    {
                    kw=(Token)match(input,171,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getBannerSecondsKeyword_35());
                    		

                    }
                    break;
                case 37 :
                    // InternalMazeDsl.g:6117:3: kw= 'zapSound'
                    {
                    kw=(Token)match(input,172,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getZapSoundKeyword_36());
                    		

                    }
                    break;
                case 38 :
                    // InternalMazeDsl.g:6123:3: kw= 'voiceSound'
                    {
                    kw=(Token)match(input,173,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getVoiceSoundKeyword_37());
                    		

                    }
                    break;
                case 39 :
                    // InternalMazeDsl.g:6129:3: kw= 'secondRing'
                    {
                    kw=(Token)match(input,87,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getSecondRingKeyword_38());
                    		

                    }
                    break;
                case 40 :
                    // InternalMazeDsl.g:6135:3: kw= 'guardTower'
                    {
                    kw=(Token)match(input,96,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getGuardTowerKeyword_39());
                    		

                    }
                    break;
                case 41 :
                    // InternalMazeDsl.g:6141:3: kw= 'towers'
                    {
                    kw=(Token)match(input,112,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getTowersKeyword_40());
                    		

                    }
                    break;
                case 42 :
                    // InternalMazeDsl.g:6147:3: kw= 'wallsOver'
                    {
                    kw=(Token)match(input,97,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getWallsOverKeyword_41());
                    		

                    }
                    break;
                case 43 :
                    // InternalMazeDsl.g:6153:3: kw= 'rpgInterval'
                    {
                    kw=(Token)match(input,98,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getRpgIntervalKeyword_42());
                    		

                    }
                    break;
                case 44 :
                    // InternalMazeDsl.g:6159:3: kw= 'rpgSpeed'
                    {
                    kw=(Token)match(input,99,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getRpgSpeedKeyword_43());
                    		

                    }
                    break;
                case 45 :
                    // InternalMazeDsl.g:6165:3: kw= 'rpgBlastRadius'
                    {
                    kw=(Token)match(input,100,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getRpgBlastRadiusKeyword_44());
                    		

                    }
                    break;
                case 46 :
                    // InternalMazeDsl.g:6171:3: kw= 'rpgPlayerDamage'
                    {
                    kw=(Token)match(input,101,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getRpgPlayerDamageKeyword_45());
                    		

                    }
                    break;
                case 47 :
                    // InternalMazeDsl.g:6177:3: kw= 'rpgWallDamage'
                    {
                    kw=(Token)match(input,102,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getRpgWallDamageKeyword_46());
                    		

                    }
                    break;
                case 48 :
                    // InternalMazeDsl.g:6183:3: kw= 'gatlingRounds'
                    {
                    kw=(Token)match(input,103,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getGatlingRoundsKeyword_47());
                    		

                    }
                    break;
                case 49 :
                    // InternalMazeDsl.g:6189:3: kw= 'gatlingInterval'
                    {
                    kw=(Token)match(input,104,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getGatlingIntervalKeyword_48());
                    		

                    }
                    break;
                case 50 :
                    // InternalMazeDsl.g:6195:3: kw= 'gatlingDamage'
                    {
                    kw=(Token)match(input,105,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getGatlingDamageKeyword_49());
                    		

                    }
                    break;
                case 51 :
                    // InternalMazeDsl.g:6201:3: kw= 'deathBlast'
                    {
                    kw=(Token)match(input,106,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getDeathBlastKeyword_50());
                    		

                    }
                    break;
                case 52 :
                    // InternalMazeDsl.g:6207:3: kw= 'rpgLaunchSound'
                    {
                    kw=(Token)match(input,107,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getRpgLaunchSoundKeyword_51());
                    		

                    }
                    break;
                case 53 :
                    // InternalMazeDsl.g:6213:3: kw= 'rpgExplosionSound'
                    {
                    kw=(Token)match(input,108,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getRpgExplosionSoundKeyword_52());
                    		

                    }
                    break;
                case 54 :
                    // InternalMazeDsl.g:6219:3: kw= 'gatlingSound'
                    {
                    kw=(Token)match(input,109,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getGatlingSoundKeyword_53());
                    		

                    }
                    break;
                case 55 :
                    // InternalMazeDsl.g:6225:3: kw= 'hitSound'
                    {
                    kw=(Token)match(input,110,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getHitSoundKeyword_54());
                    		

                    }
                    break;
                case 56 :
                    // InternalMazeDsl.g:6231:3: kw= 'destroyedSound'
                    {
                    kw=(Token)match(input,111,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getDestroyedSoundKeyword_55());
                    		

                    }
                    break;
                case 57 :
                    // InternalMazeDsl.g:6237:3: kw= 'wallBuilder'
                    {
                    kw=(Token)match(input,113,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getWallBuilderKeyword_56());
                    		

                    }
                    break;
                case 58 :
                    // InternalMazeDsl.g:6243:3: kw= 'builders'
                    {
                    kw=(Token)match(input,120,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getBuildersKeyword_57());
                    		

                    }
                    break;
                case 59 :
                    // InternalMazeDsl.g:6249:3: kw= 'buildSeconds'
                    {
                    kw=(Token)match(input,114,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getBuildSecondsKeyword_58());
                    		

                    }
                    break;
                case 60 :
                    // InternalMazeDsl.g:6255:3: kw= 'blockRadius'
                    {
                    kw=(Token)match(input,116,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getBlockRadiusKeyword_59());
                    		

                    }
                    break;
                case 61 :
                    // InternalMazeDsl.g:6261:3: kw= 'spawnRadius'
                    {
                    kw=(Token)match(input,117,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getSpawnRadiusKeyword_60());
                    		

                    }
                    break;
                case 62 :
                    // InternalMazeDsl.g:6267:3: kw= 'material'
                    {
                    kw=(Token)match(input,118,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getMaterialKeyword_61());
                    		

                    }
                    break;
                case 63 :
                    // InternalMazeDsl.g:6273:3: kw= 'startRadius'
                    {
                    kw=(Token)match(input,115,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getStartRadiusKeyword_62());
                    		

                    }
                    break;
                case 64 :
                    // InternalMazeDsl.g:6279:3: kw= 'lastLifeWeapon'
                    {
                    kw=(Token)match(input,167,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getLastLifeWeaponKeyword_63());
                    		

                    }
                    break;
                case 65 :
                    // InternalMazeDsl.g:6285:3: kw= 'shots'
                    {
                    kw=(Token)match(input,168,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getShotsKeyword_64());
                    		

                    }
                    break;
                case 66 :
                    // InternalMazeDsl.g:6291:3: kw= 'lava'
                    {
                    kw=(Token)match(input,121,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getLavaKeyword_65());
                    		

                    }
                    break;
                case 67 :
                    // InternalMazeDsl.g:6297:3: kw= 'streams'
                    {
                    kw=(Token)match(input,122,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getStreamsKeyword_66());
                    		

                    }
                    break;
                case 68 :
                    // InternalMazeDsl.g:6303:3: kw= 'width'
                    {
                    kw=(Token)match(input,56,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getWidthKeyword_67());
                    		

                    }
                    break;
                case 69 :
                    // InternalMazeDsl.g:6309:3: kw= 'bridges'
                    {
                    kw=(Token)match(input,123,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getBridgesKeyword_68());
                    		

                    }
                    break;
                case 70 :
                    // InternalMazeDsl.g:6315:3: kw= 'bridgeLength'
                    {
                    kw=(Token)match(input,124,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getBridgeLengthKeyword_69());
                    		

                    }
                    break;
                case 71 :
                    // InternalMazeDsl.g:6321:3: kw= 'meander'
                    {
                    kw=(Token)match(input,125,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getMeanderKeyword_70());
                    		

                    }
                    break;
                case 72 :
                    // InternalMazeDsl.g:6327:3: kw= 'wavelength'
                    {
                    kw=(Token)match(input,126,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getWavelengthKeyword_71());
                    		

                    }
                    break;
                case 73 :
                    // InternalMazeDsl.g:6333:3: kw= 'flowSpeed'
                    {
                    kw=(Token)match(input,127,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getFlowSpeedKeyword_72());
                    		

                    }
                    break;
                case 74 :
                    // InternalMazeDsl.g:6339:3: kw= 'glow'
                    {
                    kw=(Token)match(input,128,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getGlowKeyword_73());
                    		

                    }
                    break;
                case 75 :
                    // InternalMazeDsl.g:6345:3: kw= 'crust'
                    {
                    kw=(Token)match(input,129,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getCrustKeyword_74());
                    		

                    }
                    break;
                case 76 :
                    // InternalMazeDsl.g:6351:3: kw= 'deathSound'
                    {
                    kw=(Token)match(input,130,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getDeathSoundKeyword_75());
                    		

                    }
                    break;
                case 77 :
                    // InternalMazeDsl.g:6357:3: kw= 'buildSound'
                    {
                    kw=(Token)match(input,119,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getBuildSoundKeyword_76());
                    		

                    }
                    break;
                case 78 :
                    // InternalMazeDsl.g:6363:3: kw= 'ufoRaid'
                    {
                    kw=(Token)match(input,131,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getUfoRaidKeyword_77());
                    		

                    }
                    break;
                case 79 :
                    // InternalMazeDsl.g:6369:3: kw= 'trigger'
                    {
                    kw=(Token)match(input,132,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getTriggerKeyword_78());
                    		

                    }
                    break;
                case 80 :
                    // InternalMazeDsl.g:6375:3: kw= 'ufos'
                    {
                    kw=(Token)match(input,133,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getUfosKeyword_79());
                    		

                    }
                    break;
                case 81 :
                    // InternalMazeDsl.g:6381:3: kw= 'duration'
                    {
                    kw=(Token)match(input,134,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getDurationKeyword_80());
                    		

                    }
                    break;
                case 82 :
                    // InternalMazeDsl.g:6387:3: kw= 'ufoSpeed'
                    {
                    kw=(Token)match(input,135,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getUfoSpeedKeyword_81());
                    		

                    }
                    break;
                case 83 :
                    // InternalMazeDsl.g:6393:3: kw= 'beamEvery'
                    {
                    kw=(Token)match(input,136,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getBeamEveryKeyword_82());
                    		

                    }
                    break;
                case 84 :
                    // InternalMazeDsl.g:6399:3: kw= 'charge'
                    {
                    kw=(Token)match(input,137,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getChargeKeyword_83());
                    		

                    }
                    break;
                case 85 :
                    // InternalMazeDsl.g:6405:3: kw= 'beamSeconds'
                    {
                    kw=(Token)match(input,138,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getBeamSecondsKeyword_84());
                    		

                    }
                    break;
                case 86 :
                    // InternalMazeDsl.g:6411:3: kw= 'beamRadius'
                    {
                    kw=(Token)match(input,139,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getBeamRadiusKeyword_85());
                    		

                    }
                    break;
                case 87 :
                    // InternalMazeDsl.g:6417:3: kw= 'aimMin'
                    {
                    kw=(Token)match(input,140,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getAimMinKeyword_86());
                    		

                    }
                    break;
                case 88 :
                    // InternalMazeDsl.g:6423:3: kw= 'aimMax'
                    {
                    kw=(Token)match(input,141,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getAimMaxKeyword_87());
                    		

                    }
                    break;
                case 89 :
                    // InternalMazeDsl.g:6429:3: kw= 'damageFraction'
                    {
                    kw=(Token)match(input,142,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getDamageFractionKeyword_88());
                    		

                    }
                    break;
                case 90 :
                    // InternalMazeDsl.g:6435:3: kw= 'soldiers'
                    {
                    kw=(Token)match(input,143,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getSoldiersKeyword_89());
                    		

                    }
                    break;
                case 91 :
                    // InternalMazeDsl.g:6441:3: kw= 'soldierHealth'
                    {
                    kw=(Token)match(input,144,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getSoldierHealthKeyword_90());
                    		

                    }
                    break;
                case 92 :
                    // InternalMazeDsl.g:6447:3: kw= 'soldierDamage'
                    {
                    kw=(Token)match(input,145,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getSoldierDamageKeyword_91());
                    		

                    }
                    break;
                case 93 :
                    // InternalMazeDsl.g:6453:3: kw= 'soldierSight'
                    {
                    kw=(Token)match(input,146,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getSoldierSightKeyword_92());
                    		

                    }
                    break;
                case 94 :
                    // InternalMazeDsl.g:6459:3: kw= 'soldierSeconds'
                    {
                    kw=(Token)match(input,147,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getSoldierSecondsKeyword_93());
                    		

                    }
                    break;
                case 95 :
                    // InternalMazeDsl.g:6465:3: kw= 'ufoSprite'
                    {
                    kw=(Token)match(input,149,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getUfoSpriteKeyword_94());
                    		

                    }
                    break;
                case 96 :
                    // InternalMazeDsl.g:6471:3: kw= 'beamSprite'
                    {
                    kw=(Token)match(input,150,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getBeamSpriteKeyword_95());
                    		

                    }
                    break;
                case 97 :
                    // InternalMazeDsl.g:6477:3: kw= 'arriveSound'
                    {
                    kw=(Token)match(input,151,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getArriveSoundKeyword_96());
                    		

                    }
                    break;
                case 98 :
                    // InternalMazeDsl.g:6483:3: kw= 'beamSound'
                    {
                    kw=(Token)match(input,152,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getBeamSoundKeyword_97());
                    		

                    }
                    break;
                case 99 :
                    // InternalMazeDsl.g:6489:3: kw= 'shop'
                    {
                    kw=(Token)match(input,153,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getShopKeyword_98());
                    		

                    }
                    break;
                case 100 :
                    // InternalMazeDsl.g:6495:3: kw= 'tier'
                    {
                    kw=(Token)match(input,156,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getTierKeyword_99());
                    		

                    }
                    break;
                case 101 :
                    // InternalMazeDsl.g:6501:3: kw= 'price'
                    {
                    kw=(Token)match(input,157,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getPriceKeyword_100());
                    		

                    }
                    break;
                case 102 :
                    // InternalMazeDsl.g:6507:3: kw= 'amount'
                    {
                    kw=(Token)match(input,158,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getAmountKeyword_101());
                    		

                    }
                    break;
                case 103 :
                    // InternalMazeDsl.g:6513:3: kw= 'factor'
                    {
                    kw=(Token)match(input,159,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getFactorKeyword_102());
                    		

                    }
                    break;
                case 104 :
                    // InternalMazeDsl.g:6519:3: kw= 'description'
                    {
                    kw=(Token)match(input,160,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getDescriptionKeyword_103());
                    		

                    }
                    break;
                case 105 :
                    // InternalMazeDsl.g:6525:3: kw= 'unlock'
                    {
                    kw=(Token)match(input,154,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getUnlockKeyword_104());
                    		

                    }
                    break;
                case 106 :
                    // InternalMazeDsl.g:6531:3: kw= 'fromLevel'
                    {
                    kw=(Token)match(input,155,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getFromLevelKeyword_105());
                    		

                    }
                    break;
                case 107 :
                    // InternalMazeDsl.g:6537:3: kw= 'theme'
                    {
                    kw=(Token)match(input,161,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getThemeKeyword_106());
                    		

                    }
                    break;
                case 108 :
                    // InternalMazeDsl.g:6543:3: kw= 'font'
                    {
                    kw=(Token)match(input,162,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getFontKeyword_107());
                    		

                    }
                    break;
                case 109 :
                    // InternalMazeDsl.g:6549:3: kw= 'colour'
                    {
                    kw=(Token)match(input,163,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getColourKeyword_108());
                    		

                    }
                    break;
                case 110 :
                    // InternalMazeDsl.g:6555:3: kw= 'image'
                    {
                    kw=(Token)match(input,164,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getImageKeyword_109());
                    		

                    }
                    break;
                case 111 :
                    // InternalMazeDsl.g:6561:3: kw= 'slice'
                    {
                    kw=(Token)match(input,165,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getSliceKeyword_110());
                    		

                    }
                    break;
                case 112 :
                    // InternalMazeDsl.g:6567:3: kw= 'badge'
                    {
                    kw=(Token)match(input,166,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getBadgeKeyword_111());
                    		

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
    // InternalMazeDsl.g:6576:1: ruleDifficultyLevel returns [Enumerator current=null] : ( (enumLiteral_0= 'easy' ) | (enumLiteral_1= 'normal' ) | (enumLiteral_2= 'hard' ) ) ;
    public final Enumerator ruleDifficultyLevel() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalMazeDsl.g:6582:2: ( ( (enumLiteral_0= 'easy' ) | (enumLiteral_1= 'normal' ) | (enumLiteral_2= 'hard' ) ) )
            // InternalMazeDsl.g:6583:2: ( (enumLiteral_0= 'easy' ) | (enumLiteral_1= 'normal' ) | (enumLiteral_2= 'hard' ) )
            {
            // InternalMazeDsl.g:6583:2: ( (enumLiteral_0= 'easy' ) | (enumLiteral_1= 'normal' ) | (enumLiteral_2= 'hard' ) )
            int alt102=3;
            switch ( input.LA(1) ) {
            case 178:
                {
                alt102=1;
                }
                break;
            case 179:
                {
                alt102=2;
                }
                break;
            case 180:
                {
                alt102=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 102, 0, input);

                throw nvae;
            }

            switch (alt102) {
                case 1 :
                    // InternalMazeDsl.g:6584:3: (enumLiteral_0= 'easy' )
                    {
                    // InternalMazeDsl.g:6584:3: (enumLiteral_0= 'easy' )
                    // InternalMazeDsl.g:6585:4: enumLiteral_0= 'easy'
                    {
                    enumLiteral_0=(Token)match(input,178,FOLLOW_2); 

                    				current = grammarAccess.getDifficultyLevelAccess().getEASYEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getDifficultyLevelAccess().getEASYEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:6592:3: (enumLiteral_1= 'normal' )
                    {
                    // InternalMazeDsl.g:6592:3: (enumLiteral_1= 'normal' )
                    // InternalMazeDsl.g:6593:4: enumLiteral_1= 'normal'
                    {
                    enumLiteral_1=(Token)match(input,179,FOLLOW_2); 

                    				current = grammarAccess.getDifficultyLevelAccess().getNORMALEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getDifficultyLevelAccess().getNORMALEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalMazeDsl.g:6600:3: (enumLiteral_2= 'hard' )
                    {
                    // InternalMazeDsl.g:6600:3: (enumLiteral_2= 'hard' )
                    // InternalMazeDsl.g:6601:4: enumLiteral_2= 'hard'
                    {
                    enumLiteral_2=(Token)match(input,180,FOLLOW_2); 

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
    // InternalMazeDsl.g:6611:1: ruleEnemyType returns [Enumerator current=null] : ( (enumLiteral_0= 'zombie' ) | (enumLiteral_1= 'ghost' ) | (enumLiteral_2= 'pumpkinbomber' ) ) ;
    public final Enumerator ruleEnemyType() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalMazeDsl.g:6617:2: ( ( (enumLiteral_0= 'zombie' ) | (enumLiteral_1= 'ghost' ) | (enumLiteral_2= 'pumpkinbomber' ) ) )
            // InternalMazeDsl.g:6618:2: ( (enumLiteral_0= 'zombie' ) | (enumLiteral_1= 'ghost' ) | (enumLiteral_2= 'pumpkinbomber' ) )
            {
            // InternalMazeDsl.g:6618:2: ( (enumLiteral_0= 'zombie' ) | (enumLiteral_1= 'ghost' ) | (enumLiteral_2= 'pumpkinbomber' ) )
            int alt103=3;
            switch ( input.LA(1) ) {
            case 181:
                {
                alt103=1;
                }
                break;
            case 182:
                {
                alt103=2;
                }
                break;
            case 183:
                {
                alt103=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 103, 0, input);

                throw nvae;
            }

            switch (alt103) {
                case 1 :
                    // InternalMazeDsl.g:6619:3: (enumLiteral_0= 'zombie' )
                    {
                    // InternalMazeDsl.g:6619:3: (enumLiteral_0= 'zombie' )
                    // InternalMazeDsl.g:6620:4: enumLiteral_0= 'zombie'
                    {
                    enumLiteral_0=(Token)match(input,181,FOLLOW_2); 

                    				current = grammarAccess.getEnemyTypeAccess().getZOMBIEEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getEnemyTypeAccess().getZOMBIEEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:6627:3: (enumLiteral_1= 'ghost' )
                    {
                    // InternalMazeDsl.g:6627:3: (enumLiteral_1= 'ghost' )
                    // InternalMazeDsl.g:6628:4: enumLiteral_1= 'ghost'
                    {
                    enumLiteral_1=(Token)match(input,182,FOLLOW_2); 

                    				current = grammarAccess.getEnemyTypeAccess().getGHOSTEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getEnemyTypeAccess().getGHOSTEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalMazeDsl.g:6635:3: (enumLiteral_2= 'pumpkinbomber' )
                    {
                    // InternalMazeDsl.g:6635:3: (enumLiteral_2= 'pumpkinbomber' )
                    // InternalMazeDsl.g:6636:4: enumLiteral_2= 'pumpkinbomber'
                    {
                    enumLiteral_2=(Token)match(input,183,FOLLOW_2); 

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
    // InternalMazeDsl.g:6646:1: ruleCharacterTypeEnum returns [Enumerator current=null] : ( (enumLiteral_0= 'zombie' ) | (enumLiteral_1= 'ghost' ) | (enumLiteral_2= 'pumpkinbomber' ) ) ;
    public final Enumerator ruleCharacterTypeEnum() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalMazeDsl.g:6652:2: ( ( (enumLiteral_0= 'zombie' ) | (enumLiteral_1= 'ghost' ) | (enumLiteral_2= 'pumpkinbomber' ) ) )
            // InternalMazeDsl.g:6653:2: ( (enumLiteral_0= 'zombie' ) | (enumLiteral_1= 'ghost' ) | (enumLiteral_2= 'pumpkinbomber' ) )
            {
            // InternalMazeDsl.g:6653:2: ( (enumLiteral_0= 'zombie' ) | (enumLiteral_1= 'ghost' ) | (enumLiteral_2= 'pumpkinbomber' ) )
            int alt104=3;
            switch ( input.LA(1) ) {
            case 181:
                {
                alt104=1;
                }
                break;
            case 182:
                {
                alt104=2;
                }
                break;
            case 183:
                {
                alt104=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 104, 0, input);

                throw nvae;
            }

            switch (alt104) {
                case 1 :
                    // InternalMazeDsl.g:6654:3: (enumLiteral_0= 'zombie' )
                    {
                    // InternalMazeDsl.g:6654:3: (enumLiteral_0= 'zombie' )
                    // InternalMazeDsl.g:6655:4: enumLiteral_0= 'zombie'
                    {
                    enumLiteral_0=(Token)match(input,181,FOLLOW_2); 

                    				current = grammarAccess.getCharacterTypeEnumAccess().getZOMBIEEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getCharacterTypeEnumAccess().getZOMBIEEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:6662:3: (enumLiteral_1= 'ghost' )
                    {
                    // InternalMazeDsl.g:6662:3: (enumLiteral_1= 'ghost' )
                    // InternalMazeDsl.g:6663:4: enumLiteral_1= 'ghost'
                    {
                    enumLiteral_1=(Token)match(input,182,FOLLOW_2); 

                    				current = grammarAccess.getCharacterTypeEnumAccess().getGHOSTEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getCharacterTypeEnumAccess().getGHOSTEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalMazeDsl.g:6670:3: (enumLiteral_2= 'pumpkinbomber' )
                    {
                    // InternalMazeDsl.g:6670:3: (enumLiteral_2= 'pumpkinbomber' )
                    // InternalMazeDsl.g:6671:4: enumLiteral_2= 'pumpkinbomber'
                    {
                    enumLiteral_2=(Token)match(input,183,FOLLOW_2); 

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
    // InternalMazeDsl.g:6681:1: ruleBehaviorTypeEnum returns [Enumerator current=null] : ( (enumLiteral_0= 'passive' ) | (enumLiteral_1= 'wander' ) | (enumLiteral_2= 'aggressive' ) | (enumLiteral_3= 'patrol' ) ) ;
    public final Enumerator ruleBehaviorTypeEnum() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;


        	enterRule();

        try {
            // InternalMazeDsl.g:6687:2: ( ( (enumLiteral_0= 'passive' ) | (enumLiteral_1= 'wander' ) | (enumLiteral_2= 'aggressive' ) | (enumLiteral_3= 'patrol' ) ) )
            // InternalMazeDsl.g:6688:2: ( (enumLiteral_0= 'passive' ) | (enumLiteral_1= 'wander' ) | (enumLiteral_2= 'aggressive' ) | (enumLiteral_3= 'patrol' ) )
            {
            // InternalMazeDsl.g:6688:2: ( (enumLiteral_0= 'passive' ) | (enumLiteral_1= 'wander' ) | (enumLiteral_2= 'aggressive' ) | (enumLiteral_3= 'patrol' ) )
            int alt105=4;
            switch ( input.LA(1) ) {
            case 184:
                {
                alt105=1;
                }
                break;
            case 185:
                {
                alt105=2;
                }
                break;
            case 186:
                {
                alt105=3;
                }
                break;
            case 32:
                {
                alt105=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 105, 0, input);

                throw nvae;
            }

            switch (alt105) {
                case 1 :
                    // InternalMazeDsl.g:6689:3: (enumLiteral_0= 'passive' )
                    {
                    // InternalMazeDsl.g:6689:3: (enumLiteral_0= 'passive' )
                    // InternalMazeDsl.g:6690:4: enumLiteral_0= 'passive'
                    {
                    enumLiteral_0=(Token)match(input,184,FOLLOW_2); 

                    				current = grammarAccess.getBehaviorTypeEnumAccess().getPASSIVEEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getBehaviorTypeEnumAccess().getPASSIVEEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:6697:3: (enumLiteral_1= 'wander' )
                    {
                    // InternalMazeDsl.g:6697:3: (enumLiteral_1= 'wander' )
                    // InternalMazeDsl.g:6698:4: enumLiteral_1= 'wander'
                    {
                    enumLiteral_1=(Token)match(input,185,FOLLOW_2); 

                    				current = grammarAccess.getBehaviorTypeEnumAccess().getWANDEREnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getBehaviorTypeEnumAccess().getWANDEREnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalMazeDsl.g:6705:3: (enumLiteral_2= 'aggressive' )
                    {
                    // InternalMazeDsl.g:6705:3: (enumLiteral_2= 'aggressive' )
                    // InternalMazeDsl.g:6706:4: enumLiteral_2= 'aggressive'
                    {
                    enumLiteral_2=(Token)match(input,186,FOLLOW_2); 

                    				current = grammarAccess.getBehaviorTypeEnumAccess().getAGGRESSIVEEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getBehaviorTypeEnumAccess().getAGGRESSIVEEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalMazeDsl.g:6713:3: (enumLiteral_3= 'patrol' )
                    {
                    // InternalMazeDsl.g:6713:3: (enumLiteral_3= 'patrol' )
                    // InternalMazeDsl.g:6714:4: enumLiteral_3= 'patrol'
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
    // InternalMazeDsl.g:6724:1: ruleProjectileTypeEnum returns [Enumerator current=null] : ( (enumLiteral_0= 'straight' ) | (enumLiteral_1= 'lob' ) | (enumLiteral_2= 'beam' ) ) ;
    public final Enumerator ruleProjectileTypeEnum() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalMazeDsl.g:6730:2: ( ( (enumLiteral_0= 'straight' ) | (enumLiteral_1= 'lob' ) | (enumLiteral_2= 'beam' ) ) )
            // InternalMazeDsl.g:6731:2: ( (enumLiteral_0= 'straight' ) | (enumLiteral_1= 'lob' ) | (enumLiteral_2= 'beam' ) )
            {
            // InternalMazeDsl.g:6731:2: ( (enumLiteral_0= 'straight' ) | (enumLiteral_1= 'lob' ) | (enumLiteral_2= 'beam' ) )
            int alt106=3;
            switch ( input.LA(1) ) {
            case 187:
                {
                alt106=1;
                }
                break;
            case 188:
                {
                alt106=2;
                }
                break;
            case 189:
                {
                alt106=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 106, 0, input);

                throw nvae;
            }

            switch (alt106) {
                case 1 :
                    // InternalMazeDsl.g:6732:3: (enumLiteral_0= 'straight' )
                    {
                    // InternalMazeDsl.g:6732:3: (enumLiteral_0= 'straight' )
                    // InternalMazeDsl.g:6733:4: enumLiteral_0= 'straight'
                    {
                    enumLiteral_0=(Token)match(input,187,FOLLOW_2); 

                    				current = grammarAccess.getProjectileTypeEnumAccess().getSTRAIGHTEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getProjectileTypeEnumAccess().getSTRAIGHTEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:6740:3: (enumLiteral_1= 'lob' )
                    {
                    // InternalMazeDsl.g:6740:3: (enumLiteral_1= 'lob' )
                    // InternalMazeDsl.g:6741:4: enumLiteral_1= 'lob'
                    {
                    enumLiteral_1=(Token)match(input,188,FOLLOW_2); 

                    				current = grammarAccess.getProjectileTypeEnumAccess().getLOBEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getProjectileTypeEnumAccess().getLOBEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalMazeDsl.g:6748:3: (enumLiteral_2= 'beam' )
                    {
                    // InternalMazeDsl.g:6748:3: (enumLiteral_2= 'beam' )
                    // InternalMazeDsl.g:6749:4: enumLiteral_2= 'beam'
                    {
                    enumLiteral_2=(Token)match(input,189,FOLLOW_2); 

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
    // InternalMazeDsl.g:6759:1: ruleLootItemTypeEnum returns [Enumerator current=null] : ( (enumLiteral_0= 'food' ) | (enumLiteral_1= 'bomb' ) | (enumLiteral_2= 'trap' ) | (enumLiteral_3= 'weapon' ) ) ;
    public final Enumerator ruleLootItemTypeEnum() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;


        	enterRule();

        try {
            // InternalMazeDsl.g:6765:2: ( ( (enumLiteral_0= 'food' ) | (enumLiteral_1= 'bomb' ) | (enumLiteral_2= 'trap' ) | (enumLiteral_3= 'weapon' ) ) )
            // InternalMazeDsl.g:6766:2: ( (enumLiteral_0= 'food' ) | (enumLiteral_1= 'bomb' ) | (enumLiteral_2= 'trap' ) | (enumLiteral_3= 'weapon' ) )
            {
            // InternalMazeDsl.g:6766:2: ( (enumLiteral_0= 'food' ) | (enumLiteral_1= 'bomb' ) | (enumLiteral_2= 'trap' ) | (enumLiteral_3= 'weapon' ) )
            int alt107=4;
            switch ( input.LA(1) ) {
            case 190:
                {
                alt107=1;
                }
                break;
            case 191:
                {
                alt107=2;
                }
                break;
            case 192:
                {
                alt107=3;
                }
                break;
            case 193:
                {
                alt107=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 107, 0, input);

                throw nvae;
            }

            switch (alt107) {
                case 1 :
                    // InternalMazeDsl.g:6767:3: (enumLiteral_0= 'food' )
                    {
                    // InternalMazeDsl.g:6767:3: (enumLiteral_0= 'food' )
                    // InternalMazeDsl.g:6768:4: enumLiteral_0= 'food'
                    {
                    enumLiteral_0=(Token)match(input,190,FOLLOW_2); 

                    				current = grammarAccess.getLootItemTypeEnumAccess().getFOODEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getLootItemTypeEnumAccess().getFOODEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:6775:3: (enumLiteral_1= 'bomb' )
                    {
                    // InternalMazeDsl.g:6775:3: (enumLiteral_1= 'bomb' )
                    // InternalMazeDsl.g:6776:4: enumLiteral_1= 'bomb'
                    {
                    enumLiteral_1=(Token)match(input,191,FOLLOW_2); 

                    				current = grammarAccess.getLootItemTypeEnumAccess().getBOMBEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getLootItemTypeEnumAccess().getBOMBEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalMazeDsl.g:6783:3: (enumLiteral_2= 'trap' )
                    {
                    // InternalMazeDsl.g:6783:3: (enumLiteral_2= 'trap' )
                    // InternalMazeDsl.g:6784:4: enumLiteral_2= 'trap'
                    {
                    enumLiteral_2=(Token)match(input,192,FOLLOW_2); 

                    				current = grammarAccess.getLootItemTypeEnumAccess().getTRAPEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getLootItemTypeEnumAccess().getTRAPEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalMazeDsl.g:6791:3: (enumLiteral_3= 'weapon' )
                    {
                    // InternalMazeDsl.g:6791:3: (enumLiteral_3= 'weapon' )
                    // InternalMazeDsl.g:6792:4: enumLiteral_3= 'weapon'
                    {
                    enumLiteral_3=(Token)match(input,193,FOLLOW_2); 

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
    // InternalMazeDsl.g:6802:1: ruleDropWeaponEnum returns [Enumerator current=null] : (enumLiteral_0= 'gatling' ) ;
    public final Enumerator ruleDropWeaponEnum() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalMazeDsl.g:6808:2: ( (enumLiteral_0= 'gatling' ) )
            // InternalMazeDsl.g:6809:2: (enumLiteral_0= 'gatling' )
            {
            // InternalMazeDsl.g:6809:2: (enumLiteral_0= 'gatling' )
            // InternalMazeDsl.g:6810:3: enumLiteral_0= 'gatling'
            {
            enumLiteral_0=(Token)match(input,177,FOLLOW_2); 

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
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0100000000000040L,0xFFFFFFFFFFFFFFFEL,0x00023FFFFFFFFFFFL});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x100000010101E000L,0x0000000000000000L,0x0000000202000000L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x1000000101012000L,0x0000000000000000L,0x0000000202000000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x1000000100012000L,0x0000000000000000L,0x0000000202000000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x1000000000012000L,0x0000000000000000L,0x0000000202000000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000012000L,0x0000000000000000L,0x0000000202000000L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000002000L,0x0000000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x001C000000000000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x00000000007A2000L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000000782000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000000000020L,0x0000000000000000L,0x0000400000000000L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000000702000L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000000602000L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000000000402000L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x00E0000000000000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x00000247FC002000L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x00000247F8002000L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x00000247F0002000L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x00000247E0002000L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x00000247C0002000L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0000000000040000L,0x0000000000000000L,0x0001000000000000L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0000024780002000L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0000000100000000L,0x0000000000000000L,0x0700000000000000L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0000024700002000L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0000000300002000L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000000200002000L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0000003800002000L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x0000003000002000L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x0000002000002000L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x0000018800002000L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x0000018000002000L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x0000010000002000L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x00007C0800002000L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x0000780800002000L});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0x0000700800002000L});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x0000700000002000L});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x0000600000002000L});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x3800000000000000L});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x0000400000002000L});
    public static final BitSet FOLLOW_50 = new BitSet(new long[]{0x0011800000000000L});
    public static final BitSet FOLLOW_51 = new BitSet(new long[]{0x0011000000000000L});
    public static final BitSet FOLLOW_52 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_53 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_54 = new BitSet(new long[]{0x0040000000000000L});
    public static final BitSet FOLLOW_55 = new BitSet(new long[]{0x000C000000000000L});
    public static final BitSet FOLLOW_56 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_57 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_58 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_59 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_60 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_61 = new BitSet(new long[]{0x0400000000000002L});
    public static final BitSet FOLLOW_62 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_63 = new BitSet(new long[]{0x6000000000000000L});
    public static final BitSet FOLLOW_64 = new BitSet(new long[]{0x6000000000002000L});
    public static final BitSet FOLLOW_65 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0xC000000000000000L,0x0000000000000003L});
    public static final BitSet FOLLOW_66 = new BitSet(new long[]{0x8000000000000000L});
    public static final BitSet FOLLOW_67 = new BitSet(new long[]{0x0000000000002000L,0x0000000000000001L});
    public static final BitSet FOLLOW_68 = new BitSet(new long[]{0x0000000000002000L,0x03030001C018027EL,0x0000028000000008L});
    public static final BitSet FOLLOW_69 = new BitSet(new long[]{0x0000000000002000L,0x03030001C018027CL,0x0000028000000008L});
    public static final BitSet FOLLOW_70 = new BitSet(new long[]{0x0000000000002000L,0x03030001C0180278L,0x0000028000000008L});
    public static final BitSet FOLLOW_71 = new BitSet(new long[]{0x0000000000002000L,0x03030001C0180270L,0x0000028000000008L});
    public static final BitSet FOLLOW_72 = new BitSet(new long[]{0x0000000000002000L,0x03030001C0180260L,0x0000028000000008L});
    public static final BitSet FOLLOW_73 = new BitSet(new long[]{0x0000000000002000L,0x03030001C0180240L,0x0000028000000008L});
    public static final BitSet FOLLOW_74 = new BitSet(new long[]{0x0000000000002000L,0x03030001C0180000L,0x0000028000000008L});
    public static final BitSet FOLLOW_75 = new BitSet(new long[]{0x0000000000002000L,0x03030001C0000000L,0x0000028000000008L});
    public static final BitSet FOLLOW_76 = new BitSet(new long[]{0x0000000000002000L,0x0303000180000000L,0x0000028000000008L});
    public static final BitSet FOLLOW_77 = new BitSet(new long[]{0x0000000000002000L,0x0303000100000000L,0x0000008000000008L});
    public static final BitSet FOLLOW_78 = new BitSet(new long[]{0x0000000000002000L,0x0303000000000000L,0x0000008000000008L});
    public static final BitSet FOLLOW_79 = new BitSet(new long[]{0x0000000000002000L,0x0300000000000000L,0x0000008000000008L});
    public static final BitSet FOLLOW_80 = new BitSet(new long[]{0x0000000000002000L,0x0000000000000000L,0x0000008000000008L});
    public static final BitSet FOLLOW_81 = new BitSet(new long[]{0x0000000000002000L,0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_82 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000180L});
    public static final BitSet FOLLOW_83 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_84 = new BitSet(new long[]{0x0004000000000002L});
    public static final BitSet FOLLOW_85 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_86 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_87 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_88 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_89 = new BitSet(new long[]{0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_90 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_91 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_92 = new BitSet(new long[]{0x0000000000000000L,0x0000000000030000L});
    public static final BitSet FOLLOW_93 = new BitSet(new long[]{0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_94 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002000000000000L});
    public static final BitSet FOLLOW_95 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_96 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_97 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_98 = new BitSet(new long[]{0x0000000000000000L,0x0000000001800000L});
    public static final BitSet FOLLOW_99 = new BitSet(new long[]{0x0000000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_100 = new BitSet(new long[]{0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_101 = new BitSet(new long[]{0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_102 = new BitSet(new long[]{0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_103 = new BitSet(new long[]{0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_104 = new BitSet(new long[]{0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_105 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_106 = new BitSet(new long[]{0x0000000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_107 = new BitSet(new long[]{0x0000000000000000L,0x0000000400000000L});
    public static final BitSet FOLLOW_108 = new BitSet(new long[]{0x0000000000000000L,0x0000000800000000L});
    public static final BitSet FOLLOW_109 = new BitSet(new long[]{0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_110 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L});
    public static final BitSet FOLLOW_111 = new BitSet(new long[]{0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_112 = new BitSet(new long[]{0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_113 = new BitSet(new long[]{0x0000000000000000L,0x0000010000000000L});
    public static final BitSet FOLLOW_114 = new BitSet(new long[]{0x0000000000000000L,0x0000020000000000L});
    public static final BitSet FOLLOW_115 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L});
    public static final BitSet FOLLOW_116 = new BitSet(new long[]{0x0000000000002000L,0x0000F80000000000L});
    public static final BitSet FOLLOW_117 = new BitSet(new long[]{0x0000000000002000L,0x0000F00000000000L});
    public static final BitSet FOLLOW_118 = new BitSet(new long[]{0x0000000000002000L,0x0000E00000000000L});
    public static final BitSet FOLLOW_119 = new BitSet(new long[]{0x0000000000002000L,0x0000C00000000000L});
    public static final BitSet FOLLOW_120 = new BitSet(new long[]{0x0000000000002000L,0x0000800000000000L});
    public static final BitSet FOLLOW_121 = new BitSet(new long[]{0x0000000000000000L,0x0004000000000000L});
    public static final BitSet FOLLOW_122 = new BitSet(new long[]{0x0000000000000000L,0x0008000000000000L});
    public static final BitSet FOLLOW_123 = new BitSet(new long[]{0x0000000000000000L,0x0010000000000000L});
    public static final BitSet FOLLOW_124 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_125 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L});
    public static final BitSet FOLLOW_126 = new BitSet(new long[]{0x0000000000002000L,0x0080000000000000L});
    public static final BitSet FOLLOW_127 = new BitSet(new long[]{0x0000000000000000L,0x0400000000000000L});
    public static final BitSet FOLLOW_128 = new BitSet(new long[]{0x0000000000000000L,0x0800000000000000L});
    public static final BitSet FOLLOW_129 = new BitSet(new long[]{0x0000000000000000L,0x1000000000000000L});
    public static final BitSet FOLLOW_130 = new BitSet(new long[]{0x0000000000002000L,0xE000000000000000L,0x0000000000000007L});
    public static final BitSet FOLLOW_131 = new BitSet(new long[]{0x0000000000002000L,0xC000000000000000L,0x0000000000000007L});
    public static final BitSet FOLLOW_132 = new BitSet(new long[]{0x0000000000002000L,0x8000000000000000L,0x0000000000000007L});
    public static final BitSet FOLLOW_133 = new BitSet(new long[]{0x0000000000002000L,0x0000000000000000L,0x0000000000000007L});
    public static final BitSet FOLLOW_134 = new BitSet(new long[]{0x0000000000002000L,0x0000000000000000L,0x0000000000000006L});
    public static final BitSet FOLLOW_135 = new BitSet(new long[]{0x0000000000002000L,0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_136 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000030L});
    public static final BitSet FOLLOW_137 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_138 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_139 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_140 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_141 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_142 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_143 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_144 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_145 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_146 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_147 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_148 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_149 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_150 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_151 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_152 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000300000L});
    public static final BitSet FOLLOW_153 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_154 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_155 = new BitSet(new long[]{0x0000000000002000L,0x0000000000000000L,0x0000000001800000L});
    public static final BitSet FOLLOW_156 = new BitSet(new long[]{0x0000000000002000L,0x0000000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_157 = new BitSet(new long[]{0x4000000000002000L,0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_158 = new BitSet(new long[]{0x4000000000002000L});
    public static final BitSet FOLLOW_159 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_160 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_161 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_162 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x00000001C0000000L});
    public static final BitSet FOLLOW_163 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000180000000L});
    public static final BitSet FOLLOW_164 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000100000000L});
    public static final BitSet FOLLOW_165 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000400000000L});
    public static final BitSet FOLLOW_166 = new BitSet(new long[]{0x0000000000002000L,0x0000000000000000L,0x0000005800000000L});
    public static final BitSet FOLLOW_167 = new BitSet(new long[]{0x0000000000002000L,0x0000000000000000L,0x0000005000000000L});
    public static final BitSet FOLLOW_168 = new BitSet(new long[]{0x0000000000002000L,0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_169 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000002000000000L});
    public static final BitSet FOLLOW_170 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000800000000L});
    public static final BitSet FOLLOW_171 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000010000000000L});
    public static final BitSet FOLLOW_172 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000040000000000L});
    public static final BitSet FOLLOW_173 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_174 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_175 = new BitSet(new long[]{0x0000000000002000L,0x0000000000000000L,0x0000300000000000L});
    public static final BitSet FOLLOW_176 = new BitSet(new long[]{0x0000000000002000L,0x0000000000000000L,0x0000200000000000L});
    public static final BitSet FOLLOW_177 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000800000000000L});

}