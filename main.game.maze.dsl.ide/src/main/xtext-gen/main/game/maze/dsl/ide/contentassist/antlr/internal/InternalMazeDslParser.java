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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_INT", "RULE_STRING", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'gatling'", "'true'", "'false'", "'basedOn'", "'threatCapacity'", "'wallMaterials'", "'inheritWeapons'", "'cascadeDepth'", "'ring'", "'share'", "'materials'", "'sergeant'", "'sergeants'", "'sightRange'", "'rounds'", "'roundDamage'", "'roundInterval'", "'burstCooldown'", "'sprite'", "'fireSound'", "'drops'", "'postDistance'", "'mines'", "'count'", "'ringRadius'", "'triggerRadius'", "'pulse'", "'blastRadius'", "'blastDamage'", "'playerDamage'", "'holeRadius'", "'portalGhost'", "'portalGhosts'", "'teleport'", "'cooldown'", "'banner'", "'bannerSeconds'", "'zapSound'", "'voiceSound'", "'easy'", "'normal'", "'hard'", "'zombie'", "'ghost'", "'pumpkinbomber'", "'passive'", "'wander'", "'aggressive'", "'patrol'", "'straight'", "'lob'", "'beam'", "'food'", "'bomb'", "'trap'", "'weapon'", "'game'", "'{'", "'}'", "'import'", "'difficulty'", "'level'", "'instantDeath'", "'speedMultiplier'", "'damageMultiplier'", "'maxThreat'", "'limit'", "'max'", "'opponent'", "'type'", "'displayName'", "'health'", "'speed'", "'threatLevel'", "'enabled'", "'behavior'", "'loot'", "'zombie-stats'", "'attackDamage'", "'infectionLevel'", "'resurrectionTime'", "'ghost-stats'", "'visibilityLevel'", "'nonTangibilityEnergy'", "'ranged-stats'", "'attackRange'", "'attackCooldown'", "'projectileSpeed'", "'projectileType'", "'splashRadius'", "'path'", "'['", "']'", "'visionRange'", "','", "'zone'", "'topLeft'", "'('", "')'", "'width'", "'height'", "':'", "'ms'", "'loot-table'", "'capacity'", "'item'", "'value'", "'weight'", "'-'", "'.'"
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
    public static final int RULE_INT=5;
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


    // $ANTLR start "entryRuleValidID"
    // InternalMazeDsl.g:753:1: entryRuleValidID : ruleValidID EOF ;
    public final void entryRuleValidID() throws RecognitionException {
        try {
            // InternalMazeDsl.g:754:1: ( ruleValidID EOF )
            // InternalMazeDsl.g:755:1: ruleValidID EOF
            {
             before(grammarAccess.getValidIDRule()); 
            pushFollow(FOLLOW_1);
            ruleValidID();

            state._fsp--;

             after(grammarAccess.getValidIDRule()); 
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
    // $ANTLR end "entryRuleValidID"


    // $ANTLR start "ruleValidID"
    // InternalMazeDsl.g:762:1: ruleValidID : ( ( rule__ValidID__Alternatives ) ) ;
    public final void ruleValidID() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:766:2: ( ( ( rule__ValidID__Alternatives ) ) )
            // InternalMazeDsl.g:767:2: ( ( rule__ValidID__Alternatives ) )
            {
            // InternalMazeDsl.g:767:2: ( ( rule__ValidID__Alternatives ) )
            // InternalMazeDsl.g:768:3: ( rule__ValidID__Alternatives )
            {
             before(grammarAccess.getValidIDAccess().getAlternatives()); 
            // InternalMazeDsl.g:769:3: ( rule__ValidID__Alternatives )
            // InternalMazeDsl.g:769:4: rule__ValidID__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__ValidID__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getValidIDAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleValidID"


    // $ANTLR start "ruleDifficultyLevel"
    // InternalMazeDsl.g:778:1: ruleDifficultyLevel : ( ( rule__DifficultyLevel__Alternatives ) ) ;
    public final void ruleDifficultyLevel() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:782:1: ( ( ( rule__DifficultyLevel__Alternatives ) ) )
            // InternalMazeDsl.g:783:2: ( ( rule__DifficultyLevel__Alternatives ) )
            {
            // InternalMazeDsl.g:783:2: ( ( rule__DifficultyLevel__Alternatives ) )
            // InternalMazeDsl.g:784:3: ( rule__DifficultyLevel__Alternatives )
            {
             before(grammarAccess.getDifficultyLevelAccess().getAlternatives()); 
            // InternalMazeDsl.g:785:3: ( rule__DifficultyLevel__Alternatives )
            // InternalMazeDsl.g:785:4: rule__DifficultyLevel__Alternatives
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
    // InternalMazeDsl.g:794:1: ruleEnemyType : ( ( rule__EnemyType__Alternatives ) ) ;
    public final void ruleEnemyType() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:798:1: ( ( ( rule__EnemyType__Alternatives ) ) )
            // InternalMazeDsl.g:799:2: ( ( rule__EnemyType__Alternatives ) )
            {
            // InternalMazeDsl.g:799:2: ( ( rule__EnemyType__Alternatives ) )
            // InternalMazeDsl.g:800:3: ( rule__EnemyType__Alternatives )
            {
             before(grammarAccess.getEnemyTypeAccess().getAlternatives()); 
            // InternalMazeDsl.g:801:3: ( rule__EnemyType__Alternatives )
            // InternalMazeDsl.g:801:4: rule__EnemyType__Alternatives
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
    // InternalMazeDsl.g:810:1: ruleCharacterTypeEnum : ( ( rule__CharacterTypeEnum__Alternatives ) ) ;
    public final void ruleCharacterTypeEnum() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:814:1: ( ( ( rule__CharacterTypeEnum__Alternatives ) ) )
            // InternalMazeDsl.g:815:2: ( ( rule__CharacterTypeEnum__Alternatives ) )
            {
            // InternalMazeDsl.g:815:2: ( ( rule__CharacterTypeEnum__Alternatives ) )
            // InternalMazeDsl.g:816:3: ( rule__CharacterTypeEnum__Alternatives )
            {
             before(grammarAccess.getCharacterTypeEnumAccess().getAlternatives()); 
            // InternalMazeDsl.g:817:3: ( rule__CharacterTypeEnum__Alternatives )
            // InternalMazeDsl.g:817:4: rule__CharacterTypeEnum__Alternatives
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
    // InternalMazeDsl.g:826:1: ruleBehaviorTypeEnum : ( ( rule__BehaviorTypeEnum__Alternatives ) ) ;
    public final void ruleBehaviorTypeEnum() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:830:1: ( ( ( rule__BehaviorTypeEnum__Alternatives ) ) )
            // InternalMazeDsl.g:831:2: ( ( rule__BehaviorTypeEnum__Alternatives ) )
            {
            // InternalMazeDsl.g:831:2: ( ( rule__BehaviorTypeEnum__Alternatives ) )
            // InternalMazeDsl.g:832:3: ( rule__BehaviorTypeEnum__Alternatives )
            {
             before(grammarAccess.getBehaviorTypeEnumAccess().getAlternatives()); 
            // InternalMazeDsl.g:833:3: ( rule__BehaviorTypeEnum__Alternatives )
            // InternalMazeDsl.g:833:4: rule__BehaviorTypeEnum__Alternatives
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
    // InternalMazeDsl.g:842:1: ruleProjectileTypeEnum : ( ( rule__ProjectileTypeEnum__Alternatives ) ) ;
    public final void ruleProjectileTypeEnum() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:846:1: ( ( ( rule__ProjectileTypeEnum__Alternatives ) ) )
            // InternalMazeDsl.g:847:2: ( ( rule__ProjectileTypeEnum__Alternatives ) )
            {
            // InternalMazeDsl.g:847:2: ( ( rule__ProjectileTypeEnum__Alternatives ) )
            // InternalMazeDsl.g:848:3: ( rule__ProjectileTypeEnum__Alternatives )
            {
             before(grammarAccess.getProjectileTypeEnumAccess().getAlternatives()); 
            // InternalMazeDsl.g:849:3: ( rule__ProjectileTypeEnum__Alternatives )
            // InternalMazeDsl.g:849:4: rule__ProjectileTypeEnum__Alternatives
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
    // InternalMazeDsl.g:858:1: ruleLootItemTypeEnum : ( ( rule__LootItemTypeEnum__Alternatives ) ) ;
    public final void ruleLootItemTypeEnum() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:862:1: ( ( ( rule__LootItemTypeEnum__Alternatives ) ) )
            // InternalMazeDsl.g:863:2: ( ( rule__LootItemTypeEnum__Alternatives ) )
            {
            // InternalMazeDsl.g:863:2: ( ( rule__LootItemTypeEnum__Alternatives ) )
            // InternalMazeDsl.g:864:3: ( rule__LootItemTypeEnum__Alternatives )
            {
             before(grammarAccess.getLootItemTypeEnumAccess().getAlternatives()); 
            // InternalMazeDsl.g:865:3: ( rule__LootItemTypeEnum__Alternatives )
            // InternalMazeDsl.g:865:4: rule__LootItemTypeEnum__Alternatives
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
    // InternalMazeDsl.g:874:1: ruleDropWeaponEnum : ( ( 'gatling' ) ) ;
    public final void ruleDropWeaponEnum() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:878:1: ( ( ( 'gatling' ) ) )
            // InternalMazeDsl.g:879:2: ( ( 'gatling' ) )
            {
            // InternalMazeDsl.g:879:2: ( ( 'gatling' ) )
            // InternalMazeDsl.g:880:3: ( 'gatling' )
            {
             before(grammarAccess.getDropWeaponEnumAccess().getGATLING_GUNEnumLiteralDeclaration()); 
            // InternalMazeDsl.g:881:3: ( 'gatling' )
            // InternalMazeDsl.g:881:4: 'gatling'
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
    // InternalMazeDsl.g:889:1: rule__CharacterSpecifics__Alternatives : ( ( ruleZombieSpecifics ) | ( ruleGhostSpecifics ) | ( ruleRangedSpecifics ) );
    public final void rule__CharacterSpecifics__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:893:1: ( ( ruleZombieSpecifics ) | ( ruleGhostSpecifics ) | ( ruleRangedSpecifics ) )
            int alt1=3;
            switch ( input.LA(1) ) {
            case 88:
                {
                alt1=1;
                }
                break;
            case 92:
                {
                alt1=2;
                }
                break;
            case 95:
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
                    // InternalMazeDsl.g:894:2: ( ruleZombieSpecifics )
                    {
                    // InternalMazeDsl.g:894:2: ( ruleZombieSpecifics )
                    // InternalMazeDsl.g:895:3: ruleZombieSpecifics
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
                    // InternalMazeDsl.g:900:2: ( ruleGhostSpecifics )
                    {
                    // InternalMazeDsl.g:900:2: ( ruleGhostSpecifics )
                    // InternalMazeDsl.g:901:3: ruleGhostSpecifics
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
                    // InternalMazeDsl.g:906:2: ( ruleRangedSpecifics )
                    {
                    // InternalMazeDsl.g:906:2: ( ruleRangedSpecifics )
                    // InternalMazeDsl.g:907:3: ruleRangedSpecifics
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
    // InternalMazeDsl.g:916:1: rule__BOOLEAN__Alternatives : ( ( 'true' ) | ( 'false' ) );
    public final void rule__BOOLEAN__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:920:1: ( ( 'true' ) | ( 'false' ) )
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
                    // InternalMazeDsl.g:921:2: ( 'true' )
                    {
                    // InternalMazeDsl.g:921:2: ( 'true' )
                    // InternalMazeDsl.g:922:3: 'true'
                    {
                     before(grammarAccess.getBOOLEANAccess().getTrueKeyword_0()); 
                    match(input,12,FOLLOW_2); 
                     after(grammarAccess.getBOOLEANAccess().getTrueKeyword_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:927:2: ( 'false' )
                    {
                    // InternalMazeDsl.g:927:2: ( 'false' )
                    // InternalMazeDsl.g:928:3: 'false'
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
    // InternalMazeDsl.g:937:1: rule__BOOLEAN_OBJECT__Alternatives : ( ( 'true' ) | ( 'false' ) );
    public final void rule__BOOLEAN_OBJECT__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:941:1: ( ( 'true' ) | ( 'false' ) )
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
                    // InternalMazeDsl.g:942:2: ( 'true' )
                    {
                    // InternalMazeDsl.g:942:2: ( 'true' )
                    // InternalMazeDsl.g:943:3: 'true'
                    {
                     before(grammarAccess.getBOOLEAN_OBJECTAccess().getTrueKeyword_0()); 
                    match(input,12,FOLLOW_2); 
                     after(grammarAccess.getBOOLEAN_OBJECTAccess().getTrueKeyword_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:948:2: ( 'false' )
                    {
                    // InternalMazeDsl.g:948:2: ( 'false' )
                    // InternalMazeDsl.g:949:3: 'false'
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


    // $ANTLR start "rule__ValidID__Alternatives"
    // InternalMazeDsl.g:958:1: rule__ValidID__Alternatives : ( ( RULE_ID ) | ( 'basedOn' ) | ( 'threatCapacity' ) | ( 'wallMaterials' ) | ( 'inheritWeapons' ) | ( 'cascadeDepth' ) | ( 'ring' ) | ( 'share' ) | ( 'materials' ) | ( 'sergeant' ) | ( 'sergeants' ) | ( 'sightRange' ) | ( 'rounds' ) | ( 'roundDamage' ) | ( 'roundInterval' ) | ( 'burstCooldown' ) | ( 'sprite' ) | ( 'fireSound' ) | ( 'drops' ) | ( 'gatling' ) | ( 'postDistance' ) | ( 'mines' ) | ( 'count' ) | ( 'ringRadius' ) | ( 'triggerRadius' ) | ( 'pulse' ) | ( 'blastRadius' ) | ( 'blastDamage' ) | ( 'playerDamage' ) | ( 'holeRadius' ) | ( 'portalGhost' ) | ( 'portalGhosts' ) | ( 'teleport' ) | ( 'cooldown' ) | ( 'banner' ) | ( 'bannerSeconds' ) | ( 'zapSound' ) | ( 'voiceSound' ) );
    public final void rule__ValidID__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:962:1: ( ( RULE_ID ) | ( 'basedOn' ) | ( 'threatCapacity' ) | ( 'wallMaterials' ) | ( 'inheritWeapons' ) | ( 'cascadeDepth' ) | ( 'ring' ) | ( 'share' ) | ( 'materials' ) | ( 'sergeant' ) | ( 'sergeants' ) | ( 'sightRange' ) | ( 'rounds' ) | ( 'roundDamage' ) | ( 'roundInterval' ) | ( 'burstCooldown' ) | ( 'sprite' ) | ( 'fireSound' ) | ( 'drops' ) | ( 'gatling' ) | ( 'postDistance' ) | ( 'mines' ) | ( 'count' ) | ( 'ringRadius' ) | ( 'triggerRadius' ) | ( 'pulse' ) | ( 'blastRadius' ) | ( 'blastDamage' ) | ( 'playerDamage' ) | ( 'holeRadius' ) | ( 'portalGhost' ) | ( 'portalGhosts' ) | ( 'teleport' ) | ( 'cooldown' ) | ( 'banner' ) | ( 'bannerSeconds' ) | ( 'zapSound' ) | ( 'voiceSound' ) )
            int alt4=38;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                alt4=1;
                }
                break;
            case 14:
                {
                alt4=2;
                }
                break;
            case 15:
                {
                alt4=3;
                }
                break;
            case 16:
                {
                alt4=4;
                }
                break;
            case 17:
                {
                alt4=5;
                }
                break;
            case 18:
                {
                alt4=6;
                }
                break;
            case 19:
                {
                alt4=7;
                }
                break;
            case 20:
                {
                alt4=8;
                }
                break;
            case 21:
                {
                alt4=9;
                }
                break;
            case 22:
                {
                alt4=10;
                }
                break;
            case 23:
                {
                alt4=11;
                }
                break;
            case 24:
                {
                alt4=12;
                }
                break;
            case 25:
                {
                alt4=13;
                }
                break;
            case 26:
                {
                alt4=14;
                }
                break;
            case 27:
                {
                alt4=15;
                }
                break;
            case 28:
                {
                alt4=16;
                }
                break;
            case 29:
                {
                alt4=17;
                }
                break;
            case 30:
                {
                alt4=18;
                }
                break;
            case 31:
                {
                alt4=19;
                }
                break;
            case 11:
                {
                alt4=20;
                }
                break;
            case 32:
                {
                alt4=21;
                }
                break;
            case 33:
                {
                alt4=22;
                }
                break;
            case 34:
                {
                alt4=23;
                }
                break;
            case 35:
                {
                alt4=24;
                }
                break;
            case 36:
                {
                alt4=25;
                }
                break;
            case 37:
                {
                alt4=26;
                }
                break;
            case 38:
                {
                alt4=27;
                }
                break;
            case 39:
                {
                alt4=28;
                }
                break;
            case 40:
                {
                alt4=29;
                }
                break;
            case 41:
                {
                alt4=30;
                }
                break;
            case 42:
                {
                alt4=31;
                }
                break;
            case 43:
                {
                alt4=32;
                }
                break;
            case 44:
                {
                alt4=33;
                }
                break;
            case 45:
                {
                alt4=34;
                }
                break;
            case 46:
                {
                alt4=35;
                }
                break;
            case 47:
                {
                alt4=36;
                }
                break;
            case 48:
                {
                alt4=37;
                }
                break;
            case 49:
                {
                alt4=38;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }

            switch (alt4) {
                case 1 :
                    // InternalMazeDsl.g:963:2: ( RULE_ID )
                    {
                    // InternalMazeDsl.g:963:2: ( RULE_ID )
                    // InternalMazeDsl.g:964:3: RULE_ID
                    {
                     before(grammarAccess.getValidIDAccess().getIDTerminalRuleCall_0()); 
                    match(input,RULE_ID,FOLLOW_2); 
                     after(grammarAccess.getValidIDAccess().getIDTerminalRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:969:2: ( 'basedOn' )
                    {
                    // InternalMazeDsl.g:969:2: ( 'basedOn' )
                    // InternalMazeDsl.g:970:3: 'basedOn'
                    {
                     before(grammarAccess.getValidIDAccess().getBasedOnKeyword_1()); 
                    match(input,14,FOLLOW_2); 
                     after(grammarAccess.getValidIDAccess().getBasedOnKeyword_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalMazeDsl.g:975:2: ( 'threatCapacity' )
                    {
                    // InternalMazeDsl.g:975:2: ( 'threatCapacity' )
                    // InternalMazeDsl.g:976:3: 'threatCapacity'
                    {
                     before(grammarAccess.getValidIDAccess().getThreatCapacityKeyword_2()); 
                    match(input,15,FOLLOW_2); 
                     after(grammarAccess.getValidIDAccess().getThreatCapacityKeyword_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalMazeDsl.g:981:2: ( 'wallMaterials' )
                    {
                    // InternalMazeDsl.g:981:2: ( 'wallMaterials' )
                    // InternalMazeDsl.g:982:3: 'wallMaterials'
                    {
                     before(grammarAccess.getValidIDAccess().getWallMaterialsKeyword_3()); 
                    match(input,16,FOLLOW_2); 
                     after(grammarAccess.getValidIDAccess().getWallMaterialsKeyword_3()); 

                    }


                    }
                    break;
                case 5 :
                    // InternalMazeDsl.g:987:2: ( 'inheritWeapons' )
                    {
                    // InternalMazeDsl.g:987:2: ( 'inheritWeapons' )
                    // InternalMazeDsl.g:988:3: 'inheritWeapons'
                    {
                     before(grammarAccess.getValidIDAccess().getInheritWeaponsKeyword_4()); 
                    match(input,17,FOLLOW_2); 
                     after(grammarAccess.getValidIDAccess().getInheritWeaponsKeyword_4()); 

                    }


                    }
                    break;
                case 6 :
                    // InternalMazeDsl.g:993:2: ( 'cascadeDepth' )
                    {
                    // InternalMazeDsl.g:993:2: ( 'cascadeDepth' )
                    // InternalMazeDsl.g:994:3: 'cascadeDepth'
                    {
                     before(grammarAccess.getValidIDAccess().getCascadeDepthKeyword_5()); 
                    match(input,18,FOLLOW_2); 
                     after(grammarAccess.getValidIDAccess().getCascadeDepthKeyword_5()); 

                    }


                    }
                    break;
                case 7 :
                    // InternalMazeDsl.g:999:2: ( 'ring' )
                    {
                    // InternalMazeDsl.g:999:2: ( 'ring' )
                    // InternalMazeDsl.g:1000:3: 'ring'
                    {
                     before(grammarAccess.getValidIDAccess().getRingKeyword_6()); 
                    match(input,19,FOLLOW_2); 
                     after(grammarAccess.getValidIDAccess().getRingKeyword_6()); 

                    }


                    }
                    break;
                case 8 :
                    // InternalMazeDsl.g:1005:2: ( 'share' )
                    {
                    // InternalMazeDsl.g:1005:2: ( 'share' )
                    // InternalMazeDsl.g:1006:3: 'share'
                    {
                     before(grammarAccess.getValidIDAccess().getShareKeyword_7()); 
                    match(input,20,FOLLOW_2); 
                     after(grammarAccess.getValidIDAccess().getShareKeyword_7()); 

                    }


                    }
                    break;
                case 9 :
                    // InternalMazeDsl.g:1011:2: ( 'materials' )
                    {
                    // InternalMazeDsl.g:1011:2: ( 'materials' )
                    // InternalMazeDsl.g:1012:3: 'materials'
                    {
                     before(grammarAccess.getValidIDAccess().getMaterialsKeyword_8()); 
                    match(input,21,FOLLOW_2); 
                     after(grammarAccess.getValidIDAccess().getMaterialsKeyword_8()); 

                    }


                    }
                    break;
                case 10 :
                    // InternalMazeDsl.g:1017:2: ( 'sergeant' )
                    {
                    // InternalMazeDsl.g:1017:2: ( 'sergeant' )
                    // InternalMazeDsl.g:1018:3: 'sergeant'
                    {
                     before(grammarAccess.getValidIDAccess().getSergeantKeyword_9()); 
                    match(input,22,FOLLOW_2); 
                     after(grammarAccess.getValidIDAccess().getSergeantKeyword_9()); 

                    }


                    }
                    break;
                case 11 :
                    // InternalMazeDsl.g:1023:2: ( 'sergeants' )
                    {
                    // InternalMazeDsl.g:1023:2: ( 'sergeants' )
                    // InternalMazeDsl.g:1024:3: 'sergeants'
                    {
                     before(grammarAccess.getValidIDAccess().getSergeantsKeyword_10()); 
                    match(input,23,FOLLOW_2); 
                     after(grammarAccess.getValidIDAccess().getSergeantsKeyword_10()); 

                    }


                    }
                    break;
                case 12 :
                    // InternalMazeDsl.g:1029:2: ( 'sightRange' )
                    {
                    // InternalMazeDsl.g:1029:2: ( 'sightRange' )
                    // InternalMazeDsl.g:1030:3: 'sightRange'
                    {
                     before(grammarAccess.getValidIDAccess().getSightRangeKeyword_11()); 
                    match(input,24,FOLLOW_2); 
                     after(grammarAccess.getValidIDAccess().getSightRangeKeyword_11()); 

                    }


                    }
                    break;
                case 13 :
                    // InternalMazeDsl.g:1035:2: ( 'rounds' )
                    {
                    // InternalMazeDsl.g:1035:2: ( 'rounds' )
                    // InternalMazeDsl.g:1036:3: 'rounds'
                    {
                     before(grammarAccess.getValidIDAccess().getRoundsKeyword_12()); 
                    match(input,25,FOLLOW_2); 
                     after(grammarAccess.getValidIDAccess().getRoundsKeyword_12()); 

                    }


                    }
                    break;
                case 14 :
                    // InternalMazeDsl.g:1041:2: ( 'roundDamage' )
                    {
                    // InternalMazeDsl.g:1041:2: ( 'roundDamage' )
                    // InternalMazeDsl.g:1042:3: 'roundDamage'
                    {
                     before(grammarAccess.getValidIDAccess().getRoundDamageKeyword_13()); 
                    match(input,26,FOLLOW_2); 
                     after(grammarAccess.getValidIDAccess().getRoundDamageKeyword_13()); 

                    }


                    }
                    break;
                case 15 :
                    // InternalMazeDsl.g:1047:2: ( 'roundInterval' )
                    {
                    // InternalMazeDsl.g:1047:2: ( 'roundInterval' )
                    // InternalMazeDsl.g:1048:3: 'roundInterval'
                    {
                     before(grammarAccess.getValidIDAccess().getRoundIntervalKeyword_14()); 
                    match(input,27,FOLLOW_2); 
                     after(grammarAccess.getValidIDAccess().getRoundIntervalKeyword_14()); 

                    }


                    }
                    break;
                case 16 :
                    // InternalMazeDsl.g:1053:2: ( 'burstCooldown' )
                    {
                    // InternalMazeDsl.g:1053:2: ( 'burstCooldown' )
                    // InternalMazeDsl.g:1054:3: 'burstCooldown'
                    {
                     before(grammarAccess.getValidIDAccess().getBurstCooldownKeyword_15()); 
                    match(input,28,FOLLOW_2); 
                     after(grammarAccess.getValidIDAccess().getBurstCooldownKeyword_15()); 

                    }


                    }
                    break;
                case 17 :
                    // InternalMazeDsl.g:1059:2: ( 'sprite' )
                    {
                    // InternalMazeDsl.g:1059:2: ( 'sprite' )
                    // InternalMazeDsl.g:1060:3: 'sprite'
                    {
                     before(grammarAccess.getValidIDAccess().getSpriteKeyword_16()); 
                    match(input,29,FOLLOW_2); 
                     after(grammarAccess.getValidIDAccess().getSpriteKeyword_16()); 

                    }


                    }
                    break;
                case 18 :
                    // InternalMazeDsl.g:1065:2: ( 'fireSound' )
                    {
                    // InternalMazeDsl.g:1065:2: ( 'fireSound' )
                    // InternalMazeDsl.g:1066:3: 'fireSound'
                    {
                     before(grammarAccess.getValidIDAccess().getFireSoundKeyword_17()); 
                    match(input,30,FOLLOW_2); 
                     after(grammarAccess.getValidIDAccess().getFireSoundKeyword_17()); 

                    }


                    }
                    break;
                case 19 :
                    // InternalMazeDsl.g:1071:2: ( 'drops' )
                    {
                    // InternalMazeDsl.g:1071:2: ( 'drops' )
                    // InternalMazeDsl.g:1072:3: 'drops'
                    {
                     before(grammarAccess.getValidIDAccess().getDropsKeyword_18()); 
                    match(input,31,FOLLOW_2); 
                     after(grammarAccess.getValidIDAccess().getDropsKeyword_18()); 

                    }


                    }
                    break;
                case 20 :
                    // InternalMazeDsl.g:1077:2: ( 'gatling' )
                    {
                    // InternalMazeDsl.g:1077:2: ( 'gatling' )
                    // InternalMazeDsl.g:1078:3: 'gatling'
                    {
                     before(grammarAccess.getValidIDAccess().getGatlingKeyword_19()); 
                    match(input,11,FOLLOW_2); 
                     after(grammarAccess.getValidIDAccess().getGatlingKeyword_19()); 

                    }


                    }
                    break;
                case 21 :
                    // InternalMazeDsl.g:1083:2: ( 'postDistance' )
                    {
                    // InternalMazeDsl.g:1083:2: ( 'postDistance' )
                    // InternalMazeDsl.g:1084:3: 'postDistance'
                    {
                     before(grammarAccess.getValidIDAccess().getPostDistanceKeyword_20()); 
                    match(input,32,FOLLOW_2); 
                     after(grammarAccess.getValidIDAccess().getPostDistanceKeyword_20()); 

                    }


                    }
                    break;
                case 22 :
                    // InternalMazeDsl.g:1089:2: ( 'mines' )
                    {
                    // InternalMazeDsl.g:1089:2: ( 'mines' )
                    // InternalMazeDsl.g:1090:3: 'mines'
                    {
                     before(grammarAccess.getValidIDAccess().getMinesKeyword_21()); 
                    match(input,33,FOLLOW_2); 
                     after(grammarAccess.getValidIDAccess().getMinesKeyword_21()); 

                    }


                    }
                    break;
                case 23 :
                    // InternalMazeDsl.g:1095:2: ( 'count' )
                    {
                    // InternalMazeDsl.g:1095:2: ( 'count' )
                    // InternalMazeDsl.g:1096:3: 'count'
                    {
                     before(grammarAccess.getValidIDAccess().getCountKeyword_22()); 
                    match(input,34,FOLLOW_2); 
                     after(grammarAccess.getValidIDAccess().getCountKeyword_22()); 

                    }


                    }
                    break;
                case 24 :
                    // InternalMazeDsl.g:1101:2: ( 'ringRadius' )
                    {
                    // InternalMazeDsl.g:1101:2: ( 'ringRadius' )
                    // InternalMazeDsl.g:1102:3: 'ringRadius'
                    {
                     before(grammarAccess.getValidIDAccess().getRingRadiusKeyword_23()); 
                    match(input,35,FOLLOW_2); 
                     after(grammarAccess.getValidIDAccess().getRingRadiusKeyword_23()); 

                    }


                    }
                    break;
                case 25 :
                    // InternalMazeDsl.g:1107:2: ( 'triggerRadius' )
                    {
                    // InternalMazeDsl.g:1107:2: ( 'triggerRadius' )
                    // InternalMazeDsl.g:1108:3: 'triggerRadius'
                    {
                     before(grammarAccess.getValidIDAccess().getTriggerRadiusKeyword_24()); 
                    match(input,36,FOLLOW_2); 
                     after(grammarAccess.getValidIDAccess().getTriggerRadiusKeyword_24()); 

                    }


                    }
                    break;
                case 26 :
                    // InternalMazeDsl.g:1113:2: ( 'pulse' )
                    {
                    // InternalMazeDsl.g:1113:2: ( 'pulse' )
                    // InternalMazeDsl.g:1114:3: 'pulse'
                    {
                     before(grammarAccess.getValidIDAccess().getPulseKeyword_25()); 
                    match(input,37,FOLLOW_2); 
                     after(grammarAccess.getValidIDAccess().getPulseKeyword_25()); 

                    }


                    }
                    break;
                case 27 :
                    // InternalMazeDsl.g:1119:2: ( 'blastRadius' )
                    {
                    // InternalMazeDsl.g:1119:2: ( 'blastRadius' )
                    // InternalMazeDsl.g:1120:3: 'blastRadius'
                    {
                     before(grammarAccess.getValidIDAccess().getBlastRadiusKeyword_26()); 
                    match(input,38,FOLLOW_2); 
                     after(grammarAccess.getValidIDAccess().getBlastRadiusKeyword_26()); 

                    }


                    }
                    break;
                case 28 :
                    // InternalMazeDsl.g:1125:2: ( 'blastDamage' )
                    {
                    // InternalMazeDsl.g:1125:2: ( 'blastDamage' )
                    // InternalMazeDsl.g:1126:3: 'blastDamage'
                    {
                     before(grammarAccess.getValidIDAccess().getBlastDamageKeyword_27()); 
                    match(input,39,FOLLOW_2); 
                     after(grammarAccess.getValidIDAccess().getBlastDamageKeyword_27()); 

                    }


                    }
                    break;
                case 29 :
                    // InternalMazeDsl.g:1131:2: ( 'playerDamage' )
                    {
                    // InternalMazeDsl.g:1131:2: ( 'playerDamage' )
                    // InternalMazeDsl.g:1132:3: 'playerDamage'
                    {
                     before(grammarAccess.getValidIDAccess().getPlayerDamageKeyword_28()); 
                    match(input,40,FOLLOW_2); 
                     after(grammarAccess.getValidIDAccess().getPlayerDamageKeyword_28()); 

                    }


                    }
                    break;
                case 30 :
                    // InternalMazeDsl.g:1137:2: ( 'holeRadius' )
                    {
                    // InternalMazeDsl.g:1137:2: ( 'holeRadius' )
                    // InternalMazeDsl.g:1138:3: 'holeRadius'
                    {
                     before(grammarAccess.getValidIDAccess().getHoleRadiusKeyword_29()); 
                    match(input,41,FOLLOW_2); 
                     after(grammarAccess.getValidIDAccess().getHoleRadiusKeyword_29()); 

                    }


                    }
                    break;
                case 31 :
                    // InternalMazeDsl.g:1143:2: ( 'portalGhost' )
                    {
                    // InternalMazeDsl.g:1143:2: ( 'portalGhost' )
                    // InternalMazeDsl.g:1144:3: 'portalGhost'
                    {
                     before(grammarAccess.getValidIDAccess().getPortalGhostKeyword_30()); 
                    match(input,42,FOLLOW_2); 
                     after(grammarAccess.getValidIDAccess().getPortalGhostKeyword_30()); 

                    }


                    }
                    break;
                case 32 :
                    // InternalMazeDsl.g:1149:2: ( 'portalGhosts' )
                    {
                    // InternalMazeDsl.g:1149:2: ( 'portalGhosts' )
                    // InternalMazeDsl.g:1150:3: 'portalGhosts'
                    {
                     before(grammarAccess.getValidIDAccess().getPortalGhostsKeyword_31()); 
                    match(input,43,FOLLOW_2); 
                     after(grammarAccess.getValidIDAccess().getPortalGhostsKeyword_31()); 

                    }


                    }
                    break;
                case 33 :
                    // InternalMazeDsl.g:1155:2: ( 'teleport' )
                    {
                    // InternalMazeDsl.g:1155:2: ( 'teleport' )
                    // InternalMazeDsl.g:1156:3: 'teleport'
                    {
                     before(grammarAccess.getValidIDAccess().getTeleportKeyword_32()); 
                    match(input,44,FOLLOW_2); 
                     after(grammarAccess.getValidIDAccess().getTeleportKeyword_32()); 

                    }


                    }
                    break;
                case 34 :
                    // InternalMazeDsl.g:1161:2: ( 'cooldown' )
                    {
                    // InternalMazeDsl.g:1161:2: ( 'cooldown' )
                    // InternalMazeDsl.g:1162:3: 'cooldown'
                    {
                     before(grammarAccess.getValidIDAccess().getCooldownKeyword_33()); 
                    match(input,45,FOLLOW_2); 
                     after(grammarAccess.getValidIDAccess().getCooldownKeyword_33()); 

                    }


                    }
                    break;
                case 35 :
                    // InternalMazeDsl.g:1167:2: ( 'banner' )
                    {
                    // InternalMazeDsl.g:1167:2: ( 'banner' )
                    // InternalMazeDsl.g:1168:3: 'banner'
                    {
                     before(grammarAccess.getValidIDAccess().getBannerKeyword_34()); 
                    match(input,46,FOLLOW_2); 
                     after(grammarAccess.getValidIDAccess().getBannerKeyword_34()); 

                    }


                    }
                    break;
                case 36 :
                    // InternalMazeDsl.g:1173:2: ( 'bannerSeconds' )
                    {
                    // InternalMazeDsl.g:1173:2: ( 'bannerSeconds' )
                    // InternalMazeDsl.g:1174:3: 'bannerSeconds'
                    {
                     before(grammarAccess.getValidIDAccess().getBannerSecondsKeyword_35()); 
                    match(input,47,FOLLOW_2); 
                     after(grammarAccess.getValidIDAccess().getBannerSecondsKeyword_35()); 

                    }


                    }
                    break;
                case 37 :
                    // InternalMazeDsl.g:1179:2: ( 'zapSound' )
                    {
                    // InternalMazeDsl.g:1179:2: ( 'zapSound' )
                    // InternalMazeDsl.g:1180:3: 'zapSound'
                    {
                     before(grammarAccess.getValidIDAccess().getZapSoundKeyword_36()); 
                    match(input,48,FOLLOW_2); 
                     after(grammarAccess.getValidIDAccess().getZapSoundKeyword_36()); 

                    }


                    }
                    break;
                case 38 :
                    // InternalMazeDsl.g:1185:2: ( 'voiceSound' )
                    {
                    // InternalMazeDsl.g:1185:2: ( 'voiceSound' )
                    // InternalMazeDsl.g:1186:3: 'voiceSound'
                    {
                     before(grammarAccess.getValidIDAccess().getVoiceSoundKeyword_37()); 
                    match(input,49,FOLLOW_2); 
                     after(grammarAccess.getValidIDAccess().getVoiceSoundKeyword_37()); 

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
    // $ANTLR end "rule__ValidID__Alternatives"


    // $ANTLR start "rule__DifficultyLevel__Alternatives"
    // InternalMazeDsl.g:1195:1: rule__DifficultyLevel__Alternatives : ( ( ( 'easy' ) ) | ( ( 'normal' ) ) | ( ( 'hard' ) ) );
    public final void rule__DifficultyLevel__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1199:1: ( ( ( 'easy' ) ) | ( ( 'normal' ) ) | ( ( 'hard' ) ) )
            int alt5=3;
            switch ( input.LA(1) ) {
            case 50:
                {
                alt5=1;
                }
                break;
            case 51:
                {
                alt5=2;
                }
                break;
            case 52:
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
                    // InternalMazeDsl.g:1200:2: ( ( 'easy' ) )
                    {
                    // InternalMazeDsl.g:1200:2: ( ( 'easy' ) )
                    // InternalMazeDsl.g:1201:3: ( 'easy' )
                    {
                     before(grammarAccess.getDifficultyLevelAccess().getEASYEnumLiteralDeclaration_0()); 
                    // InternalMazeDsl.g:1202:3: ( 'easy' )
                    // InternalMazeDsl.g:1202:4: 'easy'
                    {
                    match(input,50,FOLLOW_2); 

                    }

                     after(grammarAccess.getDifficultyLevelAccess().getEASYEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:1206:2: ( ( 'normal' ) )
                    {
                    // InternalMazeDsl.g:1206:2: ( ( 'normal' ) )
                    // InternalMazeDsl.g:1207:3: ( 'normal' )
                    {
                     before(grammarAccess.getDifficultyLevelAccess().getNORMALEnumLiteralDeclaration_1()); 
                    // InternalMazeDsl.g:1208:3: ( 'normal' )
                    // InternalMazeDsl.g:1208:4: 'normal'
                    {
                    match(input,51,FOLLOW_2); 

                    }

                     after(grammarAccess.getDifficultyLevelAccess().getNORMALEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalMazeDsl.g:1212:2: ( ( 'hard' ) )
                    {
                    // InternalMazeDsl.g:1212:2: ( ( 'hard' ) )
                    // InternalMazeDsl.g:1213:3: ( 'hard' )
                    {
                     before(grammarAccess.getDifficultyLevelAccess().getHARDEnumLiteralDeclaration_2()); 
                    // InternalMazeDsl.g:1214:3: ( 'hard' )
                    // InternalMazeDsl.g:1214:4: 'hard'
                    {
                    match(input,52,FOLLOW_2); 

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
    // InternalMazeDsl.g:1222:1: rule__EnemyType__Alternatives : ( ( ( 'zombie' ) ) | ( ( 'ghost' ) ) | ( ( 'pumpkinbomber' ) ) );
    public final void rule__EnemyType__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1226:1: ( ( ( 'zombie' ) ) | ( ( 'ghost' ) ) | ( ( 'pumpkinbomber' ) ) )
            int alt6=3;
            switch ( input.LA(1) ) {
            case 53:
                {
                alt6=1;
                }
                break;
            case 54:
                {
                alt6=2;
                }
                break;
            case 55:
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
                    // InternalMazeDsl.g:1227:2: ( ( 'zombie' ) )
                    {
                    // InternalMazeDsl.g:1227:2: ( ( 'zombie' ) )
                    // InternalMazeDsl.g:1228:3: ( 'zombie' )
                    {
                     before(grammarAccess.getEnemyTypeAccess().getZOMBIEEnumLiteralDeclaration_0()); 
                    // InternalMazeDsl.g:1229:3: ( 'zombie' )
                    // InternalMazeDsl.g:1229:4: 'zombie'
                    {
                    match(input,53,FOLLOW_2); 

                    }

                     after(grammarAccess.getEnemyTypeAccess().getZOMBIEEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:1233:2: ( ( 'ghost' ) )
                    {
                    // InternalMazeDsl.g:1233:2: ( ( 'ghost' ) )
                    // InternalMazeDsl.g:1234:3: ( 'ghost' )
                    {
                     before(grammarAccess.getEnemyTypeAccess().getGHOSTEnumLiteralDeclaration_1()); 
                    // InternalMazeDsl.g:1235:3: ( 'ghost' )
                    // InternalMazeDsl.g:1235:4: 'ghost'
                    {
                    match(input,54,FOLLOW_2); 

                    }

                     after(grammarAccess.getEnemyTypeAccess().getGHOSTEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalMazeDsl.g:1239:2: ( ( 'pumpkinbomber' ) )
                    {
                    // InternalMazeDsl.g:1239:2: ( ( 'pumpkinbomber' ) )
                    // InternalMazeDsl.g:1240:3: ( 'pumpkinbomber' )
                    {
                     before(grammarAccess.getEnemyTypeAccess().getPUMPKINBOMBEREnumLiteralDeclaration_2()); 
                    // InternalMazeDsl.g:1241:3: ( 'pumpkinbomber' )
                    // InternalMazeDsl.g:1241:4: 'pumpkinbomber'
                    {
                    match(input,55,FOLLOW_2); 

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
    // InternalMazeDsl.g:1249:1: rule__CharacterTypeEnum__Alternatives : ( ( ( 'zombie' ) ) | ( ( 'ghost' ) ) | ( ( 'pumpkinbomber' ) ) );
    public final void rule__CharacterTypeEnum__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1253:1: ( ( ( 'zombie' ) ) | ( ( 'ghost' ) ) | ( ( 'pumpkinbomber' ) ) )
            int alt7=3;
            switch ( input.LA(1) ) {
            case 53:
                {
                alt7=1;
                }
                break;
            case 54:
                {
                alt7=2;
                }
                break;
            case 55:
                {
                alt7=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }

            switch (alt7) {
                case 1 :
                    // InternalMazeDsl.g:1254:2: ( ( 'zombie' ) )
                    {
                    // InternalMazeDsl.g:1254:2: ( ( 'zombie' ) )
                    // InternalMazeDsl.g:1255:3: ( 'zombie' )
                    {
                     before(grammarAccess.getCharacterTypeEnumAccess().getZOMBIEEnumLiteralDeclaration_0()); 
                    // InternalMazeDsl.g:1256:3: ( 'zombie' )
                    // InternalMazeDsl.g:1256:4: 'zombie'
                    {
                    match(input,53,FOLLOW_2); 

                    }

                     after(grammarAccess.getCharacterTypeEnumAccess().getZOMBIEEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:1260:2: ( ( 'ghost' ) )
                    {
                    // InternalMazeDsl.g:1260:2: ( ( 'ghost' ) )
                    // InternalMazeDsl.g:1261:3: ( 'ghost' )
                    {
                     before(grammarAccess.getCharacterTypeEnumAccess().getGHOSTEnumLiteralDeclaration_1()); 
                    // InternalMazeDsl.g:1262:3: ( 'ghost' )
                    // InternalMazeDsl.g:1262:4: 'ghost'
                    {
                    match(input,54,FOLLOW_2); 

                    }

                     after(grammarAccess.getCharacterTypeEnumAccess().getGHOSTEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalMazeDsl.g:1266:2: ( ( 'pumpkinbomber' ) )
                    {
                    // InternalMazeDsl.g:1266:2: ( ( 'pumpkinbomber' ) )
                    // InternalMazeDsl.g:1267:3: ( 'pumpkinbomber' )
                    {
                     before(grammarAccess.getCharacterTypeEnumAccess().getPUMPKINBOMBEREnumLiteralDeclaration_2()); 
                    // InternalMazeDsl.g:1268:3: ( 'pumpkinbomber' )
                    // InternalMazeDsl.g:1268:4: 'pumpkinbomber'
                    {
                    match(input,55,FOLLOW_2); 

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
    // InternalMazeDsl.g:1276:1: rule__BehaviorTypeEnum__Alternatives : ( ( ( 'passive' ) ) | ( ( 'wander' ) ) | ( ( 'aggressive' ) ) | ( ( 'patrol' ) ) );
    public final void rule__BehaviorTypeEnum__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1280:1: ( ( ( 'passive' ) ) | ( ( 'wander' ) ) | ( ( 'aggressive' ) ) | ( ( 'patrol' ) ) )
            int alt8=4;
            switch ( input.LA(1) ) {
            case 56:
                {
                alt8=1;
                }
                break;
            case 57:
                {
                alt8=2;
                }
                break;
            case 58:
                {
                alt8=3;
                }
                break;
            case 59:
                {
                alt8=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }

            switch (alt8) {
                case 1 :
                    // InternalMazeDsl.g:1281:2: ( ( 'passive' ) )
                    {
                    // InternalMazeDsl.g:1281:2: ( ( 'passive' ) )
                    // InternalMazeDsl.g:1282:3: ( 'passive' )
                    {
                     before(grammarAccess.getBehaviorTypeEnumAccess().getPASSIVEEnumLiteralDeclaration_0()); 
                    // InternalMazeDsl.g:1283:3: ( 'passive' )
                    // InternalMazeDsl.g:1283:4: 'passive'
                    {
                    match(input,56,FOLLOW_2); 

                    }

                     after(grammarAccess.getBehaviorTypeEnumAccess().getPASSIVEEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:1287:2: ( ( 'wander' ) )
                    {
                    // InternalMazeDsl.g:1287:2: ( ( 'wander' ) )
                    // InternalMazeDsl.g:1288:3: ( 'wander' )
                    {
                     before(grammarAccess.getBehaviorTypeEnumAccess().getWANDEREnumLiteralDeclaration_1()); 
                    // InternalMazeDsl.g:1289:3: ( 'wander' )
                    // InternalMazeDsl.g:1289:4: 'wander'
                    {
                    match(input,57,FOLLOW_2); 

                    }

                     after(grammarAccess.getBehaviorTypeEnumAccess().getWANDEREnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalMazeDsl.g:1293:2: ( ( 'aggressive' ) )
                    {
                    // InternalMazeDsl.g:1293:2: ( ( 'aggressive' ) )
                    // InternalMazeDsl.g:1294:3: ( 'aggressive' )
                    {
                     before(grammarAccess.getBehaviorTypeEnumAccess().getAGGRESSIVEEnumLiteralDeclaration_2()); 
                    // InternalMazeDsl.g:1295:3: ( 'aggressive' )
                    // InternalMazeDsl.g:1295:4: 'aggressive'
                    {
                    match(input,58,FOLLOW_2); 

                    }

                     after(grammarAccess.getBehaviorTypeEnumAccess().getAGGRESSIVEEnumLiteralDeclaration_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalMazeDsl.g:1299:2: ( ( 'patrol' ) )
                    {
                    // InternalMazeDsl.g:1299:2: ( ( 'patrol' ) )
                    // InternalMazeDsl.g:1300:3: ( 'patrol' )
                    {
                     before(grammarAccess.getBehaviorTypeEnumAccess().getPATROLEnumLiteralDeclaration_3()); 
                    // InternalMazeDsl.g:1301:3: ( 'patrol' )
                    // InternalMazeDsl.g:1301:4: 'patrol'
                    {
                    match(input,59,FOLLOW_2); 

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
    // InternalMazeDsl.g:1309:1: rule__ProjectileTypeEnum__Alternatives : ( ( ( 'straight' ) ) | ( ( 'lob' ) ) | ( ( 'beam' ) ) );
    public final void rule__ProjectileTypeEnum__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1313:1: ( ( ( 'straight' ) ) | ( ( 'lob' ) ) | ( ( 'beam' ) ) )
            int alt9=3;
            switch ( input.LA(1) ) {
            case 60:
                {
                alt9=1;
                }
                break;
            case 61:
                {
                alt9=2;
                }
                break;
            case 62:
                {
                alt9=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }

            switch (alt9) {
                case 1 :
                    // InternalMazeDsl.g:1314:2: ( ( 'straight' ) )
                    {
                    // InternalMazeDsl.g:1314:2: ( ( 'straight' ) )
                    // InternalMazeDsl.g:1315:3: ( 'straight' )
                    {
                     before(grammarAccess.getProjectileTypeEnumAccess().getSTRAIGHTEnumLiteralDeclaration_0()); 
                    // InternalMazeDsl.g:1316:3: ( 'straight' )
                    // InternalMazeDsl.g:1316:4: 'straight'
                    {
                    match(input,60,FOLLOW_2); 

                    }

                     after(grammarAccess.getProjectileTypeEnumAccess().getSTRAIGHTEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:1320:2: ( ( 'lob' ) )
                    {
                    // InternalMazeDsl.g:1320:2: ( ( 'lob' ) )
                    // InternalMazeDsl.g:1321:3: ( 'lob' )
                    {
                     before(grammarAccess.getProjectileTypeEnumAccess().getLOBEnumLiteralDeclaration_1()); 
                    // InternalMazeDsl.g:1322:3: ( 'lob' )
                    // InternalMazeDsl.g:1322:4: 'lob'
                    {
                    match(input,61,FOLLOW_2); 

                    }

                     after(grammarAccess.getProjectileTypeEnumAccess().getLOBEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalMazeDsl.g:1326:2: ( ( 'beam' ) )
                    {
                    // InternalMazeDsl.g:1326:2: ( ( 'beam' ) )
                    // InternalMazeDsl.g:1327:3: ( 'beam' )
                    {
                     before(grammarAccess.getProjectileTypeEnumAccess().getBEAMEnumLiteralDeclaration_2()); 
                    // InternalMazeDsl.g:1328:3: ( 'beam' )
                    // InternalMazeDsl.g:1328:4: 'beam'
                    {
                    match(input,62,FOLLOW_2); 

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
    // InternalMazeDsl.g:1336:1: rule__LootItemTypeEnum__Alternatives : ( ( ( 'food' ) ) | ( ( 'bomb' ) ) | ( ( 'trap' ) ) | ( ( 'weapon' ) ) );
    public final void rule__LootItemTypeEnum__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1340:1: ( ( ( 'food' ) ) | ( ( 'bomb' ) ) | ( ( 'trap' ) ) | ( ( 'weapon' ) ) )
            int alt10=4;
            switch ( input.LA(1) ) {
            case 63:
                {
                alt10=1;
                }
                break;
            case 64:
                {
                alt10=2;
                }
                break;
            case 65:
                {
                alt10=3;
                }
                break;
            case 66:
                {
                alt10=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }

            switch (alt10) {
                case 1 :
                    // InternalMazeDsl.g:1341:2: ( ( 'food' ) )
                    {
                    // InternalMazeDsl.g:1341:2: ( ( 'food' ) )
                    // InternalMazeDsl.g:1342:3: ( 'food' )
                    {
                     before(grammarAccess.getLootItemTypeEnumAccess().getFOODEnumLiteralDeclaration_0()); 
                    // InternalMazeDsl.g:1343:3: ( 'food' )
                    // InternalMazeDsl.g:1343:4: 'food'
                    {
                    match(input,63,FOLLOW_2); 

                    }

                     after(grammarAccess.getLootItemTypeEnumAccess().getFOODEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:1347:2: ( ( 'bomb' ) )
                    {
                    // InternalMazeDsl.g:1347:2: ( ( 'bomb' ) )
                    // InternalMazeDsl.g:1348:3: ( 'bomb' )
                    {
                     before(grammarAccess.getLootItemTypeEnumAccess().getBOMBEnumLiteralDeclaration_1()); 
                    // InternalMazeDsl.g:1349:3: ( 'bomb' )
                    // InternalMazeDsl.g:1349:4: 'bomb'
                    {
                    match(input,64,FOLLOW_2); 

                    }

                     after(grammarAccess.getLootItemTypeEnumAccess().getBOMBEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalMazeDsl.g:1353:2: ( ( 'trap' ) )
                    {
                    // InternalMazeDsl.g:1353:2: ( ( 'trap' ) )
                    // InternalMazeDsl.g:1354:3: ( 'trap' )
                    {
                     before(grammarAccess.getLootItemTypeEnumAccess().getTRAPEnumLiteralDeclaration_2()); 
                    // InternalMazeDsl.g:1355:3: ( 'trap' )
                    // InternalMazeDsl.g:1355:4: 'trap'
                    {
                    match(input,65,FOLLOW_2); 

                    }

                     after(grammarAccess.getLootItemTypeEnumAccess().getTRAPEnumLiteralDeclaration_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalMazeDsl.g:1359:2: ( ( 'weapon' ) )
                    {
                    // InternalMazeDsl.g:1359:2: ( ( 'weapon' ) )
                    // InternalMazeDsl.g:1360:3: ( 'weapon' )
                    {
                     before(grammarAccess.getLootItemTypeEnumAccess().getWEAPONEnumLiteralDeclaration_3()); 
                    // InternalMazeDsl.g:1361:3: ( 'weapon' )
                    // InternalMazeDsl.g:1361:4: 'weapon'
                    {
                    match(input,66,FOLLOW_2); 

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
    // InternalMazeDsl.g:1369:1: rule__GameConfiguration__Group__0 : rule__GameConfiguration__Group__0__Impl rule__GameConfiguration__Group__1 ;
    public final void rule__GameConfiguration__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1373:1: ( rule__GameConfiguration__Group__0__Impl rule__GameConfiguration__Group__1 )
            // InternalMazeDsl.g:1374:2: rule__GameConfiguration__Group__0__Impl rule__GameConfiguration__Group__1
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
    // InternalMazeDsl.g:1381:1: rule__GameConfiguration__Group__0__Impl : ( 'game' ) ;
    public final void rule__GameConfiguration__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1385:1: ( ( 'game' ) )
            // InternalMazeDsl.g:1386:1: ( 'game' )
            {
            // InternalMazeDsl.g:1386:1: ( 'game' )
            // InternalMazeDsl.g:1387:2: 'game'
            {
             before(grammarAccess.getGameConfigurationAccess().getGameKeyword_0()); 
            match(input,67,FOLLOW_2); 
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
    // InternalMazeDsl.g:1396:1: rule__GameConfiguration__Group__1 : rule__GameConfiguration__Group__1__Impl rule__GameConfiguration__Group__2 ;
    public final void rule__GameConfiguration__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1400:1: ( rule__GameConfiguration__Group__1__Impl rule__GameConfiguration__Group__2 )
            // InternalMazeDsl.g:1401:2: rule__GameConfiguration__Group__1__Impl rule__GameConfiguration__Group__2
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
    // InternalMazeDsl.g:1408:1: rule__GameConfiguration__Group__1__Impl : ( ( rule__GameConfiguration__NameAssignment_1 ) ) ;
    public final void rule__GameConfiguration__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1412:1: ( ( ( rule__GameConfiguration__NameAssignment_1 ) ) )
            // InternalMazeDsl.g:1413:1: ( ( rule__GameConfiguration__NameAssignment_1 ) )
            {
            // InternalMazeDsl.g:1413:1: ( ( rule__GameConfiguration__NameAssignment_1 ) )
            // InternalMazeDsl.g:1414:2: ( rule__GameConfiguration__NameAssignment_1 )
            {
             before(grammarAccess.getGameConfigurationAccess().getNameAssignment_1()); 
            // InternalMazeDsl.g:1415:2: ( rule__GameConfiguration__NameAssignment_1 )
            // InternalMazeDsl.g:1415:3: rule__GameConfiguration__NameAssignment_1
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
    // InternalMazeDsl.g:1423:1: rule__GameConfiguration__Group__2 : rule__GameConfiguration__Group__2__Impl rule__GameConfiguration__Group__3 ;
    public final void rule__GameConfiguration__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1427:1: ( rule__GameConfiguration__Group__2__Impl rule__GameConfiguration__Group__3 )
            // InternalMazeDsl.g:1428:2: rule__GameConfiguration__Group__2__Impl rule__GameConfiguration__Group__3
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
    // InternalMazeDsl.g:1435:1: rule__GameConfiguration__Group__2__Impl : ( '{' ) ;
    public final void rule__GameConfiguration__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1439:1: ( ( '{' ) )
            // InternalMazeDsl.g:1440:1: ( '{' )
            {
            // InternalMazeDsl.g:1440:1: ( '{' )
            // InternalMazeDsl.g:1441:2: '{'
            {
             before(grammarAccess.getGameConfigurationAccess().getLeftCurlyBracketKeyword_2()); 
            match(input,68,FOLLOW_2); 
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
    // InternalMazeDsl.g:1450:1: rule__GameConfiguration__Group__3 : rule__GameConfiguration__Group__3__Impl rule__GameConfiguration__Group__4 ;
    public final void rule__GameConfiguration__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1454:1: ( rule__GameConfiguration__Group__3__Impl rule__GameConfiguration__Group__4 )
            // InternalMazeDsl.g:1455:2: rule__GameConfiguration__Group__3__Impl rule__GameConfiguration__Group__4
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
    // InternalMazeDsl.g:1462:1: rule__GameConfiguration__Group__3__Impl : ( ( rule__GameConfiguration__ImportsAssignment_3 )* ) ;
    public final void rule__GameConfiguration__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1466:1: ( ( ( rule__GameConfiguration__ImportsAssignment_3 )* ) )
            // InternalMazeDsl.g:1467:1: ( ( rule__GameConfiguration__ImportsAssignment_3 )* )
            {
            // InternalMazeDsl.g:1467:1: ( ( rule__GameConfiguration__ImportsAssignment_3 )* )
            // InternalMazeDsl.g:1468:2: ( rule__GameConfiguration__ImportsAssignment_3 )*
            {
             before(grammarAccess.getGameConfigurationAccess().getImportsAssignment_3()); 
            // InternalMazeDsl.g:1469:2: ( rule__GameConfiguration__ImportsAssignment_3 )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==70) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // InternalMazeDsl.g:1469:3: rule__GameConfiguration__ImportsAssignment_3
            	    {
            	    pushFollow(FOLLOW_6);
            	    rule__GameConfiguration__ImportsAssignment_3();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop11;
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
    // InternalMazeDsl.g:1477:1: rule__GameConfiguration__Group__4 : rule__GameConfiguration__Group__4__Impl rule__GameConfiguration__Group__5 ;
    public final void rule__GameConfiguration__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1481:1: ( rule__GameConfiguration__Group__4__Impl rule__GameConfiguration__Group__5 )
            // InternalMazeDsl.g:1482:2: rule__GameConfiguration__Group__4__Impl rule__GameConfiguration__Group__5
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
    // InternalMazeDsl.g:1489:1: rule__GameConfiguration__Group__4__Impl : ( ( rule__GameConfiguration__DifficultyAssignment_4 )? ) ;
    public final void rule__GameConfiguration__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1493:1: ( ( ( rule__GameConfiguration__DifficultyAssignment_4 )? ) )
            // InternalMazeDsl.g:1494:1: ( ( rule__GameConfiguration__DifficultyAssignment_4 )? )
            {
            // InternalMazeDsl.g:1494:1: ( ( rule__GameConfiguration__DifficultyAssignment_4 )? )
            // InternalMazeDsl.g:1495:2: ( rule__GameConfiguration__DifficultyAssignment_4 )?
            {
             before(grammarAccess.getGameConfigurationAccess().getDifficultyAssignment_4()); 
            // InternalMazeDsl.g:1496:2: ( rule__GameConfiguration__DifficultyAssignment_4 )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==71) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // InternalMazeDsl.g:1496:3: rule__GameConfiguration__DifficultyAssignment_4
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
    // InternalMazeDsl.g:1504:1: rule__GameConfiguration__Group__5 : rule__GameConfiguration__Group__5__Impl rule__GameConfiguration__Group__6 ;
    public final void rule__GameConfiguration__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1508:1: ( rule__GameConfiguration__Group__5__Impl rule__GameConfiguration__Group__6 )
            // InternalMazeDsl.g:1509:2: rule__GameConfiguration__Group__5__Impl rule__GameConfiguration__Group__6
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
    // InternalMazeDsl.g:1516:1: rule__GameConfiguration__Group__5__Impl : ( ( rule__GameConfiguration__OpponentsAssignment_5 )* ) ;
    public final void rule__GameConfiguration__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1520:1: ( ( ( rule__GameConfiguration__OpponentsAssignment_5 )* ) )
            // InternalMazeDsl.g:1521:1: ( ( rule__GameConfiguration__OpponentsAssignment_5 )* )
            {
            // InternalMazeDsl.g:1521:1: ( ( rule__GameConfiguration__OpponentsAssignment_5 )* )
            // InternalMazeDsl.g:1522:2: ( rule__GameConfiguration__OpponentsAssignment_5 )*
            {
             before(grammarAccess.getGameConfigurationAccess().getOpponentsAssignment_5()); 
            // InternalMazeDsl.g:1523:2: ( rule__GameConfiguration__OpponentsAssignment_5 )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==79) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // InternalMazeDsl.g:1523:3: rule__GameConfiguration__OpponentsAssignment_5
            	    {
            	    pushFollow(FOLLOW_7);
            	    rule__GameConfiguration__OpponentsAssignment_5();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop13;
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
    // InternalMazeDsl.g:1531:1: rule__GameConfiguration__Group__6 : rule__GameConfiguration__Group__6__Impl rule__GameConfiguration__Group__7 ;
    public final void rule__GameConfiguration__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1535:1: ( rule__GameConfiguration__Group__6__Impl rule__GameConfiguration__Group__7 )
            // InternalMazeDsl.g:1536:2: rule__GameConfiguration__Group__6__Impl rule__GameConfiguration__Group__7
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
    // InternalMazeDsl.g:1543:1: rule__GameConfiguration__Group__6__Impl : ( ( rule__GameConfiguration__PatrolsAssignment_6 )* ) ;
    public final void rule__GameConfiguration__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1547:1: ( ( ( rule__GameConfiguration__PatrolsAssignment_6 )* ) )
            // InternalMazeDsl.g:1548:1: ( ( rule__GameConfiguration__PatrolsAssignment_6 )* )
            {
            // InternalMazeDsl.g:1548:1: ( ( rule__GameConfiguration__PatrolsAssignment_6 )* )
            // InternalMazeDsl.g:1549:2: ( rule__GameConfiguration__PatrolsAssignment_6 )*
            {
             before(grammarAccess.getGameConfigurationAccess().getPatrolsAssignment_6()); 
            // InternalMazeDsl.g:1550:2: ( rule__GameConfiguration__PatrolsAssignment_6 )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0==59) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // InternalMazeDsl.g:1550:3: rule__GameConfiguration__PatrolsAssignment_6
            	    {
            	    pushFollow(FOLLOW_8);
            	    rule__GameConfiguration__PatrolsAssignment_6();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop14;
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
    // InternalMazeDsl.g:1558:1: rule__GameConfiguration__Group__7 : rule__GameConfiguration__Group__7__Impl rule__GameConfiguration__Group__8 ;
    public final void rule__GameConfiguration__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1562:1: ( rule__GameConfiguration__Group__7__Impl rule__GameConfiguration__Group__8 )
            // InternalMazeDsl.g:1563:2: rule__GameConfiguration__Group__7__Impl rule__GameConfiguration__Group__8
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
    // InternalMazeDsl.g:1570:1: rule__GameConfiguration__Group__7__Impl : ( ( rule__GameConfiguration__LootTablesAssignment_7 )* ) ;
    public final void rule__GameConfiguration__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1574:1: ( ( ( rule__GameConfiguration__LootTablesAssignment_7 )* ) )
            // InternalMazeDsl.g:1575:1: ( ( rule__GameConfiguration__LootTablesAssignment_7 )* )
            {
            // InternalMazeDsl.g:1575:1: ( ( rule__GameConfiguration__LootTablesAssignment_7 )* )
            // InternalMazeDsl.g:1576:2: ( rule__GameConfiguration__LootTablesAssignment_7 )*
            {
             before(grammarAccess.getGameConfigurationAccess().getLootTablesAssignment_7()); 
            // InternalMazeDsl.g:1577:2: ( rule__GameConfiguration__LootTablesAssignment_7 )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( (LA15_0==114) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // InternalMazeDsl.g:1577:3: rule__GameConfiguration__LootTablesAssignment_7
            	    {
            	    pushFollow(FOLLOW_9);
            	    rule__GameConfiguration__LootTablesAssignment_7();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop15;
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
    // InternalMazeDsl.g:1585:1: rule__GameConfiguration__Group__8 : rule__GameConfiguration__Group__8__Impl rule__GameConfiguration__Group__9 ;
    public final void rule__GameConfiguration__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1589:1: ( rule__GameConfiguration__Group__8__Impl rule__GameConfiguration__Group__9 )
            // InternalMazeDsl.g:1590:2: rule__GameConfiguration__Group__8__Impl rule__GameConfiguration__Group__9
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
    // InternalMazeDsl.g:1597:1: rule__GameConfiguration__Group__8__Impl : ( ( rule__GameConfiguration__LevelsAssignment_8 )* ) ;
    public final void rule__GameConfiguration__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1601:1: ( ( ( rule__GameConfiguration__LevelsAssignment_8 )* ) )
            // InternalMazeDsl.g:1602:1: ( ( rule__GameConfiguration__LevelsAssignment_8 )* )
            {
            // InternalMazeDsl.g:1602:1: ( ( rule__GameConfiguration__LevelsAssignment_8 )* )
            // InternalMazeDsl.g:1603:2: ( rule__GameConfiguration__LevelsAssignment_8 )*
            {
             before(grammarAccess.getGameConfigurationAccess().getLevelsAssignment_8()); 
            // InternalMazeDsl.g:1604:2: ( rule__GameConfiguration__LevelsAssignment_8 )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0==72) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // InternalMazeDsl.g:1604:3: rule__GameConfiguration__LevelsAssignment_8
            	    {
            	    pushFollow(FOLLOW_10);
            	    rule__GameConfiguration__LevelsAssignment_8();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop16;
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
    // InternalMazeDsl.g:1612:1: rule__GameConfiguration__Group__9 : rule__GameConfiguration__Group__9__Impl ;
    public final void rule__GameConfiguration__Group__9() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1616:1: ( rule__GameConfiguration__Group__9__Impl )
            // InternalMazeDsl.g:1617:2: rule__GameConfiguration__Group__9__Impl
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
    // InternalMazeDsl.g:1623:1: rule__GameConfiguration__Group__9__Impl : ( '}' ) ;
    public final void rule__GameConfiguration__Group__9__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1627:1: ( ( '}' ) )
            // InternalMazeDsl.g:1628:1: ( '}' )
            {
            // InternalMazeDsl.g:1628:1: ( '}' )
            // InternalMazeDsl.g:1629:2: '}'
            {
             before(grammarAccess.getGameConfigurationAccess().getRightCurlyBracketKeyword_9()); 
            match(input,69,FOLLOW_2); 
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
    // InternalMazeDsl.g:1639:1: rule__Import__Group__0 : rule__Import__Group__0__Impl rule__Import__Group__1 ;
    public final void rule__Import__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1643:1: ( rule__Import__Group__0__Impl rule__Import__Group__1 )
            // InternalMazeDsl.g:1644:2: rule__Import__Group__0__Impl rule__Import__Group__1
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
    // InternalMazeDsl.g:1651:1: rule__Import__Group__0__Impl : ( 'import' ) ;
    public final void rule__Import__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1655:1: ( ( 'import' ) )
            // InternalMazeDsl.g:1656:1: ( 'import' )
            {
            // InternalMazeDsl.g:1656:1: ( 'import' )
            // InternalMazeDsl.g:1657:2: 'import'
            {
             before(grammarAccess.getImportAccess().getImportKeyword_0()); 
            match(input,70,FOLLOW_2); 
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
    // InternalMazeDsl.g:1666:1: rule__Import__Group__1 : rule__Import__Group__1__Impl ;
    public final void rule__Import__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1670:1: ( rule__Import__Group__1__Impl )
            // InternalMazeDsl.g:1671:2: rule__Import__Group__1__Impl
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
    // InternalMazeDsl.g:1677:1: rule__Import__Group__1__Impl : ( ( rule__Import__ImportURIAssignment_1 ) ) ;
    public final void rule__Import__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1681:1: ( ( ( rule__Import__ImportURIAssignment_1 ) ) )
            // InternalMazeDsl.g:1682:1: ( ( rule__Import__ImportURIAssignment_1 ) )
            {
            // InternalMazeDsl.g:1682:1: ( ( rule__Import__ImportURIAssignment_1 ) )
            // InternalMazeDsl.g:1683:2: ( rule__Import__ImportURIAssignment_1 )
            {
             before(grammarAccess.getImportAccess().getImportURIAssignment_1()); 
            // InternalMazeDsl.g:1684:2: ( rule__Import__ImportURIAssignment_1 )
            // InternalMazeDsl.g:1684:3: rule__Import__ImportURIAssignment_1
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
    // InternalMazeDsl.g:1693:1: rule__DifficultyConfig__Group__0 : rule__DifficultyConfig__Group__0__Impl rule__DifficultyConfig__Group__1 ;
    public final void rule__DifficultyConfig__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1697:1: ( rule__DifficultyConfig__Group__0__Impl rule__DifficultyConfig__Group__1 )
            // InternalMazeDsl.g:1698:2: rule__DifficultyConfig__Group__0__Impl rule__DifficultyConfig__Group__1
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
    // InternalMazeDsl.g:1705:1: rule__DifficultyConfig__Group__0__Impl : ( 'difficulty' ) ;
    public final void rule__DifficultyConfig__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1709:1: ( ( 'difficulty' ) )
            // InternalMazeDsl.g:1710:1: ( 'difficulty' )
            {
            // InternalMazeDsl.g:1710:1: ( 'difficulty' )
            // InternalMazeDsl.g:1711:2: 'difficulty'
            {
             before(grammarAccess.getDifficultyConfigAccess().getDifficultyKeyword_0()); 
            match(input,71,FOLLOW_2); 
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
    // InternalMazeDsl.g:1720:1: rule__DifficultyConfig__Group__1 : rule__DifficultyConfig__Group__1__Impl rule__DifficultyConfig__Group__2 ;
    public final void rule__DifficultyConfig__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1724:1: ( rule__DifficultyConfig__Group__1__Impl rule__DifficultyConfig__Group__2 )
            // InternalMazeDsl.g:1725:2: rule__DifficultyConfig__Group__1__Impl rule__DifficultyConfig__Group__2
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
    // InternalMazeDsl.g:1732:1: rule__DifficultyConfig__Group__1__Impl : ( '{' ) ;
    public final void rule__DifficultyConfig__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1736:1: ( ( '{' ) )
            // InternalMazeDsl.g:1737:1: ( '{' )
            {
            // InternalMazeDsl.g:1737:1: ( '{' )
            // InternalMazeDsl.g:1738:2: '{'
            {
             before(grammarAccess.getDifficultyConfigAccess().getLeftCurlyBracketKeyword_1()); 
            match(input,68,FOLLOW_2); 
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
    // InternalMazeDsl.g:1747:1: rule__DifficultyConfig__Group__2 : rule__DifficultyConfig__Group__2__Impl rule__DifficultyConfig__Group__3 ;
    public final void rule__DifficultyConfig__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1751:1: ( rule__DifficultyConfig__Group__2__Impl rule__DifficultyConfig__Group__3 )
            // InternalMazeDsl.g:1752:2: rule__DifficultyConfig__Group__2__Impl rule__DifficultyConfig__Group__3
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
    // InternalMazeDsl.g:1759:1: rule__DifficultyConfig__Group__2__Impl : ( 'level' ) ;
    public final void rule__DifficultyConfig__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1763:1: ( ( 'level' ) )
            // InternalMazeDsl.g:1764:1: ( 'level' )
            {
            // InternalMazeDsl.g:1764:1: ( 'level' )
            // InternalMazeDsl.g:1765:2: 'level'
            {
             before(grammarAccess.getDifficultyConfigAccess().getLevelKeyword_2()); 
            match(input,72,FOLLOW_2); 
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
    // InternalMazeDsl.g:1774:1: rule__DifficultyConfig__Group__3 : rule__DifficultyConfig__Group__3__Impl rule__DifficultyConfig__Group__4 ;
    public final void rule__DifficultyConfig__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1778:1: ( rule__DifficultyConfig__Group__3__Impl rule__DifficultyConfig__Group__4 )
            // InternalMazeDsl.g:1779:2: rule__DifficultyConfig__Group__3__Impl rule__DifficultyConfig__Group__4
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
    // InternalMazeDsl.g:1786:1: rule__DifficultyConfig__Group__3__Impl : ( ( rule__DifficultyConfig__LevelAssignment_3 ) ) ;
    public final void rule__DifficultyConfig__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1790:1: ( ( ( rule__DifficultyConfig__LevelAssignment_3 ) ) )
            // InternalMazeDsl.g:1791:1: ( ( rule__DifficultyConfig__LevelAssignment_3 ) )
            {
            // InternalMazeDsl.g:1791:1: ( ( rule__DifficultyConfig__LevelAssignment_3 ) )
            // InternalMazeDsl.g:1792:2: ( rule__DifficultyConfig__LevelAssignment_3 )
            {
             before(grammarAccess.getDifficultyConfigAccess().getLevelAssignment_3()); 
            // InternalMazeDsl.g:1793:2: ( rule__DifficultyConfig__LevelAssignment_3 )
            // InternalMazeDsl.g:1793:3: rule__DifficultyConfig__LevelAssignment_3
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
    // InternalMazeDsl.g:1801:1: rule__DifficultyConfig__Group__4 : rule__DifficultyConfig__Group__4__Impl rule__DifficultyConfig__Group__5 ;
    public final void rule__DifficultyConfig__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1805:1: ( rule__DifficultyConfig__Group__4__Impl rule__DifficultyConfig__Group__5 )
            // InternalMazeDsl.g:1806:2: rule__DifficultyConfig__Group__4__Impl rule__DifficultyConfig__Group__5
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
    // InternalMazeDsl.g:1813:1: rule__DifficultyConfig__Group__4__Impl : ( ( rule__DifficultyConfig__Group_4__0 )? ) ;
    public final void rule__DifficultyConfig__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1817:1: ( ( ( rule__DifficultyConfig__Group_4__0 )? ) )
            // InternalMazeDsl.g:1818:1: ( ( rule__DifficultyConfig__Group_4__0 )? )
            {
            // InternalMazeDsl.g:1818:1: ( ( rule__DifficultyConfig__Group_4__0 )? )
            // InternalMazeDsl.g:1819:2: ( rule__DifficultyConfig__Group_4__0 )?
            {
             before(grammarAccess.getDifficultyConfigAccess().getGroup_4()); 
            // InternalMazeDsl.g:1820:2: ( rule__DifficultyConfig__Group_4__0 )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==73) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // InternalMazeDsl.g:1820:3: rule__DifficultyConfig__Group_4__0
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
    // InternalMazeDsl.g:1828:1: rule__DifficultyConfig__Group__5 : rule__DifficultyConfig__Group__5__Impl rule__DifficultyConfig__Group__6 ;
    public final void rule__DifficultyConfig__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1832:1: ( rule__DifficultyConfig__Group__5__Impl rule__DifficultyConfig__Group__6 )
            // InternalMazeDsl.g:1833:2: rule__DifficultyConfig__Group__5__Impl rule__DifficultyConfig__Group__6
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
    // InternalMazeDsl.g:1840:1: rule__DifficultyConfig__Group__5__Impl : ( ( rule__DifficultyConfig__Group_5__0 )? ) ;
    public final void rule__DifficultyConfig__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1844:1: ( ( ( rule__DifficultyConfig__Group_5__0 )? ) )
            // InternalMazeDsl.g:1845:1: ( ( rule__DifficultyConfig__Group_5__0 )? )
            {
            // InternalMazeDsl.g:1845:1: ( ( rule__DifficultyConfig__Group_5__0 )? )
            // InternalMazeDsl.g:1846:2: ( rule__DifficultyConfig__Group_5__0 )?
            {
             before(grammarAccess.getDifficultyConfigAccess().getGroup_5()); 
            // InternalMazeDsl.g:1847:2: ( rule__DifficultyConfig__Group_5__0 )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==74) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // InternalMazeDsl.g:1847:3: rule__DifficultyConfig__Group_5__0
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
    // InternalMazeDsl.g:1855:1: rule__DifficultyConfig__Group__6 : rule__DifficultyConfig__Group__6__Impl rule__DifficultyConfig__Group__7 ;
    public final void rule__DifficultyConfig__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1859:1: ( rule__DifficultyConfig__Group__6__Impl rule__DifficultyConfig__Group__7 )
            // InternalMazeDsl.g:1860:2: rule__DifficultyConfig__Group__6__Impl rule__DifficultyConfig__Group__7
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
    // InternalMazeDsl.g:1867:1: rule__DifficultyConfig__Group__6__Impl : ( ( rule__DifficultyConfig__Group_6__0 )? ) ;
    public final void rule__DifficultyConfig__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1871:1: ( ( ( rule__DifficultyConfig__Group_6__0 )? ) )
            // InternalMazeDsl.g:1872:1: ( ( rule__DifficultyConfig__Group_6__0 )? )
            {
            // InternalMazeDsl.g:1872:1: ( ( rule__DifficultyConfig__Group_6__0 )? )
            // InternalMazeDsl.g:1873:2: ( rule__DifficultyConfig__Group_6__0 )?
            {
             before(grammarAccess.getDifficultyConfigAccess().getGroup_6()); 
            // InternalMazeDsl.g:1874:2: ( rule__DifficultyConfig__Group_6__0 )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==75) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // InternalMazeDsl.g:1874:3: rule__DifficultyConfig__Group_6__0
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
    // InternalMazeDsl.g:1882:1: rule__DifficultyConfig__Group__7 : rule__DifficultyConfig__Group__7__Impl rule__DifficultyConfig__Group__8 ;
    public final void rule__DifficultyConfig__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1886:1: ( rule__DifficultyConfig__Group__7__Impl rule__DifficultyConfig__Group__8 )
            // InternalMazeDsl.g:1887:2: rule__DifficultyConfig__Group__7__Impl rule__DifficultyConfig__Group__8
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
    // InternalMazeDsl.g:1894:1: rule__DifficultyConfig__Group__7__Impl : ( ( rule__DifficultyConfig__Group_7__0 )? ) ;
    public final void rule__DifficultyConfig__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1898:1: ( ( ( rule__DifficultyConfig__Group_7__0 )? ) )
            // InternalMazeDsl.g:1899:1: ( ( rule__DifficultyConfig__Group_7__0 )? )
            {
            // InternalMazeDsl.g:1899:1: ( ( rule__DifficultyConfig__Group_7__0 )? )
            // InternalMazeDsl.g:1900:2: ( rule__DifficultyConfig__Group_7__0 )?
            {
             before(grammarAccess.getDifficultyConfigAccess().getGroup_7()); 
            // InternalMazeDsl.g:1901:2: ( rule__DifficultyConfig__Group_7__0 )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==76) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // InternalMazeDsl.g:1901:3: rule__DifficultyConfig__Group_7__0
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
    // InternalMazeDsl.g:1909:1: rule__DifficultyConfig__Group__8 : rule__DifficultyConfig__Group__8__Impl rule__DifficultyConfig__Group__9 ;
    public final void rule__DifficultyConfig__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1913:1: ( rule__DifficultyConfig__Group__8__Impl rule__DifficultyConfig__Group__9 )
            // InternalMazeDsl.g:1914:2: rule__DifficultyConfig__Group__8__Impl rule__DifficultyConfig__Group__9
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
    // InternalMazeDsl.g:1921:1: rule__DifficultyConfig__Group__8__Impl : ( ( rule__DifficultyConfig__EnemyLimitsAssignment_8 )* ) ;
    public final void rule__DifficultyConfig__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1925:1: ( ( ( rule__DifficultyConfig__EnemyLimitsAssignment_8 )* ) )
            // InternalMazeDsl.g:1926:1: ( ( rule__DifficultyConfig__EnemyLimitsAssignment_8 )* )
            {
            // InternalMazeDsl.g:1926:1: ( ( rule__DifficultyConfig__EnemyLimitsAssignment_8 )* )
            // InternalMazeDsl.g:1927:2: ( rule__DifficultyConfig__EnemyLimitsAssignment_8 )*
            {
             before(grammarAccess.getDifficultyConfigAccess().getEnemyLimitsAssignment_8()); 
            // InternalMazeDsl.g:1928:2: ( rule__DifficultyConfig__EnemyLimitsAssignment_8 )*
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( (LA21_0==77) ) {
                    alt21=1;
                }


                switch (alt21) {
            	case 1 :
            	    // InternalMazeDsl.g:1928:3: rule__DifficultyConfig__EnemyLimitsAssignment_8
            	    {
            	    pushFollow(FOLLOW_15);
            	    rule__DifficultyConfig__EnemyLimitsAssignment_8();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop21;
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
    // InternalMazeDsl.g:1936:1: rule__DifficultyConfig__Group__9 : rule__DifficultyConfig__Group__9__Impl ;
    public final void rule__DifficultyConfig__Group__9() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1940:1: ( rule__DifficultyConfig__Group__9__Impl )
            // InternalMazeDsl.g:1941:2: rule__DifficultyConfig__Group__9__Impl
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
    // InternalMazeDsl.g:1947:1: rule__DifficultyConfig__Group__9__Impl : ( '}' ) ;
    public final void rule__DifficultyConfig__Group__9__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1951:1: ( ( '}' ) )
            // InternalMazeDsl.g:1952:1: ( '}' )
            {
            // InternalMazeDsl.g:1952:1: ( '}' )
            // InternalMazeDsl.g:1953:2: '}'
            {
             before(grammarAccess.getDifficultyConfigAccess().getRightCurlyBracketKeyword_9()); 
            match(input,69,FOLLOW_2); 
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
    // InternalMazeDsl.g:1963:1: rule__DifficultyConfig__Group_4__0 : rule__DifficultyConfig__Group_4__0__Impl rule__DifficultyConfig__Group_4__1 ;
    public final void rule__DifficultyConfig__Group_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1967:1: ( rule__DifficultyConfig__Group_4__0__Impl rule__DifficultyConfig__Group_4__1 )
            // InternalMazeDsl.g:1968:2: rule__DifficultyConfig__Group_4__0__Impl rule__DifficultyConfig__Group_4__1
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
    // InternalMazeDsl.g:1975:1: rule__DifficultyConfig__Group_4__0__Impl : ( 'instantDeath' ) ;
    public final void rule__DifficultyConfig__Group_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1979:1: ( ( 'instantDeath' ) )
            // InternalMazeDsl.g:1980:1: ( 'instantDeath' )
            {
            // InternalMazeDsl.g:1980:1: ( 'instantDeath' )
            // InternalMazeDsl.g:1981:2: 'instantDeath'
            {
             before(grammarAccess.getDifficultyConfigAccess().getInstantDeathKeyword_4_0()); 
            match(input,73,FOLLOW_2); 
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
    // InternalMazeDsl.g:1990:1: rule__DifficultyConfig__Group_4__1 : rule__DifficultyConfig__Group_4__1__Impl ;
    public final void rule__DifficultyConfig__Group_4__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1994:1: ( rule__DifficultyConfig__Group_4__1__Impl )
            // InternalMazeDsl.g:1995:2: rule__DifficultyConfig__Group_4__1__Impl
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
    // InternalMazeDsl.g:2001:1: rule__DifficultyConfig__Group_4__1__Impl : ( ( rule__DifficultyConfig__InstantDeathAssignment_4_1 ) ) ;
    public final void rule__DifficultyConfig__Group_4__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2005:1: ( ( ( rule__DifficultyConfig__InstantDeathAssignment_4_1 ) ) )
            // InternalMazeDsl.g:2006:1: ( ( rule__DifficultyConfig__InstantDeathAssignment_4_1 ) )
            {
            // InternalMazeDsl.g:2006:1: ( ( rule__DifficultyConfig__InstantDeathAssignment_4_1 ) )
            // InternalMazeDsl.g:2007:2: ( rule__DifficultyConfig__InstantDeathAssignment_4_1 )
            {
             before(grammarAccess.getDifficultyConfigAccess().getInstantDeathAssignment_4_1()); 
            // InternalMazeDsl.g:2008:2: ( rule__DifficultyConfig__InstantDeathAssignment_4_1 )
            // InternalMazeDsl.g:2008:3: rule__DifficultyConfig__InstantDeathAssignment_4_1
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
    // InternalMazeDsl.g:2017:1: rule__DifficultyConfig__Group_5__0 : rule__DifficultyConfig__Group_5__0__Impl rule__DifficultyConfig__Group_5__1 ;
    public final void rule__DifficultyConfig__Group_5__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2021:1: ( rule__DifficultyConfig__Group_5__0__Impl rule__DifficultyConfig__Group_5__1 )
            // InternalMazeDsl.g:2022:2: rule__DifficultyConfig__Group_5__0__Impl rule__DifficultyConfig__Group_5__1
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
    // InternalMazeDsl.g:2029:1: rule__DifficultyConfig__Group_5__0__Impl : ( 'speedMultiplier' ) ;
    public final void rule__DifficultyConfig__Group_5__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2033:1: ( ( 'speedMultiplier' ) )
            // InternalMazeDsl.g:2034:1: ( 'speedMultiplier' )
            {
            // InternalMazeDsl.g:2034:1: ( 'speedMultiplier' )
            // InternalMazeDsl.g:2035:2: 'speedMultiplier'
            {
             before(grammarAccess.getDifficultyConfigAccess().getSpeedMultiplierKeyword_5_0()); 
            match(input,74,FOLLOW_2); 
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
    // InternalMazeDsl.g:2044:1: rule__DifficultyConfig__Group_5__1 : rule__DifficultyConfig__Group_5__1__Impl ;
    public final void rule__DifficultyConfig__Group_5__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2048:1: ( rule__DifficultyConfig__Group_5__1__Impl )
            // InternalMazeDsl.g:2049:2: rule__DifficultyConfig__Group_5__1__Impl
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
    // InternalMazeDsl.g:2055:1: rule__DifficultyConfig__Group_5__1__Impl : ( ( rule__DifficultyConfig__SpeedMultiplierAssignment_5_1 ) ) ;
    public final void rule__DifficultyConfig__Group_5__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2059:1: ( ( ( rule__DifficultyConfig__SpeedMultiplierAssignment_5_1 ) ) )
            // InternalMazeDsl.g:2060:1: ( ( rule__DifficultyConfig__SpeedMultiplierAssignment_5_1 ) )
            {
            // InternalMazeDsl.g:2060:1: ( ( rule__DifficultyConfig__SpeedMultiplierAssignment_5_1 ) )
            // InternalMazeDsl.g:2061:2: ( rule__DifficultyConfig__SpeedMultiplierAssignment_5_1 )
            {
             before(grammarAccess.getDifficultyConfigAccess().getSpeedMultiplierAssignment_5_1()); 
            // InternalMazeDsl.g:2062:2: ( rule__DifficultyConfig__SpeedMultiplierAssignment_5_1 )
            // InternalMazeDsl.g:2062:3: rule__DifficultyConfig__SpeedMultiplierAssignment_5_1
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
    // InternalMazeDsl.g:2071:1: rule__DifficultyConfig__Group_6__0 : rule__DifficultyConfig__Group_6__0__Impl rule__DifficultyConfig__Group_6__1 ;
    public final void rule__DifficultyConfig__Group_6__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2075:1: ( rule__DifficultyConfig__Group_6__0__Impl rule__DifficultyConfig__Group_6__1 )
            // InternalMazeDsl.g:2076:2: rule__DifficultyConfig__Group_6__0__Impl rule__DifficultyConfig__Group_6__1
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
    // InternalMazeDsl.g:2083:1: rule__DifficultyConfig__Group_6__0__Impl : ( 'damageMultiplier' ) ;
    public final void rule__DifficultyConfig__Group_6__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2087:1: ( ( 'damageMultiplier' ) )
            // InternalMazeDsl.g:2088:1: ( 'damageMultiplier' )
            {
            // InternalMazeDsl.g:2088:1: ( 'damageMultiplier' )
            // InternalMazeDsl.g:2089:2: 'damageMultiplier'
            {
             before(grammarAccess.getDifficultyConfigAccess().getDamageMultiplierKeyword_6_0()); 
            match(input,75,FOLLOW_2); 
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
    // InternalMazeDsl.g:2098:1: rule__DifficultyConfig__Group_6__1 : rule__DifficultyConfig__Group_6__1__Impl ;
    public final void rule__DifficultyConfig__Group_6__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2102:1: ( rule__DifficultyConfig__Group_6__1__Impl )
            // InternalMazeDsl.g:2103:2: rule__DifficultyConfig__Group_6__1__Impl
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
    // InternalMazeDsl.g:2109:1: rule__DifficultyConfig__Group_6__1__Impl : ( ( rule__DifficultyConfig__DamageMultiplierAssignment_6_1 ) ) ;
    public final void rule__DifficultyConfig__Group_6__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2113:1: ( ( ( rule__DifficultyConfig__DamageMultiplierAssignment_6_1 ) ) )
            // InternalMazeDsl.g:2114:1: ( ( rule__DifficultyConfig__DamageMultiplierAssignment_6_1 ) )
            {
            // InternalMazeDsl.g:2114:1: ( ( rule__DifficultyConfig__DamageMultiplierAssignment_6_1 ) )
            // InternalMazeDsl.g:2115:2: ( rule__DifficultyConfig__DamageMultiplierAssignment_6_1 )
            {
             before(grammarAccess.getDifficultyConfigAccess().getDamageMultiplierAssignment_6_1()); 
            // InternalMazeDsl.g:2116:2: ( rule__DifficultyConfig__DamageMultiplierAssignment_6_1 )
            // InternalMazeDsl.g:2116:3: rule__DifficultyConfig__DamageMultiplierAssignment_6_1
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
    // InternalMazeDsl.g:2125:1: rule__DifficultyConfig__Group_7__0 : rule__DifficultyConfig__Group_7__0__Impl rule__DifficultyConfig__Group_7__1 ;
    public final void rule__DifficultyConfig__Group_7__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2129:1: ( rule__DifficultyConfig__Group_7__0__Impl rule__DifficultyConfig__Group_7__1 )
            // InternalMazeDsl.g:2130:2: rule__DifficultyConfig__Group_7__0__Impl rule__DifficultyConfig__Group_7__1
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
    // InternalMazeDsl.g:2137:1: rule__DifficultyConfig__Group_7__0__Impl : ( 'maxThreat' ) ;
    public final void rule__DifficultyConfig__Group_7__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2141:1: ( ( 'maxThreat' ) )
            // InternalMazeDsl.g:2142:1: ( 'maxThreat' )
            {
            // InternalMazeDsl.g:2142:1: ( 'maxThreat' )
            // InternalMazeDsl.g:2143:2: 'maxThreat'
            {
             before(grammarAccess.getDifficultyConfigAccess().getMaxThreatKeyword_7_0()); 
            match(input,76,FOLLOW_2); 
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
    // InternalMazeDsl.g:2152:1: rule__DifficultyConfig__Group_7__1 : rule__DifficultyConfig__Group_7__1__Impl ;
    public final void rule__DifficultyConfig__Group_7__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2156:1: ( rule__DifficultyConfig__Group_7__1__Impl )
            // InternalMazeDsl.g:2157:2: rule__DifficultyConfig__Group_7__1__Impl
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
    // InternalMazeDsl.g:2163:1: rule__DifficultyConfig__Group_7__1__Impl : ( ( rule__DifficultyConfig__MaxThreatAssignment_7_1 ) ) ;
    public final void rule__DifficultyConfig__Group_7__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2167:1: ( ( ( rule__DifficultyConfig__MaxThreatAssignment_7_1 ) ) )
            // InternalMazeDsl.g:2168:1: ( ( rule__DifficultyConfig__MaxThreatAssignment_7_1 ) )
            {
            // InternalMazeDsl.g:2168:1: ( ( rule__DifficultyConfig__MaxThreatAssignment_7_1 ) )
            // InternalMazeDsl.g:2169:2: ( rule__DifficultyConfig__MaxThreatAssignment_7_1 )
            {
             before(grammarAccess.getDifficultyConfigAccess().getMaxThreatAssignment_7_1()); 
            // InternalMazeDsl.g:2170:2: ( rule__DifficultyConfig__MaxThreatAssignment_7_1 )
            // InternalMazeDsl.g:2170:3: rule__DifficultyConfig__MaxThreatAssignment_7_1
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
    // InternalMazeDsl.g:2179:1: rule__EnemyLimit__Group__0 : rule__EnemyLimit__Group__0__Impl rule__EnemyLimit__Group__1 ;
    public final void rule__EnemyLimit__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2183:1: ( rule__EnemyLimit__Group__0__Impl rule__EnemyLimit__Group__1 )
            // InternalMazeDsl.g:2184:2: rule__EnemyLimit__Group__0__Impl rule__EnemyLimit__Group__1
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
    // InternalMazeDsl.g:2191:1: rule__EnemyLimit__Group__0__Impl : ( 'limit' ) ;
    public final void rule__EnemyLimit__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2195:1: ( ( 'limit' ) )
            // InternalMazeDsl.g:2196:1: ( 'limit' )
            {
            // InternalMazeDsl.g:2196:1: ( 'limit' )
            // InternalMazeDsl.g:2197:2: 'limit'
            {
             before(grammarAccess.getEnemyLimitAccess().getLimitKeyword_0()); 
            match(input,77,FOLLOW_2); 
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
    // InternalMazeDsl.g:2206:1: rule__EnemyLimit__Group__1 : rule__EnemyLimit__Group__1__Impl rule__EnemyLimit__Group__2 ;
    public final void rule__EnemyLimit__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2210:1: ( rule__EnemyLimit__Group__1__Impl rule__EnemyLimit__Group__2 )
            // InternalMazeDsl.g:2211:2: rule__EnemyLimit__Group__1__Impl rule__EnemyLimit__Group__2
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
    // InternalMazeDsl.g:2218:1: rule__EnemyLimit__Group__1__Impl : ( ( rule__EnemyLimit__TypeAssignment_1 ) ) ;
    public final void rule__EnemyLimit__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2222:1: ( ( ( rule__EnemyLimit__TypeAssignment_1 ) ) )
            // InternalMazeDsl.g:2223:1: ( ( rule__EnemyLimit__TypeAssignment_1 ) )
            {
            // InternalMazeDsl.g:2223:1: ( ( rule__EnemyLimit__TypeAssignment_1 ) )
            // InternalMazeDsl.g:2224:2: ( rule__EnemyLimit__TypeAssignment_1 )
            {
             before(grammarAccess.getEnemyLimitAccess().getTypeAssignment_1()); 
            // InternalMazeDsl.g:2225:2: ( rule__EnemyLimit__TypeAssignment_1 )
            // InternalMazeDsl.g:2225:3: rule__EnemyLimit__TypeAssignment_1
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
    // InternalMazeDsl.g:2233:1: rule__EnemyLimit__Group__2 : rule__EnemyLimit__Group__2__Impl rule__EnemyLimit__Group__3 ;
    public final void rule__EnemyLimit__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2237:1: ( rule__EnemyLimit__Group__2__Impl rule__EnemyLimit__Group__3 )
            // InternalMazeDsl.g:2238:2: rule__EnemyLimit__Group__2__Impl rule__EnemyLimit__Group__3
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
    // InternalMazeDsl.g:2245:1: rule__EnemyLimit__Group__2__Impl : ( 'max' ) ;
    public final void rule__EnemyLimit__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2249:1: ( ( 'max' ) )
            // InternalMazeDsl.g:2250:1: ( 'max' )
            {
            // InternalMazeDsl.g:2250:1: ( 'max' )
            // InternalMazeDsl.g:2251:2: 'max'
            {
             before(grammarAccess.getEnemyLimitAccess().getMaxKeyword_2()); 
            match(input,78,FOLLOW_2); 
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
    // InternalMazeDsl.g:2260:1: rule__EnemyLimit__Group__3 : rule__EnemyLimit__Group__3__Impl ;
    public final void rule__EnemyLimit__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2264:1: ( rule__EnemyLimit__Group__3__Impl )
            // InternalMazeDsl.g:2265:2: rule__EnemyLimit__Group__3__Impl
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
    // InternalMazeDsl.g:2271:1: rule__EnemyLimit__Group__3__Impl : ( ( rule__EnemyLimit__MaxCountAssignment_3 ) ) ;
    public final void rule__EnemyLimit__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2275:1: ( ( ( rule__EnemyLimit__MaxCountAssignment_3 ) ) )
            // InternalMazeDsl.g:2276:1: ( ( rule__EnemyLimit__MaxCountAssignment_3 ) )
            {
            // InternalMazeDsl.g:2276:1: ( ( rule__EnemyLimit__MaxCountAssignment_3 ) )
            // InternalMazeDsl.g:2277:2: ( rule__EnemyLimit__MaxCountAssignment_3 )
            {
             before(grammarAccess.getEnemyLimitAccess().getMaxCountAssignment_3()); 
            // InternalMazeDsl.g:2278:2: ( rule__EnemyLimit__MaxCountAssignment_3 )
            // InternalMazeDsl.g:2278:3: rule__EnemyLimit__MaxCountAssignment_3
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
    // InternalMazeDsl.g:2287:1: rule__OpponentConfig__Group__0 : rule__OpponentConfig__Group__0__Impl rule__OpponentConfig__Group__1 ;
    public final void rule__OpponentConfig__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2291:1: ( rule__OpponentConfig__Group__0__Impl rule__OpponentConfig__Group__1 )
            // InternalMazeDsl.g:2292:2: rule__OpponentConfig__Group__0__Impl rule__OpponentConfig__Group__1
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
    // InternalMazeDsl.g:2299:1: rule__OpponentConfig__Group__0__Impl : ( 'opponent' ) ;
    public final void rule__OpponentConfig__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2303:1: ( ( 'opponent' ) )
            // InternalMazeDsl.g:2304:1: ( 'opponent' )
            {
            // InternalMazeDsl.g:2304:1: ( 'opponent' )
            // InternalMazeDsl.g:2305:2: 'opponent'
            {
             before(grammarAccess.getOpponentConfigAccess().getOpponentKeyword_0()); 
            match(input,79,FOLLOW_2); 
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
    // InternalMazeDsl.g:2314:1: rule__OpponentConfig__Group__1 : rule__OpponentConfig__Group__1__Impl rule__OpponentConfig__Group__2 ;
    public final void rule__OpponentConfig__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2318:1: ( rule__OpponentConfig__Group__1__Impl rule__OpponentConfig__Group__2 )
            // InternalMazeDsl.g:2319:2: rule__OpponentConfig__Group__1__Impl rule__OpponentConfig__Group__2
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
    // InternalMazeDsl.g:2326:1: rule__OpponentConfig__Group__1__Impl : ( ( rule__OpponentConfig__NameAssignment_1 ) ) ;
    public final void rule__OpponentConfig__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2330:1: ( ( ( rule__OpponentConfig__NameAssignment_1 ) ) )
            // InternalMazeDsl.g:2331:1: ( ( rule__OpponentConfig__NameAssignment_1 ) )
            {
            // InternalMazeDsl.g:2331:1: ( ( rule__OpponentConfig__NameAssignment_1 ) )
            // InternalMazeDsl.g:2332:2: ( rule__OpponentConfig__NameAssignment_1 )
            {
             before(grammarAccess.getOpponentConfigAccess().getNameAssignment_1()); 
            // InternalMazeDsl.g:2333:2: ( rule__OpponentConfig__NameAssignment_1 )
            // InternalMazeDsl.g:2333:3: rule__OpponentConfig__NameAssignment_1
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
    // InternalMazeDsl.g:2341:1: rule__OpponentConfig__Group__2 : rule__OpponentConfig__Group__2__Impl rule__OpponentConfig__Group__3 ;
    public final void rule__OpponentConfig__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2345:1: ( rule__OpponentConfig__Group__2__Impl rule__OpponentConfig__Group__3 )
            // InternalMazeDsl.g:2346:2: rule__OpponentConfig__Group__2__Impl rule__OpponentConfig__Group__3
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
    // InternalMazeDsl.g:2353:1: rule__OpponentConfig__Group__2__Impl : ( '{' ) ;
    public final void rule__OpponentConfig__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2357:1: ( ( '{' ) )
            // InternalMazeDsl.g:2358:1: ( '{' )
            {
            // InternalMazeDsl.g:2358:1: ( '{' )
            // InternalMazeDsl.g:2359:2: '{'
            {
             before(grammarAccess.getOpponentConfigAccess().getLeftCurlyBracketKeyword_2()); 
            match(input,68,FOLLOW_2); 
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
    // InternalMazeDsl.g:2368:1: rule__OpponentConfig__Group__3 : rule__OpponentConfig__Group__3__Impl rule__OpponentConfig__Group__4 ;
    public final void rule__OpponentConfig__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2372:1: ( rule__OpponentConfig__Group__3__Impl rule__OpponentConfig__Group__4 )
            // InternalMazeDsl.g:2373:2: rule__OpponentConfig__Group__3__Impl rule__OpponentConfig__Group__4
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
    // InternalMazeDsl.g:2380:1: rule__OpponentConfig__Group__3__Impl : ( 'type' ) ;
    public final void rule__OpponentConfig__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2384:1: ( ( 'type' ) )
            // InternalMazeDsl.g:2385:1: ( 'type' )
            {
            // InternalMazeDsl.g:2385:1: ( 'type' )
            // InternalMazeDsl.g:2386:2: 'type'
            {
             before(grammarAccess.getOpponentConfigAccess().getTypeKeyword_3()); 
            match(input,80,FOLLOW_2); 
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
    // InternalMazeDsl.g:2395:1: rule__OpponentConfig__Group__4 : rule__OpponentConfig__Group__4__Impl rule__OpponentConfig__Group__5 ;
    public final void rule__OpponentConfig__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2399:1: ( rule__OpponentConfig__Group__4__Impl rule__OpponentConfig__Group__5 )
            // InternalMazeDsl.g:2400:2: rule__OpponentConfig__Group__4__Impl rule__OpponentConfig__Group__5
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
    // InternalMazeDsl.g:2407:1: rule__OpponentConfig__Group__4__Impl : ( ( rule__OpponentConfig__TypeAssignment_4 ) ) ;
    public final void rule__OpponentConfig__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2411:1: ( ( ( rule__OpponentConfig__TypeAssignment_4 ) ) )
            // InternalMazeDsl.g:2412:1: ( ( rule__OpponentConfig__TypeAssignment_4 ) )
            {
            // InternalMazeDsl.g:2412:1: ( ( rule__OpponentConfig__TypeAssignment_4 ) )
            // InternalMazeDsl.g:2413:2: ( rule__OpponentConfig__TypeAssignment_4 )
            {
             before(grammarAccess.getOpponentConfigAccess().getTypeAssignment_4()); 
            // InternalMazeDsl.g:2414:2: ( rule__OpponentConfig__TypeAssignment_4 )
            // InternalMazeDsl.g:2414:3: rule__OpponentConfig__TypeAssignment_4
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
    // InternalMazeDsl.g:2422:1: rule__OpponentConfig__Group__5 : rule__OpponentConfig__Group__5__Impl rule__OpponentConfig__Group__6 ;
    public final void rule__OpponentConfig__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2426:1: ( rule__OpponentConfig__Group__5__Impl rule__OpponentConfig__Group__6 )
            // InternalMazeDsl.g:2427:2: rule__OpponentConfig__Group__5__Impl rule__OpponentConfig__Group__6
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
    // InternalMazeDsl.g:2434:1: rule__OpponentConfig__Group__5__Impl : ( ( rule__OpponentConfig__Group_5__0 )? ) ;
    public final void rule__OpponentConfig__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2438:1: ( ( ( rule__OpponentConfig__Group_5__0 )? ) )
            // InternalMazeDsl.g:2439:1: ( ( rule__OpponentConfig__Group_5__0 )? )
            {
            // InternalMazeDsl.g:2439:1: ( ( rule__OpponentConfig__Group_5__0 )? )
            // InternalMazeDsl.g:2440:2: ( rule__OpponentConfig__Group_5__0 )?
            {
             before(grammarAccess.getOpponentConfigAccess().getGroup_5()); 
            // InternalMazeDsl.g:2441:2: ( rule__OpponentConfig__Group_5__0 )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==81) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // InternalMazeDsl.g:2441:3: rule__OpponentConfig__Group_5__0
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
    // InternalMazeDsl.g:2449:1: rule__OpponentConfig__Group__6 : rule__OpponentConfig__Group__6__Impl rule__OpponentConfig__Group__7 ;
    public final void rule__OpponentConfig__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2453:1: ( rule__OpponentConfig__Group__6__Impl rule__OpponentConfig__Group__7 )
            // InternalMazeDsl.g:2454:2: rule__OpponentConfig__Group__6__Impl rule__OpponentConfig__Group__7
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
    // InternalMazeDsl.g:2461:1: rule__OpponentConfig__Group__6__Impl : ( ( rule__OpponentConfig__Group_6__0 )? ) ;
    public final void rule__OpponentConfig__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2465:1: ( ( ( rule__OpponentConfig__Group_6__0 )? ) )
            // InternalMazeDsl.g:2466:1: ( ( rule__OpponentConfig__Group_6__0 )? )
            {
            // InternalMazeDsl.g:2466:1: ( ( rule__OpponentConfig__Group_6__0 )? )
            // InternalMazeDsl.g:2467:2: ( rule__OpponentConfig__Group_6__0 )?
            {
             before(grammarAccess.getOpponentConfigAccess().getGroup_6()); 
            // InternalMazeDsl.g:2468:2: ( rule__OpponentConfig__Group_6__0 )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==82) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // InternalMazeDsl.g:2468:3: rule__OpponentConfig__Group_6__0
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
    // InternalMazeDsl.g:2476:1: rule__OpponentConfig__Group__7 : rule__OpponentConfig__Group__7__Impl rule__OpponentConfig__Group__8 ;
    public final void rule__OpponentConfig__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2480:1: ( rule__OpponentConfig__Group__7__Impl rule__OpponentConfig__Group__8 )
            // InternalMazeDsl.g:2481:2: rule__OpponentConfig__Group__7__Impl rule__OpponentConfig__Group__8
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
    // InternalMazeDsl.g:2488:1: rule__OpponentConfig__Group__7__Impl : ( ( rule__OpponentConfig__Group_7__0 )? ) ;
    public final void rule__OpponentConfig__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2492:1: ( ( ( rule__OpponentConfig__Group_7__0 )? ) )
            // InternalMazeDsl.g:2493:1: ( ( rule__OpponentConfig__Group_7__0 )? )
            {
            // InternalMazeDsl.g:2493:1: ( ( rule__OpponentConfig__Group_7__0 )? )
            // InternalMazeDsl.g:2494:2: ( rule__OpponentConfig__Group_7__0 )?
            {
             before(grammarAccess.getOpponentConfigAccess().getGroup_7()); 
            // InternalMazeDsl.g:2495:2: ( rule__OpponentConfig__Group_7__0 )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==83) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // InternalMazeDsl.g:2495:3: rule__OpponentConfig__Group_7__0
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
    // InternalMazeDsl.g:2503:1: rule__OpponentConfig__Group__8 : rule__OpponentConfig__Group__8__Impl rule__OpponentConfig__Group__9 ;
    public final void rule__OpponentConfig__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2507:1: ( rule__OpponentConfig__Group__8__Impl rule__OpponentConfig__Group__9 )
            // InternalMazeDsl.g:2508:2: rule__OpponentConfig__Group__8__Impl rule__OpponentConfig__Group__9
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
    // InternalMazeDsl.g:2515:1: rule__OpponentConfig__Group__8__Impl : ( ( rule__OpponentConfig__Group_8__0 )? ) ;
    public final void rule__OpponentConfig__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2519:1: ( ( ( rule__OpponentConfig__Group_8__0 )? ) )
            // InternalMazeDsl.g:2520:1: ( ( rule__OpponentConfig__Group_8__0 )? )
            {
            // InternalMazeDsl.g:2520:1: ( ( rule__OpponentConfig__Group_8__0 )? )
            // InternalMazeDsl.g:2521:2: ( rule__OpponentConfig__Group_8__0 )?
            {
             before(grammarAccess.getOpponentConfigAccess().getGroup_8()); 
            // InternalMazeDsl.g:2522:2: ( rule__OpponentConfig__Group_8__0 )?
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==84) ) {
                alt25=1;
            }
            switch (alt25) {
                case 1 :
                    // InternalMazeDsl.g:2522:3: rule__OpponentConfig__Group_8__0
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
    // InternalMazeDsl.g:2530:1: rule__OpponentConfig__Group__9 : rule__OpponentConfig__Group__9__Impl rule__OpponentConfig__Group__10 ;
    public final void rule__OpponentConfig__Group__9() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2534:1: ( rule__OpponentConfig__Group__9__Impl rule__OpponentConfig__Group__10 )
            // InternalMazeDsl.g:2535:2: rule__OpponentConfig__Group__9__Impl rule__OpponentConfig__Group__10
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
    // InternalMazeDsl.g:2542:1: rule__OpponentConfig__Group__9__Impl : ( ( rule__OpponentConfig__Group_9__0 )? ) ;
    public final void rule__OpponentConfig__Group__9__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2546:1: ( ( ( rule__OpponentConfig__Group_9__0 )? ) )
            // InternalMazeDsl.g:2547:1: ( ( rule__OpponentConfig__Group_9__0 )? )
            {
            // InternalMazeDsl.g:2547:1: ( ( rule__OpponentConfig__Group_9__0 )? )
            // InternalMazeDsl.g:2548:2: ( rule__OpponentConfig__Group_9__0 )?
            {
             before(grammarAccess.getOpponentConfigAccess().getGroup_9()); 
            // InternalMazeDsl.g:2549:2: ( rule__OpponentConfig__Group_9__0 )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==85) ) {
                alt26=1;
            }
            switch (alt26) {
                case 1 :
                    // InternalMazeDsl.g:2549:3: rule__OpponentConfig__Group_9__0
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
    // InternalMazeDsl.g:2557:1: rule__OpponentConfig__Group__10 : rule__OpponentConfig__Group__10__Impl rule__OpponentConfig__Group__11 ;
    public final void rule__OpponentConfig__Group__10() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2561:1: ( rule__OpponentConfig__Group__10__Impl rule__OpponentConfig__Group__11 )
            // InternalMazeDsl.g:2562:2: rule__OpponentConfig__Group__10__Impl rule__OpponentConfig__Group__11
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
    // InternalMazeDsl.g:2569:1: rule__OpponentConfig__Group__10__Impl : ( ( rule__OpponentConfig__Group_10__0 )? ) ;
    public final void rule__OpponentConfig__Group__10__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2573:1: ( ( ( rule__OpponentConfig__Group_10__0 )? ) )
            // InternalMazeDsl.g:2574:1: ( ( rule__OpponentConfig__Group_10__0 )? )
            {
            // InternalMazeDsl.g:2574:1: ( ( rule__OpponentConfig__Group_10__0 )? )
            // InternalMazeDsl.g:2575:2: ( rule__OpponentConfig__Group_10__0 )?
            {
             before(grammarAccess.getOpponentConfigAccess().getGroup_10()); 
            // InternalMazeDsl.g:2576:2: ( rule__OpponentConfig__Group_10__0 )?
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==86) ) {
                alt27=1;
            }
            switch (alt27) {
                case 1 :
                    // InternalMazeDsl.g:2576:3: rule__OpponentConfig__Group_10__0
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
    // InternalMazeDsl.g:2584:1: rule__OpponentConfig__Group__11 : rule__OpponentConfig__Group__11__Impl rule__OpponentConfig__Group__12 ;
    public final void rule__OpponentConfig__Group__11() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2588:1: ( rule__OpponentConfig__Group__11__Impl rule__OpponentConfig__Group__12 )
            // InternalMazeDsl.g:2589:2: rule__OpponentConfig__Group__11__Impl rule__OpponentConfig__Group__12
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
    // InternalMazeDsl.g:2596:1: rule__OpponentConfig__Group__11__Impl : ( ( rule__OpponentConfig__CharacterSpecificsAssignment_11 )? ) ;
    public final void rule__OpponentConfig__Group__11__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2600:1: ( ( ( rule__OpponentConfig__CharacterSpecificsAssignment_11 )? ) )
            // InternalMazeDsl.g:2601:1: ( ( rule__OpponentConfig__CharacterSpecificsAssignment_11 )? )
            {
            // InternalMazeDsl.g:2601:1: ( ( rule__OpponentConfig__CharacterSpecificsAssignment_11 )? )
            // InternalMazeDsl.g:2602:2: ( rule__OpponentConfig__CharacterSpecificsAssignment_11 )?
            {
             before(grammarAccess.getOpponentConfigAccess().getCharacterSpecificsAssignment_11()); 
            // InternalMazeDsl.g:2603:2: ( rule__OpponentConfig__CharacterSpecificsAssignment_11 )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==88||LA28_0==92||LA28_0==95) ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // InternalMazeDsl.g:2603:3: rule__OpponentConfig__CharacterSpecificsAssignment_11
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
    // InternalMazeDsl.g:2611:1: rule__OpponentConfig__Group__12 : rule__OpponentConfig__Group__12__Impl rule__OpponentConfig__Group__13 ;
    public final void rule__OpponentConfig__Group__12() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2615:1: ( rule__OpponentConfig__Group__12__Impl rule__OpponentConfig__Group__13 )
            // InternalMazeDsl.g:2616:2: rule__OpponentConfig__Group__12__Impl rule__OpponentConfig__Group__13
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
    // InternalMazeDsl.g:2623:1: rule__OpponentConfig__Group__12__Impl : ( ( rule__OpponentConfig__Group_12__0 )? ) ;
    public final void rule__OpponentConfig__Group__12__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2627:1: ( ( ( rule__OpponentConfig__Group_12__0 )? ) )
            // InternalMazeDsl.g:2628:1: ( ( rule__OpponentConfig__Group_12__0 )? )
            {
            // InternalMazeDsl.g:2628:1: ( ( rule__OpponentConfig__Group_12__0 )? )
            // InternalMazeDsl.g:2629:2: ( rule__OpponentConfig__Group_12__0 )?
            {
             before(grammarAccess.getOpponentConfigAccess().getGroup_12()); 
            // InternalMazeDsl.g:2630:2: ( rule__OpponentConfig__Group_12__0 )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==59) ) {
                alt29=1;
            }
            switch (alt29) {
                case 1 :
                    // InternalMazeDsl.g:2630:3: rule__OpponentConfig__Group_12__0
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
    // InternalMazeDsl.g:2638:1: rule__OpponentConfig__Group__13 : rule__OpponentConfig__Group__13__Impl rule__OpponentConfig__Group__14 ;
    public final void rule__OpponentConfig__Group__13() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2642:1: ( rule__OpponentConfig__Group__13__Impl rule__OpponentConfig__Group__14 )
            // InternalMazeDsl.g:2643:2: rule__OpponentConfig__Group__13__Impl rule__OpponentConfig__Group__14
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
    // InternalMazeDsl.g:2650:1: rule__OpponentConfig__Group__13__Impl : ( ( rule__OpponentConfig__Group_13__0 )? ) ;
    public final void rule__OpponentConfig__Group__13__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2654:1: ( ( ( rule__OpponentConfig__Group_13__0 )? ) )
            // InternalMazeDsl.g:2655:1: ( ( rule__OpponentConfig__Group_13__0 )? )
            {
            // InternalMazeDsl.g:2655:1: ( ( rule__OpponentConfig__Group_13__0 )? )
            // InternalMazeDsl.g:2656:2: ( rule__OpponentConfig__Group_13__0 )?
            {
             before(grammarAccess.getOpponentConfigAccess().getGroup_13()); 
            // InternalMazeDsl.g:2657:2: ( rule__OpponentConfig__Group_13__0 )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==87) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // InternalMazeDsl.g:2657:3: rule__OpponentConfig__Group_13__0
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
    // InternalMazeDsl.g:2665:1: rule__OpponentConfig__Group__14 : rule__OpponentConfig__Group__14__Impl ;
    public final void rule__OpponentConfig__Group__14() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2669:1: ( rule__OpponentConfig__Group__14__Impl )
            // InternalMazeDsl.g:2670:2: rule__OpponentConfig__Group__14__Impl
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
    // InternalMazeDsl.g:2676:1: rule__OpponentConfig__Group__14__Impl : ( '}' ) ;
    public final void rule__OpponentConfig__Group__14__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2680:1: ( ( '}' ) )
            // InternalMazeDsl.g:2681:1: ( '}' )
            {
            // InternalMazeDsl.g:2681:1: ( '}' )
            // InternalMazeDsl.g:2682:2: '}'
            {
             before(grammarAccess.getOpponentConfigAccess().getRightCurlyBracketKeyword_14()); 
            match(input,69,FOLLOW_2); 
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
    // InternalMazeDsl.g:2692:1: rule__OpponentConfig__Group_5__0 : rule__OpponentConfig__Group_5__0__Impl rule__OpponentConfig__Group_5__1 ;
    public final void rule__OpponentConfig__Group_5__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2696:1: ( rule__OpponentConfig__Group_5__0__Impl rule__OpponentConfig__Group_5__1 )
            // InternalMazeDsl.g:2697:2: rule__OpponentConfig__Group_5__0__Impl rule__OpponentConfig__Group_5__1
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
    // InternalMazeDsl.g:2704:1: rule__OpponentConfig__Group_5__0__Impl : ( 'displayName' ) ;
    public final void rule__OpponentConfig__Group_5__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2708:1: ( ( 'displayName' ) )
            // InternalMazeDsl.g:2709:1: ( 'displayName' )
            {
            // InternalMazeDsl.g:2709:1: ( 'displayName' )
            // InternalMazeDsl.g:2710:2: 'displayName'
            {
             before(grammarAccess.getOpponentConfigAccess().getDisplayNameKeyword_5_0()); 
            match(input,81,FOLLOW_2); 
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
    // InternalMazeDsl.g:2719:1: rule__OpponentConfig__Group_5__1 : rule__OpponentConfig__Group_5__1__Impl ;
    public final void rule__OpponentConfig__Group_5__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2723:1: ( rule__OpponentConfig__Group_5__1__Impl )
            // InternalMazeDsl.g:2724:2: rule__OpponentConfig__Group_5__1__Impl
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
    // InternalMazeDsl.g:2730:1: rule__OpponentConfig__Group_5__1__Impl : ( ( rule__OpponentConfig__DisplayNameAssignment_5_1 ) ) ;
    public final void rule__OpponentConfig__Group_5__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2734:1: ( ( ( rule__OpponentConfig__DisplayNameAssignment_5_1 ) ) )
            // InternalMazeDsl.g:2735:1: ( ( rule__OpponentConfig__DisplayNameAssignment_5_1 ) )
            {
            // InternalMazeDsl.g:2735:1: ( ( rule__OpponentConfig__DisplayNameAssignment_5_1 ) )
            // InternalMazeDsl.g:2736:2: ( rule__OpponentConfig__DisplayNameAssignment_5_1 )
            {
             before(grammarAccess.getOpponentConfigAccess().getDisplayNameAssignment_5_1()); 
            // InternalMazeDsl.g:2737:2: ( rule__OpponentConfig__DisplayNameAssignment_5_1 )
            // InternalMazeDsl.g:2737:3: rule__OpponentConfig__DisplayNameAssignment_5_1
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
    // InternalMazeDsl.g:2746:1: rule__OpponentConfig__Group_6__0 : rule__OpponentConfig__Group_6__0__Impl rule__OpponentConfig__Group_6__1 ;
    public final void rule__OpponentConfig__Group_6__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2750:1: ( rule__OpponentConfig__Group_6__0__Impl rule__OpponentConfig__Group_6__1 )
            // InternalMazeDsl.g:2751:2: rule__OpponentConfig__Group_6__0__Impl rule__OpponentConfig__Group_6__1
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
    // InternalMazeDsl.g:2758:1: rule__OpponentConfig__Group_6__0__Impl : ( 'health' ) ;
    public final void rule__OpponentConfig__Group_6__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2762:1: ( ( 'health' ) )
            // InternalMazeDsl.g:2763:1: ( 'health' )
            {
            // InternalMazeDsl.g:2763:1: ( 'health' )
            // InternalMazeDsl.g:2764:2: 'health'
            {
             before(grammarAccess.getOpponentConfigAccess().getHealthKeyword_6_0()); 
            match(input,82,FOLLOW_2); 
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
    // InternalMazeDsl.g:2773:1: rule__OpponentConfig__Group_6__1 : rule__OpponentConfig__Group_6__1__Impl ;
    public final void rule__OpponentConfig__Group_6__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2777:1: ( rule__OpponentConfig__Group_6__1__Impl )
            // InternalMazeDsl.g:2778:2: rule__OpponentConfig__Group_6__1__Impl
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
    // InternalMazeDsl.g:2784:1: rule__OpponentConfig__Group_6__1__Impl : ( ( rule__OpponentConfig__HealthAssignment_6_1 ) ) ;
    public final void rule__OpponentConfig__Group_6__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2788:1: ( ( ( rule__OpponentConfig__HealthAssignment_6_1 ) ) )
            // InternalMazeDsl.g:2789:1: ( ( rule__OpponentConfig__HealthAssignment_6_1 ) )
            {
            // InternalMazeDsl.g:2789:1: ( ( rule__OpponentConfig__HealthAssignment_6_1 ) )
            // InternalMazeDsl.g:2790:2: ( rule__OpponentConfig__HealthAssignment_6_1 )
            {
             before(grammarAccess.getOpponentConfigAccess().getHealthAssignment_6_1()); 
            // InternalMazeDsl.g:2791:2: ( rule__OpponentConfig__HealthAssignment_6_1 )
            // InternalMazeDsl.g:2791:3: rule__OpponentConfig__HealthAssignment_6_1
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
    // InternalMazeDsl.g:2800:1: rule__OpponentConfig__Group_7__0 : rule__OpponentConfig__Group_7__0__Impl rule__OpponentConfig__Group_7__1 ;
    public final void rule__OpponentConfig__Group_7__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2804:1: ( rule__OpponentConfig__Group_7__0__Impl rule__OpponentConfig__Group_7__1 )
            // InternalMazeDsl.g:2805:2: rule__OpponentConfig__Group_7__0__Impl rule__OpponentConfig__Group_7__1
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
    // InternalMazeDsl.g:2812:1: rule__OpponentConfig__Group_7__0__Impl : ( 'speed' ) ;
    public final void rule__OpponentConfig__Group_7__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2816:1: ( ( 'speed' ) )
            // InternalMazeDsl.g:2817:1: ( 'speed' )
            {
            // InternalMazeDsl.g:2817:1: ( 'speed' )
            // InternalMazeDsl.g:2818:2: 'speed'
            {
             before(grammarAccess.getOpponentConfigAccess().getSpeedKeyword_7_0()); 
            match(input,83,FOLLOW_2); 
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
    // InternalMazeDsl.g:2827:1: rule__OpponentConfig__Group_7__1 : rule__OpponentConfig__Group_7__1__Impl ;
    public final void rule__OpponentConfig__Group_7__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2831:1: ( rule__OpponentConfig__Group_7__1__Impl )
            // InternalMazeDsl.g:2832:2: rule__OpponentConfig__Group_7__1__Impl
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
    // InternalMazeDsl.g:2838:1: rule__OpponentConfig__Group_7__1__Impl : ( ( rule__OpponentConfig__SpeedAssignment_7_1 ) ) ;
    public final void rule__OpponentConfig__Group_7__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2842:1: ( ( ( rule__OpponentConfig__SpeedAssignment_7_1 ) ) )
            // InternalMazeDsl.g:2843:1: ( ( rule__OpponentConfig__SpeedAssignment_7_1 ) )
            {
            // InternalMazeDsl.g:2843:1: ( ( rule__OpponentConfig__SpeedAssignment_7_1 ) )
            // InternalMazeDsl.g:2844:2: ( rule__OpponentConfig__SpeedAssignment_7_1 )
            {
             before(grammarAccess.getOpponentConfigAccess().getSpeedAssignment_7_1()); 
            // InternalMazeDsl.g:2845:2: ( rule__OpponentConfig__SpeedAssignment_7_1 )
            // InternalMazeDsl.g:2845:3: rule__OpponentConfig__SpeedAssignment_7_1
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
    // InternalMazeDsl.g:2854:1: rule__OpponentConfig__Group_8__0 : rule__OpponentConfig__Group_8__0__Impl rule__OpponentConfig__Group_8__1 ;
    public final void rule__OpponentConfig__Group_8__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2858:1: ( rule__OpponentConfig__Group_8__0__Impl rule__OpponentConfig__Group_8__1 )
            // InternalMazeDsl.g:2859:2: rule__OpponentConfig__Group_8__0__Impl rule__OpponentConfig__Group_8__1
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
    // InternalMazeDsl.g:2866:1: rule__OpponentConfig__Group_8__0__Impl : ( 'threatLevel' ) ;
    public final void rule__OpponentConfig__Group_8__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2870:1: ( ( 'threatLevel' ) )
            // InternalMazeDsl.g:2871:1: ( 'threatLevel' )
            {
            // InternalMazeDsl.g:2871:1: ( 'threatLevel' )
            // InternalMazeDsl.g:2872:2: 'threatLevel'
            {
             before(grammarAccess.getOpponentConfigAccess().getThreatLevelKeyword_8_0()); 
            match(input,84,FOLLOW_2); 
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
    // InternalMazeDsl.g:2881:1: rule__OpponentConfig__Group_8__1 : rule__OpponentConfig__Group_8__1__Impl ;
    public final void rule__OpponentConfig__Group_8__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2885:1: ( rule__OpponentConfig__Group_8__1__Impl )
            // InternalMazeDsl.g:2886:2: rule__OpponentConfig__Group_8__1__Impl
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
    // InternalMazeDsl.g:2892:1: rule__OpponentConfig__Group_8__1__Impl : ( ( rule__OpponentConfig__ThreatLevelAssignment_8_1 ) ) ;
    public final void rule__OpponentConfig__Group_8__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2896:1: ( ( ( rule__OpponentConfig__ThreatLevelAssignment_8_1 ) ) )
            // InternalMazeDsl.g:2897:1: ( ( rule__OpponentConfig__ThreatLevelAssignment_8_1 ) )
            {
            // InternalMazeDsl.g:2897:1: ( ( rule__OpponentConfig__ThreatLevelAssignment_8_1 ) )
            // InternalMazeDsl.g:2898:2: ( rule__OpponentConfig__ThreatLevelAssignment_8_1 )
            {
             before(grammarAccess.getOpponentConfigAccess().getThreatLevelAssignment_8_1()); 
            // InternalMazeDsl.g:2899:2: ( rule__OpponentConfig__ThreatLevelAssignment_8_1 )
            // InternalMazeDsl.g:2899:3: rule__OpponentConfig__ThreatLevelAssignment_8_1
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
    // InternalMazeDsl.g:2908:1: rule__OpponentConfig__Group_9__0 : rule__OpponentConfig__Group_9__0__Impl rule__OpponentConfig__Group_9__1 ;
    public final void rule__OpponentConfig__Group_9__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2912:1: ( rule__OpponentConfig__Group_9__0__Impl rule__OpponentConfig__Group_9__1 )
            // InternalMazeDsl.g:2913:2: rule__OpponentConfig__Group_9__0__Impl rule__OpponentConfig__Group_9__1
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
    // InternalMazeDsl.g:2920:1: rule__OpponentConfig__Group_9__0__Impl : ( 'enabled' ) ;
    public final void rule__OpponentConfig__Group_9__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2924:1: ( ( 'enabled' ) )
            // InternalMazeDsl.g:2925:1: ( 'enabled' )
            {
            // InternalMazeDsl.g:2925:1: ( 'enabled' )
            // InternalMazeDsl.g:2926:2: 'enabled'
            {
             before(grammarAccess.getOpponentConfigAccess().getEnabledKeyword_9_0()); 
            match(input,85,FOLLOW_2); 
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
    // InternalMazeDsl.g:2935:1: rule__OpponentConfig__Group_9__1 : rule__OpponentConfig__Group_9__1__Impl ;
    public final void rule__OpponentConfig__Group_9__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2939:1: ( rule__OpponentConfig__Group_9__1__Impl )
            // InternalMazeDsl.g:2940:2: rule__OpponentConfig__Group_9__1__Impl
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
    // InternalMazeDsl.g:2946:1: rule__OpponentConfig__Group_9__1__Impl : ( ( rule__OpponentConfig__EnabledAssignment_9_1 ) ) ;
    public final void rule__OpponentConfig__Group_9__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2950:1: ( ( ( rule__OpponentConfig__EnabledAssignment_9_1 ) ) )
            // InternalMazeDsl.g:2951:1: ( ( rule__OpponentConfig__EnabledAssignment_9_1 ) )
            {
            // InternalMazeDsl.g:2951:1: ( ( rule__OpponentConfig__EnabledAssignment_9_1 ) )
            // InternalMazeDsl.g:2952:2: ( rule__OpponentConfig__EnabledAssignment_9_1 )
            {
             before(grammarAccess.getOpponentConfigAccess().getEnabledAssignment_9_1()); 
            // InternalMazeDsl.g:2953:2: ( rule__OpponentConfig__EnabledAssignment_9_1 )
            // InternalMazeDsl.g:2953:3: rule__OpponentConfig__EnabledAssignment_9_1
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
    // InternalMazeDsl.g:2962:1: rule__OpponentConfig__Group_10__0 : rule__OpponentConfig__Group_10__0__Impl rule__OpponentConfig__Group_10__1 ;
    public final void rule__OpponentConfig__Group_10__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2966:1: ( rule__OpponentConfig__Group_10__0__Impl rule__OpponentConfig__Group_10__1 )
            // InternalMazeDsl.g:2967:2: rule__OpponentConfig__Group_10__0__Impl rule__OpponentConfig__Group_10__1
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
    // InternalMazeDsl.g:2974:1: rule__OpponentConfig__Group_10__0__Impl : ( 'behavior' ) ;
    public final void rule__OpponentConfig__Group_10__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2978:1: ( ( 'behavior' ) )
            // InternalMazeDsl.g:2979:1: ( 'behavior' )
            {
            // InternalMazeDsl.g:2979:1: ( 'behavior' )
            // InternalMazeDsl.g:2980:2: 'behavior'
            {
             before(grammarAccess.getOpponentConfigAccess().getBehaviorKeyword_10_0()); 
            match(input,86,FOLLOW_2); 
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
    // InternalMazeDsl.g:2989:1: rule__OpponentConfig__Group_10__1 : rule__OpponentConfig__Group_10__1__Impl ;
    public final void rule__OpponentConfig__Group_10__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2993:1: ( rule__OpponentConfig__Group_10__1__Impl )
            // InternalMazeDsl.g:2994:2: rule__OpponentConfig__Group_10__1__Impl
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
    // InternalMazeDsl.g:3000:1: rule__OpponentConfig__Group_10__1__Impl : ( ( rule__OpponentConfig__BehaviorAssignment_10_1 ) ) ;
    public final void rule__OpponentConfig__Group_10__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3004:1: ( ( ( rule__OpponentConfig__BehaviorAssignment_10_1 ) ) )
            // InternalMazeDsl.g:3005:1: ( ( rule__OpponentConfig__BehaviorAssignment_10_1 ) )
            {
            // InternalMazeDsl.g:3005:1: ( ( rule__OpponentConfig__BehaviorAssignment_10_1 ) )
            // InternalMazeDsl.g:3006:2: ( rule__OpponentConfig__BehaviorAssignment_10_1 )
            {
             before(grammarAccess.getOpponentConfigAccess().getBehaviorAssignment_10_1()); 
            // InternalMazeDsl.g:3007:2: ( rule__OpponentConfig__BehaviorAssignment_10_1 )
            // InternalMazeDsl.g:3007:3: rule__OpponentConfig__BehaviorAssignment_10_1
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
    // InternalMazeDsl.g:3016:1: rule__OpponentConfig__Group_12__0 : rule__OpponentConfig__Group_12__0__Impl rule__OpponentConfig__Group_12__1 ;
    public final void rule__OpponentConfig__Group_12__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3020:1: ( rule__OpponentConfig__Group_12__0__Impl rule__OpponentConfig__Group_12__1 )
            // InternalMazeDsl.g:3021:2: rule__OpponentConfig__Group_12__0__Impl rule__OpponentConfig__Group_12__1
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
    // InternalMazeDsl.g:3028:1: rule__OpponentConfig__Group_12__0__Impl : ( 'patrol' ) ;
    public final void rule__OpponentConfig__Group_12__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3032:1: ( ( 'patrol' ) )
            // InternalMazeDsl.g:3033:1: ( 'patrol' )
            {
            // InternalMazeDsl.g:3033:1: ( 'patrol' )
            // InternalMazeDsl.g:3034:2: 'patrol'
            {
             before(grammarAccess.getOpponentConfigAccess().getPatrolKeyword_12_0()); 
            match(input,59,FOLLOW_2); 
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
    // InternalMazeDsl.g:3043:1: rule__OpponentConfig__Group_12__1 : rule__OpponentConfig__Group_12__1__Impl ;
    public final void rule__OpponentConfig__Group_12__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3047:1: ( rule__OpponentConfig__Group_12__1__Impl )
            // InternalMazeDsl.g:3048:2: rule__OpponentConfig__Group_12__1__Impl
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
    // InternalMazeDsl.g:3054:1: rule__OpponentConfig__Group_12__1__Impl : ( ( rule__OpponentConfig__PatrolRefAssignment_12_1 ) ) ;
    public final void rule__OpponentConfig__Group_12__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3058:1: ( ( ( rule__OpponentConfig__PatrolRefAssignment_12_1 ) ) )
            // InternalMazeDsl.g:3059:1: ( ( rule__OpponentConfig__PatrolRefAssignment_12_1 ) )
            {
            // InternalMazeDsl.g:3059:1: ( ( rule__OpponentConfig__PatrolRefAssignment_12_1 ) )
            // InternalMazeDsl.g:3060:2: ( rule__OpponentConfig__PatrolRefAssignment_12_1 )
            {
             before(grammarAccess.getOpponentConfigAccess().getPatrolRefAssignment_12_1()); 
            // InternalMazeDsl.g:3061:2: ( rule__OpponentConfig__PatrolRefAssignment_12_1 )
            // InternalMazeDsl.g:3061:3: rule__OpponentConfig__PatrolRefAssignment_12_1
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
    // InternalMazeDsl.g:3070:1: rule__OpponentConfig__Group_13__0 : rule__OpponentConfig__Group_13__0__Impl rule__OpponentConfig__Group_13__1 ;
    public final void rule__OpponentConfig__Group_13__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3074:1: ( rule__OpponentConfig__Group_13__0__Impl rule__OpponentConfig__Group_13__1 )
            // InternalMazeDsl.g:3075:2: rule__OpponentConfig__Group_13__0__Impl rule__OpponentConfig__Group_13__1
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
    // InternalMazeDsl.g:3082:1: rule__OpponentConfig__Group_13__0__Impl : ( 'loot' ) ;
    public final void rule__OpponentConfig__Group_13__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3086:1: ( ( 'loot' ) )
            // InternalMazeDsl.g:3087:1: ( 'loot' )
            {
            // InternalMazeDsl.g:3087:1: ( 'loot' )
            // InternalMazeDsl.g:3088:2: 'loot'
            {
             before(grammarAccess.getOpponentConfigAccess().getLootKeyword_13_0()); 
            match(input,87,FOLLOW_2); 
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
    // InternalMazeDsl.g:3097:1: rule__OpponentConfig__Group_13__1 : rule__OpponentConfig__Group_13__1__Impl ;
    public final void rule__OpponentConfig__Group_13__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3101:1: ( rule__OpponentConfig__Group_13__1__Impl )
            // InternalMazeDsl.g:3102:2: rule__OpponentConfig__Group_13__1__Impl
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
    // InternalMazeDsl.g:3108:1: rule__OpponentConfig__Group_13__1__Impl : ( ( rule__OpponentConfig__LootRefAssignment_13_1 ) ) ;
    public final void rule__OpponentConfig__Group_13__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3112:1: ( ( ( rule__OpponentConfig__LootRefAssignment_13_1 ) ) )
            // InternalMazeDsl.g:3113:1: ( ( rule__OpponentConfig__LootRefAssignment_13_1 ) )
            {
            // InternalMazeDsl.g:3113:1: ( ( rule__OpponentConfig__LootRefAssignment_13_1 ) )
            // InternalMazeDsl.g:3114:2: ( rule__OpponentConfig__LootRefAssignment_13_1 )
            {
             before(grammarAccess.getOpponentConfigAccess().getLootRefAssignment_13_1()); 
            // InternalMazeDsl.g:3115:2: ( rule__OpponentConfig__LootRefAssignment_13_1 )
            // InternalMazeDsl.g:3115:3: rule__OpponentConfig__LootRefAssignment_13_1
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
    // InternalMazeDsl.g:3124:1: rule__ZombieSpecifics__Group__0 : rule__ZombieSpecifics__Group__0__Impl rule__ZombieSpecifics__Group__1 ;
    public final void rule__ZombieSpecifics__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3128:1: ( rule__ZombieSpecifics__Group__0__Impl rule__ZombieSpecifics__Group__1 )
            // InternalMazeDsl.g:3129:2: rule__ZombieSpecifics__Group__0__Impl rule__ZombieSpecifics__Group__1
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
    // InternalMazeDsl.g:3136:1: rule__ZombieSpecifics__Group__0__Impl : ( () ) ;
    public final void rule__ZombieSpecifics__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3140:1: ( ( () ) )
            // InternalMazeDsl.g:3141:1: ( () )
            {
            // InternalMazeDsl.g:3141:1: ( () )
            // InternalMazeDsl.g:3142:2: ()
            {
             before(grammarAccess.getZombieSpecificsAccess().getZombieSpecificsAction_0()); 
            // InternalMazeDsl.g:3143:2: ()
            // InternalMazeDsl.g:3143:3: 
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
    // InternalMazeDsl.g:3151:1: rule__ZombieSpecifics__Group__1 : rule__ZombieSpecifics__Group__1__Impl rule__ZombieSpecifics__Group__2 ;
    public final void rule__ZombieSpecifics__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3155:1: ( rule__ZombieSpecifics__Group__1__Impl rule__ZombieSpecifics__Group__2 )
            // InternalMazeDsl.g:3156:2: rule__ZombieSpecifics__Group__1__Impl rule__ZombieSpecifics__Group__2
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
    // InternalMazeDsl.g:3163:1: rule__ZombieSpecifics__Group__1__Impl : ( 'zombie-stats' ) ;
    public final void rule__ZombieSpecifics__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3167:1: ( ( 'zombie-stats' ) )
            // InternalMazeDsl.g:3168:1: ( 'zombie-stats' )
            {
            // InternalMazeDsl.g:3168:1: ( 'zombie-stats' )
            // InternalMazeDsl.g:3169:2: 'zombie-stats'
            {
             before(grammarAccess.getZombieSpecificsAccess().getZombieStatsKeyword_1()); 
            match(input,88,FOLLOW_2); 
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
    // InternalMazeDsl.g:3178:1: rule__ZombieSpecifics__Group__2 : rule__ZombieSpecifics__Group__2__Impl rule__ZombieSpecifics__Group__3 ;
    public final void rule__ZombieSpecifics__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3182:1: ( rule__ZombieSpecifics__Group__2__Impl rule__ZombieSpecifics__Group__3 )
            // InternalMazeDsl.g:3183:2: rule__ZombieSpecifics__Group__2__Impl rule__ZombieSpecifics__Group__3
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
    // InternalMazeDsl.g:3190:1: rule__ZombieSpecifics__Group__2__Impl : ( '{' ) ;
    public final void rule__ZombieSpecifics__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3194:1: ( ( '{' ) )
            // InternalMazeDsl.g:3195:1: ( '{' )
            {
            // InternalMazeDsl.g:3195:1: ( '{' )
            // InternalMazeDsl.g:3196:2: '{'
            {
             before(grammarAccess.getZombieSpecificsAccess().getLeftCurlyBracketKeyword_2()); 
            match(input,68,FOLLOW_2); 
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
    // InternalMazeDsl.g:3205:1: rule__ZombieSpecifics__Group__3 : rule__ZombieSpecifics__Group__3__Impl rule__ZombieSpecifics__Group__4 ;
    public final void rule__ZombieSpecifics__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3209:1: ( rule__ZombieSpecifics__Group__3__Impl rule__ZombieSpecifics__Group__4 )
            // InternalMazeDsl.g:3210:2: rule__ZombieSpecifics__Group__3__Impl rule__ZombieSpecifics__Group__4
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
    // InternalMazeDsl.g:3217:1: rule__ZombieSpecifics__Group__3__Impl : ( ( rule__ZombieSpecifics__Group_3__0 )? ) ;
    public final void rule__ZombieSpecifics__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3221:1: ( ( ( rule__ZombieSpecifics__Group_3__0 )? ) )
            // InternalMazeDsl.g:3222:1: ( ( rule__ZombieSpecifics__Group_3__0 )? )
            {
            // InternalMazeDsl.g:3222:1: ( ( rule__ZombieSpecifics__Group_3__0 )? )
            // InternalMazeDsl.g:3223:2: ( rule__ZombieSpecifics__Group_3__0 )?
            {
             before(grammarAccess.getZombieSpecificsAccess().getGroup_3()); 
            // InternalMazeDsl.g:3224:2: ( rule__ZombieSpecifics__Group_3__0 )?
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==89) ) {
                alt31=1;
            }
            switch (alt31) {
                case 1 :
                    // InternalMazeDsl.g:3224:3: rule__ZombieSpecifics__Group_3__0
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
    // InternalMazeDsl.g:3232:1: rule__ZombieSpecifics__Group__4 : rule__ZombieSpecifics__Group__4__Impl rule__ZombieSpecifics__Group__5 ;
    public final void rule__ZombieSpecifics__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3236:1: ( rule__ZombieSpecifics__Group__4__Impl rule__ZombieSpecifics__Group__5 )
            // InternalMazeDsl.g:3237:2: rule__ZombieSpecifics__Group__4__Impl rule__ZombieSpecifics__Group__5
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
    // InternalMazeDsl.g:3244:1: rule__ZombieSpecifics__Group__4__Impl : ( ( rule__ZombieSpecifics__Group_4__0 )? ) ;
    public final void rule__ZombieSpecifics__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3248:1: ( ( ( rule__ZombieSpecifics__Group_4__0 )? ) )
            // InternalMazeDsl.g:3249:1: ( ( rule__ZombieSpecifics__Group_4__0 )? )
            {
            // InternalMazeDsl.g:3249:1: ( ( rule__ZombieSpecifics__Group_4__0 )? )
            // InternalMazeDsl.g:3250:2: ( rule__ZombieSpecifics__Group_4__0 )?
            {
             before(grammarAccess.getZombieSpecificsAccess().getGroup_4()); 
            // InternalMazeDsl.g:3251:2: ( rule__ZombieSpecifics__Group_4__0 )?
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==90) ) {
                alt32=1;
            }
            switch (alt32) {
                case 1 :
                    // InternalMazeDsl.g:3251:3: rule__ZombieSpecifics__Group_4__0
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
    // InternalMazeDsl.g:3259:1: rule__ZombieSpecifics__Group__5 : rule__ZombieSpecifics__Group__5__Impl rule__ZombieSpecifics__Group__6 ;
    public final void rule__ZombieSpecifics__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3263:1: ( rule__ZombieSpecifics__Group__5__Impl rule__ZombieSpecifics__Group__6 )
            // InternalMazeDsl.g:3264:2: rule__ZombieSpecifics__Group__5__Impl rule__ZombieSpecifics__Group__6
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
    // InternalMazeDsl.g:3271:1: rule__ZombieSpecifics__Group__5__Impl : ( ( rule__ZombieSpecifics__Group_5__0 )? ) ;
    public final void rule__ZombieSpecifics__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3275:1: ( ( ( rule__ZombieSpecifics__Group_5__0 )? ) )
            // InternalMazeDsl.g:3276:1: ( ( rule__ZombieSpecifics__Group_5__0 )? )
            {
            // InternalMazeDsl.g:3276:1: ( ( rule__ZombieSpecifics__Group_5__0 )? )
            // InternalMazeDsl.g:3277:2: ( rule__ZombieSpecifics__Group_5__0 )?
            {
             before(grammarAccess.getZombieSpecificsAccess().getGroup_5()); 
            // InternalMazeDsl.g:3278:2: ( rule__ZombieSpecifics__Group_5__0 )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==91) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // InternalMazeDsl.g:3278:3: rule__ZombieSpecifics__Group_5__0
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
    // InternalMazeDsl.g:3286:1: rule__ZombieSpecifics__Group__6 : rule__ZombieSpecifics__Group__6__Impl ;
    public final void rule__ZombieSpecifics__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3290:1: ( rule__ZombieSpecifics__Group__6__Impl )
            // InternalMazeDsl.g:3291:2: rule__ZombieSpecifics__Group__6__Impl
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
    // InternalMazeDsl.g:3297:1: rule__ZombieSpecifics__Group__6__Impl : ( '}' ) ;
    public final void rule__ZombieSpecifics__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3301:1: ( ( '}' ) )
            // InternalMazeDsl.g:3302:1: ( '}' )
            {
            // InternalMazeDsl.g:3302:1: ( '}' )
            // InternalMazeDsl.g:3303:2: '}'
            {
             before(grammarAccess.getZombieSpecificsAccess().getRightCurlyBracketKeyword_6()); 
            match(input,69,FOLLOW_2); 
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
    // InternalMazeDsl.g:3313:1: rule__ZombieSpecifics__Group_3__0 : rule__ZombieSpecifics__Group_3__0__Impl rule__ZombieSpecifics__Group_3__1 ;
    public final void rule__ZombieSpecifics__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3317:1: ( rule__ZombieSpecifics__Group_3__0__Impl rule__ZombieSpecifics__Group_3__1 )
            // InternalMazeDsl.g:3318:2: rule__ZombieSpecifics__Group_3__0__Impl rule__ZombieSpecifics__Group_3__1
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
    // InternalMazeDsl.g:3325:1: rule__ZombieSpecifics__Group_3__0__Impl : ( 'attackDamage' ) ;
    public final void rule__ZombieSpecifics__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3329:1: ( ( 'attackDamage' ) )
            // InternalMazeDsl.g:3330:1: ( 'attackDamage' )
            {
            // InternalMazeDsl.g:3330:1: ( 'attackDamage' )
            // InternalMazeDsl.g:3331:2: 'attackDamage'
            {
             before(grammarAccess.getZombieSpecificsAccess().getAttackDamageKeyword_3_0()); 
            match(input,89,FOLLOW_2); 
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
    // InternalMazeDsl.g:3340:1: rule__ZombieSpecifics__Group_3__1 : rule__ZombieSpecifics__Group_3__1__Impl ;
    public final void rule__ZombieSpecifics__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3344:1: ( rule__ZombieSpecifics__Group_3__1__Impl )
            // InternalMazeDsl.g:3345:2: rule__ZombieSpecifics__Group_3__1__Impl
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
    // InternalMazeDsl.g:3351:1: rule__ZombieSpecifics__Group_3__1__Impl : ( ( rule__ZombieSpecifics__AttackDamageAssignment_3_1 ) ) ;
    public final void rule__ZombieSpecifics__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3355:1: ( ( ( rule__ZombieSpecifics__AttackDamageAssignment_3_1 ) ) )
            // InternalMazeDsl.g:3356:1: ( ( rule__ZombieSpecifics__AttackDamageAssignment_3_1 ) )
            {
            // InternalMazeDsl.g:3356:1: ( ( rule__ZombieSpecifics__AttackDamageAssignment_3_1 ) )
            // InternalMazeDsl.g:3357:2: ( rule__ZombieSpecifics__AttackDamageAssignment_3_1 )
            {
             before(grammarAccess.getZombieSpecificsAccess().getAttackDamageAssignment_3_1()); 
            // InternalMazeDsl.g:3358:2: ( rule__ZombieSpecifics__AttackDamageAssignment_3_1 )
            // InternalMazeDsl.g:3358:3: rule__ZombieSpecifics__AttackDamageAssignment_3_1
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
    // InternalMazeDsl.g:3367:1: rule__ZombieSpecifics__Group_4__0 : rule__ZombieSpecifics__Group_4__0__Impl rule__ZombieSpecifics__Group_4__1 ;
    public final void rule__ZombieSpecifics__Group_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3371:1: ( rule__ZombieSpecifics__Group_4__0__Impl rule__ZombieSpecifics__Group_4__1 )
            // InternalMazeDsl.g:3372:2: rule__ZombieSpecifics__Group_4__0__Impl rule__ZombieSpecifics__Group_4__1
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
    // InternalMazeDsl.g:3379:1: rule__ZombieSpecifics__Group_4__0__Impl : ( 'infectionLevel' ) ;
    public final void rule__ZombieSpecifics__Group_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3383:1: ( ( 'infectionLevel' ) )
            // InternalMazeDsl.g:3384:1: ( 'infectionLevel' )
            {
            // InternalMazeDsl.g:3384:1: ( 'infectionLevel' )
            // InternalMazeDsl.g:3385:2: 'infectionLevel'
            {
             before(grammarAccess.getZombieSpecificsAccess().getInfectionLevelKeyword_4_0()); 
            match(input,90,FOLLOW_2); 
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
    // InternalMazeDsl.g:3394:1: rule__ZombieSpecifics__Group_4__1 : rule__ZombieSpecifics__Group_4__1__Impl ;
    public final void rule__ZombieSpecifics__Group_4__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3398:1: ( rule__ZombieSpecifics__Group_4__1__Impl )
            // InternalMazeDsl.g:3399:2: rule__ZombieSpecifics__Group_4__1__Impl
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
    // InternalMazeDsl.g:3405:1: rule__ZombieSpecifics__Group_4__1__Impl : ( ( rule__ZombieSpecifics__InfectionLevelAssignment_4_1 ) ) ;
    public final void rule__ZombieSpecifics__Group_4__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3409:1: ( ( ( rule__ZombieSpecifics__InfectionLevelAssignment_4_1 ) ) )
            // InternalMazeDsl.g:3410:1: ( ( rule__ZombieSpecifics__InfectionLevelAssignment_4_1 ) )
            {
            // InternalMazeDsl.g:3410:1: ( ( rule__ZombieSpecifics__InfectionLevelAssignment_4_1 ) )
            // InternalMazeDsl.g:3411:2: ( rule__ZombieSpecifics__InfectionLevelAssignment_4_1 )
            {
             before(grammarAccess.getZombieSpecificsAccess().getInfectionLevelAssignment_4_1()); 
            // InternalMazeDsl.g:3412:2: ( rule__ZombieSpecifics__InfectionLevelAssignment_4_1 )
            // InternalMazeDsl.g:3412:3: rule__ZombieSpecifics__InfectionLevelAssignment_4_1
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
    // InternalMazeDsl.g:3421:1: rule__ZombieSpecifics__Group_5__0 : rule__ZombieSpecifics__Group_5__0__Impl rule__ZombieSpecifics__Group_5__1 ;
    public final void rule__ZombieSpecifics__Group_5__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3425:1: ( rule__ZombieSpecifics__Group_5__0__Impl rule__ZombieSpecifics__Group_5__1 )
            // InternalMazeDsl.g:3426:2: rule__ZombieSpecifics__Group_5__0__Impl rule__ZombieSpecifics__Group_5__1
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
    // InternalMazeDsl.g:3433:1: rule__ZombieSpecifics__Group_5__0__Impl : ( 'resurrectionTime' ) ;
    public final void rule__ZombieSpecifics__Group_5__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3437:1: ( ( 'resurrectionTime' ) )
            // InternalMazeDsl.g:3438:1: ( 'resurrectionTime' )
            {
            // InternalMazeDsl.g:3438:1: ( 'resurrectionTime' )
            // InternalMazeDsl.g:3439:2: 'resurrectionTime'
            {
             before(grammarAccess.getZombieSpecificsAccess().getResurrectionTimeKeyword_5_0()); 
            match(input,91,FOLLOW_2); 
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
    // InternalMazeDsl.g:3448:1: rule__ZombieSpecifics__Group_5__1 : rule__ZombieSpecifics__Group_5__1__Impl ;
    public final void rule__ZombieSpecifics__Group_5__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3452:1: ( rule__ZombieSpecifics__Group_5__1__Impl )
            // InternalMazeDsl.g:3453:2: rule__ZombieSpecifics__Group_5__1__Impl
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
    // InternalMazeDsl.g:3459:1: rule__ZombieSpecifics__Group_5__1__Impl : ( ( rule__ZombieSpecifics__ResurrectionTimeAssignment_5_1 ) ) ;
    public final void rule__ZombieSpecifics__Group_5__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3463:1: ( ( ( rule__ZombieSpecifics__ResurrectionTimeAssignment_5_1 ) ) )
            // InternalMazeDsl.g:3464:1: ( ( rule__ZombieSpecifics__ResurrectionTimeAssignment_5_1 ) )
            {
            // InternalMazeDsl.g:3464:1: ( ( rule__ZombieSpecifics__ResurrectionTimeAssignment_5_1 ) )
            // InternalMazeDsl.g:3465:2: ( rule__ZombieSpecifics__ResurrectionTimeAssignment_5_1 )
            {
             before(grammarAccess.getZombieSpecificsAccess().getResurrectionTimeAssignment_5_1()); 
            // InternalMazeDsl.g:3466:2: ( rule__ZombieSpecifics__ResurrectionTimeAssignment_5_1 )
            // InternalMazeDsl.g:3466:3: rule__ZombieSpecifics__ResurrectionTimeAssignment_5_1
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
    // InternalMazeDsl.g:3475:1: rule__GhostSpecifics__Group__0 : rule__GhostSpecifics__Group__0__Impl rule__GhostSpecifics__Group__1 ;
    public final void rule__GhostSpecifics__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3479:1: ( rule__GhostSpecifics__Group__0__Impl rule__GhostSpecifics__Group__1 )
            // InternalMazeDsl.g:3480:2: rule__GhostSpecifics__Group__0__Impl rule__GhostSpecifics__Group__1
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
    // InternalMazeDsl.g:3487:1: rule__GhostSpecifics__Group__0__Impl : ( () ) ;
    public final void rule__GhostSpecifics__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3491:1: ( ( () ) )
            // InternalMazeDsl.g:3492:1: ( () )
            {
            // InternalMazeDsl.g:3492:1: ( () )
            // InternalMazeDsl.g:3493:2: ()
            {
             before(grammarAccess.getGhostSpecificsAccess().getGhostSpecificsAction_0()); 
            // InternalMazeDsl.g:3494:2: ()
            // InternalMazeDsl.g:3494:3: 
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
    // InternalMazeDsl.g:3502:1: rule__GhostSpecifics__Group__1 : rule__GhostSpecifics__Group__1__Impl rule__GhostSpecifics__Group__2 ;
    public final void rule__GhostSpecifics__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3506:1: ( rule__GhostSpecifics__Group__1__Impl rule__GhostSpecifics__Group__2 )
            // InternalMazeDsl.g:3507:2: rule__GhostSpecifics__Group__1__Impl rule__GhostSpecifics__Group__2
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
    // InternalMazeDsl.g:3514:1: rule__GhostSpecifics__Group__1__Impl : ( 'ghost-stats' ) ;
    public final void rule__GhostSpecifics__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3518:1: ( ( 'ghost-stats' ) )
            // InternalMazeDsl.g:3519:1: ( 'ghost-stats' )
            {
            // InternalMazeDsl.g:3519:1: ( 'ghost-stats' )
            // InternalMazeDsl.g:3520:2: 'ghost-stats'
            {
             before(grammarAccess.getGhostSpecificsAccess().getGhostStatsKeyword_1()); 
            match(input,92,FOLLOW_2); 
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
    // InternalMazeDsl.g:3529:1: rule__GhostSpecifics__Group__2 : rule__GhostSpecifics__Group__2__Impl rule__GhostSpecifics__Group__3 ;
    public final void rule__GhostSpecifics__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3533:1: ( rule__GhostSpecifics__Group__2__Impl rule__GhostSpecifics__Group__3 )
            // InternalMazeDsl.g:3534:2: rule__GhostSpecifics__Group__2__Impl rule__GhostSpecifics__Group__3
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
    // InternalMazeDsl.g:3541:1: rule__GhostSpecifics__Group__2__Impl : ( '{' ) ;
    public final void rule__GhostSpecifics__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3545:1: ( ( '{' ) )
            // InternalMazeDsl.g:3546:1: ( '{' )
            {
            // InternalMazeDsl.g:3546:1: ( '{' )
            // InternalMazeDsl.g:3547:2: '{'
            {
             before(grammarAccess.getGhostSpecificsAccess().getLeftCurlyBracketKeyword_2()); 
            match(input,68,FOLLOW_2); 
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
    // InternalMazeDsl.g:3556:1: rule__GhostSpecifics__Group__3 : rule__GhostSpecifics__Group__3__Impl rule__GhostSpecifics__Group__4 ;
    public final void rule__GhostSpecifics__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3560:1: ( rule__GhostSpecifics__Group__3__Impl rule__GhostSpecifics__Group__4 )
            // InternalMazeDsl.g:3561:2: rule__GhostSpecifics__Group__3__Impl rule__GhostSpecifics__Group__4
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
    // InternalMazeDsl.g:3568:1: rule__GhostSpecifics__Group__3__Impl : ( ( rule__GhostSpecifics__Group_3__0 )? ) ;
    public final void rule__GhostSpecifics__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3572:1: ( ( ( rule__GhostSpecifics__Group_3__0 )? ) )
            // InternalMazeDsl.g:3573:1: ( ( rule__GhostSpecifics__Group_3__0 )? )
            {
            // InternalMazeDsl.g:3573:1: ( ( rule__GhostSpecifics__Group_3__0 )? )
            // InternalMazeDsl.g:3574:2: ( rule__GhostSpecifics__Group_3__0 )?
            {
             before(grammarAccess.getGhostSpecificsAccess().getGroup_3()); 
            // InternalMazeDsl.g:3575:2: ( rule__GhostSpecifics__Group_3__0 )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==89) ) {
                alt34=1;
            }
            switch (alt34) {
                case 1 :
                    // InternalMazeDsl.g:3575:3: rule__GhostSpecifics__Group_3__0
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
    // InternalMazeDsl.g:3583:1: rule__GhostSpecifics__Group__4 : rule__GhostSpecifics__Group__4__Impl rule__GhostSpecifics__Group__5 ;
    public final void rule__GhostSpecifics__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3587:1: ( rule__GhostSpecifics__Group__4__Impl rule__GhostSpecifics__Group__5 )
            // InternalMazeDsl.g:3588:2: rule__GhostSpecifics__Group__4__Impl rule__GhostSpecifics__Group__5
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
    // InternalMazeDsl.g:3595:1: rule__GhostSpecifics__Group__4__Impl : ( ( rule__GhostSpecifics__Group_4__0 )? ) ;
    public final void rule__GhostSpecifics__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3599:1: ( ( ( rule__GhostSpecifics__Group_4__0 )? ) )
            // InternalMazeDsl.g:3600:1: ( ( rule__GhostSpecifics__Group_4__0 )? )
            {
            // InternalMazeDsl.g:3600:1: ( ( rule__GhostSpecifics__Group_4__0 )? )
            // InternalMazeDsl.g:3601:2: ( rule__GhostSpecifics__Group_4__0 )?
            {
             before(grammarAccess.getGhostSpecificsAccess().getGroup_4()); 
            // InternalMazeDsl.g:3602:2: ( rule__GhostSpecifics__Group_4__0 )?
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==93) ) {
                alt35=1;
            }
            switch (alt35) {
                case 1 :
                    // InternalMazeDsl.g:3602:3: rule__GhostSpecifics__Group_4__0
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
    // InternalMazeDsl.g:3610:1: rule__GhostSpecifics__Group__5 : rule__GhostSpecifics__Group__5__Impl rule__GhostSpecifics__Group__6 ;
    public final void rule__GhostSpecifics__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3614:1: ( rule__GhostSpecifics__Group__5__Impl rule__GhostSpecifics__Group__6 )
            // InternalMazeDsl.g:3615:2: rule__GhostSpecifics__Group__5__Impl rule__GhostSpecifics__Group__6
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
    // InternalMazeDsl.g:3622:1: rule__GhostSpecifics__Group__5__Impl : ( ( rule__GhostSpecifics__Group_5__0 )? ) ;
    public final void rule__GhostSpecifics__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3626:1: ( ( ( rule__GhostSpecifics__Group_5__0 )? ) )
            // InternalMazeDsl.g:3627:1: ( ( rule__GhostSpecifics__Group_5__0 )? )
            {
            // InternalMazeDsl.g:3627:1: ( ( rule__GhostSpecifics__Group_5__0 )? )
            // InternalMazeDsl.g:3628:2: ( rule__GhostSpecifics__Group_5__0 )?
            {
             before(grammarAccess.getGhostSpecificsAccess().getGroup_5()); 
            // InternalMazeDsl.g:3629:2: ( rule__GhostSpecifics__Group_5__0 )?
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==94) ) {
                alt36=1;
            }
            switch (alt36) {
                case 1 :
                    // InternalMazeDsl.g:3629:3: rule__GhostSpecifics__Group_5__0
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
    // InternalMazeDsl.g:3637:1: rule__GhostSpecifics__Group__6 : rule__GhostSpecifics__Group__6__Impl ;
    public final void rule__GhostSpecifics__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3641:1: ( rule__GhostSpecifics__Group__6__Impl )
            // InternalMazeDsl.g:3642:2: rule__GhostSpecifics__Group__6__Impl
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
    // InternalMazeDsl.g:3648:1: rule__GhostSpecifics__Group__6__Impl : ( '}' ) ;
    public final void rule__GhostSpecifics__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3652:1: ( ( '}' ) )
            // InternalMazeDsl.g:3653:1: ( '}' )
            {
            // InternalMazeDsl.g:3653:1: ( '}' )
            // InternalMazeDsl.g:3654:2: '}'
            {
             before(grammarAccess.getGhostSpecificsAccess().getRightCurlyBracketKeyword_6()); 
            match(input,69,FOLLOW_2); 
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
    // InternalMazeDsl.g:3664:1: rule__GhostSpecifics__Group_3__0 : rule__GhostSpecifics__Group_3__0__Impl rule__GhostSpecifics__Group_3__1 ;
    public final void rule__GhostSpecifics__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3668:1: ( rule__GhostSpecifics__Group_3__0__Impl rule__GhostSpecifics__Group_3__1 )
            // InternalMazeDsl.g:3669:2: rule__GhostSpecifics__Group_3__0__Impl rule__GhostSpecifics__Group_3__1
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
    // InternalMazeDsl.g:3676:1: rule__GhostSpecifics__Group_3__0__Impl : ( 'attackDamage' ) ;
    public final void rule__GhostSpecifics__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3680:1: ( ( 'attackDamage' ) )
            // InternalMazeDsl.g:3681:1: ( 'attackDamage' )
            {
            // InternalMazeDsl.g:3681:1: ( 'attackDamage' )
            // InternalMazeDsl.g:3682:2: 'attackDamage'
            {
             before(grammarAccess.getGhostSpecificsAccess().getAttackDamageKeyword_3_0()); 
            match(input,89,FOLLOW_2); 
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
    // InternalMazeDsl.g:3691:1: rule__GhostSpecifics__Group_3__1 : rule__GhostSpecifics__Group_3__1__Impl ;
    public final void rule__GhostSpecifics__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3695:1: ( rule__GhostSpecifics__Group_3__1__Impl )
            // InternalMazeDsl.g:3696:2: rule__GhostSpecifics__Group_3__1__Impl
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
    // InternalMazeDsl.g:3702:1: rule__GhostSpecifics__Group_3__1__Impl : ( ( rule__GhostSpecifics__AttackDamageAssignment_3_1 ) ) ;
    public final void rule__GhostSpecifics__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3706:1: ( ( ( rule__GhostSpecifics__AttackDamageAssignment_3_1 ) ) )
            // InternalMazeDsl.g:3707:1: ( ( rule__GhostSpecifics__AttackDamageAssignment_3_1 ) )
            {
            // InternalMazeDsl.g:3707:1: ( ( rule__GhostSpecifics__AttackDamageAssignment_3_1 ) )
            // InternalMazeDsl.g:3708:2: ( rule__GhostSpecifics__AttackDamageAssignment_3_1 )
            {
             before(grammarAccess.getGhostSpecificsAccess().getAttackDamageAssignment_3_1()); 
            // InternalMazeDsl.g:3709:2: ( rule__GhostSpecifics__AttackDamageAssignment_3_1 )
            // InternalMazeDsl.g:3709:3: rule__GhostSpecifics__AttackDamageAssignment_3_1
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
    // InternalMazeDsl.g:3718:1: rule__GhostSpecifics__Group_4__0 : rule__GhostSpecifics__Group_4__0__Impl rule__GhostSpecifics__Group_4__1 ;
    public final void rule__GhostSpecifics__Group_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3722:1: ( rule__GhostSpecifics__Group_4__0__Impl rule__GhostSpecifics__Group_4__1 )
            // InternalMazeDsl.g:3723:2: rule__GhostSpecifics__Group_4__0__Impl rule__GhostSpecifics__Group_4__1
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
    // InternalMazeDsl.g:3730:1: rule__GhostSpecifics__Group_4__0__Impl : ( 'visibilityLevel' ) ;
    public final void rule__GhostSpecifics__Group_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3734:1: ( ( 'visibilityLevel' ) )
            // InternalMazeDsl.g:3735:1: ( 'visibilityLevel' )
            {
            // InternalMazeDsl.g:3735:1: ( 'visibilityLevel' )
            // InternalMazeDsl.g:3736:2: 'visibilityLevel'
            {
             before(grammarAccess.getGhostSpecificsAccess().getVisibilityLevelKeyword_4_0()); 
            match(input,93,FOLLOW_2); 
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
    // InternalMazeDsl.g:3745:1: rule__GhostSpecifics__Group_4__1 : rule__GhostSpecifics__Group_4__1__Impl ;
    public final void rule__GhostSpecifics__Group_4__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3749:1: ( rule__GhostSpecifics__Group_4__1__Impl )
            // InternalMazeDsl.g:3750:2: rule__GhostSpecifics__Group_4__1__Impl
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
    // InternalMazeDsl.g:3756:1: rule__GhostSpecifics__Group_4__1__Impl : ( ( rule__GhostSpecifics__VisibilityLevelAssignment_4_1 ) ) ;
    public final void rule__GhostSpecifics__Group_4__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3760:1: ( ( ( rule__GhostSpecifics__VisibilityLevelAssignment_4_1 ) ) )
            // InternalMazeDsl.g:3761:1: ( ( rule__GhostSpecifics__VisibilityLevelAssignment_4_1 ) )
            {
            // InternalMazeDsl.g:3761:1: ( ( rule__GhostSpecifics__VisibilityLevelAssignment_4_1 ) )
            // InternalMazeDsl.g:3762:2: ( rule__GhostSpecifics__VisibilityLevelAssignment_4_1 )
            {
             before(grammarAccess.getGhostSpecificsAccess().getVisibilityLevelAssignment_4_1()); 
            // InternalMazeDsl.g:3763:2: ( rule__GhostSpecifics__VisibilityLevelAssignment_4_1 )
            // InternalMazeDsl.g:3763:3: rule__GhostSpecifics__VisibilityLevelAssignment_4_1
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
    // InternalMazeDsl.g:3772:1: rule__GhostSpecifics__Group_5__0 : rule__GhostSpecifics__Group_5__0__Impl rule__GhostSpecifics__Group_5__1 ;
    public final void rule__GhostSpecifics__Group_5__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3776:1: ( rule__GhostSpecifics__Group_5__0__Impl rule__GhostSpecifics__Group_5__1 )
            // InternalMazeDsl.g:3777:2: rule__GhostSpecifics__Group_5__0__Impl rule__GhostSpecifics__Group_5__1
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
    // InternalMazeDsl.g:3784:1: rule__GhostSpecifics__Group_5__0__Impl : ( 'nonTangibilityEnergy' ) ;
    public final void rule__GhostSpecifics__Group_5__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3788:1: ( ( 'nonTangibilityEnergy' ) )
            // InternalMazeDsl.g:3789:1: ( 'nonTangibilityEnergy' )
            {
            // InternalMazeDsl.g:3789:1: ( 'nonTangibilityEnergy' )
            // InternalMazeDsl.g:3790:2: 'nonTangibilityEnergy'
            {
             before(grammarAccess.getGhostSpecificsAccess().getNonTangibilityEnergyKeyword_5_0()); 
            match(input,94,FOLLOW_2); 
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
    // InternalMazeDsl.g:3799:1: rule__GhostSpecifics__Group_5__1 : rule__GhostSpecifics__Group_5__1__Impl ;
    public final void rule__GhostSpecifics__Group_5__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3803:1: ( rule__GhostSpecifics__Group_5__1__Impl )
            // InternalMazeDsl.g:3804:2: rule__GhostSpecifics__Group_5__1__Impl
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
    // InternalMazeDsl.g:3810:1: rule__GhostSpecifics__Group_5__1__Impl : ( ( rule__GhostSpecifics__NonTangibilityEnergyAssignment_5_1 ) ) ;
    public final void rule__GhostSpecifics__Group_5__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3814:1: ( ( ( rule__GhostSpecifics__NonTangibilityEnergyAssignment_5_1 ) ) )
            // InternalMazeDsl.g:3815:1: ( ( rule__GhostSpecifics__NonTangibilityEnergyAssignment_5_1 ) )
            {
            // InternalMazeDsl.g:3815:1: ( ( rule__GhostSpecifics__NonTangibilityEnergyAssignment_5_1 ) )
            // InternalMazeDsl.g:3816:2: ( rule__GhostSpecifics__NonTangibilityEnergyAssignment_5_1 )
            {
             before(grammarAccess.getGhostSpecificsAccess().getNonTangibilityEnergyAssignment_5_1()); 
            // InternalMazeDsl.g:3817:2: ( rule__GhostSpecifics__NonTangibilityEnergyAssignment_5_1 )
            // InternalMazeDsl.g:3817:3: rule__GhostSpecifics__NonTangibilityEnergyAssignment_5_1
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
    // InternalMazeDsl.g:3826:1: rule__RangedSpecifics__Group__0 : rule__RangedSpecifics__Group__0__Impl rule__RangedSpecifics__Group__1 ;
    public final void rule__RangedSpecifics__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3830:1: ( rule__RangedSpecifics__Group__0__Impl rule__RangedSpecifics__Group__1 )
            // InternalMazeDsl.g:3831:2: rule__RangedSpecifics__Group__0__Impl rule__RangedSpecifics__Group__1
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
    // InternalMazeDsl.g:3838:1: rule__RangedSpecifics__Group__0__Impl : ( () ) ;
    public final void rule__RangedSpecifics__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3842:1: ( ( () ) )
            // InternalMazeDsl.g:3843:1: ( () )
            {
            // InternalMazeDsl.g:3843:1: ( () )
            // InternalMazeDsl.g:3844:2: ()
            {
             before(grammarAccess.getRangedSpecificsAccess().getRangedSpecificsAction_0()); 
            // InternalMazeDsl.g:3845:2: ()
            // InternalMazeDsl.g:3845:3: 
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
    // InternalMazeDsl.g:3853:1: rule__RangedSpecifics__Group__1 : rule__RangedSpecifics__Group__1__Impl rule__RangedSpecifics__Group__2 ;
    public final void rule__RangedSpecifics__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3857:1: ( rule__RangedSpecifics__Group__1__Impl rule__RangedSpecifics__Group__2 )
            // InternalMazeDsl.g:3858:2: rule__RangedSpecifics__Group__1__Impl rule__RangedSpecifics__Group__2
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
    // InternalMazeDsl.g:3865:1: rule__RangedSpecifics__Group__1__Impl : ( 'ranged-stats' ) ;
    public final void rule__RangedSpecifics__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3869:1: ( ( 'ranged-stats' ) )
            // InternalMazeDsl.g:3870:1: ( 'ranged-stats' )
            {
            // InternalMazeDsl.g:3870:1: ( 'ranged-stats' )
            // InternalMazeDsl.g:3871:2: 'ranged-stats'
            {
             before(grammarAccess.getRangedSpecificsAccess().getRangedStatsKeyword_1()); 
            match(input,95,FOLLOW_2); 
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
    // InternalMazeDsl.g:3880:1: rule__RangedSpecifics__Group__2 : rule__RangedSpecifics__Group__2__Impl rule__RangedSpecifics__Group__3 ;
    public final void rule__RangedSpecifics__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3884:1: ( rule__RangedSpecifics__Group__2__Impl rule__RangedSpecifics__Group__3 )
            // InternalMazeDsl.g:3885:2: rule__RangedSpecifics__Group__2__Impl rule__RangedSpecifics__Group__3
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
    // InternalMazeDsl.g:3892:1: rule__RangedSpecifics__Group__2__Impl : ( '{' ) ;
    public final void rule__RangedSpecifics__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3896:1: ( ( '{' ) )
            // InternalMazeDsl.g:3897:1: ( '{' )
            {
            // InternalMazeDsl.g:3897:1: ( '{' )
            // InternalMazeDsl.g:3898:2: '{'
            {
             before(grammarAccess.getRangedSpecificsAccess().getLeftCurlyBracketKeyword_2()); 
            match(input,68,FOLLOW_2); 
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
    // InternalMazeDsl.g:3907:1: rule__RangedSpecifics__Group__3 : rule__RangedSpecifics__Group__3__Impl rule__RangedSpecifics__Group__4 ;
    public final void rule__RangedSpecifics__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3911:1: ( rule__RangedSpecifics__Group__3__Impl rule__RangedSpecifics__Group__4 )
            // InternalMazeDsl.g:3912:2: rule__RangedSpecifics__Group__3__Impl rule__RangedSpecifics__Group__4
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
    // InternalMazeDsl.g:3919:1: rule__RangedSpecifics__Group__3__Impl : ( ( rule__RangedSpecifics__Group_3__0 )? ) ;
    public final void rule__RangedSpecifics__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3923:1: ( ( ( rule__RangedSpecifics__Group_3__0 )? ) )
            // InternalMazeDsl.g:3924:1: ( ( rule__RangedSpecifics__Group_3__0 )? )
            {
            // InternalMazeDsl.g:3924:1: ( ( rule__RangedSpecifics__Group_3__0 )? )
            // InternalMazeDsl.g:3925:2: ( rule__RangedSpecifics__Group_3__0 )?
            {
             before(grammarAccess.getRangedSpecificsAccess().getGroup_3()); 
            // InternalMazeDsl.g:3926:2: ( rule__RangedSpecifics__Group_3__0 )?
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( (LA37_0==96) ) {
                alt37=1;
            }
            switch (alt37) {
                case 1 :
                    // InternalMazeDsl.g:3926:3: rule__RangedSpecifics__Group_3__0
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
    // InternalMazeDsl.g:3934:1: rule__RangedSpecifics__Group__4 : rule__RangedSpecifics__Group__4__Impl rule__RangedSpecifics__Group__5 ;
    public final void rule__RangedSpecifics__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3938:1: ( rule__RangedSpecifics__Group__4__Impl rule__RangedSpecifics__Group__5 )
            // InternalMazeDsl.g:3939:2: rule__RangedSpecifics__Group__4__Impl rule__RangedSpecifics__Group__5
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
    // InternalMazeDsl.g:3946:1: rule__RangedSpecifics__Group__4__Impl : ( ( rule__RangedSpecifics__Group_4__0 )? ) ;
    public final void rule__RangedSpecifics__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3950:1: ( ( ( rule__RangedSpecifics__Group_4__0 )? ) )
            // InternalMazeDsl.g:3951:1: ( ( rule__RangedSpecifics__Group_4__0 )? )
            {
            // InternalMazeDsl.g:3951:1: ( ( rule__RangedSpecifics__Group_4__0 )? )
            // InternalMazeDsl.g:3952:2: ( rule__RangedSpecifics__Group_4__0 )?
            {
             before(grammarAccess.getRangedSpecificsAccess().getGroup_4()); 
            // InternalMazeDsl.g:3953:2: ( rule__RangedSpecifics__Group_4__0 )?
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==97) ) {
                alt38=1;
            }
            switch (alt38) {
                case 1 :
                    // InternalMazeDsl.g:3953:3: rule__RangedSpecifics__Group_4__0
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
    // InternalMazeDsl.g:3961:1: rule__RangedSpecifics__Group__5 : rule__RangedSpecifics__Group__5__Impl rule__RangedSpecifics__Group__6 ;
    public final void rule__RangedSpecifics__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3965:1: ( rule__RangedSpecifics__Group__5__Impl rule__RangedSpecifics__Group__6 )
            // InternalMazeDsl.g:3966:2: rule__RangedSpecifics__Group__5__Impl rule__RangedSpecifics__Group__6
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
    // InternalMazeDsl.g:3973:1: rule__RangedSpecifics__Group__5__Impl : ( ( rule__RangedSpecifics__Group_5__0 )? ) ;
    public final void rule__RangedSpecifics__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3977:1: ( ( ( rule__RangedSpecifics__Group_5__0 )? ) )
            // InternalMazeDsl.g:3978:1: ( ( rule__RangedSpecifics__Group_5__0 )? )
            {
            // InternalMazeDsl.g:3978:1: ( ( rule__RangedSpecifics__Group_5__0 )? )
            // InternalMazeDsl.g:3979:2: ( rule__RangedSpecifics__Group_5__0 )?
            {
             before(grammarAccess.getRangedSpecificsAccess().getGroup_5()); 
            // InternalMazeDsl.g:3980:2: ( rule__RangedSpecifics__Group_5__0 )?
            int alt39=2;
            int LA39_0 = input.LA(1);

            if ( (LA39_0==89) ) {
                alt39=1;
            }
            switch (alt39) {
                case 1 :
                    // InternalMazeDsl.g:3980:3: rule__RangedSpecifics__Group_5__0
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
    // InternalMazeDsl.g:3988:1: rule__RangedSpecifics__Group__6 : rule__RangedSpecifics__Group__6__Impl rule__RangedSpecifics__Group__7 ;
    public final void rule__RangedSpecifics__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3992:1: ( rule__RangedSpecifics__Group__6__Impl rule__RangedSpecifics__Group__7 )
            // InternalMazeDsl.g:3993:2: rule__RangedSpecifics__Group__6__Impl rule__RangedSpecifics__Group__7
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
    // InternalMazeDsl.g:4000:1: rule__RangedSpecifics__Group__6__Impl : ( ( rule__RangedSpecifics__Group_6__0 )? ) ;
    public final void rule__RangedSpecifics__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4004:1: ( ( ( rule__RangedSpecifics__Group_6__0 )? ) )
            // InternalMazeDsl.g:4005:1: ( ( rule__RangedSpecifics__Group_6__0 )? )
            {
            // InternalMazeDsl.g:4005:1: ( ( rule__RangedSpecifics__Group_6__0 )? )
            // InternalMazeDsl.g:4006:2: ( rule__RangedSpecifics__Group_6__0 )?
            {
             before(grammarAccess.getRangedSpecificsAccess().getGroup_6()); 
            // InternalMazeDsl.g:4007:2: ( rule__RangedSpecifics__Group_6__0 )?
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( (LA40_0==98) ) {
                alt40=1;
            }
            switch (alt40) {
                case 1 :
                    // InternalMazeDsl.g:4007:3: rule__RangedSpecifics__Group_6__0
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
    // InternalMazeDsl.g:4015:1: rule__RangedSpecifics__Group__7 : rule__RangedSpecifics__Group__7__Impl rule__RangedSpecifics__Group__8 ;
    public final void rule__RangedSpecifics__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4019:1: ( rule__RangedSpecifics__Group__7__Impl rule__RangedSpecifics__Group__8 )
            // InternalMazeDsl.g:4020:2: rule__RangedSpecifics__Group__7__Impl rule__RangedSpecifics__Group__8
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
    // InternalMazeDsl.g:4027:1: rule__RangedSpecifics__Group__7__Impl : ( ( rule__RangedSpecifics__Group_7__0 )? ) ;
    public final void rule__RangedSpecifics__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4031:1: ( ( ( rule__RangedSpecifics__Group_7__0 )? ) )
            // InternalMazeDsl.g:4032:1: ( ( rule__RangedSpecifics__Group_7__0 )? )
            {
            // InternalMazeDsl.g:4032:1: ( ( rule__RangedSpecifics__Group_7__0 )? )
            // InternalMazeDsl.g:4033:2: ( rule__RangedSpecifics__Group_7__0 )?
            {
             before(grammarAccess.getRangedSpecificsAccess().getGroup_7()); 
            // InternalMazeDsl.g:4034:2: ( rule__RangedSpecifics__Group_7__0 )?
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( (LA41_0==99) ) {
                alt41=1;
            }
            switch (alt41) {
                case 1 :
                    // InternalMazeDsl.g:4034:3: rule__RangedSpecifics__Group_7__0
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
    // InternalMazeDsl.g:4042:1: rule__RangedSpecifics__Group__8 : rule__RangedSpecifics__Group__8__Impl rule__RangedSpecifics__Group__9 ;
    public final void rule__RangedSpecifics__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4046:1: ( rule__RangedSpecifics__Group__8__Impl rule__RangedSpecifics__Group__9 )
            // InternalMazeDsl.g:4047:2: rule__RangedSpecifics__Group__8__Impl rule__RangedSpecifics__Group__9
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
    // InternalMazeDsl.g:4054:1: rule__RangedSpecifics__Group__8__Impl : ( ( rule__RangedSpecifics__Group_8__0 )? ) ;
    public final void rule__RangedSpecifics__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4058:1: ( ( ( rule__RangedSpecifics__Group_8__0 )? ) )
            // InternalMazeDsl.g:4059:1: ( ( rule__RangedSpecifics__Group_8__0 )? )
            {
            // InternalMazeDsl.g:4059:1: ( ( rule__RangedSpecifics__Group_8__0 )? )
            // InternalMazeDsl.g:4060:2: ( rule__RangedSpecifics__Group_8__0 )?
            {
             before(grammarAccess.getRangedSpecificsAccess().getGroup_8()); 
            // InternalMazeDsl.g:4061:2: ( rule__RangedSpecifics__Group_8__0 )?
            int alt42=2;
            int LA42_0 = input.LA(1);

            if ( (LA42_0==100) ) {
                alt42=1;
            }
            switch (alt42) {
                case 1 :
                    // InternalMazeDsl.g:4061:3: rule__RangedSpecifics__Group_8__0
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
    // InternalMazeDsl.g:4069:1: rule__RangedSpecifics__Group__9 : rule__RangedSpecifics__Group__9__Impl ;
    public final void rule__RangedSpecifics__Group__9() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4073:1: ( rule__RangedSpecifics__Group__9__Impl )
            // InternalMazeDsl.g:4074:2: rule__RangedSpecifics__Group__9__Impl
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
    // InternalMazeDsl.g:4080:1: rule__RangedSpecifics__Group__9__Impl : ( '}' ) ;
    public final void rule__RangedSpecifics__Group__9__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4084:1: ( ( '}' ) )
            // InternalMazeDsl.g:4085:1: ( '}' )
            {
            // InternalMazeDsl.g:4085:1: ( '}' )
            // InternalMazeDsl.g:4086:2: '}'
            {
             before(grammarAccess.getRangedSpecificsAccess().getRightCurlyBracketKeyword_9()); 
            match(input,69,FOLLOW_2); 
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
    // InternalMazeDsl.g:4096:1: rule__RangedSpecifics__Group_3__0 : rule__RangedSpecifics__Group_3__0__Impl rule__RangedSpecifics__Group_3__1 ;
    public final void rule__RangedSpecifics__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4100:1: ( rule__RangedSpecifics__Group_3__0__Impl rule__RangedSpecifics__Group_3__1 )
            // InternalMazeDsl.g:4101:2: rule__RangedSpecifics__Group_3__0__Impl rule__RangedSpecifics__Group_3__1
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
    // InternalMazeDsl.g:4108:1: rule__RangedSpecifics__Group_3__0__Impl : ( 'attackRange' ) ;
    public final void rule__RangedSpecifics__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4112:1: ( ( 'attackRange' ) )
            // InternalMazeDsl.g:4113:1: ( 'attackRange' )
            {
            // InternalMazeDsl.g:4113:1: ( 'attackRange' )
            // InternalMazeDsl.g:4114:2: 'attackRange'
            {
             before(grammarAccess.getRangedSpecificsAccess().getAttackRangeKeyword_3_0()); 
            match(input,96,FOLLOW_2); 
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
    // InternalMazeDsl.g:4123:1: rule__RangedSpecifics__Group_3__1 : rule__RangedSpecifics__Group_3__1__Impl ;
    public final void rule__RangedSpecifics__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4127:1: ( rule__RangedSpecifics__Group_3__1__Impl )
            // InternalMazeDsl.g:4128:2: rule__RangedSpecifics__Group_3__1__Impl
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
    // InternalMazeDsl.g:4134:1: rule__RangedSpecifics__Group_3__1__Impl : ( ( rule__RangedSpecifics__AttackRangeAssignment_3_1 ) ) ;
    public final void rule__RangedSpecifics__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4138:1: ( ( ( rule__RangedSpecifics__AttackRangeAssignment_3_1 ) ) )
            // InternalMazeDsl.g:4139:1: ( ( rule__RangedSpecifics__AttackRangeAssignment_3_1 ) )
            {
            // InternalMazeDsl.g:4139:1: ( ( rule__RangedSpecifics__AttackRangeAssignment_3_1 ) )
            // InternalMazeDsl.g:4140:2: ( rule__RangedSpecifics__AttackRangeAssignment_3_1 )
            {
             before(grammarAccess.getRangedSpecificsAccess().getAttackRangeAssignment_3_1()); 
            // InternalMazeDsl.g:4141:2: ( rule__RangedSpecifics__AttackRangeAssignment_3_1 )
            // InternalMazeDsl.g:4141:3: rule__RangedSpecifics__AttackRangeAssignment_3_1
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
    // InternalMazeDsl.g:4150:1: rule__RangedSpecifics__Group_4__0 : rule__RangedSpecifics__Group_4__0__Impl rule__RangedSpecifics__Group_4__1 ;
    public final void rule__RangedSpecifics__Group_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4154:1: ( rule__RangedSpecifics__Group_4__0__Impl rule__RangedSpecifics__Group_4__1 )
            // InternalMazeDsl.g:4155:2: rule__RangedSpecifics__Group_4__0__Impl rule__RangedSpecifics__Group_4__1
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
    // InternalMazeDsl.g:4162:1: rule__RangedSpecifics__Group_4__0__Impl : ( 'attackCooldown' ) ;
    public final void rule__RangedSpecifics__Group_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4166:1: ( ( 'attackCooldown' ) )
            // InternalMazeDsl.g:4167:1: ( 'attackCooldown' )
            {
            // InternalMazeDsl.g:4167:1: ( 'attackCooldown' )
            // InternalMazeDsl.g:4168:2: 'attackCooldown'
            {
             before(grammarAccess.getRangedSpecificsAccess().getAttackCooldownKeyword_4_0()); 
            match(input,97,FOLLOW_2); 
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
    // InternalMazeDsl.g:4177:1: rule__RangedSpecifics__Group_4__1 : rule__RangedSpecifics__Group_4__1__Impl ;
    public final void rule__RangedSpecifics__Group_4__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4181:1: ( rule__RangedSpecifics__Group_4__1__Impl )
            // InternalMazeDsl.g:4182:2: rule__RangedSpecifics__Group_4__1__Impl
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
    // InternalMazeDsl.g:4188:1: rule__RangedSpecifics__Group_4__1__Impl : ( ( rule__RangedSpecifics__AttackCooldownAssignment_4_1 ) ) ;
    public final void rule__RangedSpecifics__Group_4__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4192:1: ( ( ( rule__RangedSpecifics__AttackCooldownAssignment_4_1 ) ) )
            // InternalMazeDsl.g:4193:1: ( ( rule__RangedSpecifics__AttackCooldownAssignment_4_1 ) )
            {
            // InternalMazeDsl.g:4193:1: ( ( rule__RangedSpecifics__AttackCooldownAssignment_4_1 ) )
            // InternalMazeDsl.g:4194:2: ( rule__RangedSpecifics__AttackCooldownAssignment_4_1 )
            {
             before(grammarAccess.getRangedSpecificsAccess().getAttackCooldownAssignment_4_1()); 
            // InternalMazeDsl.g:4195:2: ( rule__RangedSpecifics__AttackCooldownAssignment_4_1 )
            // InternalMazeDsl.g:4195:3: rule__RangedSpecifics__AttackCooldownAssignment_4_1
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
    // InternalMazeDsl.g:4204:1: rule__RangedSpecifics__Group_5__0 : rule__RangedSpecifics__Group_5__0__Impl rule__RangedSpecifics__Group_5__1 ;
    public final void rule__RangedSpecifics__Group_5__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4208:1: ( rule__RangedSpecifics__Group_5__0__Impl rule__RangedSpecifics__Group_5__1 )
            // InternalMazeDsl.g:4209:2: rule__RangedSpecifics__Group_5__0__Impl rule__RangedSpecifics__Group_5__1
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
    // InternalMazeDsl.g:4216:1: rule__RangedSpecifics__Group_5__0__Impl : ( 'attackDamage' ) ;
    public final void rule__RangedSpecifics__Group_5__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4220:1: ( ( 'attackDamage' ) )
            // InternalMazeDsl.g:4221:1: ( 'attackDamage' )
            {
            // InternalMazeDsl.g:4221:1: ( 'attackDamage' )
            // InternalMazeDsl.g:4222:2: 'attackDamage'
            {
             before(grammarAccess.getRangedSpecificsAccess().getAttackDamageKeyword_5_0()); 
            match(input,89,FOLLOW_2); 
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
    // InternalMazeDsl.g:4231:1: rule__RangedSpecifics__Group_5__1 : rule__RangedSpecifics__Group_5__1__Impl ;
    public final void rule__RangedSpecifics__Group_5__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4235:1: ( rule__RangedSpecifics__Group_5__1__Impl )
            // InternalMazeDsl.g:4236:2: rule__RangedSpecifics__Group_5__1__Impl
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
    // InternalMazeDsl.g:4242:1: rule__RangedSpecifics__Group_5__1__Impl : ( ( rule__RangedSpecifics__AttackDamageAssignment_5_1 ) ) ;
    public final void rule__RangedSpecifics__Group_5__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4246:1: ( ( ( rule__RangedSpecifics__AttackDamageAssignment_5_1 ) ) )
            // InternalMazeDsl.g:4247:1: ( ( rule__RangedSpecifics__AttackDamageAssignment_5_1 ) )
            {
            // InternalMazeDsl.g:4247:1: ( ( rule__RangedSpecifics__AttackDamageAssignment_5_1 ) )
            // InternalMazeDsl.g:4248:2: ( rule__RangedSpecifics__AttackDamageAssignment_5_1 )
            {
             before(grammarAccess.getRangedSpecificsAccess().getAttackDamageAssignment_5_1()); 
            // InternalMazeDsl.g:4249:2: ( rule__RangedSpecifics__AttackDamageAssignment_5_1 )
            // InternalMazeDsl.g:4249:3: rule__RangedSpecifics__AttackDamageAssignment_5_1
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
    // InternalMazeDsl.g:4258:1: rule__RangedSpecifics__Group_6__0 : rule__RangedSpecifics__Group_6__0__Impl rule__RangedSpecifics__Group_6__1 ;
    public final void rule__RangedSpecifics__Group_6__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4262:1: ( rule__RangedSpecifics__Group_6__0__Impl rule__RangedSpecifics__Group_6__1 )
            // InternalMazeDsl.g:4263:2: rule__RangedSpecifics__Group_6__0__Impl rule__RangedSpecifics__Group_6__1
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
    // InternalMazeDsl.g:4270:1: rule__RangedSpecifics__Group_6__0__Impl : ( 'projectileSpeed' ) ;
    public final void rule__RangedSpecifics__Group_6__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4274:1: ( ( 'projectileSpeed' ) )
            // InternalMazeDsl.g:4275:1: ( 'projectileSpeed' )
            {
            // InternalMazeDsl.g:4275:1: ( 'projectileSpeed' )
            // InternalMazeDsl.g:4276:2: 'projectileSpeed'
            {
             before(grammarAccess.getRangedSpecificsAccess().getProjectileSpeedKeyword_6_0()); 
            match(input,98,FOLLOW_2); 
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
    // InternalMazeDsl.g:4285:1: rule__RangedSpecifics__Group_6__1 : rule__RangedSpecifics__Group_6__1__Impl ;
    public final void rule__RangedSpecifics__Group_6__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4289:1: ( rule__RangedSpecifics__Group_6__1__Impl )
            // InternalMazeDsl.g:4290:2: rule__RangedSpecifics__Group_6__1__Impl
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
    // InternalMazeDsl.g:4296:1: rule__RangedSpecifics__Group_6__1__Impl : ( ( rule__RangedSpecifics__ProjectileSpeedAssignment_6_1 ) ) ;
    public final void rule__RangedSpecifics__Group_6__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4300:1: ( ( ( rule__RangedSpecifics__ProjectileSpeedAssignment_6_1 ) ) )
            // InternalMazeDsl.g:4301:1: ( ( rule__RangedSpecifics__ProjectileSpeedAssignment_6_1 ) )
            {
            // InternalMazeDsl.g:4301:1: ( ( rule__RangedSpecifics__ProjectileSpeedAssignment_6_1 ) )
            // InternalMazeDsl.g:4302:2: ( rule__RangedSpecifics__ProjectileSpeedAssignment_6_1 )
            {
             before(grammarAccess.getRangedSpecificsAccess().getProjectileSpeedAssignment_6_1()); 
            // InternalMazeDsl.g:4303:2: ( rule__RangedSpecifics__ProjectileSpeedAssignment_6_1 )
            // InternalMazeDsl.g:4303:3: rule__RangedSpecifics__ProjectileSpeedAssignment_6_1
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
    // InternalMazeDsl.g:4312:1: rule__RangedSpecifics__Group_7__0 : rule__RangedSpecifics__Group_7__0__Impl rule__RangedSpecifics__Group_7__1 ;
    public final void rule__RangedSpecifics__Group_7__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4316:1: ( rule__RangedSpecifics__Group_7__0__Impl rule__RangedSpecifics__Group_7__1 )
            // InternalMazeDsl.g:4317:2: rule__RangedSpecifics__Group_7__0__Impl rule__RangedSpecifics__Group_7__1
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
    // InternalMazeDsl.g:4324:1: rule__RangedSpecifics__Group_7__0__Impl : ( 'projectileType' ) ;
    public final void rule__RangedSpecifics__Group_7__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4328:1: ( ( 'projectileType' ) )
            // InternalMazeDsl.g:4329:1: ( 'projectileType' )
            {
            // InternalMazeDsl.g:4329:1: ( 'projectileType' )
            // InternalMazeDsl.g:4330:2: 'projectileType'
            {
             before(grammarAccess.getRangedSpecificsAccess().getProjectileTypeKeyword_7_0()); 
            match(input,99,FOLLOW_2); 
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
    // InternalMazeDsl.g:4339:1: rule__RangedSpecifics__Group_7__1 : rule__RangedSpecifics__Group_7__1__Impl ;
    public final void rule__RangedSpecifics__Group_7__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4343:1: ( rule__RangedSpecifics__Group_7__1__Impl )
            // InternalMazeDsl.g:4344:2: rule__RangedSpecifics__Group_7__1__Impl
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
    // InternalMazeDsl.g:4350:1: rule__RangedSpecifics__Group_7__1__Impl : ( ( rule__RangedSpecifics__ProjectileTypeAssignment_7_1 ) ) ;
    public final void rule__RangedSpecifics__Group_7__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4354:1: ( ( ( rule__RangedSpecifics__ProjectileTypeAssignment_7_1 ) ) )
            // InternalMazeDsl.g:4355:1: ( ( rule__RangedSpecifics__ProjectileTypeAssignment_7_1 ) )
            {
            // InternalMazeDsl.g:4355:1: ( ( rule__RangedSpecifics__ProjectileTypeAssignment_7_1 ) )
            // InternalMazeDsl.g:4356:2: ( rule__RangedSpecifics__ProjectileTypeAssignment_7_1 )
            {
             before(grammarAccess.getRangedSpecificsAccess().getProjectileTypeAssignment_7_1()); 
            // InternalMazeDsl.g:4357:2: ( rule__RangedSpecifics__ProjectileTypeAssignment_7_1 )
            // InternalMazeDsl.g:4357:3: rule__RangedSpecifics__ProjectileTypeAssignment_7_1
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
    // InternalMazeDsl.g:4366:1: rule__RangedSpecifics__Group_8__0 : rule__RangedSpecifics__Group_8__0__Impl rule__RangedSpecifics__Group_8__1 ;
    public final void rule__RangedSpecifics__Group_8__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4370:1: ( rule__RangedSpecifics__Group_8__0__Impl rule__RangedSpecifics__Group_8__1 )
            // InternalMazeDsl.g:4371:2: rule__RangedSpecifics__Group_8__0__Impl rule__RangedSpecifics__Group_8__1
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
    // InternalMazeDsl.g:4378:1: rule__RangedSpecifics__Group_8__0__Impl : ( 'splashRadius' ) ;
    public final void rule__RangedSpecifics__Group_8__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4382:1: ( ( 'splashRadius' ) )
            // InternalMazeDsl.g:4383:1: ( 'splashRadius' )
            {
            // InternalMazeDsl.g:4383:1: ( 'splashRadius' )
            // InternalMazeDsl.g:4384:2: 'splashRadius'
            {
             before(grammarAccess.getRangedSpecificsAccess().getSplashRadiusKeyword_8_0()); 
            match(input,100,FOLLOW_2); 
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
    // InternalMazeDsl.g:4393:1: rule__RangedSpecifics__Group_8__1 : rule__RangedSpecifics__Group_8__1__Impl ;
    public final void rule__RangedSpecifics__Group_8__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4397:1: ( rule__RangedSpecifics__Group_8__1__Impl )
            // InternalMazeDsl.g:4398:2: rule__RangedSpecifics__Group_8__1__Impl
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
    // InternalMazeDsl.g:4404:1: rule__RangedSpecifics__Group_8__1__Impl : ( ( rule__RangedSpecifics__SplashRadiusAssignment_8_1 ) ) ;
    public final void rule__RangedSpecifics__Group_8__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4408:1: ( ( ( rule__RangedSpecifics__SplashRadiusAssignment_8_1 ) ) )
            // InternalMazeDsl.g:4409:1: ( ( rule__RangedSpecifics__SplashRadiusAssignment_8_1 ) )
            {
            // InternalMazeDsl.g:4409:1: ( ( rule__RangedSpecifics__SplashRadiusAssignment_8_1 ) )
            // InternalMazeDsl.g:4410:2: ( rule__RangedSpecifics__SplashRadiusAssignment_8_1 )
            {
             before(grammarAccess.getRangedSpecificsAccess().getSplashRadiusAssignment_8_1()); 
            // InternalMazeDsl.g:4411:2: ( rule__RangedSpecifics__SplashRadiusAssignment_8_1 )
            // InternalMazeDsl.g:4411:3: rule__RangedSpecifics__SplashRadiusAssignment_8_1
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
    // InternalMazeDsl.g:4420:1: rule__PatrolConfig__Group__0 : rule__PatrolConfig__Group__0__Impl rule__PatrolConfig__Group__1 ;
    public final void rule__PatrolConfig__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4424:1: ( rule__PatrolConfig__Group__0__Impl rule__PatrolConfig__Group__1 )
            // InternalMazeDsl.g:4425:2: rule__PatrolConfig__Group__0__Impl rule__PatrolConfig__Group__1
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
    // InternalMazeDsl.g:4432:1: rule__PatrolConfig__Group__0__Impl : ( 'patrol' ) ;
    public final void rule__PatrolConfig__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4436:1: ( ( 'patrol' ) )
            // InternalMazeDsl.g:4437:1: ( 'patrol' )
            {
            // InternalMazeDsl.g:4437:1: ( 'patrol' )
            // InternalMazeDsl.g:4438:2: 'patrol'
            {
             before(grammarAccess.getPatrolConfigAccess().getPatrolKeyword_0()); 
            match(input,59,FOLLOW_2); 
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
    // InternalMazeDsl.g:4447:1: rule__PatrolConfig__Group__1 : rule__PatrolConfig__Group__1__Impl rule__PatrolConfig__Group__2 ;
    public final void rule__PatrolConfig__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4451:1: ( rule__PatrolConfig__Group__1__Impl rule__PatrolConfig__Group__2 )
            // InternalMazeDsl.g:4452:2: rule__PatrolConfig__Group__1__Impl rule__PatrolConfig__Group__2
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
    // InternalMazeDsl.g:4459:1: rule__PatrolConfig__Group__1__Impl : ( ( rule__PatrolConfig__NameAssignment_1 ) ) ;
    public final void rule__PatrolConfig__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4463:1: ( ( ( rule__PatrolConfig__NameAssignment_1 ) ) )
            // InternalMazeDsl.g:4464:1: ( ( rule__PatrolConfig__NameAssignment_1 ) )
            {
            // InternalMazeDsl.g:4464:1: ( ( rule__PatrolConfig__NameAssignment_1 ) )
            // InternalMazeDsl.g:4465:2: ( rule__PatrolConfig__NameAssignment_1 )
            {
             before(grammarAccess.getPatrolConfigAccess().getNameAssignment_1()); 
            // InternalMazeDsl.g:4466:2: ( rule__PatrolConfig__NameAssignment_1 )
            // InternalMazeDsl.g:4466:3: rule__PatrolConfig__NameAssignment_1
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
    // InternalMazeDsl.g:4474:1: rule__PatrolConfig__Group__2 : rule__PatrolConfig__Group__2__Impl rule__PatrolConfig__Group__3 ;
    public final void rule__PatrolConfig__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4478:1: ( rule__PatrolConfig__Group__2__Impl rule__PatrolConfig__Group__3 )
            // InternalMazeDsl.g:4479:2: rule__PatrolConfig__Group__2__Impl rule__PatrolConfig__Group__3
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
    // InternalMazeDsl.g:4486:1: rule__PatrolConfig__Group__2__Impl : ( '{' ) ;
    public final void rule__PatrolConfig__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4490:1: ( ( '{' ) )
            // InternalMazeDsl.g:4491:1: ( '{' )
            {
            // InternalMazeDsl.g:4491:1: ( '{' )
            // InternalMazeDsl.g:4492:2: '{'
            {
             before(grammarAccess.getPatrolConfigAccess().getLeftCurlyBracketKeyword_2()); 
            match(input,68,FOLLOW_2); 
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
    // InternalMazeDsl.g:4501:1: rule__PatrolConfig__Group__3 : rule__PatrolConfig__Group__3__Impl rule__PatrolConfig__Group__4 ;
    public final void rule__PatrolConfig__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4505:1: ( rule__PatrolConfig__Group__3__Impl rule__PatrolConfig__Group__4 )
            // InternalMazeDsl.g:4506:2: rule__PatrolConfig__Group__3__Impl rule__PatrolConfig__Group__4
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
    // InternalMazeDsl.g:4513:1: rule__PatrolConfig__Group__3__Impl : ( ( rule__PatrolConfig__Group_3__0 )? ) ;
    public final void rule__PatrolConfig__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4517:1: ( ( ( rule__PatrolConfig__Group_3__0 )? ) )
            // InternalMazeDsl.g:4518:1: ( ( rule__PatrolConfig__Group_3__0 )? )
            {
            // InternalMazeDsl.g:4518:1: ( ( rule__PatrolConfig__Group_3__0 )? )
            // InternalMazeDsl.g:4519:2: ( rule__PatrolConfig__Group_3__0 )?
            {
             before(grammarAccess.getPatrolConfigAccess().getGroup_3()); 
            // InternalMazeDsl.g:4520:2: ( rule__PatrolConfig__Group_3__0 )?
            int alt43=2;
            int LA43_0 = input.LA(1);

            if ( (LA43_0==104) ) {
                alt43=1;
            }
            switch (alt43) {
                case 1 :
                    // InternalMazeDsl.g:4520:3: rule__PatrolConfig__Group_3__0
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
    // InternalMazeDsl.g:4528:1: rule__PatrolConfig__Group__4 : rule__PatrolConfig__Group__4__Impl rule__PatrolConfig__Group__5 ;
    public final void rule__PatrolConfig__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4532:1: ( rule__PatrolConfig__Group__4__Impl rule__PatrolConfig__Group__5 )
            // InternalMazeDsl.g:4533:2: rule__PatrolConfig__Group__4__Impl rule__PatrolConfig__Group__5
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
    // InternalMazeDsl.g:4540:1: rule__PatrolConfig__Group__4__Impl : ( ( rule__PatrolConfig__ZoneAssignment_4 )? ) ;
    public final void rule__PatrolConfig__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4544:1: ( ( ( rule__PatrolConfig__ZoneAssignment_4 )? ) )
            // InternalMazeDsl.g:4545:1: ( ( rule__PatrolConfig__ZoneAssignment_4 )? )
            {
            // InternalMazeDsl.g:4545:1: ( ( rule__PatrolConfig__ZoneAssignment_4 )? )
            // InternalMazeDsl.g:4546:2: ( rule__PatrolConfig__ZoneAssignment_4 )?
            {
             before(grammarAccess.getPatrolConfigAccess().getZoneAssignment_4()); 
            // InternalMazeDsl.g:4547:2: ( rule__PatrolConfig__ZoneAssignment_4 )?
            int alt44=2;
            int LA44_0 = input.LA(1);

            if ( (LA44_0==106) ) {
                alt44=1;
            }
            switch (alt44) {
                case 1 :
                    // InternalMazeDsl.g:4547:3: rule__PatrolConfig__ZoneAssignment_4
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
    // InternalMazeDsl.g:4555:1: rule__PatrolConfig__Group__5 : rule__PatrolConfig__Group__5__Impl rule__PatrolConfig__Group__6 ;
    public final void rule__PatrolConfig__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4559:1: ( rule__PatrolConfig__Group__5__Impl rule__PatrolConfig__Group__6 )
            // InternalMazeDsl.g:4560:2: rule__PatrolConfig__Group__5__Impl rule__PatrolConfig__Group__6
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
    // InternalMazeDsl.g:4567:1: rule__PatrolConfig__Group__5__Impl : ( 'path' ) ;
    public final void rule__PatrolConfig__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4571:1: ( ( 'path' ) )
            // InternalMazeDsl.g:4572:1: ( 'path' )
            {
            // InternalMazeDsl.g:4572:1: ( 'path' )
            // InternalMazeDsl.g:4573:2: 'path'
            {
             before(grammarAccess.getPatrolConfigAccess().getPathKeyword_5()); 
            match(input,101,FOLLOW_2); 
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
    // InternalMazeDsl.g:4582:1: rule__PatrolConfig__Group__6 : rule__PatrolConfig__Group__6__Impl rule__PatrolConfig__Group__7 ;
    public final void rule__PatrolConfig__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4586:1: ( rule__PatrolConfig__Group__6__Impl rule__PatrolConfig__Group__7 )
            // InternalMazeDsl.g:4587:2: rule__PatrolConfig__Group__6__Impl rule__PatrolConfig__Group__7
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
    // InternalMazeDsl.g:4594:1: rule__PatrolConfig__Group__6__Impl : ( '[' ) ;
    public final void rule__PatrolConfig__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4598:1: ( ( '[' ) )
            // InternalMazeDsl.g:4599:1: ( '[' )
            {
            // InternalMazeDsl.g:4599:1: ( '[' )
            // InternalMazeDsl.g:4600:2: '['
            {
             before(grammarAccess.getPatrolConfigAccess().getLeftSquareBracketKeyword_6()); 
            match(input,102,FOLLOW_2); 
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
    // InternalMazeDsl.g:4609:1: rule__PatrolConfig__Group__7 : rule__PatrolConfig__Group__7__Impl rule__PatrolConfig__Group__8 ;
    public final void rule__PatrolConfig__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4613:1: ( rule__PatrolConfig__Group__7__Impl rule__PatrolConfig__Group__8 )
            // InternalMazeDsl.g:4614:2: rule__PatrolConfig__Group__7__Impl rule__PatrolConfig__Group__8
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
    // InternalMazeDsl.g:4621:1: rule__PatrolConfig__Group__7__Impl : ( ( rule__PatrolConfig__WaypointsAssignment_7 ) ) ;
    public final void rule__PatrolConfig__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4625:1: ( ( ( rule__PatrolConfig__WaypointsAssignment_7 ) ) )
            // InternalMazeDsl.g:4626:1: ( ( rule__PatrolConfig__WaypointsAssignment_7 ) )
            {
            // InternalMazeDsl.g:4626:1: ( ( rule__PatrolConfig__WaypointsAssignment_7 ) )
            // InternalMazeDsl.g:4627:2: ( rule__PatrolConfig__WaypointsAssignment_7 )
            {
             before(grammarAccess.getPatrolConfigAccess().getWaypointsAssignment_7()); 
            // InternalMazeDsl.g:4628:2: ( rule__PatrolConfig__WaypointsAssignment_7 )
            // InternalMazeDsl.g:4628:3: rule__PatrolConfig__WaypointsAssignment_7
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
    // InternalMazeDsl.g:4636:1: rule__PatrolConfig__Group__8 : rule__PatrolConfig__Group__8__Impl rule__PatrolConfig__Group__9 ;
    public final void rule__PatrolConfig__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4640:1: ( rule__PatrolConfig__Group__8__Impl rule__PatrolConfig__Group__9 )
            // InternalMazeDsl.g:4641:2: rule__PatrolConfig__Group__8__Impl rule__PatrolConfig__Group__9
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
    // InternalMazeDsl.g:4648:1: rule__PatrolConfig__Group__8__Impl : ( ( rule__PatrolConfig__Group_8__0 )* ) ;
    public final void rule__PatrolConfig__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4652:1: ( ( ( rule__PatrolConfig__Group_8__0 )* ) )
            // InternalMazeDsl.g:4653:1: ( ( rule__PatrolConfig__Group_8__0 )* )
            {
            // InternalMazeDsl.g:4653:1: ( ( rule__PatrolConfig__Group_8__0 )* )
            // InternalMazeDsl.g:4654:2: ( rule__PatrolConfig__Group_8__0 )*
            {
             before(grammarAccess.getPatrolConfigAccess().getGroup_8()); 
            // InternalMazeDsl.g:4655:2: ( rule__PatrolConfig__Group_8__0 )*
            loop45:
            do {
                int alt45=2;
                int LA45_0 = input.LA(1);

                if ( (LA45_0==105) ) {
                    alt45=1;
                }


                switch (alt45) {
            	case 1 :
            	    // InternalMazeDsl.g:4655:3: rule__PatrolConfig__Group_8__0
            	    {
            	    pushFollow(FOLLOW_36);
            	    rule__PatrolConfig__Group_8__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop45;
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
    // InternalMazeDsl.g:4663:1: rule__PatrolConfig__Group__9 : rule__PatrolConfig__Group__9__Impl rule__PatrolConfig__Group__10 ;
    public final void rule__PatrolConfig__Group__9() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4667:1: ( rule__PatrolConfig__Group__9__Impl rule__PatrolConfig__Group__10 )
            // InternalMazeDsl.g:4668:2: rule__PatrolConfig__Group__9__Impl rule__PatrolConfig__Group__10
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
    // InternalMazeDsl.g:4675:1: rule__PatrolConfig__Group__9__Impl : ( ']' ) ;
    public final void rule__PatrolConfig__Group__9__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4679:1: ( ( ']' ) )
            // InternalMazeDsl.g:4680:1: ( ']' )
            {
            // InternalMazeDsl.g:4680:1: ( ']' )
            // InternalMazeDsl.g:4681:2: ']'
            {
             before(grammarAccess.getPatrolConfigAccess().getRightSquareBracketKeyword_9()); 
            match(input,103,FOLLOW_2); 
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
    // InternalMazeDsl.g:4690:1: rule__PatrolConfig__Group__10 : rule__PatrolConfig__Group__10__Impl ;
    public final void rule__PatrolConfig__Group__10() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4694:1: ( rule__PatrolConfig__Group__10__Impl )
            // InternalMazeDsl.g:4695:2: rule__PatrolConfig__Group__10__Impl
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
    // InternalMazeDsl.g:4701:1: rule__PatrolConfig__Group__10__Impl : ( '}' ) ;
    public final void rule__PatrolConfig__Group__10__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4705:1: ( ( '}' ) )
            // InternalMazeDsl.g:4706:1: ( '}' )
            {
            // InternalMazeDsl.g:4706:1: ( '}' )
            // InternalMazeDsl.g:4707:2: '}'
            {
             before(grammarAccess.getPatrolConfigAccess().getRightCurlyBracketKeyword_10()); 
            match(input,69,FOLLOW_2); 
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
    // InternalMazeDsl.g:4717:1: rule__PatrolConfig__Group_3__0 : rule__PatrolConfig__Group_3__0__Impl rule__PatrolConfig__Group_3__1 ;
    public final void rule__PatrolConfig__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4721:1: ( rule__PatrolConfig__Group_3__0__Impl rule__PatrolConfig__Group_3__1 )
            // InternalMazeDsl.g:4722:2: rule__PatrolConfig__Group_3__0__Impl rule__PatrolConfig__Group_3__1
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
    // InternalMazeDsl.g:4729:1: rule__PatrolConfig__Group_3__0__Impl : ( 'visionRange' ) ;
    public final void rule__PatrolConfig__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4733:1: ( ( 'visionRange' ) )
            // InternalMazeDsl.g:4734:1: ( 'visionRange' )
            {
            // InternalMazeDsl.g:4734:1: ( 'visionRange' )
            // InternalMazeDsl.g:4735:2: 'visionRange'
            {
             before(grammarAccess.getPatrolConfigAccess().getVisionRangeKeyword_3_0()); 
            match(input,104,FOLLOW_2); 
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
    // InternalMazeDsl.g:4744:1: rule__PatrolConfig__Group_3__1 : rule__PatrolConfig__Group_3__1__Impl ;
    public final void rule__PatrolConfig__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4748:1: ( rule__PatrolConfig__Group_3__1__Impl )
            // InternalMazeDsl.g:4749:2: rule__PatrolConfig__Group_3__1__Impl
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
    // InternalMazeDsl.g:4755:1: rule__PatrolConfig__Group_3__1__Impl : ( ( rule__PatrolConfig__VisionRangeAssignment_3_1 ) ) ;
    public final void rule__PatrolConfig__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4759:1: ( ( ( rule__PatrolConfig__VisionRangeAssignment_3_1 ) ) )
            // InternalMazeDsl.g:4760:1: ( ( rule__PatrolConfig__VisionRangeAssignment_3_1 ) )
            {
            // InternalMazeDsl.g:4760:1: ( ( rule__PatrolConfig__VisionRangeAssignment_3_1 ) )
            // InternalMazeDsl.g:4761:2: ( rule__PatrolConfig__VisionRangeAssignment_3_1 )
            {
             before(grammarAccess.getPatrolConfigAccess().getVisionRangeAssignment_3_1()); 
            // InternalMazeDsl.g:4762:2: ( rule__PatrolConfig__VisionRangeAssignment_3_1 )
            // InternalMazeDsl.g:4762:3: rule__PatrolConfig__VisionRangeAssignment_3_1
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
    // InternalMazeDsl.g:4771:1: rule__PatrolConfig__Group_8__0 : rule__PatrolConfig__Group_8__0__Impl rule__PatrolConfig__Group_8__1 ;
    public final void rule__PatrolConfig__Group_8__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4775:1: ( rule__PatrolConfig__Group_8__0__Impl rule__PatrolConfig__Group_8__1 )
            // InternalMazeDsl.g:4776:2: rule__PatrolConfig__Group_8__0__Impl rule__PatrolConfig__Group_8__1
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
    // InternalMazeDsl.g:4783:1: rule__PatrolConfig__Group_8__0__Impl : ( ',' ) ;
    public final void rule__PatrolConfig__Group_8__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4787:1: ( ( ',' ) )
            // InternalMazeDsl.g:4788:1: ( ',' )
            {
            // InternalMazeDsl.g:4788:1: ( ',' )
            // InternalMazeDsl.g:4789:2: ','
            {
             before(grammarAccess.getPatrolConfigAccess().getCommaKeyword_8_0()); 
            match(input,105,FOLLOW_2); 
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
    // InternalMazeDsl.g:4798:1: rule__PatrolConfig__Group_8__1 : rule__PatrolConfig__Group_8__1__Impl ;
    public final void rule__PatrolConfig__Group_8__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4802:1: ( rule__PatrolConfig__Group_8__1__Impl )
            // InternalMazeDsl.g:4803:2: rule__PatrolConfig__Group_8__1__Impl
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
    // InternalMazeDsl.g:4809:1: rule__PatrolConfig__Group_8__1__Impl : ( ( rule__PatrolConfig__WaypointsAssignment_8_1 ) ) ;
    public final void rule__PatrolConfig__Group_8__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4813:1: ( ( ( rule__PatrolConfig__WaypointsAssignment_8_1 ) ) )
            // InternalMazeDsl.g:4814:1: ( ( rule__PatrolConfig__WaypointsAssignment_8_1 ) )
            {
            // InternalMazeDsl.g:4814:1: ( ( rule__PatrolConfig__WaypointsAssignment_8_1 ) )
            // InternalMazeDsl.g:4815:2: ( rule__PatrolConfig__WaypointsAssignment_8_1 )
            {
             before(grammarAccess.getPatrolConfigAccess().getWaypointsAssignment_8_1()); 
            // InternalMazeDsl.g:4816:2: ( rule__PatrolConfig__WaypointsAssignment_8_1 )
            // InternalMazeDsl.g:4816:3: rule__PatrolConfig__WaypointsAssignment_8_1
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
    // InternalMazeDsl.g:4825:1: rule__PatrolZoneConfig__Group__0 : rule__PatrolZoneConfig__Group__0__Impl rule__PatrolZoneConfig__Group__1 ;
    public final void rule__PatrolZoneConfig__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4829:1: ( rule__PatrolZoneConfig__Group__0__Impl rule__PatrolZoneConfig__Group__1 )
            // InternalMazeDsl.g:4830:2: rule__PatrolZoneConfig__Group__0__Impl rule__PatrolZoneConfig__Group__1
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
    // InternalMazeDsl.g:4837:1: rule__PatrolZoneConfig__Group__0__Impl : ( 'zone' ) ;
    public final void rule__PatrolZoneConfig__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4841:1: ( ( 'zone' ) )
            // InternalMazeDsl.g:4842:1: ( 'zone' )
            {
            // InternalMazeDsl.g:4842:1: ( 'zone' )
            // InternalMazeDsl.g:4843:2: 'zone'
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getZoneKeyword_0()); 
            match(input,106,FOLLOW_2); 
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
    // InternalMazeDsl.g:4852:1: rule__PatrolZoneConfig__Group__1 : rule__PatrolZoneConfig__Group__1__Impl rule__PatrolZoneConfig__Group__2 ;
    public final void rule__PatrolZoneConfig__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4856:1: ( rule__PatrolZoneConfig__Group__1__Impl rule__PatrolZoneConfig__Group__2 )
            // InternalMazeDsl.g:4857:2: rule__PatrolZoneConfig__Group__1__Impl rule__PatrolZoneConfig__Group__2
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
    // InternalMazeDsl.g:4864:1: rule__PatrolZoneConfig__Group__1__Impl : ( '{' ) ;
    public final void rule__PatrolZoneConfig__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4868:1: ( ( '{' ) )
            // InternalMazeDsl.g:4869:1: ( '{' )
            {
            // InternalMazeDsl.g:4869:1: ( '{' )
            // InternalMazeDsl.g:4870:2: '{'
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getLeftCurlyBracketKeyword_1()); 
            match(input,68,FOLLOW_2); 
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
    // InternalMazeDsl.g:4879:1: rule__PatrolZoneConfig__Group__2 : rule__PatrolZoneConfig__Group__2__Impl rule__PatrolZoneConfig__Group__3 ;
    public final void rule__PatrolZoneConfig__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4883:1: ( rule__PatrolZoneConfig__Group__2__Impl rule__PatrolZoneConfig__Group__3 )
            // InternalMazeDsl.g:4884:2: rule__PatrolZoneConfig__Group__2__Impl rule__PatrolZoneConfig__Group__3
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
    // InternalMazeDsl.g:4891:1: rule__PatrolZoneConfig__Group__2__Impl : ( 'topLeft' ) ;
    public final void rule__PatrolZoneConfig__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4895:1: ( ( 'topLeft' ) )
            // InternalMazeDsl.g:4896:1: ( 'topLeft' )
            {
            // InternalMazeDsl.g:4896:1: ( 'topLeft' )
            // InternalMazeDsl.g:4897:2: 'topLeft'
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getTopLeftKeyword_2()); 
            match(input,107,FOLLOW_2); 
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
    // InternalMazeDsl.g:4906:1: rule__PatrolZoneConfig__Group__3 : rule__PatrolZoneConfig__Group__3__Impl rule__PatrolZoneConfig__Group__4 ;
    public final void rule__PatrolZoneConfig__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4910:1: ( rule__PatrolZoneConfig__Group__3__Impl rule__PatrolZoneConfig__Group__4 )
            // InternalMazeDsl.g:4911:2: rule__PatrolZoneConfig__Group__3__Impl rule__PatrolZoneConfig__Group__4
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
    // InternalMazeDsl.g:4918:1: rule__PatrolZoneConfig__Group__3__Impl : ( '(' ) ;
    public final void rule__PatrolZoneConfig__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4922:1: ( ( '(' ) )
            // InternalMazeDsl.g:4923:1: ( '(' )
            {
            // InternalMazeDsl.g:4923:1: ( '(' )
            // InternalMazeDsl.g:4924:2: '('
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getLeftParenthesisKeyword_3()); 
            match(input,108,FOLLOW_2); 
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
    // InternalMazeDsl.g:4933:1: rule__PatrolZoneConfig__Group__4 : rule__PatrolZoneConfig__Group__4__Impl rule__PatrolZoneConfig__Group__5 ;
    public final void rule__PatrolZoneConfig__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4937:1: ( rule__PatrolZoneConfig__Group__4__Impl rule__PatrolZoneConfig__Group__5 )
            // InternalMazeDsl.g:4938:2: rule__PatrolZoneConfig__Group__4__Impl rule__PatrolZoneConfig__Group__5
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
    // InternalMazeDsl.g:4945:1: rule__PatrolZoneConfig__Group__4__Impl : ( ( rule__PatrolZoneConfig__TopLeftXAssignment_4 ) ) ;
    public final void rule__PatrolZoneConfig__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4949:1: ( ( ( rule__PatrolZoneConfig__TopLeftXAssignment_4 ) ) )
            // InternalMazeDsl.g:4950:1: ( ( rule__PatrolZoneConfig__TopLeftXAssignment_4 ) )
            {
            // InternalMazeDsl.g:4950:1: ( ( rule__PatrolZoneConfig__TopLeftXAssignment_4 ) )
            // InternalMazeDsl.g:4951:2: ( rule__PatrolZoneConfig__TopLeftXAssignment_4 )
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getTopLeftXAssignment_4()); 
            // InternalMazeDsl.g:4952:2: ( rule__PatrolZoneConfig__TopLeftXAssignment_4 )
            // InternalMazeDsl.g:4952:3: rule__PatrolZoneConfig__TopLeftXAssignment_4
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
    // InternalMazeDsl.g:4960:1: rule__PatrolZoneConfig__Group__5 : rule__PatrolZoneConfig__Group__5__Impl rule__PatrolZoneConfig__Group__6 ;
    public final void rule__PatrolZoneConfig__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4964:1: ( rule__PatrolZoneConfig__Group__5__Impl rule__PatrolZoneConfig__Group__6 )
            // InternalMazeDsl.g:4965:2: rule__PatrolZoneConfig__Group__5__Impl rule__PatrolZoneConfig__Group__6
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
    // InternalMazeDsl.g:4972:1: rule__PatrolZoneConfig__Group__5__Impl : ( ',' ) ;
    public final void rule__PatrolZoneConfig__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4976:1: ( ( ',' ) )
            // InternalMazeDsl.g:4977:1: ( ',' )
            {
            // InternalMazeDsl.g:4977:1: ( ',' )
            // InternalMazeDsl.g:4978:2: ','
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getCommaKeyword_5()); 
            match(input,105,FOLLOW_2); 
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
    // InternalMazeDsl.g:4987:1: rule__PatrolZoneConfig__Group__6 : rule__PatrolZoneConfig__Group__6__Impl rule__PatrolZoneConfig__Group__7 ;
    public final void rule__PatrolZoneConfig__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4991:1: ( rule__PatrolZoneConfig__Group__6__Impl rule__PatrolZoneConfig__Group__7 )
            // InternalMazeDsl.g:4992:2: rule__PatrolZoneConfig__Group__6__Impl rule__PatrolZoneConfig__Group__7
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
    // InternalMazeDsl.g:4999:1: rule__PatrolZoneConfig__Group__6__Impl : ( ( rule__PatrolZoneConfig__TopLeftYAssignment_6 ) ) ;
    public final void rule__PatrolZoneConfig__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5003:1: ( ( ( rule__PatrolZoneConfig__TopLeftYAssignment_6 ) ) )
            // InternalMazeDsl.g:5004:1: ( ( rule__PatrolZoneConfig__TopLeftYAssignment_6 ) )
            {
            // InternalMazeDsl.g:5004:1: ( ( rule__PatrolZoneConfig__TopLeftYAssignment_6 ) )
            // InternalMazeDsl.g:5005:2: ( rule__PatrolZoneConfig__TopLeftYAssignment_6 )
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getTopLeftYAssignment_6()); 
            // InternalMazeDsl.g:5006:2: ( rule__PatrolZoneConfig__TopLeftYAssignment_6 )
            // InternalMazeDsl.g:5006:3: rule__PatrolZoneConfig__TopLeftYAssignment_6
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
    // InternalMazeDsl.g:5014:1: rule__PatrolZoneConfig__Group__7 : rule__PatrolZoneConfig__Group__7__Impl rule__PatrolZoneConfig__Group__8 ;
    public final void rule__PatrolZoneConfig__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5018:1: ( rule__PatrolZoneConfig__Group__7__Impl rule__PatrolZoneConfig__Group__8 )
            // InternalMazeDsl.g:5019:2: rule__PatrolZoneConfig__Group__7__Impl rule__PatrolZoneConfig__Group__8
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
    // InternalMazeDsl.g:5026:1: rule__PatrolZoneConfig__Group__7__Impl : ( ')' ) ;
    public final void rule__PatrolZoneConfig__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5030:1: ( ( ')' ) )
            // InternalMazeDsl.g:5031:1: ( ')' )
            {
            // InternalMazeDsl.g:5031:1: ( ')' )
            // InternalMazeDsl.g:5032:2: ')'
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getRightParenthesisKeyword_7()); 
            match(input,109,FOLLOW_2); 
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
    // InternalMazeDsl.g:5041:1: rule__PatrolZoneConfig__Group__8 : rule__PatrolZoneConfig__Group__8__Impl rule__PatrolZoneConfig__Group__9 ;
    public final void rule__PatrolZoneConfig__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5045:1: ( rule__PatrolZoneConfig__Group__8__Impl rule__PatrolZoneConfig__Group__9 )
            // InternalMazeDsl.g:5046:2: rule__PatrolZoneConfig__Group__8__Impl rule__PatrolZoneConfig__Group__9
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
    // InternalMazeDsl.g:5053:1: rule__PatrolZoneConfig__Group__8__Impl : ( 'width' ) ;
    public final void rule__PatrolZoneConfig__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5057:1: ( ( 'width' ) )
            // InternalMazeDsl.g:5058:1: ( 'width' )
            {
            // InternalMazeDsl.g:5058:1: ( 'width' )
            // InternalMazeDsl.g:5059:2: 'width'
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getWidthKeyword_8()); 
            match(input,110,FOLLOW_2); 
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
    // InternalMazeDsl.g:5068:1: rule__PatrolZoneConfig__Group__9 : rule__PatrolZoneConfig__Group__9__Impl rule__PatrolZoneConfig__Group__10 ;
    public final void rule__PatrolZoneConfig__Group__9() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5072:1: ( rule__PatrolZoneConfig__Group__9__Impl rule__PatrolZoneConfig__Group__10 )
            // InternalMazeDsl.g:5073:2: rule__PatrolZoneConfig__Group__9__Impl rule__PatrolZoneConfig__Group__10
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
    // InternalMazeDsl.g:5080:1: rule__PatrolZoneConfig__Group__9__Impl : ( ( rule__PatrolZoneConfig__WidthAssignment_9 ) ) ;
    public final void rule__PatrolZoneConfig__Group__9__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5084:1: ( ( ( rule__PatrolZoneConfig__WidthAssignment_9 ) ) )
            // InternalMazeDsl.g:5085:1: ( ( rule__PatrolZoneConfig__WidthAssignment_9 ) )
            {
            // InternalMazeDsl.g:5085:1: ( ( rule__PatrolZoneConfig__WidthAssignment_9 ) )
            // InternalMazeDsl.g:5086:2: ( rule__PatrolZoneConfig__WidthAssignment_9 )
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getWidthAssignment_9()); 
            // InternalMazeDsl.g:5087:2: ( rule__PatrolZoneConfig__WidthAssignment_9 )
            // InternalMazeDsl.g:5087:3: rule__PatrolZoneConfig__WidthAssignment_9
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
    // InternalMazeDsl.g:5095:1: rule__PatrolZoneConfig__Group__10 : rule__PatrolZoneConfig__Group__10__Impl rule__PatrolZoneConfig__Group__11 ;
    public final void rule__PatrolZoneConfig__Group__10() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5099:1: ( rule__PatrolZoneConfig__Group__10__Impl rule__PatrolZoneConfig__Group__11 )
            // InternalMazeDsl.g:5100:2: rule__PatrolZoneConfig__Group__10__Impl rule__PatrolZoneConfig__Group__11
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
    // InternalMazeDsl.g:5107:1: rule__PatrolZoneConfig__Group__10__Impl : ( 'height' ) ;
    public final void rule__PatrolZoneConfig__Group__10__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5111:1: ( ( 'height' ) )
            // InternalMazeDsl.g:5112:1: ( 'height' )
            {
            // InternalMazeDsl.g:5112:1: ( 'height' )
            // InternalMazeDsl.g:5113:2: 'height'
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getHeightKeyword_10()); 
            match(input,111,FOLLOW_2); 
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
    // InternalMazeDsl.g:5122:1: rule__PatrolZoneConfig__Group__11 : rule__PatrolZoneConfig__Group__11__Impl rule__PatrolZoneConfig__Group__12 ;
    public final void rule__PatrolZoneConfig__Group__11() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5126:1: ( rule__PatrolZoneConfig__Group__11__Impl rule__PatrolZoneConfig__Group__12 )
            // InternalMazeDsl.g:5127:2: rule__PatrolZoneConfig__Group__11__Impl rule__PatrolZoneConfig__Group__12
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
    // InternalMazeDsl.g:5134:1: rule__PatrolZoneConfig__Group__11__Impl : ( ( rule__PatrolZoneConfig__HeightAssignment_11 ) ) ;
    public final void rule__PatrolZoneConfig__Group__11__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5138:1: ( ( ( rule__PatrolZoneConfig__HeightAssignment_11 ) ) )
            // InternalMazeDsl.g:5139:1: ( ( rule__PatrolZoneConfig__HeightAssignment_11 ) )
            {
            // InternalMazeDsl.g:5139:1: ( ( rule__PatrolZoneConfig__HeightAssignment_11 ) )
            // InternalMazeDsl.g:5140:2: ( rule__PatrolZoneConfig__HeightAssignment_11 )
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getHeightAssignment_11()); 
            // InternalMazeDsl.g:5141:2: ( rule__PatrolZoneConfig__HeightAssignment_11 )
            // InternalMazeDsl.g:5141:3: rule__PatrolZoneConfig__HeightAssignment_11
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
    // InternalMazeDsl.g:5149:1: rule__PatrolZoneConfig__Group__12 : rule__PatrolZoneConfig__Group__12__Impl ;
    public final void rule__PatrolZoneConfig__Group__12() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5153:1: ( rule__PatrolZoneConfig__Group__12__Impl )
            // InternalMazeDsl.g:5154:2: rule__PatrolZoneConfig__Group__12__Impl
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
    // InternalMazeDsl.g:5160:1: rule__PatrolZoneConfig__Group__12__Impl : ( '}' ) ;
    public final void rule__PatrolZoneConfig__Group__12__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5164:1: ( ( '}' ) )
            // InternalMazeDsl.g:5165:1: ( '}' )
            {
            // InternalMazeDsl.g:5165:1: ( '}' )
            // InternalMazeDsl.g:5166:2: '}'
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getRightCurlyBracketKeyword_12()); 
            match(input,69,FOLLOW_2); 
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
    // InternalMazeDsl.g:5176:1: rule__Waypoint__Group__0 : rule__Waypoint__Group__0__Impl rule__Waypoint__Group__1 ;
    public final void rule__Waypoint__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5180:1: ( rule__Waypoint__Group__0__Impl rule__Waypoint__Group__1 )
            // InternalMazeDsl.g:5181:2: rule__Waypoint__Group__0__Impl rule__Waypoint__Group__1
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
    // InternalMazeDsl.g:5188:1: rule__Waypoint__Group__0__Impl : ( '(' ) ;
    public final void rule__Waypoint__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5192:1: ( ( '(' ) )
            // InternalMazeDsl.g:5193:1: ( '(' )
            {
            // InternalMazeDsl.g:5193:1: ( '(' )
            // InternalMazeDsl.g:5194:2: '('
            {
             before(grammarAccess.getWaypointAccess().getLeftParenthesisKeyword_0()); 
            match(input,108,FOLLOW_2); 
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
    // InternalMazeDsl.g:5203:1: rule__Waypoint__Group__1 : rule__Waypoint__Group__1__Impl rule__Waypoint__Group__2 ;
    public final void rule__Waypoint__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5207:1: ( rule__Waypoint__Group__1__Impl rule__Waypoint__Group__2 )
            // InternalMazeDsl.g:5208:2: rule__Waypoint__Group__1__Impl rule__Waypoint__Group__2
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
    // InternalMazeDsl.g:5215:1: rule__Waypoint__Group__1__Impl : ( ( rule__Waypoint__XAssignment_1 ) ) ;
    public final void rule__Waypoint__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5219:1: ( ( ( rule__Waypoint__XAssignment_1 ) ) )
            // InternalMazeDsl.g:5220:1: ( ( rule__Waypoint__XAssignment_1 ) )
            {
            // InternalMazeDsl.g:5220:1: ( ( rule__Waypoint__XAssignment_1 ) )
            // InternalMazeDsl.g:5221:2: ( rule__Waypoint__XAssignment_1 )
            {
             before(grammarAccess.getWaypointAccess().getXAssignment_1()); 
            // InternalMazeDsl.g:5222:2: ( rule__Waypoint__XAssignment_1 )
            // InternalMazeDsl.g:5222:3: rule__Waypoint__XAssignment_1
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
    // InternalMazeDsl.g:5230:1: rule__Waypoint__Group__2 : rule__Waypoint__Group__2__Impl rule__Waypoint__Group__3 ;
    public final void rule__Waypoint__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5234:1: ( rule__Waypoint__Group__2__Impl rule__Waypoint__Group__3 )
            // InternalMazeDsl.g:5235:2: rule__Waypoint__Group__2__Impl rule__Waypoint__Group__3
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
    // InternalMazeDsl.g:5242:1: rule__Waypoint__Group__2__Impl : ( ',' ) ;
    public final void rule__Waypoint__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5246:1: ( ( ',' ) )
            // InternalMazeDsl.g:5247:1: ( ',' )
            {
            // InternalMazeDsl.g:5247:1: ( ',' )
            // InternalMazeDsl.g:5248:2: ','
            {
             before(grammarAccess.getWaypointAccess().getCommaKeyword_2()); 
            match(input,105,FOLLOW_2); 
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
    // InternalMazeDsl.g:5257:1: rule__Waypoint__Group__3 : rule__Waypoint__Group__3__Impl rule__Waypoint__Group__4 ;
    public final void rule__Waypoint__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5261:1: ( rule__Waypoint__Group__3__Impl rule__Waypoint__Group__4 )
            // InternalMazeDsl.g:5262:2: rule__Waypoint__Group__3__Impl rule__Waypoint__Group__4
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
    // InternalMazeDsl.g:5269:1: rule__Waypoint__Group__3__Impl : ( ( rule__Waypoint__YAssignment_3 ) ) ;
    public final void rule__Waypoint__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5273:1: ( ( ( rule__Waypoint__YAssignment_3 ) ) )
            // InternalMazeDsl.g:5274:1: ( ( rule__Waypoint__YAssignment_3 ) )
            {
            // InternalMazeDsl.g:5274:1: ( ( rule__Waypoint__YAssignment_3 ) )
            // InternalMazeDsl.g:5275:2: ( rule__Waypoint__YAssignment_3 )
            {
             before(grammarAccess.getWaypointAccess().getYAssignment_3()); 
            // InternalMazeDsl.g:5276:2: ( rule__Waypoint__YAssignment_3 )
            // InternalMazeDsl.g:5276:3: rule__Waypoint__YAssignment_3
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
    // InternalMazeDsl.g:5284:1: rule__Waypoint__Group__4 : rule__Waypoint__Group__4__Impl rule__Waypoint__Group__5 ;
    public final void rule__Waypoint__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5288:1: ( rule__Waypoint__Group__4__Impl rule__Waypoint__Group__5 )
            // InternalMazeDsl.g:5289:2: rule__Waypoint__Group__4__Impl rule__Waypoint__Group__5
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
    // InternalMazeDsl.g:5296:1: rule__Waypoint__Group__4__Impl : ( ')' ) ;
    public final void rule__Waypoint__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5300:1: ( ( ')' ) )
            // InternalMazeDsl.g:5301:1: ( ')' )
            {
            // InternalMazeDsl.g:5301:1: ( ')' )
            // InternalMazeDsl.g:5302:2: ')'
            {
             before(grammarAccess.getWaypointAccess().getRightParenthesisKeyword_4()); 
            match(input,109,FOLLOW_2); 
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
    // InternalMazeDsl.g:5311:1: rule__Waypoint__Group__5 : rule__Waypoint__Group__5__Impl ;
    public final void rule__Waypoint__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5315:1: ( rule__Waypoint__Group__5__Impl )
            // InternalMazeDsl.g:5316:2: rule__Waypoint__Group__5__Impl
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
    // InternalMazeDsl.g:5322:1: rule__Waypoint__Group__5__Impl : ( ( rule__Waypoint__Group_5__0 )? ) ;
    public final void rule__Waypoint__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5326:1: ( ( ( rule__Waypoint__Group_5__0 )? ) )
            // InternalMazeDsl.g:5327:1: ( ( rule__Waypoint__Group_5__0 )? )
            {
            // InternalMazeDsl.g:5327:1: ( ( rule__Waypoint__Group_5__0 )? )
            // InternalMazeDsl.g:5328:2: ( rule__Waypoint__Group_5__0 )?
            {
             before(grammarAccess.getWaypointAccess().getGroup_5()); 
            // InternalMazeDsl.g:5329:2: ( rule__Waypoint__Group_5__0 )?
            int alt46=2;
            int LA46_0 = input.LA(1);

            if ( (LA46_0==112) ) {
                alt46=1;
            }
            switch (alt46) {
                case 1 :
                    // InternalMazeDsl.g:5329:3: rule__Waypoint__Group_5__0
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
    // InternalMazeDsl.g:5338:1: rule__Waypoint__Group_5__0 : rule__Waypoint__Group_5__0__Impl rule__Waypoint__Group_5__1 ;
    public final void rule__Waypoint__Group_5__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5342:1: ( rule__Waypoint__Group_5__0__Impl rule__Waypoint__Group_5__1 )
            // InternalMazeDsl.g:5343:2: rule__Waypoint__Group_5__0__Impl rule__Waypoint__Group_5__1
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
    // InternalMazeDsl.g:5350:1: rule__Waypoint__Group_5__0__Impl : ( ':' ) ;
    public final void rule__Waypoint__Group_5__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5354:1: ( ( ':' ) )
            // InternalMazeDsl.g:5355:1: ( ':' )
            {
            // InternalMazeDsl.g:5355:1: ( ':' )
            // InternalMazeDsl.g:5356:2: ':'
            {
             before(grammarAccess.getWaypointAccess().getColonKeyword_5_0()); 
            match(input,112,FOLLOW_2); 
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
    // InternalMazeDsl.g:5365:1: rule__Waypoint__Group_5__1 : rule__Waypoint__Group_5__1__Impl rule__Waypoint__Group_5__2 ;
    public final void rule__Waypoint__Group_5__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5369:1: ( rule__Waypoint__Group_5__1__Impl rule__Waypoint__Group_5__2 )
            // InternalMazeDsl.g:5370:2: rule__Waypoint__Group_5__1__Impl rule__Waypoint__Group_5__2
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
    // InternalMazeDsl.g:5377:1: rule__Waypoint__Group_5__1__Impl : ( ( rule__Waypoint__WaitTimeAssignment_5_1 ) ) ;
    public final void rule__Waypoint__Group_5__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5381:1: ( ( ( rule__Waypoint__WaitTimeAssignment_5_1 ) ) )
            // InternalMazeDsl.g:5382:1: ( ( rule__Waypoint__WaitTimeAssignment_5_1 ) )
            {
            // InternalMazeDsl.g:5382:1: ( ( rule__Waypoint__WaitTimeAssignment_5_1 ) )
            // InternalMazeDsl.g:5383:2: ( rule__Waypoint__WaitTimeAssignment_5_1 )
            {
             before(grammarAccess.getWaypointAccess().getWaitTimeAssignment_5_1()); 
            // InternalMazeDsl.g:5384:2: ( rule__Waypoint__WaitTimeAssignment_5_1 )
            // InternalMazeDsl.g:5384:3: rule__Waypoint__WaitTimeAssignment_5_1
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
    // InternalMazeDsl.g:5392:1: rule__Waypoint__Group_5__2 : rule__Waypoint__Group_5__2__Impl ;
    public final void rule__Waypoint__Group_5__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5396:1: ( rule__Waypoint__Group_5__2__Impl )
            // InternalMazeDsl.g:5397:2: rule__Waypoint__Group_5__2__Impl
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
    // InternalMazeDsl.g:5403:1: rule__Waypoint__Group_5__2__Impl : ( 'ms' ) ;
    public final void rule__Waypoint__Group_5__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5407:1: ( ( 'ms' ) )
            // InternalMazeDsl.g:5408:1: ( 'ms' )
            {
            // InternalMazeDsl.g:5408:1: ( 'ms' )
            // InternalMazeDsl.g:5409:2: 'ms'
            {
             before(grammarAccess.getWaypointAccess().getMsKeyword_5_2()); 
            match(input,113,FOLLOW_2); 
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
    // InternalMazeDsl.g:5419:1: rule__LootTableConfig__Group__0 : rule__LootTableConfig__Group__0__Impl rule__LootTableConfig__Group__1 ;
    public final void rule__LootTableConfig__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5423:1: ( rule__LootTableConfig__Group__0__Impl rule__LootTableConfig__Group__1 )
            // InternalMazeDsl.g:5424:2: rule__LootTableConfig__Group__0__Impl rule__LootTableConfig__Group__1
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
    // InternalMazeDsl.g:5431:1: rule__LootTableConfig__Group__0__Impl : ( 'loot-table' ) ;
    public final void rule__LootTableConfig__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5435:1: ( ( 'loot-table' ) )
            // InternalMazeDsl.g:5436:1: ( 'loot-table' )
            {
            // InternalMazeDsl.g:5436:1: ( 'loot-table' )
            // InternalMazeDsl.g:5437:2: 'loot-table'
            {
             before(grammarAccess.getLootTableConfigAccess().getLootTableKeyword_0()); 
            match(input,114,FOLLOW_2); 
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
    // InternalMazeDsl.g:5446:1: rule__LootTableConfig__Group__1 : rule__LootTableConfig__Group__1__Impl rule__LootTableConfig__Group__2 ;
    public final void rule__LootTableConfig__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5450:1: ( rule__LootTableConfig__Group__1__Impl rule__LootTableConfig__Group__2 )
            // InternalMazeDsl.g:5451:2: rule__LootTableConfig__Group__1__Impl rule__LootTableConfig__Group__2
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
    // InternalMazeDsl.g:5458:1: rule__LootTableConfig__Group__1__Impl : ( ( rule__LootTableConfig__NameAssignment_1 ) ) ;
    public final void rule__LootTableConfig__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5462:1: ( ( ( rule__LootTableConfig__NameAssignment_1 ) ) )
            // InternalMazeDsl.g:5463:1: ( ( rule__LootTableConfig__NameAssignment_1 ) )
            {
            // InternalMazeDsl.g:5463:1: ( ( rule__LootTableConfig__NameAssignment_1 ) )
            // InternalMazeDsl.g:5464:2: ( rule__LootTableConfig__NameAssignment_1 )
            {
             before(grammarAccess.getLootTableConfigAccess().getNameAssignment_1()); 
            // InternalMazeDsl.g:5465:2: ( rule__LootTableConfig__NameAssignment_1 )
            // InternalMazeDsl.g:5465:3: rule__LootTableConfig__NameAssignment_1
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
    // InternalMazeDsl.g:5473:1: rule__LootTableConfig__Group__2 : rule__LootTableConfig__Group__2__Impl rule__LootTableConfig__Group__3 ;
    public final void rule__LootTableConfig__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5477:1: ( rule__LootTableConfig__Group__2__Impl rule__LootTableConfig__Group__3 )
            // InternalMazeDsl.g:5478:2: rule__LootTableConfig__Group__2__Impl rule__LootTableConfig__Group__3
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
    // InternalMazeDsl.g:5485:1: rule__LootTableConfig__Group__2__Impl : ( '{' ) ;
    public final void rule__LootTableConfig__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5489:1: ( ( '{' ) )
            // InternalMazeDsl.g:5490:1: ( '{' )
            {
            // InternalMazeDsl.g:5490:1: ( '{' )
            // InternalMazeDsl.g:5491:2: '{'
            {
             before(grammarAccess.getLootTableConfigAccess().getLeftCurlyBracketKeyword_2()); 
            match(input,68,FOLLOW_2); 
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
    // InternalMazeDsl.g:5500:1: rule__LootTableConfig__Group__3 : rule__LootTableConfig__Group__3__Impl rule__LootTableConfig__Group__4 ;
    public final void rule__LootTableConfig__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5504:1: ( rule__LootTableConfig__Group__3__Impl rule__LootTableConfig__Group__4 )
            // InternalMazeDsl.g:5505:2: rule__LootTableConfig__Group__3__Impl rule__LootTableConfig__Group__4
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
    // InternalMazeDsl.g:5512:1: rule__LootTableConfig__Group__3__Impl : ( ( rule__LootTableConfig__Group_3__0 )? ) ;
    public final void rule__LootTableConfig__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5516:1: ( ( ( rule__LootTableConfig__Group_3__0 )? ) )
            // InternalMazeDsl.g:5517:1: ( ( rule__LootTableConfig__Group_3__0 )? )
            {
            // InternalMazeDsl.g:5517:1: ( ( rule__LootTableConfig__Group_3__0 )? )
            // InternalMazeDsl.g:5518:2: ( rule__LootTableConfig__Group_3__0 )?
            {
             before(grammarAccess.getLootTableConfigAccess().getGroup_3()); 
            // InternalMazeDsl.g:5519:2: ( rule__LootTableConfig__Group_3__0 )?
            int alt47=2;
            int LA47_0 = input.LA(1);

            if ( (LA47_0==115) ) {
                alt47=1;
            }
            switch (alt47) {
                case 1 :
                    // InternalMazeDsl.g:5519:3: rule__LootTableConfig__Group_3__0
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
    // InternalMazeDsl.g:5527:1: rule__LootTableConfig__Group__4 : rule__LootTableConfig__Group__4__Impl rule__LootTableConfig__Group__5 ;
    public final void rule__LootTableConfig__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5531:1: ( rule__LootTableConfig__Group__4__Impl rule__LootTableConfig__Group__5 )
            // InternalMazeDsl.g:5532:2: rule__LootTableConfig__Group__4__Impl rule__LootTableConfig__Group__5
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
    // InternalMazeDsl.g:5539:1: rule__LootTableConfig__Group__4__Impl : ( ( ( rule__LootTableConfig__ItemsAssignment_4 ) ) ( ( rule__LootTableConfig__ItemsAssignment_4 )* ) ) ;
    public final void rule__LootTableConfig__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5543:1: ( ( ( ( rule__LootTableConfig__ItemsAssignment_4 ) ) ( ( rule__LootTableConfig__ItemsAssignment_4 )* ) ) )
            // InternalMazeDsl.g:5544:1: ( ( ( rule__LootTableConfig__ItemsAssignment_4 ) ) ( ( rule__LootTableConfig__ItemsAssignment_4 )* ) )
            {
            // InternalMazeDsl.g:5544:1: ( ( ( rule__LootTableConfig__ItemsAssignment_4 ) ) ( ( rule__LootTableConfig__ItemsAssignment_4 )* ) )
            // InternalMazeDsl.g:5545:2: ( ( rule__LootTableConfig__ItemsAssignment_4 ) ) ( ( rule__LootTableConfig__ItemsAssignment_4 )* )
            {
            // InternalMazeDsl.g:5545:2: ( ( rule__LootTableConfig__ItemsAssignment_4 ) )
            // InternalMazeDsl.g:5546:3: ( rule__LootTableConfig__ItemsAssignment_4 )
            {
             before(grammarAccess.getLootTableConfigAccess().getItemsAssignment_4()); 
            // InternalMazeDsl.g:5547:3: ( rule__LootTableConfig__ItemsAssignment_4 )
            // InternalMazeDsl.g:5547:4: rule__LootTableConfig__ItemsAssignment_4
            {
            pushFollow(FOLLOW_46);
            rule__LootTableConfig__ItemsAssignment_4();

            state._fsp--;


            }

             after(grammarAccess.getLootTableConfigAccess().getItemsAssignment_4()); 

            }

            // InternalMazeDsl.g:5550:2: ( ( rule__LootTableConfig__ItemsAssignment_4 )* )
            // InternalMazeDsl.g:5551:3: ( rule__LootTableConfig__ItemsAssignment_4 )*
            {
             before(grammarAccess.getLootTableConfigAccess().getItemsAssignment_4()); 
            // InternalMazeDsl.g:5552:3: ( rule__LootTableConfig__ItemsAssignment_4 )*
            loop48:
            do {
                int alt48=2;
                int LA48_0 = input.LA(1);

                if ( (LA48_0==116) ) {
                    alt48=1;
                }


                switch (alt48) {
            	case 1 :
            	    // InternalMazeDsl.g:5552:4: rule__LootTableConfig__ItemsAssignment_4
            	    {
            	    pushFollow(FOLLOW_46);
            	    rule__LootTableConfig__ItemsAssignment_4();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop48;
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
    // InternalMazeDsl.g:5561:1: rule__LootTableConfig__Group__5 : rule__LootTableConfig__Group__5__Impl ;
    public final void rule__LootTableConfig__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5565:1: ( rule__LootTableConfig__Group__5__Impl )
            // InternalMazeDsl.g:5566:2: rule__LootTableConfig__Group__5__Impl
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
    // InternalMazeDsl.g:5572:1: rule__LootTableConfig__Group__5__Impl : ( '}' ) ;
    public final void rule__LootTableConfig__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5576:1: ( ( '}' ) )
            // InternalMazeDsl.g:5577:1: ( '}' )
            {
            // InternalMazeDsl.g:5577:1: ( '}' )
            // InternalMazeDsl.g:5578:2: '}'
            {
             before(grammarAccess.getLootTableConfigAccess().getRightCurlyBracketKeyword_5()); 
            match(input,69,FOLLOW_2); 
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
    // InternalMazeDsl.g:5588:1: rule__LootTableConfig__Group_3__0 : rule__LootTableConfig__Group_3__0__Impl rule__LootTableConfig__Group_3__1 ;
    public final void rule__LootTableConfig__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5592:1: ( rule__LootTableConfig__Group_3__0__Impl rule__LootTableConfig__Group_3__1 )
            // InternalMazeDsl.g:5593:2: rule__LootTableConfig__Group_3__0__Impl rule__LootTableConfig__Group_3__1
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
    // InternalMazeDsl.g:5600:1: rule__LootTableConfig__Group_3__0__Impl : ( 'capacity' ) ;
    public final void rule__LootTableConfig__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5604:1: ( ( 'capacity' ) )
            // InternalMazeDsl.g:5605:1: ( 'capacity' )
            {
            // InternalMazeDsl.g:5605:1: ( 'capacity' )
            // InternalMazeDsl.g:5606:2: 'capacity'
            {
             before(grammarAccess.getLootTableConfigAccess().getCapacityKeyword_3_0()); 
            match(input,115,FOLLOW_2); 
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
    // InternalMazeDsl.g:5615:1: rule__LootTableConfig__Group_3__1 : rule__LootTableConfig__Group_3__1__Impl ;
    public final void rule__LootTableConfig__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5619:1: ( rule__LootTableConfig__Group_3__1__Impl )
            // InternalMazeDsl.g:5620:2: rule__LootTableConfig__Group_3__1__Impl
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
    // InternalMazeDsl.g:5626:1: rule__LootTableConfig__Group_3__1__Impl : ( ( rule__LootTableConfig__CapacityAssignment_3_1 ) ) ;
    public final void rule__LootTableConfig__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5630:1: ( ( ( rule__LootTableConfig__CapacityAssignment_3_1 ) ) )
            // InternalMazeDsl.g:5631:1: ( ( rule__LootTableConfig__CapacityAssignment_3_1 ) )
            {
            // InternalMazeDsl.g:5631:1: ( ( rule__LootTableConfig__CapacityAssignment_3_1 ) )
            // InternalMazeDsl.g:5632:2: ( rule__LootTableConfig__CapacityAssignment_3_1 )
            {
             before(grammarAccess.getLootTableConfigAccess().getCapacityAssignment_3_1()); 
            // InternalMazeDsl.g:5633:2: ( rule__LootTableConfig__CapacityAssignment_3_1 )
            // InternalMazeDsl.g:5633:3: rule__LootTableConfig__CapacityAssignment_3_1
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
    // InternalMazeDsl.g:5642:1: rule__LootItemConfig__Group__0 : rule__LootItemConfig__Group__0__Impl rule__LootItemConfig__Group__1 ;
    public final void rule__LootItemConfig__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5646:1: ( rule__LootItemConfig__Group__0__Impl rule__LootItemConfig__Group__1 )
            // InternalMazeDsl.g:5647:2: rule__LootItemConfig__Group__0__Impl rule__LootItemConfig__Group__1
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
    // InternalMazeDsl.g:5654:1: rule__LootItemConfig__Group__0__Impl : ( 'item' ) ;
    public final void rule__LootItemConfig__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5658:1: ( ( 'item' ) )
            // InternalMazeDsl.g:5659:1: ( 'item' )
            {
            // InternalMazeDsl.g:5659:1: ( 'item' )
            // InternalMazeDsl.g:5660:2: 'item'
            {
             before(grammarAccess.getLootItemConfigAccess().getItemKeyword_0()); 
            match(input,116,FOLLOW_2); 
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
    // InternalMazeDsl.g:5669:1: rule__LootItemConfig__Group__1 : rule__LootItemConfig__Group__1__Impl rule__LootItemConfig__Group__2 ;
    public final void rule__LootItemConfig__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5673:1: ( rule__LootItemConfig__Group__1__Impl rule__LootItemConfig__Group__2 )
            // InternalMazeDsl.g:5674:2: rule__LootItemConfig__Group__1__Impl rule__LootItemConfig__Group__2
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
    // InternalMazeDsl.g:5681:1: rule__LootItemConfig__Group__1__Impl : ( ( rule__LootItemConfig__NameAssignment_1 ) ) ;
    public final void rule__LootItemConfig__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5685:1: ( ( ( rule__LootItemConfig__NameAssignment_1 ) ) )
            // InternalMazeDsl.g:5686:1: ( ( rule__LootItemConfig__NameAssignment_1 ) )
            {
            // InternalMazeDsl.g:5686:1: ( ( rule__LootItemConfig__NameAssignment_1 ) )
            // InternalMazeDsl.g:5687:2: ( rule__LootItemConfig__NameAssignment_1 )
            {
             before(grammarAccess.getLootItemConfigAccess().getNameAssignment_1()); 
            // InternalMazeDsl.g:5688:2: ( rule__LootItemConfig__NameAssignment_1 )
            // InternalMazeDsl.g:5688:3: rule__LootItemConfig__NameAssignment_1
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
    // InternalMazeDsl.g:5696:1: rule__LootItemConfig__Group__2 : rule__LootItemConfig__Group__2__Impl rule__LootItemConfig__Group__3 ;
    public final void rule__LootItemConfig__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5700:1: ( rule__LootItemConfig__Group__2__Impl rule__LootItemConfig__Group__3 )
            // InternalMazeDsl.g:5701:2: rule__LootItemConfig__Group__2__Impl rule__LootItemConfig__Group__3
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
    // InternalMazeDsl.g:5708:1: rule__LootItemConfig__Group__2__Impl : ( '{' ) ;
    public final void rule__LootItemConfig__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5712:1: ( ( '{' ) )
            // InternalMazeDsl.g:5713:1: ( '{' )
            {
            // InternalMazeDsl.g:5713:1: ( '{' )
            // InternalMazeDsl.g:5714:2: '{'
            {
             before(grammarAccess.getLootItemConfigAccess().getLeftCurlyBracketKeyword_2()); 
            match(input,68,FOLLOW_2); 
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
    // InternalMazeDsl.g:5723:1: rule__LootItemConfig__Group__3 : rule__LootItemConfig__Group__3__Impl rule__LootItemConfig__Group__4 ;
    public final void rule__LootItemConfig__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5727:1: ( rule__LootItemConfig__Group__3__Impl rule__LootItemConfig__Group__4 )
            // InternalMazeDsl.g:5728:2: rule__LootItemConfig__Group__3__Impl rule__LootItemConfig__Group__4
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
    // InternalMazeDsl.g:5735:1: rule__LootItemConfig__Group__3__Impl : ( 'type' ) ;
    public final void rule__LootItemConfig__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5739:1: ( ( 'type' ) )
            // InternalMazeDsl.g:5740:1: ( 'type' )
            {
            // InternalMazeDsl.g:5740:1: ( 'type' )
            // InternalMazeDsl.g:5741:2: 'type'
            {
             before(grammarAccess.getLootItemConfigAccess().getTypeKeyword_3()); 
            match(input,80,FOLLOW_2); 
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
    // InternalMazeDsl.g:5750:1: rule__LootItemConfig__Group__4 : rule__LootItemConfig__Group__4__Impl rule__LootItemConfig__Group__5 ;
    public final void rule__LootItemConfig__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5754:1: ( rule__LootItemConfig__Group__4__Impl rule__LootItemConfig__Group__5 )
            // InternalMazeDsl.g:5755:2: rule__LootItemConfig__Group__4__Impl rule__LootItemConfig__Group__5
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
    // InternalMazeDsl.g:5762:1: rule__LootItemConfig__Group__4__Impl : ( ( rule__LootItemConfig__TypeAssignment_4 ) ) ;
    public final void rule__LootItemConfig__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5766:1: ( ( ( rule__LootItemConfig__TypeAssignment_4 ) ) )
            // InternalMazeDsl.g:5767:1: ( ( rule__LootItemConfig__TypeAssignment_4 ) )
            {
            // InternalMazeDsl.g:5767:1: ( ( rule__LootItemConfig__TypeAssignment_4 ) )
            // InternalMazeDsl.g:5768:2: ( rule__LootItemConfig__TypeAssignment_4 )
            {
             before(grammarAccess.getLootItemConfigAccess().getTypeAssignment_4()); 
            // InternalMazeDsl.g:5769:2: ( rule__LootItemConfig__TypeAssignment_4 )
            // InternalMazeDsl.g:5769:3: rule__LootItemConfig__TypeAssignment_4
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
    // InternalMazeDsl.g:5777:1: rule__LootItemConfig__Group__5 : rule__LootItemConfig__Group__5__Impl rule__LootItemConfig__Group__6 ;
    public final void rule__LootItemConfig__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5781:1: ( rule__LootItemConfig__Group__5__Impl rule__LootItemConfig__Group__6 )
            // InternalMazeDsl.g:5782:2: rule__LootItemConfig__Group__5__Impl rule__LootItemConfig__Group__6
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
    // InternalMazeDsl.g:5789:1: rule__LootItemConfig__Group__5__Impl : ( 'value' ) ;
    public final void rule__LootItemConfig__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5793:1: ( ( 'value' ) )
            // InternalMazeDsl.g:5794:1: ( 'value' )
            {
            // InternalMazeDsl.g:5794:1: ( 'value' )
            // InternalMazeDsl.g:5795:2: 'value'
            {
             before(grammarAccess.getLootItemConfigAccess().getValueKeyword_5()); 
            match(input,117,FOLLOW_2); 
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
    // InternalMazeDsl.g:5804:1: rule__LootItemConfig__Group__6 : rule__LootItemConfig__Group__6__Impl rule__LootItemConfig__Group__7 ;
    public final void rule__LootItemConfig__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5808:1: ( rule__LootItemConfig__Group__6__Impl rule__LootItemConfig__Group__7 )
            // InternalMazeDsl.g:5809:2: rule__LootItemConfig__Group__6__Impl rule__LootItemConfig__Group__7
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
    // InternalMazeDsl.g:5816:1: rule__LootItemConfig__Group__6__Impl : ( ( rule__LootItemConfig__ValueAssignment_6 ) ) ;
    public final void rule__LootItemConfig__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5820:1: ( ( ( rule__LootItemConfig__ValueAssignment_6 ) ) )
            // InternalMazeDsl.g:5821:1: ( ( rule__LootItemConfig__ValueAssignment_6 ) )
            {
            // InternalMazeDsl.g:5821:1: ( ( rule__LootItemConfig__ValueAssignment_6 ) )
            // InternalMazeDsl.g:5822:2: ( rule__LootItemConfig__ValueAssignment_6 )
            {
             before(grammarAccess.getLootItemConfigAccess().getValueAssignment_6()); 
            // InternalMazeDsl.g:5823:2: ( rule__LootItemConfig__ValueAssignment_6 )
            // InternalMazeDsl.g:5823:3: rule__LootItemConfig__ValueAssignment_6
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
    // InternalMazeDsl.g:5831:1: rule__LootItemConfig__Group__7 : rule__LootItemConfig__Group__7__Impl rule__LootItemConfig__Group__8 ;
    public final void rule__LootItemConfig__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5835:1: ( rule__LootItemConfig__Group__7__Impl rule__LootItemConfig__Group__8 )
            // InternalMazeDsl.g:5836:2: rule__LootItemConfig__Group__7__Impl rule__LootItemConfig__Group__8
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
    // InternalMazeDsl.g:5843:1: rule__LootItemConfig__Group__7__Impl : ( ( rule__LootItemConfig__Group_7__0 )? ) ;
    public final void rule__LootItemConfig__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5847:1: ( ( ( rule__LootItemConfig__Group_7__0 )? ) )
            // InternalMazeDsl.g:5848:1: ( ( rule__LootItemConfig__Group_7__0 )? )
            {
            // InternalMazeDsl.g:5848:1: ( ( rule__LootItemConfig__Group_7__0 )? )
            // InternalMazeDsl.g:5849:2: ( rule__LootItemConfig__Group_7__0 )?
            {
             before(grammarAccess.getLootItemConfigAccess().getGroup_7()); 
            // InternalMazeDsl.g:5850:2: ( rule__LootItemConfig__Group_7__0 )?
            int alt49=2;
            int LA49_0 = input.LA(1);

            if ( (LA49_0==118) ) {
                alt49=1;
            }
            switch (alt49) {
                case 1 :
                    // InternalMazeDsl.g:5850:3: rule__LootItemConfig__Group_7__0
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
    // InternalMazeDsl.g:5858:1: rule__LootItemConfig__Group__8 : rule__LootItemConfig__Group__8__Impl ;
    public final void rule__LootItemConfig__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5862:1: ( rule__LootItemConfig__Group__8__Impl )
            // InternalMazeDsl.g:5863:2: rule__LootItemConfig__Group__8__Impl
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
    // InternalMazeDsl.g:5869:1: rule__LootItemConfig__Group__8__Impl : ( '}' ) ;
    public final void rule__LootItemConfig__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5873:1: ( ( '}' ) )
            // InternalMazeDsl.g:5874:1: ( '}' )
            {
            // InternalMazeDsl.g:5874:1: ( '}' )
            // InternalMazeDsl.g:5875:2: '}'
            {
             before(grammarAccess.getLootItemConfigAccess().getRightCurlyBracketKeyword_8()); 
            match(input,69,FOLLOW_2); 
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
    // InternalMazeDsl.g:5885:1: rule__LootItemConfig__Group_7__0 : rule__LootItemConfig__Group_7__0__Impl rule__LootItemConfig__Group_7__1 ;
    public final void rule__LootItemConfig__Group_7__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5889:1: ( rule__LootItemConfig__Group_7__0__Impl rule__LootItemConfig__Group_7__1 )
            // InternalMazeDsl.g:5890:2: rule__LootItemConfig__Group_7__0__Impl rule__LootItemConfig__Group_7__1
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
    // InternalMazeDsl.g:5897:1: rule__LootItemConfig__Group_7__0__Impl : ( 'weight' ) ;
    public final void rule__LootItemConfig__Group_7__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5901:1: ( ( 'weight' ) )
            // InternalMazeDsl.g:5902:1: ( 'weight' )
            {
            // InternalMazeDsl.g:5902:1: ( 'weight' )
            // InternalMazeDsl.g:5903:2: 'weight'
            {
             before(grammarAccess.getLootItemConfigAccess().getWeightKeyword_7_0()); 
            match(input,118,FOLLOW_2); 
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
    // InternalMazeDsl.g:5912:1: rule__LootItemConfig__Group_7__1 : rule__LootItemConfig__Group_7__1__Impl ;
    public final void rule__LootItemConfig__Group_7__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5916:1: ( rule__LootItemConfig__Group_7__1__Impl )
            // InternalMazeDsl.g:5917:2: rule__LootItemConfig__Group_7__1__Impl
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
    // InternalMazeDsl.g:5923:1: rule__LootItemConfig__Group_7__1__Impl : ( ( rule__LootItemConfig__WeightAssignment_7_1 ) ) ;
    public final void rule__LootItemConfig__Group_7__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5927:1: ( ( ( rule__LootItemConfig__WeightAssignment_7_1 ) ) )
            // InternalMazeDsl.g:5928:1: ( ( rule__LootItemConfig__WeightAssignment_7_1 ) )
            {
            // InternalMazeDsl.g:5928:1: ( ( rule__LootItemConfig__WeightAssignment_7_1 ) )
            // InternalMazeDsl.g:5929:2: ( rule__LootItemConfig__WeightAssignment_7_1 )
            {
             before(grammarAccess.getLootItemConfigAccess().getWeightAssignment_7_1()); 
            // InternalMazeDsl.g:5930:2: ( rule__LootItemConfig__WeightAssignment_7_1 )
            // InternalMazeDsl.g:5930:3: rule__LootItemConfig__WeightAssignment_7_1
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
    // InternalMazeDsl.g:5939:1: rule__LevelConfig__Group__0 : rule__LevelConfig__Group__0__Impl rule__LevelConfig__Group__1 ;
    public final void rule__LevelConfig__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5943:1: ( rule__LevelConfig__Group__0__Impl rule__LevelConfig__Group__1 )
            // InternalMazeDsl.g:5944:2: rule__LevelConfig__Group__0__Impl rule__LevelConfig__Group__1
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
    // InternalMazeDsl.g:5951:1: rule__LevelConfig__Group__0__Impl : ( 'level' ) ;
    public final void rule__LevelConfig__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5955:1: ( ( 'level' ) )
            // InternalMazeDsl.g:5956:1: ( 'level' )
            {
            // InternalMazeDsl.g:5956:1: ( 'level' )
            // InternalMazeDsl.g:5957:2: 'level'
            {
             before(grammarAccess.getLevelConfigAccess().getLevelKeyword_0()); 
            match(input,72,FOLLOW_2); 
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
    // InternalMazeDsl.g:5966:1: rule__LevelConfig__Group__1 : rule__LevelConfig__Group__1__Impl rule__LevelConfig__Group__2 ;
    public final void rule__LevelConfig__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5970:1: ( rule__LevelConfig__Group__1__Impl rule__LevelConfig__Group__2 )
            // InternalMazeDsl.g:5971:2: rule__LevelConfig__Group__1__Impl rule__LevelConfig__Group__2
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
    // InternalMazeDsl.g:5978:1: rule__LevelConfig__Group__1__Impl : ( ( rule__LevelConfig__NumberAssignment_1 ) ) ;
    public final void rule__LevelConfig__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5982:1: ( ( ( rule__LevelConfig__NumberAssignment_1 ) ) )
            // InternalMazeDsl.g:5983:1: ( ( rule__LevelConfig__NumberAssignment_1 ) )
            {
            // InternalMazeDsl.g:5983:1: ( ( rule__LevelConfig__NumberAssignment_1 ) )
            // InternalMazeDsl.g:5984:2: ( rule__LevelConfig__NumberAssignment_1 )
            {
             before(grammarAccess.getLevelConfigAccess().getNumberAssignment_1()); 
            // InternalMazeDsl.g:5985:2: ( rule__LevelConfig__NumberAssignment_1 )
            // InternalMazeDsl.g:5985:3: rule__LevelConfig__NumberAssignment_1
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
    // InternalMazeDsl.g:5993:1: rule__LevelConfig__Group__2 : rule__LevelConfig__Group__2__Impl rule__LevelConfig__Group__3 ;
    public final void rule__LevelConfig__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5997:1: ( rule__LevelConfig__Group__2__Impl rule__LevelConfig__Group__3 )
            // InternalMazeDsl.g:5998:2: rule__LevelConfig__Group__2__Impl rule__LevelConfig__Group__3
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
    // InternalMazeDsl.g:6005:1: rule__LevelConfig__Group__2__Impl : ( ( rule__LevelConfig__NameAssignment_2 ) ) ;
    public final void rule__LevelConfig__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6009:1: ( ( ( rule__LevelConfig__NameAssignment_2 ) ) )
            // InternalMazeDsl.g:6010:1: ( ( rule__LevelConfig__NameAssignment_2 ) )
            {
            // InternalMazeDsl.g:6010:1: ( ( rule__LevelConfig__NameAssignment_2 ) )
            // InternalMazeDsl.g:6011:2: ( rule__LevelConfig__NameAssignment_2 )
            {
             before(grammarAccess.getLevelConfigAccess().getNameAssignment_2()); 
            // InternalMazeDsl.g:6012:2: ( rule__LevelConfig__NameAssignment_2 )
            // InternalMazeDsl.g:6012:3: rule__LevelConfig__NameAssignment_2
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
    // InternalMazeDsl.g:6020:1: rule__LevelConfig__Group__3 : rule__LevelConfig__Group__3__Impl rule__LevelConfig__Group__4 ;
    public final void rule__LevelConfig__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6024:1: ( rule__LevelConfig__Group__3__Impl rule__LevelConfig__Group__4 )
            // InternalMazeDsl.g:6025:2: rule__LevelConfig__Group__3__Impl rule__LevelConfig__Group__4
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
    // InternalMazeDsl.g:6032:1: rule__LevelConfig__Group__3__Impl : ( '{' ) ;
    public final void rule__LevelConfig__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6036:1: ( ( '{' ) )
            // InternalMazeDsl.g:6037:1: ( '{' )
            {
            // InternalMazeDsl.g:6037:1: ( '{' )
            // InternalMazeDsl.g:6038:2: '{'
            {
             before(grammarAccess.getLevelConfigAccess().getLeftCurlyBracketKeyword_3()); 
            match(input,68,FOLLOW_2); 
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
    // InternalMazeDsl.g:6047:1: rule__LevelConfig__Group__4 : rule__LevelConfig__Group__4__Impl rule__LevelConfig__Group__5 ;
    public final void rule__LevelConfig__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6051:1: ( rule__LevelConfig__Group__4__Impl rule__LevelConfig__Group__5 )
            // InternalMazeDsl.g:6052:2: rule__LevelConfig__Group__4__Impl rule__LevelConfig__Group__5
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
    // InternalMazeDsl.g:6059:1: rule__LevelConfig__Group__4__Impl : ( ( rule__LevelConfig__Group_4__0 )? ) ;
    public final void rule__LevelConfig__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6063:1: ( ( ( rule__LevelConfig__Group_4__0 )? ) )
            // InternalMazeDsl.g:6064:1: ( ( rule__LevelConfig__Group_4__0 )? )
            {
            // InternalMazeDsl.g:6064:1: ( ( rule__LevelConfig__Group_4__0 )? )
            // InternalMazeDsl.g:6065:2: ( rule__LevelConfig__Group_4__0 )?
            {
             before(grammarAccess.getLevelConfigAccess().getGroup_4()); 
            // InternalMazeDsl.g:6066:2: ( rule__LevelConfig__Group_4__0 )?
            int alt50=2;
            int LA50_0 = input.LA(1);

            if ( (LA50_0==14) ) {
                alt50=1;
            }
            switch (alt50) {
                case 1 :
                    // InternalMazeDsl.g:6066:3: rule__LevelConfig__Group_4__0
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
    // InternalMazeDsl.g:6074:1: rule__LevelConfig__Group__5 : rule__LevelConfig__Group__5__Impl rule__LevelConfig__Group__6 ;
    public final void rule__LevelConfig__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6078:1: ( rule__LevelConfig__Group__5__Impl rule__LevelConfig__Group__6 )
            // InternalMazeDsl.g:6079:2: rule__LevelConfig__Group__5__Impl rule__LevelConfig__Group__6
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
    // InternalMazeDsl.g:6086:1: rule__LevelConfig__Group__5__Impl : ( ( rule__LevelConfig__Group_5__0 )? ) ;
    public final void rule__LevelConfig__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6090:1: ( ( ( rule__LevelConfig__Group_5__0 )? ) )
            // InternalMazeDsl.g:6091:1: ( ( rule__LevelConfig__Group_5__0 )? )
            {
            // InternalMazeDsl.g:6091:1: ( ( rule__LevelConfig__Group_5__0 )? )
            // InternalMazeDsl.g:6092:2: ( rule__LevelConfig__Group_5__0 )?
            {
             before(grammarAccess.getLevelConfigAccess().getGroup_5()); 
            // InternalMazeDsl.g:6093:2: ( rule__LevelConfig__Group_5__0 )?
            int alt51=2;
            int LA51_0 = input.LA(1);

            if ( (LA51_0==15) ) {
                alt51=1;
            }
            switch (alt51) {
                case 1 :
                    // InternalMazeDsl.g:6093:3: rule__LevelConfig__Group_5__0
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
    // InternalMazeDsl.g:6101:1: rule__LevelConfig__Group__6 : rule__LevelConfig__Group__6__Impl rule__LevelConfig__Group__7 ;
    public final void rule__LevelConfig__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6105:1: ( rule__LevelConfig__Group__6__Impl rule__LevelConfig__Group__7 )
            // InternalMazeDsl.g:6106:2: rule__LevelConfig__Group__6__Impl rule__LevelConfig__Group__7
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
    // InternalMazeDsl.g:6113:1: rule__LevelConfig__Group__6__Impl : ( ( rule__LevelConfig__Group_6__0 )? ) ;
    public final void rule__LevelConfig__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6117:1: ( ( ( rule__LevelConfig__Group_6__0 )? ) )
            // InternalMazeDsl.g:6118:1: ( ( rule__LevelConfig__Group_6__0 )? )
            {
            // InternalMazeDsl.g:6118:1: ( ( rule__LevelConfig__Group_6__0 )? )
            // InternalMazeDsl.g:6119:2: ( rule__LevelConfig__Group_6__0 )?
            {
             before(grammarAccess.getLevelConfigAccess().getGroup_6()); 
            // InternalMazeDsl.g:6120:2: ( rule__LevelConfig__Group_6__0 )?
            int alt52=2;
            int LA52_0 = input.LA(1);

            if ( (LA52_0==16) ) {
                alt52=1;
            }
            switch (alt52) {
                case 1 :
                    // InternalMazeDsl.g:6120:3: rule__LevelConfig__Group_6__0
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
    // InternalMazeDsl.g:6128:1: rule__LevelConfig__Group__7 : rule__LevelConfig__Group__7__Impl rule__LevelConfig__Group__8 ;
    public final void rule__LevelConfig__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6132:1: ( rule__LevelConfig__Group__7__Impl rule__LevelConfig__Group__8 )
            // InternalMazeDsl.g:6133:2: rule__LevelConfig__Group__7__Impl rule__LevelConfig__Group__8
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
    // InternalMazeDsl.g:6140:1: rule__LevelConfig__Group__7__Impl : ( ( rule__LevelConfig__Group_7__0 )? ) ;
    public final void rule__LevelConfig__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6144:1: ( ( ( rule__LevelConfig__Group_7__0 )? ) )
            // InternalMazeDsl.g:6145:1: ( ( rule__LevelConfig__Group_7__0 )? )
            {
            // InternalMazeDsl.g:6145:1: ( ( rule__LevelConfig__Group_7__0 )? )
            // InternalMazeDsl.g:6146:2: ( rule__LevelConfig__Group_7__0 )?
            {
             before(grammarAccess.getLevelConfigAccess().getGroup_7()); 
            // InternalMazeDsl.g:6147:2: ( rule__LevelConfig__Group_7__0 )?
            int alt53=2;
            int LA53_0 = input.LA(1);

            if ( (LA53_0==17) ) {
                alt53=1;
            }
            switch (alt53) {
                case 1 :
                    // InternalMazeDsl.g:6147:3: rule__LevelConfig__Group_7__0
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
    // InternalMazeDsl.g:6155:1: rule__LevelConfig__Group__8 : rule__LevelConfig__Group__8__Impl rule__LevelConfig__Group__9 ;
    public final void rule__LevelConfig__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6159:1: ( rule__LevelConfig__Group__8__Impl rule__LevelConfig__Group__9 )
            // InternalMazeDsl.g:6160:2: rule__LevelConfig__Group__8__Impl rule__LevelConfig__Group__9
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
    // InternalMazeDsl.g:6167:1: rule__LevelConfig__Group__8__Impl : ( ( rule__LevelConfig__Group_8__0 )? ) ;
    public final void rule__LevelConfig__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6171:1: ( ( ( rule__LevelConfig__Group_8__0 )? ) )
            // InternalMazeDsl.g:6172:1: ( ( rule__LevelConfig__Group_8__0 )? )
            {
            // InternalMazeDsl.g:6172:1: ( ( rule__LevelConfig__Group_8__0 )? )
            // InternalMazeDsl.g:6173:2: ( rule__LevelConfig__Group_8__0 )?
            {
             before(grammarAccess.getLevelConfigAccess().getGroup_8()); 
            // InternalMazeDsl.g:6174:2: ( rule__LevelConfig__Group_8__0 )?
            int alt54=2;
            int LA54_0 = input.LA(1);

            if ( (LA54_0==18) ) {
                alt54=1;
            }
            switch (alt54) {
                case 1 :
                    // InternalMazeDsl.g:6174:3: rule__LevelConfig__Group_8__0
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
    // InternalMazeDsl.g:6182:1: rule__LevelConfig__Group__9 : rule__LevelConfig__Group__9__Impl rule__LevelConfig__Group__10 ;
    public final void rule__LevelConfig__Group__9() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6186:1: ( rule__LevelConfig__Group__9__Impl rule__LevelConfig__Group__10 )
            // InternalMazeDsl.g:6187:2: rule__LevelConfig__Group__9__Impl rule__LevelConfig__Group__10
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
    // InternalMazeDsl.g:6194:1: rule__LevelConfig__Group__9__Impl : ( ( rule__LevelConfig__ZonesAssignment_9 )* ) ;
    public final void rule__LevelConfig__Group__9__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6198:1: ( ( ( rule__LevelConfig__ZonesAssignment_9 )* ) )
            // InternalMazeDsl.g:6199:1: ( ( rule__LevelConfig__ZonesAssignment_9 )* )
            {
            // InternalMazeDsl.g:6199:1: ( ( rule__LevelConfig__ZonesAssignment_9 )* )
            // InternalMazeDsl.g:6200:2: ( rule__LevelConfig__ZonesAssignment_9 )*
            {
             before(grammarAccess.getLevelConfigAccess().getZonesAssignment_9()); 
            // InternalMazeDsl.g:6201:2: ( rule__LevelConfig__ZonesAssignment_9 )*
            loop55:
            do {
                int alt55=2;
                int LA55_0 = input.LA(1);

                if ( (LA55_0==19) ) {
                    alt55=1;
                }


                switch (alt55) {
            	case 1 :
            	    // InternalMazeDsl.g:6201:3: rule__LevelConfig__ZonesAssignment_9
            	    {
            	    pushFollow(FOLLOW_51);
            	    rule__LevelConfig__ZonesAssignment_9();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop55;
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
    // InternalMazeDsl.g:6209:1: rule__LevelConfig__Group__10 : rule__LevelConfig__Group__10__Impl rule__LevelConfig__Group__11 ;
    public final void rule__LevelConfig__Group__10() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6213:1: ( rule__LevelConfig__Group__10__Impl rule__LevelConfig__Group__11 )
            // InternalMazeDsl.g:6214:2: rule__LevelConfig__Group__10__Impl rule__LevelConfig__Group__11
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
    // InternalMazeDsl.g:6221:1: rule__LevelConfig__Group__10__Impl : ( ( rule__LevelConfig__SergeantAssignment_10 )? ) ;
    public final void rule__LevelConfig__Group__10__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6225:1: ( ( ( rule__LevelConfig__SergeantAssignment_10 )? ) )
            // InternalMazeDsl.g:6226:1: ( ( rule__LevelConfig__SergeantAssignment_10 )? )
            {
            // InternalMazeDsl.g:6226:1: ( ( rule__LevelConfig__SergeantAssignment_10 )? )
            // InternalMazeDsl.g:6227:2: ( rule__LevelConfig__SergeantAssignment_10 )?
            {
             before(grammarAccess.getLevelConfigAccess().getSergeantAssignment_10()); 
            // InternalMazeDsl.g:6228:2: ( rule__LevelConfig__SergeantAssignment_10 )?
            int alt56=2;
            int LA56_0 = input.LA(1);

            if ( (LA56_0==22) ) {
                alt56=1;
            }
            switch (alt56) {
                case 1 :
                    // InternalMazeDsl.g:6228:3: rule__LevelConfig__SergeantAssignment_10
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
    // InternalMazeDsl.g:6236:1: rule__LevelConfig__Group__11 : rule__LevelConfig__Group__11__Impl rule__LevelConfig__Group__12 ;
    public final void rule__LevelConfig__Group__11() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6240:1: ( rule__LevelConfig__Group__11__Impl rule__LevelConfig__Group__12 )
            // InternalMazeDsl.g:6241:2: rule__LevelConfig__Group__11__Impl rule__LevelConfig__Group__12
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
    // InternalMazeDsl.g:6248:1: rule__LevelConfig__Group__11__Impl : ( ( rule__LevelConfig__SergeantPostsAssignment_11 )* ) ;
    public final void rule__LevelConfig__Group__11__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6252:1: ( ( ( rule__LevelConfig__SergeantPostsAssignment_11 )* ) )
            // InternalMazeDsl.g:6253:1: ( ( rule__LevelConfig__SergeantPostsAssignment_11 )* )
            {
            // InternalMazeDsl.g:6253:1: ( ( rule__LevelConfig__SergeantPostsAssignment_11 )* )
            // InternalMazeDsl.g:6254:2: ( rule__LevelConfig__SergeantPostsAssignment_11 )*
            {
             before(grammarAccess.getLevelConfigAccess().getSergeantPostsAssignment_11()); 
            // InternalMazeDsl.g:6255:2: ( rule__LevelConfig__SergeantPostsAssignment_11 )*
            loop57:
            do {
                int alt57=2;
                int LA57_0 = input.LA(1);

                if ( (LA57_0==23) ) {
                    alt57=1;
                }


                switch (alt57) {
            	case 1 :
            	    // InternalMazeDsl.g:6255:3: rule__LevelConfig__SergeantPostsAssignment_11
            	    {
            	    pushFollow(FOLLOW_52);
            	    rule__LevelConfig__SergeantPostsAssignment_11();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop57;
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
    // InternalMazeDsl.g:6263:1: rule__LevelConfig__Group__12 : rule__LevelConfig__Group__12__Impl rule__LevelConfig__Group__13 ;
    public final void rule__LevelConfig__Group__12() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6267:1: ( rule__LevelConfig__Group__12__Impl rule__LevelConfig__Group__13 )
            // InternalMazeDsl.g:6268:2: rule__LevelConfig__Group__12__Impl rule__LevelConfig__Group__13
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
    // InternalMazeDsl.g:6275:1: rule__LevelConfig__Group__12__Impl : ( ( rule__LevelConfig__MinesAssignment_12 )? ) ;
    public final void rule__LevelConfig__Group__12__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6279:1: ( ( ( rule__LevelConfig__MinesAssignment_12 )? ) )
            // InternalMazeDsl.g:6280:1: ( ( rule__LevelConfig__MinesAssignment_12 )? )
            {
            // InternalMazeDsl.g:6280:1: ( ( rule__LevelConfig__MinesAssignment_12 )? )
            // InternalMazeDsl.g:6281:2: ( rule__LevelConfig__MinesAssignment_12 )?
            {
             before(grammarAccess.getLevelConfigAccess().getMinesAssignment_12()); 
            // InternalMazeDsl.g:6282:2: ( rule__LevelConfig__MinesAssignment_12 )?
            int alt58=2;
            int LA58_0 = input.LA(1);

            if ( (LA58_0==33) ) {
                alt58=1;
            }
            switch (alt58) {
                case 1 :
                    // InternalMazeDsl.g:6282:3: rule__LevelConfig__MinesAssignment_12
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
    // InternalMazeDsl.g:6290:1: rule__LevelConfig__Group__13 : rule__LevelConfig__Group__13__Impl rule__LevelConfig__Group__14 ;
    public final void rule__LevelConfig__Group__13() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6294:1: ( rule__LevelConfig__Group__13__Impl rule__LevelConfig__Group__14 )
            // InternalMazeDsl.g:6295:2: rule__LevelConfig__Group__13__Impl rule__LevelConfig__Group__14
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
    // InternalMazeDsl.g:6302:1: rule__LevelConfig__Group__13__Impl : ( ( rule__LevelConfig__PortalGhostAssignment_13 )? ) ;
    public final void rule__LevelConfig__Group__13__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6306:1: ( ( ( rule__LevelConfig__PortalGhostAssignment_13 )? ) )
            // InternalMazeDsl.g:6307:1: ( ( rule__LevelConfig__PortalGhostAssignment_13 )? )
            {
            // InternalMazeDsl.g:6307:1: ( ( rule__LevelConfig__PortalGhostAssignment_13 )? )
            // InternalMazeDsl.g:6308:2: ( rule__LevelConfig__PortalGhostAssignment_13 )?
            {
             before(grammarAccess.getLevelConfigAccess().getPortalGhostAssignment_13()); 
            // InternalMazeDsl.g:6309:2: ( rule__LevelConfig__PortalGhostAssignment_13 )?
            int alt59=2;
            int LA59_0 = input.LA(1);

            if ( (LA59_0==42) ) {
                alt59=1;
            }
            switch (alt59) {
                case 1 :
                    // InternalMazeDsl.g:6309:3: rule__LevelConfig__PortalGhostAssignment_13
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
    // InternalMazeDsl.g:6317:1: rule__LevelConfig__Group__14 : rule__LevelConfig__Group__14__Impl rule__LevelConfig__Group__15 ;
    public final void rule__LevelConfig__Group__14() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6321:1: ( rule__LevelConfig__Group__14__Impl rule__LevelConfig__Group__15 )
            // InternalMazeDsl.g:6322:2: rule__LevelConfig__Group__14__Impl rule__LevelConfig__Group__15
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
    // InternalMazeDsl.g:6329:1: rule__LevelConfig__Group__14__Impl : ( ( rule__LevelConfig__GhostCountsAssignment_14 )* ) ;
    public final void rule__LevelConfig__Group__14__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6333:1: ( ( ( rule__LevelConfig__GhostCountsAssignment_14 )* ) )
            // InternalMazeDsl.g:6334:1: ( ( rule__LevelConfig__GhostCountsAssignment_14 )* )
            {
            // InternalMazeDsl.g:6334:1: ( ( rule__LevelConfig__GhostCountsAssignment_14 )* )
            // InternalMazeDsl.g:6335:2: ( rule__LevelConfig__GhostCountsAssignment_14 )*
            {
             before(grammarAccess.getLevelConfigAccess().getGhostCountsAssignment_14()); 
            // InternalMazeDsl.g:6336:2: ( rule__LevelConfig__GhostCountsAssignment_14 )*
            loop60:
            do {
                int alt60=2;
                int LA60_0 = input.LA(1);

                if ( (LA60_0==43) ) {
                    alt60=1;
                }


                switch (alt60) {
            	case 1 :
            	    // InternalMazeDsl.g:6336:3: rule__LevelConfig__GhostCountsAssignment_14
            	    {
            	    pushFollow(FOLLOW_53);
            	    rule__LevelConfig__GhostCountsAssignment_14();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop60;
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
    // InternalMazeDsl.g:6344:1: rule__LevelConfig__Group__15 : rule__LevelConfig__Group__15__Impl rule__LevelConfig__Group__16 ;
    public final void rule__LevelConfig__Group__15() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6348:1: ( rule__LevelConfig__Group__15__Impl rule__LevelConfig__Group__16 )
            // InternalMazeDsl.g:6349:2: rule__LevelConfig__Group__15__Impl rule__LevelConfig__Group__16
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
    // InternalMazeDsl.g:6356:1: rule__LevelConfig__Group__15__Impl : ( ( rule__LevelConfig__TeleportAssignment_15 )? ) ;
    public final void rule__LevelConfig__Group__15__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6360:1: ( ( ( rule__LevelConfig__TeleportAssignment_15 )? ) )
            // InternalMazeDsl.g:6361:1: ( ( rule__LevelConfig__TeleportAssignment_15 )? )
            {
            // InternalMazeDsl.g:6361:1: ( ( rule__LevelConfig__TeleportAssignment_15 )? )
            // InternalMazeDsl.g:6362:2: ( rule__LevelConfig__TeleportAssignment_15 )?
            {
             before(grammarAccess.getLevelConfigAccess().getTeleportAssignment_15()); 
            // InternalMazeDsl.g:6363:2: ( rule__LevelConfig__TeleportAssignment_15 )?
            int alt61=2;
            int LA61_0 = input.LA(1);

            if ( (LA61_0==44) ) {
                alt61=1;
            }
            switch (alt61) {
                case 1 :
                    // InternalMazeDsl.g:6363:3: rule__LevelConfig__TeleportAssignment_15
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
    // InternalMazeDsl.g:6371:1: rule__LevelConfig__Group__16 : rule__LevelConfig__Group__16__Impl ;
    public final void rule__LevelConfig__Group__16() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6375:1: ( rule__LevelConfig__Group__16__Impl )
            // InternalMazeDsl.g:6376:2: rule__LevelConfig__Group__16__Impl
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
    // InternalMazeDsl.g:6382:1: rule__LevelConfig__Group__16__Impl : ( '}' ) ;
    public final void rule__LevelConfig__Group__16__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6386:1: ( ( '}' ) )
            // InternalMazeDsl.g:6387:1: ( '}' )
            {
            // InternalMazeDsl.g:6387:1: ( '}' )
            // InternalMazeDsl.g:6388:2: '}'
            {
             before(grammarAccess.getLevelConfigAccess().getRightCurlyBracketKeyword_16()); 
            match(input,69,FOLLOW_2); 
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
    // InternalMazeDsl.g:6398:1: rule__LevelConfig__Group_4__0 : rule__LevelConfig__Group_4__0__Impl rule__LevelConfig__Group_4__1 ;
    public final void rule__LevelConfig__Group_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6402:1: ( rule__LevelConfig__Group_4__0__Impl rule__LevelConfig__Group_4__1 )
            // InternalMazeDsl.g:6403:2: rule__LevelConfig__Group_4__0__Impl rule__LevelConfig__Group_4__1
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
    // InternalMazeDsl.g:6410:1: rule__LevelConfig__Group_4__0__Impl : ( 'basedOn' ) ;
    public final void rule__LevelConfig__Group_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6414:1: ( ( 'basedOn' ) )
            // InternalMazeDsl.g:6415:1: ( 'basedOn' )
            {
            // InternalMazeDsl.g:6415:1: ( 'basedOn' )
            // InternalMazeDsl.g:6416:2: 'basedOn'
            {
             before(grammarAccess.getLevelConfigAccess().getBasedOnKeyword_4_0()); 
            match(input,14,FOLLOW_2); 
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
    // InternalMazeDsl.g:6425:1: rule__LevelConfig__Group_4__1 : rule__LevelConfig__Group_4__1__Impl ;
    public final void rule__LevelConfig__Group_4__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6429:1: ( rule__LevelConfig__Group_4__1__Impl )
            // InternalMazeDsl.g:6430:2: rule__LevelConfig__Group_4__1__Impl
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
    // InternalMazeDsl.g:6436:1: rule__LevelConfig__Group_4__1__Impl : ( ( rule__LevelConfig__BaseLevelAssignment_4_1 ) ) ;
    public final void rule__LevelConfig__Group_4__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6440:1: ( ( ( rule__LevelConfig__BaseLevelAssignment_4_1 ) ) )
            // InternalMazeDsl.g:6441:1: ( ( rule__LevelConfig__BaseLevelAssignment_4_1 ) )
            {
            // InternalMazeDsl.g:6441:1: ( ( rule__LevelConfig__BaseLevelAssignment_4_1 ) )
            // InternalMazeDsl.g:6442:2: ( rule__LevelConfig__BaseLevelAssignment_4_1 )
            {
             before(grammarAccess.getLevelConfigAccess().getBaseLevelAssignment_4_1()); 
            // InternalMazeDsl.g:6443:2: ( rule__LevelConfig__BaseLevelAssignment_4_1 )
            // InternalMazeDsl.g:6443:3: rule__LevelConfig__BaseLevelAssignment_4_1
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
    // InternalMazeDsl.g:6452:1: rule__LevelConfig__Group_5__0 : rule__LevelConfig__Group_5__0__Impl rule__LevelConfig__Group_5__1 ;
    public final void rule__LevelConfig__Group_5__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6456:1: ( rule__LevelConfig__Group_5__0__Impl rule__LevelConfig__Group_5__1 )
            // InternalMazeDsl.g:6457:2: rule__LevelConfig__Group_5__0__Impl rule__LevelConfig__Group_5__1
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
    // InternalMazeDsl.g:6464:1: rule__LevelConfig__Group_5__0__Impl : ( 'threatCapacity' ) ;
    public final void rule__LevelConfig__Group_5__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6468:1: ( ( 'threatCapacity' ) )
            // InternalMazeDsl.g:6469:1: ( 'threatCapacity' )
            {
            // InternalMazeDsl.g:6469:1: ( 'threatCapacity' )
            // InternalMazeDsl.g:6470:2: 'threatCapacity'
            {
             before(grammarAccess.getLevelConfigAccess().getThreatCapacityKeyword_5_0()); 
            match(input,15,FOLLOW_2); 
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
    // InternalMazeDsl.g:6479:1: rule__LevelConfig__Group_5__1 : rule__LevelConfig__Group_5__1__Impl ;
    public final void rule__LevelConfig__Group_5__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6483:1: ( rule__LevelConfig__Group_5__1__Impl )
            // InternalMazeDsl.g:6484:2: rule__LevelConfig__Group_5__1__Impl
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
    // InternalMazeDsl.g:6490:1: rule__LevelConfig__Group_5__1__Impl : ( ( rule__LevelConfig__ThreatCapacityFactorAssignment_5_1 ) ) ;
    public final void rule__LevelConfig__Group_5__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6494:1: ( ( ( rule__LevelConfig__ThreatCapacityFactorAssignment_5_1 ) ) )
            // InternalMazeDsl.g:6495:1: ( ( rule__LevelConfig__ThreatCapacityFactorAssignment_5_1 ) )
            {
            // InternalMazeDsl.g:6495:1: ( ( rule__LevelConfig__ThreatCapacityFactorAssignment_5_1 ) )
            // InternalMazeDsl.g:6496:2: ( rule__LevelConfig__ThreatCapacityFactorAssignment_5_1 )
            {
             before(grammarAccess.getLevelConfigAccess().getThreatCapacityFactorAssignment_5_1()); 
            // InternalMazeDsl.g:6497:2: ( rule__LevelConfig__ThreatCapacityFactorAssignment_5_1 )
            // InternalMazeDsl.g:6497:3: rule__LevelConfig__ThreatCapacityFactorAssignment_5_1
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
    // InternalMazeDsl.g:6506:1: rule__LevelConfig__Group_6__0 : rule__LevelConfig__Group_6__0__Impl rule__LevelConfig__Group_6__1 ;
    public final void rule__LevelConfig__Group_6__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6510:1: ( rule__LevelConfig__Group_6__0__Impl rule__LevelConfig__Group_6__1 )
            // InternalMazeDsl.g:6511:2: rule__LevelConfig__Group_6__0__Impl rule__LevelConfig__Group_6__1
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
    // InternalMazeDsl.g:6518:1: rule__LevelConfig__Group_6__0__Impl : ( 'wallMaterials' ) ;
    public final void rule__LevelConfig__Group_6__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6522:1: ( ( 'wallMaterials' ) )
            // InternalMazeDsl.g:6523:1: ( 'wallMaterials' )
            {
            // InternalMazeDsl.g:6523:1: ( 'wallMaterials' )
            // InternalMazeDsl.g:6524:2: 'wallMaterials'
            {
             before(grammarAccess.getLevelConfigAccess().getWallMaterialsKeyword_6_0()); 
            match(input,16,FOLLOW_2); 
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
    // InternalMazeDsl.g:6533:1: rule__LevelConfig__Group_6__1 : rule__LevelConfig__Group_6__1__Impl ;
    public final void rule__LevelConfig__Group_6__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6537:1: ( rule__LevelConfig__Group_6__1__Impl )
            // InternalMazeDsl.g:6538:2: rule__LevelConfig__Group_6__1__Impl
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
    // InternalMazeDsl.g:6544:1: rule__LevelConfig__Group_6__1__Impl : ( ( rule__LevelConfig__ShowWallMaterialsAssignment_6_1 ) ) ;
    public final void rule__LevelConfig__Group_6__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6548:1: ( ( ( rule__LevelConfig__ShowWallMaterialsAssignment_6_1 ) ) )
            // InternalMazeDsl.g:6549:1: ( ( rule__LevelConfig__ShowWallMaterialsAssignment_6_1 ) )
            {
            // InternalMazeDsl.g:6549:1: ( ( rule__LevelConfig__ShowWallMaterialsAssignment_6_1 ) )
            // InternalMazeDsl.g:6550:2: ( rule__LevelConfig__ShowWallMaterialsAssignment_6_1 )
            {
             before(grammarAccess.getLevelConfigAccess().getShowWallMaterialsAssignment_6_1()); 
            // InternalMazeDsl.g:6551:2: ( rule__LevelConfig__ShowWallMaterialsAssignment_6_1 )
            // InternalMazeDsl.g:6551:3: rule__LevelConfig__ShowWallMaterialsAssignment_6_1
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
    // InternalMazeDsl.g:6560:1: rule__LevelConfig__Group_7__0 : rule__LevelConfig__Group_7__0__Impl rule__LevelConfig__Group_7__1 ;
    public final void rule__LevelConfig__Group_7__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6564:1: ( rule__LevelConfig__Group_7__0__Impl rule__LevelConfig__Group_7__1 )
            // InternalMazeDsl.g:6565:2: rule__LevelConfig__Group_7__0__Impl rule__LevelConfig__Group_7__1
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
    // InternalMazeDsl.g:6572:1: rule__LevelConfig__Group_7__0__Impl : ( 'inheritWeapons' ) ;
    public final void rule__LevelConfig__Group_7__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6576:1: ( ( 'inheritWeapons' ) )
            // InternalMazeDsl.g:6577:1: ( 'inheritWeapons' )
            {
            // InternalMazeDsl.g:6577:1: ( 'inheritWeapons' )
            // InternalMazeDsl.g:6578:2: 'inheritWeapons'
            {
             before(grammarAccess.getLevelConfigAccess().getInheritWeaponsKeyword_7_0()); 
            match(input,17,FOLLOW_2); 
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
    // InternalMazeDsl.g:6587:1: rule__LevelConfig__Group_7__1 : rule__LevelConfig__Group_7__1__Impl ;
    public final void rule__LevelConfig__Group_7__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6591:1: ( rule__LevelConfig__Group_7__1__Impl )
            // InternalMazeDsl.g:6592:2: rule__LevelConfig__Group_7__1__Impl
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
    // InternalMazeDsl.g:6598:1: rule__LevelConfig__Group_7__1__Impl : ( ( rule__LevelConfig__InheritWeaponsAssignment_7_1 ) ) ;
    public final void rule__LevelConfig__Group_7__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6602:1: ( ( ( rule__LevelConfig__InheritWeaponsAssignment_7_1 ) ) )
            // InternalMazeDsl.g:6603:1: ( ( rule__LevelConfig__InheritWeaponsAssignment_7_1 ) )
            {
            // InternalMazeDsl.g:6603:1: ( ( rule__LevelConfig__InheritWeaponsAssignment_7_1 ) )
            // InternalMazeDsl.g:6604:2: ( rule__LevelConfig__InheritWeaponsAssignment_7_1 )
            {
             before(grammarAccess.getLevelConfigAccess().getInheritWeaponsAssignment_7_1()); 
            // InternalMazeDsl.g:6605:2: ( rule__LevelConfig__InheritWeaponsAssignment_7_1 )
            // InternalMazeDsl.g:6605:3: rule__LevelConfig__InheritWeaponsAssignment_7_1
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
    // InternalMazeDsl.g:6614:1: rule__LevelConfig__Group_8__0 : rule__LevelConfig__Group_8__0__Impl rule__LevelConfig__Group_8__1 ;
    public final void rule__LevelConfig__Group_8__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6618:1: ( rule__LevelConfig__Group_8__0__Impl rule__LevelConfig__Group_8__1 )
            // InternalMazeDsl.g:6619:2: rule__LevelConfig__Group_8__0__Impl rule__LevelConfig__Group_8__1
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
    // InternalMazeDsl.g:6626:1: rule__LevelConfig__Group_8__0__Impl : ( 'cascadeDepth' ) ;
    public final void rule__LevelConfig__Group_8__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6630:1: ( ( 'cascadeDepth' ) )
            // InternalMazeDsl.g:6631:1: ( 'cascadeDepth' )
            {
            // InternalMazeDsl.g:6631:1: ( 'cascadeDepth' )
            // InternalMazeDsl.g:6632:2: 'cascadeDepth'
            {
             before(grammarAccess.getLevelConfigAccess().getCascadeDepthKeyword_8_0()); 
            match(input,18,FOLLOW_2); 
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
    // InternalMazeDsl.g:6641:1: rule__LevelConfig__Group_8__1 : rule__LevelConfig__Group_8__1__Impl ;
    public final void rule__LevelConfig__Group_8__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6645:1: ( rule__LevelConfig__Group_8__1__Impl )
            // InternalMazeDsl.g:6646:2: rule__LevelConfig__Group_8__1__Impl
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
    // InternalMazeDsl.g:6652:1: rule__LevelConfig__Group_8__1__Impl : ( ( rule__LevelConfig__CascadeDepthAssignment_8_1 ) ) ;
    public final void rule__LevelConfig__Group_8__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6656:1: ( ( ( rule__LevelConfig__CascadeDepthAssignment_8_1 ) ) )
            // InternalMazeDsl.g:6657:1: ( ( rule__LevelConfig__CascadeDepthAssignment_8_1 ) )
            {
            // InternalMazeDsl.g:6657:1: ( ( rule__LevelConfig__CascadeDepthAssignment_8_1 ) )
            // InternalMazeDsl.g:6658:2: ( rule__LevelConfig__CascadeDepthAssignment_8_1 )
            {
             before(grammarAccess.getLevelConfigAccess().getCascadeDepthAssignment_8_1()); 
            // InternalMazeDsl.g:6659:2: ( rule__LevelConfig__CascadeDepthAssignment_8_1 )
            // InternalMazeDsl.g:6659:3: rule__LevelConfig__CascadeDepthAssignment_8_1
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
    // InternalMazeDsl.g:6668:1: rule__LevelZone__Group__0 : rule__LevelZone__Group__0__Impl rule__LevelZone__Group__1 ;
    public final void rule__LevelZone__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6672:1: ( rule__LevelZone__Group__0__Impl rule__LevelZone__Group__1 )
            // InternalMazeDsl.g:6673:2: rule__LevelZone__Group__0__Impl rule__LevelZone__Group__1
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
    // InternalMazeDsl.g:6680:1: rule__LevelZone__Group__0__Impl : ( 'ring' ) ;
    public final void rule__LevelZone__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6684:1: ( ( 'ring' ) )
            // InternalMazeDsl.g:6685:1: ( 'ring' )
            {
            // InternalMazeDsl.g:6685:1: ( 'ring' )
            // InternalMazeDsl.g:6686:2: 'ring'
            {
             before(grammarAccess.getLevelZoneAccess().getRingKeyword_0()); 
            match(input,19,FOLLOW_2); 
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
    // InternalMazeDsl.g:6695:1: rule__LevelZone__Group__1 : rule__LevelZone__Group__1__Impl rule__LevelZone__Group__2 ;
    public final void rule__LevelZone__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6699:1: ( rule__LevelZone__Group__1__Impl rule__LevelZone__Group__2 )
            // InternalMazeDsl.g:6700:2: rule__LevelZone__Group__1__Impl rule__LevelZone__Group__2
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
    // InternalMazeDsl.g:6707:1: rule__LevelZone__Group__1__Impl : ( ( rule__LevelZone__DisplayNameAssignment_1 ) ) ;
    public final void rule__LevelZone__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6711:1: ( ( ( rule__LevelZone__DisplayNameAssignment_1 ) ) )
            // InternalMazeDsl.g:6712:1: ( ( rule__LevelZone__DisplayNameAssignment_1 ) )
            {
            // InternalMazeDsl.g:6712:1: ( ( rule__LevelZone__DisplayNameAssignment_1 ) )
            // InternalMazeDsl.g:6713:2: ( rule__LevelZone__DisplayNameAssignment_1 )
            {
             before(grammarAccess.getLevelZoneAccess().getDisplayNameAssignment_1()); 
            // InternalMazeDsl.g:6714:2: ( rule__LevelZone__DisplayNameAssignment_1 )
            // InternalMazeDsl.g:6714:3: rule__LevelZone__DisplayNameAssignment_1
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
    // InternalMazeDsl.g:6722:1: rule__LevelZone__Group__2 : rule__LevelZone__Group__2__Impl rule__LevelZone__Group__3 ;
    public final void rule__LevelZone__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6726:1: ( rule__LevelZone__Group__2__Impl rule__LevelZone__Group__3 )
            // InternalMazeDsl.g:6727:2: rule__LevelZone__Group__2__Impl rule__LevelZone__Group__3
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
    // InternalMazeDsl.g:6734:1: rule__LevelZone__Group__2__Impl : ( ( rule__LevelZone__Group_2__0 )? ) ;
    public final void rule__LevelZone__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6738:1: ( ( ( rule__LevelZone__Group_2__0 )? ) )
            // InternalMazeDsl.g:6739:1: ( ( rule__LevelZone__Group_2__0 )? )
            {
            // InternalMazeDsl.g:6739:1: ( ( rule__LevelZone__Group_2__0 )? )
            // InternalMazeDsl.g:6740:2: ( rule__LevelZone__Group_2__0 )?
            {
             before(grammarAccess.getLevelZoneAccess().getGroup_2()); 
            // InternalMazeDsl.g:6741:2: ( rule__LevelZone__Group_2__0 )?
            int alt62=2;
            int LA62_0 = input.LA(1);

            if ( (LA62_0==20) ) {
                alt62=1;
            }
            switch (alt62) {
                case 1 :
                    // InternalMazeDsl.g:6741:3: rule__LevelZone__Group_2__0
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
    // InternalMazeDsl.g:6749:1: rule__LevelZone__Group__3 : rule__LevelZone__Group__3__Impl rule__LevelZone__Group__4 ;
    public final void rule__LevelZone__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6753:1: ( rule__LevelZone__Group__3__Impl rule__LevelZone__Group__4 )
            // InternalMazeDsl.g:6754:2: rule__LevelZone__Group__3__Impl rule__LevelZone__Group__4
            {
            pushFollow(FOLLOW_55);
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
    // InternalMazeDsl.g:6761:1: rule__LevelZone__Group__3__Impl : ( 'materials' ) ;
    public final void rule__LevelZone__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6765:1: ( ( 'materials' ) )
            // InternalMazeDsl.g:6766:1: ( 'materials' )
            {
            // InternalMazeDsl.g:6766:1: ( 'materials' )
            // InternalMazeDsl.g:6767:2: 'materials'
            {
             before(grammarAccess.getLevelZoneAccess().getMaterialsKeyword_3()); 
            match(input,21,FOLLOW_2); 
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
    // InternalMazeDsl.g:6776:1: rule__LevelZone__Group__4 : rule__LevelZone__Group__4__Impl rule__LevelZone__Group__5 ;
    public final void rule__LevelZone__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6780:1: ( rule__LevelZone__Group__4__Impl rule__LevelZone__Group__5 )
            // InternalMazeDsl.g:6781:2: rule__LevelZone__Group__4__Impl rule__LevelZone__Group__5
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
    // InternalMazeDsl.g:6788:1: rule__LevelZone__Group__4__Impl : ( ( rule__LevelZone__MaterialIdsAssignment_4 ) ) ;
    public final void rule__LevelZone__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6792:1: ( ( ( rule__LevelZone__MaterialIdsAssignment_4 ) ) )
            // InternalMazeDsl.g:6793:1: ( ( rule__LevelZone__MaterialIdsAssignment_4 ) )
            {
            // InternalMazeDsl.g:6793:1: ( ( rule__LevelZone__MaterialIdsAssignment_4 ) )
            // InternalMazeDsl.g:6794:2: ( rule__LevelZone__MaterialIdsAssignment_4 )
            {
             before(grammarAccess.getLevelZoneAccess().getMaterialIdsAssignment_4()); 
            // InternalMazeDsl.g:6795:2: ( rule__LevelZone__MaterialIdsAssignment_4 )
            // InternalMazeDsl.g:6795:3: rule__LevelZone__MaterialIdsAssignment_4
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
    // InternalMazeDsl.g:6803:1: rule__LevelZone__Group__5 : rule__LevelZone__Group__5__Impl ;
    public final void rule__LevelZone__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6807:1: ( rule__LevelZone__Group__5__Impl )
            // InternalMazeDsl.g:6808:2: rule__LevelZone__Group__5__Impl
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
    // InternalMazeDsl.g:6814:1: rule__LevelZone__Group__5__Impl : ( ( rule__LevelZone__Group_5__0 )* ) ;
    public final void rule__LevelZone__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6818:1: ( ( ( rule__LevelZone__Group_5__0 )* ) )
            // InternalMazeDsl.g:6819:1: ( ( rule__LevelZone__Group_5__0 )* )
            {
            // InternalMazeDsl.g:6819:1: ( ( rule__LevelZone__Group_5__0 )* )
            // InternalMazeDsl.g:6820:2: ( rule__LevelZone__Group_5__0 )*
            {
             before(grammarAccess.getLevelZoneAccess().getGroup_5()); 
            // InternalMazeDsl.g:6821:2: ( rule__LevelZone__Group_5__0 )*
            loop63:
            do {
                int alt63=2;
                int LA63_0 = input.LA(1);

                if ( (LA63_0==105) ) {
                    alt63=1;
                }


                switch (alt63) {
            	case 1 :
            	    // InternalMazeDsl.g:6821:3: rule__LevelZone__Group_5__0
            	    {
            	    pushFollow(FOLLOW_36);
            	    rule__LevelZone__Group_5__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop63;
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
    // InternalMazeDsl.g:6830:1: rule__LevelZone__Group_2__0 : rule__LevelZone__Group_2__0__Impl rule__LevelZone__Group_2__1 ;
    public final void rule__LevelZone__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6834:1: ( rule__LevelZone__Group_2__0__Impl rule__LevelZone__Group_2__1 )
            // InternalMazeDsl.g:6835:2: rule__LevelZone__Group_2__0__Impl rule__LevelZone__Group_2__1
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
    // InternalMazeDsl.g:6842:1: rule__LevelZone__Group_2__0__Impl : ( 'share' ) ;
    public final void rule__LevelZone__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6846:1: ( ( 'share' ) )
            // InternalMazeDsl.g:6847:1: ( 'share' )
            {
            // InternalMazeDsl.g:6847:1: ( 'share' )
            // InternalMazeDsl.g:6848:2: 'share'
            {
             before(grammarAccess.getLevelZoneAccess().getShareKeyword_2_0()); 
            match(input,20,FOLLOW_2); 
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
    // InternalMazeDsl.g:6857:1: rule__LevelZone__Group_2__1 : rule__LevelZone__Group_2__1__Impl ;
    public final void rule__LevelZone__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6861:1: ( rule__LevelZone__Group_2__1__Impl )
            // InternalMazeDsl.g:6862:2: rule__LevelZone__Group_2__1__Impl
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
    // InternalMazeDsl.g:6868:1: rule__LevelZone__Group_2__1__Impl : ( ( rule__LevelZone__BreakableShareAssignment_2_1 ) ) ;
    public final void rule__LevelZone__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6872:1: ( ( ( rule__LevelZone__BreakableShareAssignment_2_1 ) ) )
            // InternalMazeDsl.g:6873:1: ( ( rule__LevelZone__BreakableShareAssignment_2_1 ) )
            {
            // InternalMazeDsl.g:6873:1: ( ( rule__LevelZone__BreakableShareAssignment_2_1 ) )
            // InternalMazeDsl.g:6874:2: ( rule__LevelZone__BreakableShareAssignment_2_1 )
            {
             before(grammarAccess.getLevelZoneAccess().getBreakableShareAssignment_2_1()); 
            // InternalMazeDsl.g:6875:2: ( rule__LevelZone__BreakableShareAssignment_2_1 )
            // InternalMazeDsl.g:6875:3: rule__LevelZone__BreakableShareAssignment_2_1
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
    // InternalMazeDsl.g:6884:1: rule__LevelZone__Group_5__0 : rule__LevelZone__Group_5__0__Impl rule__LevelZone__Group_5__1 ;
    public final void rule__LevelZone__Group_5__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6888:1: ( rule__LevelZone__Group_5__0__Impl rule__LevelZone__Group_5__1 )
            // InternalMazeDsl.g:6889:2: rule__LevelZone__Group_5__0__Impl rule__LevelZone__Group_5__1
            {
            pushFollow(FOLLOW_55);
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
    // InternalMazeDsl.g:6896:1: rule__LevelZone__Group_5__0__Impl : ( ',' ) ;
    public final void rule__LevelZone__Group_5__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6900:1: ( ( ',' ) )
            // InternalMazeDsl.g:6901:1: ( ',' )
            {
            // InternalMazeDsl.g:6901:1: ( ',' )
            // InternalMazeDsl.g:6902:2: ','
            {
             before(grammarAccess.getLevelZoneAccess().getCommaKeyword_5_0()); 
            match(input,105,FOLLOW_2); 
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
    // InternalMazeDsl.g:6911:1: rule__LevelZone__Group_5__1 : rule__LevelZone__Group_5__1__Impl ;
    public final void rule__LevelZone__Group_5__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6915:1: ( rule__LevelZone__Group_5__1__Impl )
            // InternalMazeDsl.g:6916:2: rule__LevelZone__Group_5__1__Impl
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
    // InternalMazeDsl.g:6922:1: rule__LevelZone__Group_5__1__Impl : ( ( rule__LevelZone__MaterialIdsAssignment_5_1 ) ) ;
    public final void rule__LevelZone__Group_5__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6926:1: ( ( ( rule__LevelZone__MaterialIdsAssignment_5_1 ) ) )
            // InternalMazeDsl.g:6927:1: ( ( rule__LevelZone__MaterialIdsAssignment_5_1 ) )
            {
            // InternalMazeDsl.g:6927:1: ( ( rule__LevelZone__MaterialIdsAssignment_5_1 ) )
            // InternalMazeDsl.g:6928:2: ( rule__LevelZone__MaterialIdsAssignment_5_1 )
            {
             before(grammarAccess.getLevelZoneAccess().getMaterialIdsAssignment_5_1()); 
            // InternalMazeDsl.g:6929:2: ( rule__LevelZone__MaterialIdsAssignment_5_1 )
            // InternalMazeDsl.g:6929:3: rule__LevelZone__MaterialIdsAssignment_5_1
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
    // InternalMazeDsl.g:6938:1: rule__SergeantConfig__Group__0 : rule__SergeantConfig__Group__0__Impl rule__SergeantConfig__Group__1 ;
    public final void rule__SergeantConfig__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6942:1: ( rule__SergeantConfig__Group__0__Impl rule__SergeantConfig__Group__1 )
            // InternalMazeDsl.g:6943:2: rule__SergeantConfig__Group__0__Impl rule__SergeantConfig__Group__1
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
    // InternalMazeDsl.g:6950:1: rule__SergeantConfig__Group__0__Impl : ( 'sergeant' ) ;
    public final void rule__SergeantConfig__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6954:1: ( ( 'sergeant' ) )
            // InternalMazeDsl.g:6955:1: ( 'sergeant' )
            {
            // InternalMazeDsl.g:6955:1: ( 'sergeant' )
            // InternalMazeDsl.g:6956:2: 'sergeant'
            {
             before(grammarAccess.getSergeantConfigAccess().getSergeantKeyword_0()); 
            match(input,22,FOLLOW_2); 
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
    // InternalMazeDsl.g:6965:1: rule__SergeantConfig__Group__1 : rule__SergeantConfig__Group__1__Impl rule__SergeantConfig__Group__2 ;
    public final void rule__SergeantConfig__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6969:1: ( rule__SergeantConfig__Group__1__Impl rule__SergeantConfig__Group__2 )
            // InternalMazeDsl.g:6970:2: rule__SergeantConfig__Group__1__Impl rule__SergeantConfig__Group__2
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
    // InternalMazeDsl.g:6977:1: rule__SergeantConfig__Group__1__Impl : ( ( rule__SergeantConfig__DisplayNameAssignment_1 ) ) ;
    public final void rule__SergeantConfig__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6981:1: ( ( ( rule__SergeantConfig__DisplayNameAssignment_1 ) ) )
            // InternalMazeDsl.g:6982:1: ( ( rule__SergeantConfig__DisplayNameAssignment_1 ) )
            {
            // InternalMazeDsl.g:6982:1: ( ( rule__SergeantConfig__DisplayNameAssignment_1 ) )
            // InternalMazeDsl.g:6983:2: ( rule__SergeantConfig__DisplayNameAssignment_1 )
            {
             before(grammarAccess.getSergeantConfigAccess().getDisplayNameAssignment_1()); 
            // InternalMazeDsl.g:6984:2: ( rule__SergeantConfig__DisplayNameAssignment_1 )
            // InternalMazeDsl.g:6984:3: rule__SergeantConfig__DisplayNameAssignment_1
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
    // InternalMazeDsl.g:6992:1: rule__SergeantConfig__Group__2 : rule__SergeantConfig__Group__2__Impl rule__SergeantConfig__Group__3 ;
    public final void rule__SergeantConfig__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6996:1: ( rule__SergeantConfig__Group__2__Impl rule__SergeantConfig__Group__3 )
            // InternalMazeDsl.g:6997:2: rule__SergeantConfig__Group__2__Impl rule__SergeantConfig__Group__3
            {
            pushFollow(FOLLOW_56);
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
    // InternalMazeDsl.g:7004:1: rule__SergeantConfig__Group__2__Impl : ( '{' ) ;
    public final void rule__SergeantConfig__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7008:1: ( ( '{' ) )
            // InternalMazeDsl.g:7009:1: ( '{' )
            {
            // InternalMazeDsl.g:7009:1: ( '{' )
            // InternalMazeDsl.g:7010:2: '{'
            {
             before(grammarAccess.getSergeantConfigAccess().getLeftCurlyBracketKeyword_2()); 
            match(input,68,FOLLOW_2); 
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
    // InternalMazeDsl.g:7019:1: rule__SergeantConfig__Group__3 : rule__SergeantConfig__Group__3__Impl rule__SergeantConfig__Group__4 ;
    public final void rule__SergeantConfig__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7023:1: ( rule__SergeantConfig__Group__3__Impl rule__SergeantConfig__Group__4 )
            // InternalMazeDsl.g:7024:2: rule__SergeantConfig__Group__3__Impl rule__SergeantConfig__Group__4
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
    // InternalMazeDsl.g:7031:1: rule__SergeantConfig__Group__3__Impl : ( 'health' ) ;
    public final void rule__SergeantConfig__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7035:1: ( ( 'health' ) )
            // InternalMazeDsl.g:7036:1: ( 'health' )
            {
            // InternalMazeDsl.g:7036:1: ( 'health' )
            // InternalMazeDsl.g:7037:2: 'health'
            {
             before(grammarAccess.getSergeantConfigAccess().getHealthKeyword_3()); 
            match(input,82,FOLLOW_2); 
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
    // InternalMazeDsl.g:7046:1: rule__SergeantConfig__Group__4 : rule__SergeantConfig__Group__4__Impl rule__SergeantConfig__Group__5 ;
    public final void rule__SergeantConfig__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7050:1: ( rule__SergeantConfig__Group__4__Impl rule__SergeantConfig__Group__5 )
            // InternalMazeDsl.g:7051:2: rule__SergeantConfig__Group__4__Impl rule__SergeantConfig__Group__5
            {
            pushFollow(FOLLOW_57);
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
    // InternalMazeDsl.g:7058:1: rule__SergeantConfig__Group__4__Impl : ( ( rule__SergeantConfig__HealthAssignment_4 ) ) ;
    public final void rule__SergeantConfig__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7062:1: ( ( ( rule__SergeantConfig__HealthAssignment_4 ) ) )
            // InternalMazeDsl.g:7063:1: ( ( rule__SergeantConfig__HealthAssignment_4 ) )
            {
            // InternalMazeDsl.g:7063:1: ( ( rule__SergeantConfig__HealthAssignment_4 ) )
            // InternalMazeDsl.g:7064:2: ( rule__SergeantConfig__HealthAssignment_4 )
            {
             before(grammarAccess.getSergeantConfigAccess().getHealthAssignment_4()); 
            // InternalMazeDsl.g:7065:2: ( rule__SergeantConfig__HealthAssignment_4 )
            // InternalMazeDsl.g:7065:3: rule__SergeantConfig__HealthAssignment_4
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
    // InternalMazeDsl.g:7073:1: rule__SergeantConfig__Group__5 : rule__SergeantConfig__Group__5__Impl rule__SergeantConfig__Group__6 ;
    public final void rule__SergeantConfig__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7077:1: ( rule__SergeantConfig__Group__5__Impl rule__SergeantConfig__Group__6 )
            // InternalMazeDsl.g:7078:2: rule__SergeantConfig__Group__5__Impl rule__SergeantConfig__Group__6
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
    // InternalMazeDsl.g:7085:1: rule__SergeantConfig__Group__5__Impl : ( 'sightRange' ) ;
    public final void rule__SergeantConfig__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7089:1: ( ( 'sightRange' ) )
            // InternalMazeDsl.g:7090:1: ( 'sightRange' )
            {
            // InternalMazeDsl.g:7090:1: ( 'sightRange' )
            // InternalMazeDsl.g:7091:2: 'sightRange'
            {
             before(grammarAccess.getSergeantConfigAccess().getSightRangeKeyword_5()); 
            match(input,24,FOLLOW_2); 
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
    // InternalMazeDsl.g:7100:1: rule__SergeantConfig__Group__6 : rule__SergeantConfig__Group__6__Impl rule__SergeantConfig__Group__7 ;
    public final void rule__SergeantConfig__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7104:1: ( rule__SergeantConfig__Group__6__Impl rule__SergeantConfig__Group__7 )
            // InternalMazeDsl.g:7105:2: rule__SergeantConfig__Group__6__Impl rule__SergeantConfig__Group__7
            {
            pushFollow(FOLLOW_58);
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
    // InternalMazeDsl.g:7112:1: rule__SergeantConfig__Group__6__Impl : ( ( rule__SergeantConfig__SightRangeAssignment_6 ) ) ;
    public final void rule__SergeantConfig__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7116:1: ( ( ( rule__SergeantConfig__SightRangeAssignment_6 ) ) )
            // InternalMazeDsl.g:7117:1: ( ( rule__SergeantConfig__SightRangeAssignment_6 ) )
            {
            // InternalMazeDsl.g:7117:1: ( ( rule__SergeantConfig__SightRangeAssignment_6 ) )
            // InternalMazeDsl.g:7118:2: ( rule__SergeantConfig__SightRangeAssignment_6 )
            {
             before(grammarAccess.getSergeantConfigAccess().getSightRangeAssignment_6()); 
            // InternalMazeDsl.g:7119:2: ( rule__SergeantConfig__SightRangeAssignment_6 )
            // InternalMazeDsl.g:7119:3: rule__SergeantConfig__SightRangeAssignment_6
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
    // InternalMazeDsl.g:7127:1: rule__SergeantConfig__Group__7 : rule__SergeantConfig__Group__7__Impl rule__SergeantConfig__Group__8 ;
    public final void rule__SergeantConfig__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7131:1: ( rule__SergeantConfig__Group__7__Impl rule__SergeantConfig__Group__8 )
            // InternalMazeDsl.g:7132:2: rule__SergeantConfig__Group__7__Impl rule__SergeantConfig__Group__8
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
    // InternalMazeDsl.g:7139:1: rule__SergeantConfig__Group__7__Impl : ( 'rounds' ) ;
    public final void rule__SergeantConfig__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7143:1: ( ( 'rounds' ) )
            // InternalMazeDsl.g:7144:1: ( 'rounds' )
            {
            // InternalMazeDsl.g:7144:1: ( 'rounds' )
            // InternalMazeDsl.g:7145:2: 'rounds'
            {
             before(grammarAccess.getSergeantConfigAccess().getRoundsKeyword_7()); 
            match(input,25,FOLLOW_2); 
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
    // InternalMazeDsl.g:7154:1: rule__SergeantConfig__Group__8 : rule__SergeantConfig__Group__8__Impl rule__SergeantConfig__Group__9 ;
    public final void rule__SergeantConfig__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7158:1: ( rule__SergeantConfig__Group__8__Impl rule__SergeantConfig__Group__9 )
            // InternalMazeDsl.g:7159:2: rule__SergeantConfig__Group__8__Impl rule__SergeantConfig__Group__9
            {
            pushFollow(FOLLOW_59);
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
    // InternalMazeDsl.g:7166:1: rule__SergeantConfig__Group__8__Impl : ( ( rule__SergeantConfig__RoundsPerBurstAssignment_8 ) ) ;
    public final void rule__SergeantConfig__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7170:1: ( ( ( rule__SergeantConfig__RoundsPerBurstAssignment_8 ) ) )
            // InternalMazeDsl.g:7171:1: ( ( rule__SergeantConfig__RoundsPerBurstAssignment_8 ) )
            {
            // InternalMazeDsl.g:7171:1: ( ( rule__SergeantConfig__RoundsPerBurstAssignment_8 ) )
            // InternalMazeDsl.g:7172:2: ( rule__SergeantConfig__RoundsPerBurstAssignment_8 )
            {
             before(grammarAccess.getSergeantConfigAccess().getRoundsPerBurstAssignment_8()); 
            // InternalMazeDsl.g:7173:2: ( rule__SergeantConfig__RoundsPerBurstAssignment_8 )
            // InternalMazeDsl.g:7173:3: rule__SergeantConfig__RoundsPerBurstAssignment_8
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
    // InternalMazeDsl.g:7181:1: rule__SergeantConfig__Group__9 : rule__SergeantConfig__Group__9__Impl rule__SergeantConfig__Group__10 ;
    public final void rule__SergeantConfig__Group__9() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7185:1: ( rule__SergeantConfig__Group__9__Impl rule__SergeantConfig__Group__10 )
            // InternalMazeDsl.g:7186:2: rule__SergeantConfig__Group__9__Impl rule__SergeantConfig__Group__10
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
    // InternalMazeDsl.g:7193:1: rule__SergeantConfig__Group__9__Impl : ( 'roundDamage' ) ;
    public final void rule__SergeantConfig__Group__9__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7197:1: ( ( 'roundDamage' ) )
            // InternalMazeDsl.g:7198:1: ( 'roundDamage' )
            {
            // InternalMazeDsl.g:7198:1: ( 'roundDamage' )
            // InternalMazeDsl.g:7199:2: 'roundDamage'
            {
             before(grammarAccess.getSergeantConfigAccess().getRoundDamageKeyword_9()); 
            match(input,26,FOLLOW_2); 
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
    // InternalMazeDsl.g:7208:1: rule__SergeantConfig__Group__10 : rule__SergeantConfig__Group__10__Impl rule__SergeantConfig__Group__11 ;
    public final void rule__SergeantConfig__Group__10() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7212:1: ( rule__SergeantConfig__Group__10__Impl rule__SergeantConfig__Group__11 )
            // InternalMazeDsl.g:7213:2: rule__SergeantConfig__Group__10__Impl rule__SergeantConfig__Group__11
            {
            pushFollow(FOLLOW_60);
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
    // InternalMazeDsl.g:7220:1: rule__SergeantConfig__Group__10__Impl : ( ( rule__SergeantConfig__RoundDamageAssignment_10 ) ) ;
    public final void rule__SergeantConfig__Group__10__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7224:1: ( ( ( rule__SergeantConfig__RoundDamageAssignment_10 ) ) )
            // InternalMazeDsl.g:7225:1: ( ( rule__SergeantConfig__RoundDamageAssignment_10 ) )
            {
            // InternalMazeDsl.g:7225:1: ( ( rule__SergeantConfig__RoundDamageAssignment_10 ) )
            // InternalMazeDsl.g:7226:2: ( rule__SergeantConfig__RoundDamageAssignment_10 )
            {
             before(grammarAccess.getSergeantConfigAccess().getRoundDamageAssignment_10()); 
            // InternalMazeDsl.g:7227:2: ( rule__SergeantConfig__RoundDamageAssignment_10 )
            // InternalMazeDsl.g:7227:3: rule__SergeantConfig__RoundDamageAssignment_10
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
    // InternalMazeDsl.g:7235:1: rule__SergeantConfig__Group__11 : rule__SergeantConfig__Group__11__Impl rule__SergeantConfig__Group__12 ;
    public final void rule__SergeantConfig__Group__11() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7239:1: ( rule__SergeantConfig__Group__11__Impl rule__SergeantConfig__Group__12 )
            // InternalMazeDsl.g:7240:2: rule__SergeantConfig__Group__11__Impl rule__SergeantConfig__Group__12
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
    // InternalMazeDsl.g:7247:1: rule__SergeantConfig__Group__11__Impl : ( 'roundInterval' ) ;
    public final void rule__SergeantConfig__Group__11__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7251:1: ( ( 'roundInterval' ) )
            // InternalMazeDsl.g:7252:1: ( 'roundInterval' )
            {
            // InternalMazeDsl.g:7252:1: ( 'roundInterval' )
            // InternalMazeDsl.g:7253:2: 'roundInterval'
            {
             before(grammarAccess.getSergeantConfigAccess().getRoundIntervalKeyword_11()); 
            match(input,27,FOLLOW_2); 
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
    // InternalMazeDsl.g:7262:1: rule__SergeantConfig__Group__12 : rule__SergeantConfig__Group__12__Impl rule__SergeantConfig__Group__13 ;
    public final void rule__SergeantConfig__Group__12() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7266:1: ( rule__SergeantConfig__Group__12__Impl rule__SergeantConfig__Group__13 )
            // InternalMazeDsl.g:7267:2: rule__SergeantConfig__Group__12__Impl rule__SergeantConfig__Group__13
            {
            pushFollow(FOLLOW_61);
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
    // InternalMazeDsl.g:7274:1: rule__SergeantConfig__Group__12__Impl : ( ( rule__SergeantConfig__RoundIntervalSecondsAssignment_12 ) ) ;
    public final void rule__SergeantConfig__Group__12__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7278:1: ( ( ( rule__SergeantConfig__RoundIntervalSecondsAssignment_12 ) ) )
            // InternalMazeDsl.g:7279:1: ( ( rule__SergeantConfig__RoundIntervalSecondsAssignment_12 ) )
            {
            // InternalMazeDsl.g:7279:1: ( ( rule__SergeantConfig__RoundIntervalSecondsAssignment_12 ) )
            // InternalMazeDsl.g:7280:2: ( rule__SergeantConfig__RoundIntervalSecondsAssignment_12 )
            {
             before(grammarAccess.getSergeantConfigAccess().getRoundIntervalSecondsAssignment_12()); 
            // InternalMazeDsl.g:7281:2: ( rule__SergeantConfig__RoundIntervalSecondsAssignment_12 )
            // InternalMazeDsl.g:7281:3: rule__SergeantConfig__RoundIntervalSecondsAssignment_12
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
    // InternalMazeDsl.g:7289:1: rule__SergeantConfig__Group__13 : rule__SergeantConfig__Group__13__Impl rule__SergeantConfig__Group__14 ;
    public final void rule__SergeantConfig__Group__13() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7293:1: ( rule__SergeantConfig__Group__13__Impl rule__SergeantConfig__Group__14 )
            // InternalMazeDsl.g:7294:2: rule__SergeantConfig__Group__13__Impl rule__SergeantConfig__Group__14
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
    // InternalMazeDsl.g:7301:1: rule__SergeantConfig__Group__13__Impl : ( 'burstCooldown' ) ;
    public final void rule__SergeantConfig__Group__13__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7305:1: ( ( 'burstCooldown' ) )
            // InternalMazeDsl.g:7306:1: ( 'burstCooldown' )
            {
            // InternalMazeDsl.g:7306:1: ( 'burstCooldown' )
            // InternalMazeDsl.g:7307:2: 'burstCooldown'
            {
             before(grammarAccess.getSergeantConfigAccess().getBurstCooldownKeyword_13()); 
            match(input,28,FOLLOW_2); 
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
    // InternalMazeDsl.g:7316:1: rule__SergeantConfig__Group__14 : rule__SergeantConfig__Group__14__Impl rule__SergeantConfig__Group__15 ;
    public final void rule__SergeantConfig__Group__14() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7320:1: ( rule__SergeantConfig__Group__14__Impl rule__SergeantConfig__Group__15 )
            // InternalMazeDsl.g:7321:2: rule__SergeantConfig__Group__14__Impl rule__SergeantConfig__Group__15
            {
            pushFollow(FOLLOW_62);
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
    // InternalMazeDsl.g:7328:1: rule__SergeantConfig__Group__14__Impl : ( ( rule__SergeantConfig__BurstCooldownSecondsAssignment_14 ) ) ;
    public final void rule__SergeantConfig__Group__14__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7332:1: ( ( ( rule__SergeantConfig__BurstCooldownSecondsAssignment_14 ) ) )
            // InternalMazeDsl.g:7333:1: ( ( rule__SergeantConfig__BurstCooldownSecondsAssignment_14 ) )
            {
            // InternalMazeDsl.g:7333:1: ( ( rule__SergeantConfig__BurstCooldownSecondsAssignment_14 ) )
            // InternalMazeDsl.g:7334:2: ( rule__SergeantConfig__BurstCooldownSecondsAssignment_14 )
            {
             before(grammarAccess.getSergeantConfigAccess().getBurstCooldownSecondsAssignment_14()); 
            // InternalMazeDsl.g:7335:2: ( rule__SergeantConfig__BurstCooldownSecondsAssignment_14 )
            // InternalMazeDsl.g:7335:3: rule__SergeantConfig__BurstCooldownSecondsAssignment_14
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
    // InternalMazeDsl.g:7343:1: rule__SergeantConfig__Group__15 : rule__SergeantConfig__Group__15__Impl rule__SergeantConfig__Group__16 ;
    public final void rule__SergeantConfig__Group__15() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7347:1: ( rule__SergeantConfig__Group__15__Impl rule__SergeantConfig__Group__16 )
            // InternalMazeDsl.g:7348:2: rule__SergeantConfig__Group__15__Impl rule__SergeantConfig__Group__16
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
    // InternalMazeDsl.g:7355:1: rule__SergeantConfig__Group__15__Impl : ( 'sprite' ) ;
    public final void rule__SergeantConfig__Group__15__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7359:1: ( ( 'sprite' ) )
            // InternalMazeDsl.g:7360:1: ( 'sprite' )
            {
            // InternalMazeDsl.g:7360:1: ( 'sprite' )
            // InternalMazeDsl.g:7361:2: 'sprite'
            {
             before(grammarAccess.getSergeantConfigAccess().getSpriteKeyword_15()); 
            match(input,29,FOLLOW_2); 
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
    // InternalMazeDsl.g:7370:1: rule__SergeantConfig__Group__16 : rule__SergeantConfig__Group__16__Impl rule__SergeantConfig__Group__17 ;
    public final void rule__SergeantConfig__Group__16() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7374:1: ( rule__SergeantConfig__Group__16__Impl rule__SergeantConfig__Group__17 )
            // InternalMazeDsl.g:7375:2: rule__SergeantConfig__Group__16__Impl rule__SergeantConfig__Group__17
            {
            pushFollow(FOLLOW_63);
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
    // InternalMazeDsl.g:7382:1: rule__SergeantConfig__Group__16__Impl : ( ( rule__SergeantConfig__SpriteAssignment_16 ) ) ;
    public final void rule__SergeantConfig__Group__16__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7386:1: ( ( ( rule__SergeantConfig__SpriteAssignment_16 ) ) )
            // InternalMazeDsl.g:7387:1: ( ( rule__SergeantConfig__SpriteAssignment_16 ) )
            {
            // InternalMazeDsl.g:7387:1: ( ( rule__SergeantConfig__SpriteAssignment_16 ) )
            // InternalMazeDsl.g:7388:2: ( rule__SergeantConfig__SpriteAssignment_16 )
            {
             before(grammarAccess.getSergeantConfigAccess().getSpriteAssignment_16()); 
            // InternalMazeDsl.g:7389:2: ( rule__SergeantConfig__SpriteAssignment_16 )
            // InternalMazeDsl.g:7389:3: rule__SergeantConfig__SpriteAssignment_16
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
    // InternalMazeDsl.g:7397:1: rule__SergeantConfig__Group__17 : rule__SergeantConfig__Group__17__Impl rule__SergeantConfig__Group__18 ;
    public final void rule__SergeantConfig__Group__17() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7401:1: ( rule__SergeantConfig__Group__17__Impl rule__SergeantConfig__Group__18 )
            // InternalMazeDsl.g:7402:2: rule__SergeantConfig__Group__17__Impl rule__SergeantConfig__Group__18
            {
            pushFollow(FOLLOW_63);
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
    // InternalMazeDsl.g:7409:1: rule__SergeantConfig__Group__17__Impl : ( ( rule__SergeantConfig__Group_17__0 )? ) ;
    public final void rule__SergeantConfig__Group__17__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7413:1: ( ( ( rule__SergeantConfig__Group_17__0 )? ) )
            // InternalMazeDsl.g:7414:1: ( ( rule__SergeantConfig__Group_17__0 )? )
            {
            // InternalMazeDsl.g:7414:1: ( ( rule__SergeantConfig__Group_17__0 )? )
            // InternalMazeDsl.g:7415:2: ( rule__SergeantConfig__Group_17__0 )?
            {
             before(grammarAccess.getSergeantConfigAccess().getGroup_17()); 
            // InternalMazeDsl.g:7416:2: ( rule__SergeantConfig__Group_17__0 )?
            int alt64=2;
            int LA64_0 = input.LA(1);

            if ( (LA64_0==30) ) {
                alt64=1;
            }
            switch (alt64) {
                case 1 :
                    // InternalMazeDsl.g:7416:3: rule__SergeantConfig__Group_17__0
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
    // InternalMazeDsl.g:7424:1: rule__SergeantConfig__Group__18 : rule__SergeantConfig__Group__18__Impl rule__SergeantConfig__Group__19 ;
    public final void rule__SergeantConfig__Group__18() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7428:1: ( rule__SergeantConfig__Group__18__Impl rule__SergeantConfig__Group__19 )
            // InternalMazeDsl.g:7429:2: rule__SergeantConfig__Group__18__Impl rule__SergeantConfig__Group__19
            {
            pushFollow(FOLLOW_64);
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
    // InternalMazeDsl.g:7436:1: rule__SergeantConfig__Group__18__Impl : ( 'drops' ) ;
    public final void rule__SergeantConfig__Group__18__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7440:1: ( ( 'drops' ) )
            // InternalMazeDsl.g:7441:1: ( 'drops' )
            {
            // InternalMazeDsl.g:7441:1: ( 'drops' )
            // InternalMazeDsl.g:7442:2: 'drops'
            {
             before(grammarAccess.getSergeantConfigAccess().getDropsKeyword_18()); 
            match(input,31,FOLLOW_2); 
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
    // InternalMazeDsl.g:7451:1: rule__SergeantConfig__Group__19 : rule__SergeantConfig__Group__19__Impl rule__SergeantConfig__Group__20 ;
    public final void rule__SergeantConfig__Group__19() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7455:1: ( rule__SergeantConfig__Group__19__Impl rule__SergeantConfig__Group__20 )
            // InternalMazeDsl.g:7456:2: rule__SergeantConfig__Group__19__Impl rule__SergeantConfig__Group__20
            {
            pushFollow(FOLLOW_65);
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
    // InternalMazeDsl.g:7463:1: rule__SergeantConfig__Group__19__Impl : ( ( rule__SergeantConfig__DropWeaponAssignment_19 ) ) ;
    public final void rule__SergeantConfig__Group__19__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7467:1: ( ( ( rule__SergeantConfig__DropWeaponAssignment_19 ) ) )
            // InternalMazeDsl.g:7468:1: ( ( rule__SergeantConfig__DropWeaponAssignment_19 ) )
            {
            // InternalMazeDsl.g:7468:1: ( ( rule__SergeantConfig__DropWeaponAssignment_19 ) )
            // InternalMazeDsl.g:7469:2: ( rule__SergeantConfig__DropWeaponAssignment_19 )
            {
             before(grammarAccess.getSergeantConfigAccess().getDropWeaponAssignment_19()); 
            // InternalMazeDsl.g:7470:2: ( rule__SergeantConfig__DropWeaponAssignment_19 )
            // InternalMazeDsl.g:7470:3: rule__SergeantConfig__DropWeaponAssignment_19
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
    // InternalMazeDsl.g:7478:1: rule__SergeantConfig__Group__20 : rule__SergeantConfig__Group__20__Impl rule__SergeantConfig__Group__21 ;
    public final void rule__SergeantConfig__Group__20() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7482:1: ( rule__SergeantConfig__Group__20__Impl rule__SergeantConfig__Group__21 )
            // InternalMazeDsl.g:7483:2: rule__SergeantConfig__Group__20__Impl rule__SergeantConfig__Group__21
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
    // InternalMazeDsl.g:7490:1: rule__SergeantConfig__Group__20__Impl : ( 'postDistance' ) ;
    public final void rule__SergeantConfig__Group__20__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7494:1: ( ( 'postDistance' ) )
            // InternalMazeDsl.g:7495:1: ( 'postDistance' )
            {
            // InternalMazeDsl.g:7495:1: ( 'postDistance' )
            // InternalMazeDsl.g:7496:2: 'postDistance'
            {
             before(grammarAccess.getSergeantConfigAccess().getPostDistanceKeyword_20()); 
            match(input,32,FOLLOW_2); 
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
    // InternalMazeDsl.g:7505:1: rule__SergeantConfig__Group__21 : rule__SergeantConfig__Group__21__Impl rule__SergeantConfig__Group__22 ;
    public final void rule__SergeantConfig__Group__21() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7509:1: ( rule__SergeantConfig__Group__21__Impl rule__SergeantConfig__Group__22 )
            // InternalMazeDsl.g:7510:2: rule__SergeantConfig__Group__21__Impl rule__SergeantConfig__Group__22
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
    // InternalMazeDsl.g:7517:1: rule__SergeantConfig__Group__21__Impl : ( ( rule__SergeantConfig__PostDistanceFromHeartAssignment_21 ) ) ;
    public final void rule__SergeantConfig__Group__21__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7521:1: ( ( ( rule__SergeantConfig__PostDistanceFromHeartAssignment_21 ) ) )
            // InternalMazeDsl.g:7522:1: ( ( rule__SergeantConfig__PostDistanceFromHeartAssignment_21 ) )
            {
            // InternalMazeDsl.g:7522:1: ( ( rule__SergeantConfig__PostDistanceFromHeartAssignment_21 ) )
            // InternalMazeDsl.g:7523:2: ( rule__SergeantConfig__PostDistanceFromHeartAssignment_21 )
            {
             before(grammarAccess.getSergeantConfigAccess().getPostDistanceFromHeartAssignment_21()); 
            // InternalMazeDsl.g:7524:2: ( rule__SergeantConfig__PostDistanceFromHeartAssignment_21 )
            // InternalMazeDsl.g:7524:3: rule__SergeantConfig__PostDistanceFromHeartAssignment_21
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
    // InternalMazeDsl.g:7532:1: rule__SergeantConfig__Group__22 : rule__SergeantConfig__Group__22__Impl ;
    public final void rule__SergeantConfig__Group__22() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7536:1: ( rule__SergeantConfig__Group__22__Impl )
            // InternalMazeDsl.g:7537:2: rule__SergeantConfig__Group__22__Impl
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
    // InternalMazeDsl.g:7543:1: rule__SergeantConfig__Group__22__Impl : ( '}' ) ;
    public final void rule__SergeantConfig__Group__22__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7547:1: ( ( '}' ) )
            // InternalMazeDsl.g:7548:1: ( '}' )
            {
            // InternalMazeDsl.g:7548:1: ( '}' )
            // InternalMazeDsl.g:7549:2: '}'
            {
             before(grammarAccess.getSergeantConfigAccess().getRightCurlyBracketKeyword_22()); 
            match(input,69,FOLLOW_2); 
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
    // InternalMazeDsl.g:7559:1: rule__SergeantConfig__Group_17__0 : rule__SergeantConfig__Group_17__0__Impl rule__SergeantConfig__Group_17__1 ;
    public final void rule__SergeantConfig__Group_17__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7563:1: ( rule__SergeantConfig__Group_17__0__Impl rule__SergeantConfig__Group_17__1 )
            // InternalMazeDsl.g:7564:2: rule__SergeantConfig__Group_17__0__Impl rule__SergeantConfig__Group_17__1
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
    // InternalMazeDsl.g:7571:1: rule__SergeantConfig__Group_17__0__Impl : ( 'fireSound' ) ;
    public final void rule__SergeantConfig__Group_17__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7575:1: ( ( 'fireSound' ) )
            // InternalMazeDsl.g:7576:1: ( 'fireSound' )
            {
            // InternalMazeDsl.g:7576:1: ( 'fireSound' )
            // InternalMazeDsl.g:7577:2: 'fireSound'
            {
             before(grammarAccess.getSergeantConfigAccess().getFireSoundKeyword_17_0()); 
            match(input,30,FOLLOW_2); 
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
    // InternalMazeDsl.g:7586:1: rule__SergeantConfig__Group_17__1 : rule__SergeantConfig__Group_17__1__Impl ;
    public final void rule__SergeantConfig__Group_17__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7590:1: ( rule__SergeantConfig__Group_17__1__Impl )
            // InternalMazeDsl.g:7591:2: rule__SergeantConfig__Group_17__1__Impl
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
    // InternalMazeDsl.g:7597:1: rule__SergeantConfig__Group_17__1__Impl : ( ( rule__SergeantConfig__FireSoundAssignment_17_1 ) ) ;
    public final void rule__SergeantConfig__Group_17__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7601:1: ( ( ( rule__SergeantConfig__FireSoundAssignment_17_1 ) ) )
            // InternalMazeDsl.g:7602:1: ( ( rule__SergeantConfig__FireSoundAssignment_17_1 ) )
            {
            // InternalMazeDsl.g:7602:1: ( ( rule__SergeantConfig__FireSoundAssignment_17_1 ) )
            // InternalMazeDsl.g:7603:2: ( rule__SergeantConfig__FireSoundAssignment_17_1 )
            {
             before(grammarAccess.getSergeantConfigAccess().getFireSoundAssignment_17_1()); 
            // InternalMazeDsl.g:7604:2: ( rule__SergeantConfig__FireSoundAssignment_17_1 )
            // InternalMazeDsl.g:7604:3: rule__SergeantConfig__FireSoundAssignment_17_1
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
    // InternalMazeDsl.g:7613:1: rule__TierCountConfig__Group__0 : rule__TierCountConfig__Group__0__Impl rule__TierCountConfig__Group__1 ;
    public final void rule__TierCountConfig__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7617:1: ( rule__TierCountConfig__Group__0__Impl rule__TierCountConfig__Group__1 )
            // InternalMazeDsl.g:7618:2: rule__TierCountConfig__Group__0__Impl rule__TierCountConfig__Group__1
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
    // InternalMazeDsl.g:7625:1: rule__TierCountConfig__Group__0__Impl : ( 'sergeants' ) ;
    public final void rule__TierCountConfig__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7629:1: ( ( 'sergeants' ) )
            // InternalMazeDsl.g:7630:1: ( 'sergeants' )
            {
            // InternalMazeDsl.g:7630:1: ( 'sergeants' )
            // InternalMazeDsl.g:7631:2: 'sergeants'
            {
             before(grammarAccess.getTierCountConfigAccess().getSergeantsKeyword_0()); 
            match(input,23,FOLLOW_2); 
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
    // InternalMazeDsl.g:7640:1: rule__TierCountConfig__Group__1 : rule__TierCountConfig__Group__1__Impl rule__TierCountConfig__Group__2 ;
    public final void rule__TierCountConfig__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7644:1: ( rule__TierCountConfig__Group__1__Impl rule__TierCountConfig__Group__2 )
            // InternalMazeDsl.g:7645:2: rule__TierCountConfig__Group__1__Impl rule__TierCountConfig__Group__2
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
    // InternalMazeDsl.g:7652:1: rule__TierCountConfig__Group__1__Impl : ( ( rule__TierCountConfig__DifficultyAssignment_1 ) ) ;
    public final void rule__TierCountConfig__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7656:1: ( ( ( rule__TierCountConfig__DifficultyAssignment_1 ) ) )
            // InternalMazeDsl.g:7657:1: ( ( rule__TierCountConfig__DifficultyAssignment_1 ) )
            {
            // InternalMazeDsl.g:7657:1: ( ( rule__TierCountConfig__DifficultyAssignment_1 ) )
            // InternalMazeDsl.g:7658:2: ( rule__TierCountConfig__DifficultyAssignment_1 )
            {
             before(grammarAccess.getTierCountConfigAccess().getDifficultyAssignment_1()); 
            // InternalMazeDsl.g:7659:2: ( rule__TierCountConfig__DifficultyAssignment_1 )
            // InternalMazeDsl.g:7659:3: rule__TierCountConfig__DifficultyAssignment_1
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
    // InternalMazeDsl.g:7667:1: rule__TierCountConfig__Group__2 : rule__TierCountConfig__Group__2__Impl ;
    public final void rule__TierCountConfig__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7671:1: ( rule__TierCountConfig__Group__2__Impl )
            // InternalMazeDsl.g:7672:2: rule__TierCountConfig__Group__2__Impl
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
    // InternalMazeDsl.g:7678:1: rule__TierCountConfig__Group__2__Impl : ( ( rule__TierCountConfig__CountAssignment_2 ) ) ;
    public final void rule__TierCountConfig__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7682:1: ( ( ( rule__TierCountConfig__CountAssignment_2 ) ) )
            // InternalMazeDsl.g:7683:1: ( ( rule__TierCountConfig__CountAssignment_2 ) )
            {
            // InternalMazeDsl.g:7683:1: ( ( rule__TierCountConfig__CountAssignment_2 ) )
            // InternalMazeDsl.g:7684:2: ( rule__TierCountConfig__CountAssignment_2 )
            {
             before(grammarAccess.getTierCountConfigAccess().getCountAssignment_2()); 
            // InternalMazeDsl.g:7685:2: ( rule__TierCountConfig__CountAssignment_2 )
            // InternalMazeDsl.g:7685:3: rule__TierCountConfig__CountAssignment_2
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
    // InternalMazeDsl.g:7694:1: rule__MineRingConfig__Group__0 : rule__MineRingConfig__Group__0__Impl rule__MineRingConfig__Group__1 ;
    public final void rule__MineRingConfig__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7698:1: ( rule__MineRingConfig__Group__0__Impl rule__MineRingConfig__Group__1 )
            // InternalMazeDsl.g:7699:2: rule__MineRingConfig__Group__0__Impl rule__MineRingConfig__Group__1
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
    // InternalMazeDsl.g:7706:1: rule__MineRingConfig__Group__0__Impl : ( 'mines' ) ;
    public final void rule__MineRingConfig__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7710:1: ( ( 'mines' ) )
            // InternalMazeDsl.g:7711:1: ( 'mines' )
            {
            // InternalMazeDsl.g:7711:1: ( 'mines' )
            // InternalMazeDsl.g:7712:2: 'mines'
            {
             before(grammarAccess.getMineRingConfigAccess().getMinesKeyword_0()); 
            match(input,33,FOLLOW_2); 
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
    // InternalMazeDsl.g:7721:1: rule__MineRingConfig__Group__1 : rule__MineRingConfig__Group__1__Impl rule__MineRingConfig__Group__2 ;
    public final void rule__MineRingConfig__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7725:1: ( rule__MineRingConfig__Group__1__Impl rule__MineRingConfig__Group__2 )
            // InternalMazeDsl.g:7726:2: rule__MineRingConfig__Group__1__Impl rule__MineRingConfig__Group__2
            {
            pushFollow(FOLLOW_66);
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
    // InternalMazeDsl.g:7733:1: rule__MineRingConfig__Group__1__Impl : ( '{' ) ;
    public final void rule__MineRingConfig__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7737:1: ( ( '{' ) )
            // InternalMazeDsl.g:7738:1: ( '{' )
            {
            // InternalMazeDsl.g:7738:1: ( '{' )
            // InternalMazeDsl.g:7739:2: '{'
            {
             before(grammarAccess.getMineRingConfigAccess().getLeftCurlyBracketKeyword_1()); 
            match(input,68,FOLLOW_2); 
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
    // InternalMazeDsl.g:7748:1: rule__MineRingConfig__Group__2 : rule__MineRingConfig__Group__2__Impl rule__MineRingConfig__Group__3 ;
    public final void rule__MineRingConfig__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7752:1: ( rule__MineRingConfig__Group__2__Impl rule__MineRingConfig__Group__3 )
            // InternalMazeDsl.g:7753:2: rule__MineRingConfig__Group__2__Impl rule__MineRingConfig__Group__3
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
    // InternalMazeDsl.g:7760:1: rule__MineRingConfig__Group__2__Impl : ( 'count' ) ;
    public final void rule__MineRingConfig__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7764:1: ( ( 'count' ) )
            // InternalMazeDsl.g:7765:1: ( 'count' )
            {
            // InternalMazeDsl.g:7765:1: ( 'count' )
            // InternalMazeDsl.g:7766:2: 'count'
            {
             before(grammarAccess.getMineRingConfigAccess().getCountKeyword_2()); 
            match(input,34,FOLLOW_2); 
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
    // InternalMazeDsl.g:7775:1: rule__MineRingConfig__Group__3 : rule__MineRingConfig__Group__3__Impl rule__MineRingConfig__Group__4 ;
    public final void rule__MineRingConfig__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7779:1: ( rule__MineRingConfig__Group__3__Impl rule__MineRingConfig__Group__4 )
            // InternalMazeDsl.g:7780:2: rule__MineRingConfig__Group__3__Impl rule__MineRingConfig__Group__4
            {
            pushFollow(FOLLOW_67);
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
    // InternalMazeDsl.g:7787:1: rule__MineRingConfig__Group__3__Impl : ( ( rule__MineRingConfig__CountAssignment_3 ) ) ;
    public final void rule__MineRingConfig__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7791:1: ( ( ( rule__MineRingConfig__CountAssignment_3 ) ) )
            // InternalMazeDsl.g:7792:1: ( ( rule__MineRingConfig__CountAssignment_3 ) )
            {
            // InternalMazeDsl.g:7792:1: ( ( rule__MineRingConfig__CountAssignment_3 ) )
            // InternalMazeDsl.g:7793:2: ( rule__MineRingConfig__CountAssignment_3 )
            {
             before(grammarAccess.getMineRingConfigAccess().getCountAssignment_3()); 
            // InternalMazeDsl.g:7794:2: ( rule__MineRingConfig__CountAssignment_3 )
            // InternalMazeDsl.g:7794:3: rule__MineRingConfig__CountAssignment_3
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
    // InternalMazeDsl.g:7802:1: rule__MineRingConfig__Group__4 : rule__MineRingConfig__Group__4__Impl rule__MineRingConfig__Group__5 ;
    public final void rule__MineRingConfig__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7806:1: ( rule__MineRingConfig__Group__4__Impl rule__MineRingConfig__Group__5 )
            // InternalMazeDsl.g:7807:2: rule__MineRingConfig__Group__4__Impl rule__MineRingConfig__Group__5
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
    // InternalMazeDsl.g:7814:1: rule__MineRingConfig__Group__4__Impl : ( 'ringRadius' ) ;
    public final void rule__MineRingConfig__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7818:1: ( ( 'ringRadius' ) )
            // InternalMazeDsl.g:7819:1: ( 'ringRadius' )
            {
            // InternalMazeDsl.g:7819:1: ( 'ringRadius' )
            // InternalMazeDsl.g:7820:2: 'ringRadius'
            {
             before(grammarAccess.getMineRingConfigAccess().getRingRadiusKeyword_4()); 
            match(input,35,FOLLOW_2); 
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
    // InternalMazeDsl.g:7829:1: rule__MineRingConfig__Group__5 : rule__MineRingConfig__Group__5__Impl rule__MineRingConfig__Group__6 ;
    public final void rule__MineRingConfig__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7833:1: ( rule__MineRingConfig__Group__5__Impl rule__MineRingConfig__Group__6 )
            // InternalMazeDsl.g:7834:2: rule__MineRingConfig__Group__5__Impl rule__MineRingConfig__Group__6
            {
            pushFollow(FOLLOW_68);
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
    // InternalMazeDsl.g:7841:1: rule__MineRingConfig__Group__5__Impl : ( ( rule__MineRingConfig__RingRadiusAssignment_5 ) ) ;
    public final void rule__MineRingConfig__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7845:1: ( ( ( rule__MineRingConfig__RingRadiusAssignment_5 ) ) )
            // InternalMazeDsl.g:7846:1: ( ( rule__MineRingConfig__RingRadiusAssignment_5 ) )
            {
            // InternalMazeDsl.g:7846:1: ( ( rule__MineRingConfig__RingRadiusAssignment_5 ) )
            // InternalMazeDsl.g:7847:2: ( rule__MineRingConfig__RingRadiusAssignment_5 )
            {
             before(grammarAccess.getMineRingConfigAccess().getRingRadiusAssignment_5()); 
            // InternalMazeDsl.g:7848:2: ( rule__MineRingConfig__RingRadiusAssignment_5 )
            // InternalMazeDsl.g:7848:3: rule__MineRingConfig__RingRadiusAssignment_5
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
    // InternalMazeDsl.g:7856:1: rule__MineRingConfig__Group__6 : rule__MineRingConfig__Group__6__Impl rule__MineRingConfig__Group__7 ;
    public final void rule__MineRingConfig__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7860:1: ( rule__MineRingConfig__Group__6__Impl rule__MineRingConfig__Group__7 )
            // InternalMazeDsl.g:7861:2: rule__MineRingConfig__Group__6__Impl rule__MineRingConfig__Group__7
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
    // InternalMazeDsl.g:7868:1: rule__MineRingConfig__Group__6__Impl : ( 'triggerRadius' ) ;
    public final void rule__MineRingConfig__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7872:1: ( ( 'triggerRadius' ) )
            // InternalMazeDsl.g:7873:1: ( 'triggerRadius' )
            {
            // InternalMazeDsl.g:7873:1: ( 'triggerRadius' )
            // InternalMazeDsl.g:7874:2: 'triggerRadius'
            {
             before(grammarAccess.getMineRingConfigAccess().getTriggerRadiusKeyword_6()); 
            match(input,36,FOLLOW_2); 
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
    // InternalMazeDsl.g:7883:1: rule__MineRingConfig__Group__7 : rule__MineRingConfig__Group__7__Impl rule__MineRingConfig__Group__8 ;
    public final void rule__MineRingConfig__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7887:1: ( rule__MineRingConfig__Group__7__Impl rule__MineRingConfig__Group__8 )
            // InternalMazeDsl.g:7888:2: rule__MineRingConfig__Group__7__Impl rule__MineRingConfig__Group__8
            {
            pushFollow(FOLLOW_69);
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
    // InternalMazeDsl.g:7895:1: rule__MineRingConfig__Group__7__Impl : ( ( rule__MineRingConfig__TriggerRadiusAssignment_7 ) ) ;
    public final void rule__MineRingConfig__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7899:1: ( ( ( rule__MineRingConfig__TriggerRadiusAssignment_7 ) ) )
            // InternalMazeDsl.g:7900:1: ( ( rule__MineRingConfig__TriggerRadiusAssignment_7 ) )
            {
            // InternalMazeDsl.g:7900:1: ( ( rule__MineRingConfig__TriggerRadiusAssignment_7 ) )
            // InternalMazeDsl.g:7901:2: ( rule__MineRingConfig__TriggerRadiusAssignment_7 )
            {
             before(grammarAccess.getMineRingConfigAccess().getTriggerRadiusAssignment_7()); 
            // InternalMazeDsl.g:7902:2: ( rule__MineRingConfig__TriggerRadiusAssignment_7 )
            // InternalMazeDsl.g:7902:3: rule__MineRingConfig__TriggerRadiusAssignment_7
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
    // InternalMazeDsl.g:7910:1: rule__MineRingConfig__Group__8 : rule__MineRingConfig__Group__8__Impl rule__MineRingConfig__Group__9 ;
    public final void rule__MineRingConfig__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7914:1: ( rule__MineRingConfig__Group__8__Impl rule__MineRingConfig__Group__9 )
            // InternalMazeDsl.g:7915:2: rule__MineRingConfig__Group__8__Impl rule__MineRingConfig__Group__9
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
    // InternalMazeDsl.g:7922:1: rule__MineRingConfig__Group__8__Impl : ( 'pulse' ) ;
    public final void rule__MineRingConfig__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7926:1: ( ( 'pulse' ) )
            // InternalMazeDsl.g:7927:1: ( 'pulse' )
            {
            // InternalMazeDsl.g:7927:1: ( 'pulse' )
            // InternalMazeDsl.g:7928:2: 'pulse'
            {
             before(grammarAccess.getMineRingConfigAccess().getPulseKeyword_8()); 
            match(input,37,FOLLOW_2); 
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
    // InternalMazeDsl.g:7937:1: rule__MineRingConfig__Group__9 : rule__MineRingConfig__Group__9__Impl rule__MineRingConfig__Group__10 ;
    public final void rule__MineRingConfig__Group__9() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7941:1: ( rule__MineRingConfig__Group__9__Impl rule__MineRingConfig__Group__10 )
            // InternalMazeDsl.g:7942:2: rule__MineRingConfig__Group__9__Impl rule__MineRingConfig__Group__10
            {
            pushFollow(FOLLOW_70);
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
    // InternalMazeDsl.g:7949:1: rule__MineRingConfig__Group__9__Impl : ( ( rule__MineRingConfig__PulseSecondsAssignment_9 ) ) ;
    public final void rule__MineRingConfig__Group__9__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7953:1: ( ( ( rule__MineRingConfig__PulseSecondsAssignment_9 ) ) )
            // InternalMazeDsl.g:7954:1: ( ( rule__MineRingConfig__PulseSecondsAssignment_9 ) )
            {
            // InternalMazeDsl.g:7954:1: ( ( rule__MineRingConfig__PulseSecondsAssignment_9 ) )
            // InternalMazeDsl.g:7955:2: ( rule__MineRingConfig__PulseSecondsAssignment_9 )
            {
             before(grammarAccess.getMineRingConfigAccess().getPulseSecondsAssignment_9()); 
            // InternalMazeDsl.g:7956:2: ( rule__MineRingConfig__PulseSecondsAssignment_9 )
            // InternalMazeDsl.g:7956:3: rule__MineRingConfig__PulseSecondsAssignment_9
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
    // InternalMazeDsl.g:7964:1: rule__MineRingConfig__Group__10 : rule__MineRingConfig__Group__10__Impl rule__MineRingConfig__Group__11 ;
    public final void rule__MineRingConfig__Group__10() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7968:1: ( rule__MineRingConfig__Group__10__Impl rule__MineRingConfig__Group__11 )
            // InternalMazeDsl.g:7969:2: rule__MineRingConfig__Group__10__Impl rule__MineRingConfig__Group__11
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
    // InternalMazeDsl.g:7976:1: rule__MineRingConfig__Group__10__Impl : ( 'blastRadius' ) ;
    public final void rule__MineRingConfig__Group__10__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7980:1: ( ( 'blastRadius' ) )
            // InternalMazeDsl.g:7981:1: ( 'blastRadius' )
            {
            // InternalMazeDsl.g:7981:1: ( 'blastRadius' )
            // InternalMazeDsl.g:7982:2: 'blastRadius'
            {
             before(grammarAccess.getMineRingConfigAccess().getBlastRadiusKeyword_10()); 
            match(input,38,FOLLOW_2); 
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
    // InternalMazeDsl.g:7991:1: rule__MineRingConfig__Group__11 : rule__MineRingConfig__Group__11__Impl rule__MineRingConfig__Group__12 ;
    public final void rule__MineRingConfig__Group__11() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:7995:1: ( rule__MineRingConfig__Group__11__Impl rule__MineRingConfig__Group__12 )
            // InternalMazeDsl.g:7996:2: rule__MineRingConfig__Group__11__Impl rule__MineRingConfig__Group__12
            {
            pushFollow(FOLLOW_71);
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
    // InternalMazeDsl.g:8003:1: rule__MineRingConfig__Group__11__Impl : ( ( rule__MineRingConfig__BlastRadiusAssignment_11 ) ) ;
    public final void rule__MineRingConfig__Group__11__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8007:1: ( ( ( rule__MineRingConfig__BlastRadiusAssignment_11 ) ) )
            // InternalMazeDsl.g:8008:1: ( ( rule__MineRingConfig__BlastRadiusAssignment_11 ) )
            {
            // InternalMazeDsl.g:8008:1: ( ( rule__MineRingConfig__BlastRadiusAssignment_11 ) )
            // InternalMazeDsl.g:8009:2: ( rule__MineRingConfig__BlastRadiusAssignment_11 )
            {
             before(grammarAccess.getMineRingConfigAccess().getBlastRadiusAssignment_11()); 
            // InternalMazeDsl.g:8010:2: ( rule__MineRingConfig__BlastRadiusAssignment_11 )
            // InternalMazeDsl.g:8010:3: rule__MineRingConfig__BlastRadiusAssignment_11
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
    // InternalMazeDsl.g:8018:1: rule__MineRingConfig__Group__12 : rule__MineRingConfig__Group__12__Impl rule__MineRingConfig__Group__13 ;
    public final void rule__MineRingConfig__Group__12() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8022:1: ( rule__MineRingConfig__Group__12__Impl rule__MineRingConfig__Group__13 )
            // InternalMazeDsl.g:8023:2: rule__MineRingConfig__Group__12__Impl rule__MineRingConfig__Group__13
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
    // InternalMazeDsl.g:8030:1: rule__MineRingConfig__Group__12__Impl : ( 'blastDamage' ) ;
    public final void rule__MineRingConfig__Group__12__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8034:1: ( ( 'blastDamage' ) )
            // InternalMazeDsl.g:8035:1: ( 'blastDamage' )
            {
            // InternalMazeDsl.g:8035:1: ( 'blastDamage' )
            // InternalMazeDsl.g:8036:2: 'blastDamage'
            {
             before(grammarAccess.getMineRingConfigAccess().getBlastDamageKeyword_12()); 
            match(input,39,FOLLOW_2); 
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
    // InternalMazeDsl.g:8045:1: rule__MineRingConfig__Group__13 : rule__MineRingConfig__Group__13__Impl rule__MineRingConfig__Group__14 ;
    public final void rule__MineRingConfig__Group__13() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8049:1: ( rule__MineRingConfig__Group__13__Impl rule__MineRingConfig__Group__14 )
            // InternalMazeDsl.g:8050:2: rule__MineRingConfig__Group__13__Impl rule__MineRingConfig__Group__14
            {
            pushFollow(FOLLOW_72);
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
    // InternalMazeDsl.g:8057:1: rule__MineRingConfig__Group__13__Impl : ( ( rule__MineRingConfig__BlastDamageAssignment_13 ) ) ;
    public final void rule__MineRingConfig__Group__13__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8061:1: ( ( ( rule__MineRingConfig__BlastDamageAssignment_13 ) ) )
            // InternalMazeDsl.g:8062:1: ( ( rule__MineRingConfig__BlastDamageAssignment_13 ) )
            {
            // InternalMazeDsl.g:8062:1: ( ( rule__MineRingConfig__BlastDamageAssignment_13 ) )
            // InternalMazeDsl.g:8063:2: ( rule__MineRingConfig__BlastDamageAssignment_13 )
            {
             before(grammarAccess.getMineRingConfigAccess().getBlastDamageAssignment_13()); 
            // InternalMazeDsl.g:8064:2: ( rule__MineRingConfig__BlastDamageAssignment_13 )
            // InternalMazeDsl.g:8064:3: rule__MineRingConfig__BlastDamageAssignment_13
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
    // InternalMazeDsl.g:8072:1: rule__MineRingConfig__Group__14 : rule__MineRingConfig__Group__14__Impl rule__MineRingConfig__Group__15 ;
    public final void rule__MineRingConfig__Group__14() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8076:1: ( rule__MineRingConfig__Group__14__Impl rule__MineRingConfig__Group__15 )
            // InternalMazeDsl.g:8077:2: rule__MineRingConfig__Group__14__Impl rule__MineRingConfig__Group__15
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
    // InternalMazeDsl.g:8084:1: rule__MineRingConfig__Group__14__Impl : ( 'playerDamage' ) ;
    public final void rule__MineRingConfig__Group__14__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8088:1: ( ( 'playerDamage' ) )
            // InternalMazeDsl.g:8089:1: ( 'playerDamage' )
            {
            // InternalMazeDsl.g:8089:1: ( 'playerDamage' )
            // InternalMazeDsl.g:8090:2: 'playerDamage'
            {
             before(grammarAccess.getMineRingConfigAccess().getPlayerDamageKeyword_14()); 
            match(input,40,FOLLOW_2); 
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
    // InternalMazeDsl.g:8099:1: rule__MineRingConfig__Group__15 : rule__MineRingConfig__Group__15__Impl rule__MineRingConfig__Group__16 ;
    public final void rule__MineRingConfig__Group__15() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8103:1: ( rule__MineRingConfig__Group__15__Impl rule__MineRingConfig__Group__16 )
            // InternalMazeDsl.g:8104:2: rule__MineRingConfig__Group__15__Impl rule__MineRingConfig__Group__16
            {
            pushFollow(FOLLOW_73);
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
    // InternalMazeDsl.g:8111:1: rule__MineRingConfig__Group__15__Impl : ( ( rule__MineRingConfig__PlayerDamageAssignment_15 ) ) ;
    public final void rule__MineRingConfig__Group__15__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8115:1: ( ( ( rule__MineRingConfig__PlayerDamageAssignment_15 ) ) )
            // InternalMazeDsl.g:8116:1: ( ( rule__MineRingConfig__PlayerDamageAssignment_15 ) )
            {
            // InternalMazeDsl.g:8116:1: ( ( rule__MineRingConfig__PlayerDamageAssignment_15 ) )
            // InternalMazeDsl.g:8117:2: ( rule__MineRingConfig__PlayerDamageAssignment_15 )
            {
             before(grammarAccess.getMineRingConfigAccess().getPlayerDamageAssignment_15()); 
            // InternalMazeDsl.g:8118:2: ( rule__MineRingConfig__PlayerDamageAssignment_15 )
            // InternalMazeDsl.g:8118:3: rule__MineRingConfig__PlayerDamageAssignment_15
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
    // InternalMazeDsl.g:8126:1: rule__MineRingConfig__Group__16 : rule__MineRingConfig__Group__16__Impl rule__MineRingConfig__Group__17 ;
    public final void rule__MineRingConfig__Group__16() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8130:1: ( rule__MineRingConfig__Group__16__Impl rule__MineRingConfig__Group__17 )
            // InternalMazeDsl.g:8131:2: rule__MineRingConfig__Group__16__Impl rule__MineRingConfig__Group__17
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
    // InternalMazeDsl.g:8138:1: rule__MineRingConfig__Group__16__Impl : ( 'holeRadius' ) ;
    public final void rule__MineRingConfig__Group__16__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8142:1: ( ( 'holeRadius' ) )
            // InternalMazeDsl.g:8143:1: ( 'holeRadius' )
            {
            // InternalMazeDsl.g:8143:1: ( 'holeRadius' )
            // InternalMazeDsl.g:8144:2: 'holeRadius'
            {
             before(grammarAccess.getMineRingConfigAccess().getHoleRadiusKeyword_16()); 
            match(input,41,FOLLOW_2); 
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
    // InternalMazeDsl.g:8153:1: rule__MineRingConfig__Group__17 : rule__MineRingConfig__Group__17__Impl rule__MineRingConfig__Group__18 ;
    public final void rule__MineRingConfig__Group__17() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8157:1: ( rule__MineRingConfig__Group__17__Impl rule__MineRingConfig__Group__18 )
            // InternalMazeDsl.g:8158:2: rule__MineRingConfig__Group__17__Impl rule__MineRingConfig__Group__18
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
    // InternalMazeDsl.g:8165:1: rule__MineRingConfig__Group__17__Impl : ( ( rule__MineRingConfig__HoleRadiusAssignment_17 ) ) ;
    public final void rule__MineRingConfig__Group__17__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8169:1: ( ( ( rule__MineRingConfig__HoleRadiusAssignment_17 ) ) )
            // InternalMazeDsl.g:8170:1: ( ( rule__MineRingConfig__HoleRadiusAssignment_17 ) )
            {
            // InternalMazeDsl.g:8170:1: ( ( rule__MineRingConfig__HoleRadiusAssignment_17 ) )
            // InternalMazeDsl.g:8171:2: ( rule__MineRingConfig__HoleRadiusAssignment_17 )
            {
             before(grammarAccess.getMineRingConfigAccess().getHoleRadiusAssignment_17()); 
            // InternalMazeDsl.g:8172:2: ( rule__MineRingConfig__HoleRadiusAssignment_17 )
            // InternalMazeDsl.g:8172:3: rule__MineRingConfig__HoleRadiusAssignment_17
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
    // InternalMazeDsl.g:8180:1: rule__MineRingConfig__Group__18 : rule__MineRingConfig__Group__18__Impl ;
    public final void rule__MineRingConfig__Group__18() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8184:1: ( rule__MineRingConfig__Group__18__Impl )
            // InternalMazeDsl.g:8185:2: rule__MineRingConfig__Group__18__Impl
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
    // InternalMazeDsl.g:8191:1: rule__MineRingConfig__Group__18__Impl : ( '}' ) ;
    public final void rule__MineRingConfig__Group__18__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8195:1: ( ( '}' ) )
            // InternalMazeDsl.g:8196:1: ( '}' )
            {
            // InternalMazeDsl.g:8196:1: ( '}' )
            // InternalMazeDsl.g:8197:2: '}'
            {
             before(grammarAccess.getMineRingConfigAccess().getRightCurlyBracketKeyword_18()); 
            match(input,69,FOLLOW_2); 
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
    // InternalMazeDsl.g:8207:1: rule__PortalGhostConfig__Group__0 : rule__PortalGhostConfig__Group__0__Impl rule__PortalGhostConfig__Group__1 ;
    public final void rule__PortalGhostConfig__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8211:1: ( rule__PortalGhostConfig__Group__0__Impl rule__PortalGhostConfig__Group__1 )
            // InternalMazeDsl.g:8212:2: rule__PortalGhostConfig__Group__0__Impl rule__PortalGhostConfig__Group__1
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
    // InternalMazeDsl.g:8219:1: rule__PortalGhostConfig__Group__0__Impl : ( 'portalGhost' ) ;
    public final void rule__PortalGhostConfig__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8223:1: ( ( 'portalGhost' ) )
            // InternalMazeDsl.g:8224:1: ( 'portalGhost' )
            {
            // InternalMazeDsl.g:8224:1: ( 'portalGhost' )
            // InternalMazeDsl.g:8225:2: 'portalGhost'
            {
             before(grammarAccess.getPortalGhostConfigAccess().getPortalGhostKeyword_0()); 
            match(input,42,FOLLOW_2); 
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
    // InternalMazeDsl.g:8234:1: rule__PortalGhostConfig__Group__1 : rule__PortalGhostConfig__Group__1__Impl rule__PortalGhostConfig__Group__2 ;
    public final void rule__PortalGhostConfig__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8238:1: ( rule__PortalGhostConfig__Group__1__Impl rule__PortalGhostConfig__Group__2 )
            // InternalMazeDsl.g:8239:2: rule__PortalGhostConfig__Group__1__Impl rule__PortalGhostConfig__Group__2
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
    // InternalMazeDsl.g:8246:1: rule__PortalGhostConfig__Group__1__Impl : ( ( rule__PortalGhostConfig__DisplayNameAssignment_1 ) ) ;
    public final void rule__PortalGhostConfig__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8250:1: ( ( ( rule__PortalGhostConfig__DisplayNameAssignment_1 ) ) )
            // InternalMazeDsl.g:8251:1: ( ( rule__PortalGhostConfig__DisplayNameAssignment_1 ) )
            {
            // InternalMazeDsl.g:8251:1: ( ( rule__PortalGhostConfig__DisplayNameAssignment_1 ) )
            // InternalMazeDsl.g:8252:2: ( rule__PortalGhostConfig__DisplayNameAssignment_1 )
            {
             before(grammarAccess.getPortalGhostConfigAccess().getDisplayNameAssignment_1()); 
            // InternalMazeDsl.g:8253:2: ( rule__PortalGhostConfig__DisplayNameAssignment_1 )
            // InternalMazeDsl.g:8253:3: rule__PortalGhostConfig__DisplayNameAssignment_1
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
    // InternalMazeDsl.g:8261:1: rule__PortalGhostConfig__Group__2 : rule__PortalGhostConfig__Group__2__Impl rule__PortalGhostConfig__Group__3 ;
    public final void rule__PortalGhostConfig__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8265:1: ( rule__PortalGhostConfig__Group__2__Impl rule__PortalGhostConfig__Group__3 )
            // InternalMazeDsl.g:8266:2: rule__PortalGhostConfig__Group__2__Impl rule__PortalGhostConfig__Group__3
            {
            pushFollow(FOLLOW_56);
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
    // InternalMazeDsl.g:8273:1: rule__PortalGhostConfig__Group__2__Impl : ( '{' ) ;
    public final void rule__PortalGhostConfig__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8277:1: ( ( '{' ) )
            // InternalMazeDsl.g:8278:1: ( '{' )
            {
            // InternalMazeDsl.g:8278:1: ( '{' )
            // InternalMazeDsl.g:8279:2: '{'
            {
             before(grammarAccess.getPortalGhostConfigAccess().getLeftCurlyBracketKeyword_2()); 
            match(input,68,FOLLOW_2); 
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
    // InternalMazeDsl.g:8288:1: rule__PortalGhostConfig__Group__3 : rule__PortalGhostConfig__Group__3__Impl rule__PortalGhostConfig__Group__4 ;
    public final void rule__PortalGhostConfig__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8292:1: ( rule__PortalGhostConfig__Group__3__Impl rule__PortalGhostConfig__Group__4 )
            // InternalMazeDsl.g:8293:2: rule__PortalGhostConfig__Group__3__Impl rule__PortalGhostConfig__Group__4
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
    // InternalMazeDsl.g:8300:1: rule__PortalGhostConfig__Group__3__Impl : ( 'health' ) ;
    public final void rule__PortalGhostConfig__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8304:1: ( ( 'health' ) )
            // InternalMazeDsl.g:8305:1: ( 'health' )
            {
            // InternalMazeDsl.g:8305:1: ( 'health' )
            // InternalMazeDsl.g:8306:2: 'health'
            {
             before(grammarAccess.getPortalGhostConfigAccess().getHealthKeyword_3()); 
            match(input,82,FOLLOW_2); 
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
    // InternalMazeDsl.g:8315:1: rule__PortalGhostConfig__Group__4 : rule__PortalGhostConfig__Group__4__Impl rule__PortalGhostConfig__Group__5 ;
    public final void rule__PortalGhostConfig__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8319:1: ( rule__PortalGhostConfig__Group__4__Impl rule__PortalGhostConfig__Group__5 )
            // InternalMazeDsl.g:8320:2: rule__PortalGhostConfig__Group__4__Impl rule__PortalGhostConfig__Group__5
            {
            pushFollow(FOLLOW_74);
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
    // InternalMazeDsl.g:8327:1: rule__PortalGhostConfig__Group__4__Impl : ( ( rule__PortalGhostConfig__HealthAssignment_4 ) ) ;
    public final void rule__PortalGhostConfig__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8331:1: ( ( ( rule__PortalGhostConfig__HealthAssignment_4 ) ) )
            // InternalMazeDsl.g:8332:1: ( ( rule__PortalGhostConfig__HealthAssignment_4 ) )
            {
            // InternalMazeDsl.g:8332:1: ( ( rule__PortalGhostConfig__HealthAssignment_4 ) )
            // InternalMazeDsl.g:8333:2: ( rule__PortalGhostConfig__HealthAssignment_4 )
            {
             before(grammarAccess.getPortalGhostConfigAccess().getHealthAssignment_4()); 
            // InternalMazeDsl.g:8334:2: ( rule__PortalGhostConfig__HealthAssignment_4 )
            // InternalMazeDsl.g:8334:3: rule__PortalGhostConfig__HealthAssignment_4
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
    // InternalMazeDsl.g:8342:1: rule__PortalGhostConfig__Group__5 : rule__PortalGhostConfig__Group__5__Impl rule__PortalGhostConfig__Group__6 ;
    public final void rule__PortalGhostConfig__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8346:1: ( rule__PortalGhostConfig__Group__5__Impl rule__PortalGhostConfig__Group__6 )
            // InternalMazeDsl.g:8347:2: rule__PortalGhostConfig__Group__5__Impl rule__PortalGhostConfig__Group__6
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
    // InternalMazeDsl.g:8354:1: rule__PortalGhostConfig__Group__5__Impl : ( 'speed' ) ;
    public final void rule__PortalGhostConfig__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8358:1: ( ( 'speed' ) )
            // InternalMazeDsl.g:8359:1: ( 'speed' )
            {
            // InternalMazeDsl.g:8359:1: ( 'speed' )
            // InternalMazeDsl.g:8360:2: 'speed'
            {
             before(grammarAccess.getPortalGhostConfigAccess().getSpeedKeyword_5()); 
            match(input,83,FOLLOW_2); 
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
    // InternalMazeDsl.g:8369:1: rule__PortalGhostConfig__Group__6 : rule__PortalGhostConfig__Group__6__Impl rule__PortalGhostConfig__Group__7 ;
    public final void rule__PortalGhostConfig__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8373:1: ( rule__PortalGhostConfig__Group__6__Impl rule__PortalGhostConfig__Group__7 )
            // InternalMazeDsl.g:8374:2: rule__PortalGhostConfig__Group__6__Impl rule__PortalGhostConfig__Group__7
            {
            pushFollow(FOLLOW_62);
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
    // InternalMazeDsl.g:8381:1: rule__PortalGhostConfig__Group__6__Impl : ( ( rule__PortalGhostConfig__SpeedAssignment_6 ) ) ;
    public final void rule__PortalGhostConfig__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8385:1: ( ( ( rule__PortalGhostConfig__SpeedAssignment_6 ) ) )
            // InternalMazeDsl.g:8386:1: ( ( rule__PortalGhostConfig__SpeedAssignment_6 ) )
            {
            // InternalMazeDsl.g:8386:1: ( ( rule__PortalGhostConfig__SpeedAssignment_6 ) )
            // InternalMazeDsl.g:8387:2: ( rule__PortalGhostConfig__SpeedAssignment_6 )
            {
             before(grammarAccess.getPortalGhostConfigAccess().getSpeedAssignment_6()); 
            // InternalMazeDsl.g:8388:2: ( rule__PortalGhostConfig__SpeedAssignment_6 )
            // InternalMazeDsl.g:8388:3: rule__PortalGhostConfig__SpeedAssignment_6
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
    // InternalMazeDsl.g:8396:1: rule__PortalGhostConfig__Group__7 : rule__PortalGhostConfig__Group__7__Impl rule__PortalGhostConfig__Group__8 ;
    public final void rule__PortalGhostConfig__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8400:1: ( rule__PortalGhostConfig__Group__7__Impl rule__PortalGhostConfig__Group__8 )
            // InternalMazeDsl.g:8401:2: rule__PortalGhostConfig__Group__7__Impl rule__PortalGhostConfig__Group__8
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
    // InternalMazeDsl.g:8408:1: rule__PortalGhostConfig__Group__7__Impl : ( 'sprite' ) ;
    public final void rule__PortalGhostConfig__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8412:1: ( ( 'sprite' ) )
            // InternalMazeDsl.g:8413:1: ( 'sprite' )
            {
            // InternalMazeDsl.g:8413:1: ( 'sprite' )
            // InternalMazeDsl.g:8414:2: 'sprite'
            {
             before(grammarAccess.getPortalGhostConfigAccess().getSpriteKeyword_7()); 
            match(input,29,FOLLOW_2); 
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
    // InternalMazeDsl.g:8423:1: rule__PortalGhostConfig__Group__8 : rule__PortalGhostConfig__Group__8__Impl rule__PortalGhostConfig__Group__9 ;
    public final void rule__PortalGhostConfig__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8427:1: ( rule__PortalGhostConfig__Group__8__Impl rule__PortalGhostConfig__Group__9 )
            // InternalMazeDsl.g:8428:2: rule__PortalGhostConfig__Group__8__Impl rule__PortalGhostConfig__Group__9
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
    // InternalMazeDsl.g:8435:1: rule__PortalGhostConfig__Group__8__Impl : ( ( rule__PortalGhostConfig__SpriteAssignment_8 ) ) ;
    public final void rule__PortalGhostConfig__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8439:1: ( ( ( rule__PortalGhostConfig__SpriteAssignment_8 ) ) )
            // InternalMazeDsl.g:8440:1: ( ( rule__PortalGhostConfig__SpriteAssignment_8 ) )
            {
            // InternalMazeDsl.g:8440:1: ( ( rule__PortalGhostConfig__SpriteAssignment_8 ) )
            // InternalMazeDsl.g:8441:2: ( rule__PortalGhostConfig__SpriteAssignment_8 )
            {
             before(grammarAccess.getPortalGhostConfigAccess().getSpriteAssignment_8()); 
            // InternalMazeDsl.g:8442:2: ( rule__PortalGhostConfig__SpriteAssignment_8 )
            // InternalMazeDsl.g:8442:3: rule__PortalGhostConfig__SpriteAssignment_8
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
    // InternalMazeDsl.g:8450:1: rule__PortalGhostConfig__Group__9 : rule__PortalGhostConfig__Group__9__Impl ;
    public final void rule__PortalGhostConfig__Group__9() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8454:1: ( rule__PortalGhostConfig__Group__9__Impl )
            // InternalMazeDsl.g:8455:2: rule__PortalGhostConfig__Group__9__Impl
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
    // InternalMazeDsl.g:8461:1: rule__PortalGhostConfig__Group__9__Impl : ( '}' ) ;
    public final void rule__PortalGhostConfig__Group__9__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8465:1: ( ( '}' ) )
            // InternalMazeDsl.g:8466:1: ( '}' )
            {
            // InternalMazeDsl.g:8466:1: ( '}' )
            // InternalMazeDsl.g:8467:2: '}'
            {
             before(grammarAccess.getPortalGhostConfigAccess().getRightCurlyBracketKeyword_9()); 
            match(input,69,FOLLOW_2); 
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
    // InternalMazeDsl.g:8477:1: rule__GhostCountConfig__Group__0 : rule__GhostCountConfig__Group__0__Impl rule__GhostCountConfig__Group__1 ;
    public final void rule__GhostCountConfig__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8481:1: ( rule__GhostCountConfig__Group__0__Impl rule__GhostCountConfig__Group__1 )
            // InternalMazeDsl.g:8482:2: rule__GhostCountConfig__Group__0__Impl rule__GhostCountConfig__Group__1
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
    // InternalMazeDsl.g:8489:1: rule__GhostCountConfig__Group__0__Impl : ( 'portalGhosts' ) ;
    public final void rule__GhostCountConfig__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8493:1: ( ( 'portalGhosts' ) )
            // InternalMazeDsl.g:8494:1: ( 'portalGhosts' )
            {
            // InternalMazeDsl.g:8494:1: ( 'portalGhosts' )
            // InternalMazeDsl.g:8495:2: 'portalGhosts'
            {
             before(grammarAccess.getGhostCountConfigAccess().getPortalGhostsKeyword_0()); 
            match(input,43,FOLLOW_2); 
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
    // InternalMazeDsl.g:8504:1: rule__GhostCountConfig__Group__1 : rule__GhostCountConfig__Group__1__Impl rule__GhostCountConfig__Group__2 ;
    public final void rule__GhostCountConfig__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8508:1: ( rule__GhostCountConfig__Group__1__Impl rule__GhostCountConfig__Group__2 )
            // InternalMazeDsl.g:8509:2: rule__GhostCountConfig__Group__1__Impl rule__GhostCountConfig__Group__2
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
    // InternalMazeDsl.g:8516:1: rule__GhostCountConfig__Group__1__Impl : ( ( rule__GhostCountConfig__DifficultyAssignment_1 ) ) ;
    public final void rule__GhostCountConfig__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8520:1: ( ( ( rule__GhostCountConfig__DifficultyAssignment_1 ) ) )
            // InternalMazeDsl.g:8521:1: ( ( rule__GhostCountConfig__DifficultyAssignment_1 ) )
            {
            // InternalMazeDsl.g:8521:1: ( ( rule__GhostCountConfig__DifficultyAssignment_1 ) )
            // InternalMazeDsl.g:8522:2: ( rule__GhostCountConfig__DifficultyAssignment_1 )
            {
             before(grammarAccess.getGhostCountConfigAccess().getDifficultyAssignment_1()); 
            // InternalMazeDsl.g:8523:2: ( rule__GhostCountConfig__DifficultyAssignment_1 )
            // InternalMazeDsl.g:8523:3: rule__GhostCountConfig__DifficultyAssignment_1
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
    // InternalMazeDsl.g:8531:1: rule__GhostCountConfig__Group__2 : rule__GhostCountConfig__Group__2__Impl ;
    public final void rule__GhostCountConfig__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8535:1: ( rule__GhostCountConfig__Group__2__Impl )
            // InternalMazeDsl.g:8536:2: rule__GhostCountConfig__Group__2__Impl
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
    // InternalMazeDsl.g:8542:1: rule__GhostCountConfig__Group__2__Impl : ( ( rule__GhostCountConfig__CountAssignment_2 ) ) ;
    public final void rule__GhostCountConfig__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8546:1: ( ( ( rule__GhostCountConfig__CountAssignment_2 ) ) )
            // InternalMazeDsl.g:8547:1: ( ( rule__GhostCountConfig__CountAssignment_2 ) )
            {
            // InternalMazeDsl.g:8547:1: ( ( rule__GhostCountConfig__CountAssignment_2 ) )
            // InternalMazeDsl.g:8548:2: ( rule__GhostCountConfig__CountAssignment_2 )
            {
             before(grammarAccess.getGhostCountConfigAccess().getCountAssignment_2()); 
            // InternalMazeDsl.g:8549:2: ( rule__GhostCountConfig__CountAssignment_2 )
            // InternalMazeDsl.g:8549:3: rule__GhostCountConfig__CountAssignment_2
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
    // InternalMazeDsl.g:8558:1: rule__TeleportRulesConfig__Group__0 : rule__TeleportRulesConfig__Group__0__Impl rule__TeleportRulesConfig__Group__1 ;
    public final void rule__TeleportRulesConfig__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8562:1: ( rule__TeleportRulesConfig__Group__0__Impl rule__TeleportRulesConfig__Group__1 )
            // InternalMazeDsl.g:8563:2: rule__TeleportRulesConfig__Group__0__Impl rule__TeleportRulesConfig__Group__1
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
    // InternalMazeDsl.g:8570:1: rule__TeleportRulesConfig__Group__0__Impl : ( 'teleport' ) ;
    public final void rule__TeleportRulesConfig__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8574:1: ( ( 'teleport' ) )
            // InternalMazeDsl.g:8575:1: ( 'teleport' )
            {
            // InternalMazeDsl.g:8575:1: ( 'teleport' )
            // InternalMazeDsl.g:8576:2: 'teleport'
            {
             before(grammarAccess.getTeleportRulesConfigAccess().getTeleportKeyword_0()); 
            match(input,44,FOLLOW_2); 
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
    // InternalMazeDsl.g:8585:1: rule__TeleportRulesConfig__Group__1 : rule__TeleportRulesConfig__Group__1__Impl rule__TeleportRulesConfig__Group__2 ;
    public final void rule__TeleportRulesConfig__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8589:1: ( rule__TeleportRulesConfig__Group__1__Impl rule__TeleportRulesConfig__Group__2 )
            // InternalMazeDsl.g:8590:2: rule__TeleportRulesConfig__Group__1__Impl rule__TeleportRulesConfig__Group__2
            {
            pushFollow(FOLLOW_75);
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
    // InternalMazeDsl.g:8597:1: rule__TeleportRulesConfig__Group__1__Impl : ( '{' ) ;
    public final void rule__TeleportRulesConfig__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8601:1: ( ( '{' ) )
            // InternalMazeDsl.g:8602:1: ( '{' )
            {
            // InternalMazeDsl.g:8602:1: ( '{' )
            // InternalMazeDsl.g:8603:2: '{'
            {
             before(grammarAccess.getTeleportRulesConfigAccess().getLeftCurlyBracketKeyword_1()); 
            match(input,68,FOLLOW_2); 
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
    // InternalMazeDsl.g:8612:1: rule__TeleportRulesConfig__Group__2 : rule__TeleportRulesConfig__Group__2__Impl rule__TeleportRulesConfig__Group__3 ;
    public final void rule__TeleportRulesConfig__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8616:1: ( rule__TeleportRulesConfig__Group__2__Impl rule__TeleportRulesConfig__Group__3 )
            // InternalMazeDsl.g:8617:2: rule__TeleportRulesConfig__Group__2__Impl rule__TeleportRulesConfig__Group__3
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
    // InternalMazeDsl.g:8624:1: rule__TeleportRulesConfig__Group__2__Impl : ( 'cooldown' ) ;
    public final void rule__TeleportRulesConfig__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8628:1: ( ( 'cooldown' ) )
            // InternalMazeDsl.g:8629:1: ( 'cooldown' )
            {
            // InternalMazeDsl.g:8629:1: ( 'cooldown' )
            // InternalMazeDsl.g:8630:2: 'cooldown'
            {
             before(grammarAccess.getTeleportRulesConfigAccess().getCooldownKeyword_2()); 
            match(input,45,FOLLOW_2); 
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
    // InternalMazeDsl.g:8639:1: rule__TeleportRulesConfig__Group__3 : rule__TeleportRulesConfig__Group__3__Impl rule__TeleportRulesConfig__Group__4 ;
    public final void rule__TeleportRulesConfig__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8643:1: ( rule__TeleportRulesConfig__Group__3__Impl rule__TeleportRulesConfig__Group__4 )
            // InternalMazeDsl.g:8644:2: rule__TeleportRulesConfig__Group__3__Impl rule__TeleportRulesConfig__Group__4
            {
            pushFollow(FOLLOW_76);
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
    // InternalMazeDsl.g:8651:1: rule__TeleportRulesConfig__Group__3__Impl : ( ( rule__TeleportRulesConfig__CooldownSecondsAssignment_3 ) ) ;
    public final void rule__TeleportRulesConfig__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8655:1: ( ( ( rule__TeleportRulesConfig__CooldownSecondsAssignment_3 ) ) )
            // InternalMazeDsl.g:8656:1: ( ( rule__TeleportRulesConfig__CooldownSecondsAssignment_3 ) )
            {
            // InternalMazeDsl.g:8656:1: ( ( rule__TeleportRulesConfig__CooldownSecondsAssignment_3 ) )
            // InternalMazeDsl.g:8657:2: ( rule__TeleportRulesConfig__CooldownSecondsAssignment_3 )
            {
             before(grammarAccess.getTeleportRulesConfigAccess().getCooldownSecondsAssignment_3()); 
            // InternalMazeDsl.g:8658:2: ( rule__TeleportRulesConfig__CooldownSecondsAssignment_3 )
            // InternalMazeDsl.g:8658:3: rule__TeleportRulesConfig__CooldownSecondsAssignment_3
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
    // InternalMazeDsl.g:8666:1: rule__TeleportRulesConfig__Group__4 : rule__TeleportRulesConfig__Group__4__Impl rule__TeleportRulesConfig__Group__5 ;
    public final void rule__TeleportRulesConfig__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8670:1: ( rule__TeleportRulesConfig__Group__4__Impl rule__TeleportRulesConfig__Group__5 )
            // InternalMazeDsl.g:8671:2: rule__TeleportRulesConfig__Group__4__Impl rule__TeleportRulesConfig__Group__5
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
    // InternalMazeDsl.g:8678:1: rule__TeleportRulesConfig__Group__4__Impl : ( 'banner' ) ;
    public final void rule__TeleportRulesConfig__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8682:1: ( ( 'banner' ) )
            // InternalMazeDsl.g:8683:1: ( 'banner' )
            {
            // InternalMazeDsl.g:8683:1: ( 'banner' )
            // InternalMazeDsl.g:8684:2: 'banner'
            {
             before(grammarAccess.getTeleportRulesConfigAccess().getBannerKeyword_4()); 
            match(input,46,FOLLOW_2); 
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
    // InternalMazeDsl.g:8693:1: rule__TeleportRulesConfig__Group__5 : rule__TeleportRulesConfig__Group__5__Impl rule__TeleportRulesConfig__Group__6 ;
    public final void rule__TeleportRulesConfig__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8697:1: ( rule__TeleportRulesConfig__Group__5__Impl rule__TeleportRulesConfig__Group__6 )
            // InternalMazeDsl.g:8698:2: rule__TeleportRulesConfig__Group__5__Impl rule__TeleportRulesConfig__Group__6
            {
            pushFollow(FOLLOW_77);
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
    // InternalMazeDsl.g:8705:1: rule__TeleportRulesConfig__Group__5__Impl : ( ( rule__TeleportRulesConfig__BannerTextAssignment_5 ) ) ;
    public final void rule__TeleportRulesConfig__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8709:1: ( ( ( rule__TeleportRulesConfig__BannerTextAssignment_5 ) ) )
            // InternalMazeDsl.g:8710:1: ( ( rule__TeleportRulesConfig__BannerTextAssignment_5 ) )
            {
            // InternalMazeDsl.g:8710:1: ( ( rule__TeleportRulesConfig__BannerTextAssignment_5 ) )
            // InternalMazeDsl.g:8711:2: ( rule__TeleportRulesConfig__BannerTextAssignment_5 )
            {
             before(grammarAccess.getTeleportRulesConfigAccess().getBannerTextAssignment_5()); 
            // InternalMazeDsl.g:8712:2: ( rule__TeleportRulesConfig__BannerTextAssignment_5 )
            // InternalMazeDsl.g:8712:3: rule__TeleportRulesConfig__BannerTextAssignment_5
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
    // InternalMazeDsl.g:8720:1: rule__TeleportRulesConfig__Group__6 : rule__TeleportRulesConfig__Group__6__Impl rule__TeleportRulesConfig__Group__7 ;
    public final void rule__TeleportRulesConfig__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8724:1: ( rule__TeleportRulesConfig__Group__6__Impl rule__TeleportRulesConfig__Group__7 )
            // InternalMazeDsl.g:8725:2: rule__TeleportRulesConfig__Group__6__Impl rule__TeleportRulesConfig__Group__7
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
    // InternalMazeDsl.g:8732:1: rule__TeleportRulesConfig__Group__6__Impl : ( 'bannerSeconds' ) ;
    public final void rule__TeleportRulesConfig__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8736:1: ( ( 'bannerSeconds' ) )
            // InternalMazeDsl.g:8737:1: ( 'bannerSeconds' )
            {
            // InternalMazeDsl.g:8737:1: ( 'bannerSeconds' )
            // InternalMazeDsl.g:8738:2: 'bannerSeconds'
            {
             before(grammarAccess.getTeleportRulesConfigAccess().getBannerSecondsKeyword_6()); 
            match(input,47,FOLLOW_2); 
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
    // InternalMazeDsl.g:8747:1: rule__TeleportRulesConfig__Group__7 : rule__TeleportRulesConfig__Group__7__Impl rule__TeleportRulesConfig__Group__8 ;
    public final void rule__TeleportRulesConfig__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8751:1: ( rule__TeleportRulesConfig__Group__7__Impl rule__TeleportRulesConfig__Group__8 )
            // InternalMazeDsl.g:8752:2: rule__TeleportRulesConfig__Group__7__Impl rule__TeleportRulesConfig__Group__8
            {
            pushFollow(FOLLOW_78);
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
    // InternalMazeDsl.g:8759:1: rule__TeleportRulesConfig__Group__7__Impl : ( ( rule__TeleportRulesConfig__BannerSecondsAssignment_7 ) ) ;
    public final void rule__TeleportRulesConfig__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8763:1: ( ( ( rule__TeleportRulesConfig__BannerSecondsAssignment_7 ) ) )
            // InternalMazeDsl.g:8764:1: ( ( rule__TeleportRulesConfig__BannerSecondsAssignment_7 ) )
            {
            // InternalMazeDsl.g:8764:1: ( ( rule__TeleportRulesConfig__BannerSecondsAssignment_7 ) )
            // InternalMazeDsl.g:8765:2: ( rule__TeleportRulesConfig__BannerSecondsAssignment_7 )
            {
             before(grammarAccess.getTeleportRulesConfigAccess().getBannerSecondsAssignment_7()); 
            // InternalMazeDsl.g:8766:2: ( rule__TeleportRulesConfig__BannerSecondsAssignment_7 )
            // InternalMazeDsl.g:8766:3: rule__TeleportRulesConfig__BannerSecondsAssignment_7
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
    // InternalMazeDsl.g:8774:1: rule__TeleportRulesConfig__Group__8 : rule__TeleportRulesConfig__Group__8__Impl rule__TeleportRulesConfig__Group__9 ;
    public final void rule__TeleportRulesConfig__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8778:1: ( rule__TeleportRulesConfig__Group__8__Impl rule__TeleportRulesConfig__Group__9 )
            // InternalMazeDsl.g:8779:2: rule__TeleportRulesConfig__Group__8__Impl rule__TeleportRulesConfig__Group__9
            {
            pushFollow(FOLLOW_78);
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
    // InternalMazeDsl.g:8786:1: rule__TeleportRulesConfig__Group__8__Impl : ( ( rule__TeleportRulesConfig__Group_8__0 )? ) ;
    public final void rule__TeleportRulesConfig__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8790:1: ( ( ( rule__TeleportRulesConfig__Group_8__0 )? ) )
            // InternalMazeDsl.g:8791:1: ( ( rule__TeleportRulesConfig__Group_8__0 )? )
            {
            // InternalMazeDsl.g:8791:1: ( ( rule__TeleportRulesConfig__Group_8__0 )? )
            // InternalMazeDsl.g:8792:2: ( rule__TeleportRulesConfig__Group_8__0 )?
            {
             before(grammarAccess.getTeleportRulesConfigAccess().getGroup_8()); 
            // InternalMazeDsl.g:8793:2: ( rule__TeleportRulesConfig__Group_8__0 )?
            int alt65=2;
            int LA65_0 = input.LA(1);

            if ( (LA65_0==48) ) {
                alt65=1;
            }
            switch (alt65) {
                case 1 :
                    // InternalMazeDsl.g:8793:3: rule__TeleportRulesConfig__Group_8__0
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
    // InternalMazeDsl.g:8801:1: rule__TeleportRulesConfig__Group__9 : rule__TeleportRulesConfig__Group__9__Impl rule__TeleportRulesConfig__Group__10 ;
    public final void rule__TeleportRulesConfig__Group__9() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8805:1: ( rule__TeleportRulesConfig__Group__9__Impl rule__TeleportRulesConfig__Group__10 )
            // InternalMazeDsl.g:8806:2: rule__TeleportRulesConfig__Group__9__Impl rule__TeleportRulesConfig__Group__10
            {
            pushFollow(FOLLOW_78);
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
    // InternalMazeDsl.g:8813:1: rule__TeleportRulesConfig__Group__9__Impl : ( ( rule__TeleportRulesConfig__Group_9__0 )? ) ;
    public final void rule__TeleportRulesConfig__Group__9__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8817:1: ( ( ( rule__TeleportRulesConfig__Group_9__0 )? ) )
            // InternalMazeDsl.g:8818:1: ( ( rule__TeleportRulesConfig__Group_9__0 )? )
            {
            // InternalMazeDsl.g:8818:1: ( ( rule__TeleportRulesConfig__Group_9__0 )? )
            // InternalMazeDsl.g:8819:2: ( rule__TeleportRulesConfig__Group_9__0 )?
            {
             before(grammarAccess.getTeleportRulesConfigAccess().getGroup_9()); 
            // InternalMazeDsl.g:8820:2: ( rule__TeleportRulesConfig__Group_9__0 )?
            int alt66=2;
            int LA66_0 = input.LA(1);

            if ( (LA66_0==49) ) {
                alt66=1;
            }
            switch (alt66) {
                case 1 :
                    // InternalMazeDsl.g:8820:3: rule__TeleportRulesConfig__Group_9__0
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
    // InternalMazeDsl.g:8828:1: rule__TeleportRulesConfig__Group__10 : rule__TeleportRulesConfig__Group__10__Impl ;
    public final void rule__TeleportRulesConfig__Group__10() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8832:1: ( rule__TeleportRulesConfig__Group__10__Impl )
            // InternalMazeDsl.g:8833:2: rule__TeleportRulesConfig__Group__10__Impl
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
    // InternalMazeDsl.g:8839:1: rule__TeleportRulesConfig__Group__10__Impl : ( '}' ) ;
    public final void rule__TeleportRulesConfig__Group__10__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8843:1: ( ( '}' ) )
            // InternalMazeDsl.g:8844:1: ( '}' )
            {
            // InternalMazeDsl.g:8844:1: ( '}' )
            // InternalMazeDsl.g:8845:2: '}'
            {
             before(grammarAccess.getTeleportRulesConfigAccess().getRightCurlyBracketKeyword_10()); 
            match(input,69,FOLLOW_2); 
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
    // InternalMazeDsl.g:8855:1: rule__TeleportRulesConfig__Group_8__0 : rule__TeleportRulesConfig__Group_8__0__Impl rule__TeleportRulesConfig__Group_8__1 ;
    public final void rule__TeleportRulesConfig__Group_8__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8859:1: ( rule__TeleportRulesConfig__Group_8__0__Impl rule__TeleportRulesConfig__Group_8__1 )
            // InternalMazeDsl.g:8860:2: rule__TeleportRulesConfig__Group_8__0__Impl rule__TeleportRulesConfig__Group_8__1
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
    // InternalMazeDsl.g:8867:1: rule__TeleportRulesConfig__Group_8__0__Impl : ( 'zapSound' ) ;
    public final void rule__TeleportRulesConfig__Group_8__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8871:1: ( ( 'zapSound' ) )
            // InternalMazeDsl.g:8872:1: ( 'zapSound' )
            {
            // InternalMazeDsl.g:8872:1: ( 'zapSound' )
            // InternalMazeDsl.g:8873:2: 'zapSound'
            {
             before(grammarAccess.getTeleportRulesConfigAccess().getZapSoundKeyword_8_0()); 
            match(input,48,FOLLOW_2); 
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
    // InternalMazeDsl.g:8882:1: rule__TeleportRulesConfig__Group_8__1 : rule__TeleportRulesConfig__Group_8__1__Impl ;
    public final void rule__TeleportRulesConfig__Group_8__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8886:1: ( rule__TeleportRulesConfig__Group_8__1__Impl )
            // InternalMazeDsl.g:8887:2: rule__TeleportRulesConfig__Group_8__1__Impl
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
    // InternalMazeDsl.g:8893:1: rule__TeleportRulesConfig__Group_8__1__Impl : ( ( rule__TeleportRulesConfig__ZapSoundAssignment_8_1 ) ) ;
    public final void rule__TeleportRulesConfig__Group_8__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8897:1: ( ( ( rule__TeleportRulesConfig__ZapSoundAssignment_8_1 ) ) )
            // InternalMazeDsl.g:8898:1: ( ( rule__TeleportRulesConfig__ZapSoundAssignment_8_1 ) )
            {
            // InternalMazeDsl.g:8898:1: ( ( rule__TeleportRulesConfig__ZapSoundAssignment_8_1 ) )
            // InternalMazeDsl.g:8899:2: ( rule__TeleportRulesConfig__ZapSoundAssignment_8_1 )
            {
             before(grammarAccess.getTeleportRulesConfigAccess().getZapSoundAssignment_8_1()); 
            // InternalMazeDsl.g:8900:2: ( rule__TeleportRulesConfig__ZapSoundAssignment_8_1 )
            // InternalMazeDsl.g:8900:3: rule__TeleportRulesConfig__ZapSoundAssignment_8_1
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
    // InternalMazeDsl.g:8909:1: rule__TeleportRulesConfig__Group_9__0 : rule__TeleportRulesConfig__Group_9__0__Impl rule__TeleportRulesConfig__Group_9__1 ;
    public final void rule__TeleportRulesConfig__Group_9__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8913:1: ( rule__TeleportRulesConfig__Group_9__0__Impl rule__TeleportRulesConfig__Group_9__1 )
            // InternalMazeDsl.g:8914:2: rule__TeleportRulesConfig__Group_9__0__Impl rule__TeleportRulesConfig__Group_9__1
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
    // InternalMazeDsl.g:8921:1: rule__TeleportRulesConfig__Group_9__0__Impl : ( 'voiceSound' ) ;
    public final void rule__TeleportRulesConfig__Group_9__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8925:1: ( ( 'voiceSound' ) )
            // InternalMazeDsl.g:8926:1: ( 'voiceSound' )
            {
            // InternalMazeDsl.g:8926:1: ( 'voiceSound' )
            // InternalMazeDsl.g:8927:2: 'voiceSound'
            {
             before(grammarAccess.getTeleportRulesConfigAccess().getVoiceSoundKeyword_9_0()); 
            match(input,49,FOLLOW_2); 
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
    // InternalMazeDsl.g:8936:1: rule__TeleportRulesConfig__Group_9__1 : rule__TeleportRulesConfig__Group_9__1__Impl ;
    public final void rule__TeleportRulesConfig__Group_9__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8940:1: ( rule__TeleportRulesConfig__Group_9__1__Impl )
            // InternalMazeDsl.g:8941:2: rule__TeleportRulesConfig__Group_9__1__Impl
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
    // InternalMazeDsl.g:8947:1: rule__TeleportRulesConfig__Group_9__1__Impl : ( ( rule__TeleportRulesConfig__VoiceSoundAssignment_9_1 ) ) ;
    public final void rule__TeleportRulesConfig__Group_9__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8951:1: ( ( ( rule__TeleportRulesConfig__VoiceSoundAssignment_9_1 ) ) )
            // InternalMazeDsl.g:8952:1: ( ( rule__TeleportRulesConfig__VoiceSoundAssignment_9_1 ) )
            {
            // InternalMazeDsl.g:8952:1: ( ( rule__TeleportRulesConfig__VoiceSoundAssignment_9_1 ) )
            // InternalMazeDsl.g:8953:2: ( rule__TeleportRulesConfig__VoiceSoundAssignment_9_1 )
            {
             before(grammarAccess.getTeleportRulesConfigAccess().getVoiceSoundAssignment_9_1()); 
            // InternalMazeDsl.g:8954:2: ( rule__TeleportRulesConfig__VoiceSoundAssignment_9_1 )
            // InternalMazeDsl.g:8954:3: rule__TeleportRulesConfig__VoiceSoundAssignment_9_1
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
    // InternalMazeDsl.g:8963:1: rule__DOUBLE__Group__0 : rule__DOUBLE__Group__0__Impl rule__DOUBLE__Group__1 ;
    public final void rule__DOUBLE__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8967:1: ( rule__DOUBLE__Group__0__Impl rule__DOUBLE__Group__1 )
            // InternalMazeDsl.g:8968:2: rule__DOUBLE__Group__0__Impl rule__DOUBLE__Group__1
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
    // InternalMazeDsl.g:8975:1: rule__DOUBLE__Group__0__Impl : ( ( '-' )? ) ;
    public final void rule__DOUBLE__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8979:1: ( ( ( '-' )? ) )
            // InternalMazeDsl.g:8980:1: ( ( '-' )? )
            {
            // InternalMazeDsl.g:8980:1: ( ( '-' )? )
            // InternalMazeDsl.g:8981:2: ( '-' )?
            {
             before(grammarAccess.getDOUBLEAccess().getHyphenMinusKeyword_0()); 
            // InternalMazeDsl.g:8982:2: ( '-' )?
            int alt67=2;
            int LA67_0 = input.LA(1);

            if ( (LA67_0==119) ) {
                alt67=1;
            }
            switch (alt67) {
                case 1 :
                    // InternalMazeDsl.g:8982:3: '-'
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
    // InternalMazeDsl.g:8990:1: rule__DOUBLE__Group__1 : rule__DOUBLE__Group__1__Impl rule__DOUBLE__Group__2 ;
    public final void rule__DOUBLE__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:8994:1: ( rule__DOUBLE__Group__1__Impl rule__DOUBLE__Group__2 )
            // InternalMazeDsl.g:8995:2: rule__DOUBLE__Group__1__Impl rule__DOUBLE__Group__2
            {
            pushFollow(FOLLOW_79);
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
    // InternalMazeDsl.g:9002:1: rule__DOUBLE__Group__1__Impl : ( RULE_INT ) ;
    public final void rule__DOUBLE__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9006:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:9007:1: ( RULE_INT )
            {
            // InternalMazeDsl.g:9007:1: ( RULE_INT )
            // InternalMazeDsl.g:9008:2: RULE_INT
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
    // InternalMazeDsl.g:9017:1: rule__DOUBLE__Group__2 : rule__DOUBLE__Group__2__Impl ;
    public final void rule__DOUBLE__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9021:1: ( rule__DOUBLE__Group__2__Impl )
            // InternalMazeDsl.g:9022:2: rule__DOUBLE__Group__2__Impl
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
    // InternalMazeDsl.g:9028:1: rule__DOUBLE__Group__2__Impl : ( ( rule__DOUBLE__Group_2__0 )? ) ;
    public final void rule__DOUBLE__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9032:1: ( ( ( rule__DOUBLE__Group_2__0 )? ) )
            // InternalMazeDsl.g:9033:1: ( ( rule__DOUBLE__Group_2__0 )? )
            {
            // InternalMazeDsl.g:9033:1: ( ( rule__DOUBLE__Group_2__0 )? )
            // InternalMazeDsl.g:9034:2: ( rule__DOUBLE__Group_2__0 )?
            {
             before(grammarAccess.getDOUBLEAccess().getGroup_2()); 
            // InternalMazeDsl.g:9035:2: ( rule__DOUBLE__Group_2__0 )?
            int alt68=2;
            int LA68_0 = input.LA(1);

            if ( (LA68_0==120) ) {
                alt68=1;
            }
            switch (alt68) {
                case 1 :
                    // InternalMazeDsl.g:9035:3: rule__DOUBLE__Group_2__0
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
    // InternalMazeDsl.g:9044:1: rule__DOUBLE__Group_2__0 : rule__DOUBLE__Group_2__0__Impl rule__DOUBLE__Group_2__1 ;
    public final void rule__DOUBLE__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9048:1: ( rule__DOUBLE__Group_2__0__Impl rule__DOUBLE__Group_2__1 )
            // InternalMazeDsl.g:9049:2: rule__DOUBLE__Group_2__0__Impl rule__DOUBLE__Group_2__1
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
    // InternalMazeDsl.g:9056:1: rule__DOUBLE__Group_2__0__Impl : ( '.' ) ;
    public final void rule__DOUBLE__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9060:1: ( ( '.' ) )
            // InternalMazeDsl.g:9061:1: ( '.' )
            {
            // InternalMazeDsl.g:9061:1: ( '.' )
            // InternalMazeDsl.g:9062:2: '.'
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
    // InternalMazeDsl.g:9071:1: rule__DOUBLE__Group_2__1 : rule__DOUBLE__Group_2__1__Impl ;
    public final void rule__DOUBLE__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9075:1: ( rule__DOUBLE__Group_2__1__Impl )
            // InternalMazeDsl.g:9076:2: rule__DOUBLE__Group_2__1__Impl
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
    // InternalMazeDsl.g:9082:1: rule__DOUBLE__Group_2__1__Impl : ( RULE_INT ) ;
    public final void rule__DOUBLE__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9086:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:9087:1: ( RULE_INT )
            {
            // InternalMazeDsl.g:9087:1: ( RULE_INT )
            // InternalMazeDsl.g:9088:2: RULE_INT
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
    // InternalMazeDsl.g:9098:1: rule__SIGNED_INT__Group__0 : rule__SIGNED_INT__Group__0__Impl rule__SIGNED_INT__Group__1 ;
    public final void rule__SIGNED_INT__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9102:1: ( rule__SIGNED_INT__Group__0__Impl rule__SIGNED_INT__Group__1 )
            // InternalMazeDsl.g:9103:2: rule__SIGNED_INT__Group__0__Impl rule__SIGNED_INT__Group__1
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
    // InternalMazeDsl.g:9110:1: rule__SIGNED_INT__Group__0__Impl : ( ( '-' )? ) ;
    public final void rule__SIGNED_INT__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9114:1: ( ( ( '-' )? ) )
            // InternalMazeDsl.g:9115:1: ( ( '-' )? )
            {
            // InternalMazeDsl.g:9115:1: ( ( '-' )? )
            // InternalMazeDsl.g:9116:2: ( '-' )?
            {
             before(grammarAccess.getSIGNED_INTAccess().getHyphenMinusKeyword_0()); 
            // InternalMazeDsl.g:9117:2: ( '-' )?
            int alt69=2;
            int LA69_0 = input.LA(1);

            if ( (LA69_0==119) ) {
                alt69=1;
            }
            switch (alt69) {
                case 1 :
                    // InternalMazeDsl.g:9117:3: '-'
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
    // InternalMazeDsl.g:9125:1: rule__SIGNED_INT__Group__1 : rule__SIGNED_INT__Group__1__Impl ;
    public final void rule__SIGNED_INT__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9129:1: ( rule__SIGNED_INT__Group__1__Impl )
            // InternalMazeDsl.g:9130:2: rule__SIGNED_INT__Group__1__Impl
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
    // InternalMazeDsl.g:9136:1: rule__SIGNED_INT__Group__1__Impl : ( RULE_INT ) ;
    public final void rule__SIGNED_INT__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9140:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:9141:1: ( RULE_INT )
            {
            // InternalMazeDsl.g:9141:1: ( RULE_INT )
            // InternalMazeDsl.g:9142:2: RULE_INT
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
    // InternalMazeDsl.g:9152:1: rule__DOUBLE_OBJECT__Group__0 : rule__DOUBLE_OBJECT__Group__0__Impl rule__DOUBLE_OBJECT__Group__1 ;
    public final void rule__DOUBLE_OBJECT__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9156:1: ( rule__DOUBLE_OBJECT__Group__0__Impl rule__DOUBLE_OBJECT__Group__1 )
            // InternalMazeDsl.g:9157:2: rule__DOUBLE_OBJECT__Group__0__Impl rule__DOUBLE_OBJECT__Group__1
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
    // InternalMazeDsl.g:9164:1: rule__DOUBLE_OBJECT__Group__0__Impl : ( ( '-' )? ) ;
    public final void rule__DOUBLE_OBJECT__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9168:1: ( ( ( '-' )? ) )
            // InternalMazeDsl.g:9169:1: ( ( '-' )? )
            {
            // InternalMazeDsl.g:9169:1: ( ( '-' )? )
            // InternalMazeDsl.g:9170:2: ( '-' )?
            {
             before(grammarAccess.getDOUBLE_OBJECTAccess().getHyphenMinusKeyword_0()); 
            // InternalMazeDsl.g:9171:2: ( '-' )?
            int alt70=2;
            int LA70_0 = input.LA(1);

            if ( (LA70_0==119) ) {
                alt70=1;
            }
            switch (alt70) {
                case 1 :
                    // InternalMazeDsl.g:9171:3: '-'
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
    // InternalMazeDsl.g:9179:1: rule__DOUBLE_OBJECT__Group__1 : rule__DOUBLE_OBJECT__Group__1__Impl rule__DOUBLE_OBJECT__Group__2 ;
    public final void rule__DOUBLE_OBJECT__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9183:1: ( rule__DOUBLE_OBJECT__Group__1__Impl rule__DOUBLE_OBJECT__Group__2 )
            // InternalMazeDsl.g:9184:2: rule__DOUBLE_OBJECT__Group__1__Impl rule__DOUBLE_OBJECT__Group__2
            {
            pushFollow(FOLLOW_79);
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
    // InternalMazeDsl.g:9191:1: rule__DOUBLE_OBJECT__Group__1__Impl : ( RULE_INT ) ;
    public final void rule__DOUBLE_OBJECT__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9195:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:9196:1: ( RULE_INT )
            {
            // InternalMazeDsl.g:9196:1: ( RULE_INT )
            // InternalMazeDsl.g:9197:2: RULE_INT
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
    // InternalMazeDsl.g:9206:1: rule__DOUBLE_OBJECT__Group__2 : rule__DOUBLE_OBJECT__Group__2__Impl ;
    public final void rule__DOUBLE_OBJECT__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9210:1: ( rule__DOUBLE_OBJECT__Group__2__Impl )
            // InternalMazeDsl.g:9211:2: rule__DOUBLE_OBJECT__Group__2__Impl
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
    // InternalMazeDsl.g:9217:1: rule__DOUBLE_OBJECT__Group__2__Impl : ( ( rule__DOUBLE_OBJECT__Group_2__0 )? ) ;
    public final void rule__DOUBLE_OBJECT__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9221:1: ( ( ( rule__DOUBLE_OBJECT__Group_2__0 )? ) )
            // InternalMazeDsl.g:9222:1: ( ( rule__DOUBLE_OBJECT__Group_2__0 )? )
            {
            // InternalMazeDsl.g:9222:1: ( ( rule__DOUBLE_OBJECT__Group_2__0 )? )
            // InternalMazeDsl.g:9223:2: ( rule__DOUBLE_OBJECT__Group_2__0 )?
            {
             before(grammarAccess.getDOUBLE_OBJECTAccess().getGroup_2()); 
            // InternalMazeDsl.g:9224:2: ( rule__DOUBLE_OBJECT__Group_2__0 )?
            int alt71=2;
            int LA71_0 = input.LA(1);

            if ( (LA71_0==120) ) {
                alt71=1;
            }
            switch (alt71) {
                case 1 :
                    // InternalMazeDsl.g:9224:3: rule__DOUBLE_OBJECT__Group_2__0
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
    // InternalMazeDsl.g:9233:1: rule__DOUBLE_OBJECT__Group_2__0 : rule__DOUBLE_OBJECT__Group_2__0__Impl rule__DOUBLE_OBJECT__Group_2__1 ;
    public final void rule__DOUBLE_OBJECT__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9237:1: ( rule__DOUBLE_OBJECT__Group_2__0__Impl rule__DOUBLE_OBJECT__Group_2__1 )
            // InternalMazeDsl.g:9238:2: rule__DOUBLE_OBJECT__Group_2__0__Impl rule__DOUBLE_OBJECT__Group_2__1
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
    // InternalMazeDsl.g:9245:1: rule__DOUBLE_OBJECT__Group_2__0__Impl : ( '.' ) ;
    public final void rule__DOUBLE_OBJECT__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9249:1: ( ( '.' ) )
            // InternalMazeDsl.g:9250:1: ( '.' )
            {
            // InternalMazeDsl.g:9250:1: ( '.' )
            // InternalMazeDsl.g:9251:2: '.'
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
    // InternalMazeDsl.g:9260:1: rule__DOUBLE_OBJECT__Group_2__1 : rule__DOUBLE_OBJECT__Group_2__1__Impl ;
    public final void rule__DOUBLE_OBJECT__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9264:1: ( rule__DOUBLE_OBJECT__Group_2__1__Impl )
            // InternalMazeDsl.g:9265:2: rule__DOUBLE_OBJECT__Group_2__1__Impl
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
    // InternalMazeDsl.g:9271:1: rule__DOUBLE_OBJECT__Group_2__1__Impl : ( RULE_INT ) ;
    public final void rule__DOUBLE_OBJECT__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9275:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:9276:1: ( RULE_INT )
            {
            // InternalMazeDsl.g:9276:1: ( RULE_INT )
            // InternalMazeDsl.g:9277:2: RULE_INT
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
    // InternalMazeDsl.g:9287:1: rule__INT_OBJECT__Group__0 : rule__INT_OBJECT__Group__0__Impl rule__INT_OBJECT__Group__1 ;
    public final void rule__INT_OBJECT__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9291:1: ( rule__INT_OBJECT__Group__0__Impl rule__INT_OBJECT__Group__1 )
            // InternalMazeDsl.g:9292:2: rule__INT_OBJECT__Group__0__Impl rule__INT_OBJECT__Group__1
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
    // InternalMazeDsl.g:9299:1: rule__INT_OBJECT__Group__0__Impl : ( ( '-' )? ) ;
    public final void rule__INT_OBJECT__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9303:1: ( ( ( '-' )? ) )
            // InternalMazeDsl.g:9304:1: ( ( '-' )? )
            {
            // InternalMazeDsl.g:9304:1: ( ( '-' )? )
            // InternalMazeDsl.g:9305:2: ( '-' )?
            {
             before(grammarAccess.getINT_OBJECTAccess().getHyphenMinusKeyword_0()); 
            // InternalMazeDsl.g:9306:2: ( '-' )?
            int alt72=2;
            int LA72_0 = input.LA(1);

            if ( (LA72_0==119) ) {
                alt72=1;
            }
            switch (alt72) {
                case 1 :
                    // InternalMazeDsl.g:9306:3: '-'
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
    // InternalMazeDsl.g:9314:1: rule__INT_OBJECT__Group__1 : rule__INT_OBJECT__Group__1__Impl ;
    public final void rule__INT_OBJECT__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9318:1: ( rule__INT_OBJECT__Group__1__Impl )
            // InternalMazeDsl.g:9319:2: rule__INT_OBJECT__Group__1__Impl
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
    // InternalMazeDsl.g:9325:1: rule__INT_OBJECT__Group__1__Impl : ( RULE_INT ) ;
    public final void rule__INT_OBJECT__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9329:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:9330:1: ( RULE_INT )
            {
            // InternalMazeDsl.g:9330:1: ( RULE_INT )
            // InternalMazeDsl.g:9331:2: RULE_INT
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
    // InternalMazeDsl.g:9341:1: rule__GameConfiguration__NameAssignment_1 : ( ruleValidID ) ;
    public final void rule__GameConfiguration__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9345:1: ( ( ruleValidID ) )
            // InternalMazeDsl.g:9346:2: ( ruleValidID )
            {
            // InternalMazeDsl.g:9346:2: ( ruleValidID )
            // InternalMazeDsl.g:9347:3: ruleValidID
            {
             before(grammarAccess.getGameConfigurationAccess().getNameValidIDParserRuleCall_1_0()); 
            pushFollow(FOLLOW_2);
            ruleValidID();

            state._fsp--;

             after(grammarAccess.getGameConfigurationAccess().getNameValidIDParserRuleCall_1_0()); 

            }


            }

        }
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
    // InternalMazeDsl.g:9356:1: rule__GameConfiguration__ImportsAssignment_3 : ( ruleImport ) ;
    public final void rule__GameConfiguration__ImportsAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9360:1: ( ( ruleImport ) )
            // InternalMazeDsl.g:9361:2: ( ruleImport )
            {
            // InternalMazeDsl.g:9361:2: ( ruleImport )
            // InternalMazeDsl.g:9362:3: ruleImport
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
    // InternalMazeDsl.g:9371:1: rule__GameConfiguration__DifficultyAssignment_4 : ( ruleDifficultyConfig ) ;
    public final void rule__GameConfiguration__DifficultyAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9375:1: ( ( ruleDifficultyConfig ) )
            // InternalMazeDsl.g:9376:2: ( ruleDifficultyConfig )
            {
            // InternalMazeDsl.g:9376:2: ( ruleDifficultyConfig )
            // InternalMazeDsl.g:9377:3: ruleDifficultyConfig
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
    // InternalMazeDsl.g:9386:1: rule__GameConfiguration__OpponentsAssignment_5 : ( ruleOpponentConfig ) ;
    public final void rule__GameConfiguration__OpponentsAssignment_5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9390:1: ( ( ruleOpponentConfig ) )
            // InternalMazeDsl.g:9391:2: ( ruleOpponentConfig )
            {
            // InternalMazeDsl.g:9391:2: ( ruleOpponentConfig )
            // InternalMazeDsl.g:9392:3: ruleOpponentConfig
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
    // InternalMazeDsl.g:9401:1: rule__GameConfiguration__PatrolsAssignment_6 : ( rulePatrolConfig ) ;
    public final void rule__GameConfiguration__PatrolsAssignment_6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9405:1: ( ( rulePatrolConfig ) )
            // InternalMazeDsl.g:9406:2: ( rulePatrolConfig )
            {
            // InternalMazeDsl.g:9406:2: ( rulePatrolConfig )
            // InternalMazeDsl.g:9407:3: rulePatrolConfig
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
    // InternalMazeDsl.g:9416:1: rule__GameConfiguration__LootTablesAssignment_7 : ( ruleLootTableConfig ) ;
    public final void rule__GameConfiguration__LootTablesAssignment_7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9420:1: ( ( ruleLootTableConfig ) )
            // InternalMazeDsl.g:9421:2: ( ruleLootTableConfig )
            {
            // InternalMazeDsl.g:9421:2: ( ruleLootTableConfig )
            // InternalMazeDsl.g:9422:3: ruleLootTableConfig
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
    // InternalMazeDsl.g:9431:1: rule__GameConfiguration__LevelsAssignment_8 : ( ruleLevelConfig ) ;
    public final void rule__GameConfiguration__LevelsAssignment_8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9435:1: ( ( ruleLevelConfig ) )
            // InternalMazeDsl.g:9436:2: ( ruleLevelConfig )
            {
            // InternalMazeDsl.g:9436:2: ( ruleLevelConfig )
            // InternalMazeDsl.g:9437:3: ruleLevelConfig
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
    // InternalMazeDsl.g:9446:1: rule__Import__ImportURIAssignment_1 : ( RULE_STRING ) ;
    public final void rule__Import__ImportURIAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9450:1: ( ( RULE_STRING ) )
            // InternalMazeDsl.g:9451:2: ( RULE_STRING )
            {
            // InternalMazeDsl.g:9451:2: ( RULE_STRING )
            // InternalMazeDsl.g:9452:3: RULE_STRING
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
    // InternalMazeDsl.g:9461:1: rule__DifficultyConfig__LevelAssignment_3 : ( ruleDifficultyLevel ) ;
    public final void rule__DifficultyConfig__LevelAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9465:1: ( ( ruleDifficultyLevel ) )
            // InternalMazeDsl.g:9466:2: ( ruleDifficultyLevel )
            {
            // InternalMazeDsl.g:9466:2: ( ruleDifficultyLevel )
            // InternalMazeDsl.g:9467:3: ruleDifficultyLevel
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
    // InternalMazeDsl.g:9476:1: rule__DifficultyConfig__InstantDeathAssignment_4_1 : ( ( 'true' ) ) ;
    public final void rule__DifficultyConfig__InstantDeathAssignment_4_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9480:1: ( ( ( 'true' ) ) )
            // InternalMazeDsl.g:9481:2: ( ( 'true' ) )
            {
            // InternalMazeDsl.g:9481:2: ( ( 'true' ) )
            // InternalMazeDsl.g:9482:3: ( 'true' )
            {
             before(grammarAccess.getDifficultyConfigAccess().getInstantDeathTrueKeyword_4_1_0()); 
            // InternalMazeDsl.g:9483:3: ( 'true' )
            // InternalMazeDsl.g:9484:4: 'true'
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
    // InternalMazeDsl.g:9495:1: rule__DifficultyConfig__SpeedMultiplierAssignment_5_1 : ( ruleDOUBLE ) ;
    public final void rule__DifficultyConfig__SpeedMultiplierAssignment_5_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9499:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:9500:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:9500:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:9501:3: ruleDOUBLE
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
    // InternalMazeDsl.g:9510:1: rule__DifficultyConfig__DamageMultiplierAssignment_6_1 : ( ruleDOUBLE ) ;
    public final void rule__DifficultyConfig__DamageMultiplierAssignment_6_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9514:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:9515:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:9515:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:9516:3: ruleDOUBLE
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
    // InternalMazeDsl.g:9525:1: rule__DifficultyConfig__MaxThreatAssignment_7_1 : ( ruleSIGNED_INT ) ;
    public final void rule__DifficultyConfig__MaxThreatAssignment_7_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9529:1: ( ( ruleSIGNED_INT ) )
            // InternalMazeDsl.g:9530:2: ( ruleSIGNED_INT )
            {
            // InternalMazeDsl.g:9530:2: ( ruleSIGNED_INT )
            // InternalMazeDsl.g:9531:3: ruleSIGNED_INT
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
    // InternalMazeDsl.g:9540:1: rule__DifficultyConfig__EnemyLimitsAssignment_8 : ( ruleEnemyLimit ) ;
    public final void rule__DifficultyConfig__EnemyLimitsAssignment_8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9544:1: ( ( ruleEnemyLimit ) )
            // InternalMazeDsl.g:9545:2: ( ruleEnemyLimit )
            {
            // InternalMazeDsl.g:9545:2: ( ruleEnemyLimit )
            // InternalMazeDsl.g:9546:3: ruleEnemyLimit
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
    // InternalMazeDsl.g:9555:1: rule__EnemyLimit__TypeAssignment_1 : ( ruleEnemyType ) ;
    public final void rule__EnemyLimit__TypeAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9559:1: ( ( ruleEnemyType ) )
            // InternalMazeDsl.g:9560:2: ( ruleEnemyType )
            {
            // InternalMazeDsl.g:9560:2: ( ruleEnemyType )
            // InternalMazeDsl.g:9561:3: ruleEnemyType
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
    // InternalMazeDsl.g:9570:1: rule__EnemyLimit__MaxCountAssignment_3 : ( ruleSIGNED_INT ) ;
    public final void rule__EnemyLimit__MaxCountAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9574:1: ( ( ruleSIGNED_INT ) )
            // InternalMazeDsl.g:9575:2: ( ruleSIGNED_INT )
            {
            // InternalMazeDsl.g:9575:2: ( ruleSIGNED_INT )
            // InternalMazeDsl.g:9576:3: ruleSIGNED_INT
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
    // InternalMazeDsl.g:9585:1: rule__OpponentConfig__NameAssignment_1 : ( ruleValidID ) ;
    public final void rule__OpponentConfig__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9589:1: ( ( ruleValidID ) )
            // InternalMazeDsl.g:9590:2: ( ruleValidID )
            {
            // InternalMazeDsl.g:9590:2: ( ruleValidID )
            // InternalMazeDsl.g:9591:3: ruleValidID
            {
             before(grammarAccess.getOpponentConfigAccess().getNameValidIDParserRuleCall_1_0()); 
            pushFollow(FOLLOW_2);
            ruleValidID();

            state._fsp--;

             after(grammarAccess.getOpponentConfigAccess().getNameValidIDParserRuleCall_1_0()); 

            }


            }

        }
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
    // InternalMazeDsl.g:9600:1: rule__OpponentConfig__TypeAssignment_4 : ( ruleCharacterTypeEnum ) ;
    public final void rule__OpponentConfig__TypeAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9604:1: ( ( ruleCharacterTypeEnum ) )
            // InternalMazeDsl.g:9605:2: ( ruleCharacterTypeEnum )
            {
            // InternalMazeDsl.g:9605:2: ( ruleCharacterTypeEnum )
            // InternalMazeDsl.g:9606:3: ruleCharacterTypeEnum
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
    // InternalMazeDsl.g:9615:1: rule__OpponentConfig__DisplayNameAssignment_5_1 : ( RULE_STRING ) ;
    public final void rule__OpponentConfig__DisplayNameAssignment_5_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9619:1: ( ( RULE_STRING ) )
            // InternalMazeDsl.g:9620:2: ( RULE_STRING )
            {
            // InternalMazeDsl.g:9620:2: ( RULE_STRING )
            // InternalMazeDsl.g:9621:3: RULE_STRING
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
    // InternalMazeDsl.g:9630:1: rule__OpponentConfig__HealthAssignment_6_1 : ( ruleSIGNED_INT ) ;
    public final void rule__OpponentConfig__HealthAssignment_6_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9634:1: ( ( ruleSIGNED_INT ) )
            // InternalMazeDsl.g:9635:2: ( ruleSIGNED_INT )
            {
            // InternalMazeDsl.g:9635:2: ( ruleSIGNED_INT )
            // InternalMazeDsl.g:9636:3: ruleSIGNED_INT
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
    // InternalMazeDsl.g:9645:1: rule__OpponentConfig__SpeedAssignment_7_1 : ( ruleDOUBLE ) ;
    public final void rule__OpponentConfig__SpeedAssignment_7_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9649:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:9650:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:9650:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:9651:3: ruleDOUBLE
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
    // InternalMazeDsl.g:9660:1: rule__OpponentConfig__ThreatLevelAssignment_8_1 : ( ruleDOUBLE ) ;
    public final void rule__OpponentConfig__ThreatLevelAssignment_8_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9664:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:9665:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:9665:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:9666:3: ruleDOUBLE
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
    // InternalMazeDsl.g:9675:1: rule__OpponentConfig__EnabledAssignment_9_1 : ( ruleBOOLEAN ) ;
    public final void rule__OpponentConfig__EnabledAssignment_9_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9679:1: ( ( ruleBOOLEAN ) )
            // InternalMazeDsl.g:9680:2: ( ruleBOOLEAN )
            {
            // InternalMazeDsl.g:9680:2: ( ruleBOOLEAN )
            // InternalMazeDsl.g:9681:3: ruleBOOLEAN
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
    // InternalMazeDsl.g:9690:1: rule__OpponentConfig__BehaviorAssignment_10_1 : ( ruleBehaviorTypeEnum ) ;
    public final void rule__OpponentConfig__BehaviorAssignment_10_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9694:1: ( ( ruleBehaviorTypeEnum ) )
            // InternalMazeDsl.g:9695:2: ( ruleBehaviorTypeEnum )
            {
            // InternalMazeDsl.g:9695:2: ( ruleBehaviorTypeEnum )
            // InternalMazeDsl.g:9696:3: ruleBehaviorTypeEnum
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
    // InternalMazeDsl.g:9705:1: rule__OpponentConfig__CharacterSpecificsAssignment_11 : ( ruleCharacterSpecifics ) ;
    public final void rule__OpponentConfig__CharacterSpecificsAssignment_11() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9709:1: ( ( ruleCharacterSpecifics ) )
            // InternalMazeDsl.g:9710:2: ( ruleCharacterSpecifics )
            {
            // InternalMazeDsl.g:9710:2: ( ruleCharacterSpecifics )
            // InternalMazeDsl.g:9711:3: ruleCharacterSpecifics
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
    // InternalMazeDsl.g:9720:1: rule__OpponentConfig__PatrolRefAssignment_12_1 : ( ( ruleValidID ) ) ;
    public final void rule__OpponentConfig__PatrolRefAssignment_12_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9724:1: ( ( ( ruleValidID ) ) )
            // InternalMazeDsl.g:9725:2: ( ( ruleValidID ) )
            {
            // InternalMazeDsl.g:9725:2: ( ( ruleValidID ) )
            // InternalMazeDsl.g:9726:3: ( ruleValidID )
            {
             before(grammarAccess.getOpponentConfigAccess().getPatrolRefPatrolConfigCrossReference_12_1_0()); 
            // InternalMazeDsl.g:9727:3: ( ruleValidID )
            // InternalMazeDsl.g:9728:4: ruleValidID
            {
             before(grammarAccess.getOpponentConfigAccess().getPatrolRefPatrolConfigValidIDParserRuleCall_12_1_0_1()); 
            pushFollow(FOLLOW_2);
            ruleValidID();

            state._fsp--;

             after(grammarAccess.getOpponentConfigAccess().getPatrolRefPatrolConfigValidIDParserRuleCall_12_1_0_1()); 

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
    // InternalMazeDsl.g:9739:1: rule__OpponentConfig__LootRefAssignment_13_1 : ( ( ruleValidID ) ) ;
    public final void rule__OpponentConfig__LootRefAssignment_13_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9743:1: ( ( ( ruleValidID ) ) )
            // InternalMazeDsl.g:9744:2: ( ( ruleValidID ) )
            {
            // InternalMazeDsl.g:9744:2: ( ( ruleValidID ) )
            // InternalMazeDsl.g:9745:3: ( ruleValidID )
            {
             before(grammarAccess.getOpponentConfigAccess().getLootRefLootTableConfigCrossReference_13_1_0()); 
            // InternalMazeDsl.g:9746:3: ( ruleValidID )
            // InternalMazeDsl.g:9747:4: ruleValidID
            {
             before(grammarAccess.getOpponentConfigAccess().getLootRefLootTableConfigValidIDParserRuleCall_13_1_0_1()); 
            pushFollow(FOLLOW_2);
            ruleValidID();

            state._fsp--;

             after(grammarAccess.getOpponentConfigAccess().getLootRefLootTableConfigValidIDParserRuleCall_13_1_0_1()); 

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
    // InternalMazeDsl.g:9758:1: rule__ZombieSpecifics__AttackDamageAssignment_3_1 : ( RULE_INT ) ;
    public final void rule__ZombieSpecifics__AttackDamageAssignment_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9762:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:9763:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:9763:2: ( RULE_INT )
            // InternalMazeDsl.g:9764:3: RULE_INT
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
    // InternalMazeDsl.g:9773:1: rule__ZombieSpecifics__InfectionLevelAssignment_4_1 : ( RULE_INT ) ;
    public final void rule__ZombieSpecifics__InfectionLevelAssignment_4_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9777:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:9778:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:9778:2: ( RULE_INT )
            // InternalMazeDsl.g:9779:3: RULE_INT
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
    // InternalMazeDsl.g:9788:1: rule__ZombieSpecifics__ResurrectionTimeAssignment_5_1 : ( RULE_INT ) ;
    public final void rule__ZombieSpecifics__ResurrectionTimeAssignment_5_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9792:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:9793:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:9793:2: ( RULE_INT )
            // InternalMazeDsl.g:9794:3: RULE_INT
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
    // InternalMazeDsl.g:9803:1: rule__GhostSpecifics__AttackDamageAssignment_3_1 : ( RULE_INT ) ;
    public final void rule__GhostSpecifics__AttackDamageAssignment_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9807:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:9808:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:9808:2: ( RULE_INT )
            // InternalMazeDsl.g:9809:3: RULE_INT
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
    // InternalMazeDsl.g:9818:1: rule__GhostSpecifics__VisibilityLevelAssignment_4_1 : ( RULE_INT ) ;
    public final void rule__GhostSpecifics__VisibilityLevelAssignment_4_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9822:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:9823:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:9823:2: ( RULE_INT )
            // InternalMazeDsl.g:9824:3: RULE_INT
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
    // InternalMazeDsl.g:9833:1: rule__GhostSpecifics__NonTangibilityEnergyAssignment_5_1 : ( ruleDOUBLE ) ;
    public final void rule__GhostSpecifics__NonTangibilityEnergyAssignment_5_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9837:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:9838:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:9838:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:9839:3: ruleDOUBLE
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
    // InternalMazeDsl.g:9848:1: rule__RangedSpecifics__AttackRangeAssignment_3_1 : ( ruleDOUBLE ) ;
    public final void rule__RangedSpecifics__AttackRangeAssignment_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9852:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:9853:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:9853:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:9854:3: ruleDOUBLE
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
    // InternalMazeDsl.g:9863:1: rule__RangedSpecifics__AttackCooldownAssignment_4_1 : ( RULE_INT ) ;
    public final void rule__RangedSpecifics__AttackCooldownAssignment_4_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9867:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:9868:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:9868:2: ( RULE_INT )
            // InternalMazeDsl.g:9869:3: RULE_INT
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
    // InternalMazeDsl.g:9878:1: rule__RangedSpecifics__AttackDamageAssignment_5_1 : ( RULE_INT ) ;
    public final void rule__RangedSpecifics__AttackDamageAssignment_5_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9882:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:9883:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:9883:2: ( RULE_INT )
            // InternalMazeDsl.g:9884:3: RULE_INT
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
    // InternalMazeDsl.g:9893:1: rule__RangedSpecifics__ProjectileSpeedAssignment_6_1 : ( ruleDOUBLE ) ;
    public final void rule__RangedSpecifics__ProjectileSpeedAssignment_6_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9897:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:9898:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:9898:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:9899:3: ruleDOUBLE
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
    // InternalMazeDsl.g:9908:1: rule__RangedSpecifics__ProjectileTypeAssignment_7_1 : ( ruleProjectileTypeEnum ) ;
    public final void rule__RangedSpecifics__ProjectileTypeAssignment_7_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9912:1: ( ( ruleProjectileTypeEnum ) )
            // InternalMazeDsl.g:9913:2: ( ruleProjectileTypeEnum )
            {
            // InternalMazeDsl.g:9913:2: ( ruleProjectileTypeEnum )
            // InternalMazeDsl.g:9914:3: ruleProjectileTypeEnum
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
    // InternalMazeDsl.g:9923:1: rule__RangedSpecifics__SplashRadiusAssignment_8_1 : ( ruleDOUBLE ) ;
    public final void rule__RangedSpecifics__SplashRadiusAssignment_8_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9927:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:9928:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:9928:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:9929:3: ruleDOUBLE
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
    // InternalMazeDsl.g:9938:1: rule__PatrolConfig__NameAssignment_1 : ( ruleValidID ) ;
    public final void rule__PatrolConfig__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9942:1: ( ( ruleValidID ) )
            // InternalMazeDsl.g:9943:2: ( ruleValidID )
            {
            // InternalMazeDsl.g:9943:2: ( ruleValidID )
            // InternalMazeDsl.g:9944:3: ruleValidID
            {
             before(grammarAccess.getPatrolConfigAccess().getNameValidIDParserRuleCall_1_0()); 
            pushFollow(FOLLOW_2);
            ruleValidID();

            state._fsp--;

             after(grammarAccess.getPatrolConfigAccess().getNameValidIDParserRuleCall_1_0()); 

            }


            }

        }
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
    // InternalMazeDsl.g:9953:1: rule__PatrolConfig__VisionRangeAssignment_3_1 : ( ruleDOUBLE ) ;
    public final void rule__PatrolConfig__VisionRangeAssignment_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9957:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:9958:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:9958:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:9959:3: ruleDOUBLE
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
    // InternalMazeDsl.g:9968:1: rule__PatrolConfig__ZoneAssignment_4 : ( rulePatrolZoneConfig ) ;
    public final void rule__PatrolConfig__ZoneAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9972:1: ( ( rulePatrolZoneConfig ) )
            // InternalMazeDsl.g:9973:2: ( rulePatrolZoneConfig )
            {
            // InternalMazeDsl.g:9973:2: ( rulePatrolZoneConfig )
            // InternalMazeDsl.g:9974:3: rulePatrolZoneConfig
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
    // InternalMazeDsl.g:9983:1: rule__PatrolConfig__WaypointsAssignment_7 : ( ruleWaypoint ) ;
    public final void rule__PatrolConfig__WaypointsAssignment_7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:9987:1: ( ( ruleWaypoint ) )
            // InternalMazeDsl.g:9988:2: ( ruleWaypoint )
            {
            // InternalMazeDsl.g:9988:2: ( ruleWaypoint )
            // InternalMazeDsl.g:9989:3: ruleWaypoint
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
    // InternalMazeDsl.g:9998:1: rule__PatrolConfig__WaypointsAssignment_8_1 : ( ruleWaypoint ) ;
    public final void rule__PatrolConfig__WaypointsAssignment_8_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10002:1: ( ( ruleWaypoint ) )
            // InternalMazeDsl.g:10003:2: ( ruleWaypoint )
            {
            // InternalMazeDsl.g:10003:2: ( ruleWaypoint )
            // InternalMazeDsl.g:10004:3: ruleWaypoint
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
    // InternalMazeDsl.g:10013:1: rule__PatrolZoneConfig__TopLeftXAssignment_4 : ( ruleDOUBLE ) ;
    public final void rule__PatrolZoneConfig__TopLeftXAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10017:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:10018:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:10018:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:10019:3: ruleDOUBLE
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
    // InternalMazeDsl.g:10028:1: rule__PatrolZoneConfig__TopLeftYAssignment_6 : ( ruleDOUBLE ) ;
    public final void rule__PatrolZoneConfig__TopLeftYAssignment_6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10032:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:10033:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:10033:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:10034:3: ruleDOUBLE
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
    // InternalMazeDsl.g:10043:1: rule__PatrolZoneConfig__WidthAssignment_9 : ( ruleDOUBLE ) ;
    public final void rule__PatrolZoneConfig__WidthAssignment_9() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10047:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:10048:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:10048:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:10049:3: ruleDOUBLE
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
    // InternalMazeDsl.g:10058:1: rule__PatrolZoneConfig__HeightAssignment_11 : ( ruleDOUBLE ) ;
    public final void rule__PatrolZoneConfig__HeightAssignment_11() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10062:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:10063:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:10063:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:10064:3: ruleDOUBLE
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
    // InternalMazeDsl.g:10073:1: rule__Waypoint__XAssignment_1 : ( ruleDOUBLE ) ;
    public final void rule__Waypoint__XAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10077:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:10078:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:10078:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:10079:3: ruleDOUBLE
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
    // InternalMazeDsl.g:10088:1: rule__Waypoint__YAssignment_3 : ( ruleDOUBLE ) ;
    public final void rule__Waypoint__YAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10092:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:10093:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:10093:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:10094:3: ruleDOUBLE
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
    // InternalMazeDsl.g:10103:1: rule__Waypoint__WaitTimeAssignment_5_1 : ( RULE_INT ) ;
    public final void rule__Waypoint__WaitTimeAssignment_5_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10107:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:10108:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:10108:2: ( RULE_INT )
            // InternalMazeDsl.g:10109:3: RULE_INT
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
    // InternalMazeDsl.g:10118:1: rule__LootTableConfig__NameAssignment_1 : ( ruleValidID ) ;
    public final void rule__LootTableConfig__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10122:1: ( ( ruleValidID ) )
            // InternalMazeDsl.g:10123:2: ( ruleValidID )
            {
            // InternalMazeDsl.g:10123:2: ( ruleValidID )
            // InternalMazeDsl.g:10124:3: ruleValidID
            {
             before(grammarAccess.getLootTableConfigAccess().getNameValidIDParserRuleCall_1_0()); 
            pushFollow(FOLLOW_2);
            ruleValidID();

            state._fsp--;

             after(grammarAccess.getLootTableConfigAccess().getNameValidIDParserRuleCall_1_0()); 

            }


            }

        }
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
    // InternalMazeDsl.g:10133:1: rule__LootTableConfig__CapacityAssignment_3_1 : ( RULE_INT ) ;
    public final void rule__LootTableConfig__CapacityAssignment_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10137:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:10138:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:10138:2: ( RULE_INT )
            // InternalMazeDsl.g:10139:3: RULE_INT
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
    // InternalMazeDsl.g:10148:1: rule__LootTableConfig__ItemsAssignment_4 : ( ruleLootItemConfig ) ;
    public final void rule__LootTableConfig__ItemsAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10152:1: ( ( ruleLootItemConfig ) )
            // InternalMazeDsl.g:10153:2: ( ruleLootItemConfig )
            {
            // InternalMazeDsl.g:10153:2: ( ruleLootItemConfig )
            // InternalMazeDsl.g:10154:3: ruleLootItemConfig
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
    // InternalMazeDsl.g:10163:1: rule__LootItemConfig__NameAssignment_1 : ( ruleValidID ) ;
    public final void rule__LootItemConfig__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10167:1: ( ( ruleValidID ) )
            // InternalMazeDsl.g:10168:2: ( ruleValidID )
            {
            // InternalMazeDsl.g:10168:2: ( ruleValidID )
            // InternalMazeDsl.g:10169:3: ruleValidID
            {
             before(grammarAccess.getLootItemConfigAccess().getNameValidIDParserRuleCall_1_0()); 
            pushFollow(FOLLOW_2);
            ruleValidID();

            state._fsp--;

             after(grammarAccess.getLootItemConfigAccess().getNameValidIDParserRuleCall_1_0()); 

            }


            }

        }
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
    // InternalMazeDsl.g:10178:1: rule__LootItemConfig__TypeAssignment_4 : ( ruleLootItemTypeEnum ) ;
    public final void rule__LootItemConfig__TypeAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10182:1: ( ( ruleLootItemTypeEnum ) )
            // InternalMazeDsl.g:10183:2: ( ruleLootItemTypeEnum )
            {
            // InternalMazeDsl.g:10183:2: ( ruleLootItemTypeEnum )
            // InternalMazeDsl.g:10184:3: ruleLootItemTypeEnum
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
    // InternalMazeDsl.g:10193:1: rule__LootItemConfig__ValueAssignment_6 : ( RULE_INT ) ;
    public final void rule__LootItemConfig__ValueAssignment_6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10197:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:10198:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:10198:2: ( RULE_INT )
            // InternalMazeDsl.g:10199:3: RULE_INT
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
    // InternalMazeDsl.g:10208:1: rule__LootItemConfig__WeightAssignment_7_1 : ( RULE_INT ) ;
    public final void rule__LootItemConfig__WeightAssignment_7_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10212:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:10213:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:10213:2: ( RULE_INT )
            // InternalMazeDsl.g:10214:3: RULE_INT
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
    // InternalMazeDsl.g:10223:1: rule__LevelConfig__NumberAssignment_1 : ( RULE_INT ) ;
    public final void rule__LevelConfig__NumberAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10227:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:10228:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:10228:2: ( RULE_INT )
            // InternalMazeDsl.g:10229:3: RULE_INT
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
    // InternalMazeDsl.g:10238:1: rule__LevelConfig__NameAssignment_2 : ( RULE_STRING ) ;
    public final void rule__LevelConfig__NameAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10242:1: ( ( RULE_STRING ) )
            // InternalMazeDsl.g:10243:2: ( RULE_STRING )
            {
            // InternalMazeDsl.g:10243:2: ( RULE_STRING )
            // InternalMazeDsl.g:10244:3: RULE_STRING
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
    // InternalMazeDsl.g:10253:1: rule__LevelConfig__BaseLevelAssignment_4_1 : ( RULE_INT ) ;
    public final void rule__LevelConfig__BaseLevelAssignment_4_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10257:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:10258:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:10258:2: ( RULE_INT )
            // InternalMazeDsl.g:10259:3: RULE_INT
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
    // InternalMazeDsl.g:10268:1: rule__LevelConfig__ThreatCapacityFactorAssignment_5_1 : ( ruleDOUBLE_OBJECT ) ;
    public final void rule__LevelConfig__ThreatCapacityFactorAssignment_5_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10272:1: ( ( ruleDOUBLE_OBJECT ) )
            // InternalMazeDsl.g:10273:2: ( ruleDOUBLE_OBJECT )
            {
            // InternalMazeDsl.g:10273:2: ( ruleDOUBLE_OBJECT )
            // InternalMazeDsl.g:10274:3: ruleDOUBLE_OBJECT
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
    // InternalMazeDsl.g:10283:1: rule__LevelConfig__ShowWallMaterialsAssignment_6_1 : ( ruleBOOLEAN_OBJECT ) ;
    public final void rule__LevelConfig__ShowWallMaterialsAssignment_6_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10287:1: ( ( ruleBOOLEAN_OBJECT ) )
            // InternalMazeDsl.g:10288:2: ( ruleBOOLEAN_OBJECT )
            {
            // InternalMazeDsl.g:10288:2: ( ruleBOOLEAN_OBJECT )
            // InternalMazeDsl.g:10289:3: ruleBOOLEAN_OBJECT
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
    // InternalMazeDsl.g:10298:1: rule__LevelConfig__InheritWeaponsAssignment_7_1 : ( ruleBOOLEAN_OBJECT ) ;
    public final void rule__LevelConfig__InheritWeaponsAssignment_7_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10302:1: ( ( ruleBOOLEAN_OBJECT ) )
            // InternalMazeDsl.g:10303:2: ( ruleBOOLEAN_OBJECT )
            {
            // InternalMazeDsl.g:10303:2: ( ruleBOOLEAN_OBJECT )
            // InternalMazeDsl.g:10304:3: ruleBOOLEAN_OBJECT
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
    // InternalMazeDsl.g:10313:1: rule__LevelConfig__CascadeDepthAssignment_8_1 : ( ruleINT_OBJECT ) ;
    public final void rule__LevelConfig__CascadeDepthAssignment_8_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10317:1: ( ( ruleINT_OBJECT ) )
            // InternalMazeDsl.g:10318:2: ( ruleINT_OBJECT )
            {
            // InternalMazeDsl.g:10318:2: ( ruleINT_OBJECT )
            // InternalMazeDsl.g:10319:3: ruleINT_OBJECT
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
    // InternalMazeDsl.g:10328:1: rule__LevelConfig__ZonesAssignment_9 : ( ruleLevelZone ) ;
    public final void rule__LevelConfig__ZonesAssignment_9() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10332:1: ( ( ruleLevelZone ) )
            // InternalMazeDsl.g:10333:2: ( ruleLevelZone )
            {
            // InternalMazeDsl.g:10333:2: ( ruleLevelZone )
            // InternalMazeDsl.g:10334:3: ruleLevelZone
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
    // InternalMazeDsl.g:10343:1: rule__LevelConfig__SergeantAssignment_10 : ( ruleSergeantConfig ) ;
    public final void rule__LevelConfig__SergeantAssignment_10() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10347:1: ( ( ruleSergeantConfig ) )
            // InternalMazeDsl.g:10348:2: ( ruleSergeantConfig )
            {
            // InternalMazeDsl.g:10348:2: ( ruleSergeantConfig )
            // InternalMazeDsl.g:10349:3: ruleSergeantConfig
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
    // InternalMazeDsl.g:10358:1: rule__LevelConfig__SergeantPostsAssignment_11 : ( ruleTierCountConfig ) ;
    public final void rule__LevelConfig__SergeantPostsAssignment_11() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10362:1: ( ( ruleTierCountConfig ) )
            // InternalMazeDsl.g:10363:2: ( ruleTierCountConfig )
            {
            // InternalMazeDsl.g:10363:2: ( ruleTierCountConfig )
            // InternalMazeDsl.g:10364:3: ruleTierCountConfig
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
    // InternalMazeDsl.g:10373:1: rule__LevelConfig__MinesAssignment_12 : ( ruleMineRingConfig ) ;
    public final void rule__LevelConfig__MinesAssignment_12() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10377:1: ( ( ruleMineRingConfig ) )
            // InternalMazeDsl.g:10378:2: ( ruleMineRingConfig )
            {
            // InternalMazeDsl.g:10378:2: ( ruleMineRingConfig )
            // InternalMazeDsl.g:10379:3: ruleMineRingConfig
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
    // InternalMazeDsl.g:10388:1: rule__LevelConfig__PortalGhostAssignment_13 : ( rulePortalGhostConfig ) ;
    public final void rule__LevelConfig__PortalGhostAssignment_13() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10392:1: ( ( rulePortalGhostConfig ) )
            // InternalMazeDsl.g:10393:2: ( rulePortalGhostConfig )
            {
            // InternalMazeDsl.g:10393:2: ( rulePortalGhostConfig )
            // InternalMazeDsl.g:10394:3: rulePortalGhostConfig
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
    // InternalMazeDsl.g:10403:1: rule__LevelConfig__GhostCountsAssignment_14 : ( ruleGhostCountConfig ) ;
    public final void rule__LevelConfig__GhostCountsAssignment_14() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10407:1: ( ( ruleGhostCountConfig ) )
            // InternalMazeDsl.g:10408:2: ( ruleGhostCountConfig )
            {
            // InternalMazeDsl.g:10408:2: ( ruleGhostCountConfig )
            // InternalMazeDsl.g:10409:3: ruleGhostCountConfig
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
    // InternalMazeDsl.g:10418:1: rule__LevelConfig__TeleportAssignment_15 : ( ruleTeleportRulesConfig ) ;
    public final void rule__LevelConfig__TeleportAssignment_15() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10422:1: ( ( ruleTeleportRulesConfig ) )
            // InternalMazeDsl.g:10423:2: ( ruleTeleportRulesConfig )
            {
            // InternalMazeDsl.g:10423:2: ( ruleTeleportRulesConfig )
            // InternalMazeDsl.g:10424:3: ruleTeleportRulesConfig
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
    // InternalMazeDsl.g:10433:1: rule__LevelZone__DisplayNameAssignment_1 : ( RULE_STRING ) ;
    public final void rule__LevelZone__DisplayNameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10437:1: ( ( RULE_STRING ) )
            // InternalMazeDsl.g:10438:2: ( RULE_STRING )
            {
            // InternalMazeDsl.g:10438:2: ( RULE_STRING )
            // InternalMazeDsl.g:10439:3: RULE_STRING
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
    // InternalMazeDsl.g:10448:1: rule__LevelZone__BreakableShareAssignment_2_1 : ( ruleDOUBLE_OBJECT ) ;
    public final void rule__LevelZone__BreakableShareAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10452:1: ( ( ruleDOUBLE_OBJECT ) )
            // InternalMazeDsl.g:10453:2: ( ruleDOUBLE_OBJECT )
            {
            // InternalMazeDsl.g:10453:2: ( ruleDOUBLE_OBJECT )
            // InternalMazeDsl.g:10454:3: ruleDOUBLE_OBJECT
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
    // InternalMazeDsl.g:10463:1: rule__LevelZone__MaterialIdsAssignment_4 : ( RULE_ID ) ;
    public final void rule__LevelZone__MaterialIdsAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10467:1: ( ( RULE_ID ) )
            // InternalMazeDsl.g:10468:2: ( RULE_ID )
            {
            // InternalMazeDsl.g:10468:2: ( RULE_ID )
            // InternalMazeDsl.g:10469:3: RULE_ID
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
    // InternalMazeDsl.g:10478:1: rule__LevelZone__MaterialIdsAssignment_5_1 : ( RULE_ID ) ;
    public final void rule__LevelZone__MaterialIdsAssignment_5_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10482:1: ( ( RULE_ID ) )
            // InternalMazeDsl.g:10483:2: ( RULE_ID )
            {
            // InternalMazeDsl.g:10483:2: ( RULE_ID )
            // InternalMazeDsl.g:10484:3: RULE_ID
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
    // InternalMazeDsl.g:10493:1: rule__SergeantConfig__DisplayNameAssignment_1 : ( RULE_STRING ) ;
    public final void rule__SergeantConfig__DisplayNameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10497:1: ( ( RULE_STRING ) )
            // InternalMazeDsl.g:10498:2: ( RULE_STRING )
            {
            // InternalMazeDsl.g:10498:2: ( RULE_STRING )
            // InternalMazeDsl.g:10499:3: RULE_STRING
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
    // InternalMazeDsl.g:10508:1: rule__SergeantConfig__HealthAssignment_4 : ( RULE_INT ) ;
    public final void rule__SergeantConfig__HealthAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10512:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:10513:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:10513:2: ( RULE_INT )
            // InternalMazeDsl.g:10514:3: RULE_INT
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
    // InternalMazeDsl.g:10523:1: rule__SergeantConfig__SightRangeAssignment_6 : ( ruleDOUBLE ) ;
    public final void rule__SergeantConfig__SightRangeAssignment_6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10527:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:10528:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:10528:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:10529:3: ruleDOUBLE
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
    // InternalMazeDsl.g:10538:1: rule__SergeantConfig__RoundsPerBurstAssignment_8 : ( RULE_INT ) ;
    public final void rule__SergeantConfig__RoundsPerBurstAssignment_8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10542:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:10543:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:10543:2: ( RULE_INT )
            // InternalMazeDsl.g:10544:3: RULE_INT
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
    // InternalMazeDsl.g:10553:1: rule__SergeantConfig__RoundDamageAssignment_10 : ( RULE_INT ) ;
    public final void rule__SergeantConfig__RoundDamageAssignment_10() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10557:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:10558:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:10558:2: ( RULE_INT )
            // InternalMazeDsl.g:10559:3: RULE_INT
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
    // InternalMazeDsl.g:10568:1: rule__SergeantConfig__RoundIntervalSecondsAssignment_12 : ( ruleDOUBLE ) ;
    public final void rule__SergeantConfig__RoundIntervalSecondsAssignment_12() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10572:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:10573:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:10573:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:10574:3: ruleDOUBLE
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
    // InternalMazeDsl.g:10583:1: rule__SergeantConfig__BurstCooldownSecondsAssignment_14 : ( ruleDOUBLE ) ;
    public final void rule__SergeantConfig__BurstCooldownSecondsAssignment_14() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10587:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:10588:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:10588:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:10589:3: ruleDOUBLE
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
    // InternalMazeDsl.g:10598:1: rule__SergeantConfig__SpriteAssignment_16 : ( RULE_STRING ) ;
    public final void rule__SergeantConfig__SpriteAssignment_16() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10602:1: ( ( RULE_STRING ) )
            // InternalMazeDsl.g:10603:2: ( RULE_STRING )
            {
            // InternalMazeDsl.g:10603:2: ( RULE_STRING )
            // InternalMazeDsl.g:10604:3: RULE_STRING
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
    // InternalMazeDsl.g:10613:1: rule__SergeantConfig__FireSoundAssignment_17_1 : ( RULE_STRING ) ;
    public final void rule__SergeantConfig__FireSoundAssignment_17_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10617:1: ( ( RULE_STRING ) )
            // InternalMazeDsl.g:10618:2: ( RULE_STRING )
            {
            // InternalMazeDsl.g:10618:2: ( RULE_STRING )
            // InternalMazeDsl.g:10619:3: RULE_STRING
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
    // InternalMazeDsl.g:10628:1: rule__SergeantConfig__DropWeaponAssignment_19 : ( ruleDropWeaponEnum ) ;
    public final void rule__SergeantConfig__DropWeaponAssignment_19() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10632:1: ( ( ruleDropWeaponEnum ) )
            // InternalMazeDsl.g:10633:2: ( ruleDropWeaponEnum )
            {
            // InternalMazeDsl.g:10633:2: ( ruleDropWeaponEnum )
            // InternalMazeDsl.g:10634:3: ruleDropWeaponEnum
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
    // InternalMazeDsl.g:10643:1: rule__SergeantConfig__PostDistanceFromHeartAssignment_21 : ( ruleDOUBLE ) ;
    public final void rule__SergeantConfig__PostDistanceFromHeartAssignment_21() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10647:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:10648:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:10648:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:10649:3: ruleDOUBLE
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
    // InternalMazeDsl.g:10658:1: rule__TierCountConfig__DifficultyAssignment_1 : ( ruleDifficultyLevel ) ;
    public final void rule__TierCountConfig__DifficultyAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10662:1: ( ( ruleDifficultyLevel ) )
            // InternalMazeDsl.g:10663:2: ( ruleDifficultyLevel )
            {
            // InternalMazeDsl.g:10663:2: ( ruleDifficultyLevel )
            // InternalMazeDsl.g:10664:3: ruleDifficultyLevel
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
    // InternalMazeDsl.g:10673:1: rule__TierCountConfig__CountAssignment_2 : ( RULE_INT ) ;
    public final void rule__TierCountConfig__CountAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10677:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:10678:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:10678:2: ( RULE_INT )
            // InternalMazeDsl.g:10679:3: RULE_INT
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
    // InternalMazeDsl.g:10688:1: rule__MineRingConfig__CountAssignment_3 : ( RULE_INT ) ;
    public final void rule__MineRingConfig__CountAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10692:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:10693:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:10693:2: ( RULE_INT )
            // InternalMazeDsl.g:10694:3: RULE_INT
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
    // InternalMazeDsl.g:10703:1: rule__MineRingConfig__RingRadiusAssignment_5 : ( ruleDOUBLE ) ;
    public final void rule__MineRingConfig__RingRadiusAssignment_5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10707:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:10708:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:10708:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:10709:3: ruleDOUBLE
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
    // InternalMazeDsl.g:10718:1: rule__MineRingConfig__TriggerRadiusAssignment_7 : ( ruleDOUBLE ) ;
    public final void rule__MineRingConfig__TriggerRadiusAssignment_7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10722:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:10723:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:10723:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:10724:3: ruleDOUBLE
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
    // InternalMazeDsl.g:10733:1: rule__MineRingConfig__PulseSecondsAssignment_9 : ( ruleDOUBLE ) ;
    public final void rule__MineRingConfig__PulseSecondsAssignment_9() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10737:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:10738:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:10738:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:10739:3: ruleDOUBLE
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
    // InternalMazeDsl.g:10748:1: rule__MineRingConfig__BlastRadiusAssignment_11 : ( ruleDOUBLE ) ;
    public final void rule__MineRingConfig__BlastRadiusAssignment_11() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10752:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:10753:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:10753:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:10754:3: ruleDOUBLE
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
    // InternalMazeDsl.g:10763:1: rule__MineRingConfig__BlastDamageAssignment_13 : ( RULE_INT ) ;
    public final void rule__MineRingConfig__BlastDamageAssignment_13() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10767:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:10768:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:10768:2: ( RULE_INT )
            // InternalMazeDsl.g:10769:3: RULE_INT
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
    // InternalMazeDsl.g:10778:1: rule__MineRingConfig__PlayerDamageAssignment_15 : ( RULE_INT ) ;
    public final void rule__MineRingConfig__PlayerDamageAssignment_15() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10782:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:10783:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:10783:2: ( RULE_INT )
            // InternalMazeDsl.g:10784:3: RULE_INT
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
    // InternalMazeDsl.g:10793:1: rule__MineRingConfig__HoleRadiusAssignment_17 : ( ruleDOUBLE ) ;
    public final void rule__MineRingConfig__HoleRadiusAssignment_17() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10797:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:10798:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:10798:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:10799:3: ruleDOUBLE
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
    // InternalMazeDsl.g:10808:1: rule__PortalGhostConfig__DisplayNameAssignment_1 : ( RULE_STRING ) ;
    public final void rule__PortalGhostConfig__DisplayNameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10812:1: ( ( RULE_STRING ) )
            // InternalMazeDsl.g:10813:2: ( RULE_STRING )
            {
            // InternalMazeDsl.g:10813:2: ( RULE_STRING )
            // InternalMazeDsl.g:10814:3: RULE_STRING
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
    // InternalMazeDsl.g:10823:1: rule__PortalGhostConfig__HealthAssignment_4 : ( RULE_INT ) ;
    public final void rule__PortalGhostConfig__HealthAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10827:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:10828:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:10828:2: ( RULE_INT )
            // InternalMazeDsl.g:10829:3: RULE_INT
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
    // InternalMazeDsl.g:10838:1: rule__PortalGhostConfig__SpeedAssignment_6 : ( ruleDOUBLE ) ;
    public final void rule__PortalGhostConfig__SpeedAssignment_6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10842:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:10843:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:10843:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:10844:3: ruleDOUBLE
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
    // InternalMazeDsl.g:10853:1: rule__PortalGhostConfig__SpriteAssignment_8 : ( RULE_STRING ) ;
    public final void rule__PortalGhostConfig__SpriteAssignment_8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10857:1: ( ( RULE_STRING ) )
            // InternalMazeDsl.g:10858:2: ( RULE_STRING )
            {
            // InternalMazeDsl.g:10858:2: ( RULE_STRING )
            // InternalMazeDsl.g:10859:3: RULE_STRING
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
    // InternalMazeDsl.g:10868:1: rule__GhostCountConfig__DifficultyAssignment_1 : ( ruleDifficultyLevel ) ;
    public final void rule__GhostCountConfig__DifficultyAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10872:1: ( ( ruleDifficultyLevel ) )
            // InternalMazeDsl.g:10873:2: ( ruleDifficultyLevel )
            {
            // InternalMazeDsl.g:10873:2: ( ruleDifficultyLevel )
            // InternalMazeDsl.g:10874:3: ruleDifficultyLevel
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
    // InternalMazeDsl.g:10883:1: rule__GhostCountConfig__CountAssignment_2 : ( RULE_INT ) ;
    public final void rule__GhostCountConfig__CountAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10887:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:10888:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:10888:2: ( RULE_INT )
            // InternalMazeDsl.g:10889:3: RULE_INT
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
    // InternalMazeDsl.g:10898:1: rule__TeleportRulesConfig__CooldownSecondsAssignment_3 : ( ruleDOUBLE ) ;
    public final void rule__TeleportRulesConfig__CooldownSecondsAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10902:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:10903:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:10903:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:10904:3: ruleDOUBLE
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
    // InternalMazeDsl.g:10913:1: rule__TeleportRulesConfig__BannerTextAssignment_5 : ( RULE_STRING ) ;
    public final void rule__TeleportRulesConfig__BannerTextAssignment_5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10917:1: ( ( RULE_STRING ) )
            // InternalMazeDsl.g:10918:2: ( RULE_STRING )
            {
            // InternalMazeDsl.g:10918:2: ( RULE_STRING )
            // InternalMazeDsl.g:10919:3: RULE_STRING
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
    // InternalMazeDsl.g:10928:1: rule__TeleportRulesConfig__BannerSecondsAssignment_7 : ( ruleDOUBLE ) ;
    public final void rule__TeleportRulesConfig__BannerSecondsAssignment_7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10932:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:10933:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:10933:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:10934:3: ruleDOUBLE
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
    // InternalMazeDsl.g:10943:1: rule__TeleportRulesConfig__ZapSoundAssignment_8_1 : ( RULE_STRING ) ;
    public final void rule__TeleportRulesConfig__ZapSoundAssignment_8_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10947:1: ( ( RULE_STRING ) )
            // InternalMazeDsl.g:10948:2: ( RULE_STRING )
            {
            // InternalMazeDsl.g:10948:2: ( RULE_STRING )
            // InternalMazeDsl.g:10949:3: RULE_STRING
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
    // InternalMazeDsl.g:10958:1: rule__TeleportRulesConfig__VoiceSoundAssignment_9_1 : ( RULE_STRING ) ;
    public final void rule__TeleportRulesConfig__VoiceSoundAssignment_9_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:10962:1: ( ( RULE_STRING ) )
            // InternalMazeDsl.g:10963:2: ( RULE_STRING )
            {
            // InternalMazeDsl.g:10963:2: ( RULE_STRING )
            // InternalMazeDsl.g:10964:3: RULE_STRING
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
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0003FFFFFFFFC810L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0800000000000000L,0x00040000000081E0L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000040L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000000000002L,0x0000000000008000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0800000000000002L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000000002L,0x0004000000000000L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000100L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x001C000000000000L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000000000000L,0x0000000000003E20L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000000000002L,0x0000000000002000L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000000000020L,0x0080000000000000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x00E0000000000000L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0800000000000000L,0x0000000091FE0020L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000000000003000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0F00000000000000L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000000000000000L,0x000000000E000020L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000000000000000L,0x0000000062000020L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000000000000000L,0x0000000091000000L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0000000000000000L,0x0000001F02000020L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x7000000000000000L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0000000000000000L,0x0000052000000000L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0000000000000000L,0x0000100000000000L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000000000000000L,0x0000028000000000L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0000000000000002L,0x0000020000000000L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x0000000000000000L,0x0000020000000000L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x0000000000000000L,0x0000200000000000L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x0000000000000000L,0x0000400000000000L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x0000000000000000L,0x0000800000000000L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x0000000000000000L,0x0001000000000000L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x0000000000000000L,0x0002000000000000L});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0x0000000000000000L,0x0018000000000000L});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x0000000000000002L,0x0018000000000000L});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x8000000000000000L,0x0000000000000007L});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000020L});
    public static final BitSet FOLLOW_50 = new BitSet(new long[]{0x00001C0200CFC000L,0x0000000000000020L});
    public static final BitSet FOLLOW_51 = new BitSet(new long[]{0x0000000000080002L});
    public static final BitSet FOLLOW_52 = new BitSet(new long[]{0x0000000000800002L});
    public static final BitSet FOLLOW_53 = new BitSet(new long[]{0x0000080000000002L});
    public static final BitSet FOLLOW_54 = new BitSet(new long[]{0x0000000000300000L});
    public static final BitSet FOLLOW_55 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_56 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_57 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_58 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_59 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_60 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_61 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_62 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_63 = new BitSet(new long[]{0x00000000C0000000L});
    public static final BitSet FOLLOW_64 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_65 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_66 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_67 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_68 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_69 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_70 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_71 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_72 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_73 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_74 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_75 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_76 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_77 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_78 = new BitSet(new long[]{0x0003000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_79 = new BitSet(new long[]{0x0000000000000000L,0x0100000000000000L});

}