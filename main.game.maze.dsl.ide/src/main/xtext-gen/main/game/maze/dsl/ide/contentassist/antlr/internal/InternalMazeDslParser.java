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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_INT", "RULE_ID", "RULE_STRING", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'true'", "'false'", "'easy'", "'normal'", "'hard'", "'zombie'", "'ghost'", "'pumpkinbomber'", "'passive'", "'wander'", "'aggressive'", "'patrol'", "'straight'", "'lob'", "'beam'", "'food'", "'bomb'", "'trap'", "'weapon'", "'game'", "'{'", "'}'", "'import'", "'difficulty'", "'level'", "'instantDeath'", "'speedMultiplier'", "'damageMultiplier'", "'maxThreat'", "'limit'", "'max'", "'opponent'", "'type'", "'displayName'", "'health'", "'speed'", "'threatLevel'", "'enabled'", "'behavior'", "'loot'", "'zombie-stats'", "'attackDamage'", "'infectionLevel'", "'resurrectionTime'", "'ghost-stats'", "'visibilityLevel'", "'nonTangibilityEnergy'", "'ranged-stats'", "'attackRange'", "'attackCooldown'", "'projectileSpeed'", "'projectileType'", "'splashRadius'", "'path'", "'['", "']'", "'visionRange'", "'zone'", "','", "'topLeft'", "'('", "')'", "'width'", "'height'", "':'", "'ms'", "'loot-table'", "'capacity'", "'item'", "'value'", "'weight'", "'.'"
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
    public static final int RULE_INT=4;
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
    public static final int RULE_STRING=6;
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


    // $ANTLR start "entryRuleDOUBLE"
    // InternalMazeDsl.g:403:1: entryRuleDOUBLE : ruleDOUBLE EOF ;
    public final void entryRuleDOUBLE() throws RecognitionException {
        try {
            // InternalMazeDsl.g:404:1: ( ruleDOUBLE EOF )
            // InternalMazeDsl.g:405:1: ruleDOUBLE EOF
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
    // InternalMazeDsl.g:412:1: ruleDOUBLE : ( ( rule__DOUBLE__Group__0 ) ) ;
    public final void ruleDOUBLE() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:416:2: ( ( ( rule__DOUBLE__Group__0 ) ) )
            // InternalMazeDsl.g:417:2: ( ( rule__DOUBLE__Group__0 ) )
            {
            // InternalMazeDsl.g:417:2: ( ( rule__DOUBLE__Group__0 ) )
            // InternalMazeDsl.g:418:3: ( rule__DOUBLE__Group__0 )
            {
             before(grammarAccess.getDOUBLEAccess().getGroup()); 
            // InternalMazeDsl.g:419:3: ( rule__DOUBLE__Group__0 )
            // InternalMazeDsl.g:419:4: rule__DOUBLE__Group__0
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


    // $ANTLR start "ruleDifficultyLevel"
    // InternalMazeDsl.g:428:1: ruleDifficultyLevel : ( ( rule__DifficultyLevel__Alternatives ) ) ;
    public final void ruleDifficultyLevel() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:432:1: ( ( ( rule__DifficultyLevel__Alternatives ) ) )
            // InternalMazeDsl.g:433:2: ( ( rule__DifficultyLevel__Alternatives ) )
            {
            // InternalMazeDsl.g:433:2: ( ( rule__DifficultyLevel__Alternatives ) )
            // InternalMazeDsl.g:434:3: ( rule__DifficultyLevel__Alternatives )
            {
             before(grammarAccess.getDifficultyLevelAccess().getAlternatives()); 
            // InternalMazeDsl.g:435:3: ( rule__DifficultyLevel__Alternatives )
            // InternalMazeDsl.g:435:4: rule__DifficultyLevel__Alternatives
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
    // InternalMazeDsl.g:444:1: ruleEnemyType : ( ( rule__EnemyType__Alternatives ) ) ;
    public final void ruleEnemyType() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:448:1: ( ( ( rule__EnemyType__Alternatives ) ) )
            // InternalMazeDsl.g:449:2: ( ( rule__EnemyType__Alternatives ) )
            {
            // InternalMazeDsl.g:449:2: ( ( rule__EnemyType__Alternatives ) )
            // InternalMazeDsl.g:450:3: ( rule__EnemyType__Alternatives )
            {
             before(grammarAccess.getEnemyTypeAccess().getAlternatives()); 
            // InternalMazeDsl.g:451:3: ( rule__EnemyType__Alternatives )
            // InternalMazeDsl.g:451:4: rule__EnemyType__Alternatives
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
    // InternalMazeDsl.g:460:1: ruleCharacterTypeEnum : ( ( rule__CharacterTypeEnum__Alternatives ) ) ;
    public final void ruleCharacterTypeEnum() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:464:1: ( ( ( rule__CharacterTypeEnum__Alternatives ) ) )
            // InternalMazeDsl.g:465:2: ( ( rule__CharacterTypeEnum__Alternatives ) )
            {
            // InternalMazeDsl.g:465:2: ( ( rule__CharacterTypeEnum__Alternatives ) )
            // InternalMazeDsl.g:466:3: ( rule__CharacterTypeEnum__Alternatives )
            {
             before(grammarAccess.getCharacterTypeEnumAccess().getAlternatives()); 
            // InternalMazeDsl.g:467:3: ( rule__CharacterTypeEnum__Alternatives )
            // InternalMazeDsl.g:467:4: rule__CharacterTypeEnum__Alternatives
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
    // InternalMazeDsl.g:476:1: ruleBehaviorTypeEnum : ( ( rule__BehaviorTypeEnum__Alternatives ) ) ;
    public final void ruleBehaviorTypeEnum() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:480:1: ( ( ( rule__BehaviorTypeEnum__Alternatives ) ) )
            // InternalMazeDsl.g:481:2: ( ( rule__BehaviorTypeEnum__Alternatives ) )
            {
            // InternalMazeDsl.g:481:2: ( ( rule__BehaviorTypeEnum__Alternatives ) )
            // InternalMazeDsl.g:482:3: ( rule__BehaviorTypeEnum__Alternatives )
            {
             before(grammarAccess.getBehaviorTypeEnumAccess().getAlternatives()); 
            // InternalMazeDsl.g:483:3: ( rule__BehaviorTypeEnum__Alternatives )
            // InternalMazeDsl.g:483:4: rule__BehaviorTypeEnum__Alternatives
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
    // InternalMazeDsl.g:492:1: ruleProjectileTypeEnum : ( ( rule__ProjectileTypeEnum__Alternatives ) ) ;
    public final void ruleProjectileTypeEnum() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:496:1: ( ( ( rule__ProjectileTypeEnum__Alternatives ) ) )
            // InternalMazeDsl.g:497:2: ( ( rule__ProjectileTypeEnum__Alternatives ) )
            {
            // InternalMazeDsl.g:497:2: ( ( rule__ProjectileTypeEnum__Alternatives ) )
            // InternalMazeDsl.g:498:3: ( rule__ProjectileTypeEnum__Alternatives )
            {
             before(grammarAccess.getProjectileTypeEnumAccess().getAlternatives()); 
            // InternalMazeDsl.g:499:3: ( rule__ProjectileTypeEnum__Alternatives )
            // InternalMazeDsl.g:499:4: rule__ProjectileTypeEnum__Alternatives
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
    // InternalMazeDsl.g:508:1: ruleLootItemTypeEnum : ( ( rule__LootItemTypeEnum__Alternatives ) ) ;
    public final void ruleLootItemTypeEnum() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:512:1: ( ( ( rule__LootItemTypeEnum__Alternatives ) ) )
            // InternalMazeDsl.g:513:2: ( ( rule__LootItemTypeEnum__Alternatives ) )
            {
            // InternalMazeDsl.g:513:2: ( ( rule__LootItemTypeEnum__Alternatives ) )
            // InternalMazeDsl.g:514:3: ( rule__LootItemTypeEnum__Alternatives )
            {
             before(grammarAccess.getLootItemTypeEnumAccess().getAlternatives()); 
            // InternalMazeDsl.g:515:3: ( rule__LootItemTypeEnum__Alternatives )
            // InternalMazeDsl.g:515:4: rule__LootItemTypeEnum__Alternatives
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


    // $ANTLR start "rule__OpponentConfig__EnabledAlternatives_9_1_0"
    // InternalMazeDsl.g:523:1: rule__OpponentConfig__EnabledAlternatives_9_1_0 : ( ( 'true' ) | ( 'false' ) );
    public final void rule__OpponentConfig__EnabledAlternatives_9_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:527:1: ( ( 'true' ) | ( 'false' ) )
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==11) ) {
                alt1=1;
            }
            else if ( (LA1_0==12) ) {
                alt1=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }
            switch (alt1) {
                case 1 :
                    // InternalMazeDsl.g:528:2: ( 'true' )
                    {
                    // InternalMazeDsl.g:528:2: ( 'true' )
                    // InternalMazeDsl.g:529:3: 'true'
                    {
                     before(grammarAccess.getOpponentConfigAccess().getEnabledTrueKeyword_9_1_0_0()); 
                    match(input,11,FOLLOW_2); 
                     after(grammarAccess.getOpponentConfigAccess().getEnabledTrueKeyword_9_1_0_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:534:2: ( 'false' )
                    {
                    // InternalMazeDsl.g:534:2: ( 'false' )
                    // InternalMazeDsl.g:535:3: 'false'
                    {
                     before(grammarAccess.getOpponentConfigAccess().getEnabledFalseKeyword_9_1_0_1()); 
                    match(input,12,FOLLOW_2); 
                     after(grammarAccess.getOpponentConfigAccess().getEnabledFalseKeyword_9_1_0_1()); 

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
    // $ANTLR end "rule__OpponentConfig__EnabledAlternatives_9_1_0"


    // $ANTLR start "rule__CharacterSpecifics__Alternatives"
    // InternalMazeDsl.g:544:1: rule__CharacterSpecifics__Alternatives : ( ( ruleZombieSpecifics ) | ( ruleGhostSpecifics ) | ( ruleRangedSpecifics ) );
    public final void rule__CharacterSpecifics__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:548:1: ( ( ruleZombieSpecifics ) | ( ruleGhostSpecifics ) | ( ruleRangedSpecifics ) )
            int alt2=3;
            switch ( input.LA(1) ) {
            case 51:
                {
                alt2=1;
                }
                break;
            case 55:
                {
                alt2=2;
                }
                break;
            case 58:
                {
                alt2=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }

            switch (alt2) {
                case 1 :
                    // InternalMazeDsl.g:549:2: ( ruleZombieSpecifics )
                    {
                    // InternalMazeDsl.g:549:2: ( ruleZombieSpecifics )
                    // InternalMazeDsl.g:550:3: ruleZombieSpecifics
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
                    // InternalMazeDsl.g:555:2: ( ruleGhostSpecifics )
                    {
                    // InternalMazeDsl.g:555:2: ( ruleGhostSpecifics )
                    // InternalMazeDsl.g:556:3: ruleGhostSpecifics
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
                    // InternalMazeDsl.g:561:2: ( ruleRangedSpecifics )
                    {
                    // InternalMazeDsl.g:561:2: ( ruleRangedSpecifics )
                    // InternalMazeDsl.g:562:3: ruleRangedSpecifics
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


    // $ANTLR start "rule__DifficultyLevel__Alternatives"
    // InternalMazeDsl.g:571:1: rule__DifficultyLevel__Alternatives : ( ( ( 'easy' ) ) | ( ( 'normal' ) ) | ( ( 'hard' ) ) );
    public final void rule__DifficultyLevel__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:575:1: ( ( ( 'easy' ) ) | ( ( 'normal' ) ) | ( ( 'hard' ) ) )
            int alt3=3;
            switch ( input.LA(1) ) {
            case 13:
                {
                alt3=1;
                }
                break;
            case 14:
                {
                alt3=2;
                }
                break;
            case 15:
                {
                alt3=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }

            switch (alt3) {
                case 1 :
                    // InternalMazeDsl.g:576:2: ( ( 'easy' ) )
                    {
                    // InternalMazeDsl.g:576:2: ( ( 'easy' ) )
                    // InternalMazeDsl.g:577:3: ( 'easy' )
                    {
                     before(grammarAccess.getDifficultyLevelAccess().getEASYEnumLiteralDeclaration_0()); 
                    // InternalMazeDsl.g:578:3: ( 'easy' )
                    // InternalMazeDsl.g:578:4: 'easy'
                    {
                    match(input,13,FOLLOW_2); 

                    }

                     after(grammarAccess.getDifficultyLevelAccess().getEASYEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:582:2: ( ( 'normal' ) )
                    {
                    // InternalMazeDsl.g:582:2: ( ( 'normal' ) )
                    // InternalMazeDsl.g:583:3: ( 'normal' )
                    {
                     before(grammarAccess.getDifficultyLevelAccess().getNORMALEnumLiteralDeclaration_1()); 
                    // InternalMazeDsl.g:584:3: ( 'normal' )
                    // InternalMazeDsl.g:584:4: 'normal'
                    {
                    match(input,14,FOLLOW_2); 

                    }

                     after(grammarAccess.getDifficultyLevelAccess().getNORMALEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalMazeDsl.g:588:2: ( ( 'hard' ) )
                    {
                    // InternalMazeDsl.g:588:2: ( ( 'hard' ) )
                    // InternalMazeDsl.g:589:3: ( 'hard' )
                    {
                     before(grammarAccess.getDifficultyLevelAccess().getHARDEnumLiteralDeclaration_2()); 
                    // InternalMazeDsl.g:590:3: ( 'hard' )
                    // InternalMazeDsl.g:590:4: 'hard'
                    {
                    match(input,15,FOLLOW_2); 

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
    // InternalMazeDsl.g:598:1: rule__EnemyType__Alternatives : ( ( ( 'zombie' ) ) | ( ( 'ghost' ) ) | ( ( 'pumpkinbomber' ) ) );
    public final void rule__EnemyType__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:602:1: ( ( ( 'zombie' ) ) | ( ( 'ghost' ) ) | ( ( 'pumpkinbomber' ) ) )
            int alt4=3;
            switch ( input.LA(1) ) {
            case 16:
                {
                alt4=1;
                }
                break;
            case 17:
                {
                alt4=2;
                }
                break;
            case 18:
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
                    // InternalMazeDsl.g:603:2: ( ( 'zombie' ) )
                    {
                    // InternalMazeDsl.g:603:2: ( ( 'zombie' ) )
                    // InternalMazeDsl.g:604:3: ( 'zombie' )
                    {
                     before(grammarAccess.getEnemyTypeAccess().getZOMBIEEnumLiteralDeclaration_0()); 
                    // InternalMazeDsl.g:605:3: ( 'zombie' )
                    // InternalMazeDsl.g:605:4: 'zombie'
                    {
                    match(input,16,FOLLOW_2); 

                    }

                     after(grammarAccess.getEnemyTypeAccess().getZOMBIEEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:609:2: ( ( 'ghost' ) )
                    {
                    // InternalMazeDsl.g:609:2: ( ( 'ghost' ) )
                    // InternalMazeDsl.g:610:3: ( 'ghost' )
                    {
                     before(grammarAccess.getEnemyTypeAccess().getGHOSTEnumLiteralDeclaration_1()); 
                    // InternalMazeDsl.g:611:3: ( 'ghost' )
                    // InternalMazeDsl.g:611:4: 'ghost'
                    {
                    match(input,17,FOLLOW_2); 

                    }

                     after(grammarAccess.getEnemyTypeAccess().getGHOSTEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalMazeDsl.g:615:2: ( ( 'pumpkinbomber' ) )
                    {
                    // InternalMazeDsl.g:615:2: ( ( 'pumpkinbomber' ) )
                    // InternalMazeDsl.g:616:3: ( 'pumpkinbomber' )
                    {
                     before(grammarAccess.getEnemyTypeAccess().getPUMPKINBOMBEREnumLiteralDeclaration_2()); 
                    // InternalMazeDsl.g:617:3: ( 'pumpkinbomber' )
                    // InternalMazeDsl.g:617:4: 'pumpkinbomber'
                    {
                    match(input,18,FOLLOW_2); 

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
    // InternalMazeDsl.g:625:1: rule__CharacterTypeEnum__Alternatives : ( ( ( 'zombie' ) ) | ( ( 'ghost' ) ) | ( ( 'pumpkinbomber' ) ) );
    public final void rule__CharacterTypeEnum__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:629:1: ( ( ( 'zombie' ) ) | ( ( 'ghost' ) ) | ( ( 'pumpkinbomber' ) ) )
            int alt5=3;
            switch ( input.LA(1) ) {
            case 16:
                {
                alt5=1;
                }
                break;
            case 17:
                {
                alt5=2;
                }
                break;
            case 18:
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
                    // InternalMazeDsl.g:630:2: ( ( 'zombie' ) )
                    {
                    // InternalMazeDsl.g:630:2: ( ( 'zombie' ) )
                    // InternalMazeDsl.g:631:3: ( 'zombie' )
                    {
                     before(grammarAccess.getCharacterTypeEnumAccess().getZOMBIEEnumLiteralDeclaration_0()); 
                    // InternalMazeDsl.g:632:3: ( 'zombie' )
                    // InternalMazeDsl.g:632:4: 'zombie'
                    {
                    match(input,16,FOLLOW_2); 

                    }

                     after(grammarAccess.getCharacterTypeEnumAccess().getZOMBIEEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:636:2: ( ( 'ghost' ) )
                    {
                    // InternalMazeDsl.g:636:2: ( ( 'ghost' ) )
                    // InternalMazeDsl.g:637:3: ( 'ghost' )
                    {
                     before(grammarAccess.getCharacterTypeEnumAccess().getGHOSTEnumLiteralDeclaration_1()); 
                    // InternalMazeDsl.g:638:3: ( 'ghost' )
                    // InternalMazeDsl.g:638:4: 'ghost'
                    {
                    match(input,17,FOLLOW_2); 

                    }

                     after(grammarAccess.getCharacterTypeEnumAccess().getGHOSTEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalMazeDsl.g:642:2: ( ( 'pumpkinbomber' ) )
                    {
                    // InternalMazeDsl.g:642:2: ( ( 'pumpkinbomber' ) )
                    // InternalMazeDsl.g:643:3: ( 'pumpkinbomber' )
                    {
                     before(grammarAccess.getCharacterTypeEnumAccess().getPUMPKINBOMBEREnumLiteralDeclaration_2()); 
                    // InternalMazeDsl.g:644:3: ( 'pumpkinbomber' )
                    // InternalMazeDsl.g:644:4: 'pumpkinbomber'
                    {
                    match(input,18,FOLLOW_2); 

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
    // InternalMazeDsl.g:652:1: rule__BehaviorTypeEnum__Alternatives : ( ( ( 'passive' ) ) | ( ( 'wander' ) ) | ( ( 'aggressive' ) ) | ( ( 'patrol' ) ) );
    public final void rule__BehaviorTypeEnum__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:656:1: ( ( ( 'passive' ) ) | ( ( 'wander' ) ) | ( ( 'aggressive' ) ) | ( ( 'patrol' ) ) )
            int alt6=4;
            switch ( input.LA(1) ) {
            case 19:
                {
                alt6=1;
                }
                break;
            case 20:
                {
                alt6=2;
                }
                break;
            case 21:
                {
                alt6=3;
                }
                break;
            case 22:
                {
                alt6=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }

            switch (alt6) {
                case 1 :
                    // InternalMazeDsl.g:657:2: ( ( 'passive' ) )
                    {
                    // InternalMazeDsl.g:657:2: ( ( 'passive' ) )
                    // InternalMazeDsl.g:658:3: ( 'passive' )
                    {
                     before(grammarAccess.getBehaviorTypeEnumAccess().getPASSIVEEnumLiteralDeclaration_0()); 
                    // InternalMazeDsl.g:659:3: ( 'passive' )
                    // InternalMazeDsl.g:659:4: 'passive'
                    {
                    match(input,19,FOLLOW_2); 

                    }

                     after(grammarAccess.getBehaviorTypeEnumAccess().getPASSIVEEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:663:2: ( ( 'wander' ) )
                    {
                    // InternalMazeDsl.g:663:2: ( ( 'wander' ) )
                    // InternalMazeDsl.g:664:3: ( 'wander' )
                    {
                     before(grammarAccess.getBehaviorTypeEnumAccess().getWANDEREnumLiteralDeclaration_1()); 
                    // InternalMazeDsl.g:665:3: ( 'wander' )
                    // InternalMazeDsl.g:665:4: 'wander'
                    {
                    match(input,20,FOLLOW_2); 

                    }

                     after(grammarAccess.getBehaviorTypeEnumAccess().getWANDEREnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalMazeDsl.g:669:2: ( ( 'aggressive' ) )
                    {
                    // InternalMazeDsl.g:669:2: ( ( 'aggressive' ) )
                    // InternalMazeDsl.g:670:3: ( 'aggressive' )
                    {
                     before(grammarAccess.getBehaviorTypeEnumAccess().getAGGRESSIVEEnumLiteralDeclaration_2()); 
                    // InternalMazeDsl.g:671:3: ( 'aggressive' )
                    // InternalMazeDsl.g:671:4: 'aggressive'
                    {
                    match(input,21,FOLLOW_2); 

                    }

                     after(grammarAccess.getBehaviorTypeEnumAccess().getAGGRESSIVEEnumLiteralDeclaration_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalMazeDsl.g:675:2: ( ( 'patrol' ) )
                    {
                    // InternalMazeDsl.g:675:2: ( ( 'patrol' ) )
                    // InternalMazeDsl.g:676:3: ( 'patrol' )
                    {
                     before(grammarAccess.getBehaviorTypeEnumAccess().getPATROLEnumLiteralDeclaration_3()); 
                    // InternalMazeDsl.g:677:3: ( 'patrol' )
                    // InternalMazeDsl.g:677:4: 'patrol'
                    {
                    match(input,22,FOLLOW_2); 

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
    // InternalMazeDsl.g:685:1: rule__ProjectileTypeEnum__Alternatives : ( ( ( 'straight' ) ) | ( ( 'lob' ) ) | ( ( 'beam' ) ) );
    public final void rule__ProjectileTypeEnum__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:689:1: ( ( ( 'straight' ) ) | ( ( 'lob' ) ) | ( ( 'beam' ) ) )
            int alt7=3;
            switch ( input.LA(1) ) {
            case 23:
                {
                alt7=1;
                }
                break;
            case 24:
                {
                alt7=2;
                }
                break;
            case 25:
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
                    // InternalMazeDsl.g:690:2: ( ( 'straight' ) )
                    {
                    // InternalMazeDsl.g:690:2: ( ( 'straight' ) )
                    // InternalMazeDsl.g:691:3: ( 'straight' )
                    {
                     before(grammarAccess.getProjectileTypeEnumAccess().getSTRAIGHTEnumLiteralDeclaration_0()); 
                    // InternalMazeDsl.g:692:3: ( 'straight' )
                    // InternalMazeDsl.g:692:4: 'straight'
                    {
                    match(input,23,FOLLOW_2); 

                    }

                     after(grammarAccess.getProjectileTypeEnumAccess().getSTRAIGHTEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:696:2: ( ( 'lob' ) )
                    {
                    // InternalMazeDsl.g:696:2: ( ( 'lob' ) )
                    // InternalMazeDsl.g:697:3: ( 'lob' )
                    {
                     before(grammarAccess.getProjectileTypeEnumAccess().getLOBEnumLiteralDeclaration_1()); 
                    // InternalMazeDsl.g:698:3: ( 'lob' )
                    // InternalMazeDsl.g:698:4: 'lob'
                    {
                    match(input,24,FOLLOW_2); 

                    }

                     after(grammarAccess.getProjectileTypeEnumAccess().getLOBEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalMazeDsl.g:702:2: ( ( 'beam' ) )
                    {
                    // InternalMazeDsl.g:702:2: ( ( 'beam' ) )
                    // InternalMazeDsl.g:703:3: ( 'beam' )
                    {
                     before(grammarAccess.getProjectileTypeEnumAccess().getBEAMEnumLiteralDeclaration_2()); 
                    // InternalMazeDsl.g:704:3: ( 'beam' )
                    // InternalMazeDsl.g:704:4: 'beam'
                    {
                    match(input,25,FOLLOW_2); 

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
    // InternalMazeDsl.g:712:1: rule__LootItemTypeEnum__Alternatives : ( ( ( 'food' ) ) | ( ( 'bomb' ) ) | ( ( 'trap' ) ) | ( ( 'weapon' ) ) );
    public final void rule__LootItemTypeEnum__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:716:1: ( ( ( 'food' ) ) | ( ( 'bomb' ) ) | ( ( 'trap' ) ) | ( ( 'weapon' ) ) )
            int alt8=4;
            switch ( input.LA(1) ) {
            case 26:
                {
                alt8=1;
                }
                break;
            case 27:
                {
                alt8=2;
                }
                break;
            case 28:
                {
                alt8=3;
                }
                break;
            case 29:
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
                    // InternalMazeDsl.g:717:2: ( ( 'food' ) )
                    {
                    // InternalMazeDsl.g:717:2: ( ( 'food' ) )
                    // InternalMazeDsl.g:718:3: ( 'food' )
                    {
                     before(grammarAccess.getLootItemTypeEnumAccess().getFOODEnumLiteralDeclaration_0()); 
                    // InternalMazeDsl.g:719:3: ( 'food' )
                    // InternalMazeDsl.g:719:4: 'food'
                    {
                    match(input,26,FOLLOW_2); 

                    }

                     after(grammarAccess.getLootItemTypeEnumAccess().getFOODEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:723:2: ( ( 'bomb' ) )
                    {
                    // InternalMazeDsl.g:723:2: ( ( 'bomb' ) )
                    // InternalMazeDsl.g:724:3: ( 'bomb' )
                    {
                     before(grammarAccess.getLootItemTypeEnumAccess().getBOMBEnumLiteralDeclaration_1()); 
                    // InternalMazeDsl.g:725:3: ( 'bomb' )
                    // InternalMazeDsl.g:725:4: 'bomb'
                    {
                    match(input,27,FOLLOW_2); 

                    }

                     after(grammarAccess.getLootItemTypeEnumAccess().getBOMBEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalMazeDsl.g:729:2: ( ( 'trap' ) )
                    {
                    // InternalMazeDsl.g:729:2: ( ( 'trap' ) )
                    // InternalMazeDsl.g:730:3: ( 'trap' )
                    {
                     before(grammarAccess.getLootItemTypeEnumAccess().getTRAPEnumLiteralDeclaration_2()); 
                    // InternalMazeDsl.g:731:3: ( 'trap' )
                    // InternalMazeDsl.g:731:4: 'trap'
                    {
                    match(input,28,FOLLOW_2); 

                    }

                     after(grammarAccess.getLootItemTypeEnumAccess().getTRAPEnumLiteralDeclaration_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalMazeDsl.g:735:2: ( ( 'weapon' ) )
                    {
                    // InternalMazeDsl.g:735:2: ( ( 'weapon' ) )
                    // InternalMazeDsl.g:736:3: ( 'weapon' )
                    {
                     before(grammarAccess.getLootItemTypeEnumAccess().getWEAPONEnumLiteralDeclaration_3()); 
                    // InternalMazeDsl.g:737:3: ( 'weapon' )
                    // InternalMazeDsl.g:737:4: 'weapon'
                    {
                    match(input,29,FOLLOW_2); 

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
    // InternalMazeDsl.g:745:1: rule__GameConfiguration__Group__0 : rule__GameConfiguration__Group__0__Impl rule__GameConfiguration__Group__1 ;
    public final void rule__GameConfiguration__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:749:1: ( rule__GameConfiguration__Group__0__Impl rule__GameConfiguration__Group__1 )
            // InternalMazeDsl.g:750:2: rule__GameConfiguration__Group__0__Impl rule__GameConfiguration__Group__1
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
    // InternalMazeDsl.g:757:1: rule__GameConfiguration__Group__0__Impl : ( 'game' ) ;
    public final void rule__GameConfiguration__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:761:1: ( ( 'game' ) )
            // InternalMazeDsl.g:762:1: ( 'game' )
            {
            // InternalMazeDsl.g:762:1: ( 'game' )
            // InternalMazeDsl.g:763:2: 'game'
            {
             before(grammarAccess.getGameConfigurationAccess().getGameKeyword_0()); 
            match(input,30,FOLLOW_2); 
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
    // InternalMazeDsl.g:772:1: rule__GameConfiguration__Group__1 : rule__GameConfiguration__Group__1__Impl rule__GameConfiguration__Group__2 ;
    public final void rule__GameConfiguration__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:776:1: ( rule__GameConfiguration__Group__1__Impl rule__GameConfiguration__Group__2 )
            // InternalMazeDsl.g:777:2: rule__GameConfiguration__Group__1__Impl rule__GameConfiguration__Group__2
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
    // InternalMazeDsl.g:784:1: rule__GameConfiguration__Group__1__Impl : ( ( rule__GameConfiguration__NameAssignment_1 ) ) ;
    public final void rule__GameConfiguration__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:788:1: ( ( ( rule__GameConfiguration__NameAssignment_1 ) ) )
            // InternalMazeDsl.g:789:1: ( ( rule__GameConfiguration__NameAssignment_1 ) )
            {
            // InternalMazeDsl.g:789:1: ( ( rule__GameConfiguration__NameAssignment_1 ) )
            // InternalMazeDsl.g:790:2: ( rule__GameConfiguration__NameAssignment_1 )
            {
             before(grammarAccess.getGameConfigurationAccess().getNameAssignment_1()); 
            // InternalMazeDsl.g:791:2: ( rule__GameConfiguration__NameAssignment_1 )
            // InternalMazeDsl.g:791:3: rule__GameConfiguration__NameAssignment_1
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
    // InternalMazeDsl.g:799:1: rule__GameConfiguration__Group__2 : rule__GameConfiguration__Group__2__Impl rule__GameConfiguration__Group__3 ;
    public final void rule__GameConfiguration__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:803:1: ( rule__GameConfiguration__Group__2__Impl rule__GameConfiguration__Group__3 )
            // InternalMazeDsl.g:804:2: rule__GameConfiguration__Group__2__Impl rule__GameConfiguration__Group__3
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
    // InternalMazeDsl.g:811:1: rule__GameConfiguration__Group__2__Impl : ( '{' ) ;
    public final void rule__GameConfiguration__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:815:1: ( ( '{' ) )
            // InternalMazeDsl.g:816:1: ( '{' )
            {
            // InternalMazeDsl.g:816:1: ( '{' )
            // InternalMazeDsl.g:817:2: '{'
            {
             before(grammarAccess.getGameConfigurationAccess().getLeftCurlyBracketKeyword_2()); 
            match(input,31,FOLLOW_2); 
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
    // InternalMazeDsl.g:826:1: rule__GameConfiguration__Group__3 : rule__GameConfiguration__Group__3__Impl rule__GameConfiguration__Group__4 ;
    public final void rule__GameConfiguration__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:830:1: ( rule__GameConfiguration__Group__3__Impl rule__GameConfiguration__Group__4 )
            // InternalMazeDsl.g:831:2: rule__GameConfiguration__Group__3__Impl rule__GameConfiguration__Group__4
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
    // InternalMazeDsl.g:838:1: rule__GameConfiguration__Group__3__Impl : ( ( rule__GameConfiguration__ImportsAssignment_3 )* ) ;
    public final void rule__GameConfiguration__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:842:1: ( ( ( rule__GameConfiguration__ImportsAssignment_3 )* ) )
            // InternalMazeDsl.g:843:1: ( ( rule__GameConfiguration__ImportsAssignment_3 )* )
            {
            // InternalMazeDsl.g:843:1: ( ( rule__GameConfiguration__ImportsAssignment_3 )* )
            // InternalMazeDsl.g:844:2: ( rule__GameConfiguration__ImportsAssignment_3 )*
            {
             before(grammarAccess.getGameConfigurationAccess().getImportsAssignment_3()); 
            // InternalMazeDsl.g:845:2: ( rule__GameConfiguration__ImportsAssignment_3 )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==33) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // InternalMazeDsl.g:845:3: rule__GameConfiguration__ImportsAssignment_3
            	    {
            	    pushFollow(FOLLOW_6);
            	    rule__GameConfiguration__ImportsAssignment_3();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop9;
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
    // InternalMazeDsl.g:853:1: rule__GameConfiguration__Group__4 : rule__GameConfiguration__Group__4__Impl rule__GameConfiguration__Group__5 ;
    public final void rule__GameConfiguration__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:857:1: ( rule__GameConfiguration__Group__4__Impl rule__GameConfiguration__Group__5 )
            // InternalMazeDsl.g:858:2: rule__GameConfiguration__Group__4__Impl rule__GameConfiguration__Group__5
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
    // InternalMazeDsl.g:865:1: rule__GameConfiguration__Group__4__Impl : ( ( rule__GameConfiguration__DifficultyAssignment_4 )? ) ;
    public final void rule__GameConfiguration__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:869:1: ( ( ( rule__GameConfiguration__DifficultyAssignment_4 )? ) )
            // InternalMazeDsl.g:870:1: ( ( rule__GameConfiguration__DifficultyAssignment_4 )? )
            {
            // InternalMazeDsl.g:870:1: ( ( rule__GameConfiguration__DifficultyAssignment_4 )? )
            // InternalMazeDsl.g:871:2: ( rule__GameConfiguration__DifficultyAssignment_4 )?
            {
             before(grammarAccess.getGameConfigurationAccess().getDifficultyAssignment_4()); 
            // InternalMazeDsl.g:872:2: ( rule__GameConfiguration__DifficultyAssignment_4 )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==34) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // InternalMazeDsl.g:872:3: rule__GameConfiguration__DifficultyAssignment_4
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
    // InternalMazeDsl.g:880:1: rule__GameConfiguration__Group__5 : rule__GameConfiguration__Group__5__Impl rule__GameConfiguration__Group__6 ;
    public final void rule__GameConfiguration__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:884:1: ( rule__GameConfiguration__Group__5__Impl rule__GameConfiguration__Group__6 )
            // InternalMazeDsl.g:885:2: rule__GameConfiguration__Group__5__Impl rule__GameConfiguration__Group__6
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
    // InternalMazeDsl.g:892:1: rule__GameConfiguration__Group__5__Impl : ( ( rule__GameConfiguration__OpponentsAssignment_5 )* ) ;
    public final void rule__GameConfiguration__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:896:1: ( ( ( rule__GameConfiguration__OpponentsAssignment_5 )* ) )
            // InternalMazeDsl.g:897:1: ( ( rule__GameConfiguration__OpponentsAssignment_5 )* )
            {
            // InternalMazeDsl.g:897:1: ( ( rule__GameConfiguration__OpponentsAssignment_5 )* )
            // InternalMazeDsl.g:898:2: ( rule__GameConfiguration__OpponentsAssignment_5 )*
            {
             before(grammarAccess.getGameConfigurationAccess().getOpponentsAssignment_5()); 
            // InternalMazeDsl.g:899:2: ( rule__GameConfiguration__OpponentsAssignment_5 )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==42) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // InternalMazeDsl.g:899:3: rule__GameConfiguration__OpponentsAssignment_5
            	    {
            	    pushFollow(FOLLOW_7);
            	    rule__GameConfiguration__OpponentsAssignment_5();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop11;
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
    // InternalMazeDsl.g:907:1: rule__GameConfiguration__Group__6 : rule__GameConfiguration__Group__6__Impl rule__GameConfiguration__Group__7 ;
    public final void rule__GameConfiguration__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:911:1: ( rule__GameConfiguration__Group__6__Impl rule__GameConfiguration__Group__7 )
            // InternalMazeDsl.g:912:2: rule__GameConfiguration__Group__6__Impl rule__GameConfiguration__Group__7
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
    // InternalMazeDsl.g:919:1: rule__GameConfiguration__Group__6__Impl : ( ( rule__GameConfiguration__PatrolsAssignment_6 )* ) ;
    public final void rule__GameConfiguration__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:923:1: ( ( ( rule__GameConfiguration__PatrolsAssignment_6 )* ) )
            // InternalMazeDsl.g:924:1: ( ( rule__GameConfiguration__PatrolsAssignment_6 )* )
            {
            // InternalMazeDsl.g:924:1: ( ( rule__GameConfiguration__PatrolsAssignment_6 )* )
            // InternalMazeDsl.g:925:2: ( rule__GameConfiguration__PatrolsAssignment_6 )*
            {
             before(grammarAccess.getGameConfigurationAccess().getPatrolsAssignment_6()); 
            // InternalMazeDsl.g:926:2: ( rule__GameConfiguration__PatrolsAssignment_6 )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==22) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // InternalMazeDsl.g:926:3: rule__GameConfiguration__PatrolsAssignment_6
            	    {
            	    pushFollow(FOLLOW_8);
            	    rule__GameConfiguration__PatrolsAssignment_6();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop12;
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
    // InternalMazeDsl.g:934:1: rule__GameConfiguration__Group__7 : rule__GameConfiguration__Group__7__Impl rule__GameConfiguration__Group__8 ;
    public final void rule__GameConfiguration__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:938:1: ( rule__GameConfiguration__Group__7__Impl rule__GameConfiguration__Group__8 )
            // InternalMazeDsl.g:939:2: rule__GameConfiguration__Group__7__Impl rule__GameConfiguration__Group__8
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
    // InternalMazeDsl.g:946:1: rule__GameConfiguration__Group__7__Impl : ( ( rule__GameConfiguration__LootTablesAssignment_7 )* ) ;
    public final void rule__GameConfiguration__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:950:1: ( ( ( rule__GameConfiguration__LootTablesAssignment_7 )* ) )
            // InternalMazeDsl.g:951:1: ( ( rule__GameConfiguration__LootTablesAssignment_7 )* )
            {
            // InternalMazeDsl.g:951:1: ( ( rule__GameConfiguration__LootTablesAssignment_7 )* )
            // InternalMazeDsl.g:952:2: ( rule__GameConfiguration__LootTablesAssignment_7 )*
            {
             before(grammarAccess.getGameConfigurationAccess().getLootTablesAssignment_7()); 
            // InternalMazeDsl.g:953:2: ( rule__GameConfiguration__LootTablesAssignment_7 )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==77) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // InternalMazeDsl.g:953:3: rule__GameConfiguration__LootTablesAssignment_7
            	    {
            	    pushFollow(FOLLOW_9);
            	    rule__GameConfiguration__LootTablesAssignment_7();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop13;
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
    // InternalMazeDsl.g:961:1: rule__GameConfiguration__Group__8 : rule__GameConfiguration__Group__8__Impl ;
    public final void rule__GameConfiguration__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:965:1: ( rule__GameConfiguration__Group__8__Impl )
            // InternalMazeDsl.g:966:2: rule__GameConfiguration__Group__8__Impl
            {
            pushFollow(FOLLOW_2);
            rule__GameConfiguration__Group__8__Impl();

            state._fsp--;


            }

        }
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
    // InternalMazeDsl.g:972:1: rule__GameConfiguration__Group__8__Impl : ( '}' ) ;
    public final void rule__GameConfiguration__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:976:1: ( ( '}' ) )
            // InternalMazeDsl.g:977:1: ( '}' )
            {
            // InternalMazeDsl.g:977:1: ( '}' )
            // InternalMazeDsl.g:978:2: '}'
            {
             before(grammarAccess.getGameConfigurationAccess().getRightCurlyBracketKeyword_8()); 
            match(input,32,FOLLOW_2); 
             after(grammarAccess.getGameConfigurationAccess().getRightCurlyBracketKeyword_8()); 

            }


            }

        }
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


    // $ANTLR start "rule__Import__Group__0"
    // InternalMazeDsl.g:988:1: rule__Import__Group__0 : rule__Import__Group__0__Impl rule__Import__Group__1 ;
    public final void rule__Import__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:992:1: ( rule__Import__Group__0__Impl rule__Import__Group__1 )
            // InternalMazeDsl.g:993:2: rule__Import__Group__0__Impl rule__Import__Group__1
            {
            pushFollow(FOLLOW_10);
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
    // InternalMazeDsl.g:1000:1: rule__Import__Group__0__Impl : ( 'import' ) ;
    public final void rule__Import__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1004:1: ( ( 'import' ) )
            // InternalMazeDsl.g:1005:1: ( 'import' )
            {
            // InternalMazeDsl.g:1005:1: ( 'import' )
            // InternalMazeDsl.g:1006:2: 'import'
            {
             before(grammarAccess.getImportAccess().getImportKeyword_0()); 
            match(input,33,FOLLOW_2); 
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
    // InternalMazeDsl.g:1015:1: rule__Import__Group__1 : rule__Import__Group__1__Impl ;
    public final void rule__Import__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1019:1: ( rule__Import__Group__1__Impl )
            // InternalMazeDsl.g:1020:2: rule__Import__Group__1__Impl
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
    // InternalMazeDsl.g:1026:1: rule__Import__Group__1__Impl : ( ( rule__Import__ImportURIAssignment_1 ) ) ;
    public final void rule__Import__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1030:1: ( ( ( rule__Import__ImportURIAssignment_1 ) ) )
            // InternalMazeDsl.g:1031:1: ( ( rule__Import__ImportURIAssignment_1 ) )
            {
            // InternalMazeDsl.g:1031:1: ( ( rule__Import__ImportURIAssignment_1 ) )
            // InternalMazeDsl.g:1032:2: ( rule__Import__ImportURIAssignment_1 )
            {
             before(grammarAccess.getImportAccess().getImportURIAssignment_1()); 
            // InternalMazeDsl.g:1033:2: ( rule__Import__ImportURIAssignment_1 )
            // InternalMazeDsl.g:1033:3: rule__Import__ImportURIAssignment_1
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
    // InternalMazeDsl.g:1042:1: rule__DifficultyConfig__Group__0 : rule__DifficultyConfig__Group__0__Impl rule__DifficultyConfig__Group__1 ;
    public final void rule__DifficultyConfig__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1046:1: ( rule__DifficultyConfig__Group__0__Impl rule__DifficultyConfig__Group__1 )
            // InternalMazeDsl.g:1047:2: rule__DifficultyConfig__Group__0__Impl rule__DifficultyConfig__Group__1
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
    // InternalMazeDsl.g:1054:1: rule__DifficultyConfig__Group__0__Impl : ( 'difficulty' ) ;
    public final void rule__DifficultyConfig__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1058:1: ( ( 'difficulty' ) )
            // InternalMazeDsl.g:1059:1: ( 'difficulty' )
            {
            // InternalMazeDsl.g:1059:1: ( 'difficulty' )
            // InternalMazeDsl.g:1060:2: 'difficulty'
            {
             before(grammarAccess.getDifficultyConfigAccess().getDifficultyKeyword_0()); 
            match(input,34,FOLLOW_2); 
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
    // InternalMazeDsl.g:1069:1: rule__DifficultyConfig__Group__1 : rule__DifficultyConfig__Group__1__Impl rule__DifficultyConfig__Group__2 ;
    public final void rule__DifficultyConfig__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1073:1: ( rule__DifficultyConfig__Group__1__Impl rule__DifficultyConfig__Group__2 )
            // InternalMazeDsl.g:1074:2: rule__DifficultyConfig__Group__1__Impl rule__DifficultyConfig__Group__2
            {
            pushFollow(FOLLOW_11);
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
    // InternalMazeDsl.g:1081:1: rule__DifficultyConfig__Group__1__Impl : ( '{' ) ;
    public final void rule__DifficultyConfig__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1085:1: ( ( '{' ) )
            // InternalMazeDsl.g:1086:1: ( '{' )
            {
            // InternalMazeDsl.g:1086:1: ( '{' )
            // InternalMazeDsl.g:1087:2: '{'
            {
             before(grammarAccess.getDifficultyConfigAccess().getLeftCurlyBracketKeyword_1()); 
            match(input,31,FOLLOW_2); 
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
    // InternalMazeDsl.g:1096:1: rule__DifficultyConfig__Group__2 : rule__DifficultyConfig__Group__2__Impl rule__DifficultyConfig__Group__3 ;
    public final void rule__DifficultyConfig__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1100:1: ( rule__DifficultyConfig__Group__2__Impl rule__DifficultyConfig__Group__3 )
            // InternalMazeDsl.g:1101:2: rule__DifficultyConfig__Group__2__Impl rule__DifficultyConfig__Group__3
            {
            pushFollow(FOLLOW_12);
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
    // InternalMazeDsl.g:1108:1: rule__DifficultyConfig__Group__2__Impl : ( 'level' ) ;
    public final void rule__DifficultyConfig__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1112:1: ( ( 'level' ) )
            // InternalMazeDsl.g:1113:1: ( 'level' )
            {
            // InternalMazeDsl.g:1113:1: ( 'level' )
            // InternalMazeDsl.g:1114:2: 'level'
            {
             before(grammarAccess.getDifficultyConfigAccess().getLevelKeyword_2()); 
            match(input,35,FOLLOW_2); 
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
    // InternalMazeDsl.g:1123:1: rule__DifficultyConfig__Group__3 : rule__DifficultyConfig__Group__3__Impl rule__DifficultyConfig__Group__4 ;
    public final void rule__DifficultyConfig__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1127:1: ( rule__DifficultyConfig__Group__3__Impl rule__DifficultyConfig__Group__4 )
            // InternalMazeDsl.g:1128:2: rule__DifficultyConfig__Group__3__Impl rule__DifficultyConfig__Group__4
            {
            pushFollow(FOLLOW_13);
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
    // InternalMazeDsl.g:1135:1: rule__DifficultyConfig__Group__3__Impl : ( ( rule__DifficultyConfig__LevelAssignment_3 ) ) ;
    public final void rule__DifficultyConfig__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1139:1: ( ( ( rule__DifficultyConfig__LevelAssignment_3 ) ) )
            // InternalMazeDsl.g:1140:1: ( ( rule__DifficultyConfig__LevelAssignment_3 ) )
            {
            // InternalMazeDsl.g:1140:1: ( ( rule__DifficultyConfig__LevelAssignment_3 ) )
            // InternalMazeDsl.g:1141:2: ( rule__DifficultyConfig__LevelAssignment_3 )
            {
             before(grammarAccess.getDifficultyConfigAccess().getLevelAssignment_3()); 
            // InternalMazeDsl.g:1142:2: ( rule__DifficultyConfig__LevelAssignment_3 )
            // InternalMazeDsl.g:1142:3: rule__DifficultyConfig__LevelAssignment_3
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
    // InternalMazeDsl.g:1150:1: rule__DifficultyConfig__Group__4 : rule__DifficultyConfig__Group__4__Impl rule__DifficultyConfig__Group__5 ;
    public final void rule__DifficultyConfig__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1154:1: ( rule__DifficultyConfig__Group__4__Impl rule__DifficultyConfig__Group__5 )
            // InternalMazeDsl.g:1155:2: rule__DifficultyConfig__Group__4__Impl rule__DifficultyConfig__Group__5
            {
            pushFollow(FOLLOW_13);
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
    // InternalMazeDsl.g:1162:1: rule__DifficultyConfig__Group__4__Impl : ( ( rule__DifficultyConfig__Group_4__0 )? ) ;
    public final void rule__DifficultyConfig__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1166:1: ( ( ( rule__DifficultyConfig__Group_4__0 )? ) )
            // InternalMazeDsl.g:1167:1: ( ( rule__DifficultyConfig__Group_4__0 )? )
            {
            // InternalMazeDsl.g:1167:1: ( ( rule__DifficultyConfig__Group_4__0 )? )
            // InternalMazeDsl.g:1168:2: ( rule__DifficultyConfig__Group_4__0 )?
            {
             before(grammarAccess.getDifficultyConfigAccess().getGroup_4()); 
            // InternalMazeDsl.g:1169:2: ( rule__DifficultyConfig__Group_4__0 )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==36) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // InternalMazeDsl.g:1169:3: rule__DifficultyConfig__Group_4__0
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
    // InternalMazeDsl.g:1177:1: rule__DifficultyConfig__Group__5 : rule__DifficultyConfig__Group__5__Impl rule__DifficultyConfig__Group__6 ;
    public final void rule__DifficultyConfig__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1181:1: ( rule__DifficultyConfig__Group__5__Impl rule__DifficultyConfig__Group__6 )
            // InternalMazeDsl.g:1182:2: rule__DifficultyConfig__Group__5__Impl rule__DifficultyConfig__Group__6
            {
            pushFollow(FOLLOW_13);
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
    // InternalMazeDsl.g:1189:1: rule__DifficultyConfig__Group__5__Impl : ( ( rule__DifficultyConfig__Group_5__0 )? ) ;
    public final void rule__DifficultyConfig__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1193:1: ( ( ( rule__DifficultyConfig__Group_5__0 )? ) )
            // InternalMazeDsl.g:1194:1: ( ( rule__DifficultyConfig__Group_5__0 )? )
            {
            // InternalMazeDsl.g:1194:1: ( ( rule__DifficultyConfig__Group_5__0 )? )
            // InternalMazeDsl.g:1195:2: ( rule__DifficultyConfig__Group_5__0 )?
            {
             before(grammarAccess.getDifficultyConfigAccess().getGroup_5()); 
            // InternalMazeDsl.g:1196:2: ( rule__DifficultyConfig__Group_5__0 )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==37) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // InternalMazeDsl.g:1196:3: rule__DifficultyConfig__Group_5__0
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
    // InternalMazeDsl.g:1204:1: rule__DifficultyConfig__Group__6 : rule__DifficultyConfig__Group__6__Impl rule__DifficultyConfig__Group__7 ;
    public final void rule__DifficultyConfig__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1208:1: ( rule__DifficultyConfig__Group__6__Impl rule__DifficultyConfig__Group__7 )
            // InternalMazeDsl.g:1209:2: rule__DifficultyConfig__Group__6__Impl rule__DifficultyConfig__Group__7
            {
            pushFollow(FOLLOW_13);
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
    // InternalMazeDsl.g:1216:1: rule__DifficultyConfig__Group__6__Impl : ( ( rule__DifficultyConfig__Group_6__0 )? ) ;
    public final void rule__DifficultyConfig__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1220:1: ( ( ( rule__DifficultyConfig__Group_6__0 )? ) )
            // InternalMazeDsl.g:1221:1: ( ( rule__DifficultyConfig__Group_6__0 )? )
            {
            // InternalMazeDsl.g:1221:1: ( ( rule__DifficultyConfig__Group_6__0 )? )
            // InternalMazeDsl.g:1222:2: ( rule__DifficultyConfig__Group_6__0 )?
            {
             before(grammarAccess.getDifficultyConfigAccess().getGroup_6()); 
            // InternalMazeDsl.g:1223:2: ( rule__DifficultyConfig__Group_6__0 )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==38) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // InternalMazeDsl.g:1223:3: rule__DifficultyConfig__Group_6__0
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
    // InternalMazeDsl.g:1231:1: rule__DifficultyConfig__Group__7 : rule__DifficultyConfig__Group__7__Impl rule__DifficultyConfig__Group__8 ;
    public final void rule__DifficultyConfig__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1235:1: ( rule__DifficultyConfig__Group__7__Impl rule__DifficultyConfig__Group__8 )
            // InternalMazeDsl.g:1236:2: rule__DifficultyConfig__Group__7__Impl rule__DifficultyConfig__Group__8
            {
            pushFollow(FOLLOW_13);
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
    // InternalMazeDsl.g:1243:1: rule__DifficultyConfig__Group__7__Impl : ( ( rule__DifficultyConfig__Group_7__0 )? ) ;
    public final void rule__DifficultyConfig__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1247:1: ( ( ( rule__DifficultyConfig__Group_7__0 )? ) )
            // InternalMazeDsl.g:1248:1: ( ( rule__DifficultyConfig__Group_7__0 )? )
            {
            // InternalMazeDsl.g:1248:1: ( ( rule__DifficultyConfig__Group_7__0 )? )
            // InternalMazeDsl.g:1249:2: ( rule__DifficultyConfig__Group_7__0 )?
            {
             before(grammarAccess.getDifficultyConfigAccess().getGroup_7()); 
            // InternalMazeDsl.g:1250:2: ( rule__DifficultyConfig__Group_7__0 )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==39) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // InternalMazeDsl.g:1250:3: rule__DifficultyConfig__Group_7__0
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
    // InternalMazeDsl.g:1258:1: rule__DifficultyConfig__Group__8 : rule__DifficultyConfig__Group__8__Impl rule__DifficultyConfig__Group__9 ;
    public final void rule__DifficultyConfig__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1262:1: ( rule__DifficultyConfig__Group__8__Impl rule__DifficultyConfig__Group__9 )
            // InternalMazeDsl.g:1263:2: rule__DifficultyConfig__Group__8__Impl rule__DifficultyConfig__Group__9
            {
            pushFollow(FOLLOW_13);
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
    // InternalMazeDsl.g:1270:1: rule__DifficultyConfig__Group__8__Impl : ( ( rule__DifficultyConfig__EnemyLimitsAssignment_8 )* ) ;
    public final void rule__DifficultyConfig__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1274:1: ( ( ( rule__DifficultyConfig__EnemyLimitsAssignment_8 )* ) )
            // InternalMazeDsl.g:1275:1: ( ( rule__DifficultyConfig__EnemyLimitsAssignment_8 )* )
            {
            // InternalMazeDsl.g:1275:1: ( ( rule__DifficultyConfig__EnemyLimitsAssignment_8 )* )
            // InternalMazeDsl.g:1276:2: ( rule__DifficultyConfig__EnemyLimitsAssignment_8 )*
            {
             before(grammarAccess.getDifficultyConfigAccess().getEnemyLimitsAssignment_8()); 
            // InternalMazeDsl.g:1277:2: ( rule__DifficultyConfig__EnemyLimitsAssignment_8 )*
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( (LA18_0==40) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // InternalMazeDsl.g:1277:3: rule__DifficultyConfig__EnemyLimitsAssignment_8
            	    {
            	    pushFollow(FOLLOW_14);
            	    rule__DifficultyConfig__EnemyLimitsAssignment_8();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop18;
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
    // InternalMazeDsl.g:1285:1: rule__DifficultyConfig__Group__9 : rule__DifficultyConfig__Group__9__Impl ;
    public final void rule__DifficultyConfig__Group__9() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1289:1: ( rule__DifficultyConfig__Group__9__Impl )
            // InternalMazeDsl.g:1290:2: rule__DifficultyConfig__Group__9__Impl
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
    // InternalMazeDsl.g:1296:1: rule__DifficultyConfig__Group__9__Impl : ( '}' ) ;
    public final void rule__DifficultyConfig__Group__9__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1300:1: ( ( '}' ) )
            // InternalMazeDsl.g:1301:1: ( '}' )
            {
            // InternalMazeDsl.g:1301:1: ( '}' )
            // InternalMazeDsl.g:1302:2: '}'
            {
             before(grammarAccess.getDifficultyConfigAccess().getRightCurlyBracketKeyword_9()); 
            match(input,32,FOLLOW_2); 
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
    // InternalMazeDsl.g:1312:1: rule__DifficultyConfig__Group_4__0 : rule__DifficultyConfig__Group_4__0__Impl rule__DifficultyConfig__Group_4__1 ;
    public final void rule__DifficultyConfig__Group_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1316:1: ( rule__DifficultyConfig__Group_4__0__Impl rule__DifficultyConfig__Group_4__1 )
            // InternalMazeDsl.g:1317:2: rule__DifficultyConfig__Group_4__0__Impl rule__DifficultyConfig__Group_4__1
            {
            pushFollow(FOLLOW_15);
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
    // InternalMazeDsl.g:1324:1: rule__DifficultyConfig__Group_4__0__Impl : ( 'instantDeath' ) ;
    public final void rule__DifficultyConfig__Group_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1328:1: ( ( 'instantDeath' ) )
            // InternalMazeDsl.g:1329:1: ( 'instantDeath' )
            {
            // InternalMazeDsl.g:1329:1: ( 'instantDeath' )
            // InternalMazeDsl.g:1330:2: 'instantDeath'
            {
             before(grammarAccess.getDifficultyConfigAccess().getInstantDeathKeyword_4_0()); 
            match(input,36,FOLLOW_2); 
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
    // InternalMazeDsl.g:1339:1: rule__DifficultyConfig__Group_4__1 : rule__DifficultyConfig__Group_4__1__Impl ;
    public final void rule__DifficultyConfig__Group_4__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1343:1: ( rule__DifficultyConfig__Group_4__1__Impl )
            // InternalMazeDsl.g:1344:2: rule__DifficultyConfig__Group_4__1__Impl
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
    // InternalMazeDsl.g:1350:1: rule__DifficultyConfig__Group_4__1__Impl : ( ( rule__DifficultyConfig__InstantDeathAssignment_4_1 ) ) ;
    public final void rule__DifficultyConfig__Group_4__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1354:1: ( ( ( rule__DifficultyConfig__InstantDeathAssignment_4_1 ) ) )
            // InternalMazeDsl.g:1355:1: ( ( rule__DifficultyConfig__InstantDeathAssignment_4_1 ) )
            {
            // InternalMazeDsl.g:1355:1: ( ( rule__DifficultyConfig__InstantDeathAssignment_4_1 ) )
            // InternalMazeDsl.g:1356:2: ( rule__DifficultyConfig__InstantDeathAssignment_4_1 )
            {
             before(grammarAccess.getDifficultyConfigAccess().getInstantDeathAssignment_4_1()); 
            // InternalMazeDsl.g:1357:2: ( rule__DifficultyConfig__InstantDeathAssignment_4_1 )
            // InternalMazeDsl.g:1357:3: rule__DifficultyConfig__InstantDeathAssignment_4_1
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
    // InternalMazeDsl.g:1366:1: rule__DifficultyConfig__Group_5__0 : rule__DifficultyConfig__Group_5__0__Impl rule__DifficultyConfig__Group_5__1 ;
    public final void rule__DifficultyConfig__Group_5__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1370:1: ( rule__DifficultyConfig__Group_5__0__Impl rule__DifficultyConfig__Group_5__1 )
            // InternalMazeDsl.g:1371:2: rule__DifficultyConfig__Group_5__0__Impl rule__DifficultyConfig__Group_5__1
            {
            pushFollow(FOLLOW_16);
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
    // InternalMazeDsl.g:1378:1: rule__DifficultyConfig__Group_5__0__Impl : ( 'speedMultiplier' ) ;
    public final void rule__DifficultyConfig__Group_5__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1382:1: ( ( 'speedMultiplier' ) )
            // InternalMazeDsl.g:1383:1: ( 'speedMultiplier' )
            {
            // InternalMazeDsl.g:1383:1: ( 'speedMultiplier' )
            // InternalMazeDsl.g:1384:2: 'speedMultiplier'
            {
             before(grammarAccess.getDifficultyConfigAccess().getSpeedMultiplierKeyword_5_0()); 
            match(input,37,FOLLOW_2); 
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
    // InternalMazeDsl.g:1393:1: rule__DifficultyConfig__Group_5__1 : rule__DifficultyConfig__Group_5__1__Impl ;
    public final void rule__DifficultyConfig__Group_5__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1397:1: ( rule__DifficultyConfig__Group_5__1__Impl )
            // InternalMazeDsl.g:1398:2: rule__DifficultyConfig__Group_5__1__Impl
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
    // InternalMazeDsl.g:1404:1: rule__DifficultyConfig__Group_5__1__Impl : ( ( rule__DifficultyConfig__SpeedMultiplierAssignment_5_1 ) ) ;
    public final void rule__DifficultyConfig__Group_5__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1408:1: ( ( ( rule__DifficultyConfig__SpeedMultiplierAssignment_5_1 ) ) )
            // InternalMazeDsl.g:1409:1: ( ( rule__DifficultyConfig__SpeedMultiplierAssignment_5_1 ) )
            {
            // InternalMazeDsl.g:1409:1: ( ( rule__DifficultyConfig__SpeedMultiplierAssignment_5_1 ) )
            // InternalMazeDsl.g:1410:2: ( rule__DifficultyConfig__SpeedMultiplierAssignment_5_1 )
            {
             before(grammarAccess.getDifficultyConfigAccess().getSpeedMultiplierAssignment_5_1()); 
            // InternalMazeDsl.g:1411:2: ( rule__DifficultyConfig__SpeedMultiplierAssignment_5_1 )
            // InternalMazeDsl.g:1411:3: rule__DifficultyConfig__SpeedMultiplierAssignment_5_1
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
    // InternalMazeDsl.g:1420:1: rule__DifficultyConfig__Group_6__0 : rule__DifficultyConfig__Group_6__0__Impl rule__DifficultyConfig__Group_6__1 ;
    public final void rule__DifficultyConfig__Group_6__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1424:1: ( rule__DifficultyConfig__Group_6__0__Impl rule__DifficultyConfig__Group_6__1 )
            // InternalMazeDsl.g:1425:2: rule__DifficultyConfig__Group_6__0__Impl rule__DifficultyConfig__Group_6__1
            {
            pushFollow(FOLLOW_16);
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
    // InternalMazeDsl.g:1432:1: rule__DifficultyConfig__Group_6__0__Impl : ( 'damageMultiplier' ) ;
    public final void rule__DifficultyConfig__Group_6__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1436:1: ( ( 'damageMultiplier' ) )
            // InternalMazeDsl.g:1437:1: ( 'damageMultiplier' )
            {
            // InternalMazeDsl.g:1437:1: ( 'damageMultiplier' )
            // InternalMazeDsl.g:1438:2: 'damageMultiplier'
            {
             before(grammarAccess.getDifficultyConfigAccess().getDamageMultiplierKeyword_6_0()); 
            match(input,38,FOLLOW_2); 
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
    // InternalMazeDsl.g:1447:1: rule__DifficultyConfig__Group_6__1 : rule__DifficultyConfig__Group_6__1__Impl ;
    public final void rule__DifficultyConfig__Group_6__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1451:1: ( rule__DifficultyConfig__Group_6__1__Impl )
            // InternalMazeDsl.g:1452:2: rule__DifficultyConfig__Group_6__1__Impl
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
    // InternalMazeDsl.g:1458:1: rule__DifficultyConfig__Group_6__1__Impl : ( ( rule__DifficultyConfig__DamageMultiplierAssignment_6_1 ) ) ;
    public final void rule__DifficultyConfig__Group_6__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1462:1: ( ( ( rule__DifficultyConfig__DamageMultiplierAssignment_6_1 ) ) )
            // InternalMazeDsl.g:1463:1: ( ( rule__DifficultyConfig__DamageMultiplierAssignment_6_1 ) )
            {
            // InternalMazeDsl.g:1463:1: ( ( rule__DifficultyConfig__DamageMultiplierAssignment_6_1 ) )
            // InternalMazeDsl.g:1464:2: ( rule__DifficultyConfig__DamageMultiplierAssignment_6_1 )
            {
             before(grammarAccess.getDifficultyConfigAccess().getDamageMultiplierAssignment_6_1()); 
            // InternalMazeDsl.g:1465:2: ( rule__DifficultyConfig__DamageMultiplierAssignment_6_1 )
            // InternalMazeDsl.g:1465:3: rule__DifficultyConfig__DamageMultiplierAssignment_6_1
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
    // InternalMazeDsl.g:1474:1: rule__DifficultyConfig__Group_7__0 : rule__DifficultyConfig__Group_7__0__Impl rule__DifficultyConfig__Group_7__1 ;
    public final void rule__DifficultyConfig__Group_7__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1478:1: ( rule__DifficultyConfig__Group_7__0__Impl rule__DifficultyConfig__Group_7__1 )
            // InternalMazeDsl.g:1479:2: rule__DifficultyConfig__Group_7__0__Impl rule__DifficultyConfig__Group_7__1
            {
            pushFollow(FOLLOW_16);
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
    // InternalMazeDsl.g:1486:1: rule__DifficultyConfig__Group_7__0__Impl : ( 'maxThreat' ) ;
    public final void rule__DifficultyConfig__Group_7__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1490:1: ( ( 'maxThreat' ) )
            // InternalMazeDsl.g:1491:1: ( 'maxThreat' )
            {
            // InternalMazeDsl.g:1491:1: ( 'maxThreat' )
            // InternalMazeDsl.g:1492:2: 'maxThreat'
            {
             before(grammarAccess.getDifficultyConfigAccess().getMaxThreatKeyword_7_0()); 
            match(input,39,FOLLOW_2); 
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
    // InternalMazeDsl.g:1501:1: rule__DifficultyConfig__Group_7__1 : rule__DifficultyConfig__Group_7__1__Impl ;
    public final void rule__DifficultyConfig__Group_7__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1505:1: ( rule__DifficultyConfig__Group_7__1__Impl )
            // InternalMazeDsl.g:1506:2: rule__DifficultyConfig__Group_7__1__Impl
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
    // InternalMazeDsl.g:1512:1: rule__DifficultyConfig__Group_7__1__Impl : ( ( rule__DifficultyConfig__MaxThreatAssignment_7_1 ) ) ;
    public final void rule__DifficultyConfig__Group_7__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1516:1: ( ( ( rule__DifficultyConfig__MaxThreatAssignment_7_1 ) ) )
            // InternalMazeDsl.g:1517:1: ( ( rule__DifficultyConfig__MaxThreatAssignment_7_1 ) )
            {
            // InternalMazeDsl.g:1517:1: ( ( rule__DifficultyConfig__MaxThreatAssignment_7_1 ) )
            // InternalMazeDsl.g:1518:2: ( rule__DifficultyConfig__MaxThreatAssignment_7_1 )
            {
             before(grammarAccess.getDifficultyConfigAccess().getMaxThreatAssignment_7_1()); 
            // InternalMazeDsl.g:1519:2: ( rule__DifficultyConfig__MaxThreatAssignment_7_1 )
            // InternalMazeDsl.g:1519:3: rule__DifficultyConfig__MaxThreatAssignment_7_1
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
    // InternalMazeDsl.g:1528:1: rule__EnemyLimit__Group__0 : rule__EnemyLimit__Group__0__Impl rule__EnemyLimit__Group__1 ;
    public final void rule__EnemyLimit__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1532:1: ( rule__EnemyLimit__Group__0__Impl rule__EnemyLimit__Group__1 )
            // InternalMazeDsl.g:1533:2: rule__EnemyLimit__Group__0__Impl rule__EnemyLimit__Group__1
            {
            pushFollow(FOLLOW_17);
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
    // InternalMazeDsl.g:1540:1: rule__EnemyLimit__Group__0__Impl : ( 'limit' ) ;
    public final void rule__EnemyLimit__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1544:1: ( ( 'limit' ) )
            // InternalMazeDsl.g:1545:1: ( 'limit' )
            {
            // InternalMazeDsl.g:1545:1: ( 'limit' )
            // InternalMazeDsl.g:1546:2: 'limit'
            {
             before(grammarAccess.getEnemyLimitAccess().getLimitKeyword_0()); 
            match(input,40,FOLLOW_2); 
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
    // InternalMazeDsl.g:1555:1: rule__EnemyLimit__Group__1 : rule__EnemyLimit__Group__1__Impl rule__EnemyLimit__Group__2 ;
    public final void rule__EnemyLimit__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1559:1: ( rule__EnemyLimit__Group__1__Impl rule__EnemyLimit__Group__2 )
            // InternalMazeDsl.g:1560:2: rule__EnemyLimit__Group__1__Impl rule__EnemyLimit__Group__2
            {
            pushFollow(FOLLOW_18);
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
    // InternalMazeDsl.g:1567:1: rule__EnemyLimit__Group__1__Impl : ( ( rule__EnemyLimit__TypeAssignment_1 ) ) ;
    public final void rule__EnemyLimit__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1571:1: ( ( ( rule__EnemyLimit__TypeAssignment_1 ) ) )
            // InternalMazeDsl.g:1572:1: ( ( rule__EnemyLimit__TypeAssignment_1 ) )
            {
            // InternalMazeDsl.g:1572:1: ( ( rule__EnemyLimit__TypeAssignment_1 ) )
            // InternalMazeDsl.g:1573:2: ( rule__EnemyLimit__TypeAssignment_1 )
            {
             before(grammarAccess.getEnemyLimitAccess().getTypeAssignment_1()); 
            // InternalMazeDsl.g:1574:2: ( rule__EnemyLimit__TypeAssignment_1 )
            // InternalMazeDsl.g:1574:3: rule__EnemyLimit__TypeAssignment_1
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
    // InternalMazeDsl.g:1582:1: rule__EnemyLimit__Group__2 : rule__EnemyLimit__Group__2__Impl rule__EnemyLimit__Group__3 ;
    public final void rule__EnemyLimit__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1586:1: ( rule__EnemyLimit__Group__2__Impl rule__EnemyLimit__Group__3 )
            // InternalMazeDsl.g:1587:2: rule__EnemyLimit__Group__2__Impl rule__EnemyLimit__Group__3
            {
            pushFollow(FOLLOW_16);
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
    // InternalMazeDsl.g:1594:1: rule__EnemyLimit__Group__2__Impl : ( 'max' ) ;
    public final void rule__EnemyLimit__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1598:1: ( ( 'max' ) )
            // InternalMazeDsl.g:1599:1: ( 'max' )
            {
            // InternalMazeDsl.g:1599:1: ( 'max' )
            // InternalMazeDsl.g:1600:2: 'max'
            {
             before(grammarAccess.getEnemyLimitAccess().getMaxKeyword_2()); 
            match(input,41,FOLLOW_2); 
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
    // InternalMazeDsl.g:1609:1: rule__EnemyLimit__Group__3 : rule__EnemyLimit__Group__3__Impl ;
    public final void rule__EnemyLimit__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1613:1: ( rule__EnemyLimit__Group__3__Impl )
            // InternalMazeDsl.g:1614:2: rule__EnemyLimit__Group__3__Impl
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
    // InternalMazeDsl.g:1620:1: rule__EnemyLimit__Group__3__Impl : ( ( rule__EnemyLimit__MaxCountAssignment_3 ) ) ;
    public final void rule__EnemyLimit__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1624:1: ( ( ( rule__EnemyLimit__MaxCountAssignment_3 ) ) )
            // InternalMazeDsl.g:1625:1: ( ( rule__EnemyLimit__MaxCountAssignment_3 ) )
            {
            // InternalMazeDsl.g:1625:1: ( ( rule__EnemyLimit__MaxCountAssignment_3 ) )
            // InternalMazeDsl.g:1626:2: ( rule__EnemyLimit__MaxCountAssignment_3 )
            {
             before(grammarAccess.getEnemyLimitAccess().getMaxCountAssignment_3()); 
            // InternalMazeDsl.g:1627:2: ( rule__EnemyLimit__MaxCountAssignment_3 )
            // InternalMazeDsl.g:1627:3: rule__EnemyLimit__MaxCountAssignment_3
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
    // InternalMazeDsl.g:1636:1: rule__OpponentConfig__Group__0 : rule__OpponentConfig__Group__0__Impl rule__OpponentConfig__Group__1 ;
    public final void rule__OpponentConfig__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1640:1: ( rule__OpponentConfig__Group__0__Impl rule__OpponentConfig__Group__1 )
            // InternalMazeDsl.g:1641:2: rule__OpponentConfig__Group__0__Impl rule__OpponentConfig__Group__1
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
    // InternalMazeDsl.g:1648:1: rule__OpponentConfig__Group__0__Impl : ( 'opponent' ) ;
    public final void rule__OpponentConfig__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1652:1: ( ( 'opponent' ) )
            // InternalMazeDsl.g:1653:1: ( 'opponent' )
            {
            // InternalMazeDsl.g:1653:1: ( 'opponent' )
            // InternalMazeDsl.g:1654:2: 'opponent'
            {
             before(grammarAccess.getOpponentConfigAccess().getOpponentKeyword_0()); 
            match(input,42,FOLLOW_2); 
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
    // InternalMazeDsl.g:1663:1: rule__OpponentConfig__Group__1 : rule__OpponentConfig__Group__1__Impl rule__OpponentConfig__Group__2 ;
    public final void rule__OpponentConfig__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1667:1: ( rule__OpponentConfig__Group__1__Impl rule__OpponentConfig__Group__2 )
            // InternalMazeDsl.g:1668:2: rule__OpponentConfig__Group__1__Impl rule__OpponentConfig__Group__2
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
    // InternalMazeDsl.g:1675:1: rule__OpponentConfig__Group__1__Impl : ( ( rule__OpponentConfig__NameAssignment_1 ) ) ;
    public final void rule__OpponentConfig__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1679:1: ( ( ( rule__OpponentConfig__NameAssignment_1 ) ) )
            // InternalMazeDsl.g:1680:1: ( ( rule__OpponentConfig__NameAssignment_1 ) )
            {
            // InternalMazeDsl.g:1680:1: ( ( rule__OpponentConfig__NameAssignment_1 ) )
            // InternalMazeDsl.g:1681:2: ( rule__OpponentConfig__NameAssignment_1 )
            {
             before(grammarAccess.getOpponentConfigAccess().getNameAssignment_1()); 
            // InternalMazeDsl.g:1682:2: ( rule__OpponentConfig__NameAssignment_1 )
            // InternalMazeDsl.g:1682:3: rule__OpponentConfig__NameAssignment_1
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
    // InternalMazeDsl.g:1690:1: rule__OpponentConfig__Group__2 : rule__OpponentConfig__Group__2__Impl rule__OpponentConfig__Group__3 ;
    public final void rule__OpponentConfig__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1694:1: ( rule__OpponentConfig__Group__2__Impl rule__OpponentConfig__Group__3 )
            // InternalMazeDsl.g:1695:2: rule__OpponentConfig__Group__2__Impl rule__OpponentConfig__Group__3
            {
            pushFollow(FOLLOW_19);
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
    // InternalMazeDsl.g:1702:1: rule__OpponentConfig__Group__2__Impl : ( '{' ) ;
    public final void rule__OpponentConfig__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1706:1: ( ( '{' ) )
            // InternalMazeDsl.g:1707:1: ( '{' )
            {
            // InternalMazeDsl.g:1707:1: ( '{' )
            // InternalMazeDsl.g:1708:2: '{'
            {
             before(grammarAccess.getOpponentConfigAccess().getLeftCurlyBracketKeyword_2()); 
            match(input,31,FOLLOW_2); 
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
    // InternalMazeDsl.g:1717:1: rule__OpponentConfig__Group__3 : rule__OpponentConfig__Group__3__Impl rule__OpponentConfig__Group__4 ;
    public final void rule__OpponentConfig__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1721:1: ( rule__OpponentConfig__Group__3__Impl rule__OpponentConfig__Group__4 )
            // InternalMazeDsl.g:1722:2: rule__OpponentConfig__Group__3__Impl rule__OpponentConfig__Group__4
            {
            pushFollow(FOLLOW_17);
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
    // InternalMazeDsl.g:1729:1: rule__OpponentConfig__Group__3__Impl : ( 'type' ) ;
    public final void rule__OpponentConfig__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1733:1: ( ( 'type' ) )
            // InternalMazeDsl.g:1734:1: ( 'type' )
            {
            // InternalMazeDsl.g:1734:1: ( 'type' )
            // InternalMazeDsl.g:1735:2: 'type'
            {
             before(grammarAccess.getOpponentConfigAccess().getTypeKeyword_3()); 
            match(input,43,FOLLOW_2); 
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
    // InternalMazeDsl.g:1744:1: rule__OpponentConfig__Group__4 : rule__OpponentConfig__Group__4__Impl rule__OpponentConfig__Group__5 ;
    public final void rule__OpponentConfig__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1748:1: ( rule__OpponentConfig__Group__4__Impl rule__OpponentConfig__Group__5 )
            // InternalMazeDsl.g:1749:2: rule__OpponentConfig__Group__4__Impl rule__OpponentConfig__Group__5
            {
            pushFollow(FOLLOW_20);
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
    // InternalMazeDsl.g:1756:1: rule__OpponentConfig__Group__4__Impl : ( ( rule__OpponentConfig__TypeAssignment_4 ) ) ;
    public final void rule__OpponentConfig__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1760:1: ( ( ( rule__OpponentConfig__TypeAssignment_4 ) ) )
            // InternalMazeDsl.g:1761:1: ( ( rule__OpponentConfig__TypeAssignment_4 ) )
            {
            // InternalMazeDsl.g:1761:1: ( ( rule__OpponentConfig__TypeAssignment_4 ) )
            // InternalMazeDsl.g:1762:2: ( rule__OpponentConfig__TypeAssignment_4 )
            {
             before(grammarAccess.getOpponentConfigAccess().getTypeAssignment_4()); 
            // InternalMazeDsl.g:1763:2: ( rule__OpponentConfig__TypeAssignment_4 )
            // InternalMazeDsl.g:1763:3: rule__OpponentConfig__TypeAssignment_4
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
    // InternalMazeDsl.g:1771:1: rule__OpponentConfig__Group__5 : rule__OpponentConfig__Group__5__Impl rule__OpponentConfig__Group__6 ;
    public final void rule__OpponentConfig__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1775:1: ( rule__OpponentConfig__Group__5__Impl rule__OpponentConfig__Group__6 )
            // InternalMazeDsl.g:1776:2: rule__OpponentConfig__Group__5__Impl rule__OpponentConfig__Group__6
            {
            pushFollow(FOLLOW_20);
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
    // InternalMazeDsl.g:1783:1: rule__OpponentConfig__Group__5__Impl : ( ( rule__OpponentConfig__Group_5__0 )? ) ;
    public final void rule__OpponentConfig__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1787:1: ( ( ( rule__OpponentConfig__Group_5__0 )? ) )
            // InternalMazeDsl.g:1788:1: ( ( rule__OpponentConfig__Group_5__0 )? )
            {
            // InternalMazeDsl.g:1788:1: ( ( rule__OpponentConfig__Group_5__0 )? )
            // InternalMazeDsl.g:1789:2: ( rule__OpponentConfig__Group_5__0 )?
            {
             before(grammarAccess.getOpponentConfigAccess().getGroup_5()); 
            // InternalMazeDsl.g:1790:2: ( rule__OpponentConfig__Group_5__0 )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==44) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // InternalMazeDsl.g:1790:3: rule__OpponentConfig__Group_5__0
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
    // InternalMazeDsl.g:1798:1: rule__OpponentConfig__Group__6 : rule__OpponentConfig__Group__6__Impl rule__OpponentConfig__Group__7 ;
    public final void rule__OpponentConfig__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1802:1: ( rule__OpponentConfig__Group__6__Impl rule__OpponentConfig__Group__7 )
            // InternalMazeDsl.g:1803:2: rule__OpponentConfig__Group__6__Impl rule__OpponentConfig__Group__7
            {
            pushFollow(FOLLOW_20);
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
    // InternalMazeDsl.g:1810:1: rule__OpponentConfig__Group__6__Impl : ( ( rule__OpponentConfig__Group_6__0 )? ) ;
    public final void rule__OpponentConfig__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1814:1: ( ( ( rule__OpponentConfig__Group_6__0 )? ) )
            // InternalMazeDsl.g:1815:1: ( ( rule__OpponentConfig__Group_6__0 )? )
            {
            // InternalMazeDsl.g:1815:1: ( ( rule__OpponentConfig__Group_6__0 )? )
            // InternalMazeDsl.g:1816:2: ( rule__OpponentConfig__Group_6__0 )?
            {
             before(grammarAccess.getOpponentConfigAccess().getGroup_6()); 
            // InternalMazeDsl.g:1817:2: ( rule__OpponentConfig__Group_6__0 )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==45) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // InternalMazeDsl.g:1817:3: rule__OpponentConfig__Group_6__0
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
    // InternalMazeDsl.g:1825:1: rule__OpponentConfig__Group__7 : rule__OpponentConfig__Group__7__Impl rule__OpponentConfig__Group__8 ;
    public final void rule__OpponentConfig__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1829:1: ( rule__OpponentConfig__Group__7__Impl rule__OpponentConfig__Group__8 )
            // InternalMazeDsl.g:1830:2: rule__OpponentConfig__Group__7__Impl rule__OpponentConfig__Group__8
            {
            pushFollow(FOLLOW_20);
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
    // InternalMazeDsl.g:1837:1: rule__OpponentConfig__Group__7__Impl : ( ( rule__OpponentConfig__Group_7__0 )? ) ;
    public final void rule__OpponentConfig__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1841:1: ( ( ( rule__OpponentConfig__Group_7__0 )? ) )
            // InternalMazeDsl.g:1842:1: ( ( rule__OpponentConfig__Group_7__0 )? )
            {
            // InternalMazeDsl.g:1842:1: ( ( rule__OpponentConfig__Group_7__0 )? )
            // InternalMazeDsl.g:1843:2: ( rule__OpponentConfig__Group_7__0 )?
            {
             before(grammarAccess.getOpponentConfigAccess().getGroup_7()); 
            // InternalMazeDsl.g:1844:2: ( rule__OpponentConfig__Group_7__0 )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==46) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // InternalMazeDsl.g:1844:3: rule__OpponentConfig__Group_7__0
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
    // InternalMazeDsl.g:1852:1: rule__OpponentConfig__Group__8 : rule__OpponentConfig__Group__8__Impl rule__OpponentConfig__Group__9 ;
    public final void rule__OpponentConfig__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1856:1: ( rule__OpponentConfig__Group__8__Impl rule__OpponentConfig__Group__9 )
            // InternalMazeDsl.g:1857:2: rule__OpponentConfig__Group__8__Impl rule__OpponentConfig__Group__9
            {
            pushFollow(FOLLOW_20);
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
    // InternalMazeDsl.g:1864:1: rule__OpponentConfig__Group__8__Impl : ( ( rule__OpponentConfig__Group_8__0 )? ) ;
    public final void rule__OpponentConfig__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1868:1: ( ( ( rule__OpponentConfig__Group_8__0 )? ) )
            // InternalMazeDsl.g:1869:1: ( ( rule__OpponentConfig__Group_8__0 )? )
            {
            // InternalMazeDsl.g:1869:1: ( ( rule__OpponentConfig__Group_8__0 )? )
            // InternalMazeDsl.g:1870:2: ( rule__OpponentConfig__Group_8__0 )?
            {
             before(grammarAccess.getOpponentConfigAccess().getGroup_8()); 
            // InternalMazeDsl.g:1871:2: ( rule__OpponentConfig__Group_8__0 )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==47) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // InternalMazeDsl.g:1871:3: rule__OpponentConfig__Group_8__0
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
    // InternalMazeDsl.g:1879:1: rule__OpponentConfig__Group__9 : rule__OpponentConfig__Group__9__Impl rule__OpponentConfig__Group__10 ;
    public final void rule__OpponentConfig__Group__9() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1883:1: ( rule__OpponentConfig__Group__9__Impl rule__OpponentConfig__Group__10 )
            // InternalMazeDsl.g:1884:2: rule__OpponentConfig__Group__9__Impl rule__OpponentConfig__Group__10
            {
            pushFollow(FOLLOW_20);
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
    // InternalMazeDsl.g:1891:1: rule__OpponentConfig__Group__9__Impl : ( ( rule__OpponentConfig__Group_9__0 )? ) ;
    public final void rule__OpponentConfig__Group__9__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1895:1: ( ( ( rule__OpponentConfig__Group_9__0 )? ) )
            // InternalMazeDsl.g:1896:1: ( ( rule__OpponentConfig__Group_9__0 )? )
            {
            // InternalMazeDsl.g:1896:1: ( ( rule__OpponentConfig__Group_9__0 )? )
            // InternalMazeDsl.g:1897:2: ( rule__OpponentConfig__Group_9__0 )?
            {
             before(grammarAccess.getOpponentConfigAccess().getGroup_9()); 
            // InternalMazeDsl.g:1898:2: ( rule__OpponentConfig__Group_9__0 )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==48) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // InternalMazeDsl.g:1898:3: rule__OpponentConfig__Group_9__0
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
    // InternalMazeDsl.g:1906:1: rule__OpponentConfig__Group__10 : rule__OpponentConfig__Group__10__Impl rule__OpponentConfig__Group__11 ;
    public final void rule__OpponentConfig__Group__10() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1910:1: ( rule__OpponentConfig__Group__10__Impl rule__OpponentConfig__Group__11 )
            // InternalMazeDsl.g:1911:2: rule__OpponentConfig__Group__10__Impl rule__OpponentConfig__Group__11
            {
            pushFollow(FOLLOW_20);
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
    // InternalMazeDsl.g:1918:1: rule__OpponentConfig__Group__10__Impl : ( ( rule__OpponentConfig__Group_10__0 )? ) ;
    public final void rule__OpponentConfig__Group__10__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1922:1: ( ( ( rule__OpponentConfig__Group_10__0 )? ) )
            // InternalMazeDsl.g:1923:1: ( ( rule__OpponentConfig__Group_10__0 )? )
            {
            // InternalMazeDsl.g:1923:1: ( ( rule__OpponentConfig__Group_10__0 )? )
            // InternalMazeDsl.g:1924:2: ( rule__OpponentConfig__Group_10__0 )?
            {
             before(grammarAccess.getOpponentConfigAccess().getGroup_10()); 
            // InternalMazeDsl.g:1925:2: ( rule__OpponentConfig__Group_10__0 )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==49) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // InternalMazeDsl.g:1925:3: rule__OpponentConfig__Group_10__0
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
    // InternalMazeDsl.g:1933:1: rule__OpponentConfig__Group__11 : rule__OpponentConfig__Group__11__Impl rule__OpponentConfig__Group__12 ;
    public final void rule__OpponentConfig__Group__11() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1937:1: ( rule__OpponentConfig__Group__11__Impl rule__OpponentConfig__Group__12 )
            // InternalMazeDsl.g:1938:2: rule__OpponentConfig__Group__11__Impl rule__OpponentConfig__Group__12
            {
            pushFollow(FOLLOW_20);
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
    // InternalMazeDsl.g:1945:1: rule__OpponentConfig__Group__11__Impl : ( ( rule__OpponentConfig__CharacterSpecificsAssignment_11 )? ) ;
    public final void rule__OpponentConfig__Group__11__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1949:1: ( ( ( rule__OpponentConfig__CharacterSpecificsAssignment_11 )? ) )
            // InternalMazeDsl.g:1950:1: ( ( rule__OpponentConfig__CharacterSpecificsAssignment_11 )? )
            {
            // InternalMazeDsl.g:1950:1: ( ( rule__OpponentConfig__CharacterSpecificsAssignment_11 )? )
            // InternalMazeDsl.g:1951:2: ( rule__OpponentConfig__CharacterSpecificsAssignment_11 )?
            {
             before(grammarAccess.getOpponentConfigAccess().getCharacterSpecificsAssignment_11()); 
            // InternalMazeDsl.g:1952:2: ( rule__OpponentConfig__CharacterSpecificsAssignment_11 )?
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==51||LA25_0==55||LA25_0==58) ) {
                alt25=1;
            }
            switch (alt25) {
                case 1 :
                    // InternalMazeDsl.g:1952:3: rule__OpponentConfig__CharacterSpecificsAssignment_11
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
    // InternalMazeDsl.g:1960:1: rule__OpponentConfig__Group__12 : rule__OpponentConfig__Group__12__Impl rule__OpponentConfig__Group__13 ;
    public final void rule__OpponentConfig__Group__12() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1964:1: ( rule__OpponentConfig__Group__12__Impl rule__OpponentConfig__Group__13 )
            // InternalMazeDsl.g:1965:2: rule__OpponentConfig__Group__12__Impl rule__OpponentConfig__Group__13
            {
            pushFollow(FOLLOW_20);
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
    // InternalMazeDsl.g:1972:1: rule__OpponentConfig__Group__12__Impl : ( ( rule__OpponentConfig__Group_12__0 )? ) ;
    public final void rule__OpponentConfig__Group__12__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1976:1: ( ( ( rule__OpponentConfig__Group_12__0 )? ) )
            // InternalMazeDsl.g:1977:1: ( ( rule__OpponentConfig__Group_12__0 )? )
            {
            // InternalMazeDsl.g:1977:1: ( ( rule__OpponentConfig__Group_12__0 )? )
            // InternalMazeDsl.g:1978:2: ( rule__OpponentConfig__Group_12__0 )?
            {
             before(grammarAccess.getOpponentConfigAccess().getGroup_12()); 
            // InternalMazeDsl.g:1979:2: ( rule__OpponentConfig__Group_12__0 )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==22) ) {
                alt26=1;
            }
            switch (alt26) {
                case 1 :
                    // InternalMazeDsl.g:1979:3: rule__OpponentConfig__Group_12__0
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
    // InternalMazeDsl.g:1987:1: rule__OpponentConfig__Group__13 : rule__OpponentConfig__Group__13__Impl rule__OpponentConfig__Group__14 ;
    public final void rule__OpponentConfig__Group__13() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1991:1: ( rule__OpponentConfig__Group__13__Impl rule__OpponentConfig__Group__14 )
            // InternalMazeDsl.g:1992:2: rule__OpponentConfig__Group__13__Impl rule__OpponentConfig__Group__14
            {
            pushFollow(FOLLOW_20);
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
    // InternalMazeDsl.g:1999:1: rule__OpponentConfig__Group__13__Impl : ( ( rule__OpponentConfig__Group_13__0 )? ) ;
    public final void rule__OpponentConfig__Group__13__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2003:1: ( ( ( rule__OpponentConfig__Group_13__0 )? ) )
            // InternalMazeDsl.g:2004:1: ( ( rule__OpponentConfig__Group_13__0 )? )
            {
            // InternalMazeDsl.g:2004:1: ( ( rule__OpponentConfig__Group_13__0 )? )
            // InternalMazeDsl.g:2005:2: ( rule__OpponentConfig__Group_13__0 )?
            {
             before(grammarAccess.getOpponentConfigAccess().getGroup_13()); 
            // InternalMazeDsl.g:2006:2: ( rule__OpponentConfig__Group_13__0 )?
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==50) ) {
                alt27=1;
            }
            switch (alt27) {
                case 1 :
                    // InternalMazeDsl.g:2006:3: rule__OpponentConfig__Group_13__0
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
    // InternalMazeDsl.g:2014:1: rule__OpponentConfig__Group__14 : rule__OpponentConfig__Group__14__Impl ;
    public final void rule__OpponentConfig__Group__14() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2018:1: ( rule__OpponentConfig__Group__14__Impl )
            // InternalMazeDsl.g:2019:2: rule__OpponentConfig__Group__14__Impl
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
    // InternalMazeDsl.g:2025:1: rule__OpponentConfig__Group__14__Impl : ( '}' ) ;
    public final void rule__OpponentConfig__Group__14__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2029:1: ( ( '}' ) )
            // InternalMazeDsl.g:2030:1: ( '}' )
            {
            // InternalMazeDsl.g:2030:1: ( '}' )
            // InternalMazeDsl.g:2031:2: '}'
            {
             before(grammarAccess.getOpponentConfigAccess().getRightCurlyBracketKeyword_14()); 
            match(input,32,FOLLOW_2); 
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
    // InternalMazeDsl.g:2041:1: rule__OpponentConfig__Group_5__0 : rule__OpponentConfig__Group_5__0__Impl rule__OpponentConfig__Group_5__1 ;
    public final void rule__OpponentConfig__Group_5__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2045:1: ( rule__OpponentConfig__Group_5__0__Impl rule__OpponentConfig__Group_5__1 )
            // InternalMazeDsl.g:2046:2: rule__OpponentConfig__Group_5__0__Impl rule__OpponentConfig__Group_5__1
            {
            pushFollow(FOLLOW_10);
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
    // InternalMazeDsl.g:2053:1: rule__OpponentConfig__Group_5__0__Impl : ( 'displayName' ) ;
    public final void rule__OpponentConfig__Group_5__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2057:1: ( ( 'displayName' ) )
            // InternalMazeDsl.g:2058:1: ( 'displayName' )
            {
            // InternalMazeDsl.g:2058:1: ( 'displayName' )
            // InternalMazeDsl.g:2059:2: 'displayName'
            {
             before(grammarAccess.getOpponentConfigAccess().getDisplayNameKeyword_5_0()); 
            match(input,44,FOLLOW_2); 
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
    // InternalMazeDsl.g:2068:1: rule__OpponentConfig__Group_5__1 : rule__OpponentConfig__Group_5__1__Impl ;
    public final void rule__OpponentConfig__Group_5__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2072:1: ( rule__OpponentConfig__Group_5__1__Impl )
            // InternalMazeDsl.g:2073:2: rule__OpponentConfig__Group_5__1__Impl
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
    // InternalMazeDsl.g:2079:1: rule__OpponentConfig__Group_5__1__Impl : ( ( rule__OpponentConfig__DisplayNameAssignment_5_1 ) ) ;
    public final void rule__OpponentConfig__Group_5__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2083:1: ( ( ( rule__OpponentConfig__DisplayNameAssignment_5_1 ) ) )
            // InternalMazeDsl.g:2084:1: ( ( rule__OpponentConfig__DisplayNameAssignment_5_1 ) )
            {
            // InternalMazeDsl.g:2084:1: ( ( rule__OpponentConfig__DisplayNameAssignment_5_1 ) )
            // InternalMazeDsl.g:2085:2: ( rule__OpponentConfig__DisplayNameAssignment_5_1 )
            {
             before(grammarAccess.getOpponentConfigAccess().getDisplayNameAssignment_5_1()); 
            // InternalMazeDsl.g:2086:2: ( rule__OpponentConfig__DisplayNameAssignment_5_1 )
            // InternalMazeDsl.g:2086:3: rule__OpponentConfig__DisplayNameAssignment_5_1
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
    // InternalMazeDsl.g:2095:1: rule__OpponentConfig__Group_6__0 : rule__OpponentConfig__Group_6__0__Impl rule__OpponentConfig__Group_6__1 ;
    public final void rule__OpponentConfig__Group_6__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2099:1: ( rule__OpponentConfig__Group_6__0__Impl rule__OpponentConfig__Group_6__1 )
            // InternalMazeDsl.g:2100:2: rule__OpponentConfig__Group_6__0__Impl rule__OpponentConfig__Group_6__1
            {
            pushFollow(FOLLOW_16);
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
    // InternalMazeDsl.g:2107:1: rule__OpponentConfig__Group_6__0__Impl : ( 'health' ) ;
    public final void rule__OpponentConfig__Group_6__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2111:1: ( ( 'health' ) )
            // InternalMazeDsl.g:2112:1: ( 'health' )
            {
            // InternalMazeDsl.g:2112:1: ( 'health' )
            // InternalMazeDsl.g:2113:2: 'health'
            {
             before(grammarAccess.getOpponentConfigAccess().getHealthKeyword_6_0()); 
            match(input,45,FOLLOW_2); 
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
    // InternalMazeDsl.g:2122:1: rule__OpponentConfig__Group_6__1 : rule__OpponentConfig__Group_6__1__Impl ;
    public final void rule__OpponentConfig__Group_6__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2126:1: ( rule__OpponentConfig__Group_6__1__Impl )
            // InternalMazeDsl.g:2127:2: rule__OpponentConfig__Group_6__1__Impl
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
    // InternalMazeDsl.g:2133:1: rule__OpponentConfig__Group_6__1__Impl : ( ( rule__OpponentConfig__HealthAssignment_6_1 ) ) ;
    public final void rule__OpponentConfig__Group_6__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2137:1: ( ( ( rule__OpponentConfig__HealthAssignment_6_1 ) ) )
            // InternalMazeDsl.g:2138:1: ( ( rule__OpponentConfig__HealthAssignment_6_1 ) )
            {
            // InternalMazeDsl.g:2138:1: ( ( rule__OpponentConfig__HealthAssignment_6_1 ) )
            // InternalMazeDsl.g:2139:2: ( rule__OpponentConfig__HealthAssignment_6_1 )
            {
             before(grammarAccess.getOpponentConfigAccess().getHealthAssignment_6_1()); 
            // InternalMazeDsl.g:2140:2: ( rule__OpponentConfig__HealthAssignment_6_1 )
            // InternalMazeDsl.g:2140:3: rule__OpponentConfig__HealthAssignment_6_1
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
    // InternalMazeDsl.g:2149:1: rule__OpponentConfig__Group_7__0 : rule__OpponentConfig__Group_7__0__Impl rule__OpponentConfig__Group_7__1 ;
    public final void rule__OpponentConfig__Group_7__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2153:1: ( rule__OpponentConfig__Group_7__0__Impl rule__OpponentConfig__Group_7__1 )
            // InternalMazeDsl.g:2154:2: rule__OpponentConfig__Group_7__0__Impl rule__OpponentConfig__Group_7__1
            {
            pushFollow(FOLLOW_16);
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
    // InternalMazeDsl.g:2161:1: rule__OpponentConfig__Group_7__0__Impl : ( 'speed' ) ;
    public final void rule__OpponentConfig__Group_7__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2165:1: ( ( 'speed' ) )
            // InternalMazeDsl.g:2166:1: ( 'speed' )
            {
            // InternalMazeDsl.g:2166:1: ( 'speed' )
            // InternalMazeDsl.g:2167:2: 'speed'
            {
             before(grammarAccess.getOpponentConfigAccess().getSpeedKeyword_7_0()); 
            match(input,46,FOLLOW_2); 
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
    // InternalMazeDsl.g:2176:1: rule__OpponentConfig__Group_7__1 : rule__OpponentConfig__Group_7__1__Impl ;
    public final void rule__OpponentConfig__Group_7__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2180:1: ( rule__OpponentConfig__Group_7__1__Impl )
            // InternalMazeDsl.g:2181:2: rule__OpponentConfig__Group_7__1__Impl
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
    // InternalMazeDsl.g:2187:1: rule__OpponentConfig__Group_7__1__Impl : ( ( rule__OpponentConfig__SpeedAssignment_7_1 ) ) ;
    public final void rule__OpponentConfig__Group_7__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2191:1: ( ( ( rule__OpponentConfig__SpeedAssignment_7_1 ) ) )
            // InternalMazeDsl.g:2192:1: ( ( rule__OpponentConfig__SpeedAssignment_7_1 ) )
            {
            // InternalMazeDsl.g:2192:1: ( ( rule__OpponentConfig__SpeedAssignment_7_1 ) )
            // InternalMazeDsl.g:2193:2: ( rule__OpponentConfig__SpeedAssignment_7_1 )
            {
             before(grammarAccess.getOpponentConfigAccess().getSpeedAssignment_7_1()); 
            // InternalMazeDsl.g:2194:2: ( rule__OpponentConfig__SpeedAssignment_7_1 )
            // InternalMazeDsl.g:2194:3: rule__OpponentConfig__SpeedAssignment_7_1
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
    // InternalMazeDsl.g:2203:1: rule__OpponentConfig__Group_8__0 : rule__OpponentConfig__Group_8__0__Impl rule__OpponentConfig__Group_8__1 ;
    public final void rule__OpponentConfig__Group_8__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2207:1: ( rule__OpponentConfig__Group_8__0__Impl rule__OpponentConfig__Group_8__1 )
            // InternalMazeDsl.g:2208:2: rule__OpponentConfig__Group_8__0__Impl rule__OpponentConfig__Group_8__1
            {
            pushFollow(FOLLOW_16);
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
    // InternalMazeDsl.g:2215:1: rule__OpponentConfig__Group_8__0__Impl : ( 'threatLevel' ) ;
    public final void rule__OpponentConfig__Group_8__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2219:1: ( ( 'threatLevel' ) )
            // InternalMazeDsl.g:2220:1: ( 'threatLevel' )
            {
            // InternalMazeDsl.g:2220:1: ( 'threatLevel' )
            // InternalMazeDsl.g:2221:2: 'threatLevel'
            {
             before(grammarAccess.getOpponentConfigAccess().getThreatLevelKeyword_8_0()); 
            match(input,47,FOLLOW_2); 
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
    // InternalMazeDsl.g:2230:1: rule__OpponentConfig__Group_8__1 : rule__OpponentConfig__Group_8__1__Impl ;
    public final void rule__OpponentConfig__Group_8__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2234:1: ( rule__OpponentConfig__Group_8__1__Impl )
            // InternalMazeDsl.g:2235:2: rule__OpponentConfig__Group_8__1__Impl
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
    // InternalMazeDsl.g:2241:1: rule__OpponentConfig__Group_8__1__Impl : ( ( rule__OpponentConfig__ThreatLevelAssignment_8_1 ) ) ;
    public final void rule__OpponentConfig__Group_8__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2245:1: ( ( ( rule__OpponentConfig__ThreatLevelAssignment_8_1 ) ) )
            // InternalMazeDsl.g:2246:1: ( ( rule__OpponentConfig__ThreatLevelAssignment_8_1 ) )
            {
            // InternalMazeDsl.g:2246:1: ( ( rule__OpponentConfig__ThreatLevelAssignment_8_1 ) )
            // InternalMazeDsl.g:2247:2: ( rule__OpponentConfig__ThreatLevelAssignment_8_1 )
            {
             before(grammarAccess.getOpponentConfigAccess().getThreatLevelAssignment_8_1()); 
            // InternalMazeDsl.g:2248:2: ( rule__OpponentConfig__ThreatLevelAssignment_8_1 )
            // InternalMazeDsl.g:2248:3: rule__OpponentConfig__ThreatLevelAssignment_8_1
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
    // InternalMazeDsl.g:2257:1: rule__OpponentConfig__Group_9__0 : rule__OpponentConfig__Group_9__0__Impl rule__OpponentConfig__Group_9__1 ;
    public final void rule__OpponentConfig__Group_9__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2261:1: ( rule__OpponentConfig__Group_9__0__Impl rule__OpponentConfig__Group_9__1 )
            // InternalMazeDsl.g:2262:2: rule__OpponentConfig__Group_9__0__Impl rule__OpponentConfig__Group_9__1
            {
            pushFollow(FOLLOW_21);
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
    // InternalMazeDsl.g:2269:1: rule__OpponentConfig__Group_9__0__Impl : ( 'enabled' ) ;
    public final void rule__OpponentConfig__Group_9__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2273:1: ( ( 'enabled' ) )
            // InternalMazeDsl.g:2274:1: ( 'enabled' )
            {
            // InternalMazeDsl.g:2274:1: ( 'enabled' )
            // InternalMazeDsl.g:2275:2: 'enabled'
            {
             before(grammarAccess.getOpponentConfigAccess().getEnabledKeyword_9_0()); 
            match(input,48,FOLLOW_2); 
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
    // InternalMazeDsl.g:2284:1: rule__OpponentConfig__Group_9__1 : rule__OpponentConfig__Group_9__1__Impl ;
    public final void rule__OpponentConfig__Group_9__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2288:1: ( rule__OpponentConfig__Group_9__1__Impl )
            // InternalMazeDsl.g:2289:2: rule__OpponentConfig__Group_9__1__Impl
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
    // InternalMazeDsl.g:2295:1: rule__OpponentConfig__Group_9__1__Impl : ( ( rule__OpponentConfig__EnabledAssignment_9_1 ) ) ;
    public final void rule__OpponentConfig__Group_9__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2299:1: ( ( ( rule__OpponentConfig__EnabledAssignment_9_1 ) ) )
            // InternalMazeDsl.g:2300:1: ( ( rule__OpponentConfig__EnabledAssignment_9_1 ) )
            {
            // InternalMazeDsl.g:2300:1: ( ( rule__OpponentConfig__EnabledAssignment_9_1 ) )
            // InternalMazeDsl.g:2301:2: ( rule__OpponentConfig__EnabledAssignment_9_1 )
            {
             before(grammarAccess.getOpponentConfigAccess().getEnabledAssignment_9_1()); 
            // InternalMazeDsl.g:2302:2: ( rule__OpponentConfig__EnabledAssignment_9_1 )
            // InternalMazeDsl.g:2302:3: rule__OpponentConfig__EnabledAssignment_9_1
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
    // InternalMazeDsl.g:2311:1: rule__OpponentConfig__Group_10__0 : rule__OpponentConfig__Group_10__0__Impl rule__OpponentConfig__Group_10__1 ;
    public final void rule__OpponentConfig__Group_10__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2315:1: ( rule__OpponentConfig__Group_10__0__Impl rule__OpponentConfig__Group_10__1 )
            // InternalMazeDsl.g:2316:2: rule__OpponentConfig__Group_10__0__Impl rule__OpponentConfig__Group_10__1
            {
            pushFollow(FOLLOW_22);
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
    // InternalMazeDsl.g:2323:1: rule__OpponentConfig__Group_10__0__Impl : ( 'behavior' ) ;
    public final void rule__OpponentConfig__Group_10__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2327:1: ( ( 'behavior' ) )
            // InternalMazeDsl.g:2328:1: ( 'behavior' )
            {
            // InternalMazeDsl.g:2328:1: ( 'behavior' )
            // InternalMazeDsl.g:2329:2: 'behavior'
            {
             before(grammarAccess.getOpponentConfigAccess().getBehaviorKeyword_10_0()); 
            match(input,49,FOLLOW_2); 
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
    // InternalMazeDsl.g:2338:1: rule__OpponentConfig__Group_10__1 : rule__OpponentConfig__Group_10__1__Impl ;
    public final void rule__OpponentConfig__Group_10__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2342:1: ( rule__OpponentConfig__Group_10__1__Impl )
            // InternalMazeDsl.g:2343:2: rule__OpponentConfig__Group_10__1__Impl
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
    // InternalMazeDsl.g:2349:1: rule__OpponentConfig__Group_10__1__Impl : ( ( rule__OpponentConfig__BehaviorAssignment_10_1 ) ) ;
    public final void rule__OpponentConfig__Group_10__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2353:1: ( ( ( rule__OpponentConfig__BehaviorAssignment_10_1 ) ) )
            // InternalMazeDsl.g:2354:1: ( ( rule__OpponentConfig__BehaviorAssignment_10_1 ) )
            {
            // InternalMazeDsl.g:2354:1: ( ( rule__OpponentConfig__BehaviorAssignment_10_1 ) )
            // InternalMazeDsl.g:2355:2: ( rule__OpponentConfig__BehaviorAssignment_10_1 )
            {
             before(grammarAccess.getOpponentConfigAccess().getBehaviorAssignment_10_1()); 
            // InternalMazeDsl.g:2356:2: ( rule__OpponentConfig__BehaviorAssignment_10_1 )
            // InternalMazeDsl.g:2356:3: rule__OpponentConfig__BehaviorAssignment_10_1
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
    // InternalMazeDsl.g:2365:1: rule__OpponentConfig__Group_12__0 : rule__OpponentConfig__Group_12__0__Impl rule__OpponentConfig__Group_12__1 ;
    public final void rule__OpponentConfig__Group_12__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2369:1: ( rule__OpponentConfig__Group_12__0__Impl rule__OpponentConfig__Group_12__1 )
            // InternalMazeDsl.g:2370:2: rule__OpponentConfig__Group_12__0__Impl rule__OpponentConfig__Group_12__1
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
    // InternalMazeDsl.g:2377:1: rule__OpponentConfig__Group_12__0__Impl : ( 'patrol' ) ;
    public final void rule__OpponentConfig__Group_12__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2381:1: ( ( 'patrol' ) )
            // InternalMazeDsl.g:2382:1: ( 'patrol' )
            {
            // InternalMazeDsl.g:2382:1: ( 'patrol' )
            // InternalMazeDsl.g:2383:2: 'patrol'
            {
             before(grammarAccess.getOpponentConfigAccess().getPatrolKeyword_12_0()); 
            match(input,22,FOLLOW_2); 
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
    // InternalMazeDsl.g:2392:1: rule__OpponentConfig__Group_12__1 : rule__OpponentConfig__Group_12__1__Impl ;
    public final void rule__OpponentConfig__Group_12__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2396:1: ( rule__OpponentConfig__Group_12__1__Impl )
            // InternalMazeDsl.g:2397:2: rule__OpponentConfig__Group_12__1__Impl
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
    // InternalMazeDsl.g:2403:1: rule__OpponentConfig__Group_12__1__Impl : ( ( rule__OpponentConfig__PatrolRefAssignment_12_1 ) ) ;
    public final void rule__OpponentConfig__Group_12__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2407:1: ( ( ( rule__OpponentConfig__PatrolRefAssignment_12_1 ) ) )
            // InternalMazeDsl.g:2408:1: ( ( rule__OpponentConfig__PatrolRefAssignment_12_1 ) )
            {
            // InternalMazeDsl.g:2408:1: ( ( rule__OpponentConfig__PatrolRefAssignment_12_1 ) )
            // InternalMazeDsl.g:2409:2: ( rule__OpponentConfig__PatrolRefAssignment_12_1 )
            {
             before(grammarAccess.getOpponentConfigAccess().getPatrolRefAssignment_12_1()); 
            // InternalMazeDsl.g:2410:2: ( rule__OpponentConfig__PatrolRefAssignment_12_1 )
            // InternalMazeDsl.g:2410:3: rule__OpponentConfig__PatrolRefAssignment_12_1
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
    // InternalMazeDsl.g:2419:1: rule__OpponentConfig__Group_13__0 : rule__OpponentConfig__Group_13__0__Impl rule__OpponentConfig__Group_13__1 ;
    public final void rule__OpponentConfig__Group_13__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2423:1: ( rule__OpponentConfig__Group_13__0__Impl rule__OpponentConfig__Group_13__1 )
            // InternalMazeDsl.g:2424:2: rule__OpponentConfig__Group_13__0__Impl rule__OpponentConfig__Group_13__1
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
    // InternalMazeDsl.g:2431:1: rule__OpponentConfig__Group_13__0__Impl : ( 'loot' ) ;
    public final void rule__OpponentConfig__Group_13__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2435:1: ( ( 'loot' ) )
            // InternalMazeDsl.g:2436:1: ( 'loot' )
            {
            // InternalMazeDsl.g:2436:1: ( 'loot' )
            // InternalMazeDsl.g:2437:2: 'loot'
            {
             before(grammarAccess.getOpponentConfigAccess().getLootKeyword_13_0()); 
            match(input,50,FOLLOW_2); 
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
    // InternalMazeDsl.g:2446:1: rule__OpponentConfig__Group_13__1 : rule__OpponentConfig__Group_13__1__Impl ;
    public final void rule__OpponentConfig__Group_13__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2450:1: ( rule__OpponentConfig__Group_13__1__Impl )
            // InternalMazeDsl.g:2451:2: rule__OpponentConfig__Group_13__1__Impl
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
    // InternalMazeDsl.g:2457:1: rule__OpponentConfig__Group_13__1__Impl : ( ( rule__OpponentConfig__LootRefAssignment_13_1 ) ) ;
    public final void rule__OpponentConfig__Group_13__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2461:1: ( ( ( rule__OpponentConfig__LootRefAssignment_13_1 ) ) )
            // InternalMazeDsl.g:2462:1: ( ( rule__OpponentConfig__LootRefAssignment_13_1 ) )
            {
            // InternalMazeDsl.g:2462:1: ( ( rule__OpponentConfig__LootRefAssignment_13_1 ) )
            // InternalMazeDsl.g:2463:2: ( rule__OpponentConfig__LootRefAssignment_13_1 )
            {
             before(grammarAccess.getOpponentConfigAccess().getLootRefAssignment_13_1()); 
            // InternalMazeDsl.g:2464:2: ( rule__OpponentConfig__LootRefAssignment_13_1 )
            // InternalMazeDsl.g:2464:3: rule__OpponentConfig__LootRefAssignment_13_1
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
    // InternalMazeDsl.g:2473:1: rule__ZombieSpecifics__Group__0 : rule__ZombieSpecifics__Group__0__Impl rule__ZombieSpecifics__Group__1 ;
    public final void rule__ZombieSpecifics__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2477:1: ( rule__ZombieSpecifics__Group__0__Impl rule__ZombieSpecifics__Group__1 )
            // InternalMazeDsl.g:2478:2: rule__ZombieSpecifics__Group__0__Impl rule__ZombieSpecifics__Group__1
            {
            pushFollow(FOLLOW_23);
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
    // InternalMazeDsl.g:2485:1: rule__ZombieSpecifics__Group__0__Impl : ( () ) ;
    public final void rule__ZombieSpecifics__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2489:1: ( ( () ) )
            // InternalMazeDsl.g:2490:1: ( () )
            {
            // InternalMazeDsl.g:2490:1: ( () )
            // InternalMazeDsl.g:2491:2: ()
            {
             before(grammarAccess.getZombieSpecificsAccess().getZombieSpecificsAction_0()); 
            // InternalMazeDsl.g:2492:2: ()
            // InternalMazeDsl.g:2492:3: 
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
    // InternalMazeDsl.g:2500:1: rule__ZombieSpecifics__Group__1 : rule__ZombieSpecifics__Group__1__Impl rule__ZombieSpecifics__Group__2 ;
    public final void rule__ZombieSpecifics__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2504:1: ( rule__ZombieSpecifics__Group__1__Impl rule__ZombieSpecifics__Group__2 )
            // InternalMazeDsl.g:2505:2: rule__ZombieSpecifics__Group__1__Impl rule__ZombieSpecifics__Group__2
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
    // InternalMazeDsl.g:2512:1: rule__ZombieSpecifics__Group__1__Impl : ( 'zombie-stats' ) ;
    public final void rule__ZombieSpecifics__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2516:1: ( ( 'zombie-stats' ) )
            // InternalMazeDsl.g:2517:1: ( 'zombie-stats' )
            {
            // InternalMazeDsl.g:2517:1: ( 'zombie-stats' )
            // InternalMazeDsl.g:2518:2: 'zombie-stats'
            {
             before(grammarAccess.getZombieSpecificsAccess().getZombieStatsKeyword_1()); 
            match(input,51,FOLLOW_2); 
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
    // InternalMazeDsl.g:2527:1: rule__ZombieSpecifics__Group__2 : rule__ZombieSpecifics__Group__2__Impl rule__ZombieSpecifics__Group__3 ;
    public final void rule__ZombieSpecifics__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2531:1: ( rule__ZombieSpecifics__Group__2__Impl rule__ZombieSpecifics__Group__3 )
            // InternalMazeDsl.g:2532:2: rule__ZombieSpecifics__Group__2__Impl rule__ZombieSpecifics__Group__3
            {
            pushFollow(FOLLOW_24);
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
    // InternalMazeDsl.g:2539:1: rule__ZombieSpecifics__Group__2__Impl : ( '{' ) ;
    public final void rule__ZombieSpecifics__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2543:1: ( ( '{' ) )
            // InternalMazeDsl.g:2544:1: ( '{' )
            {
            // InternalMazeDsl.g:2544:1: ( '{' )
            // InternalMazeDsl.g:2545:2: '{'
            {
             before(grammarAccess.getZombieSpecificsAccess().getLeftCurlyBracketKeyword_2()); 
            match(input,31,FOLLOW_2); 
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
    // InternalMazeDsl.g:2554:1: rule__ZombieSpecifics__Group__3 : rule__ZombieSpecifics__Group__3__Impl rule__ZombieSpecifics__Group__4 ;
    public final void rule__ZombieSpecifics__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2558:1: ( rule__ZombieSpecifics__Group__3__Impl rule__ZombieSpecifics__Group__4 )
            // InternalMazeDsl.g:2559:2: rule__ZombieSpecifics__Group__3__Impl rule__ZombieSpecifics__Group__4
            {
            pushFollow(FOLLOW_24);
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
    // InternalMazeDsl.g:2566:1: rule__ZombieSpecifics__Group__3__Impl : ( ( rule__ZombieSpecifics__Group_3__0 )? ) ;
    public final void rule__ZombieSpecifics__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2570:1: ( ( ( rule__ZombieSpecifics__Group_3__0 )? ) )
            // InternalMazeDsl.g:2571:1: ( ( rule__ZombieSpecifics__Group_3__0 )? )
            {
            // InternalMazeDsl.g:2571:1: ( ( rule__ZombieSpecifics__Group_3__0 )? )
            // InternalMazeDsl.g:2572:2: ( rule__ZombieSpecifics__Group_3__0 )?
            {
             before(grammarAccess.getZombieSpecificsAccess().getGroup_3()); 
            // InternalMazeDsl.g:2573:2: ( rule__ZombieSpecifics__Group_3__0 )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==52) ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // InternalMazeDsl.g:2573:3: rule__ZombieSpecifics__Group_3__0
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
    // InternalMazeDsl.g:2581:1: rule__ZombieSpecifics__Group__4 : rule__ZombieSpecifics__Group__4__Impl rule__ZombieSpecifics__Group__5 ;
    public final void rule__ZombieSpecifics__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2585:1: ( rule__ZombieSpecifics__Group__4__Impl rule__ZombieSpecifics__Group__5 )
            // InternalMazeDsl.g:2586:2: rule__ZombieSpecifics__Group__4__Impl rule__ZombieSpecifics__Group__5
            {
            pushFollow(FOLLOW_24);
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
    // InternalMazeDsl.g:2593:1: rule__ZombieSpecifics__Group__4__Impl : ( ( rule__ZombieSpecifics__Group_4__0 )? ) ;
    public final void rule__ZombieSpecifics__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2597:1: ( ( ( rule__ZombieSpecifics__Group_4__0 )? ) )
            // InternalMazeDsl.g:2598:1: ( ( rule__ZombieSpecifics__Group_4__0 )? )
            {
            // InternalMazeDsl.g:2598:1: ( ( rule__ZombieSpecifics__Group_4__0 )? )
            // InternalMazeDsl.g:2599:2: ( rule__ZombieSpecifics__Group_4__0 )?
            {
             before(grammarAccess.getZombieSpecificsAccess().getGroup_4()); 
            // InternalMazeDsl.g:2600:2: ( rule__ZombieSpecifics__Group_4__0 )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==53) ) {
                alt29=1;
            }
            switch (alt29) {
                case 1 :
                    // InternalMazeDsl.g:2600:3: rule__ZombieSpecifics__Group_4__0
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
    // InternalMazeDsl.g:2608:1: rule__ZombieSpecifics__Group__5 : rule__ZombieSpecifics__Group__5__Impl rule__ZombieSpecifics__Group__6 ;
    public final void rule__ZombieSpecifics__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2612:1: ( rule__ZombieSpecifics__Group__5__Impl rule__ZombieSpecifics__Group__6 )
            // InternalMazeDsl.g:2613:2: rule__ZombieSpecifics__Group__5__Impl rule__ZombieSpecifics__Group__6
            {
            pushFollow(FOLLOW_24);
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
    // InternalMazeDsl.g:2620:1: rule__ZombieSpecifics__Group__5__Impl : ( ( rule__ZombieSpecifics__Group_5__0 )? ) ;
    public final void rule__ZombieSpecifics__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2624:1: ( ( ( rule__ZombieSpecifics__Group_5__0 )? ) )
            // InternalMazeDsl.g:2625:1: ( ( rule__ZombieSpecifics__Group_5__0 )? )
            {
            // InternalMazeDsl.g:2625:1: ( ( rule__ZombieSpecifics__Group_5__0 )? )
            // InternalMazeDsl.g:2626:2: ( rule__ZombieSpecifics__Group_5__0 )?
            {
             before(grammarAccess.getZombieSpecificsAccess().getGroup_5()); 
            // InternalMazeDsl.g:2627:2: ( rule__ZombieSpecifics__Group_5__0 )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==54) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // InternalMazeDsl.g:2627:3: rule__ZombieSpecifics__Group_5__0
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
    // InternalMazeDsl.g:2635:1: rule__ZombieSpecifics__Group__6 : rule__ZombieSpecifics__Group__6__Impl ;
    public final void rule__ZombieSpecifics__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2639:1: ( rule__ZombieSpecifics__Group__6__Impl )
            // InternalMazeDsl.g:2640:2: rule__ZombieSpecifics__Group__6__Impl
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
    // InternalMazeDsl.g:2646:1: rule__ZombieSpecifics__Group__6__Impl : ( '}' ) ;
    public final void rule__ZombieSpecifics__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2650:1: ( ( '}' ) )
            // InternalMazeDsl.g:2651:1: ( '}' )
            {
            // InternalMazeDsl.g:2651:1: ( '}' )
            // InternalMazeDsl.g:2652:2: '}'
            {
             before(grammarAccess.getZombieSpecificsAccess().getRightCurlyBracketKeyword_6()); 
            match(input,32,FOLLOW_2); 
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
    // InternalMazeDsl.g:2662:1: rule__ZombieSpecifics__Group_3__0 : rule__ZombieSpecifics__Group_3__0__Impl rule__ZombieSpecifics__Group_3__1 ;
    public final void rule__ZombieSpecifics__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2666:1: ( rule__ZombieSpecifics__Group_3__0__Impl rule__ZombieSpecifics__Group_3__1 )
            // InternalMazeDsl.g:2667:2: rule__ZombieSpecifics__Group_3__0__Impl rule__ZombieSpecifics__Group_3__1
            {
            pushFollow(FOLLOW_16);
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
    // InternalMazeDsl.g:2674:1: rule__ZombieSpecifics__Group_3__0__Impl : ( 'attackDamage' ) ;
    public final void rule__ZombieSpecifics__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2678:1: ( ( 'attackDamage' ) )
            // InternalMazeDsl.g:2679:1: ( 'attackDamage' )
            {
            // InternalMazeDsl.g:2679:1: ( 'attackDamage' )
            // InternalMazeDsl.g:2680:2: 'attackDamage'
            {
             before(grammarAccess.getZombieSpecificsAccess().getAttackDamageKeyword_3_0()); 
            match(input,52,FOLLOW_2); 
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
    // InternalMazeDsl.g:2689:1: rule__ZombieSpecifics__Group_3__1 : rule__ZombieSpecifics__Group_3__1__Impl ;
    public final void rule__ZombieSpecifics__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2693:1: ( rule__ZombieSpecifics__Group_3__1__Impl )
            // InternalMazeDsl.g:2694:2: rule__ZombieSpecifics__Group_3__1__Impl
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
    // InternalMazeDsl.g:2700:1: rule__ZombieSpecifics__Group_3__1__Impl : ( ( rule__ZombieSpecifics__AttackDamageAssignment_3_1 ) ) ;
    public final void rule__ZombieSpecifics__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2704:1: ( ( ( rule__ZombieSpecifics__AttackDamageAssignment_3_1 ) ) )
            // InternalMazeDsl.g:2705:1: ( ( rule__ZombieSpecifics__AttackDamageAssignment_3_1 ) )
            {
            // InternalMazeDsl.g:2705:1: ( ( rule__ZombieSpecifics__AttackDamageAssignment_3_1 ) )
            // InternalMazeDsl.g:2706:2: ( rule__ZombieSpecifics__AttackDamageAssignment_3_1 )
            {
             before(grammarAccess.getZombieSpecificsAccess().getAttackDamageAssignment_3_1()); 
            // InternalMazeDsl.g:2707:2: ( rule__ZombieSpecifics__AttackDamageAssignment_3_1 )
            // InternalMazeDsl.g:2707:3: rule__ZombieSpecifics__AttackDamageAssignment_3_1
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
    // InternalMazeDsl.g:2716:1: rule__ZombieSpecifics__Group_4__0 : rule__ZombieSpecifics__Group_4__0__Impl rule__ZombieSpecifics__Group_4__1 ;
    public final void rule__ZombieSpecifics__Group_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2720:1: ( rule__ZombieSpecifics__Group_4__0__Impl rule__ZombieSpecifics__Group_4__1 )
            // InternalMazeDsl.g:2721:2: rule__ZombieSpecifics__Group_4__0__Impl rule__ZombieSpecifics__Group_4__1
            {
            pushFollow(FOLLOW_16);
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
    // InternalMazeDsl.g:2728:1: rule__ZombieSpecifics__Group_4__0__Impl : ( 'infectionLevel' ) ;
    public final void rule__ZombieSpecifics__Group_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2732:1: ( ( 'infectionLevel' ) )
            // InternalMazeDsl.g:2733:1: ( 'infectionLevel' )
            {
            // InternalMazeDsl.g:2733:1: ( 'infectionLevel' )
            // InternalMazeDsl.g:2734:2: 'infectionLevel'
            {
             before(grammarAccess.getZombieSpecificsAccess().getInfectionLevelKeyword_4_0()); 
            match(input,53,FOLLOW_2); 
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
    // InternalMazeDsl.g:2743:1: rule__ZombieSpecifics__Group_4__1 : rule__ZombieSpecifics__Group_4__1__Impl ;
    public final void rule__ZombieSpecifics__Group_4__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2747:1: ( rule__ZombieSpecifics__Group_4__1__Impl )
            // InternalMazeDsl.g:2748:2: rule__ZombieSpecifics__Group_4__1__Impl
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
    // InternalMazeDsl.g:2754:1: rule__ZombieSpecifics__Group_4__1__Impl : ( ( rule__ZombieSpecifics__InfectionLevelAssignment_4_1 ) ) ;
    public final void rule__ZombieSpecifics__Group_4__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2758:1: ( ( ( rule__ZombieSpecifics__InfectionLevelAssignment_4_1 ) ) )
            // InternalMazeDsl.g:2759:1: ( ( rule__ZombieSpecifics__InfectionLevelAssignment_4_1 ) )
            {
            // InternalMazeDsl.g:2759:1: ( ( rule__ZombieSpecifics__InfectionLevelAssignment_4_1 ) )
            // InternalMazeDsl.g:2760:2: ( rule__ZombieSpecifics__InfectionLevelAssignment_4_1 )
            {
             before(grammarAccess.getZombieSpecificsAccess().getInfectionLevelAssignment_4_1()); 
            // InternalMazeDsl.g:2761:2: ( rule__ZombieSpecifics__InfectionLevelAssignment_4_1 )
            // InternalMazeDsl.g:2761:3: rule__ZombieSpecifics__InfectionLevelAssignment_4_1
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
    // InternalMazeDsl.g:2770:1: rule__ZombieSpecifics__Group_5__0 : rule__ZombieSpecifics__Group_5__0__Impl rule__ZombieSpecifics__Group_5__1 ;
    public final void rule__ZombieSpecifics__Group_5__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2774:1: ( rule__ZombieSpecifics__Group_5__0__Impl rule__ZombieSpecifics__Group_5__1 )
            // InternalMazeDsl.g:2775:2: rule__ZombieSpecifics__Group_5__0__Impl rule__ZombieSpecifics__Group_5__1
            {
            pushFollow(FOLLOW_16);
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
    // InternalMazeDsl.g:2782:1: rule__ZombieSpecifics__Group_5__0__Impl : ( 'resurrectionTime' ) ;
    public final void rule__ZombieSpecifics__Group_5__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2786:1: ( ( 'resurrectionTime' ) )
            // InternalMazeDsl.g:2787:1: ( 'resurrectionTime' )
            {
            // InternalMazeDsl.g:2787:1: ( 'resurrectionTime' )
            // InternalMazeDsl.g:2788:2: 'resurrectionTime'
            {
             before(grammarAccess.getZombieSpecificsAccess().getResurrectionTimeKeyword_5_0()); 
            match(input,54,FOLLOW_2); 
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
    // InternalMazeDsl.g:2797:1: rule__ZombieSpecifics__Group_5__1 : rule__ZombieSpecifics__Group_5__1__Impl ;
    public final void rule__ZombieSpecifics__Group_5__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2801:1: ( rule__ZombieSpecifics__Group_5__1__Impl )
            // InternalMazeDsl.g:2802:2: rule__ZombieSpecifics__Group_5__1__Impl
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
    // InternalMazeDsl.g:2808:1: rule__ZombieSpecifics__Group_5__1__Impl : ( ( rule__ZombieSpecifics__ResurrectionTimeAssignment_5_1 ) ) ;
    public final void rule__ZombieSpecifics__Group_5__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2812:1: ( ( ( rule__ZombieSpecifics__ResurrectionTimeAssignment_5_1 ) ) )
            // InternalMazeDsl.g:2813:1: ( ( rule__ZombieSpecifics__ResurrectionTimeAssignment_5_1 ) )
            {
            // InternalMazeDsl.g:2813:1: ( ( rule__ZombieSpecifics__ResurrectionTimeAssignment_5_1 ) )
            // InternalMazeDsl.g:2814:2: ( rule__ZombieSpecifics__ResurrectionTimeAssignment_5_1 )
            {
             before(grammarAccess.getZombieSpecificsAccess().getResurrectionTimeAssignment_5_1()); 
            // InternalMazeDsl.g:2815:2: ( rule__ZombieSpecifics__ResurrectionTimeAssignment_5_1 )
            // InternalMazeDsl.g:2815:3: rule__ZombieSpecifics__ResurrectionTimeAssignment_5_1
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
    // InternalMazeDsl.g:2824:1: rule__GhostSpecifics__Group__0 : rule__GhostSpecifics__Group__0__Impl rule__GhostSpecifics__Group__1 ;
    public final void rule__GhostSpecifics__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2828:1: ( rule__GhostSpecifics__Group__0__Impl rule__GhostSpecifics__Group__1 )
            // InternalMazeDsl.g:2829:2: rule__GhostSpecifics__Group__0__Impl rule__GhostSpecifics__Group__1
            {
            pushFollow(FOLLOW_25);
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
    // InternalMazeDsl.g:2836:1: rule__GhostSpecifics__Group__0__Impl : ( () ) ;
    public final void rule__GhostSpecifics__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2840:1: ( ( () ) )
            // InternalMazeDsl.g:2841:1: ( () )
            {
            // InternalMazeDsl.g:2841:1: ( () )
            // InternalMazeDsl.g:2842:2: ()
            {
             before(grammarAccess.getGhostSpecificsAccess().getGhostSpecificsAction_0()); 
            // InternalMazeDsl.g:2843:2: ()
            // InternalMazeDsl.g:2843:3: 
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
    // InternalMazeDsl.g:2851:1: rule__GhostSpecifics__Group__1 : rule__GhostSpecifics__Group__1__Impl rule__GhostSpecifics__Group__2 ;
    public final void rule__GhostSpecifics__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2855:1: ( rule__GhostSpecifics__Group__1__Impl rule__GhostSpecifics__Group__2 )
            // InternalMazeDsl.g:2856:2: rule__GhostSpecifics__Group__1__Impl rule__GhostSpecifics__Group__2
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
    // InternalMazeDsl.g:2863:1: rule__GhostSpecifics__Group__1__Impl : ( 'ghost-stats' ) ;
    public final void rule__GhostSpecifics__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2867:1: ( ( 'ghost-stats' ) )
            // InternalMazeDsl.g:2868:1: ( 'ghost-stats' )
            {
            // InternalMazeDsl.g:2868:1: ( 'ghost-stats' )
            // InternalMazeDsl.g:2869:2: 'ghost-stats'
            {
             before(grammarAccess.getGhostSpecificsAccess().getGhostStatsKeyword_1()); 
            match(input,55,FOLLOW_2); 
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
    // InternalMazeDsl.g:2878:1: rule__GhostSpecifics__Group__2 : rule__GhostSpecifics__Group__2__Impl rule__GhostSpecifics__Group__3 ;
    public final void rule__GhostSpecifics__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2882:1: ( rule__GhostSpecifics__Group__2__Impl rule__GhostSpecifics__Group__3 )
            // InternalMazeDsl.g:2883:2: rule__GhostSpecifics__Group__2__Impl rule__GhostSpecifics__Group__3
            {
            pushFollow(FOLLOW_26);
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
    // InternalMazeDsl.g:2890:1: rule__GhostSpecifics__Group__2__Impl : ( '{' ) ;
    public final void rule__GhostSpecifics__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2894:1: ( ( '{' ) )
            // InternalMazeDsl.g:2895:1: ( '{' )
            {
            // InternalMazeDsl.g:2895:1: ( '{' )
            // InternalMazeDsl.g:2896:2: '{'
            {
             before(grammarAccess.getGhostSpecificsAccess().getLeftCurlyBracketKeyword_2()); 
            match(input,31,FOLLOW_2); 
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
    // InternalMazeDsl.g:2905:1: rule__GhostSpecifics__Group__3 : rule__GhostSpecifics__Group__3__Impl rule__GhostSpecifics__Group__4 ;
    public final void rule__GhostSpecifics__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2909:1: ( rule__GhostSpecifics__Group__3__Impl rule__GhostSpecifics__Group__4 )
            // InternalMazeDsl.g:2910:2: rule__GhostSpecifics__Group__3__Impl rule__GhostSpecifics__Group__4
            {
            pushFollow(FOLLOW_26);
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
    // InternalMazeDsl.g:2917:1: rule__GhostSpecifics__Group__3__Impl : ( ( rule__GhostSpecifics__Group_3__0 )? ) ;
    public final void rule__GhostSpecifics__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2921:1: ( ( ( rule__GhostSpecifics__Group_3__0 )? ) )
            // InternalMazeDsl.g:2922:1: ( ( rule__GhostSpecifics__Group_3__0 )? )
            {
            // InternalMazeDsl.g:2922:1: ( ( rule__GhostSpecifics__Group_3__0 )? )
            // InternalMazeDsl.g:2923:2: ( rule__GhostSpecifics__Group_3__0 )?
            {
             before(grammarAccess.getGhostSpecificsAccess().getGroup_3()); 
            // InternalMazeDsl.g:2924:2: ( rule__GhostSpecifics__Group_3__0 )?
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==52) ) {
                alt31=1;
            }
            switch (alt31) {
                case 1 :
                    // InternalMazeDsl.g:2924:3: rule__GhostSpecifics__Group_3__0
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
    // InternalMazeDsl.g:2932:1: rule__GhostSpecifics__Group__4 : rule__GhostSpecifics__Group__4__Impl rule__GhostSpecifics__Group__5 ;
    public final void rule__GhostSpecifics__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2936:1: ( rule__GhostSpecifics__Group__4__Impl rule__GhostSpecifics__Group__5 )
            // InternalMazeDsl.g:2937:2: rule__GhostSpecifics__Group__4__Impl rule__GhostSpecifics__Group__5
            {
            pushFollow(FOLLOW_26);
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
    // InternalMazeDsl.g:2944:1: rule__GhostSpecifics__Group__4__Impl : ( ( rule__GhostSpecifics__Group_4__0 )? ) ;
    public final void rule__GhostSpecifics__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2948:1: ( ( ( rule__GhostSpecifics__Group_4__0 )? ) )
            // InternalMazeDsl.g:2949:1: ( ( rule__GhostSpecifics__Group_4__0 )? )
            {
            // InternalMazeDsl.g:2949:1: ( ( rule__GhostSpecifics__Group_4__0 )? )
            // InternalMazeDsl.g:2950:2: ( rule__GhostSpecifics__Group_4__0 )?
            {
             before(grammarAccess.getGhostSpecificsAccess().getGroup_4()); 
            // InternalMazeDsl.g:2951:2: ( rule__GhostSpecifics__Group_4__0 )?
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==56) ) {
                alt32=1;
            }
            switch (alt32) {
                case 1 :
                    // InternalMazeDsl.g:2951:3: rule__GhostSpecifics__Group_4__0
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
    // InternalMazeDsl.g:2959:1: rule__GhostSpecifics__Group__5 : rule__GhostSpecifics__Group__5__Impl rule__GhostSpecifics__Group__6 ;
    public final void rule__GhostSpecifics__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2963:1: ( rule__GhostSpecifics__Group__5__Impl rule__GhostSpecifics__Group__6 )
            // InternalMazeDsl.g:2964:2: rule__GhostSpecifics__Group__5__Impl rule__GhostSpecifics__Group__6
            {
            pushFollow(FOLLOW_26);
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
    // InternalMazeDsl.g:2971:1: rule__GhostSpecifics__Group__5__Impl : ( ( rule__GhostSpecifics__Group_5__0 )? ) ;
    public final void rule__GhostSpecifics__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2975:1: ( ( ( rule__GhostSpecifics__Group_5__0 )? ) )
            // InternalMazeDsl.g:2976:1: ( ( rule__GhostSpecifics__Group_5__0 )? )
            {
            // InternalMazeDsl.g:2976:1: ( ( rule__GhostSpecifics__Group_5__0 )? )
            // InternalMazeDsl.g:2977:2: ( rule__GhostSpecifics__Group_5__0 )?
            {
             before(grammarAccess.getGhostSpecificsAccess().getGroup_5()); 
            // InternalMazeDsl.g:2978:2: ( rule__GhostSpecifics__Group_5__0 )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==57) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // InternalMazeDsl.g:2978:3: rule__GhostSpecifics__Group_5__0
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
    // InternalMazeDsl.g:2986:1: rule__GhostSpecifics__Group__6 : rule__GhostSpecifics__Group__6__Impl ;
    public final void rule__GhostSpecifics__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2990:1: ( rule__GhostSpecifics__Group__6__Impl )
            // InternalMazeDsl.g:2991:2: rule__GhostSpecifics__Group__6__Impl
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
    // InternalMazeDsl.g:2997:1: rule__GhostSpecifics__Group__6__Impl : ( '}' ) ;
    public final void rule__GhostSpecifics__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3001:1: ( ( '}' ) )
            // InternalMazeDsl.g:3002:1: ( '}' )
            {
            // InternalMazeDsl.g:3002:1: ( '}' )
            // InternalMazeDsl.g:3003:2: '}'
            {
             before(grammarAccess.getGhostSpecificsAccess().getRightCurlyBracketKeyword_6()); 
            match(input,32,FOLLOW_2); 
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
    // InternalMazeDsl.g:3013:1: rule__GhostSpecifics__Group_3__0 : rule__GhostSpecifics__Group_3__0__Impl rule__GhostSpecifics__Group_3__1 ;
    public final void rule__GhostSpecifics__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3017:1: ( rule__GhostSpecifics__Group_3__0__Impl rule__GhostSpecifics__Group_3__1 )
            // InternalMazeDsl.g:3018:2: rule__GhostSpecifics__Group_3__0__Impl rule__GhostSpecifics__Group_3__1
            {
            pushFollow(FOLLOW_16);
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
    // InternalMazeDsl.g:3025:1: rule__GhostSpecifics__Group_3__0__Impl : ( 'attackDamage' ) ;
    public final void rule__GhostSpecifics__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3029:1: ( ( 'attackDamage' ) )
            // InternalMazeDsl.g:3030:1: ( 'attackDamage' )
            {
            // InternalMazeDsl.g:3030:1: ( 'attackDamage' )
            // InternalMazeDsl.g:3031:2: 'attackDamage'
            {
             before(grammarAccess.getGhostSpecificsAccess().getAttackDamageKeyword_3_0()); 
            match(input,52,FOLLOW_2); 
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
    // InternalMazeDsl.g:3040:1: rule__GhostSpecifics__Group_3__1 : rule__GhostSpecifics__Group_3__1__Impl ;
    public final void rule__GhostSpecifics__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3044:1: ( rule__GhostSpecifics__Group_3__1__Impl )
            // InternalMazeDsl.g:3045:2: rule__GhostSpecifics__Group_3__1__Impl
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
    // InternalMazeDsl.g:3051:1: rule__GhostSpecifics__Group_3__1__Impl : ( ( rule__GhostSpecifics__AttackDamageAssignment_3_1 ) ) ;
    public final void rule__GhostSpecifics__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3055:1: ( ( ( rule__GhostSpecifics__AttackDamageAssignment_3_1 ) ) )
            // InternalMazeDsl.g:3056:1: ( ( rule__GhostSpecifics__AttackDamageAssignment_3_1 ) )
            {
            // InternalMazeDsl.g:3056:1: ( ( rule__GhostSpecifics__AttackDamageAssignment_3_1 ) )
            // InternalMazeDsl.g:3057:2: ( rule__GhostSpecifics__AttackDamageAssignment_3_1 )
            {
             before(grammarAccess.getGhostSpecificsAccess().getAttackDamageAssignment_3_1()); 
            // InternalMazeDsl.g:3058:2: ( rule__GhostSpecifics__AttackDamageAssignment_3_1 )
            // InternalMazeDsl.g:3058:3: rule__GhostSpecifics__AttackDamageAssignment_3_1
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
    // InternalMazeDsl.g:3067:1: rule__GhostSpecifics__Group_4__0 : rule__GhostSpecifics__Group_4__0__Impl rule__GhostSpecifics__Group_4__1 ;
    public final void rule__GhostSpecifics__Group_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3071:1: ( rule__GhostSpecifics__Group_4__0__Impl rule__GhostSpecifics__Group_4__1 )
            // InternalMazeDsl.g:3072:2: rule__GhostSpecifics__Group_4__0__Impl rule__GhostSpecifics__Group_4__1
            {
            pushFollow(FOLLOW_16);
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
    // InternalMazeDsl.g:3079:1: rule__GhostSpecifics__Group_4__0__Impl : ( 'visibilityLevel' ) ;
    public final void rule__GhostSpecifics__Group_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3083:1: ( ( 'visibilityLevel' ) )
            // InternalMazeDsl.g:3084:1: ( 'visibilityLevel' )
            {
            // InternalMazeDsl.g:3084:1: ( 'visibilityLevel' )
            // InternalMazeDsl.g:3085:2: 'visibilityLevel'
            {
             before(grammarAccess.getGhostSpecificsAccess().getVisibilityLevelKeyword_4_0()); 
            match(input,56,FOLLOW_2); 
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
    // InternalMazeDsl.g:3094:1: rule__GhostSpecifics__Group_4__1 : rule__GhostSpecifics__Group_4__1__Impl ;
    public final void rule__GhostSpecifics__Group_4__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3098:1: ( rule__GhostSpecifics__Group_4__1__Impl )
            // InternalMazeDsl.g:3099:2: rule__GhostSpecifics__Group_4__1__Impl
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
    // InternalMazeDsl.g:3105:1: rule__GhostSpecifics__Group_4__1__Impl : ( ( rule__GhostSpecifics__VisibilityLevelAssignment_4_1 ) ) ;
    public final void rule__GhostSpecifics__Group_4__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3109:1: ( ( ( rule__GhostSpecifics__VisibilityLevelAssignment_4_1 ) ) )
            // InternalMazeDsl.g:3110:1: ( ( rule__GhostSpecifics__VisibilityLevelAssignment_4_1 ) )
            {
            // InternalMazeDsl.g:3110:1: ( ( rule__GhostSpecifics__VisibilityLevelAssignment_4_1 ) )
            // InternalMazeDsl.g:3111:2: ( rule__GhostSpecifics__VisibilityLevelAssignment_4_1 )
            {
             before(grammarAccess.getGhostSpecificsAccess().getVisibilityLevelAssignment_4_1()); 
            // InternalMazeDsl.g:3112:2: ( rule__GhostSpecifics__VisibilityLevelAssignment_4_1 )
            // InternalMazeDsl.g:3112:3: rule__GhostSpecifics__VisibilityLevelAssignment_4_1
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
    // InternalMazeDsl.g:3121:1: rule__GhostSpecifics__Group_5__0 : rule__GhostSpecifics__Group_5__0__Impl rule__GhostSpecifics__Group_5__1 ;
    public final void rule__GhostSpecifics__Group_5__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3125:1: ( rule__GhostSpecifics__Group_5__0__Impl rule__GhostSpecifics__Group_5__1 )
            // InternalMazeDsl.g:3126:2: rule__GhostSpecifics__Group_5__0__Impl rule__GhostSpecifics__Group_5__1
            {
            pushFollow(FOLLOW_16);
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
    // InternalMazeDsl.g:3133:1: rule__GhostSpecifics__Group_5__0__Impl : ( 'nonTangibilityEnergy' ) ;
    public final void rule__GhostSpecifics__Group_5__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3137:1: ( ( 'nonTangibilityEnergy' ) )
            // InternalMazeDsl.g:3138:1: ( 'nonTangibilityEnergy' )
            {
            // InternalMazeDsl.g:3138:1: ( 'nonTangibilityEnergy' )
            // InternalMazeDsl.g:3139:2: 'nonTangibilityEnergy'
            {
             before(grammarAccess.getGhostSpecificsAccess().getNonTangibilityEnergyKeyword_5_0()); 
            match(input,57,FOLLOW_2); 
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
    // InternalMazeDsl.g:3148:1: rule__GhostSpecifics__Group_5__1 : rule__GhostSpecifics__Group_5__1__Impl ;
    public final void rule__GhostSpecifics__Group_5__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3152:1: ( rule__GhostSpecifics__Group_5__1__Impl )
            // InternalMazeDsl.g:3153:2: rule__GhostSpecifics__Group_5__1__Impl
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
    // InternalMazeDsl.g:3159:1: rule__GhostSpecifics__Group_5__1__Impl : ( ( rule__GhostSpecifics__NonTangibilityEnergyAssignment_5_1 ) ) ;
    public final void rule__GhostSpecifics__Group_5__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3163:1: ( ( ( rule__GhostSpecifics__NonTangibilityEnergyAssignment_5_1 ) ) )
            // InternalMazeDsl.g:3164:1: ( ( rule__GhostSpecifics__NonTangibilityEnergyAssignment_5_1 ) )
            {
            // InternalMazeDsl.g:3164:1: ( ( rule__GhostSpecifics__NonTangibilityEnergyAssignment_5_1 ) )
            // InternalMazeDsl.g:3165:2: ( rule__GhostSpecifics__NonTangibilityEnergyAssignment_5_1 )
            {
             before(grammarAccess.getGhostSpecificsAccess().getNonTangibilityEnergyAssignment_5_1()); 
            // InternalMazeDsl.g:3166:2: ( rule__GhostSpecifics__NonTangibilityEnergyAssignment_5_1 )
            // InternalMazeDsl.g:3166:3: rule__GhostSpecifics__NonTangibilityEnergyAssignment_5_1
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
    // InternalMazeDsl.g:3175:1: rule__RangedSpecifics__Group__0 : rule__RangedSpecifics__Group__0__Impl rule__RangedSpecifics__Group__1 ;
    public final void rule__RangedSpecifics__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3179:1: ( rule__RangedSpecifics__Group__0__Impl rule__RangedSpecifics__Group__1 )
            // InternalMazeDsl.g:3180:2: rule__RangedSpecifics__Group__0__Impl rule__RangedSpecifics__Group__1
            {
            pushFollow(FOLLOW_27);
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
    // InternalMazeDsl.g:3187:1: rule__RangedSpecifics__Group__0__Impl : ( () ) ;
    public final void rule__RangedSpecifics__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3191:1: ( ( () ) )
            // InternalMazeDsl.g:3192:1: ( () )
            {
            // InternalMazeDsl.g:3192:1: ( () )
            // InternalMazeDsl.g:3193:2: ()
            {
             before(grammarAccess.getRangedSpecificsAccess().getRangedSpecificsAction_0()); 
            // InternalMazeDsl.g:3194:2: ()
            // InternalMazeDsl.g:3194:3: 
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
    // InternalMazeDsl.g:3202:1: rule__RangedSpecifics__Group__1 : rule__RangedSpecifics__Group__1__Impl rule__RangedSpecifics__Group__2 ;
    public final void rule__RangedSpecifics__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3206:1: ( rule__RangedSpecifics__Group__1__Impl rule__RangedSpecifics__Group__2 )
            // InternalMazeDsl.g:3207:2: rule__RangedSpecifics__Group__1__Impl rule__RangedSpecifics__Group__2
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
    // InternalMazeDsl.g:3214:1: rule__RangedSpecifics__Group__1__Impl : ( 'ranged-stats' ) ;
    public final void rule__RangedSpecifics__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3218:1: ( ( 'ranged-stats' ) )
            // InternalMazeDsl.g:3219:1: ( 'ranged-stats' )
            {
            // InternalMazeDsl.g:3219:1: ( 'ranged-stats' )
            // InternalMazeDsl.g:3220:2: 'ranged-stats'
            {
             before(grammarAccess.getRangedSpecificsAccess().getRangedStatsKeyword_1()); 
            match(input,58,FOLLOW_2); 
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
    // InternalMazeDsl.g:3229:1: rule__RangedSpecifics__Group__2 : rule__RangedSpecifics__Group__2__Impl rule__RangedSpecifics__Group__3 ;
    public final void rule__RangedSpecifics__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3233:1: ( rule__RangedSpecifics__Group__2__Impl rule__RangedSpecifics__Group__3 )
            // InternalMazeDsl.g:3234:2: rule__RangedSpecifics__Group__2__Impl rule__RangedSpecifics__Group__3
            {
            pushFollow(FOLLOW_28);
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
    // InternalMazeDsl.g:3241:1: rule__RangedSpecifics__Group__2__Impl : ( '{' ) ;
    public final void rule__RangedSpecifics__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3245:1: ( ( '{' ) )
            // InternalMazeDsl.g:3246:1: ( '{' )
            {
            // InternalMazeDsl.g:3246:1: ( '{' )
            // InternalMazeDsl.g:3247:2: '{'
            {
             before(grammarAccess.getRangedSpecificsAccess().getLeftCurlyBracketKeyword_2()); 
            match(input,31,FOLLOW_2); 
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
    // InternalMazeDsl.g:3256:1: rule__RangedSpecifics__Group__3 : rule__RangedSpecifics__Group__3__Impl rule__RangedSpecifics__Group__4 ;
    public final void rule__RangedSpecifics__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3260:1: ( rule__RangedSpecifics__Group__3__Impl rule__RangedSpecifics__Group__4 )
            // InternalMazeDsl.g:3261:2: rule__RangedSpecifics__Group__3__Impl rule__RangedSpecifics__Group__4
            {
            pushFollow(FOLLOW_28);
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
    // InternalMazeDsl.g:3268:1: rule__RangedSpecifics__Group__3__Impl : ( ( rule__RangedSpecifics__Group_3__0 )? ) ;
    public final void rule__RangedSpecifics__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3272:1: ( ( ( rule__RangedSpecifics__Group_3__0 )? ) )
            // InternalMazeDsl.g:3273:1: ( ( rule__RangedSpecifics__Group_3__0 )? )
            {
            // InternalMazeDsl.g:3273:1: ( ( rule__RangedSpecifics__Group_3__0 )? )
            // InternalMazeDsl.g:3274:2: ( rule__RangedSpecifics__Group_3__0 )?
            {
             before(grammarAccess.getRangedSpecificsAccess().getGroup_3()); 
            // InternalMazeDsl.g:3275:2: ( rule__RangedSpecifics__Group_3__0 )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==59) ) {
                alt34=1;
            }
            switch (alt34) {
                case 1 :
                    // InternalMazeDsl.g:3275:3: rule__RangedSpecifics__Group_3__0
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
    // InternalMazeDsl.g:3283:1: rule__RangedSpecifics__Group__4 : rule__RangedSpecifics__Group__4__Impl rule__RangedSpecifics__Group__5 ;
    public final void rule__RangedSpecifics__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3287:1: ( rule__RangedSpecifics__Group__4__Impl rule__RangedSpecifics__Group__5 )
            // InternalMazeDsl.g:3288:2: rule__RangedSpecifics__Group__4__Impl rule__RangedSpecifics__Group__5
            {
            pushFollow(FOLLOW_28);
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
    // InternalMazeDsl.g:3295:1: rule__RangedSpecifics__Group__4__Impl : ( ( rule__RangedSpecifics__Group_4__0 )? ) ;
    public final void rule__RangedSpecifics__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3299:1: ( ( ( rule__RangedSpecifics__Group_4__0 )? ) )
            // InternalMazeDsl.g:3300:1: ( ( rule__RangedSpecifics__Group_4__0 )? )
            {
            // InternalMazeDsl.g:3300:1: ( ( rule__RangedSpecifics__Group_4__0 )? )
            // InternalMazeDsl.g:3301:2: ( rule__RangedSpecifics__Group_4__0 )?
            {
             before(grammarAccess.getRangedSpecificsAccess().getGroup_4()); 
            // InternalMazeDsl.g:3302:2: ( rule__RangedSpecifics__Group_4__0 )?
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==60) ) {
                alt35=1;
            }
            switch (alt35) {
                case 1 :
                    // InternalMazeDsl.g:3302:3: rule__RangedSpecifics__Group_4__0
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
    // InternalMazeDsl.g:3310:1: rule__RangedSpecifics__Group__5 : rule__RangedSpecifics__Group__5__Impl rule__RangedSpecifics__Group__6 ;
    public final void rule__RangedSpecifics__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3314:1: ( rule__RangedSpecifics__Group__5__Impl rule__RangedSpecifics__Group__6 )
            // InternalMazeDsl.g:3315:2: rule__RangedSpecifics__Group__5__Impl rule__RangedSpecifics__Group__6
            {
            pushFollow(FOLLOW_28);
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
    // InternalMazeDsl.g:3322:1: rule__RangedSpecifics__Group__5__Impl : ( ( rule__RangedSpecifics__Group_5__0 )? ) ;
    public final void rule__RangedSpecifics__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3326:1: ( ( ( rule__RangedSpecifics__Group_5__0 )? ) )
            // InternalMazeDsl.g:3327:1: ( ( rule__RangedSpecifics__Group_5__0 )? )
            {
            // InternalMazeDsl.g:3327:1: ( ( rule__RangedSpecifics__Group_5__0 )? )
            // InternalMazeDsl.g:3328:2: ( rule__RangedSpecifics__Group_5__0 )?
            {
             before(grammarAccess.getRangedSpecificsAccess().getGroup_5()); 
            // InternalMazeDsl.g:3329:2: ( rule__RangedSpecifics__Group_5__0 )?
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==52) ) {
                alt36=1;
            }
            switch (alt36) {
                case 1 :
                    // InternalMazeDsl.g:3329:3: rule__RangedSpecifics__Group_5__0
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
    // InternalMazeDsl.g:3337:1: rule__RangedSpecifics__Group__6 : rule__RangedSpecifics__Group__6__Impl rule__RangedSpecifics__Group__7 ;
    public final void rule__RangedSpecifics__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3341:1: ( rule__RangedSpecifics__Group__6__Impl rule__RangedSpecifics__Group__7 )
            // InternalMazeDsl.g:3342:2: rule__RangedSpecifics__Group__6__Impl rule__RangedSpecifics__Group__7
            {
            pushFollow(FOLLOW_28);
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
    // InternalMazeDsl.g:3349:1: rule__RangedSpecifics__Group__6__Impl : ( ( rule__RangedSpecifics__Group_6__0 )? ) ;
    public final void rule__RangedSpecifics__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3353:1: ( ( ( rule__RangedSpecifics__Group_6__0 )? ) )
            // InternalMazeDsl.g:3354:1: ( ( rule__RangedSpecifics__Group_6__0 )? )
            {
            // InternalMazeDsl.g:3354:1: ( ( rule__RangedSpecifics__Group_6__0 )? )
            // InternalMazeDsl.g:3355:2: ( rule__RangedSpecifics__Group_6__0 )?
            {
             before(grammarAccess.getRangedSpecificsAccess().getGroup_6()); 
            // InternalMazeDsl.g:3356:2: ( rule__RangedSpecifics__Group_6__0 )?
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( (LA37_0==61) ) {
                alt37=1;
            }
            switch (alt37) {
                case 1 :
                    // InternalMazeDsl.g:3356:3: rule__RangedSpecifics__Group_6__0
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
    // InternalMazeDsl.g:3364:1: rule__RangedSpecifics__Group__7 : rule__RangedSpecifics__Group__7__Impl rule__RangedSpecifics__Group__8 ;
    public final void rule__RangedSpecifics__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3368:1: ( rule__RangedSpecifics__Group__7__Impl rule__RangedSpecifics__Group__8 )
            // InternalMazeDsl.g:3369:2: rule__RangedSpecifics__Group__7__Impl rule__RangedSpecifics__Group__8
            {
            pushFollow(FOLLOW_28);
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
    // InternalMazeDsl.g:3376:1: rule__RangedSpecifics__Group__7__Impl : ( ( rule__RangedSpecifics__Group_7__0 )? ) ;
    public final void rule__RangedSpecifics__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3380:1: ( ( ( rule__RangedSpecifics__Group_7__0 )? ) )
            // InternalMazeDsl.g:3381:1: ( ( rule__RangedSpecifics__Group_7__0 )? )
            {
            // InternalMazeDsl.g:3381:1: ( ( rule__RangedSpecifics__Group_7__0 )? )
            // InternalMazeDsl.g:3382:2: ( rule__RangedSpecifics__Group_7__0 )?
            {
             before(grammarAccess.getRangedSpecificsAccess().getGroup_7()); 
            // InternalMazeDsl.g:3383:2: ( rule__RangedSpecifics__Group_7__0 )?
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==62) ) {
                alt38=1;
            }
            switch (alt38) {
                case 1 :
                    // InternalMazeDsl.g:3383:3: rule__RangedSpecifics__Group_7__0
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
    // InternalMazeDsl.g:3391:1: rule__RangedSpecifics__Group__8 : rule__RangedSpecifics__Group__8__Impl rule__RangedSpecifics__Group__9 ;
    public final void rule__RangedSpecifics__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3395:1: ( rule__RangedSpecifics__Group__8__Impl rule__RangedSpecifics__Group__9 )
            // InternalMazeDsl.g:3396:2: rule__RangedSpecifics__Group__8__Impl rule__RangedSpecifics__Group__9
            {
            pushFollow(FOLLOW_28);
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
    // InternalMazeDsl.g:3403:1: rule__RangedSpecifics__Group__8__Impl : ( ( rule__RangedSpecifics__Group_8__0 )? ) ;
    public final void rule__RangedSpecifics__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3407:1: ( ( ( rule__RangedSpecifics__Group_8__0 )? ) )
            // InternalMazeDsl.g:3408:1: ( ( rule__RangedSpecifics__Group_8__0 )? )
            {
            // InternalMazeDsl.g:3408:1: ( ( rule__RangedSpecifics__Group_8__0 )? )
            // InternalMazeDsl.g:3409:2: ( rule__RangedSpecifics__Group_8__0 )?
            {
             before(grammarAccess.getRangedSpecificsAccess().getGroup_8()); 
            // InternalMazeDsl.g:3410:2: ( rule__RangedSpecifics__Group_8__0 )?
            int alt39=2;
            int LA39_0 = input.LA(1);

            if ( (LA39_0==63) ) {
                alt39=1;
            }
            switch (alt39) {
                case 1 :
                    // InternalMazeDsl.g:3410:3: rule__RangedSpecifics__Group_8__0
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
    // InternalMazeDsl.g:3418:1: rule__RangedSpecifics__Group__9 : rule__RangedSpecifics__Group__9__Impl ;
    public final void rule__RangedSpecifics__Group__9() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3422:1: ( rule__RangedSpecifics__Group__9__Impl )
            // InternalMazeDsl.g:3423:2: rule__RangedSpecifics__Group__9__Impl
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
    // InternalMazeDsl.g:3429:1: rule__RangedSpecifics__Group__9__Impl : ( '}' ) ;
    public final void rule__RangedSpecifics__Group__9__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3433:1: ( ( '}' ) )
            // InternalMazeDsl.g:3434:1: ( '}' )
            {
            // InternalMazeDsl.g:3434:1: ( '}' )
            // InternalMazeDsl.g:3435:2: '}'
            {
             before(grammarAccess.getRangedSpecificsAccess().getRightCurlyBracketKeyword_9()); 
            match(input,32,FOLLOW_2); 
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
    // InternalMazeDsl.g:3445:1: rule__RangedSpecifics__Group_3__0 : rule__RangedSpecifics__Group_3__0__Impl rule__RangedSpecifics__Group_3__1 ;
    public final void rule__RangedSpecifics__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3449:1: ( rule__RangedSpecifics__Group_3__0__Impl rule__RangedSpecifics__Group_3__1 )
            // InternalMazeDsl.g:3450:2: rule__RangedSpecifics__Group_3__0__Impl rule__RangedSpecifics__Group_3__1
            {
            pushFollow(FOLLOW_16);
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
    // InternalMazeDsl.g:3457:1: rule__RangedSpecifics__Group_3__0__Impl : ( 'attackRange' ) ;
    public final void rule__RangedSpecifics__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3461:1: ( ( 'attackRange' ) )
            // InternalMazeDsl.g:3462:1: ( 'attackRange' )
            {
            // InternalMazeDsl.g:3462:1: ( 'attackRange' )
            // InternalMazeDsl.g:3463:2: 'attackRange'
            {
             before(grammarAccess.getRangedSpecificsAccess().getAttackRangeKeyword_3_0()); 
            match(input,59,FOLLOW_2); 
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
    // InternalMazeDsl.g:3472:1: rule__RangedSpecifics__Group_3__1 : rule__RangedSpecifics__Group_3__1__Impl ;
    public final void rule__RangedSpecifics__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3476:1: ( rule__RangedSpecifics__Group_3__1__Impl )
            // InternalMazeDsl.g:3477:2: rule__RangedSpecifics__Group_3__1__Impl
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
    // InternalMazeDsl.g:3483:1: rule__RangedSpecifics__Group_3__1__Impl : ( ( rule__RangedSpecifics__AttackRangeAssignment_3_1 ) ) ;
    public final void rule__RangedSpecifics__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3487:1: ( ( ( rule__RangedSpecifics__AttackRangeAssignment_3_1 ) ) )
            // InternalMazeDsl.g:3488:1: ( ( rule__RangedSpecifics__AttackRangeAssignment_3_1 ) )
            {
            // InternalMazeDsl.g:3488:1: ( ( rule__RangedSpecifics__AttackRangeAssignment_3_1 ) )
            // InternalMazeDsl.g:3489:2: ( rule__RangedSpecifics__AttackRangeAssignment_3_1 )
            {
             before(grammarAccess.getRangedSpecificsAccess().getAttackRangeAssignment_3_1()); 
            // InternalMazeDsl.g:3490:2: ( rule__RangedSpecifics__AttackRangeAssignment_3_1 )
            // InternalMazeDsl.g:3490:3: rule__RangedSpecifics__AttackRangeAssignment_3_1
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
    // InternalMazeDsl.g:3499:1: rule__RangedSpecifics__Group_4__0 : rule__RangedSpecifics__Group_4__0__Impl rule__RangedSpecifics__Group_4__1 ;
    public final void rule__RangedSpecifics__Group_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3503:1: ( rule__RangedSpecifics__Group_4__0__Impl rule__RangedSpecifics__Group_4__1 )
            // InternalMazeDsl.g:3504:2: rule__RangedSpecifics__Group_4__0__Impl rule__RangedSpecifics__Group_4__1
            {
            pushFollow(FOLLOW_16);
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
    // InternalMazeDsl.g:3511:1: rule__RangedSpecifics__Group_4__0__Impl : ( 'attackCooldown' ) ;
    public final void rule__RangedSpecifics__Group_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3515:1: ( ( 'attackCooldown' ) )
            // InternalMazeDsl.g:3516:1: ( 'attackCooldown' )
            {
            // InternalMazeDsl.g:3516:1: ( 'attackCooldown' )
            // InternalMazeDsl.g:3517:2: 'attackCooldown'
            {
             before(grammarAccess.getRangedSpecificsAccess().getAttackCooldownKeyword_4_0()); 
            match(input,60,FOLLOW_2); 
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
    // InternalMazeDsl.g:3526:1: rule__RangedSpecifics__Group_4__1 : rule__RangedSpecifics__Group_4__1__Impl ;
    public final void rule__RangedSpecifics__Group_4__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3530:1: ( rule__RangedSpecifics__Group_4__1__Impl )
            // InternalMazeDsl.g:3531:2: rule__RangedSpecifics__Group_4__1__Impl
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
    // InternalMazeDsl.g:3537:1: rule__RangedSpecifics__Group_4__1__Impl : ( ( rule__RangedSpecifics__AttackCooldownAssignment_4_1 ) ) ;
    public final void rule__RangedSpecifics__Group_4__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3541:1: ( ( ( rule__RangedSpecifics__AttackCooldownAssignment_4_1 ) ) )
            // InternalMazeDsl.g:3542:1: ( ( rule__RangedSpecifics__AttackCooldownAssignment_4_1 ) )
            {
            // InternalMazeDsl.g:3542:1: ( ( rule__RangedSpecifics__AttackCooldownAssignment_4_1 ) )
            // InternalMazeDsl.g:3543:2: ( rule__RangedSpecifics__AttackCooldownAssignment_4_1 )
            {
             before(grammarAccess.getRangedSpecificsAccess().getAttackCooldownAssignment_4_1()); 
            // InternalMazeDsl.g:3544:2: ( rule__RangedSpecifics__AttackCooldownAssignment_4_1 )
            // InternalMazeDsl.g:3544:3: rule__RangedSpecifics__AttackCooldownAssignment_4_1
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
    // InternalMazeDsl.g:3553:1: rule__RangedSpecifics__Group_5__0 : rule__RangedSpecifics__Group_5__0__Impl rule__RangedSpecifics__Group_5__1 ;
    public final void rule__RangedSpecifics__Group_5__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3557:1: ( rule__RangedSpecifics__Group_5__0__Impl rule__RangedSpecifics__Group_5__1 )
            // InternalMazeDsl.g:3558:2: rule__RangedSpecifics__Group_5__0__Impl rule__RangedSpecifics__Group_5__1
            {
            pushFollow(FOLLOW_16);
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
    // InternalMazeDsl.g:3565:1: rule__RangedSpecifics__Group_5__0__Impl : ( 'attackDamage' ) ;
    public final void rule__RangedSpecifics__Group_5__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3569:1: ( ( 'attackDamage' ) )
            // InternalMazeDsl.g:3570:1: ( 'attackDamage' )
            {
            // InternalMazeDsl.g:3570:1: ( 'attackDamage' )
            // InternalMazeDsl.g:3571:2: 'attackDamage'
            {
             before(grammarAccess.getRangedSpecificsAccess().getAttackDamageKeyword_5_0()); 
            match(input,52,FOLLOW_2); 
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
    // InternalMazeDsl.g:3580:1: rule__RangedSpecifics__Group_5__1 : rule__RangedSpecifics__Group_5__1__Impl ;
    public final void rule__RangedSpecifics__Group_5__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3584:1: ( rule__RangedSpecifics__Group_5__1__Impl )
            // InternalMazeDsl.g:3585:2: rule__RangedSpecifics__Group_5__1__Impl
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
    // InternalMazeDsl.g:3591:1: rule__RangedSpecifics__Group_5__1__Impl : ( ( rule__RangedSpecifics__AttackDamageAssignment_5_1 ) ) ;
    public final void rule__RangedSpecifics__Group_5__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3595:1: ( ( ( rule__RangedSpecifics__AttackDamageAssignment_5_1 ) ) )
            // InternalMazeDsl.g:3596:1: ( ( rule__RangedSpecifics__AttackDamageAssignment_5_1 ) )
            {
            // InternalMazeDsl.g:3596:1: ( ( rule__RangedSpecifics__AttackDamageAssignment_5_1 ) )
            // InternalMazeDsl.g:3597:2: ( rule__RangedSpecifics__AttackDamageAssignment_5_1 )
            {
             before(grammarAccess.getRangedSpecificsAccess().getAttackDamageAssignment_5_1()); 
            // InternalMazeDsl.g:3598:2: ( rule__RangedSpecifics__AttackDamageAssignment_5_1 )
            // InternalMazeDsl.g:3598:3: rule__RangedSpecifics__AttackDamageAssignment_5_1
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
    // InternalMazeDsl.g:3607:1: rule__RangedSpecifics__Group_6__0 : rule__RangedSpecifics__Group_6__0__Impl rule__RangedSpecifics__Group_6__1 ;
    public final void rule__RangedSpecifics__Group_6__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3611:1: ( rule__RangedSpecifics__Group_6__0__Impl rule__RangedSpecifics__Group_6__1 )
            // InternalMazeDsl.g:3612:2: rule__RangedSpecifics__Group_6__0__Impl rule__RangedSpecifics__Group_6__1
            {
            pushFollow(FOLLOW_16);
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
    // InternalMazeDsl.g:3619:1: rule__RangedSpecifics__Group_6__0__Impl : ( 'projectileSpeed' ) ;
    public final void rule__RangedSpecifics__Group_6__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3623:1: ( ( 'projectileSpeed' ) )
            // InternalMazeDsl.g:3624:1: ( 'projectileSpeed' )
            {
            // InternalMazeDsl.g:3624:1: ( 'projectileSpeed' )
            // InternalMazeDsl.g:3625:2: 'projectileSpeed'
            {
             before(grammarAccess.getRangedSpecificsAccess().getProjectileSpeedKeyword_6_0()); 
            match(input,61,FOLLOW_2); 
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
    // InternalMazeDsl.g:3634:1: rule__RangedSpecifics__Group_6__1 : rule__RangedSpecifics__Group_6__1__Impl ;
    public final void rule__RangedSpecifics__Group_6__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3638:1: ( rule__RangedSpecifics__Group_6__1__Impl )
            // InternalMazeDsl.g:3639:2: rule__RangedSpecifics__Group_6__1__Impl
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
    // InternalMazeDsl.g:3645:1: rule__RangedSpecifics__Group_6__1__Impl : ( ( rule__RangedSpecifics__ProjectileSpeedAssignment_6_1 ) ) ;
    public final void rule__RangedSpecifics__Group_6__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3649:1: ( ( ( rule__RangedSpecifics__ProjectileSpeedAssignment_6_1 ) ) )
            // InternalMazeDsl.g:3650:1: ( ( rule__RangedSpecifics__ProjectileSpeedAssignment_6_1 ) )
            {
            // InternalMazeDsl.g:3650:1: ( ( rule__RangedSpecifics__ProjectileSpeedAssignment_6_1 ) )
            // InternalMazeDsl.g:3651:2: ( rule__RangedSpecifics__ProjectileSpeedAssignment_6_1 )
            {
             before(grammarAccess.getRangedSpecificsAccess().getProjectileSpeedAssignment_6_1()); 
            // InternalMazeDsl.g:3652:2: ( rule__RangedSpecifics__ProjectileSpeedAssignment_6_1 )
            // InternalMazeDsl.g:3652:3: rule__RangedSpecifics__ProjectileSpeedAssignment_6_1
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
    // InternalMazeDsl.g:3661:1: rule__RangedSpecifics__Group_7__0 : rule__RangedSpecifics__Group_7__0__Impl rule__RangedSpecifics__Group_7__1 ;
    public final void rule__RangedSpecifics__Group_7__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3665:1: ( rule__RangedSpecifics__Group_7__0__Impl rule__RangedSpecifics__Group_7__1 )
            // InternalMazeDsl.g:3666:2: rule__RangedSpecifics__Group_7__0__Impl rule__RangedSpecifics__Group_7__1
            {
            pushFollow(FOLLOW_29);
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
    // InternalMazeDsl.g:3673:1: rule__RangedSpecifics__Group_7__0__Impl : ( 'projectileType' ) ;
    public final void rule__RangedSpecifics__Group_7__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3677:1: ( ( 'projectileType' ) )
            // InternalMazeDsl.g:3678:1: ( 'projectileType' )
            {
            // InternalMazeDsl.g:3678:1: ( 'projectileType' )
            // InternalMazeDsl.g:3679:2: 'projectileType'
            {
             before(grammarAccess.getRangedSpecificsAccess().getProjectileTypeKeyword_7_0()); 
            match(input,62,FOLLOW_2); 
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
    // InternalMazeDsl.g:3688:1: rule__RangedSpecifics__Group_7__1 : rule__RangedSpecifics__Group_7__1__Impl ;
    public final void rule__RangedSpecifics__Group_7__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3692:1: ( rule__RangedSpecifics__Group_7__1__Impl )
            // InternalMazeDsl.g:3693:2: rule__RangedSpecifics__Group_7__1__Impl
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
    // InternalMazeDsl.g:3699:1: rule__RangedSpecifics__Group_7__1__Impl : ( ( rule__RangedSpecifics__ProjectileTypeAssignment_7_1 ) ) ;
    public final void rule__RangedSpecifics__Group_7__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3703:1: ( ( ( rule__RangedSpecifics__ProjectileTypeAssignment_7_1 ) ) )
            // InternalMazeDsl.g:3704:1: ( ( rule__RangedSpecifics__ProjectileTypeAssignment_7_1 ) )
            {
            // InternalMazeDsl.g:3704:1: ( ( rule__RangedSpecifics__ProjectileTypeAssignment_7_1 ) )
            // InternalMazeDsl.g:3705:2: ( rule__RangedSpecifics__ProjectileTypeAssignment_7_1 )
            {
             before(grammarAccess.getRangedSpecificsAccess().getProjectileTypeAssignment_7_1()); 
            // InternalMazeDsl.g:3706:2: ( rule__RangedSpecifics__ProjectileTypeAssignment_7_1 )
            // InternalMazeDsl.g:3706:3: rule__RangedSpecifics__ProjectileTypeAssignment_7_1
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
    // InternalMazeDsl.g:3715:1: rule__RangedSpecifics__Group_8__0 : rule__RangedSpecifics__Group_8__0__Impl rule__RangedSpecifics__Group_8__1 ;
    public final void rule__RangedSpecifics__Group_8__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3719:1: ( rule__RangedSpecifics__Group_8__0__Impl rule__RangedSpecifics__Group_8__1 )
            // InternalMazeDsl.g:3720:2: rule__RangedSpecifics__Group_8__0__Impl rule__RangedSpecifics__Group_8__1
            {
            pushFollow(FOLLOW_16);
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
    // InternalMazeDsl.g:3727:1: rule__RangedSpecifics__Group_8__0__Impl : ( 'splashRadius' ) ;
    public final void rule__RangedSpecifics__Group_8__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3731:1: ( ( 'splashRadius' ) )
            // InternalMazeDsl.g:3732:1: ( 'splashRadius' )
            {
            // InternalMazeDsl.g:3732:1: ( 'splashRadius' )
            // InternalMazeDsl.g:3733:2: 'splashRadius'
            {
             before(grammarAccess.getRangedSpecificsAccess().getSplashRadiusKeyword_8_0()); 
            match(input,63,FOLLOW_2); 
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
    // InternalMazeDsl.g:3742:1: rule__RangedSpecifics__Group_8__1 : rule__RangedSpecifics__Group_8__1__Impl ;
    public final void rule__RangedSpecifics__Group_8__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3746:1: ( rule__RangedSpecifics__Group_8__1__Impl )
            // InternalMazeDsl.g:3747:2: rule__RangedSpecifics__Group_8__1__Impl
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
    // InternalMazeDsl.g:3753:1: rule__RangedSpecifics__Group_8__1__Impl : ( ( rule__RangedSpecifics__SplashRadiusAssignment_8_1 ) ) ;
    public final void rule__RangedSpecifics__Group_8__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3757:1: ( ( ( rule__RangedSpecifics__SplashRadiusAssignment_8_1 ) ) )
            // InternalMazeDsl.g:3758:1: ( ( rule__RangedSpecifics__SplashRadiusAssignment_8_1 ) )
            {
            // InternalMazeDsl.g:3758:1: ( ( rule__RangedSpecifics__SplashRadiusAssignment_8_1 ) )
            // InternalMazeDsl.g:3759:2: ( rule__RangedSpecifics__SplashRadiusAssignment_8_1 )
            {
             before(grammarAccess.getRangedSpecificsAccess().getSplashRadiusAssignment_8_1()); 
            // InternalMazeDsl.g:3760:2: ( rule__RangedSpecifics__SplashRadiusAssignment_8_1 )
            // InternalMazeDsl.g:3760:3: rule__RangedSpecifics__SplashRadiusAssignment_8_1
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
    // InternalMazeDsl.g:3769:1: rule__PatrolConfig__Group__0 : rule__PatrolConfig__Group__0__Impl rule__PatrolConfig__Group__1 ;
    public final void rule__PatrolConfig__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3773:1: ( rule__PatrolConfig__Group__0__Impl rule__PatrolConfig__Group__1 )
            // InternalMazeDsl.g:3774:2: rule__PatrolConfig__Group__0__Impl rule__PatrolConfig__Group__1
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
    // InternalMazeDsl.g:3781:1: rule__PatrolConfig__Group__0__Impl : ( 'patrol' ) ;
    public final void rule__PatrolConfig__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3785:1: ( ( 'patrol' ) )
            // InternalMazeDsl.g:3786:1: ( 'patrol' )
            {
            // InternalMazeDsl.g:3786:1: ( 'patrol' )
            // InternalMazeDsl.g:3787:2: 'patrol'
            {
             before(grammarAccess.getPatrolConfigAccess().getPatrolKeyword_0()); 
            match(input,22,FOLLOW_2); 
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
    // InternalMazeDsl.g:3796:1: rule__PatrolConfig__Group__1 : rule__PatrolConfig__Group__1__Impl rule__PatrolConfig__Group__2 ;
    public final void rule__PatrolConfig__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3800:1: ( rule__PatrolConfig__Group__1__Impl rule__PatrolConfig__Group__2 )
            // InternalMazeDsl.g:3801:2: rule__PatrolConfig__Group__1__Impl rule__PatrolConfig__Group__2
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
    // InternalMazeDsl.g:3808:1: rule__PatrolConfig__Group__1__Impl : ( ( rule__PatrolConfig__NameAssignment_1 ) ) ;
    public final void rule__PatrolConfig__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3812:1: ( ( ( rule__PatrolConfig__NameAssignment_1 ) ) )
            // InternalMazeDsl.g:3813:1: ( ( rule__PatrolConfig__NameAssignment_1 ) )
            {
            // InternalMazeDsl.g:3813:1: ( ( rule__PatrolConfig__NameAssignment_1 ) )
            // InternalMazeDsl.g:3814:2: ( rule__PatrolConfig__NameAssignment_1 )
            {
             before(grammarAccess.getPatrolConfigAccess().getNameAssignment_1()); 
            // InternalMazeDsl.g:3815:2: ( rule__PatrolConfig__NameAssignment_1 )
            // InternalMazeDsl.g:3815:3: rule__PatrolConfig__NameAssignment_1
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
    // InternalMazeDsl.g:3823:1: rule__PatrolConfig__Group__2 : rule__PatrolConfig__Group__2__Impl rule__PatrolConfig__Group__3 ;
    public final void rule__PatrolConfig__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3827:1: ( rule__PatrolConfig__Group__2__Impl rule__PatrolConfig__Group__3 )
            // InternalMazeDsl.g:3828:2: rule__PatrolConfig__Group__2__Impl rule__PatrolConfig__Group__3
            {
            pushFollow(FOLLOW_30);
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
    // InternalMazeDsl.g:3835:1: rule__PatrolConfig__Group__2__Impl : ( '{' ) ;
    public final void rule__PatrolConfig__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3839:1: ( ( '{' ) )
            // InternalMazeDsl.g:3840:1: ( '{' )
            {
            // InternalMazeDsl.g:3840:1: ( '{' )
            // InternalMazeDsl.g:3841:2: '{'
            {
             before(grammarAccess.getPatrolConfigAccess().getLeftCurlyBracketKeyword_2()); 
            match(input,31,FOLLOW_2); 
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
    // InternalMazeDsl.g:3850:1: rule__PatrolConfig__Group__3 : rule__PatrolConfig__Group__3__Impl rule__PatrolConfig__Group__4 ;
    public final void rule__PatrolConfig__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3854:1: ( rule__PatrolConfig__Group__3__Impl rule__PatrolConfig__Group__4 )
            // InternalMazeDsl.g:3855:2: rule__PatrolConfig__Group__3__Impl rule__PatrolConfig__Group__4
            {
            pushFollow(FOLLOW_30);
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
    // InternalMazeDsl.g:3862:1: rule__PatrolConfig__Group__3__Impl : ( ( rule__PatrolConfig__Group_3__0 )? ) ;
    public final void rule__PatrolConfig__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3866:1: ( ( ( rule__PatrolConfig__Group_3__0 )? ) )
            // InternalMazeDsl.g:3867:1: ( ( rule__PatrolConfig__Group_3__0 )? )
            {
            // InternalMazeDsl.g:3867:1: ( ( rule__PatrolConfig__Group_3__0 )? )
            // InternalMazeDsl.g:3868:2: ( rule__PatrolConfig__Group_3__0 )?
            {
             before(grammarAccess.getPatrolConfigAccess().getGroup_3()); 
            // InternalMazeDsl.g:3869:2: ( rule__PatrolConfig__Group_3__0 )?
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( (LA40_0==67) ) {
                alt40=1;
            }
            switch (alt40) {
                case 1 :
                    // InternalMazeDsl.g:3869:3: rule__PatrolConfig__Group_3__0
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
    // InternalMazeDsl.g:3877:1: rule__PatrolConfig__Group__4 : rule__PatrolConfig__Group__4__Impl rule__PatrolConfig__Group__5 ;
    public final void rule__PatrolConfig__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3881:1: ( rule__PatrolConfig__Group__4__Impl rule__PatrolConfig__Group__5 )
            // InternalMazeDsl.g:3882:2: rule__PatrolConfig__Group__4__Impl rule__PatrolConfig__Group__5
            {
            pushFollow(FOLLOW_30);
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
    // InternalMazeDsl.g:3889:1: rule__PatrolConfig__Group__4__Impl : ( ( rule__PatrolConfig__Group_4__0 )? ) ;
    public final void rule__PatrolConfig__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3893:1: ( ( ( rule__PatrolConfig__Group_4__0 )? ) )
            // InternalMazeDsl.g:3894:1: ( ( rule__PatrolConfig__Group_4__0 )? )
            {
            // InternalMazeDsl.g:3894:1: ( ( rule__PatrolConfig__Group_4__0 )? )
            // InternalMazeDsl.g:3895:2: ( rule__PatrolConfig__Group_4__0 )?
            {
             before(grammarAccess.getPatrolConfigAccess().getGroup_4()); 
            // InternalMazeDsl.g:3896:2: ( rule__PatrolConfig__Group_4__0 )?
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( (LA41_0==68) ) {
                alt41=1;
            }
            switch (alt41) {
                case 1 :
                    // InternalMazeDsl.g:3896:3: rule__PatrolConfig__Group_4__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__PatrolConfig__Group_4__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getPatrolConfigAccess().getGroup_4()); 

            }


            }

        }
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
    // InternalMazeDsl.g:3904:1: rule__PatrolConfig__Group__5 : rule__PatrolConfig__Group__5__Impl rule__PatrolConfig__Group__6 ;
    public final void rule__PatrolConfig__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3908:1: ( rule__PatrolConfig__Group__5__Impl rule__PatrolConfig__Group__6 )
            // InternalMazeDsl.g:3909:2: rule__PatrolConfig__Group__5__Impl rule__PatrolConfig__Group__6
            {
            pushFollow(FOLLOW_31);
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
    // InternalMazeDsl.g:3916:1: rule__PatrolConfig__Group__5__Impl : ( 'path' ) ;
    public final void rule__PatrolConfig__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3920:1: ( ( 'path' ) )
            // InternalMazeDsl.g:3921:1: ( 'path' )
            {
            // InternalMazeDsl.g:3921:1: ( 'path' )
            // InternalMazeDsl.g:3922:2: 'path'
            {
             before(grammarAccess.getPatrolConfigAccess().getPathKeyword_5()); 
            match(input,64,FOLLOW_2); 
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
    // InternalMazeDsl.g:3931:1: rule__PatrolConfig__Group__6 : rule__PatrolConfig__Group__6__Impl rule__PatrolConfig__Group__7 ;
    public final void rule__PatrolConfig__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3935:1: ( rule__PatrolConfig__Group__6__Impl rule__PatrolConfig__Group__7 )
            // InternalMazeDsl.g:3936:2: rule__PatrolConfig__Group__6__Impl rule__PatrolConfig__Group__7
            {
            pushFollow(FOLLOW_32);
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
    // InternalMazeDsl.g:3943:1: rule__PatrolConfig__Group__6__Impl : ( '[' ) ;
    public final void rule__PatrolConfig__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3947:1: ( ( '[' ) )
            // InternalMazeDsl.g:3948:1: ( '[' )
            {
            // InternalMazeDsl.g:3948:1: ( '[' )
            // InternalMazeDsl.g:3949:2: '['
            {
             before(grammarAccess.getPatrolConfigAccess().getLeftSquareBracketKeyword_6()); 
            match(input,65,FOLLOW_2); 
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
    // InternalMazeDsl.g:3958:1: rule__PatrolConfig__Group__7 : rule__PatrolConfig__Group__7__Impl rule__PatrolConfig__Group__8 ;
    public final void rule__PatrolConfig__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3962:1: ( rule__PatrolConfig__Group__7__Impl rule__PatrolConfig__Group__8 )
            // InternalMazeDsl.g:3963:2: rule__PatrolConfig__Group__7__Impl rule__PatrolConfig__Group__8
            {
            pushFollow(FOLLOW_33);
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
    // InternalMazeDsl.g:3970:1: rule__PatrolConfig__Group__7__Impl : ( ( rule__PatrolConfig__WaypointsAssignment_7 ) ) ;
    public final void rule__PatrolConfig__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3974:1: ( ( ( rule__PatrolConfig__WaypointsAssignment_7 ) ) )
            // InternalMazeDsl.g:3975:1: ( ( rule__PatrolConfig__WaypointsAssignment_7 ) )
            {
            // InternalMazeDsl.g:3975:1: ( ( rule__PatrolConfig__WaypointsAssignment_7 ) )
            // InternalMazeDsl.g:3976:2: ( rule__PatrolConfig__WaypointsAssignment_7 )
            {
             before(grammarAccess.getPatrolConfigAccess().getWaypointsAssignment_7()); 
            // InternalMazeDsl.g:3977:2: ( rule__PatrolConfig__WaypointsAssignment_7 )
            // InternalMazeDsl.g:3977:3: rule__PatrolConfig__WaypointsAssignment_7
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
    // InternalMazeDsl.g:3985:1: rule__PatrolConfig__Group__8 : rule__PatrolConfig__Group__8__Impl rule__PatrolConfig__Group__9 ;
    public final void rule__PatrolConfig__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3989:1: ( rule__PatrolConfig__Group__8__Impl rule__PatrolConfig__Group__9 )
            // InternalMazeDsl.g:3990:2: rule__PatrolConfig__Group__8__Impl rule__PatrolConfig__Group__9
            {
            pushFollow(FOLLOW_33);
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
    // InternalMazeDsl.g:3997:1: rule__PatrolConfig__Group__8__Impl : ( ( rule__PatrolConfig__Group_8__0 )* ) ;
    public final void rule__PatrolConfig__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4001:1: ( ( ( rule__PatrolConfig__Group_8__0 )* ) )
            // InternalMazeDsl.g:4002:1: ( ( rule__PatrolConfig__Group_8__0 )* )
            {
            // InternalMazeDsl.g:4002:1: ( ( rule__PatrolConfig__Group_8__0 )* )
            // InternalMazeDsl.g:4003:2: ( rule__PatrolConfig__Group_8__0 )*
            {
             before(grammarAccess.getPatrolConfigAccess().getGroup_8()); 
            // InternalMazeDsl.g:4004:2: ( rule__PatrolConfig__Group_8__0 )*
            loop42:
            do {
                int alt42=2;
                int LA42_0 = input.LA(1);

                if ( (LA42_0==69) ) {
                    alt42=1;
                }


                switch (alt42) {
            	case 1 :
            	    // InternalMazeDsl.g:4004:3: rule__PatrolConfig__Group_8__0
            	    {
            	    pushFollow(FOLLOW_34);
            	    rule__PatrolConfig__Group_8__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop42;
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
    // InternalMazeDsl.g:4012:1: rule__PatrolConfig__Group__9 : rule__PatrolConfig__Group__9__Impl rule__PatrolConfig__Group__10 ;
    public final void rule__PatrolConfig__Group__9() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4016:1: ( rule__PatrolConfig__Group__9__Impl rule__PatrolConfig__Group__10 )
            // InternalMazeDsl.g:4017:2: rule__PatrolConfig__Group__9__Impl rule__PatrolConfig__Group__10
            {
            pushFollow(FOLLOW_35);
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
    // InternalMazeDsl.g:4024:1: rule__PatrolConfig__Group__9__Impl : ( ']' ) ;
    public final void rule__PatrolConfig__Group__9__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4028:1: ( ( ']' ) )
            // InternalMazeDsl.g:4029:1: ( ']' )
            {
            // InternalMazeDsl.g:4029:1: ( ']' )
            // InternalMazeDsl.g:4030:2: ']'
            {
             before(grammarAccess.getPatrolConfigAccess().getRightSquareBracketKeyword_9()); 
            match(input,66,FOLLOW_2); 
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
    // InternalMazeDsl.g:4039:1: rule__PatrolConfig__Group__10 : rule__PatrolConfig__Group__10__Impl ;
    public final void rule__PatrolConfig__Group__10() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4043:1: ( rule__PatrolConfig__Group__10__Impl )
            // InternalMazeDsl.g:4044:2: rule__PatrolConfig__Group__10__Impl
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
    // InternalMazeDsl.g:4050:1: rule__PatrolConfig__Group__10__Impl : ( '}' ) ;
    public final void rule__PatrolConfig__Group__10__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4054:1: ( ( '}' ) )
            // InternalMazeDsl.g:4055:1: ( '}' )
            {
            // InternalMazeDsl.g:4055:1: ( '}' )
            // InternalMazeDsl.g:4056:2: '}'
            {
             before(grammarAccess.getPatrolConfigAccess().getRightCurlyBracketKeyword_10()); 
            match(input,32,FOLLOW_2); 
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
    // InternalMazeDsl.g:4066:1: rule__PatrolConfig__Group_3__0 : rule__PatrolConfig__Group_3__0__Impl rule__PatrolConfig__Group_3__1 ;
    public final void rule__PatrolConfig__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4070:1: ( rule__PatrolConfig__Group_3__0__Impl rule__PatrolConfig__Group_3__1 )
            // InternalMazeDsl.g:4071:2: rule__PatrolConfig__Group_3__0__Impl rule__PatrolConfig__Group_3__1
            {
            pushFollow(FOLLOW_16);
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
    // InternalMazeDsl.g:4078:1: rule__PatrolConfig__Group_3__0__Impl : ( 'visionRange' ) ;
    public final void rule__PatrolConfig__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4082:1: ( ( 'visionRange' ) )
            // InternalMazeDsl.g:4083:1: ( 'visionRange' )
            {
            // InternalMazeDsl.g:4083:1: ( 'visionRange' )
            // InternalMazeDsl.g:4084:2: 'visionRange'
            {
             before(grammarAccess.getPatrolConfigAccess().getVisionRangeKeyword_3_0()); 
            match(input,67,FOLLOW_2); 
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
    // InternalMazeDsl.g:4093:1: rule__PatrolConfig__Group_3__1 : rule__PatrolConfig__Group_3__1__Impl ;
    public final void rule__PatrolConfig__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4097:1: ( rule__PatrolConfig__Group_3__1__Impl )
            // InternalMazeDsl.g:4098:2: rule__PatrolConfig__Group_3__1__Impl
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
    // InternalMazeDsl.g:4104:1: rule__PatrolConfig__Group_3__1__Impl : ( ( rule__PatrolConfig__VisionRangeAssignment_3_1 ) ) ;
    public final void rule__PatrolConfig__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4108:1: ( ( ( rule__PatrolConfig__VisionRangeAssignment_3_1 ) ) )
            // InternalMazeDsl.g:4109:1: ( ( rule__PatrolConfig__VisionRangeAssignment_3_1 ) )
            {
            // InternalMazeDsl.g:4109:1: ( ( rule__PatrolConfig__VisionRangeAssignment_3_1 ) )
            // InternalMazeDsl.g:4110:2: ( rule__PatrolConfig__VisionRangeAssignment_3_1 )
            {
             before(grammarAccess.getPatrolConfigAccess().getVisionRangeAssignment_3_1()); 
            // InternalMazeDsl.g:4111:2: ( rule__PatrolConfig__VisionRangeAssignment_3_1 )
            // InternalMazeDsl.g:4111:3: rule__PatrolConfig__VisionRangeAssignment_3_1
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


    // $ANTLR start "rule__PatrolConfig__Group_4__0"
    // InternalMazeDsl.g:4120:1: rule__PatrolConfig__Group_4__0 : rule__PatrolConfig__Group_4__0__Impl rule__PatrolConfig__Group_4__1 ;
    public final void rule__PatrolConfig__Group_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4124:1: ( rule__PatrolConfig__Group_4__0__Impl rule__PatrolConfig__Group_4__1 )
            // InternalMazeDsl.g:4125:2: rule__PatrolConfig__Group_4__0__Impl rule__PatrolConfig__Group_4__1
            {
            pushFollow(FOLLOW_36);
            rule__PatrolConfig__Group_4__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PatrolConfig__Group_4__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group_4__0"


    // $ANTLR start "rule__PatrolConfig__Group_4__0__Impl"
    // InternalMazeDsl.g:4132:1: rule__PatrolConfig__Group_4__0__Impl : ( 'zone' ) ;
    public final void rule__PatrolConfig__Group_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4136:1: ( ( 'zone' ) )
            // InternalMazeDsl.g:4137:1: ( 'zone' )
            {
            // InternalMazeDsl.g:4137:1: ( 'zone' )
            // InternalMazeDsl.g:4138:2: 'zone'
            {
             before(grammarAccess.getPatrolConfigAccess().getZoneKeyword_4_0()); 
            match(input,68,FOLLOW_2); 
             after(grammarAccess.getPatrolConfigAccess().getZoneKeyword_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group_4__0__Impl"


    // $ANTLR start "rule__PatrolConfig__Group_4__1"
    // InternalMazeDsl.g:4147:1: rule__PatrolConfig__Group_4__1 : rule__PatrolConfig__Group_4__1__Impl ;
    public final void rule__PatrolConfig__Group_4__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4151:1: ( rule__PatrolConfig__Group_4__1__Impl )
            // InternalMazeDsl.g:4152:2: rule__PatrolConfig__Group_4__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__PatrolConfig__Group_4__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group_4__1"


    // $ANTLR start "rule__PatrolConfig__Group_4__1__Impl"
    // InternalMazeDsl.g:4158:1: rule__PatrolConfig__Group_4__1__Impl : ( ( rule__PatrolConfig__ZoneAssignment_4_1 ) ) ;
    public final void rule__PatrolConfig__Group_4__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4162:1: ( ( ( rule__PatrolConfig__ZoneAssignment_4_1 ) ) )
            // InternalMazeDsl.g:4163:1: ( ( rule__PatrolConfig__ZoneAssignment_4_1 ) )
            {
            // InternalMazeDsl.g:4163:1: ( ( rule__PatrolConfig__ZoneAssignment_4_1 ) )
            // InternalMazeDsl.g:4164:2: ( rule__PatrolConfig__ZoneAssignment_4_1 )
            {
             before(grammarAccess.getPatrolConfigAccess().getZoneAssignment_4_1()); 
            // InternalMazeDsl.g:4165:2: ( rule__PatrolConfig__ZoneAssignment_4_1 )
            // InternalMazeDsl.g:4165:3: rule__PatrolConfig__ZoneAssignment_4_1
            {
            pushFollow(FOLLOW_2);
            rule__PatrolConfig__ZoneAssignment_4_1();

            state._fsp--;


            }

             after(grammarAccess.getPatrolConfigAccess().getZoneAssignment_4_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group_4__1__Impl"


    // $ANTLR start "rule__PatrolConfig__Group_8__0"
    // InternalMazeDsl.g:4174:1: rule__PatrolConfig__Group_8__0 : rule__PatrolConfig__Group_8__0__Impl rule__PatrolConfig__Group_8__1 ;
    public final void rule__PatrolConfig__Group_8__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4178:1: ( rule__PatrolConfig__Group_8__0__Impl rule__PatrolConfig__Group_8__1 )
            // InternalMazeDsl.g:4179:2: rule__PatrolConfig__Group_8__0__Impl rule__PatrolConfig__Group_8__1
            {
            pushFollow(FOLLOW_32);
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
    // InternalMazeDsl.g:4186:1: rule__PatrolConfig__Group_8__0__Impl : ( ',' ) ;
    public final void rule__PatrolConfig__Group_8__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4190:1: ( ( ',' ) )
            // InternalMazeDsl.g:4191:1: ( ',' )
            {
            // InternalMazeDsl.g:4191:1: ( ',' )
            // InternalMazeDsl.g:4192:2: ','
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
    // InternalMazeDsl.g:4201:1: rule__PatrolConfig__Group_8__1 : rule__PatrolConfig__Group_8__1__Impl ;
    public final void rule__PatrolConfig__Group_8__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4205:1: ( rule__PatrolConfig__Group_8__1__Impl )
            // InternalMazeDsl.g:4206:2: rule__PatrolConfig__Group_8__1__Impl
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
    // InternalMazeDsl.g:4212:1: rule__PatrolConfig__Group_8__1__Impl : ( ( rule__PatrolConfig__WaypointsAssignment_8_1 ) ) ;
    public final void rule__PatrolConfig__Group_8__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4216:1: ( ( ( rule__PatrolConfig__WaypointsAssignment_8_1 ) ) )
            // InternalMazeDsl.g:4217:1: ( ( rule__PatrolConfig__WaypointsAssignment_8_1 ) )
            {
            // InternalMazeDsl.g:4217:1: ( ( rule__PatrolConfig__WaypointsAssignment_8_1 ) )
            // InternalMazeDsl.g:4218:2: ( rule__PatrolConfig__WaypointsAssignment_8_1 )
            {
             before(grammarAccess.getPatrolConfigAccess().getWaypointsAssignment_8_1()); 
            // InternalMazeDsl.g:4219:2: ( rule__PatrolConfig__WaypointsAssignment_8_1 )
            // InternalMazeDsl.g:4219:3: rule__PatrolConfig__WaypointsAssignment_8_1
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
    // InternalMazeDsl.g:4228:1: rule__PatrolZoneConfig__Group__0 : rule__PatrolZoneConfig__Group__0__Impl rule__PatrolZoneConfig__Group__1 ;
    public final void rule__PatrolZoneConfig__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4232:1: ( rule__PatrolZoneConfig__Group__0__Impl rule__PatrolZoneConfig__Group__1 )
            // InternalMazeDsl.g:4233:2: rule__PatrolZoneConfig__Group__0__Impl rule__PatrolZoneConfig__Group__1
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
    // InternalMazeDsl.g:4240:1: rule__PatrolZoneConfig__Group__0__Impl : ( 'zone' ) ;
    public final void rule__PatrolZoneConfig__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4244:1: ( ( 'zone' ) )
            // InternalMazeDsl.g:4245:1: ( 'zone' )
            {
            // InternalMazeDsl.g:4245:1: ( 'zone' )
            // InternalMazeDsl.g:4246:2: 'zone'
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getZoneKeyword_0()); 
            match(input,68,FOLLOW_2); 
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
    // InternalMazeDsl.g:4255:1: rule__PatrolZoneConfig__Group__1 : rule__PatrolZoneConfig__Group__1__Impl rule__PatrolZoneConfig__Group__2 ;
    public final void rule__PatrolZoneConfig__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4259:1: ( rule__PatrolZoneConfig__Group__1__Impl rule__PatrolZoneConfig__Group__2 )
            // InternalMazeDsl.g:4260:2: rule__PatrolZoneConfig__Group__1__Impl rule__PatrolZoneConfig__Group__2
            {
            pushFollow(FOLLOW_37);
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
    // InternalMazeDsl.g:4267:1: rule__PatrolZoneConfig__Group__1__Impl : ( '{' ) ;
    public final void rule__PatrolZoneConfig__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4271:1: ( ( '{' ) )
            // InternalMazeDsl.g:4272:1: ( '{' )
            {
            // InternalMazeDsl.g:4272:1: ( '{' )
            // InternalMazeDsl.g:4273:2: '{'
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getLeftCurlyBracketKeyword_1()); 
            match(input,31,FOLLOW_2); 
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
    // InternalMazeDsl.g:4282:1: rule__PatrolZoneConfig__Group__2 : rule__PatrolZoneConfig__Group__2__Impl rule__PatrolZoneConfig__Group__3 ;
    public final void rule__PatrolZoneConfig__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4286:1: ( rule__PatrolZoneConfig__Group__2__Impl rule__PatrolZoneConfig__Group__3 )
            // InternalMazeDsl.g:4287:2: rule__PatrolZoneConfig__Group__2__Impl rule__PatrolZoneConfig__Group__3
            {
            pushFollow(FOLLOW_32);
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
    // InternalMazeDsl.g:4294:1: rule__PatrolZoneConfig__Group__2__Impl : ( 'topLeft' ) ;
    public final void rule__PatrolZoneConfig__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4298:1: ( ( 'topLeft' ) )
            // InternalMazeDsl.g:4299:1: ( 'topLeft' )
            {
            // InternalMazeDsl.g:4299:1: ( 'topLeft' )
            // InternalMazeDsl.g:4300:2: 'topLeft'
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getTopLeftKeyword_2()); 
            match(input,70,FOLLOW_2); 
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
    // InternalMazeDsl.g:4309:1: rule__PatrolZoneConfig__Group__3 : rule__PatrolZoneConfig__Group__3__Impl rule__PatrolZoneConfig__Group__4 ;
    public final void rule__PatrolZoneConfig__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4313:1: ( rule__PatrolZoneConfig__Group__3__Impl rule__PatrolZoneConfig__Group__4 )
            // InternalMazeDsl.g:4314:2: rule__PatrolZoneConfig__Group__3__Impl rule__PatrolZoneConfig__Group__4
            {
            pushFollow(FOLLOW_16);
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
    // InternalMazeDsl.g:4321:1: rule__PatrolZoneConfig__Group__3__Impl : ( '(' ) ;
    public final void rule__PatrolZoneConfig__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4325:1: ( ( '(' ) )
            // InternalMazeDsl.g:4326:1: ( '(' )
            {
            // InternalMazeDsl.g:4326:1: ( '(' )
            // InternalMazeDsl.g:4327:2: '('
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getLeftParenthesisKeyword_3()); 
            match(input,71,FOLLOW_2); 
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
    // InternalMazeDsl.g:4336:1: rule__PatrolZoneConfig__Group__4 : rule__PatrolZoneConfig__Group__4__Impl rule__PatrolZoneConfig__Group__5 ;
    public final void rule__PatrolZoneConfig__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4340:1: ( rule__PatrolZoneConfig__Group__4__Impl rule__PatrolZoneConfig__Group__5 )
            // InternalMazeDsl.g:4341:2: rule__PatrolZoneConfig__Group__4__Impl rule__PatrolZoneConfig__Group__5
            {
            pushFollow(FOLLOW_38);
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
    // InternalMazeDsl.g:4348:1: rule__PatrolZoneConfig__Group__4__Impl : ( ( rule__PatrolZoneConfig__TopLeftXAssignment_4 ) ) ;
    public final void rule__PatrolZoneConfig__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4352:1: ( ( ( rule__PatrolZoneConfig__TopLeftXAssignment_4 ) ) )
            // InternalMazeDsl.g:4353:1: ( ( rule__PatrolZoneConfig__TopLeftXAssignment_4 ) )
            {
            // InternalMazeDsl.g:4353:1: ( ( rule__PatrolZoneConfig__TopLeftXAssignment_4 ) )
            // InternalMazeDsl.g:4354:2: ( rule__PatrolZoneConfig__TopLeftXAssignment_4 )
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getTopLeftXAssignment_4()); 
            // InternalMazeDsl.g:4355:2: ( rule__PatrolZoneConfig__TopLeftXAssignment_4 )
            // InternalMazeDsl.g:4355:3: rule__PatrolZoneConfig__TopLeftXAssignment_4
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
    // InternalMazeDsl.g:4363:1: rule__PatrolZoneConfig__Group__5 : rule__PatrolZoneConfig__Group__5__Impl rule__PatrolZoneConfig__Group__6 ;
    public final void rule__PatrolZoneConfig__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4367:1: ( rule__PatrolZoneConfig__Group__5__Impl rule__PatrolZoneConfig__Group__6 )
            // InternalMazeDsl.g:4368:2: rule__PatrolZoneConfig__Group__5__Impl rule__PatrolZoneConfig__Group__6
            {
            pushFollow(FOLLOW_16);
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
    // InternalMazeDsl.g:4375:1: rule__PatrolZoneConfig__Group__5__Impl : ( ',' ) ;
    public final void rule__PatrolZoneConfig__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4379:1: ( ( ',' ) )
            // InternalMazeDsl.g:4380:1: ( ',' )
            {
            // InternalMazeDsl.g:4380:1: ( ',' )
            // InternalMazeDsl.g:4381:2: ','
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
    // InternalMazeDsl.g:4390:1: rule__PatrolZoneConfig__Group__6 : rule__PatrolZoneConfig__Group__6__Impl rule__PatrolZoneConfig__Group__7 ;
    public final void rule__PatrolZoneConfig__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4394:1: ( rule__PatrolZoneConfig__Group__6__Impl rule__PatrolZoneConfig__Group__7 )
            // InternalMazeDsl.g:4395:2: rule__PatrolZoneConfig__Group__6__Impl rule__PatrolZoneConfig__Group__7
            {
            pushFollow(FOLLOW_39);
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
    // InternalMazeDsl.g:4402:1: rule__PatrolZoneConfig__Group__6__Impl : ( ( rule__PatrolZoneConfig__TopLeftYAssignment_6 ) ) ;
    public final void rule__PatrolZoneConfig__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4406:1: ( ( ( rule__PatrolZoneConfig__TopLeftYAssignment_6 ) ) )
            // InternalMazeDsl.g:4407:1: ( ( rule__PatrolZoneConfig__TopLeftYAssignment_6 ) )
            {
            // InternalMazeDsl.g:4407:1: ( ( rule__PatrolZoneConfig__TopLeftYAssignment_6 ) )
            // InternalMazeDsl.g:4408:2: ( rule__PatrolZoneConfig__TopLeftYAssignment_6 )
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getTopLeftYAssignment_6()); 
            // InternalMazeDsl.g:4409:2: ( rule__PatrolZoneConfig__TopLeftYAssignment_6 )
            // InternalMazeDsl.g:4409:3: rule__PatrolZoneConfig__TopLeftYAssignment_6
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
    // InternalMazeDsl.g:4417:1: rule__PatrolZoneConfig__Group__7 : rule__PatrolZoneConfig__Group__7__Impl rule__PatrolZoneConfig__Group__8 ;
    public final void rule__PatrolZoneConfig__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4421:1: ( rule__PatrolZoneConfig__Group__7__Impl rule__PatrolZoneConfig__Group__8 )
            // InternalMazeDsl.g:4422:2: rule__PatrolZoneConfig__Group__7__Impl rule__PatrolZoneConfig__Group__8
            {
            pushFollow(FOLLOW_40);
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
    // InternalMazeDsl.g:4429:1: rule__PatrolZoneConfig__Group__7__Impl : ( ')' ) ;
    public final void rule__PatrolZoneConfig__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4433:1: ( ( ')' ) )
            // InternalMazeDsl.g:4434:1: ( ')' )
            {
            // InternalMazeDsl.g:4434:1: ( ')' )
            // InternalMazeDsl.g:4435:2: ')'
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getRightParenthesisKeyword_7()); 
            match(input,72,FOLLOW_2); 
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
    // InternalMazeDsl.g:4444:1: rule__PatrolZoneConfig__Group__8 : rule__PatrolZoneConfig__Group__8__Impl rule__PatrolZoneConfig__Group__9 ;
    public final void rule__PatrolZoneConfig__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4448:1: ( rule__PatrolZoneConfig__Group__8__Impl rule__PatrolZoneConfig__Group__9 )
            // InternalMazeDsl.g:4449:2: rule__PatrolZoneConfig__Group__8__Impl rule__PatrolZoneConfig__Group__9
            {
            pushFollow(FOLLOW_16);
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
    // InternalMazeDsl.g:4456:1: rule__PatrolZoneConfig__Group__8__Impl : ( 'width' ) ;
    public final void rule__PatrolZoneConfig__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4460:1: ( ( 'width' ) )
            // InternalMazeDsl.g:4461:1: ( 'width' )
            {
            // InternalMazeDsl.g:4461:1: ( 'width' )
            // InternalMazeDsl.g:4462:2: 'width'
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getWidthKeyword_8()); 
            match(input,73,FOLLOW_2); 
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
    // InternalMazeDsl.g:4471:1: rule__PatrolZoneConfig__Group__9 : rule__PatrolZoneConfig__Group__9__Impl rule__PatrolZoneConfig__Group__10 ;
    public final void rule__PatrolZoneConfig__Group__9() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4475:1: ( rule__PatrolZoneConfig__Group__9__Impl rule__PatrolZoneConfig__Group__10 )
            // InternalMazeDsl.g:4476:2: rule__PatrolZoneConfig__Group__9__Impl rule__PatrolZoneConfig__Group__10
            {
            pushFollow(FOLLOW_41);
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
    // InternalMazeDsl.g:4483:1: rule__PatrolZoneConfig__Group__9__Impl : ( ( rule__PatrolZoneConfig__WidthAssignment_9 ) ) ;
    public final void rule__PatrolZoneConfig__Group__9__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4487:1: ( ( ( rule__PatrolZoneConfig__WidthAssignment_9 ) ) )
            // InternalMazeDsl.g:4488:1: ( ( rule__PatrolZoneConfig__WidthAssignment_9 ) )
            {
            // InternalMazeDsl.g:4488:1: ( ( rule__PatrolZoneConfig__WidthAssignment_9 ) )
            // InternalMazeDsl.g:4489:2: ( rule__PatrolZoneConfig__WidthAssignment_9 )
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getWidthAssignment_9()); 
            // InternalMazeDsl.g:4490:2: ( rule__PatrolZoneConfig__WidthAssignment_9 )
            // InternalMazeDsl.g:4490:3: rule__PatrolZoneConfig__WidthAssignment_9
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
    // InternalMazeDsl.g:4498:1: rule__PatrolZoneConfig__Group__10 : rule__PatrolZoneConfig__Group__10__Impl rule__PatrolZoneConfig__Group__11 ;
    public final void rule__PatrolZoneConfig__Group__10() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4502:1: ( rule__PatrolZoneConfig__Group__10__Impl rule__PatrolZoneConfig__Group__11 )
            // InternalMazeDsl.g:4503:2: rule__PatrolZoneConfig__Group__10__Impl rule__PatrolZoneConfig__Group__11
            {
            pushFollow(FOLLOW_16);
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
    // InternalMazeDsl.g:4510:1: rule__PatrolZoneConfig__Group__10__Impl : ( 'height' ) ;
    public final void rule__PatrolZoneConfig__Group__10__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4514:1: ( ( 'height' ) )
            // InternalMazeDsl.g:4515:1: ( 'height' )
            {
            // InternalMazeDsl.g:4515:1: ( 'height' )
            // InternalMazeDsl.g:4516:2: 'height'
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getHeightKeyword_10()); 
            match(input,74,FOLLOW_2); 
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
    // InternalMazeDsl.g:4525:1: rule__PatrolZoneConfig__Group__11 : rule__PatrolZoneConfig__Group__11__Impl rule__PatrolZoneConfig__Group__12 ;
    public final void rule__PatrolZoneConfig__Group__11() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4529:1: ( rule__PatrolZoneConfig__Group__11__Impl rule__PatrolZoneConfig__Group__12 )
            // InternalMazeDsl.g:4530:2: rule__PatrolZoneConfig__Group__11__Impl rule__PatrolZoneConfig__Group__12
            {
            pushFollow(FOLLOW_35);
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
    // InternalMazeDsl.g:4537:1: rule__PatrolZoneConfig__Group__11__Impl : ( ( rule__PatrolZoneConfig__HeightAssignment_11 ) ) ;
    public final void rule__PatrolZoneConfig__Group__11__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4541:1: ( ( ( rule__PatrolZoneConfig__HeightAssignment_11 ) ) )
            // InternalMazeDsl.g:4542:1: ( ( rule__PatrolZoneConfig__HeightAssignment_11 ) )
            {
            // InternalMazeDsl.g:4542:1: ( ( rule__PatrolZoneConfig__HeightAssignment_11 ) )
            // InternalMazeDsl.g:4543:2: ( rule__PatrolZoneConfig__HeightAssignment_11 )
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getHeightAssignment_11()); 
            // InternalMazeDsl.g:4544:2: ( rule__PatrolZoneConfig__HeightAssignment_11 )
            // InternalMazeDsl.g:4544:3: rule__PatrolZoneConfig__HeightAssignment_11
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
    // InternalMazeDsl.g:4552:1: rule__PatrolZoneConfig__Group__12 : rule__PatrolZoneConfig__Group__12__Impl ;
    public final void rule__PatrolZoneConfig__Group__12() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4556:1: ( rule__PatrolZoneConfig__Group__12__Impl )
            // InternalMazeDsl.g:4557:2: rule__PatrolZoneConfig__Group__12__Impl
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
    // InternalMazeDsl.g:4563:1: rule__PatrolZoneConfig__Group__12__Impl : ( '}' ) ;
    public final void rule__PatrolZoneConfig__Group__12__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4567:1: ( ( '}' ) )
            // InternalMazeDsl.g:4568:1: ( '}' )
            {
            // InternalMazeDsl.g:4568:1: ( '}' )
            // InternalMazeDsl.g:4569:2: '}'
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getRightCurlyBracketKeyword_12()); 
            match(input,32,FOLLOW_2); 
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
    // InternalMazeDsl.g:4579:1: rule__Waypoint__Group__0 : rule__Waypoint__Group__0__Impl rule__Waypoint__Group__1 ;
    public final void rule__Waypoint__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4583:1: ( rule__Waypoint__Group__0__Impl rule__Waypoint__Group__1 )
            // InternalMazeDsl.g:4584:2: rule__Waypoint__Group__0__Impl rule__Waypoint__Group__1
            {
            pushFollow(FOLLOW_16);
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
    // InternalMazeDsl.g:4591:1: rule__Waypoint__Group__0__Impl : ( '(' ) ;
    public final void rule__Waypoint__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4595:1: ( ( '(' ) )
            // InternalMazeDsl.g:4596:1: ( '(' )
            {
            // InternalMazeDsl.g:4596:1: ( '(' )
            // InternalMazeDsl.g:4597:2: '('
            {
             before(grammarAccess.getWaypointAccess().getLeftParenthesisKeyword_0()); 
            match(input,71,FOLLOW_2); 
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
    // InternalMazeDsl.g:4606:1: rule__Waypoint__Group__1 : rule__Waypoint__Group__1__Impl rule__Waypoint__Group__2 ;
    public final void rule__Waypoint__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4610:1: ( rule__Waypoint__Group__1__Impl rule__Waypoint__Group__2 )
            // InternalMazeDsl.g:4611:2: rule__Waypoint__Group__1__Impl rule__Waypoint__Group__2
            {
            pushFollow(FOLLOW_38);
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
    // InternalMazeDsl.g:4618:1: rule__Waypoint__Group__1__Impl : ( ( rule__Waypoint__XAssignment_1 ) ) ;
    public final void rule__Waypoint__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4622:1: ( ( ( rule__Waypoint__XAssignment_1 ) ) )
            // InternalMazeDsl.g:4623:1: ( ( rule__Waypoint__XAssignment_1 ) )
            {
            // InternalMazeDsl.g:4623:1: ( ( rule__Waypoint__XAssignment_1 ) )
            // InternalMazeDsl.g:4624:2: ( rule__Waypoint__XAssignment_1 )
            {
             before(grammarAccess.getWaypointAccess().getXAssignment_1()); 
            // InternalMazeDsl.g:4625:2: ( rule__Waypoint__XAssignment_1 )
            // InternalMazeDsl.g:4625:3: rule__Waypoint__XAssignment_1
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
    // InternalMazeDsl.g:4633:1: rule__Waypoint__Group__2 : rule__Waypoint__Group__2__Impl rule__Waypoint__Group__3 ;
    public final void rule__Waypoint__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4637:1: ( rule__Waypoint__Group__2__Impl rule__Waypoint__Group__3 )
            // InternalMazeDsl.g:4638:2: rule__Waypoint__Group__2__Impl rule__Waypoint__Group__3
            {
            pushFollow(FOLLOW_16);
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
    // InternalMazeDsl.g:4645:1: rule__Waypoint__Group__2__Impl : ( ',' ) ;
    public final void rule__Waypoint__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4649:1: ( ( ',' ) )
            // InternalMazeDsl.g:4650:1: ( ',' )
            {
            // InternalMazeDsl.g:4650:1: ( ',' )
            // InternalMazeDsl.g:4651:2: ','
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
    // InternalMazeDsl.g:4660:1: rule__Waypoint__Group__3 : rule__Waypoint__Group__3__Impl rule__Waypoint__Group__4 ;
    public final void rule__Waypoint__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4664:1: ( rule__Waypoint__Group__3__Impl rule__Waypoint__Group__4 )
            // InternalMazeDsl.g:4665:2: rule__Waypoint__Group__3__Impl rule__Waypoint__Group__4
            {
            pushFollow(FOLLOW_39);
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
    // InternalMazeDsl.g:4672:1: rule__Waypoint__Group__3__Impl : ( ( rule__Waypoint__YAssignment_3 ) ) ;
    public final void rule__Waypoint__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4676:1: ( ( ( rule__Waypoint__YAssignment_3 ) ) )
            // InternalMazeDsl.g:4677:1: ( ( rule__Waypoint__YAssignment_3 ) )
            {
            // InternalMazeDsl.g:4677:1: ( ( rule__Waypoint__YAssignment_3 ) )
            // InternalMazeDsl.g:4678:2: ( rule__Waypoint__YAssignment_3 )
            {
             before(grammarAccess.getWaypointAccess().getYAssignment_3()); 
            // InternalMazeDsl.g:4679:2: ( rule__Waypoint__YAssignment_3 )
            // InternalMazeDsl.g:4679:3: rule__Waypoint__YAssignment_3
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
    // InternalMazeDsl.g:4687:1: rule__Waypoint__Group__4 : rule__Waypoint__Group__4__Impl rule__Waypoint__Group__5 ;
    public final void rule__Waypoint__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4691:1: ( rule__Waypoint__Group__4__Impl rule__Waypoint__Group__5 )
            // InternalMazeDsl.g:4692:2: rule__Waypoint__Group__4__Impl rule__Waypoint__Group__5
            {
            pushFollow(FOLLOW_42);
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
    // InternalMazeDsl.g:4699:1: rule__Waypoint__Group__4__Impl : ( ')' ) ;
    public final void rule__Waypoint__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4703:1: ( ( ')' ) )
            // InternalMazeDsl.g:4704:1: ( ')' )
            {
            // InternalMazeDsl.g:4704:1: ( ')' )
            // InternalMazeDsl.g:4705:2: ')'
            {
             before(grammarAccess.getWaypointAccess().getRightParenthesisKeyword_4()); 
            match(input,72,FOLLOW_2); 
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
    // InternalMazeDsl.g:4714:1: rule__Waypoint__Group__5 : rule__Waypoint__Group__5__Impl ;
    public final void rule__Waypoint__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4718:1: ( rule__Waypoint__Group__5__Impl )
            // InternalMazeDsl.g:4719:2: rule__Waypoint__Group__5__Impl
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
    // InternalMazeDsl.g:4725:1: rule__Waypoint__Group__5__Impl : ( ( rule__Waypoint__Group_5__0 )? ) ;
    public final void rule__Waypoint__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4729:1: ( ( ( rule__Waypoint__Group_5__0 )? ) )
            // InternalMazeDsl.g:4730:1: ( ( rule__Waypoint__Group_5__0 )? )
            {
            // InternalMazeDsl.g:4730:1: ( ( rule__Waypoint__Group_5__0 )? )
            // InternalMazeDsl.g:4731:2: ( rule__Waypoint__Group_5__0 )?
            {
             before(grammarAccess.getWaypointAccess().getGroup_5()); 
            // InternalMazeDsl.g:4732:2: ( rule__Waypoint__Group_5__0 )?
            int alt43=2;
            int LA43_0 = input.LA(1);

            if ( (LA43_0==75) ) {
                alt43=1;
            }
            switch (alt43) {
                case 1 :
                    // InternalMazeDsl.g:4732:3: rule__Waypoint__Group_5__0
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
    // InternalMazeDsl.g:4741:1: rule__Waypoint__Group_5__0 : rule__Waypoint__Group_5__0__Impl rule__Waypoint__Group_5__1 ;
    public final void rule__Waypoint__Group_5__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4745:1: ( rule__Waypoint__Group_5__0__Impl rule__Waypoint__Group_5__1 )
            // InternalMazeDsl.g:4746:2: rule__Waypoint__Group_5__0__Impl rule__Waypoint__Group_5__1
            {
            pushFollow(FOLLOW_16);
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
    // InternalMazeDsl.g:4753:1: rule__Waypoint__Group_5__0__Impl : ( ':' ) ;
    public final void rule__Waypoint__Group_5__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4757:1: ( ( ':' ) )
            // InternalMazeDsl.g:4758:1: ( ':' )
            {
            // InternalMazeDsl.g:4758:1: ( ':' )
            // InternalMazeDsl.g:4759:2: ':'
            {
             before(grammarAccess.getWaypointAccess().getColonKeyword_5_0()); 
            match(input,75,FOLLOW_2); 
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
    // InternalMazeDsl.g:4768:1: rule__Waypoint__Group_5__1 : rule__Waypoint__Group_5__1__Impl rule__Waypoint__Group_5__2 ;
    public final void rule__Waypoint__Group_5__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4772:1: ( rule__Waypoint__Group_5__1__Impl rule__Waypoint__Group_5__2 )
            // InternalMazeDsl.g:4773:2: rule__Waypoint__Group_5__1__Impl rule__Waypoint__Group_5__2
            {
            pushFollow(FOLLOW_43);
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
    // InternalMazeDsl.g:4780:1: rule__Waypoint__Group_5__1__Impl : ( ( rule__Waypoint__WaitTimeAssignment_5_1 ) ) ;
    public final void rule__Waypoint__Group_5__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4784:1: ( ( ( rule__Waypoint__WaitTimeAssignment_5_1 ) ) )
            // InternalMazeDsl.g:4785:1: ( ( rule__Waypoint__WaitTimeAssignment_5_1 ) )
            {
            // InternalMazeDsl.g:4785:1: ( ( rule__Waypoint__WaitTimeAssignment_5_1 ) )
            // InternalMazeDsl.g:4786:2: ( rule__Waypoint__WaitTimeAssignment_5_1 )
            {
             before(grammarAccess.getWaypointAccess().getWaitTimeAssignment_5_1()); 
            // InternalMazeDsl.g:4787:2: ( rule__Waypoint__WaitTimeAssignment_5_1 )
            // InternalMazeDsl.g:4787:3: rule__Waypoint__WaitTimeAssignment_5_1
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
    // InternalMazeDsl.g:4795:1: rule__Waypoint__Group_5__2 : rule__Waypoint__Group_5__2__Impl ;
    public final void rule__Waypoint__Group_5__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4799:1: ( rule__Waypoint__Group_5__2__Impl )
            // InternalMazeDsl.g:4800:2: rule__Waypoint__Group_5__2__Impl
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
    // InternalMazeDsl.g:4806:1: rule__Waypoint__Group_5__2__Impl : ( 'ms' ) ;
    public final void rule__Waypoint__Group_5__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4810:1: ( ( 'ms' ) )
            // InternalMazeDsl.g:4811:1: ( 'ms' )
            {
            // InternalMazeDsl.g:4811:1: ( 'ms' )
            // InternalMazeDsl.g:4812:2: 'ms'
            {
             before(grammarAccess.getWaypointAccess().getMsKeyword_5_2()); 
            match(input,76,FOLLOW_2); 
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
    // InternalMazeDsl.g:4822:1: rule__LootTableConfig__Group__0 : rule__LootTableConfig__Group__0__Impl rule__LootTableConfig__Group__1 ;
    public final void rule__LootTableConfig__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4826:1: ( rule__LootTableConfig__Group__0__Impl rule__LootTableConfig__Group__1 )
            // InternalMazeDsl.g:4827:2: rule__LootTableConfig__Group__0__Impl rule__LootTableConfig__Group__1
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
    // InternalMazeDsl.g:4834:1: rule__LootTableConfig__Group__0__Impl : ( 'loot-table' ) ;
    public final void rule__LootTableConfig__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4838:1: ( ( 'loot-table' ) )
            // InternalMazeDsl.g:4839:1: ( 'loot-table' )
            {
            // InternalMazeDsl.g:4839:1: ( 'loot-table' )
            // InternalMazeDsl.g:4840:2: 'loot-table'
            {
             before(grammarAccess.getLootTableConfigAccess().getLootTableKeyword_0()); 
            match(input,77,FOLLOW_2); 
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
    // InternalMazeDsl.g:4849:1: rule__LootTableConfig__Group__1 : rule__LootTableConfig__Group__1__Impl rule__LootTableConfig__Group__2 ;
    public final void rule__LootTableConfig__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4853:1: ( rule__LootTableConfig__Group__1__Impl rule__LootTableConfig__Group__2 )
            // InternalMazeDsl.g:4854:2: rule__LootTableConfig__Group__1__Impl rule__LootTableConfig__Group__2
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
    // InternalMazeDsl.g:4861:1: rule__LootTableConfig__Group__1__Impl : ( ( rule__LootTableConfig__NameAssignment_1 ) ) ;
    public final void rule__LootTableConfig__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4865:1: ( ( ( rule__LootTableConfig__NameAssignment_1 ) ) )
            // InternalMazeDsl.g:4866:1: ( ( rule__LootTableConfig__NameAssignment_1 ) )
            {
            // InternalMazeDsl.g:4866:1: ( ( rule__LootTableConfig__NameAssignment_1 ) )
            // InternalMazeDsl.g:4867:2: ( rule__LootTableConfig__NameAssignment_1 )
            {
             before(grammarAccess.getLootTableConfigAccess().getNameAssignment_1()); 
            // InternalMazeDsl.g:4868:2: ( rule__LootTableConfig__NameAssignment_1 )
            // InternalMazeDsl.g:4868:3: rule__LootTableConfig__NameAssignment_1
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
    // InternalMazeDsl.g:4876:1: rule__LootTableConfig__Group__2 : rule__LootTableConfig__Group__2__Impl rule__LootTableConfig__Group__3 ;
    public final void rule__LootTableConfig__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4880:1: ( rule__LootTableConfig__Group__2__Impl rule__LootTableConfig__Group__3 )
            // InternalMazeDsl.g:4881:2: rule__LootTableConfig__Group__2__Impl rule__LootTableConfig__Group__3
            {
            pushFollow(FOLLOW_44);
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
    // InternalMazeDsl.g:4888:1: rule__LootTableConfig__Group__2__Impl : ( '{' ) ;
    public final void rule__LootTableConfig__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4892:1: ( ( '{' ) )
            // InternalMazeDsl.g:4893:1: ( '{' )
            {
            // InternalMazeDsl.g:4893:1: ( '{' )
            // InternalMazeDsl.g:4894:2: '{'
            {
             before(grammarAccess.getLootTableConfigAccess().getLeftCurlyBracketKeyword_2()); 
            match(input,31,FOLLOW_2); 
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
    // InternalMazeDsl.g:4903:1: rule__LootTableConfig__Group__3 : rule__LootTableConfig__Group__3__Impl rule__LootTableConfig__Group__4 ;
    public final void rule__LootTableConfig__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4907:1: ( rule__LootTableConfig__Group__3__Impl rule__LootTableConfig__Group__4 )
            // InternalMazeDsl.g:4908:2: rule__LootTableConfig__Group__3__Impl rule__LootTableConfig__Group__4
            {
            pushFollow(FOLLOW_44);
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
    // InternalMazeDsl.g:4915:1: rule__LootTableConfig__Group__3__Impl : ( ( rule__LootTableConfig__Group_3__0 )? ) ;
    public final void rule__LootTableConfig__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4919:1: ( ( ( rule__LootTableConfig__Group_3__0 )? ) )
            // InternalMazeDsl.g:4920:1: ( ( rule__LootTableConfig__Group_3__0 )? )
            {
            // InternalMazeDsl.g:4920:1: ( ( rule__LootTableConfig__Group_3__0 )? )
            // InternalMazeDsl.g:4921:2: ( rule__LootTableConfig__Group_3__0 )?
            {
             before(grammarAccess.getLootTableConfigAccess().getGroup_3()); 
            // InternalMazeDsl.g:4922:2: ( rule__LootTableConfig__Group_3__0 )?
            int alt44=2;
            int LA44_0 = input.LA(1);

            if ( (LA44_0==78) ) {
                alt44=1;
            }
            switch (alt44) {
                case 1 :
                    // InternalMazeDsl.g:4922:3: rule__LootTableConfig__Group_3__0
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
    // InternalMazeDsl.g:4930:1: rule__LootTableConfig__Group__4 : rule__LootTableConfig__Group__4__Impl rule__LootTableConfig__Group__5 ;
    public final void rule__LootTableConfig__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4934:1: ( rule__LootTableConfig__Group__4__Impl rule__LootTableConfig__Group__5 )
            // InternalMazeDsl.g:4935:2: rule__LootTableConfig__Group__4__Impl rule__LootTableConfig__Group__5
            {
            pushFollow(FOLLOW_35);
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
    // InternalMazeDsl.g:4942:1: rule__LootTableConfig__Group__4__Impl : ( ( ( rule__LootTableConfig__ItemsAssignment_4 ) ) ( ( rule__LootTableConfig__ItemsAssignment_4 )* ) ) ;
    public final void rule__LootTableConfig__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4946:1: ( ( ( ( rule__LootTableConfig__ItemsAssignment_4 ) ) ( ( rule__LootTableConfig__ItemsAssignment_4 )* ) ) )
            // InternalMazeDsl.g:4947:1: ( ( ( rule__LootTableConfig__ItemsAssignment_4 ) ) ( ( rule__LootTableConfig__ItemsAssignment_4 )* ) )
            {
            // InternalMazeDsl.g:4947:1: ( ( ( rule__LootTableConfig__ItemsAssignment_4 ) ) ( ( rule__LootTableConfig__ItemsAssignment_4 )* ) )
            // InternalMazeDsl.g:4948:2: ( ( rule__LootTableConfig__ItemsAssignment_4 ) ) ( ( rule__LootTableConfig__ItemsAssignment_4 )* )
            {
            // InternalMazeDsl.g:4948:2: ( ( rule__LootTableConfig__ItemsAssignment_4 ) )
            // InternalMazeDsl.g:4949:3: ( rule__LootTableConfig__ItemsAssignment_4 )
            {
             before(grammarAccess.getLootTableConfigAccess().getItemsAssignment_4()); 
            // InternalMazeDsl.g:4950:3: ( rule__LootTableConfig__ItemsAssignment_4 )
            // InternalMazeDsl.g:4950:4: rule__LootTableConfig__ItemsAssignment_4
            {
            pushFollow(FOLLOW_45);
            rule__LootTableConfig__ItemsAssignment_4();

            state._fsp--;


            }

             after(grammarAccess.getLootTableConfigAccess().getItemsAssignment_4()); 

            }

            // InternalMazeDsl.g:4953:2: ( ( rule__LootTableConfig__ItemsAssignment_4 )* )
            // InternalMazeDsl.g:4954:3: ( rule__LootTableConfig__ItemsAssignment_4 )*
            {
             before(grammarAccess.getLootTableConfigAccess().getItemsAssignment_4()); 
            // InternalMazeDsl.g:4955:3: ( rule__LootTableConfig__ItemsAssignment_4 )*
            loop45:
            do {
                int alt45=2;
                int LA45_0 = input.LA(1);

                if ( (LA45_0==79) ) {
                    alt45=1;
                }


                switch (alt45) {
            	case 1 :
            	    // InternalMazeDsl.g:4955:4: rule__LootTableConfig__ItemsAssignment_4
            	    {
            	    pushFollow(FOLLOW_45);
            	    rule__LootTableConfig__ItemsAssignment_4();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop45;
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
    // InternalMazeDsl.g:4964:1: rule__LootTableConfig__Group__5 : rule__LootTableConfig__Group__5__Impl ;
    public final void rule__LootTableConfig__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4968:1: ( rule__LootTableConfig__Group__5__Impl )
            // InternalMazeDsl.g:4969:2: rule__LootTableConfig__Group__5__Impl
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
    // InternalMazeDsl.g:4975:1: rule__LootTableConfig__Group__5__Impl : ( '}' ) ;
    public final void rule__LootTableConfig__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4979:1: ( ( '}' ) )
            // InternalMazeDsl.g:4980:1: ( '}' )
            {
            // InternalMazeDsl.g:4980:1: ( '}' )
            // InternalMazeDsl.g:4981:2: '}'
            {
             before(grammarAccess.getLootTableConfigAccess().getRightCurlyBracketKeyword_5()); 
            match(input,32,FOLLOW_2); 
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
    // InternalMazeDsl.g:4991:1: rule__LootTableConfig__Group_3__0 : rule__LootTableConfig__Group_3__0__Impl rule__LootTableConfig__Group_3__1 ;
    public final void rule__LootTableConfig__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4995:1: ( rule__LootTableConfig__Group_3__0__Impl rule__LootTableConfig__Group_3__1 )
            // InternalMazeDsl.g:4996:2: rule__LootTableConfig__Group_3__0__Impl rule__LootTableConfig__Group_3__1
            {
            pushFollow(FOLLOW_16);
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
    // InternalMazeDsl.g:5003:1: rule__LootTableConfig__Group_3__0__Impl : ( 'capacity' ) ;
    public final void rule__LootTableConfig__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5007:1: ( ( 'capacity' ) )
            // InternalMazeDsl.g:5008:1: ( 'capacity' )
            {
            // InternalMazeDsl.g:5008:1: ( 'capacity' )
            // InternalMazeDsl.g:5009:2: 'capacity'
            {
             before(grammarAccess.getLootTableConfigAccess().getCapacityKeyword_3_0()); 
            match(input,78,FOLLOW_2); 
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
    // InternalMazeDsl.g:5018:1: rule__LootTableConfig__Group_3__1 : rule__LootTableConfig__Group_3__1__Impl ;
    public final void rule__LootTableConfig__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5022:1: ( rule__LootTableConfig__Group_3__1__Impl )
            // InternalMazeDsl.g:5023:2: rule__LootTableConfig__Group_3__1__Impl
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
    // InternalMazeDsl.g:5029:1: rule__LootTableConfig__Group_3__1__Impl : ( ( rule__LootTableConfig__CapacityAssignment_3_1 ) ) ;
    public final void rule__LootTableConfig__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5033:1: ( ( ( rule__LootTableConfig__CapacityAssignment_3_1 ) ) )
            // InternalMazeDsl.g:5034:1: ( ( rule__LootTableConfig__CapacityAssignment_3_1 ) )
            {
            // InternalMazeDsl.g:5034:1: ( ( rule__LootTableConfig__CapacityAssignment_3_1 ) )
            // InternalMazeDsl.g:5035:2: ( rule__LootTableConfig__CapacityAssignment_3_1 )
            {
             before(grammarAccess.getLootTableConfigAccess().getCapacityAssignment_3_1()); 
            // InternalMazeDsl.g:5036:2: ( rule__LootTableConfig__CapacityAssignment_3_1 )
            // InternalMazeDsl.g:5036:3: rule__LootTableConfig__CapacityAssignment_3_1
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
    // InternalMazeDsl.g:5045:1: rule__LootItemConfig__Group__0 : rule__LootItemConfig__Group__0__Impl rule__LootItemConfig__Group__1 ;
    public final void rule__LootItemConfig__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5049:1: ( rule__LootItemConfig__Group__0__Impl rule__LootItemConfig__Group__1 )
            // InternalMazeDsl.g:5050:2: rule__LootItemConfig__Group__0__Impl rule__LootItemConfig__Group__1
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
    // InternalMazeDsl.g:5057:1: rule__LootItemConfig__Group__0__Impl : ( 'item' ) ;
    public final void rule__LootItemConfig__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5061:1: ( ( 'item' ) )
            // InternalMazeDsl.g:5062:1: ( 'item' )
            {
            // InternalMazeDsl.g:5062:1: ( 'item' )
            // InternalMazeDsl.g:5063:2: 'item'
            {
             before(grammarAccess.getLootItemConfigAccess().getItemKeyword_0()); 
            match(input,79,FOLLOW_2); 
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
    // InternalMazeDsl.g:5072:1: rule__LootItemConfig__Group__1 : rule__LootItemConfig__Group__1__Impl rule__LootItemConfig__Group__2 ;
    public final void rule__LootItemConfig__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5076:1: ( rule__LootItemConfig__Group__1__Impl rule__LootItemConfig__Group__2 )
            // InternalMazeDsl.g:5077:2: rule__LootItemConfig__Group__1__Impl rule__LootItemConfig__Group__2
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
    // InternalMazeDsl.g:5084:1: rule__LootItemConfig__Group__1__Impl : ( ( rule__LootItemConfig__NameAssignment_1 ) ) ;
    public final void rule__LootItemConfig__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5088:1: ( ( ( rule__LootItemConfig__NameAssignment_1 ) ) )
            // InternalMazeDsl.g:5089:1: ( ( rule__LootItemConfig__NameAssignment_1 ) )
            {
            // InternalMazeDsl.g:5089:1: ( ( rule__LootItemConfig__NameAssignment_1 ) )
            // InternalMazeDsl.g:5090:2: ( rule__LootItemConfig__NameAssignment_1 )
            {
             before(grammarAccess.getLootItemConfigAccess().getNameAssignment_1()); 
            // InternalMazeDsl.g:5091:2: ( rule__LootItemConfig__NameAssignment_1 )
            // InternalMazeDsl.g:5091:3: rule__LootItemConfig__NameAssignment_1
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
    // InternalMazeDsl.g:5099:1: rule__LootItemConfig__Group__2 : rule__LootItemConfig__Group__2__Impl rule__LootItemConfig__Group__3 ;
    public final void rule__LootItemConfig__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5103:1: ( rule__LootItemConfig__Group__2__Impl rule__LootItemConfig__Group__3 )
            // InternalMazeDsl.g:5104:2: rule__LootItemConfig__Group__2__Impl rule__LootItemConfig__Group__3
            {
            pushFollow(FOLLOW_19);
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
    // InternalMazeDsl.g:5111:1: rule__LootItemConfig__Group__2__Impl : ( '{' ) ;
    public final void rule__LootItemConfig__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5115:1: ( ( '{' ) )
            // InternalMazeDsl.g:5116:1: ( '{' )
            {
            // InternalMazeDsl.g:5116:1: ( '{' )
            // InternalMazeDsl.g:5117:2: '{'
            {
             before(grammarAccess.getLootItemConfigAccess().getLeftCurlyBracketKeyword_2()); 
            match(input,31,FOLLOW_2); 
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
    // InternalMazeDsl.g:5126:1: rule__LootItemConfig__Group__3 : rule__LootItemConfig__Group__3__Impl rule__LootItemConfig__Group__4 ;
    public final void rule__LootItemConfig__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5130:1: ( rule__LootItemConfig__Group__3__Impl rule__LootItemConfig__Group__4 )
            // InternalMazeDsl.g:5131:2: rule__LootItemConfig__Group__3__Impl rule__LootItemConfig__Group__4
            {
            pushFollow(FOLLOW_46);
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
    // InternalMazeDsl.g:5138:1: rule__LootItemConfig__Group__3__Impl : ( 'type' ) ;
    public final void rule__LootItemConfig__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5142:1: ( ( 'type' ) )
            // InternalMazeDsl.g:5143:1: ( 'type' )
            {
            // InternalMazeDsl.g:5143:1: ( 'type' )
            // InternalMazeDsl.g:5144:2: 'type'
            {
             before(grammarAccess.getLootItemConfigAccess().getTypeKeyword_3()); 
            match(input,43,FOLLOW_2); 
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
    // InternalMazeDsl.g:5153:1: rule__LootItemConfig__Group__4 : rule__LootItemConfig__Group__4__Impl rule__LootItemConfig__Group__5 ;
    public final void rule__LootItemConfig__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5157:1: ( rule__LootItemConfig__Group__4__Impl rule__LootItemConfig__Group__5 )
            // InternalMazeDsl.g:5158:2: rule__LootItemConfig__Group__4__Impl rule__LootItemConfig__Group__5
            {
            pushFollow(FOLLOW_47);
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
    // InternalMazeDsl.g:5165:1: rule__LootItemConfig__Group__4__Impl : ( ( rule__LootItemConfig__TypeAssignment_4 ) ) ;
    public final void rule__LootItemConfig__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5169:1: ( ( ( rule__LootItemConfig__TypeAssignment_4 ) ) )
            // InternalMazeDsl.g:5170:1: ( ( rule__LootItemConfig__TypeAssignment_4 ) )
            {
            // InternalMazeDsl.g:5170:1: ( ( rule__LootItemConfig__TypeAssignment_4 ) )
            // InternalMazeDsl.g:5171:2: ( rule__LootItemConfig__TypeAssignment_4 )
            {
             before(grammarAccess.getLootItemConfigAccess().getTypeAssignment_4()); 
            // InternalMazeDsl.g:5172:2: ( rule__LootItemConfig__TypeAssignment_4 )
            // InternalMazeDsl.g:5172:3: rule__LootItemConfig__TypeAssignment_4
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
    // InternalMazeDsl.g:5180:1: rule__LootItemConfig__Group__5 : rule__LootItemConfig__Group__5__Impl rule__LootItemConfig__Group__6 ;
    public final void rule__LootItemConfig__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5184:1: ( rule__LootItemConfig__Group__5__Impl rule__LootItemConfig__Group__6 )
            // InternalMazeDsl.g:5185:2: rule__LootItemConfig__Group__5__Impl rule__LootItemConfig__Group__6
            {
            pushFollow(FOLLOW_16);
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
    // InternalMazeDsl.g:5192:1: rule__LootItemConfig__Group__5__Impl : ( 'value' ) ;
    public final void rule__LootItemConfig__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5196:1: ( ( 'value' ) )
            // InternalMazeDsl.g:5197:1: ( 'value' )
            {
            // InternalMazeDsl.g:5197:1: ( 'value' )
            // InternalMazeDsl.g:5198:2: 'value'
            {
             before(grammarAccess.getLootItemConfigAccess().getValueKeyword_5()); 
            match(input,80,FOLLOW_2); 
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
    // InternalMazeDsl.g:5207:1: rule__LootItemConfig__Group__6 : rule__LootItemConfig__Group__6__Impl rule__LootItemConfig__Group__7 ;
    public final void rule__LootItemConfig__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5211:1: ( rule__LootItemConfig__Group__6__Impl rule__LootItemConfig__Group__7 )
            // InternalMazeDsl.g:5212:2: rule__LootItemConfig__Group__6__Impl rule__LootItemConfig__Group__7
            {
            pushFollow(FOLLOW_48);
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
    // InternalMazeDsl.g:5219:1: rule__LootItemConfig__Group__6__Impl : ( ( rule__LootItemConfig__ValueAssignment_6 ) ) ;
    public final void rule__LootItemConfig__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5223:1: ( ( ( rule__LootItemConfig__ValueAssignment_6 ) ) )
            // InternalMazeDsl.g:5224:1: ( ( rule__LootItemConfig__ValueAssignment_6 ) )
            {
            // InternalMazeDsl.g:5224:1: ( ( rule__LootItemConfig__ValueAssignment_6 ) )
            // InternalMazeDsl.g:5225:2: ( rule__LootItemConfig__ValueAssignment_6 )
            {
             before(grammarAccess.getLootItemConfigAccess().getValueAssignment_6()); 
            // InternalMazeDsl.g:5226:2: ( rule__LootItemConfig__ValueAssignment_6 )
            // InternalMazeDsl.g:5226:3: rule__LootItemConfig__ValueAssignment_6
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
    // InternalMazeDsl.g:5234:1: rule__LootItemConfig__Group__7 : rule__LootItemConfig__Group__7__Impl rule__LootItemConfig__Group__8 ;
    public final void rule__LootItemConfig__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5238:1: ( rule__LootItemConfig__Group__7__Impl rule__LootItemConfig__Group__8 )
            // InternalMazeDsl.g:5239:2: rule__LootItemConfig__Group__7__Impl rule__LootItemConfig__Group__8
            {
            pushFollow(FOLLOW_48);
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
    // InternalMazeDsl.g:5246:1: rule__LootItemConfig__Group__7__Impl : ( ( rule__LootItemConfig__Group_7__0 )? ) ;
    public final void rule__LootItemConfig__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5250:1: ( ( ( rule__LootItemConfig__Group_7__0 )? ) )
            // InternalMazeDsl.g:5251:1: ( ( rule__LootItemConfig__Group_7__0 )? )
            {
            // InternalMazeDsl.g:5251:1: ( ( rule__LootItemConfig__Group_7__0 )? )
            // InternalMazeDsl.g:5252:2: ( rule__LootItemConfig__Group_7__0 )?
            {
             before(grammarAccess.getLootItemConfigAccess().getGroup_7()); 
            // InternalMazeDsl.g:5253:2: ( rule__LootItemConfig__Group_7__0 )?
            int alt46=2;
            int LA46_0 = input.LA(1);

            if ( (LA46_0==81) ) {
                alt46=1;
            }
            switch (alt46) {
                case 1 :
                    // InternalMazeDsl.g:5253:3: rule__LootItemConfig__Group_7__0
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
    // InternalMazeDsl.g:5261:1: rule__LootItemConfig__Group__8 : rule__LootItemConfig__Group__8__Impl ;
    public final void rule__LootItemConfig__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5265:1: ( rule__LootItemConfig__Group__8__Impl )
            // InternalMazeDsl.g:5266:2: rule__LootItemConfig__Group__8__Impl
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
    // InternalMazeDsl.g:5272:1: rule__LootItemConfig__Group__8__Impl : ( '}' ) ;
    public final void rule__LootItemConfig__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5276:1: ( ( '}' ) )
            // InternalMazeDsl.g:5277:1: ( '}' )
            {
            // InternalMazeDsl.g:5277:1: ( '}' )
            // InternalMazeDsl.g:5278:2: '}'
            {
             before(grammarAccess.getLootItemConfigAccess().getRightCurlyBracketKeyword_8()); 
            match(input,32,FOLLOW_2); 
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
    // InternalMazeDsl.g:5288:1: rule__LootItemConfig__Group_7__0 : rule__LootItemConfig__Group_7__0__Impl rule__LootItemConfig__Group_7__1 ;
    public final void rule__LootItemConfig__Group_7__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5292:1: ( rule__LootItemConfig__Group_7__0__Impl rule__LootItemConfig__Group_7__1 )
            // InternalMazeDsl.g:5293:2: rule__LootItemConfig__Group_7__0__Impl rule__LootItemConfig__Group_7__1
            {
            pushFollow(FOLLOW_16);
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
    // InternalMazeDsl.g:5300:1: rule__LootItemConfig__Group_7__0__Impl : ( 'weight' ) ;
    public final void rule__LootItemConfig__Group_7__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5304:1: ( ( 'weight' ) )
            // InternalMazeDsl.g:5305:1: ( 'weight' )
            {
            // InternalMazeDsl.g:5305:1: ( 'weight' )
            // InternalMazeDsl.g:5306:2: 'weight'
            {
             before(grammarAccess.getLootItemConfigAccess().getWeightKeyword_7_0()); 
            match(input,81,FOLLOW_2); 
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
    // InternalMazeDsl.g:5315:1: rule__LootItemConfig__Group_7__1 : rule__LootItemConfig__Group_7__1__Impl ;
    public final void rule__LootItemConfig__Group_7__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5319:1: ( rule__LootItemConfig__Group_7__1__Impl )
            // InternalMazeDsl.g:5320:2: rule__LootItemConfig__Group_7__1__Impl
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
    // InternalMazeDsl.g:5326:1: rule__LootItemConfig__Group_7__1__Impl : ( ( rule__LootItemConfig__WeightAssignment_7_1 ) ) ;
    public final void rule__LootItemConfig__Group_7__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5330:1: ( ( ( rule__LootItemConfig__WeightAssignment_7_1 ) ) )
            // InternalMazeDsl.g:5331:1: ( ( rule__LootItemConfig__WeightAssignment_7_1 ) )
            {
            // InternalMazeDsl.g:5331:1: ( ( rule__LootItemConfig__WeightAssignment_7_1 ) )
            // InternalMazeDsl.g:5332:2: ( rule__LootItemConfig__WeightAssignment_7_1 )
            {
             before(grammarAccess.getLootItemConfigAccess().getWeightAssignment_7_1()); 
            // InternalMazeDsl.g:5333:2: ( rule__LootItemConfig__WeightAssignment_7_1 )
            // InternalMazeDsl.g:5333:3: rule__LootItemConfig__WeightAssignment_7_1
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


    // $ANTLR start "rule__DOUBLE__Group__0"
    // InternalMazeDsl.g:5342:1: rule__DOUBLE__Group__0 : rule__DOUBLE__Group__0__Impl rule__DOUBLE__Group__1 ;
    public final void rule__DOUBLE__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5346:1: ( rule__DOUBLE__Group__0__Impl rule__DOUBLE__Group__1 )
            // InternalMazeDsl.g:5347:2: rule__DOUBLE__Group__0__Impl rule__DOUBLE__Group__1
            {
            pushFollow(FOLLOW_49);
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
    // InternalMazeDsl.g:5354:1: rule__DOUBLE__Group__0__Impl : ( RULE_INT ) ;
    public final void rule__DOUBLE__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5358:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:5359:1: ( RULE_INT )
            {
            // InternalMazeDsl.g:5359:1: ( RULE_INT )
            // InternalMazeDsl.g:5360:2: RULE_INT
            {
             before(grammarAccess.getDOUBLEAccess().getINTTerminalRuleCall_0()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getDOUBLEAccess().getINTTerminalRuleCall_0()); 

            }


            }

        }
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
    // InternalMazeDsl.g:5369:1: rule__DOUBLE__Group__1 : rule__DOUBLE__Group__1__Impl ;
    public final void rule__DOUBLE__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5373:1: ( rule__DOUBLE__Group__1__Impl )
            // InternalMazeDsl.g:5374:2: rule__DOUBLE__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__DOUBLE__Group__1__Impl();

            state._fsp--;


            }

        }
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
    // InternalMazeDsl.g:5380:1: rule__DOUBLE__Group__1__Impl : ( ( rule__DOUBLE__Group_1__0 )? ) ;
    public final void rule__DOUBLE__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5384:1: ( ( ( rule__DOUBLE__Group_1__0 )? ) )
            // InternalMazeDsl.g:5385:1: ( ( rule__DOUBLE__Group_1__0 )? )
            {
            // InternalMazeDsl.g:5385:1: ( ( rule__DOUBLE__Group_1__0 )? )
            // InternalMazeDsl.g:5386:2: ( rule__DOUBLE__Group_1__0 )?
            {
             before(grammarAccess.getDOUBLEAccess().getGroup_1()); 
            // InternalMazeDsl.g:5387:2: ( rule__DOUBLE__Group_1__0 )?
            int alt47=2;
            int LA47_0 = input.LA(1);

            if ( (LA47_0==82) ) {
                alt47=1;
            }
            switch (alt47) {
                case 1 :
                    // InternalMazeDsl.g:5387:3: rule__DOUBLE__Group_1__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__DOUBLE__Group_1__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getDOUBLEAccess().getGroup_1()); 

            }


            }

        }
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


    // $ANTLR start "rule__DOUBLE__Group_1__0"
    // InternalMazeDsl.g:5396:1: rule__DOUBLE__Group_1__0 : rule__DOUBLE__Group_1__0__Impl rule__DOUBLE__Group_1__1 ;
    public final void rule__DOUBLE__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5400:1: ( rule__DOUBLE__Group_1__0__Impl rule__DOUBLE__Group_1__1 )
            // InternalMazeDsl.g:5401:2: rule__DOUBLE__Group_1__0__Impl rule__DOUBLE__Group_1__1
            {
            pushFollow(FOLLOW_16);
            rule__DOUBLE__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DOUBLE__Group_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DOUBLE__Group_1__0"


    // $ANTLR start "rule__DOUBLE__Group_1__0__Impl"
    // InternalMazeDsl.g:5408:1: rule__DOUBLE__Group_1__0__Impl : ( '.' ) ;
    public final void rule__DOUBLE__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5412:1: ( ( '.' ) )
            // InternalMazeDsl.g:5413:1: ( '.' )
            {
            // InternalMazeDsl.g:5413:1: ( '.' )
            // InternalMazeDsl.g:5414:2: '.'
            {
             before(grammarAccess.getDOUBLEAccess().getFullStopKeyword_1_0()); 
            match(input,82,FOLLOW_2); 
             after(grammarAccess.getDOUBLEAccess().getFullStopKeyword_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DOUBLE__Group_1__0__Impl"


    // $ANTLR start "rule__DOUBLE__Group_1__1"
    // InternalMazeDsl.g:5423:1: rule__DOUBLE__Group_1__1 : rule__DOUBLE__Group_1__1__Impl ;
    public final void rule__DOUBLE__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5427:1: ( rule__DOUBLE__Group_1__1__Impl )
            // InternalMazeDsl.g:5428:2: rule__DOUBLE__Group_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__DOUBLE__Group_1__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DOUBLE__Group_1__1"


    // $ANTLR start "rule__DOUBLE__Group_1__1__Impl"
    // InternalMazeDsl.g:5434:1: rule__DOUBLE__Group_1__1__Impl : ( RULE_INT ) ;
    public final void rule__DOUBLE__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5438:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:5439:1: ( RULE_INT )
            {
            // InternalMazeDsl.g:5439:1: ( RULE_INT )
            // InternalMazeDsl.g:5440:2: RULE_INT
            {
             before(grammarAccess.getDOUBLEAccess().getINTTerminalRuleCall_1_1()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getDOUBLEAccess().getINTTerminalRuleCall_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DOUBLE__Group_1__1__Impl"


    // $ANTLR start "rule__GameConfiguration__NameAssignment_1"
    // InternalMazeDsl.g:5450:1: rule__GameConfiguration__NameAssignment_1 : ( RULE_ID ) ;
    public final void rule__GameConfiguration__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5454:1: ( ( RULE_ID ) )
            // InternalMazeDsl.g:5455:2: ( RULE_ID )
            {
            // InternalMazeDsl.g:5455:2: ( RULE_ID )
            // InternalMazeDsl.g:5456:3: RULE_ID
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
    // InternalMazeDsl.g:5465:1: rule__GameConfiguration__ImportsAssignment_3 : ( ruleImport ) ;
    public final void rule__GameConfiguration__ImportsAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5469:1: ( ( ruleImport ) )
            // InternalMazeDsl.g:5470:2: ( ruleImport )
            {
            // InternalMazeDsl.g:5470:2: ( ruleImport )
            // InternalMazeDsl.g:5471:3: ruleImport
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
    // InternalMazeDsl.g:5480:1: rule__GameConfiguration__DifficultyAssignment_4 : ( ruleDifficultyConfig ) ;
    public final void rule__GameConfiguration__DifficultyAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5484:1: ( ( ruleDifficultyConfig ) )
            // InternalMazeDsl.g:5485:2: ( ruleDifficultyConfig )
            {
            // InternalMazeDsl.g:5485:2: ( ruleDifficultyConfig )
            // InternalMazeDsl.g:5486:3: ruleDifficultyConfig
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
    // InternalMazeDsl.g:5495:1: rule__GameConfiguration__OpponentsAssignment_5 : ( ruleOpponentConfig ) ;
    public final void rule__GameConfiguration__OpponentsAssignment_5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5499:1: ( ( ruleOpponentConfig ) )
            // InternalMazeDsl.g:5500:2: ( ruleOpponentConfig )
            {
            // InternalMazeDsl.g:5500:2: ( ruleOpponentConfig )
            // InternalMazeDsl.g:5501:3: ruleOpponentConfig
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
    // InternalMazeDsl.g:5510:1: rule__GameConfiguration__PatrolsAssignment_6 : ( rulePatrolConfig ) ;
    public final void rule__GameConfiguration__PatrolsAssignment_6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5514:1: ( ( rulePatrolConfig ) )
            // InternalMazeDsl.g:5515:2: ( rulePatrolConfig )
            {
            // InternalMazeDsl.g:5515:2: ( rulePatrolConfig )
            // InternalMazeDsl.g:5516:3: rulePatrolConfig
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
    // InternalMazeDsl.g:5525:1: rule__GameConfiguration__LootTablesAssignment_7 : ( ruleLootTableConfig ) ;
    public final void rule__GameConfiguration__LootTablesAssignment_7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5529:1: ( ( ruleLootTableConfig ) )
            // InternalMazeDsl.g:5530:2: ( ruleLootTableConfig )
            {
            // InternalMazeDsl.g:5530:2: ( ruleLootTableConfig )
            // InternalMazeDsl.g:5531:3: ruleLootTableConfig
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


    // $ANTLR start "rule__Import__ImportURIAssignment_1"
    // InternalMazeDsl.g:5540:1: rule__Import__ImportURIAssignment_1 : ( RULE_STRING ) ;
    public final void rule__Import__ImportURIAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5544:1: ( ( RULE_STRING ) )
            // InternalMazeDsl.g:5545:2: ( RULE_STRING )
            {
            // InternalMazeDsl.g:5545:2: ( RULE_STRING )
            // InternalMazeDsl.g:5546:3: RULE_STRING
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
    // InternalMazeDsl.g:5555:1: rule__DifficultyConfig__LevelAssignment_3 : ( ruleDifficultyLevel ) ;
    public final void rule__DifficultyConfig__LevelAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5559:1: ( ( ruleDifficultyLevel ) )
            // InternalMazeDsl.g:5560:2: ( ruleDifficultyLevel )
            {
            // InternalMazeDsl.g:5560:2: ( ruleDifficultyLevel )
            // InternalMazeDsl.g:5561:3: ruleDifficultyLevel
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
    // InternalMazeDsl.g:5570:1: rule__DifficultyConfig__InstantDeathAssignment_4_1 : ( ( 'true' ) ) ;
    public final void rule__DifficultyConfig__InstantDeathAssignment_4_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5574:1: ( ( ( 'true' ) ) )
            // InternalMazeDsl.g:5575:2: ( ( 'true' ) )
            {
            // InternalMazeDsl.g:5575:2: ( ( 'true' ) )
            // InternalMazeDsl.g:5576:3: ( 'true' )
            {
             before(grammarAccess.getDifficultyConfigAccess().getInstantDeathTrueKeyword_4_1_0()); 
            // InternalMazeDsl.g:5577:3: ( 'true' )
            // InternalMazeDsl.g:5578:4: 'true'
            {
             before(grammarAccess.getDifficultyConfigAccess().getInstantDeathTrueKeyword_4_1_0()); 
            match(input,11,FOLLOW_2); 
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
    // InternalMazeDsl.g:5589:1: rule__DifficultyConfig__SpeedMultiplierAssignment_5_1 : ( ruleDOUBLE ) ;
    public final void rule__DifficultyConfig__SpeedMultiplierAssignment_5_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5593:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:5594:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:5594:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:5595:3: ruleDOUBLE
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
    // InternalMazeDsl.g:5604:1: rule__DifficultyConfig__DamageMultiplierAssignment_6_1 : ( ruleDOUBLE ) ;
    public final void rule__DifficultyConfig__DamageMultiplierAssignment_6_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5608:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:5609:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:5609:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:5610:3: ruleDOUBLE
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
    // InternalMazeDsl.g:5619:1: rule__DifficultyConfig__MaxThreatAssignment_7_1 : ( RULE_INT ) ;
    public final void rule__DifficultyConfig__MaxThreatAssignment_7_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5623:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:5624:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:5624:2: ( RULE_INT )
            // InternalMazeDsl.g:5625:3: RULE_INT
            {
             before(grammarAccess.getDifficultyConfigAccess().getMaxThreatINTTerminalRuleCall_7_1_0()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getDifficultyConfigAccess().getMaxThreatINTTerminalRuleCall_7_1_0()); 

            }


            }

        }
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
    // InternalMazeDsl.g:5634:1: rule__DifficultyConfig__EnemyLimitsAssignment_8 : ( ruleEnemyLimit ) ;
    public final void rule__DifficultyConfig__EnemyLimitsAssignment_8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5638:1: ( ( ruleEnemyLimit ) )
            // InternalMazeDsl.g:5639:2: ( ruleEnemyLimit )
            {
            // InternalMazeDsl.g:5639:2: ( ruleEnemyLimit )
            // InternalMazeDsl.g:5640:3: ruleEnemyLimit
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
    // InternalMazeDsl.g:5649:1: rule__EnemyLimit__TypeAssignment_1 : ( ruleEnemyType ) ;
    public final void rule__EnemyLimit__TypeAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5653:1: ( ( ruleEnemyType ) )
            // InternalMazeDsl.g:5654:2: ( ruleEnemyType )
            {
            // InternalMazeDsl.g:5654:2: ( ruleEnemyType )
            // InternalMazeDsl.g:5655:3: ruleEnemyType
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
    // InternalMazeDsl.g:5664:1: rule__EnemyLimit__MaxCountAssignment_3 : ( RULE_INT ) ;
    public final void rule__EnemyLimit__MaxCountAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5668:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:5669:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:5669:2: ( RULE_INT )
            // InternalMazeDsl.g:5670:3: RULE_INT
            {
             before(grammarAccess.getEnemyLimitAccess().getMaxCountINTTerminalRuleCall_3_0()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getEnemyLimitAccess().getMaxCountINTTerminalRuleCall_3_0()); 

            }


            }

        }
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
    // InternalMazeDsl.g:5679:1: rule__OpponentConfig__NameAssignment_1 : ( RULE_ID ) ;
    public final void rule__OpponentConfig__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5683:1: ( ( RULE_ID ) )
            // InternalMazeDsl.g:5684:2: ( RULE_ID )
            {
            // InternalMazeDsl.g:5684:2: ( RULE_ID )
            // InternalMazeDsl.g:5685:3: RULE_ID
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
    // InternalMazeDsl.g:5694:1: rule__OpponentConfig__TypeAssignment_4 : ( ruleCharacterTypeEnum ) ;
    public final void rule__OpponentConfig__TypeAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5698:1: ( ( ruleCharacterTypeEnum ) )
            // InternalMazeDsl.g:5699:2: ( ruleCharacterTypeEnum )
            {
            // InternalMazeDsl.g:5699:2: ( ruleCharacterTypeEnum )
            // InternalMazeDsl.g:5700:3: ruleCharacterTypeEnum
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
    // InternalMazeDsl.g:5709:1: rule__OpponentConfig__DisplayNameAssignment_5_1 : ( RULE_STRING ) ;
    public final void rule__OpponentConfig__DisplayNameAssignment_5_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5713:1: ( ( RULE_STRING ) )
            // InternalMazeDsl.g:5714:2: ( RULE_STRING )
            {
            // InternalMazeDsl.g:5714:2: ( RULE_STRING )
            // InternalMazeDsl.g:5715:3: RULE_STRING
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
    // InternalMazeDsl.g:5724:1: rule__OpponentConfig__HealthAssignment_6_1 : ( RULE_INT ) ;
    public final void rule__OpponentConfig__HealthAssignment_6_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5728:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:5729:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:5729:2: ( RULE_INT )
            // InternalMazeDsl.g:5730:3: RULE_INT
            {
             before(grammarAccess.getOpponentConfigAccess().getHealthINTTerminalRuleCall_6_1_0()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getOpponentConfigAccess().getHealthINTTerminalRuleCall_6_1_0()); 

            }


            }

        }
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
    // InternalMazeDsl.g:5739:1: rule__OpponentConfig__SpeedAssignment_7_1 : ( ruleDOUBLE ) ;
    public final void rule__OpponentConfig__SpeedAssignment_7_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5743:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:5744:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:5744:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:5745:3: ruleDOUBLE
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
    // InternalMazeDsl.g:5754:1: rule__OpponentConfig__ThreatLevelAssignment_8_1 : ( ruleDOUBLE ) ;
    public final void rule__OpponentConfig__ThreatLevelAssignment_8_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5758:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:5759:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:5759:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:5760:3: ruleDOUBLE
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
    // InternalMazeDsl.g:5769:1: rule__OpponentConfig__EnabledAssignment_9_1 : ( ( rule__OpponentConfig__EnabledAlternatives_9_1_0 ) ) ;
    public final void rule__OpponentConfig__EnabledAssignment_9_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5773:1: ( ( ( rule__OpponentConfig__EnabledAlternatives_9_1_0 ) ) )
            // InternalMazeDsl.g:5774:2: ( ( rule__OpponentConfig__EnabledAlternatives_9_1_0 ) )
            {
            // InternalMazeDsl.g:5774:2: ( ( rule__OpponentConfig__EnabledAlternatives_9_1_0 ) )
            // InternalMazeDsl.g:5775:3: ( rule__OpponentConfig__EnabledAlternatives_9_1_0 )
            {
             before(grammarAccess.getOpponentConfigAccess().getEnabledAlternatives_9_1_0()); 
            // InternalMazeDsl.g:5776:3: ( rule__OpponentConfig__EnabledAlternatives_9_1_0 )
            // InternalMazeDsl.g:5776:4: rule__OpponentConfig__EnabledAlternatives_9_1_0
            {
            pushFollow(FOLLOW_2);
            rule__OpponentConfig__EnabledAlternatives_9_1_0();

            state._fsp--;


            }

             after(grammarAccess.getOpponentConfigAccess().getEnabledAlternatives_9_1_0()); 

            }


            }

        }
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
    // InternalMazeDsl.g:5784:1: rule__OpponentConfig__BehaviorAssignment_10_1 : ( ruleBehaviorTypeEnum ) ;
    public final void rule__OpponentConfig__BehaviorAssignment_10_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5788:1: ( ( ruleBehaviorTypeEnum ) )
            // InternalMazeDsl.g:5789:2: ( ruleBehaviorTypeEnum )
            {
            // InternalMazeDsl.g:5789:2: ( ruleBehaviorTypeEnum )
            // InternalMazeDsl.g:5790:3: ruleBehaviorTypeEnum
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
    // InternalMazeDsl.g:5799:1: rule__OpponentConfig__CharacterSpecificsAssignment_11 : ( ruleCharacterSpecifics ) ;
    public final void rule__OpponentConfig__CharacterSpecificsAssignment_11() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5803:1: ( ( ruleCharacterSpecifics ) )
            // InternalMazeDsl.g:5804:2: ( ruleCharacterSpecifics )
            {
            // InternalMazeDsl.g:5804:2: ( ruleCharacterSpecifics )
            // InternalMazeDsl.g:5805:3: ruleCharacterSpecifics
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
    // InternalMazeDsl.g:5814:1: rule__OpponentConfig__PatrolRefAssignment_12_1 : ( ( RULE_ID ) ) ;
    public final void rule__OpponentConfig__PatrolRefAssignment_12_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5818:1: ( ( ( RULE_ID ) ) )
            // InternalMazeDsl.g:5819:2: ( ( RULE_ID ) )
            {
            // InternalMazeDsl.g:5819:2: ( ( RULE_ID ) )
            // InternalMazeDsl.g:5820:3: ( RULE_ID )
            {
             before(grammarAccess.getOpponentConfigAccess().getPatrolRefPatrolConfigCrossReference_12_1_0()); 
            // InternalMazeDsl.g:5821:3: ( RULE_ID )
            // InternalMazeDsl.g:5822:4: RULE_ID
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
    // InternalMazeDsl.g:5833:1: rule__OpponentConfig__LootRefAssignment_13_1 : ( ( RULE_ID ) ) ;
    public final void rule__OpponentConfig__LootRefAssignment_13_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5837:1: ( ( ( RULE_ID ) ) )
            // InternalMazeDsl.g:5838:2: ( ( RULE_ID ) )
            {
            // InternalMazeDsl.g:5838:2: ( ( RULE_ID ) )
            // InternalMazeDsl.g:5839:3: ( RULE_ID )
            {
             before(grammarAccess.getOpponentConfigAccess().getLootRefLootTableConfigCrossReference_13_1_0()); 
            // InternalMazeDsl.g:5840:3: ( RULE_ID )
            // InternalMazeDsl.g:5841:4: RULE_ID
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
    // InternalMazeDsl.g:5852:1: rule__ZombieSpecifics__AttackDamageAssignment_3_1 : ( RULE_INT ) ;
    public final void rule__ZombieSpecifics__AttackDamageAssignment_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5856:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:5857:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:5857:2: ( RULE_INT )
            // InternalMazeDsl.g:5858:3: RULE_INT
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
    // InternalMazeDsl.g:5867:1: rule__ZombieSpecifics__InfectionLevelAssignment_4_1 : ( RULE_INT ) ;
    public final void rule__ZombieSpecifics__InfectionLevelAssignment_4_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5871:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:5872:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:5872:2: ( RULE_INT )
            // InternalMazeDsl.g:5873:3: RULE_INT
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
    // InternalMazeDsl.g:5882:1: rule__ZombieSpecifics__ResurrectionTimeAssignment_5_1 : ( RULE_INT ) ;
    public final void rule__ZombieSpecifics__ResurrectionTimeAssignment_5_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5886:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:5887:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:5887:2: ( RULE_INT )
            // InternalMazeDsl.g:5888:3: RULE_INT
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
    // InternalMazeDsl.g:5897:1: rule__GhostSpecifics__AttackDamageAssignment_3_1 : ( RULE_INT ) ;
    public final void rule__GhostSpecifics__AttackDamageAssignment_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5901:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:5902:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:5902:2: ( RULE_INT )
            // InternalMazeDsl.g:5903:3: RULE_INT
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
    // InternalMazeDsl.g:5912:1: rule__GhostSpecifics__VisibilityLevelAssignment_4_1 : ( RULE_INT ) ;
    public final void rule__GhostSpecifics__VisibilityLevelAssignment_4_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5916:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:5917:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:5917:2: ( RULE_INT )
            // InternalMazeDsl.g:5918:3: RULE_INT
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
    // InternalMazeDsl.g:5927:1: rule__GhostSpecifics__NonTangibilityEnergyAssignment_5_1 : ( ruleDOUBLE ) ;
    public final void rule__GhostSpecifics__NonTangibilityEnergyAssignment_5_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5931:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:5932:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:5932:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:5933:3: ruleDOUBLE
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
    // InternalMazeDsl.g:5942:1: rule__RangedSpecifics__AttackRangeAssignment_3_1 : ( ruleDOUBLE ) ;
    public final void rule__RangedSpecifics__AttackRangeAssignment_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5946:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:5947:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:5947:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:5948:3: ruleDOUBLE
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
    // InternalMazeDsl.g:5957:1: rule__RangedSpecifics__AttackCooldownAssignment_4_1 : ( RULE_INT ) ;
    public final void rule__RangedSpecifics__AttackCooldownAssignment_4_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5961:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:5962:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:5962:2: ( RULE_INT )
            // InternalMazeDsl.g:5963:3: RULE_INT
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
    // InternalMazeDsl.g:5972:1: rule__RangedSpecifics__AttackDamageAssignment_5_1 : ( RULE_INT ) ;
    public final void rule__RangedSpecifics__AttackDamageAssignment_5_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5976:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:5977:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:5977:2: ( RULE_INT )
            // InternalMazeDsl.g:5978:3: RULE_INT
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
    // InternalMazeDsl.g:5987:1: rule__RangedSpecifics__ProjectileSpeedAssignment_6_1 : ( ruleDOUBLE ) ;
    public final void rule__RangedSpecifics__ProjectileSpeedAssignment_6_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5991:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:5992:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:5992:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:5993:3: ruleDOUBLE
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
    // InternalMazeDsl.g:6002:1: rule__RangedSpecifics__ProjectileTypeAssignment_7_1 : ( ruleProjectileTypeEnum ) ;
    public final void rule__RangedSpecifics__ProjectileTypeAssignment_7_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6006:1: ( ( ruleProjectileTypeEnum ) )
            // InternalMazeDsl.g:6007:2: ( ruleProjectileTypeEnum )
            {
            // InternalMazeDsl.g:6007:2: ( ruleProjectileTypeEnum )
            // InternalMazeDsl.g:6008:3: ruleProjectileTypeEnum
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
    // InternalMazeDsl.g:6017:1: rule__RangedSpecifics__SplashRadiusAssignment_8_1 : ( ruleDOUBLE ) ;
    public final void rule__RangedSpecifics__SplashRadiusAssignment_8_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6021:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:6022:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:6022:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:6023:3: ruleDOUBLE
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
    // InternalMazeDsl.g:6032:1: rule__PatrolConfig__NameAssignment_1 : ( RULE_ID ) ;
    public final void rule__PatrolConfig__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6036:1: ( ( RULE_ID ) )
            // InternalMazeDsl.g:6037:2: ( RULE_ID )
            {
            // InternalMazeDsl.g:6037:2: ( RULE_ID )
            // InternalMazeDsl.g:6038:3: RULE_ID
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
    // InternalMazeDsl.g:6047:1: rule__PatrolConfig__VisionRangeAssignment_3_1 : ( ruleDOUBLE ) ;
    public final void rule__PatrolConfig__VisionRangeAssignment_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6051:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:6052:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:6052:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:6053:3: ruleDOUBLE
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


    // $ANTLR start "rule__PatrolConfig__ZoneAssignment_4_1"
    // InternalMazeDsl.g:6062:1: rule__PatrolConfig__ZoneAssignment_4_1 : ( rulePatrolZoneConfig ) ;
    public final void rule__PatrolConfig__ZoneAssignment_4_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6066:1: ( ( rulePatrolZoneConfig ) )
            // InternalMazeDsl.g:6067:2: ( rulePatrolZoneConfig )
            {
            // InternalMazeDsl.g:6067:2: ( rulePatrolZoneConfig )
            // InternalMazeDsl.g:6068:3: rulePatrolZoneConfig
            {
             before(grammarAccess.getPatrolConfigAccess().getZonePatrolZoneConfigParserRuleCall_4_1_0()); 
            pushFollow(FOLLOW_2);
            rulePatrolZoneConfig();

            state._fsp--;

             after(grammarAccess.getPatrolConfigAccess().getZonePatrolZoneConfigParserRuleCall_4_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__ZoneAssignment_4_1"


    // $ANTLR start "rule__PatrolConfig__WaypointsAssignment_7"
    // InternalMazeDsl.g:6077:1: rule__PatrolConfig__WaypointsAssignment_7 : ( ruleWaypoint ) ;
    public final void rule__PatrolConfig__WaypointsAssignment_7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6081:1: ( ( ruleWaypoint ) )
            // InternalMazeDsl.g:6082:2: ( ruleWaypoint )
            {
            // InternalMazeDsl.g:6082:2: ( ruleWaypoint )
            // InternalMazeDsl.g:6083:3: ruleWaypoint
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
    // InternalMazeDsl.g:6092:1: rule__PatrolConfig__WaypointsAssignment_8_1 : ( ruleWaypoint ) ;
    public final void rule__PatrolConfig__WaypointsAssignment_8_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6096:1: ( ( ruleWaypoint ) )
            // InternalMazeDsl.g:6097:2: ( ruleWaypoint )
            {
            // InternalMazeDsl.g:6097:2: ( ruleWaypoint )
            // InternalMazeDsl.g:6098:3: ruleWaypoint
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
    // InternalMazeDsl.g:6107:1: rule__PatrolZoneConfig__TopLeftXAssignment_4 : ( ruleDOUBLE ) ;
    public final void rule__PatrolZoneConfig__TopLeftXAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6111:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:6112:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:6112:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:6113:3: ruleDOUBLE
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
    // InternalMazeDsl.g:6122:1: rule__PatrolZoneConfig__TopLeftYAssignment_6 : ( ruleDOUBLE ) ;
    public final void rule__PatrolZoneConfig__TopLeftYAssignment_6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6126:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:6127:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:6127:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:6128:3: ruleDOUBLE
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
    // InternalMazeDsl.g:6137:1: rule__PatrolZoneConfig__WidthAssignment_9 : ( ruleDOUBLE ) ;
    public final void rule__PatrolZoneConfig__WidthAssignment_9() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6141:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:6142:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:6142:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:6143:3: ruleDOUBLE
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
    // InternalMazeDsl.g:6152:1: rule__PatrolZoneConfig__HeightAssignment_11 : ( ruleDOUBLE ) ;
    public final void rule__PatrolZoneConfig__HeightAssignment_11() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6156:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:6157:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:6157:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:6158:3: ruleDOUBLE
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
    // InternalMazeDsl.g:6167:1: rule__Waypoint__XAssignment_1 : ( ruleDOUBLE ) ;
    public final void rule__Waypoint__XAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6171:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:6172:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:6172:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:6173:3: ruleDOUBLE
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
    // InternalMazeDsl.g:6182:1: rule__Waypoint__YAssignment_3 : ( ruleDOUBLE ) ;
    public final void rule__Waypoint__YAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6186:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:6187:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:6187:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:6188:3: ruleDOUBLE
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
    // InternalMazeDsl.g:6197:1: rule__Waypoint__WaitTimeAssignment_5_1 : ( RULE_INT ) ;
    public final void rule__Waypoint__WaitTimeAssignment_5_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6201:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:6202:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:6202:2: ( RULE_INT )
            // InternalMazeDsl.g:6203:3: RULE_INT
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
    // InternalMazeDsl.g:6212:1: rule__LootTableConfig__NameAssignment_1 : ( RULE_ID ) ;
    public final void rule__LootTableConfig__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6216:1: ( ( RULE_ID ) )
            // InternalMazeDsl.g:6217:2: ( RULE_ID )
            {
            // InternalMazeDsl.g:6217:2: ( RULE_ID )
            // InternalMazeDsl.g:6218:3: RULE_ID
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
    // InternalMazeDsl.g:6227:1: rule__LootTableConfig__CapacityAssignment_3_1 : ( RULE_INT ) ;
    public final void rule__LootTableConfig__CapacityAssignment_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6231:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:6232:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:6232:2: ( RULE_INT )
            // InternalMazeDsl.g:6233:3: RULE_INT
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
    // InternalMazeDsl.g:6242:1: rule__LootTableConfig__ItemsAssignment_4 : ( ruleLootItemConfig ) ;
    public final void rule__LootTableConfig__ItemsAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6246:1: ( ( ruleLootItemConfig ) )
            // InternalMazeDsl.g:6247:2: ( ruleLootItemConfig )
            {
            // InternalMazeDsl.g:6247:2: ( ruleLootItemConfig )
            // InternalMazeDsl.g:6248:3: ruleLootItemConfig
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
    // InternalMazeDsl.g:6257:1: rule__LootItemConfig__NameAssignment_1 : ( RULE_ID ) ;
    public final void rule__LootItemConfig__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6261:1: ( ( RULE_ID ) )
            // InternalMazeDsl.g:6262:2: ( RULE_ID )
            {
            // InternalMazeDsl.g:6262:2: ( RULE_ID )
            // InternalMazeDsl.g:6263:3: RULE_ID
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
    // InternalMazeDsl.g:6272:1: rule__LootItemConfig__TypeAssignment_4 : ( ruleLootItemTypeEnum ) ;
    public final void rule__LootItemConfig__TypeAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6276:1: ( ( ruleLootItemTypeEnum ) )
            // InternalMazeDsl.g:6277:2: ( ruleLootItemTypeEnum )
            {
            // InternalMazeDsl.g:6277:2: ( ruleLootItemTypeEnum )
            // InternalMazeDsl.g:6278:3: ruleLootItemTypeEnum
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
    // InternalMazeDsl.g:6287:1: rule__LootItemConfig__ValueAssignment_6 : ( RULE_INT ) ;
    public final void rule__LootItemConfig__ValueAssignment_6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6291:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:6292:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:6292:2: ( RULE_INT )
            // InternalMazeDsl.g:6293:3: RULE_INT
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
    // InternalMazeDsl.g:6302:1: rule__LootItemConfig__WeightAssignment_7_1 : ( RULE_INT ) ;
    public final void rule__LootItemConfig__WeightAssignment_7_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6306:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:6307:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:6307:2: ( RULE_INT )
            // InternalMazeDsl.g:6308:3: RULE_INT
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

    // Delegated rules


 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000040700400000L,0x0000000000002000L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000200000002L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000040000000002L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000000400002L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000000002L,0x0000000000002000L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x000000000000E000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x000001F100000000L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000010000000002L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000000070000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x048FF00100400000L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000000000001800L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000000000780000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0070000100000000L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0310000100000000L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0488000000000000L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0xF810000100000000L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000000003800000L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000019L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000024L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000020L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x0000000000000000L,0x000000000000C000L});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0x0000000000000002L,0x000000000000C000L});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x000000003C000000L});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0x0000000100000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});

}