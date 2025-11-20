package main.game.maze.parser.antlr.internal;

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
import main.game.maze.services.MazeCompGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalMazeCompParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_STRING", "RULE_ID", "RULE_INT", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'true'", "'false'", "'-'", "'.'", "'E'", "'e'", "'Zombie'", "'{'", "'id'", "'displayName'", "'enabled'", "'health'", "'speed'", "'ImageBase'", "'ImageTurnLeft'", "'ImageTurnRight'", "'ImageTurnUp'", "'ImageTurnDown'", "'behavior'", "'attackDamage'", "'infectionLevel'", "'resurrectionTime'", "'touchSound'", "'zombieLootTable'", "'}'", "'Ghost'", "'visibilityLevel'", "'nonTangibilityEnergy'", "'PumpkinBomber'", "'attackRange'", "'attackCooldownMs'", "'projectileSpeed'", "'projectileType'", "'splashRadius'", "'arcHeight'", "'projectileImage'", "'explosionImage'", "'explosionSound'", "'throwSound'", "'LootTable'", "'weightCapacity'", "','", "'items'", "'LootItem'", "'type'", "'value'", "'weight'", "'graphicBase'", "'PASSIVE'", "'WANDER'", "'AGGRESSIVE'", "'FOOD'", "'BOMB'", "'TRAP'", "'WEAPON'", "'STRAIGHT'", "'LOB'", "'BEAM'"
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
    public static final int RULE_ID=5;
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
    public static final int T__62=62;
    public static final int T__63=63;
    public static final int T__20=20;
    public static final int T__64=64;
    public static final int T__21=21;
    public static final int T__65=65;
    public static final int RULE_STRING=4;
    public static final int RULE_SL_COMMENT=8;
    public static final int T__37=37;
    public static final int T__38=38;
    public static final int T__39=39;
    public static final int T__33=33;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int EOF=-1;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int T__32=32;
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


        public InternalMazeCompParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalMazeCompParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalMazeCompParser.tokenNames; }
    public String getGrammarFileName() { return "InternalMazeComp.g"; }



     	private MazeCompGrammarAccess grammarAccess;

        public InternalMazeCompParser(TokenStream input, MazeCompGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }

        @Override
        protected String getFirstRuleName() {
        	return "MazeFile";
       	}

       	@Override
       	protected MazeCompGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}




    // $ANTLR start "entryRuleMazeFile"
    // InternalMazeComp.g:65:1: entryRuleMazeFile returns [EObject current=null] : iv_ruleMazeFile= ruleMazeFile EOF ;
    public final EObject entryRuleMazeFile() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMazeFile = null;


        try {
            // InternalMazeComp.g:65:49: (iv_ruleMazeFile= ruleMazeFile EOF )
            // InternalMazeComp.g:66:2: iv_ruleMazeFile= ruleMazeFile EOF
            {
             newCompositeNode(grammarAccess.getMazeFileRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleMazeFile=ruleMazeFile();

            state._fsp--;

             current =iv_ruleMazeFile; 
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
    // $ANTLR end "entryRuleMazeFile"


    // $ANTLR start "ruleMazeFile"
    // InternalMazeComp.g:72:1: ruleMazeFile returns [EObject current=null] : ( ( (lv_zombies_0_0= ruleZombie ) )* ( (lv_ghosts_1_0= ruleGhost ) )* ( (lv_pumpkinBombers_2_0= rulePumpkinBomber ) )* ( (lv_lootTables_3_0= ruleLootTable ) )* ( (lv_lootItems_4_0= ruleLootItem ) )* ) ;
    public final EObject ruleMazeFile() throws RecognitionException {
        EObject current = null;

        EObject lv_zombies_0_0 = null;

        EObject lv_ghosts_1_0 = null;

        EObject lv_pumpkinBombers_2_0 = null;

        EObject lv_lootTables_3_0 = null;

        EObject lv_lootItems_4_0 = null;



        	enterRule();

        try {
            // InternalMazeComp.g:78:2: ( ( ( (lv_zombies_0_0= ruleZombie ) )* ( (lv_ghosts_1_0= ruleGhost ) )* ( (lv_pumpkinBombers_2_0= rulePumpkinBomber ) )* ( (lv_lootTables_3_0= ruleLootTable ) )* ( (lv_lootItems_4_0= ruleLootItem ) )* ) )
            // InternalMazeComp.g:79:2: ( ( (lv_zombies_0_0= ruleZombie ) )* ( (lv_ghosts_1_0= ruleGhost ) )* ( (lv_pumpkinBombers_2_0= rulePumpkinBomber ) )* ( (lv_lootTables_3_0= ruleLootTable ) )* ( (lv_lootItems_4_0= ruleLootItem ) )* )
            {
            // InternalMazeComp.g:79:2: ( ( (lv_zombies_0_0= ruleZombie ) )* ( (lv_ghosts_1_0= ruleGhost ) )* ( (lv_pumpkinBombers_2_0= rulePumpkinBomber ) )* ( (lv_lootTables_3_0= ruleLootTable ) )* ( (lv_lootItems_4_0= ruleLootItem ) )* )
            // InternalMazeComp.g:80:3: ( (lv_zombies_0_0= ruleZombie ) )* ( (lv_ghosts_1_0= ruleGhost ) )* ( (lv_pumpkinBombers_2_0= rulePumpkinBomber ) )* ( (lv_lootTables_3_0= ruleLootTable ) )* ( (lv_lootItems_4_0= ruleLootItem ) )*
            {
            // InternalMazeComp.g:80:3: ( (lv_zombies_0_0= ruleZombie ) )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==17) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalMazeComp.g:81:4: (lv_zombies_0_0= ruleZombie )
            	    {
            	    // InternalMazeComp.g:81:4: (lv_zombies_0_0= ruleZombie )
            	    // InternalMazeComp.g:82:5: lv_zombies_0_0= ruleZombie
            	    {

            	    					newCompositeNode(grammarAccess.getMazeFileAccess().getZombiesZombieParserRuleCall_0_0());
            	    				
            	    pushFollow(FOLLOW_3);
            	    lv_zombies_0_0=ruleZombie();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getMazeFileRule());
            	    					}
            	    					add(
            	    						current,
            	    						"zombies",
            	    						lv_zombies_0_0,
            	    						"main.game.maze.MazeComp.Zombie");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            // InternalMazeComp.g:99:3: ( (lv_ghosts_1_0= ruleGhost ) )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==36) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // InternalMazeComp.g:100:4: (lv_ghosts_1_0= ruleGhost )
            	    {
            	    // InternalMazeComp.g:100:4: (lv_ghosts_1_0= ruleGhost )
            	    // InternalMazeComp.g:101:5: lv_ghosts_1_0= ruleGhost
            	    {

            	    					newCompositeNode(grammarAccess.getMazeFileAccess().getGhostsGhostParserRuleCall_1_0());
            	    				
            	    pushFollow(FOLLOW_4);
            	    lv_ghosts_1_0=ruleGhost();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getMazeFileRule());
            	    					}
            	    					add(
            	    						current,
            	    						"ghosts",
            	    						lv_ghosts_1_0,
            	    						"main.game.maze.MazeComp.Ghost");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            // InternalMazeComp.g:118:3: ( (lv_pumpkinBombers_2_0= rulePumpkinBomber ) )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==39) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // InternalMazeComp.g:119:4: (lv_pumpkinBombers_2_0= rulePumpkinBomber )
            	    {
            	    // InternalMazeComp.g:119:4: (lv_pumpkinBombers_2_0= rulePumpkinBomber )
            	    // InternalMazeComp.g:120:5: lv_pumpkinBombers_2_0= rulePumpkinBomber
            	    {

            	    					newCompositeNode(grammarAccess.getMazeFileAccess().getPumpkinBombersPumpkinBomberParserRuleCall_2_0());
            	    				
            	    pushFollow(FOLLOW_5);
            	    lv_pumpkinBombers_2_0=rulePumpkinBomber();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getMazeFileRule());
            	    					}
            	    					add(
            	    						current,
            	    						"pumpkinBombers",
            	    						lv_pumpkinBombers_2_0,
            	    						"main.game.maze.MazeComp.PumpkinBomber");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);

            // InternalMazeComp.g:137:3: ( (lv_lootTables_3_0= ruleLootTable ) )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==50) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // InternalMazeComp.g:138:4: (lv_lootTables_3_0= ruleLootTable )
            	    {
            	    // InternalMazeComp.g:138:4: (lv_lootTables_3_0= ruleLootTable )
            	    // InternalMazeComp.g:139:5: lv_lootTables_3_0= ruleLootTable
            	    {

            	    					newCompositeNode(grammarAccess.getMazeFileAccess().getLootTablesLootTableParserRuleCall_3_0());
            	    				
            	    pushFollow(FOLLOW_6);
            	    lv_lootTables_3_0=ruleLootTable();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getMazeFileRule());
            	    					}
            	    					add(
            	    						current,
            	    						"lootTables",
            	    						lv_lootTables_3_0,
            	    						"main.game.maze.MazeComp.LootTable");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);

            // InternalMazeComp.g:156:3: ( (lv_lootItems_4_0= ruleLootItem ) )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==54) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // InternalMazeComp.g:157:4: (lv_lootItems_4_0= ruleLootItem )
            	    {
            	    // InternalMazeComp.g:157:4: (lv_lootItems_4_0= ruleLootItem )
            	    // InternalMazeComp.g:158:5: lv_lootItems_4_0= ruleLootItem
            	    {

            	    					newCompositeNode(grammarAccess.getMazeFileAccess().getLootItemsLootItemParserRuleCall_4_0());
            	    				
            	    pushFollow(FOLLOW_7);
            	    lv_lootItems_4_0=ruleLootItem();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getMazeFileRule());
            	    					}
            	    					add(
            	    						current,
            	    						"lootItems",
            	    						lv_lootItems_4_0,
            	    						"main.game.maze.MazeComp.LootItem");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop5;
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
    // $ANTLR end "ruleMazeFile"


    // $ANTLR start "entryRuleEString"
    // InternalMazeComp.g:179:1: entryRuleEString returns [String current=null] : iv_ruleEString= ruleEString EOF ;
    public final String entryRuleEString() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleEString = null;


        try {
            // InternalMazeComp.g:179:47: (iv_ruleEString= ruleEString EOF )
            // InternalMazeComp.g:180:2: iv_ruleEString= ruleEString EOF
            {
             newCompositeNode(grammarAccess.getEStringRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleEString=ruleEString();

            state._fsp--;

             current =iv_ruleEString.getText(); 
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
    // $ANTLR end "entryRuleEString"


    // $ANTLR start "ruleEString"
    // InternalMazeComp.g:186:1: ruleEString returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_STRING_0= RULE_STRING | this_ID_1= RULE_ID ) ;
    public final AntlrDatatypeRuleToken ruleEString() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_STRING_0=null;
        Token this_ID_1=null;


        	enterRule();

        try {
            // InternalMazeComp.g:192:2: ( (this_STRING_0= RULE_STRING | this_ID_1= RULE_ID ) )
            // InternalMazeComp.g:193:2: (this_STRING_0= RULE_STRING | this_ID_1= RULE_ID )
            {
            // InternalMazeComp.g:193:2: (this_STRING_0= RULE_STRING | this_ID_1= RULE_ID )
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==RULE_STRING) ) {
                alt6=1;
            }
            else if ( (LA6_0==RULE_ID) ) {
                alt6=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }
            switch (alt6) {
                case 1 :
                    // InternalMazeComp.g:194:3: this_STRING_0= RULE_STRING
                    {
                    this_STRING_0=(Token)match(input,RULE_STRING,FOLLOW_2); 

                    			current.merge(this_STRING_0);
                    		

                    			newLeafNode(this_STRING_0, grammarAccess.getEStringAccess().getSTRINGTerminalRuleCall_0());
                    		

                    }
                    break;
                case 2 :
                    // InternalMazeComp.g:202:3: this_ID_1= RULE_ID
                    {
                    this_ID_1=(Token)match(input,RULE_ID,FOLLOW_2); 

                    			current.merge(this_ID_1);
                    		

                    			newLeafNode(this_ID_1, grammarAccess.getEStringAccess().getIDTerminalRuleCall_1());
                    		

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
    // $ANTLR end "ruleEString"


    // $ANTLR start "entryRuleEBoolean"
    // InternalMazeComp.g:213:1: entryRuleEBoolean returns [String current=null] : iv_ruleEBoolean= ruleEBoolean EOF ;
    public final String entryRuleEBoolean() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleEBoolean = null;


        try {
            // InternalMazeComp.g:213:48: (iv_ruleEBoolean= ruleEBoolean EOF )
            // InternalMazeComp.g:214:2: iv_ruleEBoolean= ruleEBoolean EOF
            {
             newCompositeNode(grammarAccess.getEBooleanRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleEBoolean=ruleEBoolean();

            state._fsp--;

             current =iv_ruleEBoolean.getText(); 
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
    // $ANTLR end "entryRuleEBoolean"


    // $ANTLR start "ruleEBoolean"
    // InternalMazeComp.g:220:1: ruleEBoolean returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'true' | kw= 'false' ) ;
    public final AntlrDatatypeRuleToken ruleEBoolean() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalMazeComp.g:226:2: ( (kw= 'true' | kw= 'false' ) )
            // InternalMazeComp.g:227:2: (kw= 'true' | kw= 'false' )
            {
            // InternalMazeComp.g:227:2: (kw= 'true' | kw= 'false' )
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==11) ) {
                alt7=1;
            }
            else if ( (LA7_0==12) ) {
                alt7=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }
            switch (alt7) {
                case 1 :
                    // InternalMazeComp.g:228:3: kw= 'true'
                    {
                    kw=(Token)match(input,11,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getEBooleanAccess().getTrueKeyword_0());
                    		

                    }
                    break;
                case 2 :
                    // InternalMazeComp.g:234:3: kw= 'false'
                    {
                    kw=(Token)match(input,12,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getEBooleanAccess().getFalseKeyword_1());
                    		

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
    // $ANTLR end "ruleEBoolean"


    // $ANTLR start "entryRuleEInt"
    // InternalMazeComp.g:243:1: entryRuleEInt returns [String current=null] : iv_ruleEInt= ruleEInt EOF ;
    public final String entryRuleEInt() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleEInt = null;


        try {
            // InternalMazeComp.g:243:44: (iv_ruleEInt= ruleEInt EOF )
            // InternalMazeComp.g:244:2: iv_ruleEInt= ruleEInt EOF
            {
             newCompositeNode(grammarAccess.getEIntRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleEInt=ruleEInt();

            state._fsp--;

             current =iv_ruleEInt.getText(); 
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
    // $ANTLR end "entryRuleEInt"


    // $ANTLR start "ruleEInt"
    // InternalMazeComp.g:250:1: ruleEInt returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= '-' )? this_INT_1= RULE_INT ) ;
    public final AntlrDatatypeRuleToken ruleEInt() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_INT_1=null;


        	enterRule();

        try {
            // InternalMazeComp.g:256:2: ( ( (kw= '-' )? this_INT_1= RULE_INT ) )
            // InternalMazeComp.g:257:2: ( (kw= '-' )? this_INT_1= RULE_INT )
            {
            // InternalMazeComp.g:257:2: ( (kw= '-' )? this_INT_1= RULE_INT )
            // InternalMazeComp.g:258:3: (kw= '-' )? this_INT_1= RULE_INT
            {
            // InternalMazeComp.g:258:3: (kw= '-' )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==13) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // InternalMazeComp.g:259:4: kw= '-'
                    {
                    kw=(Token)match(input,13,FOLLOW_8); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getEIntAccess().getHyphenMinusKeyword_0());
                    			

                    }
                    break;

            }

            this_INT_1=(Token)match(input,RULE_INT,FOLLOW_2); 

            			current.merge(this_INT_1);
            		

            			newLeafNode(this_INT_1, grammarAccess.getEIntAccess().getINTTerminalRuleCall_1());
            		

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
    // $ANTLR end "ruleEInt"


    // $ANTLR start "entryRuleEDouble"
    // InternalMazeComp.g:276:1: entryRuleEDouble returns [String current=null] : iv_ruleEDouble= ruleEDouble EOF ;
    public final String entryRuleEDouble() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleEDouble = null;


        try {
            // InternalMazeComp.g:276:47: (iv_ruleEDouble= ruleEDouble EOF )
            // InternalMazeComp.g:277:2: iv_ruleEDouble= ruleEDouble EOF
            {
             newCompositeNode(grammarAccess.getEDoubleRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleEDouble=ruleEDouble();

            state._fsp--;

             current =iv_ruleEDouble.getText(); 
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
    // $ANTLR end "entryRuleEDouble"


    // $ANTLR start "ruleEDouble"
    // InternalMazeComp.g:283:1: ruleEDouble returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= '-' )? (this_INT_1= RULE_INT )? kw= '.' this_INT_3= RULE_INT ( (kw= 'E' | kw= 'e' ) (kw= '-' )? this_INT_7= RULE_INT )? ) ;
    public final AntlrDatatypeRuleToken ruleEDouble() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_INT_1=null;
        Token this_INT_3=null;
        Token this_INT_7=null;


        	enterRule();

        try {
            // InternalMazeComp.g:289:2: ( ( (kw= '-' )? (this_INT_1= RULE_INT )? kw= '.' this_INT_3= RULE_INT ( (kw= 'E' | kw= 'e' ) (kw= '-' )? this_INT_7= RULE_INT )? ) )
            // InternalMazeComp.g:290:2: ( (kw= '-' )? (this_INT_1= RULE_INT )? kw= '.' this_INT_3= RULE_INT ( (kw= 'E' | kw= 'e' ) (kw= '-' )? this_INT_7= RULE_INT )? )
            {
            // InternalMazeComp.g:290:2: ( (kw= '-' )? (this_INT_1= RULE_INT )? kw= '.' this_INT_3= RULE_INT ( (kw= 'E' | kw= 'e' ) (kw= '-' )? this_INT_7= RULE_INT )? )
            // InternalMazeComp.g:291:3: (kw= '-' )? (this_INT_1= RULE_INT )? kw= '.' this_INT_3= RULE_INT ( (kw= 'E' | kw= 'e' ) (kw= '-' )? this_INT_7= RULE_INT )?
            {
            // InternalMazeComp.g:291:3: (kw= '-' )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==13) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // InternalMazeComp.g:292:4: kw= '-'
                    {
                    kw=(Token)match(input,13,FOLLOW_9); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getEDoubleAccess().getHyphenMinusKeyword_0());
                    			

                    }
                    break;

            }

            // InternalMazeComp.g:298:3: (this_INT_1= RULE_INT )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==RULE_INT) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // InternalMazeComp.g:299:4: this_INT_1= RULE_INT
                    {
                    this_INT_1=(Token)match(input,RULE_INT,FOLLOW_10); 

                    				current.merge(this_INT_1);
                    			

                    				newLeafNode(this_INT_1, grammarAccess.getEDoubleAccess().getINTTerminalRuleCall_1());
                    			

                    }
                    break;

            }

            kw=(Token)match(input,14,FOLLOW_8); 

            			current.merge(kw);
            			newLeafNode(kw, grammarAccess.getEDoubleAccess().getFullStopKeyword_2());
            		
            this_INT_3=(Token)match(input,RULE_INT,FOLLOW_11); 

            			current.merge(this_INT_3);
            		

            			newLeafNode(this_INT_3, grammarAccess.getEDoubleAccess().getINTTerminalRuleCall_3());
            		
            // InternalMazeComp.g:319:3: ( (kw= 'E' | kw= 'e' ) (kw= '-' )? this_INT_7= RULE_INT )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( ((LA13_0>=15 && LA13_0<=16)) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // InternalMazeComp.g:320:4: (kw= 'E' | kw= 'e' ) (kw= '-' )? this_INT_7= RULE_INT
                    {
                    // InternalMazeComp.g:320:4: (kw= 'E' | kw= 'e' )
                    int alt11=2;
                    int LA11_0 = input.LA(1);

                    if ( (LA11_0==15) ) {
                        alt11=1;
                    }
                    else if ( (LA11_0==16) ) {
                        alt11=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 11, 0, input);

                        throw nvae;
                    }
                    switch (alt11) {
                        case 1 :
                            // InternalMazeComp.g:321:5: kw= 'E'
                            {
                            kw=(Token)match(input,15,FOLLOW_12); 

                            					current.merge(kw);
                            					newLeafNode(kw, grammarAccess.getEDoubleAccess().getEKeyword_4_0_0());
                            				

                            }
                            break;
                        case 2 :
                            // InternalMazeComp.g:327:5: kw= 'e'
                            {
                            kw=(Token)match(input,16,FOLLOW_12); 

                            					current.merge(kw);
                            					newLeafNode(kw, grammarAccess.getEDoubleAccess().getEKeyword_4_0_1());
                            				

                            }
                            break;

                    }

                    // InternalMazeComp.g:333:4: (kw= '-' )?
                    int alt12=2;
                    int LA12_0 = input.LA(1);

                    if ( (LA12_0==13) ) {
                        alt12=1;
                    }
                    switch (alt12) {
                        case 1 :
                            // InternalMazeComp.g:334:5: kw= '-'
                            {
                            kw=(Token)match(input,13,FOLLOW_8); 

                            					current.merge(kw);
                            					newLeafNode(kw, grammarAccess.getEDoubleAccess().getHyphenMinusKeyword_4_1());
                            				

                            }
                            break;

                    }

                    this_INT_7=(Token)match(input,RULE_INT,FOLLOW_2); 

                    				current.merge(this_INT_7);
                    			

                    				newLeafNode(this_INT_7, grammarAccess.getEDoubleAccess().getINTTerminalRuleCall_4_2());
                    			

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
    // $ANTLR end "ruleEDouble"


    // $ANTLR start "entryRuleZombie"
    // InternalMazeComp.g:352:1: entryRuleZombie returns [EObject current=null] : iv_ruleZombie= ruleZombie EOF ;
    public final EObject entryRuleZombie() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleZombie = null;


        try {
            // InternalMazeComp.g:352:47: (iv_ruleZombie= ruleZombie EOF )
            // InternalMazeComp.g:353:2: iv_ruleZombie= ruleZombie EOF
            {
             newCompositeNode(grammarAccess.getZombieRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleZombie=ruleZombie();

            state._fsp--;

             current =iv_ruleZombie; 
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
    // $ANTLR end "entryRuleZombie"


    // $ANTLR start "ruleZombie"
    // InternalMazeComp.g:359:1: ruleZombie returns [EObject current=null] : (otherlv_0= 'Zombie' otherlv_1= '{' (otherlv_2= 'id' ( (lv_id_3_0= ruleEString ) ) )? (otherlv_4= 'displayName' ( (lv_displayName_5_0= ruleEString ) ) )? otherlv_6= 'enabled' ( (lv_enabled_7_0= ruleEBoolean ) ) otherlv_8= 'health' ( (lv_health_9_0= ruleEInt ) ) otherlv_10= 'speed' ( (lv_speed_11_0= ruleEDouble ) ) (otherlv_12= 'ImageBase' ( (lv_ImageBase_13_0= ruleEString ) ) )? (otherlv_14= 'ImageTurnLeft' ( (lv_ImageTurnLeft_15_0= ruleEString ) ) )? (otherlv_16= 'ImageTurnRight' ( (lv_ImageTurnRight_17_0= ruleEString ) ) )? (otherlv_18= 'ImageTurnUp' ( (lv_ImageTurnUp_19_0= ruleEString ) ) )? (otherlv_20= 'ImageTurnDown' ( (lv_ImageTurnDown_21_0= ruleEString ) ) )? (otherlv_22= 'behavior' ( (lv_behavior_23_0= ruleBehaviorType ) ) )? otherlv_24= 'attackDamage' ( (lv_attackDamage_25_0= ruleEInt ) ) otherlv_26= 'infectionLevel' ( (lv_infectionLevel_27_0= ruleEInt ) ) otherlv_28= 'resurrectionTime' ( (lv_resurrectionTime_29_0= ruleEInt ) ) (otherlv_30= 'touchSound' ( (lv_touchSound_31_0= ruleEString ) ) )? (otherlv_32= 'zombieLootTable' ( ( ruleEString ) ) )? otherlv_34= '}' ) ;
    public final EObject ruleZombie() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
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
        Token otherlv_26=null;
        Token otherlv_28=null;
        Token otherlv_30=null;
        Token otherlv_32=null;
        Token otherlv_34=null;
        AntlrDatatypeRuleToken lv_id_3_0 = null;

        AntlrDatatypeRuleToken lv_displayName_5_0 = null;

        AntlrDatatypeRuleToken lv_enabled_7_0 = null;

        AntlrDatatypeRuleToken lv_health_9_0 = null;

        AntlrDatatypeRuleToken lv_speed_11_0 = null;

        AntlrDatatypeRuleToken lv_ImageBase_13_0 = null;

        AntlrDatatypeRuleToken lv_ImageTurnLeft_15_0 = null;

        AntlrDatatypeRuleToken lv_ImageTurnRight_17_0 = null;

        AntlrDatatypeRuleToken lv_ImageTurnUp_19_0 = null;

        AntlrDatatypeRuleToken lv_ImageTurnDown_21_0 = null;

        Enumerator lv_behavior_23_0 = null;

        AntlrDatatypeRuleToken lv_attackDamage_25_0 = null;

        AntlrDatatypeRuleToken lv_infectionLevel_27_0 = null;

        AntlrDatatypeRuleToken lv_resurrectionTime_29_0 = null;

        AntlrDatatypeRuleToken lv_touchSound_31_0 = null;



        	enterRule();

        try {
            // InternalMazeComp.g:365:2: ( (otherlv_0= 'Zombie' otherlv_1= '{' (otherlv_2= 'id' ( (lv_id_3_0= ruleEString ) ) )? (otherlv_4= 'displayName' ( (lv_displayName_5_0= ruleEString ) ) )? otherlv_6= 'enabled' ( (lv_enabled_7_0= ruleEBoolean ) ) otherlv_8= 'health' ( (lv_health_9_0= ruleEInt ) ) otherlv_10= 'speed' ( (lv_speed_11_0= ruleEDouble ) ) (otherlv_12= 'ImageBase' ( (lv_ImageBase_13_0= ruleEString ) ) )? (otherlv_14= 'ImageTurnLeft' ( (lv_ImageTurnLeft_15_0= ruleEString ) ) )? (otherlv_16= 'ImageTurnRight' ( (lv_ImageTurnRight_17_0= ruleEString ) ) )? (otherlv_18= 'ImageTurnUp' ( (lv_ImageTurnUp_19_0= ruleEString ) ) )? (otherlv_20= 'ImageTurnDown' ( (lv_ImageTurnDown_21_0= ruleEString ) ) )? (otherlv_22= 'behavior' ( (lv_behavior_23_0= ruleBehaviorType ) ) )? otherlv_24= 'attackDamage' ( (lv_attackDamage_25_0= ruleEInt ) ) otherlv_26= 'infectionLevel' ( (lv_infectionLevel_27_0= ruleEInt ) ) otherlv_28= 'resurrectionTime' ( (lv_resurrectionTime_29_0= ruleEInt ) ) (otherlv_30= 'touchSound' ( (lv_touchSound_31_0= ruleEString ) ) )? (otherlv_32= 'zombieLootTable' ( ( ruleEString ) ) )? otherlv_34= '}' ) )
            // InternalMazeComp.g:366:2: (otherlv_0= 'Zombie' otherlv_1= '{' (otherlv_2= 'id' ( (lv_id_3_0= ruleEString ) ) )? (otherlv_4= 'displayName' ( (lv_displayName_5_0= ruleEString ) ) )? otherlv_6= 'enabled' ( (lv_enabled_7_0= ruleEBoolean ) ) otherlv_8= 'health' ( (lv_health_9_0= ruleEInt ) ) otherlv_10= 'speed' ( (lv_speed_11_0= ruleEDouble ) ) (otherlv_12= 'ImageBase' ( (lv_ImageBase_13_0= ruleEString ) ) )? (otherlv_14= 'ImageTurnLeft' ( (lv_ImageTurnLeft_15_0= ruleEString ) ) )? (otherlv_16= 'ImageTurnRight' ( (lv_ImageTurnRight_17_0= ruleEString ) ) )? (otherlv_18= 'ImageTurnUp' ( (lv_ImageTurnUp_19_0= ruleEString ) ) )? (otherlv_20= 'ImageTurnDown' ( (lv_ImageTurnDown_21_0= ruleEString ) ) )? (otherlv_22= 'behavior' ( (lv_behavior_23_0= ruleBehaviorType ) ) )? otherlv_24= 'attackDamage' ( (lv_attackDamage_25_0= ruleEInt ) ) otherlv_26= 'infectionLevel' ( (lv_infectionLevel_27_0= ruleEInt ) ) otherlv_28= 'resurrectionTime' ( (lv_resurrectionTime_29_0= ruleEInt ) ) (otherlv_30= 'touchSound' ( (lv_touchSound_31_0= ruleEString ) ) )? (otherlv_32= 'zombieLootTable' ( ( ruleEString ) ) )? otherlv_34= '}' )
            {
            // InternalMazeComp.g:366:2: (otherlv_0= 'Zombie' otherlv_1= '{' (otherlv_2= 'id' ( (lv_id_3_0= ruleEString ) ) )? (otherlv_4= 'displayName' ( (lv_displayName_5_0= ruleEString ) ) )? otherlv_6= 'enabled' ( (lv_enabled_7_0= ruleEBoolean ) ) otherlv_8= 'health' ( (lv_health_9_0= ruleEInt ) ) otherlv_10= 'speed' ( (lv_speed_11_0= ruleEDouble ) ) (otherlv_12= 'ImageBase' ( (lv_ImageBase_13_0= ruleEString ) ) )? (otherlv_14= 'ImageTurnLeft' ( (lv_ImageTurnLeft_15_0= ruleEString ) ) )? (otherlv_16= 'ImageTurnRight' ( (lv_ImageTurnRight_17_0= ruleEString ) ) )? (otherlv_18= 'ImageTurnUp' ( (lv_ImageTurnUp_19_0= ruleEString ) ) )? (otherlv_20= 'ImageTurnDown' ( (lv_ImageTurnDown_21_0= ruleEString ) ) )? (otherlv_22= 'behavior' ( (lv_behavior_23_0= ruleBehaviorType ) ) )? otherlv_24= 'attackDamage' ( (lv_attackDamage_25_0= ruleEInt ) ) otherlv_26= 'infectionLevel' ( (lv_infectionLevel_27_0= ruleEInt ) ) otherlv_28= 'resurrectionTime' ( (lv_resurrectionTime_29_0= ruleEInt ) ) (otherlv_30= 'touchSound' ( (lv_touchSound_31_0= ruleEString ) ) )? (otherlv_32= 'zombieLootTable' ( ( ruleEString ) ) )? otherlv_34= '}' )
            // InternalMazeComp.g:367:3: otherlv_0= 'Zombie' otherlv_1= '{' (otherlv_2= 'id' ( (lv_id_3_0= ruleEString ) ) )? (otherlv_4= 'displayName' ( (lv_displayName_5_0= ruleEString ) ) )? otherlv_6= 'enabled' ( (lv_enabled_7_0= ruleEBoolean ) ) otherlv_8= 'health' ( (lv_health_9_0= ruleEInt ) ) otherlv_10= 'speed' ( (lv_speed_11_0= ruleEDouble ) ) (otherlv_12= 'ImageBase' ( (lv_ImageBase_13_0= ruleEString ) ) )? (otherlv_14= 'ImageTurnLeft' ( (lv_ImageTurnLeft_15_0= ruleEString ) ) )? (otherlv_16= 'ImageTurnRight' ( (lv_ImageTurnRight_17_0= ruleEString ) ) )? (otherlv_18= 'ImageTurnUp' ( (lv_ImageTurnUp_19_0= ruleEString ) ) )? (otherlv_20= 'ImageTurnDown' ( (lv_ImageTurnDown_21_0= ruleEString ) ) )? (otherlv_22= 'behavior' ( (lv_behavior_23_0= ruleBehaviorType ) ) )? otherlv_24= 'attackDamage' ( (lv_attackDamage_25_0= ruleEInt ) ) otherlv_26= 'infectionLevel' ( (lv_infectionLevel_27_0= ruleEInt ) ) otherlv_28= 'resurrectionTime' ( (lv_resurrectionTime_29_0= ruleEInt ) ) (otherlv_30= 'touchSound' ( (lv_touchSound_31_0= ruleEString ) ) )? (otherlv_32= 'zombieLootTable' ( ( ruleEString ) ) )? otherlv_34= '}'
            {
            otherlv_0=(Token)match(input,17,FOLLOW_13); 

            			newLeafNode(otherlv_0, grammarAccess.getZombieAccess().getZombieKeyword_0());
            		
            otherlv_1=(Token)match(input,18,FOLLOW_14); 

            			newLeafNode(otherlv_1, grammarAccess.getZombieAccess().getLeftCurlyBracketKeyword_1());
            		
            // InternalMazeComp.g:375:3: (otherlv_2= 'id' ( (lv_id_3_0= ruleEString ) ) )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==19) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // InternalMazeComp.g:376:4: otherlv_2= 'id' ( (lv_id_3_0= ruleEString ) )
                    {
                    otherlv_2=(Token)match(input,19,FOLLOW_15); 

                    				newLeafNode(otherlv_2, grammarAccess.getZombieAccess().getIdKeyword_2_0());
                    			
                    // InternalMazeComp.g:380:4: ( (lv_id_3_0= ruleEString ) )
                    // InternalMazeComp.g:381:5: (lv_id_3_0= ruleEString )
                    {
                    // InternalMazeComp.g:381:5: (lv_id_3_0= ruleEString )
                    // InternalMazeComp.g:382:6: lv_id_3_0= ruleEString
                    {

                    						newCompositeNode(grammarAccess.getZombieAccess().getIdEStringParserRuleCall_2_1_0());
                    					
                    pushFollow(FOLLOW_16);
                    lv_id_3_0=ruleEString();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getZombieRule());
                    						}
                    						set(
                    							current,
                    							"id",
                    							lv_id_3_0,
                    							"main.game.maze.MazeComp.EString");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeComp.g:400:3: (otherlv_4= 'displayName' ( (lv_displayName_5_0= ruleEString ) ) )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==20) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // InternalMazeComp.g:401:4: otherlv_4= 'displayName' ( (lv_displayName_5_0= ruleEString ) )
                    {
                    otherlv_4=(Token)match(input,20,FOLLOW_15); 

                    				newLeafNode(otherlv_4, grammarAccess.getZombieAccess().getDisplayNameKeyword_3_0());
                    			
                    // InternalMazeComp.g:405:4: ( (lv_displayName_5_0= ruleEString ) )
                    // InternalMazeComp.g:406:5: (lv_displayName_5_0= ruleEString )
                    {
                    // InternalMazeComp.g:406:5: (lv_displayName_5_0= ruleEString )
                    // InternalMazeComp.g:407:6: lv_displayName_5_0= ruleEString
                    {

                    						newCompositeNode(grammarAccess.getZombieAccess().getDisplayNameEStringParserRuleCall_3_1_0());
                    					
                    pushFollow(FOLLOW_17);
                    lv_displayName_5_0=ruleEString();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getZombieRule());
                    						}
                    						set(
                    							current,
                    							"displayName",
                    							lv_displayName_5_0,
                    							"main.game.maze.MazeComp.EString");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_6=(Token)match(input,21,FOLLOW_18); 

            			newLeafNode(otherlv_6, grammarAccess.getZombieAccess().getEnabledKeyword_4());
            		
            // InternalMazeComp.g:429:3: ( (lv_enabled_7_0= ruleEBoolean ) )
            // InternalMazeComp.g:430:4: (lv_enabled_7_0= ruleEBoolean )
            {
            // InternalMazeComp.g:430:4: (lv_enabled_7_0= ruleEBoolean )
            // InternalMazeComp.g:431:5: lv_enabled_7_0= ruleEBoolean
            {

            					newCompositeNode(grammarAccess.getZombieAccess().getEnabledEBooleanParserRuleCall_5_0());
            				
            pushFollow(FOLLOW_19);
            lv_enabled_7_0=ruleEBoolean();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getZombieRule());
            					}
            					set(
            						current,
            						"enabled",
            						lv_enabled_7_0,
            						"main.game.maze.MazeComp.EBoolean");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_8=(Token)match(input,22,FOLLOW_12); 

            			newLeafNode(otherlv_8, grammarAccess.getZombieAccess().getHealthKeyword_6());
            		
            // InternalMazeComp.g:452:3: ( (lv_health_9_0= ruleEInt ) )
            // InternalMazeComp.g:453:4: (lv_health_9_0= ruleEInt )
            {
            // InternalMazeComp.g:453:4: (lv_health_9_0= ruleEInt )
            // InternalMazeComp.g:454:5: lv_health_9_0= ruleEInt
            {

            					newCompositeNode(grammarAccess.getZombieAccess().getHealthEIntParserRuleCall_7_0());
            				
            pushFollow(FOLLOW_20);
            lv_health_9_0=ruleEInt();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getZombieRule());
            					}
            					set(
            						current,
            						"health",
            						lv_health_9_0,
            						"main.game.maze.MazeComp.EInt");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_10=(Token)match(input,23,FOLLOW_21); 

            			newLeafNode(otherlv_10, grammarAccess.getZombieAccess().getSpeedKeyword_8());
            		
            // InternalMazeComp.g:475:3: ( (lv_speed_11_0= ruleEDouble ) )
            // InternalMazeComp.g:476:4: (lv_speed_11_0= ruleEDouble )
            {
            // InternalMazeComp.g:476:4: (lv_speed_11_0= ruleEDouble )
            // InternalMazeComp.g:477:5: lv_speed_11_0= ruleEDouble
            {

            					newCompositeNode(grammarAccess.getZombieAccess().getSpeedEDoubleParserRuleCall_9_0());
            				
            pushFollow(FOLLOW_22);
            lv_speed_11_0=ruleEDouble();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getZombieRule());
            					}
            					set(
            						current,
            						"speed",
            						lv_speed_11_0,
            						"main.game.maze.MazeComp.EDouble");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalMazeComp.g:494:3: (otherlv_12= 'ImageBase' ( (lv_ImageBase_13_0= ruleEString ) ) )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==24) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // InternalMazeComp.g:495:4: otherlv_12= 'ImageBase' ( (lv_ImageBase_13_0= ruleEString ) )
                    {
                    otherlv_12=(Token)match(input,24,FOLLOW_15); 

                    				newLeafNode(otherlv_12, grammarAccess.getZombieAccess().getImageBaseKeyword_10_0());
                    			
                    // InternalMazeComp.g:499:4: ( (lv_ImageBase_13_0= ruleEString ) )
                    // InternalMazeComp.g:500:5: (lv_ImageBase_13_0= ruleEString )
                    {
                    // InternalMazeComp.g:500:5: (lv_ImageBase_13_0= ruleEString )
                    // InternalMazeComp.g:501:6: lv_ImageBase_13_0= ruleEString
                    {

                    						newCompositeNode(grammarAccess.getZombieAccess().getImageBaseEStringParserRuleCall_10_1_0());
                    					
                    pushFollow(FOLLOW_23);
                    lv_ImageBase_13_0=ruleEString();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getZombieRule());
                    						}
                    						set(
                    							current,
                    							"ImageBase",
                    							lv_ImageBase_13_0,
                    							"main.game.maze.MazeComp.EString");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeComp.g:519:3: (otherlv_14= 'ImageTurnLeft' ( (lv_ImageTurnLeft_15_0= ruleEString ) ) )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==25) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // InternalMazeComp.g:520:4: otherlv_14= 'ImageTurnLeft' ( (lv_ImageTurnLeft_15_0= ruleEString ) )
                    {
                    otherlv_14=(Token)match(input,25,FOLLOW_15); 

                    				newLeafNode(otherlv_14, grammarAccess.getZombieAccess().getImageTurnLeftKeyword_11_0());
                    			
                    // InternalMazeComp.g:524:4: ( (lv_ImageTurnLeft_15_0= ruleEString ) )
                    // InternalMazeComp.g:525:5: (lv_ImageTurnLeft_15_0= ruleEString )
                    {
                    // InternalMazeComp.g:525:5: (lv_ImageTurnLeft_15_0= ruleEString )
                    // InternalMazeComp.g:526:6: lv_ImageTurnLeft_15_0= ruleEString
                    {

                    						newCompositeNode(grammarAccess.getZombieAccess().getImageTurnLeftEStringParserRuleCall_11_1_0());
                    					
                    pushFollow(FOLLOW_24);
                    lv_ImageTurnLeft_15_0=ruleEString();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getZombieRule());
                    						}
                    						set(
                    							current,
                    							"ImageTurnLeft",
                    							lv_ImageTurnLeft_15_0,
                    							"main.game.maze.MazeComp.EString");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeComp.g:544:3: (otherlv_16= 'ImageTurnRight' ( (lv_ImageTurnRight_17_0= ruleEString ) ) )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==26) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // InternalMazeComp.g:545:4: otherlv_16= 'ImageTurnRight' ( (lv_ImageTurnRight_17_0= ruleEString ) )
                    {
                    otherlv_16=(Token)match(input,26,FOLLOW_15); 

                    				newLeafNode(otherlv_16, grammarAccess.getZombieAccess().getImageTurnRightKeyword_12_0());
                    			
                    // InternalMazeComp.g:549:4: ( (lv_ImageTurnRight_17_0= ruleEString ) )
                    // InternalMazeComp.g:550:5: (lv_ImageTurnRight_17_0= ruleEString )
                    {
                    // InternalMazeComp.g:550:5: (lv_ImageTurnRight_17_0= ruleEString )
                    // InternalMazeComp.g:551:6: lv_ImageTurnRight_17_0= ruleEString
                    {

                    						newCompositeNode(grammarAccess.getZombieAccess().getImageTurnRightEStringParserRuleCall_12_1_0());
                    					
                    pushFollow(FOLLOW_25);
                    lv_ImageTurnRight_17_0=ruleEString();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getZombieRule());
                    						}
                    						set(
                    							current,
                    							"ImageTurnRight",
                    							lv_ImageTurnRight_17_0,
                    							"main.game.maze.MazeComp.EString");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeComp.g:569:3: (otherlv_18= 'ImageTurnUp' ( (lv_ImageTurnUp_19_0= ruleEString ) ) )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==27) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // InternalMazeComp.g:570:4: otherlv_18= 'ImageTurnUp' ( (lv_ImageTurnUp_19_0= ruleEString ) )
                    {
                    otherlv_18=(Token)match(input,27,FOLLOW_15); 

                    				newLeafNode(otherlv_18, grammarAccess.getZombieAccess().getImageTurnUpKeyword_13_0());
                    			
                    // InternalMazeComp.g:574:4: ( (lv_ImageTurnUp_19_0= ruleEString ) )
                    // InternalMazeComp.g:575:5: (lv_ImageTurnUp_19_0= ruleEString )
                    {
                    // InternalMazeComp.g:575:5: (lv_ImageTurnUp_19_0= ruleEString )
                    // InternalMazeComp.g:576:6: lv_ImageTurnUp_19_0= ruleEString
                    {

                    						newCompositeNode(grammarAccess.getZombieAccess().getImageTurnUpEStringParserRuleCall_13_1_0());
                    					
                    pushFollow(FOLLOW_26);
                    lv_ImageTurnUp_19_0=ruleEString();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getZombieRule());
                    						}
                    						set(
                    							current,
                    							"ImageTurnUp",
                    							lv_ImageTurnUp_19_0,
                    							"main.game.maze.MazeComp.EString");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeComp.g:594:3: (otherlv_20= 'ImageTurnDown' ( (lv_ImageTurnDown_21_0= ruleEString ) ) )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==28) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // InternalMazeComp.g:595:4: otherlv_20= 'ImageTurnDown' ( (lv_ImageTurnDown_21_0= ruleEString ) )
                    {
                    otherlv_20=(Token)match(input,28,FOLLOW_15); 

                    				newLeafNode(otherlv_20, grammarAccess.getZombieAccess().getImageTurnDownKeyword_14_0());
                    			
                    // InternalMazeComp.g:599:4: ( (lv_ImageTurnDown_21_0= ruleEString ) )
                    // InternalMazeComp.g:600:5: (lv_ImageTurnDown_21_0= ruleEString )
                    {
                    // InternalMazeComp.g:600:5: (lv_ImageTurnDown_21_0= ruleEString )
                    // InternalMazeComp.g:601:6: lv_ImageTurnDown_21_0= ruleEString
                    {

                    						newCompositeNode(grammarAccess.getZombieAccess().getImageTurnDownEStringParserRuleCall_14_1_0());
                    					
                    pushFollow(FOLLOW_27);
                    lv_ImageTurnDown_21_0=ruleEString();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getZombieRule());
                    						}
                    						set(
                    							current,
                    							"ImageTurnDown",
                    							lv_ImageTurnDown_21_0,
                    							"main.game.maze.MazeComp.EString");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeComp.g:619:3: (otherlv_22= 'behavior' ( (lv_behavior_23_0= ruleBehaviorType ) ) )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==29) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // InternalMazeComp.g:620:4: otherlv_22= 'behavior' ( (lv_behavior_23_0= ruleBehaviorType ) )
                    {
                    otherlv_22=(Token)match(input,29,FOLLOW_28); 

                    				newLeafNode(otherlv_22, grammarAccess.getZombieAccess().getBehaviorKeyword_15_0());
                    			
                    // InternalMazeComp.g:624:4: ( (lv_behavior_23_0= ruleBehaviorType ) )
                    // InternalMazeComp.g:625:5: (lv_behavior_23_0= ruleBehaviorType )
                    {
                    // InternalMazeComp.g:625:5: (lv_behavior_23_0= ruleBehaviorType )
                    // InternalMazeComp.g:626:6: lv_behavior_23_0= ruleBehaviorType
                    {

                    						newCompositeNode(grammarAccess.getZombieAccess().getBehaviorBehaviorTypeEnumRuleCall_15_1_0());
                    					
                    pushFollow(FOLLOW_29);
                    lv_behavior_23_0=ruleBehaviorType();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getZombieRule());
                    						}
                    						set(
                    							current,
                    							"behavior",
                    							lv_behavior_23_0,
                    							"main.game.maze.MazeComp.BehaviorType");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_24=(Token)match(input,30,FOLLOW_12); 

            			newLeafNode(otherlv_24, grammarAccess.getZombieAccess().getAttackDamageKeyword_16());
            		
            // InternalMazeComp.g:648:3: ( (lv_attackDamage_25_0= ruleEInt ) )
            // InternalMazeComp.g:649:4: (lv_attackDamage_25_0= ruleEInt )
            {
            // InternalMazeComp.g:649:4: (lv_attackDamage_25_0= ruleEInt )
            // InternalMazeComp.g:650:5: lv_attackDamage_25_0= ruleEInt
            {

            					newCompositeNode(grammarAccess.getZombieAccess().getAttackDamageEIntParserRuleCall_17_0());
            				
            pushFollow(FOLLOW_30);
            lv_attackDamage_25_0=ruleEInt();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getZombieRule());
            					}
            					set(
            						current,
            						"attackDamage",
            						lv_attackDamage_25_0,
            						"main.game.maze.MazeComp.EInt");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_26=(Token)match(input,31,FOLLOW_12); 

            			newLeafNode(otherlv_26, grammarAccess.getZombieAccess().getInfectionLevelKeyword_18());
            		
            // InternalMazeComp.g:671:3: ( (lv_infectionLevel_27_0= ruleEInt ) )
            // InternalMazeComp.g:672:4: (lv_infectionLevel_27_0= ruleEInt )
            {
            // InternalMazeComp.g:672:4: (lv_infectionLevel_27_0= ruleEInt )
            // InternalMazeComp.g:673:5: lv_infectionLevel_27_0= ruleEInt
            {

            					newCompositeNode(grammarAccess.getZombieAccess().getInfectionLevelEIntParserRuleCall_19_0());
            				
            pushFollow(FOLLOW_31);
            lv_infectionLevel_27_0=ruleEInt();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getZombieRule());
            					}
            					set(
            						current,
            						"infectionLevel",
            						lv_infectionLevel_27_0,
            						"main.game.maze.MazeComp.EInt");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_28=(Token)match(input,32,FOLLOW_12); 

            			newLeafNode(otherlv_28, grammarAccess.getZombieAccess().getResurrectionTimeKeyword_20());
            		
            // InternalMazeComp.g:694:3: ( (lv_resurrectionTime_29_0= ruleEInt ) )
            // InternalMazeComp.g:695:4: (lv_resurrectionTime_29_0= ruleEInt )
            {
            // InternalMazeComp.g:695:4: (lv_resurrectionTime_29_0= ruleEInt )
            // InternalMazeComp.g:696:5: lv_resurrectionTime_29_0= ruleEInt
            {

            					newCompositeNode(grammarAccess.getZombieAccess().getResurrectionTimeEIntParserRuleCall_21_0());
            				
            pushFollow(FOLLOW_32);
            lv_resurrectionTime_29_0=ruleEInt();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getZombieRule());
            					}
            					set(
            						current,
            						"resurrectionTime",
            						lv_resurrectionTime_29_0,
            						"main.game.maze.MazeComp.EInt");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalMazeComp.g:713:3: (otherlv_30= 'touchSound' ( (lv_touchSound_31_0= ruleEString ) ) )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==33) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // InternalMazeComp.g:714:4: otherlv_30= 'touchSound' ( (lv_touchSound_31_0= ruleEString ) )
                    {
                    otherlv_30=(Token)match(input,33,FOLLOW_15); 

                    				newLeafNode(otherlv_30, grammarAccess.getZombieAccess().getTouchSoundKeyword_22_0());
                    			
                    // InternalMazeComp.g:718:4: ( (lv_touchSound_31_0= ruleEString ) )
                    // InternalMazeComp.g:719:5: (lv_touchSound_31_0= ruleEString )
                    {
                    // InternalMazeComp.g:719:5: (lv_touchSound_31_0= ruleEString )
                    // InternalMazeComp.g:720:6: lv_touchSound_31_0= ruleEString
                    {

                    						newCompositeNode(grammarAccess.getZombieAccess().getTouchSoundEStringParserRuleCall_22_1_0());
                    					
                    pushFollow(FOLLOW_33);
                    lv_touchSound_31_0=ruleEString();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getZombieRule());
                    						}
                    						set(
                    							current,
                    							"touchSound",
                    							lv_touchSound_31_0,
                    							"main.game.maze.MazeComp.EString");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeComp.g:738:3: (otherlv_32= 'zombieLootTable' ( ( ruleEString ) ) )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==34) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // InternalMazeComp.g:739:4: otherlv_32= 'zombieLootTable' ( ( ruleEString ) )
                    {
                    otherlv_32=(Token)match(input,34,FOLLOW_15); 

                    				newLeafNode(otherlv_32, grammarAccess.getZombieAccess().getZombieLootTableKeyword_23_0());
                    			
                    // InternalMazeComp.g:743:4: ( ( ruleEString ) )
                    // InternalMazeComp.g:744:5: ( ruleEString )
                    {
                    // InternalMazeComp.g:744:5: ( ruleEString )
                    // InternalMazeComp.g:745:6: ruleEString
                    {

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getZombieRule());
                    						}
                    					

                    						newCompositeNode(grammarAccess.getZombieAccess().getZombieLootTableLootTableCrossReference_23_1_0());
                    					
                    pushFollow(FOLLOW_34);
                    ruleEString();

                    state._fsp--;


                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_34=(Token)match(input,35,FOLLOW_2); 

            			newLeafNode(otherlv_34, grammarAccess.getZombieAccess().getRightCurlyBracketKeyword_24());
            		

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
    // $ANTLR end "ruleZombie"


    // $ANTLR start "entryRuleGhost"
    // InternalMazeComp.g:768:1: entryRuleGhost returns [EObject current=null] : iv_ruleGhost= ruleGhost EOF ;
    public final EObject entryRuleGhost() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGhost = null;


        try {
            // InternalMazeComp.g:768:46: (iv_ruleGhost= ruleGhost EOF )
            // InternalMazeComp.g:769:2: iv_ruleGhost= ruleGhost EOF
            {
             newCompositeNode(grammarAccess.getGhostRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleGhost=ruleGhost();

            state._fsp--;

             current =iv_ruleGhost; 
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
    // $ANTLR end "entryRuleGhost"


    // $ANTLR start "ruleGhost"
    // InternalMazeComp.g:775:1: ruleGhost returns [EObject current=null] : (otherlv_0= 'Ghost' otherlv_1= '{' (otherlv_2= 'id' ( (lv_id_3_0= ruleEString ) ) )? (otherlv_4= 'displayName' ( (lv_displayName_5_0= ruleEString ) ) )? otherlv_6= 'enabled' ( (lv_enabled_7_0= ruleEBoolean ) ) otherlv_8= 'health' ( (lv_health_9_0= ruleEInt ) ) otherlv_10= 'speed' ( (lv_speed_11_0= ruleEDouble ) ) (otherlv_12= 'ImageBase' ( (lv_ImageBase_13_0= ruleEString ) ) )? (otherlv_14= 'ImageTurnLeft' ( (lv_ImageTurnLeft_15_0= ruleEString ) ) )? (otherlv_16= 'ImageTurnRight' ( (lv_ImageTurnRight_17_0= ruleEString ) ) )? (otherlv_18= 'ImageTurnUp' ( (lv_ImageTurnUp_19_0= ruleEString ) ) )? (otherlv_20= 'ImageTurnDown' ( (lv_ImageTurnDown_21_0= ruleEString ) ) )? (otherlv_22= 'behavior' ( (lv_behavior_23_0= ruleBehaviorType ) ) )? otherlv_24= 'attackDamage' ( (lv_attackDamage_25_0= ruleEInt ) ) otherlv_26= 'visibilityLevel' ( (lv_visibilityLevel_27_0= ruleEInt ) ) otherlv_28= 'nonTangibilityEnergy' ( (lv_nonTangibilityEnergy_29_0= ruleEDouble ) ) otherlv_30= '}' ) ;
    public final EObject ruleGhost() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
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
        Token otherlv_26=null;
        Token otherlv_28=null;
        Token otherlv_30=null;
        AntlrDatatypeRuleToken lv_id_3_0 = null;

        AntlrDatatypeRuleToken lv_displayName_5_0 = null;

        AntlrDatatypeRuleToken lv_enabled_7_0 = null;

        AntlrDatatypeRuleToken lv_health_9_0 = null;

        AntlrDatatypeRuleToken lv_speed_11_0 = null;

        AntlrDatatypeRuleToken lv_ImageBase_13_0 = null;

        AntlrDatatypeRuleToken lv_ImageTurnLeft_15_0 = null;

        AntlrDatatypeRuleToken lv_ImageTurnRight_17_0 = null;

        AntlrDatatypeRuleToken lv_ImageTurnUp_19_0 = null;

        AntlrDatatypeRuleToken lv_ImageTurnDown_21_0 = null;

        Enumerator lv_behavior_23_0 = null;

        AntlrDatatypeRuleToken lv_attackDamage_25_0 = null;

        AntlrDatatypeRuleToken lv_visibilityLevel_27_0 = null;

        AntlrDatatypeRuleToken lv_nonTangibilityEnergy_29_0 = null;



        	enterRule();

        try {
            // InternalMazeComp.g:781:2: ( (otherlv_0= 'Ghost' otherlv_1= '{' (otherlv_2= 'id' ( (lv_id_3_0= ruleEString ) ) )? (otherlv_4= 'displayName' ( (lv_displayName_5_0= ruleEString ) ) )? otherlv_6= 'enabled' ( (lv_enabled_7_0= ruleEBoolean ) ) otherlv_8= 'health' ( (lv_health_9_0= ruleEInt ) ) otherlv_10= 'speed' ( (lv_speed_11_0= ruleEDouble ) ) (otherlv_12= 'ImageBase' ( (lv_ImageBase_13_0= ruleEString ) ) )? (otherlv_14= 'ImageTurnLeft' ( (lv_ImageTurnLeft_15_0= ruleEString ) ) )? (otherlv_16= 'ImageTurnRight' ( (lv_ImageTurnRight_17_0= ruleEString ) ) )? (otherlv_18= 'ImageTurnUp' ( (lv_ImageTurnUp_19_0= ruleEString ) ) )? (otherlv_20= 'ImageTurnDown' ( (lv_ImageTurnDown_21_0= ruleEString ) ) )? (otherlv_22= 'behavior' ( (lv_behavior_23_0= ruleBehaviorType ) ) )? otherlv_24= 'attackDamage' ( (lv_attackDamage_25_0= ruleEInt ) ) otherlv_26= 'visibilityLevel' ( (lv_visibilityLevel_27_0= ruleEInt ) ) otherlv_28= 'nonTangibilityEnergy' ( (lv_nonTangibilityEnergy_29_0= ruleEDouble ) ) otherlv_30= '}' ) )
            // InternalMazeComp.g:782:2: (otherlv_0= 'Ghost' otherlv_1= '{' (otherlv_2= 'id' ( (lv_id_3_0= ruleEString ) ) )? (otherlv_4= 'displayName' ( (lv_displayName_5_0= ruleEString ) ) )? otherlv_6= 'enabled' ( (lv_enabled_7_0= ruleEBoolean ) ) otherlv_8= 'health' ( (lv_health_9_0= ruleEInt ) ) otherlv_10= 'speed' ( (lv_speed_11_0= ruleEDouble ) ) (otherlv_12= 'ImageBase' ( (lv_ImageBase_13_0= ruleEString ) ) )? (otherlv_14= 'ImageTurnLeft' ( (lv_ImageTurnLeft_15_0= ruleEString ) ) )? (otherlv_16= 'ImageTurnRight' ( (lv_ImageTurnRight_17_0= ruleEString ) ) )? (otherlv_18= 'ImageTurnUp' ( (lv_ImageTurnUp_19_0= ruleEString ) ) )? (otherlv_20= 'ImageTurnDown' ( (lv_ImageTurnDown_21_0= ruleEString ) ) )? (otherlv_22= 'behavior' ( (lv_behavior_23_0= ruleBehaviorType ) ) )? otherlv_24= 'attackDamage' ( (lv_attackDamage_25_0= ruleEInt ) ) otherlv_26= 'visibilityLevel' ( (lv_visibilityLevel_27_0= ruleEInt ) ) otherlv_28= 'nonTangibilityEnergy' ( (lv_nonTangibilityEnergy_29_0= ruleEDouble ) ) otherlv_30= '}' )
            {
            // InternalMazeComp.g:782:2: (otherlv_0= 'Ghost' otherlv_1= '{' (otherlv_2= 'id' ( (lv_id_3_0= ruleEString ) ) )? (otherlv_4= 'displayName' ( (lv_displayName_5_0= ruleEString ) ) )? otherlv_6= 'enabled' ( (lv_enabled_7_0= ruleEBoolean ) ) otherlv_8= 'health' ( (lv_health_9_0= ruleEInt ) ) otherlv_10= 'speed' ( (lv_speed_11_0= ruleEDouble ) ) (otherlv_12= 'ImageBase' ( (lv_ImageBase_13_0= ruleEString ) ) )? (otherlv_14= 'ImageTurnLeft' ( (lv_ImageTurnLeft_15_0= ruleEString ) ) )? (otherlv_16= 'ImageTurnRight' ( (lv_ImageTurnRight_17_0= ruleEString ) ) )? (otherlv_18= 'ImageTurnUp' ( (lv_ImageTurnUp_19_0= ruleEString ) ) )? (otherlv_20= 'ImageTurnDown' ( (lv_ImageTurnDown_21_0= ruleEString ) ) )? (otherlv_22= 'behavior' ( (lv_behavior_23_0= ruleBehaviorType ) ) )? otherlv_24= 'attackDamage' ( (lv_attackDamage_25_0= ruleEInt ) ) otherlv_26= 'visibilityLevel' ( (lv_visibilityLevel_27_0= ruleEInt ) ) otherlv_28= 'nonTangibilityEnergy' ( (lv_nonTangibilityEnergy_29_0= ruleEDouble ) ) otherlv_30= '}' )
            // InternalMazeComp.g:783:3: otherlv_0= 'Ghost' otherlv_1= '{' (otherlv_2= 'id' ( (lv_id_3_0= ruleEString ) ) )? (otherlv_4= 'displayName' ( (lv_displayName_5_0= ruleEString ) ) )? otherlv_6= 'enabled' ( (lv_enabled_7_0= ruleEBoolean ) ) otherlv_8= 'health' ( (lv_health_9_0= ruleEInt ) ) otherlv_10= 'speed' ( (lv_speed_11_0= ruleEDouble ) ) (otherlv_12= 'ImageBase' ( (lv_ImageBase_13_0= ruleEString ) ) )? (otherlv_14= 'ImageTurnLeft' ( (lv_ImageTurnLeft_15_0= ruleEString ) ) )? (otherlv_16= 'ImageTurnRight' ( (lv_ImageTurnRight_17_0= ruleEString ) ) )? (otherlv_18= 'ImageTurnUp' ( (lv_ImageTurnUp_19_0= ruleEString ) ) )? (otherlv_20= 'ImageTurnDown' ( (lv_ImageTurnDown_21_0= ruleEString ) ) )? (otherlv_22= 'behavior' ( (lv_behavior_23_0= ruleBehaviorType ) ) )? otherlv_24= 'attackDamage' ( (lv_attackDamage_25_0= ruleEInt ) ) otherlv_26= 'visibilityLevel' ( (lv_visibilityLevel_27_0= ruleEInt ) ) otherlv_28= 'nonTangibilityEnergy' ( (lv_nonTangibilityEnergy_29_0= ruleEDouble ) ) otherlv_30= '}'
            {
            otherlv_0=(Token)match(input,36,FOLLOW_13); 

            			newLeafNode(otherlv_0, grammarAccess.getGhostAccess().getGhostKeyword_0());
            		
            otherlv_1=(Token)match(input,18,FOLLOW_14); 

            			newLeafNode(otherlv_1, grammarAccess.getGhostAccess().getLeftCurlyBracketKeyword_1());
            		
            // InternalMazeComp.g:791:3: (otherlv_2= 'id' ( (lv_id_3_0= ruleEString ) ) )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==19) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // InternalMazeComp.g:792:4: otherlv_2= 'id' ( (lv_id_3_0= ruleEString ) )
                    {
                    otherlv_2=(Token)match(input,19,FOLLOW_15); 

                    				newLeafNode(otherlv_2, grammarAccess.getGhostAccess().getIdKeyword_2_0());
                    			
                    // InternalMazeComp.g:796:4: ( (lv_id_3_0= ruleEString ) )
                    // InternalMazeComp.g:797:5: (lv_id_3_0= ruleEString )
                    {
                    // InternalMazeComp.g:797:5: (lv_id_3_0= ruleEString )
                    // InternalMazeComp.g:798:6: lv_id_3_0= ruleEString
                    {

                    						newCompositeNode(grammarAccess.getGhostAccess().getIdEStringParserRuleCall_2_1_0());
                    					
                    pushFollow(FOLLOW_16);
                    lv_id_3_0=ruleEString();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getGhostRule());
                    						}
                    						set(
                    							current,
                    							"id",
                    							lv_id_3_0,
                    							"main.game.maze.MazeComp.EString");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeComp.g:816:3: (otherlv_4= 'displayName' ( (lv_displayName_5_0= ruleEString ) ) )?
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==20) ) {
                alt25=1;
            }
            switch (alt25) {
                case 1 :
                    // InternalMazeComp.g:817:4: otherlv_4= 'displayName' ( (lv_displayName_5_0= ruleEString ) )
                    {
                    otherlv_4=(Token)match(input,20,FOLLOW_15); 

                    				newLeafNode(otherlv_4, grammarAccess.getGhostAccess().getDisplayNameKeyword_3_0());
                    			
                    // InternalMazeComp.g:821:4: ( (lv_displayName_5_0= ruleEString ) )
                    // InternalMazeComp.g:822:5: (lv_displayName_5_0= ruleEString )
                    {
                    // InternalMazeComp.g:822:5: (lv_displayName_5_0= ruleEString )
                    // InternalMazeComp.g:823:6: lv_displayName_5_0= ruleEString
                    {

                    						newCompositeNode(grammarAccess.getGhostAccess().getDisplayNameEStringParserRuleCall_3_1_0());
                    					
                    pushFollow(FOLLOW_17);
                    lv_displayName_5_0=ruleEString();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getGhostRule());
                    						}
                    						set(
                    							current,
                    							"displayName",
                    							lv_displayName_5_0,
                    							"main.game.maze.MazeComp.EString");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_6=(Token)match(input,21,FOLLOW_18); 

            			newLeafNode(otherlv_6, grammarAccess.getGhostAccess().getEnabledKeyword_4());
            		
            // InternalMazeComp.g:845:3: ( (lv_enabled_7_0= ruleEBoolean ) )
            // InternalMazeComp.g:846:4: (lv_enabled_7_0= ruleEBoolean )
            {
            // InternalMazeComp.g:846:4: (lv_enabled_7_0= ruleEBoolean )
            // InternalMazeComp.g:847:5: lv_enabled_7_0= ruleEBoolean
            {

            					newCompositeNode(grammarAccess.getGhostAccess().getEnabledEBooleanParserRuleCall_5_0());
            				
            pushFollow(FOLLOW_19);
            lv_enabled_7_0=ruleEBoolean();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getGhostRule());
            					}
            					set(
            						current,
            						"enabled",
            						lv_enabled_7_0,
            						"main.game.maze.MazeComp.EBoolean");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_8=(Token)match(input,22,FOLLOW_12); 

            			newLeafNode(otherlv_8, grammarAccess.getGhostAccess().getHealthKeyword_6());
            		
            // InternalMazeComp.g:868:3: ( (lv_health_9_0= ruleEInt ) )
            // InternalMazeComp.g:869:4: (lv_health_9_0= ruleEInt )
            {
            // InternalMazeComp.g:869:4: (lv_health_9_0= ruleEInt )
            // InternalMazeComp.g:870:5: lv_health_9_0= ruleEInt
            {

            					newCompositeNode(grammarAccess.getGhostAccess().getHealthEIntParserRuleCall_7_0());
            				
            pushFollow(FOLLOW_20);
            lv_health_9_0=ruleEInt();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getGhostRule());
            					}
            					set(
            						current,
            						"health",
            						lv_health_9_0,
            						"main.game.maze.MazeComp.EInt");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_10=(Token)match(input,23,FOLLOW_21); 

            			newLeafNode(otherlv_10, grammarAccess.getGhostAccess().getSpeedKeyword_8());
            		
            // InternalMazeComp.g:891:3: ( (lv_speed_11_0= ruleEDouble ) )
            // InternalMazeComp.g:892:4: (lv_speed_11_0= ruleEDouble )
            {
            // InternalMazeComp.g:892:4: (lv_speed_11_0= ruleEDouble )
            // InternalMazeComp.g:893:5: lv_speed_11_0= ruleEDouble
            {

            					newCompositeNode(grammarAccess.getGhostAccess().getSpeedEDoubleParserRuleCall_9_0());
            				
            pushFollow(FOLLOW_22);
            lv_speed_11_0=ruleEDouble();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getGhostRule());
            					}
            					set(
            						current,
            						"speed",
            						lv_speed_11_0,
            						"main.game.maze.MazeComp.EDouble");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalMazeComp.g:910:3: (otherlv_12= 'ImageBase' ( (lv_ImageBase_13_0= ruleEString ) ) )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==24) ) {
                alt26=1;
            }
            switch (alt26) {
                case 1 :
                    // InternalMazeComp.g:911:4: otherlv_12= 'ImageBase' ( (lv_ImageBase_13_0= ruleEString ) )
                    {
                    otherlv_12=(Token)match(input,24,FOLLOW_15); 

                    				newLeafNode(otherlv_12, grammarAccess.getGhostAccess().getImageBaseKeyword_10_0());
                    			
                    // InternalMazeComp.g:915:4: ( (lv_ImageBase_13_0= ruleEString ) )
                    // InternalMazeComp.g:916:5: (lv_ImageBase_13_0= ruleEString )
                    {
                    // InternalMazeComp.g:916:5: (lv_ImageBase_13_0= ruleEString )
                    // InternalMazeComp.g:917:6: lv_ImageBase_13_0= ruleEString
                    {

                    						newCompositeNode(grammarAccess.getGhostAccess().getImageBaseEStringParserRuleCall_10_1_0());
                    					
                    pushFollow(FOLLOW_23);
                    lv_ImageBase_13_0=ruleEString();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getGhostRule());
                    						}
                    						set(
                    							current,
                    							"ImageBase",
                    							lv_ImageBase_13_0,
                    							"main.game.maze.MazeComp.EString");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeComp.g:935:3: (otherlv_14= 'ImageTurnLeft' ( (lv_ImageTurnLeft_15_0= ruleEString ) ) )?
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==25) ) {
                alt27=1;
            }
            switch (alt27) {
                case 1 :
                    // InternalMazeComp.g:936:4: otherlv_14= 'ImageTurnLeft' ( (lv_ImageTurnLeft_15_0= ruleEString ) )
                    {
                    otherlv_14=(Token)match(input,25,FOLLOW_15); 

                    				newLeafNode(otherlv_14, grammarAccess.getGhostAccess().getImageTurnLeftKeyword_11_0());
                    			
                    // InternalMazeComp.g:940:4: ( (lv_ImageTurnLeft_15_0= ruleEString ) )
                    // InternalMazeComp.g:941:5: (lv_ImageTurnLeft_15_0= ruleEString )
                    {
                    // InternalMazeComp.g:941:5: (lv_ImageTurnLeft_15_0= ruleEString )
                    // InternalMazeComp.g:942:6: lv_ImageTurnLeft_15_0= ruleEString
                    {

                    						newCompositeNode(grammarAccess.getGhostAccess().getImageTurnLeftEStringParserRuleCall_11_1_0());
                    					
                    pushFollow(FOLLOW_24);
                    lv_ImageTurnLeft_15_0=ruleEString();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getGhostRule());
                    						}
                    						set(
                    							current,
                    							"ImageTurnLeft",
                    							lv_ImageTurnLeft_15_0,
                    							"main.game.maze.MazeComp.EString");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeComp.g:960:3: (otherlv_16= 'ImageTurnRight' ( (lv_ImageTurnRight_17_0= ruleEString ) ) )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==26) ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // InternalMazeComp.g:961:4: otherlv_16= 'ImageTurnRight' ( (lv_ImageTurnRight_17_0= ruleEString ) )
                    {
                    otherlv_16=(Token)match(input,26,FOLLOW_15); 

                    				newLeafNode(otherlv_16, grammarAccess.getGhostAccess().getImageTurnRightKeyword_12_0());
                    			
                    // InternalMazeComp.g:965:4: ( (lv_ImageTurnRight_17_0= ruleEString ) )
                    // InternalMazeComp.g:966:5: (lv_ImageTurnRight_17_0= ruleEString )
                    {
                    // InternalMazeComp.g:966:5: (lv_ImageTurnRight_17_0= ruleEString )
                    // InternalMazeComp.g:967:6: lv_ImageTurnRight_17_0= ruleEString
                    {

                    						newCompositeNode(grammarAccess.getGhostAccess().getImageTurnRightEStringParserRuleCall_12_1_0());
                    					
                    pushFollow(FOLLOW_25);
                    lv_ImageTurnRight_17_0=ruleEString();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getGhostRule());
                    						}
                    						set(
                    							current,
                    							"ImageTurnRight",
                    							lv_ImageTurnRight_17_0,
                    							"main.game.maze.MazeComp.EString");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeComp.g:985:3: (otherlv_18= 'ImageTurnUp' ( (lv_ImageTurnUp_19_0= ruleEString ) ) )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==27) ) {
                alt29=1;
            }
            switch (alt29) {
                case 1 :
                    // InternalMazeComp.g:986:4: otherlv_18= 'ImageTurnUp' ( (lv_ImageTurnUp_19_0= ruleEString ) )
                    {
                    otherlv_18=(Token)match(input,27,FOLLOW_15); 

                    				newLeafNode(otherlv_18, grammarAccess.getGhostAccess().getImageTurnUpKeyword_13_0());
                    			
                    // InternalMazeComp.g:990:4: ( (lv_ImageTurnUp_19_0= ruleEString ) )
                    // InternalMazeComp.g:991:5: (lv_ImageTurnUp_19_0= ruleEString )
                    {
                    // InternalMazeComp.g:991:5: (lv_ImageTurnUp_19_0= ruleEString )
                    // InternalMazeComp.g:992:6: lv_ImageTurnUp_19_0= ruleEString
                    {

                    						newCompositeNode(grammarAccess.getGhostAccess().getImageTurnUpEStringParserRuleCall_13_1_0());
                    					
                    pushFollow(FOLLOW_26);
                    lv_ImageTurnUp_19_0=ruleEString();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getGhostRule());
                    						}
                    						set(
                    							current,
                    							"ImageTurnUp",
                    							lv_ImageTurnUp_19_0,
                    							"main.game.maze.MazeComp.EString");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeComp.g:1010:3: (otherlv_20= 'ImageTurnDown' ( (lv_ImageTurnDown_21_0= ruleEString ) ) )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==28) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // InternalMazeComp.g:1011:4: otherlv_20= 'ImageTurnDown' ( (lv_ImageTurnDown_21_0= ruleEString ) )
                    {
                    otherlv_20=(Token)match(input,28,FOLLOW_15); 

                    				newLeafNode(otherlv_20, grammarAccess.getGhostAccess().getImageTurnDownKeyword_14_0());
                    			
                    // InternalMazeComp.g:1015:4: ( (lv_ImageTurnDown_21_0= ruleEString ) )
                    // InternalMazeComp.g:1016:5: (lv_ImageTurnDown_21_0= ruleEString )
                    {
                    // InternalMazeComp.g:1016:5: (lv_ImageTurnDown_21_0= ruleEString )
                    // InternalMazeComp.g:1017:6: lv_ImageTurnDown_21_0= ruleEString
                    {

                    						newCompositeNode(grammarAccess.getGhostAccess().getImageTurnDownEStringParserRuleCall_14_1_0());
                    					
                    pushFollow(FOLLOW_27);
                    lv_ImageTurnDown_21_0=ruleEString();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getGhostRule());
                    						}
                    						set(
                    							current,
                    							"ImageTurnDown",
                    							lv_ImageTurnDown_21_0,
                    							"main.game.maze.MazeComp.EString");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeComp.g:1035:3: (otherlv_22= 'behavior' ( (lv_behavior_23_0= ruleBehaviorType ) ) )?
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==29) ) {
                alt31=1;
            }
            switch (alt31) {
                case 1 :
                    // InternalMazeComp.g:1036:4: otherlv_22= 'behavior' ( (lv_behavior_23_0= ruleBehaviorType ) )
                    {
                    otherlv_22=(Token)match(input,29,FOLLOW_28); 

                    				newLeafNode(otherlv_22, grammarAccess.getGhostAccess().getBehaviorKeyword_15_0());
                    			
                    // InternalMazeComp.g:1040:4: ( (lv_behavior_23_0= ruleBehaviorType ) )
                    // InternalMazeComp.g:1041:5: (lv_behavior_23_0= ruleBehaviorType )
                    {
                    // InternalMazeComp.g:1041:5: (lv_behavior_23_0= ruleBehaviorType )
                    // InternalMazeComp.g:1042:6: lv_behavior_23_0= ruleBehaviorType
                    {

                    						newCompositeNode(grammarAccess.getGhostAccess().getBehaviorBehaviorTypeEnumRuleCall_15_1_0());
                    					
                    pushFollow(FOLLOW_29);
                    lv_behavior_23_0=ruleBehaviorType();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getGhostRule());
                    						}
                    						set(
                    							current,
                    							"behavior",
                    							lv_behavior_23_0,
                    							"main.game.maze.MazeComp.BehaviorType");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_24=(Token)match(input,30,FOLLOW_12); 

            			newLeafNode(otherlv_24, grammarAccess.getGhostAccess().getAttackDamageKeyword_16());
            		
            // InternalMazeComp.g:1064:3: ( (lv_attackDamage_25_0= ruleEInt ) )
            // InternalMazeComp.g:1065:4: (lv_attackDamage_25_0= ruleEInt )
            {
            // InternalMazeComp.g:1065:4: (lv_attackDamage_25_0= ruleEInt )
            // InternalMazeComp.g:1066:5: lv_attackDamage_25_0= ruleEInt
            {

            					newCompositeNode(grammarAccess.getGhostAccess().getAttackDamageEIntParserRuleCall_17_0());
            				
            pushFollow(FOLLOW_35);
            lv_attackDamage_25_0=ruleEInt();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getGhostRule());
            					}
            					set(
            						current,
            						"attackDamage",
            						lv_attackDamage_25_0,
            						"main.game.maze.MazeComp.EInt");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_26=(Token)match(input,37,FOLLOW_12); 

            			newLeafNode(otherlv_26, grammarAccess.getGhostAccess().getVisibilityLevelKeyword_18());
            		
            // InternalMazeComp.g:1087:3: ( (lv_visibilityLevel_27_0= ruleEInt ) )
            // InternalMazeComp.g:1088:4: (lv_visibilityLevel_27_0= ruleEInt )
            {
            // InternalMazeComp.g:1088:4: (lv_visibilityLevel_27_0= ruleEInt )
            // InternalMazeComp.g:1089:5: lv_visibilityLevel_27_0= ruleEInt
            {

            					newCompositeNode(grammarAccess.getGhostAccess().getVisibilityLevelEIntParserRuleCall_19_0());
            				
            pushFollow(FOLLOW_36);
            lv_visibilityLevel_27_0=ruleEInt();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getGhostRule());
            					}
            					set(
            						current,
            						"visibilityLevel",
            						lv_visibilityLevel_27_0,
            						"main.game.maze.MazeComp.EInt");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_28=(Token)match(input,38,FOLLOW_21); 

            			newLeafNode(otherlv_28, grammarAccess.getGhostAccess().getNonTangibilityEnergyKeyword_20());
            		
            // InternalMazeComp.g:1110:3: ( (lv_nonTangibilityEnergy_29_0= ruleEDouble ) )
            // InternalMazeComp.g:1111:4: (lv_nonTangibilityEnergy_29_0= ruleEDouble )
            {
            // InternalMazeComp.g:1111:4: (lv_nonTangibilityEnergy_29_0= ruleEDouble )
            // InternalMazeComp.g:1112:5: lv_nonTangibilityEnergy_29_0= ruleEDouble
            {

            					newCompositeNode(grammarAccess.getGhostAccess().getNonTangibilityEnergyEDoubleParserRuleCall_21_0());
            				
            pushFollow(FOLLOW_34);
            lv_nonTangibilityEnergy_29_0=ruleEDouble();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getGhostRule());
            					}
            					set(
            						current,
            						"nonTangibilityEnergy",
            						lv_nonTangibilityEnergy_29_0,
            						"main.game.maze.MazeComp.EDouble");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_30=(Token)match(input,35,FOLLOW_2); 

            			newLeafNode(otherlv_30, grammarAccess.getGhostAccess().getRightCurlyBracketKeyword_22());
            		

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
    // $ANTLR end "ruleGhost"


    // $ANTLR start "entryRulePumpkinBomber"
    // InternalMazeComp.g:1137:1: entryRulePumpkinBomber returns [EObject current=null] : iv_rulePumpkinBomber= rulePumpkinBomber EOF ;
    public final EObject entryRulePumpkinBomber() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePumpkinBomber = null;


        try {
            // InternalMazeComp.g:1137:54: (iv_rulePumpkinBomber= rulePumpkinBomber EOF )
            // InternalMazeComp.g:1138:2: iv_rulePumpkinBomber= rulePumpkinBomber EOF
            {
             newCompositeNode(grammarAccess.getPumpkinBomberRule()); 
            pushFollow(FOLLOW_1);
            iv_rulePumpkinBomber=rulePumpkinBomber();

            state._fsp--;

             current =iv_rulePumpkinBomber; 
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
    // $ANTLR end "entryRulePumpkinBomber"


    // $ANTLR start "rulePumpkinBomber"
    // InternalMazeComp.g:1144:1: rulePumpkinBomber returns [EObject current=null] : (otherlv_0= 'PumpkinBomber' otherlv_1= '{' (otherlv_2= 'id' ( (lv_id_3_0= ruleEString ) ) )? (otherlv_4= 'displayName' ( (lv_displayName_5_0= ruleEString ) ) )? otherlv_6= 'enabled' ( (lv_enabled_7_0= ruleEBoolean ) ) otherlv_8= 'health' ( (lv_health_9_0= ruleEInt ) ) otherlv_10= 'speed' ( (lv_speed_11_0= ruleEDouble ) ) (otherlv_12= 'ImageBase' ( (lv_ImageBase_13_0= ruleEString ) ) )? (otherlv_14= 'ImageTurnLeft' ( (lv_ImageTurnLeft_15_0= ruleEString ) ) )? (otherlv_16= 'ImageTurnRight' ( (lv_ImageTurnRight_17_0= ruleEString ) ) )? (otherlv_18= 'ImageTurnUp' ( (lv_ImageTurnUp_19_0= ruleEString ) ) )? (otherlv_20= 'ImageTurnDown' ( (lv_ImageTurnDown_21_0= ruleEString ) ) )? (otherlv_22= 'behavior' ( (lv_behavior_23_0= ruleBehaviorType ) ) )? otherlv_24= 'attackRange' ( (lv_attackRange_25_0= ruleEDouble ) ) otherlv_26= 'attackCooldownMs' ( (lv_attackCooldownMs_27_0= ruleEInt ) ) otherlv_28= 'attackDamage' ( (lv_attackDamage_29_0= ruleEInt ) ) otherlv_30= 'projectileSpeed' ( (lv_projectileSpeed_31_0= ruleEDouble ) ) otherlv_32= 'projectileType' ( (lv_projectileType_33_0= ruleProjectileType ) ) otherlv_34= 'splashRadius' ( (lv_splashRadius_35_0= ruleEDouble ) ) otherlv_36= 'arcHeight' ( (lv_arcHeight_37_0= ruleEDouble ) ) (otherlv_38= 'projectileImage' ( (lv_projectileImage_39_0= ruleEString ) ) )? (otherlv_40= 'explosionImage' ( (lv_explosionImage_41_0= ruleEString ) ) )? (otherlv_42= 'explosionSound' ( (lv_explosionSound_43_0= ruleEString ) ) )? (otherlv_44= 'throwSound' ( (lv_throwSound_45_0= ruleEString ) ) )? otherlv_46= '}' ) ;
    public final EObject rulePumpkinBomber() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
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
        Token otherlv_26=null;
        Token otherlv_28=null;
        Token otherlv_30=null;
        Token otherlv_32=null;
        Token otherlv_34=null;
        Token otherlv_36=null;
        Token otherlv_38=null;
        Token otherlv_40=null;
        Token otherlv_42=null;
        Token otherlv_44=null;
        Token otherlv_46=null;
        AntlrDatatypeRuleToken lv_id_3_0 = null;

        AntlrDatatypeRuleToken lv_displayName_5_0 = null;

        AntlrDatatypeRuleToken lv_enabled_7_0 = null;

        AntlrDatatypeRuleToken lv_health_9_0 = null;

        AntlrDatatypeRuleToken lv_speed_11_0 = null;

        AntlrDatatypeRuleToken lv_ImageBase_13_0 = null;

        AntlrDatatypeRuleToken lv_ImageTurnLeft_15_0 = null;

        AntlrDatatypeRuleToken lv_ImageTurnRight_17_0 = null;

        AntlrDatatypeRuleToken lv_ImageTurnUp_19_0 = null;

        AntlrDatatypeRuleToken lv_ImageTurnDown_21_0 = null;

        Enumerator lv_behavior_23_0 = null;

        AntlrDatatypeRuleToken lv_attackRange_25_0 = null;

        AntlrDatatypeRuleToken lv_attackCooldownMs_27_0 = null;

        AntlrDatatypeRuleToken lv_attackDamage_29_0 = null;

        AntlrDatatypeRuleToken lv_projectileSpeed_31_0 = null;

        Enumerator lv_projectileType_33_0 = null;

        AntlrDatatypeRuleToken lv_splashRadius_35_0 = null;

        AntlrDatatypeRuleToken lv_arcHeight_37_0 = null;

        AntlrDatatypeRuleToken lv_projectileImage_39_0 = null;

        AntlrDatatypeRuleToken lv_explosionImage_41_0 = null;

        AntlrDatatypeRuleToken lv_explosionSound_43_0 = null;

        AntlrDatatypeRuleToken lv_throwSound_45_0 = null;



        	enterRule();

        try {
            // InternalMazeComp.g:1150:2: ( (otherlv_0= 'PumpkinBomber' otherlv_1= '{' (otherlv_2= 'id' ( (lv_id_3_0= ruleEString ) ) )? (otherlv_4= 'displayName' ( (lv_displayName_5_0= ruleEString ) ) )? otherlv_6= 'enabled' ( (lv_enabled_7_0= ruleEBoolean ) ) otherlv_8= 'health' ( (lv_health_9_0= ruleEInt ) ) otherlv_10= 'speed' ( (lv_speed_11_0= ruleEDouble ) ) (otherlv_12= 'ImageBase' ( (lv_ImageBase_13_0= ruleEString ) ) )? (otherlv_14= 'ImageTurnLeft' ( (lv_ImageTurnLeft_15_0= ruleEString ) ) )? (otherlv_16= 'ImageTurnRight' ( (lv_ImageTurnRight_17_0= ruleEString ) ) )? (otherlv_18= 'ImageTurnUp' ( (lv_ImageTurnUp_19_0= ruleEString ) ) )? (otherlv_20= 'ImageTurnDown' ( (lv_ImageTurnDown_21_0= ruleEString ) ) )? (otherlv_22= 'behavior' ( (lv_behavior_23_0= ruleBehaviorType ) ) )? otherlv_24= 'attackRange' ( (lv_attackRange_25_0= ruleEDouble ) ) otherlv_26= 'attackCooldownMs' ( (lv_attackCooldownMs_27_0= ruleEInt ) ) otherlv_28= 'attackDamage' ( (lv_attackDamage_29_0= ruleEInt ) ) otherlv_30= 'projectileSpeed' ( (lv_projectileSpeed_31_0= ruleEDouble ) ) otherlv_32= 'projectileType' ( (lv_projectileType_33_0= ruleProjectileType ) ) otherlv_34= 'splashRadius' ( (lv_splashRadius_35_0= ruleEDouble ) ) otherlv_36= 'arcHeight' ( (lv_arcHeight_37_0= ruleEDouble ) ) (otherlv_38= 'projectileImage' ( (lv_projectileImage_39_0= ruleEString ) ) )? (otherlv_40= 'explosionImage' ( (lv_explosionImage_41_0= ruleEString ) ) )? (otherlv_42= 'explosionSound' ( (lv_explosionSound_43_0= ruleEString ) ) )? (otherlv_44= 'throwSound' ( (lv_throwSound_45_0= ruleEString ) ) )? otherlv_46= '}' ) )
            // InternalMazeComp.g:1151:2: (otherlv_0= 'PumpkinBomber' otherlv_1= '{' (otherlv_2= 'id' ( (lv_id_3_0= ruleEString ) ) )? (otherlv_4= 'displayName' ( (lv_displayName_5_0= ruleEString ) ) )? otherlv_6= 'enabled' ( (lv_enabled_7_0= ruleEBoolean ) ) otherlv_8= 'health' ( (lv_health_9_0= ruleEInt ) ) otherlv_10= 'speed' ( (lv_speed_11_0= ruleEDouble ) ) (otherlv_12= 'ImageBase' ( (lv_ImageBase_13_0= ruleEString ) ) )? (otherlv_14= 'ImageTurnLeft' ( (lv_ImageTurnLeft_15_0= ruleEString ) ) )? (otherlv_16= 'ImageTurnRight' ( (lv_ImageTurnRight_17_0= ruleEString ) ) )? (otherlv_18= 'ImageTurnUp' ( (lv_ImageTurnUp_19_0= ruleEString ) ) )? (otherlv_20= 'ImageTurnDown' ( (lv_ImageTurnDown_21_0= ruleEString ) ) )? (otherlv_22= 'behavior' ( (lv_behavior_23_0= ruleBehaviorType ) ) )? otherlv_24= 'attackRange' ( (lv_attackRange_25_0= ruleEDouble ) ) otherlv_26= 'attackCooldownMs' ( (lv_attackCooldownMs_27_0= ruleEInt ) ) otherlv_28= 'attackDamage' ( (lv_attackDamage_29_0= ruleEInt ) ) otherlv_30= 'projectileSpeed' ( (lv_projectileSpeed_31_0= ruleEDouble ) ) otherlv_32= 'projectileType' ( (lv_projectileType_33_0= ruleProjectileType ) ) otherlv_34= 'splashRadius' ( (lv_splashRadius_35_0= ruleEDouble ) ) otherlv_36= 'arcHeight' ( (lv_arcHeight_37_0= ruleEDouble ) ) (otherlv_38= 'projectileImage' ( (lv_projectileImage_39_0= ruleEString ) ) )? (otherlv_40= 'explosionImage' ( (lv_explosionImage_41_0= ruleEString ) ) )? (otherlv_42= 'explosionSound' ( (lv_explosionSound_43_0= ruleEString ) ) )? (otherlv_44= 'throwSound' ( (lv_throwSound_45_0= ruleEString ) ) )? otherlv_46= '}' )
            {
            // InternalMazeComp.g:1151:2: (otherlv_0= 'PumpkinBomber' otherlv_1= '{' (otherlv_2= 'id' ( (lv_id_3_0= ruleEString ) ) )? (otherlv_4= 'displayName' ( (lv_displayName_5_0= ruleEString ) ) )? otherlv_6= 'enabled' ( (lv_enabled_7_0= ruleEBoolean ) ) otherlv_8= 'health' ( (lv_health_9_0= ruleEInt ) ) otherlv_10= 'speed' ( (lv_speed_11_0= ruleEDouble ) ) (otherlv_12= 'ImageBase' ( (lv_ImageBase_13_0= ruleEString ) ) )? (otherlv_14= 'ImageTurnLeft' ( (lv_ImageTurnLeft_15_0= ruleEString ) ) )? (otherlv_16= 'ImageTurnRight' ( (lv_ImageTurnRight_17_0= ruleEString ) ) )? (otherlv_18= 'ImageTurnUp' ( (lv_ImageTurnUp_19_0= ruleEString ) ) )? (otherlv_20= 'ImageTurnDown' ( (lv_ImageTurnDown_21_0= ruleEString ) ) )? (otherlv_22= 'behavior' ( (lv_behavior_23_0= ruleBehaviorType ) ) )? otherlv_24= 'attackRange' ( (lv_attackRange_25_0= ruleEDouble ) ) otherlv_26= 'attackCooldownMs' ( (lv_attackCooldownMs_27_0= ruleEInt ) ) otherlv_28= 'attackDamage' ( (lv_attackDamage_29_0= ruleEInt ) ) otherlv_30= 'projectileSpeed' ( (lv_projectileSpeed_31_0= ruleEDouble ) ) otherlv_32= 'projectileType' ( (lv_projectileType_33_0= ruleProjectileType ) ) otherlv_34= 'splashRadius' ( (lv_splashRadius_35_0= ruleEDouble ) ) otherlv_36= 'arcHeight' ( (lv_arcHeight_37_0= ruleEDouble ) ) (otherlv_38= 'projectileImage' ( (lv_projectileImage_39_0= ruleEString ) ) )? (otherlv_40= 'explosionImage' ( (lv_explosionImage_41_0= ruleEString ) ) )? (otherlv_42= 'explosionSound' ( (lv_explosionSound_43_0= ruleEString ) ) )? (otherlv_44= 'throwSound' ( (lv_throwSound_45_0= ruleEString ) ) )? otherlv_46= '}' )
            // InternalMazeComp.g:1152:3: otherlv_0= 'PumpkinBomber' otherlv_1= '{' (otherlv_2= 'id' ( (lv_id_3_0= ruleEString ) ) )? (otherlv_4= 'displayName' ( (lv_displayName_5_0= ruleEString ) ) )? otherlv_6= 'enabled' ( (lv_enabled_7_0= ruleEBoolean ) ) otherlv_8= 'health' ( (lv_health_9_0= ruleEInt ) ) otherlv_10= 'speed' ( (lv_speed_11_0= ruleEDouble ) ) (otherlv_12= 'ImageBase' ( (lv_ImageBase_13_0= ruleEString ) ) )? (otherlv_14= 'ImageTurnLeft' ( (lv_ImageTurnLeft_15_0= ruleEString ) ) )? (otherlv_16= 'ImageTurnRight' ( (lv_ImageTurnRight_17_0= ruleEString ) ) )? (otherlv_18= 'ImageTurnUp' ( (lv_ImageTurnUp_19_0= ruleEString ) ) )? (otherlv_20= 'ImageTurnDown' ( (lv_ImageTurnDown_21_0= ruleEString ) ) )? (otherlv_22= 'behavior' ( (lv_behavior_23_0= ruleBehaviorType ) ) )? otherlv_24= 'attackRange' ( (lv_attackRange_25_0= ruleEDouble ) ) otherlv_26= 'attackCooldownMs' ( (lv_attackCooldownMs_27_0= ruleEInt ) ) otherlv_28= 'attackDamage' ( (lv_attackDamage_29_0= ruleEInt ) ) otherlv_30= 'projectileSpeed' ( (lv_projectileSpeed_31_0= ruleEDouble ) ) otherlv_32= 'projectileType' ( (lv_projectileType_33_0= ruleProjectileType ) ) otherlv_34= 'splashRadius' ( (lv_splashRadius_35_0= ruleEDouble ) ) otherlv_36= 'arcHeight' ( (lv_arcHeight_37_0= ruleEDouble ) ) (otherlv_38= 'projectileImage' ( (lv_projectileImage_39_0= ruleEString ) ) )? (otherlv_40= 'explosionImage' ( (lv_explosionImage_41_0= ruleEString ) ) )? (otherlv_42= 'explosionSound' ( (lv_explosionSound_43_0= ruleEString ) ) )? (otherlv_44= 'throwSound' ( (lv_throwSound_45_0= ruleEString ) ) )? otherlv_46= '}'
            {
            otherlv_0=(Token)match(input,39,FOLLOW_13); 

            			newLeafNode(otherlv_0, grammarAccess.getPumpkinBomberAccess().getPumpkinBomberKeyword_0());
            		
            otherlv_1=(Token)match(input,18,FOLLOW_14); 

            			newLeafNode(otherlv_1, grammarAccess.getPumpkinBomberAccess().getLeftCurlyBracketKeyword_1());
            		
            // InternalMazeComp.g:1160:3: (otherlv_2= 'id' ( (lv_id_3_0= ruleEString ) ) )?
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==19) ) {
                alt32=1;
            }
            switch (alt32) {
                case 1 :
                    // InternalMazeComp.g:1161:4: otherlv_2= 'id' ( (lv_id_3_0= ruleEString ) )
                    {
                    otherlv_2=(Token)match(input,19,FOLLOW_15); 

                    				newLeafNode(otherlv_2, grammarAccess.getPumpkinBomberAccess().getIdKeyword_2_0());
                    			
                    // InternalMazeComp.g:1165:4: ( (lv_id_3_0= ruleEString ) )
                    // InternalMazeComp.g:1166:5: (lv_id_3_0= ruleEString )
                    {
                    // InternalMazeComp.g:1166:5: (lv_id_3_0= ruleEString )
                    // InternalMazeComp.g:1167:6: lv_id_3_0= ruleEString
                    {

                    						newCompositeNode(grammarAccess.getPumpkinBomberAccess().getIdEStringParserRuleCall_2_1_0());
                    					
                    pushFollow(FOLLOW_16);
                    lv_id_3_0=ruleEString();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getPumpkinBomberRule());
                    						}
                    						set(
                    							current,
                    							"id",
                    							lv_id_3_0,
                    							"main.game.maze.MazeComp.EString");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeComp.g:1185:3: (otherlv_4= 'displayName' ( (lv_displayName_5_0= ruleEString ) ) )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==20) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // InternalMazeComp.g:1186:4: otherlv_4= 'displayName' ( (lv_displayName_5_0= ruleEString ) )
                    {
                    otherlv_4=(Token)match(input,20,FOLLOW_15); 

                    				newLeafNode(otherlv_4, grammarAccess.getPumpkinBomberAccess().getDisplayNameKeyword_3_0());
                    			
                    // InternalMazeComp.g:1190:4: ( (lv_displayName_5_0= ruleEString ) )
                    // InternalMazeComp.g:1191:5: (lv_displayName_5_0= ruleEString )
                    {
                    // InternalMazeComp.g:1191:5: (lv_displayName_5_0= ruleEString )
                    // InternalMazeComp.g:1192:6: lv_displayName_5_0= ruleEString
                    {

                    						newCompositeNode(grammarAccess.getPumpkinBomberAccess().getDisplayNameEStringParserRuleCall_3_1_0());
                    					
                    pushFollow(FOLLOW_17);
                    lv_displayName_5_0=ruleEString();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getPumpkinBomberRule());
                    						}
                    						set(
                    							current,
                    							"displayName",
                    							lv_displayName_5_0,
                    							"main.game.maze.MazeComp.EString");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_6=(Token)match(input,21,FOLLOW_18); 

            			newLeafNode(otherlv_6, grammarAccess.getPumpkinBomberAccess().getEnabledKeyword_4());
            		
            // InternalMazeComp.g:1214:3: ( (lv_enabled_7_0= ruleEBoolean ) )
            // InternalMazeComp.g:1215:4: (lv_enabled_7_0= ruleEBoolean )
            {
            // InternalMazeComp.g:1215:4: (lv_enabled_7_0= ruleEBoolean )
            // InternalMazeComp.g:1216:5: lv_enabled_7_0= ruleEBoolean
            {

            					newCompositeNode(grammarAccess.getPumpkinBomberAccess().getEnabledEBooleanParserRuleCall_5_0());
            				
            pushFollow(FOLLOW_19);
            lv_enabled_7_0=ruleEBoolean();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getPumpkinBomberRule());
            					}
            					set(
            						current,
            						"enabled",
            						lv_enabled_7_0,
            						"main.game.maze.MazeComp.EBoolean");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_8=(Token)match(input,22,FOLLOW_12); 

            			newLeafNode(otherlv_8, grammarAccess.getPumpkinBomberAccess().getHealthKeyword_6());
            		
            // InternalMazeComp.g:1237:3: ( (lv_health_9_0= ruleEInt ) )
            // InternalMazeComp.g:1238:4: (lv_health_9_0= ruleEInt )
            {
            // InternalMazeComp.g:1238:4: (lv_health_9_0= ruleEInt )
            // InternalMazeComp.g:1239:5: lv_health_9_0= ruleEInt
            {

            					newCompositeNode(grammarAccess.getPumpkinBomberAccess().getHealthEIntParserRuleCall_7_0());
            				
            pushFollow(FOLLOW_20);
            lv_health_9_0=ruleEInt();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getPumpkinBomberRule());
            					}
            					set(
            						current,
            						"health",
            						lv_health_9_0,
            						"main.game.maze.MazeComp.EInt");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_10=(Token)match(input,23,FOLLOW_21); 

            			newLeafNode(otherlv_10, grammarAccess.getPumpkinBomberAccess().getSpeedKeyword_8());
            		
            // InternalMazeComp.g:1260:3: ( (lv_speed_11_0= ruleEDouble ) )
            // InternalMazeComp.g:1261:4: (lv_speed_11_0= ruleEDouble )
            {
            // InternalMazeComp.g:1261:4: (lv_speed_11_0= ruleEDouble )
            // InternalMazeComp.g:1262:5: lv_speed_11_0= ruleEDouble
            {

            					newCompositeNode(grammarAccess.getPumpkinBomberAccess().getSpeedEDoubleParserRuleCall_9_0());
            				
            pushFollow(FOLLOW_37);
            lv_speed_11_0=ruleEDouble();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getPumpkinBomberRule());
            					}
            					set(
            						current,
            						"speed",
            						lv_speed_11_0,
            						"main.game.maze.MazeComp.EDouble");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalMazeComp.g:1279:3: (otherlv_12= 'ImageBase' ( (lv_ImageBase_13_0= ruleEString ) ) )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==24) ) {
                alt34=1;
            }
            switch (alt34) {
                case 1 :
                    // InternalMazeComp.g:1280:4: otherlv_12= 'ImageBase' ( (lv_ImageBase_13_0= ruleEString ) )
                    {
                    otherlv_12=(Token)match(input,24,FOLLOW_15); 

                    				newLeafNode(otherlv_12, grammarAccess.getPumpkinBomberAccess().getImageBaseKeyword_10_0());
                    			
                    // InternalMazeComp.g:1284:4: ( (lv_ImageBase_13_0= ruleEString ) )
                    // InternalMazeComp.g:1285:5: (lv_ImageBase_13_0= ruleEString )
                    {
                    // InternalMazeComp.g:1285:5: (lv_ImageBase_13_0= ruleEString )
                    // InternalMazeComp.g:1286:6: lv_ImageBase_13_0= ruleEString
                    {

                    						newCompositeNode(grammarAccess.getPumpkinBomberAccess().getImageBaseEStringParserRuleCall_10_1_0());
                    					
                    pushFollow(FOLLOW_38);
                    lv_ImageBase_13_0=ruleEString();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getPumpkinBomberRule());
                    						}
                    						set(
                    							current,
                    							"ImageBase",
                    							lv_ImageBase_13_0,
                    							"main.game.maze.MazeComp.EString");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeComp.g:1304:3: (otherlv_14= 'ImageTurnLeft' ( (lv_ImageTurnLeft_15_0= ruleEString ) ) )?
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==25) ) {
                alt35=1;
            }
            switch (alt35) {
                case 1 :
                    // InternalMazeComp.g:1305:4: otherlv_14= 'ImageTurnLeft' ( (lv_ImageTurnLeft_15_0= ruleEString ) )
                    {
                    otherlv_14=(Token)match(input,25,FOLLOW_15); 

                    				newLeafNode(otherlv_14, grammarAccess.getPumpkinBomberAccess().getImageTurnLeftKeyword_11_0());
                    			
                    // InternalMazeComp.g:1309:4: ( (lv_ImageTurnLeft_15_0= ruleEString ) )
                    // InternalMazeComp.g:1310:5: (lv_ImageTurnLeft_15_0= ruleEString )
                    {
                    // InternalMazeComp.g:1310:5: (lv_ImageTurnLeft_15_0= ruleEString )
                    // InternalMazeComp.g:1311:6: lv_ImageTurnLeft_15_0= ruleEString
                    {

                    						newCompositeNode(grammarAccess.getPumpkinBomberAccess().getImageTurnLeftEStringParserRuleCall_11_1_0());
                    					
                    pushFollow(FOLLOW_39);
                    lv_ImageTurnLeft_15_0=ruleEString();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getPumpkinBomberRule());
                    						}
                    						set(
                    							current,
                    							"ImageTurnLeft",
                    							lv_ImageTurnLeft_15_0,
                    							"main.game.maze.MazeComp.EString");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeComp.g:1329:3: (otherlv_16= 'ImageTurnRight' ( (lv_ImageTurnRight_17_0= ruleEString ) ) )?
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==26) ) {
                alt36=1;
            }
            switch (alt36) {
                case 1 :
                    // InternalMazeComp.g:1330:4: otherlv_16= 'ImageTurnRight' ( (lv_ImageTurnRight_17_0= ruleEString ) )
                    {
                    otherlv_16=(Token)match(input,26,FOLLOW_15); 

                    				newLeafNode(otherlv_16, grammarAccess.getPumpkinBomberAccess().getImageTurnRightKeyword_12_0());
                    			
                    // InternalMazeComp.g:1334:4: ( (lv_ImageTurnRight_17_0= ruleEString ) )
                    // InternalMazeComp.g:1335:5: (lv_ImageTurnRight_17_0= ruleEString )
                    {
                    // InternalMazeComp.g:1335:5: (lv_ImageTurnRight_17_0= ruleEString )
                    // InternalMazeComp.g:1336:6: lv_ImageTurnRight_17_0= ruleEString
                    {

                    						newCompositeNode(grammarAccess.getPumpkinBomberAccess().getImageTurnRightEStringParserRuleCall_12_1_0());
                    					
                    pushFollow(FOLLOW_40);
                    lv_ImageTurnRight_17_0=ruleEString();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getPumpkinBomberRule());
                    						}
                    						set(
                    							current,
                    							"ImageTurnRight",
                    							lv_ImageTurnRight_17_0,
                    							"main.game.maze.MazeComp.EString");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeComp.g:1354:3: (otherlv_18= 'ImageTurnUp' ( (lv_ImageTurnUp_19_0= ruleEString ) ) )?
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( (LA37_0==27) ) {
                alt37=1;
            }
            switch (alt37) {
                case 1 :
                    // InternalMazeComp.g:1355:4: otherlv_18= 'ImageTurnUp' ( (lv_ImageTurnUp_19_0= ruleEString ) )
                    {
                    otherlv_18=(Token)match(input,27,FOLLOW_15); 

                    				newLeafNode(otherlv_18, grammarAccess.getPumpkinBomberAccess().getImageTurnUpKeyword_13_0());
                    			
                    // InternalMazeComp.g:1359:4: ( (lv_ImageTurnUp_19_0= ruleEString ) )
                    // InternalMazeComp.g:1360:5: (lv_ImageTurnUp_19_0= ruleEString )
                    {
                    // InternalMazeComp.g:1360:5: (lv_ImageTurnUp_19_0= ruleEString )
                    // InternalMazeComp.g:1361:6: lv_ImageTurnUp_19_0= ruleEString
                    {

                    						newCompositeNode(grammarAccess.getPumpkinBomberAccess().getImageTurnUpEStringParserRuleCall_13_1_0());
                    					
                    pushFollow(FOLLOW_41);
                    lv_ImageTurnUp_19_0=ruleEString();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getPumpkinBomberRule());
                    						}
                    						set(
                    							current,
                    							"ImageTurnUp",
                    							lv_ImageTurnUp_19_0,
                    							"main.game.maze.MazeComp.EString");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeComp.g:1379:3: (otherlv_20= 'ImageTurnDown' ( (lv_ImageTurnDown_21_0= ruleEString ) ) )?
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==28) ) {
                alt38=1;
            }
            switch (alt38) {
                case 1 :
                    // InternalMazeComp.g:1380:4: otherlv_20= 'ImageTurnDown' ( (lv_ImageTurnDown_21_0= ruleEString ) )
                    {
                    otherlv_20=(Token)match(input,28,FOLLOW_15); 

                    				newLeafNode(otherlv_20, grammarAccess.getPumpkinBomberAccess().getImageTurnDownKeyword_14_0());
                    			
                    // InternalMazeComp.g:1384:4: ( (lv_ImageTurnDown_21_0= ruleEString ) )
                    // InternalMazeComp.g:1385:5: (lv_ImageTurnDown_21_0= ruleEString )
                    {
                    // InternalMazeComp.g:1385:5: (lv_ImageTurnDown_21_0= ruleEString )
                    // InternalMazeComp.g:1386:6: lv_ImageTurnDown_21_0= ruleEString
                    {

                    						newCompositeNode(grammarAccess.getPumpkinBomberAccess().getImageTurnDownEStringParserRuleCall_14_1_0());
                    					
                    pushFollow(FOLLOW_42);
                    lv_ImageTurnDown_21_0=ruleEString();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getPumpkinBomberRule());
                    						}
                    						set(
                    							current,
                    							"ImageTurnDown",
                    							lv_ImageTurnDown_21_0,
                    							"main.game.maze.MazeComp.EString");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeComp.g:1404:3: (otherlv_22= 'behavior' ( (lv_behavior_23_0= ruleBehaviorType ) ) )?
            int alt39=2;
            int LA39_0 = input.LA(1);

            if ( (LA39_0==29) ) {
                alt39=1;
            }
            switch (alt39) {
                case 1 :
                    // InternalMazeComp.g:1405:4: otherlv_22= 'behavior' ( (lv_behavior_23_0= ruleBehaviorType ) )
                    {
                    otherlv_22=(Token)match(input,29,FOLLOW_28); 

                    				newLeafNode(otherlv_22, grammarAccess.getPumpkinBomberAccess().getBehaviorKeyword_15_0());
                    			
                    // InternalMazeComp.g:1409:4: ( (lv_behavior_23_0= ruleBehaviorType ) )
                    // InternalMazeComp.g:1410:5: (lv_behavior_23_0= ruleBehaviorType )
                    {
                    // InternalMazeComp.g:1410:5: (lv_behavior_23_0= ruleBehaviorType )
                    // InternalMazeComp.g:1411:6: lv_behavior_23_0= ruleBehaviorType
                    {

                    						newCompositeNode(grammarAccess.getPumpkinBomberAccess().getBehaviorBehaviorTypeEnumRuleCall_15_1_0());
                    					
                    pushFollow(FOLLOW_43);
                    lv_behavior_23_0=ruleBehaviorType();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getPumpkinBomberRule());
                    						}
                    						set(
                    							current,
                    							"behavior",
                    							lv_behavior_23_0,
                    							"main.game.maze.MazeComp.BehaviorType");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_24=(Token)match(input,40,FOLLOW_21); 

            			newLeafNode(otherlv_24, grammarAccess.getPumpkinBomberAccess().getAttackRangeKeyword_16());
            		
            // InternalMazeComp.g:1433:3: ( (lv_attackRange_25_0= ruleEDouble ) )
            // InternalMazeComp.g:1434:4: (lv_attackRange_25_0= ruleEDouble )
            {
            // InternalMazeComp.g:1434:4: (lv_attackRange_25_0= ruleEDouble )
            // InternalMazeComp.g:1435:5: lv_attackRange_25_0= ruleEDouble
            {

            					newCompositeNode(grammarAccess.getPumpkinBomberAccess().getAttackRangeEDoubleParserRuleCall_17_0());
            				
            pushFollow(FOLLOW_44);
            lv_attackRange_25_0=ruleEDouble();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getPumpkinBomberRule());
            					}
            					set(
            						current,
            						"attackRange",
            						lv_attackRange_25_0,
            						"main.game.maze.MazeComp.EDouble");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_26=(Token)match(input,41,FOLLOW_12); 

            			newLeafNode(otherlv_26, grammarAccess.getPumpkinBomberAccess().getAttackCooldownMsKeyword_18());
            		
            // InternalMazeComp.g:1456:3: ( (lv_attackCooldownMs_27_0= ruleEInt ) )
            // InternalMazeComp.g:1457:4: (lv_attackCooldownMs_27_0= ruleEInt )
            {
            // InternalMazeComp.g:1457:4: (lv_attackCooldownMs_27_0= ruleEInt )
            // InternalMazeComp.g:1458:5: lv_attackCooldownMs_27_0= ruleEInt
            {

            					newCompositeNode(grammarAccess.getPumpkinBomberAccess().getAttackCooldownMsEIntParserRuleCall_19_0());
            				
            pushFollow(FOLLOW_29);
            lv_attackCooldownMs_27_0=ruleEInt();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getPumpkinBomberRule());
            					}
            					set(
            						current,
            						"attackCooldownMs",
            						lv_attackCooldownMs_27_0,
            						"main.game.maze.MazeComp.EInt");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_28=(Token)match(input,30,FOLLOW_12); 

            			newLeafNode(otherlv_28, grammarAccess.getPumpkinBomberAccess().getAttackDamageKeyword_20());
            		
            // InternalMazeComp.g:1479:3: ( (lv_attackDamage_29_0= ruleEInt ) )
            // InternalMazeComp.g:1480:4: (lv_attackDamage_29_0= ruleEInt )
            {
            // InternalMazeComp.g:1480:4: (lv_attackDamage_29_0= ruleEInt )
            // InternalMazeComp.g:1481:5: lv_attackDamage_29_0= ruleEInt
            {

            					newCompositeNode(grammarAccess.getPumpkinBomberAccess().getAttackDamageEIntParserRuleCall_21_0());
            				
            pushFollow(FOLLOW_45);
            lv_attackDamage_29_0=ruleEInt();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getPumpkinBomberRule());
            					}
            					set(
            						current,
            						"attackDamage",
            						lv_attackDamage_29_0,
            						"main.game.maze.MazeComp.EInt");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_30=(Token)match(input,42,FOLLOW_21); 

            			newLeafNode(otherlv_30, grammarAccess.getPumpkinBomberAccess().getProjectileSpeedKeyword_22());
            		
            // InternalMazeComp.g:1502:3: ( (lv_projectileSpeed_31_0= ruleEDouble ) )
            // InternalMazeComp.g:1503:4: (lv_projectileSpeed_31_0= ruleEDouble )
            {
            // InternalMazeComp.g:1503:4: (lv_projectileSpeed_31_0= ruleEDouble )
            // InternalMazeComp.g:1504:5: lv_projectileSpeed_31_0= ruleEDouble
            {

            					newCompositeNode(grammarAccess.getPumpkinBomberAccess().getProjectileSpeedEDoubleParserRuleCall_23_0());
            				
            pushFollow(FOLLOW_46);
            lv_projectileSpeed_31_0=ruleEDouble();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getPumpkinBomberRule());
            					}
            					set(
            						current,
            						"projectileSpeed",
            						lv_projectileSpeed_31_0,
            						"main.game.maze.MazeComp.EDouble");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_32=(Token)match(input,43,FOLLOW_47); 

            			newLeafNode(otherlv_32, grammarAccess.getPumpkinBomberAccess().getProjectileTypeKeyword_24());
            		
            // InternalMazeComp.g:1525:3: ( (lv_projectileType_33_0= ruleProjectileType ) )
            // InternalMazeComp.g:1526:4: (lv_projectileType_33_0= ruleProjectileType )
            {
            // InternalMazeComp.g:1526:4: (lv_projectileType_33_0= ruleProjectileType )
            // InternalMazeComp.g:1527:5: lv_projectileType_33_0= ruleProjectileType
            {

            					newCompositeNode(grammarAccess.getPumpkinBomberAccess().getProjectileTypeProjectileTypeEnumRuleCall_25_0());
            				
            pushFollow(FOLLOW_48);
            lv_projectileType_33_0=ruleProjectileType();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getPumpkinBomberRule());
            					}
            					set(
            						current,
            						"projectileType",
            						lv_projectileType_33_0,
            						"main.game.maze.MazeComp.ProjectileType");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_34=(Token)match(input,44,FOLLOW_21); 

            			newLeafNode(otherlv_34, grammarAccess.getPumpkinBomberAccess().getSplashRadiusKeyword_26());
            		
            // InternalMazeComp.g:1548:3: ( (lv_splashRadius_35_0= ruleEDouble ) )
            // InternalMazeComp.g:1549:4: (lv_splashRadius_35_0= ruleEDouble )
            {
            // InternalMazeComp.g:1549:4: (lv_splashRadius_35_0= ruleEDouble )
            // InternalMazeComp.g:1550:5: lv_splashRadius_35_0= ruleEDouble
            {

            					newCompositeNode(grammarAccess.getPumpkinBomberAccess().getSplashRadiusEDoubleParserRuleCall_27_0());
            				
            pushFollow(FOLLOW_49);
            lv_splashRadius_35_0=ruleEDouble();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getPumpkinBomberRule());
            					}
            					set(
            						current,
            						"splashRadius",
            						lv_splashRadius_35_0,
            						"main.game.maze.MazeComp.EDouble");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_36=(Token)match(input,45,FOLLOW_21); 

            			newLeafNode(otherlv_36, grammarAccess.getPumpkinBomberAccess().getArcHeightKeyword_28());
            		
            // InternalMazeComp.g:1571:3: ( (lv_arcHeight_37_0= ruleEDouble ) )
            // InternalMazeComp.g:1572:4: (lv_arcHeight_37_0= ruleEDouble )
            {
            // InternalMazeComp.g:1572:4: (lv_arcHeight_37_0= ruleEDouble )
            // InternalMazeComp.g:1573:5: lv_arcHeight_37_0= ruleEDouble
            {

            					newCompositeNode(grammarAccess.getPumpkinBomberAccess().getArcHeightEDoubleParserRuleCall_29_0());
            				
            pushFollow(FOLLOW_50);
            lv_arcHeight_37_0=ruleEDouble();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getPumpkinBomberRule());
            					}
            					set(
            						current,
            						"arcHeight",
            						lv_arcHeight_37_0,
            						"main.game.maze.MazeComp.EDouble");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalMazeComp.g:1590:3: (otherlv_38= 'projectileImage' ( (lv_projectileImage_39_0= ruleEString ) ) )?
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( (LA40_0==46) ) {
                alt40=1;
            }
            switch (alt40) {
                case 1 :
                    // InternalMazeComp.g:1591:4: otherlv_38= 'projectileImage' ( (lv_projectileImage_39_0= ruleEString ) )
                    {
                    otherlv_38=(Token)match(input,46,FOLLOW_15); 

                    				newLeafNode(otherlv_38, grammarAccess.getPumpkinBomberAccess().getProjectileImageKeyword_30_0());
                    			
                    // InternalMazeComp.g:1595:4: ( (lv_projectileImage_39_0= ruleEString ) )
                    // InternalMazeComp.g:1596:5: (lv_projectileImage_39_0= ruleEString )
                    {
                    // InternalMazeComp.g:1596:5: (lv_projectileImage_39_0= ruleEString )
                    // InternalMazeComp.g:1597:6: lv_projectileImage_39_0= ruleEString
                    {

                    						newCompositeNode(grammarAccess.getPumpkinBomberAccess().getProjectileImageEStringParserRuleCall_30_1_0());
                    					
                    pushFollow(FOLLOW_51);
                    lv_projectileImage_39_0=ruleEString();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getPumpkinBomberRule());
                    						}
                    						set(
                    							current,
                    							"projectileImage",
                    							lv_projectileImage_39_0,
                    							"main.game.maze.MazeComp.EString");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeComp.g:1615:3: (otherlv_40= 'explosionImage' ( (lv_explosionImage_41_0= ruleEString ) ) )?
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( (LA41_0==47) ) {
                alt41=1;
            }
            switch (alt41) {
                case 1 :
                    // InternalMazeComp.g:1616:4: otherlv_40= 'explosionImage' ( (lv_explosionImage_41_0= ruleEString ) )
                    {
                    otherlv_40=(Token)match(input,47,FOLLOW_15); 

                    				newLeafNode(otherlv_40, grammarAccess.getPumpkinBomberAccess().getExplosionImageKeyword_31_0());
                    			
                    // InternalMazeComp.g:1620:4: ( (lv_explosionImage_41_0= ruleEString ) )
                    // InternalMazeComp.g:1621:5: (lv_explosionImage_41_0= ruleEString )
                    {
                    // InternalMazeComp.g:1621:5: (lv_explosionImage_41_0= ruleEString )
                    // InternalMazeComp.g:1622:6: lv_explosionImage_41_0= ruleEString
                    {

                    						newCompositeNode(grammarAccess.getPumpkinBomberAccess().getExplosionImageEStringParserRuleCall_31_1_0());
                    					
                    pushFollow(FOLLOW_52);
                    lv_explosionImage_41_0=ruleEString();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getPumpkinBomberRule());
                    						}
                    						set(
                    							current,
                    							"explosionImage",
                    							lv_explosionImage_41_0,
                    							"main.game.maze.MazeComp.EString");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeComp.g:1640:3: (otherlv_42= 'explosionSound' ( (lv_explosionSound_43_0= ruleEString ) ) )?
            int alt42=2;
            int LA42_0 = input.LA(1);

            if ( (LA42_0==48) ) {
                alt42=1;
            }
            switch (alt42) {
                case 1 :
                    // InternalMazeComp.g:1641:4: otherlv_42= 'explosionSound' ( (lv_explosionSound_43_0= ruleEString ) )
                    {
                    otherlv_42=(Token)match(input,48,FOLLOW_15); 

                    				newLeafNode(otherlv_42, grammarAccess.getPumpkinBomberAccess().getExplosionSoundKeyword_32_0());
                    			
                    // InternalMazeComp.g:1645:4: ( (lv_explosionSound_43_0= ruleEString ) )
                    // InternalMazeComp.g:1646:5: (lv_explosionSound_43_0= ruleEString )
                    {
                    // InternalMazeComp.g:1646:5: (lv_explosionSound_43_0= ruleEString )
                    // InternalMazeComp.g:1647:6: lv_explosionSound_43_0= ruleEString
                    {

                    						newCompositeNode(grammarAccess.getPumpkinBomberAccess().getExplosionSoundEStringParserRuleCall_32_1_0());
                    					
                    pushFollow(FOLLOW_53);
                    lv_explosionSound_43_0=ruleEString();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getPumpkinBomberRule());
                    						}
                    						set(
                    							current,
                    							"explosionSound",
                    							lv_explosionSound_43_0,
                    							"main.game.maze.MazeComp.EString");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMazeComp.g:1665:3: (otherlv_44= 'throwSound' ( (lv_throwSound_45_0= ruleEString ) ) )?
            int alt43=2;
            int LA43_0 = input.LA(1);

            if ( (LA43_0==49) ) {
                alt43=1;
            }
            switch (alt43) {
                case 1 :
                    // InternalMazeComp.g:1666:4: otherlv_44= 'throwSound' ( (lv_throwSound_45_0= ruleEString ) )
                    {
                    otherlv_44=(Token)match(input,49,FOLLOW_15); 

                    				newLeafNode(otherlv_44, grammarAccess.getPumpkinBomberAccess().getThrowSoundKeyword_33_0());
                    			
                    // InternalMazeComp.g:1670:4: ( (lv_throwSound_45_0= ruleEString ) )
                    // InternalMazeComp.g:1671:5: (lv_throwSound_45_0= ruleEString )
                    {
                    // InternalMazeComp.g:1671:5: (lv_throwSound_45_0= ruleEString )
                    // InternalMazeComp.g:1672:6: lv_throwSound_45_0= ruleEString
                    {

                    						newCompositeNode(grammarAccess.getPumpkinBomberAccess().getThrowSoundEStringParserRuleCall_33_1_0());
                    					
                    pushFollow(FOLLOW_34);
                    lv_throwSound_45_0=ruleEString();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getPumpkinBomberRule());
                    						}
                    						set(
                    							current,
                    							"throwSound",
                    							lv_throwSound_45_0,
                    							"main.game.maze.MazeComp.EString");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_46=(Token)match(input,35,FOLLOW_2); 

            			newLeafNode(otherlv_46, grammarAccess.getPumpkinBomberAccess().getRightCurlyBracketKeyword_34());
            		

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
    // $ANTLR end "rulePumpkinBomber"


    // $ANTLR start "entryRuleLootTable"
    // InternalMazeComp.g:1698:1: entryRuleLootTable returns [EObject current=null] : iv_ruleLootTable= ruleLootTable EOF ;
    public final EObject entryRuleLootTable() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLootTable = null;


        try {
            // InternalMazeComp.g:1698:50: (iv_ruleLootTable= ruleLootTable EOF )
            // InternalMazeComp.g:1699:2: iv_ruleLootTable= ruleLootTable EOF
            {
             newCompositeNode(grammarAccess.getLootTableRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleLootTable=ruleLootTable();

            state._fsp--;

             current =iv_ruleLootTable; 
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
    // $ANTLR end "entryRuleLootTable"


    // $ANTLR start "ruleLootTable"
    // InternalMazeComp.g:1705:1: ruleLootTable returns [EObject current=null] : (otherlv_0= 'LootTable' otherlv_1= '{' otherlv_2= 'weightCapacity' otherlv_3= '{' ( (lv_weightCapacity_4_0= ruleEInt ) ) (otherlv_5= ',' ( (lv_weightCapacity_6_0= ruleEInt ) ) )* otherlv_7= '}' (otherlv_8= 'items' otherlv_9= '{' ( (lv_items_10_0= ruleLootItem ) ) (otherlv_11= ',' ( (lv_items_12_0= ruleLootItem ) ) )* otherlv_13= '}' )? otherlv_14= '}' ) ;
    public final EObject ruleLootTable() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Token otherlv_8=null;
        Token otherlv_9=null;
        Token otherlv_11=null;
        Token otherlv_13=null;
        Token otherlv_14=null;
        AntlrDatatypeRuleToken lv_weightCapacity_4_0 = null;

        AntlrDatatypeRuleToken lv_weightCapacity_6_0 = null;

        EObject lv_items_10_0 = null;

        EObject lv_items_12_0 = null;



        	enterRule();

        try {
            // InternalMazeComp.g:1711:2: ( (otherlv_0= 'LootTable' otherlv_1= '{' otherlv_2= 'weightCapacity' otherlv_3= '{' ( (lv_weightCapacity_4_0= ruleEInt ) ) (otherlv_5= ',' ( (lv_weightCapacity_6_0= ruleEInt ) ) )* otherlv_7= '}' (otherlv_8= 'items' otherlv_9= '{' ( (lv_items_10_0= ruleLootItem ) ) (otherlv_11= ',' ( (lv_items_12_0= ruleLootItem ) ) )* otherlv_13= '}' )? otherlv_14= '}' ) )
            // InternalMazeComp.g:1712:2: (otherlv_0= 'LootTable' otherlv_1= '{' otherlv_2= 'weightCapacity' otherlv_3= '{' ( (lv_weightCapacity_4_0= ruleEInt ) ) (otherlv_5= ',' ( (lv_weightCapacity_6_0= ruleEInt ) ) )* otherlv_7= '}' (otherlv_8= 'items' otherlv_9= '{' ( (lv_items_10_0= ruleLootItem ) ) (otherlv_11= ',' ( (lv_items_12_0= ruleLootItem ) ) )* otherlv_13= '}' )? otherlv_14= '}' )
            {
            // InternalMazeComp.g:1712:2: (otherlv_0= 'LootTable' otherlv_1= '{' otherlv_2= 'weightCapacity' otherlv_3= '{' ( (lv_weightCapacity_4_0= ruleEInt ) ) (otherlv_5= ',' ( (lv_weightCapacity_6_0= ruleEInt ) ) )* otherlv_7= '}' (otherlv_8= 'items' otherlv_9= '{' ( (lv_items_10_0= ruleLootItem ) ) (otherlv_11= ',' ( (lv_items_12_0= ruleLootItem ) ) )* otherlv_13= '}' )? otherlv_14= '}' )
            // InternalMazeComp.g:1713:3: otherlv_0= 'LootTable' otherlv_1= '{' otherlv_2= 'weightCapacity' otherlv_3= '{' ( (lv_weightCapacity_4_0= ruleEInt ) ) (otherlv_5= ',' ( (lv_weightCapacity_6_0= ruleEInt ) ) )* otherlv_7= '}' (otherlv_8= 'items' otherlv_9= '{' ( (lv_items_10_0= ruleLootItem ) ) (otherlv_11= ',' ( (lv_items_12_0= ruleLootItem ) ) )* otherlv_13= '}' )? otherlv_14= '}'
            {
            otherlv_0=(Token)match(input,50,FOLLOW_13); 

            			newLeafNode(otherlv_0, grammarAccess.getLootTableAccess().getLootTableKeyword_0());
            		
            otherlv_1=(Token)match(input,18,FOLLOW_54); 

            			newLeafNode(otherlv_1, grammarAccess.getLootTableAccess().getLeftCurlyBracketKeyword_1());
            		
            otherlv_2=(Token)match(input,51,FOLLOW_13); 

            			newLeafNode(otherlv_2, grammarAccess.getLootTableAccess().getWeightCapacityKeyword_2());
            		
            otherlv_3=(Token)match(input,18,FOLLOW_12); 

            			newLeafNode(otherlv_3, grammarAccess.getLootTableAccess().getLeftCurlyBracketKeyword_3());
            		
            // InternalMazeComp.g:1729:3: ( (lv_weightCapacity_4_0= ruleEInt ) )
            // InternalMazeComp.g:1730:4: (lv_weightCapacity_4_0= ruleEInt )
            {
            // InternalMazeComp.g:1730:4: (lv_weightCapacity_4_0= ruleEInt )
            // InternalMazeComp.g:1731:5: lv_weightCapacity_4_0= ruleEInt
            {

            					newCompositeNode(grammarAccess.getLootTableAccess().getWeightCapacityEIntParserRuleCall_4_0());
            				
            pushFollow(FOLLOW_55);
            lv_weightCapacity_4_0=ruleEInt();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getLootTableRule());
            					}
            					add(
            						current,
            						"weightCapacity",
            						lv_weightCapacity_4_0,
            						"main.game.maze.MazeComp.EInt");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalMazeComp.g:1748:3: (otherlv_5= ',' ( (lv_weightCapacity_6_0= ruleEInt ) ) )*
            loop44:
            do {
                int alt44=2;
                int LA44_0 = input.LA(1);

                if ( (LA44_0==52) ) {
                    alt44=1;
                }


                switch (alt44) {
            	case 1 :
            	    // InternalMazeComp.g:1749:4: otherlv_5= ',' ( (lv_weightCapacity_6_0= ruleEInt ) )
            	    {
            	    otherlv_5=(Token)match(input,52,FOLLOW_12); 

            	    				newLeafNode(otherlv_5, grammarAccess.getLootTableAccess().getCommaKeyword_5_0());
            	    			
            	    // InternalMazeComp.g:1753:4: ( (lv_weightCapacity_6_0= ruleEInt ) )
            	    // InternalMazeComp.g:1754:5: (lv_weightCapacity_6_0= ruleEInt )
            	    {
            	    // InternalMazeComp.g:1754:5: (lv_weightCapacity_6_0= ruleEInt )
            	    // InternalMazeComp.g:1755:6: lv_weightCapacity_6_0= ruleEInt
            	    {

            	    						newCompositeNode(grammarAccess.getLootTableAccess().getWeightCapacityEIntParserRuleCall_5_1_0());
            	    					
            	    pushFollow(FOLLOW_55);
            	    lv_weightCapacity_6_0=ruleEInt();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getLootTableRule());
            	    						}
            	    						add(
            	    							current,
            	    							"weightCapacity",
            	    							lv_weightCapacity_6_0,
            	    							"main.game.maze.MazeComp.EInt");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop44;
                }
            } while (true);

            otherlv_7=(Token)match(input,35,FOLLOW_56); 

            			newLeafNode(otherlv_7, grammarAccess.getLootTableAccess().getRightCurlyBracketKeyword_6());
            		
            // InternalMazeComp.g:1777:3: (otherlv_8= 'items' otherlv_9= '{' ( (lv_items_10_0= ruleLootItem ) ) (otherlv_11= ',' ( (lv_items_12_0= ruleLootItem ) ) )* otherlv_13= '}' )?
            int alt46=2;
            int LA46_0 = input.LA(1);

            if ( (LA46_0==53) ) {
                alt46=1;
            }
            switch (alt46) {
                case 1 :
                    // InternalMazeComp.g:1778:4: otherlv_8= 'items' otherlv_9= '{' ( (lv_items_10_0= ruleLootItem ) ) (otherlv_11= ',' ( (lv_items_12_0= ruleLootItem ) ) )* otherlv_13= '}'
                    {
                    otherlv_8=(Token)match(input,53,FOLLOW_13); 

                    				newLeafNode(otherlv_8, grammarAccess.getLootTableAccess().getItemsKeyword_7_0());
                    			
                    otherlv_9=(Token)match(input,18,FOLLOW_57); 

                    				newLeafNode(otherlv_9, grammarAccess.getLootTableAccess().getLeftCurlyBracketKeyword_7_1());
                    			
                    // InternalMazeComp.g:1786:4: ( (lv_items_10_0= ruleLootItem ) )
                    // InternalMazeComp.g:1787:5: (lv_items_10_0= ruleLootItem )
                    {
                    // InternalMazeComp.g:1787:5: (lv_items_10_0= ruleLootItem )
                    // InternalMazeComp.g:1788:6: lv_items_10_0= ruleLootItem
                    {

                    						newCompositeNode(grammarAccess.getLootTableAccess().getItemsLootItemParserRuleCall_7_2_0());
                    					
                    pushFollow(FOLLOW_55);
                    lv_items_10_0=ruleLootItem();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getLootTableRule());
                    						}
                    						add(
                    							current,
                    							"items",
                    							lv_items_10_0,
                    							"main.game.maze.MazeComp.LootItem");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalMazeComp.g:1805:4: (otherlv_11= ',' ( (lv_items_12_0= ruleLootItem ) ) )*
                    loop45:
                    do {
                        int alt45=2;
                        int LA45_0 = input.LA(1);

                        if ( (LA45_0==52) ) {
                            alt45=1;
                        }


                        switch (alt45) {
                    	case 1 :
                    	    // InternalMazeComp.g:1806:5: otherlv_11= ',' ( (lv_items_12_0= ruleLootItem ) )
                    	    {
                    	    otherlv_11=(Token)match(input,52,FOLLOW_57); 

                    	    					newLeafNode(otherlv_11, grammarAccess.getLootTableAccess().getCommaKeyword_7_3_0());
                    	    				
                    	    // InternalMazeComp.g:1810:5: ( (lv_items_12_0= ruleLootItem ) )
                    	    // InternalMazeComp.g:1811:6: (lv_items_12_0= ruleLootItem )
                    	    {
                    	    // InternalMazeComp.g:1811:6: (lv_items_12_0= ruleLootItem )
                    	    // InternalMazeComp.g:1812:7: lv_items_12_0= ruleLootItem
                    	    {

                    	    							newCompositeNode(grammarAccess.getLootTableAccess().getItemsLootItemParserRuleCall_7_3_1_0());
                    	    						
                    	    pushFollow(FOLLOW_55);
                    	    lv_items_12_0=ruleLootItem();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getLootTableRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"items",
                    	    								lv_items_12_0,
                    	    								"main.game.maze.MazeComp.LootItem");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop45;
                        }
                    } while (true);

                    otherlv_13=(Token)match(input,35,FOLLOW_34); 

                    				newLeafNode(otherlv_13, grammarAccess.getLootTableAccess().getRightCurlyBracketKeyword_7_4());
                    			

                    }
                    break;

            }

            otherlv_14=(Token)match(input,35,FOLLOW_2); 

            			newLeafNode(otherlv_14, grammarAccess.getLootTableAccess().getRightCurlyBracketKeyword_8());
            		

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
    // $ANTLR end "ruleLootTable"


    // $ANTLR start "entryRuleLootItem"
    // InternalMazeComp.g:1843:1: entryRuleLootItem returns [EObject current=null] : iv_ruleLootItem= ruleLootItem EOF ;
    public final EObject entryRuleLootItem() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLootItem = null;


        try {
            // InternalMazeComp.g:1843:49: (iv_ruleLootItem= ruleLootItem EOF )
            // InternalMazeComp.g:1844:2: iv_ruleLootItem= ruleLootItem EOF
            {
             newCompositeNode(grammarAccess.getLootItemRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleLootItem=ruleLootItem();

            state._fsp--;

             current =iv_ruleLootItem; 
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
    // $ANTLR end "entryRuleLootItem"


    // $ANTLR start "ruleLootItem"
    // InternalMazeComp.g:1850:1: ruleLootItem returns [EObject current=null] : (otherlv_0= 'LootItem' ( (lv_name_1_0= ruleEString ) ) otherlv_2= '{' otherlv_3= 'type' ( (lv_type_4_0= ruleLootItemType ) ) otherlv_5= 'value' ( (lv_value_6_0= ruleEInt ) ) otherlv_7= 'weight' otherlv_8= '{' ( (lv_weight_9_0= ruleEInt ) ) (otherlv_10= ',' ( (lv_weight_11_0= ruleEInt ) ) )* otherlv_12= '}' (otherlv_13= 'graphicBase' ( (lv_graphicBase_14_0= ruleEString ) ) )? otherlv_15= '}' ) ;
    public final EObject ruleLootItem() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Token otherlv_8=null;
        Token otherlv_10=null;
        Token otherlv_12=null;
        Token otherlv_13=null;
        Token otherlv_15=null;
        AntlrDatatypeRuleToken lv_name_1_0 = null;

        Enumerator lv_type_4_0 = null;

        AntlrDatatypeRuleToken lv_value_6_0 = null;

        AntlrDatatypeRuleToken lv_weight_9_0 = null;

        AntlrDatatypeRuleToken lv_weight_11_0 = null;

        AntlrDatatypeRuleToken lv_graphicBase_14_0 = null;



        	enterRule();

        try {
            // InternalMazeComp.g:1856:2: ( (otherlv_0= 'LootItem' ( (lv_name_1_0= ruleEString ) ) otherlv_2= '{' otherlv_3= 'type' ( (lv_type_4_0= ruleLootItemType ) ) otherlv_5= 'value' ( (lv_value_6_0= ruleEInt ) ) otherlv_7= 'weight' otherlv_8= '{' ( (lv_weight_9_0= ruleEInt ) ) (otherlv_10= ',' ( (lv_weight_11_0= ruleEInt ) ) )* otherlv_12= '}' (otherlv_13= 'graphicBase' ( (lv_graphicBase_14_0= ruleEString ) ) )? otherlv_15= '}' ) )
            // InternalMazeComp.g:1857:2: (otherlv_0= 'LootItem' ( (lv_name_1_0= ruleEString ) ) otherlv_2= '{' otherlv_3= 'type' ( (lv_type_4_0= ruleLootItemType ) ) otherlv_5= 'value' ( (lv_value_6_0= ruleEInt ) ) otherlv_7= 'weight' otherlv_8= '{' ( (lv_weight_9_0= ruleEInt ) ) (otherlv_10= ',' ( (lv_weight_11_0= ruleEInt ) ) )* otherlv_12= '}' (otherlv_13= 'graphicBase' ( (lv_graphicBase_14_0= ruleEString ) ) )? otherlv_15= '}' )
            {
            // InternalMazeComp.g:1857:2: (otherlv_0= 'LootItem' ( (lv_name_1_0= ruleEString ) ) otherlv_2= '{' otherlv_3= 'type' ( (lv_type_4_0= ruleLootItemType ) ) otherlv_5= 'value' ( (lv_value_6_0= ruleEInt ) ) otherlv_7= 'weight' otherlv_8= '{' ( (lv_weight_9_0= ruleEInt ) ) (otherlv_10= ',' ( (lv_weight_11_0= ruleEInt ) ) )* otherlv_12= '}' (otherlv_13= 'graphicBase' ( (lv_graphicBase_14_0= ruleEString ) ) )? otherlv_15= '}' )
            // InternalMazeComp.g:1858:3: otherlv_0= 'LootItem' ( (lv_name_1_0= ruleEString ) ) otherlv_2= '{' otherlv_3= 'type' ( (lv_type_4_0= ruleLootItemType ) ) otherlv_5= 'value' ( (lv_value_6_0= ruleEInt ) ) otherlv_7= 'weight' otherlv_8= '{' ( (lv_weight_9_0= ruleEInt ) ) (otherlv_10= ',' ( (lv_weight_11_0= ruleEInt ) ) )* otherlv_12= '}' (otherlv_13= 'graphicBase' ( (lv_graphicBase_14_0= ruleEString ) ) )? otherlv_15= '}'
            {
            otherlv_0=(Token)match(input,54,FOLLOW_15); 

            			newLeafNode(otherlv_0, grammarAccess.getLootItemAccess().getLootItemKeyword_0());
            		
            // InternalMazeComp.g:1862:3: ( (lv_name_1_0= ruleEString ) )
            // InternalMazeComp.g:1863:4: (lv_name_1_0= ruleEString )
            {
            // InternalMazeComp.g:1863:4: (lv_name_1_0= ruleEString )
            // InternalMazeComp.g:1864:5: lv_name_1_0= ruleEString
            {

            					newCompositeNode(grammarAccess.getLootItemAccess().getNameEStringParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_13);
            lv_name_1_0=ruleEString();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getLootItemRule());
            					}
            					set(
            						current,
            						"name",
            						lv_name_1_0,
            						"main.game.maze.MazeComp.EString");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_2=(Token)match(input,18,FOLLOW_58); 

            			newLeafNode(otherlv_2, grammarAccess.getLootItemAccess().getLeftCurlyBracketKeyword_2());
            		
            otherlv_3=(Token)match(input,55,FOLLOW_59); 

            			newLeafNode(otherlv_3, grammarAccess.getLootItemAccess().getTypeKeyword_3());
            		
            // InternalMazeComp.g:1889:3: ( (lv_type_4_0= ruleLootItemType ) )
            // InternalMazeComp.g:1890:4: (lv_type_4_0= ruleLootItemType )
            {
            // InternalMazeComp.g:1890:4: (lv_type_4_0= ruleLootItemType )
            // InternalMazeComp.g:1891:5: lv_type_4_0= ruleLootItemType
            {

            					newCompositeNode(grammarAccess.getLootItemAccess().getTypeLootItemTypeEnumRuleCall_4_0());
            				
            pushFollow(FOLLOW_60);
            lv_type_4_0=ruleLootItemType();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getLootItemRule());
            					}
            					set(
            						current,
            						"type",
            						lv_type_4_0,
            						"main.game.maze.MazeComp.LootItemType");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_5=(Token)match(input,56,FOLLOW_12); 

            			newLeafNode(otherlv_5, grammarAccess.getLootItemAccess().getValueKeyword_5());
            		
            // InternalMazeComp.g:1912:3: ( (lv_value_6_0= ruleEInt ) )
            // InternalMazeComp.g:1913:4: (lv_value_6_0= ruleEInt )
            {
            // InternalMazeComp.g:1913:4: (lv_value_6_0= ruleEInt )
            // InternalMazeComp.g:1914:5: lv_value_6_0= ruleEInt
            {

            					newCompositeNode(grammarAccess.getLootItemAccess().getValueEIntParserRuleCall_6_0());
            				
            pushFollow(FOLLOW_61);
            lv_value_6_0=ruleEInt();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getLootItemRule());
            					}
            					set(
            						current,
            						"value",
            						lv_value_6_0,
            						"main.game.maze.MazeComp.EInt");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_7=(Token)match(input,57,FOLLOW_13); 

            			newLeafNode(otherlv_7, grammarAccess.getLootItemAccess().getWeightKeyword_7());
            		
            otherlv_8=(Token)match(input,18,FOLLOW_12); 

            			newLeafNode(otherlv_8, grammarAccess.getLootItemAccess().getLeftCurlyBracketKeyword_8());
            		
            // InternalMazeComp.g:1939:3: ( (lv_weight_9_0= ruleEInt ) )
            // InternalMazeComp.g:1940:4: (lv_weight_9_0= ruleEInt )
            {
            // InternalMazeComp.g:1940:4: (lv_weight_9_0= ruleEInt )
            // InternalMazeComp.g:1941:5: lv_weight_9_0= ruleEInt
            {

            					newCompositeNode(grammarAccess.getLootItemAccess().getWeightEIntParserRuleCall_9_0());
            				
            pushFollow(FOLLOW_55);
            lv_weight_9_0=ruleEInt();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getLootItemRule());
            					}
            					add(
            						current,
            						"weight",
            						lv_weight_9_0,
            						"main.game.maze.MazeComp.EInt");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalMazeComp.g:1958:3: (otherlv_10= ',' ( (lv_weight_11_0= ruleEInt ) ) )*
            loop47:
            do {
                int alt47=2;
                int LA47_0 = input.LA(1);

                if ( (LA47_0==52) ) {
                    alt47=1;
                }


                switch (alt47) {
            	case 1 :
            	    // InternalMazeComp.g:1959:4: otherlv_10= ',' ( (lv_weight_11_0= ruleEInt ) )
            	    {
            	    otherlv_10=(Token)match(input,52,FOLLOW_12); 

            	    				newLeafNode(otherlv_10, grammarAccess.getLootItemAccess().getCommaKeyword_10_0());
            	    			
            	    // InternalMazeComp.g:1963:4: ( (lv_weight_11_0= ruleEInt ) )
            	    // InternalMazeComp.g:1964:5: (lv_weight_11_0= ruleEInt )
            	    {
            	    // InternalMazeComp.g:1964:5: (lv_weight_11_0= ruleEInt )
            	    // InternalMazeComp.g:1965:6: lv_weight_11_0= ruleEInt
            	    {

            	    						newCompositeNode(grammarAccess.getLootItemAccess().getWeightEIntParserRuleCall_10_1_0());
            	    					
            	    pushFollow(FOLLOW_55);
            	    lv_weight_11_0=ruleEInt();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getLootItemRule());
            	    						}
            	    						add(
            	    							current,
            	    							"weight",
            	    							lv_weight_11_0,
            	    							"main.game.maze.MazeComp.EInt");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop47;
                }
            } while (true);

            otherlv_12=(Token)match(input,35,FOLLOW_62); 

            			newLeafNode(otherlv_12, grammarAccess.getLootItemAccess().getRightCurlyBracketKeyword_11());
            		
            // InternalMazeComp.g:1987:3: (otherlv_13= 'graphicBase' ( (lv_graphicBase_14_0= ruleEString ) ) )?
            int alt48=2;
            int LA48_0 = input.LA(1);

            if ( (LA48_0==58) ) {
                alt48=1;
            }
            switch (alt48) {
                case 1 :
                    // InternalMazeComp.g:1988:4: otherlv_13= 'graphicBase' ( (lv_graphicBase_14_0= ruleEString ) )
                    {
                    otherlv_13=(Token)match(input,58,FOLLOW_15); 

                    				newLeafNode(otherlv_13, grammarAccess.getLootItemAccess().getGraphicBaseKeyword_12_0());
                    			
                    // InternalMazeComp.g:1992:4: ( (lv_graphicBase_14_0= ruleEString ) )
                    // InternalMazeComp.g:1993:5: (lv_graphicBase_14_0= ruleEString )
                    {
                    // InternalMazeComp.g:1993:5: (lv_graphicBase_14_0= ruleEString )
                    // InternalMazeComp.g:1994:6: lv_graphicBase_14_0= ruleEString
                    {

                    						newCompositeNode(grammarAccess.getLootItemAccess().getGraphicBaseEStringParserRuleCall_12_1_0());
                    					
                    pushFollow(FOLLOW_34);
                    lv_graphicBase_14_0=ruleEString();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getLootItemRule());
                    						}
                    						set(
                    							current,
                    							"graphicBase",
                    							lv_graphicBase_14_0,
                    							"main.game.maze.MazeComp.EString");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_15=(Token)match(input,35,FOLLOW_2); 

            			newLeafNode(otherlv_15, grammarAccess.getLootItemAccess().getRightCurlyBracketKeyword_13());
            		

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
    // $ANTLR end "ruleLootItem"


    // $ANTLR start "ruleBehaviorType"
    // InternalMazeComp.g:2020:1: ruleBehaviorType returns [Enumerator current=null] : ( (enumLiteral_0= 'PASSIVE' ) | (enumLiteral_1= 'WANDER' ) | (enumLiteral_2= 'AGGRESSIVE' ) ) ;
    public final Enumerator ruleBehaviorType() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalMazeComp.g:2026:2: ( ( (enumLiteral_0= 'PASSIVE' ) | (enumLiteral_1= 'WANDER' ) | (enumLiteral_2= 'AGGRESSIVE' ) ) )
            // InternalMazeComp.g:2027:2: ( (enumLiteral_0= 'PASSIVE' ) | (enumLiteral_1= 'WANDER' ) | (enumLiteral_2= 'AGGRESSIVE' ) )
            {
            // InternalMazeComp.g:2027:2: ( (enumLiteral_0= 'PASSIVE' ) | (enumLiteral_1= 'WANDER' ) | (enumLiteral_2= 'AGGRESSIVE' ) )
            int alt49=3;
            switch ( input.LA(1) ) {
            case 59:
                {
                alt49=1;
                }
                break;
            case 60:
                {
                alt49=2;
                }
                break;
            case 61:
                {
                alt49=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 49, 0, input);

                throw nvae;
            }

            switch (alt49) {
                case 1 :
                    // InternalMazeComp.g:2028:3: (enumLiteral_0= 'PASSIVE' )
                    {
                    // InternalMazeComp.g:2028:3: (enumLiteral_0= 'PASSIVE' )
                    // InternalMazeComp.g:2029:4: enumLiteral_0= 'PASSIVE'
                    {
                    enumLiteral_0=(Token)match(input,59,FOLLOW_2); 

                    				current = grammarAccess.getBehaviorTypeAccess().getPASSIVEEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getBehaviorTypeAccess().getPASSIVEEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeComp.g:2036:3: (enumLiteral_1= 'WANDER' )
                    {
                    // InternalMazeComp.g:2036:3: (enumLiteral_1= 'WANDER' )
                    // InternalMazeComp.g:2037:4: enumLiteral_1= 'WANDER'
                    {
                    enumLiteral_1=(Token)match(input,60,FOLLOW_2); 

                    				current = grammarAccess.getBehaviorTypeAccess().getWANDEREnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getBehaviorTypeAccess().getWANDEREnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalMazeComp.g:2044:3: (enumLiteral_2= 'AGGRESSIVE' )
                    {
                    // InternalMazeComp.g:2044:3: (enumLiteral_2= 'AGGRESSIVE' )
                    // InternalMazeComp.g:2045:4: enumLiteral_2= 'AGGRESSIVE'
                    {
                    enumLiteral_2=(Token)match(input,61,FOLLOW_2); 

                    				current = grammarAccess.getBehaviorTypeAccess().getAGGRESSIVEEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getBehaviorTypeAccess().getAGGRESSIVEEnumLiteralDeclaration_2());
                    			

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
    // $ANTLR end "ruleBehaviorType"


    // $ANTLR start "ruleLootItemType"
    // InternalMazeComp.g:2055:1: ruleLootItemType returns [Enumerator current=null] : ( (enumLiteral_0= 'FOOD' ) | (enumLiteral_1= 'BOMB' ) | (enumLiteral_2= 'TRAP' ) | (enumLiteral_3= 'WEAPON' ) ) ;
    public final Enumerator ruleLootItemType() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;


        	enterRule();

        try {
            // InternalMazeComp.g:2061:2: ( ( (enumLiteral_0= 'FOOD' ) | (enumLiteral_1= 'BOMB' ) | (enumLiteral_2= 'TRAP' ) | (enumLiteral_3= 'WEAPON' ) ) )
            // InternalMazeComp.g:2062:2: ( (enumLiteral_0= 'FOOD' ) | (enumLiteral_1= 'BOMB' ) | (enumLiteral_2= 'TRAP' ) | (enumLiteral_3= 'WEAPON' ) )
            {
            // InternalMazeComp.g:2062:2: ( (enumLiteral_0= 'FOOD' ) | (enumLiteral_1= 'BOMB' ) | (enumLiteral_2= 'TRAP' ) | (enumLiteral_3= 'WEAPON' ) )
            int alt50=4;
            switch ( input.LA(1) ) {
            case 62:
                {
                alt50=1;
                }
                break;
            case 63:
                {
                alt50=2;
                }
                break;
            case 64:
                {
                alt50=3;
                }
                break;
            case 65:
                {
                alt50=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 50, 0, input);

                throw nvae;
            }

            switch (alt50) {
                case 1 :
                    // InternalMazeComp.g:2063:3: (enumLiteral_0= 'FOOD' )
                    {
                    // InternalMazeComp.g:2063:3: (enumLiteral_0= 'FOOD' )
                    // InternalMazeComp.g:2064:4: enumLiteral_0= 'FOOD'
                    {
                    enumLiteral_0=(Token)match(input,62,FOLLOW_2); 

                    				current = grammarAccess.getLootItemTypeAccess().getFOODEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getLootItemTypeAccess().getFOODEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeComp.g:2071:3: (enumLiteral_1= 'BOMB' )
                    {
                    // InternalMazeComp.g:2071:3: (enumLiteral_1= 'BOMB' )
                    // InternalMazeComp.g:2072:4: enumLiteral_1= 'BOMB'
                    {
                    enumLiteral_1=(Token)match(input,63,FOLLOW_2); 

                    				current = grammarAccess.getLootItemTypeAccess().getBOMBEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getLootItemTypeAccess().getBOMBEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalMazeComp.g:2079:3: (enumLiteral_2= 'TRAP' )
                    {
                    // InternalMazeComp.g:2079:3: (enumLiteral_2= 'TRAP' )
                    // InternalMazeComp.g:2080:4: enumLiteral_2= 'TRAP'
                    {
                    enumLiteral_2=(Token)match(input,64,FOLLOW_2); 

                    				current = grammarAccess.getLootItemTypeAccess().getTRAPEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getLootItemTypeAccess().getTRAPEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalMazeComp.g:2087:3: (enumLiteral_3= 'WEAPON' )
                    {
                    // InternalMazeComp.g:2087:3: (enumLiteral_3= 'WEAPON' )
                    // InternalMazeComp.g:2088:4: enumLiteral_3= 'WEAPON'
                    {
                    enumLiteral_3=(Token)match(input,65,FOLLOW_2); 

                    				current = grammarAccess.getLootItemTypeAccess().getWEAPONEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_3, grammarAccess.getLootItemTypeAccess().getWEAPONEnumLiteralDeclaration_3());
                    			

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
    // $ANTLR end "ruleLootItemType"


    // $ANTLR start "ruleProjectileType"
    // InternalMazeComp.g:2098:1: ruleProjectileType returns [Enumerator current=null] : ( (enumLiteral_0= 'STRAIGHT' ) | (enumLiteral_1= 'LOB' ) | (enumLiteral_2= 'BEAM' ) ) ;
    public final Enumerator ruleProjectileType() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalMazeComp.g:2104:2: ( ( (enumLiteral_0= 'STRAIGHT' ) | (enumLiteral_1= 'LOB' ) | (enumLiteral_2= 'BEAM' ) ) )
            // InternalMazeComp.g:2105:2: ( (enumLiteral_0= 'STRAIGHT' ) | (enumLiteral_1= 'LOB' ) | (enumLiteral_2= 'BEAM' ) )
            {
            // InternalMazeComp.g:2105:2: ( (enumLiteral_0= 'STRAIGHT' ) | (enumLiteral_1= 'LOB' ) | (enumLiteral_2= 'BEAM' ) )
            int alt51=3;
            switch ( input.LA(1) ) {
            case 66:
                {
                alt51=1;
                }
                break;
            case 67:
                {
                alt51=2;
                }
                break;
            case 68:
                {
                alt51=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 51, 0, input);

                throw nvae;
            }

            switch (alt51) {
                case 1 :
                    // InternalMazeComp.g:2106:3: (enumLiteral_0= 'STRAIGHT' )
                    {
                    // InternalMazeComp.g:2106:3: (enumLiteral_0= 'STRAIGHT' )
                    // InternalMazeComp.g:2107:4: enumLiteral_0= 'STRAIGHT'
                    {
                    enumLiteral_0=(Token)match(input,66,FOLLOW_2); 

                    				current = grammarAccess.getProjectileTypeAccess().getSTRAIGHTEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getProjectileTypeAccess().getSTRAIGHTEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeComp.g:2114:3: (enumLiteral_1= 'LOB' )
                    {
                    // InternalMazeComp.g:2114:3: (enumLiteral_1= 'LOB' )
                    // InternalMazeComp.g:2115:4: enumLiteral_1= 'LOB'
                    {
                    enumLiteral_1=(Token)match(input,67,FOLLOW_2); 

                    				current = grammarAccess.getProjectileTypeAccess().getLOBEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getProjectileTypeAccess().getLOBEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalMazeComp.g:2122:3: (enumLiteral_2= 'BEAM' )
                    {
                    // InternalMazeComp.g:2122:3: (enumLiteral_2= 'BEAM' )
                    // InternalMazeComp.g:2123:4: enumLiteral_2= 'BEAM'
                    {
                    enumLiteral_2=(Token)match(input,68,FOLLOW_2); 

                    				current = grammarAccess.getProjectileTypeAccess().getBEAMEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getProjectileTypeAccess().getBEAMEnumLiteralDeclaration_2());
                    			

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
    // $ANTLR end "ruleProjectileType"

    // Delegated rules


 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0044009000020002L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0044009000000002L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0044008000000002L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0044000000000002L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0040000000000002L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000004040L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000018002L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000002040L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000000380000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000000000030L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000000300000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000000001800L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000000000006040L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x000000007F000000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x000000007E000000L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x000000007C000000L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000000078000000L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000000070000000L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000000060000000L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x3800000000000000L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0000000E00000000L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0000000C00000000L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x000001003F000000L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x000001003E000000L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x000001003C000000L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x0000010038000000L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x0000010030000000L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x0000010020000000L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x0000000000000000L,0x000000000000001CL});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_50 = new BitSet(new long[]{0x0003C00800000000L});
    public static final BitSet FOLLOW_51 = new BitSet(new long[]{0x0003800800000000L});
    public static final BitSet FOLLOW_52 = new BitSet(new long[]{0x0003000800000000L});
    public static final BitSet FOLLOW_53 = new BitSet(new long[]{0x0002000800000000L});
    public static final BitSet FOLLOW_54 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_55 = new BitSet(new long[]{0x0010000800000000L});
    public static final BitSet FOLLOW_56 = new BitSet(new long[]{0x0020000800000000L});
    public static final BitSet FOLLOW_57 = new BitSet(new long[]{0x0040000000000000L});
    public static final BitSet FOLLOW_58 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_59 = new BitSet(new long[]{0xC000000000000000L,0x0000000000000003L});
    public static final BitSet FOLLOW_60 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_61 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_62 = new BitSet(new long[]{0x0400000800000000L});

}