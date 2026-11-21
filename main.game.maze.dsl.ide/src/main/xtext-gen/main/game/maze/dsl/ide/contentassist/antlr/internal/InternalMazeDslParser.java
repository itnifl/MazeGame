package main.game.maze.dsl.ide.contentassist.antlr.internal;

import java.io.InputStream;
import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.DFA;
import main.game.maze.dsl.services.MazeDslGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalMazeDslParser extends AbstractInternalContentAssistParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_INT", "RULE_ID", "RULE_STRING", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'gatling'", "'true'", "'false'", "'easy'", "'normal'", "'hard'", "'zombie'", "'ghost'", "'pumpkinbomber'", "'passive'", "'wander'", "'aggressive'", "'patrol'", "'straight'", "'lob'", "'beam'", "'food'", "'bomb'", "'trap'", "'weapon'", "'game'", "'{'", "'}'", "'import'", "'difficulty'", "'level'", "'instantDeath'", "'speedMultiplier'", "'damageMultiplier'", "'maxThreat'", "'limit'", "'max'", "'opponent'", "'type'", "'displayName'", "'health'", "'speed'", "'threatLevel'", "'enabled'", "'behavior'", "'loot'", "'zombie-stats'", "'attackDamage'", "'infectionLevel'", "'resurrectionTime'", "'ghost-stats'", "'visibilityLevel'", "'nonTangibilityEnergy'", "'ranged-stats'", "'attackRange'", "'attackCooldown'", "'projectileSpeed'", "'projectileType'", "'splashRadius'", "'path'", "'['", "']'", "'visionRange'", "','", "'zone'", "'topLeft'", "'('", "')'", "'width'", "'height'", "':'", "'ms'", "'loot-table'", "'capacity'", "'item'", "'value'", "'weight'", "'basedOn'", "'threatCapacity'", "'wallMaterials'", "'inheritWeapons'", "'cascadeDepth'", "'ring'", "'materials'", "'share'", "'sergeant'", "'sightRange'", "'rounds'", "'roundDamage'", "'roundInterval'", "'burstCooldown'", "'sprite'", "'drops'", "'postDistance'", "'fireSound'", "'sergeants'", "'mines'", "'count'", "'ringRadius'", "'triggerRadius'", "'pulse'", "'blastRadius'", "'blastDamage'", "'playerDamage'", "'holeRadius'", "'portalGhost'", "'portalGhosts'", "'teleport'", "'cooldown'", "'banner'", "'bannerSeconds'", "'zapSound'", "'voiceSound'", "'-'", "'.'"
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
    public static final int RULE_ID=5;
    public static final int RULE_INT=4;
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
    public static final int RULE_STRING=6;
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

    	public void setGrammarAccess(MazeDslGrammarAccess grammarAccess) {
    		this.grammarAccess = grammarAccess;
    	}

    	@Override
    	protected Grammar getGrammar() {
    		return grammarAccess.getGrammar();
    	}

    	@Override
    	protected String getValueForTokenName(String tokenName) {
    		return tokenName;
    	}



    // $ANTLR start "entryRuleGameConfiguration"
    // InternalMazeDsl.g:53:1: entryRuleGameConfiguration : ruleGameConfiguration EOF ;
    public final void entryRuleGameConfiguration() throws RecognitionException {
        try {
            // InternalMazeDsl.g:54:1: ( ruleGameConfiguration EOF )
            // InternalMazeDsl.g:55:1: ruleGameConfiguration EOF
            {
             before(grammarAccess.getGameConfigurationRule()); 
            pushFollow(FOLLOW_1);
            ruleGameConfiguration();

            state._fsp--;

             after(grammarAccess.getGameConfigurationRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleGameConfiguration"


    // $ANTLR start "ruleGameConfiguration"
    // InternalMazeDsl.g:62:1: ruleGameConfiguration : ( ( rule__GameConfiguration__Group__0 ) ) ;
    public final void ruleGameConfiguration() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:66:2: ( ( ( rule__GameConfiguration__Group__0 ) ) )
            // InternalMazeDsl.g:67:2: ( ( rule__GameConfiguration__Group__0 ) )
            {
            // InternalMazeDsl.g:67:2: ( ( rule__GameConfiguration__Group__0 ) )
            // InternalMazeDsl.g:68:3: ( rule__GameConfiguration__Group__0 )
            {
             before(grammarAccess.getGameConfigurationAccess().getGroup()); 
            // InternalMazeDsl.g:69:3: ( rule__GameConfiguration__Group__0 )
            // InternalMazeDsl.g:69:4: rule__GameConfiguration__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__GameConfiguration__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getGameConfigurationAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleGameConfiguration"


    // $ANTLR start "entryRuleImport"
    // InternalMazeDsl.g:78:1: entryRuleImport : ruleImport EOF ;
    public final void entryRuleImport() throws RecognitionException {
        try {
            // InternalMazeDsl.g:79:1: ( ruleImport EOF )
            // InternalMazeDsl.g:80:1: ruleImport EOF
            {
             before(grammarAccess.getImportRule()); 
            pushFollow(FOLLOW_1);
            ruleImport();

            state._fsp--;

             after(grammarAccess.getImportRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleImport"


    // $ANTLR start "ruleImport"
    // InternalMazeDsl.g:87:1: ruleImport : ( ( rule__Import__Group__0 ) ) ;
    public final void ruleImport() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:91:2: ( ( ( rule__Import__Group__0 ) ) )
            // InternalMazeDsl.g:92:2: ( ( rule__Import__Group__0 ) )
            {
            // InternalMazeDsl.g:92:2: ( ( rule__Import__Group__0 ) )
            // InternalMazeDsl.g:93:3: ( rule__Import__Group__0 )
            {
             before(grammarAccess.getImportAccess().getGroup()); 
            // InternalMazeDsl.g:94:3: ( rule__Import__Group__0 )
            // InternalMazeDsl.g:94:4: rule__Import__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Import__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getImportAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleImport"


    // $ANTLR start "entryRuleDifficultyConfig"
    // InternalMazeDsl.g:103:1: entryRuleDifficultyConfig : ruleDifficultyConfig EOF ;
    public final void entryRuleDifficultyConfig() throws RecognitionException {
        try {
            // InternalMazeDsl.g:104:1: ( ruleDifficultyConfig EOF )
            // InternalMazeDsl.g:105:1: ruleDifficultyConfig EOF
            {
             before(grammarAccess.getDifficultyConfigRule()); 
            pushFollow(FOLLOW_1);
            ruleDifficultyConfig();

            state._fsp--;

             after(grammarAccess.getDifficultyConfigRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleDifficultyConfig"


    // $ANTLR start "ruleDifficultyConfig"
    // InternalMazeDsl.g:112:1: ruleDifficultyConfig : ( ( rule__DifficultyConfig__Group__0 ) ) ;
    public final void ruleDifficultyConfig() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:116:2: ( ( ( rule__DifficultyConfig__Group__0 ) ) )
            // InternalMazeDsl.g:117:2: ( ( rule__DifficultyConfig__Group__0 ) )
            {
            // InternalMazeDsl.g:117:2: ( ( rule__DifficultyConfig__Group__0 ) )
            // InternalMazeDsl.g:118:3: ( rule__DifficultyConfig__Group__0 )
            {
             before(grammarAccess.getDifficultyConfigAccess().getGroup()); 
            // InternalMazeDsl.g:119:3: ( rule__DifficultyConfig__Group__0 )
            // InternalMazeDsl.g:119:4: rule__DifficultyConfig__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__DifficultyConfig__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getDifficultyConfigAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleDifficultyConfig"


    // $ANTLR start "entryRuleEnemyLimit"
    // InternalMazeDsl.g:128:1: entryRuleEnemyLimit : ruleEnemyLimit EOF ;
    public final void entryRuleEnemyLimit() throws RecognitionException {
        try {
            // InternalMazeDsl.g:129:1: ( ruleEnemyLimit EOF )
            // InternalMazeDsl.g:130:1: ruleEnemyLimit EOF
            {
             before(grammarAccess.getEnemyLimitRule()); 
            pushFollow(FOLLOW_1);
            ruleEnemyLimit();

            state._fsp--;

             after(grammarAccess.getEnemyLimitRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleEnemyLimit"


    // $ANTLR start "ruleEnemyLimit"
    // InternalMazeDsl.g:137:1: ruleEnemyLimit : ( ( rule__EnemyLimit__Group__0 ) ) ;
    public final void ruleEnemyLimit() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:141:2: ( ( ( rule__EnemyLimit__Group__0 ) ) )
            // InternalMazeDsl.g:142:2: ( ( rule__EnemyLimit__Group__0 ) )
            {
            // InternalMazeDsl.g:142:2: ( ( rule__EnemyLimit__Group__0 ) )
            // InternalMazeDsl.g:143:3: ( rule__EnemyLimit__Group__0 )
            {
             before(grammarAccess.getEnemyLimitAccess().getGroup()); 
            // InternalMazeDsl.g:144:3: ( rule__EnemyLimit__Group__0 )
            // InternalMazeDsl.g:144:4: rule__EnemyLimit__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__EnemyLimit__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getEnemyLimitAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleEnemyLimit"


    // $ANTLR start "entryRuleOpponentConfig"
    // InternalMazeDsl.g:153:1: entryRuleOpponentConfig : ruleOpponentConfig EOF ;
    public final void entryRuleOpponentConfig() throws RecognitionException {
        try {
            // InternalMazeDsl.g:154:1: ( ruleOpponentConfig EOF )
            // InternalMazeDsl.g:155:1: ruleOpponentConfig EOF
            {
             before(grammarAccess.getOpponentConfigRule()); 
            pushFollow(FOLLOW_1);
            ruleOpponentConfig();

            state._fsp--;

             after(grammarAccess.getOpponentConfigRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleOpponentConfig"


    // $ANTLR start "ruleOpponentConfig"
    // InternalMazeDsl.g:162:1: ruleOpponentConfig : ( ( rule__OpponentConfig__Group__0 ) ) ;
    public final void ruleOpponentConfig() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:166:2: ( ( ( rule__OpponentConfig__Group__0 ) ) )
            // InternalMazeDsl.g:167:2: ( ( rule__OpponentConfig__Group__0 ) )
            {
            // InternalMazeDsl.g:167:2: ( ( rule__OpponentConfig__Group__0 ) )
            // InternalMazeDsl.g:168:3: ( rule__OpponentConfig__Group__0 )
            {
             before(grammarAccess.getOpponentConfigAccess().getGroup()); 
            // InternalMazeDsl.g:169:3: ( rule__OpponentConfig__Group__0 )
            // InternalMazeDsl.g:169:4: rule__OpponentConfig__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getOpponentConfigAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleOpponentConfig"


    // $ANTLR start "entryRuleCharacterSpecifics"
    // InternalMazeDsl.g:178:1: entryRuleCharacterSpecifics : ruleCharacterSpecifics EOF ;
    public final void entryRuleCharacterSpecifics() throws RecognitionException {
        try {
            // InternalMazeDsl.g:179:1: ( ruleCharacterSpecifics EOF )
            // InternalMazeDsl.g:180:1: ruleCharacterSpecifics EOF
            {
             before(grammarAccess.getCharacterSpecificsRule()); 
            pushFollow(FOLLOW_1);
            ruleCharacterSpecifics();

            state._fsp--;

             after(grammarAccess.getCharacterSpecificsRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleCharacterSpecifics"


    // $ANTLR start "ruleCharacterSpecifics"
    // InternalMazeDsl.g:187:1: ruleCharacterSpecifics : ( ( rule__CharacterSpecifics__Alternatives ) ) ;
    public final void ruleCharacterSpecifics() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:191:2: ( ( ( rule__CharacterSpecifics__Alternatives ) ) )
            // InternalMazeDsl.g:192:2: ( ( rule__CharacterSpecifics__Alternatives ) )
            {
            // InternalMazeDsl.g:192:2: ( ( rule__CharacterSpecifics__Alternatives ) )
            // InternalMazeDsl.g:193:3: ( rule__CharacterSpecifics__Alternatives )
            {
             before(grammarAccess.getCharacterSpecificsAccess().getAlternatives()); 
            // InternalMazeDsl.g:194:3: ( rule__CharacterSpecifics__Alternatives )
            // InternalMazeDsl.g:194:4: rule__CharacterSpecifics__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__CharacterSpecifics__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getCharacterSpecificsAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleCharacterSpecifics"


    // $ANTLR start "entryRuleZombieSpecifics"
    // InternalMazeDsl.g:203:1: entryRuleZombieSpecifics : ruleZombieSpecifics EOF ;
    public final void entryRuleZombieSpecifics() throws RecognitionException {
        try {
            // InternalMazeDsl.g:204:1: ( ruleZombieSpecifics EOF )
            // InternalMazeDsl.g:205:1: ruleZombieSpecifics EOF
            {
             before(grammarAccess.getZombieSpecificsRule()); 
            pushFollow(FOLLOW_1);
            ruleZombieSpecifics();

            state._fsp--;

             after(grammarAccess.getZombieSpecificsRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleZombieSpecifics"


    // $ANTLR start "ruleZombieSpecifics"
    // InternalMazeDsl.g:212:1: ruleZombieSpecifics : ( ( rule__ZombieSpecifics__Group__0 ) ) ;
    public final void ruleZombieSpecifics() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:216:2: ( ( ( rule__ZombieSpecifics__Group__0 ) ) )
            // InternalMazeDsl.g:217:2: ( ( rule__ZombieSpecifics__Group__0 ) )
            {
            // InternalMazeDsl.g:217:2: ( ( rule__ZombieSpecifics__Group__0 ) )
            // InternalMazeDsl.g:218:3: ( rule__ZombieSpecifics__Group__0 )
            {
             before(grammarAccess.getZombieSpecificsAccess().getGroup()); 
            // InternalMazeDsl.g:219:3: ( rule__ZombieSpecifics__Group__0 )
            // InternalMazeDsl.g:219:4: rule__ZombieSpecifics__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__ZombieSpecifics__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getZombieSpecificsAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleZombieSpecifics"


    // $ANTLR start "entryRuleGhostSpecifics"
    // InternalMazeDsl.g:228:1: entryRuleGhostSpecifics : ruleGhostSpecifics EOF ;
    public final void entryRuleGhostSpecifics() throws RecognitionException {
        try {
            // InternalMazeDsl.g:229:1: ( ruleGhostSpecifics EOF )
            // InternalMazeDsl.g:230:1: ruleGhostSpecifics EOF
            {
             before(grammarAccess.getGhostSpecificsRule()); 
            pushFollow(FOLLOW_1);
            ruleGhostSpecifics();

            state._fsp--;

             after(grammarAccess.getGhostSpecificsRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleGhostSpecifics"


    // $ANTLR start "ruleGhostSpecifics"
    // InternalMazeDsl.g:237:1: ruleGhostSpecifics : ( ( rule__GhostSpecifics__Group__0 ) ) ;
    public final void ruleGhostSpecifics() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:241:2: ( ( ( rule__GhostSpecifics__Group__0 ) ) )
            // InternalMazeDsl.g:242:2: ( ( rule__GhostSpecifics__Group__0 ) )
            {
            // InternalMazeDsl.g:242:2: ( ( rule__GhostSpecifics__Group__0 ) )
            // InternalMazeDsl.g:243:3: ( rule__GhostSpecifics__Group__0 )
            {
             before(grammarAccess.getGhostSpecificsAccess().getGroup()); 
            // InternalMazeDsl.g:244:3: ( rule__GhostSpecifics__Group__0 )
            // InternalMazeDsl.g:244:4: rule__GhostSpecifics__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__GhostSpecifics__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getGhostSpecificsAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleGhostSpecifics"


    // $ANTLR start "entryRuleRangedSpecifics"
    // InternalMazeDsl.g:253:1: entryRuleRangedSpecifics : ruleRangedSpecifics EOF ;
    public final void entryRuleRangedSpecifics() throws RecognitionException {
        try {
            // InternalMazeDsl.g:254:1: ( ruleRangedSpecifics EOF )
            // InternalMazeDsl.g:255:1: ruleRangedSpecifics EOF
            {
             before(grammarAccess.getRangedSpecificsRule()); 
            pushFollow(FOLLOW_1);
            ruleRangedSpecifics();

            state._fsp--;

             after(grammarAccess.getRangedSpecificsRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleRangedSpecifics"


    // $ANTLR start "ruleRangedSpecifics"
    // InternalMazeDsl.g:262:1: ruleRangedSpecifics : ( ( rule__RangedSpecifics__Group__0 ) ) ;
    public final void ruleRangedSpecifics() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:266:2: ( ( ( rule__RangedSpecifics__Group__0 ) ) )
            // InternalMazeDsl.g:267:2: ( ( rule__RangedSpecifics__Group__0 ) )
            {
            // InternalMazeDsl.g:267:2: ( ( rule__RangedSpecifics__Group__0 ) )
            // InternalMazeDsl.g:268:3: ( rule__RangedSpecifics__Group__0 )
            {
             before(grammarAccess.getRangedSpecificsAccess().getGroup()); 
            // InternalMazeDsl.g:269:3: ( rule__RangedSpecifics__Group__0 )
            // InternalMazeDsl.g:269:4: rule__RangedSpecifics__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getRangedSpecificsAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleRangedSpecifics"


    // $ANTLR start "entryRulePatrolConfig"
    // InternalMazeDsl.g:278:1: entryRulePatrolConfig : rulePatrolConfig EOF ;
    public final void entryRulePatrolConfig() throws RecognitionException {
        try {
            // InternalMazeDsl.g:279:1: ( rulePatrolConfig EOF )
            // InternalMazeDsl.g:280:1: rulePatrolConfig EOF
            {
             before(grammarAccess.getPatrolConfigRule()); 
            pushFollow(FOLLOW_1);
            rulePatrolConfig();

            state._fsp--;

             after(grammarAccess.getPatrolConfigRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRulePatrolConfig"


    // $ANTLR start "rulePatrolConfig"
    // InternalMazeDsl.g:287:1: rulePatrolConfig : ( ( rule__PatrolConfig__Group__0 ) ) ;
    public final void rulePatrolConfig() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:291:2: ( ( ( rule__PatrolConfig__Group__0 ) ) )
            // InternalMazeDsl.g:292:2: ( ( rule__PatrolConfig__Group__0 ) )
            {
            // InternalMazeDsl.g:292:2: ( ( rule__PatrolConfig__Group__0 ) )
            // InternalMazeDsl.g:293:3: ( rule__PatrolConfig__Group__0 )
            {
             before(grammarAccess.getPatrolConfigAccess().getGroup()); 
            // InternalMazeDsl.g:294:3: ( rule__PatrolConfig__Group__0 )
            // InternalMazeDsl.g:294:4: rule__PatrolConfig__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__PatrolConfig__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getPatrolConfigAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rulePatrolConfig"


    // $ANTLR start "entryRulePatrolZoneConfig"
    // InternalMazeDsl.g:303:1: entryRulePatrolZoneConfig : rulePatrolZoneConfig EOF ;
    public final void entryRulePatrolZoneConfig() throws RecognitionException {
        try {
            // InternalMazeDsl.g:304:1: ( rulePatrolZoneConfig EOF )
            // InternalMazeDsl.g:305:1: rulePatrolZoneConfig EOF
            {
             before(grammarAccess.getPatrolZoneConfigRule()); 
            pushFollow(FOLLOW_1);
            rulePatrolZoneConfig();

            state._fsp--;

             after(grammarAccess.getPatrolZoneConfigRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRulePatrolZoneConfig"


    // $ANTLR start "rulePatrolZoneConfig"
    // InternalMazeDsl.g:312:1: rulePatrolZoneConfig : ( ( rule__PatrolZoneConfig__Group__0 ) ) ;
    public final void rulePatrolZoneConfig() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:316:2: ( ( ( rule__PatrolZoneConfig__Group__0 ) ) )
            // InternalMazeDsl.g:317:2: ( ( rule__PatrolZoneConfig__Group__0 ) )
            {
            // InternalMazeDsl.g:317:2: ( ( rule__PatrolZoneConfig__Group__0 ) )
            // InternalMazeDsl.g:318:3: ( rule__PatrolZoneConfig__Group__0 )
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getGroup()); 
            // InternalMazeDsl.g:319:3: ( rule__PatrolZoneConfig__Group__0 )
            // InternalMazeDsl.g:319:4: rule__PatrolZoneConfig__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__PatrolZoneConfig__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getPatrolZoneConfigAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rulePatrolZoneConfig"


    // $ANTLR start "entryRuleWaypoint"
    // InternalMazeDsl.g:328:1: entryRuleWaypoint : ruleWaypoint EOF ;
    public final void entryRuleWaypoint() throws RecognitionException {
        try {
            // InternalMazeDsl.g:329:1: ( ruleWaypoint EOF )
            // InternalMazeDsl.g:330:1: ruleWaypoint EOF
            {
             before(grammarAccess.getWaypointRule()); 
            pushFollow(FOLLOW_1);
            ruleWaypoint();

            state._fsp--;

             after(grammarAccess.getWaypointRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleWaypoint"


    // $ANTLR start "ruleWaypoint"
    // InternalMazeDsl.g:337:1: ruleWaypoint : ( ( rule__Waypoint__Group__0 ) ) ;
    public final void ruleWaypoint() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:341:2: ( ( ( rule__Waypoint__Group__0 ) ) )
            // InternalMazeDsl.g:342:2: ( ( rule__Waypoint__Group__0 ) )
            {
            // InternalMazeDsl.g:342:2: ( ( rule__Waypoint__Group__0 ) )
            // InternalMazeDsl.g:343:3: ( rule__Waypoint__Group__0 )
            {
             before(grammarAccess.getWaypointAccess().getGroup()); 
            // InternalMazeDsl.g:344:3: ( rule__Waypoint__Group__0 )
            // InternalMazeDsl.g:344:4: rule__Waypoint__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Waypoint__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getWaypointAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleWaypoint"


    // $ANTLR start "entryRuleLootTableConfig"
    // InternalMazeDsl.g:353:1: entryRuleLootTableConfig : ruleLootTableConfig EOF ;
    public final void entryRuleLootTableConfig() throws RecognitionException {
        try {
            // InternalMazeDsl.g:354:1: ( ruleLootTableConfig EOF )
            // InternalMazeDsl.g:355:1: ruleLootTableConfig EOF
            {
             before(grammarAccess.getLootTableConfigRule()); 
            pushFollow(FOLLOW_1);
            ruleLootTableConfig();

            state._fsp--;

             after(grammarAccess.getLootTableConfigRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleLootTableConfig"


    // $ANTLR start "ruleLootTableConfig"
    // InternalMazeDsl.g:362:1: ruleLootTableConfig : ( ( rule__LootTableConfig__Group__0 ) ) ;
    public final void ruleLootTableConfig() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:366:2: ( ( ( rule__LootTableConfig__Group__0 ) ) )
            // InternalMazeDsl.g:367:2: ( ( rule__LootTableConfig__Group__0 ) )
            {
            // InternalMazeDsl.g:367:2: ( ( rule__LootTableConfig__Group__0 ) )
            // InternalMazeDsl.g:368:3: ( rule__LootTableConfig__Group__0 )
            {
             before(grammarAccess.getLootTableConfigAccess().getGroup()); 
            // InternalMazeDsl.g:369:3: ( rule__LootTableConfig__Group__0 )
            // InternalMazeDsl.g:369:4: rule__LootTableConfig__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__LootTableConfig__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getLootTableConfigAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleLootTableConfig"


    // $ANTLR start "entryRuleLootItemConfig"
    // InternalMazeDsl.g:378:1: entryRuleLootItemConfig : ruleLootItemConfig EOF ;
    public final void entryRuleLootItemConfig() throws RecognitionException {
        try {
            // InternalMazeDsl.g:379:1: ( ruleLootItemConfig EOF )
            // InternalMazeDsl.g:380:1: ruleLootItemConfig EOF
            {
             before(grammarAccess.getLootItemConfigRule()); 
            pushFollow(FOLLOW_1);
            ruleLootItemConfig();

            state._fsp--;

             after(grammarAccess.getLootItemConfigRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleLootItemConfig"


    // $ANTLR start "ruleLootItemConfig"
    // InternalMazeDsl.g:387:1: ruleLootItemConfig : ( ( rule__LootItemConfig__Group__0 ) ) ;
    public final void ruleLootItemConfig() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:391:2: ( ( ( rule__LootItemConfig__Group__0 ) ) )
            // InternalMazeDsl.g:392:2: ( ( rule__LootItemConfig__Group__0 ) )
            {
            // InternalMazeDsl.g:392:2: ( ( rule__LootItemConfig__Group__0 ) )
            // InternalMazeDsl.g:393:3: ( rule__LootItemConfig__Group__0 )
            {
             before(grammarAccess.getLootItemConfigAccess().getGroup()); 
            // InternalMazeDsl.g:394:3: ( rule__LootItemConfig__Group__0 )
            // InternalMazeDsl.g:394:4: rule__LootItemConfig__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__LootItemConfig__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getLootItemConfigAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleLootItemConfig"


    // $ANTLR start "entryRuleLevelConfig"
    // InternalMazeDsl.g:403:1: entryRuleLevelConfig : ruleLevelConfig EOF ;
    public final void entryRuleLevelConfig() throws RecognitionException {
        try {
            // InternalMazeDsl.g:404:1: ( ruleLevelConfig EOF )
            // InternalMazeDsl.g:405:1: ruleLevelConfig EOF
            {
             before(grammarAccess.getLevelConfigRule()); 
            pushFollow(FOLLOW_1);
            ruleLevelConfig();

            state._fsp--;

             after(grammarAccess.getLevelConfigRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleLevelConfig"


    // $ANTLR start "ruleLevelConfig"
    // InternalMazeDsl.g:412:1: ruleLevelConfig : ( ( rule__LevelConfig__Group__0 ) ) ;
    public final void ruleLevelConfig() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:416:2: ( ( ( rule__LevelConfig__Group__0 ) ) )
            // InternalMazeDsl.g:417:2: ( ( rule__LevelConfig__Group__0 ) )
            {
            // InternalMazeDsl.g:417:2: ( ( rule__LevelConfig__Group__0 ) )
            // InternalMazeDsl.g:418:3: ( rule__LevelConfig__Group__0 )
            {
             before(grammarAccess.getLevelConfigAccess().getGroup()); 
            // InternalMazeDsl.g:419:3: ( rule__LevelConfig__Group__0 )
            // InternalMazeDsl.g:419:4: rule__LevelConfig__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__LevelConfig__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getLevelConfigAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleLevelConfig"


    // $ANTLR start "entryRuleLevelZone"
    // InternalMazeDsl.g:428:1: entryRuleLevelZone : ruleLevelZone EOF ;
    public final void entryRuleLevelZone() throws RecognitionException {
        try {
            // InternalMazeDsl.g:429:1: ( ruleLevelZone EOF )
            // InternalMazeDsl.g:430:1: ruleLevelZone EOF
            {
             before(grammarAccess.getLevelZoneRule()); 
            pushFollow(FOLLOW_1);
            ruleLevelZone();

            state._fsp--;

             after(grammarAccess.getLevelZoneRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleLevelZone"


    // $ANTLR start "ruleLevelZone"
    // InternalMazeDsl.g:437:1: ruleLevelZone : ( ( rule__LevelZone__Group__0 ) ) ;
    public final void ruleLevelZone() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:441:2: ( ( ( rule__LevelZone__Group__0 ) ) )
            // InternalMazeDsl.g:442:2: ( ( rule__LevelZone__Group__0 ) )
            {
            // InternalMazeDsl.g:442:2: ( ( rule__LevelZone__Group__0 ) )
            // InternalMazeDsl.g:443:3: ( rule__LevelZone__Group__0 )
            {
             before(grammarAccess.getLevelZoneAccess().getGroup()); 
            // InternalMazeDsl.g:444:3: ( rule__LevelZone__Group__0 )
            // InternalMazeDsl.g:444:4: rule__LevelZone__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__LevelZone__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getLevelZoneAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleLevelZone"


    // $ANTLR start "entryRuleSergeantConfig"
    // InternalMazeDsl.g:453:1: entryRuleSergeantConfig : ruleSergeantConfig EOF ;
    public final void entryRuleSergeantConfig() throws RecognitionException {
        try {
            // InternalMazeDsl.g:454:1: ( ruleSergeantConfig EOF )
            // InternalMazeDsl.g:455:1: ruleSergeantConfig EOF
            {
             before(grammarAccess.getSergeantConfigRule()); 
            pushFollow(FOLLOW_1);
            ruleSergeantConfig();

            state._fsp--;

             after(grammarAccess.getSergeantConfigRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleSergeantConfig"


    // $ANTLR start "ruleSergeantConfig"
    // InternalMazeDsl.g:462:1: ruleSergeantConfig : ( ( rule__SergeantConfig__Group__0 ) ) ;
    public final void ruleSergeantConfig() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:466:2: ( ( ( rule__SergeantConfig__Group__0 ) ) )
            // InternalMazeDsl.g:467:2: ( ( rule__SergeantConfig__Group__0 ) )
            {
            // InternalMazeDsl.g:467:2: ( ( rule__SergeantConfig__Group__0 ) )
            // InternalMazeDsl.g:468:3: ( rule__SergeantConfig__Group__0 )
            {
             before(grammarAccess.getSergeantConfigAccess().getGroup()); 
            // InternalMazeDsl.g:469:3: ( rule__SergeantConfig__Group__0 )
            // InternalMazeDsl.g:469:4: rule__SergeantConfig__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__SergeantConfig__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getSergeantConfigAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleSergeantConfig"


    // $ANTLR start "entryRuleTierCountConfig"
    // InternalMazeDsl.g:478:1: entryRuleTierCountConfig : ruleTierCountConfig EOF ;
    public final void entryRuleTierCountConfig() throws RecognitionException {
        try {
            // InternalMazeDsl.g:479:1: ( ruleTierCountConfig EOF )
            // InternalMazeDsl.g:480:1: ruleTierCountConfig EOF
            {
             before(grammarAccess.getTierCountConfigRule()); 
            pushFollow(FOLLOW_1);
            ruleTierCountConfig();

            state._fsp--;

             after(grammarAccess.getTierCountConfigRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleTierCountConfig"


    // $ANTLR start "ruleTierCountConfig"
    // InternalMazeDsl.g:487:1: ruleTierCountConfig : ( ( rule__TierCountConfig__Group__0 ) ) ;
    public final void ruleTierCountConfig() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:491:2: ( ( ( rule__TierCountConfig__Group__0 ) ) )
            // InternalMazeDsl.g:492:2: ( ( rule__TierCountConfig__Group__0 ) )
            {
            // InternalMazeDsl.g:492:2: ( ( rule__TierCountConfig__Group__0 ) )
            // InternalMazeDsl.g:493:3: ( rule__TierCountConfig__Group__0 )
            {
             before(grammarAccess.getTierCountConfigAccess().getGroup()); 
            // InternalMazeDsl.g:494:3: ( rule__TierCountConfig__Group__0 )
            // InternalMazeDsl.g:494:4: rule__TierCountConfig__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__TierCountConfig__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getTierCountConfigAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleTierCountConfig"


    // $ANTLR start "entryRuleMineRingConfig"
    // InternalMazeDsl.g:503:1: entryRuleMineRingConfig : ruleMineRingConfig EOF ;
    public final void entryRuleMineRingConfig() throws RecognitionException {
        try {
            // InternalMazeDsl.g:504:1: ( ruleMineRingConfig EOF )
            // InternalMazeDsl.g:505:1: ruleMineRingConfig EOF
            {
             before(grammarAccess.getMineRingConfigRule()); 
            pushFollow(FOLLOW_1);
            ruleMineRingConfig();

            state._fsp--;

             after(grammarAccess.getMineRingConfigRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleMineRingConfig"


    // $ANTLR start "ruleMineRingConfig"
    // InternalMazeDsl.g:512:1: ruleMineRingConfig : ( ( rule__MineRingConfig__Group__0 ) ) ;
    public final void ruleMineRingConfig() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:516:2: ( ( ( rule__MineRingConfig__Group__0 ) ) )
            // InternalMazeDsl.g:517:2: ( ( rule__MineRingConfig__Group__0 ) )
            {
            // InternalMazeDsl.g:517:2: ( ( rule__MineRingConfig__Group__0 ) )
            // InternalMazeDsl.g:518:3: ( rule__MineRingConfig__Group__0 )
            {
             before(grammarAccess.getMineRingConfigAccess().getGroup()); 
            // InternalMazeDsl.g:519:3: ( rule__MineRingConfig__Group__0 )
            // InternalMazeDsl.g:519:4: rule__MineRingConfig__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__MineRingConfig__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getMineRingConfigAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleMineRingConfig"


    // $ANTLR start "entryRulePortalGhostConfig"
    // InternalMazeDsl.g:528:1: entryRulePortalGhostConfig : rulePortalGhostConfig EOF ;
    public final void entryRulePortalGhostConfig() throws RecognitionException {
        try {
            // InternalMazeDsl.g:529:1: ( rulePortalGhostConfig EOF )
            // InternalMazeDsl.g:530:1: rulePortalGhostConfig EOF
            {
             before(grammarAccess.getPortalGhostConfigRule()); 
            pushFollow(FOLLOW_1);
            rulePortalGhostConfig();

            state._fsp--;

             after(grammarAccess.getPortalGhostConfigRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRulePortalGhostConfig"


    // $ANTLR start "rulePortalGhostConfig"
    // InternalMazeDsl.g:537:1: rulePortalGhostConfig : ( ( rule__PortalGhostConfig__Group__0 ) ) ;
    public final void rulePortalGhostConfig() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:541:2: ( ( ( rule__PortalGhostConfig__Group__0 ) ) )
            // InternalMazeDsl.g:542:2: ( ( rule__PortalGhostConfig__Group__0 ) )
            {
            // InternalMazeDsl.g:542:2: ( ( rule__PortalGhostConfig__Group__0 ) )
            // InternalMazeDsl.g:543:3: ( rule__PortalGhostConfig__Group__0 )
            {
             before(grammarAccess.getPortalGhostConfigAccess().getGroup()); 
            // InternalMazeDsl.g:544:3: ( rule__PortalGhostConfig__Group__0 )
            // InternalMazeDsl.g:544:4: rule__PortalGhostConfig__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__PortalGhostConfig__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getPortalGhostConfigAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rulePortalGhostConfig"


    // $ANTLR start "entryRuleGhostCountConfig"
    // InternalMazeDsl.g:553:1: entryRuleGhostCountConfig : ruleGhostCountConfig EOF ;
    public final void entryRuleGhostCountConfig() throws RecognitionException {
        try {
            // InternalMazeDsl.g:554:1: ( ruleGhostCountConfig EOF )
            // InternalMazeDsl.g:555:1: ruleGhostCountConfig EOF
            {
             before(grammarAccess.getGhostCountConfigRule()); 
            pushFollow(FOLLOW_1);
            ruleGhostCountConfig();

            state._fsp--;

             after(grammarAccess.getGhostCountConfigRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleGhostCountConfig"


    // $ANTLR start "ruleGhostCountConfig"
    // InternalMazeDsl.g:562:1: ruleGhostCountConfig : ( ( rule__GhostCountConfig__Group__0 ) ) ;
    public final void ruleGhostCountConfig() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:566:2: ( ( ( rule__GhostCountConfig__Group__0 ) ) )
            // InternalMazeDsl.g:567:2: ( ( rule__GhostCountConfig__Group__0 ) )
            {
            // InternalMazeDsl.g:567:2: ( ( rule__GhostCountConfig__Group__0 ) )
            // InternalMazeDsl.g:568:3: ( rule__GhostCountConfig__Group__0 )
            {
             before(grammarAccess.getGhostCountConfigAccess().getGroup()); 
            // InternalMazeDsl.g:569:3: ( rule__GhostCountConfig__Group__0 )
            // InternalMazeDsl.g:569:4: rule__GhostCountConfig__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__GhostCountConfig__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getGhostCountConfigAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleGhostCountConfig"


    // $ANTLR start "entryRuleTeleportRulesConfig"
    // InternalMazeDsl.g:578:1: entryRuleTeleportRulesConfig : ruleTeleportRulesConfig EOF ;
    public final void entryRuleTeleportRulesConfig() throws RecognitionException {
        try {
            // InternalMazeDsl.g:579:1: ( ruleTeleportRulesConfig EOF )
            // InternalMazeDsl.g:580:1: ruleTeleportRulesConfig EOF
            {
             before(grammarAccess.getTeleportRulesConfigRule()); 
            pushFollow(FOLLOW_1);
            ruleTeleportRulesConfig();

            state._fsp--;

             after(grammarAccess.getTeleportRulesConfigRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleTeleportRulesConfig"


    // $ANTLR start "ruleTeleportRulesConfig"
    // InternalMazeDsl.g:587:1: ruleTeleportRulesConfig : ( ( rule__TeleportRulesConfig__Group__0 ) ) ;
    public final void ruleTeleportRulesConfig() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:591:2: ( ( ( rule__TeleportRulesConfig__Group__0 ) ) )
            // InternalMazeDsl.g:592:2: ( ( rule__TeleportRulesConfig__Group__0 ) )
            {
            // InternalMazeDsl.g:592:2: ( ( rule__TeleportRulesConfig__Group__0 ) )
            // InternalMazeDsl.g:593:3: ( rule__TeleportRulesConfig__Group__0 )
            {
             before(grammarAccess.getTeleportRulesConfigAccess().getGroup()); 
            // InternalMazeDsl.g:594:3: ( rule__TeleportRulesConfig__Group__0 )
            // InternalMazeDsl.g:594:4: rule__TeleportRulesConfig__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__TeleportRulesConfig__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getTeleportRulesConfigAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleTeleportRulesConfig"


    // $ANTLR start "entryRuleDOUBLE"
    // InternalMazeDsl.g:603:1: entryRuleDOUBLE : ruleDOUBLE EOF ;
    public final void entryRuleDOUBLE() throws RecognitionException {
        try {
            // InternalMazeDsl.g:604:1: ( ruleDOUBLE EOF )
            // InternalMazeDsl.g:605:1: ruleDOUBLE EOF
            {
             before(grammarAccess.getDOUBLERule()); 
            pushFollow(FOLLOW_1);
            ruleDOUBLE();

            state._fsp--;

             after(grammarAccess.getDOUBLERule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleDOUBLE"


    // $ANTLR start "ruleDOUBLE"
    // InternalMazeDsl.g:612:1: ruleDOUBLE : ( ( rule__DOUBLE__Group__0 ) ) ;
    public final void ruleDOUBLE() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:616:2: ( ( ( rule__DOUBLE__Group__0 ) ) )
            // InternalMazeDsl.g:617:2: ( ( rule__DOUBLE__Group__0 ) )
            {
            // InternalMazeDsl.g:617:2: ( ( rule__DOUBLE__Group__0 ) )
            // InternalMazeDsl.g:618:3: ( rule__DOUBLE__Group__0 )
            {
             before(grammarAccess.getDOUBLEAccess().getGroup()); 
            // InternalMazeDsl.g:619:3: ( rule__DOUBLE__Group__0 )
            // InternalMazeDsl.g:619:4: rule__DOUBLE__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__DOUBLE__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getDOUBLEAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleDOUBLE"


    // $ANTLR start "entryRuleSIGNED_INT"
    // InternalMazeDsl.g:628:1: entryRuleSIGNED_INT : ruleSIGNED_INT EOF ;
    public final void entryRuleSIGNED_INT() throws RecognitionException {
        try {
            // InternalMazeDsl.g:629:1: ( ruleSIGNED_INT EOF )
            // InternalMazeDsl.g:630:1: ruleSIGNED_INT EOF
            {
             before(grammarAccess.getSIGNED_INTRule()); 
            pushFollow(FOLLOW_1);
            ruleSIGNED_INT();

            state._fsp--;

             after(grammarAccess.getSIGNED_INTRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleSIGNED_INT"


    // $ANTLR start "ruleSIGNED_INT"
    // InternalMazeDsl.g:637:1: ruleSIGNED_INT : ( ( rule__SIGNED_INT__Group__0 ) ) ;
    public final void ruleSIGNED_INT() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:641:2: ( ( ( rule__SIGNED_INT__Group__0 ) ) )
            // InternalMazeDsl.g:642:2: ( ( rule__SIGNED_INT__Group__0 ) )
            {
            // InternalMazeDsl.g:642:2: ( ( rule__SIGNED_INT__Group__0 ) )
            // InternalMazeDsl.g:643:3: ( rule__SIGNED_INT__Group__0 )
            {
             before(grammarAccess.getSIGNED_INTAccess().getGroup()); 
            // InternalMazeDsl.g:644:3: ( rule__SIGNED_INT__Group__0 )
            // InternalMazeDsl.g:644:4: rule__SIGNED_INT__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__SIGNED_INT__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getSIGNED_INTAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleSIGNED_INT"


    // $ANTLR start "entryRuleBOOLEAN"
    // InternalMazeDsl.g:653:1: entryRuleBOOLEAN : ruleBOOLEAN EOF ;
    public final void entryRuleBOOLEAN() throws RecognitionException {
        try {
            // InternalMazeDsl.g:654:1: ( ruleBOOLEAN EOF )
            // InternalMazeDsl.g:655:1: ruleBOOLEAN EOF
            {
             before(grammarAccess.getBOOLEANRule()); 
            pushFollow(FOLLOW_1);
            ruleBOOLEAN();

            state._fsp--;

             after(grammarAccess.getBOOLEANRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleBOOLEAN"


    // $ANTLR start "ruleBOOLEAN"
    // InternalMazeDsl.g:662:1: ruleBOOLEAN : ( ( rule__BOOLEAN__Alternatives ) ) ;
    public final void ruleBOOLEAN() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:666:2: ( ( ( rule__BOOLEAN__Alternatives ) ) )
            // InternalMazeDsl.g:667:2: ( ( rule__BOOLEAN__Alternatives ) )
            {
            // InternalMazeDsl.g:667:2: ( ( rule__BOOLEAN__Alternatives ) )
            // InternalMazeDsl.g:668:3: ( rule__BOOLEAN__Alternatives )
            {
             before(grammarAccess.getBOOLEANAccess().getAlternatives()); 
            // InternalMazeDsl.g:669:3: ( rule__BOOLEAN__Alternatives )
            // InternalMazeDsl.g:669:4: rule__BOOLEAN__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__BOOLEAN__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getBOOLEANAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleBOOLEAN"


    // $ANTLR start "entryRuleDOUBLE_OBJECT"
    // InternalMazeDsl.g:678:1: entryRuleDOUBLE_OBJECT : ruleDOUBLE_OBJECT EOF ;
    public final void entryRuleDOUBLE_OBJECT() throws RecognitionException {
        try {
            // InternalMazeDsl.g:679:1: ( ruleDOUBLE_OBJECT EOF )
            // InternalMazeDsl.g:680:1: ruleDOUBLE_OBJECT EOF
            {
             before(grammarAccess.getDOUBLE_OBJECTRule()); 
            pushFollow(FOLLOW_1);
            ruleDOUBLE_OBJECT();

            state._fsp--;

             after(grammarAccess.getDOUBLE_OBJECTRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleDOUBLE_OBJECT"


    // $ANTLR start "ruleDOUBLE_OBJECT"
    // InternalMazeDsl.g:687:1: ruleDOUBLE_OBJECT : ( ( rule__DOUBLE_OBJECT__Group__0 ) ) ;
    public final void ruleDOUBLE_OBJECT() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:691:2: ( ( ( rule__DOUBLE_OBJECT__Group__0 ) ) )
            // InternalMazeDsl.g:692:2: ( ( rule__DOUBLE_OBJECT__Group__0 ) )
            {
            // InternalMazeDsl.g:692:2: ( ( rule__DOUBLE_OBJECT__Group__0 ) )
            // InternalMazeDsl.g:693:3: ( rule__DOUBLE_OBJECT__Group__0 )
            {
             before(grammarAccess.getDOUBLE_OBJECTAccess().getGroup()); 
            // InternalMazeDsl.g:694:3: ( rule__DOUBLE_OBJECT__Group__0 )
            // InternalMazeDsl.g:694:4: rule__DOUBLE_OBJECT__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__DOUBLE_OBJECT__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getDOUBLE_OBJECTAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleDOUBLE_OBJECT"


    // $ANTLR start "entryRuleINT_OBJECT"
    // InternalMazeDsl.g:703:1: entryRuleINT_OBJECT : ruleINT_OBJECT EOF ;
    public final void entryRuleINT_OBJECT() throws RecognitionException {
        try {
            // InternalMazeDsl.g:704:1: ( ruleINT_OBJECT EOF )
            // InternalMazeDsl.g:705:1: ruleINT_OBJECT EOF
            {
             before(grammarAccess.getINT_OBJECTRule()); 
            pushFollow(FOLLOW_1);
            ruleINT_OBJECT();

            state._fsp--;

             after(grammarAccess.getINT_OBJECTRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleINT_OBJECT"


    // $ANTLR start "ruleINT_OBJECT"
    // InternalMazeDsl.g:712:1: ruleINT_OBJECT : ( ( rule__INT_OBJECT__Group__0 ) ) ;
    public final void ruleINT_OBJECT() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:716:2: ( ( ( rule__INT_OBJECT__Group__0 ) ) )
            // InternalMazeDsl.g:717:2: ( ( rule__INT_OBJECT__Group__0 ) )
            {
            // InternalMazeDsl.g:717:2: ( ( rule__INT_OBJECT__Group__0 ) )
            // InternalMazeDsl.g:718:3: ( rule__INT_OBJECT__Group__0 )
            {
             before(grammarAccess.getINT_OBJECTAccess().getGroup()); 
            // InternalMazeDsl.g:719:3: ( rule__INT_OBJECT__Group__0 )
            // InternalMazeDsl.g:719:4: rule__INT_OBJECT__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__INT_OBJECT__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getINT_OBJECTAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleINT_OBJECT"


    // $ANTLR start "entryRuleBOOLEAN_OBJECT"
    // InternalMazeDsl.g:728:1: entryRuleBOOLEAN_OBJECT : ruleBOOLEAN_OBJECT EOF ;
    public final void entryRuleBOOLEAN_OBJECT() throws RecognitionException {
        try {
            // InternalMazeDsl.g:729:1: ( ruleBOOLEAN_OBJECT EOF )
            // InternalMazeDsl.g:730:1: ruleBOOLEAN_OBJECT EOF
            {
             before(grammarAccess.getBOOLEAN_OBJECTRule()); 
            pushFollow(FOLLOW_1);
            ruleBOOLEAN_OBJECT();

            state._fsp--;

             after(grammarAccess.getBOOLEAN_OBJECTRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleBOOLEAN_OBJECT"


    // $ANTLR start "ruleBOOLEAN_OBJECT"
    // InternalMazeDsl.g:737:1: ruleBOOLEAN_OBJECT : ( ( rule__BOOLEAN_OBJECT__Alternatives ) ) ;
    public final void ruleBOOLEAN_OBJECT() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:741:2: ( ( ( rule__BOOLEAN_OBJECT__Alternatives ) ) )
            // InternalMazeDsl.g:742:2: ( ( rule__BOOLEAN_OBJECT__Alternatives ) )
            {
            // InternalMazeDsl.g:742:2: ( ( rule__BOOLEAN_OBJECT__Alternatives ) )
            // InternalMazeDsl.g:743:3: ( rule__BOOLEAN_OBJECT__Alternatives )
            {
             before(grammarAccess.getBOOLEAN_OBJECTAccess().getAlternatives()); 
            // InternalMazeDsl.g:744:3: ( rule__BOOLEAN_OBJECT__Alternatives )
            // InternalMazeDsl.g:744:4: rule__BOOLEAN_OBJECT__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__BOOLEAN_OBJECT__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getBOOLEAN_OBJECTAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleBOOLEAN_OBJECT"


    // $ANTLR start "ruleDifficultyLevel"
    // InternalMazeDsl.g:753:1: ruleDifficultyLevel : ( ( rule__DifficultyLevel__Alternatives ) ) ;
    public final void ruleDifficultyLevel() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:757:1: ( ( ( rule__DifficultyLevel__Alternatives ) ) )
            // InternalMazeDsl.g:758:2: ( ( rule__DifficultyLevel__Alternatives ) )
            {
            // InternalMazeDsl.g:758:2: ( ( rule__DifficultyLevel__Alternatives ) )
            // InternalMazeDsl.g:759:3: ( rule__DifficultyLevel__Alternatives )
            {
             before(grammarAccess.getDifficultyLevelAccess().getAlternatives()); 
            // InternalMazeDsl.g:760:3: ( rule__DifficultyLevel__Alternatives )
            // InternalMazeDsl.g:760:4: rule__DifficultyLevel__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__DifficultyLevel__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getDifficultyLevelAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleDifficultyLevel"


    // $ANTLR start "ruleEnemyType"
    // InternalMazeDsl.g:769:1: ruleEnemyType : ( ( rule__EnemyType__Alternatives ) ) ;
    public final void ruleEnemyType() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:773:1: ( ( ( rule__EnemyType__Alternatives ) ) )
            // InternalMazeDsl.g:774:2: ( ( rule__EnemyType__Alternatives ) )
            {
            // InternalMazeDsl.g:774:2: ( ( rule__EnemyType__Alternatives ) )
            // InternalMazeDsl.g:775:3: ( rule__EnemyType__Alternatives )
            {
             before(grammarAccess.getEnemyTypeAccess().getAlternatives()); 
            // InternalMazeDsl.g:776:3: ( rule__EnemyType__Alternatives )
            // InternalMazeDsl.g:776:4: rule__EnemyType__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__EnemyType__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getEnemyTypeAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleEnemyType"


    // $ANTLR start "ruleCharacterTypeEnum"
    // InternalMazeDsl.g:785:1: ruleCharacterTypeEnum : ( ( rule__CharacterTypeEnum__Alternatives ) ) ;
    public final void ruleCharacterTypeEnum() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:789:1: ( ( ( rule__CharacterTypeEnum__Alternatives ) ) )
            // InternalMazeDsl.g:790:2: ( ( rule__CharacterTypeEnum__Alternatives ) )
            {
            // InternalMazeDsl.g:790:2: ( ( rule__CharacterTypeEnum__Alternatives ) )
            // InternalMazeDsl.g:791:3: ( rule__CharacterTypeEnum__Alternatives )
            {
             before(grammarAccess.getCharacterTypeEnumAccess().getAlternatives()); 
            // InternalMazeDsl.g:792:3: ( rule__CharacterTypeEnum__Alternatives )
            // InternalMazeDsl.g:792:4: rule__CharacterTypeEnum__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__CharacterTypeEnum__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getCharacterTypeEnumAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleCharacterTypeEnum"


    // $ANTLR start "ruleBehaviorTypeEnum"
    // InternalMazeDsl.g:801:1: ruleBehaviorTypeEnum : ( ( rule__BehaviorTypeEnum__Alternatives ) ) ;
    public final void ruleBehaviorTypeEnum() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:805:1: ( ( ( rule__BehaviorTypeEnum__Alternatives ) ) )
            // InternalMazeDsl.g:806:2: ( ( rule__BehaviorTypeEnum__Alternatives ) )
            {
            // InternalMazeDsl.g:806:2: ( ( rule__BehaviorTypeEnum__Alternatives ) )
            // InternalMazeDsl.g:807:3: ( rule__BehaviorTypeEnum__Alternatives )
            {
             before(grammarAccess.getBehaviorTypeEnumAccess().getAlternatives()); 
            // InternalMazeDsl.g:808:3: ( rule__BehaviorTypeEnum__Alternatives )
            // InternalMazeDsl.g:808:4: rule__BehaviorTypeEnum__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__BehaviorTypeEnum__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getBehaviorTypeEnumAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleBehaviorTypeEnum"


    // $ANTLR start "ruleProjectileTypeEnum"
    // InternalMazeDsl.g:817:1: ruleProjectileTypeEnum : ( ( rule__ProjectileTypeEnum__Alternatives ) ) ;
    public final void ruleProjectileTypeEnum() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:821:1: ( ( ( rule__ProjectileTypeEnum__Alternatives ) ) )
            // InternalMazeDsl.g:822:2: ( ( rule__ProjectileTypeEnum__Alternatives ) )
            {
            // InternalMazeDsl.g:822:2: ( ( rule__ProjectileTypeEnum__Alternatives ) )
            // InternalMazeDsl.g:823:3: ( rule__ProjectileTypeEnum__Alternatives )
            {
             before(grammarAccess.getProjectileTypeEnumAccess().getAlternatives()); 
            // InternalMazeDsl.g:824:3: ( rule__ProjectileTypeEnum__Alternatives )
            // InternalMazeDsl.g:824:4: rule__ProjectileTypeEnum__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__ProjectileTypeEnum__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getProjectileTypeEnumAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleProjectileTypeEnum"


    // $ANTLR start "ruleLootItemTypeEnum"
    // InternalMazeDsl.g:833:1: ruleLootItemTypeEnum : ( ( rule__LootItemTypeEnum__Alternatives ) ) ;
    public final void ruleLootItemTypeEnum() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:837:1: ( ( ( rule__LootItemTypeEnum__Alternatives ) ) )
            // InternalMazeDsl.g:838:2: ( ( rule__LootItemTypeEnum__Alternatives ) )
            {
            // InternalMazeDsl.g:838:2: ( ( rule__LootItemTypeEnum__Alternatives ) )
            // InternalMazeDsl.g:839:3: ( rule__LootItemTypeEnum__Alternatives )
            {
             before(grammarAccess.getLootItemTypeEnumAccess().getAlternatives()); 
            // InternalMazeDsl.g:840:3: ( rule__LootItemTypeEnum__Alternatives )
            // InternalMazeDsl.g:840:4: rule__LootItemTypeEnum__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__LootItemTypeEnum__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getLootItemTypeEnumAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleLootItemTypeEnum"


    // $ANTLR start "ruleDropWeaponEnum"
    // InternalMazeDsl.g:849:1: ruleDropWeaponEnum : ( ( 'gatling' ) ) ;
    public final void ruleDropWeaponEnum() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:853:1: ( ( ( 'gatling' ) ) )
            // InternalMazeDsl.g:854:2: ( ( 'gatling' ) )
            {
            // InternalMazeDsl.g:854:2: ( ( 'gatling' ) )
            // InternalMazeDsl.g:855:3: ( 'gatling' )
            {
             before(grammarAccess.getDropWeaponEnumAccess().getGATLING_GUNEnumLiteralDeclaration()); 
            // InternalMazeDsl.g:856:3: ( 'gatling' )
            // InternalMazeDsl.g:856:4: 'gatling'
            {
            match(input,11,FOLLOW_2); 

            }

             after(grammarAccess.getDropWeaponEnumAccess().getGATLING_GUNEnumLiteralDeclaration()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleDropWeaponEnum"


    // $ANTLR start "rule__CharacterSpecifics__Alternatives"
    // InternalMazeDsl.g:864:1: rule__CharacterSpecifics__Alternatives : ( ( ruleZombieSpecifics ) | ( ruleGhostSpecifics ) | ( ruleRangedSpecifics ) );
    public final void rule__CharacterSpecifics__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:868:1: ( ( ruleZombieSpecifics ) | ( ruleGhostSpecifics ) | ( ruleRangedSpecifics ) )
            int alt1=3;
            switch ( input.LA(1) ) {
            case 52:
                {
                alt1=1;
                }
                break;
            case 56:
                {
                alt1=2;
                }
                break;
            case 59:
                {
                alt1=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }

            switch (alt1) {
                case 1 :
                    // InternalMazeDsl.g:869:2: ( ruleZombieSpecifics )
                    {
                    // InternalMazeDsl.g:869:2: ( ruleZombieSpecifics )
                    // InternalMazeDsl.g:870:3: ruleZombieSpecifics
                    {
                     before(grammarAccess.getCharacterSpecificsAccess().getZombieSpecificsParserRuleCall_0()); 
                    pushFollow(FOLLOW_2);
                    ruleZombieSpecifics();

                    state._fsp--;

                     after(grammarAccess.getCharacterSpecificsAccess().getZombieSpecificsParserRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:875:2: ( ruleGhostSpecifics )
                    {
                    // InternalMazeDsl.g:875:2: ( ruleGhostSpecifics )
                    // InternalMazeDsl.g:876:3: ruleGhostSpecifics
                    {
                     before(grammarAccess.getCharacterSpecificsAccess().getGhostSpecificsParserRuleCall_1()); 
                    pushFollow(FOLLOW_2);
                    ruleGhostSpecifics();

                    state._fsp--;

                     after(grammarAccess.getCharacterSpecificsAccess().getGhostSpecificsParserRuleCall_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalMazeDsl.g:881:2: ( ruleRangedSpecifics )
                    {
                    // InternalMazeDsl.g:881:2: ( ruleRangedSpecifics )
                    // InternalMazeDsl.g:882:3: ruleRangedSpecifics
                    {
                     before(grammarAccess.getCharacterSpecificsAccess().getRangedSpecificsParserRuleCall_2()); 
                    pushFollow(FOLLOW_2);
                    ruleRangedSpecifics();

                    state._fsp--;

                     after(grammarAccess.getCharacterSpecificsAccess().getRangedSpecificsParserRuleCall_2()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CharacterSpecifics__Alternatives"


    // $ANTLR start "rule__BOOLEAN__Alternatives"
    // InternalMazeDsl.g:891:1: rule__BOOLEAN__Alternatives : ( ( 'true' ) | ( 'false' ) );
    public final void rule__BOOLEAN__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:895:1: ( ( 'true' ) | ( 'false' ) )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==12) ) {
                alt2=1;
            }
            else if ( (LA2_0==13) ) {
                alt2=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // InternalMazeDsl.g:896:2: ( 'true' )
                    {
                    // InternalMazeDsl.g:896:2: ( 'true' )
                    // InternalMazeDsl.g:897:3: 'true'
                    {
                     before(grammarAccess.getBOOLEANAccess().getTrueKeyword_0()); 
                    match(input,12,FOLLOW_2); 
                     after(grammarAccess.getBOOLEANAccess().getTrueKeyword_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:902:2: ( 'false' )
                    {
                    // InternalMazeDsl.g:902:2: ( 'false' )
                    // InternalMazeDsl.g:903:3: 'false'
                    {
                     before(grammarAccess.getBOOLEANAccess().getFalseKeyword_1()); 
                    match(input,13,FOLLOW_2); 
                     after(grammarAccess.getBOOLEANAccess().getFalseKeyword_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__BOOLEAN__Alternatives"


    // $ANTLR start "rule__BOOLEAN_OBJECT__Alternatives"
    // InternalMazeDsl.g:912:1: rule__BOOLEAN_OBJECT__Alternatives : ( ( 'true' ) | ( 'false' ) );
    public final void rule__BOOLEAN_OBJECT__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:916:1: ( ( 'true' ) | ( 'false' ) )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==12) ) {
                alt3=1;
            }
            else if ( (LA3_0==13) ) {
                alt3=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // InternalMazeDsl.g:917:2: ( 'true' )
                    {
                    // InternalMazeDsl.g:917:2: ( 'true' )
                    // InternalMazeDsl.g:918:3: 'true'
                    {
                     before(grammarAccess.getBOOLEAN_OBJECTAccess().getTrueKeyword_0()); 
                    match(input,12,FOLLOW_2); 
                     after(grammarAccess.getBOOLEAN_OBJECTAccess().getTrueKeyword_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:923:2: ( 'false' )
                    {
                    // InternalMazeDsl.g:923:2: ( 'false' )
                    // InternalMazeDsl.g:924:3: 'false'
                    {
                     before(grammarAccess.getBOOLEAN_OBJECTAccess().getFalseKeyword_1()); 
                    match(input,13,FOLLOW_2); 
                     after(grammarAccess.getBOOLEAN_OBJECTAccess().getFalseKeyword_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__BOOLEAN_OBJECT__Alternatives"


    // $ANTLR start "rule__DifficultyLevel__Alternatives"
    // InternalMazeDsl.g:933:1: rule__DifficultyLevel__Alternatives : ( ( ( 'easy' ) ) | ( ( 'normal' ) ) | ( ( 'hard' ) ) );
    public final void rule__DifficultyLevel__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:937:1: ( ( ( 'easy' ) ) | ( ( 'normal' ) ) | ( ( 'hard' ) ) )
            int alt4=3;
            switch ( input.LA(1) ) {
            case 14:
                {
                alt4=1;
                }
                break;
            case 15:
                {
                alt4=2;
                }
                break;
            case 16:
                {
                alt4=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }

            switch (alt4) {
                case 1 :
                    // InternalMazeDsl.g:938:2: ( ( 'easy' ) )
                    {
                    // InternalMazeDsl.g:938:2: ( ( 'easy' ) )
                    // InternalMazeDsl.g:939:3: ( 'easy' )
                    {
                     before(grammarAccess.getDifficultyLevelAccess().getEASYEnumLiteralDeclaration_0()); 
                    // InternalMazeDsl.g:940:3: ( 'easy' )
                    // InternalMazeDsl.g:940:4: 'easy'
                    {
                    match(input,14,FOLLOW_2); 

                    }

                     after(grammarAccess.getDifficultyLevelAccess().getEASYEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:944:2: ( ( 'normal' ) )
                    {
                    // InternalMazeDsl.g:944:2: ( ( 'normal' ) )
                    // InternalMazeDsl.g:945:3: ( 'normal' )
                    {
                     before(grammarAccess.getDifficultyLevelAccess().getNORMALEnumLiteralDeclaration_1()); 
                    // InternalMazeDsl.g:946:3: ( 'normal' )
                    // InternalMazeDsl.g:946:4: 'normal'
                    {
                    match(input,15,FOLLOW_2); 

                    }

                     after(grammarAccess.getDifficultyLevelAccess().getNORMALEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalMazeDsl.g:950:2: ( ( 'hard' ) )
                    {
                    // InternalMazeDsl.g:950:2: ( ( 'hard' ) )
                    // InternalMazeDsl.g:951:3: ( 'hard' )
                    {
                     before(grammarAccess.getDifficultyLevelAccess().getHARDEnumLiteralDeclaration_2()); 
                    // InternalMazeDsl.g:952:3: ( 'hard' )
                    // InternalMazeDsl.g:952:4: 'hard'
                    {
                    match(input,16,FOLLOW_2); 

                    }

                     after(grammarAccess.getDifficultyLevelAccess().getHARDEnumLiteralDeclaration_2()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyLevel__Alternatives"


    // $ANTLR start "rule__EnemyType__Alternatives"
    // InternalMazeDsl.g:960:1: rule__EnemyType__Alternatives : ( ( ( 'zombie' ) ) | ( ( 'ghost' ) ) | ( ( 'pumpkinbomber' ) ) );
    public final void rule__EnemyType__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:964:1: ( ( ( 'zombie' ) ) | ( ( 'ghost' ) ) | ( ( 'pumpkinbomber' ) ) )
            int alt5=3;
            switch ( input.LA(1) ) {
            case 17:
                {
                alt5=1;
                }
                break;
            case 18:
                {
                alt5=2;
                }
                break;
            case 19:
                {
                alt5=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }

            switch (alt5) {
                case 1 :
                    // InternalMazeDsl.g:965:2: ( ( 'zombie' ) )
                    {
                    // InternalMazeDsl.g:965:2: ( ( 'zombie' ) )
                    // InternalMazeDsl.g:966:3: ( 'zombie' )
                    {
                     before(grammarAccess.getEnemyTypeAccess().getZOMBIEEnumLiteralDeclaration_0()); 
                    // InternalMazeDsl.g:967:3: ( 'zombie' )
                    // InternalMazeDsl.g:967:4: 'zombie'
                    {
                    match(input,17,FOLLOW_2); 

                    }

                     after(grammarAccess.getEnemyTypeAccess().getZOMBIEEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:971:2: ( ( 'ghost' ) )
                    {
                    // InternalMazeDsl.g:971:2: ( ( 'ghost' ) )
                    // InternalMazeDsl.g:972:3: ( 'ghost' )
                    {
                     before(grammarAccess.getEnemyTypeAccess().getGHOSTEnumLiteralDeclaration_1()); 
                    // InternalMazeDsl.g:973:3: ( 'ghost' )
                    // InternalMazeDsl.g:973:4: 'ghost'
                    {
                    match(input,18,FOLLOW_2); 

                    }

                     after(grammarAccess.getEnemyTypeAccess().getGHOSTEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalMazeDsl.g:977:2: ( ( 'pumpkinbomber' ) )
                    {
                    // InternalMazeDsl.g:977:2: ( ( 'pumpkinbomber' ) )
                    // InternalMazeDsl.g:978:3: ( 'pumpkinbomber' )
                    {
                     before(grammarAccess.getEnemyTypeAccess().getPUMPKINBOMBEREnumLiteralDeclaration_2()); 
                    // InternalMazeDsl.g:979:3: ( 'pumpkinbomber' )
                    // InternalMazeDsl.g:979:4: 'pumpkinbomber'
                    {
                    match(input,19,FOLLOW_2); 

                    }

                     after(grammarAccess.getEnemyTypeAccess().getPUMPKINBOMBEREnumLiteralDeclaration_2()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EnemyType__Alternatives"


    // $ANTLR start "rule__CharacterTypeEnum__Alternatives"
    // InternalMazeDsl.g:987:1: rule__CharacterTypeEnum__Alternatives : ( ( ( 'zombie' ) ) | ( ( 'ghost' ) ) | ( ( 'pumpkinbomber' ) ) );
    public final void rule__CharacterTypeEnum__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:991:1: ( ( ( 'zombie' ) ) | ( ( 'ghost' ) ) | ( ( 'pumpkinbomber' ) ) )
            int alt6=3;
            switch ( input.LA(1) ) {
            case 17:
                {
                alt6=1;
                }
                break;
            case 18:
                {
                alt6=2;
                }
                break;
            case 19:
                {
                alt6=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }

            switch (alt6) {
                case 1 :
                    // InternalMazeDsl.g:992:2: ( ( 'zombie' ) )
                    {
                    // InternalMazeDsl.g:992:2: ( ( 'zombie' ) )
                    // InternalMazeDsl.g:993:3: ( 'zombie' )
                    {
                     before(grammarAccess.getCharacterTypeEnumAccess().getZOMBIEEnumLiteralDeclaration_0()); 
                    // InternalMazeDsl.g:994:3: ( 'zombie' )
                    // InternalMazeDsl.g:994:4: 'zombie'
                    {
                    match(input,17,FOLLOW_2); 

                    }

                     after(grammarAccess.getCharacterTypeEnumAccess().getZOMBIEEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:998:2: ( ( 'ghost' ) )
                    {
                    // InternalMazeDsl.g:998:2: ( ( 'ghost' ) )
                    // InternalMazeDsl.g:999:3: ( 'ghost' )
                    {
                     before(grammarAccess.getCharacterTypeEnumAccess().getGHOSTEnumLiteralDeclaration_1()); 
                    // InternalMazeDsl.g:1000:3: ( 'ghost' )
                    // InternalMazeDsl.g:1000:4: 'ghost'
                    {
                    match(input,18,FOLLOW_2); 

                    }

                     after(grammarAccess.getCharacterTypeEnumAccess().getGHOSTEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalMazeDsl.g:1004:2: ( ( 'pumpkinbomber' ) )
                    {
                    // InternalMazeDsl.g:1004:2: ( ( 'pumpkinbomber' ) )
                    // InternalMazeDsl.g:1005:3: ( 'pumpkinbomber' )
                    {
                     before(grammarAccess.getCharacterTypeEnumAccess().getPUMPKINBOMBEREnumLiteralDeclaration_2()); 
                    // InternalMazeDsl.g:1006:3: ( 'pumpkinbomber' )
                    // InternalMazeDsl.g:1006:4: 'pumpkinbomber'
                    {
                    match(input,19,FOLLOW_2); 

                    }

                     after(grammarAccess.getCharacterTypeEnumAccess().getPUMPKINBOMBEREnumLiteralDeclaration_2()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CharacterTypeEnum__Alternatives"


    // $ANTLR start "rule__BehaviorTypeEnum__Alternatives"
    // InternalMazeDsl.g:1014:1: rule__BehaviorTypeEnum__Alternatives : ( ( ( 'passive' ) ) | ( ( 'wander' ) ) | ( ( 'aggressive' ) ) | ( ( 'patrol' ) ) );
    public final void rule__BehaviorTypeEnum__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1018:1: ( ( ( 'passive' ) ) | ( ( 'wander' ) ) | ( ( 'aggressive' ) ) | ( ( 'patrol' ) ) )
            int alt7=4;
            switch ( input.LA(1) ) {
            case 20:
                {
                alt7=1;
                }
                break;
            case 21:
                {
                alt7=2;
                }
                break;
            case 22:
                {
                alt7=3;
                }
                break;
            case 23:
                {
                alt7=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }

            switch (alt7) {
                case 1 :
                    // InternalMazeDsl.g:1019:2: ( ( 'passive' ) )
                    {
                    // InternalMazeDsl.g:1019:2: ( ( 'passive' ) )
                    // InternalMazeDsl.g:1020:3: ( 'passive' )
                    {
                     before(grammarAccess.getBehaviorTypeEnumAccess().getPASSIVEEnumLiteralDeclaration_0()); 
                    // InternalMazeDsl.g:1021:3: ( 'passive' )
                    // InternalMazeDsl.g:1021:4: 'passive'
                    {
                    match(input,20,FOLLOW_2); 

                    }

                     after(grammarAccess.getBehaviorTypeEnumAccess().getPASSIVEEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:1025:2: ( ( 'wander' ) )
                    {
                    // InternalMazeDsl.g:1025:2: ( ( 'wander' ) )
                    // InternalMazeDsl.g:1026:3: ( 'wander' )
                    {
                     before(grammarAccess.getBehaviorTypeEnumAccess().getWANDEREnumLiteralDeclaration_1()); 
                    // InternalMazeDsl.g:1027:3: ( 'wander' )
                    // InternalMazeDsl.g:1027:4: 'wander'
                    {
                    match(input,21,FOLLOW_2); 

                    }

                     after(grammarAccess.getBehaviorTypeEnumAccess().getWANDEREnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalMazeDsl.g:1031:2: ( ( 'aggressive' ) )
                    {
                    // InternalMazeDsl.g:1031:2: ( ( 'aggressive' ) )
                    // InternalMazeDsl.g:1032:3: ( 'aggressive' )
                    {
                     before(grammarAccess.getBehaviorTypeEnumAccess().getAGGRESSIVEEnumLiteralDeclaration_2()); 
                    // InternalMazeDsl.g:1033:3: ( 'aggressive' )
                    // InternalMazeDsl.g:1033:4: 'aggressive'
                    {
                    match(input,22,FOLLOW_2); 

                    }

                     after(grammarAccess.getBehaviorTypeEnumAccess().getAGGRESSIVEEnumLiteralDeclaration_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalMazeDsl.g:1037:2: ( ( 'patrol' ) )
                    {
                    // InternalMazeDsl.g:1037:2: ( ( 'patrol' ) )
                    // InternalMazeDsl.g:1038:3: ( 'patrol' )
                    {
                     before(grammarAccess.getBehaviorTypeEnumAccess().getPATROLEnumLiteralDeclaration_3()); 
                    // InternalMazeDsl.g:1039:3: ( 'patrol' )
                    // InternalMazeDsl.g:1039:4: 'patrol'
                    {
                    match(input,23,FOLLOW_2); 

                    }

                     after(grammarAccess.getBehaviorTypeEnumAccess().getPATROLEnumLiteralDeclaration_3()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__BehaviorTypeEnum__Alternatives"


    // $ANTLR start "rule__ProjectileTypeEnum__Alternatives"
    // InternalMazeDsl.g:1047:1: rule__ProjectileTypeEnum__Alternatives : ( ( ( 'straight' ) ) | ( ( 'lob' ) ) | ( ( 'beam' ) ) );
    public final void rule__ProjectileTypeEnum__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1051:1: ( ( ( 'straight' ) ) | ( ( 'lob' ) ) | ( ( 'beam' ) ) )
            int alt8=3;
            switch ( input.LA(1) ) {
            case 24:
                {
                alt8=1;
                }
                break;
            case 25:
                {
                alt8=2;
                }
                break;
            case 26:
                {
                alt8=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }

            switch (alt8) {
                case 1 :
                    // InternalMazeDsl.g:1052:2: ( ( 'straight' ) )
                    {
                    // InternalMazeDsl.g:1052:2: ( ( 'straight' ) )
                    // InternalMazeDsl.g:1053:3: ( 'straight' )
                    {
                     before(grammarAccess.getProjectileTypeEnumAccess().getSTRAIGHTEnumLiteralDeclaration_0()); 
                    // InternalMazeDsl.g:1054:3: ( 'straight' )
                    // InternalMazeDsl.g:1054:4: 'straight'
                    {
                    match(input,24,FOLLOW_2); 

                    }

                     after(grammarAccess.getProjectileTypeEnumAccess().getSTRAIGHTEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:1058:2: ( ( 'lob' ) )
                    {
                    // InternalMazeDsl.g:1058:2: ( ( 'lob' ) )
                    // InternalMazeDsl.g:1059:3: ( 'lob' )
                    {
                     before(grammarAccess.getProjectileTypeEnumAccess().getLOBEnumLiteralDeclaration_1()); 
                    // InternalMazeDsl.g:1060:3: ( 'lob' )
                    // InternalMazeDsl.g:1060:4: 'lob'
                    {
                    match(input,25,FOLLOW_2); 

                    }

                     after(grammarAccess.getProjectileTypeEnumAccess().getLOBEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalMazeDsl.g:1064:2: ( ( 'beam' ) )
                    {
                    // InternalMazeDsl.g:1064:2: ( ( 'beam' ) )
                    // InternalMazeDsl.g:1065:3: ( 'beam' )
                    {
                     before(grammarAccess.getProjectileTypeEnumAccess().getBEAMEnumLiteralDeclaration_2()); 
                    // InternalMazeDsl.g:1066:3: ( 'beam' )
                    // InternalMazeDsl.g:1066:4: 'beam'
                    {
                    match(input,26,FOLLOW_2); 

                    }

                     after(grammarAccess.getProjectileTypeEnumAccess().getBEAMEnumLiteralDeclaration_2()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectileTypeEnum__Alternatives"


    // $ANTLR start "rule__LootItemTypeEnum__Alternatives"
    // InternalMazeDsl.g:1074:1: rule__LootItemTypeEnum__Alternatives : ( ( ( 'food' ) ) | ( ( 'bomb' ) ) | ( ( 'trap' ) ) | ( ( 'weapon' ) ) );
    public final void rule__LootItemTypeEnum__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1078:1: ( ( ( 'food' ) ) | ( ( 'bomb' ) ) | ( ( 'trap' ) ) | ( ( 'weapon' ) ) )
            int alt9=4;
            switch ( input.LA(1) ) {
            case 27:
                {
                alt9=1;
                }
                break;
            case 28:
                {
                alt9=2;
                }
                break;
            case 29:
                {
                alt9=3;
                }
                break;
            case 30:
                {
                alt9=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }

            switch (alt9) {
                case 1 :
                    // InternalMazeDsl.g:1079:2: ( ( 'food' ) )
                    {
                    // InternalMazeDsl.g:1079:2: ( ( 'food' ) )
                    // InternalMazeDsl.g:1080:3: ( 'food' )
                    {
                     before(grammarAccess.getLootItemTypeEnumAccess().getFOODEnumLiteralDeclaration_0()); 
                    // InternalMazeDsl.g:1081:3: ( 'food' )
                    // InternalMazeDsl.g:1081:4: 'food'
                    {
                    match(input,27,FOLLOW_2); 

                    }

                     after(grammarAccess.getLootItemTypeEnumAccess().getFOODEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:1085:2: ( ( 'bomb' ) )
                    {
                    // InternalMazeDsl.g:1085:2: ( ( 'bomb' ) )
                    // InternalMazeDsl.g:1086:3: ( 'bomb' )
                    {
                     before(grammarAccess.getLootItemTypeEnumAccess().getBOMBEnumLiteralDeclaration_1()); 
                    // InternalMazeDsl.g:1087:3: ( 'bomb' )
                    // InternalMazeDsl.g:1087:4: 'bomb'
                    {
                    match(input,28,FOLLOW_2); 

                    }

                     after(grammarAccess.getLootItemTypeEnumAccess().getBOMBEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalMazeDsl.g:1091:2: ( ( 'trap' ) )
                    {
                    // InternalMazeDsl.g:1091:2: ( ( 'trap' ) )
                    // InternalMazeDsl.g:1092:3: ( 'trap' )
                    {
                     before(grammarAccess.getLootItemTypeEnumAccess().getTRAPEnumLiteralDeclaration_2()); 
                    // InternalMazeDsl.g:1093:3: ( 'trap' )
                    // InternalMazeDsl.g:1093:4: 'trap'
                    {
                    match(input,29,FOLLOW_2); 

                    }

                     after(grammarAccess.getLootItemTypeEnumAccess().getTRAPEnumLiteralDeclaration_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalMazeDsl.g:1097:2: ( ( 'weapon' ) )
                    {
                    // InternalMazeDsl.g:1097:2: ( ( 'weapon' ) )
                    // InternalMazeDsl.g:1098:3: ( 'weapon' )
                    {
                     before(grammarAccess.getLootItemTypeEnumAccess().getWEAPONEnumLiteralDeclaration_3()); 
                    // InternalMazeDsl.g:1099:3: ( 'weapon' )
                    // InternalMazeDsl.g:1099:4: 'weapon'
                    {
                    match(input,30,FOLLOW_2); 

                    }

                     after(grammarAccess.getLootItemTypeEnumAccess().getWEAPONEnumLiteralDeclaration_3()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootItemTypeEnum__Alternatives"


    // $ANTLR start "rule__GameConfiguration__Group__0"
    // InternalMazeDsl.g:1107:1: rule__GameConfiguration__Group__0 : rule__GameConfiguration__Group__0__Impl rule__GameConfiguration__Group__1 ;
    public final void rule__GameConfiguration__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1111:1: ( rule__GameConfiguration__Group__0__Impl rule__GameConfiguration__Group__1 )
            // InternalMazeDsl.g:1112:2: rule__GameConfiguration__Group__0__Impl rule__GameConfiguration__Group__1
            {
            pushFollow(FOLLOW_3);
            rule__GameConfiguration__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__GameConfiguration__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GameConfiguration__Group__0"


    // $ANTLR start "rule__GameConfiguration__Group__0__Impl"
    // InternalMazeDsl.g:1119:1: rule__GameConfiguration__Group__0__Impl : ( 'game' ) ;
    public final void rule__GameConfiguration__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1123:1: ( ( 'game' ) )
            // InternalMazeDsl.g:1124:1: ( 'game' )
            {
            // InternalMazeDsl.g:1124:1: ( 'game' )
            // InternalMazeDsl.g:1125:2: 'game'
            {
             before(grammarAccess.getGameConfigurationAccess().getGameKeyword_0()); 
            match(input,31,FOLLOW_2); 
             after(grammarAccess.getGameConfigurationAccess().getGameKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GameConfiguration__Group__0__Impl"


    // $ANTLR start "rule__GameConfiguration__Group__1"
    // InternalMazeDsl.g:1134:1: rule__GameConfiguration__Group__1 : rule__GameConfiguration__Group__1__Impl rule__GameConfiguration__Group__2 ;
    public final void rule__GameConfiguration__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1138:1: ( rule__GameConfiguration__Group__1__Impl rule__GameConfiguration__Group__2 )
            // InternalMazeDsl.g:1139:2: rule__GameConfiguration__Group__1__Impl rule__GameConfiguration__Group__2
            {
            pushFollow(FOLLOW_4);
            rule__GameConfiguration__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__GameConfiguration__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GameConfiguration__Group__1"


    // $ANTLR start "rule__GameConfiguration__Group__1__Impl"
    // InternalMazeDsl.g:1146:1: rule__GameConfiguration__Group__1__Impl : ( ( rule__GameConfiguration__NameAssignment_1 ) ) ;
    public final void rule__GameConfiguration__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1150:1: ( ( ( rule__GameConfiguration__NameAssignment_1 ) ) )
            // InternalMazeDsl.g:1151:1: ( ( rule__GameConfiguration__NameAssignment_1 ) )
            {
            // InternalMazeDsl.g:1151:1: ( ( rule__GameConfiguration__NameAssignment_1 ) )
            // InternalMazeDsl.g:1152:2: ( rule__GameConfiguration__NameAssignment_1 )
            {
             before(grammarAccess.getGameConfigurationAccess().getNameAssignment_1()); 
            // InternalMazeDsl.g:1153:2: ( rule__GameConfiguration__NameAssignment_1 )
            // InternalMazeDsl.g:1153:3: rule__GameConfiguration__NameAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__GameConfiguration__NameAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getGameConfigurationAccess().getNameAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GameConfiguration__Group__1__Impl"


    // $ANTLR start "rule__GameConfiguration__Group__2"
    // InternalMazeDsl.g:1161:1: rule__GameConfiguration__Group__2 : rule__GameConfiguration__Group__2__Impl rule__GameConfiguration__Group__3 ;
    public final void rule__GameConfiguration__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1165:1: ( rule__GameConfiguration__Group__2__Impl rule__GameConfiguration__Group__3 )
            // InternalMazeDsl.g:1166:2: rule__GameConfiguration__Group__2__Impl rule__GameConfiguration__Group__3
            {
            pushFollow(FOLLOW_5);
            rule__GameConfiguration__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__GameConfiguration__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GameConfiguration__Group__2"


    // $ANTLR start "rule__GameConfiguration__Group__2__Impl"
    // InternalMazeDsl.g:1173:1: rule__GameConfiguration__Group__2__Impl : ( '{' ) ;
    public final void rule__GameConfiguration__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1177:1: ( ( '{' ) )
            // InternalMazeDsl.g:1178:1: ( '{' )
            {
            // InternalMazeDsl.g:1178:1: ( '{' )
            // InternalMazeDsl.g:1179:2: '{'
            {
             before(grammarAccess.getGameConfigurationAccess().getLeftCurlyBracketKeyword_2()); 
            match(input,32,FOLLOW_2); 
             after(grammarAccess.getGameConfigurationAccess().getLeftCurlyBracketKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GameConfiguration__Group__2__Impl"


    // $ANTLR start "rule__GameConfiguration__Group__3"
    // InternalMazeDsl.g:1188:1: rule__GameConfiguration__Group__3 : rule__GameConfiguration__Group__3__Impl rule__GameConfiguration__Group__4 ;
    public final void rule__GameConfiguration__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1192:1: ( rule__GameConfiguration__Group__3__Impl rule__GameConfiguration__Group__4 )
            // InternalMazeDsl.g:1193:2: rule__GameConfiguration__Group__3__Impl rule__GameConfiguration__Group__4
            {
            pushFollow(FOLLOW_5);
            rule__GameConfiguration__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__GameConfiguration__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GameConfiguration__Group__3"


    // $ANTLR start "rule__GameConfiguration__Group__3__Impl"
    // InternalMazeDsl.g:1200:1: rule__GameConfiguration__Group__3__Impl : ( ( rule__GameConfiguration__ImportsAssignment_3 )* ) ;
    public final void rule__GameConfiguration__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1204:1: ( ( ( rule__GameConfiguration__ImportsAssignment_3 )* ) )
            // InternalMazeDsl.g:1205:1: ( ( rule__GameConfiguration__ImportsAssignment_3 )* )
            {
            // InternalMazeDsl.g:1205:1: ( ( rule__GameConfiguration__ImportsAssignment_3 )* )
            // InternalMazeDsl.g:1206:2: ( rule__GameConfiguration__ImportsAssignment_3 )*
            {
             before(grammarAccess.getGameConfigurationAccess().getImportsAssignment_3()); 
            // InternalMazeDsl.g:1207:2: ( rule__GameConfiguration__ImportsAssignment_3 )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==34) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // InternalMazeDsl.g:1207:3: rule__GameConfiguration__ImportsAssignment_3
            	    {
            	    pushFollow(FOLLOW_6);
            	    rule__GameConfiguration__ImportsAssignment_3();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop10;
                }
            } while (true);

             after(grammarAccess.getGameConfigurationAccess().getImportsAssignment_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GameConfiguration__Group__3__Impl"


    // $ANTLR start "rule__GameConfiguration__Group__4"
    // InternalMazeDsl.g:1215:1: rule__GameConfiguration__Group__4 : rule__GameConfiguration__Group__4__Impl rule__GameConfiguration__Group__5 ;
    public final void rule__GameConfiguration__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1219:1: ( rule__GameConfiguration__Group__4__Impl rule__GameConfiguration__Group__5 )
            // InternalMazeDsl.g:1220:2: rule__GameConfiguration__Group__4__Impl rule__GameConfiguration__Group__5
            {
            pushFollow(FOLLOW_5);
            rule__GameConfiguration__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__GameConfiguration__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GameConfiguration__Group__4"


    // $ANTLR start "rule__GameConfiguration__Group__4__Impl"
    // InternalMazeDsl.g:1227:1: rule__GameConfiguration__Group__4__Impl : ( ( rule__GameConfiguration__DifficultyAssignment_4 )? ) ;
    public final void rule__GameConfiguration__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1231:1: ( ( ( rule__GameConfiguration__DifficultyAssignment_4 )? ) )
            // InternalMazeDsl.g:1232:1: ( ( rule__GameConfiguration__DifficultyAssignment_4 )? )
            {
            // InternalMazeDsl.g:1232:1: ( ( rule__GameConfiguration__DifficultyAssignment_4 )? )
            // InternalMazeDsl.g:1233:2: ( rule__GameConfiguration__DifficultyAssignment_4 )?
            {
             before(grammarAccess.getGameConfigurationAccess().getDifficultyAssignment_4()); 
            // InternalMazeDsl.g:1234:2: ( rule__GameConfiguration__DifficultyAssignment_4 )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==35) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // InternalMazeDsl.g:1234:3: rule__GameConfiguration__DifficultyAssignment_4
                    {
                    pushFollow(FOLLOW_2);
                    rule__GameConfiguration__DifficultyAssignment_4();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getGameConfigurationAccess().getDifficultyAssignment_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GameConfiguration__Group__4__Impl"


    // $ANTLR start "rule__GameConfiguration__Group__5"
    // InternalMazeDsl.g:1242:1: rule__GameConfiguration__Group__5 : rule__GameConfiguration__Group__5__Impl rule__GameConfiguration__Group__6 ;
    public final void rule__GameConfiguration__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1246:1: ( rule__GameConfiguration__Group__5__Impl rule__GameConfiguration__Group__6 )
            // InternalMazeDsl.g:1247:2: rule__GameConfiguration__Group__5__Impl rule__GameConfiguration__Group__6
            {
            pushFollow(FOLLOW_5);
            rule__GameConfiguration__Group__5__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__GameConfiguration__Group__6();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GameConfiguration__Group__5"


    // $ANTLR start "rule__GameConfiguration__Group__5__Impl"
    // InternalMazeDsl.g:1254:1: rule__GameConfiguration__Group__5__Impl : ( ( rule__GameConfiguration__OpponentsAssignment_5 )* ) ;
    public final void rule__GameConfiguration__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1258:1: ( ( ( rule__GameConfiguration__OpponentsAssignment_5 )* ) )
            // InternalMazeDsl.g:1259:1: ( ( rule__GameConfiguration__OpponentsAssignment_5 )* )
            {
            // InternalMazeDsl.g:1259:1: ( ( rule__GameConfiguration__OpponentsAssignment_5 )* )
            // InternalMazeDsl.g:1260:2: ( rule__GameConfiguration__OpponentsAssignment_5 )*
            {
             before(grammarAccess.getGameConfigurationAccess().getOpponentsAssignment_5()); 
            // InternalMazeDsl.g:1261:2: ( rule__GameConfiguration__OpponentsAssignment_5 )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==43) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // InternalMazeDsl.g:1261:3: rule__GameConfiguration__OpponentsAssignment_5
            	    {
            	    pushFollow(FOLLOW_7);
            	    rule__GameConfiguration__OpponentsAssignment_5();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop12;
                }
            } while (true);

             after(grammarAccess.getGameConfigurationAccess().getOpponentsAssignment_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GameConfiguration__Group__5__Impl"


    // $ANTLR start "rule__GameConfiguration__Group__6"
    // InternalMazeDsl.g:1269:1: rule__GameConfiguration__Group__6 : rule__GameConfiguration__Group__6__Impl rule__GameConfiguration__Group__7 ;
    public final void rule__GameConfiguration__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1273:1: ( rule__GameConfiguration__Group__6__Impl rule__GameConfiguration__Group__7 )
            // InternalMazeDsl.g:1274:2: rule__GameConfiguration__Group__6__Impl rule__GameConfiguration__Group__7
            {
            pushFollow(FOLLOW_5);
            rule__GameConfiguration__Group__6__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__GameConfiguration__Group__7();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GameConfiguration__Group__6"


    // $ANTLR start "rule__GameConfiguration__Group__6__Impl"
    // InternalMazeDsl.g:1281:1: rule__GameConfiguration__Group__6__Impl : ( ( rule__GameConfiguration__PatrolsAssignment_6 )* ) ;
    public final void rule__GameConfiguration__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1285:1: ( ( ( rule__GameConfiguration__PatrolsAssignment_6 )* ) )
            // InternalMazeDsl.g:1286:1: ( ( rule__GameConfiguration__PatrolsAssignment_6 )* )
            {
            // InternalMazeDsl.g:1286:1: ( ( rule__GameConfiguration__PatrolsAssignment_6 )* )
            // InternalMazeDsl.g:1287:2: ( rule__GameConfiguration__PatrolsAssignment_6 )*
            {
             before(grammarAccess.getGameConfigurationAccess().getPatrolsAssignment_6()); 
            // InternalMazeDsl.g:1288:2: ( rule__GameConfiguration__PatrolsAssignment_6 )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==23) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // InternalMazeDsl.g:1288:3: rule__GameConfiguration__PatrolsAssignment_6
            	    {
            	    pushFollow(FOLLOW_8);
            	    rule__GameConfiguration__PatrolsAssignment_6();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop13;
                }
            } while (true);

             after(grammarAccess.getGameConfigurationAccess().getPatrolsAssignment_6()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GameConfiguration__Group__6__Impl"


    // $ANTLR start "rule__GameConfiguration__Group__7"
    // InternalMazeDsl.g:1296:1: rule__GameConfiguration__Group__7 : rule__GameConfiguration__Group__7__Impl rule__GameConfiguration__Group__8 ;
    public final void rule__GameConfiguration__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1300:1: ( rule__GameConfiguration__Group__7__Impl rule__GameConfiguration__Group__8 )
            // InternalMazeDsl.g:1301:2: rule__GameConfiguration__Group__7__Impl rule__GameConfiguration__Group__8
            {
            pushFollow(FOLLOW_5);
            rule__GameConfiguration__Group__7__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__GameConfiguration__Group__8();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GameConfiguration__Group__7"


    // $ANTLR start "rule__GameConfiguration__Group__7__Impl"
    // InternalMazeDsl.g:1308:1: rule__GameConfiguration__Group__7__Impl : ( ( rule__GameConfiguration__LootTablesAssignment_7 )* ) ;
    public final void rule__GameConfiguration__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1312:1: ( ( ( rule__GameConfiguration__LootTablesAssignment_7 )* ) )
            // InternalMazeDsl.g:1313:1: ( ( rule__GameConfiguration__LootTablesAssignment_7 )* )
            {
            // InternalMazeDsl.g:1313:1: ( ( rule__GameConfiguration__LootTablesAssignment_7 )* )
            // InternalMazeDsl.g:1314:2: ( rule__GameConfiguration__LootTablesAssignment_7 )*
            {
             before(grammarAccess.getGameConfigurationAccess().getLootTablesAssignment_7()); 
            // InternalMazeDsl.g:1315:2: ( rule__GameConfiguration__LootTablesAssignment_7 )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0==78) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // InternalMazeDsl.g:1315:3: rule__GameConfiguration__LootTablesAssignment_7
            	    {
            	    pushFollow(FOLLOW_9);
            	    rule__GameConfiguration__LootTablesAssignment_7();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop14;
                }
            } while (true);

             after(grammarAccess.getGameConfigurationAccess().getLootTablesAssignment_7()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GameConfiguration__Group__7__Impl"


    // $ANTLR start "rule__GameConfiguration__Group__8"
    // InternalMazeDsl.g:1323:1: rule__GameConfiguration__Group__8 : rule__GameConfiguration__Group__8__Impl rule__GameConfiguration__Group__9 ;
    public final void rule__GameConfiguration__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1327:1: ( rule__GameConfiguration__Group__8__Impl rule__GameConfiguration__Group__9 )
            // InternalMazeDsl.g:1328:2: rule__GameConfiguration__Group__8__Impl rule__GameConfiguration__Group__9
            {
            pushFollow(FOLLOW_5);
            rule__GameConfiguration__Group__8__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__GameConfiguration__Group__9();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GameConfiguration__Group__8"


    // $ANTLR start "rule__GameConfiguration__Group__8__Impl"
    // InternalMazeDsl.g:1335:1: rule__GameConfiguration__Group__8__Impl : ( ( rule__GameConfiguration__LevelsAssignment_8 )* ) ;
    public final void rule__GameConfiguration__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1339:1: ( ( ( rule__GameConfiguration__LevelsAssignment_8 )* ) )
            // InternalMazeDsl.g:1340:1: ( ( rule__GameConfiguration__LevelsAssignment_8 )* )
            {
            // InternalMazeDsl.g:1340:1: ( ( rule__GameConfiguration__LevelsAssignment_8 )* )
            // InternalMazeDsl.g:1341:2: ( rule__GameConfiguration__LevelsAssignment_8 )*
            {
             before(grammarAccess.getGameConfigurationAccess().getLevelsAssignment_8()); 
            // InternalMazeDsl.g:1342:2: ( rule__GameConfiguration__LevelsAssignment_8 )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( (LA15_0==36) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // InternalMazeDsl.g:1342:3: rule__GameConfiguration__LevelsAssignment_8
            	    {
            	    pushFollow(FOLLOW_10);
            	    rule__GameConfiguration__LevelsAssignment_8();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop15;
                }
            } while (true);

             after(grammarAccess.getGameConfigurationAccess().getLevelsAssignment_8()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GameConfiguration__Group__8__Impl"


    // $ANTLR start "rule__GameConfiguration__Group__9"
    // InternalMazeDsl.g:1350:1: rule__GameConfiguration__Group__9 : rule__GameConfiguration__Group__9__Impl ;
    public final void rule__GameConfiguration__Group__9() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1354:1: ( rule__GameConfiguration__Group__9__Impl )
            // InternalMazeDsl.g:1355:2: rule__GameConfiguration__Group__9__Impl
            {
            pushFollow(FOLLOW_2);
            rule__GameConfiguration__Group__9__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GameConfiguration__Group__9"


    // $ANTLR start "rule__GameConfiguration__Group__9__Impl"
    // InternalMazeDsl.g:1361:1: rule__GameConfiguration__Group__9__Impl : ( '}' ) ;
    public final void rule__GameConfiguration__Group__9__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1365:1: ( ( '}' ) )
            // InternalMazeDsl.g:1366:1: ( '}' )
            {
            // InternalMazeDsl.g:1366:1: ( '}' )
            // InternalMazeDsl.g:1367:2: '}'
            {
             before(grammarAccess.getGameConfigurationAccess().getRightCurlyBracketKeyword_9()); 
            match(input,33,FOLLOW_2); 
             after(grammarAccess.getGameConfigurationAccess().getRightCurlyBracketKeyword_9()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GameConfiguration__Group__9__Impl"


    // $ANTLR start "rule__Import__Group__0"
    // InternalMazeDsl.g:1377:1: rule__Import__Group__0 : rule__Import__Group__0__Impl rule__Import__Group__1 ;
    public final void rule__Import__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1381:1: ( rule__Import__Group__0__Impl rule__Import__Group__1 )
            // InternalMazeDsl.g:1382:2: rule__Import__Group__0__Impl rule__Import__Group__1
            {
            pushFollow(FOLLOW_11);
            rule__Import__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Import__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Import__Group__0"


    // $ANTLR start "rule__Import__Group__0__Impl"
    // InternalMazeDsl.g:1389:1: rule__Import__Group__0__Impl : ( 'import' ) ;
    public final void rule__Import__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1393:1: ( ( 'import' ) )
            // InternalMazeDsl.g:1394:1: ( 'import' )
            {
            // InternalMazeDsl.g:1394:1: ( 'import' )
            // InternalMazeDsl.g:1395:2: 'import'
            {
             before(grammarAccess.getImportAccess().getImportKeyword_0()); 
            match(input,34,FOLLOW_2); 
             after(grammarAccess.getImportAccess().getImportKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Import__Group__0__Impl"


    // $ANTLR start "rule__Import__Group__1"
    // InternalMazeDsl.g:1404:1: rule__Import__Group__1 : rule__Import__Group__1__Impl ;
    public final void rule__Import__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1408:1: ( rule__Import__Group__1__Impl )
            // InternalMazeDsl.g:1409:2: rule__Import__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Import__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Import__Group__1"


    // $ANTLR start "rule__Import__Group__1__Impl"
    // InternalMazeDsl.g:1415:1: rule__Import__Group__1__Impl : ( ( rule__Import__ImportURIAssignment_1 ) ) ;
    public final void rule__Import__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1419:1: ( ( ( rule__Import__ImportURIAssignment_1 ) ) )
            // InternalMazeDsl.g:1420:1: ( ( rule__Import__ImportURIAssignment_1 ) )
            {
            // InternalMazeDsl.g:1420:1: ( ( rule__Import__ImportURIAssignment_1 ) )
            // InternalMazeDsl.g:1421:2: ( rule__Import__ImportURIAssignment_1 )
            {
             before(grammarAccess.getImportAccess().getImportURIAssignment_1()); 
            // InternalMazeDsl.g:1422:2: ( rule__Import__ImportURIAssignment_1 )
            // InternalMazeDsl.g:1422:3: rule__Import__ImportURIAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__Import__ImportURIAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getImportAccess().getImportURIAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Import__Group__1__Impl"


    // $ANTLR start "rule__DifficultyConfig__Group__0"
    // InternalMazeDsl.g:1431:1: rule__DifficultyConfig__Group__0 : rule__DifficultyConfig__Group__0__Impl rule__DifficultyConfig__Group__1 ;
    public final void rule__DifficultyConfig__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1435:1: ( rule__DifficultyConfig__Group__0__Impl rule__DifficultyConfig__Group__1 )
            // InternalMazeDsl.g:1436:2: rule__DifficultyConfig__Group__0__Impl rule__DifficultyConfig__Group__1
            {
            pushFollow(FOLLOW_4);
            rule__DifficultyConfig__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DifficultyConfig__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group__0"


    // $ANTLR start "rule__DifficultyConfig__Group__0__Impl"
    // InternalMazeDsl.g:1443:1: rule__DifficultyConfig__Group__0__Impl : ( 'difficulty' ) ;
    public final void rule__DifficultyConfig__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1447:1: ( ( 'difficulty' ) )
            // InternalMazeDsl.g:1448:1: ( 'difficulty' )
            {
            // InternalMazeDsl.g:1448:1: ( 'difficulty' )
            // InternalMazeDsl.g:1449:2: 'difficulty'
            {
             before(grammarAccess.getDifficultyConfigAccess().getDifficultyKeyword_0()); 
            match(input,35,FOLLOW_2); 
             after(grammarAccess.getDifficultyConfigAccess().getDifficultyKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group__0__Impl"


    // $ANTLR start "rule__DifficultyConfig__Group__1"
    // InternalMazeDsl.g:1458:1: rule__DifficultyConfig__Group__1 : rule__DifficultyConfig__Group__1__Impl rule__DifficultyConfig__Group__2 ;
    public final void rule__DifficultyConfig__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1462:1: ( rule__DifficultyConfig__Group__1__Impl rule__DifficultyConfig__Group__2 )
            // InternalMazeDsl.g:1463:2: rule__DifficultyConfig__Group__1__Impl rule__DifficultyConfig__Group__2
            {
            pushFollow(FOLLOW_12);
            rule__DifficultyConfig__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DifficultyConfig__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group__1"


    // $ANTLR start "rule__DifficultyConfig__Group__1__Impl"
    // InternalMazeDsl.g:1470:1: rule__DifficultyConfig__Group__1__Impl : ( '{' ) ;
    public final void rule__DifficultyConfig__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1474:1: ( ( '{' ) )
            // InternalMazeDsl.g:1475:1: ( '{' )
            {
            // InternalMazeDsl.g:1475:1: ( '{' )
            // InternalMazeDsl.g:1476:2: '{'
            {
             before(grammarAccess.getDifficultyConfigAccess().getLeftCurlyBracketKeyword_1()); 
            match(input,32,FOLLOW_2); 
             after(grammarAccess.getDifficultyConfigAccess().getLeftCurlyBracketKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group__1__Impl"


    // $ANTLR start "rule__DifficultyConfig__Group__2"
    // InternalMazeDsl.g:1485:1: rule__DifficultyConfig__Group__2 : rule__DifficultyConfig__Group__2__Impl rule__DifficultyConfig__Group__3 ;
    public final void rule__DifficultyConfig__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1489:1: ( rule__DifficultyConfig__Group__2__Impl rule__DifficultyConfig__Group__3 )
            // InternalMazeDsl.g:1490:2: rule__DifficultyConfig__Group__2__Impl rule__DifficultyConfig__Group__3
            {
            pushFollow(FOLLOW_13);
            rule__DifficultyConfig__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DifficultyConfig__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group__2"


    // $ANTLR start "rule__DifficultyConfig__Group__2__Impl"
    // InternalMazeDsl.g:1497:1: rule__DifficultyConfig__Group__2__Impl : ( 'level' ) ;
    public final void rule__DifficultyConfig__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1501:1: ( ( 'level' ) )
            // InternalMazeDsl.g:1502:1: ( 'level' )
            {
            // InternalMazeDsl.g:1502:1: ( 'level' )
            // InternalMazeDsl.g:1503:2: 'level'
            {
             before(grammarAccess.getDifficultyConfigAccess().getLevelKeyword_2()); 
            match(input,36,FOLLOW_2); 
             after(grammarAccess.getDifficultyConfigAccess().getLevelKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group__2__Impl"


    // $ANTLR start "rule__DifficultyConfig__Group__3"
    // InternalMazeDsl.g:1512:1: rule__DifficultyConfig__Group__3 : rule__DifficultyConfig__Group__3__Impl rule__DifficultyConfig__Group__4 ;
    public final void rule__DifficultyConfig__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1516:1: ( rule__DifficultyConfig__Group__3__Impl rule__DifficultyConfig__Group__4 )
            // InternalMazeDsl.g:1517:2: rule__DifficultyConfig__Group__3__Impl rule__DifficultyConfig__Group__4
            {
            pushFollow(FOLLOW_14);
            rule__DifficultyConfig__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DifficultyConfig__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group__3"


    // $ANTLR start "rule__DifficultyConfig__Group__3__Impl"
    // InternalMazeDsl.g:1524:1: rule__DifficultyConfig__Group__3__Impl : ( ( rule__DifficultyConfig__LevelAssignment_3 ) ) ;
    public final void rule__DifficultyConfig__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1528:1: ( ( ( rule__DifficultyConfig__LevelAssignment_3 ) ) )
            // InternalMazeDsl.g:1529:1: ( ( rule__DifficultyConfig__LevelAssignment_3 ) )
            {
            // InternalMazeDsl.g:1529:1: ( ( rule__DifficultyConfig__LevelAssignment_3 ) )
            // InternalMazeDsl.g:1530:2: ( rule__DifficultyConfig__LevelAssignment_3 )
            {
             before(grammarAccess.getDifficultyConfigAccess().getLevelAssignment_3()); 
            // InternalMazeDsl.g:1531:2: ( rule__DifficultyConfig__LevelAssignment_3 )
            // InternalMazeDsl.g:1531:3: rule__DifficultyConfig__LevelAssignment_3
            {
            pushFollow(FOLLOW_2);
            rule__DifficultyConfig__LevelAssignment_3();

            state._fsp--;


            }

             after(grammarAccess.getDifficultyConfigAccess().getLevelAssignment_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group__3__Impl"


    // $ANTLR start "rule__DifficultyConfig__Group__4"
    // InternalMazeDsl.g:1539:1: rule__DifficultyConfig__Group__4 : rule__DifficultyConfig__Group__4__Impl rule__DifficultyConfig__Group__5 ;
    public final void rule__DifficultyConfig__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1543:1: ( rule__DifficultyConfig__Group__4__Impl rule__DifficultyConfig__Group__5 )
            // InternalMazeDsl.g:1544:2: rule__DifficultyConfig__Group__4__Impl rule__DifficultyConfig__Group__5
            {
            pushFollow(FOLLOW_14);
            rule__DifficultyConfig__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DifficultyConfig__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group__4"


    // $ANTLR start "rule__DifficultyConfig__Group__4__Impl"
    // InternalMazeDsl.g:1551:1: rule__DifficultyConfig__Group__4__Impl : ( ( rule__DifficultyConfig__Group_4__0 )? ) ;
    public final void rule__DifficultyConfig__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1555:1: ( ( ( rule__DifficultyConfig__Group_4__0 )? ) )
            // InternalMazeDsl.g:1556:1: ( ( rule__DifficultyConfig__Group_4__0 )? )
            {
            // InternalMazeDsl.g:1556:1: ( ( rule__DifficultyConfig__Group_4__0 )? )
            // InternalMazeDsl.g:1557:2: ( rule__DifficultyConfig__Group_4__0 )?
            {
             before(grammarAccess.getDifficultyConfigAccess().getGroup_4()); 
            // InternalMazeDsl.g:1558:2: ( rule__DifficultyConfig__Group_4__0 )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==37) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // InternalMazeDsl.g:1558:3: rule__DifficultyConfig__Group_4__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__DifficultyConfig__Group_4__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getDifficultyConfigAccess().getGroup_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group__4__Impl"


    // $ANTLR start "rule__DifficultyConfig__Group__5"
    // InternalMazeDsl.g:1566:1: rule__DifficultyConfig__Group__5 : rule__DifficultyConfig__Group__5__Impl rule__DifficultyConfig__Group__6 ;
    public final void rule__DifficultyConfig__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1570:1: ( rule__DifficultyConfig__Group__5__Impl rule__DifficultyConfig__Group__6 )
            // InternalMazeDsl.g:1571:2: rule__DifficultyConfig__Group__5__Impl rule__DifficultyConfig__Group__6
            {
            pushFollow(FOLLOW_14);
            rule__DifficultyConfig__Group__5__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DifficultyConfig__Group__6();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group__5"


    // $ANTLR start "rule__DifficultyConfig__Group__5__Impl"
    // InternalMazeDsl.g:1578:1: rule__DifficultyConfig__Group__5__Impl : ( ( rule__DifficultyConfig__Group_5__0 )? ) ;
    public final void rule__DifficultyConfig__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1582:1: ( ( ( rule__DifficultyConfig__Group_5__0 )? ) )
            // InternalMazeDsl.g:1583:1: ( ( rule__DifficultyConfig__Group_5__0 )? )
            {
            // InternalMazeDsl.g:1583:1: ( ( rule__DifficultyConfig__Group_5__0 )? )
            // InternalMazeDsl.g:1584:2: ( rule__DifficultyConfig__Group_5__0 )?
            {
             before(grammarAccess.getDifficultyConfigAccess().getGroup_5()); 
            // InternalMazeDsl.g:1585:2: ( rule__DifficultyConfig__Group_5__0 )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==38) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // InternalMazeDsl.g:1585:3: rule__DifficultyConfig__Group_5__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__DifficultyConfig__Group_5__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getDifficultyConfigAccess().getGroup_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group__5__Impl"


    // $ANTLR start "rule__DifficultyConfig__Group__6"
    // InternalMazeDsl.g:1593:1: rule__DifficultyConfig__Group__6 : rule__DifficultyConfig__Group__6__Impl rule__DifficultyConfig__Group__7 ;
    public final void rule__DifficultyConfig__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1597:1: ( rule__DifficultyConfig__Group__6__Impl rule__DifficultyConfig__Group__7 )
            // InternalMazeDsl.g:1598:2: rule__DifficultyConfig__Group__6__Impl rule__DifficultyConfig__Group__7
            {
            pushFollow(FOLLOW_14);
            rule__DifficultyConfig__Group__6__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DifficultyConfig__Group__7();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group__6"


    // $ANTLR start "rule__DifficultyConfig__Group__6__Impl"
    // InternalMazeDsl.g:1605:1: rule__DifficultyConfig__Group__6__Impl : ( ( rule__DifficultyConfig__Group_6__0 )? ) ;
    public final void rule__DifficultyConfig__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1609:1: ( ( ( rule__DifficultyConfig__Group_6__0 )? ) )
            // InternalMazeDsl.g:1610:1: ( ( rule__DifficultyConfig__Group_6__0 )? )
            {
            // InternalMazeDsl.g:1610:1: ( ( rule__DifficultyConfig__Group_6__0 )? )
            // InternalMazeDsl.g:1611:2: ( rule__DifficultyConfig__Group_6__0 )?
            {
             before(grammarAccess.getDifficultyConfigAccess().getGroup_6()); 
            // InternalMazeDsl.g:1612:2: ( rule__DifficultyConfig__Group_6__0 )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==39) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // InternalMazeDsl.g:1612:3: rule__DifficultyConfig__Group_6__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__DifficultyConfig__Group_6__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getDifficultyConfigAccess().getGroup_6()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group__6__Impl"


    // $ANTLR start "rule__DifficultyConfig__Group__7"
    // InternalMazeDsl.g:1620:1: rule__DifficultyConfig__Group__7 : rule__DifficultyConfig__Group__7__Impl rule__DifficultyConfig__Group__8 ;
    public final void rule__DifficultyConfig__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1624:1: ( rule__DifficultyConfig__Group__7__Impl rule__DifficultyConfig__Group__8 )
            // InternalMazeDsl.g:1625:2: rule__DifficultyConfig__Group__7__Impl rule__DifficultyConfig__Group__8
            {
            pushFollow(FOLLOW_14);
            rule__DifficultyConfig__Group__7__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DifficultyConfig__Group__8();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group__7"


    // $ANTLR start "rule__DifficultyConfig__Group__7__Impl"
    // InternalMazeDsl.g:1632:1: rule__DifficultyConfig__Group__7__Impl : ( ( rule__DifficultyConfig__Group_7__0 )? ) ;
    public final void rule__DifficultyConfig__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1636:1: ( ( ( rule__DifficultyConfig__Group_7__0 )? ) )
            // InternalMazeDsl.g:1637:1: ( ( rule__DifficultyConfig__Group_7__0 )? )
            {
            // InternalMazeDsl.g:1637:1: ( ( rule__DifficultyConfig__Group_7__0 )? )
            // InternalMazeDsl.g:1638:2: ( rule__DifficultyConfig__Group_7__0 )?
            {
             before(grammarAccess.getDifficultyConfigAccess().getGroup_7()); 
            // InternalMazeDsl.g:1639:2: ( rule__DifficultyConfig__Group_7__0 )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==40) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // InternalMazeDsl.g:1639:3: rule__DifficultyConfig__Group_7__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__DifficultyConfig__Group_7__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getDifficultyConfigAccess().getGroup_7()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group__7__Impl"


    // $ANTLR start "rule__DifficultyConfig__Group__8"
    // InternalMazeDsl.g:1647:1: rule__DifficultyConfig__Group__8 : rule__DifficultyConfig__Group__8__Impl rule__DifficultyConfig__Group__9 ;
    public final void rule__DifficultyConfig__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1651:1: ( rule__DifficultyConfig__Group__8__Impl rule__DifficultyConfig__Group__9 )
            // InternalMazeDsl.g:1652:2: rule__DifficultyConfig__Group__8__Impl rule__DifficultyConfig__Group__9
            {
            pushFollow(FOLLOW_14);
            rule__DifficultyConfig__Group__8__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DifficultyConfig__Group__9();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group__8"


    // $ANTLR start "rule__DifficultyConfig__Group__8__Impl"
    // InternalMazeDsl.g:1659:1: rule__DifficultyConfig__Group__8__Impl : ( ( rule__DifficultyConfig__EnemyLimitsAssignment_8 )* ) ;
    public final void rule__DifficultyConfig__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1663:1: ( ( ( rule__DifficultyConfig__EnemyLimitsAssignment_8 )* ) )
            // InternalMazeDsl.g:1664:1: ( ( rule__DifficultyConfig__EnemyLimitsAssignment_8 )* )
            {
            // InternalMazeDsl.g:1664:1: ( ( rule__DifficultyConfig__EnemyLimitsAssignment_8 )* )
            // InternalMazeDsl.g:1665:2: ( rule__DifficultyConfig__EnemyLimitsAssignment_8 )*
            {
             before(grammarAccess.getDifficultyConfigAccess().getEnemyLimitsAssignment_8()); 
            // InternalMazeDsl.g:1666:2: ( rule__DifficultyConfig__EnemyLimitsAssignment_8 )*
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( (LA20_0==41) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // InternalMazeDsl.g:1666:3: rule__DifficultyConfig__EnemyLimitsAssignment_8
            	    {
            	    pushFollow(FOLLOW_15);
            	    rule__DifficultyConfig__EnemyLimitsAssignment_8();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop20;
                }
            } while (true);

             after(grammarAccess.getDifficultyConfigAccess().getEnemyLimitsAssignment_8()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group__8__Impl"


    // $ANTLR start "rule__DifficultyConfig__Group__9"
    // InternalMazeDsl.g:1674:1: rule__DifficultyConfig__Group__9 : rule__DifficultyConfig__Group__9__Impl ;
    public final void rule__DifficultyConfig__Group__9() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1678:1: ( rule__DifficultyConfig__Group__9__Impl )
            // InternalMazeDsl.g:1679:2: rule__DifficultyConfig__Group__9__Impl
            {
            pushFollow(FOLLOW_2);
            rule__DifficultyConfig__Group__9__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group__9"


    // $ANTLR start "rule__DifficultyConfig__Group__9__Impl"
    // InternalMazeDsl.g:1685:1: rule__DifficultyConfig__Group__9__Impl : ( '}' ) ;
    public final void rule__DifficultyConfig__Group__9__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1689:1: ( ( '}' ) )
            // InternalMazeDsl.g:1690:1: ( '}' )
            {
            // InternalMazeDsl.g:1690:1: ( '}' )
            // InternalMazeDsl.g:1691:2: '}'
            {
             before(grammarAccess.getDifficultyConfigAccess().getRightCurlyBracketKeyword_9()); 
            match(input,33,FOLLOW_2); 
             after(grammarAccess.getDifficultyConfigAccess().getRightCurlyBracketKeyword_9()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group__9__Impl"


    // $ANTLR start "rule__DifficultyConfig__Group_4__0"
    // InternalMazeDsl.g:1701:1: rule__DifficultyConfig__Group_4__0 : rule__DifficultyConfig__Group_4__0__Impl rule__DifficultyConfig__Group_4__1 ;
    public final void rule__DifficultyConfig__Group_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1705:1: ( rule__DifficultyConfig__Group_4__0__Impl rule__DifficultyConfig__Group_4__1 )
            // InternalMazeDsl.g:1706:2: rule__DifficultyConfig__Group_4__0__Impl rule__DifficultyConfig__Group_4__1
            {
            pushFollow(FOLLOW_16);
            rule__DifficultyConfig__Group_4__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DifficultyConfig__Group_4__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group_4__0"


    // $ANTLR start "rule__DifficultyConfig__Group_4__0__Impl"
    // InternalMazeDsl.g:1713:1: rule__DifficultyConfig__Group_4__0__Impl : ( 'instantDeath' ) ;
    public final void rule__DifficultyConfig__Group_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1717:1: ( ( 'instantDeath' ) )
            // InternalMazeDsl.g:1718:1: ( 'instantDeath' )
            {
            // InternalMazeDsl.g:1718:1: ( 'instantDeath' )
            // InternalMazeDsl.g:1719:2: 'instantDeath'
            {
             before(grammarAccess.getDifficultyConfigAccess().getInstantDeathKeyword_4_0()); 
            match(input,37,FOLLOW_2); 
             after(grammarAccess.getDifficultyConfigAccess().getInstantDeathKeyword_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group_4__0__Impl"


    // $ANTLR start "rule__DifficultyConfig__Group_4__1"
    // InternalMazeDsl.g:1728:1: rule__DifficultyConfig__Group_4__1 : rule__DifficultyConfig__Group_4__1__Impl ;
    public final void rule__DifficultyConfig__Group_4__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1732:1: ( rule__DifficultyConfig__Group_4__1__Impl )
            // InternalMazeDsl.g:1733:2: rule__DifficultyConfig__Group_4__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__DifficultyConfig__Group_4__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group_4__1"


    // $ANTLR start "rule__DifficultyConfig__Group_4__1__Impl"
    // InternalMazeDsl.g:1739:1: rule__DifficultyConfig__Group_4__1__Impl : ( ( rule__DifficultyConfig__InstantDeathAssignment_4_1 ) ) ;
    public final void rule__DifficultyConfig__Group_4__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1743:1: ( ( ( rule__DifficultyConfig__InstantDeathAssignment_4_1 ) ) )
            // InternalMazeDsl.g:1744:1: ( ( rule__DifficultyConfig__InstantDeathAssignment_4_1 ) )
            {
            // InternalMazeDsl.g:1744:1: ( ( rule__DifficultyConfig__InstantDeathAssignment_4_1 ) )
            // InternalMazeDsl.g:1745:2: ( rule__DifficultyConfig__InstantDeathAssignment_4_1 )
            {
             before(grammarAccess.getDifficultyConfigAccess().getInstantDeathAssignment_4_1()); 
            // InternalMazeDsl.g:1746:2: ( rule__DifficultyConfig__InstantDeathAssignment_4_1 )
            // InternalMazeDsl.g:1746:3: rule__DifficultyConfig__InstantDeathAssignment_4_1
            {
            pushFollow(FOLLOW_2);
            rule__DifficultyConfig__InstantDeathAssignment_4_1();

            state._fsp--;


            }

             after(grammarAccess.getDifficultyConfigAccess().getInstantDeathAssignment_4_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group_4__1__Impl"


    // $ANTLR start "rule__DifficultyConfig__Group_5__0"
    // InternalMazeDsl.g:1755:1: rule__DifficultyConfig__Group_5__0 : rule__DifficultyConfig__Group_5__0__Impl rule__DifficultyConfig__Group_5__1 ;
    public final void rule__DifficultyConfig__Group_5__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1759:1: ( rule__DifficultyConfig__Group_5__0__Impl rule__DifficultyConfig__Group_5__1 )
            // InternalMazeDsl.g:1760:2: rule__DifficultyConfig__Group_5__0__Impl rule__DifficultyConfig__Group_5__1
            {
            pushFollow(FOLLOW_17);
            rule__DifficultyConfig__Group_5__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DifficultyConfig__Group_5__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group_5__0"


    // $ANTLR start "rule__DifficultyConfig__Group_5__0__Impl"
    // InternalMazeDsl.g:1767:1: rule__DifficultyConfig__Group_5__0__Impl : ( 'speedMultiplier' ) ;
    public final void rule__DifficultyConfig__Group_5__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1771:1: ( ( 'speedMultiplier' ) )
            // InternalMazeDsl.g:1772:1: ( 'speedMultiplier' )
            {
            // InternalMazeDsl.g:1772:1: ( 'speedMultiplier' )
            // InternalMazeDsl.g:1773:2: 'speedMultiplier'
            {
             before(grammarAccess.getDifficultyConfigAccess().getSpeedMultiplierKeyword_5_0()); 
            match(input,38,FOLLOW_2); 
             after(grammarAccess.getDifficultyConfigAccess().getSpeedMultiplierKeyword_5_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group_5__0__Impl"


    // $ANTLR start "rule__DifficultyConfig__Group_5__1"
    // InternalMazeDsl.g:1782:1: rule__DifficultyConfig__Group_5__1 : rule__DifficultyConfig__Group_5__1__Impl ;
    public final void rule__DifficultyConfig__Group_5__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1786:1: ( rule__DifficultyConfig__Group_5__1__Impl )
            // InternalMazeDsl.g:1787:2: rule__DifficultyConfig__Group_5__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__DifficultyConfig__Group_5__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group_5__1"


    // $ANTLR start "rule__DifficultyConfig__Group_5__1__Impl"
    // InternalMazeDsl.g:1793:1: rule__DifficultyConfig__Group_5__1__Impl : ( ( rule__DifficultyConfig__SpeedMultiplierAssignment_5_1 ) ) ;
    public final void rule__DifficultyConfig__Group_5__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1797:1: ( ( ( rule__DifficultyConfig__SpeedMultiplierAssignment_5_1 ) ) )
            // InternalMazeDsl.g:1798:1: ( ( rule__DifficultyConfig__SpeedMultiplierAssignment_5_1 ) )
            {
            // InternalMazeDsl.g:1798:1: ( ( rule__DifficultyConfig__SpeedMultiplierAssignment_5_1 ) )
            // InternalMazeDsl.g:1799:2: ( rule__DifficultyConfig__SpeedMultiplierAssignment_5_1 )
            {
             before(grammarAccess.getDifficultyConfigAccess().getSpeedMultiplierAssignment_5_1()); 
            // InternalMazeDsl.g:1800:2: ( rule__DifficultyConfig__SpeedMultiplierAssignment_5_1 )
            // InternalMazeDsl.g:1800:3: rule__DifficultyConfig__SpeedMultiplierAssignment_5_1
            {
            pushFollow(FOLLOW_2);
            rule__DifficultyConfig__SpeedMultiplierAssignment_5_1();

            state._fsp--;


            }

             after(grammarAccess.getDifficultyConfigAccess().getSpeedMultiplierAssignment_5_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group_5__1__Impl"


    // $ANTLR start "rule__DifficultyConfig__Group_6__0"
    // InternalMazeDsl.g:1809:1: rule__DifficultyConfig__Group_6__0 : rule__DifficultyConfig__Group_6__0__Impl rule__DifficultyConfig__Group_6__1 ;
    public final void rule__DifficultyConfig__Group_6__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1813:1: ( rule__DifficultyConfig__Group_6__0__Impl rule__DifficultyConfig__Group_6__1 )
            // InternalMazeDsl.g:1814:2: rule__DifficultyConfig__Group_6__0__Impl rule__DifficultyConfig__Group_6__1
            {
            pushFollow(FOLLOW_17);
            rule__DifficultyConfig__Group_6__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DifficultyConfig__Group_6__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group_6__0"


    // $ANTLR start "rule__DifficultyConfig__Group_6__0__Impl"
    // InternalMazeDsl.g:1821:1: rule__DifficultyConfig__Group_6__0__Impl : ( 'damageMultiplier' ) ;
    public final void rule__DifficultyConfig__Group_6__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1825:1: ( ( 'damageMultiplier' ) )
            // InternalMazeDsl.g:1826:1: ( 'damageMultiplier' )
            {
            // InternalMazeDsl.g:1826:1: ( 'damageMultiplier' )
            // InternalMazeDsl.g:1827:2: 'damageMultiplier'
            {
             before(grammarAccess.getDifficultyConfigAccess().getDamageMultiplierKeyword_6_0()); 
            match(input,39,FOLLOW_2); 
             after(grammarAccess.getDifficultyConfigAccess().getDamageMultiplierKeyword_6_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group_6__0__Impl"


    // $ANTLR start "rule__DifficultyConfig__Group_6__1"
    // InternalMazeDsl.g:1836:1: rule__DifficultyConfig__Group_6__1 : rule__DifficultyConfig__Group_6__1__Impl ;
    public final void rule__DifficultyConfig__Group_6__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1840:1: ( rule__DifficultyConfig__Group_6__1__Impl )
            // InternalMazeDsl.g:1841:2: rule__DifficultyConfig__Group_6__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__DifficultyConfig__Group_6__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group_6__1"


    // $ANTLR start "rule__DifficultyConfig__Group_6__1__Impl"
    // InternalMazeDsl.g:1847:1: rule__DifficultyConfig__Group_6__1__Impl : ( ( rule__DifficultyConfig__DamageMultiplierAssignment_6_1 ) ) ;
    public final void rule__DifficultyConfig__Group_6__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1851:1: ( ( ( rule__DifficultyConfig__DamageMultiplierAssignment_6_1 ) ) )
            // InternalMazeDsl.g:1852:1: ( ( rule__DifficultyConfig__DamageMultiplierAssignment_6_1 ) )
            {
            // InternalMazeDsl.g:1852:1: ( ( rule__DifficultyConfig__DamageMultiplierAssignment_6_1 ) )
            // InternalMazeDsl.g:1853:2: ( rule__DifficultyConfig__DamageMultiplierAssignment_6_1 )
            {
             before(grammarAccess.getDifficultyConfigAccess().getDamageMultiplierAssignment_6_1()); 
            // InternalMazeDsl.g:1854:2: ( rule__DifficultyConfig__DamageMultiplierAssignment_6_1 )
            // InternalMazeDsl.g:1854:3: rule__DifficultyConfig__DamageMultiplierAssignment_6_1
            {
            pushFollow(FOLLOW_2);
            rule__DifficultyConfig__DamageMultiplierAssignment_6_1();

            state._fsp--;


            }

             after(grammarAccess.getDifficultyConfigAccess().getDamageMultiplierAssignment_6_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group_6__1__Impl"


    // $ANTLR start "rule__DifficultyConfig__Group_7__0"
    // InternalMazeDsl.g:1863:1: rule__DifficultyConfig__Group_7__0 : rule__DifficultyConfig__Group_7__0__Impl rule__DifficultyConfig__Group_7__1 ;
    public final void rule__DifficultyConfig__Group_7__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1867:1: ( rule__DifficultyConfig__Group_7__0__Impl rule__DifficultyConfig__Group_7__1 )
            // InternalMazeDsl.g:1868:2: rule__DifficultyConfig__Group_7__0__Impl rule__DifficultyConfig__Group_7__1
            {
            pushFollow(FOLLOW_17);
            rule__DifficultyConfig__Group_7__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DifficultyConfig__Group_7__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group_7__0"


    // $ANTLR start "rule__DifficultyConfig__Group_7__0__Impl"
    // InternalMazeDsl.g:1875:1: rule__DifficultyConfig__Group_7__0__Impl : ( 'maxThreat' ) ;
    public final void rule__DifficultyConfig__Group_7__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1879:1: ( ( 'maxThreat' ) )
            // InternalMazeDsl.g:1880:1: ( 'maxThreat' )
            {
            // InternalMazeDsl.g:1880:1: ( 'maxThreat' )
            // InternalMazeDsl.g:1881:2: 'maxThreat'
            {
             before(grammarAccess.getDifficultyConfigAccess().getMaxThreatKeyword_7_0()); 
            match(input,40,FOLLOW_2); 
             after(grammarAccess.getDifficultyConfigAccess().getMaxThreatKeyword_7_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group_7__0__Impl"


    // $ANTLR start "rule__DifficultyConfig__Group_7__1"
    // InternalMazeDsl.g:1890:1: rule__DifficultyConfig__Group_7__1 : rule__DifficultyConfig__Group_7__1__Impl ;
    public final void rule__DifficultyConfig__Group_7__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1894:1: ( rule__DifficultyConfig__Group_7__1__Impl )
            // InternalMazeDsl.g:1895:2: rule__DifficultyConfig__Group_7__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__DifficultyConfig__Group_7__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group_7__1"


    // $ANTLR start "rule__DifficultyConfig__Group_7__1__Impl"
    // InternalMazeDsl.g:1901:1: rule__DifficultyConfig__Group_7__1__Impl : ( ( rule__DifficultyConfig__MaxThreatAssignment_7_1 ) ) ;
    public final void rule__DifficultyConfig__Group_7__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1905:1: ( ( ( rule__DifficultyConfig__MaxThreatAssignment_7_1 ) ) )
            // InternalMazeDsl.g:1906:1: ( ( rule__DifficultyConfig__MaxThreatAssignment_7_1 ) )
            {
            // InternalMazeDsl.g:1906:1: ( ( rule__DifficultyConfig__MaxThreatAssignment_7_1 ) )
            // InternalMazeDsl.g:1907:2: ( rule__DifficultyConfig__MaxThreatAssignment_7_1 )
            {
             before(grammarAccess.getDifficultyConfigAccess().getMaxThreatAssignment_7_1()); 
            // InternalMazeDsl.g:1908:2: ( rule__DifficultyConfig__MaxThreatAssignment_7_1 )
            // InternalMazeDsl.g:1908:3: rule__DifficultyConfig__MaxThreatAssignment_7_1
            {
            pushFollow(FOLLOW_2);
            rule__DifficultyConfig__MaxThreatAssignment_7_1();

            state._fsp--;


            }

             after(grammarAccess.getDifficultyConfigAccess().getMaxThreatAssignment_7_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group_7__1__Impl"


    // $ANTLR start "rule__EnemyLimit__Group__0"
    // InternalMazeDsl.g:1917:1: rule__EnemyLimit__Group__0 : rule__EnemyLimit__Group__0__Impl rule__EnemyLimit__Group__1 ;
    public final void rule__EnemyLimit__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1921:1: ( rule__EnemyLimit__Group__0__Impl rule__EnemyLimit__Group__1 )
            // InternalMazeDsl.g:1922:2: rule__EnemyLimit__Group__0__Impl rule__EnemyLimit__Group__1
            {
            pushFollow(FOLLOW_18);
            rule__EnemyLimit__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__EnemyLimit__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EnemyLimit__Group__0"


    // $ANTLR start "rule__EnemyLimit__Group__0__Impl"
    // InternalMazeDsl.g:1929:1: rule__EnemyLimit__Group__0__Impl : ( 'limit' ) ;
    public final void rule__EnemyLimit__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1933:1: ( ( 'limit' ) )
            // InternalMazeDsl.g:1934:1: ( 'limit' )
            {
            // InternalMazeDsl.g:1934:1: ( 'limit' )
            // InternalMazeDsl.g:1935:2: 'limit'
            {
             before(grammarAccess.getEnemyLimitAccess().getLimitKeyword_0()); 
            match(input,41,FOLLOW_2); 
             after(grammarAccess.getEnemyLimitAccess().getLimitKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EnemyLimit__Group__0__Impl"


    // $ANTLR start "rule__EnemyLimit__Group__1"
    // InternalMazeDsl.g:1944:1: rule__EnemyLimit__Group__1 : rule__EnemyLimit__Group__1__Impl rule__EnemyLimit__Group__2 ;
    public final void rule__EnemyLimit__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1948:1: ( rule__EnemyLimit__Group__1__Impl rule__EnemyLimit__Group__2 )
            // InternalMazeDsl.g:1949:2: rule__EnemyLimit__Group__1__Impl rule__EnemyLimit__Group__2
            {
            pushFollow(FOLLOW_19);
            rule__EnemyLimit__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__EnemyLimit__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EnemyLimit__Group__1"


    // $ANTLR start "rule__EnemyLimit__Group__1__Impl"
    // InternalMazeDsl.g:1956:1: rule__EnemyLimit__Group__1__Impl : ( ( rule__EnemyLimit__TypeAssignment_1 ) ) ;
    public final void rule__EnemyLimit__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1960:1: ( ( ( rule__EnemyLimit__TypeAssignment_1 ) ) )
            // InternalMazeDsl.g:1961:1: ( ( rule__EnemyLimit__TypeAssignment_1 ) )
            {
            // InternalMazeDsl.g:1961:1: ( ( rule__EnemyLimit__TypeAssignment_1 ) )
            // InternalMazeDsl.g:1962:2: ( rule__EnemyLimit__TypeAssignment_1 )
            {
             before(grammarAccess.getEnemyLimitAccess().getTypeAssignment_1()); 
            // InternalMazeDsl.g:1963:2: ( rule__EnemyLimit__TypeAssignment_1 )
            // InternalMazeDsl.g:1963:3: rule__EnemyLimit__TypeAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__EnemyLimit__TypeAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getEnemyLimitAccess().getTypeAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EnemyLimit__Group__1__Impl"


    // $ANTLR start "rule__EnemyLimit__Group__2"
    // InternalMazeDsl.g:1971:1: rule__EnemyLimit__Group__2 : rule__EnemyLimit__Group__2__Impl rule__EnemyLimit__Group__3 ;
    public final void rule__EnemyLimit__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1975:1: ( rule__EnemyLimit__Group__2__Impl rule__EnemyLimit__Group__3 )
            // InternalMazeDsl.g:1976:2: rule__EnemyLimit__Group__2__Impl rule__EnemyLimit__Group__3
            {
            pushFollow(FOLLOW_17);
            rule__EnemyLimit__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__EnemyLimit__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EnemyLimit__Group__2"


    // $ANTLR start "rule__EnemyLimit__Group__2__Impl"
    // InternalMazeDsl.g:1983:1: rule__EnemyLimit__Group__2__Impl : ( 'max' ) ;
    public final void rule__EnemyLimit__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1987:1: ( ( 'max' ) )
            // InternalMazeDsl.g:1988:1: ( 'max' )
            {
            // InternalMazeDsl.g:1988:1: ( 'max' )
            // InternalMazeDsl.g:1989:2: 'max'
            {
             before(grammarAccess.getEnemyLimitAccess().getMaxKeyword_2()); 
            match(input,42,FOLLOW_2); 
             after(grammarAccess.getEnemyLimitAccess().getMaxKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EnemyLimit__Group__2__Impl"


    // $ANTLR start "rule__EnemyLimit__Group__3"
    // InternalMazeDsl.g:1998:1: rule__EnemyLimit__Group__3 : rule__EnemyLimit__Group__3__Impl ;
    public final void rule__EnemyLimit__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2002:1: ( rule__EnemyLimit__Group__3__Impl )
            // InternalMazeDsl.g:2003:2: rule__EnemyLimit__Group__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__EnemyLimit__Group__3__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EnemyLimit__Group__3"


    // $ANTLR start "rule__EnemyLimit__Group__3__Impl"
    // InternalMazeDsl.g:2009:1: rule__EnemyLimit__Group__3__Impl : ( ( rule__EnemyLimit__MaxCountAssignment_3 ) ) ;
    public final void rule__EnemyLimit__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2013:1: ( ( ( rule__EnemyLimit__MaxCountAssignment_3 ) ) )
            // InternalMazeDsl.g:2014:1: ( ( rule__EnemyLimit__MaxCountAssignment_3 ) )
            {
            // InternalMazeDsl.g:2014:1: ( ( rule__EnemyLimit__MaxCountAssignment_3 ) )
            // InternalMazeDsl.g:2015:2: ( rule__EnemyLimit__MaxCountAssignment_3 )
            {
             before(grammarAccess.getEnemyLimitAccess().getMaxCountAssignment_3()); 
            // InternalMazeDsl.g:2016:2: ( rule__EnemyLimit__MaxCountAssignment_3 )
            // InternalMazeDsl.g:2016:3: rule__EnemyLimit__MaxCountAssignment_3
            {
            pushFollow(FOLLOW_2);
            rule__EnemyLimit__MaxCountAssignment_3();

            state._fsp--;


            }

             after(grammarAccess.getEnemyLimitAccess().getMaxCountAssignment_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EnemyLimit__Group__3__Impl"


    // $ANTLR start "rule__OpponentConfig__Group__0"
    // InternalMazeDsl.g:2025:1: rule__OpponentConfig__Group__0 : rule__OpponentConfig__Group__0__Impl rule__OpponentConfig__Group__1 ;
    public final void rule__OpponentConfig__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2029:1: ( rule__OpponentConfig__Group__0__Impl rule__OpponentConfig__Group__1 )
            // InternalMazeDsl.g:2030:2: rule__OpponentConfig__Group__0__Impl rule__OpponentConfig__Group__1
            {
            pushFollow(FOLLOW_3);
            rule__OpponentConfig__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__0"


    // $ANTLR start "rule__OpponentConfig__Group__0__Impl"
    // InternalMazeDsl.g:2037:1: rule__OpponentConfig__Group__0__Impl : ( 'opponent' ) ;
    public final void rule__OpponentConfig__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2041:1: ( ( 'opponent' ) )
            // InternalMazeDsl.g:2042:1: ( 'opponent' )
            {
            // InternalMazeDsl.g:2042:1: ( 'opponent' )
            // InternalMazeDsl.g:2043:2: 'opponent'
            {
             before(grammarAccess.getOpponentConfigAccess().getOpponentKeyword_0()); 
            match(input,43,FOLLOW_2); 
             after(grammarAccess.getOpponentConfigAccess().getOpponentKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__0__Impl"


    // $ANTLR start "rule__OpponentConfig__Group__1"
    // InternalMazeDsl.g:2052:1: rule__OpponentConfig__Group__1 : rule__OpponentConfig__Group__1__Impl rule__OpponentConfig__Group__2 ;
    public final void rule__OpponentConfig__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2056:1: ( rule__OpponentConfig__Group__1__Impl rule__OpponentConfig__Group__2 )
            // InternalMazeDsl.g:2057:2: rule__OpponentConfig__Group__1__Impl rule__OpponentConfig__Group__2
            {
            pushFollow(FOLLOW_4);
            rule__OpponentConfig__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__1"


    // $ANTLR start "rule__OpponentConfig__Group__1__Impl"
    // InternalMazeDsl.g:2064:1: rule__OpponentConfig__Group__1__Impl : ( ( rule__OpponentConfig__NameAssignment_1 ) ) ;
    public final void rule__OpponentConfig__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2068:1: ( ( ( rule__OpponentConfig__NameAssignment_1 ) ) )
            // InternalMazeDsl.g:2069:1: ( ( rule__OpponentConfig__NameAssignment_1 ) )
            {
            // InternalMazeDsl.g:2069:1: ( ( rule__OpponentConfig__NameAssignment_1 ) )
            // InternalMazeDsl.g:2070:2: ( rule__OpponentConfig__NameAssignment_1 )
            {
             before(grammarAccess.getOpponentConfigAccess().getNameAssignment_1()); 
            // InternalMazeDsl.g:2071:2: ( rule__OpponentConfig__NameAssignment_1 )
            // InternalMazeDsl.g:2071:3: rule__OpponentConfig__NameAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__OpponentConfig__NameAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getOpponentConfigAccess().getNameAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__1__Impl"


    // $ANTLR start "rule__OpponentConfig__Group__2"
    // InternalMazeDsl.g:2079:1: rule__OpponentConfig__Group__2 : rule__OpponentConfig__Group__2__Impl rule__OpponentConfig__Group__3 ;
    public final void rule__OpponentConfig__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2083:1: ( rule__OpponentConfig__Group__2__Impl rule__OpponentConfig__Group__3 )
            // InternalMazeDsl.g:2084:2: rule__OpponentConfig__Group__2__Impl rule__OpponentConfig__Group__3
            {
            pushFollow(FOLLOW_20);
            rule__OpponentConfig__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__2"


    // $ANTLR start "rule__OpponentConfig__Group__2__Impl"
    // InternalMazeDsl.g:2091:1: rule__OpponentConfig__Group__2__Impl : ( '{' ) ;
    public final void rule__OpponentConfig__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2095:1: ( ( '{' ) )
            // InternalMazeDsl.g:2096:1: ( '{' )
            {
            // InternalMazeDsl.g:2096:1: ( '{' )
            // InternalMazeDsl.g:2097:2: '{'
            {
             before(grammarAccess.getOpponentConfigAccess().getLeftCurlyBracketKeyword_2()); 
            match(input,32,FOLLOW_2); 
             after(grammarAccess.getOpponentConfigAccess().getLeftCurlyBracketKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__2__Impl"


    // $ANTLR start "rule__OpponentConfig__Group__3"
    // InternalMazeDsl.g:2106:1: rule__OpponentConfig__Group__3 : rule__OpponentConfig__Group__3__Impl rule__OpponentConfig__Group__4 ;
    public final void rule__OpponentConfig__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2110:1: ( rule__OpponentConfig__Group__3__Impl rule__OpponentConfig__Group__4 )
            // InternalMazeDsl.g:2111:2: rule__OpponentConfig__Group__3__Impl rule__OpponentConfig__Group__4
            {
            pushFollow(FOLLOW_18);
            rule__OpponentConfig__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__3"


    // $ANTLR start "rule__OpponentConfig__Group__3__Impl"
    // InternalMazeDsl.g:2118:1: rule__OpponentConfig__Group__3__Impl : ( 'type' ) ;
    public final void rule__OpponentConfig__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2122:1: ( ( 'type' ) )
            // InternalMazeDsl.g:2123:1: ( 'type' )
            {
            // InternalMazeDsl.g:2123:1: ( 'type' )
            // InternalMazeDsl.g:2124:2: 'type'
            {
             before(grammarAccess.getOpponentConfigAccess().getTypeKeyword_3()); 
            match(input,44,FOLLOW_2); 
             after(grammarAccess.getOpponentConfigAccess().getTypeKeyword_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__3__Impl"


    // $ANTLR start "rule__OpponentConfig__Group__4"
    // InternalMazeDsl.g:2133:1: rule__OpponentConfig__Group__4 : rule__OpponentConfig__Group__4__Impl rule__OpponentConfig__Group__5 ;
    public final void rule__OpponentConfig__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2137:1: ( rule__OpponentConfig__Group__4__Impl rule__OpponentConfig__Group__5 )
            // InternalMazeDsl.g:2138:2: rule__OpponentConfig__Group__4__Impl rule__OpponentConfig__Group__5
            {
            pushFollow(FOLLOW_21);
            rule__OpponentConfig__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__4"


    // $ANTLR start "rule__OpponentConfig__Group__4__Impl"
    // InternalMazeDsl.g:2145:1: rule__OpponentConfig__Group__4__Impl : ( ( rule__OpponentConfig__TypeAssignment_4 ) ) ;
    public final void rule__OpponentConfig__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2149:1: ( ( ( rule__OpponentConfig__TypeAssignment_4 ) ) )
            // InternalMazeDsl.g:2150:1: ( ( rule__OpponentConfig__TypeAssignment_4 ) )
            {
            // InternalMazeDsl.g:2150:1: ( ( rule__OpponentConfig__TypeAssignment_4 ) )
            // InternalMazeDsl.g:2151:2: ( rule__OpponentConfig__TypeAssignment_4 )
            {
             before(grammarAccess.getOpponentConfigAccess().getTypeAssignment_4()); 
            // InternalMazeDsl.g:2152:2: ( rule__OpponentConfig__TypeAssignment_4 )
            // InternalMazeDsl.g:2152:3: rule__OpponentConfig__TypeAssignment_4
            {
            pushFollow(FOLLOW_2);
            rule__OpponentConfig__TypeAssignment_4();

            state._fsp--;


            }

             after(grammarAccess.getOpponentConfigAccess().getTypeAssignment_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__4__Impl"


    // $ANTLR start "rule__OpponentConfig__Group__5"
    // InternalMazeDsl.g:2160:1: rule__OpponentConfig__Group__5 : rule__OpponentConfig__Group__5__Impl rule__OpponentConfig__Group__6 ;
    public final void rule__OpponentConfig__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2164:1: ( rule__OpponentConfig__Group__5__Impl rule__OpponentConfig__Group__6 )
            // InternalMazeDsl.g:2165:2: rule__OpponentConfig__Group__5__Impl rule__OpponentConfig__Group__6
            {
            pushFollow(FOLLOW_21);
            rule__OpponentConfig__Group__5__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group__6();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__5"


    // $ANTLR start "rule__OpponentConfig__Group__5__Impl"
    // InternalMazeDsl.g:2172:1: rule__OpponentConfig__Group__5__Impl : ( ( rule__OpponentConfig__Group_5__0 )? ) ;
    public final void rule__OpponentConfig__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2176:1: ( ( ( rule__OpponentConfig__Group_5__0 )? ) )
            // InternalMazeDsl.g:2177:1: ( ( rule__OpponentConfig__Group_5__0 )? )
            {
            // InternalMazeDsl.g:2177:1: ( ( rule__OpponentConfig__Group_5__0 )? )
            // InternalMazeDsl.g:2178:2: ( rule__OpponentConfig__Group_5__0 )?
            {
             before(grammarAccess.getOpponentConfigAccess().getGroup_5()); 
            // InternalMazeDsl.g:2179:2: ( rule__OpponentConfig__Group_5__0 )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==45) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // InternalMazeDsl.g:2179:3: rule__OpponentConfig__Group_5__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__OpponentConfig__Group_5__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getOpponentConfigAccess().getGroup_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__5__Impl"


    // $ANTLR start "rule__OpponentConfig__Group__6"
    // InternalMazeDsl.g:2187:1: rule__OpponentConfig__Group__6 : rule__OpponentConfig__Group__6__Impl rule__OpponentConfig__Group__7 ;
    public final void rule__OpponentConfig__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2191:1: ( rule__OpponentConfig__Group__6__Impl rule__OpponentConfig__Group__7 )
            // InternalMazeDsl.g:2192:2: rule__OpponentConfig__Group__6__Impl rule__OpponentConfig__Group__7
            {
            pushFollow(FOLLOW_21);
            rule__OpponentConfig__Group__6__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group__7();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__6"


    // $ANTLR start "rule__OpponentConfig__Group__6__Impl"
    // InternalMazeDsl.g:2199:1: rule__OpponentConfig__Group__6__Impl : ( ( rule__OpponentConfig__Group_6__0 )? ) ;
    public final void rule__OpponentConfig__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2203:1: ( ( ( rule__OpponentConfig__Group_6__0 )? ) )
            // InternalMazeDsl.g:2204:1: ( ( rule__OpponentConfig__Group_6__0 )? )
            {
            // InternalMazeDsl.g:2204:1: ( ( rule__OpponentConfig__Group_6__0 )? )
            // InternalMazeDsl.g:2205:2: ( rule__OpponentConfig__Group_6__0 )?
            {
             before(grammarAccess.getOpponentConfigAccess().getGroup_6()); 
            // InternalMazeDsl.g:2206:2: ( rule__OpponentConfig__Group_6__0 )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==46) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // InternalMazeDsl.g:2206:3: rule__OpponentConfig__Group_6__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__OpponentConfig__Group_6__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getOpponentConfigAccess().getGroup_6()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__6__Impl"


    // $ANTLR start "rule__OpponentConfig__Group__7"
    // InternalMazeDsl.g:2214:1: rule__OpponentConfig__Group__7 : rule__OpponentConfig__Group__7__Impl rule__OpponentConfig__Group__8 ;
    public final void rule__OpponentConfig__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2218:1: ( rule__OpponentConfig__Group__7__Impl rule__OpponentConfig__Group__8 )
            // InternalMazeDsl.g:2219:2: rule__OpponentConfig__Group__7__Impl rule__OpponentConfig__Group__8
            {
            pushFollow(FOLLOW_21);
            rule__OpponentConfig__Group__7__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group__8();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__7"


    // $ANTLR start "rule__OpponentConfig__Group__7__Impl"
    // InternalMazeDsl.g:2226:1: rule__OpponentConfig__Group__7__Impl : ( ( rule__OpponentConfig__Group_7__0 )? ) ;
    public final void rule__OpponentConfig__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2230:1: ( ( ( rule__OpponentConfig__Group_7__0 )? ) )
            // InternalMazeDsl.g:2231:1: ( ( rule__OpponentConfig__Group_7__0 )? )
            {
            // InternalMazeDsl.g:2231:1: ( ( rule__OpponentConfig__Group_7__0 )? )
            // InternalMazeDsl.g:2232:2: ( rule__OpponentConfig__Group_7__0 )?
            {
             before(grammarAccess.getOpponentConfigAccess().getGroup_7()); 
            // InternalMazeDsl.g:2233:2: ( rule__OpponentConfig__Group_7__0 )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==47) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // InternalMazeDsl.g:2233:3: rule__OpponentConfig__Group_7__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__OpponentConfig__Group_7__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getOpponentConfigAccess().getGroup_7()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__7__Impl"


    // $ANTLR start "rule__OpponentConfig__Group__8"
    // InternalMazeDsl.g:2241:1: rule__OpponentConfig__Group__8 : rule__OpponentConfig__Group__8__Impl rule__OpponentConfig__Group__9 ;
    public final void rule__OpponentConfig__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2245:1: ( rule__OpponentConfig__Group__8__Impl rule__OpponentConfig__Group__9 )
            // InternalMazeDsl.g:2246:2: rule__OpponentConfig__Group__8__Impl rule__OpponentConfig__Group__9
            {
            pushFollow(FOLLOW_21);
            rule__OpponentConfig__Group__8__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group__9();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__8"


    // $ANTLR start "rule__OpponentConfig__Group__8__Impl"
    // InternalMazeDsl.g:2253:1: rule__OpponentConfig__Group__8__Impl : ( ( rule__OpponentConfig__Group_8__0 )? ) ;
    public final void rule__OpponentConfig__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2257:1: ( ( ( rule__OpponentConfig__Group_8__0 )? ) )
            // InternalMazeDsl.g:2258:1: ( ( rule__OpponentConfig__Group_8__0 )? )
            {
            // InternalMazeDsl.g:2258:1: ( ( rule__OpponentConfig__Group_8__0 )? )
            // InternalMazeDsl.g:2259:2: ( rule__OpponentConfig__Group_8__0 )?
            {
             before(grammarAccess.getOpponentConfigAccess().getGroup_8()); 
            // InternalMazeDsl.g:2260:2: ( rule__OpponentConfig__Group_8__0 )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==48) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // InternalMazeDsl.g:2260:3: rule__OpponentConfig__Group_8__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__OpponentConfig__Group_8__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getOpponentConfigAccess().getGroup_8()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__8__Impl"


    // $ANTLR start "rule__OpponentConfig__Group__9"
    // InternalMazeDsl.g:2268:1: rule__OpponentConfig__Group__9 : rule__OpponentConfig__Group__9__Impl rule__OpponentConfig__Group__10 ;
    public final void rule__OpponentConfig__Group__9() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2272:1: ( rule__OpponentConfig__Group__9__Impl rule__OpponentConfig__Group__10 )
            // InternalMazeDsl.g:2273:2: rule__OpponentConfig__Group__9__Impl rule__OpponentConfig__Group__10
            {
            pushFollow(FOLLOW_21);
            rule__OpponentConfig__Group__9__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group__10();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__9"


    // $ANTLR start "rule__OpponentConfig__Group__9__Impl"
    // InternalMazeDsl.g:2280:1: rule__OpponentConfig__Group__9__Impl : ( ( rule__OpponentConfig__Group_9__0 )? ) ;
    public final void rule__OpponentConfig__Group__9__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2284:1: ( ( ( rule__OpponentConfig__Group_9__0 )? ) )
            // InternalMazeDsl.g:2285:1: ( ( rule__OpponentConfig__Group_9__0 )? )
            {
            // InternalMazeDsl.g:2285:1: ( ( rule__OpponentConfig__Group_9__0 )? )
            // InternalMazeDsl.g:2286:2: ( rule__OpponentConfig__Group_9__0 )?
            {
             before(grammarAccess.getOpponentConfigAccess().getGroup_9()); 
            // InternalMazeDsl.g:2287:2: ( rule__OpponentConfig__Group_9__0 )?
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==49) ) {
                alt25=1;
            }
            switch (alt25) {
                case 1 :
                    // InternalMazeDsl.g:2287:3: rule__OpponentConfig__Group_9__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__OpponentConfig__Group_9__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getOpponentConfigAccess().getGroup_9()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__9__Impl"


    // $ANTLR start "rule__OpponentConfig__Group__10"
    // InternalMazeDsl.g:2295:1: rule__OpponentConfig__Group__10 : rule__OpponentConfig__Group__10__Impl rule__OpponentConfig__Group__11 ;
    public final void rule__OpponentConfig__Group__10() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2299:1: ( rule__OpponentConfig__Group__10__Impl rule__OpponentConfig__Group__11 )
            // InternalMazeDsl.g:2300:2: rule__OpponentConfig__Group__10__Impl rule__OpponentConfig__Group__11
            {
            pushFollow(FOLLOW_21);
            rule__OpponentConfig__Group__10__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group__11();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__10"


    // $ANTLR start "rule__OpponentConfig__Group__10__Impl"
    // InternalMazeDsl.g:2307:1: rule__OpponentConfig__Group__10__Impl : ( ( rule__OpponentConfig__Group_10__0 )? ) ;
    public final void rule__OpponentConfig__Group__10__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2311:1: ( ( ( rule__OpponentConfig__Group_10__0 )? ) )
            // InternalMazeDsl.g:2312:1: ( ( rule__OpponentConfig__Group_10__0 )? )
            {
            // InternalMazeDsl.g:2312:1: ( ( rule__OpponentConfig__Group_10__0 )? )
            // InternalMazeDsl.g:2313:2: ( rule__OpponentConfig__Group_10__0 )?
            {
             before(grammarAccess.getOpponentConfigAccess().getGroup_10()); 
            // InternalMazeDsl.g:2314:2: ( rule__OpponentConfig__Group_10__0 )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==50) ) {
                alt26=1;
            }
            switch (alt26) {
                case 1 :
                    // InternalMazeDsl.g:2314:3: rule__OpponentConfig__Group_10__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__OpponentConfig__Group_10__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getOpponentConfigAccess().getGroup_10()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__10__Impl"


    // $ANTLR start "rule__OpponentConfig__Group__11"
    // InternalMazeDsl.g:2322:1: rule__OpponentConfig__Group__11 : rule__OpponentConfig__Group__11__Impl rule__OpponentConfig__Group__12 ;
    public final void rule__OpponentConfig__Group__11() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2326:1: ( rule__OpponentConfig__Group__11__Impl rule__OpponentConfig__Group__12 )
            // InternalMazeDsl.g:2327:2: rule__OpponentConfig__Group__11__Impl rule__OpponentConfig__Group__12
            {
            pushFollow(FOLLOW_21);
            rule__OpponentConfig__Group__11__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group__12();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__11"


    // $ANTLR start "rule__OpponentConfig__Group__11__Impl"
    // InternalMazeDsl.g:2334:1: rule__OpponentConfig__Group__11__Impl : ( ( rule__OpponentConfig__CharacterSpecificsAssignment_11 )? ) ;
    public final void rule__OpponentConfig__Group__11__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2338:1: ( ( ( rule__OpponentConfig__CharacterSpecificsAssignment_11 )? ) )
            // InternalMazeDsl.g:2339:1: ( ( rule__OpponentConfig__CharacterSpecificsAssignment_11 )? )
            {
            // InternalMazeDsl.g:2339:1: ( ( rule__OpponentConfig__CharacterSpecificsAssignment_11 )? )
            // InternalMazeDsl.g:2340:2: ( rule__OpponentConfig__CharacterSpecificsAssignment_11 )?
            {
             before(grammarAccess.getOpponentConfigAccess().getCharacterSpecificsAssignment_11()); 
            // InternalMazeDsl.g:2341:2: ( rule__OpponentConfig__CharacterSpecificsAssignment_11 )?
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==52||LA27_0==56||LA27_0==59) ) {
                alt27=1;
            }
            switch (alt27) {
                case 1 :
                    // InternalMazeDsl.g:2341:3: rule__OpponentConfig__CharacterSpecificsAssignment_11
                    {
                    pushFollow(FOLLOW_2);
                    rule__OpponentConfig__CharacterSpecificsAssignment_11();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getOpponentConfigAccess().getCharacterSpecificsAssignment_11()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__11__Impl"


    // $ANTLR start "rule__OpponentConfig__Group__12"
    // InternalMazeDsl.g:2349:1: rule__OpponentConfig__Group__12 : rule__OpponentConfig__Group__12__Impl rule__OpponentConfig__Group__13 ;
    public final void rule__OpponentConfig__Group__12() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2353:1: ( rule__OpponentConfig__Group__12__Impl rule__OpponentConfig__Group__13 )
            // InternalMazeDsl.g:2354:2: rule__OpponentConfig__Group__12__Impl rule__OpponentConfig__Group__13
            {
            pushFollow(FOLLOW_21);
            rule__OpponentConfig__Group__12__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group__13();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__12"


    // $ANTLR start "rule__OpponentConfig__Group__12__Impl"
    // InternalMazeDsl.g:2361:1: rule__OpponentConfig__Group__12__Impl : ( ( rule__OpponentConfig__Group_12__0 )? ) ;
    public final void rule__OpponentConfig__Group__12__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2365:1: ( ( ( rule__OpponentConfig__Group_12__0 )? ) )
            // InternalMazeDsl.g:2366:1: ( ( rule__OpponentConfig__Group_12__0 )? )
            {
            // InternalMazeDsl.g:2366:1: ( ( rule__OpponentConfig__Group_12__0 )? )
            // InternalMazeDsl.g:2367:2: ( rule__OpponentConfig__Group_12__0 )?
            {
             before(grammarAccess.getOpponentConfigAccess().getGroup_12()); 
            // InternalMazeDsl.g:2368:2: ( rule__OpponentConfig__Group_12__0 )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==23) ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // InternalMazeDsl.g:2368:3: rule__OpponentConfig__Group_12__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__OpponentConfig__Group_12__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getOpponentConfigAccess().getGroup_12()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__12__Impl"


    // $ANTLR start "rule__OpponentConfig__Group__13"
    // InternalMazeDsl.g:2376:1: rule__OpponentConfig__Group__13 : rule__OpponentConfig__Group__13__Impl rule__OpponentConfig__Group__14 ;
    public final void rule__OpponentConfig__Group__13() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2380:1: ( rule__OpponentConfig__Group__13__Impl rule__OpponentConfig__Group__14 )
            // InternalMazeDsl.g:2381:2: rule__OpponentConfig__Group__13__Impl rule__OpponentConfig__Group__14
            {
            pushFollow(FOLLOW_21);
            rule__OpponentConfig__Group__13__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group__14();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__13"


    // $ANTLR start "rule__OpponentConfig__Group__13__Impl"
    // InternalMazeDsl.g:2388:1: rule__OpponentConfig__Group__13__Impl : ( ( rule__OpponentConfig__Group_13__0 )? ) ;
    public final void rule__OpponentConfig__Group__13__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2392:1: ( ( ( rule__OpponentConfig__Group_13__0 )? ) )
            // InternalMazeDsl.g:2393:1: ( ( rule__OpponentConfig__Group_13__0 )? )
            {
            // InternalMazeDsl.g:2393:1: ( ( rule__OpponentConfig__Group_13__0 )? )
            // InternalMazeDsl.g:2394:2: ( rule__OpponentConfig__Group_13__0 )?
            {
             before(grammarAccess.getOpponentConfigAccess().getGroup_13()); 
            // InternalMazeDsl.g:2395:2: ( rule__OpponentConfig__Group_13__0 )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==51) ) {
                alt29=1;
            }
            switch (alt29) {
                case 1 :
                    // InternalMazeDsl.g:2395:3: rule__OpponentConfig__Group_13__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__OpponentConfig__Group_13__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getOpponentConfigAccess().getGroup_13()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__13__Impl"


    // $ANTLR start "rule__OpponentConfig__Group__14"
    // InternalMazeDsl.g:2403:1: rule__OpponentConfig__Group__14 : rule__OpponentConfig__Group__14__Impl ;
    public final void rule__OpponentConfig__Group__14() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2407:1: ( rule__OpponentConfig__Group__14__Impl )
            // InternalMazeDsl.g:2408:2: rule__OpponentConfig__Group__14__Impl
            {
            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group__14__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__14"


    // $ANTLR start "rule__OpponentConfig__Group__14__Impl"
    // InternalMazeDsl.g:2414:1: rule__OpponentConfig__Group__14__Impl : ( '}' ) ;
    public final void rule__OpponentConfig__Group__14__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2418:1: ( ( '}' ) )
            // InternalMazeDsl.g:2419:1: ( '}' )
            {
            // InternalMazeDsl.g:2419:1: ( '}' )
            // InternalMazeDsl.g:2420:2: '}'
            {
             before(grammarAccess.getOpponentConfigAccess().getRightCurlyBracketKeyword_14()); 
            match(input,33,FOLLOW_2); 
             after(grammarAccess.getOpponentConfigAccess().getRightCurlyBracketKeyword_14()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__14__Impl"


    // $ANTLR start "rule__OpponentConfig__Group_5__0"
    // InternalMazeDsl.g:2430:1: rule__OpponentConfig__Group_5__0 : rule__OpponentConfig__Group_5__0__Impl rule__OpponentConfig__Group_5__1 ;
    public final void rule__OpponentConfig__Group_5__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2434:1: ( rule__OpponentConfig__Group_5__0__Impl rule__OpponentConfig__Group_5__1 )
            // InternalMazeDsl.g:2435:2: rule__OpponentConfig__Group_5__0__Impl rule__OpponentConfig__Group_5__1
            {
            pushFollow(FOLLOW_11);
            rule__OpponentConfig__Group_5__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group_5__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_5__0"


    // $ANTLR start "rule__OpponentConfig__Group_5__0__Impl"
    // InternalMazeDsl.g:2442:1: rule__OpponentConfig__Group_5__0__Impl : ( 'displayName' ) ;
    public final void rule__OpponentConfig__Group_5__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2446:1: ( ( 'displayName' ) )
            // InternalMazeDsl.g:2447:1: ( 'displayName' )
            {
            // InternalMazeDsl.g:2447:1: ( 'displayName' )
            // InternalMazeDsl.g:2448:2: 'displayName'
            {
             before(grammarAccess.getOpponentConfigAccess().getDisplayNameKeyword_5_0()); 
            match(input,45,FOLLOW_2); 
             after(grammarAccess.getOpponentConfigAccess().getDisplayNameKeyword_5_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_5__0__Impl"


    // $ANTLR start "rule__OpponentConfig__Group_5__1"
    // InternalMazeDsl.g:2457:1: rule__OpponentConfig__Group_5__1 : rule__OpponentConfig__Group_5__1__Impl ;
    public final void rule__OpponentConfig__Group_5__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2461:1: ( rule__OpponentConfig__Group_5__1__Impl )
            // InternalMazeDsl.g:2462:2: rule__OpponentConfig__Group_5__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group_5__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_5__1"


    // $ANTLR start "rule__OpponentConfig__Group_5__1__Impl"
    // InternalMazeDsl.g:2468:1: rule__OpponentConfig__Group_5__1__Impl : ( ( rule__OpponentConfig__DisplayNameAssignment_5_1 ) ) ;
    public final void rule__OpponentConfig__Group_5__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2472:1: ( ( ( rule__OpponentConfig__DisplayNameAssignment_5_1 ) ) )
            // InternalMazeDsl.g:2473:1: ( ( rule__OpponentConfig__DisplayNameAssignment_5_1 ) )
            {
            // InternalMazeDsl.g:2473:1: ( ( rule__OpponentConfig__DisplayNameAssignment_5_1 ) )
            // InternalMazeDsl.g:2474:2: ( rule__OpponentConfig__DisplayNameAssignment_5_1 )
            {
             before(grammarAccess.getOpponentConfigAccess().getDisplayNameAssignment_5_1()); 
            // InternalMazeDsl.g:2475:2: ( rule__OpponentConfig__DisplayNameAssignment_5_1 )
            // InternalMazeDsl.g:2475:3: rule__OpponentConfig__DisplayNameAssignment_5_1
            {
            pushFollow(FOLLOW_2);
            rule__OpponentConfig__DisplayNameAssignment_5_1();

            state._fsp--;


            }

             after(grammarAccess.getOpponentConfigAccess().getDisplayNameAssignment_5_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_5__1__Impl"


    // $ANTLR start "rule__OpponentConfig__Group_6__0"
    // InternalMazeDsl.g:2484:1: rule__OpponentConfig__Group_6__0 : rule__OpponentConfig__Group_6__0__Impl rule__OpponentConfig__Group_6__1 ;
    public final void rule__OpponentConfig__Group_6__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2488:1: ( rule__OpponentConfig__Group_6__0__Impl rule__OpponentConfig__Group_6__1 )
            // InternalMazeDsl.g:2489:2: rule__OpponentConfig__Group_6__0__Impl rule__OpponentConfig__Group_6__1
            {
            pushFollow(FOLLOW_17);
            rule__OpponentConfig__Group_6__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group_6__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_6__0"


    // $ANTLR start "rule__OpponentConfig__Group_6__0__Impl"
    // InternalMazeDsl.g:2496:1: rule__OpponentConfig__Group_6__0__Impl : ( 'health' ) ;
    public final void rule__OpponentConfig__Group_6__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2500:1: ( ( 'health' ) )
            // InternalMazeDsl.g:2501:1: ( 'health' )
            {
            // InternalMazeDsl.g:2501:1: ( 'health' )
            // InternalMazeDsl.g:2502:2: 'health'
            {
             before(grammarAccess.getOpponentConfigAccess().getHealthKeyword_6_0()); 
            match(input,46,FOLLOW_2); 
             after(grammarAccess.getOpponentConfigAccess().getHealthKeyword_6_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_6__0__Impl"


    // $ANTLR start "rule__OpponentConfig__Group_6__1"
    // InternalMazeDsl.g:2511:1: rule__OpponentConfig__Group_6__1 : rule__OpponentConfig__Group_6__1__Impl ;
    public final void rule__OpponentConfig__Group_6__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2515:1: ( rule__OpponentConfig__Group_6__1__Impl )
            // InternalMazeDsl.g:2516:2: rule__OpponentConfig__Group_6__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group_6__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_6__1"


    // $ANTLR start "rule__OpponentConfig__Group_6__1__Impl"
    // InternalMazeDsl.g:2522:1: rule__OpponentConfig__Group_6__1__Impl : ( ( rule__OpponentConfig__HealthAssignment_6_1 ) ) ;
    public final void rule__OpponentConfig__Group_6__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2526:1: ( ( ( rule__OpponentConfig__HealthAssignment_6_1 ) ) )
            // InternalMazeDsl.g:2527:1: ( ( rule__OpponentConfig__HealthAssignment_6_1 ) )
            {
            // InternalMazeDsl.g:2527:1: ( ( rule__OpponentConfig__HealthAssignment_6_1 ) )
            // InternalMazeDsl.g:2528:2: ( rule__OpponentConfig__HealthAssignment_6_1 )
            {
             before(grammarAccess.getOpponentConfigAccess().getHealthAssignment_6_1()); 
            // InternalMazeDsl.g:2529:2: ( rule__OpponentConfig__HealthAssignment_6_1 )
            // InternalMazeDsl.g:2529:3: rule__OpponentConfig__HealthAssignment_6_1
            {
            pushFollow(FOLLOW_2);
            rule__OpponentConfig__HealthAssignment_6_1();

            state._fsp--;


            }

             after(grammarAccess.getOpponentConfigAccess().getHealthAssignment_6_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_6__1__Impl"


    // $ANTLR start "rule__OpponentConfig__Group_7__0"
    // InternalMazeDsl.g:2538:1: rule__OpponentConfig__Group_7__0 : rule__OpponentConfig__Group_7__0__Impl rule__OpponentConfig__Group_7__1 ;
    public final void rule__OpponentConfig__Group_7__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2542:1: ( rule__OpponentConfig__Group_7__0__Impl rule__OpponentConfig__Group_7__1 )
            // InternalMazeDsl.g:2543:2: rule__OpponentConfig__Group_7__0__Impl rule__OpponentConfig__Group_7__1
            {
            pushFollow(FOLLOW_17);
            rule__OpponentConfig__Group_7__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group_7__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_7__0"


    // $ANTLR start "rule__OpponentConfig__Group_7__0__Impl"
    // InternalMazeDsl.g:2550:1: rule__OpponentConfig__Group_7__0__Impl : ( 'speed' ) ;
    public final void rule__OpponentConfig__Group_7__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2554:1: ( ( 'speed' ) )
            // InternalMazeDsl.g:2555:1: ( 'speed' )
            {
            // InternalMazeDsl.g:2555:1: ( 'speed' )
            // InternalMazeDsl.g:2556:2: 'speed'
            {
             before(grammarAccess.getOpponentConfigAccess().getSpeedKeyword_7_0()); 
            match(input,47,FOLLOW_2); 
             after(grammarAccess.getOpponentConfigAccess().getSpeedKeyword_7_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_7__0__Impl"


    // $ANTLR start "rule__OpponentConfig__Group_7__1"
    // InternalMazeDsl.g:2565:1: rule__OpponentConfig__Group_7__1 : rule__OpponentConfig__Group_7__1__Impl ;
    public final void rule__OpponentConfig__Group_7__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2569:1: ( rule__OpponentConfig__Group_7__1__Impl )
            // InternalMazeDsl.g:2570:2: rule__OpponentConfig__Group_7__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group_7__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_7__1"


    // $ANTLR start "rule__OpponentConfig__Group_7__1__Impl"
    // InternalMazeDsl.g:2576:1: rule__OpponentConfig__Group_7__1__Impl : ( ( rule__OpponentConfig__SpeedAssignment_7_1 ) ) ;
    public final void rule__OpponentConfig__Group_7__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2580:1: ( ( ( rule__OpponentConfig__SpeedAssignment_7_1 ) ) )
            // InternalMazeDsl.g:2581:1: ( ( rule__OpponentConfig__SpeedAssignment_7_1 ) )
            {
            // InternalMazeDsl.g:2581:1: ( ( rule__OpponentConfig__SpeedAssignment_7_1 ) )
            // InternalMazeDsl.g:2582:2: ( rule__OpponentConfig__SpeedAssignment_7_1 )
            {
             before(grammarAccess.getOpponentConfigAccess().getSpeedAssignment_7_1()); 
            // InternalMazeDsl.g:2583:2: ( rule__OpponentConfig__SpeedAssignment_7_1 )
            // InternalMazeDsl.g:2583:3: rule__OpponentConfig__SpeedAssignment_7_1
            {
            pushFollow(FOLLOW_2);
            rule__OpponentConfig__SpeedAssignment_7_1();

            state._fsp--;


            }

             after(grammarAccess.getOpponentConfigAccess().getSpeedAssignment_7_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_7__1__Impl"


    // $ANTLR start "rule__OpponentConfig__Group_8__0"
    // InternalMazeDsl.g:2592:1: rule__OpponentConfig__Group_8__0 : rule__OpponentConfig__Group_8__0__Impl rule__OpponentConfig__Group_8__1 ;
    public final void rule__OpponentConfig__Group_8__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2596:1: ( rule__OpponentConfig__Group_8__0__Impl rule__OpponentConfig__Group_8__1 )
            // InternalMazeDsl.g:2597:2: rule__OpponentConfig__Group_8__0__Impl rule__OpponentConfig__Group_8__1
            {
            pushFollow(FOLLOW_17);
            rule__OpponentConfig__Group_8__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group_8__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_8__0"


    // $ANTLR start "rule__OpponentConfig__Group_8__0__Impl"
    // InternalMazeDsl.g:2604:1: rule__OpponentConfig__Group_8__0__Impl : ( 'threatLevel' ) ;
    public final void rule__OpponentConfig__Group_8__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2608:1: ( ( 'threatLevel' ) )
            // InternalMazeDsl.g:2609:1: ( 'threatLevel' )
            {
            // InternalMazeDsl.g:2609:1: ( 'threatLevel' )
            // InternalMazeDsl.g:2610:2: 'threatLevel'
            {
             before(grammarAccess.getOpponentConfigAccess().getThreatLevelKeyword_8_0()); 
            match(input,48,FOLLOW_2); 
             after(grammarAccess.getOpponentConfigAccess().getThreatLevelKeyword_8_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_8__0__Impl"


    // $ANTLR start "rule__OpponentConfig__Group_8__1"
    // InternalMazeDsl.g:2619:1: rule__OpponentConfig__Group_8__1 : rule__OpponentConfig__Group_8__1__Impl ;
    public final void rule__OpponentConfig__Group_8__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2623:1: ( rule__OpponentConfig__Group_8__1__Impl )
            // InternalMazeDsl.g:2624:2: rule__OpponentConfig__Group_8__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group_8__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_8__1"


    // $ANTLR start "rule__OpponentConfig__Group_8__1__Impl"
    // InternalMazeDsl.g:2630:1: rule__OpponentConfig__Group_8__1__Impl : ( ( rule__OpponentConfig__ThreatLevelAssignment_8_1 ) ) ;
    public final void rule__OpponentConfig__Group_8__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2634:1: ( ( ( rule__OpponentConfig__ThreatLevelAssignment_8_1 ) ) )
            // InternalMazeDsl.g:2635:1: ( ( rule__OpponentConfig__ThreatLevelAssignment_8_1 ) )
            {
            // InternalMazeDsl.g:2635:1: ( ( rule__OpponentConfig__ThreatLevelAssignment_8_1 ) )
            // InternalMazeDsl.g:2636:2: ( rule__OpponentConfig__ThreatLevelAssignment_8_1 )
            {
             before(grammarAccess.getOpponentConfigAccess().getThreatLevelAssignment_8_1()); 
            // InternalMazeDsl.g:2637:2: ( rule__OpponentConfig__ThreatLevelAssignment_8_1 )
            // InternalMazeDsl.g:2637:3: rule__OpponentConfig__ThreatLevelAssignment_8_1
            {
            pushFollow(FOLLOW_2);
            rule__OpponentConfig__ThreatLevelAssignment_8_1();

            state._fsp--;


            }

             after(grammarAccess.getOpponentConfigAccess().getThreatLevelAssignment_8_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_8__1__Impl"


    // $ANTLR start "rule__OpponentConfig__Group_9__0"
    // InternalMazeDsl.g:2646:1: rule__OpponentConfig__Group_9__0 : rule__OpponentConfig__Group_9__0__Impl rule__OpponentConfig__Group_9__1 ;
    public final void rule__OpponentConfig__Group_9__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2650:1: ( rule__OpponentConfig__Group_9__0__Impl rule__OpponentConfig__Group_9__1 )
            // InternalMazeDsl.g:2651:2: rule__OpponentConfig__Group_9__0__Impl rule__OpponentConfig__Group_9__1
            {
            pushFollow(FOLLOW_22);
            rule__OpponentConfig__Group_9__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group_9__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_9__0"


    // $ANTLR start "rule__OpponentConfig__Group_9__0__Impl"
    // InternalMazeDsl.g:2658:1: rule__OpponentConfig__Group_9__0__Impl : ( 'enabled' ) ;
    public final void rule__OpponentConfig__Group_9__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2662:1: ( ( 'enabled' ) )
            // InternalMazeDsl.g:2663:1: ( 'enabled' )
            {
            // InternalMazeDsl.g:2663:1: ( 'enabled' )
            // InternalMazeDsl.g:2664:2: 'enabled'
            {
             before(grammarAccess.getOpponentConfigAccess().getEnabledKeyword_9_0()); 
            match(input,49,FOLLOW_2); 
             after(grammarAccess.getOpponentConfigAccess().getEnabledKeyword_9_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_9__0__Impl"


    // $ANTLR start "rule__OpponentConfig__Group_9__1"
    // InternalMazeDsl.g:2673:1: rule__OpponentConfig__Group_9__1 : rule__OpponentConfig__Group_9__1__Impl ;
    public final void rule__OpponentConfig__Group_9__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2677:1: ( rule__OpponentConfig__Group_9__1__Impl )
            // InternalMazeDsl.g:2678:2: rule__OpponentConfig__Group_9__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group_9__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_9__1"


    // $ANTLR start "rule__OpponentConfig__Group_9__1__Impl"
    // InternalMazeDsl.g:2684:1: rule__OpponentConfig__Group_9__1__Impl : ( ( rule__OpponentConfig__EnabledAssignment_9_1 ) ) ;
    public final void rule__OpponentConfig__Group_9__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2688:1: ( ( ( rule__OpponentConfig__EnabledAssignment_9_1 ) ) )
            // InternalMazeDsl.g:2689:1: ( ( rule__OpponentConfig__EnabledAssignment_9_1 ) )
            {
            // InternalMazeDsl.g:2689:1: ( ( rule__OpponentConfig__EnabledAssignment_9_1 ) )
            // InternalMazeDsl.g:2690:2: ( rule__OpponentConfig__EnabledAssignment_9_1 )
            {
             before(grammarAccess.getOpponentConfigAccess().getEnabledAssignment_9_1()); 
            // InternalMazeDsl.g:2691:2: ( rule__OpponentConfig__EnabledAssignment_9_1 )
            // InternalMazeDsl.g:2691:3: rule__OpponentConfig__EnabledAssignment_9_1
            {
            pushFollow(FOLLOW_2);
            rule__OpponentConfig__EnabledAssignment_9_1();

            state._fsp--;


            }

             after(grammarAccess.getOpponentConfigAccess().getEnabledAssignment_9_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_9__1__Impl"


    // $ANTLR start "rule__OpponentConfig__Group_10__0"
    // InternalMazeDsl.g:2700:1: rule__OpponentConfig__Group_10__0 : rule__OpponentConfig__Group_10__0__Impl rule__OpponentConfig__Group_10__1 ;
    public final void rule__OpponentConfig__Group_10__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2704:1: ( rule__OpponentConfig__Group_10__0__Impl rule__OpponentConfig__Group_10__1 )
            // InternalMazeDsl.g:2705:2: rule__OpponentConfig__Group_10__0__Impl rule__OpponentConfig__Group_10__1
            {
            pushFollow(FOLLOW_23);
            rule__OpponentConfig__Group_10__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group_10__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_10__0"


    // $ANTLR start "rule__OpponentConfig__Group_10__0__Impl"
    // InternalMazeDsl.g:2712:1: rule__OpponentConfig__Group_10__0__Impl : ( 'behavior' ) ;
    public final void rule__OpponentConfig__Group_10__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2716:1: ( ( 'behavior' ) )
            // InternalMazeDsl.g:2717:1: ( 'behavior' )
            {
            // InternalMazeDsl.g:2717:1: ( 'behavior' )
            // InternalMazeDsl.g:2718:2: 'behavior'
            {
             before(grammarAccess.getOpponentConfigAccess().getBehaviorKeyword_10_0()); 
            match(input,50,FOLLOW_2); 
             after(grammarAccess.getOpponentConfigAccess().getBehaviorKeyword_10_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_10__0__Impl"


    // $ANTLR start "rule__OpponentConfig__Group_10__1"
    // InternalMazeDsl.g:2727:1: rule__OpponentConfig__Group_10__1 : rule__OpponentConfig__Group_10__1__Impl ;
    public final void rule__OpponentConfig__Group_10__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2731:1: ( rule__OpponentConfig__Group_10__1__Impl )
            // InternalMazeDsl.g:2732:2: rule__OpponentConfig__Group_10__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group_10__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_10__1"


    // $ANTLR start "rule__OpponentConfig__Group_10__1__Impl"
    // InternalMazeDsl.g:2738:1: rule__OpponentConfig__Group_10__1__Impl : ( ( rule__OpponentConfig__BehaviorAssignment_10_1 ) ) ;
    public final void rule__OpponentConfig__Group_10__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2742:1: ( ( ( rule__OpponentConfig__BehaviorAssignment_10_1 ) ) )
            // InternalMazeDsl.g:2743:1: ( ( rule__OpponentConfig__BehaviorAssignment_10_1 ) )
            {
            // InternalMazeDsl.g:2743:1: ( ( rule__OpponentConfig__BehaviorAssignment_10_1 ) )
            // InternalMazeDsl.g:2744:2: ( rule__OpponentConfig__BehaviorAssignment_10_1 )
            {
             before(grammarAccess.getOpponentConfigAccess().getBehaviorAssignment_10_1()); 
            // InternalMazeDsl.g:2745:2: ( rule__OpponentConfig__BehaviorAssignment_10_1 )
            // InternalMazeDsl.g:2745:3: rule__OpponentConfig__BehaviorAssignment_10_1
            {
            pushFollow(FOLLOW_2);
            rule__OpponentConfig__BehaviorAssignment_10_1();

            state._fsp--;


            }

             after(grammarAccess.getOpponentConfigAccess().getBehaviorAssignment_10_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_10__1__Impl"


    // $ANTLR start "rule__OpponentConfig__Group_12__0"
    // InternalMazeDsl.g:2754:1: rule__OpponentConfig__Group_12__0 : rule__OpponentConfig__Group_12__0__Impl rule__OpponentConfig__Group_12__1 ;
    public final void rule__OpponentConfig__Group_12__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2758:1: ( rule__OpponentConfig__Group_12__0__Impl rule__OpponentConfig__Group_12__1 )
            // InternalMazeDsl.g:2759:2: rule__OpponentConfig__Group_12__0__Impl rule__OpponentConfig__Group_12__1
            {
            pushFollow(FOLLOW_3);
            rule__OpponentConfig__Group_12__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group_12__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_12__0"


    // $ANTLR start "rule__OpponentConfig__Group_12__0__Impl"
    // InternalMazeDsl.g:2766:1: rule__OpponentConfig__Group_12__0__Impl : ( 'patrol' ) ;
    public final void rule__OpponentConfig__Group_12__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2770:1: ( ( 'patrol' ) )
            // InternalMazeDsl.g:2771:1: ( 'patrol' )
            {
            // InternalMazeDsl.g:2771:1: ( 'patrol' )
            // InternalMazeDsl.g:2772:2: 'patrol'
            {
             before(grammarAccess.getOpponentConfigAccess().getPatrolKeyword_12_0()); 
            match(input,23,FOLLOW_2); 
             after(grammarAccess.getOpponentConfigAccess().getPatrolKeyword_12_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_12__0__Impl"


    // $ANTLR start "rule__OpponentConfig__Group_12__1"
    // InternalMazeDsl.g:2781:1: rule__OpponentConfig__Group_12__1 : rule__OpponentConfig__Group_12__1__Impl ;
    public final void rule__OpponentConfig__Group_12__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2785:1: ( rule__OpponentConfig__Group_12__1__Impl )
            // InternalMazeDsl.g:2786:2: rule__OpponentConfig__Group_12__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group_12__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_12__1"


    // $ANTLR start "rule__OpponentConfig__Group_12__1__Impl"
    // InternalMazeDsl.g:2792:1: rule__OpponentConfig__Group_12__1__Impl : ( ( rule__OpponentConfig__PatrolRefAssignment_12_1 ) ) ;
    public final void rule__OpponentConfig__Group_12__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2796:1: ( ( ( rule__OpponentConfig__PatrolRefAssignment_12_1 ) ) )
            // InternalMazeDsl.g:2797:1: ( ( rule__OpponentConfig__PatrolRefAssignment_12_1 ) )
            {
            // InternalMazeDsl.g:2797:1: ( ( rule__OpponentConfig__PatrolRefAssignment_12_1 ) )
            // InternalMazeDsl.g:2798:2: ( rule__OpponentConfig__PatrolRefAssignment_12_1 )
            {
             before(grammarAccess.getOpponentConfigAccess().getPatrolRefAssignment_12_1()); 
            // InternalMazeDsl.g:2799:2: ( rule__OpponentConfig__PatrolRefAssignment_12_1 )
            // InternalMazeDsl.g:2799:3: rule__OpponentConfig__PatrolRefAssignment_12_1
            {
            pushFollow(FOLLOW_2);
            rule__OpponentConfig__PatrolRefAssignment_12_1();

            state._fsp--;


            }

             after(grammarAccess.getOpponentConfigAccess().getPatrolRefAssignment_12_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_12__1__Impl"


    // $ANTLR start "rule__OpponentConfig__Group_13__0"
    // InternalMazeDsl.g:2808:1: rule__OpponentConfig__Group_13__0 : rule__OpponentConfig__Group_13__0__Impl rule__OpponentConfig__Group_13__1 ;
    public final void rule__OpponentConfig__Group_13__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2812:1: ( rule__OpponentConfig__Group_13__0__Impl rule__OpponentConfig__Group_13__1 )
            // InternalMazeDsl.g:2813:2: rule__OpponentConfig__Group_13__0__Impl rule__OpponentConfig__Group_13__1
            {
            pushFollow(FOLLOW_3);
            rule__OpponentConfig__Group_13__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group_13__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_13__0"


    // $ANTLR start "rule__OpponentConfig__Group_13__0__Impl"
    // InternalMazeDsl.g:2820:1: rule__OpponentConfig__Group_13__0__Impl : ( 'loot' ) ;
    public final void rule__OpponentConfig__Group_13__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2824:1: ( ( 'loot' ) )
            // InternalMazeDsl.g:2825:1: ( 'loot' )
            {
            // InternalMazeDsl.g:2825:1: ( 'loot' )
            // InternalMazeDsl.g:2826:2: 'loot'
            {
             before(grammarAccess.getOpponentConfigAccess().getLootKeyword_13_0()); 
            match(input,51,FOLLOW_2); 
             after(grammarAccess.getOpponentConfigAccess().getLootKeyword_13_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_13__0__Impl"


    // $ANTLR start "rule__OpponentConfig__Group_13__1"
    // InternalMazeDsl.g:2835:1: rule__OpponentConfig__Group_13__1 : rule__OpponentConfig__Group_13__1__Impl ;
    public final void rule__OpponentConfig__Group_13__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2839:1: ( rule__OpponentConfig__Group_13__1__Impl )
            // InternalMazeDsl.g:2840:2: rule__OpponentConfig__Group_13__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group_13__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_13__1"


    // $ANTLR start "rule__OpponentConfig__Group_13__1__Impl"
    // InternalMazeDsl.g:2846:1: rule__OpponentConfig__Group_13__1__Impl : ( ( rule__OpponentConfig__LootRefAssignment_13_1 ) ) ;
    public final void rule__OpponentConfig__Group_13__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2850:1: ( ( ( rule__OpponentConfig__LootRefAssignment_13_1 ) ) )
            // InternalMazeDsl.g:2851:1: ( ( rule__OpponentConfig__LootRefAssignment_13_1 ) )
            {
            // InternalMazeDsl.g:2851:1: ( ( rule__OpponentConfig__LootRefAssignment_13_1 ) )
            // InternalMazeDsl.g:2852:2: ( rule__OpponentConfig__LootRefAssignment_13_1 )
            {
             before(grammarAccess.getOpponentConfigAccess().getLootRefAssignment_13_1()); 
            // InternalMazeDsl.g:2853:2: ( rule__OpponentConfig__LootRefAssignment_13_1 )
            // InternalMazeDsl.g:2853:3: rule__OpponentConfig__LootRefAssignment_13_1
            {
            pushFollow(FOLLOW_2);
            rule__OpponentConfig__LootRefAssignment_13_1();

            state._fsp--;


            }

             after(grammarAccess.getOpponentConfigAccess().getLootRefAssignment_13_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_13__1__Impl"


    // $ANTLR start "rule__ZombieSpecifics__Group__0"
    // InternalMazeDsl.g:2862:1: rule__ZombieSpecifics__Group__0 : rule__ZombieSpecifics__Group__0__Impl rule__ZombieSpecifics__Group__1 ;
    public final void rule__ZombieSpecifics__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2866:1: ( rule__ZombieSpecifics__Group__0__Impl rule__ZombieSpecifics__Group__1 )
            // InternalMazeDsl.g:2867:2: rule__ZombieSpecifics__Group__0__Impl rule__ZombieSpecifics__Group__1
            {
            pushFollow(FOLLOW_24);
            rule__ZombieSpecifics__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ZombieSpecifics__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__Group__0"


    // $ANTLR start "rule__ZombieSpecifics__Group__0__Impl"
    // InternalMazeDsl.g:2874:1: rule__ZombieSpecifics__Group__0__Impl : ( () ) ;
    public final void rule__ZombieSpecifics__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2878:1: ( ( () ) )
            // InternalMazeDsl.g:2879:1: ( () )
            {
            // InternalMazeDsl.g:2879:1: ( () )
            // InternalMazeDsl.g:2880:2: ()
            {
             before(grammarAccess.getZombieSpecificsAccess().getZombieSpecificsAction_0()); 
            // InternalMazeDsl.g:2881:2: ()
            // InternalMazeDsl.g:2881:3: 
            {
            }

             after(grammarAccess.getZombieSpecificsAccess().getZombieSpecificsAction_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__Group__0__Impl"


    // $ANTLR start "rule__ZombieSpecifics__Group__1"
    // InternalMazeDsl.g:2889:1: rule__ZombieSpecifics__Group__1 : rule__ZombieSpecifics__Group__1__Impl rule__ZombieSpecifics__Group__2 ;
    public final void rule__ZombieSpecifics__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2893:1: ( rule__ZombieSpecifics__Group__1__Impl rule__ZombieSpecifics__Group__2 )
            // InternalMazeDsl.g:2894:2: rule__ZombieSpecifics__Group__1__Impl rule__ZombieSpecifics__Group__2
            {
            pushFollow(FOLLOW_4);
            rule__ZombieSpecifics__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ZombieSpecifics__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__Group__1"


    // $ANTLR start "rule__ZombieSpecifics__Group__1__Impl"
    // InternalMazeDsl.g:2901:1: rule__ZombieSpecifics__Group__1__Impl : ( 'zombie-stats' ) ;
    public final void rule__ZombieSpecifics__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2905:1: ( ( 'zombie-stats' ) )
            // InternalMazeDsl.g:2906:1: ( 'zombie-stats' )
            {
            // InternalMazeDsl.g:2906:1: ( 'zombie-stats' )
            // InternalMazeDsl.g:2907:2: 'zombie-stats'
            {
             before(grammarAccess.getZombieSpecificsAccess().getZombieStatsKeyword_1()); 
            match(input,52,FOLLOW_2); 
             after(grammarAccess.getZombieSpecificsAccess().getZombieStatsKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__Group__1__Impl"


    // $ANTLR start "rule__ZombieSpecifics__Group__2"
    // InternalMazeDsl.g:2916:1: rule__ZombieSpecifics__Group__2 : rule__ZombieSpecifics__Group__2__Impl rule__ZombieSpecifics__Group__3 ;
    public final void rule__ZombieSpecifics__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2920:1: ( rule__ZombieSpecifics__Group__2__Impl rule__ZombieSpecifics__Group__3 )
            // InternalMazeDsl.g:2921:2: rule__ZombieSpecifics__Group__2__Impl rule__ZombieSpecifics__Group__3
            {
            pushFollow(FOLLOW_25);
            rule__ZombieSpecifics__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ZombieSpecifics__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__Group__2"


    // $ANTLR start "rule__ZombieSpecifics__Group__2__Impl"
    // InternalMazeDsl.g:2928:1: rule__ZombieSpecifics__Group__2__Impl : ( '{' ) ;
    public final void rule__ZombieSpecifics__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2932:1: ( ( '{' ) )
            // InternalMazeDsl.g:2933:1: ( '{' )
            {
            // InternalMazeDsl.g:2933:1: ( '{' )
            // InternalMazeDsl.g:2934:2: '{'
            {
             before(grammarAccess.getZombieSpecificsAccess().getLeftCurlyBracketKeyword_2()); 
            match(input,32,FOLLOW_2); 
             after(grammarAccess.getZombieSpecificsAccess().getLeftCurlyBracketKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__Group__2__Impl"


    // $ANTLR start "rule__ZombieSpecifics__Group__3"
    // InternalMazeDsl.g:2943:1: rule__ZombieSpecifics__Group__3 : rule__ZombieSpecifics__Group__3__Impl rule__ZombieSpecifics__Group__4 ;
    public final void rule__ZombieSpecifics__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2947:1: ( rule__ZombieSpecifics__Group__3__Impl rule__ZombieSpecifics__Group__4 )
            // InternalMazeDsl.g:2948:2: rule__ZombieSpecifics__Group__3__Impl rule__ZombieSpecifics__Group__4
            {
            pushFollow(FOLLOW_25);
            rule__ZombieSpecifics__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ZombieSpecifics__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__Group__3"


    // $ANTLR start "rule__ZombieSpecifics__Group__3__Impl"
    // InternalMazeDsl.g:2955:1: rule__ZombieSpecifics__Group__3__Impl : ( ( rule__ZombieSpecifics__Group_3__0 )? ) ;
    public final void rule__ZombieSpecifics__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2959:1: ( ( ( rule__ZombieSpecifics__Group_3__0 )? ) )
            // InternalMazeDsl.g:2960:1: ( ( rule__ZombieSpecifics__Group_3__0 )? )
            {
            // InternalMazeDsl.g:2960:1: ( ( rule__ZombieSpecifics__Group_3__0 )? )
            // InternalMazeDsl.g:2961:2: ( rule__ZombieSpecifics__Group_3__0 )?
            {
             before(grammarAccess.getZombieSpecificsAccess().getGroup_3()); 
            // InternalMazeDsl.g:2962:2: ( rule__ZombieSpecifics__Group_3__0 )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==53) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // InternalMazeDsl.g:2962:3: rule__ZombieSpecifics__Group_3__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__ZombieSpecifics__Group_3__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getZombieSpecificsAccess().getGroup_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__Group__3__Impl"


    // $ANTLR start "rule__ZombieSpecifics__Group__4"
    // InternalMazeDsl.g:2970:1: rule__ZombieSpecifics__Group__4 : rule__ZombieSpecifics__Group__4__Impl rule__ZombieSpecifics__Group__5 ;
    public final void rule__ZombieSpecifics__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2974:1: ( rule__ZombieSpecifics__Group__4__Impl rule__ZombieSpecifics__Group__5 )
            // InternalMazeDsl.g:2975:2: rule__ZombieSpecifics__Group__4__Impl rule__ZombieSpecifics__Group__5
            {
            pushFollow(FOLLOW_25);
            rule__ZombieSpecifics__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ZombieSpecifics__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__Group__4"


    // $ANTLR start "rule__ZombieSpecifics__Group__4__Impl"
    // InternalMazeDsl.g:2982:1: rule__ZombieSpecifics__Group__4__Impl : ( ( rule__ZombieSpecifics__Group_4__0 )? ) ;
    public final void rule__ZombieSpecifics__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2986:1: ( ( ( rule__ZombieSpecifics__Group_4__0 )? ) )
            // InternalMazeDsl.g:2987:1: ( ( rule__ZombieSpecifics__Group_4__0 )? )
            {
            // InternalMazeDsl.g:2987:1: ( ( rule__ZombieSpecifics__Group_4__0 )? )
            // InternalMazeDsl.g:2988:2: ( rule__ZombieSpecifics__Group_4__0 )?
            {
             before(grammarAccess.getZombieSpecificsAccess().getGroup_4()); 
            // InternalMazeDsl.g:2989:2: ( rule__ZombieSpecifics__Group_4__0 )?
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==54) ) {
                alt31=1;
            }
            switch (alt31) {
                case 1 :
                    // InternalMazeDsl.g:2989:3: rule__ZombieSpecifics__Group_4__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__ZombieSpecifics__Group_4__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getZombieSpecificsAccess().getGroup_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__Group__4__Impl"


    // $ANTLR start "rule__ZombieSpecifics__Group__5"
    // InternalMazeDsl.g:2997:1: rule__ZombieSpecifics__Group__5 : rule__ZombieSpecifics__Group__5__Impl rule__ZombieSpecifics__Group__6 ;
    public final void rule__ZombieSpecifics__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3001:1: ( rule__ZombieSpecifics__Group__5__Impl rule__ZombieSpecifics__Group__6 )
            // InternalMazeDsl.g:3002:2: rule__ZombieSpecifics__Group__5__Impl rule__ZombieSpecifics__Group__6
            {
            pushFollow(FOLLOW_25);
            rule__ZombieSpecifics__Group__5__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ZombieSpecifics__Group__6();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__Group__5"


    // $ANTLR start "rule__ZombieSpecifics__Group__5__Impl"
    // InternalMazeDsl.g:3009:1: rule__ZombieSpecifics__Group__5__Impl : ( ( rule__ZombieSpecifics__Group_5__0 )? ) ;
    public final void rule__ZombieSpecifics__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3013:1: ( ( ( rule__ZombieSpecifics__Group_5__0 )? ) )
            // InternalMazeDsl.g:3014:1: ( ( rule__ZombieSpecifics__Group_5__0 )? )
            {
            // InternalMazeDsl.g:3014:1: ( ( rule__ZombieSpecifics__Group_5__0 )? )
            // InternalMazeDsl.g:3015:2: ( rule__ZombieSpecifics__Group_5__0 )?
            {
             before(grammarAccess.getZombieSpecificsAccess().getGroup_5()); 
            // InternalMazeDsl.g:3016:2: ( rule__ZombieSpecifics__Group_5__0 )?
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==55) ) {
                alt32=1;
            }
            switch (alt32) {
                case 1 :
                    // InternalMazeDsl.g:3016:3: rule__ZombieSpecifics__Group_5__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__ZombieSpecifics__Group_5__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getZombieSpecificsAccess().getGroup_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__Group__5__Impl"


    // $ANTLR start "rule__ZombieSpecifics__Group__6"
    // InternalMazeDsl.g:3024:1: rule__ZombieSpecifics__Group__6 : rule__ZombieSpecifics__Group__6__Impl ;
    public final void rule__ZombieSpecifics__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3028:1: ( rule__ZombieSpecifics__Group__6__Impl )
            // InternalMazeDsl.g:3029:2: rule__ZombieSpecifics__Group__6__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ZombieSpecifics__Group__6__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__Group__6"


    // $ANTLR start "rule__ZombieSpecifics__Group__6__Impl"
    // InternalMazeDsl.g:3035:1: rule__ZombieSpecifics__Group__6__Impl : ( '}' ) ;
    public final void rule__ZombieSpecifics__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3039:1: ( ( '}' ) )
            // InternalMazeDsl.g:3040:1: ( '}' )
            {
            // InternalMazeDsl.g:3040:1: ( '}' )
            // InternalMazeDsl.g:3041:2: '}'
            {
             before(grammarAccess.getZombieSpecificsAccess().getRightCurlyBracketKeyword_6()); 
            match(input,33,FOLLOW_2); 
             after(grammarAccess.getZombieSpecificsAccess().getRightCurlyBracketKeyword_6()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__Group__6__Impl"


    // $ANTLR start "rule__ZombieSpecifics__Group_3__0"
    // InternalMazeDsl.g:3051:1: rule__ZombieSpecifics__Group_3__0 : rule__ZombieSpecifics__Group_3__0__Impl rule__ZombieSpecifics__Group_3__1 ;
    public final void rule__ZombieSpecifics__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3055:1: ( rule__ZombieSpecifics__Group_3__0__Impl rule__ZombieSpecifics__Group_3__1 )
            // InternalMazeDsl.g:3056:2: rule__ZombieSpecifics__Group_3__0__Impl rule__ZombieSpecifics__Group_3__1
            {
            pushFollow(FOLLOW_26);
            rule__ZombieSpecifics__Group_3__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ZombieSpecifics__Group_3__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__Group_3__0"


    // $ANTLR start "rule__ZombieSpecifics__Group_3__0__Impl"
    // InternalMazeDsl.g:3063:1: rule__ZombieSpecifics__Group_3__0__Impl : ( 'attackDamage' ) ;
    public final void rule__ZombieSpecifics__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3067:1: ( ( 'attackDamage' ) )
            // InternalMazeDsl.g:3068:1: ( 'attackDamage' )
            {
            // InternalMazeDsl.g:3068:1: ( 'attackDamage' )
            // InternalMazeDsl.g:3069:2: 'attackDamage'
            {
             before(grammarAccess.getZombieSpecificsAccess().getAttackDamageKeyword_3_0()); 
            match(input,53,FOLLOW_2); 
             after(grammarAccess.getZombieSpecificsAccess().getAttackDamageKeyword_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__Group_3__0__Impl"


    // $ANTLR start "rule__ZombieSpecifics__Group_3__1"
    // InternalMazeDsl.g:3078:1: rule__ZombieSpecifics__Group_3__1 : rule__ZombieSpecifics__Group_3__1__Impl ;
    public final void rule__ZombieSpecifics__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3082:1: ( rule__ZombieSpecifics__Group_3__1__Impl )
            // InternalMazeDsl.g:3083:2: rule__ZombieSpecifics__Group_3__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ZombieSpecifics__Group_3__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__Group_3__1"


    // $ANTLR start "rule__ZombieSpecifics__Group_3__1__Impl"
    // InternalMazeDsl.g:3089:1: rule__ZombieSpecifics__Group_3__1__Impl : ( ( rule__ZombieSpecifics__AttackDamageAssignment_3_1 ) ) ;
    public final void rule__ZombieSpecifics__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3093:1: ( ( ( rule__ZombieSpecifics__AttackDamageAssignment_3_1 ) ) )
            // InternalMazeDsl.g:3094:1: ( ( rule__ZombieSpecifics__AttackDamageAssignment_3_1 ) )
            {
            // InternalMazeDsl.g:3094:1: ( ( rule__ZombieSpecifics__AttackDamageAssignment_3_1 ) )
            // InternalMazeDsl.g:3095:2: ( rule__ZombieSpecifics__AttackDamageAssignment_3_1 )
            {
             before(grammarAccess.getZombieSpecificsAccess().getAttackDamageAssignment_3_1()); 
            // InternalMazeDsl.g:3096:2: ( rule__ZombieSpecifics__AttackDamageAssignment_3_1 )
            // InternalMazeDsl.g:3096:3: rule__ZombieSpecifics__AttackDamageAssignment_3_1
            {
            pushFollow(FOLLOW_2);
            rule__ZombieSpecifics__AttackDamageAssignment_3_1();

            state._fsp--;


            }

             after(grammarAccess.getZombieSpecificsAccess().getAttackDamageAssignment_3_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__Group_3__1__Impl"


    // $ANTLR start "rule__ZombieSpecifics__Group_4__0"
    // InternalMazeDsl.g:3105:1: rule__ZombieSpecifics__Group_4__0 : rule__ZombieSpecifics__Group_4__0__Impl rule__ZombieSpecifics__Group_4__1 ;
    public final void rule__ZombieSpecifics__Group_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3109:1: ( rule__ZombieSpecifics__Group_4__0__Impl rule__ZombieSpecifics__Group_4__1 )
            // InternalMazeDsl.g:3110:2: rule__ZombieSpecifics__Group_4__0__Impl rule__ZombieSpecifics__Group_4__1
            {
            pushFollow(FOLLOW_26);
            rule__ZombieSpecifics__Group_4__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ZombieSpecifics__Group_4__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__Group_4__0"


    // $ANTLR start "rule__ZombieSpecifics__Group_4__0__Impl"
    // InternalMazeDsl.g:3117:1: rule__ZombieSpecifics__Group_4__0__Impl : ( 'infectionLevel' ) ;
    public final void rule__ZombieSpecifics__Group_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3121:1: ( ( 'infectionLevel' ) )
            // InternalMazeDsl.g:3122:1: ( 'infectionLevel' )
            {
            // InternalMazeDsl.g:3122:1: ( 'infectionLevel' )
            // InternalMazeDsl.g:3123:2: 'infectionLevel'
            {
             before(grammarAccess.getZombieSpecificsAccess().getInfectionLevelKeyword_4_0()); 
            match(input,54,FOLLOW_2); 
             after(grammarAccess.getZombieSpecificsAccess().getInfectionLevelKeyword_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__Group_4__0__Impl"


    // $ANTLR start "rule__ZombieSpecifics__Group_4__1"
    // InternalMazeDsl.g:3132:1: rule__ZombieSpecifics__Group_4__1 : rule__ZombieSpecifics__Group_4__1__Impl ;
    public final void rule__ZombieSpecifics__Group_4__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3136:1: ( rule__ZombieSpecifics__Group_4__1__Impl )
            // InternalMazeDsl.g:3137:2: rule__ZombieSpecifics__Group_4__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ZombieSpecifics__Group_4__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__Group_4__1"


    // $ANTLR start "rule__ZombieSpecifics__Group_4__1__Impl"
    // InternalMazeDsl.g:3143:1: rule__ZombieSpecifics__Group_4__1__Impl : ( ( rule__ZombieSpecifics__InfectionLevelAssignment_4_1 ) ) ;
    public final void rule__ZombieSpecifics__Group_4__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3147:1: ( ( ( rule__ZombieSpecifics__InfectionLevelAssignment_4_1 ) ) )
            // InternalMazeDsl.g:3148:1: ( ( rule__ZombieSpecifics__InfectionLevelAssignment_4_1 ) )
            {
            // InternalMazeDsl.g:3148:1: ( ( rule__ZombieSpecifics__InfectionLevelAssignment_4_1 ) )
            // InternalMazeDsl.g:3149:2: ( rule__ZombieSpecifics__InfectionLevelAssignment_4_1 )
            {
             before(grammarAccess.getZombieSpecificsAccess().getInfectionLevelAssignment_4_1()); 
            // InternalMazeDsl.g:3150:2: ( rule__ZombieSpecifics__InfectionLevelAssignment_4_1 )
            // InternalMazeDsl.g:3150:3: rule__ZombieSpecifics__InfectionLevelAssignment_4_1
            {
            pushFollow(FOLLOW_2);
            rule__ZombieSpecifics__InfectionLevelAssignment_4_1();

            state._fsp--;


            }

             after(grammarAccess.getZombieSpecificsAccess().getInfectionLevelAssignment_4_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__Group_4__1__Impl"


    // $ANTLR start "rule__ZombieSpecifics__Group_5__0"
    // InternalMazeDsl.g:3159:1: rule__ZombieSpecifics__Group_5__0 : rule__ZombieSpecifics__Group_5__0__Impl rule__ZombieSpecifics__Group_5__1 ;
    public final void rule__ZombieSpecifics__Group_5__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3163:1: ( rule__ZombieSpecifics__Group_5__0__Impl rule__ZombieSpecifics__Group_5__1 )
            // InternalMazeDsl.g:3164:2: rule__ZombieSpecifics__Group_5__0__Impl rule__ZombieSpecifics__Group_5__1
            {
            pushFollow(FOLLOW_26);
            rule__ZombieSpecifics__Group_5__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ZombieSpecifics__Group_5__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__Group_5__0"


    // $ANTLR start "rule__ZombieSpecifics__Group_5__0__Impl"
    // InternalMazeDsl.g:3171:1: rule__ZombieSpecifics__Group_5__0__Impl : ( 'resurrectionTime' ) ;
    public final void rule__ZombieSpecifics__Group_5__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3175:1: ( ( 'resurrectionTime' ) )
            // InternalMazeDsl.g:3176:1: ( 'resurrectionTime' )
            {
            // InternalMazeDsl.g:3176:1: ( 'resurrectionTime' )
            // InternalMazeDsl.g:3177:2: 'resurrectionTime'
            {
             before(grammarAccess.getZombieSpecificsAccess().getResurrectionTimeKeyword_5_0()); 
            match(input,55,FOLLOW_2); 
             after(grammarAccess.getZombieSpecificsAccess().getResurrectionTimeKeyword_5_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__Group_5__0__Impl"


    // $ANTLR start "rule__ZombieSpecifics__Group_5__1"
    // InternalMazeDsl.g:3186:1: rule__ZombieSpecifics__Group_5__1 : rule__ZombieSpecifics__Group_5__1__Impl ;
    public final void rule__ZombieSpecifics__Group_5__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3190:1: ( rule__ZombieSpecifics__Group_5__1__Impl )
            // InternalMazeDsl.g:3191:2: rule__ZombieSpecifics__Group_5__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ZombieSpecifics__Group_5__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__Group_5__1"


    // $ANTLR start "rule__ZombieSpecifics__Group_5__1__Impl"
    // InternalMazeDsl.g:3197:1: rule__ZombieSpecifics__Group_5__1__Impl : ( ( rule__ZombieSpecifics__ResurrectionTimeAssignment_5_1 ) ) ;
    public final void rule__ZombieSpecifics__Group_5__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3201:1: ( ( ( rule__ZombieSpecifics__ResurrectionTimeAssignment_5_1 ) ) )
            // InternalMazeDsl.g:3202:1: ( ( rule__ZombieSpecifics__ResurrectionTimeAssignment_5_1 ) )
            {
            // InternalMazeDsl.g:3202:1: ( ( rule__ZombieSpecifics__ResurrectionTimeAssignment_5_1 ) )
            // InternalMazeDsl.g:3203:2: ( rule__ZombieSpecifics__ResurrectionTimeAssignment_5_1 )
            {
             before(grammarAccess.getZombieSpecificsAccess().getResurrectionTimeAssignment_5_1()); 
            // InternalMazeDsl.g:3204:2: ( rule__ZombieSpecifics__ResurrectionTimeAssignment_5_1 )
            // InternalMazeDsl.g:3204:3: rule__ZombieSpecifics__ResurrectionTimeAssignment_5_1
            {
            pushFollow(FOLLOW_2);
            rule__ZombieSpecifics__ResurrectionTimeAssignment_5_1();

            state._fsp--;


            }

             after(grammarAccess.getZombieSpecificsAccess().getResurrectionTimeAssignment_5_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__Group_5__1__Impl"


    // $ANTLR start "rule__GhostSpecifics__Group__0"
    // InternalMazeDsl.g:3213:1: rule__GhostSpecifics__Group__0 : rule__GhostSpecifics__Group__0__Impl rule__GhostSpecifics__Group__1 ;
    public final void rule__GhostSpecifics__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3217:1: ( rule__GhostSpecifics__Group__0__Impl rule__GhostSpecifics__Group__1 )
            // InternalMazeDsl.g:3218:2: rule__GhostSpecifics__Group__0__Impl rule__GhostSpecifics__Group__1
            {
            pushFollow(FOLLOW_27);
            rule__GhostSpecifics__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__GhostSpecifics__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__Group__0"


    // $ANTLR start "rule__GhostSpecifics__Group__0__Impl"
    // InternalMazeDsl.g:3225:1: rule__GhostSpecifics__Group__0__Impl : ( () ) ;
    public final void rule__GhostSpecifics__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3229:1: ( ( () ) )
            // InternalMazeDsl.g:3230:1: ( () )
            {
            // InternalMazeDsl.g:3230:1: ( () )
            // InternalMazeDsl.g:3231:2: ()
            {
             before(grammarAccess.getGhostSpecificsAccess().getGhostSpecificsAction_0()); 
            // InternalMazeDsl.g:3232:2: ()
            // InternalMazeDsl.g:3232:3: 
            {
            }

             after(grammarAccess.getGhostSpecificsAccess().getGhostSpecificsAction_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__Group__0__Impl"


    // $ANTLR start "rule__GhostSpecifics__Group__1"
    // InternalMazeDsl.g:3240:1: rule__GhostSpecifics__Group__1 : rule__GhostSpecifics__Group__1__Impl rule__GhostSpecifics__Group__2 ;
    public final void rule__GhostSpecifics__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3244:1: ( rule__GhostSpecifics__Group__1__Impl rule__GhostSpecifics__Group__2 )
            // InternalMazeDsl.g:3245:2: rule__GhostSpecifics__Group__1__Impl rule__GhostSpecifics__Group__2
            {
            pushFollow(FOLLOW_4);
            rule__GhostSpecifics__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__GhostSpecifics__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__Group__1"


    // $ANTLR start "rule__GhostSpecifics__Group__1__Impl"
    // InternalMazeDsl.g:3252:1: rule__GhostSpecifics__Group__1__Impl : ( 'ghost-stats' ) ;
    public final void rule__GhostSpecifics__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3256:1: ( ( 'ghost-stats' ) )
            // InternalMazeDsl.g:3257:1: ( 'ghost-stats' )
            {
            // InternalMazeDsl.g:3257:1: ( 'ghost-stats' )
            // InternalMazeDsl.g:3258:2: 'ghost-stats'
            {
             before(grammarAccess.getGhostSpecificsAccess().getGhostStatsKeyword_1()); 
            match(input,56,FOLLOW_2); 
             after(grammarAccess.getGhostSpecificsAccess().getGhostStatsKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__Group__1__Impl"


    // $ANTLR start "rule__GhostSpecifics__Group__2"
    // InternalMazeDsl.g:3267:1: rule__GhostSpecifics__Group__2 : rule__GhostSpecifics__Group__2__Impl rule__GhostSpecifics__Group__3 ;
    public final void rule__GhostSpecifics__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3271:1: ( rule__GhostSpecifics__Group__2__Impl rule__GhostSpecifics__Group__3 )
            // InternalMazeDsl.g:3272:2: rule__GhostSpecifics__Group__2__Impl rule__GhostSpecifics__Group__3
            {
            pushFollow(FOLLOW_28);
            rule__GhostSpecifics__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__GhostSpecifics__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__Group__2"


    // $ANTLR start "rule__GhostSpecifics__Group__2__Impl"
    // InternalMazeDsl.g:3279:1: rule__GhostSpecifics__Group__2__Impl : ( '{' ) ;
    public final void rule__GhostSpecifics__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3283:1: ( ( '{' ) )
            // InternalMazeDsl.g:3284:1: ( '{' )
            {
            // InternalMazeDsl.g:3284:1: ( '{' )
            // InternalMazeDsl.g:3285:2: '{'
            {
             before(grammarAccess.getGhostSpecificsAccess().getLeftCurlyBracketKeyword_2()); 
            match(input,32,FOLLOW_2); 
             after(grammarAccess.getGhostSpecificsAccess().getLeftCurlyBracketKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__Group__2__Impl"


    // $ANTLR start "rule__GhostSpecifics__Group__3"
    // InternalMazeDsl.g:3294:1: rule__GhostSpecifics__Group__3 : rule__GhostSpecifics__Group__3__Impl rule__GhostSpecifics__Group__4 ;
    public final void rule__GhostSpecifics__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3298:1: ( rule__GhostSpecifics__Group__3__Impl rule__GhostSpecifics__Group__4 )
            // InternalMazeDsl.g:3299:2: rule__GhostSpecifics__Group__3__Impl rule__GhostSpecifics__Group__4
            {
            pushFollow(FOLLOW_28);
            rule__GhostSpecifics__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__GhostSpecifics__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__Group__3"


    // $ANTLR start "rule__GhostSpecifics__Group__3__Impl"
    // InternalMazeDsl.g:3306:1: rule__GhostSpecifics__Group__3__Impl : ( ( rule__GhostSpecifics__Group_3__0 )? ) ;
    public final void rule__GhostSpecifics__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3310:1: ( ( ( rule__GhostSpecifics__Group_3__0 )? ) )
            // InternalMazeDsl.g:3311:1: ( ( rule__GhostSpecifics__Group_3__0 )? )
            {
            // InternalMazeDsl.g:3311:1: ( ( rule__GhostSpecifics__Group_3__0 )? )
            // InternalMazeDsl.g:3312:2: ( rule__GhostSpecifics__Group_3__0 )?
            {
             before(grammarAccess.getGhostSpecificsAccess().getGroup_3()); 
            // InternalMazeDsl.g:3313:2: ( rule__GhostSpecifics__Group_3__0 )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==53) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // InternalMazeDsl.g:3313:3: rule__GhostSpecifics__Group_3__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__GhostSpecifics__Group_3__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getGhostSpecificsAccess().getGroup_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__Group__3__Impl"


    // $ANTLR start "rule__GhostSpecifics__Group__4"
    // InternalMazeDsl.g:3321:1: rule__GhostSpecifics__Group__4 : rule__GhostSpecifics__Group__4__Impl rule__GhostSpecifics__Group__5 ;
    public final void rule__GhostSpecifics__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3325:1: ( rule__GhostSpecifics__Group__4__Impl rule__GhostSpecifics__Group__5 )
            // InternalMazeDsl.g:3326:2: rule__GhostSpecifics__Group__4__Impl rule__GhostSpecifics__Group__5
            {
            pushFollow(FOLLOW_28);
            rule__GhostSpecifics__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__GhostSpecifics__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__Group__4"


    // $ANTLR start "rule__GhostSpecifics__Group__4__Impl"
    // InternalMazeDsl.g:3333:1: rule__GhostSpecifics__Group__4__Impl : ( ( rule__GhostSpecifics__Group_4__0 )? ) ;
    public final void rule__GhostSpecifics__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3337:1: ( ( ( rule__GhostSpecifics__Group_4__0 )? ) )
            // InternalMazeDsl.g:3338:1: ( ( rule__GhostSpecifics__Group_4__0 )? )
            {
            // InternalMazeDsl.g:3338:1: ( ( rule__GhostSpecifics__Group_4__0 )? )
            // InternalMazeDsl.g:3339:2: ( rule__GhostSpecifics__Group_4__0 )?
            {
             before(grammarAccess.getGhostSpecificsAccess().getGroup_4()); 
            // InternalMazeDsl.g:3340:2: ( rule__GhostSpecifics__Group_4__0 )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==57) ) {
                alt34=1;
            }
            switch (alt34) {
                case 1 :
                    // InternalMazeDsl.g:3340:3: rule__GhostSpecifics__Group_4__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__GhostSpecifics__Group_4__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getGhostSpecificsAccess().getGroup_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__Group__4__Impl"


    // $ANTLR start "rule__GhostSpecifics__Group__5"
    // InternalMazeDsl.g:3348:1: rule__GhostSpecifics__Group__5 : rule__GhostSpecifics__Group__5__Impl rule__GhostSpecifics__Group__6 ;
    public final void rule__GhostSpecifics__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3352:1: ( rule__GhostSpecifics__Group__5__Impl rule__GhostSpecifics__Group__6 )
            // InternalMazeDsl.g:3353:2: rule__GhostSpecifics__Group__5__Impl rule__GhostSpecifics__Group__6
            {
            pushFollow(FOLLOW_28);
            rule__GhostSpecifics__Group__5__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__GhostSpecifics__Group__6();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__Group__5"


    // $ANTLR start "rule__GhostSpecifics__Group__5__Impl"
    // InternalMazeDsl.g:3360:1: rule__GhostSpecifics__Group__5__Impl : ( ( rule__GhostSpecifics__Group_5__0 )? ) ;
    public final void rule__GhostSpecifics__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3364:1: ( ( ( rule__GhostSpecifics__Group_5__0 )? ) )
            // InternalMazeDsl.g:3365:1: ( ( rule__GhostSpecifics__Group_5__0 )? )
            {
            // InternalMazeDsl.g:3365:1: ( ( rule__GhostSpecifics__Group_5__0 )? )
            // InternalMazeDsl.g:3366:2: ( rule__GhostSpecifics__Group_5__0 )?
            {
             before(grammarAccess.getGhostSpecificsAccess().getGroup_5()); 
            // InternalMazeDsl.g:3367:2: ( rule__GhostSpecifics__Group_5__0 )?
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==58) ) {
                alt35=1;
            }
            switch (alt35) {
                case 1 :
                    // InternalMazeDsl.g:3367:3: rule__GhostSpecifics__Group_5__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__GhostSpecifics__Group_5__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getGhostSpecificsAccess().getGroup_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__Group__5__Impl"


    // $ANTLR start "rule__GhostSpecifics__Group__6"
    // InternalMazeDsl.g:3375:1: rule__GhostSpecifics__Group__6 : rule__GhostSpecifics__Group__6__Impl ;
    public final void rule__GhostSpecifics__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3379:1: ( rule__GhostSpecifics__Group__6__Impl )
            // InternalMazeDsl.g:3380:2: rule__GhostSpecifics__Group__6__Impl
            {
            pushFollow(FOLLOW_2);
            rule__GhostSpecifics__Group__6__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__Group__6"


    // $ANTLR start "rule__GhostSpecifics__Group__6__Impl"
    // InternalMazeDsl.g:3386:1: rule__GhostSpecifics__Group__6__Impl : ( '}' ) ;
    public final void rule__GhostSpecifics__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3390:1: ( ( '}' ) )
            // InternalMazeDsl.g:3391:1: ( '}' )
            {
            // InternalMazeDsl.g:3391:1: ( '}' )
            // InternalMazeDsl.g:3392:2: '}'
            {
             before(grammarAccess.getGhostSpecificsAccess().getRightCurlyBracketKeyword_6()); 
            match(input,33,FOLLOW_2); 
             after(grammarAccess.getGhostSpecificsAccess().getRightCurlyBracketKeyword_6()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__Group__6__Impl"


    // $ANTLR start "rule__GhostSpecifics__Group_3__0"
    // InternalMazeDsl.g:3402:1: rule__GhostSpecifics__Group_3__0 : rule__GhostSpecifics__Group_3__0__Impl rule__GhostSpecifics__Group_3__1 ;
    public final void rule__GhostSpecifics__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3406:1: ( rule__GhostSpecifics__Group_3__0__Impl rule__GhostSpecifics__Group_3__1 )
            // InternalMazeDsl.g:3407:2: rule__GhostSpecifics__Group_3__0__Impl rule__GhostSpecifics__Group_3__1
            {
            pushFollow(FOLLOW_26);
            rule__GhostSpecifics__Group_3__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__GhostSpecifics__Group_3__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__Group_3__0"


    // $ANTLR start "rule__GhostSpecifics__Group_3__0__Impl"
    // InternalMazeDsl.g:3414:1: rule__GhostSpecifics__Group_3__0__Impl : ( 'attackDamage' ) ;
    public final void rule__GhostSpecifics__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3418:1: ( ( 'attackDamage' ) )
            // InternalMazeDsl.g:3419:1: ( 'attackDamage' )
            {
            // InternalMazeDsl.g:3419:1: ( 'attackDamage' )
            // InternalMazeDsl.g:3420:2: 'attackDamage'
            {
             before(grammarAccess.getGhostSpecificsAccess().getAttackDamageKeyword_3_0()); 
            match(input,53,FOLLOW_2); 
             after(grammarAccess.getGhostSpecificsAccess().getAttackDamageKeyword_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__Group_3__0__Impl"


    // $ANTLR start "rule__GhostSpecifics__Group_3__1"
    // InternalMazeDsl.g:3429:1: rule__GhostSpecifics__Group_3__1 : rule__GhostSpecifics__Group_3__1__Impl ;
    public final void rule__GhostSpecifics__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3433:1: ( rule__GhostSpecifics__Group_3__1__Impl )
            // InternalMazeDsl.g:3434:2: rule__GhostSpecifics__Group_3__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__GhostSpecifics__Group_3__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__Group_3__1"


    // $ANTLR start "rule__GhostSpecifics__Group_3__1__Impl"
    // InternalMazeDsl.g:3440:1: rule__GhostSpecifics__Group_3__1__Impl : ( ( rule__GhostSpecifics__AttackDamageAssignment_3_1 ) ) ;
    public final void rule__GhostSpecifics__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3444:1: ( ( ( rule__GhostSpecifics__AttackDamageAssignment_3_1 ) ) )
            // InternalMazeDsl.g:3445:1: ( ( rule__GhostSpecifics__AttackDamageAssignment_3_1 ) )
            {
            // InternalMazeDsl.g:3445:1: ( ( rule__GhostSpecifics__AttackDamageAssignment_3_1 ) )
            // InternalMazeDsl.g:3446:2: ( rule__GhostSpecifics__AttackDamageAssignment_3_1 )
            {
             before(grammarAccess.getGhostSpecificsAccess().getAttackDamageAssignment_3_1()); 
            // InternalMazeDsl.g:3447:2: ( rule__GhostSpecifics__AttackDamageAssignment_3_1 )
            // InternalMazeDsl.g:3447:3: rule__GhostSpecifics__AttackDamageAssignment_3_1
            {
            pushFollow(FOLLOW_2);
            rule__GhostSpecifics__AttackDamageAssignment_3_1();

            state._fsp--;


            }

             after(grammarAccess.getGhostSpecificsAccess().getAttackDamageAssignment_3_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__Group_3__1__Impl"


    // $ANTLR start "rule__GhostSpecifics__Group_4__0"
    // InternalMazeDsl.g:3456:1: rule__GhostSpecifics__Group_4__0 : rule__GhostSpecifics__Group_4__0__Impl rule__GhostSpecifics__Group_4__1 ;
    public final void rule__GhostSpecifics__Group_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3460:1: ( rule__GhostSpecifics__Group_4__0__Impl rule__GhostSpecifics__Group_4__1 )
            // InternalMazeDsl.g:3461:2: rule__GhostSpecifics__Group_4__0__Impl rule__GhostSpecifics__Group_4__1
            {
            pushFollow(FOLLOW_26);
            rule__GhostSpecifics__Group_4__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__GhostSpecifics__Group_4__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__Group_4__0"


    // $ANTLR start "rule__GhostSpecifics__Group_4__0__Impl"
    // InternalMazeDsl.g:3468:1: rule__GhostSpecifics__Group_4__0__Impl : ( 'visibilityLevel' ) ;
    public final void rule__GhostSpecifics__Group_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3472:1: ( ( 'visibilityLevel' ) )
            // InternalMazeDsl.g:3473:1: ( 'visibilityLevel' )
            {
            // InternalMazeDsl.g:3473:1: ( 'visibilityLevel' )
            // InternalMazeDsl.g:3474:2: 'visibilityLevel'
            {
             before(grammarAccess.getGhostSpecificsAccess().getVisibilityLevelKeyword_4_0()); 
            match(input,57,FOLLOW_2); 
             after(grammarAccess.getGhostSpecificsAccess().getVisibilityLevelKeyword_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__Group_4__0__Impl"


    // $ANTLR start "rule__GhostSpecifics__Group_4__1"
    // InternalMazeDsl.g:3483:1: rule__GhostSpecifics__Group_4__1 : rule__GhostSpecifics__Group_4__1__Impl ;
    public final void rule__GhostSpecifics__Group_4__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3487:1: ( rule__GhostSpecifics__Group_4__1__Impl )
            // InternalMazeDsl.g:3488:2: rule__GhostSpecifics__Group_4__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__GhostSpecifics__Group_4__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__Group_4__1"


    // $ANTLR start "rule__GhostSpecifics__Group_4__1__Impl"
    // InternalMazeDsl.g:3494:1: rule__GhostSpecifics__Group_4__1__Impl : ( ( rule__GhostSpecifics__VisibilityLevelAssignment_4_1 ) ) ;
    public final void rule__GhostSpecifics__Group_4__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3498:1: ( ( ( rule__GhostSpecifics__VisibilityLevelAssignment_4_1 ) ) )
            // InternalMazeDsl.g:3499:1: ( ( rule__GhostSpecifics__VisibilityLevelAssignment_4_1 ) )
            {
            // InternalMazeDsl.g:3499:1: ( ( rule__GhostSpecifics__VisibilityLevelAssignment_4_1 ) )
            // InternalMazeDsl.g:3500:2: ( rule__GhostSpecifics__VisibilityLevelAssignment_4_1 )
            {
             before(grammarAccess.getGhostSpecificsAccess().getVisibilityLevelAssignment_4_1()); 
            // InternalMazeDsl.g:3501:2: ( rule__GhostSpecifics__VisibilityLevelAssignment_4_1 )
            // InternalMazeDsl.g:3501:3: rule__GhostSpecifics__VisibilityLevelAssignment_4_1
            {
            pushFollow(FOLLOW_2);
            rule__GhostSpecifics__VisibilityLevelAssignment_4_1();

            state._fsp--;


            }

             after(grammarAccess.getGhostSpecificsAccess().getVisibilityLevelAssignment_4_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__Group_4__1__Impl"


    // $ANTLR start "rule__GhostSpecifics__Group_5__0"
    // InternalMazeDsl.g:3510:1: rule__GhostSpecifics__Group_5__0 : rule__GhostSpecifics__Group_5__0__Impl rule__GhostSpecifics__Group_5__1 ;
    public final void rule__GhostSpecifics__Group_5__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3514:1: ( rule__GhostSpecifics__Group_5__0__Impl rule__GhostSpecifics__Group_5__1 )
            // InternalMazeDsl.g:3515:2: rule__GhostSpecifics__Group_5__0__Impl rule__GhostSpecifics__Group_5__1
            {
            pushFollow(FOLLOW_17);
            rule__GhostSpecifics__Group_5__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__GhostSpecifics__Group_5__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__Group_5__0"


    // $ANTLR start "rule__GhostSpecifics__Group_5__0__Impl"
    // InternalMazeDsl.g:3522:1: rule__GhostSpecifics__Group_5__0__Impl : ( 'nonTangibilityEnergy' ) ;
    public final void rule__GhostSpecifics__Group_5__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3526:1: ( ( 'nonTangibilityEnergy' ) )
            // InternalMazeDsl.g:3527:1: ( 'nonTangibilityEnergy' )
            {
            // InternalMazeDsl.g:3527:1: ( 'nonTangibilityEnergy' )
            // InternalMazeDsl.g:3528:2: 'nonTangibilityEnergy'
            {
             before(grammarAccess.getGhostSpecificsAccess().getNonTangibilityEnergyKeyword_5_0()); 
            match(input,58,FOLLOW_2); 
             after(grammarAccess.getGhostSpecificsAccess().getNonTangibilityEnergyKeyword_5_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__Group_5__0__Impl"


    // $ANTLR start "rule__GhostSpecifics__Group_5__1"
    // InternalMazeDsl.g:3537:1: rule__GhostSpecifics__Group_5__1 : rule__GhostSpecifics__Group_5__1__Impl ;
    public final void rule__GhostSpecifics__Group_5__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3541:1: ( rule__GhostSpecifics__Group_5__1__Impl )
            // InternalMazeDsl.g:3542:2: rule__GhostSpecifics__Group_5__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__GhostSpecifics__Group_5__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__Group_5__1"


    // $ANTLR start "rule__GhostSpecifics__Group_5__1__Impl"
    // InternalMazeDsl.g:3548:1: rule__GhostSpecifics__Group_5__1__Impl : ( ( rule__GhostSpecifics__NonTangibilityEnergyAssignment_5_1 ) ) ;
    public final void rule__GhostSpecifics__Group_5__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3552:1: ( ( ( rule__GhostSpecifics__NonTangibilityEnergyAssignment_5_1 ) ) )
            // InternalMazeDsl.g:3553:1: ( ( rule__GhostSpecifics__NonTangibilityEnergyAssignment_5_1 ) )
            {
            // InternalMazeDsl.g:3553:1: ( ( rule__GhostSpecifics__NonTangibilityEnergyAssignment_5_1 ) )
            // InternalMazeDsl.g:3554:2: ( rule__GhostSpecifics__NonTangibilityEnergyAssignment_5_1 )
            {
             before(grammarAccess.getGhostSpecificsAccess().getNonTangibilityEnergyAssignment_5_1()); 
            // InternalMazeDsl.g:3555:2: ( rule__GhostSpecifics__NonTangibilityEnergyAssignment_5_1 )
            // InternalMazeDsl.g:3555:3: rule__GhostSpecifics__NonTangibilityEnergyAssignment_5_1
            {
            pushFollow(FOLLOW_2);
            rule__GhostSpecifics__NonTangibilityEnergyAssignment_5_1();

            state._fsp--;


            }

             after(grammarAccess.getGhostSpecificsAccess().getNonTangibilityEnergyAssignment_5_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__Group_5__1__Impl"


    // $ANTLR start "rule__RangedSpecifics__Group__0"
    // InternalMazeDsl.g:3564:1: rule__RangedSpecifics__Group__0 : rule__RangedSpecifics__Group__0__Impl rule__RangedSpecifics__Group__1 ;
    public final void rule__RangedSpecifics__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3568:1: ( rule__RangedSpecifics__Group__0__Impl rule__RangedSpecifics__Group__1 )
            // InternalMazeDsl.g:3569:2: rule__RangedSpecifics__Group__0__Impl rule__RangedSpecifics__Group__1
            {
            pushFollow(FOLLOW_29);
            rule__RangedSpecifics__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group__0"


    // $ANTLR start "rule__RangedSpecifics__Group__0__Impl"
    // InternalMazeDsl.g:3576:1: rule__RangedSpecifics__Group__0__Impl : ( () ) ;
    public final void rule__RangedSpecifics__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3580:1: ( ( () ) )
            // InternalMazeDsl.g:3581:1: ( () )
            {
            // InternalMazeDsl.g:3581:1: ( () )
            // InternalMazeDsl.g:3582:2: ()
            {
             before(grammarAccess.getRangedSpecificsAccess().getRangedSpecificsAction_0()); 
            // InternalMazeDsl.g:3583:2: ()
            // InternalMazeDsl.g:3583:3: 
            {
            }

             after(grammarAccess.getRangedSpecificsAccess().getRangedSpecificsAction_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group__0__Impl"


    // $ANTLR start "rule__RangedSpecifics__Group__1"
    // InternalMazeDsl.g:3591:1: rule__RangedSpecifics__Group__1 : rule__RangedSpecifics__Group__1__Impl rule__RangedSpecifics__Group__2 ;
    public final void rule__RangedSpecifics__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3595:1: ( rule__RangedSpecifics__Group__1__Impl rule__RangedSpecifics__Group__2 )
            // InternalMazeDsl.g:3596:2: rule__RangedSpecifics__Group__1__Impl rule__RangedSpecifics__Group__2
            {
            pushFollow(FOLLOW_4);
            rule__RangedSpecifics__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group__1"


    // $ANTLR start "rule__RangedSpecifics__Group__1__Impl"
    // InternalMazeDsl.g:3603:1: rule__RangedSpecifics__Group__1__Impl : ( 'ranged-stats' ) ;
    public final void rule__RangedSpecifics__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3607:1: ( ( 'ranged-stats' ) )
            // InternalMazeDsl.g:3608:1: ( 'ranged-stats' )
            {
            // InternalMazeDsl.g:3608:1: ( 'ranged-stats' )
            // InternalMazeDsl.g:3609:2: 'ranged-stats'
            {
             before(grammarAccess.getRangedSpecificsAccess().getRangedStatsKeyword_1()); 
            match(input,59,FOLLOW_2); 
             after(grammarAccess.getRangedSpecificsAccess().getRangedStatsKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group__1__Impl"


    // $ANTLR start "rule__RangedSpecifics__Group__2"
    // InternalMazeDsl.g:3618:1: rule__RangedSpecifics__Group__2 : rule__RangedSpecifics__Group__2__Impl rule__RangedSpecifics__Group__3 ;
    public final void rule__RangedSpecifics__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3622:1: ( rule__RangedSpecifics__Group__2__Impl rule__RangedSpecifics__Group__3 )
            // InternalMazeDsl.g:3623:2: rule__RangedSpecifics__Group__2__Impl rule__RangedSpecifics__Group__3
            {
            pushFollow(FOLLOW_30);
            rule__RangedSpecifics__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group__2"


    // $ANTLR start "rule__RangedSpecifics__Group__2__Impl"
    // InternalMazeDsl.g:3630:1: rule__RangedSpecifics__Group__2__Impl : ( '{' ) ;
    public final void rule__RangedSpecifics__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3634:1: ( ( '{' ) )
            // InternalMazeDsl.g:3635:1: ( '{' )
            {
            // InternalMazeDsl.g:3635:1: ( '{' )
            // InternalMazeDsl.g:3636:2: '{'
            {
             before(grammarAccess.getRangedSpecificsAccess().getLeftCurlyBracketKeyword_2()); 
            match(input,32,FOLLOW_2); 
             after(grammarAccess.getRangedSpecificsAccess().getLeftCurlyBracketKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group__2__Impl"


    // $ANTLR start "rule__RangedSpecifics__Group__3"
    // InternalMazeDsl.g:3645:1: rule__RangedSpecifics__Group__3 : rule__RangedSpecifics__Group__3__Impl rule__RangedSpecifics__Group__4 ;
    public final void rule__RangedSpecifics__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3649:1: ( rule__RangedSpecifics__Group__3__Impl rule__RangedSpecifics__Group__4 )
            // InternalMazeDsl.g:3650:2: rule__RangedSpecifics__Group__3__Impl rule__RangedSpecifics__Group__4
            {
            pushFollow(FOLLOW_30);
            rule__RangedSpecifics__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group__3"


    // $ANTLR start "rule__RangedSpecifics__Group__3__Impl"
    // InternalMazeDsl.g:3657:1: rule__RangedSpecifics__Group__3__Impl : ( ( rule__RangedSpecifics__Group_3__0 )? ) ;
    public final void rule__RangedSpecifics__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3661:1: ( ( ( rule__RangedSpecifics__Group_3__0 )? ) )
            // InternalMazeDsl.g:3662:1: ( ( rule__RangedSpecifics__Group_3__0 )? )
            {
            // InternalMazeDsl.g:3662:1: ( ( rule__RangedSpecifics__Group_3__0 )? )
            // InternalMazeDsl.g:3663:2: ( rule__RangedSpecifics__Group_3__0 )?
            {
             before(grammarAccess.getRangedSpecificsAccess().getGroup_3()); 
            // InternalMazeDsl.g:3664:2: ( rule__RangedSpecifics__Group_3__0 )?
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==60) ) {
                alt36=1;
            }
            switch (alt36) {
                case 1 :
                    // InternalMazeDsl.g:3664:3: rule__RangedSpecifics__Group_3__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__RangedSpecifics__Group_3__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getRangedSpecificsAccess().getGroup_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group__3__Impl"


    // $ANTLR start "rule__RangedSpecifics__Group__4"
    // InternalMazeDsl.g:3672:1: rule__RangedSpecifics__Group__4 : rule__RangedSpecifics__Group__4__Impl rule__RangedSpecifics__Group__5 ;
    public final void rule__RangedSpecifics__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3676:1: ( rule__RangedSpecifics__Group__4__Impl rule__RangedSpecifics__Group__5 )
            // InternalMazeDsl.g:3677:2: rule__RangedSpecifics__Group__4__Impl rule__RangedSpecifics__Group__5
            {
            pushFollow(FOLLOW_30);
            rule__RangedSpecifics__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group__4"


    // $ANTLR start "rule__RangedSpecifics__Group__4__Impl"
    // InternalMazeDsl.g:3684:1: rule__RangedSpecifics__Group__4__Impl : ( ( rule__RangedSpecifics__Group_4__0 )? ) ;
    public final void rule__RangedSpecifics__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3688:1: ( ( ( rule__RangedSpecifics__Group_4__0 )? ) )
            // InternalMazeDsl.g:3689:1: ( ( rule__RangedSpecifics__Group_4__0 )? )
            {
            // InternalMazeDsl.g:3689:1: ( ( rule__RangedSpecifics__Group_4__0 )? )
            // InternalMazeDsl.g:3690:2: ( rule__RangedSpecifics__Group_4__0 )?
            {
             before(grammarAccess.getRangedSpecificsAccess().getGroup_4()); 
            // InternalMazeDsl.g:3691:2: ( rule__RangedSpecifics__Group_4__0 )?
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( (LA37_0==61) ) {
                alt37=1;
            }
            switch (alt37) {
                case 1 :
                    // InternalMazeDsl.g:3691:3: rule__RangedSpecifics__Group_4__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__RangedSpecifics__Group_4__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getRangedSpecificsAccess().getGroup_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group__4__Impl"


    // $ANTLR start "rule__RangedSpecifics__Group__5"
    // InternalMazeDsl.g:3699:1: rule__RangedSpecifics__Group__5 : rule__RangedSpecifics__Group__5__Impl rule__RangedSpecifics__Group__6 ;
    public final void rule__RangedSpecifics__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3703:1: ( rule__RangedSpecifics__Group__5__Impl rule__RangedSpecifics__Group__6 )
            // InternalMazeDsl.g:3704:2: rule__RangedSpecifics__Group__5__Impl rule__RangedSpecifics__Group__6
            {
            pushFollow(FOLLOW_30);
            rule__RangedSpecifics__Group__5__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__Group__6();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group__5"


    // $ANTLR start "rule__RangedSpecifics__Group__5__Impl"
    // InternalMazeDsl.g:3711:1: rule__RangedSpecifics__Group__5__Impl : ( ( rule__RangedSpecifics__Group_5__0 )? ) ;
    public final void rule__RangedSpecifics__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3715:1: ( ( ( rule__RangedSpecifics__Group_5__0 )? ) )
            // InternalMazeDsl.g:3716:1: ( ( rule__RangedSpecifics__Group_5__0 )? )
            {
            // InternalMazeDsl.g:3716:1: ( ( rule__RangedSpecifics__Group_5__0 )? )
            // InternalMazeDsl.g:3717:2: ( rule__RangedSpecifics__Group_5__0 )?
            {
             before(grammarAccess.getRangedSpecificsAccess().getGroup_5()); 
            // InternalMazeDsl.g:3718:2: ( rule__RangedSpecifics__Group_5__0 )?
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==53) ) {
                alt38=1;
            }
            switch (alt38) {
                case 1 :
                    // InternalMazeDsl.g:3718:3: rule__RangedSpecifics__Group_5__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__RangedSpecifics__Group_5__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getRangedSpecificsAccess().getGroup_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group__5__Impl"


    // $ANTLR start "rule__RangedSpecifics__Group__6"
    // InternalMazeDsl.g:3726:1: rule__RangedSpecifics__Group__6 : rule__RangedSpecifics__Group__6__Impl rule__RangedSpecifics__Group__7 ;
    public final void rule__RangedSpecifics__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3730:1: ( rule__RangedSpecifics__Group__6__Impl rule__RangedSpecifics__Group__7 )
            // InternalMazeDsl.g:3731:2: rule__RangedSpecifics__Group__6__Impl rule__RangedSpecifics__Group__7
            {
            pushFollow(FOLLOW_30);
            rule__RangedSpecifics__Group__6__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__Group__7();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group__6"


    // $ANTLR start "rule__RangedSpecifics__Group__6__Impl"
    // InternalMazeDsl.g:3738:1: rule__RangedSpecifics__Group__6__Impl : ( ( rule__RangedSpecifics__Group_6__0 )? ) ;
    public final void rule__RangedSpecifics__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3742:1: ( ( ( rule__RangedSpecifics__Group_6__0 )? ) )
            // InternalMazeDsl.g:3743:1: ( ( rule__RangedSpecifics__Group_6__0 )? )
            {
            // InternalMazeDsl.g:3743:1: ( ( rule__RangedSpecifics__Group_6__0 )? )
            // InternalMazeDsl.g:3744:2: ( rule__RangedSpecifics__Group_6__0 )?
            {
             before(grammarAccess.getRangedSpecificsAccess().getGroup_6()); 
            // InternalMazeDsl.g:3745:2: ( rule__RangedSpecifics__Group_6__0 )?
            int alt39=2;
            int LA39_0 = input.LA(1);

            if ( (LA39_0==62) ) {
                alt39=1;
            }
            switch (alt39) {
                case 1 :
                    // InternalMazeDsl.g:3745:3: rule__RangedSpecifics__Group_6__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__RangedSpecifics__Group_6__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getRangedSpecificsAccess().getGroup_6()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group__6__Impl"


    // $ANTLR start "rule__RangedSpecifics__Group__7"
    // InternalMazeDsl.g:3753:1: rule__RangedSpecifics__Group__7 : rule__RangedSpecifics__Group__7__Impl rule__RangedSpecifics__Group__8 ;
    public final void rule__RangedSpecifics__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3757:1: ( rule__RangedSpecifics__Group__7__Impl rule__RangedSpecifics__Group__8 )
            // InternalMazeDsl.g:3758:2: rule__RangedSpecifics__Group__7__Impl rule__RangedSpecifics__Group__8
            {
            pushFollow(FOLLOW_30);
            rule__RangedSpecifics__Group__7__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__Group__8();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group__7"


    // $ANTLR start "rule__RangedSpecifics__Group__7__Impl"
    // InternalMazeDsl.g:3765:1: rule__RangedSpecifics__Group__7__Impl : ( ( rule__RangedSpecifics__Group_7__0 )? ) ;
    public final void rule__RangedSpecifics__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3769:1: ( ( ( rule__RangedSpecifics__Group_7__0 )? ) )
            // InternalMazeDsl.g:3770:1: ( ( rule__RangedSpecifics__Group_7__0 )? )
            {
            // InternalMazeDsl.g:3770:1: ( ( rule__RangedSpecifics__Group_7__0 )? )
            // InternalMazeDsl.g:3771:2: ( rule__RangedSpecifics__Group_7__0 )?
            {
             before(grammarAccess.getRangedSpecificsAccess().getGroup_7()); 
            // InternalMazeDsl.g:3772:2: ( rule__RangedSpecifics__Group_7__0 )?
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( (LA40_0==63) ) {
                alt40=1;
            }
            switch (alt40) {
                case 1 :
                    // InternalMazeDsl.g:3772:3: rule__RangedSpecifics__Group_7__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__RangedSpecifics__Group_7__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getRangedSpecificsAccess().getGroup_7()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group__7__Impl"


    // $ANTLR start "rule__RangedSpecifics__Group__8"
    // InternalMazeDsl.g:3780:1: rule__RangedSpecifics__Group__8 : rule__RangedSpecifics__Group__8__Impl rule__RangedSpecifics__Group__9 ;
    public final void rule__RangedSpecifics__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3784:1: ( rule__RangedSpecifics__Group__8__Impl rule__RangedSpecifics__Group__9 )
            // InternalMazeDsl.g:3785:2: rule__RangedSpecifics__Group__8__Impl rule__RangedSpecifics__Group__9
            {
            pushFollow(FOLLOW_30);
            rule__RangedSpecifics__Group__8__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__Group__9();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group__8"


    // $ANTLR start "rule__RangedSpecifics__Group__8__Impl"
    // InternalMazeDsl.g:3792:1: rule__RangedSpecifics__Group__8__Impl : ( ( rule__RangedSpecifics__Group_8__0 )? ) ;
    public final void rule__RangedSpecifics__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3796:1: ( ( ( rule__RangedSpecifics__Group_8__0 )? ) )
            // InternalMazeDsl.g:3797:1: ( ( rule__RangedSpecifics__Group_8__0 )? )
            {
            // InternalMazeDsl.g:3797:1: ( ( rule__RangedSpecifics__Group_8__0 )? )
            // InternalMazeDsl.g:3798:2: ( rule__RangedSpecifics__Group_8__0 )?
            {
             before(grammarAccess.getRangedSpecificsAccess().getGroup_8()); 
            // InternalMazeDsl.g:3799:2: ( rule__RangedSpecifics__Group_8__0 )?
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( (LA41_0==64) ) {
                alt41=1;
            }
            switch (alt41) {
                case 1 :
                    // InternalMazeDsl.g:3799:3: rule__RangedSpecifics__Group_8__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__RangedSpecifics__Group_8__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getRangedSpecificsAccess().getGroup_8()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group__8__Impl"


    // $ANTLR start "rule__RangedSpecifics__Group__9"
    // InternalMazeDsl.g:3807:1: rule__RangedSpecifics__Group__9 : rule__RangedSpecifics__Group__9__Impl ;
    public final void rule__RangedSpecifics__Group__9() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3811:1: ( rule__RangedSpecifics__Group__9__Impl )
            // InternalMazeDsl.g:3812:2: rule__RangedSpecifics__Group__9__Impl
            {
            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__Group__9__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group__9"


    // $ANTLR start "rule__RangedSpecifics__Group__9__Impl"
    // InternalMazeDsl.g:3818:1: rule__RangedSpecifics__Group__9__Impl : ( '}' ) ;
    public final void rule__RangedSpecifics__Group__9__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3822:1: ( ( '}' ) )
            // InternalMazeDsl.g:3823:1: ( '}' )
            {
            // InternalMazeDsl.g:3823:1: ( '}' )
            // InternalMazeDsl.g:3824:2: '}'
            {
             before(grammarAccess.getRangedSpecificsAccess().getRightCurlyBracketKeyword_9()); 
            match(input,33,FOLLOW_2); 
             after(grammarAccess.getRangedSpecificsAccess().getRightCurlyBracketKeyword_9()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group__9__Impl"


    // $ANTLR start "rule__RangedSpecifics__Group_3__0"
    // InternalMazeDsl.g:3834:1: rule__RangedSpecifics__Group_3__0 : rule__RangedSpecifics__Group_3__0__Impl rule__RangedSpecifics__Group_3__1 ;
    public final void rule__RangedSpecifics__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3838:1: ( rule__RangedSpecifics__Group_3__0__Impl rule__RangedSpecifics__Group_3__1 )
            // InternalMazeDsl.g:3839:2: rule__RangedSpecifics__Group_3__0__Impl rule__RangedSpecifics__Group_3__1
            {
            pushFollow(FOLLOW_17);
            rule__RangedSpecifics__Group_3__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__Group_3__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group_3__0"


    // $ANTLR start "rule__RangedSpecifics__Group_3__0__Impl"
    // InternalMazeDsl.g:3846:1: rule__RangedSpecifics__Group_3__0__Impl : ( 'attackRange' ) ;
    public final void rule__RangedSpecifics__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3850:1: ( ( 'attackRange' ) )
            // InternalMazeDsl.g:3851:1: ( 'attackRange' )
            {
            // InternalMazeDsl.g:3851:1: ( 'attackRange' )
            // InternalMazeDsl.g:3852:2: 'attackRange'
            {
             before(grammarAccess.getRangedSpecificsAccess().getAttackRangeKeyword_3_0()); 
            match(input,60,FOLLOW_2); 
             after(grammarAccess.getRangedSpecificsAccess().getAttackRangeKeyword_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group_3__0__Impl"


    // $ANTLR start "rule__RangedSpecifics__Group_3__1"
    // InternalMazeDsl.g:3861:1: rule__RangedSpecifics__Group_3__1 : rule__RangedSpecifics__Group_3__1__Impl ;
    public final void rule__RangedSpecifics__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3865:1: ( rule__RangedSpecifics__Group_3__1__Impl )
            // InternalMazeDsl.g:3866:2: rule__RangedSpecifics__Group_3__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__Group_3__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group_3__1"


    // $ANTLR start "rule__RangedSpecifics__Group_3__1__Impl"
    // InternalMazeDsl.g:3872:1: rule__RangedSpecifics__Group_3__1__Impl : ( ( rule__RangedSpecifics__AttackRangeAssignment_3_1 ) ) ;
    public final void rule__RangedSpecifics__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3876:1: ( ( ( rule__RangedSpecifics__AttackRangeAssignment_3_1 ) ) )
            // InternalMazeDsl.g:3877:1: ( ( rule__RangedSpecifics__AttackRangeAssignment_3_1 ) )
            {
            // InternalMazeDsl.g:3877:1: ( ( rule__RangedSpecifics__AttackRangeAssignment_3_1 ) )
            // InternalMazeDsl.g:3878:2: ( rule__RangedSpecifics__AttackRangeAssignment_3_1 )
            {
             before(grammarAccess.getRangedSpecificsAccess().getAttackRangeAssignment_3_1()); 
            // InternalMazeDsl.g:3879:2: ( rule__RangedSpecifics__AttackRangeAssignment_3_1 )
            // InternalMazeDsl.g:3879:3: rule__RangedSpecifics__AttackRangeAssignment_3_1
            {
            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__AttackRangeAssignment_3_1();

            state._fsp--;


            }

             after(grammarAccess.getRangedSpecificsAccess().getAttackRangeAssignment_3_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group_3__1__Impl"


    // $ANTLR start "rule__RangedSpecifics__Group_4__0"
    // InternalMazeDsl.g:3888:1: rule__RangedSpecifics__Group_4__0 : rule__RangedSpecifics__Group_4__0__Impl rule__RangedSpecifics__Group_4__1 ;
    public final void rule__RangedSpecifics__Group_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3892:1: ( rule__RangedSpecifics__Group_4__0__Impl rule__RangedSpecifics__Group_4__1 )
            // InternalMazeDsl.g:3893:2: rule__RangedSpecifics__Group_4__0__Impl rule__RangedSpecifics__Group_4__1
            {
            pushFollow(FOLLOW_26);
            rule__RangedSpecifics__Group_4__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__Group_4__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group_4__0"


    // $ANTLR start "rule__RangedSpecifics__Group_4__0__Impl"
    // InternalMazeDsl.g:3900:1: rule__RangedSpecifics__Group_4__0__Impl : ( 'attackCooldown' ) ;
    public final void rule__RangedSpecifics__Group_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3904:1: ( ( 'attackCooldown' ) )
            // InternalMazeDsl.g:3905:1: ( 'attackCooldown' )
            {
            // InternalMazeDsl.g:3905:1: ( 'attackCooldown' )
            // InternalMazeDsl.g:3906:2: 'attackCooldown'
            {
             before(grammarAccess.getRangedSpecificsAccess().getAttackCooldownKeyword_4_0()); 
            match(input,61,FOLLOW_2); 
             after(grammarAccess.getRangedSpecificsAccess().getAttackCooldownKeyword_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group_4__0__Impl"


    // $ANTLR start "rule__RangedSpecifics__Group_4__1"
    // InternalMazeDsl.g:3915:1: rule__RangedSpecifics__Group_4__1 : rule__RangedSpecifics__Group_4__1__Impl ;
    public final void rule__RangedSpecifics__Group_4__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3919:1: ( rule__RangedSpecifics__Group_4__1__Impl )
            // InternalMazeDsl.g:3920:2: rule__RangedSpecifics__Group_4__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__Group_4__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group_4__1"


    // $ANTLR start "rule__RangedSpecifics__Group_4__1__Impl"
    // InternalMazeDsl.g:3926:1: rule__RangedSpecifics__Group_4__1__Impl : ( ( rule__RangedSpecifics__AttackCooldownAssignment_4_1 ) ) ;
    public final void rule__RangedSpecifics__Group_4__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3930:1: ( ( ( rule__RangedSpecifics__AttackCooldownAssignment_4_1 ) ) )
            // InternalMazeDsl.g:3931:1: ( ( rule__RangedSpecifics__AttackCooldownAssignment_4_1 ) )
            {
            // InternalMazeDsl.g:3931:1: ( ( rule__RangedSpecifics__AttackCooldownAssignment_4_1 ) )
            // InternalMazeDsl.g:3932:2: ( rule__RangedSpecifics__AttackCooldownAssignment_4_1 )
            {
             before(grammarAccess.getRangedSpecificsAccess().getAttackCooldownAssignment_4_1()); 
            // InternalMazeDsl.g:3933:2: ( rule__RangedSpecifics__AttackCooldownAssignment_4_1 )
            // InternalMazeDsl.g:3933:3: rule__RangedSpecifics__AttackCooldownAssignment_4_1
            {
            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__AttackCooldownAssignment_4_1();

            state._fsp--;


            }

             after(grammarAccess.getRangedSpecificsAccess().getAttackCooldownAssignment_4_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group_4__1__Impl"


    // $ANTLR start "rule__RangedSpecifics__Group_5__0"
    // InternalMazeDsl.g:3942:1: rule__RangedSpecifics__Group_5__0 : rule__RangedSpecifics__Group_5__0__Impl rule__RangedSpecifics__Group_5__1 ;
    public final void rule__RangedSpecifics__Group_5__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3946:1: ( rule__RangedSpecifics__Group_5__0__Impl rule__RangedSpecifics__Group_5__1 )
            // InternalMazeDsl.g:3947:2: rule__RangedSpecifics__Group_5__0__Impl rule__RangedSpecifics__Group_5__1
            {
            pushFollow(FOLLOW_26);
            rule__RangedSpecifics__Group_5__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__Group_5__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group_5__0"


    // $ANTLR start "rule__RangedSpecifics__Group_5__0__Impl"
    // InternalMazeDsl.g:3954:1: rule__RangedSpecifics__Group_5__0__Impl : ( 'attackDamage' ) ;
    public final void rule__RangedSpecifics__Group_5__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3958:1: ( ( 'attackDamage' ) )
            // InternalMazeDsl.g:3959:1: ( 'attackDamage' )
            {
            // InternalMazeDsl.g:3959:1: ( 'attackDamage' )
            // InternalMazeDsl.g:3960:2: 'attackDamage'
            {
             before(grammarAccess.getRangedSpecificsAccess().getAttackDamageKeyword_5_0()); 
            match(input,53,FOLLOW_2); 
             after(grammarAccess.getRangedSpecificsAccess().getAttackDamageKeyword_5_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group_5__0__Impl"


    // $ANTLR start "rule__RangedSpecifics__Group_5__1"
    // InternalMazeDsl.g:3969:1: rule__RangedSpecifics__Group_5__1 : rule__RangedSpecifics__Group_5__1__Impl ;
    public final void rule__RangedSpecifics__Group_5__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3973:1: ( rule__RangedSpecifics__Group_5__1__Impl )
            // InternalMazeDsl.g:3974:2: rule__RangedSpecifics__Group_5__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__Group_5__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group_5__1"


    // $ANTLR start "rule__RangedSpecifics__Group_5__1__Impl"
    // InternalMazeDsl.g:3980:1: rule__RangedSpecifics__Group_5__1__Impl : ( ( rule__RangedSpecifics__AttackDamageAssignment_5_1 ) ) ;
    public final void rule__RangedSpecifics__Group_5__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3984:1: ( ( ( rule__RangedSpecifics__AttackDamageAssignment_5_1 ) ) )
            // InternalMazeDsl.g:3985:1: ( ( rule__RangedSpecifics__AttackDamageAssignment_5_1 ) )
            {
            // InternalMazeDsl.g:3985:1: ( ( rule__RangedSpecifics__AttackDamageAssignment_5_1 ) )
            // InternalMazeDsl.g:3986:2: ( rule__RangedSpecifics__AttackDamageAssignment_5_1 )
            {
             before(grammarAccess.getRangedSpecificsAccess().getAttackDamageAssignment_5_1()); 
            // InternalMazeDsl.g:3987:2: ( rule__RangedSpecifics__AttackDamageAssignment_5_1 )
            // InternalMazeDsl.g:3987:3: rule__RangedSpecifics__AttackDamageAssignment_5_1
            {
            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__AttackDamageAssignment_5_1();

            state._fsp--;


            }

             after(grammarAccess.getRangedSpecificsAccess().getAttackDamageAssignment_5_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group_5__1__Impl"


    // $ANTLR start "rule__RangedSpecifics__Group_6__0"
    // InternalMazeDsl.g:3996:1: rule__RangedSpecifics__Group_6__0 : rule__RangedSpecifics__Group_6__0__Impl rule__RangedSpecifics__Group_6__1 ;
    public final void rule__RangedSpecifics__Group_6__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4000:1: ( rule__RangedSpecifics__Group_6__0__Impl rule__RangedSpecifics__Group_6__1 )
            // InternalMazeDsl.g:4001:2: rule__RangedSpecifics__Group_6__0__Impl rule__RangedSpecifics__Group_6__1
            {
            pushFollow(FOLLOW_17);
            rule__RangedSpecifics__Group_6__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__Group_6__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group_6__0"


    // $ANTLR start "rule__RangedSpecifics__Group_6__0__Impl"
    // InternalMazeDsl.g:4008:1: rule__RangedSpecifics__Group_6__0__Impl : ( 'projectileSpeed' ) ;
    public final void rule__RangedSpecifics__Group_6__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4012:1: ( ( 'projectileSpeed' ) )
            // InternalMazeDsl.g:4013:1: ( 'projectileSpeed' )
            {
            // InternalMazeDsl.g:4013:1: ( 'projectileSpeed' )
            // InternalMazeDsl.g:4014:2: 'projectileSpeed'
            {
             before(grammarAccess.getRangedSpecificsAccess().getProjectileSpeedKeyword_6_0()); 
            match(input,62,FOLLOW_2); 
             after(grammarAccess.getRangedSpecificsAccess().getProjectileSpeedKeyword_6_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group_6__0__Impl"


    // $ANTLR start "rule__RangedSpecifics__Group_6__1"
    // InternalMazeDsl.g:4023:1: rule__RangedSpecifics__Group_6__1 : rule__RangedSpecifics__Group_6__1__Impl ;
    public final void rule__RangedSpecifics__Group_6__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4027:1: ( rule__RangedSpecifics__Group_6__1__Impl )
            // InternalMazeDsl.g:4028:2: rule__RangedSpecifics__Group_6__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__Group_6__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group_6__1"


    // $ANTLR start "rule__RangedSpecifics__Group_6__1__Impl"
    // InternalMazeDsl.g:4034:1: rule__RangedSpecifics__Group_6__1__Impl : ( ( rule__RangedSpecifics__ProjectileSpeedAssignment_6_1 ) ) ;
    public final void rule__RangedSpecifics__Group_6__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4038:1: ( ( ( rule__RangedSpecifics__ProjectileSpeedAssignment_6_1 ) ) )
            // InternalMazeDsl.g:4039:1: ( ( rule__RangedSpecifics__ProjectileSpeedAssignment_6_1 ) )
            {
            // InternalMazeDsl.g:4039:1: ( ( rule__RangedSpecifics__ProjectileSpeedAssignment_6_1 ) )
            // InternalMazeDsl.g:4040:2: ( rule__RangedSpecifics__ProjectileSpeedAssignment_6_1 )
            {
             before(grammarAccess.getRangedSpecificsAccess().getProjectileSpeedAssignment_6_1()); 
            // InternalMazeDsl.g:4041:2: ( rule__RangedSpecifics__ProjectileSpeedAssignment_6_1 )
            // InternalMazeDsl.g:4041:3: rule__RangedSpecifics__ProjectileSpeedAssignment_6_1
            {
            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__ProjectileSpeedAssignment_6_1();

            state._fsp--;


            }

             after(grammarAccess.getRangedSpecificsAccess().getProjectileSpeedAssignment_6_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group_6__1__Impl"


    // $ANTLR start "rule__RangedSpecifics__Group_7__0"
    // InternalMazeDsl.g:4050:1: rule__RangedSpecifics__Group_7__0 : rule__RangedSpecifics__Group_7__0__Impl rule__RangedSpecifics__Group_7__1 ;
    public final void rule__RangedSpecifics__Group_7__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4054:1: ( rule__RangedSpecifics__Group_7__0__Impl rule__RangedSpecifics__Group_7__1 )
            // InternalMazeDsl.g:4055:2: rule__RangedSpecifics__Group_7__0__Impl rule__RangedSpecifics__Group_7__1
            {
            pushFollow(FOLLOW_31);
            rule__RangedSpecifics__Group_7__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__Group_7__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group_7__0"


    // $ANTLR start "rule__RangedSpecifics__Group_7__0__Impl"
    // InternalMazeDsl.g:4062:1: rule__RangedSpecifics__Group_7__0__Impl : ( 'projectileType' ) ;
    public final void rule__RangedSpecifics__Group_7__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4066:1: ( ( 'projectileType' ) )
            // InternalMazeDsl.g:4067:1: ( 'projectileType' )
            {
            // InternalMazeDsl.g:4067:1: ( 'projectileType' )
            // InternalMazeDsl.g:4068:2: 'projectileType'
            {
             before(grammarAccess.getRangedSpecificsAccess().getProjectileTypeKeyword_7_0()); 
            match(input,63,FOLLOW_2); 
             after(grammarAccess.getRangedSpecificsAccess().getProjectileTypeKeyword_7_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group_7__0__Impl"


    // $ANTLR start "rule__RangedSpecifics__Group_7__1"
    // InternalMazeDsl.g:4077:1: rule__RangedSpecifics__Group_7__1 : rule__RangedSpecifics__Group_7__1__Impl ;
    public final void rule__RangedSpecifics__Group_7__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4081:1: ( rule__RangedSpecifics__Group_7__1__Impl )
            // InternalMazeDsl.g:4082:2: rule__RangedSpecifics__Group_7__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__Group_7__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group_7__1"


    // $ANTLR start "rule__RangedSpecifics__Group_7__1__Impl"
    // InternalMazeDsl.g:4088:1: rule__RangedSpecifics__Group_7__1__Impl : ( ( rule__RangedSpecifics__ProjectileTypeAssignment_7_1 ) ) ;
    public final void rule__RangedSpecifics__Group_7__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4092:1: ( ( ( rule__RangedSpecifics__ProjectileTypeAssignment_7_1 ) ) )
            // InternalMazeDsl.g:4093:1: ( ( rule__RangedSpecifics__ProjectileTypeAssignment_7_1 ) )
            {
            // InternalMazeDsl.g:4093:1: ( ( rule__RangedSpecifics__ProjectileTypeAssignment_7_1 ) )
            // InternalMazeDsl.g:4094:2: ( rule__RangedSpecifics__ProjectileTypeAssignment_7_1 )
            {
             before(grammarAccess.getRangedSpecificsAccess().getProjectileTypeAssignment_7_1()); 
            // InternalMazeDsl.g:4095:2: ( rule__RangedSpecifics__ProjectileTypeAssignment_7_1 )
            // InternalMazeDsl.g:4095:3: rule__RangedSpecifics__ProjectileTypeAssignment_7_1
            {
            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__ProjectileTypeAssignment_7_1();

            state._fsp--;


            }

             after(grammarAccess.getRangedSpecificsAccess().getProjectileTypeAssignment_7_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group_7__1__Impl"


    // $ANTLR start "rule__RangedSpecifics__Group_8__0"
    // InternalMazeDsl.g:4104:1: rule__RangedSpecifics__Group_8__0 : rule__RangedSpecifics__Group_8__0__Impl rule__RangedSpecifics__Group_8__1 ;
    public final void rule__RangedSpecifics__Group_8__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4108:1: ( rule__RangedSpecifics__Group_8__0__Impl rule__RangedSpecifics__Group_8__1 )
            // InternalMazeDsl.g:4109:2: rule__RangedSpecifics__Group_8__0__Impl rule__RangedSpecifics__Group_8__1
            {
            pushFollow(FOLLOW_17);
            rule__RangedSpecifics__Group_8__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__Group_8__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group_8__0"


    // $ANTLR start "rule__RangedSpecifics__Group_8__0__Impl"
    // InternalMazeDsl.g:4116:1: rule__RangedSpecifics__Group_8__0__Impl : ( 'splashRadius' ) ;
    public final void rule__RangedSpecifics__Group_8__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4120:1: ( ( 'splashRadius' ) )
            // InternalMazeDsl.g:4121:1: ( 'splashRadius' )
            {
            // InternalMazeDsl.g:4121:1: ( 'splashRadius' )
            // InternalMazeDsl.g:4122:2: 'splashRadius'
            {
             before(grammarAccess.getRangedSpecificsAccess().getSplashRadiusKeyword_8_0()); 
            match(input,64,FOLLOW_2); 
             after(grammarAccess.getRangedSpecificsAccess().getSplashRadiusKeyword_8_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group_8__0__Impl"


    // $ANTLR start "rule__RangedSpecifics__Group_8__1"
    // InternalMazeDsl.g:4131:1: rule__RangedSpecifics__Group_8__1 : rule__RangedSpecifics__Group_8__1__Impl ;
    public final void rule__RangedSpecifics__Group_8__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4135:1: ( rule__RangedSpecifics__Group_8__1__Impl )
            // InternalMazeDsl.g:4136:2: rule__RangedSpecifics__Group_8__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__Group_8__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group_8__1"


    // $ANTLR start "rule__RangedSpecifics__Group_8__1__Impl"
    // InternalMazeDsl.g:4142:1: rule__RangedSpecifics__Group_8__1__Impl : ( ( rule__RangedSpecifics__SplashRadiusAssignment_8_1 ) ) ;
    public final void rule__RangedSpecifics__Group_8__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4146:1: ( ( ( rule__RangedSpecifics__SplashRadiusAssignment_8_1 ) ) )
            // InternalMazeDsl.g:4147:1: ( ( rule__RangedSpecifics__SplashRadiusAssignment_8_1 ) )
            {
            // InternalMazeDsl.g:4147:1: ( ( rule__RangedSpecifics__SplashRadiusAssignment_8_1 ) )
            // InternalMazeDsl.g:4148:2: ( rule__RangedSpecifics__SplashRadiusAssignment_8_1 )
            {
             before(grammarAccess.getRangedSpecificsAccess().getSplashRadiusAssignment_8_1()); 
            // InternalMazeDsl.g:4149:2: ( rule__RangedSpecifics__SplashRadiusAssignment_8_1 )
            // InternalMazeDsl.g:4149:3: rule__RangedSpecifics__SplashRadiusAssignment_8_1
            {
            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__SplashRadiusAssignment_8_1();

            state._fsp--;


            }

             after(grammarAccess.getRangedSpecificsAccess().getSplashRadiusAssignment_8_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group_8__1__Impl"


    // $ANTLR start "rule__PatrolConfig__Group__0"
    // InternalMazeDsl.g:4158:1: rule__PatrolConfig__Group__0 : rule__PatrolConfig__Group__0__Impl rule__PatrolConfig__Group__1 ;
    public final void rule__PatrolConfig__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4162:1: ( rule__PatrolConfig__Group__0__Impl rule__PatrolConfig__Group__1 )
            // InternalMazeDsl.g:4163:2: rule__PatrolConfig__Group__0__Impl rule__PatrolConfig__Group__1
            {
            pushFollow(FOLLOW_3);
            rule__PatrolConfig__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PatrolConfig__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group__0"


    // $ANTLR start "rule__PatrolConfig__Group__0__Impl"
    // InternalMazeDsl.g:4170:1: rule__PatrolConfig__Group__0__Impl : ( 'patrol' ) ;
    public final void rule__PatrolConfig__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4174:1: ( ( 'patrol' ) )
            // InternalMazeDsl.g:4175:1: ( 'patrol' )
            {
            // InternalMazeDsl.g:4175:1: ( 'patrol' )
            // InternalMazeDsl.g:4176:2: 'patrol'
            {
             before(grammarAccess.getPatrolConfigAccess().getPatrolKeyword_0()); 
            match(input,23,FOLLOW_2); 
             after(grammarAccess.getPatrolConfigAccess().getPatrolKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group__0__Impl"


    // $ANTLR start "rule__PatrolConfig__Group__1"
    // InternalMazeDsl.g:4185:1: rule__PatrolConfig__Group__1 : rule__PatrolConfig__Group__1__Impl rule__PatrolConfig__Group__2 ;
    public final void rule__PatrolConfig__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4189:1: ( rule__PatrolConfig__Group__1__Impl rule__PatrolConfig__Group__2 )
            // InternalMazeDsl.g:4190:2: rule__PatrolConfig__Group__1__Impl rule__PatrolConfig__Group__2
            {
            pushFollow(FOLLOW_4);
            rule__PatrolConfig__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PatrolConfig__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group__1"


    // $ANTLR start "rule__PatrolConfig__Group__1__Impl"
    // InternalMazeDsl.g:4197:1: rule__PatrolConfig__Group__1__Impl : ( ( rule__PatrolConfig__NameAssignment_1 ) ) ;
    public final void rule__PatrolConfig__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4201:1: ( ( ( rule__PatrolConfig__NameAssignment_1 ) ) )
            // InternalMazeDsl.g:4202:1: ( ( rule__PatrolConfig__NameAssignment_1 ) )
            {
            // InternalMazeDsl.g:4202:1: ( ( rule__PatrolConfig__NameAssignment_1 ) )
            // InternalMazeDsl.g:4203:2: ( rule__PatrolConfig__NameAssignment_1 )
            {
             before(grammarAccess.getPatrolConfigAccess().getNameAssignment_1()); 
            // InternalMazeDsl.g:4204:2: ( rule__PatrolConfig__NameAssignment_1 )
            // InternalMazeDsl.g:4204:3: rule__PatrolConfig__NameAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__PatrolConfig__NameAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getPatrolConfigAccess().getNameAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group__1__Impl"


    // $ANTLR start "rule__PatrolConfig__Group__2"
    // InternalMazeDsl.g:4212:1: rule__PatrolConfig__Group__2 : rule__PatrolConfig__Group__2__Impl rule__PatrolConfig__Group__3 ;
    public final void rule__PatrolConfig__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4216:1: ( rule__PatrolConfig__Group__2__Impl rule__PatrolConfig__Group__3 )
            // InternalMazeDsl.g:4217:2: rule__PatrolConfig__Group__2__Impl rule__PatrolConfig__Group__3
            {
            pushFollow(FOLLOW_32);
            rule__PatrolConfig__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PatrolConfig__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group__2"


    // $ANTLR start "rule__PatrolConfig__Group__2__Impl"
    // InternalMazeDsl.g:4224:1: rule__PatrolConfig__Group__2__Impl : ( '{' ) ;
    public final void rule__PatrolConfig__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4228:1: ( ( '{' ) )
            // InternalMazeDsl.g:4229:1: ( '{' )
            {
            // InternalMazeDsl.g:4229:1: ( '{' )
            // InternalMazeDsl.g:4230:2: '{'
            {
             before(grammarAccess.getPatrolConfigAccess().getLeftCurlyBracketKeyword_2()); 
            match(input,32,FOLLOW_2); 
             after(grammarAccess.getPatrolConfigAccess().getLeftCurlyBracketKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group__2__Impl"


    // $ANTLR start "rule__PatrolConfig__Group__3"
    // InternalMazeDsl.g:4239:1: rule__PatrolConfig__Group__3 : rule__PatrolConfig__Group__3__Impl rule__PatrolConfig__Group__4 ;
    public final void rule__PatrolConfig__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4243:1: ( rule__PatrolConfig__Group__3__Impl rule__PatrolConfig__Group__4 )
            // InternalMazeDsl.g:4244:2: rule__PatrolConfig__Group__3__Impl rule__PatrolConfig__Group__4
            {
            pushFollow(FOLLOW_32);
            rule__PatrolConfig__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PatrolConfig__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group__3"


    // $ANTLR start "rule__PatrolConfig__Group__3__Impl"
    // InternalMazeDsl.g:4251:1: rule__PatrolConfig__Group__3__Impl : ( ( rule__PatrolConfig__Group_3__0 )? ) ;
    public final void rule__PatrolConfig__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4255:1: ( ( ( rule__PatrolConfig__Group_3__0 )? ) )
            // InternalMazeDsl.g:4256:1: ( ( rule__PatrolConfig__Group_3__0 )? )
            {
            // InternalMazeDsl.g:4256:1: ( ( rule__PatrolConfig__Group_3__0 )? )
            // InternalMazeDsl.g:4257:2: ( rule__PatrolConfig__Group_3__0 )?
            {
             before(grammarAccess.getPatrolConfigAccess().getGroup_3()); 
            // InternalMazeDsl.g:4258:2: ( rule__PatrolConfig__Group_3__0 )?
            int alt42=2;
            int LA42_0 = input.LA(1);

            if ( (LA42_0==68) ) {
                alt42=1;
            }
            switch (alt42) {
                case 1 :
                    // InternalMazeDsl.g:4258:3: rule__PatrolConfig__Group_3__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__PatrolConfig__Group_3__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getPatrolConfigAccess().getGroup_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group__3__Impl"


    // $ANTLR start "rule__PatrolConfig__Group__4"
    // InternalMazeDsl.g:4266:1: rule__PatrolConfig__Group__4 : rule__PatrolConfig__Group__4__Impl rule__PatrolConfig__Group__5 ;
    public final void rule__PatrolConfig__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4270:1: ( rule__PatrolConfig__Group__4__Impl rule__PatrolConfig__Group__5 )
            // InternalMazeDsl.g:4271:2: rule__PatrolConfig__Group__4__Impl rule__PatrolConfig__Group__5
            {
            pushFollow(FOLLOW_32);
            rule__PatrolConfig__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PatrolConfig__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group__4"


    // $ANTLR start "rule__PatrolConfig__Group__4__Impl"
    // InternalMazeDsl.g:4278:1: rule__PatrolConfig__Group__4__Impl : ( ( rule__PatrolConfig__ZoneAssignment_4 )? ) ;
    public final void rule__PatrolConfig__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4282:1: ( ( ( rule__PatrolConfig__ZoneAssignment_4 )? ) )
            // InternalMazeDsl.g:4283:1: ( ( rule__PatrolConfig__ZoneAssignment_4 )? )
            {
            // InternalMazeDsl.g:4283:1: ( ( rule__PatrolConfig__ZoneAssignment_4 )? )
            // InternalMazeDsl.g:4284:2: ( rule__PatrolConfig__ZoneAssignment_4 )?
            {
             before(grammarAccess.getPatrolConfigAccess().getZoneAssignment_4()); 
            // InternalMazeDsl.g:4285:2: ( rule__PatrolConfig__ZoneAssignment_4 )?
            int alt43=2;
            int LA43_0 = input.LA(1);

            if ( (LA43_0==70) ) {
                alt43=1;
            }
            switch (alt43) {
                case 1 :
                    // InternalMazeDsl.g:4285:3: rule__PatrolConfig__ZoneAssignment_4
                    {
                    pushFollow(FOLLOW_2);
                    rule__PatrolConfig__ZoneAssignment_4();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getPatrolConfigAccess().getZoneAssignment_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group__4__Impl"


    // $ANTLR start "rule__PatrolConfig__Group__5"
    // InternalMazeDsl.g:4293:1: rule__PatrolConfig__Group__5 : rule__PatrolConfig__Group__5__Impl rule__PatrolConfig__Group__6 ;
    public final void rule__PatrolConfig__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4297:1: ( rule__PatrolConfig__Group__5__Impl rule__PatrolConfig__Group__6 )
            // InternalMazeDsl.g:4298:2: rule__PatrolConfig__Group__5__Impl rule__PatrolConfig__Group__6
            {
            pushFollow(FOLLOW_33);
            rule__PatrolConfig__Group__5__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PatrolConfig__Group__6();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group__5"


    // $ANTLR start "rule__PatrolConfig__Group__5__Impl"
    // InternalMazeDsl.g:4305:1: rule__PatrolConfig__Group__5__Impl : ( 'path' ) ;
    public final void rule__PatrolConfig__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4309:1: ( ( 'path' ) )
            // InternalMazeDsl.g:4310:1: ( 'path' )
            {
            // InternalMazeDsl.g:4310:1: ( 'path' )
            // InternalMazeDsl.g:4311:2: 'path'
            {
             before(grammarAccess.getPatrolConfigAccess().getPathKeyword_5()); 
            match(input,65,FOLLOW_2); 
             after(grammarAccess.getPatrolConfigAccess().getPathKeyword_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group__5__Impl"


    // $ANTLR start "rule__PatrolConfig__Group__6"
    // InternalMazeDsl.g:4320:1: rule__PatrolConfig__Group__6 : rule__PatrolConfig__Group__6__Impl rule__PatrolConfig__Group__7 ;
    public final void rule__PatrolConfig__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4324:1: ( rule__PatrolConfig__Group__6__Impl rule__PatrolConfig__Group__7 )
            // InternalMazeDsl.g:4325:2: rule__PatrolConfig__Group__6__Impl rule__PatrolConfig__Group__7
            {
            pushFollow(FOLLOW_34);
            rule__PatrolConfig__Group__6__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PatrolConfig__Group__7();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group__6"


    // $ANTLR start "rule__PatrolConfig__Group__6__Impl"
    // InternalMazeDsl.g:4332:1: rule__PatrolConfig__Group__6__Impl : ( '[' ) ;
    public final void rule__PatrolConfig__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4336:1: ( ( '[' ) )
            // InternalMazeDsl.g:4337:1: ( '[' )
            {
            // InternalMazeDsl.g:4337:1: ( '[' )
            // InternalMazeDsl.g:4338:2: '['
            {
             before(grammarAccess.getPatrolConfigAccess().getLeftSquareBracketKeyword_6()); 
            match(input,66,FOLLOW_2); 
             after(grammarAccess.getPatrolConfigAccess().getLeftSquareBracketKeyword_6()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group__6__Impl"


    // $ANTLR start "rule__PatrolConfig__Group__7"
    // InternalMazeDsl.g:4347:1: rule__PatrolConfig__Group__7 : rule__PatrolConfig__Group__7__Impl rule__PatrolConfig__Group__8 ;
    public final void rule__PatrolConfig__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4351:1: ( rule__PatrolConfig__Group__7__Impl rule__PatrolConfig__Group__8 )
            // InternalMazeDsl.g:4352:2: rule__PatrolConfig__Group__7__Impl rule__PatrolConfig__Group__8
            {
            pushFollow(FOLLOW_35);
            rule__PatrolConfig__Group__7__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PatrolConfig__Group__8();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group__7"


    // $ANTLR start "rule__PatrolConfig__Group__7__Impl"
    // InternalMazeDsl.g:4359:1: rule__PatrolConfig__Group__7__Impl : ( ( rule__PatrolConfig__WaypointsAssignment_7 ) ) ;
    public final void rule__PatrolConfig__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4363:1: ( ( ( rule__PatrolConfig__WaypointsAssignment_7 ) ) )
            // InternalMazeDsl.g:4364:1: ( ( rule__PatrolConfig__WaypointsAssignment_7 ) )
            {
            // InternalMazeDsl.g:4364:1: ( ( rule__PatrolConfig__WaypointsAssignment_7 ) )
            // InternalMazeDsl.g:4365:2: ( rule__PatrolConfig__WaypointsAssignment_7 )
            {
             before(grammarAccess.getPatrolConfigAccess().getWaypointsAssignment_7()); 
            // InternalMazeDsl.g:4366:2: ( rule__PatrolConfig__WaypointsAssignment_7 )
            // InternalMazeDsl.g:4366:3: rule__PatrolConfig__WaypointsAssignment_7
            {
            pushFollow(FOLLOW_2);
            rule__PatrolConfig__WaypointsAssignment_7();

            state._fsp--;


            }

             after(grammarAccess.getPatrolConfigAccess().getWaypointsAssignment_7()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group__7__Impl"


    // $ANTLR start "rule__PatrolConfig__Group__8"
    // InternalMazeDsl.g:4374:1: rule__PatrolConfig__Group__8 : rule__PatrolConfig__Group__8__Impl rule__PatrolConfig__Group__9 ;
    public final void rule__PatrolConfig__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4378:1: ( rule__PatrolConfig__Group__8__Impl rule__PatrolConfig__Group__9 )
            // InternalMazeDsl.g:4379:2: rule__PatrolConfig__Group__8__Impl rule__PatrolConfig__Group__9
            {
            pushFollow(FOLLOW_35);
            rule__PatrolConfig__Group__8__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PatrolConfig__Group__9();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group__8"


    // $ANTLR start "rule__PatrolConfig__Group__8__Impl"
    // InternalMazeDsl.g:4386:1: rule__PatrolConfig__Group__8__Impl : ( ( rule__PatrolConfig__Group_8__0 )* ) ;
    public final void rule__PatrolConfig__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4390:1: ( ( ( rule__PatrolConfig__Group_8__0 )* ) )
            // InternalMazeDsl.g:4391:1: ( ( rule__PatrolConfig__Group_8__0 )* )
            {
            // InternalMazeDsl.g:4391:1: ( ( rule__PatrolConfig__Group_8__0 )* )
            // InternalMazeDsl.g:4392:2: ( rule__PatrolConfig__Group_8__0 )*
            {
             before(grammarAccess.getPatrolConfigAccess().getGroup_8()); 
            // InternalMazeDsl.g:4393:2: ( rule__PatrolConfig__Group_8__0 )*
            loop44:
            do {
                int alt44=2;
                int LA44_0 = input.LA(1);

                if ( (LA44_0==69) ) {
                    alt44=1;
                }


                switch (alt44) {
            	case 1 :
            	    // InternalMazeDsl.g:4393:3: rule__PatrolConfig__Group_8__0
            	    {
            	    pushFollow(FOLLOW_36);
            	    rule__PatrolConfig__Group_8__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop44;
                }
            } while (true);

             after(grammarAccess.getPatrolConfigAccess().getGroup_8()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group__8__Impl"


    // $ANTLR start "rule__PatrolConfig__Group__9"
    // InternalMazeDsl.g:4401:1: rule__PatrolConfig__Group__9 : rule__PatrolConfig__Group__9__Impl rule__PatrolConfig__Group__10 ;
    public final void rule__PatrolConfig__Group__9() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4405:1: ( rule__PatrolConfig__Group__9__Impl rule__PatrolConfig__Group__10 )
            // InternalMazeDsl.g:4406:2: rule__PatrolConfig__Group__9__Impl rule__PatrolConfig__Group__10
            {
            pushFollow(FOLLOW_37);
            rule__PatrolConfig__Group__9__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PatrolConfig__Group__10();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group__9"


    // $ANTLR start "rule__PatrolConfig__Group__9__Impl"
    // InternalMazeDsl.g:4413:1: rule__PatrolConfig__Group__9__Impl : ( ']' ) ;
    public final void rule__PatrolConfig__Group__9__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4417:1: ( ( ']' ) )
            // InternalMazeDsl.g:4418:1: ( ']' )
            {
            // InternalMazeDsl.g:4418:1: ( ']' )
            // InternalMazeDsl.g:4419:2: ']'
            {
             before(grammarAccess.getPatrolConfigAccess().getRightSquareBracketKeyword_9()); 
            match(input,67,FOLLOW_2); 
             after(grammarAccess.getPatrolConfigAccess().getRightSquareBracketKeyword_9()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group__9__Impl"


    // $ANTLR start "rule__PatrolConfig__Group__10"
    // InternalMazeDsl.g:4428:1: rule__PatrolConfig__Group__10 : rule__PatrolConfig__Group__10__Impl ;
    public final void rule__PatrolConfig__Group__10() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4432:1: ( rule__PatrolConfig__Group__10__Impl )
            // InternalMazeDsl.g:4433:2: rule__PatrolConfig__Group__10__Impl
            {
            pushFollow(FOLLOW_2);
            rule__PatrolConfig__Group__10__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group__10"


    // $ANTLR start "rule__PatrolConfig__Group__10__Impl"
    // InternalMazeDsl.g:4439:1: rule__PatrolConfig__Group__10__Impl : ( '}' ) ;
    public final void rule__PatrolConfig__Group__10__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4443:1: ( ( '}' ) )
            // InternalMazeDsl.g:4444:1: ( '}' )
            {
            // InternalMazeDsl.g:4444:1: ( '}' )
            // InternalMazeDsl.g:4445:2: '}'
            {
             before(grammarAccess.getPatrolConfigAccess().getRightCurlyBracketKeyword_10()); 
            match(input,33,FOLLOW_2); 
             after(grammarAccess.getPatrolConfigAccess().getRightCurlyBracketKeyword_10()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group__10__Impl"


    // $ANTLR start "rule__PatrolConfig__Group_3__0"
    // InternalMazeDsl.g:4455:1: rule__PatrolConfig__Group_3__0 : rule__PatrolConfig__Group_3__0__Impl rule__PatrolConfig__Group_3__1 ;
    public final void rule__PatrolConfig__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4459:1: ( rule__PatrolConfig__Group_3__0__Impl rule__PatrolConfig__Group_3__1 )
            // InternalMazeDsl.g:4460:2: rule__PatrolConfig__Group_3__0__Impl rule__PatrolConfig__Group_3__1
            {
            pushFollow(FOLLOW_17);
            rule__PatrolConfig__Group_3__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PatrolConfig__Group_3__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group_3__0"


    // $ANTLR start "rule__PatrolConfig__Group_3__0__Impl"
    // InternalMazeDsl.g:4467:1: rule__PatrolConfig__Group_3__0__Impl : ( 'visionRange' ) ;
    public final void rule__PatrolConfig__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4471:1: ( ( 'visionRange' ) )
            // InternalMazeDsl.g:4472:1: ( 'visionRange' )
            {
            // InternalMazeDsl.g:4472:1: ( 'visionRange' )
            // InternalMazeDsl.g:4473:2: 'visionRange'
            {
             before(grammarAccess.getPatrolConfigAccess().getVisionRangeKeyword_3_0()); 
            match(input,68,FOLLOW_2); 
             after(grammarAccess.getPatrolConfigAccess().getVisionRangeKeyword_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group_3__0__Impl"


    // $ANTLR start "rule__PatrolConfig__Group_3__1"
    // InternalMazeDsl.g:4482:1: rule__PatrolConfig__Group_3__1 : rule__PatrolConfig__Group_3__1__Impl ;
    public final void rule__PatrolConfig__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4486:1: ( rule__PatrolConfig__Group_3__1__Impl )
            // InternalMazeDsl.g:4487:2: rule__PatrolConfig__Group_3__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__PatrolConfig__Group_3__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group_3__1"


    // $ANTLR start "rule__PatrolConfig__Group_3__1__Impl"
    // InternalMazeDsl.g:4493:1: rule__PatrolConfig__Group_3__1__Impl : ( ( rule__PatrolConfig__VisionRangeAssignment_3_1 ) ) ;
    public final void rule__PatrolConfig__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4497:1: ( ( ( rule__PatrolConfig__VisionRangeAssignment_3_1 ) ) )
            // InternalMazeDsl.g:4498:1: ( ( rule__PatrolConfig__VisionRangeAssignment_3_1 ) )
            {
            // InternalMazeDsl.g:4498:1: ( ( rule__PatrolConfig__VisionRangeAssignment_3_1 ) )
            // InternalMazeDsl.g:4499:2: ( rule__PatrolConfig__VisionRangeAssignment_3_1 )
            {
             before(grammarAccess.getPatrolConfigAccess().getVisionRangeAssignment_3_1()); 
            // InternalMazeDsl.g:4500:2: ( rule__PatrolConfig__VisionRangeAssignment_3_1 )
            // InternalMazeDsl.g:4500:3: rule__PatrolConfig__VisionRangeAssignment_3_1
            {
            pushFollow(FOLLOW_2);
            rule__PatrolConfig__VisionRangeAssignment_3_1();

            state._fsp--;


            }

             after(grammarAccess.getPatrolConfigAccess().getVisionRangeAssignment_3_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group_3__1__Impl"


    // $ANTLR start "rule__PatrolConfig__Group_8__0"
    // InternalMazeDsl.g:4509:1: rule__PatrolConfig__Group_8__0 : rule__PatrolConfig__Group_8__0__Impl rule__PatrolConfig__Group_8__1 ;
    public final void rule__PatrolConfig__Group_8__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4513:1: ( rule__PatrolConfig__Group_8__0__Impl rule__PatrolConfig__Group_8__1 )
            // InternalMazeDsl.g:4514:2: rule__PatrolConfig__Group_8__0__Impl rule__PatrolConfig__Group_8__1
            {
            pushFollow(FOLLOW_34);
            rule__PatrolConfig__Group_8__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PatrolConfig__Group_8__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group_8__0"


    // $ANTLR start "rule__PatrolConfig__Group_8__0__Impl"
    // InternalMazeDsl.g:4521:1: rule__PatrolConfig__Group_8__0__Impl : ( ',' ) ;
    public final void rule__PatrolConfig__Group_8__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4525:1: ( ( ',' ) )
            // InternalMazeDsl.g:4526:1: ( ',' )
            {
            // InternalMazeDsl.g:4526:1: ( ',' )
            // InternalMazeDsl.g:4527:2: ','
            {
             before(grammarAccess.getPatrolConfigAccess().getCommaKeyword_8_0()); 
            match(input,69,FOLLOW_2); 
             after(grammarAccess.getPatrolConfigAccess().getCommaKeyword_8_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group_8__0__Impl"


    // $ANTLR start "rule__PatrolConfig__Group_8__1"
    // InternalMazeDsl.g:4536:1: rule__PatrolConfig__Group_8__1 : rule__PatrolConfig__Group_8__1__Impl ;
    public final void rule__PatrolConfig__Group_8__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4540:1: ( rule__PatrolConfig__Group_8__1__Impl )
            // InternalMazeDsl.g:4541:2: rule__PatrolConfig__Group_8__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__PatrolConfig__Group_8__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group_8__1"


    // $ANTLR start "rule__PatrolConfig__Group_8__1__Impl"
    // InternalMazeDsl.g:4547:1: rule__PatrolConfig__Group_8__1__Impl : ( ( rule__PatrolConfig__WaypointsAssignment_8_1 ) ) ;
    public final void rule__PatrolConfig__Group_8__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4551:1: ( ( ( rule__PatrolConfig__WaypointsAssignment_8_1 ) ) )
            // InternalMazeDsl.g:4552:1: ( ( rule__PatrolConfig__WaypointsAssignment_8_1 ) )
            {
            // InternalMazeDsl.g:4552:1: ( ( rule__PatrolConfig__WaypointsAssignment_8_1 ) )
            // InternalMazeDsl.g:4553:2: ( rule__PatrolConfig__WaypointsAssignment_8_1 )
            {
             before(grammarAccess.getPatrolConfigAccess().getWaypointsAssignment_8_1()); 
            // InternalMazeDsl.g:4554:2: ( rule__PatrolConfig__WaypointsAssignment_8_1 )
            // InternalMazeDsl.g:4554:3: rule__PatrolConfig__WaypointsAssignment_8_1
            {
            pushFollow(FOLLOW_2);
            rule__PatrolConfig__WaypointsAssignment_8_1();

            state._fsp--;


            }

             after(grammarAccess.getPatrolConfigAccess().getWaypointsAssignment_8_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group_8__1__Impl"


    // $ANTLR start "rule__PatrolZoneConfig__Group__0"
    // InternalMazeDsl.g:4563:1: rule__PatrolZoneConfig__Group__0 : rule__PatrolZoneConfig__Group__0__Impl rule__PatrolZoneConfig__Group__1 ;
    public final void rule__PatrolZoneConfig__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4567:1: ( rule__PatrolZoneConfig__Group__0__Impl rule__PatrolZoneConfig__Group__1 )
            // InternalMazeDsl.g:4568:2: rule__PatrolZoneConfig__Group__0__Impl rule__PatrolZoneConfig__Group__1
            {
            pushFollow(FOLLOW_4);
            rule__PatrolZoneConfig__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PatrolZoneConfig__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__Group__0"


    // $ANTLR start "rule__PatrolZoneConfig__Group__0__Impl"
    // InternalMazeDsl.g:4575:1: rule__PatrolZoneConfig__Group__0__Impl : ( 'zone' ) ;
    public final void rule__PatrolZoneConfig__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4579:1: ( ( 'zone' ) )
            // InternalMazeDsl.g:4580:1: ( 'zone' )
            {
            // InternalMazeDsl.g:4580:1: ( 'zone' )
            // InternalMazeDsl.g:4581:2: 'zone'
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getZoneKeyword_0()); 
            match(input,70,FOLLOW_2); 
             after(grammarAccess.getPatrolZoneConfigAccess().getZoneKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__Group__0__Impl"


    // $ANTLR start "rule__PatrolZoneConfig__Group__1"
    // InternalMazeDsl.g:4590:1: rule__PatrolZoneConfig__Group__1 : rule__PatrolZoneConfig__Group__1__Impl rule__PatrolZoneConfig__Group__2 ;
    public final void rule__PatrolZoneConfig__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4594:1: ( rule__PatrolZoneConfig__Group__1__Impl rule__PatrolZoneConfig__Group__2 )
            // InternalMazeDsl.g:4595:2: rule__PatrolZoneConfig__Group__1__Impl rule__PatrolZoneConfig__Group__2
            {
            pushFollow(FOLLOW_38);
            rule__PatrolZoneConfig__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PatrolZoneConfig__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__Group__1"


    // $ANTLR start "rule__PatrolZoneConfig__Group__1__Impl"
    // InternalMazeDsl.g:4602:1: rule__PatrolZoneConfig__Group__1__Impl : ( '{' ) ;
    public final void rule__PatrolZoneConfig__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4606:1: ( ( '{' ) )
            // InternalMazeDsl.g:4607:1: ( '{' )
            {
            // InternalMazeDsl.g:4607:1: ( '{' )
            // InternalMazeDsl.g:4608:2: '{'
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getLeftCurlyBracketKeyword_1()); 
            match(input,32,FOLLOW_2); 
             after(grammarAccess.getPatrolZoneConfigAccess().getLeftCurlyBracketKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__Group__1__Impl"


    // $ANTLR start "rule__PatrolZoneConfig__Group__2"
    // InternalMazeDsl.g:4617:1: rule__PatrolZoneConfig__Group__2 : rule__PatrolZoneConfig__Group__2__Impl rule__PatrolZoneConfig__Group__3 ;
    public final void rule__PatrolZoneConfig__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4621:1: ( rule__PatrolZoneConfig__Group__2__Impl rule__PatrolZoneConfig__Group__3 )
            // InternalMazeDsl.g:4622:2: rule__PatrolZoneConfig__Group__2__Impl rule__PatrolZoneConfig__Group__3
            {
            pushFollow(FOLLOW_34);
            rule__PatrolZoneConfig__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PatrolZoneConfig__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__Group__2"


    // $ANTLR start "rule__PatrolZoneConfig__Group__2__Impl"
    // InternalMazeDsl.g:4629:1: rule__PatrolZoneConfig__Group__2__Impl : ( 'topLeft' ) ;
    public final void rule__PatrolZoneConfig__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4633:1: ( ( 'topLeft' ) )
            // InternalMazeDsl.g:4634:1: ( 'topLeft' )
            {
            // InternalMazeDsl.g:4634:1: ( 'topLeft' )
            // InternalMazeDsl.g:4635:2: 'topLeft'
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getTopLeftKeyword_2()); 
            match(input,71,FOLLOW_2); 
             after(grammarAccess.getPatrolZoneConfigAccess().getTopLeftKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__Group__2__Impl"


    // $ANTLR start "rule__PatrolZoneConfig__Group__3"
    // InternalMazeDsl.g:4644:1: rule__PatrolZoneConfig__Group__3 : rule__PatrolZoneConfig__Group__3__Impl rule__PatrolZoneConfig__Group__4 ;
    public final void rule__PatrolZoneConfig__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4648:1: ( rule__PatrolZoneConfig__Group__3__Impl rule__PatrolZoneConfig__Group__4 )
            // InternalMazeDsl.g:4649:2: rule__PatrolZoneConfig__Group__3__Impl rule__PatrolZoneConfig__Group__4
            {
            pushFollow(FOLLOW_17);
            rule__PatrolZoneConfig__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PatrolZoneConfig__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__Group__3"


    // $ANTLR start "rule__PatrolZoneConfig__Group__3__Impl"
    // InternalMazeDsl.g:4656:1: rule__PatrolZoneConfig__Group__3__Impl : ( '(' ) ;
    public final void rule__PatrolZoneConfig__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4660:1: ( ( '(' ) )
            // InternalMazeDsl.g:4661:1: ( '(' )
            {
            // InternalMazeDsl.g:4661:1: ( '(' )
            // InternalMazeDsl.g:4662:2: '('
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getLeftParenthesisKeyword_3()); 
            match(input,72,FOLLOW_2); 
             after(grammarAccess.getPatrolZoneConfigAccess().getLeftParenthesisKeyword_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__Group__3__Impl"


    // $ANTLR start "rule__PatrolZoneConfig__Group__4"
    // InternalMazeDsl.g:4671:1: rule__PatrolZoneConfig__Group__4 : rule__PatrolZoneConfig__Group__4__Impl rule__PatrolZoneConfig__Group__5 ;
    public final void rule__PatrolZoneConfig__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4675:1: ( rule__PatrolZoneConfig__Group__4__Impl rule__PatrolZoneConfig__Group__5 )
            // InternalMazeDsl.g:4676:2: rule__PatrolZoneConfig__Group__4__Impl rule__PatrolZoneConfig__Group__5
            {
            pushFollow(FOLLOW_39);
            rule__PatrolZoneConfig__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PatrolZoneConfig__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__Group__4"


    // $ANTLR start "rule__PatrolZoneConfig__Group__4__Impl"
    // InternalMazeDsl.g:4683:1: rule__PatrolZoneConfig__Group__4__Impl : ( ( rule__PatrolZoneConfig__TopLeftXAssignment_4 ) ) ;
    public final void rule__PatrolZoneConfig__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4687:1: ( ( ( rule__PatrolZoneConfig__TopLeftXAssignment_4 ) ) )
            // InternalMazeDsl.g:4688:1: ( ( rule__PatrolZoneConfig__TopLeftXAssignment_4 ) )
            {
            // InternalMazeDsl.g:4688:1: ( ( rule__PatrolZoneConfig__TopLeftXAssignment_4 ) )
            // InternalMazeDsl.g:4689:2: ( rule__PatrolZoneConfig__TopLeftXAssignment_4 )
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getTopLeftXAssignment_4()); 
            // InternalMazeDsl.g:4690:2: ( rule__PatrolZoneConfig__TopLeftXAssignment_4 )
            // InternalMazeDsl.g:4690:3: rule__PatrolZoneConfig__TopLeftXAssignment_4
            {
            pushFollow(FOLLOW_2);
            rule__PatrolZoneConfig__TopLeftXAssignment_4();

            state._fsp--;


            }

             after(grammarAccess.getPatrolZoneConfigAccess().getTopLeftXAssignment_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__Group__4__Impl"


    // $ANTLR start "rule__PatrolZoneConfig__Group__5"
    // InternalMazeDsl.g:4698:1: rule__PatrolZoneConfig__Group__5 : rule__PatrolZoneConfig__Group__5__Impl rule__PatrolZoneConfig__Group__6 ;
    public final void rule__PatrolZoneConfig__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4702:1: ( rule__PatrolZoneConfig__Group__5__Impl rule__PatrolZoneConfig__Group__6 )
            // InternalMazeDsl.g:4703:2: rule__PatrolZoneConfig__Group__5__Impl rule__PatrolZoneConfig__Group__6
            {
            pushFollow(FOLLOW_17);
            rule__PatrolZoneConfig__Group__5__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PatrolZoneConfig__Group__6();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__Group__5"


    // $ANTLR start "rule__PatrolZoneConfig__Group__5__Impl"
    // InternalMazeDsl.g:4710:1: rule__PatrolZoneConfig__Group__5__Impl : ( ',' ) ;
    public final void rule__PatrolZoneConfig__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4714:1: ( ( ',' ) )
            // InternalMazeDsl.g:4715:1: ( ',' )
            {
            // InternalMazeDsl.g:4715:1: ( ',' )
            // InternalMazeDsl.g:4716:2: ','
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getCommaKeyword_5()); 
            match(input,69,FOLLOW_2); 
             after(grammarAccess.getPatrolZoneConfigAccess().getCommaKeyword_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__Group__5__Impl"


    // $ANTLR start "rule__PatrolZoneConfig__Group__6"
    // InternalMazeDsl.g:4725:1: rule__PatrolZoneConfig__Group__6 : rule__PatrolZoneConfig__Group__6__Impl rule__PatrolZoneConfig__Group__7 ;
    public final void rule__PatrolZoneConfig__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4729:1: ( rule__PatrolZoneConfig__Group__6__Impl rule__PatrolZoneConfig__Group__7 )
            // InternalMazeDsl.g:4730:2: rule__PatrolZoneConfig__Group__6__Impl rule__PatrolZoneConfig__Group__7
            {
            pushFollow(FOLLOW_40);
            rule__PatrolZoneConfig__Group__6__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PatrolZoneConfig__Group__7();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__Group__6"


    // $ANTLR start "rule__PatrolZoneConfig__Group__6__Impl"
    // InternalMazeDsl.g:4737:1: rule__PatrolZoneConfig__Group__6__Impl : ( ( rule__PatrolZoneConfig__TopLeftYAssignment_6 ) ) ;
    public final void rule__PatrolZoneConfig__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4741:1: ( ( ( rule__PatrolZoneConfig__TopLeftYAssignment_6 ) ) )
            // InternalMazeDsl.g:4742:1: ( ( rule__PatrolZoneConfig__TopLeftYAssignment_6 ) )
            {
            // InternalMazeDsl.g:4742:1: ( ( rule__PatrolZoneConfig__TopLeftYAssignment_6 ) )
            // InternalMazeDsl.g:4743:2: ( rule__PatrolZoneConfig__TopLeftYAssignment_6 )
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getTopLeftYAssignment_6()); 
            // InternalMazeDsl.g:4744:2: ( rule__PatrolZoneConfig__TopLeftYAssignment_6 )
            // InternalMazeDsl.g:4744:3: rule__PatrolZoneConfig__TopLeftYAssignment_6
            {
            pushFollow(FOLLOW_2);
            rule__PatrolZoneConfig__TopLeftYAssignment_6();

            state._fsp--;


            }

             after(grammarAccess.getPatrolZoneConfigAccess().getTopLeftYAssignment_6()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__Group__6__Impl"


    // $ANTLR start "rule__PatrolZoneConfig__Group__7"
    // InternalMazeDsl.g:4752:1: rule__PatrolZoneConfig__Group__7 : rule__PatrolZoneConfig__Group__7__Impl rule__PatrolZoneConfig__Group__8 ;
    public final void rule__PatrolZoneConfig__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4756:1: ( rule__PatrolZoneConfig__Group__7__Impl rule__PatrolZoneConfig__Group__8 )
            // InternalMazeDsl.g:4757:2: rule__PatrolZoneConfig__Group__7__Impl rule__PatrolZoneConfig__Group__8
            {
            pushFollow(FOLLOW_41);
            rule__PatrolZoneConfig__Group__7__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PatrolZoneConfig__Group__8();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__Group__7"


    // $ANTLR start "rule__PatrolZoneConfig__Group__7__Impl"
    // InternalMazeDsl.g:4764:1: rule__PatrolZoneConfig__Group__7__Impl : ( ')' ) ;
    public final void rule__PatrolZoneConfig__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4768:1: ( ( ')' ) )
            // InternalMazeDsl.g:4769:1: ( ')' )
            {
            // InternalMazeDsl.g:4769:1: ( ')' )
            // InternalMazeDsl.g:4770:2: ')'
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getRightParenthesisKeyword_7()); 
            match(input,73,FOLLOW_2); 
             after(grammarAccess.getPatrolZoneConfigAccess().getRightParenthesisKeyword_7()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__Group__7__Impl"


    // $ANTLR start "rule__PatrolZoneConfig__Group__8"
    // InternalMazeDsl.g:4779:1: rule__PatrolZoneConfig__Group__8 : rule__PatrolZoneConfig__Group__8__Impl rule__PatrolZoneConfig__Group__9 ;
    public final void rule__PatrolZoneConfig__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4783:1: ( rule__PatrolZoneConfig__Group__8__Impl rule__PatrolZoneConfig__Group__9 )
            // InternalMazeDsl.g:4784:2: rule__PatrolZoneConfig__Group__8__Impl rule__PatrolZoneConfig__Group__9
            {
            pushFollow(FOLLOW_17);
            rule__PatrolZoneConfig__Group__8__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PatrolZoneConfig__Group__9();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__Group__8"


    // $ANTLR start "rule__PatrolZoneConfig__Group__8__Impl"
    // InternalMazeDsl.g:4791:1: rule__PatrolZoneConfig__Group__8__Impl : ( 'width' ) ;
    public final void rule__PatrolZoneConfig__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4795:1: ( ( 'width' ) )
            // InternalMazeDsl.g:4796:1: ( 'width' )
            {
            // InternalMazeDsl.g:4796:1: ( 'width' )
            // InternalMazeDsl.g:4797:2: 'width'
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getWidthKeyword_8()); 
            match(input,74,FOLLOW_2); 
             after(grammarAccess.getPatrolZoneConfigAccess().getWidthKeyword_8()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__Group__8__Impl"


    // $ANTLR start "rule__PatrolZoneConfig__Group__9"
    // InternalMazeDsl.g:4806:1: rule__PatrolZoneConfig__Group__9 : rule__PatrolZoneConfig__Group__9__Impl rule__PatrolZoneConfig__Group__10 ;
    public final void rule__PatrolZoneConfig__Group__9() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4810:1: ( rule__PatrolZoneConfig__Group__9__Impl rule__PatrolZoneConfig__Group__10 )
            // InternalMazeDsl.g:4811:2: rule__PatrolZoneConfig__Group__9__Impl rule__PatrolZoneConfig__Group__10
            {
            pushFollow(FOLLOW_42);
            rule__PatrolZoneConfig__Group__9__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PatrolZoneConfig__Group__10();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__Group__9"


    // $ANTLR start "rule__PatrolZoneConfig__Group__9__Impl"
    // InternalMazeDsl.g:4818:1: rule__PatrolZoneConfig__Group__9__Impl : ( ( rule__PatrolZoneConfig__WidthAssignment_9 ) ) ;
    public final void rule__PatrolZoneConfig__Group__9__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4822:1: ( ( ( rule__PatrolZoneConfig__WidthAssignment_9 ) ) )
            // InternalMazeDsl.g:4823:1: ( ( rule__PatrolZoneConfig__WidthAssignment_9 ) )
            {
            // InternalMazeDsl.g:4823:1: ( ( rule__PatrolZoneConfig__WidthAssignment_9 ) )
            // InternalMazeDsl.g:4824:2: ( rule__PatrolZoneConfig__WidthAssignment_9 )
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getWidthAssignment_9()); 
            // InternalMazeDsl.g:4825:2: ( rule__PatrolZoneConfig__WidthAssignment_9 )
            // InternalMazeDsl.g:4825:3: rule__PatrolZoneConfig__WidthAssignment_9
            {
            pushFollow(FOLLOW_2);
            rule__PatrolZoneConfig__WidthAssignment_9();

            state._fsp--;


            }

             after(grammarAccess.getPatrolZoneConfigAccess().getWidthAssignment_9()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__Group__9__Impl"


    // $ANTLR start "rule__PatrolZoneConfig__Group__10"
    // InternalMazeDsl.g:4833:1: rule__PatrolZoneConfig__Group__10 : rule__PatrolZoneConfig__Group__10__Impl rule__PatrolZoneConfig__Group__11 ;
    public final void rule__PatrolZoneConfig__Group__10() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4837:1: ( rule__PatrolZoneConfig__Group__10__Impl rule__PatrolZoneConfig__Group__11 )
            // InternalMazeDsl.g:4838:2: rule__PatrolZoneConfig__Group__10__Impl rule__PatrolZoneConfig__Group__11
            {
            pushFollow(FOLLOW_17);
            rule__PatrolZoneConfig__Group__10__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PatrolZoneConfig__Group__11();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__Group__10"


    // $ANTLR start "rule__PatrolZoneConfig__Group__10__Impl"
    // InternalMazeDsl.g:4845:1: rule__PatrolZoneConfig__Group__10__Impl : ( 'height' ) ;
    public final void rule__PatrolZoneConfig__Group__10__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4849:1: ( ( 'height' ) )
            // InternalMazeDsl.g:4850:1: ( 'height' )
            {
            // InternalMazeDsl.g:4850:1: ( 'height' )
            // InternalMazeDsl.g:4851:2: 'height'
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getHeightKeyword_10()); 
            match(input,75,FOLLOW_2); 
             after(grammarAccess.getPatrolZoneConfigAccess().getHeightKeyword_10()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__Group__10__Impl"


    // $ANTLR start "rule__PatrolZoneConfig__Group__11"
    // InternalMazeDsl.g:4860:1: rule__PatrolZoneConfig__Group__11 : rule__PatrolZoneConfig__Group__11__Impl rule__PatrolZoneConfig__Group__12 ;
    public final void rule__PatrolZoneConfig__Group__11() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4864:1: ( rule__PatrolZoneConfig__Group__11__Impl rule__PatrolZoneConfig__Group__12 )
            // InternalMazeDsl.g:4865:2: rule__PatrolZoneConfig__Group__11__Impl rule__PatrolZoneConfig__Group__12
            {
            pushFollow(FOLLOW_37);
            rule__PatrolZoneConfig__Group__11__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PatrolZoneConfig__Group__12();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__Group__11"


    // $ANTLR start "rule__PatrolZoneConfig__Group__11__Impl"
    // InternalMazeDsl.g:4872:1: rule__PatrolZoneConfig__Group__11__Impl : ( ( rule__PatrolZoneConfig__HeightAssignment_11 ) ) ;
    public final void rule__PatrolZoneConfig__Group__11__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4876:1: ( ( ( rule__PatrolZoneConfig__HeightAssignment_11 ) ) )
            // InternalMazeDsl.g:4877:1: ( ( rule__PatrolZoneConfig__HeightAssignment_11 ) )
            {
            // InternalMazeDsl.g:4877:1: ( ( rule__PatrolZoneConfig__HeightAssignment_11 ) )
            // InternalMazeDsl.g:4878:2: ( rule__PatrolZoneConfig__HeightAssignment_11 )
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getHeightAssignment_11()); 
            // InternalMazeDsl.g:4879:2: ( rule__PatrolZoneConfig__HeightAssignment_11 )
            // InternalMazeDsl.g:4879:3: rule__PatrolZoneConfig__HeightAssignment_11
            {
            pushFollow(FOLLOW_2);
            rule__PatrolZoneConfig__HeightAssignment_11();

            state._fsp--;


            }

             after(grammarAccess.getPatrolZoneConfigAccess().getHeightAssignment_11()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__Group__11__Impl"


    // $ANTLR start "rule__PatrolZoneConfig__Group__12"
    // InternalMazeDsl.g:4887:1: rule__PatrolZoneConfig__Group__12 : rule__PatrolZoneConfig__Group__12__Impl ;
    public final void rule__PatrolZoneConfig__Group__12() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4891:1: ( rule__PatrolZoneConfig__Group__12__Impl )
            // InternalMazeDsl.g:4892:2: rule__PatrolZoneConfig__Group__12__Impl
            {
            pushFollow(FOLLOW_2);
            rule__PatrolZoneConfig__Group__12__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__Group__12"


    // $ANTLR start "rule__PatrolZoneConfig__Group__12__Impl"
    // InternalMazeDsl.g:4898:1: rule__PatrolZoneConfig__Group__12__Impl : ( '}' ) ;
    public final void rule__PatrolZoneConfig__Group__12__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4902:1: ( ( '}' ) )
            // InternalMazeDsl.g:4903:1: ( '}' )
            {
            // InternalMazeDsl.g:4903:1: ( '}' )
            // InternalMazeDsl.g:4904:2: '}'
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getRightCurlyBracketKeyword_12()); 
            match(input,33,FOLLOW_2); 
             after(grammarAccess.getPatrolZoneConfigAccess().getRightCurlyBracketKeyword_12()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__Group__12__Impl"


    // $ANTLR start "rule__Waypoint__Group__0"
    // InternalMazeDsl.g:4914:1: rule__Waypoint__Group__0 : rule__Waypoint__Group__0__Impl rule__Waypoint__Group__1 ;
    public final void rule__Waypoint__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4918:1: ( rule__Waypoint__Group__0__Impl rule__Waypoint__Group__1 )
            // InternalMazeDsl.g:4919:2: rule__Waypoint__Group__0__Impl rule__Waypoint__Group__1
            {
            pushFollow(FOLLOW_17);
            rule__Waypoint__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Waypoint__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Waypoint__Group__0"


    // $ANTLR start "rule__Waypoint__Group__0__Impl"
    // InternalMazeDsl.g:4926:1: rule__Waypoint__Group__0__Impl : ( '(' ) ;
    public final void rule__Waypoint__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4930:1: ( ( '(' ) )
            // InternalMazeDsl.g:4931:1: ( '(' )
            {
            // InternalMazeDsl.g:4931:1: ( '(' )
            // InternalMazeDsl.g:4932:2: '('
            {
             before(grammarAccess.getWaypointAccess().getLeftParenthesisKeyword_0()); 
            match(input,72,FOLLOW_2); 
             after(grammarAccess.getWaypointAccess().getLeftParenthesisKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Waypoint__Group__0__Impl"


    // $ANTLR start "rule__Waypoint__Group__1"
    // InternalMazeDsl.g:4941:1: rule__Waypoint__Group__1 : rule__Waypoint__Group__1__Impl rule__Waypoint__Group__2 ;
    public final void rule__Waypoint__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4945:1: ( rule__Waypoint__Group__1__Impl rule__Waypoint__Group__2 )
            // InternalMazeDsl.g:4946:2: rule__Waypoint__Group__1__Impl rule__Waypoint__Group__2
            {
            pushFollow(FOLLOW_39);
            rule__Waypoint__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Waypoint__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Waypoint__Group__1"


    // $ANTLR start "rule__Waypoint__Group__1__Impl"
    // InternalMazeDsl.g:4953:1: rule__Waypoint__Group__1__Impl : ( ( rule__Waypoint__XAssignment_1 ) ) ;
    public final void rule__Waypoint__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4957:1: ( ( ( rule__Waypoint__XAssignment_1 ) ) )
            // InternalMazeDsl.g:4958:1: ( ( rule__Waypoint__XAssignment_1 ) )
            {
            // InternalMazeDsl.g:4958:1: ( ( rule__Waypoint__XAssignment_1 ) )
            // InternalMazeDsl.g:4959:2: ( rule__Waypoint__XAssignment_1 )
            {
             before(grammarAccess.getWaypointAccess().getXAssignment_1()); 
            // InternalMazeDsl.g:4960:2: ( rule__Waypoint__XAssignment_1 )
            // InternalMazeDsl.g:4960:3: rule__Waypoint__XAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__Waypoint__XAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getWaypointAccess().getXAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Waypoint__Group__1__Impl"


    // $ANTLR start "rule__Waypoint__Group__2"
    // InternalMazeDsl.g:4968:1: rule__Waypoint__Group__2 : rule__Waypoint__Group__2__Impl rule__Waypoint__Group__3 ;
    public final void rule__Waypoint__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4972:1: ( rule__Waypoint__Group__2__Impl rule__Waypoint__Group__3 )
            // InternalMazeDsl.g:4973:2: rule__Waypoint__Group__2__Impl rule__Waypoint__Group__3
            {
            pushFollow(FOLLOW_17);
            rule__Waypoint__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Waypoint__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Waypoint__Group__2"


    // $ANTLR start "rule__Waypoint__Group__2__Impl"
    // InternalMazeDsl.g:4980:1: rule__Waypoint__Group__2__Impl : ( ',' ) ;
    public final void rule__Waypoint__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4984:1: ( ( ',' ) )
            // InternalMazeDsl.g:4985:1: ( ',' )
            {
            // InternalMazeDsl.g:4985:1: ( ',' )
            // InternalMazeDsl.g:4986:2: ','
            {
             before(grammarAccess.getWaypointAccess().getCommaKeyword_2()); 
            match(input,69,FOLLOW_2); 
             after(grammarAccess.getWaypointAccess().getCommaKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Waypoint__Group__2__Impl"


    // $ANTLR start "rule__Waypoint__Group__3"
    // InternalMazeDsl.g:4995:1: rule__Waypoint__Group__3 : rule__Waypoint__Group__3__Impl rule__Waypoint__Group__4 ;
    public final void rule__Waypoint__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4999:1: ( rule__Waypoint__Group__3__Impl rule__Waypoint__Group__4 )
            // InternalMazeDsl.g:5000:2: rule__Waypoint__Group__3__Impl rule__Waypoint__Group__4
            {
            pushFollow(FOLLOW_40);
            rule__Waypoint__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Waypoint__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Waypoint__Group__3"


    // $ANTLR start "rule__Waypoint__Group__3__Impl"
    // InternalMazeDsl.g:5007:1: rule__Waypoint__Group__3__Impl : ( ( rule__Waypoint__YAssignment_3 ) ) ;
    public final void rule__Waypoint__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5011:1: ( ( ( rule__Waypoint__YAssignment_3 ) ) )
            // InternalMazeDsl.g:5012:1: ( ( rule__Waypoint__YAssignment_3 ) )
            {
            // InternalMazeDsl.g:5012:1: ( ( rule__Waypoint__YAssignment_3 ) )
            // InternalMazeDsl.g:5013:2: ( rule__Waypoint__YAssignment_3 )
            {
             before(grammarAccess.getWaypointAccess().getYAssignment_3()); 
            // InternalMazeDsl.g:5014:2: ( rule__Waypoint__YAssignment_3 )
            // InternalMazeDsl.g:5014:3: rule__Waypoint__YAssignment_3
            {
            pushFollow(FOLLOW_2);
            rule__Waypoint__YAssignment_3();

            state._fsp--;


            }

             after(grammarAccess.getWaypointAccess().getYAssignment_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Waypoint__Group__3__Impl"


    // $ANTLR start "rule__Waypoint__Group__4"
    // InternalMazeDsl.g:5022:1: rule__Waypoint__Group__4 : rule__Waypoint__Group__4__Impl rule__Waypoint__Group__5 ;
    public final void rule__Waypoint__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5026:1: ( rule__Waypoint__Group__4__Impl rule__Waypoint__Group__5 )
            // InternalMazeDsl.g:5027:2: rule__Waypoint__Group__4__Impl rule__Waypoint__Group__5
            {
            pushFollow(FOLLOW_43);
            rule__Waypoint__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Waypoint__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Waypoint__Group__4"


    // $ANTLR start "rule__Waypoint__Group__4__Impl"
    // InternalMazeDsl.g:5034:1: rule__Waypoint__Group__4__Impl : ( ')' ) ;
    public final void rule__Waypoint__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5038:1: ( ( ')' ) )
            // InternalMazeDsl.g:5039:1: ( ')' )
            {
            // InternalMazeDsl.g:5039:1: ( ')' )
            // InternalMazeDsl.g:5040:2: ')'
            {
             before(grammarAccess.getWaypointAccess().getRightParenthesisKeyword_4()); 
            match(input,73,FOLLOW_2); 
             after(grammarAccess.getWaypointAccess().getRightParenthesisKeyword_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Waypoint__Group__4__Impl"


    // $ANTLR start "rule__Waypoint__Group__5"
    // InternalMazeDsl.g:5049:1: rule__Waypoint__Group__5 : rule__Waypoint__Group__5__Impl ;
    public final void rule__Waypoint__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5053:1: ( rule__Waypoint__Group__5__Impl )
            // InternalMazeDsl.g:5054:2: rule__Waypoint__Group__5__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Waypoint__Group__5__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Waypoint__Group__5"


    // $ANTLR start "rule__Waypoint__Group__5__Impl"
    // InternalMazeDsl.g:5060:1: rule__Waypoint__Group__5__Impl : ( ( rule__Waypoint__Group_5__0 )? ) ;
    public final void rule__Waypoint__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5064:1: ( ( ( rule__Waypoint__Group_5__0 )? ) )
            // InternalMazeDsl.g:5065:1: ( ( rule__Waypoint__Group_5__0 )? )
            {
            // InternalMazeDsl.g:5065:1: ( ( rule__Waypoint__Group_5__0 )? )
            // InternalMazeDsl.g:5066:2: ( rule__Waypoint__Group_5__0 )?
            {
             before(grammarAccess.getWaypointAccess().getGroup_5()); 
            // InternalMazeDsl.g:5067:2: ( rule__Waypoint__Group_5__0 )?
            int alt45=2;
            int LA45_0 = input.LA(1);

            if ( (LA45_0==76) ) {
                alt45=1;
            }
            switch (alt45) {
                case 1 :
                    // InternalMazeDsl.g:5067:3: rule__Waypoint__Group_5__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Waypoint__Group_5__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getWaypointAccess().getGroup_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Waypoint__Group__5__Impl"


    // $ANTLR start "rule__Waypoint__Group_5__0"
    // InternalMazeDsl.g:5076:1: rule__Waypoint__Group_5__0 : rule__Waypoint__Group_5__0__Impl rule__Waypoint__Group_5__1 ;
    public final void rule__Waypoint__Group_5__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5080:1: ( rule__Waypoint__Group_5__0__Impl rule__Waypoint__Group_5__1 )
            // InternalMazeDsl.g:5081:2: rule__Waypoint__Group_5__0__Impl rule__Waypoint__Group_5__1
            {
            pushFollow(FOLLOW_26);
            rule__Waypoint__Group_5__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Waypoint__Group_5__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Waypoint__Group_5__0"


    // $ANTLR start "rule__Waypoint__Group_5__0__Impl"
    // InternalMazeDsl.g:5088:1: rule__Waypoint__Group_5__0__Impl : ( ':' ) ;
    public final void rule__Waypoint__Group_5__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5092:1: ( ( ':' ) )
            // InternalMazeDsl.g:5093:1: ( ':' )
            {
            // InternalMazeDsl.g:5093:1: ( ':' )
            // InternalMazeDsl.g:5094:2: ':'
            {
             before(grammarAccess.getWaypointAccess().getColonKeyword_5_0()); 
            match(input,76,FOLLOW_2); 
             after(grammarAccess.getWaypointAccess().getColonKeyword_5_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Waypoint__Group_5__0__Impl"


    // $ANTLR start "rule__Waypoint__Group_5__1"
    // InternalMazeDsl.g:5103:1: rule__Waypoint__Group_5__1 : rule__Waypoint__Group_5__1__Impl rule__Waypoint__Group_5__2 ;
    public final void rule__Waypoint__Group_5__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5107:1: ( rule__Waypoint__Group_5__1__Impl rule__Waypoint__Group_5__2 )
            // InternalMazeDsl.g:5108:2: rule__Waypoint__Group_5__1__Impl rule__Waypoint__Group_5__2
            {
            pushFollow(FOLLOW_44);
            rule__Waypoint__Group_5__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Waypoint__Group_5__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Waypoint__Group_5__1"


    // $ANTLR start "rule__Waypoint__Group_5__1__Impl"
    // InternalMazeDsl.g:5115:1: rule__Waypoint__Group_5__1__Impl : ( ( rule__Waypoint__WaitTimeAssignment_5_1 ) ) ;
    public final void rule__Waypoint__Group_5__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5119:1: ( ( ( rule__Waypoint__WaitTimeAssignment_5_1 ) ) )
            // InternalMazeDsl.g:5120:1: ( ( rule__Waypoint__WaitTimeAssignment_5_1 ) )
            {
            // InternalMazeDsl.g:5120:1: ( ( rule__Waypoint__WaitTimeAssignment_5_1 ) )
            // InternalMazeDsl.g:5121:2: ( rule__Waypoint__WaitTimeAssignment_5_1 )
            {
             before(grammarAccess.getWaypointAccess().getWaitTimeAssignment_5_1()); 
            // InternalMazeDsl.g:5122:2: ( rule__Waypoint__WaitTimeAssignment_5_1 )
            // InternalMazeDsl.g:5122:3: rule__Waypoint__WaitTimeAssignment_5_1
            {
            pushFollow(FOLLOW_2);
            rule__Waypoint__WaitTimeAssignment_5_1();

            state._fsp--;


            }

             after(grammarAccess.getWaypointAccess().getWaitTimeAssignment_5_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Waypoint__Group_5__1__Impl"


    // $ANTLR start "rule__Waypoint__Group_5__2"
    // InternalMazeDsl.g:5130:1: rule__Waypoint__Group_5__2 : rule__Waypoint__Group_5__2__Impl ;
    public final void rule__Waypoint__Group_5__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5134:1: ( rule__Waypoint__Group_5__2__Impl )
            // InternalMazeDsl.g:5135:2: rule__Waypoint__Group_5__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Waypoint__Group_5__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Waypoint__Group_5__2"


    // $ANTLR start "rule__Waypoint__Group_5__2__Impl"
    // InternalMazeDsl.g:5141:1: rule__Waypoint__Group_5__2__Impl : ( 'ms' ) ;
    public final void rule__Waypoint__Group_5__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5145:1: ( ( 'ms' ) )
            // InternalMazeDsl.g:5146:1: ( 'ms' )
            {
            // InternalMazeDsl.g:5146:1: ( 'ms' )
            // InternalMazeDsl.g:5147:2: 'ms'
            {
             before(grammarAccess.getWaypointAccess().getMsKeyword_5_2()); 
            match(input,77,FOLLOW_2); 
             after(grammarAccess.getWaypointAccess().getMsKeyword_5_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Waypoint__Group_5__2__Impl"


    // $ANTLR start "rule__LootTableConfig__Group__0"
    // InternalMazeDsl.g:5157:1: rule__LootTableConfig__Group__0 : rule__LootTableConfig__Group__0__Impl rule__LootTableConfig__Group__1 ;
    public final void rule__LootTableConfig__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5161:1: ( rule__LootTableConfig__Group__0__Impl rule__LootTableConfig__Group__1 )
            // InternalMazeDsl.g:5162:2: rule__LootTableConfig__Group__0__Impl rule__LootTableConfig__Group__1
            {
            pushFollow(FOLLOW_3);
            rule__LootTableConfig__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LootTableConfig__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootTableConfig__Group__0"


    // $ANTLR start "rule__LootTableConfig__Group__0__Impl"
    // InternalMazeDsl.g:5169:1: rule__LootTableConfig__Group__0__Impl : ( 'loot-table' ) ;
    public final void rule__LootTableConfig__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5173:1: ( ( 'loot-table' ) )
            // InternalMazeDsl.g:5174:1: ( 'loot-table' )
            {
            // InternalMazeDsl.g:5174:1: ( 'loot-table' )
            // InternalMazeDsl.g:5175:2: 'loot-table'
            {
             before(grammarAccess.getLootTableConfigAccess().getLootTableKeyword_0()); 
            match(input,78,FOLLOW_2); 
             after(grammarAccess.getLootTableConfigAccess().getLootTableKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootTableConfig__Group__0__Impl"


    // $ANTLR start "rule__LootTableConfig__Group__1"
    // InternalMazeDsl.g:5184:1: rule__LootTableConfig__Group__1 : rule__LootTableConfig__Group__1__Impl rule__LootTableConfig__Group__2 ;
    public final void rule__LootTableConfig__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5188:1: ( rule__LootTableConfig__Group__1__Impl rule__LootTableConfig__Group__2 )
            // InternalMazeDsl.g:5189:2: rule__LootTableConfig__Group__1__Impl rule__LootTableConfig__Group__2
            {
            pushFollow(FOLLOW_4);
            rule__LootTableConfig__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LootTableConfig__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootTableConfig__Group__1"


    // $ANTLR start "rule__LootTableConfig__Group__1__Impl"
    // InternalMazeDsl.g:5196:1: rule__LootTableConfig__Group__1__Impl : ( ( rule__LootTableConfig__NameAssignment_1 ) ) ;
    public final void rule__LootTableConfig__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5200:1: ( ( ( rule__LootTableConfig__NameAssignment_1 ) ) )
            // InternalMazeDsl.g:5201:1: ( ( rule__LootTableConfig__NameAssignment_1 ) )
            {
            // InternalMazeDsl.g:5201:1: ( ( rule__LootTableConfig__NameAssignment_1 ) )
            // InternalMazeDsl.g:5202:2: ( rule__LootTableConfig__NameAssignment_1 )
            {
             before(grammarAccess.getLootTableConfigAccess().getNameAssignment_1()); 
            // InternalMazeDsl.g:5203:2: ( rule__LootTableConfig__NameAssignment_1 )
            // InternalMazeDsl.g:5203:3: rule__LootTableConfig__NameAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__LootTableConfig__NameAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getLootTableConfigAccess().getNameAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootTableConfig__Group__1__Impl"


    // $ANTLR start "rule__LootTableConfig__Group__2"
    // InternalMazeDsl.g:5211:1: rule__LootTableConfig__Group__2 : rule__LootTableConfig__Group__2__Impl rule__LootTableConfig__Group__3 ;
    public final void rule__LootTableConfig__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5215:1: ( rule__LootTableConfig__Group__2__Impl rule__LootTableConfig__Group__3 )
            // InternalMazeDsl.g:5216:2: rule__LootTableConfig__Group__2__Impl rule__LootTableConfig__Group__3
            {
            pushFollow(FOLLOW_45);
            rule__LootTableConfig__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LootTableConfig__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootTableConfig__Group__2"


    // $ANTLR start "rule__LootTableConfig__Group__2__Impl"
    // InternalMazeDsl.g:5223:1: rule__LootTableConfig__Group__2__Impl : ( '{' ) ;
    public final void rule__LootTableConfig__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5227:1: ( ( '{' ) )
            // InternalMazeDsl.g:5228:1: ( '{' )
            {
            // InternalMazeDsl.g:5228:1: ( '{' )
            // InternalMazeDsl.g:5229:2: '{'
            {
             before(grammarAccess.getLootTableConfigAccess().getLeftCurlyBracketKeyword_2()); 
            match(input,32,FOLLOW_2); 
             after(grammarAccess.getLootTableConfigAccess().getLeftCurlyBracketKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootTableConfig__Group__2__Impl"


    // $ANTLR start "rule__LootTableConfig__Group__3"
    // InternalMazeDsl.g:5238:1: rule__LootTableConfig__Group__3 : rule__LootTableConfig__Group__3__Impl rule__LootTableConfig__Group__4 ;
    public final void rule__LootTableConfig__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5242:1: ( rule__LootTableConfig__Group__3__Impl rule__LootTableConfig__Group__4 )
            // InternalMazeDsl.g:5243:2: rule__LootTableConfig__Group__3__Impl rule__LootTableConfig__Group__4
            {
            pushFollow(FOLLOW_45);
            rule__LootTableConfig__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LootTableConfig__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootTableConfig__Group__3"


    // $ANTLR start "rule__LootTableConfig__Group__3__Impl"
    // InternalMazeDsl.g:5250:1: rule__LootTableConfig__Group__3__Impl : ( ( rule__LootTableConfig__Group_3__0 )? ) ;
    public final void rule__LootTableConfig__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5254:1: ( ( ( rule__LootTableConfig__Group_3__0 )? ) )
            // InternalMazeDsl.g:5255:1: ( ( rule__LootTableConfig__Group_3__0 )? )
            {
            // InternalMazeDsl.g:5255:1: ( ( rule__LootTableConfig__Group_3__0 )? )
            // InternalMazeDsl.g:5256:2: ( rule__LootTableConfig__Group_3__0 )?
            {
             before(grammarAccess.getLootTableConfigAccess().getGroup_3()); 
            // InternalMazeDsl.g:5257:2: ( rule__LootTableConfig__Group_3__0 )?
            int alt46=2;
            int LA46_0 = input.LA(1);

            if ( (LA46_0==79) ) {
                alt46=1;
            }
            switch (alt46) {
                case 1 :
                    // InternalMazeDsl.g:5257:3: rule__LootTableConfig__Group_3__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__LootTableConfig__Group_3__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getLootTableConfigAccess().getGroup_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootTableConfig__Group__3__Impl"


    // $ANTLR start "rule__LootTableConfig__Group__4"
    // InternalMazeDsl.g:5265:1: rule__LootTableConfig__Group__4 : rule__LootTableConfig__Group__4__Impl rule__LootTableConfig__Group__5 ;
    public final void rule__LootTableConfig__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5269:1: ( rule__LootTableConfig__Group__4__Impl rule__LootTableConfig__Group__5 )
            // InternalMazeDsl.g:5270:2: rule__LootTableConfig__Group__4__Impl rule__LootTableConfig__Group__5
            {
            pushFollow(FOLLOW_37);
            rule__LootTableConfig__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LootTableConfig__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootTableConfig__Group__4"


    // $ANTLR start "rule__LootTableConfig__Group__4__Impl"
    // InternalMazeDsl.g:5277:1: rule__LootTableConfig__Group__4__Impl : ( ( ( rule__LootTableConfig__ItemsAssignment_4 ) ) ( ( rule__LootTableConfig__ItemsAssignment_4 )* ) ) ;
    public final void rule__LootTableConfig__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5281:1: ( ( ( ( rule__LootTableConfig__ItemsAssignment_4 ) ) ( ( rule__LootTableConfig__ItemsAssignment_4 )* ) ) )
            // InternalMazeDsl.g:5282:1: ( ( ( rule__LootTableConfig__ItemsAssignment_4 ) ) ( ( rule__LootTableConfig__ItemsAssignment_4 )* ) )
            {
            // InternalMazeDsl.g:5282:1: ( ( ( rule__LootTableConfig__ItemsAssignment_4 ) ) ( ( rule__LootTableConfig__ItemsAssignment_4 )* ) )
            // InternalMazeDsl.g:5283:2: ( ( rule__LootTableConfig__ItemsAssignment_4 ) ) ( ( rule__LootTableConfig__ItemsAssignment_4 )* )
            {
            // InternalMazeDsl.g:5283:2: ( ( rule__LootTableConfig__ItemsAssignment_4 ) )
            // InternalMazeDsl.g:5284:3: ( rule__LootTableConfig__ItemsAssignment_4 )
            {
             before(grammarAccess.getLootTableConfigAccess().getItemsAssignment_4()); 
            // InternalMazeDsl.g:5285:3: ( rule__LootTableConfig__ItemsAssignment_4 )
            // InternalMazeDsl.g:5285:4: rule__LootTableConfig__ItemsAssignment_4
            {
            pushFollow(FOLLOW_46);
            rule__LootTableConfig__ItemsAssignment_4();

            state._fsp--;


            }

             after(grammarAccess.getLootTableConfigAccess().getItemsAssignment_4()); 

            }

            // InternalMazeDsl.g:5288:2: ( ( rule__LootTableConfig__ItemsAssignment_4 )* )
            // InternalMazeDsl.g:5289:3: ( rule__LootTableConfig__ItemsAssignment_4 )*
            {
             before(grammarAccess.getLootTableConfigAccess().getItemsAssignment_4()); 
            // InternalMazeDsl.g:5290:3: ( rule__LootTableConfig__ItemsAssignment_4 )*
            loop47:
            do {
                int alt47=2;
                int LA47_0 = input.LA(1);

                if ( (LA47_0==80) ) {
                    alt47=1;
                }


                switch (alt47) {
            	case 1 :
            	    // InternalMazeDsl.g:5290:4: rule__LootTableConfig__ItemsAssignment_4
            	    {
            	    pushFollow(FOLLOW_46);
            	    rule__LootTableConfig__ItemsAssignment_4();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop47;
                }
            } while (true);

             after(grammarAccess.getLootTableConfigAccess().getItemsAssignment_4()); 

            }


            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootTableConfig__Group__4__Impl"


    // $ANTLR start "rule__LootTableConfig__Group__5"
    // InternalMazeDsl.g:5299:1: rule__LootTableConfig__Group__5 : rule__LootTableConfig__Group__5__Impl ;
    public final void rule__LootTableConfig__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5303:1: ( rule__LootTableConfig__Group__5__Impl )
            // InternalMazeDsl.g:5304:2: rule__LootTableConfig__Group__5__Impl
            {
            pushFollow(FOLLOW_2);
            rule__LootTableConfig__Group__5__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootTableConfig__Group__5"


    // $ANTLR start "rule__LootTableConfig__Group__5__Impl"
    // InternalMazeDsl.g:5310:1: rule__LootTableConfig__Group__5__Impl : ( '}' ) ;
    public final void rule__LootTableConfig__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5314:1: ( ( '}' ) )
            // InternalMazeDsl.g:5315:1: ( '}' )
            {
            // InternalMazeDsl.g:5315:1: ( '}' )
            // InternalMazeDsl.g:5316:2: '}'
            {
             before(grammarAccess.getLootTableConfigAccess().getRightCurlyBracketKeyword_5()); 
            match(input,33,FOLLOW_2); 
             after(grammarAccess.getLootTableConfigAccess().getRightCurlyBracketKeyword_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootTableConfig__Group__5__Impl"


    // $ANTLR start "rule__LootTableConfig__Group_3__0"
    // InternalMazeDsl.g:5326:1: rule__LootTableConfig__Group_3__0 : rule__LootTableConfig__Group_3__0__Impl rule__LootTableConfig__Group_3__1 ;
    public final void rule__LootTableConfig__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5330:1: ( rule__LootTableConfig__Group_3__0__Impl rule__LootTableConfig__Group_3__1 )
            // InternalMazeDsl.g:5331:2: rule__LootTableConfig__Group_3__0__Impl rule__LootTableConfig__Group_3__1
            {
            pushFollow(FOLLOW_26);
            rule__LootTableConfig__Group_3__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LootTableConfig__Group_3__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootTableConfig__Group_3__0"


    // $ANTLR start "rule__LootTableConfig__Group_3__0__Impl"
    // InternalMazeDsl.g:5338:1: rule__LootTableConfig__Group_3__0__Impl : ( 'capacity' ) ;
    public final void rule__LootTableConfig__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5342:1: ( ( 'capacity' ) )
            // InternalMazeDsl.g:5343:1: ( 'capacity' )
            {
            // InternalMazeDsl.g:5343:1: ( 'capacity' )
            // InternalMazeDsl.g:5344:2: 'capacity'
            {
             before(grammarAccess.getLootTableConfigAccess().getCapacityKeyword_3_0()); 
            match(input,79,FOLLOW_2); 
             after(grammarAccess.getLootTableConfigAccess().getCapacityKeyword_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootTableConfig__Group_3__0__Impl"


    // $ANTLR start "rule__LootTableConfig__Group_3__1"
    // InternalMazeDsl.g:5353:1: rule__LootTableConfig__Group_3__1 : rule__LootTableConfig__Group_3__1__Impl ;
    public final void rule__LootTableConfig__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5357:1: ( rule__LootTableConfig__Group_3__1__Impl )
            // InternalMazeDsl.g:5358:2: rule__LootTableConfig__Group_3__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__LootTableConfig__Group_3__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootTableConfig__Group_3__1"


    // $ANTLR start "rule__LootTableConfig__Group_3__1__Impl"
    // InternalMazeDsl.g:5364:1: rule__LootTableConfig__Group_3__1__Impl : ( ( rule__LootTableConfig__CapacityAssignment_3_1 ) ) ;
    public final void rule__LootTableConfig__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5368:1: ( ( ( rule__LootTableConfig__CapacityAssignment_3_1 ) ) )
            // InternalMazeDsl.g:5369:1: ( ( rule__LootTableConfig__CapacityAssignment_3_1 ) )
            {
            // InternalMazeDsl.g:5369:1: ( ( rule__LootTableConfig__CapacityAssignment_3_1 ) )
            // InternalMazeDsl.g:5370:2: ( rule__LootTableConfig__CapacityAssignment_3_1 )
            {
             before(grammarAccess.getLootTableConfigAccess().getCapacityAssignment_3_1()); 
            // InternalMazeDsl.g:5371:2: ( rule__LootTableConfig__CapacityAssignment_3_1 )
            // InternalMazeDsl.g:5371:3: rule__LootTableConfig__CapacityAssignment_3_1
            {
            pushFollow(FOLLOW_2);
            rule__LootTableConfig__CapacityAssignment_3_1();

            state._fsp--;


            }

             after(grammarAccess.getLootTableConfigAccess().getCapacityAssignment_3_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootTableConfig__Group_3__1__Impl"


    // $ANTLR start "rule__LootItemConfig__Group__0"
    // InternalMazeDsl.g:5380:1: rule__LootItemConfig__Group__0 : rule__LootItemConfig__Group__0__Impl rule__LootItemConfig__Group__1 ;
    public final void rule__LootItemConfig__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5384:1: ( rule__LootItemConfig__Group__0__Impl rule__LootItemConfig__Group__1 )
            // InternalMazeDsl.g:5385:2: rule__LootItemConfig__Group__0__Impl rule__LootItemConfig__Group__1
            {
            pushFollow(FOLLOW_3);
            rule__LootItemConfig__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LootItemConfig__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootItemConfig__Group__0"


    // $ANTLR start "rule__LootItemConfig__Group__0__Impl"
    // InternalMazeDsl.g:5392:1: rule__LootItemConfig__Group__0__Impl : ( 'item' ) ;
    public final void rule__LootItemConfig__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5396:1: ( ( 'item' ) )
            // InternalMazeDsl.g:5397:1: ( 'item' )
            {
            // InternalMazeDsl.g:5397:1: ( 'item' )
            // InternalMazeDsl.g:5398:2: 'item'
            {
             before(grammarAccess.getLootItemConfigAccess().getItemKeyword_0()); 
            match(input,80,FOLLOW_2); 
             after(grammarAccess.getLootItemConfigAccess().getItemKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootItemConfig__Group__0__Impl"


    // $ANTLR start "rule__LootItemConfig__Group__1"
    // InternalMazeDsl.g:5407:1: rule__LootItemConfig__Group__1 : rule__LootItemConfig__Group__1__Impl rule__LootItemConfig__Group__2 ;
    public final void rule__LootItemConfig__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5411:1: ( rule__LootItemConfig__Group__1__Impl rule__LootItemConfig__Group__2 )
            // InternalMazeDsl.g:5412:2: rule__LootItemConfig__Group__1__Impl rule__LootItemConfig__Group__2
            {
            pushFollow(FOLLOW_4);
            rule__LootItemConfig__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LootItemConfig__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootItemConfig__Group__1"


    // $ANTLR start "rule__LootItemConfig__Group__1__Impl"
    // InternalMazeDsl.g:5419:1: rule__LootItemConfig__Group__1__Impl : ( ( rule__LootItemConfig__NameAssignment_1 ) ) ;
    public final void rule__LootItemConfig__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5423:1: ( ( ( rule__LootItemConfig__NameAssignment_1 ) ) )
            // InternalMazeDsl.g:5424:1: ( ( rule__LootItemConfig__NameAssignment_1 ) )
            {
            // InternalMazeDsl.g:5424:1: ( ( rule__LootItemConfig__NameAssignment_1 ) )
            // InternalMazeDsl.g:5425:2: ( rule__LootItemConfig__NameAssignment_1 )
            {
             before(grammarAccess.getLootItemConfigAccess().getNameAssignment_1()); 
            // InternalMazeDsl.g:5426:2: ( rule__LootItemConfig__NameAssignment_1 )
            // InternalMazeDsl.g:5426:3: rule__LootItemConfig__NameAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__LootItemConfig__NameAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getLootItemConfigAccess().getNameAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootItemConfig__Group__1__Impl"


    // $ANTLR start "rule__LootItemConfig__Group__2"
    // InternalMazeDsl.g:5434:1: rule__LootItemConfig__Group__2 : rule__LootItemConfig__Group__2__Impl rule__LootItemConfig__Group__3 ;
    public final void rule__LootItemConfig__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5438:1: ( rule__LootItemConfig__Group__2__Impl rule__LootItemConfig__Group__3 )
            // InternalMazeDsl.g:5439:2: rule__LootItemConfig__Group__2__Impl rule__LootItemConfig__Group__3
            {
            pushFollow(FOLLOW_20);
            rule__LootItemConfig__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LootItemConfig__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootItemConfig__Group__2"


    // $ANTLR start "rule__LootItemConfig__Group__2__Impl"
    // InternalMazeDsl.g:5446:1: rule__LootItemConfig__Group__2__Impl : ( '{' ) ;
    public final void rule__LootItemConfig__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5450:1: ( ( '{' ) )
            // InternalMazeDsl.g:5451:1: ( '{' )
            {
            // InternalMazeDsl.g:5451:1: ( '{' )
            // InternalMazeDsl.g:5452:2: '{'
            {
             before(grammarAccess.getLootItemConfigAccess().getLeftCurlyBracketKeyword_2()); 
            match(input,32,FOLLOW_2); 
             after(grammarAccess.getLootItemConfigAccess().getLeftCurlyBracketKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootItemConfig__Group__2__Impl"


    // $ANTLR start "rule__LootItemConfig__Group__3"
    // InternalMazeDsl.g:5461:1: rule__LootItemConfig__Group__3 : rule__LootItemConfig__Group__3__Impl rule__LootItemConfig__Group__4 ;
    public final void rule__LootItemConfig__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5465:1: ( rule__LootItemConfig__Group__3__Impl rule__LootItemConfig__Group__4 )
            // InternalMazeDsl.g:5466:2: rule__LootItemConfig__Group__3__Impl rule__LootItemConfig__Group__4
            {
            pushFollow(FOLLOW_47);
            rule__LootItemConfig__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LootItemConfig__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootItemConfig__Group__3"


    // $ANTLR start "rule__LootItemConfig__Group__3__Impl"
    // InternalMazeDsl.g:5473:1: rule__LootItemConfig__Group__3__Impl : ( 'type' ) ;
    public final void rule__LootItemConfig__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5477:1: ( ( 'type' ) )
            // InternalMazeDsl.g:5478:1: ( 'type' )
            {
            // InternalMazeDsl.g:5478:1: ( 'type' )
            // InternalMazeDsl.g:5479:2: 'type'
            {
             before(grammarAccess.getLootItemConfigAccess().getTypeKeyword_3()); 
            match(input,44,FOLLOW_2); 
             after(grammarAccess.getLootItemConfigAccess().getTypeKeyword_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootItemConfig__Group__3__Impl"


    // $ANTLR start "rule__LootItemConfig__Group__4"
    // InternalMazeDsl.g:5488:1: rule__LootItemConfig__Group__4 : rule__LootItemConfig__Group__4__Impl rule__LootItemConfig__Group__5 ;
    public final void rule__LootItemConfig__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5492:1: ( rule__LootItemConfig__Group__4__Impl rule__LootItemConfig__Group__5 )
            // InternalMazeDsl.g:5493:2: rule__LootItemConfig__Group__4__Impl rule__LootItemConfig__Group__5
            {
            pushFollow(FOLLOW_48);
            rule__LootItemConfig__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LootItemConfig__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootItemConfig__Group__4"


    // $ANTLR start "rule__LootItemConfig__Group__4__Impl"
    // InternalMazeDsl.g:5500:1: rule__LootItemConfig__Group__4__Impl : ( ( rule__LootItemConfig__TypeAssignment_4 ) ) ;
    public final void rule__LootItemConfig__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5504:1: ( ( ( rule__LootItemConfig__TypeAssignment_4 ) ) )
            // InternalMazeDsl.g:5505:1: ( ( rule__LootItemConfig__TypeAssignment_4 ) )
            {
            // InternalMazeDsl.g:5505:1: ( ( rule__LootItemConfig__TypeAssignment_4 ) )
            // InternalMazeDsl.g:5506:2: ( rule__LootItemConfig__TypeAssignment_4 )
            {
             before(grammarAccess.getLootItemConfigAccess().getTypeAssignment_4()); 
            // InternalMazeDsl.g:5507:2: ( rule__LootItemConfig__TypeAssignment_4 )
            // InternalMazeDsl.g:5507:3: rule__LootItemConfig__TypeAssignment_4
            {
            pushFollow(FOLLOW_2);
            rule__LootItemConfig__TypeAssignment_4();

            state._fsp--;


            }

             after(grammarAccess.getLootItemConfigAccess().getTypeAssignment_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootItemConfig__Group__4__Impl"


    // $ANTLR start "rule__LootItemConfig__Group__5"
    // InternalMazeDsl.g:5515:1: rule__LootItemConfig__Group__5 : rule__LootItemConfig__Group__5__Impl rule__LootItemConfig__Group__6 ;
    public final void rule__LootItemConfig__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5519:1: ( rule__LootItemConfig__Group__5__Impl rule__LootItemConfig__Group__6 )
            // InternalMazeDsl.g:5520:2: rule__LootItemConfig__Group__5__Impl rule__LootItemConfig__Group__6
            {
            pushFollow(FOLLOW_26);
            rule__LootItemConfig__Group__5__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LootItemConfig__Group__6();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootItemConfig__Group__5"


    // $ANTLR start "rule__LootItemConfig__Group__5__Impl"
    // InternalMazeDsl.g:5527:1: rule__LootItemConfig__Group__5__Impl : ( 'value' ) ;
    public final void rule__LootItemConfig__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5531:1: ( ( 'value' ) )
            // InternalMazeDsl.g:5532:1: ( 'value' )
            {
            // InternalMazeDsl.g:5532:1: ( 'value' )
            // InternalMazeDsl.g:5533:2: 'value'
            {
             before(grammarAccess.getLootItemConfigAccess().getValueKeyword_5()); 
            match(input,81,FOLLOW_2); 
             after(grammarAccess.getLootItemConfigAccess().getValueKeyword_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootItemConfig__Group__5__Impl"


    // $ANTLR start "rule__LootItemConfig__Group__6"
    // InternalMazeDsl.g:5542:1: rule__LootItemConfig__Group__6 : rule__LootItemConfig__Group__6__Impl rule__LootItemConfig__Group__7 ;
    public final void rule__LootItemConfig__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5546:1: ( rule__LootItemConfig__Group__6__Impl rule__LootItemConfig__Group__7 )
            // InternalMazeDsl.g:5547:2: rule__LootItemConfig__Group__6__Impl rule__LootItemConfig__Group__7
            {
            pushFollow(FOLLOW_49);
            rule__LootItemConfig__Group__6__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LootItemConfig__Group__7();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootItemConfig__Group__6"


    // $ANTLR start "rule__LootItemConfig__Group__6__Impl"
    // InternalMazeDsl.g:5554:1: rule__LootItemConfig__Group__6__Impl : ( ( rule__LootItemConfig__ValueAssignment_6 ) ) ;
    public final void rule__LootItemConfig__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5558:1: ( ( ( rule__LootItemConfig__ValueAssignment_6 ) ) )
            // InternalMazeDsl.g:5559:1: ( ( rule__LootItemConfig__ValueAssignment_6 ) )
            {
            // InternalMazeDsl.g:5559:1: ( ( rule__LootItemConfig__ValueAssignment_6 ) )
            // InternalMazeDsl.g:5560:2: ( rule__LootItemConfig__ValueAssignment_6 )
            {
             before(grammarAccess.getLootItemConfigAccess().getValueAssignment_6()); 
            // InternalMazeDsl.g:5561:2: ( rule__LootItemConfig__ValueAssignment_6 )
            // InternalMazeDsl.g:5561:3: rule__LootItemConfig__ValueAssignment_6
            {
            pushFollow(FOLLOW_2);
            rule__LootItemConfig__ValueAssignment_6();

            state._fsp--;


            }

             after(grammarAccess.getLootItemConfigAccess().getValueAssignment_6()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootItemConfig__Group__6__Impl"


    // $ANTLR start "rule__LootItemConfig__Group__7"
    // InternalMazeDsl.g:5569:1: rule__LootItemConfig__Group__7 : rule__LootItemConfig__Group__7__Impl rule__LootItemConfig__Group__8 ;
    public final void rule__LootItemConfig__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5573:1: ( rule__LootItemConfig__Group__7__Impl rule__LootItemConfig__Group__8 )
            // InternalMazeDsl.g:5574:2: rule__LootItemConfig__Group__7__Impl rule__LootItemConfig__Group__8
            {
            pushFollow(FOLLOW_49);
            rule__LootItemConfig__Group__7__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LootItemConfig__Group__8();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootItemConfig__Group__7"


    // $ANTLR start "rule__LootItemConfig__Group__7__Impl"
    // InternalMazeDsl.g:5581:1: rule__LootItemConfig__Group__7__Impl : ( ( rule__LootItemConfig__Group_7__0 )? ) ;
    public final void rule__LootItemConfig__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5585:1: ( ( ( rule__LootItemConfig__Group_7__0 )? ) )
            // InternalMazeDsl.g:5586:1: ( ( rule__LootItemConfig__Group_7__0 )? )
            {
            // InternalMazeDsl.g:5586:1: ( ( rule__LootItemConfig__Group_7__0 )? )
            // InternalMazeDsl.g:5587:2: ( rule__LootItemConfig__Group_7__0 )?
            {
             before(grammarAccess.getLootItemConfigAccess().getGroup_7()); 
            // InternalMazeDsl.g:5588:2: ( rule__LootItemConfig__Group_7__0 )?
            int alt48=2;
            int LA48_0 = input.LA(1);

            if ( (LA48_0==82) ) {
                alt48=1;
            }
            switch (alt48) {
                case 1 :
                    // InternalMazeDsl.g:5588:3: rule__LootItemConfig__Group_7__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__LootItemConfig__Group_7__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getLootItemConfigAccess().getGroup_7()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootItemConfig__Group__7__Impl"


    // $ANTLR start "rule__LootItemConfig__Group__8"
    // InternalMazeDsl.g:5596:1: rule__LootItemConfig__Group__8 : rule__LootItemConfig__Group__8__Impl ;
    public final void rule__LootItemConfig__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5600:1: ( rule__LootItemConfig__Group__8__Impl )
            // InternalMazeDsl.g:5601:2: rule__LootItemConfig__Group__8__Impl
            {
            pushFollow(FOLLOW_2);
            rule__LootItemConfig__Group__8__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootItemConfig__Group__8"


    // $ANTLR start "rule__LootItemConfig__Group__8__Impl"
    // InternalMazeDsl.g:5607:1: rule__LootItemConfig__Group__8__Impl : ( '}' ) ;
    public final void rule__LootItemConfig__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5611:1: ( ( '}' ) )
            // InternalMazeDsl.g:5612:1: ( '}' )
            {
            // InternalMazeDsl.g:5612:1: ( '}' )
            // InternalMazeDsl.g:5613:2: '}'
            {
             before(grammarAccess.getLootItemConfigAccess().getRightCurlyBracketKeyword_8()); 
            match(input,33,FOLLOW_2); 
             after(grammarAccess.getLootItemConfigAccess().getRightCurlyBracketKeyword_8()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootItemConfig__Group__8__Impl"


    // $ANTLR start "rule__LootItemConfig__Group_7__0"
    // InternalMazeDsl.g:5623:1: rule__LootItemConfig__Group_7__0 : rule__LootItemConfig__Group_7__0__Impl rule__LootItemConfig__Group_7__1 ;
    public final void rule__LootItemConfig__Group_7__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5627:1: ( rule__LootItemConfig__Group_7__0__Impl rule__LootItemConfig__Group_7__1 )
            // InternalMazeDsl.g:5628:2: rule__LootItemConfig__Group_7__0__Impl rule__LootItemConfig__Group_7__1
            {
            pushFollow(FOLLOW_26);
            rule__LootItemConfig__Group_7__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LootItemConfig__Group_7__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootItemConfig__Group_7__0"


    // $ANTLR start "rule__LootItemConfig__Group_7__0__Impl"
    // InternalMazeDsl.g:5635:1: rule__LootItemConfig__Group_7__0__Impl : ( 'weight' ) ;
    public final void rule__LootItemConfig__Group_7__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5639:1: ( ( 'weight' ) )
            // InternalMazeDsl.g:5640:1: ( 'weight' )
            {
            // InternalMazeDsl.g:5640:1: ( 'weight' )
            // InternalMazeDsl.g:5641:2: 'weight'
            {
             before(grammarAccess.getLootItemConfigAccess().getWeightKeyword_7_0()); 
            match(input,82,FOLLOW_2); 
             after(grammarAccess.getLootItemConfigAccess().getWeightKeyword_7_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootItemConfig__Group_7__0__Impl"


    // $ANTLR start "rule__LootItemConfig__Group_7__1"
    // InternalMazeDsl.g:5650:1: rule__LootItemConfig__Group_7__1 : rule__LootItemConfig__Group_7__1__Impl ;
    public final void rule__LootItemConfig__Group_7__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5654:1: ( rule__LootItemConfig__Group_7__1__Impl )
            // InternalMazeDsl.g:5655:2: rule__LootItemConfig__Group_7__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__LootItemConfig__Group_7__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootItemConfig__Group_7__1"


    // $ANTLR start "rule__LootItemConfig__Group_7__1__Impl"
    // InternalMazeDsl.g:5661:1: rule__LootItemConfig__Group_7__1__Impl : ( ( rule__LootItemConfig__WeightAssignment_7_1 ) ) ;
    public final void rule__LootItemConfig__Group_7__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5665:1: ( ( ( rule__LootItemConfig__WeightAssignment_7_1 ) ) )
            // InternalMazeDsl.g:5666:1: ( ( rule__LootItemConfig__WeightAssignment_7_1 ) )
            {
            // InternalMazeDsl.g:5666:1: ( ( rule__LootItemConfig__WeightAssignment_7_1 ) )
            // InternalMazeDsl.g:5667:2: ( rule__LootItemConfig__WeightAssignment_7_1 )
            {
             before(grammarAccess.getLootItemConfigAccess().getWeightAssignment_7_1()); 
            // InternalMazeDsl.g:5668:2: ( rule__LootItemConfig__WeightAssignment_7_1 )
            // InternalMazeDsl.g:5668:3: rule__LootItemConfig__WeightAssignment_7_1
            {
            pushFollow(FOLLOW_2);
            rule__LootItemConfig__WeightAssignment_7_1();

            state._fsp--;


            }

             after(grammarAccess.getLootItemConfigAccess().getWeightAssignment_7_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootItemConfig__Group_7__1__Impl"


    // $ANTLR start "rule__LevelConfig__Group__0"
    // InternalMazeDsl.g:5677:1: rule__LevelConfig__Group__0 : rule__LevelConfig__Group__0__Impl rule__LevelConfig__Group__1 ;
    public final void rule__LevelConfig__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5681:1: ( rule__LevelConfig__Group__0__Impl rule__LevelConfig__Group__1 )
            // InternalMazeDsl.g:5682:2: rule__LevelConfig__Group__0__Impl rule__LevelConfig__Group__1
            {
            pushFollow(FOLLOW_26);
            rule__LevelConfig__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LevelConfig__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__Group__0"


    // $ANTLR start "rule__LevelConfig__Group__0__Impl"
    // InternalMazeDsl.g:5689:1: rule__LevelConfig__Group__0__Impl : ( 'level' ) ;
    public final void rule__LevelConfig__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5693:1: ( ( 'level' ) )
            // InternalMazeDsl.g:5694:1: ( 'level' )
            {
            // InternalMazeDsl.g:5694:1: ( 'level' )
            // InternalMazeDsl.g:5695:2: 'level'
            {
             before(grammarAccess.getLevelConfigAccess().getLevelKeyword_0()); 
            match(input,36,FOLLOW_2); 
             after(grammarAccess.getLevelConfigAccess().getLevelKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__Group__0__Impl"


    // $ANTLR start "rule__LevelConfig__Group__1"
    // InternalMazeDsl.g:5704:1: rule__LevelConfig__Group__1 : rule__LevelConfig__Group__1__Impl rule__LevelConfig__Group__2 ;
    public final void rule__LevelConfig__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5708:1: ( rule__LevelConfig__Group__1__Impl rule__LevelConfig__Group__2 )
            // InternalMazeDsl.g:5709:2: rule__LevelConfig__Group__1__Impl rule__LevelConfig__Group__2
            {
            pushFollow(FOLLOW_11);
            rule__LevelConfig__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LevelConfig__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__Group__1"


    // $ANTLR start "rule__LevelConfig__Group__1__Impl"
    // InternalMazeDsl.g:5716:1: rule__LevelConfig__Group__1__Impl : ( ( rule__LevelConfig__NumberAssignment_1 ) ) ;
    public final void rule__LevelConfig__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5720:1: ( ( ( rule__LevelConfig__NumberAssignment_1 ) ) )
            // InternalMazeDsl.g:5721:1: ( ( rule__LevelConfig__NumberAssignment_1 ) )
            {
            // InternalMazeDsl.g:5721:1: ( ( rule__LevelConfig__NumberAssignment_1 ) )
            // InternalMazeDsl.g:5722:2: ( rule__LevelConfig__NumberAssignment_1 )
            {
             before(grammarAccess.getLevelConfigAccess().getNumberAssignment_1()); 
            // InternalMazeDsl.g:5723:2: ( rule__LevelConfig__NumberAssignment_1 )
            // InternalMazeDsl.g:5723:3: rule__LevelConfig__NumberAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__LevelConfig__NumberAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getLevelConfigAccess().getNumberAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__Group__1__Impl"


    // $ANTLR start "rule__LevelConfig__Group__2"
    // InternalMazeDsl.g:5731:1: rule__LevelConfig__Group__2 : rule__LevelConfig__Group__2__Impl rule__LevelConfig__Group__3 ;
    public final void rule__LevelConfig__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5735:1: ( rule__LevelConfig__Group__2__Impl rule__LevelConfig__Group__3 )
            // InternalMazeDsl.g:5736:2: rule__LevelConfig__Group__2__Impl rule__LevelConfig__Group__3
            {
            pushFollow(FOLLOW_4);
            rule__LevelConfig__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LevelConfig__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__Group__2"


    // $ANTLR start "rule__LevelConfig__Group__2__Impl"
    // InternalMazeDsl.g:5743:1: rule__LevelConfig__Group__2__Impl : ( ( rule__LevelConfig__NameAssignment_2 ) ) ;
    public final void rule__LevelConfig__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5747:1: ( ( ( rule__LevelConfig__NameAssignment_2 ) ) )
            // InternalMazeDsl.g:5748:1: ( ( rule__LevelConfig__NameAssignment_2 ) )
            {
            // InternalMazeDsl.g:5748:1: ( ( rule__LevelConfig__NameAssignment_2 ) )
            // InternalMazeDsl.g:5749:2: ( rule__LevelConfig__NameAssignment_2 )
            {
             before(grammarAccess.getLevelConfigAccess().getNameAssignment_2()); 
            // InternalMazeDsl.g:5750:2: ( rule__LevelConfig__NameAssignment_2 )
            // InternalMazeDsl.g:5750:3: rule__LevelConfig__NameAssignment_2
            {
            pushFollow(FOLLOW_2);
            rule__LevelConfig__NameAssignment_2();

            state._fsp--;


            }

             after(grammarAccess.getLevelConfigAccess().getNameAssignment_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__Group__2__Impl"


    // $ANTLR start "rule__LevelConfig__Group__3"
    // InternalMazeDsl.g:5758:1: rule__LevelConfig__Group__3 : rule__LevelConfig__Group__3__Impl rule__LevelConfig__Group__4 ;
    public final void rule__LevelConfig__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5762:1: ( rule__LevelConfig__Group__3__Impl rule__LevelConfig__Group__4 )
            // InternalMazeDsl.g:5763:2: rule__LevelConfig__Group__3__Impl rule__LevelConfig__Group__4
            {
            pushFollow(FOLLOW_50);
            rule__LevelConfig__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LevelConfig__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__Group__3"


    // $ANTLR start "rule__LevelConfig__Group__3__Impl"
    // InternalMazeDsl.g:5770:1: rule__LevelConfig__Group__3__Impl : ( '{' ) ;
    public final void rule__LevelConfig__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5774:1: ( ( '{' ) )
            // InternalMazeDsl.g:5775:1: ( '{' )
            {
            // InternalMazeDsl.g:5775:1: ( '{' )
            // InternalMazeDsl.g:5776:2: '{'
            {
             before(grammarAccess.getLevelConfigAccess().getLeftCurlyBracketKeyword_3()); 
            match(input,32,FOLLOW_2); 
             after(grammarAccess.getLevelConfigAccess().getLeftCurlyBracketKeyword_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__Group__3__Impl"


    // $ANTLR start "rule__LevelConfig__Group__4"
    // InternalMazeDsl.g:5785:1: rule__LevelConfig__Group__4 : rule__LevelConfig__Group__4__Impl rule__LevelConfig__Group__5 ;
    public final void rule__LevelConfig__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5789:1: ( rule__LevelConfig__Group__4__Impl rule__LevelConfig__Group__5 )
            // InternalMazeDsl.g:5790:2: rule__LevelConfig__Group__4__Impl rule__LevelConfig__Group__5
            {
            pushFollow(FOLLOW_50);
            rule__LevelConfig__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LevelConfig__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__Group__4"


    // $ANTLR start "rule__LevelConfig__Group__4__Impl"
    // InternalMazeDsl.g:5797:1: rule__LevelConfig__Group__4__Impl : ( ( rule__LevelConfig__Group_4__0 )? ) ;
    public final void rule__LevelConfig__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5801:1: ( ( ( rule__LevelConfig__Group_4__0 )? ) )
            // InternalMazeDsl.g:5802:1: ( ( rule__LevelConfig__Group_4__0 )? )
            {
            // InternalMazeDsl.g:5802:1: ( ( rule__LevelConfig__Group_4__0 )? )
            // InternalMazeDsl.g:5803:2: ( rule__LevelConfig__Group_4__0 )?
            {
             before(grammarAccess.getLevelConfigAccess().getGroup_4()); 
            // InternalMazeDsl.g:5804:2: ( rule__LevelConfig__Group_4__0 )?
            int alt49=2;
            int LA49_0 = input.LA(1);

            if ( (LA49_0==83) ) {
                alt49=1;
            }
            switch (alt49) {
                case 1 :
                    // InternalMazeDsl.g:5804:3: rule__LevelConfig__Group_4__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__LevelConfig__Group_4__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getLevelConfigAccess().getGroup_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__Group__4__Impl"


    // $ANTLR start "rule__LevelConfig__Group__5"
    // InternalMazeDsl.g:5812:1: rule__LevelConfig__Group__5 : rule__LevelConfig__Group__5__Impl rule__LevelConfig__Group__6 ;
    public final void rule__LevelConfig__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5816:1: ( rule__LevelConfig__Group__5__Impl rule__LevelConfig__Group__6 )
            // InternalMazeDsl.g:5817:2: rule__LevelConfig__Group__5__Impl rule__LevelConfig__Group__6
            {
            pushFollow(FOLLOW_50);
            rule__LevelConfig__Group__5__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LevelConfig__Group__6();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__Group__5"


    // $ANTLR start "rule__LevelConfig__Group__5__Impl"
    // InternalMazeDsl.g:5824:1: rule__LevelConfig__Group__5__Impl : ( ( rule__LevelConfig__Group_5__0 )? ) ;
    public final void rule__LevelConfig__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5828:1: ( ( ( rule__LevelConfig__Group_5__0 )? ) )
            // InternalMazeDsl.g:5829:1: ( ( rule__LevelConfig__Group_5__0 )? )
            {
            // InternalMazeDsl.g:5829:1: ( ( rule__LevelConfig__Group_5__0 )? )
            // InternalMazeDsl.g:5830:2: ( rule__LevelConfig__Group_5__0 )?
            {
             before(grammarAccess.getLevelConfigAccess().getGroup_5()); 
            // InternalMazeDsl.g:5831:2: ( rule__LevelConfig__Group_5__0 )?
            int alt50=2;
            int LA50_0 = input.LA(1);

            if ( (LA50_0==84) ) {
                alt50=1;
            }
            switch (alt50) {
                case 1 :
                    // InternalMazeDsl.g:5831:3: rule__LevelConfig__Group_5__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__LevelConfig__Group_5__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getLevelConfigAccess().getGroup_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__Group__5__Impl"


    // $ANTLR start "rule__LevelConfig__Group__6"
    // InternalMazeDsl.g:5839:1: rule__LevelConfig__Group__6 : rule__LevelConfig__Group__6__Impl rule__LevelConfig__Group__7 ;
    public final void rule__LevelConfig__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5843:1: ( rule__LevelConfig__Group__6__Impl rule__LevelConfig__Group__7 )
            // InternalMazeDsl.g:5844:2: rule__LevelConfig__Group__6__Impl rule__LevelConfig__Group__7
            {
            pushFollow(FOLLOW_50);
            rule__LevelConfig__Group__6__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LevelConfig__Group__7();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__Group__6"


    // $ANTLR start "rule__LevelConfig__Group__6__Impl"
    // InternalMazeDsl.g:5851:1: rule__LevelConfig__Group__6__Impl : ( ( rule__LevelConfig__Group_6__0 )? ) ;
    public final void rule__LevelConfig__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5855:1: ( ( ( rule__LevelConfig__Group_6__0 )? ) )
            // InternalMazeDsl.g:5856:1: ( ( rule__LevelConfig__Group_6__0 )? )
            {
            // InternalMazeDsl.g:5856:1: ( ( rule__LevelConfig__Group_6__0 )? )
            // InternalMazeDsl.g:5857:2: ( rule__LevelConfig__Group_6__0 )?
            {
             before(grammarAccess.getLevelConfigAccess().getGroup_6()); 
            // InternalMazeDsl.g:5858:2: ( rule__LevelConfig__Group_6__0 )?
            int alt51=2;
            int LA51_0 = input.LA(1);

            if ( (LA51_0==85) ) {
                alt51=1;
            }
            switch (alt51) {
                case 1 :
                    // InternalMazeDsl.g:5858:3: rule__LevelConfig__Group_6__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__LevelConfig__Group_6__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getLevelConfigAccess().getGroup_6()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__Group__6__Impl"


    // $ANTLR start "rule__LevelConfig__Group__7"
    // InternalMazeDsl.g:5866:1: rule__LevelConfig__Group__7 : rule__LevelConfig__Group__7__Impl rule__LevelConfig__Group__8 ;
    public final void rule__LevelConfig__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5870:1: ( rule__LevelConfig__Group__7__Impl rule__LevelConfig__Group__8 )
            // InternalMazeDsl.g:5871:2: rule__LevelConfig__Group__7__Impl rule__LevelConfig__Group__8
            {
            pushFollow(FOLLOW_50);
            rule__LevelConfig__Group__7__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LevelConfig__Group__8();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__Group__7"


    // $ANTLR start "rule__LevelConfig__Group__7__Impl"
    // InternalMazeDsl.g:5878:1: rule__LevelConfig__Group__7__Impl : ( ( rule__LevelConfig__Group_7__0 )? ) ;
    public final void rule__LevelConfig__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5882:1: ( ( ( rule__LevelConfig__Group_7__0 )? ) )
            // InternalMazeDsl.g:5883:1: ( ( rule__LevelConfig__Group_7__0 )? )
            {
            // InternalMazeDsl.g:5883:1: ( ( rule__LevelConfig__Group_7__0 )? )
            // InternalMazeDsl.g:5884:2: ( rule__LevelConfig__Group_7__0 )?
            {
             before(grammarAccess.getLevelConfigAccess().getGroup_7()); 
            // InternalMazeDsl.g:5885:2: ( rule__LevelConfig__Group_7__0 )?
            int alt52=2;
            int LA52_0 = input.LA(1);

            if ( (LA52_0==86) ) {
                alt52=1;
            }
            switch (alt52) {
                case 1 :
                    // InternalMazeDsl.g:5885:3: rule__LevelConfig__Group_7__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__LevelConfig__Group_7__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getLevelConfigAccess().getGroup_7()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__Group__7__Impl"


    // $ANTLR start "rule__LevelConfig__Group__8"
    // InternalMazeDsl.g:5893:1: rule__LevelConfig__Group__8 : rule__LevelConfig__Group__8__Impl rule__LevelConfig__Group__9 ;
    public final void rule__LevelConfig__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5897:1: ( rule__LevelConfig__Group__8__Impl rule__LevelConfig__Group__9 )
            // InternalMazeDsl.g:5898:2: rule__LevelConfig__Group__8__Impl rule__LevelConfig__Group__9
            {
            pushFollow(FOLLOW_50);
            rule__LevelConfig__Group__8__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LevelConfig__Group__9();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__Group__8"


    // $ANTLR start "rule__LevelConfig__Group__8__Impl"
    // InternalMazeDsl.g:5905:1: rule__LevelConfig__Group__8__Impl : ( ( rule__LevelConfig__Group_8__0 )? ) ;
    public final void rule__LevelConfig__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5909:1: ( ( ( rule__LevelConfig__Group_8__0 )? ) )
            // InternalMazeDsl.g:5910:1: ( ( rule__LevelConfig__Group_8__0 )? )
            {
            // InternalMazeDsl.g:5910:1: ( ( rule__LevelConfig__Group_8__0 )? )
            // InternalMazeDsl.g:5911:2: ( rule__LevelConfig__Group_8__0 )?
            {
             before(grammarAccess.getLevelConfigAccess().getGroup_8()); 
            // InternalMazeDsl.g:5912:2: ( rule__LevelConfig__Group_8__0 )?
            int alt53=2;
            int LA53_0 = input.LA(1);

            if ( (LA53_0==87) ) {
                alt53=1;
            }
            switch (alt53) {
                case 1 :
                    // InternalMazeDsl.g:5912:3: rule__LevelConfig__Group_8__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__LevelConfig__Group_8__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getLevelConfigAccess().getGroup_8()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__Group__8__Impl"


    // $ANTLR start "rule__LevelConfig__Group__9"
    // InternalMazeDsl.g:5920:1: rule__LevelConfig__Group__9 : rule__LevelConfig__Group__9__Impl rule__LevelConfig__Group__10 ;
    public final void rule__LevelConfig__Group__9() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5924:1: ( rule__LevelConfig__Group__9__Impl rule__LevelConfig__Group__10 )
            // InternalMazeDsl.g:5925:2: rule__LevelConfig__Group__9__Impl rule__LevelConfig__Group__10
            {
            pushFollow(FOLLOW_50);
            rule__LevelConfig__Group__9__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LevelConfig__Group__10();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__Group__9"


    // $ANTLR start "rule__LevelConfig__Group__9__Impl"
    // InternalMazeDsl.g:5932:1: rule__LevelConfig__Group__9__Impl : ( ( rule__LevelConfig__ZonesAssignment_9 )* ) ;
    public final void rule__LevelConfig__Group__9__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5936:1: ( ( ( rule__LevelConfig__ZonesAssignment_9 )* ) )
            // InternalMazeDsl.g:5937:1: ( ( rule__LevelConfig__ZonesAssignment_9 )* )
            {
            // InternalMazeDsl.g:5937:1: ( ( rule__LevelConfig__ZonesAssignment_9 )* )
            // InternalMazeDsl.g:5938:2: ( rule__LevelConfig__ZonesAssignment_9 )*
            {
             before(grammarAccess.getLevelConfigAccess().getZonesAssignment_9()); 
            // InternalMazeDsl.g:5939:2: ( rule__LevelConfig__ZonesAssignment_9 )*
            loop54:
            do {
                int alt54=2;
                int LA54_0 = input.LA(1);

                if ( (LA54_0==88) ) {
                    alt54=1;
                }


                switch (alt54) {
            	case 1 :
            	    // InternalMazeDsl.g:5939:3: rule__LevelConfig__ZonesAssignment_9
            	    {
            	    pushFollow(FOLLOW_51);
            	    rule__LevelConfig__ZonesAssignment_9();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop54;
                }
            } while (true);

             after(grammarAccess.getLevelConfigAccess().getZonesAssignment_9()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__Group__9__Impl"


    // $ANTLR start "rule__LevelConfig__Group__10"
    // InternalMazeDsl.g:5947:1: rule__LevelConfig__Group__10 : rule__LevelConfig__Group__10__Impl rule__LevelConfig__Group__11 ;
    public final void rule__LevelConfig__Group__10() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5951:1: ( rule__LevelConfig__Group__10__Impl rule__LevelConfig__Group__11 )
            // InternalMazeDsl.g:5952:2: rule__LevelConfig__Group__10__Impl rule__LevelConfig__Group__11
            {
            pushFollow(FOLLOW_50);
            rule__LevelConfig__Group__10__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LevelConfig__Group__11();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__Group__10"


    // $ANTLR start "rule__LevelConfig__Group__10__Impl"
    // InternalMazeDsl.g:5959:1: rule__LevelConfig__Group__10__Impl : ( ( rule__LevelConfig__SergeantAssignment_10 )? ) ;
    public final void rule__LevelConfig__Group__10__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5963:1: ( ( ( rule__LevelConfig__SergeantAssignment_10 )? ) )
            // InternalMazeDsl.g:5964:1: ( ( rule__LevelConfig__SergeantAssignment_10 )? )
            {
            // InternalMazeDsl.g:5964:1: ( ( rule__LevelConfig__SergeantAssignment_10 )? )
            // InternalMazeDsl.g:5965:2: ( rule__LevelConfig__SergeantAssignment_10 )?
            {
             before(grammarAccess.getLevelConfigAccess().getSergeantAssignment_10()); 
            // InternalMazeDsl.g:5966:2: ( rule__LevelConfig__SergeantAssignment_10 )?
            int alt55=2;
            int LA55_0 = input.LA(1);

            if ( (LA55_0==91) ) {
                alt55=1;
            }
            switch (alt55) {
                case 1 :
                    // InternalMazeDsl.g:5966:3: rule__LevelConfig__SergeantAssignment_10
                    {
                    pushFollow(FOLLOW_2);
                    rule__LevelConfig__SergeantAssignment_10();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getLevelConfigAccess().getSergeantAssignment_10()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__Group__10__Impl"


    // $ANTLR start "rule__LevelConfig__Group__11"
    // InternalMazeDsl.g:5974:1: rule__LevelConfig__Group__11 : rule__LevelConfig__Group__11__Impl rule__LevelConfig__Group__12 ;
    public final void rule__LevelConfig__Group__11() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5978:1: ( rule__LevelConfig__Group__11__Impl rule__LevelConfig__Group__12 )
            // InternalMazeDsl.g:5979:2: rule__LevelConfig__Group__11__Impl rule__LevelConfig__Group__12
            {
            pushFollow(FOLLOW_50);
            rule__LevelConfig__Group__11__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LevelConfig__Group__12();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__Group__11"


    // $ANTLR start "rule__LevelConfig__Group__11__Impl"
    // InternalMazeDsl.g:5986:1: rule__LevelConfig__Group__11__Impl : ( ( rule__LevelConfig__SergeantPostsAssignment_11 )* ) ;
    public final void rule__LevelConfig__Group__11__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5990:1: ( ( ( rule__LevelConfig__SergeantPostsAssignment_11 )* ) )
            // InternalMazeDsl.g:5991:1: ( ( rule__LevelConfig__SergeantPostsAssignment_11 )* )
            {
            // InternalMazeDsl.g:5991:1: ( ( rule__LevelConfig__SergeantPostsAssignment_11 )* )
            // InternalMazeDsl.g:5992:2: ( rule__LevelConfig__SergeantPostsAssignment_11 )*
            {
             before(grammarAccess.getLevelConfigAccess().getSergeantPostsAssignment_11()); 
            // InternalMazeDsl.g:5993:2: ( rule__LevelConfig__SergeantPostsAssignment_11 )*
            loop56:
            do {
                int alt56=2;
                int LA56_0 = input.LA(1);

                if ( (LA56_0==101) ) {
                    alt56=1;
                }


                switch (alt56) {
            	case 1 :
            	    // InternalMazeDsl.g:5993:3: rule__LevelConfig__SergeantPostsAssignment_11
            	    {
            	    pushFollow(FOLLOW_52);
            	    rule__LevelConfig__SergeantPostsAssignment_11();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop56;
                }
            } while (true);

             after(grammarAccess.getLevelConfigAccess().getSergeantPostsAssignment_11()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__Group__11__Impl"


    // $ANTLR start "rule__LevelConfig__Group__12"
    // InternalMazeDsl.g:6001:1: rule__LevelConfig__Group__12 : rule__LevelConfig__Group__12__Impl rule__LevelConfig__Group__13 ;
    public final void rule__LevelConfig__Group__12() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6005:1: ( rule__LevelConfig__Group__12__Impl rule__LevelConfig__Group__13 )
            // InternalMazeDsl.g:6006:2: rule__LevelConfig__Group__12__Impl rule__LevelConfig__Group__13
            {
            pushFollow(FOLLOW_50);
            rule__LevelConfig__Group__12__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LevelConfig__Group__13();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__Group__12"


    // $ANTLR start "rule__LevelConfig__Group__12__Impl"
    // InternalMazeDsl.g:6013:1: rule__LevelConfig__Group__12__Impl : ( ( rule__LevelConfig__MinesAssignment_12 )? ) ;
    public final void rule__LevelConfig__Group__12__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6017:1: ( ( ( rule__LevelConfig__MinesAssignment_12 )? ) )
            // InternalMazeDsl.g:6018:1: ( ( rule__LevelConfig__MinesAssignment_12 )? )
            {
            // InternalMazeDsl.g:6018:1: ( ( rule__LevelConfig__MinesAssignment_12 )? )
            // InternalMazeDsl.g:6019:2: ( rule__LevelConfig__MinesAssignment_12 )?
            {
             before(grammarAccess.getLevelConfigAccess().getMinesAssignment_12()); 
            // InternalMazeDsl.g:6020:2: ( rule__LevelConfig__MinesAssignment_12 )?
            int alt57=2;
            int LA57_0 = input.LA(1);

            if ( (LA57_0==102) ) {
                alt57=1;
            }
            switch (alt57) {
                case 1 :
                    // InternalMazeDsl.g:6020:3: rule__LevelConfig__MinesAssignment_12
                    {
                    pushFollow(FOLLOW_2);
                    rule__LevelConfig__MinesAssignment_12();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getLevelConfigAccess().getMinesAssignment_12()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__Group__12__Impl"


    // $ANTLR start "rule__LevelConfig__Group__13"
    // InternalMazeDsl.g:6028:1: rule__LevelConfig__Group__13 : rule__LevelConfig__Group__13__Impl rule__LevelConfig__Group__14 ;
    public final void rule__LevelConfig__Group__13() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6032:1: ( rule__LevelConfig__Group__13__Impl rule__LevelConfig__Group__14 )
            // InternalMazeDsl.g:6033:2: rule__LevelConfig__Group__13__Impl rule__LevelConfig__Group__14
            {
            pushFollow(FOLLOW_50);
            rule__LevelConfig__Group__13__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LevelConfig__Group__14();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__Group__13"


    // $ANTLR start "rule__LevelConfig__Group__13__Impl"
    // InternalMazeDsl.g:6040:1: rule__LevelConfig__Group__13__Impl : ( ( rule__LevelConfig__PortalGhostAssignment_13 )? ) ;
    public final void rule__LevelConfig__Group__13__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6044:1: ( ( ( rule__LevelConfig__PortalGhostAssignment_13 )? ) )
            // InternalMazeDsl.g:6045:1: ( ( rule__LevelConfig__PortalGhostAssignment_13 )? )
            {
            // InternalMazeDsl.g:6045:1: ( ( rule__LevelConfig__PortalGhostAssignment_13 )? )
            // InternalMazeDsl.g:6046:2: ( rule__LevelConfig__PortalGhostAssignment_13 )?
            {
             before(grammarAccess.getLevelConfigAccess().getPortalGhostAssignment_13()); 
            // InternalMazeDsl.g:6047:2: ( rule__LevelConfig__PortalGhostAssignment_13 )?
            int alt58=2;
            int LA58_0 = input.LA(1);

            if ( (LA58_0==111) ) {
                alt58=1;
            }
            switch (alt58) {
                case 1 :
                    // InternalMazeDsl.g:6047:3: rule__LevelConfig__PortalGhostAssignment_13
                    {
                    pushFollow(FOLLOW_2);
                    rule__LevelConfig__PortalGhostAssignment_13();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getLevelConfigAccess().getPortalGhostAssignment_13()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__Group__13__Impl"


    // $ANTLR start "rule__LevelConfig__Group__14"
    // InternalMazeDsl.g:6055:1: rule__LevelConfig__Group__14 : rule__LevelConfig__Group__14__Impl rule__LevelConfig__Group__15 ;
    public final void rule__LevelConfig__Group__14() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6059:1: ( rule__LevelConfig__Group__14__Impl rule__LevelConfig__Group__15 )
            // InternalMazeDsl.g:6060:2: rule__LevelConfig__Group__14__Impl rule__LevelConfig__Group__15
            {
            pushFollow(FOLLOW_50);
            rule__LevelConfig__Group__14__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LevelConfig__Group__15();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__Group__14"


    // $ANTLR start "rule__LevelConfig__Group__14__Impl"
    // InternalMazeDsl.g:6067:1: rule__LevelConfig__Group__14__Impl : ( ( rule__LevelConfig__GhostCountsAssignment_14 )* ) ;
    public final void rule__LevelConfig__Group__14__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6071:1: ( ( ( rule__LevelConfig__GhostCountsAssignment_14 )* ) )
            // InternalMazeDsl.g:6072:1: ( ( rule__LevelConfig__GhostCountsAssignment_14 )* )
            {
            // InternalMazeDsl.g:6072:1: ( ( rule__LevelConfig__GhostCountsAssignment_14 )* )
            // InternalMazeDsl.g:6073:2: ( rule__LevelConfig__GhostCountsAssignment_14 )*
            {
             before(grammarAccess.getLevelConfigAccess().getGhostCountsAssignment_14()); 
            // InternalMazeDsl.g:6074:2: ( rule__LevelConfig__GhostCountsAssignment_14 )*
            loop59:
            do {
                int alt59=2;
                int LA59_0 = input.LA(1);

                if ( (LA59_0==112) ) {
                    alt59=1;
                }


                switch (alt59) {
            	case 1 :
            	    // InternalMazeDsl.g:6074:3: rule__LevelConfig__GhostCountsAssignment_14
            	    {
            	    pushFollow(FOLLOW_53);
            	    rule__LevelConfig__GhostCountsAssignment_14();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop59;
                }
            } while (true);

             after(grammarAccess.getLevelConfigAccess().getGhostCountsAssignment_14()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__Group__14__Impl"


    // $ANTLR start "rule__LevelConfig__Group__15"
    // InternalMazeDsl.g:6082:1: rule__LevelConfig__Group__15 : rule__LevelConfig__Group__15__Impl rule__LevelConfig__Group__16 ;
    public final void rule__LevelConfig__Group__15() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6086:1: ( rule__LevelConfig__Group__15__Impl rule__LevelConfig__Group__16 )
            // InternalMazeDsl.g:6087:2: rule__LevelConfig__Group__15__Impl rule__LevelConfig__Group__16
            {
            pushFollow(FOLLOW_50);
            rule__LevelConfig__Group__15__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LevelConfig__Group__16();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__Group__15"


    // $ANTLR start "rule__LevelConfig__Group__15__Impl"
    // InternalMazeDsl.g:6094:1: rule__LevelConfig__Group__15__Impl : ( ( rule__LevelConfig__TeleportAssignment_15 )? ) ;
    public final void rule__LevelConfig__Group__15__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6098:1: ( ( ( rule__LevelConfig__TeleportAssignment_15 )? ) )
            // InternalMazeDsl.g:6099:1: ( ( rule__LevelConfig__TeleportAssignment_15 )? )
            {
            // InternalMazeDsl.g:6099:1: ( ( rule__LevelConfig__TeleportAssignment_15 )? )
            // InternalMazeDsl.g:6100:2: ( rule__LevelConfig__TeleportAssignment_15 )?
            {
             before(grammarAccess.getLevelConfigAccess().getTeleportAssignment_15()); 
            // InternalMazeDsl.g:6101:2: ( rule__LevelConfig__TeleportAssignment_15 )?
            int alt60=2;
            int LA60_0 = input.LA(1);

            if ( (LA60_0==113) ) {
                alt60=1;
            }
            switch (alt60) {
                case 1 :
                    // InternalMazeDsl.g:6101:3: rule__LevelConfig__TeleportAssignment_15
                    {
                    pushFollow(FOLLOW_2);
                    rule__LevelConfig__TeleportAssignment_15();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getLevelConfigAccess().getTeleportAssignment_15()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__Group__15__Impl"


    // $ANTLR start "rule__LevelConfig__Group__16"
    // InternalMazeDsl.g:6109:1: rule__LevelConfig__Group__16 : rule__LevelConfig__Group__16__Impl ;
    public final void rule__LevelConfig__Group__16() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6113:1: ( rule__LevelConfig__Group__16__Impl )
            // InternalMazeDsl.g:6114:2: rule__LevelConfig__Group__16__Impl
            {
            pushFollow(FOLLOW_2);
            rule__LevelConfig__Group__16__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__Group__16"


    // $ANTLR start "rule__LevelConfig__Group__16__Impl"
    // InternalMazeDsl.g:6120:1: rule__LevelConfig__Group__16__Impl : ( '}' ) ;
    public final void rule__LevelConfig__Group__16__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6124:1: ( ( '}' ) )
            // InternalMazeDsl.g:6125:1: ( '}' )
            {
            // InternalMazeDsl.g:6125:1: ( '}' )
            // InternalMazeDsl.g:6126:2: '}'
            {
             before(grammarAccess.getLevelConfigAccess().getRightCurlyBracketKeyword_16()); 
            match(input,33,FOLLOW_2); 
             after(grammarAccess.getLevelConfigAccess().getRightCurlyBracketKeyword_16()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__Group__16__Impl"


    // $ANTLR start "rule__LevelConfig__Group_4__0"
    // InternalMazeDsl.g:6136:1: rule__LevelConfig__Group_4__0 : rule__LevelConfig__Group_4__0__Impl rule__LevelConfig__Group_4__1 ;
    public final void rule__LevelConfig__Group_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6140:1: ( rule__LevelConfig__Group_4__0__Impl rule__LevelConfig__Group_4__1 )
            // InternalMazeDsl.g:6141:2: rule__LevelConfig__Group_4__0__Impl rule__LevelConfig__Group_4__1
            {
            pushFollow(FOLLOW_26);
            rule__LevelConfig__Group_4__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LevelConfig__Group_4__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__Group_4__0"


    // $ANTLR start "rule__LevelConfig__Group_4__0__Impl"
    // InternalMazeDsl.g:6148:1: rule__LevelConfig__Group_4__0__Impl : ( 'basedOn' ) ;
    public final void rule__LevelConfig__Group_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6152:1: ( ( 'basedOn' ) )
            // InternalMazeDsl.g:6153:1: ( 'basedOn' )
            {
            // InternalMazeDsl.g:6153:1: ( 'basedOn' )
            // InternalMazeDsl.g:6154:2: 'basedOn'
            {
             before(grammarAccess.getLevelConfigAccess().getBasedOnKeyword_4_0()); 
            match(input,83,FOLLOW_2); 
             after(grammarAccess.getLevelConfigAccess().getBasedOnKeyword_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__Group_4__0__Impl"


    // $ANTLR start "rule__LevelConfig__Group_4__1"
    // InternalMazeDsl.g:6163:1: rule__LevelConfig__Group_4__1 : rule__LevelConfig__Group_4__1__Impl ;
    public final void rule__LevelConfig__Group_4__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6167:1: ( rule__LevelConfig__Group_4__1__Impl )
            // InternalMazeDsl.g:6168:2: rule__LevelConfig__Group_4__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__LevelConfig__Group_4__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__Group_4__1"


    // $ANTLR start "rule__LevelConfig__Group_4__1__Impl"
    // InternalMazeDsl.g:6174:1: rule__LevelConfig__Group_4__1__Impl : ( ( rule__LevelConfig__BaseLevelAssignment_4_1 ) ) ;
    public final void rule__LevelConfig__Group_4__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6178:1: ( ( ( rule__LevelConfig__BaseLevelAssignment_4_1 ) ) )
            // InternalMazeDsl.g:6179:1: ( ( rule__LevelConfig__BaseLevelAssignment_4_1 ) )
            {
            // InternalMazeDsl.g:6179:1: ( ( rule__LevelConfig__BaseLevelAssignment_4_1 ) )
            // InternalMazeDsl.g:6180:2: ( rule__LevelConfig__BaseLevelAssignment_4_1 )
            {
             before(grammarAccess.getLevelConfigAccess().getBaseLevelAssignment_4_1()); 
            // InternalMazeDsl.g:6181:2: ( rule__LevelConfig__BaseLevelAssignment_4_1 )
            // InternalMazeDsl.g:6181:3: rule__LevelConfig__BaseLevelAssignment_4_1
            {
            pushFollow(FOLLOW_2);
            rule__LevelConfig__BaseLevelAssignment_4_1();

            state._fsp--;


            }

             after(grammarAccess.getLevelConfigAccess().getBaseLevelAssignment_4_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__Group_4__1__Impl"


    // $ANTLR start "rule__LevelConfig__Group_5__0"
    // InternalMazeDsl.g:6190:1: rule__LevelConfig__Group_5__0 : rule__LevelConfig__Group_5__0__Impl rule__LevelConfig__Group_5__1 ;
    public final void rule__LevelConfig__Group_5__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6194:1: ( rule__LevelConfig__Group_5__0__Impl rule__LevelConfig__Group_5__1 )
            // InternalMazeDsl.g:6195:2: rule__LevelConfig__Group_5__0__Impl rule__LevelConfig__Group_5__1
            {
            pushFollow(FOLLOW_17);
            rule__LevelConfig__Group_5__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LevelConfig__Group_5__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__Group_5__0"


    // $ANTLR start "rule__LevelConfig__Group_5__0__Impl"
    // InternalMazeDsl.g:6202:1: rule__LevelConfig__Group_5__0__Impl : ( 'threatCapacity' ) ;
    public final void rule__LevelConfig__Group_5__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6206:1: ( ( 'threatCapacity' ) )
            // InternalMazeDsl.g:6207:1: ( 'threatCapacity' )
            {
            // InternalMazeDsl.g:6207:1: ( 'threatCapacity' )
            // InternalMazeDsl.g:6208:2: 'threatCapacity'
            {
             before(grammarAccess.getLevelConfigAccess().getThreatCapacityKeyword_5_0()); 
            match(input,84,FOLLOW_2); 
             after(grammarAccess.getLevelConfigAccess().getThreatCapacityKeyword_5_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__Group_5__0__Impl"


    // $ANTLR start "rule__LevelConfig__Group_5__1"
    // InternalMazeDsl.g:6217:1: rule__LevelConfig__Group_5__1 : rule__LevelConfig__Group_5__1__Impl ;
    public final void rule__LevelConfig__Group_5__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6221:1: ( rule__LevelConfig__Group_5__1__Impl )
            // InternalMazeDsl.g:6222:2: rule__LevelConfig__Group_5__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__LevelConfig__Group_5__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__Group_5__1"


    // $ANTLR start "rule__LevelConfig__Group_5__1__Impl"
    // InternalMazeDsl.g:6228:1: rule__LevelConfig__Group_5__1__Impl : ( ( rule__LevelConfig__ThreatCapacityFactorAssignment_5_1 ) ) ;
    public final void rule__LevelConfig__Group_5__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6232:1: ( ( ( rule__LevelConfig__ThreatCapacityFactorAssignment_5_1 ) ) )
            // InternalMazeDsl.g:6233:1: ( ( rule__LevelConfig__ThreatCapacityFactorAssignment_5_1 ) )
            {
            // InternalMazeDsl.g:6233:1: ( ( rule__LevelConfig__ThreatCapacityFactorAssignment_5_1 ) )
            // InternalMazeDsl.g:6234:2: ( rule__LevelConfig__ThreatCapacityFactorAssignment_5_1 )
            {
             before(grammarAccess.getLevelConfigAccess().getThreatCapacityFactorAssignment_5_1()); 
            // InternalMazeDsl.g:6235:2: ( rule__LevelConfig__ThreatCapacityFactorAssignment_5_1 )
            // InternalMazeDsl.g:6235:3: rule__LevelConfig__ThreatCapacityFactorAssignment_5_1
            {
            pushFollow(FOLLOW_2);
            rule__LevelConfig__ThreatCapacityFactorAssignment_5_1();

            state._fsp--;


            }

             after(grammarAccess.getLevelConfigAccess().getThreatCapacityFactorAssignment_5_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__Group_5__1__Impl"


    // $ANTLR start "rule__LevelConfig__Group_6__0"
    // InternalMazeDsl.g:6244:1: rule__LevelConfig__Group_6__0 : rule__LevelConfig__Group_6__0__Impl rule__LevelConfig__Group_6__1 ;
    public final void rule__LevelConfig__Group_6__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6248:1: ( rule__LevelConfig__Group_6__0__Impl rule__LevelConfig__Group_6__1 )
            // InternalMazeDsl.g:6249:2: rule__LevelConfig__Group_6__0__Impl rule__LevelConfig__Group_6__1
            {
            pushFollow(FOLLOW_22);
            rule__LevelConfig__Group_6__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LevelConfig__Group_6__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__Group_6__0"


    // $ANTLR start "rule__LevelConfig__Group_6__0__Impl"
    // InternalMazeDsl.g:6256:1: rule__LevelConfig__Group_6__0__Impl : ( 'wallMaterials' ) ;
    public final void rule__LevelConfig__Group_6__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6260:1: ( ( 'wallMaterials' ) )
            // InternalMazeDsl.g:6261:1: ( 'wallMaterials' )
            {
            // InternalMazeDsl.g:6261:1: ( 'wallMaterials' )
            // InternalMazeDsl.g:6262:2: 'wallMaterials'
            {
             before(grammarAccess.getLevelConfigAccess().getWallMaterialsKeyword_6_0()); 
            match(input,85,FOLLOW_2); 
             after(grammarAccess.getLevelConfigAccess().getWallMaterialsKeyword_6_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__Group_6__0__Impl"


    // $ANTLR start "rule__LevelConfig__Group_6__1"
    // InternalMazeDsl.g:6271:1: rule__LevelConfig__Group_6__1 : rule__LevelConfig__Group_6__1__Impl ;
    public final void rule__LevelConfig__Group_6__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6275:1: ( rule__LevelConfig__Group_6__1__Impl )
            // InternalMazeDsl.g:6276:2: rule__LevelConfig__Group_6__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__LevelConfig__Group_6__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__Group_6__1"


    // $ANTLR start "rule__LevelConfig__Group_6__1__Impl"
    // InternalMazeDsl.g:6282:1: rule__LevelConfig__Group_6__1__Impl : ( ( rule__LevelConfig__ShowWallMaterialsAssignment_6_1 ) ) ;
    public final void rule__LevelConfig__Group_6__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6286:1: ( ( ( rule__LevelConfig__ShowWallMaterialsAssignment_6_1 ) ) )
            // InternalMazeDsl.g:6287:1: ( ( rule__LevelConfig__ShowWallMaterialsAssignment_6_1 ) )
            {
            // InternalMazeDsl.g:6287:1: ( ( rule__LevelConfig__ShowWallMaterialsAssignment_6_1 ) )
            // InternalMazeDsl.g:6288:2: ( rule__LevelConfig__ShowWallMaterialsAssignment_6_1 )
            {
             before(grammarAccess.getLevelConfigAccess().getShowWallMaterialsAssignment_6_1()); 
            // InternalMazeDsl.g:6289:2: ( rule__LevelConfig__ShowWallMaterialsAssignment_6_1 )
            // InternalMazeDsl.g:6289:3: rule__LevelConfig__ShowWallMaterialsAssignment_6_1
            {
            pushFollow(FOLLOW_2);
            rule__LevelConfig__ShowWallMaterialsAssignment_6_1();

            state._fsp--;


            }

             after(grammarAccess.getLevelConfigAccess().getShowWallMaterialsAssignment_6_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__Group_6__1__Impl"


    // $ANTLR start "rule__LevelConfig__Group_7__0"
    // InternalMazeDsl.g:6298:1: rule__LevelConfig__Group_7__0 : rule__LevelConfig__Group_7__0__Impl rule__LevelConfig__Group_7__1 ;
    public final void rule__LevelConfig__Group_7__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6302:1: ( rule__LevelConfig__Group_7__0__Impl rule__LevelConfig__Group_7__1 )
            // InternalMazeDsl.g:6303:2: rule__LevelConfig__Group_7__0__Impl rule__LevelConfig__Group_7__1
            {
            pushFollow(FOLLOW_22);
            rule__LevelConfig__Group_7__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LevelConfig__Group_7__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__Group_7__0"


    // $ANTLR start "rule__LevelConfig__Group_7__0__Impl"
    // InternalMazeDsl.g:6310:1: rule__LevelConfig__Group_7__0__Impl : ( 'inheritWeapons' ) ;
    public final void rule__LevelConfig__Group_7__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6314:1: ( ( 'inheritWeapons' ) )
            // InternalMazeDsl.g:6315:1: ( 'inheritWeapons' )
            {
            // InternalMazeDsl.g:6315:1: ( 'inheritWeapons' )
            // InternalMazeDsl.g:6316:2: 'inheritWeapons'
            {
             before(grammarAccess.getLevelConfigAccess().getInheritWeaponsKeyword_7_0()); 
            match(input,86,FOLLOW_2); 
             after(grammarAccess.getLevelConfigAccess().getInheritWeaponsKeyword_7_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__Group_7__0__Impl"


    // $ANTLR start "rule__LevelConfig__Group_7__1"
    // InternalMazeDsl.g:6325:1: rule__LevelConfig__Group_7__1 : rule__LevelConfig__Group_7__1__Impl ;
    public final void rule__LevelConfig__Group_7__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6329:1: ( rule__LevelConfig__Group_7__1__Impl )
            // InternalMazeDsl.g:6330:2: rule__LevelConfig__Group_7__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__LevelConfig__Group_7__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__Group_7__1"


    // $ANTLR start "rule__LevelConfig__Group_7__1__Impl"
    // InternalMazeDsl.g:6336:1: rule__LevelConfig__Group_7__1__Impl : ( ( rule__LevelConfig__InheritWeaponsAssignment_7_1 ) ) ;
    public final void rule__LevelConfig__Group_7__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6340:1: ( ( ( rule__LevelConfig__InheritWeaponsAssignment_7_1 ) ) )
            // InternalMazeDsl.g:6341:1: ( ( rule__LevelConfig__InheritWeaponsAssignment_7_1 ) )
            {
            // InternalMazeDsl.g:6341:1: ( ( rule__LevelConfig__InheritWeaponsAssignment_7_1 ) )
            // InternalMazeDsl.g:6342:2: ( rule__LevelConfig__InheritWeaponsAssignment_7_1 )
            {
             before(grammarAccess.getLevelConfigAccess().getInheritWeaponsAssignment_7_1()); 
            // InternalMazeDsl.g:6343:2: ( rule__LevelConfig__InheritWeaponsAssignment_7_1 )
            // InternalMazeDsl.g:6343:3: rule__LevelConfig__InheritWeaponsAssignment_7_1
            {
            pushFollow(FOLLOW_2);
            rule__LevelConfig__InheritWeaponsAssignment_7_1();

            state._fsp--;


            }

             after(grammarAccess.getLevelConfigAccess().getInheritWeaponsAssignment_7_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__Group_7__1__Impl"


    // $ANTLR start "rule__LevelConfig__Group_8__0"
    // InternalMazeDsl.g:6352:1: rule__LevelConfig__Group_8__0 : rule__LevelConfig__Group_8__0__Impl rule__LevelConfig__Group_8__1 ;
    public final void rule__LevelConfig__Group_8__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6356:1: ( rule__LevelConfig__Group_8__0__Impl rule__LevelConfig__Group_8__1 )
            // InternalMazeDsl.g:6357:2: rule__LevelConfig__Group_8__0__Impl rule__LevelConfig__Group_8__1
            {
            pushFollow(FOLLOW_17);
            rule__LevelConfig__Group_8__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LevelConfig__Group_8__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__Group_8__0"


    // $ANTLR start "rule__LevelConfig__Group_8__0__Impl"
    // InternalMazeDsl.g:6364:1: rule__LevelConfig__Group_8__0__Impl : ( 'cascadeDepth' ) ;
    public final void rule__LevelConfig__Group_8__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6368:1: ( ( 'cascadeDepth' ) )
            // InternalMazeDsl.g:6369:1: ( 'cascadeDepth' )
            {
            // InternalMazeDsl.g:6369:1: ( 'cascadeDepth' )
            // InternalMazeDsl.g:6370:2: 'cascadeDepth'
            {
             before(grammarAccess.getLevelConfigAccess().getCascadeDepthKeyword_8_0()); 
            match(input,87,FOLLOW_2); 
             after(grammarAccess.getLevelConfigAccess().getCascadeDepthKeyword_8_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__Group_8__0__Impl"


    // $ANTLR start "rule__LevelConfig__Group_8__1"
    // InternalMazeDsl.g:6379:1: rule__LevelConfig__Group_8__1 : rule__LevelConfig__Group_8__1__Impl ;
    public final void rule__LevelConfig__Group_8__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6383:1: ( rule__LevelConfig__Group_8__1__Impl )
            // InternalMazeDsl.g:6384:2: rule__LevelConfig__Group_8__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__LevelConfig__Group_8__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__Group_8__1"


    // $ANTLR start "rule__LevelConfig__Group_8__1__Impl"
    // InternalMazeDsl.g:6390:1: rule__LevelConfig__Group_8__1__Impl : ( ( rule__LevelConfig__CascadeDepthAssignment_8_1 ) ) ;
    public final void rule__LevelConfig__Group_8__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6394:1: ( ( ( rule__LevelConfig__CascadeDepthAssignment_8_1 ) ) )
            // InternalMazeDsl.g:6395:1: ( ( rule__LevelConfig__CascadeDepthAssignment_8_1 ) )
            {
            // InternalMazeDsl.g:6395:1: ( ( rule__LevelConfig__CascadeDepthAssignment_8_1 ) )
            // InternalMazeDsl.g:6396:2: ( rule__LevelConfig__CascadeDepthAssignment_8_1 )
            {
             before(grammarAccess.getLevelConfigAccess().getCascadeDepthAssignment_8_1()); 
            // InternalMazeDsl.g:6397:2: ( rule__LevelConfig__CascadeDepthAssignment_8_1 )
            // InternalMazeDsl.g:6397:3: rule__LevelConfig__CascadeDepthAssignment_8_1
            {
            pushFollow(FOLLOW_2);
            rule__LevelConfig__CascadeDepthAssignment_8_1();

            state._fsp--;


            }

             after(grammarAccess.getLevelConfigAccess().getCascadeDepthAssignment_8_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__Group_8__1__Impl"


    // $ANTLR start "rule__LevelZone__Group__0"
    // InternalMazeDsl.g:6406:1: rule__LevelZone__Group__0 : rule__LevelZone__Group__0__Impl rule__LevelZone__Group__1 ;
    public final void rule__LevelZone__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6410:1: ( rule__LevelZone__Group__0__Impl rule__LevelZone__Group__1 )
            // InternalMazeDsl.g:6411:2: rule__LevelZone__Group__0__Impl rule__LevelZone__Group__1
            {
            pushFollow(FOLLOW_11);
            rule__LevelZone__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LevelZone__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelZone__Group__0"


    // $ANTLR start "rule__LevelZone__Group__0__Impl"
    // InternalMazeDsl.g:6418:1: rule__LevelZone__Group__0__Impl : ( 'ring' ) ;
    public final void rule__LevelZone__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6422:1: ( ( 'ring' ) )
            // InternalMazeDsl.g:6423:1: ( 'ring' )
            {
            // InternalMazeDsl.g:6423:1: ( 'ring' )
            // InternalMazeDsl.g:6424:2: 'ring'
            {
             before(grammarAccess.getLevelZoneAccess().getRingKeyword_0()); 
            match(input,88,FOLLOW_2); 
             after(grammarAccess.getLevelZoneAccess().getRingKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelZone__Group__0__Impl"


    // $ANTLR start "rule__LevelZone__Group__1"
    // InternalMazeDsl.g:6433:1: rule__LevelZone__Group__1 : rule__LevelZone__Group__1__Impl rule__LevelZone__Group__2 ;
    public final void rule__LevelZone__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6437:1: ( rule__LevelZone__Group__1__Impl rule__LevelZone__Group__2 )
            // InternalMazeDsl.g:6438:2: rule__LevelZone__Group__1__Impl rule__LevelZone__Group__2
            {
            pushFollow(FOLLOW_54);
            rule__LevelZone__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LevelZone__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelZone__Group__1"


    // $ANTLR start "rule__LevelZone__Group__1__Impl"
    // InternalMazeDsl.g:6445:1: rule__LevelZone__Group__1__Impl : ( ( rule__LevelZone__DisplayNameAssignment_1 ) ) ;
    public final void rule__LevelZone__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6449:1: ( ( ( rule__LevelZone__DisplayNameAssignment_1 ) ) )
            // InternalMazeDsl.g:6450:1: ( ( rule__LevelZone__DisplayNameAssignment_1 ) )
            {
            // InternalMazeDsl.g:6450:1: ( ( rule__LevelZone__DisplayNameAssignment_1 ) )
            // InternalMazeDsl.g:6451:2: ( rule__LevelZone__DisplayNameAssignment_1 )
            {
             before(grammarAccess.getLevelZoneAccess().getDisplayNameAssignment_1()); 
            // InternalMazeDsl.g:6452:2: ( rule__LevelZone__DisplayNameAssignment_1 )
            // InternalMazeDsl.g:6452:3: rule__LevelZone__DisplayNameAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__LevelZone__DisplayNameAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getLevelZoneAccess().getDisplayNameAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelZone__Group__1__Impl"


    // $ANTLR start "rule__LevelZone__Group__2"
    // InternalMazeDsl.g:6460:1: rule__LevelZone__Group__2 : rule__LevelZone__Group__2__Impl rule__LevelZone__Group__3 ;
    public final void rule__LevelZone__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6464:1: ( rule__LevelZone__Group__2__Impl rule__LevelZone__Group__3 )
            // InternalMazeDsl.g:6465:2: rule__LevelZone__Group__2__Impl rule__LevelZone__Group__3
            {
            pushFollow(FOLLOW_54);
            rule__LevelZone__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LevelZone__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelZone__Group__2"


    // $ANTLR start "rule__LevelZone__Group__2__Impl"
    // InternalMazeDsl.g:6472:1: rule__LevelZone__Group__2__Impl : ( ( rule__LevelZone__Group_2__0 )? ) ;
    public final void rule__LevelZone__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6476:1: ( ( ( rule__LevelZone__Group_2__0 )? ) )
            // InternalMazeDsl.g:6477:1: ( ( rule__LevelZone__Group_2__0 )? )
            {
            // InternalMazeDsl.g:6477:1: ( ( rule__LevelZone__Group_2__0 )? )
            // InternalMazeDsl.g:6478:2: ( rule__LevelZone__Group_2__0 )?
            {
             before(grammarAccess.getLevelZoneAccess().getGroup_2()); 
            // InternalMazeDsl.g:6479:2: ( rule__LevelZone__Group_2__0 )?
            int alt61=2;
            int LA61_0 = input.LA(1);

            if ( (LA61_0==90) ) {
                alt61=1;
            }
            switch (alt61) {
                case 1 :
                    // InternalMazeDsl.g:6479:3: rule__LevelZone__Group_2__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__LevelZone__Group_2__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getLevelZoneAccess().getGroup_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelZone__Group__2__Impl"


    // $ANTLR start "rule__LevelZone__Group__3"
    // InternalMazeDsl.g:6487:1: rule__LevelZone__Group__3 : rule__LevelZone__Group__3__Impl rule__LevelZone__Group__4 ;
    public final void rule__LevelZone__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6491:1: ( rule__LevelZone__Group__3__Impl rule__LevelZone__Group__4 )
            // InternalMazeDsl.g:6492:2: rule__LevelZone__Group__3__Impl rule__LevelZone__Group__4
            {
            pushFollow(FOLLOW_3);
            rule__LevelZone__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LevelZone__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelZone__Group__3"


    // $ANTLR start "rule__LevelZone__Group__3__Impl"
    // InternalMazeDsl.g:6499:1: rule__LevelZone__Group__3__Impl : ( 'materials' ) ;
    public final void rule__LevelZone__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6503:1: ( ( 'materials' ) )
            // InternalMazeDsl.g:6504:1: ( 'materials' )
            {
            // InternalMazeDsl.g:6504:1: ( 'materials' )
            // InternalMazeDsl.g:6505:2: 'materials'
            {
             before(grammarAccess.getLevelZoneAccess().getMaterialsKeyword_3()); 
            match(input,89,FOLLOW_2); 
             after(grammarAccess.getLevelZoneAccess().getMaterialsKeyword_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelZone__Group__3__Impl"


    // $ANTLR start "rule__LevelZone__Group__4"
    // InternalMazeDsl.g:6514:1: rule__LevelZone__Group__4 : rule__LevelZone__Group__4__Impl rule__LevelZone__Group__5 ;
    public final void rule__LevelZone__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6518:1: ( rule__LevelZone__Group__4__Impl rule__LevelZone__Group__5 )
            // InternalMazeDsl.g:6519:2: rule__LevelZone__Group__4__Impl rule__LevelZone__Group__5
            {
            pushFollow(FOLLOW_39);
            rule__LevelZone__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LevelZone__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelZone__Group__4"


    // $ANTLR start "rule__LevelZone__Group__4__Impl"
    // InternalMazeDsl.g:6526:1: rule__LevelZone__Group__4__Impl : ( ( rule__LevelZone__MaterialIdsAssignment_4 ) ) ;
    public final void rule__LevelZone__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6530:1: ( ( ( rule__LevelZone__MaterialIdsAssignment_4 ) ) )
            // InternalMazeDsl.g:6531:1: ( ( rule__LevelZone__MaterialIdsAssignment_4 ) )
            {
            // InternalMazeDsl.g:6531:1: ( ( rule__LevelZone__MaterialIdsAssignment_4 ) )
            // InternalMazeDsl.g:6532:2: ( rule__LevelZone__MaterialIdsAssignment_4 )
            {
             before(grammarAccess.getLevelZoneAccess().getMaterialIdsAssignment_4()); 
            // InternalMazeDsl.g:6533:2: ( rule__LevelZone__MaterialIdsAssignment_4 )
            // InternalMazeDsl.g:6533:3: rule__LevelZone__MaterialIdsAssignment_4
            {
            pushFollow(FOLLOW_2);
            rule__LevelZone__MaterialIdsAssignment_4();

            state._fsp--;


            }

             after(grammarAccess.getLevelZoneAccess().getMaterialIdsAssignment_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelZone__Group__4__Impl"


    // $ANTLR start "rule__LevelZone__Group__5"
    // InternalMazeDsl.g:6541:1: rule__LevelZone__Group__5 : rule__LevelZone__Group__5__Impl ;
    public final void rule__LevelZone__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6545:1: ( rule__LevelZone__Group__5__Impl )
            // InternalMazeDsl.g:6546:2: rule__LevelZone__Group__5__Impl
            {
            pushFollow(FOLLOW_2);
            rule__LevelZone__Group__5__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelZone__Group__5"


    // $ANTLR start "rule__LevelZone__Group__5__Impl"
    // InternalMazeDsl.g:6552:1: rule__LevelZone__Group__5__Impl : ( ( rule__LevelZone__Group_5__0 )* ) ;
    public final void rule__LevelZone__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6556:1: ( ( ( rule__LevelZone__Group_5__0 )* ) )
            // InternalMazeDsl.g:6557:1: ( ( rule__LevelZone__Group_5__0 )* )
            {
            // InternalMazeDsl.g:6557:1: ( ( rule__LevelZone__Group_5__0 )* )
            // InternalMazeDsl.g:6558:2: ( rule__LevelZone__Group_5__0 )*
            {
             before(grammarAccess.getLevelZoneAccess().getGroup_5()); 
            // InternalMazeDsl.g:6559:2: ( rule__LevelZone__Group_5__0 )*
            loop62:
            do {
                int alt62=2;
                int LA62_0 = input.LA(1);

                if ( (LA62_0==69) ) {
                    alt62=1;
                }


                switch (alt62) {
            	case 1 :
            	    // InternalMazeDsl.g:6559:3: rule__LevelZone__Group_5__0
            	    {
            	    pushFollow(FOLLOW_36);
            	    rule__LevelZone__Group_5__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop62;
                }
            } while (true);

             after(grammarAccess.getLevelZoneAccess().getGroup_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelZone__Group__5__Impl"


    // $ANTLR start "rule__LevelZone__Group_2__0"
    // InternalMazeDsl.g:6568:1: rule__LevelZone__Group_2__0 : rule__LevelZone__Group_2__0__Impl rule__LevelZone__Group_2__1 ;
    public final void rule__LevelZone__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6572:1: ( rule__LevelZone__Group_2__0__Impl rule__LevelZone__Group_2__1 )
            // InternalMazeDsl.g:6573:2: rule__LevelZone__Group_2__0__Impl rule__LevelZone__Group_2__1
            {
            pushFollow(FOLLOW_17);
            rule__LevelZone__Group_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LevelZone__Group_2__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelZone__Group_2__0"


    // $ANTLR start "rule__LevelZone__Group_2__0__Impl"
    // InternalMazeDsl.g:6580:1: rule__LevelZone__Group_2__0__Impl : ( 'share' ) ;
    public final void rule__LevelZone__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6584:1: ( ( 'share' ) )
            // InternalMazeDsl.g:6585:1: ( 'share' )
            {
            // InternalMazeDsl.g:6585:1: ( 'share' )
            // InternalMazeDsl.g:6586:2: 'share'
            {
             before(grammarAccess.getLevelZoneAccess().getShareKeyword_2_0()); 
            match(input,90,FOLLOW_2); 
             after(grammarAccess.getLevelZoneAccess().getShareKeyword_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelZone__Group_2__0__Impl"


    // $ANTLR start "rule__LevelZone__Group_2__1"
    // InternalMazeDsl.g:6595:1: rule__LevelZone__Group_2__1 : rule__LevelZone__Group_2__1__Impl ;
    public final void rule__LevelZone__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6599:1: ( rule__LevelZone__Group_2__1__Impl )
            // InternalMazeDsl.g:6600:2: rule__LevelZone__Group_2__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__LevelZone__Group_2__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelZone__Group_2__1"


    // $ANTLR start "rule__LevelZone__Group_2__1__Impl"
    // InternalMazeDsl.g:6606:1: rule__LevelZone__Group_2__1__Impl : ( ( rule__LevelZone__BreakableShareAssignment_2_1 ) ) ;
    public final void rule__LevelZone__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6610:1: ( ( ( rule__LevelZone__BreakableShareAssignment_2_1 ) ) )
            // InternalMazeDsl.g:6611:1: ( ( rule__LevelZone__BreakableShareAssignment_2_1 ) )
            {
            // InternalMazeDsl.g:6611:1: ( ( rule__LevelZone__BreakableShareAssignment_2_1 ) )
            // InternalMazeDsl.g:6612:2: ( rule__LevelZone__BreakableShareAssignment_2_1 )
            {
             before(grammarAccess.getLevelZoneAccess().getBreakableShareAssignment_2_1()); 
            // InternalMazeDsl.g:6613:2: ( rule__LevelZone__BreakableShareAssignment_2_1 )
            // InternalMazeDsl.g:6613:3: rule__LevelZone__BreakableShareAssignment_2_1
            {
            pushFollow(FOLLOW_2);
            rule__LevelZone__BreakableShareAssignment_2_1();

            state._fsp--;


            }

             after(grammarAccess.getLevelZoneAccess().getBreakableShareAssignment_2_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelZone__Group_2__1__Impl"


    // $ANTLR start "rule__LevelZone__Group_5__0"
    // InternalMazeDsl.g:6622:1: rule__LevelZone__Group_5__0 : rule__LevelZone__Group_5__0__Impl rule__LevelZone__Group_5__1 ;
    public final void rule__LevelZone__Group_5__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6626:1: ( rule__LevelZone__Group_5__0__Impl rule__LevelZone__Group_5__1 )
            // InternalMazeDsl.g:6627:2: rule__LevelZone__Group_5__0__Impl rule__LevelZone__Group_5__1
            {
            pushFollow(FOLLOW_3);
            rule__LevelZone__Group_5__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LevelZone__Group_5__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelZone__Group_5__0"


    // $ANTLR start "rule__LevelZone__Group_5__0__Impl"
    // InternalMazeDsl.g:6634:1: rule__LevelZone__Group_5__0__Impl : ( ',' ) ;
    public final void rule__LevelZone__Group_5__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6638:1: ( ( ',' ) )
            // InternalMazeDsl.g:6639:1: ( ',' )
            {
            // InternalMazeDsl.g:6639:1: ( ',' )
            // InternalMazeDsl.g:6640:2: ','
            {
             before(grammarAccess.getLevelZoneAccess().getCommaKeyword_5_0()); 
            match(input,69,FOLLOW_2); 
             after(grammarAccess.getLevelZoneAccess().getCommaKeyword_5_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelZone__Group_5__0__Impl"


    // $ANTLR start "rule__LevelZone__Group_5__1"
    // InternalMazeDsl.g:6649:1: rule__LevelZone__Group_5__1 : rule__LevelZone__Group_5__1__Impl ;
    public final void rule__LevelZone__Group_5__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6653:1: ( rule__LevelZone__Group_5__1__Impl )
            // InternalMazeDsl.g:6654:2: rule__LevelZone__Group_5__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__LevelZone__Group_5__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelZone__Group_5__1"


    // $ANTLR start "rule__LevelZone__Group_5__1__Impl"
    // InternalMazeDsl.g:6660:1: rule__LevelZone__Group_5__1__Impl : ( ( rule__LevelZone__MaterialIdsAssignment_5_1 ) ) ;
    public final void rule__LevelZone__Group_5__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6664:1: ( ( ( rule__LevelZone__MaterialIdsAssignment_5_1 ) ) )
            // InternalMazeDsl.g:6665:1: ( ( rule__LevelZone__MaterialIdsAssignment_5_1 ) )
            {
            // InternalMazeDsl.g:6665:1: ( ( rule__LevelZone__MaterialIdsAssignment_5_1 ) )
            // InternalMazeDsl.g:6666:2: ( rule__LevelZone__MaterialIdsAssignment_5_1 )
            {
             before(grammarAccess.getLevelZoneAccess().getMaterialIdsAssignment_5_1()); 
            // InternalMazeDsl.g:6667:2: ( rule__LevelZone__MaterialIdsAssignment_5_1 )
            // InternalMazeDsl.g:6667:3: rule__LevelZone__MaterialIdsAssignment_5_1
            {
            pushFollow(FOLLOW_2);
            rule__LevelZone__MaterialIdsAssignment_5_1();

            state._fsp--;


            }

             after(grammarAccess.getLevelZoneAccess().getMaterialIdsAssignment_5_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelZone__Group_5__1__Impl"


    // $ANTLR start "rule__SergeantConfig__Group__0"
    // InternalMazeDsl.g:6676:1: rule__SergeantConfig__Group__0 : rule__SergeantConfig__Group__0__Impl rule__SergeantConfig__Group__1 ;
    public final void rule__SergeantConfig__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6680:1: ( rule__SergeantConfig__Group__0__Impl rule__SergeantConfig__Group__1 )
            // InternalMazeDsl.g:6681:2: rule__SergeantConfig__Group__0__Impl rule__SergeantConfig__Group__1
            {
            pushFollow(FOLLOW_11);
            rule__SergeantConfig__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__SergeantConfig__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__Group__0"


    // $ANTLR start "rule__SergeantConfig__Group__0__Impl"
    // InternalMazeDsl.g:6688:1: rule__SergeantConfig__Group__0__Impl : ( 'sergeant' ) ;
    public final void rule__SergeantConfig__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6692:1: ( ( 'sergeant' ) )
            // InternalMazeDsl.g:6693:1: ( 'sergeant' )
            {
            // InternalMazeDsl.g:6693:1: ( 'sergeant' )
            // InternalMazeDsl.g:6694:2: 'sergeant'
            {
             before(grammarAccess.getSergeantConfigAccess().getSergeantKeyword_0()); 
            match(input,91,FOLLOW_2); 
             after(grammarAccess.getSergeantConfigAccess().getSergeantKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__Group__0__Impl"


    // $ANTLR start "rule__SergeantConfig__Group__1"
    // InternalMazeDsl.g:6703:1: rule__SergeantConfig__Group__1 : rule__SergeantConfig__Group__1__Impl rule__SergeantConfig__Group__2 ;
    public final void rule__SergeantConfig__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6707:1: ( rule__SergeantConfig__Group__1__Impl rule__SergeantConfig__Group__2 )
            // InternalMazeDsl.g:6708:2: rule__SergeantConfig__Group__1__Impl rule__SergeantConfig__Group__2
            {
            pushFollow(FOLLOW_4);
            rule__SergeantConfig__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__SergeantConfig__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__Group__1"


    // $ANTLR start "rule__SergeantConfig__Group__1__Impl"
    // InternalMazeDsl.g:6715:1: rule__SergeantConfig__Group__1__Impl : ( ( rule__SergeantConfig__DisplayNameAssignment_1 ) ) ;
    public final void rule__SergeantConfig__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6719:1: ( ( ( rule__SergeantConfig__DisplayNameAssignment_1 ) ) )
            // InternalMazeDsl.g:6720:1: ( ( rule__SergeantConfig__DisplayNameAssignment_1 ) )
            {
            // InternalMazeDsl.g:6720:1: ( ( rule__SergeantConfig__DisplayNameAssignment_1 ) )
            // InternalMazeDsl.g:6721:2: ( rule__SergeantConfig__DisplayNameAssignment_1 )
            {
             before(grammarAccess.getSergeantConfigAccess().getDisplayNameAssignment_1()); 
            // InternalMazeDsl.g:6722:2: ( rule__SergeantConfig__DisplayNameAssignment_1 )
            // InternalMazeDsl.g:6722:3: rule__SergeantConfig__DisplayNameAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__SergeantConfig__DisplayNameAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getSergeantConfigAccess().getDisplayNameAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__Group__1__Impl"


    // $ANTLR start "rule__SergeantConfig__Group__2"
    // InternalMazeDsl.g:6730:1: rule__SergeantConfig__Group__2 : rule__SergeantConfig__Group__2__Impl rule__SergeantConfig__Group__3 ;
    public final void rule__SergeantConfig__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6734:1: ( rule__SergeantConfig__Group__2__Impl rule__SergeantConfig__Group__3 )
            // InternalMazeDsl.g:6735:2: rule__SergeantConfig__Group__2__Impl rule__SergeantConfig__Group__3
            {
            pushFollow(FOLLOW_55);
            rule__SergeantConfig__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__SergeantConfig__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__Group__2"


    // $ANTLR start "rule__SergeantConfig__Group__2__Impl"
    // InternalMazeDsl.g:6742:1: rule__SergeantConfig__Group__2__Impl : ( '{' ) ;
    public final void rule__SergeantConfig__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6746:1: ( ( '{' ) )
            // InternalMazeDsl.g:6747:1: ( '{' )
            {
            // InternalMazeDsl.g:6747:1: ( '{' )
            // InternalMazeDsl.g:6748:2: '{'
            {
             before(grammarAccess.getSergeantConfigAccess().getLeftCurlyBracketKeyword_2()); 
            match(input,32,FOLLOW_2); 
             after(grammarAccess.getSergeantConfigAccess().getLeftCurlyBracketKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__Group__2__Impl"


    // $ANTLR start "rule__SergeantConfig__Group__3"
    // InternalMazeDsl.g:6757:1: rule__SergeantConfig__Group__3 : rule__SergeantConfig__Group__3__Impl rule__SergeantConfig__Group__4 ;
    public final void rule__SergeantConfig__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6761:1: ( rule__SergeantConfig__Group__3__Impl rule__SergeantConfig__Group__4 )
            // InternalMazeDsl.g:6762:2: rule__SergeantConfig__Group__3__Impl rule__SergeantConfig__Group__4
            {
            pushFollow(FOLLOW_26);
            rule__SergeantConfig__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__SergeantConfig__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__Group__3"


    // $ANTLR start "rule__SergeantConfig__Group__3__Impl"
    // InternalMazeDsl.g:6769:1: rule__SergeantConfig__Group__3__Impl : ( 'health' ) ;
    public final void rule__SergeantConfig__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6773:1: ( ( 'health' ) )
            // InternalMazeDsl.g:6774:1: ( 'health' )
            {
            // InternalMazeDsl.g:6774:1: ( 'health' )
            // InternalMazeDsl.g:6775:2: 'health'
            {
             before(grammarAccess.getSergeantConfigAccess().getHealthKeyword_3()); 
            match(input,46,FOLLOW_2); 
             after(grammarAccess.getSergeantConfigAccess().getHealthKeyword_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__Group__3__Impl"


    // $ANTLR start "rule__SergeantConfig__Group__4"
    // InternalMazeDsl.g:6784:1: rule__SergeantConfig__Group__4 : rule__SergeantConfig__Group__4__Impl rule__SergeantConfig__Group__5 ;
    public final void rule__SergeantConfig__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6788:1: ( rule__SergeantConfig__Group__4__Impl rule__SergeantConfig__Group__5 )
            // InternalMazeDsl.g:6789:2: rule__SergeantConfig__Group__4__Impl rule__SergeantConfig__Group__5
            {
            pushFollow(FOLLOW_56);
            rule__SergeantConfig__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__SergeantConfig__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__Group__4"


    // $ANTLR start "rule__SergeantConfig__Group__4__Impl"
    // InternalMazeDsl.g:6796:1: rule__SergeantConfig__Group__4__Impl : ( ( rule__SergeantConfig__HealthAssignment_4 ) ) ;
    public final void rule__SergeantConfig__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6800:1: ( ( ( rule__SergeantConfig__HealthAssignment_4 ) ) )
            // InternalMazeDsl.g:6801:1: ( ( rule__SergeantConfig__HealthAssignment_4 ) )
            {
            // InternalMazeDsl.g:6801:1: ( ( rule__SergeantConfig__HealthAssignment_4 ) )
            // InternalMazeDsl.g:6802:2: ( rule__SergeantConfig__HealthAssignment_4 )
            {
             before(grammarAccess.getSergeantConfigAccess().getHealthAssignment_4()); 
            // InternalMazeDsl.g:6803:2: ( rule__SergeantConfig__HealthAssignment_4 )
            // InternalMazeDsl.g:6803:3: rule__SergeantConfig__HealthAssignment_4
            {
            pushFollow(FOLLOW_2);
            rule__SergeantConfig__HealthAssignment_4();

            state._fsp--;


            }

             after(grammarAccess.getSergeantConfigAccess().getHealthAssignment_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__Group__4__Impl"


    // $ANTLR start "rule__SergeantConfig__Group__5"
    // InternalMazeDsl.g:6811:1: rule__SergeantConfig__Group__5 : rule__SergeantConfig__Group__5__Impl rule__SergeantConfig__Group__6 ;
    public final void rule__SergeantConfig__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6815:1: ( rule__SergeantConfig__Group__5__Impl rule__SergeantConfig__Group__6 )
            // InternalMazeDsl.g:6816:2: rule__SergeantConfig__Group__5__Impl rule__SergeantConfig__Group__6
            {
            pushFollow(FOLLOW_17);
            rule__SergeantConfig__Group__5__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__SergeantConfig__Group__6();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__Group__5"


    // $ANTLR start "rule__SergeantConfig__Group__5__Impl"
    // InternalMazeDsl.g:6823:1: rule__SergeantConfig__Group__5__Impl : ( 'sightRange' ) ;
    public final void rule__SergeantConfig__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6827:1: ( ( 'sightRange' ) )
            // InternalMazeDsl.g:6828:1: ( 'sightRange' )
            {
            // InternalMazeDsl.g:6828:1: ( 'sightRange' )
            // InternalMazeDsl.g:6829:2: 'sightRange'
            {
             before(grammarAccess.getSergeantConfigAccess().getSightRangeKeyword_5()); 
            match(input,92,FOLLOW_2); 
             after(grammarAccess.getSergeantConfigAccess().getSightRangeKeyword_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__Group__5__Impl"


    // $ANTLR start "rule__SergeantConfig__Group__6"
    // InternalMazeDsl.g:6838:1: rule__SergeantConfig__Group__6 : rule__SergeantConfig__Group__6__Impl rule__SergeantConfig__Group__7 ;
    public final void rule__SergeantConfig__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6842:1: ( rule__SergeantConfig__Group__6__Impl rule__SergeantConfig__Group__7 )
            // InternalMazeDsl.g:6843:2: rule__SergeantConfig__Group__6__Impl rule__SergeantConfig__Group__7
            {
            pushFollow(FOLLOW_57);
            rule__SergeantConfig__Group__6__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__SergeantConfig__Group__7();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__Group__6"


    // $ANTLR start "rule__SergeantConfig__Group__6__Impl"
    // InternalMazeDsl.g:6850:1: rule__SergeantConfig__Group__6__Impl : ( ( rule__SergeantConfig__SightRangeAssignment_6 ) ) ;
    public final void rule__SergeantConfig__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6854:1: ( ( ( rule__SergeantConfig__SightRangeAssignment_6 ) ) )
            // InternalMazeDsl.g:6855:1: ( ( rule__SergeantConfig__SightRangeAssignment_6 ) )
            {
            // InternalMazeDsl.g:6855:1: ( ( rule__SergeantConfig__SightRangeAssignment_6 ) )
            // InternalMazeDsl.g:6856:2: ( rule__SergeantConfig__SightRangeAssignment_6 )
            {
             before(grammarAccess.getSergeantConfigAccess().getSightRangeAssignment_6()); 
            // InternalMazeDsl.g:6857:2: ( rule__SergeantConfig__SightRangeAssignment_6 )
            // InternalMazeDsl.g:6857:3: rule__SergeantConfig__SightRangeAssignment_6
            {
            pushFollow(FOLLOW_2);
            rule__SergeantConfig__SightRangeAssignment_6();

            state._fsp--;


            }

             after(grammarAccess.getSergeantConfigAccess().getSightRangeAssignment_6()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__Group__6__Impl"


    // $ANTLR start "rule__SergeantConfig__Group__7"
    // InternalMazeDsl.g:6865:1: rule__SergeantConfig__Group__7 : rule__SergeantConfig__Group__7__Impl rule__SergeantConfig__Group__8 ;
    public final void rule__SergeantConfig__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6869:1: ( rule__SergeantConfig__Group__7__Impl rule__SergeantConfig__Group__8 )
            // InternalMazeDsl.g:6870:2: rule__SergeantConfig__Group__7__Impl rule__SergeantConfig__Group__8
            {
            pushFollow(FOLLOW_26);
            rule__SergeantConfig__Group__7__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__SergeantConfig__Group__8();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__Group__7"


    // $ANTLR start "rule__SergeantConfig__Group__7__Impl"
    // InternalMazeDsl.g:6877:1: rule__SergeantConfig__Group__7__Impl : ( 'rounds' ) ;
    public final void rule__SergeantConfig__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6881:1: ( ( 'rounds' ) )
            // InternalMazeDsl.g:6882:1: ( 'rounds' )
            {
            // InternalMazeDsl.g:6882:1: ( 'rounds' )
            // InternalMazeDsl.g:6883:2: 'rounds'
            {
             before(grammarAccess.getSergeantConfigAccess().getRoundsKeyword_7()); 
            match(input,93,FOLLOW_2); 
             after(grammarAccess.getSergeantConfigAccess().getRoundsKeyword_7()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__Group__7__Impl"


    // $ANTLR start "rule__SergeantConfig__Group__8"
    // InternalMazeDsl.g:6892:1: rule__SergeantConfig__Group__8 : rule__SergeantConfig__Group__8__Impl rule__SergeantConfig__Group__9 ;
    public final void rule__SergeantConfig__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6896:1: ( rule__SergeantConfig__Group__8__Impl rule__SergeantConfig__Group__9 )
            // InternalMazeDsl.g:6897:2: rule__SergeantConfig__Group__8__Impl rule__SergeantConfig__Group__9
            {
            pushFollow(FOLLOW_58);
            rule__SergeantConfig__Group__8__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__SergeantConfig__Group__9();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__Group__8"


    // $ANTLR start "rule__SergeantConfig__Group__8__Impl"
    // InternalMazeDsl.g:6904:1: rule__SergeantConfig__Group__8__Impl : ( ( rule__SergeantConfig__RoundsPerBurstAssignment_8 ) ) ;
    public final void rule__SergeantConfig__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6908:1: ( ( ( rule__SergeantConfig__RoundsPerBurstAssignment_8 ) ) )
            // InternalMazeDsl.g:6909:1: ( ( rule__SergeantConfig__RoundsPerBurstAssignment_8 ) )
            {
            // InternalMazeDsl.g:6909:1: ( ( rule__SergeantConfig__RoundsPerBurstAssignment_8 ) )
            // InternalMazeDsl.g:6910:2: ( rule__SergeantConfig__RoundsPerBurstAssignment_8 )
            {
             before(grammarAccess.getSergeantConfigAccess().getRoundsPerBurstAssignment_8()); 
            // InternalMazeDsl.g:6911:2: ( rule__SergeantConfig__RoundsPerBurstAssignment_8 )
            // InternalMazeDsl.g:6911:3: rule__SergeantConfig__RoundsPerBurstAssignment_8
            {
            pushFollow(FOLLOW_2);
            rule__SergeantConfig__RoundsPerBurstAssignment_8();

            state._fsp--;


            }

             after(grammarAccess.getSergeantConfigAccess().getRoundsPerBurstAssignment_8()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__Group__8__Impl"


    // $ANTLR start "rule__SergeantConfig__Group__9"
    // InternalMazeDsl.g:6919:1: rule__SergeantConfig__Group__9 : rule__SergeantConfig__Group__9__Impl rule__SergeantConfig__Group__10 ;
    public final void rule__SergeantConfig__Group__9() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6923:1: ( rule__SergeantConfig__Group__9__Impl rule__SergeantConfig__Group__10 )
            // InternalMazeDsl.g:6924:2: rule__SergeantConfig__Group__9__Impl rule__SergeantConfig__Group__10
            {
            pushFollow(FOLLOW_26);
            rule__SergeantConfig__Group__9__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__SergeantConfig__Group__10();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__Group__9"


    // $ANTLR start "rule__SergeantConfig__Group__9__Impl"
    // InternalMazeDsl.g:6931:1: rule__SergeantConfig__Group__9__Impl : ( 'roundDamage' ) ;
    public final void rule__SergeantConfig__Group__9__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6935:1: ( ( 'roundDamage' ) )
            // InternalMazeDsl.g:6936:1: ( 'roundDamage' )
            {
            // InternalMazeDsl.g:6936:1: ( 'roundDamage' )
            // InternalMazeDsl.g:6937:2: 'roundDamage'
            {
             before(grammarAccess.getSergeantConfigAccess().getRoundDamageKeyword_9()); 
            match(input,94,FOLLOW_2); 
             after(grammarAccess.getSergeantConfigAccess().getRoundDamageKeyword_9()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__Group__9__Impl"


    // $ANTLR start "rule__SergeantConfig__Group__10"
    // InternalMazeDsl.g:6946:1: rule__SergeantConfig__Group__10 : rule__SergeantConfig__Group__10__Impl rule__SergeantConfig__Group__11 ;
    public final void rule__SergeantConfig__Group__10() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6950:1: ( rule__SergeantConfig__Group__10__Impl rule__SergeantConfig__Group__11 )
            // InternalMazeDsl.g:6951:2: rule__SergeantConfig__Group__10__Impl rule__SergeantConfig__Group__11
            {
            pushFollow(FOLLOW_59);
            rule__SergeantConfig__Group__10__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__SergeantConfig__Group__11();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__Group__10"


    // $ANTLR start "rule__SergeantConfig__Group__10__Impl"
    // InternalMazeDsl.g:6958:1: rule__SergeantConfig__Group__10__Impl : ( ( rule__SergeantConfig__RoundDamageAssignment_10 ) ) ;
    public final void rule__SergeantConfig__Group__10__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6962:1: ( ( ( rule__SergeantConfig__RoundDamageAssignment_10 ) ) )
            // InternalMazeDsl.g:6963:1: ( ( rule__SergeantConfig__RoundDamageAssignment_10 ) )
            {
            // InternalMazeDsl.g:6963:1: ( ( rule__SergeantConfig__RoundDamageAssignment_10 ) )
            // InternalMazeDsl.g:6964:2: ( rule__SergeantConfig__RoundDamageAssignment_10 )
            {
             before(grammarAccess.getSergeantConfigAccess().getRoundDamageAssignment_10()); 
            // InternalMazeDsl.g:6965:2: ( rule__SergeantConfig__RoundDamageAssignment_10 )
            // InternalMazeDsl.g:6965:3: rule__SergeantConfig__RoundDamageAssignment_10
            {
            pushFollow(FOLLOW_2);
            rule__SergeantConfig__RoundDamageAssignment_10();

            state._fsp--;


            }

             after(grammarAccess.getSergeantConfigAccess().getRoundDamageAssignment_10()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__Group__10__Impl"


    // $ANTLR start "rule__SergeantConfig__Group__11"
    // InternalMazeDsl.g:6973:1: rule__SergeantConfig__Group__11 : rule__SergeantConfig__Group__11__Impl rule__SergeantConfig__Group__12 ;
    public final void rule__SergeantConfig__Group__11() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6977:1: ( rule__SergeantConfig__Group__11__Impl rule__SergeantConfig__Group__12 )
            // InternalMazeDsl.g:6978:2: rule__SergeantConfig__Group__11__Impl rule__SergeantConfig__Group__12
            {
            pushFollow(FOLLOW_17);
            rule__SergeantConfig__Group__11__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__SergeantConfig__Group__12();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__Group__11"


    // $ANTLR start "rule__SergeantConfig__Group__11__Impl"
    // InternalMazeDsl.g:6985:1: rule__SergeantConfig__Group__11__Impl : ( 'roundInterval' ) ;
    public final void rule__SergeantConfig__Group__11__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6989:1: ( ( 'roundInterval' ) )
            // InternalMazeDsl.g:6990:1: ( 'roundInterval' )
            {
            // InternalMazeDsl.g:6990:1: ( 'roundInterval' )
            // InternalMazeDsl.g:6991:2: 'roundInterval'
            {
             before(grammarAccess.getSergeantConfigAccess().getRoundIntervalKeyword_11()); 
            match(input,95,FOLLOW_2); 
             after(grammarAccess.getSergeantConfigAccess().getRoundIntervalKeyword_11()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__Group__11__Impl"


    // $ANTLR start "rule__SergeantConfig__Group__12"
    // InternalMazeDsl.g:7000:1: rule__SergeantConfig__Group__12 : rule__SergeantConfig__Group__12__Impl rule__SergeantConfig__Group__13 ;
    public final void rule__SergeantConfig__Group__12() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7004:1: ( rule__SergeantConfig__Group__12__Impl rule__SergeantConfig__Group__13 )
            // InternalMazeDsl.g:7005:2: rule__SergeantConfig__Group__12__Impl rule__SergeantConfig__Group__13
            {
            pushFollow(FOLLOW_60);
            rule__SergeantConfig__Group__12__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__SergeantConfig__Group__13();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__Group__12"


    // $ANTLR start "rule__SergeantConfig__Group__12__Impl"
    // InternalMazeDsl.g:7012:1: rule__SergeantConfig__Group__12__Impl : ( ( rule__SergeantConfig__RoundIntervalSecondsAssignment_12 ) ) ;
    public final void rule__SergeantConfig__Group__12__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7016:1: ( ( ( rule__SergeantConfig__RoundIntervalSecondsAssignment_12 ) ) )
            // InternalMazeDsl.g:7017:1: ( ( rule__SergeantConfig__RoundIntervalSecondsAssignment_12 ) )
            {
            // InternalMazeDsl.g:7017:1: ( ( rule__SergeantConfig__RoundIntervalSecondsAssignment_12 ) )
            // InternalMazeDsl.g:7018:2: ( rule__SergeantConfig__RoundIntervalSecondsAssignment_12 )
            {
             before(grammarAccess.getSergeantConfigAccess().getRoundIntervalSecondsAssignment_12()); 
            // InternalMazeDsl.g:7019:2: ( rule__SergeantConfig__RoundIntervalSecondsAssignment_12 )
            // InternalMazeDsl.g:7019:3: rule__SergeantConfig__RoundIntervalSecondsAssignment_12
            {
            pushFollow(FOLLOW_2);
            rule__SergeantConfig__RoundIntervalSecondsAssignment_12();

            state._fsp--;


            }

             after(grammarAccess.getSergeantConfigAccess().getRoundIntervalSecondsAssignment_12()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__Group__12__Impl"


    // $ANTLR start "rule__SergeantConfig__Group__13"
    // InternalMazeDsl.g:7027:1: rule__SergeantConfig__Group__13 : rule__SergeantConfig__Group__13__Impl rule__SergeantConfig__Group__14 ;
    public final void rule__SergeantConfig__Group__13() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7031:1: ( rule__SergeantConfig__Group__13__Impl rule__SergeantConfig__Group__14 )
            // InternalMazeDsl.g:7032:2: rule__SergeantConfig__Group__13__Impl rule__SergeantConfig__Group__14
            {
            pushFollow(FOLLOW_17);
            rule__SergeantConfig__Group__13__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__SergeantConfig__Group__14();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__Group__13"


    // $ANTLR start "rule__SergeantConfig__Group__13__Impl"
    // InternalMazeDsl.g:7039:1: rule__SergeantConfig__Group__13__Impl : ( 'burstCooldown' ) ;
    public final void rule__SergeantConfig__Group__13__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7043:1: ( ( 'burstCooldown' ) )
            // InternalMazeDsl.g:7044:1: ( 'burstCooldown' )
            {
            // InternalMazeDsl.g:7044:1: ( 'burstCooldown' )
            // InternalMazeDsl.g:7045:2: 'burstCooldown'
            {
             before(grammarAccess.getSergeantConfigAccess().getBurstCooldownKeyword_13()); 
            match(input,96,FOLLOW_2); 
             after(grammarAccess.getSergeantConfigAccess().getBurstCooldownKeyword_13()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__Group__13__Impl"


    // $ANTLR start "rule__SergeantConfig__Group__14"
    // InternalMazeDsl.g:7054:1: rule__SergeantConfig__Group__14 : rule__SergeantConfig__Group__14__Impl rule__SergeantConfig__Group__15 ;
    public final void rule__SergeantConfig__Group__14() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7058:1: ( rule__SergeantConfig__Group__14__Impl rule__SergeantConfig__Group__15 )
            // InternalMazeDsl.g:7059:2: rule__SergeantConfig__Group__14__Impl rule__SergeantConfig__Group__15
            {
            pushFollow(FOLLOW_61);
            rule__SergeantConfig__Group__14__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__SergeantConfig__Group__15();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__Group__14"


    // $ANTLR start "rule__SergeantConfig__Group__14__Impl"
    // InternalMazeDsl.g:7066:1: rule__SergeantConfig__Group__14__Impl : ( ( rule__SergeantConfig__BurstCooldownSecondsAssignment_14 ) ) ;
    public final void rule__SergeantConfig__Group__14__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7070:1: ( ( ( rule__SergeantConfig__BurstCooldownSecondsAssignment_14 ) ) )
            // InternalMazeDsl.g:7071:1: ( ( rule__SergeantConfig__BurstCooldownSecondsAssignment_14 ) )
            {
            // InternalMazeDsl.g:7071:1: ( ( rule__SergeantConfig__BurstCooldownSecondsAssignment_14 ) )
            // InternalMazeDsl.g:7072:2: ( rule__SergeantConfig__BurstCooldownSecondsAssignment_14 )
            {
             before(grammarAccess.getSergeantConfigAccess().getBurstCooldownSecondsAssignment_14()); 
            // InternalMazeDsl.g:7073:2: ( rule__SergeantConfig__BurstCooldownSecondsAssignment_14 )
            // InternalMazeDsl.g:7073:3: rule__SergeantConfig__BurstCooldownSecondsAssignment_14
            {
            pushFollow(FOLLOW_2);
            rule__SergeantConfig__BurstCooldownSecondsAssignment_14();

            state._fsp--;


            }

             after(grammarAccess.getSergeantConfigAccess().getBurstCooldownSecondsAssignment_14()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__Group__14__Impl"


    // $ANTLR start "rule__SergeantConfig__Group__15"
    // InternalMazeDsl.g:7081:1: rule__SergeantConfig__Group__15 : rule__SergeantConfig__Group__15__Impl rule__SergeantConfig__Group__16 ;
    public final void rule__SergeantConfig__Group__15() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7085:1: ( rule__SergeantConfig__Group__15__Impl rule__SergeantConfig__Group__16 )
            // InternalMazeDsl.g:7086:2: rule__SergeantConfig__Group__15__Impl rule__SergeantConfig__Group__16
            {
            pushFollow(FOLLOW_11);
            rule__SergeantConfig__Group__15__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__SergeantConfig__Group__16();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__Group__15"


    // $ANTLR start "rule__SergeantConfig__Group__15__Impl"
    // InternalMazeDsl.g:7093:1: rule__SergeantConfig__Group__15__Impl : ( 'sprite' ) ;
    public final void rule__SergeantConfig__Group__15__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7097:1: ( ( 'sprite' ) )
            // InternalMazeDsl.g:7098:1: ( 'sprite' )
            {
            // InternalMazeDsl.g:7098:1: ( 'sprite' )
            // InternalMazeDsl.g:7099:2: 'sprite'
            {
             before(grammarAccess.getSergeantConfigAccess().getSpriteKeyword_15()); 
            match(input,97,FOLLOW_2); 
             after(grammarAccess.getSergeantConfigAccess().getSpriteKeyword_15()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__Group__15__Impl"


    // $ANTLR start "rule__SergeantConfig__Group__16"
    // InternalMazeDsl.g:7108:1: rule__SergeantConfig__Group__16 : rule__SergeantConfig__Group__16__Impl rule__SergeantConfig__Group__17 ;
    public final void rule__SergeantConfig__Group__16() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7112:1: ( rule__SergeantConfig__Group__16__Impl rule__SergeantConfig__Group__17 )
            // InternalMazeDsl.g:7113:2: rule__SergeantConfig__Group__16__Impl rule__SergeantConfig__Group__17
            {
            pushFollow(FOLLOW_62);
            rule__SergeantConfig__Group__16__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__SergeantConfig__Group__17();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__Group__16"


    // $ANTLR start "rule__SergeantConfig__Group__16__Impl"
    // InternalMazeDsl.g:7120:1: rule__SergeantConfig__Group__16__Impl : ( ( rule__SergeantConfig__SpriteAssignment_16 ) ) ;
    public final void rule__SergeantConfig__Group__16__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7124:1: ( ( ( rule__SergeantConfig__SpriteAssignment_16 ) ) )
            // InternalMazeDsl.g:7125:1: ( ( rule__SergeantConfig__SpriteAssignment_16 ) )
            {
            // InternalMazeDsl.g:7125:1: ( ( rule__SergeantConfig__SpriteAssignment_16 ) )
            // InternalMazeDsl.g:7126:2: ( rule__SergeantConfig__SpriteAssignment_16 )
            {
             before(grammarAccess.getSergeantConfigAccess().getSpriteAssignment_16()); 
            // InternalMazeDsl.g:7127:2: ( rule__SergeantConfig__SpriteAssignment_16 )
            // InternalMazeDsl.g:7127:3: rule__SergeantConfig__SpriteAssignment_16
            {
            pushFollow(FOLLOW_2);
            rule__SergeantConfig__SpriteAssignment_16();

            state._fsp--;


            }

             after(grammarAccess.getSergeantConfigAccess().getSpriteAssignment_16()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__Group__16__Impl"


    // $ANTLR start "rule__SergeantConfig__Group__17"
    // InternalMazeDsl.g:7135:1: rule__SergeantConfig__Group__17 : rule__SergeantConfig__Group__17__Impl rule__SergeantConfig__Group__18 ;
    public final void rule__SergeantConfig__Group__17() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7139:1: ( rule__SergeantConfig__Group__17__Impl rule__SergeantConfig__Group__18 )
            // InternalMazeDsl.g:7140:2: rule__SergeantConfig__Group__17__Impl rule__SergeantConfig__Group__18
            {
            pushFollow(FOLLOW_62);
            rule__SergeantConfig__Group__17__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__SergeantConfig__Group__18();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__Group__17"


    // $ANTLR start "rule__SergeantConfig__Group__17__Impl"
    // InternalMazeDsl.g:7147:1: rule__SergeantConfig__Group__17__Impl : ( ( rule__SergeantConfig__Group_17__0 )? ) ;
    public final void rule__SergeantConfig__Group__17__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7151:1: ( ( ( rule__SergeantConfig__Group_17__0 )? ) )
            // InternalMazeDsl.g:7152:1: ( ( rule__SergeantConfig__Group_17__0 )? )
            {
            // InternalMazeDsl.g:7152:1: ( ( rule__SergeantConfig__Group_17__0 )? )
            // InternalMazeDsl.g:7153:2: ( rule__SergeantConfig__Group_17__0 )?
            {
             before(grammarAccess.getSergeantConfigAccess().getGroup_17()); 
            // InternalMazeDsl.g:7154:2: ( rule__SergeantConfig__Group_17__0 )?
            int alt63=2;
            int LA63_0 = input.LA(1);

            if ( (LA63_0==100) ) {
                alt63=1;
            }
            switch (alt63) {
                case 1 :
                    // InternalMazeDsl.g:7154:3: rule__SergeantConfig__Group_17__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__SergeantConfig__Group_17__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getSergeantConfigAccess().getGroup_17()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__Group__17__Impl"


    // $ANTLR start "rule__SergeantConfig__Group__18"
    // InternalMazeDsl.g:7162:1: rule__SergeantConfig__Group__18 : rule__SergeantConfig__Group__18__Impl rule__SergeantConfig__Group__19 ;
    public final void rule__SergeantConfig__Group__18() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7166:1: ( rule__SergeantConfig__Group__18__Impl rule__SergeantConfig__Group__19 )
            // InternalMazeDsl.g:7167:2: rule__SergeantConfig__Group__18__Impl rule__SergeantConfig__Group__19
            {
            pushFollow(FOLLOW_63);
            rule__SergeantConfig__Group__18__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__SergeantConfig__Group__19();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__Group__18"


    // $ANTLR start "rule__SergeantConfig__Group__18__Impl"
    // InternalMazeDsl.g:7174:1: rule__SergeantConfig__Group__18__Impl : ( 'drops' ) ;
    public final void rule__SergeantConfig__Group__18__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7178:1: ( ( 'drops' ) )
            // InternalMazeDsl.g:7179:1: ( 'drops' )
            {
            // InternalMazeDsl.g:7179:1: ( 'drops' )
            // InternalMazeDsl.g:7180:2: 'drops'
            {
             before(grammarAccess.getSergeantConfigAccess().getDropsKeyword_18()); 
            match(input,98,FOLLOW_2); 
             after(grammarAccess.getSergeantConfigAccess().getDropsKeyword_18()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__Group__18__Impl"


    // $ANTLR start "rule__SergeantConfig__Group__19"
    // InternalMazeDsl.g:7189:1: rule__SergeantConfig__Group__19 : rule__SergeantConfig__Group__19__Impl rule__SergeantConfig__Group__20 ;
    public final void rule__SergeantConfig__Group__19() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7193:1: ( rule__SergeantConfig__Group__19__Impl rule__SergeantConfig__Group__20 )
            // InternalMazeDsl.g:7194:2: rule__SergeantConfig__Group__19__Impl rule__SergeantConfig__Group__20
            {
            pushFollow(FOLLOW_64);
            rule__SergeantConfig__Group__19__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__SergeantConfig__Group__20();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__Group__19"


    // $ANTLR start "rule__SergeantConfig__Group__19__Impl"
    // InternalMazeDsl.g:7201:1: rule__SergeantConfig__Group__19__Impl : ( ( rule__SergeantConfig__DropWeaponAssignment_19 ) ) ;
    public final void rule__SergeantConfig__Group__19__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7205:1: ( ( ( rule__SergeantConfig__DropWeaponAssignment_19 ) ) )
            // InternalMazeDsl.g:7206:1: ( ( rule__SergeantConfig__DropWeaponAssignment_19 ) )
            {
            // InternalMazeDsl.g:7206:1: ( ( rule__SergeantConfig__DropWeaponAssignment_19 ) )
            // InternalMazeDsl.g:7207:2: ( rule__SergeantConfig__DropWeaponAssignment_19 )
            {
             before(grammarAccess.getSergeantConfigAccess().getDropWeaponAssignment_19()); 
            // InternalMazeDsl.g:7208:2: ( rule__SergeantConfig__DropWeaponAssignment_19 )
            // InternalMazeDsl.g:7208:3: rule__SergeantConfig__DropWeaponAssignment_19
            {
            pushFollow(FOLLOW_2);
            rule__SergeantConfig__DropWeaponAssignment_19();

            state._fsp--;


            }

             after(grammarAccess.getSergeantConfigAccess().getDropWeaponAssignment_19()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__Group__19__Impl"


    // $ANTLR start "rule__SergeantConfig__Group__20"
    // InternalMazeDsl.g:7216:1: rule__SergeantConfig__Group__20 : rule__SergeantConfig__Group__20__Impl rule__SergeantConfig__Group__21 ;
    public final void rule__SergeantConfig__Group__20() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7220:1: ( rule__SergeantConfig__Group__20__Impl rule__SergeantConfig__Group__21 )
            // InternalMazeDsl.g:7221:2: rule__SergeantConfig__Group__20__Impl rule__SergeantConfig__Group__21
            {
            pushFollow(FOLLOW_17);
            rule__SergeantConfig__Group__20__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__SergeantConfig__Group__21();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__Group__20"


    // $ANTLR start "rule__SergeantConfig__Group__20__Impl"
    // InternalMazeDsl.g:7228:1: rule__SergeantConfig__Group__20__Impl : ( 'postDistance' ) ;
    public final void rule__SergeantConfig__Group__20__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7232:1: ( ( 'postDistance' ) )
            // InternalMazeDsl.g:7233:1: ( 'postDistance' )
            {
            // InternalMazeDsl.g:7233:1: ( 'postDistance' )
            // InternalMazeDsl.g:7234:2: 'postDistance'
            {
             before(grammarAccess.getSergeantConfigAccess().getPostDistanceKeyword_20()); 
            match(input,99,FOLLOW_2); 
             after(grammarAccess.getSergeantConfigAccess().getPostDistanceKeyword_20()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__Group__20__Impl"


    // $ANTLR start "rule__SergeantConfig__Group__21"
    // InternalMazeDsl.g:7243:1: rule__SergeantConfig__Group__21 : rule__SergeantConfig__Group__21__Impl rule__SergeantConfig__Group__22 ;
    public final void rule__SergeantConfig__Group__21() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7247:1: ( rule__SergeantConfig__Group__21__Impl rule__SergeantConfig__Group__22 )
            // InternalMazeDsl.g:7248:2: rule__SergeantConfig__Group__21__Impl rule__SergeantConfig__Group__22
            {
            pushFollow(FOLLOW_37);
            rule__SergeantConfig__Group__21__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__SergeantConfig__Group__22();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__Group__21"


    // $ANTLR start "rule__SergeantConfig__Group__21__Impl"
    // InternalMazeDsl.g:7255:1: rule__SergeantConfig__Group__21__Impl : ( ( rule__SergeantConfig__PostDistanceFromHeartAssignment_21 ) ) ;
    public final void rule__SergeantConfig__Group__21__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7259:1: ( ( ( rule__SergeantConfig__PostDistanceFromHeartAssignment_21 ) ) )
            // InternalMazeDsl.g:7260:1: ( ( rule__SergeantConfig__PostDistanceFromHeartAssignment_21 ) )
            {
            // InternalMazeDsl.g:7260:1: ( ( rule__SergeantConfig__PostDistanceFromHeartAssignment_21 ) )
            // InternalMazeDsl.g:7261:2: ( rule__SergeantConfig__PostDistanceFromHeartAssignment_21 )
            {
             before(grammarAccess.getSergeantConfigAccess().getPostDistanceFromHeartAssignment_21()); 
            // InternalMazeDsl.g:7262:2: ( rule__SergeantConfig__PostDistanceFromHeartAssignment_21 )
            // InternalMazeDsl.g:7262:3: rule__SergeantConfig__PostDistanceFromHeartAssignment_21
            {
            pushFollow(FOLLOW_2);
            rule__SergeantConfig__PostDistanceFromHeartAssignment_21();

            state._fsp--;


            }

             after(grammarAccess.getSergeantConfigAccess().getPostDistanceFromHeartAssignment_21()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__Group__21__Impl"


    // $ANTLR start "rule__SergeantConfig__Group__22"
    // InternalMazeDsl.g:7270:1: rule__SergeantConfig__Group__22 : rule__SergeantConfig__Group__22__Impl ;
    public final void rule__SergeantConfig__Group__22() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7274:1: ( rule__SergeantConfig__Group__22__Impl )
            // InternalMazeDsl.g:7275:2: rule__SergeantConfig__Group__22__Impl
            {
            pushFollow(FOLLOW_2);
            rule__SergeantConfig__Group__22__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__Group__22"


    // $ANTLR start "rule__SergeantConfig__Group__22__Impl"
    // InternalMazeDsl.g:7281:1: rule__SergeantConfig__Group__22__Impl : ( '}' ) ;
    public final void rule__SergeantConfig__Group__22__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7285:1: ( ( '}' ) )
            // InternalMazeDsl.g:7286:1: ( '}' )
            {
            // InternalMazeDsl.g:7286:1: ( '}' )
            // InternalMazeDsl.g:7287:2: '}'
            {
             before(grammarAccess.getSergeantConfigAccess().getRightCurlyBracketKeyword_22()); 
            match(input,33,FOLLOW_2); 
             after(grammarAccess.getSergeantConfigAccess().getRightCurlyBracketKeyword_22()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__Group__22__Impl"


    // $ANTLR start "rule__SergeantConfig__Group_17__0"
    // InternalMazeDsl.g:7297:1: rule__SergeantConfig__Group_17__0 : rule__SergeantConfig__Group_17__0__Impl rule__SergeantConfig__Group_17__1 ;
    public final void rule__SergeantConfig__Group_17__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7301:1: ( rule__SergeantConfig__Group_17__0__Impl rule__SergeantConfig__Group_17__1 )
            // InternalMazeDsl.g:7302:2: rule__SergeantConfig__Group_17__0__Impl rule__SergeantConfig__Group_17__1
            {
            pushFollow(FOLLOW_11);
            rule__SergeantConfig__Group_17__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__SergeantConfig__Group_17__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__Group_17__0"


    // $ANTLR start "rule__SergeantConfig__Group_17__0__Impl"
    // InternalMazeDsl.g:7309:1: rule__SergeantConfig__Group_17__0__Impl : ( 'fireSound' ) ;
    public final void rule__SergeantConfig__Group_17__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7313:1: ( ( 'fireSound' ) )
            // InternalMazeDsl.g:7314:1: ( 'fireSound' )
            {
            // InternalMazeDsl.g:7314:1: ( 'fireSound' )
            // InternalMazeDsl.g:7315:2: 'fireSound'
            {
             before(grammarAccess.getSergeantConfigAccess().getFireSoundKeyword_17_0()); 
            match(input,100,FOLLOW_2); 
             after(grammarAccess.getSergeantConfigAccess().getFireSoundKeyword_17_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__Group_17__0__Impl"


    // $ANTLR start "rule__SergeantConfig__Group_17__1"
    // InternalMazeDsl.g:7324:1: rule__SergeantConfig__Group_17__1 : rule__SergeantConfig__Group_17__1__Impl ;
    public final void rule__SergeantConfig__Group_17__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7328:1: ( rule__SergeantConfig__Group_17__1__Impl )
            // InternalMazeDsl.g:7329:2: rule__SergeantConfig__Group_17__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__SergeantConfig__Group_17__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__Group_17__1"


    // $ANTLR start "rule__SergeantConfig__Group_17__1__Impl"
    // InternalMazeDsl.g:7335:1: rule__SergeantConfig__Group_17__1__Impl : ( ( rule__SergeantConfig__FireSoundAssignment_17_1 ) ) ;
    public final void rule__SergeantConfig__Group_17__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7339:1: ( ( ( rule__SergeantConfig__FireSoundAssignment_17_1 ) ) )
            // InternalMazeDsl.g:7340:1: ( ( rule__SergeantConfig__FireSoundAssignment_17_1 ) )
            {
            // InternalMazeDsl.g:7340:1: ( ( rule__SergeantConfig__FireSoundAssignment_17_1 ) )
            // InternalMazeDsl.g:7341:2: ( rule__SergeantConfig__FireSoundAssignment_17_1 )
            {
             before(grammarAccess.getSergeantConfigAccess().getFireSoundAssignment_17_1()); 
            // InternalMazeDsl.g:7342:2: ( rule__SergeantConfig__FireSoundAssignment_17_1 )
            // InternalMazeDsl.g:7342:3: rule__SergeantConfig__FireSoundAssignment_17_1
            {
            pushFollow(FOLLOW_2);
            rule__SergeantConfig__FireSoundAssignment_17_1();

            state._fsp--;


            }

             after(grammarAccess.getSergeantConfigAccess().getFireSoundAssignment_17_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__Group_17__1__Impl"


    // $ANTLR start "rule__TierCountConfig__Group__0"
    // InternalMazeDsl.g:7351:1: rule__TierCountConfig__Group__0 : rule__TierCountConfig__Group__0__Impl rule__TierCountConfig__Group__1 ;
    public final void rule__TierCountConfig__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7355:1: ( rule__TierCountConfig__Group__0__Impl rule__TierCountConfig__Group__1 )
            // InternalMazeDsl.g:7356:2: rule__TierCountConfig__Group__0__Impl rule__TierCountConfig__Group__1
            {
            pushFollow(FOLLOW_13);
            rule__TierCountConfig__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__TierCountConfig__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TierCountConfig__Group__0"


    // $ANTLR start "rule__TierCountConfig__Group__0__Impl"
    // InternalMazeDsl.g:7363:1: rule__TierCountConfig__Group__0__Impl : ( 'sergeants' ) ;
    public final void rule__TierCountConfig__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7367:1: ( ( 'sergeants' ) )
            // InternalMazeDsl.g:7368:1: ( 'sergeants' )
            {
            // InternalMazeDsl.g:7368:1: ( 'sergeants' )
            // InternalMazeDsl.g:7369:2: 'sergeants'
            {
             before(grammarAccess.getTierCountConfigAccess().getSergeantsKeyword_0()); 
            match(input,101,FOLLOW_2); 
             after(grammarAccess.getTierCountConfigAccess().getSergeantsKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TierCountConfig__Group__0__Impl"


    // $ANTLR start "rule__TierCountConfig__Group__1"
    // InternalMazeDsl.g:7378:1: rule__TierCountConfig__Group__1 : rule__TierCountConfig__Group__1__Impl rule__TierCountConfig__Group__2 ;
    public final void rule__TierCountConfig__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7382:1: ( rule__TierCountConfig__Group__1__Impl rule__TierCountConfig__Group__2 )
            // InternalMazeDsl.g:7383:2: rule__TierCountConfig__Group__1__Impl rule__TierCountConfig__Group__2
            {
            pushFollow(FOLLOW_26);
            rule__TierCountConfig__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__TierCountConfig__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TierCountConfig__Group__1"


    // $ANTLR start "rule__TierCountConfig__Group__1__Impl"
    // InternalMazeDsl.g:7390:1: rule__TierCountConfig__Group__1__Impl : ( ( rule__TierCountConfig__DifficultyAssignment_1 ) ) ;
    public final void rule__TierCountConfig__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7394:1: ( ( ( rule__TierCountConfig__DifficultyAssignment_1 ) ) )
            // InternalMazeDsl.g:7395:1: ( ( rule__TierCountConfig__DifficultyAssignment_1 ) )
            {
            // InternalMazeDsl.g:7395:1: ( ( rule__TierCountConfig__DifficultyAssignment_1 ) )
            // InternalMazeDsl.g:7396:2: ( rule__TierCountConfig__DifficultyAssignment_1 )
            {
             before(grammarAccess.getTierCountConfigAccess().getDifficultyAssignment_1()); 
            // InternalMazeDsl.g:7397:2: ( rule__TierCountConfig__DifficultyAssignment_1 )
            // InternalMazeDsl.g:7397:3: rule__TierCountConfig__DifficultyAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__TierCountConfig__DifficultyAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getTierCountConfigAccess().getDifficultyAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TierCountConfig__Group__1__Impl"


    // $ANTLR start "rule__TierCountConfig__Group__2"
    // InternalMazeDsl.g:7405:1: rule__TierCountConfig__Group__2 : rule__TierCountConfig__Group__2__Impl ;
    public final void rule__TierCountConfig__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7409:1: ( rule__TierCountConfig__Group__2__Impl )
            // InternalMazeDsl.g:7410:2: rule__TierCountConfig__Group__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__TierCountConfig__Group__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TierCountConfig__Group__2"


    // $ANTLR start "rule__TierCountConfig__Group__2__Impl"
    // InternalMazeDsl.g:7416:1: rule__TierCountConfig__Group__2__Impl : ( ( rule__TierCountConfig__CountAssignment_2 ) ) ;
    public final void rule__TierCountConfig__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7420:1: ( ( ( rule__TierCountConfig__CountAssignment_2 ) ) )
            // InternalMazeDsl.g:7421:1: ( ( rule__TierCountConfig__CountAssignment_2 ) )
            {
            // InternalMazeDsl.g:7421:1: ( ( rule__TierCountConfig__CountAssignment_2 ) )
            // InternalMazeDsl.g:7422:2: ( rule__TierCountConfig__CountAssignment_2 )
            {
             before(grammarAccess.getTierCountConfigAccess().getCountAssignment_2()); 
            // InternalMazeDsl.g:7423:2: ( rule__TierCountConfig__CountAssignment_2 )
            // InternalMazeDsl.g:7423:3: rule__TierCountConfig__CountAssignment_2
            {
            pushFollow(FOLLOW_2);
            rule__TierCountConfig__CountAssignment_2();

            state._fsp--;


            }

             after(grammarAccess.getTierCountConfigAccess().getCountAssignment_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TierCountConfig__Group__2__Impl"


    // $ANTLR start "rule__MineRingConfig__Group__0"
    // InternalMazeDsl.g:7432:1: rule__MineRingConfig__Group__0 : rule__MineRingConfig__Group__0__Impl rule__MineRingConfig__Group__1 ;
    public final void rule__MineRingConfig__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7436:1: ( rule__MineRingConfig__Group__0__Impl rule__MineRingConfig__Group__1 )
            // InternalMazeDsl.g:7437:2: rule__MineRingConfig__Group__0__Impl rule__MineRingConfig__Group__1
            {
            pushFollow(FOLLOW_4);
            rule__MineRingConfig__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__MineRingConfig__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MineRingConfig__Group__0"


    // $ANTLR start "rule__MineRingConfig__Group__0__Impl"
    // InternalMazeDsl.g:7444:1: rule__MineRingConfig__Group__0__Impl : ( 'mines' ) ;
    public final void rule__MineRingConfig__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7448:1: ( ( 'mines' ) )
            // InternalMazeDsl.g:7449:1: ( 'mines' )
            {
            // InternalMazeDsl.g:7449:1: ( 'mines' )
            // InternalMazeDsl.g:7450:2: 'mines'
            {
             before(grammarAccess.getMineRingConfigAccess().getMinesKeyword_0()); 
            match(input,102,FOLLOW_2); 
             after(grammarAccess.getMineRingConfigAccess().getMinesKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MineRingConfig__Group__0__Impl"


    // $ANTLR start "rule__MineRingConfig__Group__1"
    // InternalMazeDsl.g:7459:1: rule__MineRingConfig__Group__1 : rule__MineRingConfig__Group__1__Impl rule__MineRingConfig__Group__2 ;
    public final void rule__MineRingConfig__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7463:1: ( rule__MineRingConfig__Group__1__Impl rule__MineRingConfig__Group__2 )
            // InternalMazeDsl.g:7464:2: rule__MineRingConfig__Group__1__Impl rule__MineRingConfig__Group__2
            {
            pushFollow(FOLLOW_65);
            rule__MineRingConfig__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__MineRingConfig__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MineRingConfig__Group__1"


    // $ANTLR start "rule__MineRingConfig__Group__1__Impl"
    // InternalMazeDsl.g:7471:1: rule__MineRingConfig__Group__1__Impl : ( '{' ) ;
    public final void rule__MineRingConfig__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7475:1: ( ( '{' ) )
            // InternalMazeDsl.g:7476:1: ( '{' )
            {
            // InternalMazeDsl.g:7476:1: ( '{' )
            // InternalMazeDsl.g:7477:2: '{'
            {
             before(grammarAccess.getMineRingConfigAccess().getLeftCurlyBracketKeyword_1()); 
            match(input,32,FOLLOW_2); 
             after(grammarAccess.getMineRingConfigAccess().getLeftCurlyBracketKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MineRingConfig__Group__1__Impl"


    // $ANTLR start "rule__MineRingConfig__Group__2"
    // InternalMazeDsl.g:7486:1: rule__MineRingConfig__Group__2 : rule__MineRingConfig__Group__2__Impl rule__MineRingConfig__Group__3 ;
    public final void rule__MineRingConfig__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7490:1: ( rule__MineRingConfig__Group__2__Impl rule__MineRingConfig__Group__3 )
            // InternalMazeDsl.g:7491:2: rule__MineRingConfig__Group__2__Impl rule__MineRingConfig__Group__3
            {
            pushFollow(FOLLOW_26);
            rule__MineRingConfig__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__MineRingConfig__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MineRingConfig__Group__2"


    // $ANTLR start "rule__MineRingConfig__Group__2__Impl"
    // InternalMazeDsl.g:7498:1: rule__MineRingConfig__Group__2__Impl : ( 'count' ) ;
    public final void rule__MineRingConfig__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7502:1: ( ( 'count' ) )
            // InternalMazeDsl.g:7503:1: ( 'count' )
            {
            // InternalMazeDsl.g:7503:1: ( 'count' )
            // InternalMazeDsl.g:7504:2: 'count'
            {
             before(grammarAccess.getMineRingConfigAccess().getCountKeyword_2()); 
            match(input,103,FOLLOW_2); 
             after(grammarAccess.getMineRingConfigAccess().getCountKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MineRingConfig__Group__2__Impl"


    // $ANTLR start "rule__MineRingConfig__Group__3"
    // InternalMazeDsl.g:7513:1: rule__MineRingConfig__Group__3 : rule__MineRingConfig__Group__3__Impl rule__MineRingConfig__Group__4 ;
    public final void rule__MineRingConfig__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7517:1: ( rule__MineRingConfig__Group__3__Impl rule__MineRingConfig__Group__4 )
            // InternalMazeDsl.g:7518:2: rule__MineRingConfig__Group__3__Impl rule__MineRingConfig__Group__4
            {
            pushFollow(FOLLOW_66);
            rule__MineRingConfig__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__MineRingConfig__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MineRingConfig__Group__3"


    // $ANTLR start "rule__MineRingConfig__Group__3__Impl"
    // InternalMazeDsl.g:7525:1: rule__MineRingConfig__Group__3__Impl : ( ( rule__MineRingConfig__CountAssignment_3 ) ) ;
    public final void rule__MineRingConfig__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7529:1: ( ( ( rule__MineRingConfig__CountAssignment_3 ) ) )
            // InternalMazeDsl.g:7530:1: ( ( rule__MineRingConfig__CountAssignment_3 ) )
            {
            // InternalMazeDsl.g:7530:1: ( ( rule__MineRingConfig__CountAssignment_3 ) )
            // InternalMazeDsl.g:7531:2: ( rule__MineRingConfig__CountAssignment_3 )
            {
             before(grammarAccess.getMineRingConfigAccess().getCountAssignment_3()); 
            // InternalMazeDsl.g:7532:2: ( rule__MineRingConfig__CountAssignment_3 )
            // InternalMazeDsl.g:7532:3: rule__MineRingConfig__CountAssignment_3
            {
            pushFollow(FOLLOW_2);
            rule__MineRingConfig__CountAssignment_3();

            state._fsp--;


            }

             after(grammarAccess.getMineRingConfigAccess().getCountAssignment_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MineRingConfig__Group__3__Impl"


    // $ANTLR start "rule__MineRingConfig__Group__4"
    // InternalMazeDsl.g:7540:1: rule__MineRingConfig__Group__4 : rule__MineRingConfig__Group__4__Impl rule__MineRingConfig__Group__5 ;
    public final void rule__MineRingConfig__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7544:1: ( rule__MineRingConfig__Group__4__Impl rule__MineRingConfig__Group__5 )
            // InternalMazeDsl.g:7545:2: rule__MineRingConfig__Group__4__Impl rule__MineRingConfig__Group__5
            {
            pushFollow(FOLLOW_17);
            rule__MineRingConfig__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__MineRingConfig__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MineRingConfig__Group__4"


    // $ANTLR start "rule__MineRingConfig__Group__4__Impl"
    // InternalMazeDsl.g:7552:1: rule__MineRingConfig__Group__4__Impl : ( 'ringRadius' ) ;
    public final void rule__MineRingConfig__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7556:1: ( ( 'ringRadius' ) )
            // InternalMazeDsl.g:7557:1: ( 'ringRadius' )
            {
            // InternalMazeDsl.g:7557:1: ( 'ringRadius' )
            // InternalMazeDsl.g:7558:2: 'ringRadius'
            {
             before(grammarAccess.getMineRingConfigAccess().getRingRadiusKeyword_4()); 
            match(input,104,FOLLOW_2); 
             after(grammarAccess.getMineRingConfigAccess().getRingRadiusKeyword_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MineRingConfig__Group__4__Impl"


    // $ANTLR start "rule__MineRingConfig__Group__5"
    // InternalMazeDsl.g:7567:1: rule__MineRingConfig__Group__5 : rule__MineRingConfig__Group__5__Impl rule__MineRingConfig__Group__6 ;
    public final void rule__MineRingConfig__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7571:1: ( rule__MineRingConfig__Group__5__Impl rule__MineRingConfig__Group__6 )
            // InternalMazeDsl.g:7572:2: rule__MineRingConfig__Group__5__Impl rule__MineRingConfig__Group__6
            {
            pushFollow(FOLLOW_67);
            rule__MineRingConfig__Group__5__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__MineRingConfig__Group__6();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MineRingConfig__Group__5"


    // $ANTLR start "rule__MineRingConfig__Group__5__Impl"
    // InternalMazeDsl.g:7579:1: rule__MineRingConfig__Group__5__Impl : ( ( rule__MineRingConfig__RingRadiusAssignment_5 ) ) ;
    public final void rule__MineRingConfig__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7583:1: ( ( ( rule__MineRingConfig__RingRadiusAssignment_5 ) ) )
            // InternalMazeDsl.g:7584:1: ( ( rule__MineRingConfig__RingRadiusAssignment_5 ) )
            {
            // InternalMazeDsl.g:7584:1: ( ( rule__MineRingConfig__RingRadiusAssignment_5 ) )
            // InternalMazeDsl.g:7585:2: ( rule__MineRingConfig__RingRadiusAssignment_5 )
            {
             before(grammarAccess.getMineRingConfigAccess().getRingRadiusAssignment_5()); 
            // InternalMazeDsl.g:7586:2: ( rule__MineRingConfig__RingRadiusAssignment_5 )
            // InternalMazeDsl.g:7586:3: rule__MineRingConfig__RingRadiusAssignment_5
            {
            pushFollow(FOLLOW_2);
            rule__MineRingConfig__RingRadiusAssignment_5();

            state._fsp--;


            }

             after(grammarAccess.getMineRingConfigAccess().getRingRadiusAssignment_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MineRingConfig__Group__5__Impl"


    // $ANTLR start "rule__MineRingConfig__Group__6"
    // InternalMazeDsl.g:7594:1: rule__MineRingConfig__Group__6 : rule__MineRingConfig__Group__6__Impl rule__MineRingConfig__Group__7 ;
    public final void rule__MineRingConfig__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7598:1: ( rule__MineRingConfig__Group__6__Impl rule__MineRingConfig__Group__7 )
            // InternalMazeDsl.g:7599:2: rule__MineRingConfig__Group__6__Impl rule__MineRingConfig__Group__7
            {
            pushFollow(FOLLOW_17);
            rule__MineRingConfig__Group__6__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__MineRingConfig__Group__7();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MineRingConfig__Group__6"


    // $ANTLR start "rule__MineRingConfig__Group__6__Impl"
    // InternalMazeDsl.g:7606:1: rule__MineRingConfig__Group__6__Impl : ( 'triggerRadius' ) ;
    public final void rule__MineRingConfig__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7610:1: ( ( 'triggerRadius' ) )
            // InternalMazeDsl.g:7611:1: ( 'triggerRadius' )
            {
            // InternalMazeDsl.g:7611:1: ( 'triggerRadius' )
            // InternalMazeDsl.g:7612:2: 'triggerRadius'
            {
             before(grammarAccess.getMineRingConfigAccess().getTriggerRadiusKeyword_6()); 
            match(input,105,FOLLOW_2); 
             after(grammarAccess.getMineRingConfigAccess().getTriggerRadiusKeyword_6()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MineRingConfig__Group__6__Impl"


    // $ANTLR start "rule__MineRingConfig__Group__7"
    // InternalMazeDsl.g:7621:1: rule__MineRingConfig__Group__7 : rule__MineRingConfig__Group__7__Impl rule__MineRingConfig__Group__8 ;
    public final void rule__MineRingConfig__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7625:1: ( rule__MineRingConfig__Group__7__Impl rule__MineRingConfig__Group__8 )
            // InternalMazeDsl.g:7626:2: rule__MineRingConfig__Group__7__Impl rule__MineRingConfig__Group__8
            {
            pushFollow(FOLLOW_68);
            rule__MineRingConfig__Group__7__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__MineRingConfig__Group__8();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MineRingConfig__Group__7"


    // $ANTLR start "rule__MineRingConfig__Group__7__Impl"
    // InternalMazeDsl.g:7633:1: rule__MineRingConfig__Group__7__Impl : ( ( rule__MineRingConfig__TriggerRadiusAssignment_7 ) ) ;
    public final void rule__MineRingConfig__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7637:1: ( ( ( rule__MineRingConfig__TriggerRadiusAssignment_7 ) ) )
            // InternalMazeDsl.g:7638:1: ( ( rule__MineRingConfig__TriggerRadiusAssignment_7 ) )
            {
            // InternalMazeDsl.g:7638:1: ( ( rule__MineRingConfig__TriggerRadiusAssignment_7 ) )
            // InternalMazeDsl.g:7639:2: ( rule__MineRingConfig__TriggerRadiusAssignment_7 )
            {
             before(grammarAccess.getMineRingConfigAccess().getTriggerRadiusAssignment_7()); 
            // InternalMazeDsl.g:7640:2: ( rule__MineRingConfig__TriggerRadiusAssignment_7 )
            // InternalMazeDsl.g:7640:3: rule__MineRingConfig__TriggerRadiusAssignment_7
            {
            pushFollow(FOLLOW_2);
            rule__MineRingConfig__TriggerRadiusAssignment_7();

            state._fsp--;


            }

             after(grammarAccess.getMineRingConfigAccess().getTriggerRadiusAssignment_7()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MineRingConfig__Group__7__Impl"


    // $ANTLR start "rule__MineRingConfig__Group__8"
    // InternalMazeDsl.g:7648:1: rule__MineRingConfig__Group__8 : rule__MineRingConfig__Group__8__Impl rule__MineRingConfig__Group__9 ;
    public final void rule__MineRingConfig__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7652:1: ( rule__MineRingConfig__Group__8__Impl rule__MineRingConfig__Group__9 )
            // InternalMazeDsl.g:7653:2: rule__MineRingConfig__Group__8__Impl rule__MineRingConfig__Group__9
            {
            pushFollow(FOLLOW_17);
            rule__MineRingConfig__Group__8__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__MineRingConfig__Group__9();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MineRingConfig__Group__8"


    // $ANTLR start "rule__MineRingConfig__Group__8__Impl"
    // InternalMazeDsl.g:7660:1: rule__MineRingConfig__Group__8__Impl : ( 'pulse' ) ;
    public final void rule__MineRingConfig__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7664:1: ( ( 'pulse' ) )
            // InternalMazeDsl.g:7665:1: ( 'pulse' )
            {
            // InternalMazeDsl.g:7665:1: ( 'pulse' )
            // InternalMazeDsl.g:7666:2: 'pulse'
            {
             before(grammarAccess.getMineRingConfigAccess().getPulseKeyword_8()); 
            match(input,106,FOLLOW_2); 
             after(grammarAccess.getMineRingConfigAccess().getPulseKeyword_8()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MineRingConfig__Group__8__Impl"


    // $ANTLR start "rule__MineRingConfig__Group__9"
    // InternalMazeDsl.g:7675:1: rule__MineRingConfig__Group__9 : rule__MineRingConfig__Group__9__Impl rule__MineRingConfig__Group__10 ;
    public final void rule__MineRingConfig__Group__9() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7679:1: ( rule__MineRingConfig__Group__9__Impl rule__MineRingConfig__Group__10 )
            // InternalMazeDsl.g:7680:2: rule__MineRingConfig__Group__9__Impl rule__MineRingConfig__Group__10
            {
            pushFollow(FOLLOW_69);
            rule__MineRingConfig__Group__9__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__MineRingConfig__Group__10();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MineRingConfig__Group__9"


    // $ANTLR start "rule__MineRingConfig__Group__9__Impl"
    // InternalMazeDsl.g:7687:1: rule__MineRingConfig__Group__9__Impl : ( ( rule__MineRingConfig__PulseSecondsAssignment_9 ) ) ;
    public final void rule__MineRingConfig__Group__9__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7691:1: ( ( ( rule__MineRingConfig__PulseSecondsAssignment_9 ) ) )
            // InternalMazeDsl.g:7692:1: ( ( rule__MineRingConfig__PulseSecondsAssignment_9 ) )
            {
            // InternalMazeDsl.g:7692:1: ( ( rule__MineRingConfig__PulseSecondsAssignment_9 ) )
            // InternalMazeDsl.g:7693:2: ( rule__MineRingConfig__PulseSecondsAssignment_9 )
            {
             before(grammarAccess.getMineRingConfigAccess().getPulseSecondsAssignment_9()); 
            // InternalMazeDsl.g:7694:2: ( rule__MineRingConfig__PulseSecondsAssignment_9 )
            // InternalMazeDsl.g:7694:3: rule__MineRingConfig__PulseSecondsAssignment_9
            {
            pushFollow(FOLLOW_2);
            rule__MineRingConfig__PulseSecondsAssignment_9();

            state._fsp--;


            }

             after(grammarAccess.getMineRingConfigAccess().getPulseSecondsAssignment_9()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MineRingConfig__Group__9__Impl"


    // $ANTLR start "rule__MineRingConfig__Group__10"
    // InternalMazeDsl.g:7702:1: rule__MineRingConfig__Group__10 : rule__MineRingConfig__Group__10__Impl rule__MineRingConfig__Group__11 ;
    public final void rule__MineRingConfig__Group__10() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7706:1: ( rule__MineRingConfig__Group__10__Impl rule__MineRingConfig__Group__11 )
            // InternalMazeDsl.g:7707:2: rule__MineRingConfig__Group__10__Impl rule__MineRingConfig__Group__11
            {
            pushFollow(FOLLOW_17);
            rule__MineRingConfig__Group__10__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__MineRingConfig__Group__11();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MineRingConfig__Group__10"


    // $ANTLR start "rule__MineRingConfig__Group__10__Impl"
    // InternalMazeDsl.g:7714:1: rule__MineRingConfig__Group__10__Impl : ( 'blastRadius' ) ;
    public final void rule__MineRingConfig__Group__10__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7718:1: ( ( 'blastRadius' ) )
            // InternalMazeDsl.g:7719:1: ( 'blastRadius' )
            {
            // InternalMazeDsl.g:7719:1: ( 'blastRadius' )
            // InternalMazeDsl.g:7720:2: 'blastRadius'
            {
             before(grammarAccess.getMineRingConfigAccess().getBlastRadiusKeyword_10()); 
            match(input,107,FOLLOW_2); 
             after(grammarAccess.getMineRingConfigAccess().getBlastRadiusKeyword_10()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MineRingConfig__Group__10__Impl"


    // $ANTLR start "rule__MineRingConfig__Group__11"
    // InternalMazeDsl.g:7729:1: rule__MineRingConfig__Group__11 : rule__MineRingConfig__Group__11__Impl rule__MineRingConfig__Group__12 ;
    public final void rule__MineRingConfig__Group__11() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7733:1: ( rule__MineRingConfig__Group__11__Impl rule__MineRingConfig__Group__12 )
            // InternalMazeDsl.g:7734:2: rule__MineRingConfig__Group__11__Impl rule__MineRingConfig__Group__12
            {
            pushFollow(FOLLOW_70);
            rule__MineRingConfig__Group__11__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__MineRingConfig__Group__12();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MineRingConfig__Group__11"


    // $ANTLR start "rule__MineRingConfig__Group__11__Impl"
    // InternalMazeDsl.g:7741:1: rule__MineRingConfig__Group__11__Impl : ( ( rule__MineRingConfig__BlastRadiusAssignment_11 ) ) ;
    public final void rule__MineRingConfig__Group__11__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7745:1: ( ( ( rule__MineRingConfig__BlastRadiusAssignment_11 ) ) )
            // InternalMazeDsl.g:7746:1: ( ( rule__MineRingConfig__BlastRadiusAssignment_11 ) )
            {
            // InternalMazeDsl.g:7746:1: ( ( rule__MineRingConfig__BlastRadiusAssignment_11 ) )
            // InternalMazeDsl.g:7747:2: ( rule__MineRingConfig__BlastRadiusAssignment_11 )
            {
             before(grammarAccess.getMineRingConfigAccess().getBlastRadiusAssignment_11()); 
            // InternalMazeDsl.g:7748:2: ( rule__MineRingConfig__BlastRadiusAssignment_11 )
            // InternalMazeDsl.g:7748:3: rule__MineRingConfig__BlastRadiusAssignment_11
            {
            pushFollow(FOLLOW_2);
            rule__MineRingConfig__BlastRadiusAssignment_11();

            state._fsp--;


            }

             after(grammarAccess.getMineRingConfigAccess().getBlastRadiusAssignment_11()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MineRingConfig__Group__11__Impl"


    // $ANTLR start "rule__MineRingConfig__Group__12"
    // InternalMazeDsl.g:7756:1: rule__MineRingConfig__Group__12 : rule__MineRingConfig__Group__12__Impl rule__MineRingConfig__Group__13 ;
    public final void rule__MineRingConfig__Group__12() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7760:1: ( rule__MineRingConfig__Group__12__Impl rule__MineRingConfig__Group__13 )
            // InternalMazeDsl.g:7761:2: rule__MineRingConfig__Group__12__Impl rule__MineRingConfig__Group__13
            {
            pushFollow(FOLLOW_26);
            rule__MineRingConfig__Group__12__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__MineRingConfig__Group__13();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MineRingConfig__Group__12"


    // $ANTLR start "rule__MineRingConfig__Group__12__Impl"
    // InternalMazeDsl.g:7768:1: rule__MineRingConfig__Group__12__Impl : ( 'blastDamage' ) ;
    public final void rule__MineRingConfig__Group__12__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7772:1: ( ( 'blastDamage' ) )
            // InternalMazeDsl.g:7773:1: ( 'blastDamage' )
            {
            // InternalMazeDsl.g:7773:1: ( 'blastDamage' )
            // InternalMazeDsl.g:7774:2: 'blastDamage'
            {
             before(grammarAccess.getMineRingConfigAccess().getBlastDamageKeyword_12()); 
            match(input,108,FOLLOW_2); 
             after(grammarAccess.getMineRingConfigAccess().getBlastDamageKeyword_12()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MineRingConfig__Group__12__Impl"


    // $ANTLR start "rule__MineRingConfig__Group__13"
    // InternalMazeDsl.g:7783:1: rule__MineRingConfig__Group__13 : rule__MineRingConfig__Group__13__Impl rule__MineRingConfig__Group__14 ;
    public final void rule__MineRingConfig__Group__13() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7787:1: ( rule__MineRingConfig__Group__13__Impl rule__MineRingConfig__Group__14 )
            // InternalMazeDsl.g:7788:2: rule__MineRingConfig__Group__13__Impl rule__MineRingConfig__Group__14
            {
            pushFollow(FOLLOW_71);
            rule__MineRingConfig__Group__13__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__MineRingConfig__Group__14();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MineRingConfig__Group__13"


    // $ANTLR start "rule__MineRingConfig__Group__13__Impl"
    // InternalMazeDsl.g:7795:1: rule__MineRingConfig__Group__13__Impl : ( ( rule__MineRingConfig__BlastDamageAssignment_13 ) ) ;
    public final void rule__MineRingConfig__Group__13__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7799:1: ( ( ( rule__MineRingConfig__BlastDamageAssignment_13 ) ) )
            // InternalMazeDsl.g:7800:1: ( ( rule__MineRingConfig__BlastDamageAssignment_13 ) )
            {
            // InternalMazeDsl.g:7800:1: ( ( rule__MineRingConfig__BlastDamageAssignment_13 ) )
            // InternalMazeDsl.g:7801:2: ( rule__MineRingConfig__BlastDamageAssignment_13 )
            {
             before(grammarAccess.getMineRingConfigAccess().getBlastDamageAssignment_13()); 
            // InternalMazeDsl.g:7802:2: ( rule__MineRingConfig__BlastDamageAssignment_13 )
            // InternalMazeDsl.g:7802:3: rule__MineRingConfig__BlastDamageAssignment_13
            {
            pushFollow(FOLLOW_2);
            rule__MineRingConfig__BlastDamageAssignment_13();

            state._fsp--;


            }

             after(grammarAccess.getMineRingConfigAccess().getBlastDamageAssignment_13()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MineRingConfig__Group__13__Impl"


    // $ANTLR start "rule__MineRingConfig__Group__14"
    // InternalMazeDsl.g:7810:1: rule__MineRingConfig__Group__14 : rule__MineRingConfig__Group__14__Impl rule__MineRingConfig__Group__15 ;
    public final void rule__MineRingConfig__Group__14() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7814:1: ( rule__MineRingConfig__Group__14__Impl rule__MineRingConfig__Group__15 )
            // InternalMazeDsl.g:7815:2: rule__MineRingConfig__Group__14__Impl rule__MineRingConfig__Group__15
            {
            pushFollow(FOLLOW_26);
            rule__MineRingConfig__Group__14__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__MineRingConfig__Group__15();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MineRingConfig__Group__14"


    // $ANTLR start "rule__MineRingConfig__Group__14__Impl"
    // InternalMazeDsl.g:7822:1: rule__MineRingConfig__Group__14__Impl : ( 'playerDamage' ) ;
    public final void rule__MineRingConfig__Group__14__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7826:1: ( ( 'playerDamage' ) )
            // InternalMazeDsl.g:7827:1: ( 'playerDamage' )
            {
            // InternalMazeDsl.g:7827:1: ( 'playerDamage' )
            // InternalMazeDsl.g:7828:2: 'playerDamage'
            {
             before(grammarAccess.getMineRingConfigAccess().getPlayerDamageKeyword_14()); 
            match(input,109,FOLLOW_2); 
             after(grammarAccess.getMineRingConfigAccess().getPlayerDamageKeyword_14()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MineRingConfig__Group__14__Impl"


    // $ANTLR start "rule__MineRingConfig__Group__15"
    // InternalMazeDsl.g:7837:1: rule__MineRingConfig__Group__15 : rule__MineRingConfig__Group__15__Impl rule__MineRingConfig__Group__16 ;
    public final void rule__MineRingConfig__Group__15() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7841:1: ( rule__MineRingConfig__Group__15__Impl rule__MineRingConfig__Group__16 )
            // InternalMazeDsl.g:7842:2: rule__MineRingConfig__Group__15__Impl rule__MineRingConfig__Group__16
            {
            pushFollow(FOLLOW_72);
            rule__MineRingConfig__Group__15__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__MineRingConfig__Group__16();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MineRingConfig__Group__15"


    // $ANTLR start "rule__MineRingConfig__Group__15__Impl"
    // InternalMazeDsl.g:7849:1: rule__MineRingConfig__Group__15__Impl : ( ( rule__MineRingConfig__PlayerDamageAssignment_15 ) ) ;
    public final void rule__MineRingConfig__Group__15__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7853:1: ( ( ( rule__MineRingConfig__PlayerDamageAssignment_15 ) ) )
            // InternalMazeDsl.g:7854:1: ( ( rule__MineRingConfig__PlayerDamageAssignment_15 ) )
            {
            // InternalMazeDsl.g:7854:1: ( ( rule__MineRingConfig__PlayerDamageAssignment_15 ) )
            // InternalMazeDsl.g:7855:2: ( rule__MineRingConfig__PlayerDamageAssignment_15 )
            {
             before(grammarAccess.getMineRingConfigAccess().getPlayerDamageAssignment_15()); 
            // InternalMazeDsl.g:7856:2: ( rule__MineRingConfig__PlayerDamageAssignment_15 )
            // InternalMazeDsl.g:7856:3: rule__MineRingConfig__PlayerDamageAssignment_15
            {
            pushFollow(FOLLOW_2);
            rule__MineRingConfig__PlayerDamageAssignment_15();

            state._fsp--;


            }

             after(grammarAccess.getMineRingConfigAccess().getPlayerDamageAssignment_15()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MineRingConfig__Group__15__Impl"


    // $ANTLR start "rule__MineRingConfig__Group__16"
    // InternalMazeDsl.g:7864:1: rule__MineRingConfig__Group__16 : rule__MineRingConfig__Group__16__Impl rule__MineRingConfig__Group__17 ;
    public final void rule__MineRingConfig__Group__16() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7868:1: ( rule__MineRingConfig__Group__16__Impl rule__MineRingConfig__Group__17 )
            // InternalMazeDsl.g:7869:2: rule__MineRingConfig__Group__16__Impl rule__MineRingConfig__Group__17
            {
            pushFollow(FOLLOW_17);
            rule__MineRingConfig__Group__16__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__MineRingConfig__Group__17();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MineRingConfig__Group__16"


    // $ANTLR start "rule__MineRingConfig__Group__16__Impl"
    // InternalMazeDsl.g:7876:1: rule__MineRingConfig__Group__16__Impl : ( 'holeRadius' ) ;
    public final void rule__MineRingConfig__Group__16__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7880:1: ( ( 'holeRadius' ) )
            // InternalMazeDsl.g:7881:1: ( 'holeRadius' )
            {
            // InternalMazeDsl.g:7881:1: ( 'holeRadius' )
            // InternalMazeDsl.g:7882:2: 'holeRadius'
            {
             before(grammarAccess.getMineRingConfigAccess().getHoleRadiusKeyword_16()); 
            match(input,110,FOLLOW_2); 
             after(grammarAccess.getMineRingConfigAccess().getHoleRadiusKeyword_16()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MineRingConfig__Group__16__Impl"


    // $ANTLR start "rule__MineRingConfig__Group__17"
    // InternalMazeDsl.g:7891:1: rule__MineRingConfig__Group__17 : rule__MineRingConfig__Group__17__Impl rule__MineRingConfig__Group__18 ;
    public final void rule__MineRingConfig__Group__17() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7895:1: ( rule__MineRingConfig__Group__17__Impl rule__MineRingConfig__Group__18 )
            // InternalMazeDsl.g:7896:2: rule__MineRingConfig__Group__17__Impl rule__MineRingConfig__Group__18
            {
            pushFollow(FOLLOW_37);
            rule__MineRingConfig__Group__17__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__MineRingConfig__Group__18();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MineRingConfig__Group__17"


    // $ANTLR start "rule__MineRingConfig__Group__17__Impl"
    // InternalMazeDsl.g:7903:1: rule__MineRingConfig__Group__17__Impl : ( ( rule__MineRingConfig__HoleRadiusAssignment_17 ) ) ;
    public final void rule__MineRingConfig__Group__17__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7907:1: ( ( ( rule__MineRingConfig__HoleRadiusAssignment_17 ) ) )
            // InternalMazeDsl.g:7908:1: ( ( rule__MineRingConfig__HoleRadiusAssignment_17 ) )
            {
            // InternalMazeDsl.g:7908:1: ( ( rule__MineRingConfig__HoleRadiusAssignment_17 ) )
            // InternalMazeDsl.g:7909:2: ( rule__MineRingConfig__HoleRadiusAssignment_17 )
            {
             before(grammarAccess.getMineRingConfigAccess().getHoleRadiusAssignment_17()); 
            // InternalMazeDsl.g:7910:2: ( rule__MineRingConfig__HoleRadiusAssignment_17 )
            // InternalMazeDsl.g:7910:3: rule__MineRingConfig__HoleRadiusAssignment_17
            {
            pushFollow(FOLLOW_2);
            rule__MineRingConfig__HoleRadiusAssignment_17();

            state._fsp--;


            }

             after(grammarAccess.getMineRingConfigAccess().getHoleRadiusAssignment_17()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MineRingConfig__Group__17__Impl"


    // $ANTLR start "rule__MineRingConfig__Group__18"
    // InternalMazeDsl.g:7918:1: rule__MineRingConfig__Group__18 : rule__MineRingConfig__Group__18__Impl ;
    public final void rule__MineRingConfig__Group__18() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7922:1: ( rule__MineRingConfig__Group__18__Impl )
            // InternalMazeDsl.g:7923:2: rule__MineRingConfig__Group__18__Impl
            {
            pushFollow(FOLLOW_2);
            rule__MineRingConfig__Group__18__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MineRingConfig__Group__18"


    // $ANTLR start "rule__MineRingConfig__Group__18__Impl"
    // InternalMazeDsl.g:7929:1: rule__MineRingConfig__Group__18__Impl : ( '}' ) ;
    public final void rule__MineRingConfig__Group__18__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7933:1: ( ( '}' ) )
            // InternalMazeDsl.g:7934:1: ( '}' )
            {
            // InternalMazeDsl.g:7934:1: ( '}' )
            // InternalMazeDsl.g:7935:2: '}'
            {
             before(grammarAccess.getMineRingConfigAccess().getRightCurlyBracketKeyword_18()); 
            match(input,33,FOLLOW_2); 
             after(grammarAccess.getMineRingConfigAccess().getRightCurlyBracketKeyword_18()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MineRingConfig__Group__18__Impl"


    // $ANTLR start "rule__PortalGhostConfig__Group__0"
    // InternalMazeDsl.g:7945:1: rule__PortalGhostConfig__Group__0 : rule__PortalGhostConfig__Group__0__Impl rule__PortalGhostConfig__Group__1 ;
    public final void rule__PortalGhostConfig__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7949:1: ( rule__PortalGhostConfig__Group__0__Impl rule__PortalGhostConfig__Group__1 )
            // InternalMazeDsl.g:7950:2: rule__PortalGhostConfig__Group__0__Impl rule__PortalGhostConfig__Group__1
            {
            pushFollow(FOLLOW_11);
            rule__PortalGhostConfig__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PortalGhostConfig__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PortalGhostConfig__Group__0"


    // $ANTLR start "rule__PortalGhostConfig__Group__0__Impl"
    // InternalMazeDsl.g:7957:1: rule__PortalGhostConfig__Group__0__Impl : ( 'portalGhost' ) ;
    public final void rule__PortalGhostConfig__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7961:1: ( ( 'portalGhost' ) )
            // InternalMazeDsl.g:7962:1: ( 'portalGhost' )
            {
            // InternalMazeDsl.g:7962:1: ( 'portalGhost' )
            // InternalMazeDsl.g:7963:2: 'portalGhost'
            {
             before(grammarAccess.getPortalGhostConfigAccess().getPortalGhostKeyword_0()); 
            match(input,111,FOLLOW_2); 
             after(grammarAccess.getPortalGhostConfigAccess().getPortalGhostKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PortalGhostConfig__Group__0__Impl"


    // $ANTLR start "rule__PortalGhostConfig__Group__1"
    // InternalMazeDsl.g:7972:1: rule__PortalGhostConfig__Group__1 : rule__PortalGhostConfig__Group__1__Impl rule__PortalGhostConfig__Group__2 ;
    public final void rule__PortalGhostConfig__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7976:1: ( rule__PortalGhostConfig__Group__1__Impl rule__PortalGhostConfig__Group__2 )
            // InternalMazeDsl.g:7977:2: rule__PortalGhostConfig__Group__1__Impl rule__PortalGhostConfig__Group__2
            {
            pushFollow(FOLLOW_4);
            rule__PortalGhostConfig__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PortalGhostConfig__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PortalGhostConfig__Group__1"


    // $ANTLR start "rule__PortalGhostConfig__Group__1__Impl"
    // InternalMazeDsl.g:7984:1: rule__PortalGhostConfig__Group__1__Impl : ( ( rule__PortalGhostConfig__DisplayNameAssignment_1 ) ) ;
    public final void rule__PortalGhostConfig__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7988:1: ( ( ( rule__PortalGhostConfig__DisplayNameAssignment_1 ) ) )
            // InternalMazeDsl.g:7989:1: ( ( rule__PortalGhostConfig__DisplayNameAssignment_1 ) )
            {
            // InternalMazeDsl.g:7989:1: ( ( rule__PortalGhostConfig__DisplayNameAssignment_1 ) )
            // InternalMazeDsl.g:7990:2: ( rule__PortalGhostConfig__DisplayNameAssignment_1 )
            {
             before(grammarAccess.getPortalGhostConfigAccess().getDisplayNameAssignment_1()); 
            // InternalMazeDsl.g:7991:2: ( rule__PortalGhostConfig__DisplayNameAssignment_1 )
            // InternalMazeDsl.g:7991:3: rule__PortalGhostConfig__DisplayNameAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__PortalGhostConfig__DisplayNameAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getPortalGhostConfigAccess().getDisplayNameAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PortalGhostConfig__Group__1__Impl"


    // $ANTLR start "rule__PortalGhostConfig__Group__2"
    // InternalMazeDsl.g:7999:1: rule__PortalGhostConfig__Group__2 : rule__PortalGhostConfig__Group__2__Impl rule__PortalGhostConfig__Group__3 ;
    public final void rule__PortalGhostConfig__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8003:1: ( rule__PortalGhostConfig__Group__2__Impl rule__PortalGhostConfig__Group__3 )
            // InternalMazeDsl.g:8004:2: rule__PortalGhostConfig__Group__2__Impl rule__PortalGhostConfig__Group__3
            {
            pushFollow(FOLLOW_55);
            rule__PortalGhostConfig__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PortalGhostConfig__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PortalGhostConfig__Group__2"


    // $ANTLR start "rule__PortalGhostConfig__Group__2__Impl"
    // InternalMazeDsl.g:8011:1: rule__PortalGhostConfig__Group__2__Impl : ( '{' ) ;
    public final void rule__PortalGhostConfig__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8015:1: ( ( '{' ) )
            // InternalMazeDsl.g:8016:1: ( '{' )
            {
            // InternalMazeDsl.g:8016:1: ( '{' )
            // InternalMazeDsl.g:8017:2: '{'
            {
             before(grammarAccess.getPortalGhostConfigAccess().getLeftCurlyBracketKeyword_2()); 
            match(input,32,FOLLOW_2); 
             after(grammarAccess.getPortalGhostConfigAccess().getLeftCurlyBracketKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PortalGhostConfig__Group__2__Impl"


    // $ANTLR start "rule__PortalGhostConfig__Group__3"
    // InternalMazeDsl.g:8026:1: rule__PortalGhostConfig__Group__3 : rule__PortalGhostConfig__Group__3__Impl rule__PortalGhostConfig__Group__4 ;
    public final void rule__PortalGhostConfig__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8030:1: ( rule__PortalGhostConfig__Group__3__Impl rule__PortalGhostConfig__Group__4 )
            // InternalMazeDsl.g:8031:2: rule__PortalGhostConfig__Group__3__Impl rule__PortalGhostConfig__Group__4
            {
            pushFollow(FOLLOW_26);
            rule__PortalGhostConfig__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PortalGhostConfig__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PortalGhostConfig__Group__3"


    // $ANTLR start "rule__PortalGhostConfig__Group__3__Impl"
    // InternalMazeDsl.g:8038:1: rule__PortalGhostConfig__Group__3__Impl : ( 'health' ) ;
    public final void rule__PortalGhostConfig__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8042:1: ( ( 'health' ) )
            // InternalMazeDsl.g:8043:1: ( 'health' )
            {
            // InternalMazeDsl.g:8043:1: ( 'health' )
            // InternalMazeDsl.g:8044:2: 'health'
            {
             before(grammarAccess.getPortalGhostConfigAccess().getHealthKeyword_3()); 
            match(input,46,FOLLOW_2); 
             after(grammarAccess.getPortalGhostConfigAccess().getHealthKeyword_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PortalGhostConfig__Group__3__Impl"


    // $ANTLR start "rule__PortalGhostConfig__Group__4"
    // InternalMazeDsl.g:8053:1: rule__PortalGhostConfig__Group__4 : rule__PortalGhostConfig__Group__4__Impl rule__PortalGhostConfig__Group__5 ;
    public final void rule__PortalGhostConfig__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8057:1: ( rule__PortalGhostConfig__Group__4__Impl rule__PortalGhostConfig__Group__5 )
            // InternalMazeDsl.g:8058:2: rule__PortalGhostConfig__Group__4__Impl rule__PortalGhostConfig__Group__5
            {
            pushFollow(FOLLOW_73);
            rule__PortalGhostConfig__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PortalGhostConfig__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PortalGhostConfig__Group__4"


    // $ANTLR start "rule__PortalGhostConfig__Group__4__Impl"
    // InternalMazeDsl.g:8065:1: rule__PortalGhostConfig__Group__4__Impl : ( ( rule__PortalGhostConfig__HealthAssignment_4 ) ) ;
    public final void rule__PortalGhostConfig__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8069:1: ( ( ( rule__PortalGhostConfig__HealthAssignment_4 ) ) )
            // InternalMazeDsl.g:8070:1: ( ( rule__PortalGhostConfig__HealthAssignment_4 ) )
            {
            // InternalMazeDsl.g:8070:1: ( ( rule__PortalGhostConfig__HealthAssignment_4 ) )
            // InternalMazeDsl.g:8071:2: ( rule__PortalGhostConfig__HealthAssignment_4 )
            {
             before(grammarAccess.getPortalGhostConfigAccess().getHealthAssignment_4()); 
            // InternalMazeDsl.g:8072:2: ( rule__PortalGhostConfig__HealthAssignment_4 )
            // InternalMazeDsl.g:8072:3: rule__PortalGhostConfig__HealthAssignment_4
            {
            pushFollow(FOLLOW_2);
            rule__PortalGhostConfig__HealthAssignment_4();

            state._fsp--;


            }

             after(grammarAccess.getPortalGhostConfigAccess().getHealthAssignment_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PortalGhostConfig__Group__4__Impl"


    // $ANTLR start "rule__PortalGhostConfig__Group__5"
    // InternalMazeDsl.g:8080:1: rule__PortalGhostConfig__Group__5 : rule__PortalGhostConfig__Group__5__Impl rule__PortalGhostConfig__Group__6 ;
    public final void rule__PortalGhostConfig__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8084:1: ( rule__PortalGhostConfig__Group__5__Impl rule__PortalGhostConfig__Group__6 )
            // InternalMazeDsl.g:8085:2: rule__PortalGhostConfig__Group__5__Impl rule__PortalGhostConfig__Group__6
            {
            pushFollow(FOLLOW_17);
            rule__PortalGhostConfig__Group__5__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PortalGhostConfig__Group__6();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PortalGhostConfig__Group__5"


    // $ANTLR start "rule__PortalGhostConfig__Group__5__Impl"
    // InternalMazeDsl.g:8092:1: rule__PortalGhostConfig__Group__5__Impl : ( 'speed' ) ;
    public final void rule__PortalGhostConfig__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8096:1: ( ( 'speed' ) )
            // InternalMazeDsl.g:8097:1: ( 'speed' )
            {
            // InternalMazeDsl.g:8097:1: ( 'speed' )
            // InternalMazeDsl.g:8098:2: 'speed'
            {
             before(grammarAccess.getPortalGhostConfigAccess().getSpeedKeyword_5()); 
            match(input,47,FOLLOW_2); 
             after(grammarAccess.getPortalGhostConfigAccess().getSpeedKeyword_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PortalGhostConfig__Group__5__Impl"


    // $ANTLR start "rule__PortalGhostConfig__Group__6"
    // InternalMazeDsl.g:8107:1: rule__PortalGhostConfig__Group__6 : rule__PortalGhostConfig__Group__6__Impl rule__PortalGhostConfig__Group__7 ;
    public final void rule__PortalGhostConfig__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8111:1: ( rule__PortalGhostConfig__Group__6__Impl rule__PortalGhostConfig__Group__7 )
            // InternalMazeDsl.g:8112:2: rule__PortalGhostConfig__Group__6__Impl rule__PortalGhostConfig__Group__7
            {
            pushFollow(FOLLOW_61);
            rule__PortalGhostConfig__Group__6__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PortalGhostConfig__Group__7();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PortalGhostConfig__Group__6"


    // $ANTLR start "rule__PortalGhostConfig__Group__6__Impl"
    // InternalMazeDsl.g:8119:1: rule__PortalGhostConfig__Group__6__Impl : ( ( rule__PortalGhostConfig__SpeedAssignment_6 ) ) ;
    public final void rule__PortalGhostConfig__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8123:1: ( ( ( rule__PortalGhostConfig__SpeedAssignment_6 ) ) )
            // InternalMazeDsl.g:8124:1: ( ( rule__PortalGhostConfig__SpeedAssignment_6 ) )
            {
            // InternalMazeDsl.g:8124:1: ( ( rule__PortalGhostConfig__SpeedAssignment_6 ) )
            // InternalMazeDsl.g:8125:2: ( rule__PortalGhostConfig__SpeedAssignment_6 )
            {
             before(grammarAccess.getPortalGhostConfigAccess().getSpeedAssignment_6()); 
            // InternalMazeDsl.g:8126:2: ( rule__PortalGhostConfig__SpeedAssignment_6 )
            // InternalMazeDsl.g:8126:3: rule__PortalGhostConfig__SpeedAssignment_6
            {
            pushFollow(FOLLOW_2);
            rule__PortalGhostConfig__SpeedAssignment_6();

            state._fsp--;


            }

             after(grammarAccess.getPortalGhostConfigAccess().getSpeedAssignment_6()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PortalGhostConfig__Group__6__Impl"


    // $ANTLR start "rule__PortalGhostConfig__Group__7"
    // InternalMazeDsl.g:8134:1: rule__PortalGhostConfig__Group__7 : rule__PortalGhostConfig__Group__7__Impl rule__PortalGhostConfig__Group__8 ;
    public final void rule__PortalGhostConfig__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8138:1: ( rule__PortalGhostConfig__Group__7__Impl rule__PortalGhostConfig__Group__8 )
            // InternalMazeDsl.g:8139:2: rule__PortalGhostConfig__Group__7__Impl rule__PortalGhostConfig__Group__8
            {
            pushFollow(FOLLOW_11);
            rule__PortalGhostConfig__Group__7__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PortalGhostConfig__Group__8();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PortalGhostConfig__Group__7"


    // $ANTLR start "rule__PortalGhostConfig__Group__7__Impl"
    // InternalMazeDsl.g:8146:1: rule__PortalGhostConfig__Group__7__Impl : ( 'sprite' ) ;
    public final void rule__PortalGhostConfig__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8150:1: ( ( 'sprite' ) )
            // InternalMazeDsl.g:8151:1: ( 'sprite' )
            {
            // InternalMazeDsl.g:8151:1: ( 'sprite' )
            // InternalMazeDsl.g:8152:2: 'sprite'
            {
             before(grammarAccess.getPortalGhostConfigAccess().getSpriteKeyword_7()); 
            match(input,97,FOLLOW_2); 
             after(grammarAccess.getPortalGhostConfigAccess().getSpriteKeyword_7()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PortalGhostConfig__Group__7__Impl"


    // $ANTLR start "rule__PortalGhostConfig__Group__8"
    // InternalMazeDsl.g:8161:1: rule__PortalGhostConfig__Group__8 : rule__PortalGhostConfig__Group__8__Impl rule__PortalGhostConfig__Group__9 ;
    public final void rule__PortalGhostConfig__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8165:1: ( rule__PortalGhostConfig__Group__8__Impl rule__PortalGhostConfig__Group__9 )
            // InternalMazeDsl.g:8166:2: rule__PortalGhostConfig__Group__8__Impl rule__PortalGhostConfig__Group__9
            {
            pushFollow(FOLLOW_37);
            rule__PortalGhostConfig__Group__8__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PortalGhostConfig__Group__9();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PortalGhostConfig__Group__8"


    // $ANTLR start "rule__PortalGhostConfig__Group__8__Impl"
    // InternalMazeDsl.g:8173:1: rule__PortalGhostConfig__Group__8__Impl : ( ( rule__PortalGhostConfig__SpriteAssignment_8 ) ) ;
    public final void rule__PortalGhostConfig__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8177:1: ( ( ( rule__PortalGhostConfig__SpriteAssignment_8 ) ) )
            // InternalMazeDsl.g:8178:1: ( ( rule__PortalGhostConfig__SpriteAssignment_8 ) )
            {
            // InternalMazeDsl.g:8178:1: ( ( rule__PortalGhostConfig__SpriteAssignment_8 ) )
            // InternalMazeDsl.g:8179:2: ( rule__PortalGhostConfig__SpriteAssignment_8 )
            {
             before(grammarAccess.getPortalGhostConfigAccess().getSpriteAssignment_8()); 
            // InternalMazeDsl.g:8180:2: ( rule__PortalGhostConfig__SpriteAssignment_8 )
            // InternalMazeDsl.g:8180:3: rule__PortalGhostConfig__SpriteAssignment_8
            {
            pushFollow(FOLLOW_2);
            rule__PortalGhostConfig__SpriteAssignment_8();

            state._fsp--;


            }

             after(grammarAccess.getPortalGhostConfigAccess().getSpriteAssignment_8()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PortalGhostConfig__Group__8__Impl"


    // $ANTLR start "rule__PortalGhostConfig__Group__9"
    // InternalMazeDsl.g:8188:1: rule__PortalGhostConfig__Group__9 : rule__PortalGhostConfig__Group__9__Impl ;
    public final void rule__PortalGhostConfig__Group__9() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8192:1: ( rule__PortalGhostConfig__Group__9__Impl )
            // InternalMazeDsl.g:8193:2: rule__PortalGhostConfig__Group__9__Impl
            {
            pushFollow(FOLLOW_2);
            rule__PortalGhostConfig__Group__9__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PortalGhostConfig__Group__9"


    // $ANTLR start "rule__PortalGhostConfig__Group__9__Impl"
    // InternalMazeDsl.g:8199:1: rule__PortalGhostConfig__Group__9__Impl : ( '}' ) ;
    public final void rule__PortalGhostConfig__Group__9__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8203:1: ( ( '}' ) )
            // InternalMazeDsl.g:8204:1: ( '}' )
            {
            // InternalMazeDsl.g:8204:1: ( '}' )
            // InternalMazeDsl.g:8205:2: '}'
            {
             before(grammarAccess.getPortalGhostConfigAccess().getRightCurlyBracketKeyword_9()); 
            match(input,33,FOLLOW_2); 
             after(grammarAccess.getPortalGhostConfigAccess().getRightCurlyBracketKeyword_9()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PortalGhostConfig__Group__9__Impl"


    // $ANTLR start "rule__GhostCountConfig__Group__0"
    // InternalMazeDsl.g:8215:1: rule__GhostCountConfig__Group__0 : rule__GhostCountConfig__Group__0__Impl rule__GhostCountConfig__Group__1 ;
    public final void rule__GhostCountConfig__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8219:1: ( rule__GhostCountConfig__Group__0__Impl rule__GhostCountConfig__Group__1 )
            // InternalMazeDsl.g:8220:2: rule__GhostCountConfig__Group__0__Impl rule__GhostCountConfig__Group__1
            {
            pushFollow(FOLLOW_13);
            rule__GhostCountConfig__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__GhostCountConfig__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostCountConfig__Group__0"


    // $ANTLR start "rule__GhostCountConfig__Group__0__Impl"
    // InternalMazeDsl.g:8227:1: rule__GhostCountConfig__Group__0__Impl : ( 'portalGhosts' ) ;
    public final void rule__GhostCountConfig__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8231:1: ( ( 'portalGhosts' ) )
            // InternalMazeDsl.g:8232:1: ( 'portalGhosts' )
            {
            // InternalMazeDsl.g:8232:1: ( 'portalGhosts' )
            // InternalMazeDsl.g:8233:2: 'portalGhosts'
            {
             before(grammarAccess.getGhostCountConfigAccess().getPortalGhostsKeyword_0()); 
            match(input,112,FOLLOW_2); 
             after(grammarAccess.getGhostCountConfigAccess().getPortalGhostsKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostCountConfig__Group__0__Impl"


    // $ANTLR start "rule__GhostCountConfig__Group__1"
    // InternalMazeDsl.g:8242:1: rule__GhostCountConfig__Group__1 : rule__GhostCountConfig__Group__1__Impl rule__GhostCountConfig__Group__2 ;
    public final void rule__GhostCountConfig__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8246:1: ( rule__GhostCountConfig__Group__1__Impl rule__GhostCountConfig__Group__2 )
            // InternalMazeDsl.g:8247:2: rule__GhostCountConfig__Group__1__Impl rule__GhostCountConfig__Group__2
            {
            pushFollow(FOLLOW_26);
            rule__GhostCountConfig__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__GhostCountConfig__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostCountConfig__Group__1"


    // $ANTLR start "rule__GhostCountConfig__Group__1__Impl"
    // InternalMazeDsl.g:8254:1: rule__GhostCountConfig__Group__1__Impl : ( ( rule__GhostCountConfig__DifficultyAssignment_1 ) ) ;
    public final void rule__GhostCountConfig__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8258:1: ( ( ( rule__GhostCountConfig__DifficultyAssignment_1 ) ) )
            // InternalMazeDsl.g:8259:1: ( ( rule__GhostCountConfig__DifficultyAssignment_1 ) )
            {
            // InternalMazeDsl.g:8259:1: ( ( rule__GhostCountConfig__DifficultyAssignment_1 ) )
            // InternalMazeDsl.g:8260:2: ( rule__GhostCountConfig__DifficultyAssignment_1 )
            {
             before(grammarAccess.getGhostCountConfigAccess().getDifficultyAssignment_1()); 
            // InternalMazeDsl.g:8261:2: ( rule__GhostCountConfig__DifficultyAssignment_1 )
            // InternalMazeDsl.g:8261:3: rule__GhostCountConfig__DifficultyAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__GhostCountConfig__DifficultyAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getGhostCountConfigAccess().getDifficultyAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostCountConfig__Group__1__Impl"


    // $ANTLR start "rule__GhostCountConfig__Group__2"
    // InternalMazeDsl.g:8269:1: rule__GhostCountConfig__Group__2 : rule__GhostCountConfig__Group__2__Impl ;
    public final void rule__GhostCountConfig__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8273:1: ( rule__GhostCountConfig__Group__2__Impl )
            // InternalMazeDsl.g:8274:2: rule__GhostCountConfig__Group__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__GhostCountConfig__Group__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostCountConfig__Group__2"


    // $ANTLR start "rule__GhostCountConfig__Group__2__Impl"
    // InternalMazeDsl.g:8280:1: rule__GhostCountConfig__Group__2__Impl : ( ( rule__GhostCountConfig__CountAssignment_2 ) ) ;
    public final void rule__GhostCountConfig__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8284:1: ( ( ( rule__GhostCountConfig__CountAssignment_2 ) ) )
            // InternalMazeDsl.g:8285:1: ( ( rule__GhostCountConfig__CountAssignment_2 ) )
            {
            // InternalMazeDsl.g:8285:1: ( ( rule__GhostCountConfig__CountAssignment_2 ) )
            // InternalMazeDsl.g:8286:2: ( rule__GhostCountConfig__CountAssignment_2 )
            {
             before(grammarAccess.getGhostCountConfigAccess().getCountAssignment_2()); 
            // InternalMazeDsl.g:8287:2: ( rule__GhostCountConfig__CountAssignment_2 )
            // InternalMazeDsl.g:8287:3: rule__GhostCountConfig__CountAssignment_2
            {
            pushFollow(FOLLOW_2);
            rule__GhostCountConfig__CountAssignment_2();

            state._fsp--;


            }

             after(grammarAccess.getGhostCountConfigAccess().getCountAssignment_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostCountConfig__Group__2__Impl"


    // $ANTLR start "rule__TeleportRulesConfig__Group__0"
    // InternalMazeDsl.g:8296:1: rule__TeleportRulesConfig__Group__0 : rule__TeleportRulesConfig__Group__0__Impl rule__TeleportRulesConfig__Group__1 ;
    public final void rule__TeleportRulesConfig__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8300:1: ( rule__TeleportRulesConfig__Group__0__Impl rule__TeleportRulesConfig__Group__1 )
            // InternalMazeDsl.g:8301:2: rule__TeleportRulesConfig__Group__0__Impl rule__TeleportRulesConfig__Group__1
            {
            pushFollow(FOLLOW_4);
            rule__TeleportRulesConfig__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__TeleportRulesConfig__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TeleportRulesConfig__Group__0"


    // $ANTLR start "rule__TeleportRulesConfig__Group__0__Impl"
    // InternalMazeDsl.g:8308:1: rule__TeleportRulesConfig__Group__0__Impl : ( 'teleport' ) ;
    public final void rule__TeleportRulesConfig__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8312:1: ( ( 'teleport' ) )
            // InternalMazeDsl.g:8313:1: ( 'teleport' )
            {
            // InternalMazeDsl.g:8313:1: ( 'teleport' )
            // InternalMazeDsl.g:8314:2: 'teleport'
            {
             before(grammarAccess.getTeleportRulesConfigAccess().getTeleportKeyword_0()); 
            match(input,113,FOLLOW_2); 
             after(grammarAccess.getTeleportRulesConfigAccess().getTeleportKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TeleportRulesConfig__Group__0__Impl"


    // $ANTLR start "rule__TeleportRulesConfig__Group__1"
    // InternalMazeDsl.g:8323:1: rule__TeleportRulesConfig__Group__1 : rule__TeleportRulesConfig__Group__1__Impl rule__TeleportRulesConfig__Group__2 ;
    public final void rule__TeleportRulesConfig__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8327:1: ( rule__TeleportRulesConfig__Group__1__Impl rule__TeleportRulesConfig__Group__2 )
            // InternalMazeDsl.g:8328:2: rule__TeleportRulesConfig__Group__1__Impl rule__TeleportRulesConfig__Group__2
            {
            pushFollow(FOLLOW_74);
            rule__TeleportRulesConfig__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__TeleportRulesConfig__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TeleportRulesConfig__Group__1"


    // $ANTLR start "rule__TeleportRulesConfig__Group__1__Impl"
    // InternalMazeDsl.g:8335:1: rule__TeleportRulesConfig__Group__1__Impl : ( '{' ) ;
    public final void rule__TeleportRulesConfig__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8339:1: ( ( '{' ) )
            // InternalMazeDsl.g:8340:1: ( '{' )
            {
            // InternalMazeDsl.g:8340:1: ( '{' )
            // InternalMazeDsl.g:8341:2: '{'
            {
             before(grammarAccess.getTeleportRulesConfigAccess().getLeftCurlyBracketKeyword_1()); 
            match(input,32,FOLLOW_2); 
             after(grammarAccess.getTeleportRulesConfigAccess().getLeftCurlyBracketKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TeleportRulesConfig__Group__1__Impl"


    // $ANTLR start "rule__TeleportRulesConfig__Group__2"
    // InternalMazeDsl.g:8350:1: rule__TeleportRulesConfig__Group__2 : rule__TeleportRulesConfig__Group__2__Impl rule__TeleportRulesConfig__Group__3 ;
    public final void rule__TeleportRulesConfig__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8354:1: ( rule__TeleportRulesConfig__Group__2__Impl rule__TeleportRulesConfig__Group__3 )
            // InternalMazeDsl.g:8355:2: rule__TeleportRulesConfig__Group__2__Impl rule__TeleportRulesConfig__Group__3
            {
            pushFollow(FOLLOW_17);
            rule__TeleportRulesConfig__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__TeleportRulesConfig__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TeleportRulesConfig__Group__2"


    // $ANTLR start "rule__TeleportRulesConfig__Group__2__Impl"
    // InternalMazeDsl.g:8362:1: rule__TeleportRulesConfig__Group__2__Impl : ( 'cooldown' ) ;
    public final void rule__TeleportRulesConfig__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8366:1: ( ( 'cooldown' ) )
            // InternalMazeDsl.g:8367:1: ( 'cooldown' )
            {
            // InternalMazeDsl.g:8367:1: ( 'cooldown' )
            // InternalMazeDsl.g:8368:2: 'cooldown'
            {
             before(grammarAccess.getTeleportRulesConfigAccess().getCooldownKeyword_2()); 
            match(input,114,FOLLOW_2); 
             after(grammarAccess.getTeleportRulesConfigAccess().getCooldownKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TeleportRulesConfig__Group__2__Impl"


    // $ANTLR start "rule__TeleportRulesConfig__Group__3"
    // InternalMazeDsl.g:8377:1: rule__TeleportRulesConfig__Group__3 : rule__TeleportRulesConfig__Group__3__Impl rule__TeleportRulesConfig__Group__4 ;
    public final void rule__TeleportRulesConfig__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8381:1: ( rule__TeleportRulesConfig__Group__3__Impl rule__TeleportRulesConfig__Group__4 )
            // InternalMazeDsl.g:8382:2: rule__TeleportRulesConfig__Group__3__Impl rule__TeleportRulesConfig__Group__4
            {
            pushFollow(FOLLOW_75);
            rule__TeleportRulesConfig__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__TeleportRulesConfig__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TeleportRulesConfig__Group__3"


    // $ANTLR start "rule__TeleportRulesConfig__Group__3__Impl"
    // InternalMazeDsl.g:8389:1: rule__TeleportRulesConfig__Group__3__Impl : ( ( rule__TeleportRulesConfig__CooldownSecondsAssignment_3 ) ) ;
    public final void rule__TeleportRulesConfig__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8393:1: ( ( ( rule__TeleportRulesConfig__CooldownSecondsAssignment_3 ) ) )
            // InternalMazeDsl.g:8394:1: ( ( rule__TeleportRulesConfig__CooldownSecondsAssignment_3 ) )
            {
            // InternalMazeDsl.g:8394:1: ( ( rule__TeleportRulesConfig__CooldownSecondsAssignment_3 ) )
            // InternalMazeDsl.g:8395:2: ( rule__TeleportRulesConfig__CooldownSecondsAssignment_3 )
            {
             before(grammarAccess.getTeleportRulesConfigAccess().getCooldownSecondsAssignment_3()); 
            // InternalMazeDsl.g:8396:2: ( rule__TeleportRulesConfig__CooldownSecondsAssignment_3 )
            // InternalMazeDsl.g:8396:3: rule__TeleportRulesConfig__CooldownSecondsAssignment_3
            {
            pushFollow(FOLLOW_2);
            rule__TeleportRulesConfig__CooldownSecondsAssignment_3();

            state._fsp--;


            }

             after(grammarAccess.getTeleportRulesConfigAccess().getCooldownSecondsAssignment_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TeleportRulesConfig__Group__3__Impl"


    // $ANTLR start "rule__TeleportRulesConfig__Group__4"
    // InternalMazeDsl.g:8404:1: rule__TeleportRulesConfig__Group__4 : rule__TeleportRulesConfig__Group__4__Impl rule__TeleportRulesConfig__Group__5 ;
    public final void rule__TeleportRulesConfig__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8408:1: ( rule__TeleportRulesConfig__Group__4__Impl rule__TeleportRulesConfig__Group__5 )
            // InternalMazeDsl.g:8409:2: rule__TeleportRulesConfig__Group__4__Impl rule__TeleportRulesConfig__Group__5
            {
            pushFollow(FOLLOW_11);
            rule__TeleportRulesConfig__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__TeleportRulesConfig__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TeleportRulesConfig__Group__4"


    // $ANTLR start "rule__TeleportRulesConfig__Group__4__Impl"
    // InternalMazeDsl.g:8416:1: rule__TeleportRulesConfig__Group__4__Impl : ( 'banner' ) ;
    public final void rule__TeleportRulesConfig__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8420:1: ( ( 'banner' ) )
            // InternalMazeDsl.g:8421:1: ( 'banner' )
            {
            // InternalMazeDsl.g:8421:1: ( 'banner' )
            // InternalMazeDsl.g:8422:2: 'banner'
            {
             before(grammarAccess.getTeleportRulesConfigAccess().getBannerKeyword_4()); 
            match(input,115,FOLLOW_2); 
             after(grammarAccess.getTeleportRulesConfigAccess().getBannerKeyword_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TeleportRulesConfig__Group__4__Impl"


    // $ANTLR start "rule__TeleportRulesConfig__Group__5"
    // InternalMazeDsl.g:8431:1: rule__TeleportRulesConfig__Group__5 : rule__TeleportRulesConfig__Group__5__Impl rule__TeleportRulesConfig__Group__6 ;
    public final void rule__TeleportRulesConfig__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8435:1: ( rule__TeleportRulesConfig__Group__5__Impl rule__TeleportRulesConfig__Group__6 )
            // InternalMazeDsl.g:8436:2: rule__TeleportRulesConfig__Group__5__Impl rule__TeleportRulesConfig__Group__6
            {
            pushFollow(FOLLOW_76);
            rule__TeleportRulesConfig__Group__5__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__TeleportRulesConfig__Group__6();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TeleportRulesConfig__Group__5"


    // $ANTLR start "rule__TeleportRulesConfig__Group__5__Impl"
    // InternalMazeDsl.g:8443:1: rule__TeleportRulesConfig__Group__5__Impl : ( ( rule__TeleportRulesConfig__BannerTextAssignment_5 ) ) ;
    public final void rule__TeleportRulesConfig__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8447:1: ( ( ( rule__TeleportRulesConfig__BannerTextAssignment_5 ) ) )
            // InternalMazeDsl.g:8448:1: ( ( rule__TeleportRulesConfig__BannerTextAssignment_5 ) )
            {
            // InternalMazeDsl.g:8448:1: ( ( rule__TeleportRulesConfig__BannerTextAssignment_5 ) )
            // InternalMazeDsl.g:8449:2: ( rule__TeleportRulesConfig__BannerTextAssignment_5 )
            {
             before(grammarAccess.getTeleportRulesConfigAccess().getBannerTextAssignment_5()); 
            // InternalMazeDsl.g:8450:2: ( rule__TeleportRulesConfig__BannerTextAssignment_5 )
            // InternalMazeDsl.g:8450:3: rule__TeleportRulesConfig__BannerTextAssignment_5
            {
            pushFollow(FOLLOW_2);
            rule__TeleportRulesConfig__BannerTextAssignment_5();

            state._fsp--;


            }

             after(grammarAccess.getTeleportRulesConfigAccess().getBannerTextAssignment_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TeleportRulesConfig__Group__5__Impl"


    // $ANTLR start "rule__TeleportRulesConfig__Group__6"
    // InternalMazeDsl.g:8458:1: rule__TeleportRulesConfig__Group__6 : rule__TeleportRulesConfig__Group__6__Impl rule__TeleportRulesConfig__Group__7 ;
    public final void rule__TeleportRulesConfig__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8462:1: ( rule__TeleportRulesConfig__Group__6__Impl rule__TeleportRulesConfig__Group__7 )
            // InternalMazeDsl.g:8463:2: rule__TeleportRulesConfig__Group__6__Impl rule__TeleportRulesConfig__Group__7
            {
            pushFollow(FOLLOW_17);
            rule__TeleportRulesConfig__Group__6__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__TeleportRulesConfig__Group__7();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TeleportRulesConfig__Group__6"


    // $ANTLR start "rule__TeleportRulesConfig__Group__6__Impl"
    // InternalMazeDsl.g:8470:1: rule__TeleportRulesConfig__Group__6__Impl : ( 'bannerSeconds' ) ;
    public final void rule__TeleportRulesConfig__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8474:1: ( ( 'bannerSeconds' ) )
            // InternalMazeDsl.g:8475:1: ( 'bannerSeconds' )
            {
            // InternalMazeDsl.g:8475:1: ( 'bannerSeconds' )
            // InternalMazeDsl.g:8476:2: 'bannerSeconds'
            {
             before(grammarAccess.getTeleportRulesConfigAccess().getBannerSecondsKeyword_6()); 
            match(input,116,FOLLOW_2); 
             after(grammarAccess.getTeleportRulesConfigAccess().getBannerSecondsKeyword_6()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TeleportRulesConfig__Group__6__Impl"


    // $ANTLR start "rule__TeleportRulesConfig__Group__7"
    // InternalMazeDsl.g:8485:1: rule__TeleportRulesConfig__Group__7 : rule__TeleportRulesConfig__Group__7__Impl rule__TeleportRulesConfig__Group__8 ;
    public final void rule__TeleportRulesConfig__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8489:1: ( rule__TeleportRulesConfig__Group__7__Impl rule__TeleportRulesConfig__Group__8 )
            // InternalMazeDsl.g:8490:2: rule__TeleportRulesConfig__Group__7__Impl rule__TeleportRulesConfig__Group__8
            {
            pushFollow(FOLLOW_77);
            rule__TeleportRulesConfig__Group__7__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__TeleportRulesConfig__Group__8();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TeleportRulesConfig__Group__7"


    // $ANTLR start "rule__TeleportRulesConfig__Group__7__Impl"
    // InternalMazeDsl.g:8497:1: rule__TeleportRulesConfig__Group__7__Impl : ( ( rule__TeleportRulesConfig__BannerSecondsAssignment_7 ) ) ;
    public final void rule__TeleportRulesConfig__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8501:1: ( ( ( rule__TeleportRulesConfig__BannerSecondsAssignment_7 ) ) )
            // InternalMazeDsl.g:8502:1: ( ( rule__TeleportRulesConfig__BannerSecondsAssignment_7 ) )
            {
            // InternalMazeDsl.g:8502:1: ( ( rule__TeleportRulesConfig__BannerSecondsAssignment_7 ) )
            // InternalMazeDsl.g:8503:2: ( rule__TeleportRulesConfig__BannerSecondsAssignment_7 )
            {
             before(grammarAccess.getTeleportRulesConfigAccess().getBannerSecondsAssignment_7()); 
            // InternalMazeDsl.g:8504:2: ( rule__TeleportRulesConfig__BannerSecondsAssignment_7 )
            // InternalMazeDsl.g:8504:3: rule__TeleportRulesConfig__BannerSecondsAssignment_7
            {
            pushFollow(FOLLOW_2);
            rule__TeleportRulesConfig__BannerSecondsAssignment_7();

            state._fsp--;


            }

             after(grammarAccess.getTeleportRulesConfigAccess().getBannerSecondsAssignment_7()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TeleportRulesConfig__Group__7__Impl"


    // $ANTLR start "rule__TeleportRulesConfig__Group__8"
    // InternalMazeDsl.g:8512:1: rule__TeleportRulesConfig__Group__8 : rule__TeleportRulesConfig__Group__8__Impl rule__TeleportRulesConfig__Group__9 ;
    public final void rule__TeleportRulesConfig__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8516:1: ( rule__TeleportRulesConfig__Group__8__Impl rule__TeleportRulesConfig__Group__9 )
            // InternalMazeDsl.g:8517:2: rule__TeleportRulesConfig__Group__8__Impl rule__TeleportRulesConfig__Group__9
            {
            pushFollow(FOLLOW_77);
            rule__TeleportRulesConfig__Group__8__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__TeleportRulesConfig__Group__9();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TeleportRulesConfig__Group__8"


    // $ANTLR start "rule__TeleportRulesConfig__Group__8__Impl"
    // InternalMazeDsl.g:8524:1: rule__TeleportRulesConfig__Group__8__Impl : ( ( rule__TeleportRulesConfig__Group_8__0 )? ) ;
    public final void rule__TeleportRulesConfig__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8528:1: ( ( ( rule__TeleportRulesConfig__Group_8__0 )? ) )
            // InternalMazeDsl.g:8529:1: ( ( rule__TeleportRulesConfig__Group_8__0 )? )
            {
            // InternalMazeDsl.g:8529:1: ( ( rule__TeleportRulesConfig__Group_8__0 )? )
            // InternalMazeDsl.g:8530:2: ( rule__TeleportRulesConfig__Group_8__0 )?
            {
             before(grammarAccess.getTeleportRulesConfigAccess().getGroup_8()); 
            // InternalMazeDsl.g:8531:2: ( rule__TeleportRulesConfig__Group_8__0 )?
            int alt64=2;
            int LA64_0 = input.LA(1);

            if ( (LA64_0==117) ) {
                alt64=1;
            }
            switch (alt64) {
                case 1 :
                    // InternalMazeDsl.g:8531:3: rule__TeleportRulesConfig__Group_8__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__TeleportRulesConfig__Group_8__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getTeleportRulesConfigAccess().getGroup_8()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TeleportRulesConfig__Group__8__Impl"


    // $ANTLR start "rule__TeleportRulesConfig__Group__9"
    // InternalMazeDsl.g:8539:1: rule__TeleportRulesConfig__Group__9 : rule__TeleportRulesConfig__Group__9__Impl rule__TeleportRulesConfig__Group__10 ;
    public final void rule__TeleportRulesConfig__Group__9() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8543:1: ( rule__TeleportRulesConfig__Group__9__Impl rule__TeleportRulesConfig__Group__10 )
            // InternalMazeDsl.g:8544:2: rule__TeleportRulesConfig__Group__9__Impl rule__TeleportRulesConfig__Group__10
            {
            pushFollow(FOLLOW_77);
            rule__TeleportRulesConfig__Group__9__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__TeleportRulesConfig__Group__10();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TeleportRulesConfig__Group__9"


    // $ANTLR start "rule__TeleportRulesConfig__Group__9__Impl"
    // InternalMazeDsl.g:8551:1: rule__TeleportRulesConfig__Group__9__Impl : ( ( rule__TeleportRulesConfig__Group_9__0 )? ) ;
    public final void rule__TeleportRulesConfig__Group__9__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8555:1: ( ( ( rule__TeleportRulesConfig__Group_9__0 )? ) )
            // InternalMazeDsl.g:8556:1: ( ( rule__TeleportRulesConfig__Group_9__0 )? )
            {
            // InternalMazeDsl.g:8556:1: ( ( rule__TeleportRulesConfig__Group_9__0 )? )
            // InternalMazeDsl.g:8557:2: ( rule__TeleportRulesConfig__Group_9__0 )?
            {
             before(grammarAccess.getTeleportRulesConfigAccess().getGroup_9()); 
            // InternalMazeDsl.g:8558:2: ( rule__TeleportRulesConfig__Group_9__0 )?
            int alt65=2;
            int LA65_0 = input.LA(1);

            if ( (LA65_0==118) ) {
                alt65=1;
            }
            switch (alt65) {
                case 1 :
                    // InternalMazeDsl.g:8558:3: rule__TeleportRulesConfig__Group_9__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__TeleportRulesConfig__Group_9__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getTeleportRulesConfigAccess().getGroup_9()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TeleportRulesConfig__Group__9__Impl"


    // $ANTLR start "rule__TeleportRulesConfig__Group__10"
    // InternalMazeDsl.g:8566:1: rule__TeleportRulesConfig__Group__10 : rule__TeleportRulesConfig__Group__10__Impl ;
    public final void rule__TeleportRulesConfig__Group__10() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8570:1: ( rule__TeleportRulesConfig__Group__10__Impl )
            // InternalMazeDsl.g:8571:2: rule__TeleportRulesConfig__Group__10__Impl
            {
            pushFollow(FOLLOW_2);
            rule__TeleportRulesConfig__Group__10__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TeleportRulesConfig__Group__10"


    // $ANTLR start "rule__TeleportRulesConfig__Group__10__Impl"
    // InternalMazeDsl.g:8577:1: rule__TeleportRulesConfig__Group__10__Impl : ( '}' ) ;
    public final void rule__TeleportRulesConfig__Group__10__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8581:1: ( ( '}' ) )
            // InternalMazeDsl.g:8582:1: ( '}' )
            {
            // InternalMazeDsl.g:8582:1: ( '}' )
            // InternalMazeDsl.g:8583:2: '}'
            {
             before(grammarAccess.getTeleportRulesConfigAccess().getRightCurlyBracketKeyword_10()); 
            match(input,33,FOLLOW_2); 
             after(grammarAccess.getTeleportRulesConfigAccess().getRightCurlyBracketKeyword_10()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TeleportRulesConfig__Group__10__Impl"


    // $ANTLR start "rule__TeleportRulesConfig__Group_8__0"
    // InternalMazeDsl.g:8593:1: rule__TeleportRulesConfig__Group_8__0 : rule__TeleportRulesConfig__Group_8__0__Impl rule__TeleportRulesConfig__Group_8__1 ;
    public final void rule__TeleportRulesConfig__Group_8__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8597:1: ( rule__TeleportRulesConfig__Group_8__0__Impl rule__TeleportRulesConfig__Group_8__1 )
            // InternalMazeDsl.g:8598:2: rule__TeleportRulesConfig__Group_8__0__Impl rule__TeleportRulesConfig__Group_8__1
            {
            pushFollow(FOLLOW_11);
            rule__TeleportRulesConfig__Group_8__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__TeleportRulesConfig__Group_8__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TeleportRulesConfig__Group_8__0"


    // $ANTLR start "rule__TeleportRulesConfig__Group_8__0__Impl"
    // InternalMazeDsl.g:8605:1: rule__TeleportRulesConfig__Group_8__0__Impl : ( 'zapSound' ) ;
    public final void rule__TeleportRulesConfig__Group_8__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8609:1: ( ( 'zapSound' ) )
            // InternalMazeDsl.g:8610:1: ( 'zapSound' )
            {
            // InternalMazeDsl.g:8610:1: ( 'zapSound' )
            // InternalMazeDsl.g:8611:2: 'zapSound'
            {
             before(grammarAccess.getTeleportRulesConfigAccess().getZapSoundKeyword_8_0()); 
            match(input,117,FOLLOW_2); 
             after(grammarAccess.getTeleportRulesConfigAccess().getZapSoundKeyword_8_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TeleportRulesConfig__Group_8__0__Impl"


    // $ANTLR start "rule__TeleportRulesConfig__Group_8__1"
    // InternalMazeDsl.g:8620:1: rule__TeleportRulesConfig__Group_8__1 : rule__TeleportRulesConfig__Group_8__1__Impl ;
    public final void rule__TeleportRulesConfig__Group_8__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8624:1: ( rule__TeleportRulesConfig__Group_8__1__Impl )
            // InternalMazeDsl.g:8625:2: rule__TeleportRulesConfig__Group_8__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__TeleportRulesConfig__Group_8__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TeleportRulesConfig__Group_8__1"


    // $ANTLR start "rule__TeleportRulesConfig__Group_8__1__Impl"
    // InternalMazeDsl.g:8631:1: rule__TeleportRulesConfig__Group_8__1__Impl : ( ( rule__TeleportRulesConfig__ZapSoundAssignment_8_1 ) ) ;
    public final void rule__TeleportRulesConfig__Group_8__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8635:1: ( ( ( rule__TeleportRulesConfig__ZapSoundAssignment_8_1 ) ) )
            // InternalMazeDsl.g:8636:1: ( ( rule__TeleportRulesConfig__ZapSoundAssignment_8_1 ) )
            {
            // InternalMazeDsl.g:8636:1: ( ( rule__TeleportRulesConfig__ZapSoundAssignment_8_1 ) )
            // InternalMazeDsl.g:8637:2: ( rule__TeleportRulesConfig__ZapSoundAssignment_8_1 )
            {
             before(grammarAccess.getTeleportRulesConfigAccess().getZapSoundAssignment_8_1()); 
            // InternalMazeDsl.g:8638:2: ( rule__TeleportRulesConfig__ZapSoundAssignment_8_1 )
            // InternalMazeDsl.g:8638:3: rule__TeleportRulesConfig__ZapSoundAssignment_8_1
            {
            pushFollow(FOLLOW_2);
            rule__TeleportRulesConfig__ZapSoundAssignment_8_1();

            state._fsp--;


            }

             after(grammarAccess.getTeleportRulesConfigAccess().getZapSoundAssignment_8_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TeleportRulesConfig__Group_8__1__Impl"


    // $ANTLR start "rule__TeleportRulesConfig__Group_9__0"
    // InternalMazeDsl.g:8647:1: rule__TeleportRulesConfig__Group_9__0 : rule__TeleportRulesConfig__Group_9__0__Impl rule__TeleportRulesConfig__Group_9__1 ;
    public final void rule__TeleportRulesConfig__Group_9__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8651:1: ( rule__TeleportRulesConfig__Group_9__0__Impl rule__TeleportRulesConfig__Group_9__1 )
            // InternalMazeDsl.g:8652:2: rule__TeleportRulesConfig__Group_9__0__Impl rule__TeleportRulesConfig__Group_9__1
            {
            pushFollow(FOLLOW_11);
            rule__TeleportRulesConfig__Group_9__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__TeleportRulesConfig__Group_9__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TeleportRulesConfig__Group_9__0"


    // $ANTLR start "rule__TeleportRulesConfig__Group_9__0__Impl"
    // InternalMazeDsl.g:8659:1: rule__TeleportRulesConfig__Group_9__0__Impl : ( 'voiceSound' ) ;
    public final void rule__TeleportRulesConfig__Group_9__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8663:1: ( ( 'voiceSound' ) )
            // InternalMazeDsl.g:8664:1: ( 'voiceSound' )
            {
            // InternalMazeDsl.g:8664:1: ( 'voiceSound' )
            // InternalMazeDsl.g:8665:2: 'voiceSound'
            {
             before(grammarAccess.getTeleportRulesConfigAccess().getVoiceSoundKeyword_9_0()); 
            match(input,118,FOLLOW_2); 
             after(grammarAccess.getTeleportRulesConfigAccess().getVoiceSoundKeyword_9_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TeleportRulesConfig__Group_9__0__Impl"


    // $ANTLR start "rule__TeleportRulesConfig__Group_9__1"
    // InternalMazeDsl.g:8674:1: rule__TeleportRulesConfig__Group_9__1 : rule__TeleportRulesConfig__Group_9__1__Impl ;
    public final void rule__TeleportRulesConfig__Group_9__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8678:1: ( rule__TeleportRulesConfig__Group_9__1__Impl )
            // InternalMazeDsl.g:8679:2: rule__TeleportRulesConfig__Group_9__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__TeleportRulesConfig__Group_9__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TeleportRulesConfig__Group_9__1"


    // $ANTLR start "rule__TeleportRulesConfig__Group_9__1__Impl"
    // InternalMazeDsl.g:8685:1: rule__TeleportRulesConfig__Group_9__1__Impl : ( ( rule__TeleportRulesConfig__VoiceSoundAssignment_9_1 ) ) ;
    public final void rule__TeleportRulesConfig__Group_9__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8689:1: ( ( ( rule__TeleportRulesConfig__VoiceSoundAssignment_9_1 ) ) )
            // InternalMazeDsl.g:8690:1: ( ( rule__TeleportRulesConfig__VoiceSoundAssignment_9_1 ) )
            {
            // InternalMazeDsl.g:8690:1: ( ( rule__TeleportRulesConfig__VoiceSoundAssignment_9_1 ) )
            // InternalMazeDsl.g:8691:2: ( rule__TeleportRulesConfig__VoiceSoundAssignment_9_1 )
            {
             before(grammarAccess.getTeleportRulesConfigAccess().getVoiceSoundAssignment_9_1()); 
            // InternalMazeDsl.g:8692:2: ( rule__TeleportRulesConfig__VoiceSoundAssignment_9_1 )
            // InternalMazeDsl.g:8692:3: rule__TeleportRulesConfig__VoiceSoundAssignment_9_1
            {
            pushFollow(FOLLOW_2);
            rule__TeleportRulesConfig__VoiceSoundAssignment_9_1();

            state._fsp--;


            }

             after(grammarAccess.getTeleportRulesConfigAccess().getVoiceSoundAssignment_9_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TeleportRulesConfig__Group_9__1__Impl"


    // $ANTLR start "rule__DOUBLE__Group__0"
    // InternalMazeDsl.g:8701:1: rule__DOUBLE__Group__0 : rule__DOUBLE__Group__0__Impl rule__DOUBLE__Group__1 ;
    public final void rule__DOUBLE__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8705:1: ( rule__DOUBLE__Group__0__Impl rule__DOUBLE__Group__1 )
            // InternalMazeDsl.g:8706:2: rule__DOUBLE__Group__0__Impl rule__DOUBLE__Group__1
            {
            pushFollow(FOLLOW_17);
            rule__DOUBLE__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DOUBLE__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DOUBLE__Group__0"


    // $ANTLR start "rule__DOUBLE__Group__0__Impl"
    // InternalMazeDsl.g:8713:1: rule__DOUBLE__Group__0__Impl : ( ( '-' )? ) ;
    public final void rule__DOUBLE__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8717:1: ( ( ( '-' )? ) )
            // InternalMazeDsl.g:8718:1: ( ( '-' )? )
            {
            // InternalMazeDsl.g:8718:1: ( ( '-' )? )
            // InternalMazeDsl.g:8719:2: ( '-' )?
            {
             before(grammarAccess.getDOUBLEAccess().getHyphenMinusKeyword_0()); 
            // InternalMazeDsl.g:8720:2: ( '-' )?
            int alt66=2;
            int LA66_0 = input.LA(1);

            if ( (LA66_0==119) ) {
                alt66=1;
            }
            switch (alt66) {
                case 1 :
                    // InternalMazeDsl.g:8720:3: '-'
                    {
                    match(input,119,FOLLOW_2); 

                    }
                    break;

            }

             after(grammarAccess.getDOUBLEAccess().getHyphenMinusKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DOUBLE__Group__0__Impl"


    // $ANTLR start "rule__DOUBLE__Group__1"
    // InternalMazeDsl.g:8728:1: rule__DOUBLE__Group__1 : rule__DOUBLE__Group__1__Impl rule__DOUBLE__Group__2 ;
    public final void rule__DOUBLE__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8732:1: ( rule__DOUBLE__Group__1__Impl rule__DOUBLE__Group__2 )
            // InternalMazeDsl.g:8733:2: rule__DOUBLE__Group__1__Impl rule__DOUBLE__Group__2
            {
            pushFollow(FOLLOW_78);
            rule__DOUBLE__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DOUBLE__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DOUBLE__Group__1"


    // $ANTLR start "rule__DOUBLE__Group__1__Impl"
    // InternalMazeDsl.g:8740:1: rule__DOUBLE__Group__1__Impl : ( RULE_INT ) ;
    public final void rule__DOUBLE__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8744:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:8745:1: ( RULE_INT )
            {
            // InternalMazeDsl.g:8745:1: ( RULE_INT )
            // InternalMazeDsl.g:8746:2: RULE_INT
            {
             before(grammarAccess.getDOUBLEAccess().getINTTerminalRuleCall_1()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getDOUBLEAccess().getINTTerminalRuleCall_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DOUBLE__Group__1__Impl"


    // $ANTLR start "rule__DOUBLE__Group__2"
    // InternalMazeDsl.g:8755:1: rule__DOUBLE__Group__2 : rule__DOUBLE__Group__2__Impl ;
    public final void rule__DOUBLE__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8759:1: ( rule__DOUBLE__Group__2__Impl )
            // InternalMazeDsl.g:8760:2: rule__DOUBLE__Group__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__DOUBLE__Group__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DOUBLE__Group__2"


    // $ANTLR start "rule__DOUBLE__Group__2__Impl"
    // InternalMazeDsl.g:8766:1: rule__DOUBLE__Group__2__Impl : ( ( rule__DOUBLE__Group_2__0 )? ) ;
    public final void rule__DOUBLE__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8770:1: ( ( ( rule__DOUBLE__Group_2__0 )? ) )
            // InternalMazeDsl.g:8771:1: ( ( rule__DOUBLE__Group_2__0 )? )
            {
            // InternalMazeDsl.g:8771:1: ( ( rule__DOUBLE__Group_2__0 )? )
            // InternalMazeDsl.g:8772:2: ( rule__DOUBLE__Group_2__0 )?
            {
             before(grammarAccess.getDOUBLEAccess().getGroup_2()); 
            // InternalMazeDsl.g:8773:2: ( rule__DOUBLE__Group_2__0 )?
            int alt67=2;
            int LA67_0 = input.LA(1);

            if ( (LA67_0==120) ) {
                alt67=1;
            }
            switch (alt67) {
                case 1 :
                    // InternalMazeDsl.g:8773:3: rule__DOUBLE__Group_2__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__DOUBLE__Group_2__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getDOUBLEAccess().getGroup_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DOUBLE__Group__2__Impl"


    // $ANTLR start "rule__DOUBLE__Group_2__0"
    // InternalMazeDsl.g:8782:1: rule__DOUBLE__Group_2__0 : rule__DOUBLE__Group_2__0__Impl rule__DOUBLE__Group_2__1 ;
    public final void rule__DOUBLE__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8786:1: ( rule__DOUBLE__Group_2__0__Impl rule__DOUBLE__Group_2__1 )
            // InternalMazeDsl.g:8787:2: rule__DOUBLE__Group_2__0__Impl rule__DOUBLE__Group_2__1
            {
            pushFollow(FOLLOW_26);
            rule__DOUBLE__Group_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DOUBLE__Group_2__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DOUBLE__Group_2__0"


    // $ANTLR start "rule__DOUBLE__Group_2__0__Impl"
    // InternalMazeDsl.g:8794:1: rule__DOUBLE__Group_2__0__Impl : ( '.' ) ;
    public final void rule__DOUBLE__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8798:1: ( ( '.' ) )
            // InternalMazeDsl.g:8799:1: ( '.' )
            {
            // InternalMazeDsl.g:8799:1: ( '.' )
            // InternalMazeDsl.g:8800:2: '.'
            {
             before(grammarAccess.getDOUBLEAccess().getFullStopKeyword_2_0()); 
            match(input,120,FOLLOW_2); 
             after(grammarAccess.getDOUBLEAccess().getFullStopKeyword_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DOUBLE__Group_2__0__Impl"


    // $ANTLR start "rule__DOUBLE__Group_2__1"
    // InternalMazeDsl.g:8809:1: rule__DOUBLE__Group_2__1 : rule__DOUBLE__Group_2__1__Impl ;
    public final void rule__DOUBLE__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8813:1: ( rule__DOUBLE__Group_2__1__Impl )
            // InternalMazeDsl.g:8814:2: rule__DOUBLE__Group_2__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__DOUBLE__Group_2__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DOUBLE__Group_2__1"


    // $ANTLR start "rule__DOUBLE__Group_2__1__Impl"
    // InternalMazeDsl.g:8820:1: rule__DOUBLE__Group_2__1__Impl : ( RULE_INT ) ;
    public final void rule__DOUBLE__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8824:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:8825:1: ( RULE_INT )
            {
            // InternalMazeDsl.g:8825:1: ( RULE_INT )
            // InternalMazeDsl.g:8826:2: RULE_INT
            {
             before(grammarAccess.getDOUBLEAccess().getINTTerminalRuleCall_2_1()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getDOUBLEAccess().getINTTerminalRuleCall_2_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DOUBLE__Group_2__1__Impl"


    // $ANTLR start "rule__SIGNED_INT__Group__0"
    // InternalMazeDsl.g:8836:1: rule__SIGNED_INT__Group__0 : rule__SIGNED_INT__Group__0__Impl rule__SIGNED_INT__Group__1 ;
    public final void rule__SIGNED_INT__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8840:1: ( rule__SIGNED_INT__Group__0__Impl rule__SIGNED_INT__Group__1 )
            // InternalMazeDsl.g:8841:2: rule__SIGNED_INT__Group__0__Impl rule__SIGNED_INT__Group__1
            {
            pushFollow(FOLLOW_17);
            rule__SIGNED_INT__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__SIGNED_INT__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SIGNED_INT__Group__0"


    // $ANTLR start "rule__SIGNED_INT__Group__0__Impl"
    // InternalMazeDsl.g:8848:1: rule__SIGNED_INT__Group__0__Impl : ( ( '-' )? ) ;
    public final void rule__SIGNED_INT__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8852:1: ( ( ( '-' )? ) )
            // InternalMazeDsl.g:8853:1: ( ( '-' )? )
            {
            // InternalMazeDsl.g:8853:1: ( ( '-' )? )
            // InternalMazeDsl.g:8854:2: ( '-' )?
            {
             before(grammarAccess.getSIGNED_INTAccess().getHyphenMinusKeyword_0()); 
            // InternalMazeDsl.g:8855:2: ( '-' )?
            int alt68=2;
            int LA68_0 = input.LA(1);

            if ( (LA68_0==119) ) {
                alt68=1;
            }
            switch (alt68) {
                case 1 :
                    // InternalMazeDsl.g:8855:3: '-'
                    {
                    match(input,119,FOLLOW_2); 

                    }
                    break;

            }

             after(grammarAccess.getSIGNED_INTAccess().getHyphenMinusKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SIGNED_INT__Group__0__Impl"


    // $ANTLR start "rule__SIGNED_INT__Group__1"
    // InternalMazeDsl.g:8863:1: rule__SIGNED_INT__Group__1 : rule__SIGNED_INT__Group__1__Impl ;
    public final void rule__SIGNED_INT__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8867:1: ( rule__SIGNED_INT__Group__1__Impl )
            // InternalMazeDsl.g:8868:2: rule__SIGNED_INT__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__SIGNED_INT__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SIGNED_INT__Group__1"


    // $ANTLR start "rule__SIGNED_INT__Group__1__Impl"
    // InternalMazeDsl.g:8874:1: rule__SIGNED_INT__Group__1__Impl : ( RULE_INT ) ;
    public final void rule__SIGNED_INT__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8878:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:8879:1: ( RULE_INT )
            {
            // InternalMazeDsl.g:8879:1: ( RULE_INT )
            // InternalMazeDsl.g:8880:2: RULE_INT
            {
             before(grammarAccess.getSIGNED_INTAccess().getINTTerminalRuleCall_1()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getSIGNED_INTAccess().getINTTerminalRuleCall_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SIGNED_INT__Group__1__Impl"


    // $ANTLR start "rule__DOUBLE_OBJECT__Group__0"
    // InternalMazeDsl.g:8890:1: rule__DOUBLE_OBJECT__Group__0 : rule__DOUBLE_OBJECT__Group__0__Impl rule__DOUBLE_OBJECT__Group__1 ;
    public final void rule__DOUBLE_OBJECT__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8894:1: ( rule__DOUBLE_OBJECT__Group__0__Impl rule__DOUBLE_OBJECT__Group__1 )
            // InternalMazeDsl.g:8895:2: rule__DOUBLE_OBJECT__Group__0__Impl rule__DOUBLE_OBJECT__Group__1
            {
            pushFollow(FOLLOW_17);
            rule__DOUBLE_OBJECT__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DOUBLE_OBJECT__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DOUBLE_OBJECT__Group__0"


    // $ANTLR start "rule__DOUBLE_OBJECT__Group__0__Impl"
    // InternalMazeDsl.g:8902:1: rule__DOUBLE_OBJECT__Group__0__Impl : ( ( '-' )? ) ;
    public final void rule__DOUBLE_OBJECT__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8906:1: ( ( ( '-' )? ) )
            // InternalMazeDsl.g:8907:1: ( ( '-' )? )
            {
            // InternalMazeDsl.g:8907:1: ( ( '-' )? )
            // InternalMazeDsl.g:8908:2: ( '-' )?
            {
             before(grammarAccess.getDOUBLE_OBJECTAccess().getHyphenMinusKeyword_0()); 
            // InternalMazeDsl.g:8909:2: ( '-' )?
            int alt69=2;
            int LA69_0 = input.LA(1);

            if ( (LA69_0==119) ) {
                alt69=1;
            }
            switch (alt69) {
                case 1 :
                    // InternalMazeDsl.g:8909:3: '-'
                    {
                    match(input,119,FOLLOW_2); 

                    }
                    break;

            }

             after(grammarAccess.getDOUBLE_OBJECTAccess().getHyphenMinusKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DOUBLE_OBJECT__Group__0__Impl"


    // $ANTLR start "rule__DOUBLE_OBJECT__Group__1"
    // InternalMazeDsl.g:8917:1: rule__DOUBLE_OBJECT__Group__1 : rule__DOUBLE_OBJECT__Group__1__Impl rule__DOUBLE_OBJECT__Group__2 ;
    public final void rule__DOUBLE_OBJECT__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8921:1: ( rule__DOUBLE_OBJECT__Group__1__Impl rule__DOUBLE_OBJECT__Group__2 )
            // InternalMazeDsl.g:8922:2: rule__DOUBLE_OBJECT__Group__1__Impl rule__DOUBLE_OBJECT__Group__2
            {
            pushFollow(FOLLOW_78);
            rule__DOUBLE_OBJECT__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DOUBLE_OBJECT__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DOUBLE_OBJECT__Group__1"


    // $ANTLR start "rule__DOUBLE_OBJECT__Group__1__Impl"
    // InternalMazeDsl.g:8929:1: rule__DOUBLE_OBJECT__Group__1__Impl : ( RULE_INT ) ;
    public final void rule__DOUBLE_OBJECT__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8933:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:8934:1: ( RULE_INT )
            {
            // InternalMazeDsl.g:8934:1: ( RULE_INT )
            // InternalMazeDsl.g:8935:2: RULE_INT
            {
             before(grammarAccess.getDOUBLE_OBJECTAccess().getINTTerminalRuleCall_1()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getDOUBLE_OBJECTAccess().getINTTerminalRuleCall_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DOUBLE_OBJECT__Group__1__Impl"


    // $ANTLR start "rule__DOUBLE_OBJECT__Group__2"
    // InternalMazeDsl.g:8944:1: rule__DOUBLE_OBJECT__Group__2 : rule__DOUBLE_OBJECT__Group__2__Impl ;
    public final void rule__DOUBLE_OBJECT__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8948:1: ( rule__DOUBLE_OBJECT__Group__2__Impl )
            // InternalMazeDsl.g:8949:2: rule__DOUBLE_OBJECT__Group__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__DOUBLE_OBJECT__Group__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DOUBLE_OBJECT__Group__2"


    // $ANTLR start "rule__DOUBLE_OBJECT__Group__2__Impl"
    // InternalMazeDsl.g:8955:1: rule__DOUBLE_OBJECT__Group__2__Impl : ( ( rule__DOUBLE_OBJECT__Group_2__0 )? ) ;
    public final void rule__DOUBLE_OBJECT__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8959:1: ( ( ( rule__DOUBLE_OBJECT__Group_2__0 )? ) )
            // InternalMazeDsl.g:8960:1: ( ( rule__DOUBLE_OBJECT__Group_2__0 )? )
            {
            // InternalMazeDsl.g:8960:1: ( ( rule__DOUBLE_OBJECT__Group_2__0 )? )
            // InternalMazeDsl.g:8961:2: ( rule__DOUBLE_OBJECT__Group_2__0 )?
            {
             before(grammarAccess.getDOUBLE_OBJECTAccess().getGroup_2()); 
            // InternalMazeDsl.g:8962:2: ( rule__DOUBLE_OBJECT__Group_2__0 )?
            int alt70=2;
            int LA70_0 = input.LA(1);

            if ( (LA70_0==120) ) {
                alt70=1;
            }
            switch (alt70) {
                case 1 :
                    // InternalMazeDsl.g:8962:3: rule__DOUBLE_OBJECT__Group_2__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__DOUBLE_OBJECT__Group_2__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getDOUBLE_OBJECTAccess().getGroup_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DOUBLE_OBJECT__Group__2__Impl"


    // $ANTLR start "rule__DOUBLE_OBJECT__Group_2__0"
    // InternalMazeDsl.g:8971:1: rule__DOUBLE_OBJECT__Group_2__0 : rule__DOUBLE_OBJECT__Group_2__0__Impl rule__DOUBLE_OBJECT__Group_2__1 ;
    public final void rule__DOUBLE_OBJECT__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8975:1: ( rule__DOUBLE_OBJECT__Group_2__0__Impl rule__DOUBLE_OBJECT__Group_2__1 )
            // InternalMazeDsl.g:8976:2: rule__DOUBLE_OBJECT__Group_2__0__Impl rule__DOUBLE_OBJECT__Group_2__1
            {
            pushFollow(FOLLOW_26);
            rule__DOUBLE_OBJECT__Group_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DOUBLE_OBJECT__Group_2__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DOUBLE_OBJECT__Group_2__0"


    // $ANTLR start "rule__DOUBLE_OBJECT__Group_2__0__Impl"
    // InternalMazeDsl.g:8983:1: rule__DOUBLE_OBJECT__Group_2__0__Impl : ( '.' ) ;
    public final void rule__DOUBLE_OBJECT__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8987:1: ( ( '.' ) )
            // InternalMazeDsl.g:8988:1: ( '.' )
            {
            // InternalMazeDsl.g:8988:1: ( '.' )
            // InternalMazeDsl.g:8989:2: '.'
            {
             before(grammarAccess.getDOUBLE_OBJECTAccess().getFullStopKeyword_2_0()); 
            match(input,120,FOLLOW_2); 
             after(grammarAccess.getDOUBLE_OBJECTAccess().getFullStopKeyword_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DOUBLE_OBJECT__Group_2__0__Impl"


    // $ANTLR start "rule__DOUBLE_OBJECT__Group_2__1"
    // InternalMazeDsl.g:8998:1: rule__DOUBLE_OBJECT__Group_2__1 : rule__DOUBLE_OBJECT__Group_2__1__Impl ;
    public final void rule__DOUBLE_OBJECT__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9002:1: ( rule__DOUBLE_OBJECT__Group_2__1__Impl )
            // InternalMazeDsl.g:9003:2: rule__DOUBLE_OBJECT__Group_2__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__DOUBLE_OBJECT__Group_2__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DOUBLE_OBJECT__Group_2__1"


    // $ANTLR start "rule__DOUBLE_OBJECT__Group_2__1__Impl"
    // InternalMazeDsl.g:9009:1: rule__DOUBLE_OBJECT__Group_2__1__Impl : ( RULE_INT ) ;
    public final void rule__DOUBLE_OBJECT__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9013:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:9014:1: ( RULE_INT )
            {
            // InternalMazeDsl.g:9014:1: ( RULE_INT )
            // InternalMazeDsl.g:9015:2: RULE_INT
            {
             before(grammarAccess.getDOUBLE_OBJECTAccess().getINTTerminalRuleCall_2_1()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getDOUBLE_OBJECTAccess().getINTTerminalRuleCall_2_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DOUBLE_OBJECT__Group_2__1__Impl"


    // $ANTLR start "rule__INT_OBJECT__Group__0"
    // InternalMazeDsl.g:9025:1: rule__INT_OBJECT__Group__0 : rule__INT_OBJECT__Group__0__Impl rule__INT_OBJECT__Group__1 ;
    public final void rule__INT_OBJECT__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9029:1: ( rule__INT_OBJECT__Group__0__Impl rule__INT_OBJECT__Group__1 )
            // InternalMazeDsl.g:9030:2: rule__INT_OBJECT__Group__0__Impl rule__INT_OBJECT__Group__1
            {
            pushFollow(FOLLOW_17);
            rule__INT_OBJECT__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__INT_OBJECT__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__INT_OBJECT__Group__0"


    // $ANTLR start "rule__INT_OBJECT__Group__0__Impl"
    // InternalMazeDsl.g:9037:1: rule__INT_OBJECT__Group__0__Impl : ( ( '-' )? ) ;
    public final void rule__INT_OBJECT__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9041:1: ( ( ( '-' )? ) )
            // InternalMazeDsl.g:9042:1: ( ( '-' )? )
            {
            // InternalMazeDsl.g:9042:1: ( ( '-' )? )
            // InternalMazeDsl.g:9043:2: ( '-' )?
            {
             before(grammarAccess.getINT_OBJECTAccess().getHyphenMinusKeyword_0()); 
            // InternalMazeDsl.g:9044:2: ( '-' )?
            int alt71=2;
            int LA71_0 = input.LA(1);

            if ( (LA71_0==119) ) {
                alt71=1;
            }
            switch (alt71) {
                case 1 :
                    // InternalMazeDsl.g:9044:3: '-'
                    {
                    match(input,119,FOLLOW_2); 

                    }
                    break;

            }

             after(grammarAccess.getINT_OBJECTAccess().getHyphenMinusKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__INT_OBJECT__Group__0__Impl"


    // $ANTLR start "rule__INT_OBJECT__Group__1"
    // InternalMazeDsl.g:9052:1: rule__INT_OBJECT__Group__1 : rule__INT_OBJECT__Group__1__Impl ;
    public final void rule__INT_OBJECT__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9056:1: ( rule__INT_OBJECT__Group__1__Impl )
            // InternalMazeDsl.g:9057:2: rule__INT_OBJECT__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__INT_OBJECT__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__INT_OBJECT__Group__1"


    // $ANTLR start "rule__INT_OBJECT__Group__1__Impl"
    // InternalMazeDsl.g:9063:1: rule__INT_OBJECT__Group__1__Impl : ( RULE_INT ) ;
    public final void rule__INT_OBJECT__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9067:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:9068:1: ( RULE_INT )
            {
            // InternalMazeDsl.g:9068:1: ( RULE_INT )
            // InternalMazeDsl.g:9069:2: RULE_INT
            {
             before(grammarAccess.getINT_OBJECTAccess().getINTTerminalRuleCall_1()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getINT_OBJECTAccess().getINTTerminalRuleCall_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__INT_OBJECT__Group__1__Impl"


    // $ANTLR start "rule__GameConfiguration__NameAssignment_1"
    // InternalMazeDsl.g:9079:1: rule__GameConfiguration__NameAssignment_1 : ( RULE_ID ) ;
    public final void rule__GameConfiguration__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9083:1: ( ( RULE_ID ) )
            // InternalMazeDsl.g:9084:2: ( RULE_ID )
            {
            // InternalMazeDsl.g:9084:2: ( RULE_ID )
            // InternalMazeDsl.g:9085:3: RULE_ID
            {
             before(grammarAccess.getGameConfigurationAccess().getNameIDTerminalRuleCall_1_0()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getGameConfigurationAccess().getNameIDTerminalRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GameConfiguration__NameAssignment_1"


    // $ANTLR start "rule__GameConfiguration__ImportsAssignment_3"
    // InternalMazeDsl.g:9094:1: rule__GameConfiguration__ImportsAssignment_3 : ( ruleImport ) ;
    public final void rule__GameConfiguration__ImportsAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9098:1: ( ( ruleImport ) )
            // InternalMazeDsl.g:9099:2: ( ruleImport )
            {
            // InternalMazeDsl.g:9099:2: ( ruleImport )
            // InternalMazeDsl.g:9100:3: ruleImport
            {
             before(grammarAccess.getGameConfigurationAccess().getImportsImportParserRuleCall_3_0()); 
            pushFollow(FOLLOW_2);
            ruleImport();

            state._fsp--;

             after(grammarAccess.getGameConfigurationAccess().getImportsImportParserRuleCall_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GameConfiguration__ImportsAssignment_3"


    // $ANTLR start "rule__GameConfiguration__DifficultyAssignment_4"
    // InternalMazeDsl.g:9109:1: rule__GameConfiguration__DifficultyAssignment_4 : ( ruleDifficultyConfig ) ;
    public final void rule__GameConfiguration__DifficultyAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9113:1: ( ( ruleDifficultyConfig ) )
            // InternalMazeDsl.g:9114:2: ( ruleDifficultyConfig )
            {
            // InternalMazeDsl.g:9114:2: ( ruleDifficultyConfig )
            // InternalMazeDsl.g:9115:3: ruleDifficultyConfig
            {
             before(grammarAccess.getGameConfigurationAccess().getDifficultyDifficultyConfigParserRuleCall_4_0()); 
            pushFollow(FOLLOW_2);
            ruleDifficultyConfig();

            state._fsp--;

             after(grammarAccess.getGameConfigurationAccess().getDifficultyDifficultyConfigParserRuleCall_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GameConfiguration__DifficultyAssignment_4"


    // $ANTLR start "rule__GameConfiguration__OpponentsAssignment_5"
    // InternalMazeDsl.g:9124:1: rule__GameConfiguration__OpponentsAssignment_5 : ( ruleOpponentConfig ) ;
    public final void rule__GameConfiguration__OpponentsAssignment_5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9128:1: ( ( ruleOpponentConfig ) )
            // InternalMazeDsl.g:9129:2: ( ruleOpponentConfig )
            {
            // InternalMazeDsl.g:9129:2: ( ruleOpponentConfig )
            // InternalMazeDsl.g:9130:3: ruleOpponentConfig
            {
             before(grammarAccess.getGameConfigurationAccess().getOpponentsOpponentConfigParserRuleCall_5_0()); 
            pushFollow(FOLLOW_2);
            ruleOpponentConfig();

            state._fsp--;

             after(grammarAccess.getGameConfigurationAccess().getOpponentsOpponentConfigParserRuleCall_5_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GameConfiguration__OpponentsAssignment_5"


    // $ANTLR start "rule__GameConfiguration__PatrolsAssignment_6"
    // InternalMazeDsl.g:9139:1: rule__GameConfiguration__PatrolsAssignment_6 : ( rulePatrolConfig ) ;
    public final void rule__GameConfiguration__PatrolsAssignment_6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9143:1: ( ( rulePatrolConfig ) )
            // InternalMazeDsl.g:9144:2: ( rulePatrolConfig )
            {
            // InternalMazeDsl.g:9144:2: ( rulePatrolConfig )
            // InternalMazeDsl.g:9145:3: rulePatrolConfig
            {
             before(grammarAccess.getGameConfigurationAccess().getPatrolsPatrolConfigParserRuleCall_6_0()); 
            pushFollow(FOLLOW_2);
            rulePatrolConfig();

            state._fsp--;

             after(grammarAccess.getGameConfigurationAccess().getPatrolsPatrolConfigParserRuleCall_6_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GameConfiguration__PatrolsAssignment_6"


    // $ANTLR start "rule__GameConfiguration__LootTablesAssignment_7"
    // InternalMazeDsl.g:9154:1: rule__GameConfiguration__LootTablesAssignment_7 : ( ruleLootTableConfig ) ;
    public final void rule__GameConfiguration__LootTablesAssignment_7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9158:1: ( ( ruleLootTableConfig ) )
            // InternalMazeDsl.g:9159:2: ( ruleLootTableConfig )
            {
            // InternalMazeDsl.g:9159:2: ( ruleLootTableConfig )
            // InternalMazeDsl.g:9160:3: ruleLootTableConfig
            {
             before(grammarAccess.getGameConfigurationAccess().getLootTablesLootTableConfigParserRuleCall_7_0()); 
            pushFollow(FOLLOW_2);
            ruleLootTableConfig();

            state._fsp--;

             after(grammarAccess.getGameConfigurationAccess().getLootTablesLootTableConfigParserRuleCall_7_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GameConfiguration__LootTablesAssignment_7"


    // $ANTLR start "rule__GameConfiguration__LevelsAssignment_8"
    // InternalMazeDsl.g:9169:1: rule__GameConfiguration__LevelsAssignment_8 : ( ruleLevelConfig ) ;
    public final void rule__GameConfiguration__LevelsAssignment_8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9173:1: ( ( ruleLevelConfig ) )
            // InternalMazeDsl.g:9174:2: ( ruleLevelConfig )
            {
            // InternalMazeDsl.g:9174:2: ( ruleLevelConfig )
            // InternalMazeDsl.g:9175:3: ruleLevelConfig
            {
             before(grammarAccess.getGameConfigurationAccess().getLevelsLevelConfigParserRuleCall_8_0()); 
            pushFollow(FOLLOW_2);
            ruleLevelConfig();

            state._fsp--;

             after(grammarAccess.getGameConfigurationAccess().getLevelsLevelConfigParserRuleCall_8_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GameConfiguration__LevelsAssignment_8"


    // $ANTLR start "rule__Import__ImportURIAssignment_1"
    // InternalMazeDsl.g:9184:1: rule__Import__ImportURIAssignment_1 : ( RULE_STRING ) ;
    public final void rule__Import__ImportURIAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9188:1: ( ( RULE_STRING ) )
            // InternalMazeDsl.g:9189:2: ( RULE_STRING )
            {
            // InternalMazeDsl.g:9189:2: ( RULE_STRING )
            // InternalMazeDsl.g:9190:3: RULE_STRING
            {
             before(grammarAccess.getImportAccess().getImportURISTRINGTerminalRuleCall_1_0()); 
            match(input,RULE_STRING,FOLLOW_2); 
             after(grammarAccess.getImportAccess().getImportURISTRINGTerminalRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Import__ImportURIAssignment_1"


    // $ANTLR start "rule__DifficultyConfig__LevelAssignment_3"
    // InternalMazeDsl.g:9199:1: rule__DifficultyConfig__LevelAssignment_3 : ( ruleDifficultyLevel ) ;
    public final void rule__DifficultyConfig__LevelAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9203:1: ( ( ruleDifficultyLevel ) )
            // InternalMazeDsl.g:9204:2: ( ruleDifficultyLevel )
            {
            // InternalMazeDsl.g:9204:2: ( ruleDifficultyLevel )
            // InternalMazeDsl.g:9205:3: ruleDifficultyLevel
            {
             before(grammarAccess.getDifficultyConfigAccess().getLevelDifficultyLevelEnumRuleCall_3_0()); 
            pushFollow(FOLLOW_2);
            ruleDifficultyLevel();

            state._fsp--;

             after(grammarAccess.getDifficultyConfigAccess().getLevelDifficultyLevelEnumRuleCall_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__LevelAssignment_3"


    // $ANTLR start "rule__DifficultyConfig__InstantDeathAssignment_4_1"
    // InternalMazeDsl.g:9214:1: rule__DifficultyConfig__InstantDeathAssignment_4_1 : ( ( 'true' ) ) ;
    public final void rule__DifficultyConfig__InstantDeathAssignment_4_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9218:1: ( ( ( 'true' ) ) )
            // InternalMazeDsl.g:9219:2: ( ( 'true' ) )
            {
            // InternalMazeDsl.g:9219:2: ( ( 'true' ) )
            // InternalMazeDsl.g:9220:3: ( 'true' )
            {
             before(grammarAccess.getDifficultyConfigAccess().getInstantDeathTrueKeyword_4_1_0()); 
            // InternalMazeDsl.g:9221:3: ( 'true' )
            // InternalMazeDsl.g:9222:4: 'true'
            {
             before(grammarAccess.getDifficultyConfigAccess().getInstantDeathTrueKeyword_4_1_0()); 
            match(input,12,FOLLOW_2); 
             after(grammarAccess.getDifficultyConfigAccess().getInstantDeathTrueKeyword_4_1_0()); 

            }

             after(grammarAccess.getDifficultyConfigAccess().getInstantDeathTrueKeyword_4_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__InstantDeathAssignment_4_1"


    // $ANTLR start "rule__DifficultyConfig__SpeedMultiplierAssignment_5_1"
    // InternalMazeDsl.g:9233:1: rule__DifficultyConfig__SpeedMultiplierAssignment_5_1 : ( ruleDOUBLE ) ;
    public final void rule__DifficultyConfig__SpeedMultiplierAssignment_5_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9237:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:9238:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:9238:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:9239:3: ruleDOUBLE
            {
             before(grammarAccess.getDifficultyConfigAccess().getSpeedMultiplierDOUBLEParserRuleCall_5_1_0()); 
            pushFollow(FOLLOW_2);
            ruleDOUBLE();

            state._fsp--;

             after(grammarAccess.getDifficultyConfigAccess().getSpeedMultiplierDOUBLEParserRuleCall_5_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__SpeedMultiplierAssignment_5_1"


    // $ANTLR start "rule__DifficultyConfig__DamageMultiplierAssignment_6_1"
    // InternalMazeDsl.g:9248:1: rule__DifficultyConfig__DamageMultiplierAssignment_6_1 : ( ruleDOUBLE ) ;
    public final void rule__DifficultyConfig__DamageMultiplierAssignment_6_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9252:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:9253:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:9253:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:9254:3: ruleDOUBLE
            {
             before(grammarAccess.getDifficultyConfigAccess().getDamageMultiplierDOUBLEParserRuleCall_6_1_0()); 
            pushFollow(FOLLOW_2);
            ruleDOUBLE();

            state._fsp--;

             after(grammarAccess.getDifficultyConfigAccess().getDamageMultiplierDOUBLEParserRuleCall_6_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__DamageMultiplierAssignment_6_1"


    // $ANTLR start "rule__DifficultyConfig__MaxThreatAssignment_7_1"
    // InternalMazeDsl.g:9263:1: rule__DifficultyConfig__MaxThreatAssignment_7_1 : ( ruleSIGNED_INT ) ;
    public final void rule__DifficultyConfig__MaxThreatAssignment_7_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9267:1: ( ( ruleSIGNED_INT ) )
            // InternalMazeDsl.g:9268:2: ( ruleSIGNED_INT )
            {
            // InternalMazeDsl.g:9268:2: ( ruleSIGNED_INT )
            // InternalMazeDsl.g:9269:3: ruleSIGNED_INT
            {
             before(grammarAccess.getDifficultyConfigAccess().getMaxThreatSIGNED_INTParserRuleCall_7_1_0()); 
            pushFollow(FOLLOW_2);
            ruleSIGNED_INT();

            state._fsp--;

             after(grammarAccess.getDifficultyConfigAccess().getMaxThreatSIGNED_INTParserRuleCall_7_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__MaxThreatAssignment_7_1"


    // $ANTLR start "rule__DifficultyConfig__EnemyLimitsAssignment_8"
    // InternalMazeDsl.g:9278:1: rule__DifficultyConfig__EnemyLimitsAssignment_8 : ( ruleEnemyLimit ) ;
    public final void rule__DifficultyConfig__EnemyLimitsAssignment_8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9282:1: ( ( ruleEnemyLimit ) )
            // InternalMazeDsl.g:9283:2: ( ruleEnemyLimit )
            {
            // InternalMazeDsl.g:9283:2: ( ruleEnemyLimit )
            // InternalMazeDsl.g:9284:3: ruleEnemyLimit
            {
             before(grammarAccess.getDifficultyConfigAccess().getEnemyLimitsEnemyLimitParserRuleCall_8_0()); 
            pushFollow(FOLLOW_2);
            ruleEnemyLimit();

            state._fsp--;

             after(grammarAccess.getDifficultyConfigAccess().getEnemyLimitsEnemyLimitParserRuleCall_8_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__EnemyLimitsAssignment_8"


    // $ANTLR start "rule__EnemyLimit__TypeAssignment_1"
    // InternalMazeDsl.g:9293:1: rule__EnemyLimit__TypeAssignment_1 : ( ruleEnemyType ) ;
    public final void rule__EnemyLimit__TypeAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9297:1: ( ( ruleEnemyType ) )
            // InternalMazeDsl.g:9298:2: ( ruleEnemyType )
            {
            // InternalMazeDsl.g:9298:2: ( ruleEnemyType )
            // InternalMazeDsl.g:9299:3: ruleEnemyType
            {
             before(grammarAccess.getEnemyLimitAccess().getTypeEnemyTypeEnumRuleCall_1_0()); 
            pushFollow(FOLLOW_2);
            ruleEnemyType();

            state._fsp--;

             after(grammarAccess.getEnemyLimitAccess().getTypeEnemyTypeEnumRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EnemyLimit__TypeAssignment_1"


    // $ANTLR start "rule__EnemyLimit__MaxCountAssignment_3"
    // InternalMazeDsl.g:9308:1: rule__EnemyLimit__MaxCountAssignment_3 : ( ruleSIGNED_INT ) ;
    public final void rule__EnemyLimit__MaxCountAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9312:1: ( ( ruleSIGNED_INT ) )
            // InternalMazeDsl.g:9313:2: ( ruleSIGNED_INT )
            {
            // InternalMazeDsl.g:9313:2: ( ruleSIGNED_INT )
            // InternalMazeDsl.g:9314:3: ruleSIGNED_INT
            {
             before(grammarAccess.getEnemyLimitAccess().getMaxCountSIGNED_INTParserRuleCall_3_0()); 
            pushFollow(FOLLOW_2);
            ruleSIGNED_INT();

            state._fsp--;

             after(grammarAccess.getEnemyLimitAccess().getMaxCountSIGNED_INTParserRuleCall_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EnemyLimit__MaxCountAssignment_3"


    // $ANTLR start "rule__OpponentConfig__NameAssignment_1"
    // InternalMazeDsl.g:9323:1: rule__OpponentConfig__NameAssignment_1 : ( RULE_ID ) ;
    public final void rule__OpponentConfig__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9327:1: ( ( RULE_ID ) )
            // InternalMazeDsl.g:9328:2: ( RULE_ID )
            {
            // InternalMazeDsl.g:9328:2: ( RULE_ID )
            // InternalMazeDsl.g:9329:3: RULE_ID
            {
             before(grammarAccess.getOpponentConfigAccess().getNameIDTerminalRuleCall_1_0()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getOpponentConfigAccess().getNameIDTerminalRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__NameAssignment_1"


    // $ANTLR start "rule__OpponentConfig__TypeAssignment_4"
    // InternalMazeDsl.g:9338:1: rule__OpponentConfig__TypeAssignment_4 : ( ruleCharacterTypeEnum ) ;
    public final void rule__OpponentConfig__TypeAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9342:1: ( ( ruleCharacterTypeEnum ) )
            // InternalMazeDsl.g:9343:2: ( ruleCharacterTypeEnum )
            {
            // InternalMazeDsl.g:9343:2: ( ruleCharacterTypeEnum )
            // InternalMazeDsl.g:9344:3: ruleCharacterTypeEnum
            {
             before(grammarAccess.getOpponentConfigAccess().getTypeCharacterTypeEnumEnumRuleCall_4_0()); 
            pushFollow(FOLLOW_2);
            ruleCharacterTypeEnum();

            state._fsp--;

             after(grammarAccess.getOpponentConfigAccess().getTypeCharacterTypeEnumEnumRuleCall_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__TypeAssignment_4"


    // $ANTLR start "rule__OpponentConfig__DisplayNameAssignment_5_1"
    // InternalMazeDsl.g:9353:1: rule__OpponentConfig__DisplayNameAssignment_5_1 : ( RULE_STRING ) ;
    public final void rule__OpponentConfig__DisplayNameAssignment_5_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9357:1: ( ( RULE_STRING ) )
            // InternalMazeDsl.g:9358:2: ( RULE_STRING )
            {
            // InternalMazeDsl.g:9358:2: ( RULE_STRING )
            // InternalMazeDsl.g:9359:3: RULE_STRING
            {
             before(grammarAccess.getOpponentConfigAccess().getDisplayNameSTRINGTerminalRuleCall_5_1_0()); 
            match(input,RULE_STRING,FOLLOW_2); 
             after(grammarAccess.getOpponentConfigAccess().getDisplayNameSTRINGTerminalRuleCall_5_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__DisplayNameAssignment_5_1"


    // $ANTLR start "rule__OpponentConfig__HealthAssignment_6_1"
    // InternalMazeDsl.g:9368:1: rule__OpponentConfig__HealthAssignment_6_1 : ( ruleSIGNED_INT ) ;
    public final void rule__OpponentConfig__HealthAssignment_6_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9372:1: ( ( ruleSIGNED_INT ) )
            // InternalMazeDsl.g:9373:2: ( ruleSIGNED_INT )
            {
            // InternalMazeDsl.g:9373:2: ( ruleSIGNED_INT )
            // InternalMazeDsl.g:9374:3: ruleSIGNED_INT
            {
             before(grammarAccess.getOpponentConfigAccess().getHealthSIGNED_INTParserRuleCall_6_1_0()); 
            pushFollow(FOLLOW_2);
            ruleSIGNED_INT();

            state._fsp--;

             after(grammarAccess.getOpponentConfigAccess().getHealthSIGNED_INTParserRuleCall_6_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__HealthAssignment_6_1"


    // $ANTLR start "rule__OpponentConfig__SpeedAssignment_7_1"
    // InternalMazeDsl.g:9383:1: rule__OpponentConfig__SpeedAssignment_7_1 : ( ruleDOUBLE ) ;
    public final void rule__OpponentConfig__SpeedAssignment_7_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9387:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:9388:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:9388:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:9389:3: ruleDOUBLE
            {
             before(grammarAccess.getOpponentConfigAccess().getSpeedDOUBLEParserRuleCall_7_1_0()); 
            pushFollow(FOLLOW_2);
            ruleDOUBLE();

            state._fsp--;

             after(grammarAccess.getOpponentConfigAccess().getSpeedDOUBLEParserRuleCall_7_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__SpeedAssignment_7_1"


    // $ANTLR start "rule__OpponentConfig__ThreatLevelAssignment_8_1"
    // InternalMazeDsl.g:9398:1: rule__OpponentConfig__ThreatLevelAssignment_8_1 : ( ruleDOUBLE ) ;
    public final void rule__OpponentConfig__ThreatLevelAssignment_8_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9402:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:9403:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:9403:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:9404:3: ruleDOUBLE
            {
             before(grammarAccess.getOpponentConfigAccess().getThreatLevelDOUBLEParserRuleCall_8_1_0()); 
            pushFollow(FOLLOW_2);
            ruleDOUBLE();

            state._fsp--;

             after(grammarAccess.getOpponentConfigAccess().getThreatLevelDOUBLEParserRuleCall_8_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__ThreatLevelAssignment_8_1"


    // $ANTLR start "rule__OpponentConfig__EnabledAssignment_9_1"
    // InternalMazeDsl.g:9413:1: rule__OpponentConfig__EnabledAssignment_9_1 : ( ruleBOOLEAN ) ;
    public final void rule__OpponentConfig__EnabledAssignment_9_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9417:1: ( ( ruleBOOLEAN ) )
            // InternalMazeDsl.g:9418:2: ( ruleBOOLEAN )
            {
            // InternalMazeDsl.g:9418:2: ( ruleBOOLEAN )
            // InternalMazeDsl.g:9419:3: ruleBOOLEAN
            {
             before(grammarAccess.getOpponentConfigAccess().getEnabledBOOLEANParserRuleCall_9_1_0()); 
            pushFollow(FOLLOW_2);
            ruleBOOLEAN();

            state._fsp--;

             after(grammarAccess.getOpponentConfigAccess().getEnabledBOOLEANParserRuleCall_9_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__EnabledAssignment_9_1"


    // $ANTLR start "rule__OpponentConfig__BehaviorAssignment_10_1"
    // InternalMazeDsl.g:9428:1: rule__OpponentConfig__BehaviorAssignment_10_1 : ( ruleBehaviorTypeEnum ) ;
    public final void rule__OpponentConfig__BehaviorAssignment_10_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9432:1: ( ( ruleBehaviorTypeEnum ) )
            // InternalMazeDsl.g:9433:2: ( ruleBehaviorTypeEnum )
            {
            // InternalMazeDsl.g:9433:2: ( ruleBehaviorTypeEnum )
            // InternalMazeDsl.g:9434:3: ruleBehaviorTypeEnum
            {
             before(grammarAccess.getOpponentConfigAccess().getBehaviorBehaviorTypeEnumEnumRuleCall_10_1_0()); 
            pushFollow(FOLLOW_2);
            ruleBehaviorTypeEnum();

            state._fsp--;

             after(grammarAccess.getOpponentConfigAccess().getBehaviorBehaviorTypeEnumEnumRuleCall_10_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__BehaviorAssignment_10_1"


    // $ANTLR start "rule__OpponentConfig__CharacterSpecificsAssignment_11"
    // InternalMazeDsl.g:9443:1: rule__OpponentConfig__CharacterSpecificsAssignment_11 : ( ruleCharacterSpecifics ) ;
    public final void rule__OpponentConfig__CharacterSpecificsAssignment_11() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9447:1: ( ( ruleCharacterSpecifics ) )
            // InternalMazeDsl.g:9448:2: ( ruleCharacterSpecifics )
            {
            // InternalMazeDsl.g:9448:2: ( ruleCharacterSpecifics )
            // InternalMazeDsl.g:9449:3: ruleCharacterSpecifics
            {
             before(grammarAccess.getOpponentConfigAccess().getCharacterSpecificsCharacterSpecificsParserRuleCall_11_0()); 
            pushFollow(FOLLOW_2);
            ruleCharacterSpecifics();

            state._fsp--;

             after(grammarAccess.getOpponentConfigAccess().getCharacterSpecificsCharacterSpecificsParserRuleCall_11_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__CharacterSpecificsAssignment_11"


    // $ANTLR start "rule__OpponentConfig__PatrolRefAssignment_12_1"
    // InternalMazeDsl.g:9458:1: rule__OpponentConfig__PatrolRefAssignment_12_1 : ( ( RULE_ID ) ) ;
    public final void rule__OpponentConfig__PatrolRefAssignment_12_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9462:1: ( ( ( RULE_ID ) ) )
            // InternalMazeDsl.g:9463:2: ( ( RULE_ID ) )
            {
            // InternalMazeDsl.g:9463:2: ( ( RULE_ID ) )
            // InternalMazeDsl.g:9464:3: ( RULE_ID )
            {
             before(grammarAccess.getOpponentConfigAccess().getPatrolRefPatrolConfigCrossReference_12_1_0()); 
            // InternalMazeDsl.g:9465:3: ( RULE_ID )
            // InternalMazeDsl.g:9466:4: RULE_ID
            {
             before(grammarAccess.getOpponentConfigAccess().getPatrolRefPatrolConfigIDTerminalRuleCall_12_1_0_1()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getOpponentConfigAccess().getPatrolRefPatrolConfigIDTerminalRuleCall_12_1_0_1()); 

            }

             after(grammarAccess.getOpponentConfigAccess().getPatrolRefPatrolConfigCrossReference_12_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__PatrolRefAssignment_12_1"


    // $ANTLR start "rule__OpponentConfig__LootRefAssignment_13_1"
    // InternalMazeDsl.g:9477:1: rule__OpponentConfig__LootRefAssignment_13_1 : ( ( RULE_ID ) ) ;
    public final void rule__OpponentConfig__LootRefAssignment_13_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9481:1: ( ( ( RULE_ID ) ) )
            // InternalMazeDsl.g:9482:2: ( ( RULE_ID ) )
            {
            // InternalMazeDsl.g:9482:2: ( ( RULE_ID ) )
            // InternalMazeDsl.g:9483:3: ( RULE_ID )
            {
             before(grammarAccess.getOpponentConfigAccess().getLootRefLootTableConfigCrossReference_13_1_0()); 
            // InternalMazeDsl.g:9484:3: ( RULE_ID )
            // InternalMazeDsl.g:9485:4: RULE_ID
            {
             before(grammarAccess.getOpponentConfigAccess().getLootRefLootTableConfigIDTerminalRuleCall_13_1_0_1()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getOpponentConfigAccess().getLootRefLootTableConfigIDTerminalRuleCall_13_1_0_1()); 

            }

             after(grammarAccess.getOpponentConfigAccess().getLootRefLootTableConfigCrossReference_13_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__LootRefAssignment_13_1"


    // $ANTLR start "rule__ZombieSpecifics__AttackDamageAssignment_3_1"
    // InternalMazeDsl.g:9496:1: rule__ZombieSpecifics__AttackDamageAssignment_3_1 : ( RULE_INT ) ;
    public final void rule__ZombieSpecifics__AttackDamageAssignment_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9500:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:9501:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:9501:2: ( RULE_INT )
            // InternalMazeDsl.g:9502:3: RULE_INT
            {
             before(grammarAccess.getZombieSpecificsAccess().getAttackDamageINTTerminalRuleCall_3_1_0()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getZombieSpecificsAccess().getAttackDamageINTTerminalRuleCall_3_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__AttackDamageAssignment_3_1"


    // $ANTLR start "rule__ZombieSpecifics__InfectionLevelAssignment_4_1"
    // InternalMazeDsl.g:9511:1: rule__ZombieSpecifics__InfectionLevelAssignment_4_1 : ( RULE_INT ) ;
    public final void rule__ZombieSpecifics__InfectionLevelAssignment_4_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9515:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:9516:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:9516:2: ( RULE_INT )
            // InternalMazeDsl.g:9517:3: RULE_INT
            {
             before(grammarAccess.getZombieSpecificsAccess().getInfectionLevelINTTerminalRuleCall_4_1_0()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getZombieSpecificsAccess().getInfectionLevelINTTerminalRuleCall_4_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__InfectionLevelAssignment_4_1"


    // $ANTLR start "rule__ZombieSpecifics__ResurrectionTimeAssignment_5_1"
    // InternalMazeDsl.g:9526:1: rule__ZombieSpecifics__ResurrectionTimeAssignment_5_1 : ( RULE_INT ) ;
    public final void rule__ZombieSpecifics__ResurrectionTimeAssignment_5_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9530:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:9531:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:9531:2: ( RULE_INT )
            // InternalMazeDsl.g:9532:3: RULE_INT
            {
             before(grammarAccess.getZombieSpecificsAccess().getResurrectionTimeINTTerminalRuleCall_5_1_0()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getZombieSpecificsAccess().getResurrectionTimeINTTerminalRuleCall_5_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__ResurrectionTimeAssignment_5_1"


    // $ANTLR start "rule__GhostSpecifics__AttackDamageAssignment_3_1"
    // InternalMazeDsl.g:9541:1: rule__GhostSpecifics__AttackDamageAssignment_3_1 : ( RULE_INT ) ;
    public final void rule__GhostSpecifics__AttackDamageAssignment_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9545:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:9546:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:9546:2: ( RULE_INT )
            // InternalMazeDsl.g:9547:3: RULE_INT
            {
             before(grammarAccess.getGhostSpecificsAccess().getAttackDamageINTTerminalRuleCall_3_1_0()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getGhostSpecificsAccess().getAttackDamageINTTerminalRuleCall_3_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__AttackDamageAssignment_3_1"


    // $ANTLR start "rule__GhostSpecifics__VisibilityLevelAssignment_4_1"
    // InternalMazeDsl.g:9556:1: rule__GhostSpecifics__VisibilityLevelAssignment_4_1 : ( RULE_INT ) ;
    public final void rule__GhostSpecifics__VisibilityLevelAssignment_4_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9560:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:9561:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:9561:2: ( RULE_INT )
            // InternalMazeDsl.g:9562:3: RULE_INT
            {
             before(grammarAccess.getGhostSpecificsAccess().getVisibilityLevelINTTerminalRuleCall_4_1_0()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getGhostSpecificsAccess().getVisibilityLevelINTTerminalRuleCall_4_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__VisibilityLevelAssignment_4_1"


    // $ANTLR start "rule__GhostSpecifics__NonTangibilityEnergyAssignment_5_1"
    // InternalMazeDsl.g:9571:1: rule__GhostSpecifics__NonTangibilityEnergyAssignment_5_1 : ( ruleDOUBLE ) ;
    public final void rule__GhostSpecifics__NonTangibilityEnergyAssignment_5_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9575:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:9576:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:9576:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:9577:3: ruleDOUBLE
            {
             before(grammarAccess.getGhostSpecificsAccess().getNonTangibilityEnergyDOUBLEParserRuleCall_5_1_0()); 
            pushFollow(FOLLOW_2);
            ruleDOUBLE();

            state._fsp--;

             after(grammarAccess.getGhostSpecificsAccess().getNonTangibilityEnergyDOUBLEParserRuleCall_5_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__NonTangibilityEnergyAssignment_5_1"


    // $ANTLR start "rule__RangedSpecifics__AttackRangeAssignment_3_1"
    // InternalMazeDsl.g:9586:1: rule__RangedSpecifics__AttackRangeAssignment_3_1 : ( ruleDOUBLE ) ;
    public final void rule__RangedSpecifics__AttackRangeAssignment_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9590:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:9591:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:9591:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:9592:3: ruleDOUBLE
            {
             before(grammarAccess.getRangedSpecificsAccess().getAttackRangeDOUBLEParserRuleCall_3_1_0()); 
            pushFollow(FOLLOW_2);
            ruleDOUBLE();

            state._fsp--;

             after(grammarAccess.getRangedSpecificsAccess().getAttackRangeDOUBLEParserRuleCall_3_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__AttackRangeAssignment_3_1"


    // $ANTLR start "rule__RangedSpecifics__AttackCooldownAssignment_4_1"
    // InternalMazeDsl.g:9601:1: rule__RangedSpecifics__AttackCooldownAssignment_4_1 : ( RULE_INT ) ;
    public final void rule__RangedSpecifics__AttackCooldownAssignment_4_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9605:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:9606:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:9606:2: ( RULE_INT )
            // InternalMazeDsl.g:9607:3: RULE_INT
            {
             before(grammarAccess.getRangedSpecificsAccess().getAttackCooldownINTTerminalRuleCall_4_1_0()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getRangedSpecificsAccess().getAttackCooldownINTTerminalRuleCall_4_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__AttackCooldownAssignment_4_1"


    // $ANTLR start "rule__RangedSpecifics__AttackDamageAssignment_5_1"
    // InternalMazeDsl.g:9616:1: rule__RangedSpecifics__AttackDamageAssignment_5_1 : ( RULE_INT ) ;
    public final void rule__RangedSpecifics__AttackDamageAssignment_5_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9620:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:9621:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:9621:2: ( RULE_INT )
            // InternalMazeDsl.g:9622:3: RULE_INT
            {
             before(grammarAccess.getRangedSpecificsAccess().getAttackDamageINTTerminalRuleCall_5_1_0()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getRangedSpecificsAccess().getAttackDamageINTTerminalRuleCall_5_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__AttackDamageAssignment_5_1"


    // $ANTLR start "rule__RangedSpecifics__ProjectileSpeedAssignment_6_1"
    // InternalMazeDsl.g:9631:1: rule__RangedSpecifics__ProjectileSpeedAssignment_6_1 : ( ruleDOUBLE ) ;
    public final void rule__RangedSpecifics__ProjectileSpeedAssignment_6_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9635:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:9636:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:9636:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:9637:3: ruleDOUBLE
            {
             before(grammarAccess.getRangedSpecificsAccess().getProjectileSpeedDOUBLEParserRuleCall_6_1_0()); 
            pushFollow(FOLLOW_2);
            ruleDOUBLE();

            state._fsp--;

             after(grammarAccess.getRangedSpecificsAccess().getProjectileSpeedDOUBLEParserRuleCall_6_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__ProjectileSpeedAssignment_6_1"


    // $ANTLR start "rule__RangedSpecifics__ProjectileTypeAssignment_7_1"
    // InternalMazeDsl.g:9646:1: rule__RangedSpecifics__ProjectileTypeAssignment_7_1 : ( ruleProjectileTypeEnum ) ;
    public final void rule__RangedSpecifics__ProjectileTypeAssignment_7_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9650:1: ( ( ruleProjectileTypeEnum ) )
            // InternalMazeDsl.g:9651:2: ( ruleProjectileTypeEnum )
            {
            // InternalMazeDsl.g:9651:2: ( ruleProjectileTypeEnum )
            // InternalMazeDsl.g:9652:3: ruleProjectileTypeEnum
            {
             before(grammarAccess.getRangedSpecificsAccess().getProjectileTypeProjectileTypeEnumEnumRuleCall_7_1_0()); 
            pushFollow(FOLLOW_2);
            ruleProjectileTypeEnum();

            state._fsp--;

             after(grammarAccess.getRangedSpecificsAccess().getProjectileTypeProjectileTypeEnumEnumRuleCall_7_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__ProjectileTypeAssignment_7_1"


    // $ANTLR start "rule__RangedSpecifics__SplashRadiusAssignment_8_1"
    // InternalMazeDsl.g:9661:1: rule__RangedSpecifics__SplashRadiusAssignment_8_1 : ( ruleDOUBLE ) ;
    public final void rule__RangedSpecifics__SplashRadiusAssignment_8_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9665:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:9666:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:9666:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:9667:3: ruleDOUBLE
            {
             before(grammarAccess.getRangedSpecificsAccess().getSplashRadiusDOUBLEParserRuleCall_8_1_0()); 
            pushFollow(FOLLOW_2);
            ruleDOUBLE();

            state._fsp--;

             after(grammarAccess.getRangedSpecificsAccess().getSplashRadiusDOUBLEParserRuleCall_8_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__SplashRadiusAssignment_8_1"


    // $ANTLR start "rule__PatrolConfig__NameAssignment_1"
    // InternalMazeDsl.g:9676:1: rule__PatrolConfig__NameAssignment_1 : ( RULE_ID ) ;
    public final void rule__PatrolConfig__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9680:1: ( ( RULE_ID ) )
            // InternalMazeDsl.g:9681:2: ( RULE_ID )
            {
            // InternalMazeDsl.g:9681:2: ( RULE_ID )
            // InternalMazeDsl.g:9682:3: RULE_ID
            {
             before(grammarAccess.getPatrolConfigAccess().getNameIDTerminalRuleCall_1_0()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getPatrolConfigAccess().getNameIDTerminalRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__NameAssignment_1"


    // $ANTLR start "rule__PatrolConfig__VisionRangeAssignment_3_1"
    // InternalMazeDsl.g:9691:1: rule__PatrolConfig__VisionRangeAssignment_3_1 : ( ruleDOUBLE ) ;
    public final void rule__PatrolConfig__VisionRangeAssignment_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9695:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:9696:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:9696:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:9697:3: ruleDOUBLE
            {
             before(grammarAccess.getPatrolConfigAccess().getVisionRangeDOUBLEParserRuleCall_3_1_0()); 
            pushFollow(FOLLOW_2);
            ruleDOUBLE();

            state._fsp--;

             after(grammarAccess.getPatrolConfigAccess().getVisionRangeDOUBLEParserRuleCall_3_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__VisionRangeAssignment_3_1"


    // $ANTLR start "rule__PatrolConfig__ZoneAssignment_4"
    // InternalMazeDsl.g:9706:1: rule__PatrolConfig__ZoneAssignment_4 : ( rulePatrolZoneConfig ) ;
    public final void rule__PatrolConfig__ZoneAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9710:1: ( ( rulePatrolZoneConfig ) )
            // InternalMazeDsl.g:9711:2: ( rulePatrolZoneConfig )
            {
            // InternalMazeDsl.g:9711:2: ( rulePatrolZoneConfig )
            // InternalMazeDsl.g:9712:3: rulePatrolZoneConfig
            {
             before(grammarAccess.getPatrolConfigAccess().getZonePatrolZoneConfigParserRuleCall_4_0()); 
            pushFollow(FOLLOW_2);
            rulePatrolZoneConfig();

            state._fsp--;

             after(grammarAccess.getPatrolConfigAccess().getZonePatrolZoneConfigParserRuleCall_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__ZoneAssignment_4"


    // $ANTLR start "rule__PatrolConfig__WaypointsAssignment_7"
    // InternalMazeDsl.g:9721:1: rule__PatrolConfig__WaypointsAssignment_7 : ( ruleWaypoint ) ;
    public final void rule__PatrolConfig__WaypointsAssignment_7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9725:1: ( ( ruleWaypoint ) )
            // InternalMazeDsl.g:9726:2: ( ruleWaypoint )
            {
            // InternalMazeDsl.g:9726:2: ( ruleWaypoint )
            // InternalMazeDsl.g:9727:3: ruleWaypoint
            {
             before(grammarAccess.getPatrolConfigAccess().getWaypointsWaypointParserRuleCall_7_0()); 
            pushFollow(FOLLOW_2);
            ruleWaypoint();

            state._fsp--;

             after(grammarAccess.getPatrolConfigAccess().getWaypointsWaypointParserRuleCall_7_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__WaypointsAssignment_7"


    // $ANTLR start "rule__PatrolConfig__WaypointsAssignment_8_1"
    // InternalMazeDsl.g:9736:1: rule__PatrolConfig__WaypointsAssignment_8_1 : ( ruleWaypoint ) ;
    public final void rule__PatrolConfig__WaypointsAssignment_8_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9740:1: ( ( ruleWaypoint ) )
            // InternalMazeDsl.g:9741:2: ( ruleWaypoint )
            {
            // InternalMazeDsl.g:9741:2: ( ruleWaypoint )
            // InternalMazeDsl.g:9742:3: ruleWaypoint
            {
             before(grammarAccess.getPatrolConfigAccess().getWaypointsWaypointParserRuleCall_8_1_0()); 
            pushFollow(FOLLOW_2);
            ruleWaypoint();

            state._fsp--;

             after(grammarAccess.getPatrolConfigAccess().getWaypointsWaypointParserRuleCall_8_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__WaypointsAssignment_8_1"


    // $ANTLR start "rule__PatrolZoneConfig__TopLeftXAssignment_4"
    // InternalMazeDsl.g:9751:1: rule__PatrolZoneConfig__TopLeftXAssignment_4 : ( ruleDOUBLE ) ;
    public final void rule__PatrolZoneConfig__TopLeftXAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9755:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:9756:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:9756:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:9757:3: ruleDOUBLE
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getTopLeftXDOUBLEParserRuleCall_4_0()); 
            pushFollow(FOLLOW_2);
            ruleDOUBLE();

            state._fsp--;

             after(grammarAccess.getPatrolZoneConfigAccess().getTopLeftXDOUBLEParserRuleCall_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__TopLeftXAssignment_4"


    // $ANTLR start "rule__PatrolZoneConfig__TopLeftYAssignment_6"
    // InternalMazeDsl.g:9766:1: rule__PatrolZoneConfig__TopLeftYAssignment_6 : ( ruleDOUBLE ) ;
    public final void rule__PatrolZoneConfig__TopLeftYAssignment_6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9770:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:9771:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:9771:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:9772:3: ruleDOUBLE
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getTopLeftYDOUBLEParserRuleCall_6_0()); 
            pushFollow(FOLLOW_2);
            ruleDOUBLE();

            state._fsp--;

             after(grammarAccess.getPatrolZoneConfigAccess().getTopLeftYDOUBLEParserRuleCall_6_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__TopLeftYAssignment_6"


    // $ANTLR start "rule__PatrolZoneConfig__WidthAssignment_9"
    // InternalMazeDsl.g:9781:1: rule__PatrolZoneConfig__WidthAssignment_9 : ( ruleDOUBLE ) ;
    public final void rule__PatrolZoneConfig__WidthAssignment_9() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9785:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:9786:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:9786:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:9787:3: ruleDOUBLE
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getWidthDOUBLEParserRuleCall_9_0()); 
            pushFollow(FOLLOW_2);
            ruleDOUBLE();

            state._fsp--;

             after(grammarAccess.getPatrolZoneConfigAccess().getWidthDOUBLEParserRuleCall_9_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__WidthAssignment_9"


    // $ANTLR start "rule__PatrolZoneConfig__HeightAssignment_11"
    // InternalMazeDsl.g:9796:1: rule__PatrolZoneConfig__HeightAssignment_11 : ( ruleDOUBLE ) ;
    public final void rule__PatrolZoneConfig__HeightAssignment_11() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9800:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:9801:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:9801:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:9802:3: ruleDOUBLE
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getHeightDOUBLEParserRuleCall_11_0()); 
            pushFollow(FOLLOW_2);
            ruleDOUBLE();

            state._fsp--;

             after(grammarAccess.getPatrolZoneConfigAccess().getHeightDOUBLEParserRuleCall_11_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__HeightAssignment_11"


    // $ANTLR start "rule__Waypoint__XAssignment_1"
    // InternalMazeDsl.g:9811:1: rule__Waypoint__XAssignment_1 : ( ruleDOUBLE ) ;
    public final void rule__Waypoint__XAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9815:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:9816:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:9816:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:9817:3: ruleDOUBLE
            {
             before(grammarAccess.getWaypointAccess().getXDOUBLEParserRuleCall_1_0()); 
            pushFollow(FOLLOW_2);
            ruleDOUBLE();

            state._fsp--;

             after(grammarAccess.getWaypointAccess().getXDOUBLEParserRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Waypoint__XAssignment_1"


    // $ANTLR start "rule__Waypoint__YAssignment_3"
    // InternalMazeDsl.g:9826:1: rule__Waypoint__YAssignment_3 : ( ruleDOUBLE ) ;
    public final void rule__Waypoint__YAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9830:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:9831:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:9831:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:9832:3: ruleDOUBLE
            {
             before(grammarAccess.getWaypointAccess().getYDOUBLEParserRuleCall_3_0()); 
            pushFollow(FOLLOW_2);
            ruleDOUBLE();

            state._fsp--;

             after(grammarAccess.getWaypointAccess().getYDOUBLEParserRuleCall_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Waypoint__YAssignment_3"


    // $ANTLR start "rule__Waypoint__WaitTimeAssignment_5_1"
    // InternalMazeDsl.g:9841:1: rule__Waypoint__WaitTimeAssignment_5_1 : ( RULE_INT ) ;
    public final void rule__Waypoint__WaitTimeAssignment_5_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9845:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:9846:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:9846:2: ( RULE_INT )
            // InternalMazeDsl.g:9847:3: RULE_INT
            {
             before(grammarAccess.getWaypointAccess().getWaitTimeINTTerminalRuleCall_5_1_0()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getWaypointAccess().getWaitTimeINTTerminalRuleCall_5_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Waypoint__WaitTimeAssignment_5_1"


    // $ANTLR start "rule__LootTableConfig__NameAssignment_1"
    // InternalMazeDsl.g:9856:1: rule__LootTableConfig__NameAssignment_1 : ( RULE_ID ) ;
    public final void rule__LootTableConfig__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9860:1: ( ( RULE_ID ) )
            // InternalMazeDsl.g:9861:2: ( RULE_ID )
            {
            // InternalMazeDsl.g:9861:2: ( RULE_ID )
            // InternalMazeDsl.g:9862:3: RULE_ID
            {
             before(grammarAccess.getLootTableConfigAccess().getNameIDTerminalRuleCall_1_0()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getLootTableConfigAccess().getNameIDTerminalRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootTableConfig__NameAssignment_1"


    // $ANTLR start "rule__LootTableConfig__CapacityAssignment_3_1"
    // InternalMazeDsl.g:9871:1: rule__LootTableConfig__CapacityAssignment_3_1 : ( RULE_INT ) ;
    public final void rule__LootTableConfig__CapacityAssignment_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9875:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:9876:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:9876:2: ( RULE_INT )
            // InternalMazeDsl.g:9877:3: RULE_INT
            {
             before(grammarAccess.getLootTableConfigAccess().getCapacityINTTerminalRuleCall_3_1_0()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getLootTableConfigAccess().getCapacityINTTerminalRuleCall_3_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootTableConfig__CapacityAssignment_3_1"


    // $ANTLR start "rule__LootTableConfig__ItemsAssignment_4"
    // InternalMazeDsl.g:9886:1: rule__LootTableConfig__ItemsAssignment_4 : ( ruleLootItemConfig ) ;
    public final void rule__LootTableConfig__ItemsAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9890:1: ( ( ruleLootItemConfig ) )
            // InternalMazeDsl.g:9891:2: ( ruleLootItemConfig )
            {
            // InternalMazeDsl.g:9891:2: ( ruleLootItemConfig )
            // InternalMazeDsl.g:9892:3: ruleLootItemConfig
            {
             before(grammarAccess.getLootTableConfigAccess().getItemsLootItemConfigParserRuleCall_4_0()); 
            pushFollow(FOLLOW_2);
            ruleLootItemConfig();

            state._fsp--;

             after(grammarAccess.getLootTableConfigAccess().getItemsLootItemConfigParserRuleCall_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootTableConfig__ItemsAssignment_4"


    // $ANTLR start "rule__LootItemConfig__NameAssignment_1"
    // InternalMazeDsl.g:9901:1: rule__LootItemConfig__NameAssignment_1 : ( RULE_ID ) ;
    public final void rule__LootItemConfig__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9905:1: ( ( RULE_ID ) )
            // InternalMazeDsl.g:9906:2: ( RULE_ID )
            {
            // InternalMazeDsl.g:9906:2: ( RULE_ID )
            // InternalMazeDsl.g:9907:3: RULE_ID
            {
             before(grammarAccess.getLootItemConfigAccess().getNameIDTerminalRuleCall_1_0()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getLootItemConfigAccess().getNameIDTerminalRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootItemConfig__NameAssignment_1"


    // $ANTLR start "rule__LootItemConfig__TypeAssignment_4"
    // InternalMazeDsl.g:9916:1: rule__LootItemConfig__TypeAssignment_4 : ( ruleLootItemTypeEnum ) ;
    public final void rule__LootItemConfig__TypeAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9920:1: ( ( ruleLootItemTypeEnum ) )
            // InternalMazeDsl.g:9921:2: ( ruleLootItemTypeEnum )
            {
            // InternalMazeDsl.g:9921:2: ( ruleLootItemTypeEnum )
            // InternalMazeDsl.g:9922:3: ruleLootItemTypeEnum
            {
             before(grammarAccess.getLootItemConfigAccess().getTypeLootItemTypeEnumEnumRuleCall_4_0()); 
            pushFollow(FOLLOW_2);
            ruleLootItemTypeEnum();

            state._fsp--;

             after(grammarAccess.getLootItemConfigAccess().getTypeLootItemTypeEnumEnumRuleCall_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootItemConfig__TypeAssignment_4"


    // $ANTLR start "rule__LootItemConfig__ValueAssignment_6"
    // InternalMazeDsl.g:9931:1: rule__LootItemConfig__ValueAssignment_6 : ( RULE_INT ) ;
    public final void rule__LootItemConfig__ValueAssignment_6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9935:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:9936:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:9936:2: ( RULE_INT )
            // InternalMazeDsl.g:9937:3: RULE_INT
            {
             before(grammarAccess.getLootItemConfigAccess().getValueINTTerminalRuleCall_6_0()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getLootItemConfigAccess().getValueINTTerminalRuleCall_6_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootItemConfig__ValueAssignment_6"


    // $ANTLR start "rule__LootItemConfig__WeightAssignment_7_1"
    // InternalMazeDsl.g:9946:1: rule__LootItemConfig__WeightAssignment_7_1 : ( RULE_INT ) ;
    public final void rule__LootItemConfig__WeightAssignment_7_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9950:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:9951:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:9951:2: ( RULE_INT )
            // InternalMazeDsl.g:9952:3: RULE_INT
            {
             before(grammarAccess.getLootItemConfigAccess().getWeightINTTerminalRuleCall_7_1_0()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getLootItemConfigAccess().getWeightINTTerminalRuleCall_7_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootItemConfig__WeightAssignment_7_1"


    // $ANTLR start "rule__LevelConfig__NumberAssignment_1"
    // InternalMazeDsl.g:9961:1: rule__LevelConfig__NumberAssignment_1 : ( RULE_INT ) ;
    public final void rule__LevelConfig__NumberAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9965:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:9966:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:9966:2: ( RULE_INT )
            // InternalMazeDsl.g:9967:3: RULE_INT
            {
             before(grammarAccess.getLevelConfigAccess().getNumberINTTerminalRuleCall_1_0()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getLevelConfigAccess().getNumberINTTerminalRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__NumberAssignment_1"


    // $ANTLR start "rule__LevelConfig__NameAssignment_2"
    // InternalMazeDsl.g:9976:1: rule__LevelConfig__NameAssignment_2 : ( RULE_STRING ) ;
    public final void rule__LevelConfig__NameAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9980:1: ( ( RULE_STRING ) )
            // InternalMazeDsl.g:9981:2: ( RULE_STRING )
            {
            // InternalMazeDsl.g:9981:2: ( RULE_STRING )
            // InternalMazeDsl.g:9982:3: RULE_STRING
            {
             before(grammarAccess.getLevelConfigAccess().getNameSTRINGTerminalRuleCall_2_0()); 
            match(input,RULE_STRING,FOLLOW_2); 
             after(grammarAccess.getLevelConfigAccess().getNameSTRINGTerminalRuleCall_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__NameAssignment_2"


    // $ANTLR start "rule__LevelConfig__BaseLevelAssignment_4_1"
    // InternalMazeDsl.g:9991:1: rule__LevelConfig__BaseLevelAssignment_4_1 : ( RULE_INT ) ;
    public final void rule__LevelConfig__BaseLevelAssignment_4_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9995:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:9996:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:9996:2: ( RULE_INT )
            // InternalMazeDsl.g:9997:3: RULE_INT
            {
             before(grammarAccess.getLevelConfigAccess().getBaseLevelINTTerminalRuleCall_4_1_0()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getLevelConfigAccess().getBaseLevelINTTerminalRuleCall_4_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__BaseLevelAssignment_4_1"


    // $ANTLR start "rule__LevelConfig__ThreatCapacityFactorAssignment_5_1"
    // InternalMazeDsl.g:10006:1: rule__LevelConfig__ThreatCapacityFactorAssignment_5_1 : ( ruleDOUBLE_OBJECT ) ;
    public final void rule__LevelConfig__ThreatCapacityFactorAssignment_5_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10010:1: ( ( ruleDOUBLE_OBJECT ) )
            // InternalMazeDsl.g:10011:2: ( ruleDOUBLE_OBJECT )
            {
            // InternalMazeDsl.g:10011:2: ( ruleDOUBLE_OBJECT )
            // InternalMazeDsl.g:10012:3: ruleDOUBLE_OBJECT
            {
             before(grammarAccess.getLevelConfigAccess().getThreatCapacityFactorDOUBLE_OBJECTParserRuleCall_5_1_0()); 
            pushFollow(FOLLOW_2);
            ruleDOUBLE_OBJECT();

            state._fsp--;

             after(grammarAccess.getLevelConfigAccess().getThreatCapacityFactorDOUBLE_OBJECTParserRuleCall_5_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__ThreatCapacityFactorAssignment_5_1"


    // $ANTLR start "rule__LevelConfig__ShowWallMaterialsAssignment_6_1"
    // InternalMazeDsl.g:10021:1: rule__LevelConfig__ShowWallMaterialsAssignment_6_1 : ( ruleBOOLEAN_OBJECT ) ;
    public final void rule__LevelConfig__ShowWallMaterialsAssignment_6_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10025:1: ( ( ruleBOOLEAN_OBJECT ) )
            // InternalMazeDsl.g:10026:2: ( ruleBOOLEAN_OBJECT )
            {
            // InternalMazeDsl.g:10026:2: ( ruleBOOLEAN_OBJECT )
            // InternalMazeDsl.g:10027:3: ruleBOOLEAN_OBJECT
            {
             before(grammarAccess.getLevelConfigAccess().getShowWallMaterialsBOOLEAN_OBJECTParserRuleCall_6_1_0()); 
            pushFollow(FOLLOW_2);
            ruleBOOLEAN_OBJECT();

            state._fsp--;

             after(grammarAccess.getLevelConfigAccess().getShowWallMaterialsBOOLEAN_OBJECTParserRuleCall_6_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__ShowWallMaterialsAssignment_6_1"


    // $ANTLR start "rule__LevelConfig__InheritWeaponsAssignment_7_1"
    // InternalMazeDsl.g:10036:1: rule__LevelConfig__InheritWeaponsAssignment_7_1 : ( ruleBOOLEAN_OBJECT ) ;
    public final void rule__LevelConfig__InheritWeaponsAssignment_7_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10040:1: ( ( ruleBOOLEAN_OBJECT ) )
            // InternalMazeDsl.g:10041:2: ( ruleBOOLEAN_OBJECT )
            {
            // InternalMazeDsl.g:10041:2: ( ruleBOOLEAN_OBJECT )
            // InternalMazeDsl.g:10042:3: ruleBOOLEAN_OBJECT
            {
             before(grammarAccess.getLevelConfigAccess().getInheritWeaponsBOOLEAN_OBJECTParserRuleCall_7_1_0()); 
            pushFollow(FOLLOW_2);
            ruleBOOLEAN_OBJECT();

            state._fsp--;

             after(grammarAccess.getLevelConfigAccess().getInheritWeaponsBOOLEAN_OBJECTParserRuleCall_7_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__InheritWeaponsAssignment_7_1"


    // $ANTLR start "rule__LevelConfig__CascadeDepthAssignment_8_1"
    // InternalMazeDsl.g:10051:1: rule__LevelConfig__CascadeDepthAssignment_8_1 : ( ruleINT_OBJECT ) ;
    public final void rule__LevelConfig__CascadeDepthAssignment_8_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10055:1: ( ( ruleINT_OBJECT ) )
            // InternalMazeDsl.g:10056:2: ( ruleINT_OBJECT )
            {
            // InternalMazeDsl.g:10056:2: ( ruleINT_OBJECT )
            // InternalMazeDsl.g:10057:3: ruleINT_OBJECT
            {
             before(grammarAccess.getLevelConfigAccess().getCascadeDepthINT_OBJECTParserRuleCall_8_1_0()); 
            pushFollow(FOLLOW_2);
            ruleINT_OBJECT();

            state._fsp--;

             after(grammarAccess.getLevelConfigAccess().getCascadeDepthINT_OBJECTParserRuleCall_8_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__CascadeDepthAssignment_8_1"


    // $ANTLR start "rule__LevelConfig__ZonesAssignment_9"
    // InternalMazeDsl.g:10066:1: rule__LevelConfig__ZonesAssignment_9 : ( ruleLevelZone ) ;
    public final void rule__LevelConfig__ZonesAssignment_9() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10070:1: ( ( ruleLevelZone ) )
            // InternalMazeDsl.g:10071:2: ( ruleLevelZone )
            {
            // InternalMazeDsl.g:10071:2: ( ruleLevelZone )
            // InternalMazeDsl.g:10072:3: ruleLevelZone
            {
             before(grammarAccess.getLevelConfigAccess().getZonesLevelZoneParserRuleCall_9_0()); 
            pushFollow(FOLLOW_2);
            ruleLevelZone();

            state._fsp--;

             after(grammarAccess.getLevelConfigAccess().getZonesLevelZoneParserRuleCall_9_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__ZonesAssignment_9"


    // $ANTLR start "rule__LevelConfig__SergeantAssignment_10"
    // InternalMazeDsl.g:10081:1: rule__LevelConfig__SergeantAssignment_10 : ( ruleSergeantConfig ) ;
    public final void rule__LevelConfig__SergeantAssignment_10() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10085:1: ( ( ruleSergeantConfig ) )
            // InternalMazeDsl.g:10086:2: ( ruleSergeantConfig )
            {
            // InternalMazeDsl.g:10086:2: ( ruleSergeantConfig )
            // InternalMazeDsl.g:10087:3: ruleSergeantConfig
            {
             before(grammarAccess.getLevelConfigAccess().getSergeantSergeantConfigParserRuleCall_10_0()); 
            pushFollow(FOLLOW_2);
            ruleSergeantConfig();

            state._fsp--;

             after(grammarAccess.getLevelConfigAccess().getSergeantSergeantConfigParserRuleCall_10_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__SergeantAssignment_10"


    // $ANTLR start "rule__LevelConfig__SergeantPostsAssignment_11"
    // InternalMazeDsl.g:10096:1: rule__LevelConfig__SergeantPostsAssignment_11 : ( ruleTierCountConfig ) ;
    public final void rule__LevelConfig__SergeantPostsAssignment_11() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10100:1: ( ( ruleTierCountConfig ) )
            // InternalMazeDsl.g:10101:2: ( ruleTierCountConfig )
            {
            // InternalMazeDsl.g:10101:2: ( ruleTierCountConfig )
            // InternalMazeDsl.g:10102:3: ruleTierCountConfig
            {
             before(grammarAccess.getLevelConfigAccess().getSergeantPostsTierCountConfigParserRuleCall_11_0()); 
            pushFollow(FOLLOW_2);
            ruleTierCountConfig();

            state._fsp--;

             after(grammarAccess.getLevelConfigAccess().getSergeantPostsTierCountConfigParserRuleCall_11_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__SergeantPostsAssignment_11"


    // $ANTLR start "rule__LevelConfig__MinesAssignment_12"
    // InternalMazeDsl.g:10111:1: rule__LevelConfig__MinesAssignment_12 : ( ruleMineRingConfig ) ;
    public final void rule__LevelConfig__MinesAssignment_12() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10115:1: ( ( ruleMineRingConfig ) )
            // InternalMazeDsl.g:10116:2: ( ruleMineRingConfig )
            {
            // InternalMazeDsl.g:10116:2: ( ruleMineRingConfig )
            // InternalMazeDsl.g:10117:3: ruleMineRingConfig
            {
             before(grammarAccess.getLevelConfigAccess().getMinesMineRingConfigParserRuleCall_12_0()); 
            pushFollow(FOLLOW_2);
            ruleMineRingConfig();

            state._fsp--;

             after(grammarAccess.getLevelConfigAccess().getMinesMineRingConfigParserRuleCall_12_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__MinesAssignment_12"


    // $ANTLR start "rule__LevelConfig__PortalGhostAssignment_13"
    // InternalMazeDsl.g:10126:1: rule__LevelConfig__PortalGhostAssignment_13 : ( rulePortalGhostConfig ) ;
    public final void rule__LevelConfig__PortalGhostAssignment_13() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10130:1: ( ( rulePortalGhostConfig ) )
            // InternalMazeDsl.g:10131:2: ( rulePortalGhostConfig )
            {
            // InternalMazeDsl.g:10131:2: ( rulePortalGhostConfig )
            // InternalMazeDsl.g:10132:3: rulePortalGhostConfig
            {
             before(grammarAccess.getLevelConfigAccess().getPortalGhostPortalGhostConfigParserRuleCall_13_0()); 
            pushFollow(FOLLOW_2);
            rulePortalGhostConfig();

            state._fsp--;

             after(grammarAccess.getLevelConfigAccess().getPortalGhostPortalGhostConfigParserRuleCall_13_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__PortalGhostAssignment_13"


    // $ANTLR start "rule__LevelConfig__GhostCountsAssignment_14"
    // InternalMazeDsl.g:10141:1: rule__LevelConfig__GhostCountsAssignment_14 : ( ruleGhostCountConfig ) ;
    public final void rule__LevelConfig__GhostCountsAssignment_14() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10145:1: ( ( ruleGhostCountConfig ) )
            // InternalMazeDsl.g:10146:2: ( ruleGhostCountConfig )
            {
            // InternalMazeDsl.g:10146:2: ( ruleGhostCountConfig )
            // InternalMazeDsl.g:10147:3: ruleGhostCountConfig
            {
             before(grammarAccess.getLevelConfigAccess().getGhostCountsGhostCountConfigParserRuleCall_14_0()); 
            pushFollow(FOLLOW_2);
            ruleGhostCountConfig();

            state._fsp--;

             after(grammarAccess.getLevelConfigAccess().getGhostCountsGhostCountConfigParserRuleCall_14_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__GhostCountsAssignment_14"


    // $ANTLR start "rule__LevelConfig__TeleportAssignment_15"
    // InternalMazeDsl.g:10156:1: rule__LevelConfig__TeleportAssignment_15 : ( ruleTeleportRulesConfig ) ;
    public final void rule__LevelConfig__TeleportAssignment_15() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10160:1: ( ( ruleTeleportRulesConfig ) )
            // InternalMazeDsl.g:10161:2: ( ruleTeleportRulesConfig )
            {
            // InternalMazeDsl.g:10161:2: ( ruleTeleportRulesConfig )
            // InternalMazeDsl.g:10162:3: ruleTeleportRulesConfig
            {
             before(grammarAccess.getLevelConfigAccess().getTeleportTeleportRulesConfigParserRuleCall_15_0()); 
            pushFollow(FOLLOW_2);
            ruleTeleportRulesConfig();

            state._fsp--;

             after(grammarAccess.getLevelConfigAccess().getTeleportTeleportRulesConfigParserRuleCall_15_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelConfig__TeleportAssignment_15"


    // $ANTLR start "rule__LevelZone__DisplayNameAssignment_1"
    // InternalMazeDsl.g:10171:1: rule__LevelZone__DisplayNameAssignment_1 : ( RULE_STRING ) ;
    public final void rule__LevelZone__DisplayNameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10175:1: ( ( RULE_STRING ) )
            // InternalMazeDsl.g:10176:2: ( RULE_STRING )
            {
            // InternalMazeDsl.g:10176:2: ( RULE_STRING )
            // InternalMazeDsl.g:10177:3: RULE_STRING
            {
             before(grammarAccess.getLevelZoneAccess().getDisplayNameSTRINGTerminalRuleCall_1_0()); 
            match(input,RULE_STRING,FOLLOW_2); 
             after(grammarAccess.getLevelZoneAccess().getDisplayNameSTRINGTerminalRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelZone__DisplayNameAssignment_1"


    // $ANTLR start "rule__LevelZone__BreakableShareAssignment_2_1"
    // InternalMazeDsl.g:10186:1: rule__LevelZone__BreakableShareAssignment_2_1 : ( ruleDOUBLE_OBJECT ) ;
    public final void rule__LevelZone__BreakableShareAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10190:1: ( ( ruleDOUBLE_OBJECT ) )
            // InternalMazeDsl.g:10191:2: ( ruleDOUBLE_OBJECT )
            {
            // InternalMazeDsl.g:10191:2: ( ruleDOUBLE_OBJECT )
            // InternalMazeDsl.g:10192:3: ruleDOUBLE_OBJECT
            {
             before(grammarAccess.getLevelZoneAccess().getBreakableShareDOUBLE_OBJECTParserRuleCall_2_1_0()); 
            pushFollow(FOLLOW_2);
            ruleDOUBLE_OBJECT();

            state._fsp--;

             after(grammarAccess.getLevelZoneAccess().getBreakableShareDOUBLE_OBJECTParserRuleCall_2_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelZone__BreakableShareAssignment_2_1"


    // $ANTLR start "rule__LevelZone__MaterialIdsAssignment_4"
    // InternalMazeDsl.g:10201:1: rule__LevelZone__MaterialIdsAssignment_4 : ( RULE_ID ) ;
    public final void rule__LevelZone__MaterialIdsAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10205:1: ( ( RULE_ID ) )
            // InternalMazeDsl.g:10206:2: ( RULE_ID )
            {
            // InternalMazeDsl.g:10206:2: ( RULE_ID )
            // InternalMazeDsl.g:10207:3: RULE_ID
            {
             before(grammarAccess.getLevelZoneAccess().getMaterialIdsIDTerminalRuleCall_4_0()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getLevelZoneAccess().getMaterialIdsIDTerminalRuleCall_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelZone__MaterialIdsAssignment_4"


    // $ANTLR start "rule__LevelZone__MaterialIdsAssignment_5_1"
    // InternalMazeDsl.g:10216:1: rule__LevelZone__MaterialIdsAssignment_5_1 : ( RULE_ID ) ;
    public final void rule__LevelZone__MaterialIdsAssignment_5_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10220:1: ( ( RULE_ID ) )
            // InternalMazeDsl.g:10221:2: ( RULE_ID )
            {
            // InternalMazeDsl.g:10221:2: ( RULE_ID )
            // InternalMazeDsl.g:10222:3: RULE_ID
            {
             before(grammarAccess.getLevelZoneAccess().getMaterialIdsIDTerminalRuleCall_5_1_0()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getLevelZoneAccess().getMaterialIdsIDTerminalRuleCall_5_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LevelZone__MaterialIdsAssignment_5_1"


    // $ANTLR start "rule__SergeantConfig__DisplayNameAssignment_1"
    // InternalMazeDsl.g:10231:1: rule__SergeantConfig__DisplayNameAssignment_1 : ( RULE_STRING ) ;
    public final void rule__SergeantConfig__DisplayNameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10235:1: ( ( RULE_STRING ) )
            // InternalMazeDsl.g:10236:2: ( RULE_STRING )
            {
            // InternalMazeDsl.g:10236:2: ( RULE_STRING )
            // InternalMazeDsl.g:10237:3: RULE_STRING
            {
             before(grammarAccess.getSergeantConfigAccess().getDisplayNameSTRINGTerminalRuleCall_1_0()); 
            match(input,RULE_STRING,FOLLOW_2); 
             after(grammarAccess.getSergeantConfigAccess().getDisplayNameSTRINGTerminalRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__DisplayNameAssignment_1"


    // $ANTLR start "rule__SergeantConfig__HealthAssignment_4"
    // InternalMazeDsl.g:10246:1: rule__SergeantConfig__HealthAssignment_4 : ( RULE_INT ) ;
    public final void rule__SergeantConfig__HealthAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10250:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:10251:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:10251:2: ( RULE_INT )
            // InternalMazeDsl.g:10252:3: RULE_INT
            {
             before(grammarAccess.getSergeantConfigAccess().getHealthINTTerminalRuleCall_4_0()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getSergeantConfigAccess().getHealthINTTerminalRuleCall_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__HealthAssignment_4"


    // $ANTLR start "rule__SergeantConfig__SightRangeAssignment_6"
    // InternalMazeDsl.g:10261:1: rule__SergeantConfig__SightRangeAssignment_6 : ( ruleDOUBLE ) ;
    public final void rule__SergeantConfig__SightRangeAssignment_6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10265:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:10266:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:10266:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:10267:3: ruleDOUBLE
            {
             before(grammarAccess.getSergeantConfigAccess().getSightRangeDOUBLEParserRuleCall_6_0()); 
            pushFollow(FOLLOW_2);
            ruleDOUBLE();

            state._fsp--;

             after(grammarAccess.getSergeantConfigAccess().getSightRangeDOUBLEParserRuleCall_6_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__SightRangeAssignment_6"


    // $ANTLR start "rule__SergeantConfig__RoundsPerBurstAssignment_8"
    // InternalMazeDsl.g:10276:1: rule__SergeantConfig__RoundsPerBurstAssignment_8 : ( RULE_INT ) ;
    public final void rule__SergeantConfig__RoundsPerBurstAssignment_8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10280:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:10281:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:10281:2: ( RULE_INT )
            // InternalMazeDsl.g:10282:3: RULE_INT
            {
             before(grammarAccess.getSergeantConfigAccess().getRoundsPerBurstINTTerminalRuleCall_8_0()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getSergeantConfigAccess().getRoundsPerBurstINTTerminalRuleCall_8_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__RoundsPerBurstAssignment_8"


    // $ANTLR start "rule__SergeantConfig__RoundDamageAssignment_10"
    // InternalMazeDsl.g:10291:1: rule__SergeantConfig__RoundDamageAssignment_10 : ( RULE_INT ) ;
    public final void rule__SergeantConfig__RoundDamageAssignment_10() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10295:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:10296:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:10296:2: ( RULE_INT )
            // InternalMazeDsl.g:10297:3: RULE_INT
            {
             before(grammarAccess.getSergeantConfigAccess().getRoundDamageINTTerminalRuleCall_10_0()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getSergeantConfigAccess().getRoundDamageINTTerminalRuleCall_10_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__RoundDamageAssignment_10"


    // $ANTLR start "rule__SergeantConfig__RoundIntervalSecondsAssignment_12"
    // InternalMazeDsl.g:10306:1: rule__SergeantConfig__RoundIntervalSecondsAssignment_12 : ( ruleDOUBLE ) ;
    public final void rule__SergeantConfig__RoundIntervalSecondsAssignment_12() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10310:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:10311:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:10311:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:10312:3: ruleDOUBLE
            {
             before(grammarAccess.getSergeantConfigAccess().getRoundIntervalSecondsDOUBLEParserRuleCall_12_0()); 
            pushFollow(FOLLOW_2);
            ruleDOUBLE();

            state._fsp--;

             after(grammarAccess.getSergeantConfigAccess().getRoundIntervalSecondsDOUBLEParserRuleCall_12_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__RoundIntervalSecondsAssignment_12"


    // $ANTLR start "rule__SergeantConfig__BurstCooldownSecondsAssignment_14"
    // InternalMazeDsl.g:10321:1: rule__SergeantConfig__BurstCooldownSecondsAssignment_14 : ( ruleDOUBLE ) ;
    public final void rule__SergeantConfig__BurstCooldownSecondsAssignment_14() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10325:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:10326:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:10326:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:10327:3: ruleDOUBLE
            {
             before(grammarAccess.getSergeantConfigAccess().getBurstCooldownSecondsDOUBLEParserRuleCall_14_0()); 
            pushFollow(FOLLOW_2);
            ruleDOUBLE();

            state._fsp--;

             after(grammarAccess.getSergeantConfigAccess().getBurstCooldownSecondsDOUBLEParserRuleCall_14_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__BurstCooldownSecondsAssignment_14"


    // $ANTLR start "rule__SergeantConfig__SpriteAssignment_16"
    // InternalMazeDsl.g:10336:1: rule__SergeantConfig__SpriteAssignment_16 : ( RULE_STRING ) ;
    public final void rule__SergeantConfig__SpriteAssignment_16() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10340:1: ( ( RULE_STRING ) )
            // InternalMazeDsl.g:10341:2: ( RULE_STRING )
            {
            // InternalMazeDsl.g:10341:2: ( RULE_STRING )
            // InternalMazeDsl.g:10342:3: RULE_STRING
            {
             before(grammarAccess.getSergeantConfigAccess().getSpriteSTRINGTerminalRuleCall_16_0()); 
            match(input,RULE_STRING,FOLLOW_2); 
             after(grammarAccess.getSergeantConfigAccess().getSpriteSTRINGTerminalRuleCall_16_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__SpriteAssignment_16"


    // $ANTLR start "rule__SergeantConfig__FireSoundAssignment_17_1"
    // InternalMazeDsl.g:10351:1: rule__SergeantConfig__FireSoundAssignment_17_1 : ( RULE_STRING ) ;
    public final void rule__SergeantConfig__FireSoundAssignment_17_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10355:1: ( ( RULE_STRING ) )
            // InternalMazeDsl.g:10356:2: ( RULE_STRING )
            {
            // InternalMazeDsl.g:10356:2: ( RULE_STRING )
            // InternalMazeDsl.g:10357:3: RULE_STRING
            {
             before(grammarAccess.getSergeantConfigAccess().getFireSoundSTRINGTerminalRuleCall_17_1_0()); 
            match(input,RULE_STRING,FOLLOW_2); 
             after(grammarAccess.getSergeantConfigAccess().getFireSoundSTRINGTerminalRuleCall_17_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__FireSoundAssignment_17_1"


    // $ANTLR start "rule__SergeantConfig__DropWeaponAssignment_19"
    // InternalMazeDsl.g:10366:1: rule__SergeantConfig__DropWeaponAssignment_19 : ( ruleDropWeaponEnum ) ;
    public final void rule__SergeantConfig__DropWeaponAssignment_19() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10370:1: ( ( ruleDropWeaponEnum ) )
            // InternalMazeDsl.g:10371:2: ( ruleDropWeaponEnum )
            {
            // InternalMazeDsl.g:10371:2: ( ruleDropWeaponEnum )
            // InternalMazeDsl.g:10372:3: ruleDropWeaponEnum
            {
             before(grammarAccess.getSergeantConfigAccess().getDropWeaponDropWeaponEnumEnumRuleCall_19_0()); 
            pushFollow(FOLLOW_2);
            ruleDropWeaponEnum();

            state._fsp--;

             after(grammarAccess.getSergeantConfigAccess().getDropWeaponDropWeaponEnumEnumRuleCall_19_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__DropWeaponAssignment_19"


    // $ANTLR start "rule__SergeantConfig__PostDistanceFromHeartAssignment_21"
    // InternalMazeDsl.g:10381:1: rule__SergeantConfig__PostDistanceFromHeartAssignment_21 : ( ruleDOUBLE ) ;
    public final void rule__SergeantConfig__PostDistanceFromHeartAssignment_21() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10385:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:10386:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:10386:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:10387:3: ruleDOUBLE
            {
             before(grammarAccess.getSergeantConfigAccess().getPostDistanceFromHeartDOUBLEParserRuleCall_21_0()); 
            pushFollow(FOLLOW_2);
            ruleDOUBLE();

            state._fsp--;

             after(grammarAccess.getSergeantConfigAccess().getPostDistanceFromHeartDOUBLEParserRuleCall_21_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SergeantConfig__PostDistanceFromHeartAssignment_21"


    // $ANTLR start "rule__TierCountConfig__DifficultyAssignment_1"
    // InternalMazeDsl.g:10396:1: rule__TierCountConfig__DifficultyAssignment_1 : ( ruleDifficultyLevel ) ;
    public final void rule__TierCountConfig__DifficultyAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10400:1: ( ( ruleDifficultyLevel ) )
            // InternalMazeDsl.g:10401:2: ( ruleDifficultyLevel )
            {
            // InternalMazeDsl.g:10401:2: ( ruleDifficultyLevel )
            // InternalMazeDsl.g:10402:3: ruleDifficultyLevel
            {
             before(grammarAccess.getTierCountConfigAccess().getDifficultyDifficultyLevelEnumRuleCall_1_0()); 
            pushFollow(FOLLOW_2);
            ruleDifficultyLevel();

            state._fsp--;

             after(grammarAccess.getTierCountConfigAccess().getDifficultyDifficultyLevelEnumRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TierCountConfig__DifficultyAssignment_1"


    // $ANTLR start "rule__TierCountConfig__CountAssignment_2"
    // InternalMazeDsl.g:10411:1: rule__TierCountConfig__CountAssignment_2 : ( RULE_INT ) ;
    public final void rule__TierCountConfig__CountAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10415:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:10416:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:10416:2: ( RULE_INT )
            // InternalMazeDsl.g:10417:3: RULE_INT
            {
             before(grammarAccess.getTierCountConfigAccess().getCountINTTerminalRuleCall_2_0()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getTierCountConfigAccess().getCountINTTerminalRuleCall_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TierCountConfig__CountAssignment_2"


    // $ANTLR start "rule__MineRingConfig__CountAssignment_3"
    // InternalMazeDsl.g:10426:1: rule__MineRingConfig__CountAssignment_3 : ( RULE_INT ) ;
    public final void rule__MineRingConfig__CountAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10430:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:10431:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:10431:2: ( RULE_INT )
            // InternalMazeDsl.g:10432:3: RULE_INT
            {
             before(grammarAccess.getMineRingConfigAccess().getCountINTTerminalRuleCall_3_0()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getMineRingConfigAccess().getCountINTTerminalRuleCall_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MineRingConfig__CountAssignment_3"


    // $ANTLR start "rule__MineRingConfig__RingRadiusAssignment_5"
    // InternalMazeDsl.g:10441:1: rule__MineRingConfig__RingRadiusAssignment_5 : ( ruleDOUBLE ) ;
    public final void rule__MineRingConfig__RingRadiusAssignment_5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10445:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:10446:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:10446:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:10447:3: ruleDOUBLE
            {
             before(grammarAccess.getMineRingConfigAccess().getRingRadiusDOUBLEParserRuleCall_5_0()); 
            pushFollow(FOLLOW_2);
            ruleDOUBLE();

            state._fsp--;

             after(grammarAccess.getMineRingConfigAccess().getRingRadiusDOUBLEParserRuleCall_5_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MineRingConfig__RingRadiusAssignment_5"


    // $ANTLR start "rule__MineRingConfig__TriggerRadiusAssignment_7"
    // InternalMazeDsl.g:10456:1: rule__MineRingConfig__TriggerRadiusAssignment_7 : ( ruleDOUBLE ) ;
    public final void rule__MineRingConfig__TriggerRadiusAssignment_7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10460:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:10461:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:10461:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:10462:3: ruleDOUBLE
            {
             before(grammarAccess.getMineRingConfigAccess().getTriggerRadiusDOUBLEParserRuleCall_7_0()); 
            pushFollow(FOLLOW_2);
            ruleDOUBLE();

            state._fsp--;

             after(grammarAccess.getMineRingConfigAccess().getTriggerRadiusDOUBLEParserRuleCall_7_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MineRingConfig__TriggerRadiusAssignment_7"


    // $ANTLR start "rule__MineRingConfig__PulseSecondsAssignment_9"
    // InternalMazeDsl.g:10471:1: rule__MineRingConfig__PulseSecondsAssignment_9 : ( ruleDOUBLE ) ;
    public final void rule__MineRingConfig__PulseSecondsAssignment_9() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10475:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:10476:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:10476:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:10477:3: ruleDOUBLE
            {
             before(grammarAccess.getMineRingConfigAccess().getPulseSecondsDOUBLEParserRuleCall_9_0()); 
            pushFollow(FOLLOW_2);
            ruleDOUBLE();

            state._fsp--;

             after(grammarAccess.getMineRingConfigAccess().getPulseSecondsDOUBLEParserRuleCall_9_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MineRingConfig__PulseSecondsAssignment_9"


    // $ANTLR start "rule__MineRingConfig__BlastRadiusAssignment_11"
    // InternalMazeDsl.g:10486:1: rule__MineRingConfig__BlastRadiusAssignment_11 : ( ruleDOUBLE ) ;
    public final void rule__MineRingConfig__BlastRadiusAssignment_11() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10490:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:10491:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:10491:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:10492:3: ruleDOUBLE
            {
             before(grammarAccess.getMineRingConfigAccess().getBlastRadiusDOUBLEParserRuleCall_11_0()); 
            pushFollow(FOLLOW_2);
            ruleDOUBLE();

            state._fsp--;

             after(grammarAccess.getMineRingConfigAccess().getBlastRadiusDOUBLEParserRuleCall_11_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MineRingConfig__BlastRadiusAssignment_11"


    // $ANTLR start "rule__MineRingConfig__BlastDamageAssignment_13"
    // InternalMazeDsl.g:10501:1: rule__MineRingConfig__BlastDamageAssignment_13 : ( RULE_INT ) ;
    public final void rule__MineRingConfig__BlastDamageAssignment_13() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10505:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:10506:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:10506:2: ( RULE_INT )
            // InternalMazeDsl.g:10507:3: RULE_INT
            {
             before(grammarAccess.getMineRingConfigAccess().getBlastDamageINTTerminalRuleCall_13_0()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getMineRingConfigAccess().getBlastDamageINTTerminalRuleCall_13_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MineRingConfig__BlastDamageAssignment_13"


    // $ANTLR start "rule__MineRingConfig__PlayerDamageAssignment_15"
    // InternalMazeDsl.g:10516:1: rule__MineRingConfig__PlayerDamageAssignment_15 : ( RULE_INT ) ;
    public final void rule__MineRingConfig__PlayerDamageAssignment_15() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10520:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:10521:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:10521:2: ( RULE_INT )
            // InternalMazeDsl.g:10522:3: RULE_INT
            {
             before(grammarAccess.getMineRingConfigAccess().getPlayerDamageINTTerminalRuleCall_15_0()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getMineRingConfigAccess().getPlayerDamageINTTerminalRuleCall_15_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MineRingConfig__PlayerDamageAssignment_15"


    // $ANTLR start "rule__MineRingConfig__HoleRadiusAssignment_17"
    // InternalMazeDsl.g:10531:1: rule__MineRingConfig__HoleRadiusAssignment_17 : ( ruleDOUBLE ) ;
    public final void rule__MineRingConfig__HoleRadiusAssignment_17() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10535:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:10536:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:10536:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:10537:3: ruleDOUBLE
            {
             before(grammarAccess.getMineRingConfigAccess().getHoleRadiusDOUBLEParserRuleCall_17_0()); 
            pushFollow(FOLLOW_2);
            ruleDOUBLE();

            state._fsp--;

             after(grammarAccess.getMineRingConfigAccess().getHoleRadiusDOUBLEParserRuleCall_17_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MineRingConfig__HoleRadiusAssignment_17"


    // $ANTLR start "rule__PortalGhostConfig__DisplayNameAssignment_1"
    // InternalMazeDsl.g:10546:1: rule__PortalGhostConfig__DisplayNameAssignment_1 : ( RULE_STRING ) ;
    public final void rule__PortalGhostConfig__DisplayNameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10550:1: ( ( RULE_STRING ) )
            // InternalMazeDsl.g:10551:2: ( RULE_STRING )
            {
            // InternalMazeDsl.g:10551:2: ( RULE_STRING )
            // InternalMazeDsl.g:10552:3: RULE_STRING
            {
             before(grammarAccess.getPortalGhostConfigAccess().getDisplayNameSTRINGTerminalRuleCall_1_0()); 
            match(input,RULE_STRING,FOLLOW_2); 
             after(grammarAccess.getPortalGhostConfigAccess().getDisplayNameSTRINGTerminalRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PortalGhostConfig__DisplayNameAssignment_1"


    // $ANTLR start "rule__PortalGhostConfig__HealthAssignment_4"
    // InternalMazeDsl.g:10561:1: rule__PortalGhostConfig__HealthAssignment_4 : ( RULE_INT ) ;
    public final void rule__PortalGhostConfig__HealthAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10565:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:10566:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:10566:2: ( RULE_INT )
            // InternalMazeDsl.g:10567:3: RULE_INT
            {
             before(grammarAccess.getPortalGhostConfigAccess().getHealthINTTerminalRuleCall_4_0()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getPortalGhostConfigAccess().getHealthINTTerminalRuleCall_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PortalGhostConfig__HealthAssignment_4"


    // $ANTLR start "rule__PortalGhostConfig__SpeedAssignment_6"
    // InternalMazeDsl.g:10576:1: rule__PortalGhostConfig__SpeedAssignment_6 : ( ruleDOUBLE ) ;
    public final void rule__PortalGhostConfig__SpeedAssignment_6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10580:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:10581:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:10581:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:10582:3: ruleDOUBLE
            {
             before(grammarAccess.getPortalGhostConfigAccess().getSpeedDOUBLEParserRuleCall_6_0()); 
            pushFollow(FOLLOW_2);
            ruleDOUBLE();

            state._fsp--;

             after(grammarAccess.getPortalGhostConfigAccess().getSpeedDOUBLEParserRuleCall_6_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PortalGhostConfig__SpeedAssignment_6"


    // $ANTLR start "rule__PortalGhostConfig__SpriteAssignment_8"
    // InternalMazeDsl.g:10591:1: rule__PortalGhostConfig__SpriteAssignment_8 : ( RULE_STRING ) ;
    public final void rule__PortalGhostConfig__SpriteAssignment_8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10595:1: ( ( RULE_STRING ) )
            // InternalMazeDsl.g:10596:2: ( RULE_STRING )
            {
            // InternalMazeDsl.g:10596:2: ( RULE_STRING )
            // InternalMazeDsl.g:10597:3: RULE_STRING
            {
             before(grammarAccess.getPortalGhostConfigAccess().getSpriteSTRINGTerminalRuleCall_8_0()); 
            match(input,RULE_STRING,FOLLOW_2); 
             after(grammarAccess.getPortalGhostConfigAccess().getSpriteSTRINGTerminalRuleCall_8_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PortalGhostConfig__SpriteAssignment_8"


    // $ANTLR start "rule__GhostCountConfig__DifficultyAssignment_1"
    // InternalMazeDsl.g:10606:1: rule__GhostCountConfig__DifficultyAssignment_1 : ( ruleDifficultyLevel ) ;
    public final void rule__GhostCountConfig__DifficultyAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10610:1: ( ( ruleDifficultyLevel ) )
            // InternalMazeDsl.g:10611:2: ( ruleDifficultyLevel )
            {
            // InternalMazeDsl.g:10611:2: ( ruleDifficultyLevel )
            // InternalMazeDsl.g:10612:3: ruleDifficultyLevel
            {
             before(grammarAccess.getGhostCountConfigAccess().getDifficultyDifficultyLevelEnumRuleCall_1_0()); 
            pushFollow(FOLLOW_2);
            ruleDifficultyLevel();

            state._fsp--;

             after(grammarAccess.getGhostCountConfigAccess().getDifficultyDifficultyLevelEnumRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostCountConfig__DifficultyAssignment_1"


    // $ANTLR start "rule__GhostCountConfig__CountAssignment_2"
    // InternalMazeDsl.g:10621:1: rule__GhostCountConfig__CountAssignment_2 : ( RULE_INT ) ;
    public final void rule__GhostCountConfig__CountAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10625:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:10626:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:10626:2: ( RULE_INT )
            // InternalMazeDsl.g:10627:3: RULE_INT
            {
             before(grammarAccess.getGhostCountConfigAccess().getCountINTTerminalRuleCall_2_0()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getGhostCountConfigAccess().getCountINTTerminalRuleCall_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostCountConfig__CountAssignment_2"


    // $ANTLR start "rule__TeleportRulesConfig__CooldownSecondsAssignment_3"
    // InternalMazeDsl.g:10636:1: rule__TeleportRulesConfig__CooldownSecondsAssignment_3 : ( ruleDOUBLE ) ;
    public final void rule__TeleportRulesConfig__CooldownSecondsAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10640:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:10641:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:10641:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:10642:3: ruleDOUBLE
            {
             before(grammarAccess.getTeleportRulesConfigAccess().getCooldownSecondsDOUBLEParserRuleCall_3_0()); 
            pushFollow(FOLLOW_2);
            ruleDOUBLE();

            state._fsp--;

             after(grammarAccess.getTeleportRulesConfigAccess().getCooldownSecondsDOUBLEParserRuleCall_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TeleportRulesConfig__CooldownSecondsAssignment_3"


    // $ANTLR start "rule__TeleportRulesConfig__BannerTextAssignment_5"
    // InternalMazeDsl.g:10651:1: rule__TeleportRulesConfig__BannerTextAssignment_5 : ( RULE_STRING ) ;
    public final void rule__TeleportRulesConfig__BannerTextAssignment_5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10655:1: ( ( RULE_STRING ) )
            // InternalMazeDsl.g:10656:2: ( RULE_STRING )
            {
            // InternalMazeDsl.g:10656:2: ( RULE_STRING )
            // InternalMazeDsl.g:10657:3: RULE_STRING
            {
             before(grammarAccess.getTeleportRulesConfigAccess().getBannerTextSTRINGTerminalRuleCall_5_0()); 
            match(input,RULE_STRING,FOLLOW_2); 
             after(grammarAccess.getTeleportRulesConfigAccess().getBannerTextSTRINGTerminalRuleCall_5_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TeleportRulesConfig__BannerTextAssignment_5"


    // $ANTLR start "rule__TeleportRulesConfig__BannerSecondsAssignment_7"
    // InternalMazeDsl.g:10666:1: rule__TeleportRulesConfig__BannerSecondsAssignment_7 : ( ruleDOUBLE ) ;
    public final void rule__TeleportRulesConfig__BannerSecondsAssignment_7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10670:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:10671:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:10671:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:10672:3: ruleDOUBLE
            {
             before(grammarAccess.getTeleportRulesConfigAccess().getBannerSecondsDOUBLEParserRuleCall_7_0()); 
            pushFollow(FOLLOW_2);
            ruleDOUBLE();

            state._fsp--;

             after(grammarAccess.getTeleportRulesConfigAccess().getBannerSecondsDOUBLEParserRuleCall_7_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TeleportRulesConfig__BannerSecondsAssignment_7"


    // $ANTLR start "rule__TeleportRulesConfig__ZapSoundAssignment_8_1"
    // InternalMazeDsl.g:10681:1: rule__TeleportRulesConfig__ZapSoundAssignment_8_1 : ( RULE_STRING ) ;
    public final void rule__TeleportRulesConfig__ZapSoundAssignment_8_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10685:1: ( ( RULE_STRING ) )
            // InternalMazeDsl.g:10686:2: ( RULE_STRING )
            {
            // InternalMazeDsl.g:10686:2: ( RULE_STRING )
            // InternalMazeDsl.g:10687:3: RULE_STRING
            {
             before(grammarAccess.getTeleportRulesConfigAccess().getZapSoundSTRINGTerminalRuleCall_8_1_0()); 
            match(input,RULE_STRING,FOLLOW_2); 
             after(grammarAccess.getTeleportRulesConfigAccess().getZapSoundSTRINGTerminalRuleCall_8_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TeleportRulesConfig__ZapSoundAssignment_8_1"


    // $ANTLR start "rule__TeleportRulesConfig__VoiceSoundAssignment_9_1"
    // InternalMazeDsl.g:10696:1: rule__TeleportRulesConfig__VoiceSoundAssignment_9_1 : ( RULE_STRING ) ;
    public final void rule__TeleportRulesConfig__VoiceSoundAssignment_9_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10700:1: ( ( RULE_STRING ) )
            // InternalMazeDsl.g:10701:2: ( RULE_STRING )
            {
            // InternalMazeDsl.g:10701:2: ( RULE_STRING )
            // InternalMazeDsl.g:10702:3: RULE_STRING
            {
             before(grammarAccess.getTeleportRulesConfigAccess().getVoiceSoundSTRINGTerminalRuleCall_9_1_0()); 
            match(input,RULE_STRING,FOLLOW_2); 
             after(grammarAccess.getTeleportRulesConfigAccess().getVoiceSoundSTRINGTerminalRuleCall_9_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TeleportRulesConfig__VoiceSoundAssignment_9_1"

    // Delegated rules


 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000081E00800000L,0x0000000000004000L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000400000002L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000080000000002L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000000800002L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000000002L,0x0000000000004000L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000001000000002L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x000000000001C000L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x000003E200000000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000020000000002L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000000000010L,0x0080000000000000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x00000000000E0000L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x091FE00200800000L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000000000003000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000000000F00000L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x00E0000200000000L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0620000200000000L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0910000000000000L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0xF020000200000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0000000007000000L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000052L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000028L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000020L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0x0000000000000000L,0x0000000000018000L});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x0000000000000002L,0x0000000000018000L});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x0000000078000000L});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x0000000200000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_50 = new BitSet(new long[]{0x0000000200000000L,0x0003806009F80000L});
    public static final BitSet FOLLOW_51 = new BitSet(new long[]{0x0000000000000002L,0x0000000001000000L});
    public static final BitSet FOLLOW_52 = new BitSet(new long[]{0x0000000000000002L,0x0000002000000000L});
    public static final BitSet FOLLOW_53 = new BitSet(new long[]{0x0000000000000002L,0x0001000000000000L});
    public static final BitSet FOLLOW_54 = new BitSet(new long[]{0x0000000000000000L,0x0000000006000000L});
    public static final BitSet FOLLOW_55 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_56 = new BitSet(new long[]{0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_57 = new BitSet(new long[]{0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_58 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_59 = new BitSet(new long[]{0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_60 = new BitSet(new long[]{0x0000000000000000L,0x0000000100000000L});
    public static final BitSet FOLLOW_61 = new BitSet(new long[]{0x0000000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_62 = new BitSet(new long[]{0x0000000000000000L,0x0000001400000000L});
    public static final BitSet FOLLOW_63 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_64 = new BitSet(new long[]{0x0000000000000000L,0x0000000800000000L});
    public static final BitSet FOLLOW_65 = new BitSet(new long[]{0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_66 = new BitSet(new long[]{0x0000000000000000L,0x0000010000000000L});
    public static final BitSet FOLLOW_67 = new BitSet(new long[]{0x0000000000000000L,0x0000020000000000L});
    public static final BitSet FOLLOW_68 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L});
    public static final BitSet FOLLOW_69 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_70 = new BitSet(new long[]{0x0000000000000000L,0x0000100000000000L});
    public static final BitSet FOLLOW_71 = new BitSet(new long[]{0x0000000000000000L,0x0000200000000000L});
    public static final BitSet FOLLOW_72 = new BitSet(new long[]{0x0000000000000000L,0x0000400000000000L});
    public static final BitSet FOLLOW_73 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_74 = new BitSet(new long[]{0x0000000000000000L,0x0004000000000000L});
    public static final BitSet FOLLOW_75 = new BitSet(new long[]{0x0000000000000000L,0x0008000000000000L});
    public static final BitSet FOLLOW_76 = new BitSet(new long[]{0x0000000000000000L,0x0010000000000000L});
    public static final BitSet FOLLOW_77 = new BitSet(new long[]{0x0000000200000000L,0x0060000000000000L});
    public static final BitSet FOLLOW_78 = new BitSet(new long[]{0x0000000000000000L,0x0100000000000000L});

}